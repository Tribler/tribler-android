package org.tribler.tsap.settings;

import android.content.Context;
import android.os.Bundle;
import android.preference.DialogPreference;
import android.util.AttributeSet;

public class AboutPreference extends DialogPreference {

	public AboutPreference(Context context, AttributeSet attrs) {
		super(context, attrs);
//		setDialogLayoutResource(R.layout.pref_about);
////		setDialogTitle(R.string.pref_os_licences);
//		setPositiveButtonText(android.R.string.ok);
//		setNegativeButtonText(android.R.string.cancel);
//
//		setDialogIcon(null);
		
	}
	
	@Override
	public void showDialog(Bundle state)
	{
//		super.showDialog(state);
		AboutDialog dialog = new AboutDialog(getContext());
		dialog.show();
	}
	
//	@Override
//	public View onCreateDialogView()
//	{
//		setDia
//		return null;
//	}
}
