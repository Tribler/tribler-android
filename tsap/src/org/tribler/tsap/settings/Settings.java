package org.tribler.tsap.settings;

import java.net.URL;

public class Settings {
	public enum TorrentType {
		VIDEO, ALL
	};

	private static boolean mFamilyFilterOn;
	private static String mThumbFolder = null;
	private static XMLRPCSettingsManager mSettingsManager;

	public static String getTumbFolder() {
		return mThumbFolder;
	}
	public static void XMLRPCSetThumbFolder(String thumbFolder) {
		mThumbFolder = thumbFolder;
	}

	public static void setFamilyFilterOn(boolean familyFilterOn) {
		mFamilyFilterOn = familyFilterOn;
		mSettingsManager.setFamilyFilter(familyFilterOn);
	}

	public static boolean getFamilyFilterOn() {
		return mFamilyFilterOn;
	}

	public static void setXMLRPCServerLocation(URL url) {
		mSettingsManager = new XMLRPCSettingsManager(url);
	}

	public static void setInitialValues() {
		setFamilyFilterOn(true);
		mSettingsManager.getThumbFolder();
	}
}