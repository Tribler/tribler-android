#!/bin/bash

pushd tsap
find -name '*.pyc' -exec rm {} \;
find -name '*.pyo' -exec rm {} \;
popd

source /home/user/Documents/Projects/bep/setenv_x86.sh &&
./build.sh -p /home/user/Documents/Projects/bep/p4a-at3 &&
adb uninstall org.tsap.tribler.full &&
adb install app/a-TSAPTribler-0.9-debug.apk &&
python pidcat/pidcat.py org.tsap.tribler.full
