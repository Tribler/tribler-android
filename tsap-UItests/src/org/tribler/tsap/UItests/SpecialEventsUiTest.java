package org.tribler.tsap.UItests;

import android.os.RemoteException;

import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiObjectNotFoundException;
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
	public void ignoreTestPressBack() throws RemoteException,
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
	}

	/**
	 * Tests whether TSAP is closed and the launcher is opened when the home
	 * button is pressed
	 * 
	 * @throws RemoteException
	 * @throws UiObjectNotFoundException
	 */
	public void ignoreTestPressHome() throws RemoteException,
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
}
