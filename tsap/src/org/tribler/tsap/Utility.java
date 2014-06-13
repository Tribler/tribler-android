package org.tribler.tsap;

public class Utility {

	public static String convertBytesToString(double size) {
		String prefixes[] = {"B", "KB", "MB", "GB", "TB"};
		int prefix = 0;
		while(size > 1000 && prefix < 4)
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
	
	public static String convertBytesPerSecToString(double speed) {
		return convertBytesToString(speed) + "/s";
	}
	
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