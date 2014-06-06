package org.tribler.tsap.channels.tests;

import org.tribler.tsap.MainActivity;
import org.tribler.tsap.R;
import org.tribler.tsap.videoInfoScreen.VideoInfoFragment;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.test.ActivityInstrumentationTestCase2;

/**
 * Functional test cases for the video info fragment
 * @author Niels Spruit
 */
public class VideoInfoFragmentTest extends ActivityInstrumentationTestCase2<MainActivity> {
	
	private VideoInfoFragment mVideoInfoFrag;
	
	public VideoInfoFragmentTest()
	{
		super(MainActivity.class);
	}
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		mVideoInfoFrag = new VideoInfoFragment();
		Bundle args = new Bundle();
		args.putInt("torrentID", 0);
		mVideoInfoFrag.setArguments(args);
		
		FragmentTransaction transaction = getActivity().getFragmentManager().beginTransaction();
		transaction.replace(R.id.container,mVideoInfoFrag);
		transaction.addToBackStack(null);
		transaction.commit();
	}
	
	/**
	 * Tests whether the test fixture is set up correctly
	 */
	public void testPreconditions() {
		assertNotNull("mVideoInfoFrag is null", mVideoInfoFrag);
	}
}
