package org.tribler.tsap.tests;

import junit.framework.TestCase;
import org.tribler.tsap.Channel;

/**
 * Unit tests for the Channel class
 * 
 * @author Niels Spruit
 */
public class ChannelTest extends TestCase {

	private Channel mChannel;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		mChannel = new Channel("Test channel", false, 105, 4);
	}

	/**
	 * Tests whethet the test fixture is set up correctly
	 */
	public void testPreconditions() {
		assertNotNull("mChannel is null", mChannel);
	}

	/**
	 * Test whether the getters of Channel work correctly
	 */
	public void testGetters() {
		assertEquals("getName() incorrect", "Test channel", mChannel.getName());
		assertEquals("getFollowing() incorrect", false, mChannel.getFollowing());
		assertEquals("getTorrentAmount() incorrect", 105,
				mChannel.getTorrentAmount());
		assertEquals("getRating() incorrect", 4, mChannel.getRating());
	}

	/**
	 * Tests the Channel.toString() method
	 */
	public void testToString() {
		assertEquals("toString() incorrect", "Test channel",
				mChannel.toString());
	}

}
