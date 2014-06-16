package org.tribler.tsap;

import android.os.Looper;

/**
 * A class for checking if a function is called from the main thread. This
 * class is originally from:
 * http://www.piwai.info/android-adapter-good-practices/
 * 
 * @author piwai.info
 * 
 */
public class ThreadPreconditions {
	/**
	 * This function will throw an exeption if it is called from a thread that
	 * is not the main thread.
	 */
	public static void checkOnMainThread() {
		if (BuildConfig.DEBUG) {
			if (Thread.currentThread() != Looper.getMainLooper().getThread()) {
				throw new IllegalStateException(
						"This method should be called from the Main Thread");
			}
		}
	}
}
