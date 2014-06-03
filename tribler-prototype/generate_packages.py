__author__ = 'user'

import os, sys


def get_packages(path):
    packages = []

    if not (path.startswith("/") or path[1:3] == ":\\"):
        path = os.path.join(os.getcwdu(), path)

    for root, dirs, files in os.walk(path):
        if '__init__.py' in files:
            packages.append(str(root[len(path) + 1:].replace(os.sep, ".")))

    return path, packages

if __name__ == "__main__":
    if len(sys.argv) == 2:
        path, packages = get_packages(sys.argv[1])
        print "For path: %s\n%s" % (path, packages)
    else:
        print "Usage: %s /path/to/package/directory" % sys.argv[0]