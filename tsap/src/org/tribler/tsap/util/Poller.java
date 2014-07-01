package org.tribler.tsap.util;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Polling class that calls the onPoll function of it's listener with a constant
 * interval.
 * 
 * It's a wrapper class for java.util.Timer that calls back to an interface
 * instead of an abstract class. So you don't need multiple inheritance, which
 * Java doesn't have.
 * 
 * @author Dirk Schut
 */
public class Poller{
	protected IPollListener mListener;
	private Timer mTimer = null;
	private TimerTask mTask;
	private long mPollingInterval;

	/**
	 * Sets up the poller to poll from a new thread. If you want to poll from
	 * another thread (like the UI thread), use the other constructor.
	 * 
	 * @param listener
	 *            The listener whose onPoll function will be called each
	 *            interval
	 * @param interval
	 *            The interval between two onPoll calls
	 */
	public Poller(IPollListener listener, long interval) {
		mListener = listener;
		mPollingInterval = interval;
	}

	/**
	 * Pauses the polling.
	 */
	public synchronized void stop() {
		if(mTimer != null)
		{
			mTimer.cancel();
			mTimer = null;
		}
	}
	
	protected TimerTask MakeTimerTask() {
		return new TimerTask() {
			public void run() {
				mListener.onPoll();
			}
		};
	}

	/**
	 * Starts the polling. If the Poller is already polling, nothing changes.
	 */
	public synchronized void start() {
		if(mTimer == null)
		{
			mTimer = new Timer();
			mTask = MakeTimerTask();
			mTimer.scheduleAtFixedRate(mTask, 0, mPollingInterval);
		}
	}

	public interface IPollListener {
		public void onPoll();
	}
}