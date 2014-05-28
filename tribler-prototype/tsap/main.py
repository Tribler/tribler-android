__author__ = 'user'

import os
import sys
import logging
import time

# SETUP ENVIRONMENT, DO THIS FIRST
from service.Environment import init_environment
init_environment()

# Init logger
logging.basicConfig(level=logging.INFO)
_logger = logging.getLogger(__name__)

_logger.error("THIS IS AN ERROR")
print 'os.getcwd(): %s' % os.getcwd()

print 'sys.platform: %s\nos.name: %s' % (sys.platform, os.name)

if __name__ == '__main__':
    if os.environ['ANDROID_HOST'].startswith("ANDROID"):
        # Start android service
        from android import AndroidService
        service = AndroidService("TSAP Tribler Session", "A tribler session is running..")

        _logger.info("Starting service..")
        service.start()

        #_logger.info("Stopping service..")
        #service.stop()
    else:
        # Just run services/main.py
        import subprocess

        os.chdir(os.path.join(os.getcwd(), 'service'))
        subprocess.call(["python", "main.py"])#, shell=True)

        #from service import main

