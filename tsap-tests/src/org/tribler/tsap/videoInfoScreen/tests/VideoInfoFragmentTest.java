package org.tribler.tsap.videoInfoScreen.tests;

import org.tribler.tsap.MainActivity;
import org.tribler.tsap.R;
import org.tribler.tsap.thumbgrid.TORRENT_HEALTH;
import org.tribler.tsap.thumbgrid.ThumbItem;
import org.tribler.tsap.videoInfoScreen.VideoInfoFragment;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.test.ActivityInstrumentationTestCase2;

/**
 * Functional test cases for the video info fragment
 * 
 * @author Niels Spruit
 */
public class VideoInfoFragmentTest extends
		ActivityInstrumentationTestCase2<MainActivity> {

	private VideoInfoFragment mVideoInfoFrag;

	public VideoInfoFragmentTest() {
		super(MainActivity.class);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		mVideoInfoFrag = new VideoInfoFragment();
		Bundle args = new Bundle();
		ThumbItem item = new ThumbItem("Test torrent", R.drawable.dracula, TORRENT_HEALTH.UNKNOWN, 555, "infoHash");
		args.putSerializable("thumbData", item);
		mVideoInfoFrag.setArguments(args);

		FragmentTransaction transaction = getActivity().getFragmentManager()
				.beginTransaction();
		transaction.replace(R.id.container, mVideoInfoFrag);
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
