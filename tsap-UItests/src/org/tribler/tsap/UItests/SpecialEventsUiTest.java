package org.tribler.tsap.UItests;

import javax.crypto.SealedObject;

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
	 * Tests whether TSAP in closed and the launcher is opened when the back
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
	}

	/**
	 * Tests whether TSAP in closed and the launcher is opened when the home
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
	}

	/**
	 * Tests whether TSAP in closed and the systemUI is opened when the recent apps
	 * button is pressed and that the recent apps window contains the TSAP app
	 * 
	 * @throws RemoteException
	 * @throws UiObjectNotFoundException
	 */
	public void testPressRecentApps() throws RemoteException, UiObjectNotFoundException {
		startTSAP();
		getUiDevice().pressRecentApps();

		UiObject tsapValidation = new UiObject(
				new UiSelector().packageName("org.tribler.tsap"));
		assertFalse("Able to find TSAP app after pressing recent apps",
				tsapValidation.exists());

		UiObject systemUI = new UiObject(
				new UiSelector().packageName("com.android.systemui"));
		assertTrue("SystemUI doesn't exist after pressing recent apps",
				systemUI.exists());
		
		UiObject tsapItem = systemUI.getChild(new UiSelector().text("TSAP"));
		assertTrue("TSAP doesn't exist in recent apps", tsapItem.exists());
		assertTrue("TSAP isn't enabled in recent apps", tsapItem.isEnabled());
	}

	public void testOpenNotifications() {

	}

	public void testOpenQuickSettings() {

	}

	public void testSleepWakeUp() {

	}
}
