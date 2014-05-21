__author__ = 'user'

import threading

# Setup logger
import logging
_logger = logging.getLogger(__name__)

# Tribler defs
from Tribler.Core.simpledefs import NTFY_MISC, NTFY_TORRENTS, NTFY_MYPREFERENCES, \
    NTFY_VOTECAST, NTFY_CHANNELCAST, NTFY_METADATA, \
    DLSTATUS_METADATA, DLSTATUS_WAITING4HASHCHECK

# DB Tuples
from rpcdbtuples import Channel

# Tribler communities
from Tribler.community.search.community import SearchCommunity
from Tribler.community.allchannel.community import AllChannelCommunity
#from Tribler.community.channel.community import ChannelCommunity
#from Tribler.community.channel.preview import PreviewChannelCommunity
#from Tribler.community.metadata.community import MetadataCommunity


class SearchManager():

    _session = None

    _channel_keywords = []
    _channel_results = []

    _torrent_keywords = []
    _torrent_results = []

    # Code to make this a singleton
    __single = None

    connected = False

    _misc_db = None
    _torrent_db = None
    _channelcast_db = None
    _votecast_db = None

    _dispersy = None


    def __init__(self, session, xmlrpc=None):
        if SearchManager.__single:
            raise RuntimeError("ChannelManager is singleton")
        self.connected = False

        self._session = session

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
        xmlrpc.register_function(self.search_channel_local, "search.local_channel")
        xmlrpc.register_function(self.search_channel_remote, "search.remote_channel")

        # torrents
        xmlrpc.register_function(self.search_torrent_local, "search.local_torrent")
        xmlrpc.register_function(self.search_torrent_remote, "search.remote_torrent")

    def search_channel_local(self, keyword):
        channel_results = {}
        hits = self._channelcast_db.searchChannels(keyword)

        _, channels = self._createChannels(hits)

        for channel in channels:
            channel_results[channel.id] = channel

        return channel_results

    def _createChannels(self, hits, filterTorrents=True):
        channels = []
        for hit in hits:
            channel = Channel(*hit)
            channels.append(channel)

        return len(channels), channels

    def search_channel_remote(self, keyword):
        nr_requests_made = 0

        #self._channel_keywords.append(keyword)
        self._channel_keywords.append(u"vodo")

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

        channels = self.getChannelsByCID(answers.keys())
        _logger.error("@@@@@ Channels found:\n%s" % channels)
        self._channel_results = channels

    def getChannelsByCID(self, channel_cids):
        channels = self._channelcast_db.getChannelsByCID(channel_cids)
        return self._createChannels(channels)

    def search_torrent_local(self, keyword):
        pass

    def search_torrent_remote(self, keyword):
        pass

    def _search_torrent_remote_callback(self):
        pass
