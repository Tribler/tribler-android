__author__ = 'user'

import threading
from time import time

# Setup logger
import logging
_logger = logging.getLogger(__name__)

# Tribler defs
from Tribler.Core.simpledefs import NTFY_MISC, NTFY_TORRENTS, NTFY_MYPREFERENCES, \
    NTFY_VOTECAST, NTFY_CHANNELCAST, NTFY_METADATA, \
    DLSTATUS_METADATA, DLSTATUS_WAITING4HASHCHECK

# DB Tuples
from RpcDBTuples import Channel, RemoteChannel, ChannelTorrent, RemoteChannelTorrent

# Tribler communities
from Tribler.community.search.community import SearchCommunity
from Tribler.community.allchannel.community import AllChannelCommunity
#from Tribler.community.channel.community import ChannelCommunity
#from Tribler.community.channel.preview import PreviewChannelCommunity
#from Tribler.community.metadata.community import MetadataCommunity

from Tribler.Core.Search.SearchManager import split_into_keywords

from Tribler.community.channel.community import ChannelCommunity, \
    forceDispersyThread, forcePrioDispersyThread, warnDispersyThread

class SearchManager():
    # Code to make this a singleton
    __single = None

    connected = False

    _session = None
    _dispersy = None
    _remote_lock = None

    _misc_db = None
    _torrent_db = None
    _channelcast_db = None
    _votecast_db = None

    _channel_keywords = []
    _channel_results = []

    _torrent_keywords = []
    _torrent_results = []

    def __init__(self, session, xmlrpc=None):
        if SearchManager.__single:
            raise RuntimeError("ChannelManager is singleton")
        self.connected = False

        self._session = session
        self._remote_lock = threading.Lock()

        self._connect()

        if xmlrpc:
            self._xmlrpc_register(xmlrpc)

    def getInstance(*args, **kw):
        if SearchManager.__single is None:
            SearchManager.__single = SearchManager(*args, **kw)
        return SearchManager.__single
    getInstance = staticmethod(getInstance)

    def delInstance(*args, **kw):
        SearchManager.__single = None
    delInstance = staticmethod(delInstance)

    def _connect(self):
        if not self.connected:
            self.connected = True
            self._misc_db = self._session.open_dbhandler(NTFY_MISC)
            self._torrent_db = self._session.open_dbhandler(NTFY_TORRENTS)
            self._channelcast_db = self._session.open_dbhandler(NTFY_CHANNELCAST)
            self._votecast_db = self._session.open_dbhandler(NTFY_VOTECAST)

            self._dispersy = self._session.lm.dispersy
        else:
            raise RuntimeError('ChannelManager already connected')

    def _xmlrpc_register(self, xmlrpc):
        # channels
        xmlrpc.register_function(self.search_channel_set_keywords, "search.keywords.set_channels")
        xmlrpc.register_function(self.search_channel_get_local, "search.local.get_channels")
        xmlrpc.register_function(self.search_channel_do_remote, "search.remote.do_channels")
        xmlrpc.register_function(self.search_channel_get_results, "search.remote.get_channels")
        xmlrpc.register_function(self.search_channel_peek_results, "search.remote.peek_channels")

        # torrents
        xmlrpc.register_function(self.search_torrent_set_keywords, "search.keywords.set_torrents")
        xmlrpc.register_function(self.search_torrent_get_local, "search.local.get_torrents")
        xmlrpc.register_function(self.search_torrent_do_remote, "search.remote.do_torrents")
        xmlrpc.register_function(self.search_torrent_get_results, "search.remote.get_torrents")

        # all
        xmlrpc.register_function(self.search_set_keywords, "search.keywords.set_all")
        xmlrpc.register_function(self.search_do_remote, "search.remote.do_all")

    def search_set_keywords(self, keywords):
        return self.search_channel_set_keywords(keywords) and self.search_torrent_set_keywords(keywords)

    def search_do_remote(self):
        return self.search_channel_do_remote() and self.search_torrent_do_remote()

    def search_channel_set_keywords(self, keywords):
        keywords = split_into_keywords(unicode(keywords))
        keywords = [keyword for keyword in keywords if len(keyword) > 1]

        if keywords == self._channel_keywords:
            return True

        try:
            self._remote_lock.acquire()

            self._channel_keywords = keywords
            self._channel_results = []
        finally:
            self._remote_lock.release()

        return True

    def search_channel_get_local(self):
        channel_results = {}
        hits = self._channelcast_db.searchChannels(self._channel_keywords)

        _, channels = self._createChannels(hits)

        _logger.info("@@@ Found %s local channels" % len(channels))

        for channel in channels:
            channel_results[str(channel.id)] = str(channel)
            _logger.info("@@@@ Found local channel: %s" % channel)

        return channel_results

    def search_channel_get_results(self):
        begintime = time()

        try:
            self._remote_lock.acquire()

            if len(self._channel_results) <= 0:
                return False

            for remoteItem, permid in self._channel_results:

                channel = None
                if not isinstance(remoteItem, Channel):
                    channel_id, _, infohash, torrent_name, timestamp = remoteItem

                    if channel_id not in self._channel_results:
                        channel = self.getChannel(channel_id)
                    else:
                        channel = self._channel_results[channel_id]

                    torrent = channel.getTorrent(infohash)
                    if not torrent:
                        torrent = RemoteChannelTorrent(torrent_id=None, infohash=infohash, name=torrent_name, channel=channel, query_permids=set())
                        channel.addTorrent(torrent)

                    if not torrent.query_permids:
                        torrent.query_permids = set()
                    torrent.query_permids.add(permid)

                    channel.nr_torrents += 1
                    channel.modified = max(channel.modified, timestamp)
                else:
                    channel = remoteItem

                if channel and not channel.id in self._channel_results:
                    self._channel_results[channel.id] = channel
                    hitsUpdated = True # TODO: USE THIS SOMEHOW LATER
        finally:
            self._remote_lock.release()

        _logger.debug("ChannelManager: getChannelHits took %s", time() - begintime)

        print self._channel_results

        results = []
        for ch in self._channel_results:
            print ch.torrents
            results.append(str(ch))
        return results

    def search_channel_peek_results(self):
        return len(self._channel_results)

    def search_channel_do_remote(self):
        nr_requests_made = 0

        if self._dispersy:
            for community in self._dispersy.get_communities():
                if isinstance(community, AllChannelCommunity):
                    nr_requests_made = community.create_channelsearch(self._channel_keywords, self._search_channel_remote_callback)
                    if not nr_requests_made:
                        _logger.info("Could not send search in AllChannelCommunity, no verified candidates found")
                    break

            else:
                _logger.info("Could not send search in AllChannelCommunity, community not found")

        else:
            _logger.info("Could not send search in AllChannelCommunity, Dispersy not found")

        return nr_requests_made

    def _search_channel_remote_callback(self, kws, answers):
        _logger.error("@@@@@@@@@ DISPERY CALLBACK!")
        _logger.error("@@@@@ CALL BACK DATA: %s\n%s" % (kws, answers))

        # Ignore searches we don't want (anymore)
        if not self._channel_keywords == kws:
            return

        try:
            self._remote_lock.acquire()

            channels = self.getChannelsByCID(answers.keys())
            for channel in channels:
                try:
                    _logger.error("@@@@@ Channel found:\n%s" % str(channel[0]))
                except:
                    pass

            self._channel_results.append(channels)
        finally:
            self._remote_lock.release()

    def getChannel(self, channel_id):
        channel = self.channelcast_db.getChannel(channel_id)
        return self._getChannel(channel)

    def getChannelsByCID(self, channel_cids):
        channels = self._channelcast_db.getChannelsByCID(channel_cids)
        return self._createChannels(channels)

    def _createChannels(self, hits, filterTorrents=True):
        channels = []
        for hit in hits:
            channel = Channel(*hit)
            channels.append(channel)

        return len(channels), channels

    def search_torrent_set_keywords(self, keywords):
        keywords = split_into_keywords(unicode(keywords))
        keywords = [keyword for keyword in keywords if len(keyword) > 1]

        if keywords == self._torrent_keywords:
            return

        try:
            self._remote_lock.acquire()

            self._torrent_keywords = keywords
            self._torrent_results = []
        finally:
            self._remote_lock.release()

    def search_torrent_get_local(self):
        pass

    def search_torrent_get_results(self):
        pass

    @forceDispersyThread
    def search_torrent_do_remote(self):
        nr_requests_made = 0
        if self._dispersy:
            for community in self._dispersy.get_communities():
                if isinstance(community, SearchCommunity):
                    nr_requests_made = community.create_search(self._torrent_keywords, self._search_torrent_remote_callback)
                    if not nr_requests_made:
                        _logger.info("Could not send search in SearchCommunity, no verified candidates found")
                    break

            else:
                _logger.info("Could not send search in SearchCommunity, community not found")

        else:
            _logger.info("Could not send search in SearchCommunity, Dispersy not found")

        return nr_requests_made

    def _search_torrent_remote_callback(self, keywords, results, candidate):
#        try:
        _logger.debug("TorrentSearchGridManager: gotRemoteHist: got %s unfiltered results for %s %s %s", len(results), keywords, candidate, time())
        return


