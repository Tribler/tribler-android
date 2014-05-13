__author__ = 'user'

import os
os.environ["PYTHON_EGG_CACHE"] = "/data/data/org.tsap.tribler.full/cache"


import kivy
kivy.require('1.0.9')
import logging
from datetime import time
from Tribler.Core.Session import Session
from Tribler.Core.SessionConfig import SessionStartupConfig


_logger = logging.getLogger(__name__)
print '@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@'
print ' os.getcwd(): %s' % os.getcwd()
print '@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@'

def define_communities():
    from Tribler.community.search.community import SearchCommunity
    from Tribler.community.allchannel.community import AllChannelCommunity
    from Tribler.community.channel.community import ChannelCommunity
    from Tribler.community.channel.preview import PreviewChannelCommunity
    from Tribler.community.metadata.community import MetadataCommunity

    _logger.info("tribler: Preparing communities...")
    now = time()

    # must be called on the Dispersy thread
    dispersy.define_auto_load(SearchCommunity, s.dispersy_member, load=True)
    dispersy.define_auto_load(AllChannelCommunity, s.dispersy_member, load=True)

    # load metadata community
    dispersy.define_auto_load(MetadataCommunity, s.dispersy_member, load=True)

    # 17/07/13 Boudewijn: the missing-member message send by the BarterCommunity on the swift port is crashing
    # 6.1 clients.  We will disable the BarterCommunity for version 6.2, giving people some time to upgrade
    # their version before enabling it again.
    # if swift_process:
    #     dispersy.define_auto_load(BarterCommunity,
    #                               s.dispersy_member,
    #                               (swift_process,),
    #                               load=True)

    dispersy.define_auto_load(ChannelCommunity, s.dispersy_member, load=True)
    dispersy.define_auto_load(PreviewChannelCommunity, s.dispersy_member)

    diff = time() - now
    _logger.info("tribler: communities are ready in %.2f seconds", diff)

if __name__ == '__main__':

    config = SessionStartupConfig()
    config.set_state_dir("/data/data/org.tsap.tribler.full/.Tribler")
    config.set_torrent_checking(False)
    config.set_multicast_local_peer_discovery(False)
    config.set_megacache(False)
    #config.set_dispersy(False)
    #config.set_swift_proc(False)
    config.set_mainline_dht(False)
    config.set_torrent_collecting(False)
    config.set_libtorrent(False)
    config.set_dht_torrent_collecting(False)
    config.set_videoplayer(False)

    s = Session(config)
    s.start()

    #dispersy = s.get_dispersy_instance()
    #dispersy.callback.call(define_communities)
    #s._logger.setLevel(logging.DEBUG)

    print '@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@'
    print ' libTribler session started!'
    print '@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@'
