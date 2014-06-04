#!/bin/bash

pushd tsap
find -name '*.pyc' -exec rm {} \;
find -name '*.pyo' -exec rm {} \;
popd

source /home/user/Documents/Projects/bep/setenv_x86.sh &&
./build.sh -p `pwd`/../../p4a-at3 &&
adb uninstall org.tsap.tribler.full &&
adb install app/a-TSAPTribler-0.9-debug.apk &&
./adb_killswift.sh &&
adb logcat -c &&
adb shell am start -n org.tsap.tribler.full/org.renpy.android.PythonActivity &&
echo Listening for logcat output.. &&
python pidcat/pidcat.py org.tsap.tribler.full -t
