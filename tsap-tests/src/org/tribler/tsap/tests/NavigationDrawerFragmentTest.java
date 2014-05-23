package org.tribler.tsap.tests;

import org.tribler.tsap.MainActivity;
import org.tribler.tsap.NavigationDrawerFragment;
import org.tribler.tsap.R;

import android.test.ActivityInstrumentationTestCase2;

/**
 * Functional test cases of the Navigation Drawer fragment
 * @author Niels Spruit
 */
public class NavigationDrawerFragmentTest extends
		ActivityInstrumentationTestCase2<MainActivity> {
	
	private NavigationDrawerFragment mNavDrawFrag;
	
	public NavigationDrawerFragmentTest() {
		super(MainActivity.class);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		MainActivity mMainActivity = getActivity();
		mNavDrawFrag = (NavigationDrawerFragment) mMainActivity.getFragmentManager().findFragmentById(R.id.navigation_drawer);
	}
	
	/**
	 * Tests whether the test fixture is set up correctly
	 */
	public void testPreconditions() {
		assertNotNull("mNavDrawFrag is null", mNavDrawFrag);
	}

	/**
	 * Test whether the navigation drawer is closed
	 */
	public void testDrawerClosed()
	{
		assertFalse("Navigation drawer is open",mNavDrawFrag.isDrawerOpen());
	}
}
