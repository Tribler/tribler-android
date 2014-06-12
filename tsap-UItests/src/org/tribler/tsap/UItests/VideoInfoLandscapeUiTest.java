package org.tribler.tsap.UItests;

import android.os.RemoteException;

import com.android.uiautomator.core.UiObjectNotFoundException;

/**
 * UI test cases for the video info screen in landscape mode
 * 
 * @author Niels Spruit
 */
public class VideoInfoLandscapeUiTest extends VideoInfoUiTest {

	/**
	 * Launches the TSAP app on the device and sets the orientation to Right
	 * 
	 * @throws UiObjectNotFoundException
	 * @throws RemoteException
	 */
	@Override
	protected void startTSAP() throws UiObjectNotFoundException,
			RemoteException {
		super.startTSAP();
		getUiDevice().setOrientationRight();
	}

	/**
	 * This test is actually the same as the one in {@link VideoInfoUiTest}, but
	 * in landscape mode it won't work somehow. It seems the teardown method is
	 * not called correctly, which in turn seems to be a bug in the test runner.
	 * Therefore this overriden method simply opens the video info screen in
	 * landscape mode.
	 * 
	 * @throws RemoteException
	 * @throws UiObjectNotFoundException
	 */
	@Override
	public void testVideoInfoPlayButtonClick() throws RemoteException,
			UiObjectNotFoundException {
		openVideoInfoScreen();
	}
}
