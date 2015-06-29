#!/bin/bash
# Use this script instead of 'buildozer android debug' when you need to build a package
# that is to be used by the Java side of TSAP.
# This script will do the following:
#		1. Prepare the sdl_main.c file so that the nativeSetEnv function can be used.
#		2. Run 'buildozer android debug'.
#		3. Move generated files (.so files and assets) to the Java TSAP project.
# 		4. Create an xml with the private and public version numbers for the assets.

# Check if the python-for-android directory environment variable has been set:
if [ -z "$APP_ANDROID_P4A_DIR" ]; then
	echo "You haven't defined the path to your python-for-android folder. Execute the following command from your terminal or add the command to your .bashrc file: export APP_ANDROID_P4A_DIR=\"/path/to/python-for-android/\""
	exit 1
fi

# Variables:
CURRENTFOLDERPATH="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
DIRNAME="tsap" # package.name in buildozer.spec 
PY4APATH="${APP_ANDROID_P4A_DIR}"

# Adapt sdl_main.c in python-for-android to export the correct JNI function (PythonService_nativeSetEnv):
mv "${PY4APATH}/src/jni/sdl_main/sdl_main.c" "${PY4APATH}/src/jni/sdl_main/sdl_main.c.bak"
sed s/SDLSurfaceView_nativeSetEnv/PythonService_nativeSetEnv/ "${PY4APATH}/src/jni/sdl_main/sdl_main.c.bak" > "${PY4APATH}/src/jni/sdl_main/sdl_main.c"
rm "${PY4APATH}/src/jni/sdl_main/sdl_main.c.bak"

# Remove libsdl.so from build which forces a rebuild of sdl which in turn forces libsdl_main.so to be rebuild with the above change:
rm "${PY4APATH}/build/libs/libsdl.so"

# Run buildozer to generate a distribution:
buildozer android debug

# Remove it again so we won't mess with builds from different projects:
rm "${PY4APATH}/build/libs/libsdl.so"

# Undo our earlier change:
mv "${PY4APATH}/src/jni/sdl_main/sdl_main.c" "${PY4APATH}/src/jni/sdl_main/sdl_main.c.bak"
sed s/PythonService_nativeSetEnv/SDLSurfaceView_nativeSetEnv/ "${PY4APATH}/src/jni/sdl_main/sdl_main.c.bak" > "${PY4APATH}/src/jni/sdl_main/sdl_main.c"
rm "${PY4APATH}/src/jni/sdl_main/sdl_main.c.bak"

echo "----------Buildozer is done. Taking over.----------"

# Copy the .so files to the libs folder in tsap:
echo "Moving generated native libraries."
find "${PY4APATH}/dist/${DIRNAME}/libs/armeabi" -type f -name '*.so' -exec cp {} "${CURRENTFOLDERPATH}/../tsap/app/src/main/jniLibs/armeabi-v7a" \;

# Copy the assets MP3s to the assets folder in tsap:
echo "Moving generated assets."
find "${PY4APATH}/dist/${DIRNAME}/assets" -type f -name '*.mp3' -exec cp {} "${CURRENTFOLDERPATH}/../tsap/app/src/main/assets" \;

# Change the version strings to the correct values:
echo "Setting assets' public and private versions."
private_version=$(grep -oP '(?<=<string name="private_version">)\d*.\d*(?=</string>)' "${PY4APATH}/dist/${DIRNAME}/res/values/strings.xml")
public_version=$(grep -oP '(?<=<string name="public_version">)\d*.\d*(?=</string>)' "${PY4APATH}/dist/${DIRNAME}/res/values/strings.xml")
echo "<?xml version=\"1.0\" encoding=\"utf-8\"?>
<resources>

    <string name=\"private_version\">$private_version</string>
    <string name=\"public_version\">$public_version</string>

</resources>" > "${CURRENTFOLDERPATH}/../tsap/app/src/main/res/values/asset_versions.xml"

echo -e "All done! You can now run the project in Android Studio (̿▀̿ ̿Ĺ̯̿̿▀̿ ̿)̄."
