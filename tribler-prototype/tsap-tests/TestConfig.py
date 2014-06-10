# coding: utf-8
# Written by Wendo Sabée
# Global settings for all tests

import os


if not "XMLRPC_URL" in os.environ:
    XMLRPC_URL = "http://127.0.0.1:8000/tribler"
else:
    XMLRPC_URL = os.environ['XMLRPC_URL']

REMOTE_SEARCH_TIMEOUT = 20  # seconds
REMOTE_SEARCH_SLEEP = .5  # seconds

REMOTE_DEADLOCK_TESTS = False