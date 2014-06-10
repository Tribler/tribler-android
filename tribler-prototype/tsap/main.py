# coding: utf-8

# Written by Wendo Sabée
# This file does little more than running ./service/main.py

import sys
import logging
import os

# SETUP ENVIRONMENT, DO THIS FIRST
from service.Environment import init_environment

init_environment()

# Print some interesting setup stuff before logger init
print 'os.getcwd(): %s' % os.getcwd()
print 'sys.platform: %s\nos.name: %s' % (sys.platform, os.name)

# Init logger
logging.basicConfig(level=logging.DEBUG)
_logger = logging.getLogger(__name__)

if __name__ == '__main__':
    if os.environ['ANDROID_HOST'].startswith("ANDROID"):
        # Start android service
        from android import AndroidService

        service = AndroidService("TSAP Tribler Session", "A tribler session is running..")

        _logger.info("Starting service..")
        service.start()

    else:
        # Just run services/main.py
        import subprocess

        os.chdir(os.path.join(os.getcwd(), 'service'))
        subprocess.call(["python", "main.py"])