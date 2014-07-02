package org.tribler.tsap.util;

import java.util.Map;

import org.tribler.tsap.Torrent;
import org.tribler.tsap.settings.Settings;

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
	
	/**
	 * Applies filter to ThumbItem
	 * @param item The item which should be filtered (or not)
	 * @param filter The filter which should be applied
	 * @return True if the item should be let through, false if the item should be filtered
	 */
	public static boolean applyResultFilter(Torrent item, Settings.TorrentType filter)
	{
		String category = (item != null) ? item.getCategory().toLowerCase() : null;
		
		// Something went wrong here
		if(category == null)
			return false;
		
		switch(filter)
		{
		// True when: Video, VideoClip, XXX, Other.
		// "XXX" isn't filtered because we have a family filter, and most are video anyway
		// "Other" isn't filtered, as not all torrents have a correct category set
		case VIDEO:
			return (category.startsWith("video") || category.equals("other") || category.equals("xxx"));

		// ALL or any unknown filter should just let them all through
		case ALL:
		default:
			return true;
		}
	}
}