package org.tribler.tsap;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

@SuppressLint("ValidFragment") public class VODDialogFragment extends DialogFragment {

	private Poller mPoller;

	public VODDialogFragment(Poller mPoller) {
		this.mPoller = mPoller;
	}
	
	public VODDialogFragment(){
		super();
	}
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// Use the Builder class for convenient dialog construction
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setMessage("Please wait before the video starts playing...")
				.setNegativeButton(R.string.cancel,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								// User cancelled the dialog
								mPoller.pause();
							}
						});
		
		// Create the AlertDialog object and return it
		return builder.create();
	}

}
