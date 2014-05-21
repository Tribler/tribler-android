package org.tribler.tsap.tests;

import org.tribler.tsap.MainActivity;
import org.tribler.tsap.ThumbGridFragment;

import android.test.ActivityInstrumentationTestCase2;

/**
 * Functional test cases for the thumb grid fragment
 * @author Niels Spruit
 */
public class ThumbGridFragmentTest extends ActivityInstrumentationTestCase2<MainActivity> {
	
	private ThumbGridFragment mThumbGridFrag;
	
	public ThumbGridFragmentTest()
	{
		super(MainActivity.class);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		MainActivity mMainActivity = getActivity();
		mThumbGridFrag = mMainActivity.getThumbGridFragment();
	}
	
	/**
	 * Tests whether the test fixture is set up correctly
	 */
	public void testPreconditions() {
		assertNotNull("mThumbGridFrag is null", mThumbGridFrag);
	}
}
