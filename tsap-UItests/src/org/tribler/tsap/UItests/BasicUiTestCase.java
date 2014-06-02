package org.tribler.tsap.UItests;

import android.os.RemoteException;

import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.core.UiScrollable;
import com.android.uiautomator.core.UiSelector;
import com.android.uiautomator.testrunner.UiAutomatorTestCase;

/**
 * Basic UI test case that launches the TSAP app. This class should be extended
 * for additional UI test cases. Each test case in the subclasses should call
 * the startTSAP method first to launch the app.
 * 
 * @author Niels Spruit
 */
public class BasicUiTestCase extends UiAutomatorTestCase {

	/**
	 * Test whether the startTSAP function starts the correct app (by checking
	 * its package name)
	 * 
	 * @throws UiObjectNotFoundException
	 * @throws RemoteException 
	 */
	public void testStartTSAPPackageName() throws UiObjectNotFoundException, RemoteException {
		startTSAP();

		// Validate that the package name is the expected one
		UiObject tsapValidation = new UiObject(
				new UiSelector().packageName("org.tribler.tsap"));
		assertTrue("Unable to find TSAP app", tsapValidation.exists());
	}

	/**
	 * Launches the TSAP app on the device
	 * 
	 * @throws UiObjectNotFoundException
	 * @throws RemoteException 
	 */
	protected void startTSAP() throws UiObjectNotFoundException, RemoteException {		
		// Simulate a short press on the HOME button.
		getUiDevice().pressHome();

		// We’re now in the home screen. Next, we want to simulate
		// a user bringing up the All Apps screen.
		// If you use the uiautomatorviewer tool to capture a snapshot
		// of the Home screen, notice that the All Apps button’s
		// content-description property has the value “Apps”. We can
		// use this property to create a UiSelector to find the button.
		UiObject allAppsButton = new UiObject(
				new UiSelector().description("Apps"));

		// Simulate a click to bring up the All Apps screen.
		allAppsButton.clickAndWaitForNewWindow();

		// In the All Apps screen, the TSAP app is located in
		// the Apps tab. To simulate the user bringing up the Apps tab,
		// we create a UiSelector to find a tab with the text
		// label “Apps”.
		UiObject appsTab = new UiObject(new UiSelector().text("Apps"));

		// Simulate a click to enter the Apps tab.
		appsTab.click();

		// Next, in the apps tabs, we can simulate a user swiping until
		// they come to the TSAP app icon. Since the container view
		// is scrollable, we can use a UiScrollable object.
		UiScrollable appViews = new UiScrollable(
				new UiSelector().scrollable(true));

		// Set the swiping mode to horizontal (the default is vertical)
		appViews.setAsHorizontalList();

		// Create a UiSelector to find the TSAP app and simulate
		// a user click to launch the app.
		UiObject tsapApp = appViews.getChild(new UiSelector().text("TSAP"));
		tsapApp.clickAndWaitForNewWindow();
		
		// Set the rotation to normal (=portrait mode)
		getUiDevice().setOrientationNatural();
	}
}
