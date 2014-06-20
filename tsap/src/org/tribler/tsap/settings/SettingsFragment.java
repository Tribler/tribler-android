package org.tribler.tsap.settings;

import java.net.MalformedURLException;
import java.net.URL;

import org.tribler.tsap.R;
import org.tribler.tsap.thumbgrid.XMLRPCTorrentManager;

import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.util.Log;

public class SettingsFragment extends PreferenceFragment implements
		OnSharedPreferenceChangeListener {
	
	XMLRPCSettingsManager mSettingsManager;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Load the preferences from an XML resource
		addPreferencesFromResource(R.xml.preferences);
		try {
			mSettingsManager = new XMLRPCSettingsManager(new URL("http://127.0.0.1:8000/tribler"));
		} catch (MalformedURLException e) {
			Log.e("SettingsFragment", "URL was malformed.\n" + e.getStackTrace());
		}

	}

	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
			String key) {
		if (key.equals("pref_familyFilter"))
		{
			boolean familyFilterOn = Settings.getFamilyFilterOn();
			mSettingsManager.setFamilyFilter(familyFilterOn);
			Log.i("SettingsFragment", "family filter made: " + familyFilterOn);
		}
	}
	
	@Override
	public void onResume() {
	    super.onResume();
	    getPreferenceScreen().getSharedPreferences()
	            .registerOnSharedPreferenceChangeListener(this);
	}

	@Override
	public void onPause() {
	    super.onPause();
	    getPreferenceScreen().getSharedPreferences()
	            .unregisterOnSharedPreferenceChangeListener(this);
	}
}
