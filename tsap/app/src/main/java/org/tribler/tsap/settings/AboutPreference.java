package org.tribler.tsap.settings;

import android.content.Context;
import android.os.Bundle;
import android.preference.DialogPreference;
import android.util.AttributeSet;

/**
 * Preference that opens the AboutDialog when it is clicked
 * 
 * @author Niels Spruit
 * 
 */
public class AboutPreference extends DialogPreference {

	/**
	 * Initializes the dialog preference
	 * 
	 * @param context
	 * @param attrs
	 */
	public AboutPreference(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	/**
	 * Shows the actual AboutDialog
	 */
	@Override
	public void showDialog(Bundle state) {
		AboutDialog dialog = new AboutDialog(getContext());
		dialog.show();
	}
}
