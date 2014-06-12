import unittest
import xmlrpclib
import os
import pytest
from time import sleep

# Import configuration
from TestConfig import *


class DownloadByInfohash(unittest.TestCase):

    def setUp(self):
        """
        Load XML-RPC connection.
        :return: Nothing.
        """
        self.xmlrpc = xmlrpclib.ServerProxy(XMLRPC_URL, allow_none=True)

    def tearDown(self):
        """
        Destroy XML-RPC connection.
        :return: Nothing.
        """
        self.xmlrpc = None

    @pytest.mark.timeout(10)
    def testA_Methods(self):
        """
        Tests if the channels.* methods exist
        :return: Nothing.
        """
        methods = self.xmlrpc.system.listMethods()

        assert len(methods) > 0
        assert 'downloads.add' in methods
        assert 'downloads.remove' in methods
        assert 'downloads.get_progress_info' in methods
        assert 'downloads.get_all_progress_info' in methods
        assert 'downloads.get_vod_info' in methods
        assert 'downloads.get_full_info' in methods
        assert 'downloads.start_vod' in methods
        assert 'downloads.stop_vod' in methods
        assert 'downloads.get_vod_uri' in methods
        assert 'downloads.set_state' in methods

    def clean_torrents(self):
        all_dls = self.xmlrpc.downloads.get_all_progress_info()
        for dl in all_dls:
            self.xmlrpc.downloads.remove(dl['infohash'], True)

    def testC_AddAndRemoveSintel(self):
        """
        Search for 'Eztv' and check results.
        :return: Nothing.
        """

        self.clean_torrents()

        all_dls = self.xmlrpc.downloads.get_all_progress_info()
        assert len(all_dls) == 0, all_dls

        assert self.xmlrpc.downloads.add(SINTEL_TEST_INFOHASH, 'Sintel')
        sleep(1)

        all_dls = self.xmlrpc.downloads.get_all_progress_info()
        assert len(all_dls) == 1
        assert all_dls[0]['infohash'] == SINTEL_TEST_INFOHASH

        timeout = DHT_DOWNLOAD_TORRENT_TIMEOUT
        while all_dls[0]['status_string'] == 'DLSTATUS_METADATA' and (timeout > 0):
            all_dls = self.xmlrpc.downloads.get_all_progress_info()

            sleep(DHT_DOWNLOAD_TORRENT_SLEEP)
            timeout -= DHT_DOWNLOAD_TORRENT_SLEEP

        assert timeout >= 0
        assert not all_dls[0]['status_string'] == 'DLSTATUS_METADATA', all_dls[0]['status_string']
        sleep(1)

        assert self.xmlrpc.downloads.remove(SINTEL_TEST_INFOHASH, True)
        sleep(1)

        all_dls = self.xmlrpc.downloads.get_all_progress_info()
        assert len(all_dls) == 0

    @unittest.skipIf(not DOWNLOAD_TORRENT_TESTS, "DOWNLOAD_TORRENT_TESTS is False")
    def testD_DownloadSintel(self):
        """
        Search for 'Eztv' and check results.
        :return: Nothing.
        """

        self.clean_torrents()

        all_dls = self.xmlrpc.downloads.get_all_progress_info()
        assert len(all_dls) == 0, all_dls

        assert self.xmlrpc.downloads.add(SINTEL_TEST_INFOHASH, 'Sintel')
        sleep(1)

        all_dls = self.xmlrpc.downloads.get_all_progress_info()
        assert len(all_dls) == 1
        assert all_dls[0]['infohash'] == SINTEL_TEST_INFOHASH

        timeout = DHT_DOWNLOAD_TORRENT_TIMEOUT
        while all_dls[0]['status_string'] == 'DLSTATUS_METADATA' and (timeout > 0):
            all_dls = self.xmlrpc.downloads.get_all_progress_info()

            sleep(DHT_DOWNLOAD_TORRENT_SLEEP)
            timeout -= DHT_DOWNLOAD_TORRENT_SLEEP

        assert timeout >= 0
        assert not all_dls[0]['status_string'] == 'DLSTATUS_METADATA', all_dls[0]['status_string']
        sleep(1)

        timeout = TORRENT_DOWNLOAD_STALL_TIMEOUT
        progress = 0
        while all_dls[0]['status_string'] == 'DLSTATUS_DOWNLOADING' and (timeout > 0):
            all_dls = self.xmlrpc.downloads.get_all_progress_info()

            if progress == all_dls[0]['progress']:
                timeout -= TORRENT_DOWNLOAD_STALL_SLEEP
            else:
                progress = all_dls[0]['progress']
                timeout = TORRENT_DOWNLOAD_STALL_TIMEOUT

            print "%.2f%% at %.2f KiB/s" % (progress * 100, all_dls[0]['speed_down'] / 1024)
            sleep(TORRENT_DOWNLOAD_STALL_SLEEP)

        assert timeout >= 0
        assert not all_dls[0]['status_string'] == 'DLSTATUS_DOWNLOADING', all_dls[0]['status_string']
        assert all_dls[0]['status_string'] == 'DLSTATUS_SEEDING', all_dls[0]['status_string']
        sleep(1)

        assert self.xmlrpc.downloads.remove(SINTEL_TEST_INFOHASH, True)
        sleep(1)

        all_dls = self.xmlrpc.downloads.get_all_progress_info()
        assert len(all_dls) == 0


if __name__ == '__main__':
    print "Using %s as test target." % XMLRPC_URL
    unittest.main()