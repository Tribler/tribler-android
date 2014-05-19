#!/bin/bash

rm -r tmprunmain
mkdir -p tmprunmain

pushd tmprunmain

virtualenv --system-site-packages venv-tsap
. venv-tsap/bin/activate
pip install ../Tribler-0.1.0.tar.gz
ln -s ../tribler/Tribler .
popd

cp tsap/* tmprunmain/
cp tribler/swift tmprunmain/

pushd tmprunmain

# A running swift fucks with dispersy, even when you disable swift(?)
pkill swift

python main.py
popd
