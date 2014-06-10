# coding: utf-8

# Written by Wendo Sabée
# The main class that loads the Tribler session, all managers and sets up a XML-RPC server

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


class TSAP():
    tribler = None
    xmlrpc = None
    dm = None
    tm = None
    cm = None

    def __init__(self):
        pass

    def run(self):
        """
        This sets up a Tribler session, loads the managers and the XML-RPC server.
        :return: Nothing.
        """

        _logger.error("Loading TriblerSessionService")
        self.tribler = TriblerSession()
        self.tribler.start_session()

        _logger.error("Loading XMLRPCServer")
        self.xmlrpc = XMLRPCServer(iface="0.0.0.0", port=8000)

        _logger.error("Loading ChannelManager")
        self.cm = ChannelManager(self.tribler.get_session(), self.xmlrpc)

        _logger.error("Loading TorrentManager")
        self.tm = TorrentManager(self.tribler.get_session(), self.xmlrpc)

        _logger.error("Loading DownloadManager")
        self.dm = DownloadManager(self.tribler.get_session(), self.xmlrpc)

        _logger.error("Now running XMLRPC on http://%s:%s/tribler" % (self.xmlrpc._iface, self.xmlrpc._port))
        self.xmlrpc.start_server()

    def test_sintel(self):
        """
        Test the torrent download functionality by downloading sintel and starting a VLC stream.
        :return: Nothing.
        """
        dl_sintel = self.dm.add_torrent(os.path.join(os.getcwdu(), "..", "data", "sintel.torrent"), os.path.join(os.getcwdu(), "downloaded_videos"))

        if is_android(strict=True):
            self.dm.launch_vlc(dl_sintel)

        while True:
            for dl in self.dm.get_downloads():
                print self.dm._getDownload(dl)
            time.sleep(5)


if __name__ == '__main__':
    tsap = TSAP()
    tsap.run()

    #tsap.test_sintel()