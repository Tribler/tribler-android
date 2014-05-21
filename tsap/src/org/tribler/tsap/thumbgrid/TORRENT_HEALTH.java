package org.tribler.tsap.thumbgrid;

import android.graphics.Color;

public enum TORRENT_HEALTH {
	UNKNOWN, RED, YELLOW, GREEN;

	public static int toColor(TORRENT_HEALTH health)
	{
		switch(health)
		{
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