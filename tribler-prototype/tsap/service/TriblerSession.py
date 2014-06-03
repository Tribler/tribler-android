__author__ = 'user'

# TODO: FIND OUT WHICH OF THESE CAN BE REMOVED. IF ALL ARE REMOVED, DISPERSY HANGS ON STARTUP.
import argparse
import logging.config
import os
import sys
import threading
import time
from threading import Thread, Event
from traceback import print_exc
import twisted

from Tribler.Core.RawServer.RawServer import RawServer
from Tribler.community.anontunnel import exitstrategies
from Tribler.community.anontunnel.Socks5.server import Socks5Server
from Tribler.community.anontunnel.community import ProxyCommunity, \
    ProxySettings
from Tribler.community.anontunnel.endpoint import DispersyBypassEndpoint
from Tribler.community.anontunnel.extendstrategies import TrustThyNeighbour, \
    NeighbourSubset
from Tribler.community.anontunnel.lengthstrategies import \
    RandomCircuitLengthStrategy, ConstantCircuitLength
from Tribler.community.anontunnel.selectionstrategies import \
    RandomSelectionStrategy, LengthSelectionStrategy
from Tribler.community.anontunnel.stats import StatsCrawler
from Tribler.community.privatesemantic.crypto.elgamalcrypto import \
    ElgamalCrypto
from Tribler.dispersy.dispersy import Dispersy
from Tribler.dispersy.util import call_on_reactor_thread

# custom
from Tribler.Core.Session import Session
from Tribler.Core.SessionConfig import SessionStartupConfig
from Tribler.Core.Utilities.twisted_thread import reactor, stop_reactor
from time import time, sleep


import time
import sys
import os
import shutil

import logging
logging.basicConfig(level=logging.DEBUG)
_logger = logging.getLogger(__name__)

# Tribler Session
from Tribler.Core.Session import Session
from Tribler.Core.SessionConfig import SessionStartupConfig

from Tribler.dispersy.util import call_on_reactor_thread


class TriblerSession():

    _sconfig = None
    _session = None
    _dispersy = None
    _searchkeywords = None

    _dispersy_init = False

    def __init__(self):
        if not 'ANDROID_HOST' in os.environ or not os.environ['ANDROID_HOST'] == "YES":
            pass

        # We are on android, setup the swift binary!
        sdcard_path = os.path.abspath(os.path.join(os.getcwd(), '..'))
        swift_path_source = os.path.join(sdcard_path, 'swift')
        swift_path_dest = os.path.join(os.environ['ANDROID_PRIVATE'], 'swift')

        if not os.path.exists(swift_path_dest):
            if not os.path.exists(swift_path_source):
                _logger.error("Looked at %s and %s, but couldn't find a libswift binary!" % (swift_path_source, swift_path_dest))
                exit()

            _logger.warn("Copy swift binary (%s -> %s)" % (swift_path_source, swift_path_dest))
            shutil.copy2(swift_path_source, swift_path_dest)
            os.chmod(swift_path_dest, 0777)

    def get_session(self):
        return self._session

    def start_service(self):
        _logger.info("Set tribler_state_dir to %s" % os.environ['TRIBLER_STATE_DIR'])

        # Load configuration file (if exists)
        cfgfilename = Session.get_default_config_filename(os.environ['TRIBLER_STATE_DIR'])
        try:
            self._sconfig = SessionStartupConfig.load(cfgfilename)
            _logger.info("Loaded previous configuration file from %s" % cfgfilename)
        except:
            self._sconfig = SessionStartupConfig()
            self._sconfig.set_state_dir(os.environ['TRIBLER_STATE_DIR'])
            _logger.info("No previous configuration file found, creating one in %s" % os.environ['TRIBLER_STATE_DIR'])

        # Disable unneeded dependencies
        #self._sconfig.set_state_dir(os.environ['TRIBLER_STATE_DIR'])
        #self._sconfig.set_torrent_checking(False)
        self._sconfig.set_multicast_local_peer_discovery(False)
        #self._sconfig.set_megacache(False)
        self._sconfig.set_swift_proc(False)
        self._sconfig.set_mainline_dht(False)
        self._sconfig.set_torrent_collecting(False)
        #self._sconfig.set_libtorrent(False)
        self._sconfig.set_dht_torrent_collecting(False)
        #self._sconfig.set_videoplayer(False)

        self._sconfig.set_dispersy_tunnel_over_swift(False)
        self._sconfig.set_torrent_collecting_max_torrents(5000)

        # Start session
        _logger.info("Starting tribler session")
        self._session = Session(self._sconfig)
        self._session.start()

        #swift_process = self._session.get_swift_proc() and self._session.get_swift_process()

        self._dispersy = self._session.lm.dispersy

        _logger.info('libTribler session started!')

        _logger.info("Set autoload_communities callback")
        self.define_communities()

        _logger.error("@@@ Dispersy communitites initialized!")

    # Dispersy init communitites callback function
    @call_on_reactor_thread
    def define_communities(self):
        _logger.error("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@")

        integrate_with_tribler = True
        comm_args = {'integrate_with_tribler': integrate_with_tribler}

        from Tribler.community.search.community import SearchCommunity
        from Tribler.community.allchannel.community import AllChannelCommunity
        from Tribler.community.channel.community import ChannelCommunity
        from Tribler.community.channel.preview import PreviewChannelCommunity
        from Tribler.community.metadata.community import MetadataCommunity

        _logger.info("@@@@@@@@@@ tribler: Preparing communities...")
        #now = timef()

        # must be called on the Dispersy thread
        comm = self._dispersy.define_auto_load(SearchCommunity, self._session.dispersy_member, load=True, kargs=comm_args)
        _logger.info("@@@@@@@@@@ Loaded dispersy communities: %s" % comm)
        comm = self._dispersy.define_auto_load(AllChannelCommunity, self._session.dispersy_member, load=True, kargs=comm_args)
        _logger.info("@@@@@@@@@@ Loaded dispersy communities: %s" % comm)

        # load metadata community
        #comm = dispersy.define_auto_load(MetadataCommunity, self.session.dispersy_member, load=True, kargs=comm_args)
        #_logger.info("@@@@@@@@@@ Loaded dispersy communities: %s" % comm)

        # 17/07/13 Boudewijn: the missing-member message send by the BarterCommunity on the swift port is crashing
        # 6.1 clients.  We will disable the BarterCommunity for version 6.2, giving people some time to upgrade
        # their version before enabling it again.
        # if swift_process:
        #     dispersy.define_auto_load(BarterCommunity,
        #                               s.dispersy_member,
        #                               (swift_process,),
        #                               load=True)

        comm = self._dispersy.define_auto_load(ChannelCommunity, self._session.dispersy_member, load=True, kargs=comm_args)
        _logger.info("@@@@@@@@@@ Loaded dispersy communities: %s" % comm)
        comm = self._dispersy.define_auto_load(PreviewChannelCommunity, self._session.dispersy_member, kargs=comm_args)
        _logger.info("@@@@@@@@@@ Loaded dispersy communities: %s" % comm)

        #diff = timef() - now
        _logger.info("@@@@@@@@@@ tribler: communities are ready in %.2f seconds", 0) #diff)

        self._dispersy_init = True

    def stop_service(self):
        #self._thread.stop()
        _logger.info("Bye bye")
        pass