package org.tribler.tsap.videoInfoScreen.tests;

import java.util.ArrayList;

import junit.framework.TestCase;

import org.tribler.tsap.thumbgrid.ThumbItem;
import org.tribler.tsap.videoInfoScreen.Torrent;
import org.tribler.tsap.videoInfoScreen.TorrentManager;

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
	 * Test whether the torrent manager returns the correct torrent
	 */
	public void testGetTorrentById() {
		Torrent tor = mTorrentManager.getTorrentById(0);
		assertNotNull("tor is null", tor);
		assertEquals("getTorrentById(0) incorrect", "Sintel", tor.getName());
	}

	/**
	 * Test whether the torrent manager returns null when a non-existing torrent
	 * is requested
	 */
	public void testGetTorrentByIdNull() {
		Torrent tor = mTorrentManager.getTorrentById(mTorrentManager
				.getNumberOfTorrents());
		assertNull("tor is not null", tor);
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
				mTorrentManager.containsTorrentWithID(mTorrentManager
						.getNumberOfTorrents()));
	}

	/**
	 * Tests whether the TorrentManger.getThumbItem method functions correctly
	 */
	public void testGetThumbItems() {
		ArrayList<ThumbItem> thumbs = mTorrentManager.getThumbItems();
		assertNotNull("thumbs is null", thumbs);
		assertEquals("size of thumbs incorrect",
				mTorrentManager.getNumberOfTorrents(), thumbs.size());
	}
}
