__author__ = 'user'

from SimpleXMLRPCServer import SimpleXMLRPCServer
from SimpleXMLRPCServer import SimpleXMLRPCRequestHandler


class RequestHandler(SimpleXMLRPCRequestHandler):
    rpc_paths = ('/tribler',)

class XMLRPCServer:

    def __init__(self, iface="127.0.0.1", port=8000):
        self._server = SimpleXMLRPCServer((iface, port), requestHandler=RequestHandler)
        self._server.register_introspection_functions()

        self._iface = iface
        self._port = port

        pass

    def register_function(self, funct, name=None):
        self._server.register_function(funct, name)

    def register_class(self, cls, name=None):
        self._server.register_function(cls, name)

    def start_server(self):
        self._server.serve_forever()