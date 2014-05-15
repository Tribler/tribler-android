#!/bin/bash
echo "Remove old Tribler code.."
rm -r Tribler-0.1.0/Tribler

echo "Copy new Tribler code.."
cp -r tribler/Tribler Tribler-0.1.0/

echo "Remove pyc and pyo files.."
pushd Tribler-0.1.0/Tribler
find -name '*.pyc' -exec rm {} \;
find -name '*.pyo' -exec rm {} \;
popd

echo "Make tarball.."
tar cvzf Tribler-0.1.0.tar.gz Tribler-0.1.0

echo "md5sum:"
md5sum Tribler-0.1.0.tar.gz > Tribler-0.1.0.tar.gz.md5
cat Tribler-0.1.0.tar.gz.md5

echo "Copy to server.."
scp Tribler-0.1.0.tar.gz Tribler-0.1.0.tar.gz.md5 fr:/var/www/default/tsap/
