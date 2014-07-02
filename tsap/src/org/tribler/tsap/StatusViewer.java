package org.tribler.tsap;

import android.app.Activity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

public class StatusViewer {
	ProgressBar mProgressBar;
	TextView mMessage;
	Activity mActivity;
	boolean mEnabled = true;

	public StatusViewer(Activity activity) {
		mActivity = activity;
	}

	public void updateViews(ProgressBar progressBar, TextView message) {
		mProgressBar = progressBar;
		mMessage = message;
		if (!mEnabled) {
			disable();
		}
	}

	public void setMessage(final int messageId,
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
				}
			}
		});
	}

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

	public void enable() {
		mActivity.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				mMessage.setVisibility(View.VISIBLE);
				mProgressBar.setVisibility(View.INVISIBLE);
				mEnabled = true;
			}
		});
	}

}