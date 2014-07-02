package org.tribler.tsap.thumbgrid.tests;

import org.tribler.tsap.thumbgrid.TORRENT_HEALTH;

import android.graphics.Color;
import android.test.AndroidTestCase;

/**
 * Unit tests for the TORRENT_HEALTH class
 * 
 * @author Wendo Sabée
 */
public class TorrentHealthTest extends AndroidTestCase {

	public void testToColorUnknown() {
		TORRENT_HEALTH health = TORRENT_HEALTH.UNKNOWN;
		assertEquals("TORRENT_HEALTH.UNKNOWN color incorrect",
				TORRENT_HEALTH.toColor(health), Color.GRAY);
	}

	public void testToColorRed() {
		TORRENT_HEALTH health = TORRENT_HEALTH.RED;
		assertEquals("TORRENT_HEALTH.RED color incorrect",
				TORRENT_HEALTH.toColor(health), Color.RED);
	}

	public void testToColorYellow() {
		TORRENT_HEALTH health = TORRENT_HEALTH.YELLOW;
		assertEquals("TORRENT_HEALTH.YELLOW color incorrect",
				TORRENT_HEALTH.toColor(health), Color.YELLOW);
	}

	public void testToColorGreen() {
		TORRENT_HEALTH health = TORRENT_HEALTH.GREEN;
		assertEquals("TORRENT_HEALTH.GREEN color incorrect",
				TORRENT_HEALTH.toColor(health), Color.GREEN);
	}
}
