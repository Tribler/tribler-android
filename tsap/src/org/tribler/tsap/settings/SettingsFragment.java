package org.tribler.tsap.settings;

import org.tribler.tsap.R;
import org.tribler.tsap.XMLRPC.XMLRPCConnection;

import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.PreferenceFragment;

/**
 * Fragment showing the app's settings
 * 
 * @author Niels Spruit
 * 
 */
public class SettingsFragment extends PreferenceFragment implements
		OnSharedPreferenceChangeListener {

	private XMLRPCSettingsManager mSettingsManager;

	/**
	 * Initializes the view
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Load the preferences from an XML resource
		addPreferencesFromResource(R.xml.preferences);
		mSettingsManager = new XMLRPCSettingsManager(
				XMLRPCConnection.getInstance(), getActivity());
	}

	/**
	 * Is called when the preference specified by key has been changed and
	 * passes the settings new value to Tribler
	 */
	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
			String key) {
		if (key.equals("pref_familyFilter")) {
			mSettingsManager.setFamilyFilter(Settings.getFamilyFilterOn());
		} else if (key.equals("pref_maxDownloadRate")) {
			mSettingsManager.setMaxDownloadSpeed(Settings.getMaxDownloadRate());
		} else if (key.equals("pref_maxUploadRate")) {
			mSettingsManager.setMaxUploadSpeed(Settings.getMaxUploadRate());
		}
	}

	/**
	 * Registers the preference change listener
	 */
	@Override
	public void onResume() {
		super.onResume();
		getPreferenceScreen().getSharedPreferences()
				.registerOnSharedPreferenceChangeListener(this);
	}

	/**
	 * Unregisters the preference change listener
	 */
	@Override
	public void onPause() {
		super.onPause();
		getPreferenceScreen().getSharedPreferences()
				.unregisterOnSharedPreferenceChangeListener(this);
	}
}
