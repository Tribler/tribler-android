package org.tribler.tsap.streaming;

import org.tribler.tsap.R;
import org.tribler.tsap.util.Poller;

import android.annotation.SuppressLint;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.os.Bundle;

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

	/**
	 * @param mPoller
	 *            Poller that makes sure the dialog is updated
	 * @param mButton
	 *            The button that was clicked to open this dialog
	 */
	public VODDialogFragment(Poller mPoller) {
		this.mPoller = mPoller;
	}

	/**
	 * Creates the actual dialog, now in progress dialog.
	 */
	@Override
	public ProgressDialog onCreateDialog(Bundle savedInstanceState) {
		
		//create progressDialog:
		final ProgressDialog progressDialog = new ProgressDialog(getActivity());
		progressDialog.setMax(100);
		progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		progressDialog.setProgress(0);
		progressDialog.setProgressNumberFormat(null);
		progressDialog.setTitle(getString(R.string.vod_dialog_title));
		progressDialog.setMessage(getString(R.string.vod_dialog_initial_message));
		progressDialog.show();
		// Create the ProgressDialog object and return it
		return progressDialog;
	}

	/**
	 * When the dialog is destroyed, this method stops the poller and enables
	 * the button again
	 */
	@Override
	public void onDestroyView() {
		super.onDestroyView();
		mPoller.stop();
	}

}