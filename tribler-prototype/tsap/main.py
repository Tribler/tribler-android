__author__ = 'user'

import os
import sys
import logging
import shutil
import time

logging.basicConfig(level=logging.DEBUG)

print 'sys.platform: %s\nos.name: %s' % (sys.platform, os.name)

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
    os.environ['TRIBLER_STATE_DIR'] = os.path.join(os.getcwd(), '.Tribler-data')

    # Test Android code
    os.environ['ANDROID_HOST'] = "SIMULATED"
    os.environ['ANDROID_PRIVATE'] = os.getcwd()

#print os.environ

# Tribler Session
from Tribler.Core.Session import Session
from Tribler.Core.SessionConfig import SessionStartupConfig

# Tribler communities
#from Tribler.community.search.community import SearchCommunity
#from Tribler.community.allchannel.community import AllChannelCommunity
#from Tribler.community.channel.community import ChannelCommunity
#from Tribler.community.channel.preview import PreviewChannelCommunity
#from Tribler.community.metadata.community import MetadataCommunity


# Init logger
_logger = logging.getLogger(__name__)
_logger.error("THIS IS AN ERROR")
print 'os.getcwd(): %s' % os.getcwd()


class RunApp():

    dispersy = None
    sconfig = None
    session = None

    dispersy_init = False


    def __init__(self):
        config = SessionStartupConfig()

        _logger.info("Set tribler_sate_dir to %s" % os.environ['TRIBLER_STATE_DIR'])

        cfgfilename = Session.get_default_config_filename(os.environ['TRIBLER_STATE_DIR'])
        try:
            self.sconfig = SessionStartupConfig.load(cfgfilename)
            _logger.info("Loaded previous configuration file from %s" % cfgfilename)
        except:
            self.sconfig = SessionStartupConfig()
            self.sconfig.set_state_dir(os.environ['TRIBLER_STATE_DIR'])
            _logger.info("No previous configuration file found, creating one in %s" % os.environ['TRIBLER_STATE_DIR'])

        #self.sconfig.set_state_dir(os.environ['TRIBLER_STATE_DIR'])
        self.sconfig.set_torrent_checking(False)
        self.sconfig.set_multicast_local_peer_discovery(False)
        self.sconfig.set_megacache(False)
        #self.sconfig.set_dispersy(False)
        #self.sconfig.set_swift_proc(False)
        self.sconfig.set_mainline_dht(False)
        self.sconfig.set_torrent_collecting(False)
        self.sconfig.set_libtorrent(False)
        self.sconfig.set_dht_torrent_collecting(False)
        self.sconfig.set_videoplayer(False)

        self.sconfig.set_dispersy_tunnel_over_swift(False)
        self.sconfig.set_torrent_collecting_max_torrents(500)

        self.session = Session(self.sconfig)
        self.session.start()

        def define_communities():
            _logger.error("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@")

            from Tribler.community.search.community import SearchCommunity
            from Tribler.community.allchannel.community import AllChannelCommunity
            from Tribler.community.channel.community import ChannelCommunity
            from Tribler.community.channel.preview import PreviewChannelCommunity
            from Tribler.community.metadata.community import MetadataCommunity

            _logger.info("@@@@@@@@@@ tribler: Preparing communities...")
            #now = time()

            # must be called on the Dispersy thread
            comm = dispersy.define_auto_load(SearchCommunity, self.session.dispersy_member, load=True, kargs={'integrate_with_tribler': False})
            _logger.info("@@@@@@@@@@ Loaded dispersy communities: %s" % comm)
            comm = dispersy.define_auto_load(AllChannelCommunity, self.session.dispersy_member, load=True, kargs={'integrate_with_tribler': False})
            _logger.info("@@@@@@@@@@ Loaded dispersy communities: %s" % comm)

            # load metadata community
            comm = dispersy.define_auto_load(MetadataCommunity, self.session.dispersy_member, load=True, kargs={'integrate_with_tribler': False})
            _logger.info("@@@@@@@@@@ Loaded dispersy communities: %s" % comm)

            # 17/07/13 Boudewijn: the missing-member message send by the BarterCommunity on the swift port is crashing
            # 6.1 clients.  We will disable the BarterCommunity for version 6.2, giving people some time to upgrade
            # their version before enabling it again.
            # if swift_process:
            #     dispersy.define_auto_load(BarterCommunity,
            #                               s.dispersy_member,
            #                               (swift_process,),
            #                               load=True)

            comm = dispersy.define_auto_load(ChannelCommunity, self.session.dispersy_member, load=True, kargs={'integrate_with_tribler': False})
            _logger.info("@@@@@@@@@@ Loaded dispersy communities: %s" % comm)
            comm = dispersy.define_auto_load(PreviewChannelCommunity, self.session.dispersy_member, kargs={'integrate_with_tribler': False})
            _logger.info("@@@@@@@@@@ Loaded dispersy communities: %s" % comm)

            diff = time() - now
            _logger.info("@@@@@@@@@@ tribler: communities are ready in %.2f seconds", diff)

            self.dispersy_init = True

        swift_process = self.session.get_swift_proc() and self.session.get_swift_process()
        dispersy = self.session.get_dispersy_instance()
        dispersy.callback.call(define_communities)

        print 'libTribler session started!'

        while not self.dispersy_init:
            _logger.info("@@@ Waiting for dispersy communities to initialize..")
            time.sleep(.5)

        _logger.info("@@@ Dispersy communitites initialized!")

        #SearchCommunity.create_search("")



        #self.dispersy = self.session.lm.dispersy


if __name__ == '__main__':
    # Prepare swift binary on Android
    if 'ANDROID_HOST' in os.environ and os.environ['ANDROID_HOST'] == "YES":
        swift_path_source = os.path.join(os.getcwd(), 'swift')
        swift_path_dest = os.path.join(os.environ['ANDROID_PRIVATE'], 'swift')

        if not os.path.exists(swift_path_dest):
            if not os.path.exists(swift_path_source):
                _logger.error("Looked at %s and %s, but couldn't find a libswift binary!" % (swift_path_source, swift_path_dest))
                exit()

            _logger.warn("Copy swift binary (%s -> %s)" % (swift_path_source, swift_path_dest))
            shutil.copy2(swift_path_source, swift_path_dest)
            os.chmod(swift_path_dest, 0777)

    app = RunApp()
