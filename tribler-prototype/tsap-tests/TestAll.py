import unittest
import os

# add all tests here
for file in os.listdir(os.getcwd()):
    if file.endswith('_test.py'):
        try:
            modulename = file[:len(file) - 3]
            exec("from %s import *" % modulename)
            print "Imported tests from %s" % modulename
        except:
            pass

unittest.main()
