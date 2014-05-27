package org.tribler.tsap.UItests;

import android.os.RemoteException;

import com.android.uiautomator.core.UiObjectNotFoundException;

/**
 * UI test cases for the navigation drawer in landscape mode
 * 
 * @author Niels Spruit
 */
public class NavigationDrawerLandscapeUiTest extends NavigationDrawerUiTest {

	/**
	 * Launches the TSAP app on the device and sets the orientation to Right
	 * 
	 * @throws UiObjectNotFoundException
	 */
	@Override
	protected void startTSAP() throws UiObjectNotFoundException {
		super.startTSAP();
		try {
			getUiDevice().setOrientationRight();
		} catch (RemoteException e) {
			throw new UiObjectNotFoundException(e.getMessage());
		}
	}
}
