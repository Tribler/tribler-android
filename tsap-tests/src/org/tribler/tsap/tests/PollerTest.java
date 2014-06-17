//package org.tribler.tsap.tests;
//
//import java.util.Observable;
//import java.util.Observer;
//
//import org.tribler.tsap.Poller;
//
//import junit.framework.TestCase;
//
//public class PollerTest extends TestCase implements Observer {
//
//	Poller poller;
//	int updateCalled;
//	long startTime, stopTime;
//	final static int POLLING_PERIOD = 100;
//
//	@Override
//	protected void setUp() throws Exception {
//		poller = new Poller(this, POLLING_PERIOD);
//		updateCalled = 0;
//		poller.start();
//		startTime = System.currentTimeMillis();
//		stopTime = startTime;
//	}
//	
//	public void pollTest(int pollAmount)
//	{
//		while (updateCalled < pollAmount && System.currentTimeMillis() < startTime + (pollAmount+1)*POLLING_PERIOD)
//		{ }
//		poller.pause();
//		assertTrue("Polling time for "+pollAmount+" polls is too small.", stopTime < startTime + pollAmount*POLLING_PERIOD);
//		assertEquals("Amount of polls received is not what was expected.", pollAmount, updateCalled);
//	}
//	
//	public void testOnePoll()
//	{
//		pollTest(1);
//	}
//	public void testFivePolls()
//	{
//		pollTest(5);
//	}
//	
//	public void testSlowPause()
//	{
//		pollTest(4);
//		while (System.currentTimeMillis() < startTime + (10)*POLLING_PERIOD);
//		assertEquals("Amount of polls received gets bigger after pausing.", 4, updateCalled);
//	}
//	public void testDirectPause()
//	{
//		poller.pause();
//		while (System.currentTimeMillis() < startTime + (10)*POLLING_PERIOD);
//		assertEquals("Amount of polls received gets bigger after pausing.", 4, updateCalled);
//	}
//
//	@Override
//	public void update(Observable observable, Object data) {
//		updateCalled++;
//		stopTime = System.currentTimeMillis();
//	}
//}
