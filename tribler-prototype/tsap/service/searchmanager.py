__author__ = 'user'


class SearchManager():

    _session = None

    def __init__(self, session):
        self._session = session

    def search_channel(self, keyword):
        pass

    def search_torrent(self, keyword):
        pass