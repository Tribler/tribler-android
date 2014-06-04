package org.tribler.tsap;

import java.util.Observable;
import java.util.Observer;

import android.os.Handler;
import android.util.Log;

/**
 * Polling class that calls the update function of it's observer with a constant
 * interval
 * 
 * @author Dirk Schut
 */
public class Poller extends Observable implements Runnable {
	Observer mObserver;
	private Handler mPollingHandler = new Handler();
	public final static long POLLING_PERIOD = 500;
	boolean shouldRun = false;

	/**
	 * Constructor: sets up the observer
	 * 
	 * @param observer
	 *            The observer that will be updated every interval
	 */
	public Poller(Observer observer) {
		mObserver = observer;
		this.addObserver(observer);
	}

	/**
	 * Calls the observers update function and makes sure run will be called
	 * again in POLLING_PERIOD microseconds. This function should not be called
	 * from outside the class.
	 */
	@Override
	public void run() {
		setChanged();
		notifyObservers();
		if (shouldRun)
			mPollingHandler.postDelayed(this, POLLING_PERIOD);
	}

	/**
	 * Pauses the polling. After calling this function one more poll request
	 * will be received.
	 */
	public void pause() {
		shouldRun = false;
	}

	/**
	 * Starts the polling. If the Poller is already polling, nothing changes.
	 */
	public void start() {
		if (!shouldRun) {
			shouldRun = true;
			run();
		}
	}
}