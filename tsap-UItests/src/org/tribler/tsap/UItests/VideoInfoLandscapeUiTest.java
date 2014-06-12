package org.tribler.tsap.UItests;

import android.os.RemoteException;

import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.core.UiSelector;

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
	 * Tests whether the 'Play video' Button behaves correctly when it is
	 * pressed (=opens VLC). This test is actually the same as the one in
	 * {@link VideoInfoUiTest}, but without override this test somehow doesn't
	 * work
	 * 
	 * @throws RemoteException
	 * @throws UiObjectNotFoundException
	 */
	@Override
	public void testVideoInfoPlayButtonClick() throws RemoteException,
			UiObjectNotFoundException {
		super.testVideoInfoPlayButtonClick();
	}
}
