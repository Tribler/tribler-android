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

class ChannelManager():
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
        if ChannelManager.__single:
            raise RuntimeError("ChannelManager is singleton")
        self.connected = False

        self._session = session
        self._remote_lock = threading.Lock()

        self._connect()

        if xmlrpc:
            self._xmlrpc_register(xmlrpc)

    def getInstance(*args, **kw):
        if ChannelManager.__single is None:
            ChannelManager.__single = ChannelManager(*args, **kw)
        return ChannelManager.__single
    getInstance = staticmethod(getInstance)

    def delInstance(*args, **kw):
        ChannelManager.__single = None
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
        xmlrpc.register_function(self.get_local, "channels.get_local")
        xmlrpc.register_function(self.search_remote, "channels.search_remote")
        xmlrpc.register_function(self.get_remote_results_count, "channels.get_remote_results_count")
        xmlrpc.register_function(self.get_remote_results, "channels.get_remote_results")
        xmlrpc.register_function(self.subscribe, "channels.subscribe")

    def get_local(self, filter):
        try:
            self._set_keywords(filter)
        except:
            return False

        hits = self._channelcast_db.searchChannels(self._channel_keywords)

        _, channels = self._createChannels(hits)

        _logger.info("@@@ Found %s local channels" % len(channels))

        return self._prepare_channels(channels)

    def _prepare_channels(self, chs):
        channels = []
        for ch in chs:
            try:
                channels.append(self._prepare_channel(ch))
            except:
                pass

        return channels

    def _prepare_channel(self, ch):
        assert isinstance(ch, Channel)

        return {'id': ch.id,
                #'dispersy_cid': ch.dispersy_cid,
                'name': ch.name,
                'description': ch.description,
                'nr_torrent': ch.nr_torrents,
                'nr_favorites': ch.nr_favorites or 0,
                'nr_spam': ch.nr_spam or 0,
                'my_vote': ch.my_vote,
                'modified': ch.modified,
                'my_channel': ch.my_channel,
                #'torrents': None,
                #'populair_torrents': None,
                }

    def search_remote(self, keywords):
        try:
            self._set_keywords(keywords)
        except:
            return False

        nr_requests_made = 0

        if self._dispersy:
            for community in self._dispersy.get_communities():
                if isinstance(community, AllChannelCommunity):
                    nr_requests_made = community.create_channelsearch(self._channel_keywords, self._search_remote_callback)
                    if not nr_requests_made:
                        _logger.info("Could not send search in AllChannelCommunity, no verified candidates found")
                    break

            else:
                _logger.info("Could not send search in AllChannelCommunity, community not found")

        else:
            _logger.info("Could not send search in AllChannelCommunity, Dispersy not found")

        return nr_requests_made

    def _search_remote_callback(self, kws, answers):
        _logger.error("@@@@@@@@@ DISPERY CALLBACK!")
        _logger.error("@@@@@ CALL BACK DATA: %s\n%s" % (kws, answers))

        # Ignore searches we don't want (anymore)
        if not self._channel_keywords == kws:
            return

        try:
            self._remote_lock.acquire()

            _, channels = self.getChannelsByCID(answers.keys())
            for channel in channels:
                try:
                    _logger.error("@@@@@ Channel found:\n%s" % str(channel[0]))
                except:
                    pass

            self._channel_results += channels
        finally:
            self._remote_lock.release()

    def get_remote_results(self):
        begintime = time()

        ret = self._prepare_channels(self._channel_results)
        return ret

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

        _logger.debug("#### HITS ARE UPDATED? %s" % hitsUpdated)

        _logger.debug("ChannelManager: getChannelHits took %s", time() - begintime)

        ret = self._prepare_channels(self._channel_results)
        print ret
        return ret

    def get_remote_results_count(self):
        return len(self._channel_results)

    def subscribe(self):
        return False



    def _set_keywords(self, keywords):
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

    def getChannel(self, channel_id):
        channel = self._channelcast_db.getChannel(channel_id)
        return self._getChannel(channel)

    def _getChannel(self, channel):
        if channel:
            channel = self._createChannel(channel)

            # check if we need to convert our vote
            if channel.isDispersy() and channel.my_vote != 0:
                dispersy_id = self._votecast_db.getDispersyId(channel.id, None) or ''
                if dispersy_id <= 0:
                    timestamp = self._votecast_db.getTimestamp(channel.id, None)
                    # TODO: self.do_vote(channel.id, channel.my_vote, timestamp)

        return channel

    def getChannelsByCID(self, channel_cids):
        channels = self._channelcast_db.getChannelsByCID(channel_cids)
        return self._createChannels(channels)

    def _createChannel(self, hit):
        return Channel(*hit)

    def _createChannels(self, hits, filterTorrents=True):
        channels = []
        for hit in hits:
            channel = Channel(*hit)
            channels.append(channel)

        return len(channels), channels