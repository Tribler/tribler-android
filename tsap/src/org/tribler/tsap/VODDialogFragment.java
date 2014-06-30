package org.tribler.tsap;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Button;

@SuppressLint("ValidFragment") public class VODDialogFragment extends DialogFragment {

	private Poller mPoller;
	private Button mButton;

	public VODDialogFragment(Poller mPoller,Button mButton) {
		this.mPoller = mPoller;
		this.mButton = mButton;
	}
	
	public VODDialogFragment(){
		super();
	}
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// Use the Builder class for convenient dialog construction
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setMessage(getString(R.string.vod_dialog_initial_message))
				.setNegativeButton(R.string.cancel,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								// User cancelled the dialog
							}
						});
		
		// Create the AlertDialog object and return it
		return builder.create();
	}
	
	@Override
	public void onDestroyView(){
		super.onDestroyView();
		mPoller.stop();
		mButton.setEnabled(true);
	}

}