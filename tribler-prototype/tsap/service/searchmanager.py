__author__ = 'user'


class SearchManager():

    _session = None

    _channel_results = []
    _torrent_results = []

    def __init__(self, session):
        self._session = session

    def search_channel_local(self, keyword):
        pass

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