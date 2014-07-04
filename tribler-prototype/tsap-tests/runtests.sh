rm -rf runtests

virtualenv --system-site-packages runtests
. runtests/bin/activate
pip install pytest
pip install nose -I
deactivate

. runtests/bin/activate
echo Using nose from `which nosetests`
sleep 30
nosetests -v --with-xunit *_test.py
deactivate

rm -rf runtests
