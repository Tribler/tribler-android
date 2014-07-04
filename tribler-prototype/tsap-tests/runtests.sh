adb shell am start -n org.tribler.tsap/org.renpy.android.PythonActivity
rm -rf runtests
virtualenv --system-site-packages runtests
. runtests/bin/activate
pip install pytest
python TestAll.py
deactivate
rm -rf runtests
