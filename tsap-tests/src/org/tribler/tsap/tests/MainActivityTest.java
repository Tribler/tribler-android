package org.tribler.tsap.tests;

import org.tribler.tsap.MainActivity;

import android.test.ActivityInstrumentationTestCase2;

public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity> {

	private MainActivity mMainActivity;
	
	public MainActivityTest()
	{
		super(MainActivity.class);
	}
	
	@Override
	protected void setUp() throws Exception
	{
		super.setUp();
		mMainActivity = getActivity();
	}
}
