package org.tribler.tsap.util;

import java.util.TimerTask;

import android.app.Activity;
public class MainThreadPoller extends Poller {
	Activity mActivity;
	
	public MainThreadPoller(IPollListener listener, long interval, Activity activity) {
		super(listener, interval);
		mActivity = activity;
	}
	
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