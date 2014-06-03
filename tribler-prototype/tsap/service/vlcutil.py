__author__ = 'user'

from jnius import autoclass

def launchVLC(url):
    print "Launching VLC with URL %s" % url
    Launcher = autoclass('org.renpy.android.PythonService')
    Launcher.launchVLC(url)