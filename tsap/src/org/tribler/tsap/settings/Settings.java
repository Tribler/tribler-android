package org.tribler.tsap.settings;

import java.io.File;
import java.net.URL;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

public class Settings {
	public enum TorrentType {
		VIDEO, ALL
	};

	private static File mThumbFolder = null;
	private static XMLRPCSettingsManager mSettingsManager;
	private static Context mContext; 

	public static File getThumbFolder() {
		return mThumbFolder;
	}
	public static void XMLRPCSetThumbFolder(File thumbFolder) {
		mThumbFolder = thumbFolder;
	}

	public static boolean getFamilyFilterOn() {
		SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(mContext);
		return sharedPref.getBoolean("pref_familyFilter", true);
	}
	public static TorrentType getFilteredTorrentTypes() {
		SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(mContext);
		String typeString = sharedPref.getString("pref_supportedTorrentTypes", "@string/pref_supported_types_default");
		if (typeString.equals("Video"))
			return TorrentType.VIDEO;
		if (typeString.equals("All"))
			return TorrentType.ALL;
		return TorrentType.ALL;
	}

	public static void setup(URL url, Context context) {
		mSettingsManager = new XMLRPCSettingsManager(url);
		mContext = context;
	}
	
	public static void loadThumbFolder() {
		mSettingsManager.getThumbFolder();
	}
}