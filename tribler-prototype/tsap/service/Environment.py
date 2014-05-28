__author__ = 'user'

import os

def init_environment():
    if 'ANDROID_PRIVATE' in os.environ:
        print "We are running on android/p4a"

        split_path = os.path.split(os.environ['ANDROID_PRIVATE'])
        print split_path
        print os.environ['ANDROID_PRIVATE']
        if split_path[1].lower() == 'service':
            os.environ['ANDROID_PRIVATE'] = split_path[0]
        #    os.chdir(split_path[0])
        print os.environ['ANDROID_PRIVATE']

        # Set P4A egg cache
        os.environ["PYTHON_EGG_CACHE"] = "/data/data/org.tsap.tribler.full/cache"

        # Set tribler data dir
        os.environ['TRIBLER_STATE_DIR'] = os.path.join(os.environ['ANDROID_PRIVATE'], '.Tribler')

        # Running on Android
        os.environ['ANDROID_HOST'] = "ANDROID-99"  # TODO: SET SDK VERSION INSTEAD
    else:
        print "We are running on a pc"

        # Set tribler data dir
        os.environ['TRIBLER_STATE_DIR'] = os.path.abspath(os.path.join(os.getcwd(), '../.Tribler-data'))
        print os.environ['TRIBLER_STATE_DIR']

        # Test Android code
        os.environ['ANDROID_HOST'] = "UBUNTU-99"
        os.environ['ANDROID_PRIVATE'] = os.getcwd()