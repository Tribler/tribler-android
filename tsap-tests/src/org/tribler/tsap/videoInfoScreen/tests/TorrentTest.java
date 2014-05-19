package org.tribler.tsap.videoInfoScreen.tests;

import org.tribler.tsap.R;
import org.tribler.tsap.videoInfoScreen.Torrent;

import junit.framework.TestCase;

/**
 * Unit tests for the Torrent class
 * 
 * @author Niels Spruit
 */
public class TorrentTest extends TestCase {

	private Torrent mTorrent;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		mTorrent = new Torrent("Sintel 2010", "Video", "2011-08-07", 426.89,
				54, 4, "Sintel is a...", R.drawable.sintel);
	}

	/**
	 * Tests whether the test fixture is set up correctly
	 */
	public void testPreconditions() {
		assertNotNull("mTorrent is null", mTorrent);
	}

	/**
	 * Tests whether the getX functions of Torrent work correctly
	 */
	public void testGetters() {
		assertEquals("getName() incorrect", "Sintel 2010", mTorrent.getName());
		assertEquals("getType() incorrect", "Video", mTorrent.getType());
		assertEquals("getUploadDate() incorrect", "2011-08-07",
				mTorrent.getUploadDate());
		assertEquals("getFileSize() incorrect", "426.89 MB",
				mTorrent.getFilesize());
		assertEquals("getSeeders() incorrect", "54", mTorrent.getSeeders());
		assertEquals("getLeechers() incorrect", "4", mTorrent.getLeechers());
		assertEquals("getDescription() incorrect", "Sintel is a...",
				mTorrent.getDescription());
		assertEquals("getThumbnailID() incorrect", R.drawable.sintel,
				mTorrent.getThumbnailID());
	}

	/**
	 * Tests the Torrent.toString() method
	 */
	public void testToString() {
		assertEquals("toString() incorrect", "Sintel 2010", mTorrent.toString());
	}
}
