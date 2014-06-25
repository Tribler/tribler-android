package org.tribler.tsap.settings;

import org.tribler.tsap.R;
import org.tribler.tsap.XMLRPC.XMLRPCConnection;

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
		mSettingsManager = new XMLRPCSettingsManager(XMLRPCConnection.getInstance());
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
