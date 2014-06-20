package org.tribler.tsap;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;

public class OnQuitDialog extends AlertDialog implements OnClickListener {

	public OnQuitDialog(Context context) {
		super(context);
		setMessage("Are you sure you want to close this app including the Tribler service?");
		setButton(BUTTON_POSITIVE, "Yes", this);
	}

	@Override
	public void onClick(DialogInterface arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}

}
