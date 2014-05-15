package org.tribler.tsap.videoInfoScreen.tests;

import org.tribler.tsap.videoInfoScreen.TorrentManager;

import junit.framework.TestCase;

/**
 * Unit tests for the TorrentManager class
 * @author Niels Spruit
 */
public class TorrentManagerTest extends TestCase {
	
	@Override
	protected void setUp() throws Exception
	{
		super.setUp();
		TorrentManager.initializeTorrents();
	}
	
	/**
	 * Tests whether the torrent manager is initialized (contains at least one torrent)
	 */
	public void testTorrentManagerInitialized()
	{
		assertTrue("no torrents in torrent manager", TorrentManager.getNumberOfTorrents() > 0 );
	}
	
	/**
	 * Test whether the torrent manager contains three torrents
	 */
	public void testGetNumberOfTorrents()
	{
		assertEquals("getNumberOfTorrents() incorrect",3,TorrentManager.getNumberOfTorrents());
	}
	
	/**
	 * Tests whether the TorrentManager.containsTorrentWithID() method works correctly
	 */
	public void testContainsTorrentWithID()
	{
		assertTrue("containsTorrentWithID(0) incorrect", TorrentManager.containsTorrentWithID(0));
		assertTrue("containsTorrentWithID(1) incorrect", TorrentManager.containsTorrentWithID(1));
		assertTrue("containsTorrentWithID(2) incorrect", TorrentManager.containsTorrentWithID(2));
		assertFalse("containsTorrentWithID(3) incorrect", TorrentManager.containsTorrentWithID(3));
	}
}
