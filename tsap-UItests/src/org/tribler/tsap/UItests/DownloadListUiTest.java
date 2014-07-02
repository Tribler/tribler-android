package org.tribler.tsap.UItests;

import android.os.RemoteException;

import com.android.uiautomator.core.UiCollection;
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
	 * Tests whether the first item exists and is enabled
	 * 
	 * @throws RemoteException
	 * @throws UiObjectNotFoundException
	 */
	public void testListItemProperties() throws RemoteException,
			UiObjectNotFoundException {
		openDownloadList();

		UiCollection list = new UiCollection(
				new UiSelector().className("android.widget.ListView"));
		UiObject firstItem = list.getChild(new UiSelector().index(0));

		assertTrue("First item doesn't exist", firstItem.exists());
		assertTrue("First item isn't enabled", firstItem.isEnabled());
		assertEquals("First item isn't a RelativeLayout",
				"android.widget.RelativeLayout", firstItem.getClassName());
	}

	/**
	 * Tests whether the first item contains an existing and enabled title
	 * TextView
	 * 
	 * @throws RemoteException
	 * @throws UiObjectNotFoundException
	 */
	public void testListItemTitle() throws RemoteException,
			UiObjectNotFoundException {
		openDownloadList();

		UiCollection list = new UiCollection(
				new UiSelector().className("android.widget.ListView"));
		UiObject firstItem = list.getChild(new UiSelector().index(0));
		UiObject title = firstItem.getChild(new UiSelector().index(0));

		assertTrue("Title doesn't exist", title.exists());
		assertTrue("Title isn't enabled", title.isEnabled());
		assertEquals("Title isn't a TextView", "android.widget.TextView",
				title.getClassName());
		assertFalse("Title is empty string", title.getText().equals(""));
	}

	/**
	 * Tests whether the first item contains an existing and enabled status
	 * TextView
	 * 
	 * @throws RemoteException
	 * @throws UiObjectNotFoundException
	 */
	public void testListItemStatus() throws RemoteException,
			UiObjectNotFoundException {
		openDownloadList();

		UiCollection list = new UiCollection(
				new UiSelector().className("android.widget.ListView"));
		UiObject firstItem = list.getChild(new UiSelector().index(0));
		UiObject status = firstItem.getChild(new UiSelector().index(1));

		assertTrue("Status doesn't exist", status.exists());
		assertTrue("Status isn't enabled", status.isEnabled());
		assertEquals("Status isn't a TextView", "android.widget.TextView",
				status.getClassName());
		assertFalse("Status is empty string", status.getText().equals(""));
	}

	/**
	 * Tests whether the first item contains an existing and enabled download
	 * speed TextView
	 * 
	 * @throws RemoteException
	 * @throws UiObjectNotFoundException
	 */
	public void testListItemDownloadSpeed() throws RemoteException,
			UiObjectNotFoundException {
		openDownloadList();

		UiCollection list = new UiCollection(
				new UiSelector().className("android.widget.ListView"));
		UiObject firstItem = list.getChild(new UiSelector().index(0));
		UiObject downSpeed = firstItem.getChild(new UiSelector().index(2));

		assertTrue("Download speed doesn't exist", downSpeed.exists());
		assertTrue("Download speed isn't enabled", downSpeed.isEnabled());
		assertEquals("Download speed isn't a TextView",
				"android.widget.TextView", downSpeed.getClassName());
		assertFalse("Download speed is empty string", downSpeed.getText()
				.equals(""));
	}

	/**
	 * Tests whether the first item contains an existing and enabled upload
	 * speed TextView
	 * 
	 * @throws RemoteException
	 * @throws UiObjectNotFoundException
	 */
	public void testListItemUploadSpeed() throws RemoteException,
			UiObjectNotFoundException {
		openDownloadList();

		UiCollection list = new UiCollection(
				new UiSelector().className("android.widget.ListView"));
		UiObject firstItem = list.getChild(new UiSelector().index(0));
		UiObject upSpeed = firstItem.getChild(new UiSelector().index(3));

		assertTrue("Upload speed doesn't exist", upSpeed.exists());
		assertTrue("Upload speed isn't enabled", upSpeed.isEnabled());
		assertEquals("Upload speed isn't a TextView",
				"android.widget.TextView", upSpeed.getClassName());
		assertFalse("Upload speed is empty string", upSpeed.getText()
				.equals(""));
	}

	/**
	 * Tests whether the first item contains an existing and enabled upload
	 * speed TextView
	 * 
	 * @throws RemoteException
	 * @throws UiObjectNotFoundException
	 */
	public void testListItemProgress() throws RemoteException,
			UiObjectNotFoundException {
		openDownloadList();

		UiCollection list = new UiCollection(
				new UiSelector().className("android.widget.ListView"));
		UiObject firstItem = list.getChild(new UiSelector().index(0));
		UiObject progress = firstItem.getChild(new UiSelector().index(5));

		assertTrue("Progress bar doesn't exist", progress.exists());
		assertTrue("Progress bar isn't enabled", progress.isEnabled());
		assertEquals("Progress bar isn't a ProgressBar",
				"android.widget.ProgressBar", progress.getClassName());
	}

	/**
	 * Launches the app, opens the navigation drawer and clicks the downloads
	 * item to load the downloads list
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
