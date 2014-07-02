package org.tribler.tsap.util;

import java.util.Map;

import org.tribler.tsap.thumbgrid.TORRENT_HEALTH;

/**
 * Class with static functions that are used across multiple classes, like
 * conversion functions.
 * 
 * @author Dirk Schut
 * 
 */
public class Utility {

	/**
	 * Converts a number of seconds to a sensible string that can be
	 * shown to the user.
	 * @param seconds Number of seconds
	 * @return A user friendly string
	 */
	public static String convertSecondsToString(double seconds)
	{
		if(seconds < 10)
		{
			return "a few seconds";
		}
		else if(seconds < 60)
		{
			return "about " + (Math.round(seconds / 10) * 10) + " seconds";
		}
		else if(seconds < 3600)
		{
			long value = Math.round(seconds / 60);
			return "about " + value + " minute" + ((value > 1) ? "s" : "");
		}
		else if(seconds < 24 * 3600)
		{
			long value = (Math.round(seconds / 3600));
			return "about " + value + " hour" + ((value > 1) ? "s" : "");
		}
		else
		{
			return "more than a day";
		}
	}
	
	/**
	 * Converts an amount of bytes into a nicely formatted String. It adds the
	 * correct prefix like KB or GB and limits the precision to one number
	 * behind the point.
	 * 
	 * @param size
	 *            The amount of bytes.
	 * @return A string in the format NNN.NXB
	 */
	public static String convertBytesToString(double size) {
		String prefixes[] = { " B", " kB", " MB", " GB", " TB", " PB" };
		int prefix = 0;
		String minus;
		if(size < 0)
		{
			size = -size;
			minus = "-";
		}
		else
		{
			minus = "";
		}
		while (size > 1000 && prefix < prefixes.length - 1) {
			size /= 1000;
			prefix++;
		}
		String intString = minus + String.valueOf((int) size);
		if (prefix == 0 || size == (int) size) {
			return intString + prefixes[prefix];
		} else {
			return intString + "."
					+ String.valueOf(size - (int) size).charAt(2) + prefixes[prefix];
		}
	}

	/**
	 * Does the same as convertBytesToString, but adds /s to indicate it's bytes
	 * per second
	 * 
	 * @param speed
	 *            the amount of bytes per second
	 * @return A string in the format NNN.NXB/s
	 */
	public static String convertBytesPerSecToString(double speed) {
		return convertBytesToString(speed) + "/s";
	}

	/**
	 * Converts the Tribler state code of a download into a readable message.
	 * 
	 * @param state
	 *            Tribler state code
	 * @return The message corresponding to the code
	 */
	public static String convertDownloadStateIntToMessage(int state) {
		switch (state) {
		case 1:
			return "Allocating disk space";
		case 2:
			return "Waiting for hash check";
		case 3:
			return "Downloading";
		case 4:
			return "Seeding";
		case 5:
			return "Stopped";
		case 6:
			return "Stopped because of an error";
		case 7:
			return "Acquiring metadata";
		default:
			return "Invalid state";

		}
	}

	@SuppressWarnings("unchecked")
	public static <T> T getFromMap(Map<String, Object> map, String key, T defaultValue) {
		T returnValue = (T) map.get(key);
		if(returnValue == null)
		{
			return defaultValue;
		}
		else
		{
			return returnValue;
		}
	}
	
	public static TORRENT_HEALTH calculateTorrentHealth(int seeders, int leechers)
	{
		if (seeders == -1 || leechers == -1)
		{
			return TORRENT_HEALTH.UNKNOWN;
		}
		
		if (seeders == 0)
		{
			return TORRENT_HEALTH.RED;
		}
		
		return ((leechers / seeders) > 0.5) ? TORRENT_HEALTH.YELLOW : TORRENT_HEALTH.GREEN;
	}
}