#!/bin/bash

echo "Clearing python for android folders.."
pushd python-for-android
rm -r build/ dist -f
popd

echo "Clearing assets (*.mp3).."
pushd ../tsap/assets
rm *.mp3
popd
