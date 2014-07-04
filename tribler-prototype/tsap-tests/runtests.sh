rm -rf runtests
virtualenv --system-site-packages runtests
. runtests/bin/activate
pip install pytest
sleep 30
nosetests -v --with-xunit *_test.py
deactivate
rm -rf runtests
