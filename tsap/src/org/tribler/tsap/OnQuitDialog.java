package org.tribler.tsap;

import org.renpy.android.PythonService;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;

public class OnQuitDialog extends AlertDialog implements OnClickListener {
	private Context mContext;

	public OnQuitDialog(Context context) {
		super(context);
		mContext = context;
		setMessage("Are you sure you want to close this app including the Tribler service?");
		setButton(BUTTON_POSITIVE, "Yes", this);
		setButton(BUTTON_NEGATIVE, "No", this);
	}

	@Override
	public void onClick(DialogInterface arg0, int buttonClicked) {
		if (buttonClicked == BUTTON_POSITIVE) {
			Intent startMain = new Intent(Intent.ACTION_MAIN);
			startMain.addCategory(Intent.CATEGORY_HOME);
			startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			mContext.startActivity(startMain);
			PythonService.stop();
			((MainActivity) mContext).finish();
		}
	}

}
