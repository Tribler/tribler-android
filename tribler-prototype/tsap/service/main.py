__author__ = 'user'

import threading
import time
import sys
import os

# SETUP ENVIRONMENT, DO THIS FIRST
from Environment import init_environment
init_environment()

# Init logger
import logging
logging.basicConfig(level=logging.INFO)
_logger = logging.getLogger(__name__)

arg = os.getenv('PYTHON_SERVICE_ARGUMENT')

from Tribler.Core.osutils import is_android

# Local files
from TorrentManager import TorrentManager
from ChannelManager import ChannelManager
from DownloadManager import DownloadManager
from TriblerSession import TriblerSession
from XMLRpc import XMLRPCServer


if __name__ == '__main__':
    _logger.error("Loading TriblerSessionService")
    tribler = TriblerSession()
    tribler.start_session()

    _logger.error("Loading XMLRPCServer")
    xmlrpc = XMLRPCServer(iface="0.0.0.0", port=8000)

    _logger.error("Loading ChannelManager")
    cm = ChannelManager(tribler.get_session(), xmlrpc)

    _logger.error("Loading TorrentManager")
    tm = TorrentManager(tribler.get_session(), xmlrpc)

    _logger.error("Loading DownloadManager")
    dm = DownloadManager(tribler.get_session(), xmlrpc)

    #dl_ad = dm.add_torrent(os.path.join(os.getcwdu(), "..", "data", "ad.torrent"), os.path.join(os.getcwdu(), "downloaded_videos"))
    #dm.add_torrent(os.path.join(os.getcwdu(), "..", "data", "pone.torrent"), os.path.join(os.getcwdu(), "downloaded_videos"))

    #if os.environ['ANDROID_HOST'].startswith("ANDROID"):
    #    dm.launch_vlc(dl_ad)

    #while True:
    #    for dl in dm.get_downloads():
    #        print dm._getDownload(dl)
    #    time.sleep(5)

    _logger.error("Now running XMLRPC on http://%s:%s/tribler" % (xmlrpc._iface, xmlrpc._port))
    xmlrpc.start_server()