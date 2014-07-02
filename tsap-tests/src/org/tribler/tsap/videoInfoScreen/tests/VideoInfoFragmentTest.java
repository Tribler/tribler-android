package org.tribler.tsap.videoInfoScreen.tests;

import org.tribler.tsap.MainActivity;
import org.tribler.tsap.R;
import org.tribler.tsap.Torrent;
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

	private static final String infohash = "infohash";
	private static final String title = "Sintel 2010";
	private static final long size = 349834;
	private static final String category = "other";
	private static final int leechers = 434;
	private static final int seeders = 434;

	public VideoInfoFragmentTest() {
		super(MainActivity.class);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		mVideoInfoFrag = new VideoInfoFragment();
		Bundle args = new Bundle();
		Torrent item = new Torrent(title, infohash, size, seeders, leechers,
				null, category);
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
