package org.tribler.tsap.tests;

import org.tribler.tsap.MainActivity;
import org.tribler.tsap.util.MainThreadPoller;
import org.tribler.tsap.util.Poller;

import android.os.Looper;
import android.test.ActivityInstrumentationTestCase2;

public class MainThreadPollerTest extends
ActivityInstrumentationTestCase2<MainActivity> implements Poller.IPollListener{
	
	MainThreadPoller poller;
	boolean polled, polledFromMainThread;
	final static long POLLING_PERIOD = 50;

	public MainThreadPollerTest() {
		super(MainActivity.class);
	}
	
	public void testMainThread () {
		poller = new MainThreadPoller(this, POLLING_PERIOD, getActivity());
		polled = false;
		polledFromMainThread = false;
		poller.start();
		long startTime = System.currentTimeMillis();
		while (System.currentTimeMillis() < startTime + 3*POLLING_PERIOD)
			{ }
		poller.stop();
		assertTrue("Poller did not poll.", polled);
		assertTrue("Poller did not poll from the main thread.", polledFromMainThread);
	}

	@Override
	public void onPoll() {
		polled = true;
		polledFromMainThread = Looper.myLooper() == Looper.getMainLooper();
	}
	
}