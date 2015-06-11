package org.tribler.tsap.util;

import java.util.TimerTask;

import android.app.Activity;

/**
 * Poller that executes the run function on the main thread (= UI thread)
 * 
 * @author Dirk Schut
 * 
 */
public class MainThreadPoller extends Poller {
	private Activity mActivity;

	/**
	 * Initialized the poller
	 * 
	 * @param listener
	 * @param interval
	 * @param activity
	 */
	public MainThreadPoller(IPollListener listener, long interval,
			Activity activity) {
		super(listener, interval);
		mActivity = activity;
	}

	/**
	 * Create a TimerTask that runs the onPoll method of the listener on the UI
	 * thread
	 */
	@Override
	protected TimerTask MakeTimerTask() {
		final Runnable callOnPoll = new Runnable() {
			@Override
			public void run() {
				mListener.onPoll();
			}
		};
		return new TimerTask() {
			public void run() {
				mActivity.runOnUiThread(callOnPoll);
			}
		};
	}
}