from distutils.core import setup

setup(
    name='Tribler',
    version='0.1.0',
    author='J. Random Hacker',
    author_email='jrh@example.com',
    packages=[u'Tribler', u'Tribler.community', u'Tribler.community.metadata', u'Tribler.community.privatesocial', u'Tribler.community.privatesemantic', u'Tribler.community.privatesemantic.crypto', u'Tribler.community.anontunnel', u'Tribler.community.anontunnel.tests', u'Tribler.community.anontunnel.Socks5', u'Tribler.community.template', u'Tribler.community.privatesearch', u'Tribler.community.privatesearch.oneswarm', u'Tribler.community.privatesearch.oneswarm.tests', u'Tribler.community.channel', u'Tribler.community.bartercast3', u'Tribler.community.search', u'Tribler.community.demers', u'Tribler.community.allchannel', u'Tribler.Category', u'Tribler.Main', u'Tribler.Main.Utility', u'Tribler.Main.Utility.Feeds', u'Tribler.Main.Dialogs', u'Tribler.Main.vwxGUI', u'Tribler.Main.webUI', u'Tribler.Policies', u'Tribler.Test', u'Tribler.Test.API', u'Tribler.dispersy', u'Tribler.dispersy.discovery', u'Tribler.dispersy.tests', u'Tribler.dispersy.tests.debugcommunity', u'Tribler.dispersy.tool', u'Tribler.Utilities', u'Tribler.TrackerChecking', u'Tribler.Core', u'Tribler.Core.RawServer', u'Tribler.Core.Merkle', u'Tribler.Core.Statistics', u'Tribler.Core.Statistics.Status', u'Tribler.Core.Search', u'Tribler.Core.Utilities', u'Tribler.Core.Libtorrent', u'Tribler.Core.APIImplementation', u'Tribler.Core.DecentralizedTracking', u'Tribler.Core.DecentralizedTracking.pymdht', u'Tribler.Core.DecentralizedTracking.pymdht.ut2mdht', u'Tribler.Core.DecentralizedTracking.pymdht.ui', u'Tribler.Core.DecentralizedTracking.pymdht.profiler', u'Tribler.Core.DecentralizedTracking.pymdht.profiler.parsers', u'Tribler.Core.DecentralizedTracking.pymdht.core', u'Tribler.Core.DecentralizedTracking.pymdht.plugins', u'Tribler.Core.CacheDB', u'Tribler.Core.Tag', u'Tribler.Core.Swift', u'Tribler.Core.Video', u'Tribler.Debug'],
    url='http://pypi.python.org/pypi/TowelStuff/',
    license='LICENSE.txt',
    package_data={'': ['*.ec', '*.txt', '*.conf', '*.filter', '*.sql']},
    description='Useful towel-related stuff.',
    long_description='TRIBLERTRIBLERTRIBLERTRIBLERTRIBLER!',
)




