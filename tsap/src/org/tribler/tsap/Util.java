package org.tribler.tsap;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.PorterDuff;

public class Util {

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
}