__author__ = 'user'


class SearchManager():

    _session = None

    _channel_results = []
    _torrent_results = []

    def __init__(self, session, xmlrpc=None):
        self._session = session

        if xmlrpc:
            self._xmlrpc_register(xmlrpc)

    def _xmlrpc_register(self, xmlrpc):
        # channels
        xmlrpc.register_function(self.search_channel_local, "search.local_channel")
        xmlrpc.register_function(self.search_channel_remote, "search.remote_channel")

        # torrents
        xmlrpc.register_function(self.search_torrent_local, "search.local_torrent")
        xmlrpc.register_function(self.search_torrent_remote, "search.remote_torrent")

    def search_channel_local(self, keyword):
        return {"status": "OK", "keyword": keyword}

    def search_channel_remote(self, keyword):
        pass

    def _search_channel_remote_callback(self):
        pass

    def search_torrent_local(self, keyword):
        pass

    def search_torrent_remote(self, keyword):
        pass

    def _search_torrent_remote_callback(self):
        pass