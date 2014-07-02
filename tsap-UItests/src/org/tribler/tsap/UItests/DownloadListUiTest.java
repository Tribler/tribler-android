package org.tribler.tsap.UItests;

import android.os.RemoteException;

import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.core.UiSelector;

/**
 * UI test cases for the download list fragment
 * 
 * @author Niels Spruit
 */
public class DownloadListUiTest extends BasicUiTestCase {
	
	/**
	 * Tests whether the list view exists and is enabled
	 * 
	 * @throws RemoteException
	 * @throws UiObjectNotFoundException
	 */
	public void testListExists() throws RemoteException,
			UiObjectNotFoundException {
		openDownloadList();

		UiObject list = new UiObject(
				new UiSelector().className("android.widget.ListView"));
		assertTrue("List doesn't exist", list.exists());
		assertTrue("List isn't enabled", list.isEnabled());
	}

	/**
	 * Tests whether the list view is clickable
	 * 
	 * @throws RemoteException
	 * @throws UiObjectNotFoundException
	 */
	public void testListProperties() throws RemoteException,
			UiObjectNotFoundException {
		openDownloadList();

		UiObject list = new UiObject(
				new UiSelector().className("android.widget.ListView"));
		assertTrue("List isn't clickable", list.isClickable());
	}

	/**
	 * Tests whether the list contains any items
	 * 
	 * @throws RemoteException
	 * @throws UiObjectNotFoundException
	 */
	public void testListContainsItems() throws RemoteException,
			UiObjectNotFoundException {
		openDownloadList();

		UiObject list = new UiObject(
				new UiSelector().className("android.widget.ListView"));
		assertTrue("List doesn't contain any items", list.getChildCount() > 0);
	}
	
	/**
	 * Launches the app, opens the navigation drawer and clicks the downloads item
	 * to load the downloads list
	 * 
	 * @throws RemoteException
	 * @throws UiObjectNotFoundException
	 */
	private void openDownloadList() throws RemoteException,
			UiObjectNotFoundException {
		startTSAP();

		UiObject navDrawer = new UiObject(
				new UiSelector().description("navigation_drawer"));
		if (!navDrawer.exists()) {
			UiObject upButton = new UiObject(
					new UiSelector().description("Navigate up"));
			upButton.click();
		}

		UiObject channelsItem = navDrawer.getChild(new UiSelector()
				.text("Downloads"));
		channelsItem.click();
	}

}
