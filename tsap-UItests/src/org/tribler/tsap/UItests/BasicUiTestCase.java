package org.tribler.tsap.UItests;

import android.os.RemoteException;

import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.core.UiSelector;
import com.android.uiautomator.testrunner.UiAutomatorTestCase;

/**
 * Basic UI test case that launches the Tribler Play app. This class should be extended
 * for additional UI test cases. Each test case in the subclasses should call
 * the startTSAP method first to launch the app.
 * 
 * @author Niels Spruit
 */
public class BasicUiTestCase extends UiAutomatorTestCase {

	/**
	 * Makes sure the Tribler Play app is launched on the device and the
	 * MainActivity is shown (in portrait mode)
	 * 
	 * @throws UiObjectNotFoundException
	 * @throws RemoteException
	 */
	protected void startTSAP() throws UiObjectNotFoundException,
			RemoteException {
		UiObject upButton = new UiObject(
				new UiSelector().description("Navigate up"));
		while (!upButton.exists()) {
		}

		// Validate that the package name is the expected one
		UiObject tsapValidation = new UiObject(
				new UiSelector().packageName("org.tribler.tsap"));
		assertTrue("Unable to find Tribler Play app", tsapValidation.exists());

		// Set the rotation to normal (=portrait mode)
		getUiDevice().setOrientationNatural();
	}
}
