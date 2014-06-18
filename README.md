# Tribler Play [![Build Status](http://jenkins.tribler.org/job/Build_Test-TSAP_Android_master/badge/icon)](http://jenkins.tribler.org/job/Build_Test-TSAP_Android_master/)

Tribler Play is an application that runs the [Tribler](https://github.com/tribler/tribler) core on Android using [Python for Android](https://github.com/kivy/python-for-android/), with a native Android Java GUI. You can search for torrents using the decentralized [Dispersy](http://github/tribler/dispersy) network, and stream media files over BitTorrent using the buildin [VLC for Android](http://www.videolan.org/vlc/download-android.html) player.

## Dependencies

This project has the following dependencies:
* [Tribler](https://github.com/tribler/tribler/) (LGPL v2.1)
* [libswift](https://github.com/libswift/libswift) (LGPL v2.1)
* [dispersy](https://github.com/Tribler/dispersy) (LGPL v2.1)
* [Python for Android](https://github.com/kivy/python-for-android) (MIT/LGPL v2.1)
* [Picasso](https://github.com/square/picasso) (Apache v2)
* [VLC for Android](http://www.videolan.org/vlc/download-android.html) (GPL v2)

For testing purposes:
* [Android JUnit Report Test Runner](https://github.com/jsankey/android-junit-report) (Apache v2)
* [Automator-log-converter](https://github.com/dpreussler/automator-log-converter) (Apache v2)

## Screenshots

Below, some screenshots of the current GUI are shown.

![home_screen_landscape](screenshots/home_screen_portrait_readme.png)

*Home screen showing list of media files.*

![channels_screen_landscape](screenshots/channels_screen_portrait_readme.png)

*List of channels containing torrents.*

![info_screen_portrait](screenshots/info_screen_portrait_readme.png)

*Info about a torrent with the option to play it using the built-in VLC player.*
