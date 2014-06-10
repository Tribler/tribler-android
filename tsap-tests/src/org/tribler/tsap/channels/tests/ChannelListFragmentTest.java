package org.tribler.tsap.channels.tests;

import org.tribler.tsap.channels.ChannelListFragment;
import org.tribler.tsap.MainActivity;

import android.test.ActivityInstrumentationTestCase2;

/**
 * Functional test cases for the Channel list fragment
 * 
 * @author Niels Spruit
 */
public class ChannelListFragmentTest extends
		ActivityInstrumentationTestCase2<MainActivity> {

	private ChannelListFragment mChannelListFrag;

	public ChannelListFragmentTest() {
		super(MainActivity.class);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		MainActivity mMainActivity = getActivity();
		mMainActivity.onNavigationDrawerItemSelected(1);
		mChannelListFrag = mMainActivity.getChannelListFragment();
	}

	/**
	 * Tests whether the test fixture is set up correctly
	 */
	public void testPreconditions() {
		assertNotNull("mChannelListFrag is null", mChannelListFrag);
	}
}
