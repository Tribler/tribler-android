rm -rf runtests
virtualenv --system-site-packages runtests
. runtests/bin/activate
pip install pytest
#nosetests -v -m='*_test.py'
sleep 30
python TestAll.py
deactivate
rm -rf runtests
