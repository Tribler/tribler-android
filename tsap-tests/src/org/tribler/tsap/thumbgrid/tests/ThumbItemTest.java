package org.tribler.tsap.thumbgrid.tests;

import org.tribler.tsap.thumbgrid.TORRENT_HEALTH;
import org.tribler.tsap.thumbgrid.ThumbItem;

import android.test.AndroidTestCase;

/**
 * Unit tests for the ThumbItem class
 * 
 * @author Niels Spruit
 */
public class ThumbItemTest extends AndroidTestCase {

	private ThumbItem mThumbItem;

	private static final String infohash = "infohash";
	private static final String title = "Sintel 2010";
	private static final TORRENT_HEALTH health = TORRENT_HEALTH.RED;
	private static final long size = 349834;
	private static final String category = "other";
	private static final int leechers = 434;
	private static final int seeders = 434;
	
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		mThumbItem = new ThumbItem(infohash, title, health, size, category, leechers, seeders);
	}

	/**
	 * Tests whether the test fixture is set up correctly
	 */
	public void testPreconditions() {
		assertNotNull("mThumbItem is null", mThumbItem);
	}

	/**
	 * Tests whether the getX functions of ThumbItem behave correctly
	 */
	public void testGetters() {
		assertEquals("getTitle() incorrect", title,	mThumbItem.getTitle());
		assertEquals("getSize() incorrect", size, mThumbItem.getSize());
		assertEquals("getHealth() incorrect", health, mThumbItem.getHealth());
		assertEquals("getHealthColor() incorrect", TORRENT_HEALTH.toColor(health), mThumbItem.getHealthColor());
		assertEquals("getCategory() incorrect", category, mThumbItem.getCategory());
		assertEquals("getLeechers() incorrect", leechers, mThumbItem.getLeechers());
		assertEquals("getSeeders() incorrect", seeders, mThumbItem.getSeeders());
	}

	/**
	 * Tests whether the ThumbItem.toString() function behaves correctly
	 */
	public void testToString() {
		assertEquals("toString() incorrect", "Sintel 2010",
				mThumbItem.toString());
	}

	/**
	 * Tests whether the ThumbItem.setThumbnail() function behaves correctly
	 */
	/*public void testSetThumbnail() {
		assertEquals("getThumbnailId() incorrect", R.drawable.sintel,
				mThumbItem.getThumbnailId());
		mThumbItem.setThumbnail(24);
		assertEquals("setThumbnailId() incorrect", 24,
				mThumbItem.getThumbnailId());
	}*/

	/**
	 * Tests whether the ThumbItem.setTitle() method actually changes the title
	 */
	public void testSetTitle() {
		String newTitle = "TestMovie";

		assertEquals("getTitle() incorrect", title,
				mThumbItem.getTitle());
	
		mThumbItem.setTitle(newTitle);
		assertEquals("setTitle() incorrect", newTitle,
				mThumbItem.getTitle());
	}

	/**
	 * Tests whether the ThumbItem.setSize() method actually changes the size
	 */
	public void testSetSize() {
		long newSize = 2389478923L;
		
		assertEquals("getSize() incorrect", size, mThumbItem.getSize());
		
		mThumbItem.setSize(newSize);
		assertEquals("setSize() incorrect", newSize, mThumbItem.getSize());
	}

	/**
	 * Tests whether the ThumbItem.setLeechers() method actually changes the leechers
	 */
	public void testSetLeechers() {
		int newLeechers = 238;
		
		assertEquals("getLeechers() incorrect", leechers, mThumbItem.getLeechers());
		
		mThumbItem.setLeechers(newLeechers);
		assertEquals("setLeechers() incorrect", newLeechers, mThumbItem.getLeechers());
	}
	
	/**
	 * Tests whether the ThumbItem.setSeeders() method actually changes the seeders
	 */
	public void testSetSeeders() {
		int newSeeders = 238;
		
		assertEquals("getSeeders() incorrect", seeders, mThumbItem.getSeeders());
		
		mThumbItem.setSeeders(newSeeders);
		assertEquals("setSeeders() incorrect", newSeeders, mThumbItem.getSeeders());
	}
	
	/**
	 * Tests whether the ThumbItem.setCategory() method actually changes the category
	 */
	public void testSetCategory() {
		String newCategory = "XXX";

		assertEquals("getTitle() incorrect", category,
				mThumbItem.getCategory());
	
		mThumbItem.setCategory(newCategory);
		assertEquals("setCategory() incorrect", newCategory,
				mThumbItem.getCategory());
	}
	
	/**
	 * Tests whether the health and healthcolor are actually changed by calling
	 * ThumbItem.setHealth()
	 */
	public void testSetHealth() {
		TORRENT_HEALTH newHealth = TORRENT_HEALTH.GREEN;
		
		assertEquals("getHealth() incorrect", health,
				mThumbItem.getHealth());
		
		mThumbItem.setHealth(newHealth);
		assertEquals("setHealth() incorrect", newHealth,
				mThumbItem.getHealth());
		assertEquals("setHealth() incorrect color", TORRENT_HEALTH.toColor(newHealth),
				mThumbItem.getHealthColor());
	}
	
	/**
	 * Tests wheter the infoHash getter works
	 */
	public void testGetInfoHash() {
		assertEquals("getInfoHash incorrect", infohash, mThumbItem.getInfoHash());
	}
}
