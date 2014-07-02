package org.tribler.tsap.downloads.tests;

import org.tribler.tsap.Torrent;
import org.tribler.tsap.downloads.Download;
import org.tribler.tsap.downloads.DownloadStatus;

import junit.framework.TestCase;

public class DownloadTest extends TestCase {
	public void testDownload() {
		Torrent torrent = new Torrent("", "", 0, 0, 0, "");
		DownloadStatus downloadStatus = new DownloadStatus(0, 0.0, 0.0, 0.0,
				0.0);
		Download download = new Download(torrent, downloadStatus, 1.5, true, 1);

		assertEquals(torrent, download.getTorrent());
		assertEquals(downloadStatus, download.getDownloadStatus());
		assertEquals(1.5, download.getVOD_ETA());
		assertEquals(true, download.isVODPlayable());
		assertEquals(1, download.getAvailability());
	}
}