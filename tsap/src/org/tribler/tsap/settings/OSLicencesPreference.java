package org.tribler.tsap.settings;

import org.tribler.tsap.MainActivity;
import org.tribler.tsap.R;

import android.content.Context;
import android.preference.DialogPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.util.AttributeSet;
import android.webkit.WebView;

public class OSLicencesPreference extends DialogPreference implements OnPreferenceClickListener {

	public OSLicencesPreference(Context context, AttributeSet attrs) {
		super(context, attrs);
		setDialogLayoutResource(R.layout.os_licences_dialog);
		setDialogTitle(R.string.pref_os_licences);
		setPositiveButtonText(android.R.string.ok);
		setNegativeButtonText(null);
		setDialogIcon(null);
		
		setOnPreferenceClickListener(this);
	}

	@Override
	public boolean onPreferenceClick(Preference arg0) {
		 WebView webView = (WebView) getDialog().findViewById(R.id.os_licences_webview); 
		 webView.loadUrl("file:///android_asset/os_licences.html");
		return false;
	}
}