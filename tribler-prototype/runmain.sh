#!/bin/bash

pkill -f "python main.py"

rm -r tmprunmain
mkdir -p tmprunmain

if [ ! -f Twisted-14.0.0.tar.bz2 ]
then
	wget "https://pypi.python.org/packages/source/T/Twisted/Twisted-14.0.0.tar.bz2"
fi

if [ ! -f zope.interface-4.1.1.tar.gz ]
then
	wget "https://pypi.python.org/packages/source/z/zope.interface/zope.interface-4.1.1.tar.gz#md5=edcd5f719c5eb2e18894c4d06e29b6c6"
fi

pushd tmprunmain

virtualenv --system-site-packages venv-tsap
. venv-tsap/bin/activate
pip install ../Tribler-0.1.0.tar.gz
pip install ../zope.interface-4.1.1.tar.gz
pip install ../Twisted-14.0.0.tar.bz2
pip install Twisted --upgrade
popd

cp -rv tsap/* tmprunmain/
cp -v tribler/swift tmprunmain/

pushd tmprunmain
#rm -r service/Tribler
#ln -s ../../tribler/Tribler service/Tribler

# A running swift fucks with dispersy, even when you disable swift(?)
pkill swift

python main.py
popd
