package org.tribler.tsap.settings;

import java.io.File;

import org.tribler.tsap.XMLRPC.XMLRPCConnection;
import org.tribler.tsap.settings.XMLRPCThumbFolderManager;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Class from which the apps settings can be retrieved
 * 
 * @author Dirk Schut & Niels Spruit
 * 
 */
public class Settings {
	public enum TorrentType {
		VIDEO, ALL
	};

	private static File mThumbFolder = null;
	private static Context mContext;
	@SuppressWarnings("unused")
	private static XMLRPCThumbFolderManager mFolderManager;

	/**
	 * @return the file location of the thumbnail folder
	 */
	public static File getThumbFolder() {
		return mThumbFolder;
	}

	/**
	 * Set the thumbnail folder setting to thumbFolder
	 * 
	 * @param thumbFolder
	 */
	public static void XMLRPCSetThumbFolder(File thumbFolder) {
		mThumbFolder = thumbFolder;
	}

	/**
	 * @return the value of the maximum download rate setting
	 */
	public static int getMaxDownloadRate() {
		SharedPreferences sharedPref = PreferenceManager
				.getDefaultSharedPreferences(mContext);
		return Integer.valueOf(sharedPref
				.getString("pref_maxDownloadRate", "0"));
	}

	/**
	 * @return the value of the maximum upload rate setting
	 */
	public static int getMaxUploadRate() {
		SharedPreferences sharedPref = PreferenceManager
				.getDefaultSharedPreferences(mContext);
		return Integer.valueOf(sharedPref.getString("pref_maxUploadRate", "0"));
	}

	/**
	 * @return a boolean indicating whether Tribler's family filter is enabled
	 *         or disabled
	 */
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

	/**
	 * Sets up this class (initializes context and connection)
	 * 
	 * @param context
	 * @param connection
	 */
	public static void setup(Context context, XMLRPCConnection connection) {
		mFolderManager = new XMLRPCThumbFolderManager(connection);
		mContext = context;
	}

	/**
	 * Sets up this class (initializes context)
	 * 
	 * @param context
	 */
	public static void setup(Context context) {
		mContext = context;
	}
}