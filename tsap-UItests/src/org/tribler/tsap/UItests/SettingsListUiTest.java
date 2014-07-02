package org.tribler.tsap.UItests;

import android.os.RemoteException;

import com.android.uiautomator.core.UiCollection;
import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.core.UiSelector;

/**
 * UI test cases for the settings list fragment
 * 
 * @author Niels Spruit
 */
public class SettingsListUiTest extends BasicUiTestCase {

	/**
	 * Tests whether the list view exists and is enabled
	 * 
	 * @throws RemoteException
	 * @throws UiObjectNotFoundException
	 */
	public void testListExists() throws RemoteException,
			UiObjectNotFoundException {
		openSettingsList();

		UiObject list = new UiObject(
				new UiSelector().className("android.widget.ListView"));
		assertTrue("List doesn't exist", list.exists());
		assertTrue("List isn't enabled", list.isEnabled());
	}

	/**
	 * Tests whether the settings list contains any items
	 * 
	 * @throws RemoteException
	 * @throws UiObjectNotFoundException
	 */
	public void testListContainsItems() throws RemoteException,
			UiObjectNotFoundException {
		openSettingsList();

		UiObject list = new UiObject(
				new UiSelector().className("android.widget.ListView"));
		assertTrue("List doesn't contain any items", list.getChildCount() > 0);
	}

	/**
	 * Tests whether the first item exists and is enabled and clickable
	 * 
	 * @throws RemoteException
	 * @throws UiObjectNotFoundException
	 */
	public void testListItemProperties() throws RemoteException,
			UiObjectNotFoundException {
		openSettingsList();

		UiCollection list = new UiCollection(
				new UiSelector().className("android.widget.ListView"));
		UiObject firstItem = list.getChild(new UiSelector().index(1));

		assertTrue("First item doesn't exist", firstItem.exists());
		assertTrue("First item isn't enabled", firstItem.isEnabled());
		assertTrue("First item isn't clickable", firstItem.isClickable());
		assertEquals("First item isn't a LinearLayout",
				"android.widget.LinearLayout", firstItem.getClassName());
	}

	/**
	 * Tests whether the settings contain the family filter setting
	 * 
	 * @throws RemoteException
	 * @throws UiObjectNotFoundException
	 */
	public void testListFamilyFilter() throws RemoteException,
			UiObjectNotFoundException {
		openSettingsList();

		UiCollection list = new UiCollection(
				new UiSelector().className("android.widget.ListView"));
		UiObject famFilter = list.getChild(new UiSelector().index(1));

		assertTrue("famFilter doesn't exist", famFilter.exists());
		assertTrue("famFilter isn't enabled", famFilter.isEnabled());
		assertTrue("famFilter isn't clickable", famFilter.isClickable());
		assertEquals("famFilter isn't a LinearLayout",
				"android.widget.LinearLayout", famFilter.getClassName());
	}

	/**
	 * Tests whether the settings contain the supported torrent types setting
	 * 
	 * @throws RemoteException
	 * @throws UiObjectNotFoundException
	 */
	public void testListTorrentTypes() throws RemoteException,
			UiObjectNotFoundException {
		openSettingsList();

		UiCollection list = new UiCollection(
				new UiSelector().className("android.widget.ListView"));
		UiObject types = list.getChild(new UiSelector().index(2));

		assertTrue("Torrent types doesn't exist", types.exists());
		assertTrue("Torrent types isn't enabled", types.isEnabled());
		assertTrue("Torrent types isn't clickable", types.isClickable());
		assertEquals("Torrent types isn't a LinearLayout",
				"android.widget.LinearLayout", types.getClassName());
	}

	/**
	 * Tests whether the settings contain the maximum download rate setting
	 * 
	 * @throws RemoteException
	 * @throws UiObjectNotFoundException
	 */
	public void testListMaxDownloadRate() throws RemoteException,
			UiObjectNotFoundException {
		openSettingsList();

		UiCollection list = new UiCollection(
				new UiSelector().className("android.widget.ListView"));
		UiObject downSpeed = list.getChild(new UiSelector().index(4));

		assertTrue("Max download rate doesn't exist", downSpeed.exists());
		assertTrue("Max download rate isn't enabled", downSpeed.isEnabled());
		assertTrue("Max download rate isn't clickable", downSpeed.isClickable());
		assertEquals("Max download rate isn't a LinearLayout",
				"android.widget.LinearLayout", downSpeed.getClassName());
	}

	/**
	 * Tests whether the settings contain the maximum upload rate setting
	 * 
	 * @throws RemoteException
	 * @throws UiObjectNotFoundException
	 */
	public void testListMaxUploadRate() throws RemoteException,
			UiObjectNotFoundException {
		openSettingsList();

		UiCollection list = new UiCollection(
				new UiSelector().className("android.widget.ListView"));
		UiObject upSpeed = list.getChild(new UiSelector().index(5));

		assertTrue("Max upload rate doesn't exist", upSpeed.exists());
		assertTrue("Max upload rate isn't enabled", upSpeed.isEnabled());
		assertTrue("Max upload rate isn't clickable", upSpeed.isClickable());
		assertEquals("Max upload rate isn't a LinearLayout",
				"android.widget.LinearLayout", upSpeed.getClassName());
	}

	/**
	 * Tests whether the settings contain the OS licenses setting
	 * 
	 * @throws RemoteException
	 * @throws UiObjectNotFoundException
	 */
	public void testListLicenses() throws RemoteException,
			UiObjectNotFoundException {
		openSettingsList();

		UiCollection list = new UiCollection(
				new UiSelector().className("android.widget.ListView"));
		UiObject licences = list.getChild(new UiSelector().index(7));

		assertTrue("Licences setting doesn't exist", licences.exists());
		assertTrue("Licences setting isn't enabled", licences.isEnabled());
		assertTrue("Licences setting isn't clickable", licences.isClickable());
		assertEquals("Licences setting isn't a LinearLayout",
				"android.widget.LinearLayout", licences.getClassName());
	}

	/**
	 * Tests whether the settings contain the about setting
	 * 
	 * @throws RemoteException
	 * @throws UiObjectNotFoundException
	 */
	public void testListAbout() throws RemoteException,
			UiObjectNotFoundException {
		openSettingsList();

		UiCollection list = new UiCollection(
				new UiSelector().className("android.widget.ListView"));
		UiObject about = list.getChild(new UiSelector().index(8));

		assertTrue("About setting doesn't exist", about.exists());
		assertTrue("About setting isn't enabled", about.isEnabled());
		assertTrue("About setting isn't clickable", about.isClickable());
		assertEquals("About setting isn't a LinearLayout",
				"android.widget.LinearLayout", about.getClassName());
	}

	/**
	 * Launches the app, opens the navigation drawer and clicks the settings
	 * item to load the settings list
	 * 
	 * @throws RemoteException
	 * @throws UiObjectNotFoundException
	 */
	private void openSettingsList() throws RemoteException,
			UiObjectNotFoundException {
		startTSAP();

		UiObject navDrawer = new UiObject(
				new UiSelector().description("navigation_drawer"));
		if (!navDrawer.exists()) {
			UiObject upButton = new UiObject(
					new UiSelector().description("Navigate up"));
			upButton.click();
		}

		UiObject settingsItem = navDrawer.getChild(new UiSelector()
				.text("Settings"));
		settingsItem.click();
	}

}
