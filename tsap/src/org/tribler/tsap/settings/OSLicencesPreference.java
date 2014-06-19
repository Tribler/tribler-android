package org.tribler.tsap.settings;

import org.tribler.tsap.R;

import android.content.Context;
import android.preference.DialogPreference;
import android.util.AttributeSet;

public class OSLicencesPreference extends DialogPreference {

	public OSLicencesPreference(Context context, AttributeSet attrs) {
		super(context, attrs);

		setDialogLayoutResource(R.layout.os_licences_dialog);
		setDialogTitle(R.string.pref_os_licences);
		setPositiveButtonText(android.R.string.ok);
		setNegativeButtonText(android.R.string.cancel);

		setDialogIcon(null);
	}
}