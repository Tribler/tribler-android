from distutils.core import setup

setup(
    name='Tribler',
    version='0.1.0',
    author='Tribler',
    author_email='jrh@example.com',
    packages=['Tribler', 'Tribler.community', 'Tribler.community.metadata', 'Tribler.community.privatesocial', 'Tribler.community.privatesemantic', 'Tribler.community.privatesemantic.crypto', 'Tribler.community.anontunnel', 'Tribler.community.anontunnel.tests', 'Tribler.community.anontunnel.Socks5', 'Tribler.community.template', 'Tribler.community.privatesearch', 'Tribler.community.privatesearch.oneswarm', 'Tribler.community.privatesearch.oneswarm.tests', 'Tribler.community.channel', 'Tribler.community.bartercast3', 'Tribler.community.search', 'Tribler.community.demers', 'Tribler.community.allchannel', 'Tribler.Category', 'Tribler.Main', 'Tribler.Main.Utility', 'Tribler.Main.Utility.Feeds', 'Tribler.Main.Dialogs', 'Tribler.Main.vwxGUI', 'Tribler.Main.webUI', 'Tribler.Policies', 'Tribler.Test', 'Tribler.Test.API', 'Tribler.dispersy', 'Tribler.dispersy.discovery', 'Tribler.dispersy.tests', 'Tribler.dispersy.tests.debugcommunity', 'Tribler.dispersy.tool', 'Tribler.Utilities', 'Tribler.TrackerChecking', 'Tribler.Core', 'Tribler.Core.RawServer', 'Tribler.Core.Merkle', 'Tribler.Core.Statistics', 'Tribler.Core.Statistics.Status', 'Tribler.Core.Search', 'Tribler.Core.Utilities', 'Tribler.Core.Libtorrent', 'Tribler.Core.APIImplementation', 'Tribler.Core.DecentralizedTracking', 'Tribler.Core.DecentralizedTracking.pymdht', 'Tribler.Core.DecentralizedTracking.pymdht.ut2mdht', 'Tribler.Core.DecentralizedTracking.pymdht.ui', 'Tribler.Core.DecentralizedTracking.pymdht.profiler', 'Tribler.Core.DecentralizedTracking.pymdht.profiler.parsers', 'Tribler.Core.DecentralizedTracking.pymdht.core', 'Tribler.Core.DecentralizedTracking.pymdht.plugins', 'Tribler.Core.CacheDB', 'Tribler.Core.Tag', 'Tribler.Core.Swift', 'Tribler.Core.Video', 'Tribler.Debug'],
    url='https://github.com/tribler/tribler/',
    license='LICENSE.txt',
    package_data={'': ['*.ec', '*.txt', '*.conf', '*.filter', '*.sql']},
    description='Useful towel-related stuff.',
    long_description='TRIBLERTRIBLERTRIBLERTRIBLERTRIBLER!',
)




