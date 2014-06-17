package org.tribler.tsap;

/**
 * Class with static functions that are used across multiple classes, like conversion functions.
 * @author Dirk Schut
 *
 */
public class Utility {

	/**
	 * Converts an amount of bytes into a nicely formatted String. It adds the correct prefix like KB or GB and limits the precision to one number behind the point. 
	 * @param size
	 * 			The amount of bytes. 
	 * @return
	 * 			A string in the format NNN.NXB
	 */
	public static String convertBytesToString(double size) {
		String prefixes[] = {"B", "kB", "MB", "GB", "TB", "PB"};
		int prefix = 0;
		while(size > 1000 && prefix < prefixes.length-1)
		{
			size /= 1000;
			prefix ++;
		}
		String intString = String.valueOf((int)size); 
		if (prefix == 0 || size == (int)size)
		{
			return intString + prefixes[prefix];
		}
		else
		{
			return intString + "." + String.valueOf(size - (int)size).charAt(2) + prefixes[prefix]; 
		}
	}
	
	/**
	 * Does the same as convertBytesToString, but adds /s to indicate it's bytes per second
	 * @param speed
	 * 			the amount of bytes per second
	 * @return
	 * 			A string in the format NNN.NXB/s
	 */
	public static String convertBytesPerSecToString(double speed) {
		return convertBytesToString(speed) + "/s";
	}
	
	/**
	 * Converts the Tribler state code of a download into a readable message.
	 * @param state
	 * 			Tribler state code
	 * @return
	 * 			The message corresponding to the code
	 */
	public static String convertDownloadStateIntToMessage(int state)
	{
		switch(state)
		{
			case 1:
				return "Allocating disk space.";
			case 2:
				return "Waiting on the hash check.";
			case 3:
				return "Downloading.";
			case 4:
				return "Seeding.";
			case 5:
				return "Stopped.";
			case 6:
				return "Stopped because of an error.";
			case 7:
				return "Acquiring metadata.";
			default:
				return "Invalid state";
				
		}
	}
}