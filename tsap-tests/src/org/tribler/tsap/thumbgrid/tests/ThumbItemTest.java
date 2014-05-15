package org.tribler.tsap.thumbgrid.tests;

import org.tribler.tsap.R;
import org.tribler.tsap.thumbgrid.ThumbItem;
import org.tribler.tsap.thumbgrid.ThumbItem.TORRENT_HEALTH;

import android.graphics.Color;
import android.test.AndroidTestCase;

/**
 * Unit tests for the ThumbItem class
 * @author Niels Spruit
 */
public class ThumbItemTest extends AndroidTestCase {
	
private ThumbItem mThumbItem;
	
	@Override
	protected void setUp() throws Exception
	{
		super.setUp();
		mThumbItem = new ThumbItem("Sintel 2010", R.drawable.sintel, TORRENT_HEALTH.GREEN, 500);
	}
	
	/**
	 * Tests whether the test fixture is set up correctly
	 */
	public void testPreconditions()
	{
		assertNotNull("mThumbItem is null", mThumbItem);
	}

	/**
	 * Tests whether the getX functions of ThumbItem behave correctly
	 */
	public void testGetters()
	{
		assertEquals("getThumbnailId() incorrect", R.drawable.sintel, mThumbItem.getThumbnailId());
		assertEquals("getTitle() incorrect", "Sintel 2010", mThumbItem.getTitle());
		assertEquals("getSize() incorrect", 500, mThumbItem.getSize());
		assertEquals("getHealth() incorrect", TORRENT_HEALTH.GREEN, mThumbItem.getHealth());
		assertEquals("getHealthColor() incorrect", Color.GREEN, mThumbItem.getHealthColor());
	}
	
	/**
	 * Tests whether the ThumbItem.toString() function behaves correctly
	 */
	public void testToString()
	{
		assertEquals("toString() incorrect", "Sintel 2010", mThumbItem.toString());
	}
	
	public void testSetThumbnail()
	{
		assertEquals("getThumbnailId() incorrect", R.drawable.sintel, mThumbItem.getThumbnailId());
		mThumbItem.setThumbnail(24);
		assertEquals("setThumbnailId() incorrect", 24, mThumbItem.getThumbnailId());		
	}
	
	public void testSetTitle()
	{
		assertEquals("getTitle() incorrect", "Sintel 2010", mThumbItem.getTitle());
		mThumbItem.setTitle("Test Movie");
		assertEquals("setTitle() incorrect", "Test Movie", mThumbItem.getTitle());
	}
	
	public void testSetSize()
	{
		assertEquals("getSize() incorrect", 500, mThumbItem.getSize());
		mThumbItem.setSize(120);
		assertEquals("setSize() incorrect", 120, mThumbItem.getSize());
	}
	
	public void testSetHealth()
	{
		assertEquals("getHealth() incorrect", TORRENT_HEALTH.GREEN, mThumbItem.getHealth());
		mThumbItem.setHealth(TORRENT_HEALTH.RED);
		assertEquals("setHealth() incorrect", TORRENT_HEALTH.RED, mThumbItem.getHealth());
		assertEquals("setHealth() incorrect color", Color.RED, mThumbItem.getHealthColor());
	}
}
