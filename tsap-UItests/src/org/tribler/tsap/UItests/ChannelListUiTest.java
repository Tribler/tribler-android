package org.tribler.tsap.UItests;

import android.os.RemoteException;

import com.android.uiautomator.core.UiCollection;
import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.core.UiSelector;

/**
 * UI test cases for the channel list fragment
 * 
 * @author Niels Spruit
 */
public class ChannelListUiTest extends BasicUiTestCase {

	/**
	 * Tests whether the list view exists and is enabled
	 * 
	 * @throws RemoteException
	 * @throws UiObjectNotFoundException
	 */
	public void testListExists() throws RemoteException,
			UiObjectNotFoundException {
		openChannelList();

		UiObject list = new UiObject(
				new UiSelector().className("android.widget.ListView"));
		assertTrue("List doesn't exist", list.exists());
		assertTrue("List isn't enabled", list.isEnabled());
	}

	/**
	 * Tests whether the list view is clickable and scrollable
	 * 
	 * @throws RemoteException
	 * @throws UiObjectNotFoundException
	 */
	public void testListProperties() throws RemoteException,
			UiObjectNotFoundException {
		openChannelList();

		UiObject list = new UiObject(
				new UiSelector().className("android.widget.ListView"));
		assertTrue("List isn't clickable", list.isClickable());
		assertTrue("List isn't scrollable", list.isScrollable());
	}

