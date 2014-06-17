package org.tribler.tsap;

import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import android.os.Handler;

/**
 * Polling class that calls the update function of it's observer with a constant
 * interval
 * 
 * @author Dirk Schut
 */
public class Poller extends Observable implements Runnable {
	Observer mObserver;
	private Handler mPollingHandler = new Handler();
	public int pollingPeriod;
	boolean shouldRun = false, hasBlocked = true;
	Lock lock = new ReentrantLock();

	/**
	 * Constructor: sets up the observer
	 * 
	 * @param observer
	 *            The observer that will be updated every interval
	 */
	public Poller(Observer observer, int pollingPeriod) {
		mObserver = observer;
		this.addObserver(observer);
		this.pollingPeriod = pollingPeriod;
	}

	/**
	 * Calls the observers update function and makes sure run will be called
	 * again in POLLING_PERIOD microseconds. This function should not be called
	 * from outside the class.
	 */
	@Override
	public void run() {
		lock.lock();
		if (shouldRun)
		{
			setChanged();
			notifyObservers();
			mPollingHandler.postDelayed(this, pollingPeriod);
		}
		else
		{
			hasBlocked = true;
		}
		lock.unlock();
	}

	/**
	 * Pauses the polling.
	 */
	public void pause() {
		lock.lock();
		shouldRun = false;
		hasBlocked = false;
		lock.unlock();
	}

	/**
	 * Starts the polling. If the Poller is already polling, nothing changes.
	 */
	public void start() {
		lock.lock();
		if (!shouldRun) {
			shouldRun = true;
			if(hasBlocked)
			{
				mPollingHandler.postDelayed(this, pollingPeriod);
			}
		}
		lock.unlock();
	}
}