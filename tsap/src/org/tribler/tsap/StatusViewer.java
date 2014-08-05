package org.tribler.tsap;

import android.app.Activity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * Class that puts status messages (including a progress spinner) on the screen
 * whenever the app is waiting for an event
 * 
 * @author Dirk Schut
 * 
 */
public class StatusViewer {
	private ProgressBar mProgressBar;
	private TextView mMessage;
	private Activity mActivity;
	private boolean mEnabled = true;

	/**
	 * @param activity
	 *            The activity to show messages in
	 */
	public StatusViewer(Activity activity) {
		mActivity = activity;
	}

	/**
	 * Updates the views of the progress bar and status text
	 * 
	 * @param progressBar
	 * @param message
	 */
	public void updateViews(ProgressBar progressBar, TextView message) {
		mProgressBar = progressBar;
		mMessage = message;
		if (!mEnabled) {
			disable();
		}
	}

	/**
	 * Sets the message of the message view
	 * 
	 * @param messageId
	 *            Resource containing the message
	 * @param appendString
	 *            sets any string behind the messagId if necessary
	 * @param progressBarVisibible
	 *            Indicates whether the progress bar should be visible
	 *            
	 */
	public void setMessage(final int messageId, final String appendString,
			final boolean progressBarVisibible) {
		mActivity.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				if (mEnabled) {
					if (progressBarVisibible)
						mProgressBar.setVisibility(View.VISIBLE);
					else
						mProgressBar.setVisibility(View.INVISIBLE);
					mMessage.setText(messageId);
					if(appendString != null) 
						mMessage.append(appendString);
				}
			}
		});
	}

	/**
	 * Disables the status viewer (removes views from the screen)
	 */
	public void disable() {
		mActivity.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				mProgressBar.setVisibility(View.GONE);
				mMessage.setVisibility(View.GONE);
				mEnabled = false;
			}
		});
	}

	/**
	 * Enables the status viewer (puts the views from the screen)
	 */
	public void enable() {
		mActivity.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				mMessage.setVisibility(View.VISIBLE);
				mMessage.setText(R.string.empty);
				mProgressBar.setVisibility(View.INVISIBLE);
				mEnabled = true;
			}
		});
	}

}