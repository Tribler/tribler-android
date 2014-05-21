__author__ = 'user'

# This file exposes some DB Tuples that are defined in Tribler.Main.Utility.GuiDBTuples.
# Because the tuples found in said package are tied to some vwxGUI functions, it overrides those

from Tribler.Main.Utility.GuiDBTuples import cache, cacheProperty
from Tribler.Main.Utility.GuiDBTuples import Channel as vwxChannel
from Tribler.Main.Utility.GuiDBTuples import RemoteChannel as vwxRemoteChannel
from Tribler.Main.Utility.GuiDBTuples import ChannelTorrent as vwxChannelTorrent
from Tribler.Main.Utility.GuiDBTuples import RemoteChannelTorrent as vwxRemoteChannelTorrent


class Channel(vwxChannel):
    @cache
    def getState(self):
    #    if self.isDispersy():
    #        @forceAndReturnDispersyThread
    #        def do_dispersy():
    #            from Tribler.Main.vwxGUI.SearchGridManager import ChannelManager

    #            self._logger.debug("Channel: fetching getChannelStateByCID from DB %s", self)

    #            searchManager = ChannelManager.getInstance()
    #            result = searchManager.getChannelStateByCID(self.dispersy_cid)
    #            return result

    #        return do_dispersy()

    #    return ChannelCommunity.CHANNEL_CLOSED, self.isMyChannel()
        pass

    def loadPopularTorrentNames(self, num_torrents, force_refresh=False):
#        if not self.popular_torrents or force_refresh:
#            from Tribler.Main.vwxGUI.GuiUtility import GUIUtility
#            from Tribler.Main.vwxGUI.SearchGridManager import ChannelManager
#            results = ChannelManager.getInstance().getMostPopularTorrentsFromChannel(self.id, ['Torrent.Name'], family_filter=GUIUtility.getInstance().getFamilyFilter(), limit=num_torrents)
#            self.popular_torrents = [result[0] for result in results]
        pass

class RemoteChannel(vwxRemoteChannel):
    pass

class ChannelTorrent(vwxChannelTorrent):
    pass

class RemoteChannelTorrent(vwxRemoteChannelTorrent):
    pass