package org.tribler.tsap;

import java.util.Observable;
import java.util.Observer;

import org.renpy.android.PythonService;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;

public class OnQuitDialog extends AlertDialog implements OnClickListener,
		Observer {
	private Context mContext;

	public OnQuitDialog(Context context) {
		super(context);
		mContext = context;
		setTitle("Quit");
		setMessage("Are you sure you want to close this app including the Tribler service?");
		setButton(BUTTON_POSITIVE, "Yes", this);
		setButton(BUTTON_NEGATIVE, "No", this);
	}

	@Override
	public void onClick(DialogInterface arg0, int buttonClicked) {
		if (buttonClicked == BUTTON_POSITIVE) {
			// perform XML-RPC call to shutdown tribler (should wait 5 seconds
			// before notifying this dialog)
			new AlertDialog.Builder(mContext)
					.setMessage("Shutting down Tribler...")
					.setCancelable(false).setTitle("Quit").show();
		}
	}

	// gets called when xml-rpc shutdown call returns, shuts down the app
	@Override
	public void update(Observable observable, Object data) {
		Intent startMain = new Intent(Intent.ACTION_MAIN);
		startMain.addCategory(Intent.CATEGORY_HOME);
		startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		mContext.startActivity(startMain);
		PythonService.stop();
		((MainActivity) mContext).finish();
	}

}