	/**
	 * Tests whether the list contains any items
	 * 
	 * @throws RemoteException
	 * @throws UiObjectNotFoundException
	 */
	public void testGridContainsItems() throws RemoteException,
			UiObjectNotFoundException {
		openChannelList();

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
	public void testGridItemProperties() throws RemoteException,
			UiObjectNotFoundException {
		openChannelList();

		UiCollection list = new UiCollection(
				new UiSelector().className("android.widget.ListView"));
		UiObject firstItem = list.getChild(new UiSelector().index(0));

		assertTrue("First item doesn't exist", firstItem.exists());
		assertTrue("First item isn't enabled", firstItem.isEnabled());
		assertEquals("First item isn't a RelativeLayout",
				"android.widget.RelativeLayout", firstItem.getClassName());
	}

	/**
	 * Tests whether the first item contains an existing and enabled checkbox
	 * 
	 * @throws RemoteException
	 * @throws UiObjectNotFoundException
	 */
	public void testListItemCheckbox() throws RemoteException,
			UiObjectNotFoundException {
		openChannelList();

		UiCollection list = new UiCollection(
				new UiSelector().className("android.widget.ListView"));
		UiObject firstItem = list.getChild(new UiSelector().index(0));
		UiObject checkbox = firstItem.getChild(new UiSelector().index(0));

		assertTrue("Checkbox doesn't exist", checkbox.exists());
		assertTrue("Checkbox isn't enabled", checkbox.isEnabled());
		assertEquals("Checkbox isn't a CheckBox", "android.widget.CheckBox",
				checkbox.getClassName());
		assertTrue("Checkbox isn't checkable", checkbox.isCheckable());
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
		openChannelList();

		UiCollection list = new UiCollection(
				new UiSelector().className("android.widget.ListView"));
		UiObject firstItem = list.getChild(new UiSelector().index(0));
		UiObject title = firstItem.getChild(new UiSelector().index(1));

		assertTrue("Title doesn't exist", title.exists());
		assertTrue("Title isn't enabled", title.isEnabled());
		assertEquals("Title isn't a TextView", "android.widget.TextView",
				title.getClassName());
		assertFalse("Title is empty string", title.getText().equals(""));
	}

	/**
	 * Tests whether the first item contains an existing and enabled torrents
	 * TextView
	 * 
	 * @throws RemoteException
	 * @throws UiObjectNotFoundException
	 */
	public void testListItemTorrents() throws RemoteException,
			UiObjectNotFoundException {
		openChannelList();

		UiCollection list = new UiCollection(
				new UiSelector().className("android.widget.ListView"));
		UiObject firstItem = list.getChild(new UiSelector().index(0));
		UiObject torrents = firstItem.getChild(new UiSelector().index(2));

		assertTrue("Torrents view doesn't exist", torrents.exists());
		assertTrue("Torrents view isn't enabled", torrents.isEnabled());
		assertEquals("Torrents view isn't a TextView",
				"android.widget.TextView", torrents.getClassName());
		assertFalse("Torrents view is empty string",
				torrents.getText().equals(""));
		assertEquals("Torrents string doesn't start with 'Torrents:'",
				"Torrents: ", torrents.getText().substring(0, 9));
	}

	/**
	 * Tests whether the first item contains an existing and enabled 'first
	 * start' ImageView
	 * 
	 * @throws RemoteException
	 * @throws UiObjectNotFoundException
	 */
	public void testListItemRatingFirstStar() throws RemoteException,
			UiObjectNotFoundException {
		openChannelList();

		UiCollection list = new UiCollection(
				new UiSelector().className("android.widget.ListView"));
		UiObject firstItem = list.getChild(new UiSelector().index(0));
		UiObject firstStar = firstItem.getChild(new UiSelector().index(4));

		assertTrue("First star doesn't exist", firstStar.exists());
		assertTrue("First star isn't enabled", firstStar.isEnabled());
		assertEquals("First star  isn't an ImageView",
				"android.widget.ImageView", firstStar.getClassName());
	}

	/**
	 * Tests whether the first item contains an existing and enabled 'second
	 * start' ImageView
	 * 
	 * @throws RemoteException
	 * @throws UiObjectNotFoundException
	 */
	public void testListItemRatingSecondStar() throws RemoteException,
			UiObjectNotFoundException {
		openChannelList();

		UiCollection list = new UiCollection(
				new UiSelector().className("android.widget.ListView"));
		UiObject firstItem = list.getChild(new UiSelector().index(0));
		UiObject secondStar = firstItem.getChild(new UiSelector().index(5));

		assertTrue("Second star doesn't exist", secondStar.exists());
		assertTrue("Second star isn't enabled", secondStar.isEnabled());
		assertEquals("Second star isn't an ImageView",
				"android.widget.ImageView", secondStar.getClassName());
	}
	
	/**
	 * Tests whether the first item contains an existing and enabled 'third
	 * start' ImageView
	 * 
	 * @throws RemoteException
	 * @throws UiObjectNotFoundException
	 */
	public void testListItemRatingThirdStar() throws RemoteException,
			UiObjectNotFoundException {
		openChannelList();

		UiCollection list = new UiCollection(
				new UiSelector().className("android.widget.ListView"));
		UiObject firstItem = list.getChild(new UiSelector().index(0));
		UiObject thirdStar = firstItem.getChild(new UiSelector().index(6));

		assertTrue("Third star doesn't exist", thirdStar.exists());
		assertTrue("Third star isn't enabled", thirdStar.isEnabled());
		assertEquals("Third star isn't an ImageView",
				"android.widget.ImageView", thirdStar.getClassName());
	}
	
	/**
	 * Tests whether the first item contains an existing and enabled 'fourth
	 * start' ImageView
	 * 
	 * @throws RemoteException
	 * @throws UiObjectNotFoundException
	 */
	public void testListItemRatingFourthStar() throws RemoteException,
			UiObjectNotFoundException {
		openChannelList();

		UiCollection list = new UiCollection(
				new UiSelector().className("android.widget.ListView"));
		UiObject firstItem = list.getChild(new UiSelector().index(0));
		UiObject fourthStar = firstItem.getChild(new UiSelector().index(7));

		assertTrue("Fourth star doesn't exist", fourthStar.exists());
		assertTrue("Fourth star isn't enabled", fourthStar.isEnabled());
		assertEquals("Fourth star isn't an ImageView",
				"android.widget.ImageView", fourthStar.getClassName());
	}
	
	/**
	 * Tests whether the first item contains an existing and enabled 'fifth
	 * start' ImageView
	 * 
	 * @throws RemoteException
	 * @throws UiObjectNotFoundException
	 */
	public void testListItemRatingFifthStar() throws RemoteException,
			UiObjectNotFoundException {
		openChannelList();

		UiCollection list = new UiCollection(
				new UiSelector().className("android.widget.ListView"));
		UiObject firstItem = list.getChild(new UiSelector().index(0));
		UiObject fifthStar = firstItem.getChild(new UiSelector().index(8));

		assertTrue("Fifth star doesn't exist", fifthStar.exists());
		assertTrue("Fifth star isn't enabled", fifthStar.isEnabled());
		assertEquals("Fifth star isn't an ImageView",
				"android.widget.ImageView", fifthStar.getClassName());
	}
	
	/**
	 * Tests whether the list view contains the search option
	 * 
	 * @throws RemoteException
	 * @throws UiObjectNotFoundException
	 */
	public void testListSearchExists() throws RemoteException,
			UiObjectNotFoundException {
		openChannelList();

		UiObject search = new UiObject(new UiSelector().description("Search"));

		assertTrue("Search view doesn't exist", search.exists());
		assertTrue("Search view isn't enabled", search.isEnabled());
		assertTrue("Search view isn't clickable", search.isClickable());
	}

	/**
	 * Tests whether the list view shows a search box when the search option is
	 * clicked
	 * 
	 * @throws RemoteException
	 * @throws UiObjectNotFoundException
	 */
	public void testListSearchClick() throws RemoteException,
			UiObjectNotFoundException {
		openChannelList();

		UiObject search = new UiObject(new UiSelector().description("Search"));

		assertTrue("Search view isn't clicked", search.click());

		UiObject searchView = new UiObject(
				new UiSelector().description("Search query"));
		assertTrue("SearchView doesn't exist", searchView.exists());
		assertTrue("SearchView isn't enabled", searchView.isEnabled());
		assertTrue("SearchView isn't clickable", searchView.isClickable());
		assertEquals("SearchView text isn't correct", "Search channels",
				searchView.getText().trim());
		assertEquals("SearchView isn't an EditText", "android.widget.EditText",
				searchView.getClassName());
	}
	
	/**
	 * Launches the app, opens the navigation drawer and click the channels item
	 * to load the channel list
	 * 
	 * @throws RemoteException
	 * @throws UiObjectNotFoundException
	 */
	private void openChannelList() throws RemoteException,
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
				.text("Channels"));
		channelsItem.click();
	}

}
