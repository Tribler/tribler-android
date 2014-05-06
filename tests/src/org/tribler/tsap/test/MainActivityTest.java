package org.tribler.tsap.test;

import android.test.ActivityInstrumentationTestCase2;

import org.tribler.tsap.MainActivity;

public class MainActivityTest extends
		ActivityInstrumentationTestCase2<MainActivity> 
{
	private MainActivity mActivity;
	
	public MainActivityTest()
	{
		super(MainActivity.class);
	}
	
	@Override
	protected void setUp() throws Exception
	{
		super.setUp();
		
		mActivity = getActivity();
	}
	
	public void testPreconditions()
	{
		assertNotNull("mActivity is null", mActivity);
	} 

}
