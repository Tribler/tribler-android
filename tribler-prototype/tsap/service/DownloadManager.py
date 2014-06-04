__author__ = 'user'

import threading
import binascii
import time
import os

# Setup logger
import logging
_logger = logging.getLogger(__name__)

from Tribler.Core.TorrentDef import TorrentDef
from Tribler.Core.simpledefs import DLSTATUS_DOWNLOADING, DLSTATUS_SEEDING
from Tribler.Main.globals import DefaultDownloadStartupConfig

from Tribler.Core.Video.VideoPlayer import VideoPlayer


# Tribler defs
from Tribler.Core.simpledefs import NTFY_MISC, NTFY_TORRENTS, NTFY_MYPREFERENCES, \
    NTFY_VOTECAST, NTFY_CHANNELCAST, NTFY_METADATA, \
    DLSTATUS_METADATA, DLSTATUS_WAITING4HASHCHECK

# DB Tuples
from RpcDBTuples import Torrent, ChannelTorrent, RemoteChannelTorrent, RemoteTorrent

# Tribler communities
from Tribler.community.search.community import SearchCommunity
from Tribler.community.allchannel.community import AllChannelCommunity
#from Tribler.community.channel.community import ChannelCommunity
#from Tribler.community.channel.preview import PreviewChannelCommunity
#from Tribler.community.metadata.community import MetadataCommunity

from Tribler.Core.Search.SearchManager import split_into_keywords

from Tribler.dispersy.util import call_on_reactor_thread


class DownloadManager():
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

    _keywords = []
    _results = []
    _result_infohashes = []

    def __init__(self, session, xmlrpc=None):
        if DownloadManager.__single:
            raise RuntimeError("DownloadManager is singleton")

        self.connected = False

        self._session = session
        self._remote_lock = threading.Lock()

        self._connect()

        if xmlrpc:
            self._xmlrpc_register(xmlrpc)

    def getInstance(*args, **kw):
        if DownloadManager.__single is None:
            DownloadManager.__single = DownloadManager(*args, **kw)
        return DownloadManager.__single
    getInstance = staticmethod(getInstance)

    def delInstance(*args, **kw):
        DownloadManager.__single = None
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
            raise RuntimeError('TorrentManager already connected')

    def _xmlrpc_register(self, xmlrpc):
        pass

    def add_torrent(self, torrent_path, destination_path):
        _logger.error("Downloading %s to %s" % (torrent_path, destination_path))

        tdef = TorrentDef.load(torrent_path)
        defaultDLConfig = DefaultDownloadStartupConfig.getInstance()
        dscfg = defaultDLConfig.copy()

        dscfg.set_dest_dir(destination_path)

        dl = self._session.start_download(tdef, dscfg)

        while not dl.handle:
            time.sleep(1)
            _logger.error("Waiting for libtorrent (%s)" % dl.tdef.get_name())

        return dl

    def launch_vlc(self, dl):
        assert os.environ['ANDROID_HOST'].startswith("ANDROID")
        from vlcutil import launchVLC

        MINIMAL_DOWNLOAD_PROGRESS = 0.10

        vlcurl = "http://127.0.0.1:%s/%s/0" % (self._session.get_videoplayer_port(), binascii.hexlify(dl.tdef.get_infohash()))

        print ">>>>>>>>>>>>>>> Listening on %s" % vlcurl

        while dl.get_progress() < MINIMAL_DOWNLOAD_PROGRESS:
            print "DL Progress %s > %s not met yet.." % (dl.get_progress(), MINIMAL_DOWNLOAD_PROGRESS)
            print self._getDownload(dl)
            time.sleep(5)

        launchVLC(vlcurl)

        return dl

    def get_downloads(self):
        return self._session.get_downloads()

    def _getDownload(self, torrentimpl):
        return {'name': torrentimpl.tdef.get_name(),
                'progress': torrentimpl.get_progress(),
                'length': torrentimpl.get_length(),
                'speed_up': torrentimpl.get_current_speed('up'),
                'speed_down': torrentimpl.get_current_speed('down'),
                }