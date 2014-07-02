package org.tribler.tsap.thumbgrid;

import java.io.Serializable;

import android.graphics.Color;

/**
 * Enumerator of the possible healths of a torrent
 * 
 * @author Wendo Sabée
 */
public enum TORRENT_HEALTH implements Serializable {
	UNKNOWN, RED, YELLOW, GREEN;

	/**
	 * Returns the color value belonging to health value
	 * 
	 * @param health
	 *            The health of torrent
	 * @return The color belonging to the health
	 */
	public static int toColor(TORRENT_HEALTH health) {
		switch (health) {
		case RED:
			return Color.RED;
		case YELLOW:
			return Color.YELLOW;
		case GREEN:
			return Color.GREEN;
		default:
			return Color.GRAY;
		}
	}
}