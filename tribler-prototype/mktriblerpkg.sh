#!/bin/bash
echo "Remove old Tribler code.."
rm -r Tribler-0.1.0/Tribler

echo "Copy new Tribler code.."
cp -r tribler/Tribler Tribler-0.1.0/

echo "Remove pyc, pyo and file.ext~ files.."
pushd Tribler-0.1.0
find -name '*.pyc' -exec rm {} \;
find -name '*.pyo' -exec rm {} \;
find -name '*~' -exec rm {} \;
popd

echo "Make tarball.."
tar cvzf Tribler-0.1.0.tar.gz Tribler-0.1.0

echo "md5sum:"
MD5SUM=`md5sum Tribler-0.1.0.tar.gz`
echo $MD5SUM > Tribler-0.1.0.tar.gz.md5
cat Tribler-0.1.0.tar.gz.md5

echo "Copy to server.."
scp Tribler-0.1.0.tar.gz Tribler-0.1.0.tar.gz.md5 fr:/var/www/default/tsap/

if [ $? -ne 0 ]; then
    echo "Failed to upload to server, aborting"
    exit
fi

if [ -f "./python-for-android/recipes/Tribler/recipe.sh" ]
then
	echo "Replacing MD5SUM in python-for-android/recipes/Tribler/recipe.sh"
	pushd "./python-for-android/recipes/Tribler" > /dev/null
	MD5SUM=`echo -e $MD5SUM | cut -d' ' -f1`
	mv recipe.sh recipe.sh.orig
	sed -e "s/^MD5_Tribler=.*/MD5_Tribler=$MD5SUM/" recipe.sh.orig > recipe.sh
	popd > /dev/null
else
	echo "No tribler recipe found or P4A_PATH not set."
fi
