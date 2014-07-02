package org.tribler.tsap.streaming;

import org.tribler.tsap.R;
import org.tribler.tsap.util.Poller;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Button;

/**
 * Class showing a dialog (when the 'Play video' button is pressed) that is used
 * to inform the user about the status of a download and when it can be streamed
 * 
 * @author Niels Spruit
 * 
 */
@SuppressLint("ValidFragment")
public class VODDialogFragment extends DialogFragment {

	private Poller mPoller;
	private Button mButton;

	/**
	 * @param mPoller
	 *            Poller that makes sure the dialog is updated
	 * @param mButton
	 *            The button that was clicked to open this dialog
	 */
	public VODDialogFragment(Poller mPoller, Button mButton) {
		this.mPoller = mPoller;
		this.mButton = mButton;
	}

	/**
	 * Creates the actual dialog
	 */
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

	/**
	 * When the dialog is destroyed, this method stops the poller and enables
	 * the button again
	 */
	@Override
	public void onDestroyView() {
		super.onDestroyView();
		mPoller.stop();
		mButton.setEnabled(true);
	}

}