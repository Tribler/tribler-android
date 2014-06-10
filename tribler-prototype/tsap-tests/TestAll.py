# coding: utf-8
# Written by Wendo Sabée
# Automatically loads all tests from *_test.py files

import unittest
import os

def import_all_tests():
    """
    Import all tests from *_test.py files
    :return: Nothing
    """
    for file in os.listdir(os.getcwd()):
        if file.endswith('_test.py'):
            try:
                modulename = file[:len(file) - 3]
                exec("from %s import *" % modulename)
                print "Imported tests from %s" % modulename
            except:
                pass

if __name__ == '__main__':
    import_all_tests()
    unittest.main()
