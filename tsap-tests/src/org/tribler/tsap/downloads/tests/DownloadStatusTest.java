package org.tribler.tsap.downloads.tests;

import org.tribler.tsap.downloads.DownloadStatus;

import junit.framework.TestCase;

public class DownloadStatusTest extends TestCase {
	public void testDownloadStatus () {
		DownloadStatus downloadStatus = new DownloadStatus(3, 33.5, 129.8, 0.44, 47.1);
		assertEquals(3, downloadStatus.getStatus());
		assertEquals(33.5, downloadStatus.getDownloadSpeed());
		assertEquals(129.8, downloadStatus.getUploadSpeed());
		assertEquals(0.44, downloadStatus.getProgress());
		assertEquals(47.1, downloadStatus.getETA());
	}
}