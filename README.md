# Tribler Play

Tribler Play is an application that runs the [Tribler](https://github.com/tribler/tribler) core on Android using [Python for Android](https://github.com/kivy/python-for-android/), with a native Android Java GUI. You can search for torrents using the decentralized [Dispersy](http://github/tribler/dispersy) network, and stream media files over BitTorrent using the built-in [VLC for Android](http://www.videolan.org/vlc/download-android.html) player.

## TODO: How to install
Download the latest build from the CI server. All over HTTP for easy intercept.

## TODO: How to build
TODO
pip install buildozer
install SWIG
sudo apt-get remove ccache <- needed because P4A will use it if available but it gives a problem when compiling plyvel (ccache: invalid option -- 'l')
PYTHON-FOR-ANDROID
virtual-env not installed (make sure it works with Python 2(.7) and not Python 3)

### Optional
install Android NDK, SDK, P4A, Ant in custom directories
set .bashrc variables to these dirs

### Buildozer hack to keep your build directory
NOTE: They can disable the build delete files by commenting one line out:
sudo vim /usr/local/lib/python2.7/dist-packages/buildozer-0.28dev-py2.7.egg/buildozer/targets/android.py
 #self.buildozer.rmdir(join(self.pa_dir, 'build'))

## TODO: Project structure

## Dependencies

This project has the following dependencies:
* [Tribler](https://github.com/tribler/tribler/) (LGPL v2.1)
* [dispersy](https://github.com/Tribler/dispersy) (LGPL v2.1)
* [Python for Android](https://github.com/kivy/python-for-android) (MIT/LGPL v2.1)
* [Picasso](https://github.com/square/picasso) (Apache v2)
* [VLC for Android](http://www.videolan.org/vlc/download-android.html) (GPL v2)

For testing purposes:
* [Android JUnit Report Test Runner](https://github.com/jsankey/android-junit-report) (Apache v2)
* [Automator-log-converter](https://github.com/dpreussler/automator-log-converter) (Apache v2)
