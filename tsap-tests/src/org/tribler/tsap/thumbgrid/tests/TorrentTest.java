package org.tribler.tsap.thumbgrid.tests;

import org.tribler.tsap.Torrent;
import org.tribler.tsap.thumbgrid.TORRENT_HEALTH;

import android.test.AndroidTestCase;

/**
 * Unit tests for the Torrent class
 * 
 * @author Niels Spruit
 */
public class TorrentTest extends AndroidTestCase {

	private Torrent mTorrent;

	private static final String infohash = "infohash";
	private static final String title = "Sintel 2010";
	private static final TORRENT_HEALTH health = TORRENT_HEALTH.YELLOW;
	private static final long size = 349834;
	private static final String category = "other";
	private static final int leechers = 434;
	private static final int seeders = 434;
	
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		mTorrent = new Torrent(title,infohash,size,seeders,leechers,category);
	}

	/**
	 * Tests whether the test fixture is set up correctly
	 */
	public void testPreconditions() {
		assertNotNull("mTorrent is null", mTorrent);
	}

	/**
	 * Tests whether the getX functions of Torrent behave correctly
	 */
	public void testGetters() {
		assertEquals("getName() incorrect", title,	mTorrent.getName());
		assertEquals("getSize() incorrect", size, mTorrent.getSize());
		assertEquals("getHealth() incorrect", health, mTorrent.getHealth());
		assertEquals("getCategory() incorrect", category, mTorrent.getCategory());
		assertEquals("getLeechers() incorrect", leechers, mTorrent.getLeechers());
		assertEquals("getSeeders() incorrect", seeders, mTorrent.getSeeders());
	}

	/**
	 * Tests whether the Torrent.toString() function behaves correctly
	 */
	public void testToString() {
		assertEquals("toString() incorrect", "Sintel 2010",
				mTorrent.toString());
	}

	/**
	 * Tests whether the Torrent.setName() method actually changes the title
	 */
	public void testSetName() {
		String newName = "TestMovie";

		assertEquals("getName() incorrect", title,
				mTorrent.getName());
	
		mTorrent.setName(newName);
		assertEquals("setName() incorrect", newName,
				mTorrent.getName());
	}

	/**
	 * Tests whether the Torrent.setSize() method actually changes the size
	 */
	public void testSetSize() {
		long newSize = 2389478923L;
		
		assertEquals("getSize() incorrect", size, mTorrent.getSize());
		
		mTorrent.setSize(newSize);
		assertEquals("setSize() incorrect", newSize, mTorrent.getSize());
	}

	/**
	 * Tests whether the Torrent.setLeechers() method actually changes the leechers
	 */
	public void testSetLeechers() {
		int newLeechers = 238;
		
		assertEquals("getLeechers() incorrect", leechers, mTorrent.getLeechers());
		
		mTorrent.setLeechers(newLeechers);
		assertEquals("setLeechers() incorrect", newLeechers, mTorrent.getLeechers());
	}
	
	/**
	 * Tests whether the Torrent.setSeeders() method actually changes the seeders
	 */
	public void testSetSeeders() {
		int newSeeders = 238;
		
		assertEquals("getSeeders() incorrect", seeders, mTorrent.getSeeders());
		
		mTorrent.setSeeders(newSeeders);
		assertEquals("setSeeders() incorrect", newSeeders, mTorrent.getSeeders());
	}
	
	/**
	 * Tests whether the Torrent.setCategory() method actually changes the category
	 */
	public void testSetCategory() {
		String newCategory = "XXX";

		assertEquals("getName() incorrect", category,
				mTorrent.getCategory());
	
		mTorrent.setCategory(newCategory);
		assertEquals("setCategory() incorrect", newCategory,
				mTorrent.getCategory());
	}
	
	/**
	 * Tests wheter the infoHash getter works
	 */
	public void testGetInfoHash() {
		assertEquals("getInfoHash incorrect", infohash, mTorrent.getInfoHash());
	}
}
