package org.tribler.tsap.tests;

import org.tribler.tsap.Utility;

import android.test.AndroidTestCase;

public class UtilityTest extends AndroidTestCase {
	public void testBytesToStringConversion()
	{
		assertEquals("bytes formatted incorrectly", "0B",
				Utility.convertBytesToString(0));
		assertEquals("bytes formatted incorrectly", "44B",
				Utility.convertBytesToString(44));
		assertEquals("bytes formatted incorrectly", "-347B",
				Utility.convertBytesToString(-347.1829));
		assertEquals("bytes formatted incorrectly", "77.7kB",
				Utility.convertBytesToString(77708.99));
		assertEquals("bytes formatted incorrectly", "-439.8MB",
				Utility.convertBytesToString(-439875670.0));
		assertEquals("bytes formatted incorrectly", "43.9GB",
				Utility.convertBytesToString(43987567444.0));
		assertEquals("bytes formatted incorrectly", "-43.9TB",
				Utility.convertBytesToString(-43987567444888.0));
		assertEquals("bytes formatted incorrectly", "43.9PB",
				Utility.convertBytesToString(43987567444888777.0));
		assertEquals("bytes formatted incorrectly", "-439875.6PB",
				Utility.convertBytesToString(-439875674448887771234.0));
	}
	public void testBytesPerSecondToStringConversion()
	{
		assertEquals("bytes formatted incorrectly", "0B/s",
				Utility.convertBytesPerSecToString(0));
		assertEquals("bytes formatted incorrectly", "-44B/s",
				Utility.convertBytesPerSecToString(-44));
		assertEquals("bytes formatted incorrectly", "347B/s",
				Utility.convertBytesPerSecToString(347.1829));
		assertEquals("bytes formatted incorrectly", "-77.7kB/s",
				Utility.convertBytesPerSecToString(-77708.99));
		assertEquals("bytes formatted incorrectly", "439.8MB/s",
				Utility.convertBytesPerSecToString(439875670.0));
		assertEquals("bytes formatted incorrectly", "-43.9GB/s",
				Utility.convertBytesPerSecToString(-43987567444.0));
		assertEquals("bytes formatted incorrectly", "43.9TB/s",
				Utility.convertBytesPerSecToString(43987567444888.0));
		assertEquals("bytes formatted incorrectly", "-43.9PB/s",
				Utility.convertBytesPerSecToString(-43987567444888777.0));
		assertEquals("bytes formatted incorrectly", "439875.6PB/s",
				Utility.convertBytesPerSecToString(439875674448887771234.0));
	}
	public void testDownloadStateConversion()
	{
		assertEquals("state converted incorrectly", "Invalid state",
				Utility.convertDownloadStateIntToMessage(0));
		assertEquals("state converted incorrectly", "Invalid state",
				Utility.convertDownloadStateIntToMessage(-1));
		assertEquals("state converted incorrectly", "Invalid state",
				Utility.convertDownloadStateIntToMessage(98093));
		assertEquals("state converted incorrectly", "Allocating disk space",
				Utility.convertDownloadStateIntToMessage(1));
		assertEquals("state converted incorrectly", "Waiting on the hash check",
				Utility.convertDownloadStateIntToMessage(2));
		assertEquals("state converted incorrectly", "Downloading",
				Utility.convertDownloadStateIntToMessage(3));
		assertEquals("state converted incorrectly", "Seeding",
				Utility.convertDownloadStateIntToMessage(4));
		assertEquals("state converted incorrectly", "Stopped",
				Utility.convertDownloadStateIntToMessage(5));
		assertEquals("state converted incorrectly", "Stopped because of an error",
				Utility.convertDownloadStateIntToMessage(6));
		assertEquals("state converted incorrectly", "Acquiring metadata",
				Utility.convertDownloadStateIntToMessage(7));
	}
}