package org.tribler.tsap.UItests;

import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.core.UiSelector;

/**
 * UI test cases for the navigation drawer
 * 
 * @author Niels Spruit
 */
public class NavigationDrawerUiTest extends BasicUiTestCase {

	/**
	 * Tests whether the up navigation button exists and whether it is clickable
	 * and enabled
	 * 
	 * @throws UiObjectNotFoundException
	 */
	public void testUpButtonExists() throws UiObjectNotFoundException {
		startTSAP();
		UiObject upButton = new UiObject(
				new UiSelector().description("Navigate up"));
		assertTrue("UpButton doesn't exist", upButton.exists());
		assertTrue("UpBotton isn't enabled", upButton.isEnabled());
		assertTrue("UpButton isn't clickable", upButton.isClickable());
	}

	/**
	 * Test whether the navigation drawer is opened when the up button is
	 * clicked
	 * 
	 * @throws UiObjectNotFoundException
	 */
	public void testOpenDrawer() throws UiObjectNotFoundException {
		openNavigationDrawer();

		UiObject navDrawer = new UiObject(
				new UiSelector().description("navigation_drawer"));

		assertTrue("Navigation drawer doesn't exist", navDrawer.exists());
		assertTrue("Navigation drawer isn't enabled", navDrawer.isEnabled());
		assertTrue("Navigation drawer isn't clickable", navDrawer.isClickable());
	}

	/**
	 * Tests whether the navigation drawer contains the correct number of items
	 * 
	 * @throws UiObjectNotFoundException
	 */
	public void testNrOfItemsInDrawer() throws UiObjectNotFoundException {
		openNavigationDrawer();

		UiObject navDrawer = new UiObject(
				new UiSelector().description("navigation_drawer"));

		assertEquals(
				"Navigation drawer doesn't contain the right number of items",
				2, navDrawer.getChildCount());
	}

	/**
	 * Tests whether the title in the action is TSAP when the drawer is opened
	 * 
	 * @throws UiObjectNotFoundException
	 */
	public void testAppTitleOnOpenedDrawer() throws UiObjectNotFoundException {
		openNavigationDrawer();

		UiObject titleView = new UiObject(new UiSelector().text("TSAP"));
		assertEquals("Title text incorrect", "TSAP", titleView.getText());
		assertTrue("Title doesn't exist", titleView.exists());
		assertTrue("Title isn't enabled", titleView.isEnabled());
	}

	/**
	 * Test whether the drawer contains the home item and whether its properties
	 * are corrects
	 * 
	 * @throws UiObjectNotFoundException
	 */
	public void testHomeItemProperties() throws UiObjectNotFoundException {
		openNavigationDrawer();
		UiObject navDrawer = new UiObject(
				new UiSelector().description("navigation_drawer"));

		UiObject homeItem = navDrawer.getChild(new UiSelector().text("Home"));

		assertTrue("Home item doesn't exist", homeItem.exists());
		assertTrue("Home item isn't enabled", homeItem.isEnabled());
	}

	/**
	 * Test whether the drawer contains the channels item and whether its
	 * properties are corrects
	 * 
	 * @throws UiObjectNotFoundException
	 */
	public void testChannelsItemProperties() throws UiObjectNotFoundException {
		openNavigationDrawer();
		UiObject navDrawer = new UiObject(
				new UiSelector().description("navigation_drawer"));

		UiObject channelsItem = navDrawer.getChild(new UiSelector()
				.text("Channels"));

		assertTrue("Channels item doesn't exist", channelsItem.exists());
		assertTrue("Channels item isn't enabled", channelsItem.isEnabled());
	}

	/**
	 * Helper function that starts the TSAP app and opens the navigation drawer
	 * if it isn't opened
	 * 
	 * @throws UiObjectNotFoundException
	 */
	private void openNavigationDrawer() throws UiObjectNotFoundException {
		startTSAP();

		UiObject navDrawer = new UiObject(
				new UiSelector().description("navigation_drawer"));
		if (!navDrawer.exists()) {
			UiObject upButton = new UiObject(
					new UiSelector().description("Navigate up"));
			upButton.click();
		}
	}
}
