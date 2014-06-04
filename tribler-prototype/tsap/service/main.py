__author__ = 'user'

import threading
import time
import sys
import os

# SETUP ENVIRONMENT, DO THIS FIRST
from Environment import init_environment
init_environment()
print os.environ

import logging
logging.basicConfig(level=logging.INFO)
_logger = logging.getLogger(__name__)

arg = os.getenv('PYTHON_SERVICE_ARGUMENT')

# Local files
from TorrentManager import TorrentManager
from ChannelManager import ChannelManager
from TriblerSession import TriblerSession
from XMLRpc import XMLRPCServer


def loginfo(str):
    _logger.info(str)

if __name__ == '__main__':
    _logger.error("Python egg cache: %s" % os.environ["PYTHON_EGG_CACHE"])
    _logger.error("$ANDROID_PRIVATE=%s", os.environ["ANDROID_PRIVATE"])
    _logger.error("cwd: %s" % os.getcwd())

    _logger.error("Loading TriblerSessionService")
    tribler = TriblerSession()
    tribler.start_service()

    _logger.error("Loading XMLRPCServer")
    xmlrpc = XMLRPCServer(iface="0.0.0.0", port=8000)
    xmlrpc.register_function(loginfo, "info")

    _logger.error("Loading ChannelManager")
    cm = ChannelManager(tribler.get_session(), xmlrpc)

    _logger.error("Loading TorrentManager")
    tm = TorrentManager(tribler.get_session(), xmlrpc)

    #test_libtorrent(tribler.get_session())

    _logger.error("Now running XMLRPC on http://%s:%s/tribler" % (xmlrpc._iface, xmlrpc._port))
    xmlrpc.start_server()