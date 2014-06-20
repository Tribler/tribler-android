package org.tribler.tsap.settings;

import android.content.Context;
import android.os.Bundle;
import android.preference.DialogPreference;
import android.util.AttributeSet;

public class AboutPreference extends DialogPreference {

	public AboutPreference(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public void showDialog(Bundle state) {
		AboutDialog dialog = new AboutDialog(getContext());
		dialog.show();
	}
}
