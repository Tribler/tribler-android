__author__ = 'user'

import os
import sys
import logging
import shutil

logging.basicConfig(level=logging.DEBUG)

print 'sys.platform: %s\nos.name: %s' % (sys.platform, os.name)

if 'ANDROID_APP_PATH' in os.environ:
    print "We are running on android/p4a"

    # Set P4A egg cache
    os.environ["PYTHON_EGG_CACHE"] = "/data/data/org.tsap.tribler.full/cache"

    # Set tribler data dir
    os.environ['TRIBLER_STATE_DIR'] = os.path.join(os.environ['ANDROID_PRIVATE'], '.Tribler')
else:
    print "We are running on a pc"

    os.environ['TRIBLER_STATE_DIR'] = os.path.join(os.getcwd(), '.Tribler-data')

#print os.environ

#import kivy
#kivy.require('1.0.9')
from datetime import time
from Tribler.Core.Session import Session
from Tribler.Core.SessionConfig import SessionStartupConfig


_logger = logging.getLogger(__name__)
_logger.error("THIS IS AN ERROR")
print 'os.getcwd(): %s' % os.getcwd()

class RunApp():

    dispersy = None
    sconfig = None
    session = None


    def __init__(self):
        config = SessionStartupConfig()

        _logger.info("Set tribler_sate_dir to %s" % os.environ['TRIBLER_STATE_DIR'])

        cfgfilename = Session.get_default_config_filename(os.environ['TRIBLER_STATE_DIR'])
        try:
            self.sconfig = SessionStartupConfig.load(cfgfilename)
        except:
            self.sconfig = SessionStartupConfig()
            self.sconfig.set_state_dir(os.environ['TRIBLER_STATE_DIR'])

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
            now = time()

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

        swift_process = self.session.get_swift_proc() and self.session.get_swift_process()
        dispersy = self.session.get_dispersy_instance()
        #dispersy.callback.call(define_communities)

        print 'libTribler session started!'

        self.dispersy = self.session.lm.dispersy


if __name__ == '__main__':
    # Prepare swift binary on Android
    if 'ANDROID_APP_PATH' in os.environ:
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
