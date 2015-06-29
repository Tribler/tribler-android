# Tribler Play

Tribler Play is an application that runs the [Tribler](https://github.com/tribler/tribler) core on Android using [Python for Android](https://github.com/kivy/python-for-android/), with a native Android Java GUI. You can search for torrents using the decentralized [Dispersy](http://github/tribler/dispersy) network, and stream media files over BitTorrent using the built-in [VLC for Android](http://www.videolan.org/vlc/download-android.html) player.

## How to install
In case you want a quick .apk to install you have to download the latest build from the Tribler CI server (private access). All over HTTP for easy intercept.  

## How to build
In order to build Tribler Play you will need to package the Python code including its libraries for Android. This can be done by first installing buildozer:  
```bash
pip install buildozer  
```
Buildozer automates the build process which can be configured in the buildozer.spec file (but the one in this repository should already be fine).  
You can then run the build script in the tribler-prototype directory, which will call buildozer for you:  
```bash
cd tribler-prototype  
./build.sh  
```
After you've ran the build script, the Python code and its dependencies such as Tribler will have been packaged into private.mp3 located in the assets folder of the Java part of the application (tsap/app/src/main/assets).  
From there you should be able to run the entire application using Android Studio, just as you would run any Android Studio application.  

## Solutions to common problems
You might need to use the following commands if you encounter any problems with buildozer:  
If it complains about missing swig, then install it using:  
```bash
sudo apt-get install swig  
```
In case you get a problem about ccache: invalid option -- 'l', then remove ccache:  
```bash
sudo apt-get remove ccache  
```
If you get the following error: ImportError: No module named 'pip._vendor.requests', then just run the build script once more.  
If you get an error saying 'virtual-env not installed', then make sure you're using Python 2(.7) and not Python 3.  

### Optional
Install Android NDK, SDK, P4A and Ant in custom directories and set your .bashrc (located in /home/youruser/.bashrc) variables to these dirs. Below is an example of what to add to your .bashrc file:  
```bash
export APP_ANDROID_ANT_PATH="/usr/bin/"
export APP_ANDROID_SDK_PATH="/home/youruser/Android/Sdk/"
export APP_ANDROID_NDK_PATH="/home/youruser/Android/android-ndk-r10d/"
export APP_ANDROID_P4A_DIR="/home/youruser/Android/python-for-android/"
```

### Buildozer hack to keep your build directory
After every successful buildozer build, buildozer will delete your build directory in python-for-android (P4A). This is not really a problem, except when you add or update Python dependencies, because that would require buildozer to rebuild from scratch again to include your changes. Since the build process takes quite a long time, if might be useful to prevent buildozer from throwing its build folder away after a successful build. Open the following file:
```bash
sudo vim /usr/local/lib/python2.7/dist-packages/buildozer-0.28dev-py2.7.egg/buildozer/targets/android.py
```
And then comment out the following line:
```bash
 #self.buildozer.rmdir(join(self.pa_dir, 'build'))
```

## Project structure
/tribler-prototype/ -> contains the Python code and tests for this code. It acts as a wrapper around Tribler code.  
/tsap/ -> contains the Android Studio format for an Android project in Java.  
/tsap-tests/ -> contains Java tests.  
/tsap-UItests/ -> contains Java tests for the user interface.  

Java and Python communicate through a local webserver. The technique used for this is called XML-RPC.  

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
