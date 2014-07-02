package org.tribler.tsap.settings;

import java.io.File;

import org.tribler.tsap.XMLRPC.XMLRPCConnection;
import org.tribler.tsap.settings.XMLRPCThumbFolderManager;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Settings {
	public enum TorrentType {
		VIDEO, ALL
	};

	private static File mThumbFolder = null;
	private static Context mContext;
	@SuppressWarnings("unused")
	private static XMLRPCThumbFolderManager mFolderManager;

	public static File getThumbFolder() {
		return mThumbFolder;
	}

	public static void XMLRPCSetThumbFolder(File thumbFolder) {
		mThumbFolder = thumbFolder;
	}

	public static int getMaxDownloadRate() {
		SharedPreferences sharedPref = PreferenceManager
				.getDefaultSharedPreferences(mContext);
		return Integer.valueOf(sharedPref.getString("pref_maxDownloadRate", "0"));
	}

	public static int getMaxUploadRate() {
		SharedPreferences sharedPref = PreferenceManager
				.getDefaultSharedPreferences(mContext);
		return Integer.valueOf(sharedPref.getString("pref_maxUploadRate", "0"));
	}

	public static boolean getFamilyFilterOn() {
		SharedPreferences sharedPref = PreferenceManager
				.getDefaultSharedPreferences(mContext);
		return sharedPref.getBoolean("pref_familyFilter", true);
	}

	public static TorrentType getFilteredTorrentTypes() {
		SharedPreferences sharedPref = PreferenceManager
				.getDefaultSharedPreferences(mContext);
		String typeString = sharedPref.getString("pref_supportedTorrentTypes",
				"@string/pref_supported_types_default");
		if (typeString.equals("Video"))
			return TorrentType.VIDEO;
		if (typeString.equals("All"))
			return TorrentType.ALL;
		return TorrentType.ALL;
	}

	public static void setup(Context context, XMLRPCConnection connection) {
		mFolderManager = new XMLRPCThumbFolderManager(connection);
		mContext = context;
	}

	public static void setup(Context context) {
		mContext = context;
	}
}