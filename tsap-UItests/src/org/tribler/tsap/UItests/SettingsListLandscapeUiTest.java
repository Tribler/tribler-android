package org.tribler.tsap.UItests;

import android.os.RemoteException;

import com.android.uiautomator.core.UiObjectNotFoundException;

/**
 * UI test cases for the settings list fragment in landscape mode
 * 
 * @author Niels Spruit
 */
public class SettingsListLandscapeUiTest extends SettingsListUiTest {

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

}
