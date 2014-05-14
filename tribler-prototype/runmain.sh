#!/bin/bash

mkdir -p runmain

pushd runmain
rm -r venv-tsap Tribler swift

virtualenv --system-site-packages venv-tsap
. venv-tsap/bin/activate
pip install ../Tribler-0.1.0.tar.gz
#ln -s ../tribler/Tribler .
popd

cp tsap/* runmain/
cp tribler/swift runmain/

pushd runmain

# A running swift fucks with dispersy, even when you disable swift(?)
pkill swift

python main.py
popd
