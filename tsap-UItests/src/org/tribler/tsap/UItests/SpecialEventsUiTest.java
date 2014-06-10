package org.tribler.tsap.UItests;

import android.os.RemoteException;

import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.core.UiScrollable;
import com.android.uiautomator.core.UiSelector;

/**
 * UI tests for special events like pressing the home button
 * 
 * @author Niels Spruit
 * 
 */
public class SpecialEventsUiTest extends BasicUiTestCase {

	/**
	 * Tests whether TSAP is closed and the launcher is opened when the back
	 * button is pressed
	 * 
	 * @throws RemoteException
	 * @throws UiObjectNotFoundException
	 */
	public void testPressBack() throws RemoteException,
			UiObjectNotFoundException {
		startTSAP();
		getUiDevice().pressBack();

		UiObject tsapValidation = new UiObject(
				new UiSelector().packageName("org.tribler.tsap"));
		assertFalse("Able to find TSAP app after pressing back",
				tsapValidation.exists());

		UiObject launcher = new UiObject(
				new UiSelector().packageName("com.android.launcher"));
		assertTrue("Launcher doesn't exist after pressing back",
				launcher.exists());
		restartTSAP();
	}

	/**
	 * Tests whether TSAP is closed and the launcher is opened when the home
	 * button is pressed
	 * 
	 * @throws RemoteException
	 * @throws UiObjectNotFoundException
	 */
	public void testPressHome() throws RemoteException,
			UiObjectNotFoundException {
		startTSAP();
		getUiDevice().pressHome();

		UiObject tsapValidation = new UiObject(
				new UiSelector().packageName("org.tribler.tsap"));
		assertFalse("Able to find TSAP app after pressing home",
				tsapValidation.exists());

		UiObject launcher = new UiObject(
				new UiSelector().packageName("com.android.launcher"));
		assertTrue("Launcher doesn't exist after pressing home",
				launcher.exists());
		restartTSAP();
	}


	/**
	 * Tests whether TSAP is put in the background when the recent apps button
	 * is pressed and that the recent apps window contains the TSAP app. Also
	 * test whether TSAP is reopened when pressing back in recent apps
	 * 
	 * @throws RemoteException
	 * @throws UiObjectNotFoundException
	 */
	public void testPressRecentApps() throws RemoteException,
			UiObjectNotFoundException {
		startTSAP();
		getUiDevice().pressRecentApps();

		UiObject tsapValidation = new UiObject(
				new UiSelector().packageName("org.tribler.tsap"));
		assertTrue("Unable to find TSAP app after pressing recent apps",
				tsapValidation.exists());

		UiObject tsapItem = new UiObject(new UiSelector().text("TSAP"));
		assertTrue("TSAP doesn't exist in recent apps", tsapItem.exists());
		assertTrue("TSAP isn't enabled in recent apps", tsapItem.isEnabled());

		// close recents apps (so TSAP should be reopened)
		getUiDevice().pressBack();
		assertTrue(
				"Unable to find TSAP app after pressing back button in recent apps",
				tsapValidation.exists());
	}

	/**
	 * Tests whether TSAP is put in the background (but still exists) when the
	 * notifications are opened.
	 * 
	 * @throws RemoteException
	 * @throws UiObjectNotFoundException
	 */
	public void testOpenNotification() throws RemoteException,
			UiObjectNotFoundException {
		startTSAP();
		getUiDevice().openNotification();

		UiObject tsapValidation = new UiObject(
				new UiSelector().packageName("org.tribler.tsap"));
		assertTrue("Unable to find TSAP app after opening notifications",
				tsapValidation.exists());

		// close notifications (so TSAP should be in foreground)
		getUiDevice().pressBack();
	}

	/**
	 * Tests whether TSAP is put in the background (but still exists) when the
	 * quick settings are opened
	 * 
	 * @throws RemoteException
	 * @throws UiObjectNotFoundException
	 */
	public void testOpenQuickSettings() throws RemoteException,
			UiObjectNotFoundException {
		startTSAP();
		getUiDevice().openQuickSettings();

		UiObject tsapValidation = new UiObject(
				new UiSelector().packageName("org.tribler.tsap"));
		assertTrue("Unable to find TSAP app after opening quick settings",
				tsapValidation.exists());

		// close quick settings (so TSAP should be in foreground)
		getUiDevice().pressBack();
	}
	
	/**
	 * Launches the TSAP app on the device 
	 * 
	 * @throws UiObjectNotFoundException
	 * @throws RemoteException 
	 */
	private void restartTSAP() throws UiObjectNotFoundException, RemoteException {		
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
		appsTab.clickAndWaitForNewWindow();

		// Next, in the apps tabs, we can simulate a user swiping until
		// they come to the TSAP app icon. Since the container view
		// is scrollable, we can use a UiScrollable object.
		UiScrollable appViews = new UiScrollable(
				new UiSelector().scrollable(true));

		// Set the swiping mode to horizontal (the default is vertical)
		appViews.setAsHorizontalList();

		// Create a UiSelector to find the TSAP app and simulate
		// a user click to launch the app.
		UiObject tsapApp = appViews.getChildByText(new UiSelector().className("android.widget.TextView"), "TSAP",true);
		tsapApp.clickAndWaitForNewWindow();
		
		// Set the rotation to normal (=portrait mode)
		getUiDevice().setOrientationNatural();
	}
}
