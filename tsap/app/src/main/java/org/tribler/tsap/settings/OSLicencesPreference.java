package org.tribler.tsap.settings;

import org.tribler.tsap.R;

import android.content.Context;
import android.preference.DialogPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.util.AttributeSet;
import android.webkit.WebView;

/**
 * Preference showing the licences of the used open source software
 * 
 * @author Niels Spruit
 * 
 */
public class OSLicencesPreference extends DialogPreference implements
		OnPreferenceClickListener {

	/**
	 * Initializes the layout of the dialog and its buttons
	 * 
	 * @param context
	 * @param attrs
	 */
	public OSLicencesPreference(Context context, AttributeSet attrs) {
		super(context, attrs);
		setDialogLayoutResource(R.layout.os_licences_dialog);
		setDialogTitle(R.string.pref_os_licences);
		setPositiveButtonText(android.R.string.ok);
		setNegativeButtonText(null);
		setDialogIcon(null);

		setOnPreferenceClickListener(this);
	}

	/**
	 * Loads the licences (stored in a html file) into the WebView of the dialog
	 */
	@Override
	public boolean onPreferenceClick(Preference arg0) {
		WebView webView = (WebView) getDialog().findViewById(
				R.id.os_licences_webview);
		webView.loadUrl("file:///android_asset/os_licences.html");
		return false;
	}
}