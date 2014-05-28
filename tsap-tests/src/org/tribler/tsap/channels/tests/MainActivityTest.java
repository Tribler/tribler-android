package org.tribler.tsap.channels.tests;

import org.tribler.tsap.MainActivity;
import org.tribler.tsap.R;

import android.app.ActionBar;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.FrameLayout;

/**
 * Functional tests for the MainActivity class
 * 
 * @author Niels Spruit
 */
public class MainActivityTest extends
		ActivityInstrumentationTestCase2<MainActivity> {

	private MainActivity mMainActivity;

	public MainActivityTest() {
		super(MainActivity.class);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		mMainActivity = getActivity();
	}

	/**
	 * Tests whether the test fixture is set up correctly
	 */
	public void testPreconditions() {
		assertNotNull("mMainActivity is null", mMainActivity);
	}

	/**
	 * Tests whether the activity's layout contains the fragment container
	 */
	public void testContainerExists() {
		final View container = mMainActivity.findViewById(R.id.container);
		assertNotNull("Fragment container is null", container);
		assertEquals("Container isn't a FrameLayout", FrameLayout.class,
				container.getClass());
	}

	/**
	 * Tests whether the title of the activity is correct
	 */
	public void testActivityTitleCorrect() {
		final CharSequence expected = mMainActivity
				.getString(R.string.app_name);
		final CharSequence actual = mMainActivity.getTitle();
		assertEquals("Activity title is not correct", expected, actual);
	}

	/**
	 * Test whether the action bar is correctly shown in the main activity
	 */
	public void testActionBar() {
		final ActionBar actionBar = mMainActivity.getActionBar();
		assertTrue("ActionBar is not shown", actionBar.isShowing());
		assertEquals("Navigation mode of action bar incorrect",
				ActionBar.NAVIGATION_MODE_STANDARD,
				actionBar.getNavigationMode());
		assertEquals("ActionBar title is incorrect",
				mMainActivity.getString(R.string.app_name),
				actionBar.getTitle());
	}
}
