#!/bin/bash
# This script first builds kivy, then the whole app (after kivy is set)

# Variables
CURRENTFOLDERPATH="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
APPLOGO="${CURRENTFOLDERPATH}/images/tribler_applogo.png"
APPSPLASH="${CURRENTFOLDERPATH}/images/splash.jpg"
DIRNAME="TSAP"
PY4APATH=

# Chat colors
red="\x1B[0;31m"
yellow="\x1B[1;33m"
green="\x1B[0;32m"
NC="\x1B[0m" # No Color

while getopts ":p:" opt; do
	case $opt in
		p)
			PY4APATH=$OPTARG
			;;
	esac
done

if [ "X$PY4APATH" == "X" ]; then
	echo -e "${yellow}༼ ▀̿̿Ĺ̯̿̿▀̿ ̿ ༽_•︻̷̿┻̿═━一༼ຈل͜ຈ༽ give the path of python-for-android using the -p flag or the donger dies${NC}"
	exit 1
fi

#echo -e "${green}COPYING main.py TO TRIBLER DIRECTORY${NC}"
#cp "${CURRENTFOLDERPATH}/tsap/main.py" "${CURRENTFOLDERPATH}/tribler/main.py"

# If the folders do not exist, we try to create them and throw an error.
if [ ! -e "${CURRENTFOLDERPATH}/images/" ]; then
	# Throw an error since folders are missing
	echo -e "${red}You need to have a folder ./images/ aborting.${NC}"
	echo -e "${red}The missing folders will be made now, but images will be missing!${NC}"
	mkdir -p ".//images/"
	exit 1
fi

# Check if the app icon isn't missing
if [ ! -f $APPLOGO ]; then
	echo -e "${red}${APPLOGO} is missing! Aborting.${NC}"
	exit 1
fi

# Check if the splashscreen isn't missing
if [ ! -f $APPSPLASH ]; then
	echo -e "${red}${APPSPLASH} is missing! Aborting.${NC}"
	exit 1
fi

# If the app folder in AT3 does not exist, create it.
if [ ! -e "${CURRENTFOLDERPATH}/app" ]; then
	echo -e "${red}${CURRENTFOLDERPATH}/app does not exist! Attempting to create it${NC}"
	mkdir -p "${CURRENTFOLDERPATH}/app"
else
	echo -e "${yellow}Press any key to remove {$CURRENTFOLDERPATH}/app or Control + C to abort.${NC}"
	read
	rm -rv "${CURRENTFOLDERPATH}/app/*"
fi

if [ ! -f "${CURRENTFOLDERPATH}/tribler/main.py" ]; then
	echo -e "${red}${CURRENTFOLDERPATH}/tribler/main.py is missing{$NC}" {
	exit
fi

# Check if destination exist
if [ -e "${PY4APATH}/dist/${DIRNAME}" ]; then
	echo -e "${red}The distribution ${PY4APATH}/dist/${DIRNAME} already exist${NC}"
	echo -e "${red}Press a key to remove it, or Control + C to abort.${NC}"
	read
	rm -rf "${PY4APATH}/dist/${DIRNAME}"
fi

# Build kivy first
#pushd $PY4APATH
#./distribute.sh -m "kivy" -d $DIRNAME
#popd

# Remove the created directory 
rm -rf "${PY4APATH}/dist/${DIRNAME}"

# Build a distribute folder with all the packages now that kivy has been set
pushd $PY4APATH
#./distribute.sh -m "openssl pycrypto m2crypto sqlite3 pyasn1 dispersy netifaces Tribler" -d $DIRNAME
./distribute.sh -m "openssl pycrypto m2crypto sqlite3 pyasn1 netifaces Tribler" -d $DIRNAME
popd

cd "${PY4APATH}/dist/${DIRNAME}/"

#./build.py --package org.tsap.tribler.full --name "a-TSAP Tribler" --version 0.9 --dir "${CURRENTFOLDERPATH}/app" debug --permission INTERNET --icon $APPLOGO --presplash $APPSPLASH
./build.py --package org.tsap.tribler.full --name "a-TSAP Tribler" --version 0.9 --dir "${CURRENTFOLDERPATH}/tsap" debug --permission INTERNET --icon $APPLOGO --presplash $APPSPLASH

# Copy the .apk files to our own app folder
find "${PY4APATH}/dist/${DIRNAME}/bin" -type f -name '*.apk' -exec cp {} "${CURRENTFOLDERPATH}/app" \;

# Delete the distribute and build now that the app has been made in the AT3 folder
rm -rf "${PY4APATH}/dist/${DIRNAME}"

echo -e "${green}All done!${NC} Everything seems to be in order (̿▀̿ ̿Ĺ̯̿̿▀̿ ̿)̄ "
