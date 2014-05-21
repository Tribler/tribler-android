from distutils.core import setup

setup(
    name='Tribler',
    version='0.1.0',
    author='J. Random Hacker',
    author_email='jrh@example.com',
    packages=['Tribler', 'Tribler.Category', 'Tribler.community', 'Tribler.community.allchannel', 'Tribler.community.bartercast3', 'Tribler.community.channel', 'Tribler.community.demers', 'Tribler.community.metadata', 'Tribler.community.privatesearch', 'Tribler.community.privatesearch.oneswarm', 'Tribler.community.privatesearch.oneswarm.tests', 'Tribler.community.privatesemantic', 'Tribler.community.privatesemantic.crypto', 'Tribler.community.privatesocial', 'Tribler.community.search', 'Tribler.community.template', 'Tribler.Core', 'Tribler.Core.APIImplementation', 'Tribler.Core.CacheDB', 'Tribler.Core.DecentralizedTracking', 'Tribler.Core.DecentralizedTracking.pymdht', 'Tribler.Core.DecentralizedTracking.pymdht.core', 'Tribler.Core.DecentralizedTracking.pymdht.plugins', 'Tribler.Core.DecentralizedTracking.pymdht.profiler', 'Tribler.Core.DecentralizedTracking.pymdht.profiler.parsers', 'Tribler.Core.DecentralizedTracking.pymdht.ui', 'Tribler.Core.Libtorrent', 'Tribler.Core.Merkle', 'Tribler.Core.RawServer', 'Tribler.Core.Search', 'Tribler.Core.Statistics', 'Tribler.Core.Statistics.Status', 'Tribler.Core.Swift', 'Tribler.Core.Tag', 'Tribler.Core.Utilities', 'Tribler.Core.Video', 'Tribler.Debug', 'Tribler.dispersy', 'Tribler.dispersy.tests', 'Tribler.dispersy.tests.debugcommunity', 'Tribler.Main', 'Tribler.Policies', 'Tribler.Test', 'Tribler.Test.API', 'Tribler.TrackerChecking', 'Tribler.Utilities', 'Tribler.community.anontunnel', 'Tribler.community.anontunnel.Socks5', 'Tribler.community.anontunnel.tests'],
    url='http://pypi.python.org/pypi/TowelStuff/',
    license='LICENSE.txt',
    package_data={'': ['*.ec', '*.txt', '*.conf', '*.filter', '*.sql']},
    description='Useful towel-related stuff.',
    long_description='TRIBLERTRIBLERTRIBLERTRIBLERTRIBLER!',
)

