__author__ = 'user'

import threading
import time
import sys
import os

# SETUP ENVIRONMENT, DO THIS FIRST
from Environment import init_environment
init_environment('from service')

import logging
logging.basicConfig(level=logging.INFO)
_logger = logging.getLogger(__name__)

arg = os.getenv('PYTHON_SERVICE_ARGUMENT')

# Set up environment

if 'ANDROID_PRIVATE' in os.environ:
    print "We are running on android/p4a"

    # Set P4A egg cache
    os.environ["PYTHON_EGG_CACHE"] = "/data/data/org.tsap.tribler.full/cache"

    # Set tribler data dir
    os.environ['TRIBLER_STATE_DIR'] = os.path.join(os.environ['ANDROID_PRIVATE'], '.Tribler')

    # Running on Android
    os.environ['ANDROID_HOST'] = "YES"
else:
    print "We are running on a pc"

    # Set tribler data dir
    os.environ['TRIBLER_STATE_DIR'] = os.path.abspath(os.path.join(os.getcwd(), '../.Tribler-data'))
    print os.environ['TRIBLER_STATE_DIR']

    # Test Android code
    os.environ['ANDROID_HOST'] = "SIMULATED"
    os.environ['ANDROID_PRIVATE'] = os.getcwd()

# Local files
from SearchManager import SearchManager
#from TorrentManager import TorrentManager
from ChannelManager import ChannelManager
from TriblerSession import TriblerSession
from XMLRpc import XMLRPCServer


def loginfo(str):
    _logger.info(str)

if __name__ == '__main__':
    _logger.info("Python egg cache: %s" % os.environ["PYTHON_EGG_CACHE"])
    _logger.info("$ANDROID_PRIVATE=%s", os.environ["ANDROID_PRIVATE"])
    _logger.info("cwd: %s" % os.getcwd())

    _logger.info("Loading TriblerSessionService")
    tribler = TriblerSession()
    tribler.start_service()

    _logger.info("Loading XMLRPCServer")
    xmlrpc = XMLRPCServer(iface="0.0.0.0", port=8001)
    xmlrpc.register_function(loginfo, "info")

    _logger.info("Loading ChannelManager")
    cm = ChannelManager(tribler.get_session(), xmlrpc)

    #_logger.info("Loading TorrentManager")
    #tm = TorrentManager(tribler.get_session(), xmlrpc)

    _logger.info("Now running XMLRPC on http://%s:%s/tribler" % (xmlrpc._iface, xmlrpc._port))
    xmlrpc.start_server()