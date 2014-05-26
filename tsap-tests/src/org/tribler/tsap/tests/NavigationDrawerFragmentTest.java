package org.tribler.tsap.tests;

import org.tribler.tsap.MainActivity;
import org.tribler.tsap.NavigationDrawerFragment;
import org.tribler.tsap.R;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.widget.DrawerLayout;
import android.test.ActivityInstrumentationTestCase2;

/**
 * Functional test cases of the Navigation Drawer fragment
 * 
 * @author Niels Spruit
 */
public class NavigationDrawerFragmentTest extends
		ActivityInstrumentationTestCase2<MainActivity> {

	private NavigationDrawerFragment mNavDrawFrag;
	private MainActivity mMainActivity;

	public NavigationDrawerFragmentTest() {
		super(MainActivity.class);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		mMainActivity = getActivity();
		mNavDrawFrag = (NavigationDrawerFragment) mMainActivity
				.getFragmentManager().findFragmentById(R.id.navigation_drawer);
	}

	/**
	 * Tests whether the test fixture is set up correctly
	 */
	public void testPreconditions() {
		assertNotNull("mNavDrawFrag is null", mNavDrawFrag);
	}

	/**
	 * Test whether the navigation drawer is open the first time the app is
	 * started and that the user hasn't learned the navigation drawer yet
	 */
	public void testDrawerSharedPreferenceFirstStartup() {
		SharedPreferences sp = PreferenceManager
				.getDefaultSharedPreferences(mMainActivity);
		assertTrue("Navigation drawer is closed on first startup",
				mNavDrawFrag.isDrawerOpen());
		assertFalse("User cannot have learned the drawer on first startup",
				sp.getBoolean("navigation_drawer_learned", false));
	}
}
