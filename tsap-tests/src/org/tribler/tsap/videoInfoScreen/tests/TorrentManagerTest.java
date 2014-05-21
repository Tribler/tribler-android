package org.tribler.tsap.videoInfoScreen.tests;

import org.tribler.tsap.videoInfoScreen.TorrentManager;

import junit.framework.TestCase;

/**
 * Unit tests for the TorrentManager class
 * 
 * @author Niels Spruit
 */
public class TorrentManagerTest extends TestCase {
	
	private TorrentManager mTorrentManager;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		mTorrentManager = TorrentManager.getInstance();			
	}
	
	/**
	 * Tests whether the test fixture is set up correctly
	 */
	public void testPreconditions() {
		assertNotNull("mTorrentManager is null", mTorrentManager);
	}

	/**
	 * Tests whether the torrent manager is initialized (contains at least one
	 * torrent)
	 */
	public void testTorrentManagerInitialized() {
		assertTrue("no torrents in torrent manager",
				mTorrentManager.getNumberOfTorrents() > 0);
	}

	/**
	 * Test whether the torrent manager contains three torrents
	 */
	public void testGetNumberOfTorrents() {
		assertEquals("getNumberOfTorrents() incorrect", 10,
				mTorrentManager.getNumberOfTorrents());
	}

	/**
	 * Tests whether the TorrentManager.containsTorrentWithID() method works
	 * correctly
	 */
	public void testContainsTorrentWithID() {
		assertTrue("containsTorrentWithID(0) incorrect",
				mTorrentManager.containsTorrentWithID(0));
		assertTrue("containsTorrentWithID(1) incorrect",
				mTorrentManager.containsTorrentWithID(1));
		assertTrue("containsTorrentWithID(2) incorrect",
				mTorrentManager.containsTorrentWithID(2));
		assertFalse("containsTorrentWithID(getNumberOfTorrents()) incorrect",
				mTorrentManager.containsTorrentWithID(mTorrentManager.getNumberOfTorrents()));
	}
}
