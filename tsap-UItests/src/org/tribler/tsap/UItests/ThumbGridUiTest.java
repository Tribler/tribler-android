package org.tribler.tsap.UItests;

import android.os.RemoteException;

import com.android.uiautomator.core.UiCollection;
import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.core.UiSelector;

/**
 * UI test cases for the thumbgrid fragment
 * 
 * @author Niels Spruit
 */
public class ThumbGridUiTest extends BasicUiTestCase {

	/**
	 * Tests whether the grid view exists and is enabled
	 * 
	 * @throws RemoteException
	 * @throws UiObjectNotFoundException
	 */
	public void testGridExists() throws RemoteException,
			UiObjectNotFoundException {
		openThumbGrid();

		UiObject grid = new UiObject(
				new UiSelector().className("android.widget.GridView"));
		assertTrue("Grid doesn't exist", grid.exists());
		assertTrue("Grid isn't enabled", grid.isEnabled());
	}

	/**
	 * Tests whether the grid view is clickable
	 * 
	 * @throws RemoteException
	 * @throws UiObjectNotFoundException
	 */
	public void testGridProperties() throws RemoteException,
			UiObjectNotFoundException {
		openThumbGrid();

		UiObject grid = new UiObject(
				new UiSelector().className("android.widget.GridView"));
		assertTrue("Grid isn't clickable", grid.isClickable());
	}

	/**
	 * Tests whether the grid contains any items
	 * 
	 * @throws RemoteException
	 * @throws UiObjectNotFoundException
	 */
	public void testGridContainsItems() throws RemoteException,
			UiObjectNotFoundException {
		openThumbGrid();

		UiObject grid = new UiObject(
				new UiSelector().className("android.widget.GridView"));
		assertTrue("Grid doesn't contain any items", grid.getChildCount() > 0);
	}

	/**
	 * Test whether the first item exists and is enabled
	 * 
	 * @throws RemoteException
	 * @throws UiObjectNotFoundException
	 */
	public void testGridItemProperties() throws RemoteException,
			UiObjectNotFoundException {
		openThumbGrid();

		UiCollection grid = new UiCollection(
				new UiSelector().className("android.widget.GridView"));
		UiObject firstItem = grid.getChild(new UiSelector().index(0));

		assertTrue("First item doesn't exist", firstItem.exists());
		assertTrue("First item isn't enabled", firstItem.isEnabled());
		assertEquals("First item isn't a LinearLayout",
				"android.widget.LinearLayout", firstItem.getClassName());
	}

	/**
	 * Test whether the first item contains an existing and enabled thumbnail
	 * ImageView
	 * 
	 * @throws RemoteException
	 * @throws UiObjectNotFoundException
	 */
	public void testGridItemThumbnail() throws RemoteException,
			UiObjectNotFoundException {
		openThumbGrid();

		UiCollection grid = new UiCollection(
				new UiSelector().className("android.widget.GridView"));
		UiObject firstItem = grid.getChild(new UiSelector().index(0));
		UiObject thumbnail = firstItem.getChild(new UiSelector()
				.description("Torrent thumbnail"));

		assertTrue("Thumbnail view doesn't exist", thumbnail.exists());
		assertTrue("Thumbnail view isn't enabled", thumbnail.isEnabled());
		assertEquals("Thumbnail view isn't an ImageView",
				"android.widget.ImageView", thumbnail.getClassName());
	}

	/**
	 * Test whether the first item contains an existing and enabled title
	 * TextView
	 * 
	 * @throws RemoteException
	 * @throws UiObjectNotFoundException
	 */
	public void testGridItemTitle() throws RemoteException,
			UiObjectNotFoundException {
		openThumbGrid();

		UiCollection grid = new UiCollection(
				new UiSelector().className("android.widget.GridView"));
		UiObject firstItem = grid.getChild(new UiSelector().index(0));
		UiObject title = firstItem.getChild(new UiSelector().index(1));

		assertTrue("Title view doesn't exist", title.exists());
		assertTrue("Title view isn't enabled", title.isEnabled());
		assertEquals("Title view isn't a TextView", "android.widget.TextView",
				title.getClassName());
		assertFalse("Title is empty string", title.getText().equals(""));
	}

	/**
	 * Test whether the first item contains an existing and enabled size
	 * TextView
	 * 
	 * @throws RemoteException
	 * @throws UiObjectNotFoundException
	 */
	public void testGridItemSize() throws RemoteException,
			UiObjectNotFoundException {
		openThumbGrid();

		UiCollection grid = new UiCollection(
				new UiSelector().className("android.widget.GridView"));
		UiObject firstItem = grid.getChild(new UiSelector().index(0));
		UiObject size = firstItem.getChild(new UiSelector().index(2)).getChild(
				new UiSelector().index(0));

		assertTrue("Size view doesn't exist", size.exists());
		assertTrue("Size view isn't enabled", size.isEnabled());
		assertEquals("Size view isn't a TextView", "android.widget.TextView",
				size.getClassName());
		assertFalse("Size is empty string", size.getText().equals(""));
	}

	/**
	 * Test whether the first item contains an existing and enabled health
	 * ProgressBar
	 * 
	 * @throws RemoteException
	 * @throws UiObjectNotFoundException
	 */
	public void testGridItemHealth() throws RemoteException,
			UiObjectNotFoundException {
		openThumbGrid();

		UiCollection grid = new UiCollection(
				new UiSelector().className("android.widget.GridView"));
		UiObject firstItem = grid.getChild(new UiSelector().index(0));
		UiObject health = firstItem.getChild(new UiSelector().index(2))
				.getChild(new UiSelector().index(1));

		assertTrue("Health view doesn't exist", health.exists());
		assertTrue("Health view isn't enabled", health.isEnabled());
		assertEquals("Health view isn't a ProgressBar",
				"android.widget.ProgressBar", health.getClassName());
	}

	/**
	 * Test whether the grid view contains the search option
	 * 
	 * @throws RemoteException
	 * @throws UiObjectNotFoundException
	 */
	public void testGridSearchExists() throws RemoteException,
			UiObjectNotFoundException {
		openThumbGrid();

		UiObject search = new UiObject(new UiSelector().description("Search"));

		assertTrue("Search view doesn't exist", search.exists());
		assertTrue("Search view isn't enabled", search.isEnabled());
		assertTrue("Search view isn't clickable", search.isClickable());
	}

	/**
	 * Test whether the grid view shows a search box when the search option is
	 * clicked
	 * 
	 * @throws RemoteException
	 * @throws UiObjectNotFoundException
	 */
	public void testGridSearchClick() throws RemoteException,
			UiObjectNotFoundException {
		openThumbGrid();

		UiObject search = new UiObject(new UiSelector().description("Search"));

		assertTrue("Search view isn't clicked", search.click());

		UiObject searchView = new UiObject(
				new UiSelector().description("Search query"));
		assertTrue("SearchView doesn't exist", searchView.exists());
		assertTrue("SearchView isn't enabled", searchView.isEnabled());
		assertTrue("SearchView isn't clickable", searchView.isClickable());
		assertEquals("SearchView text isn't correct", "Search videos",
				searchView.getText());
		assertEquals("SearchView isn't an EditText", "android.widget.EditText",
				searchView.getClassName());
	}

	/**
	 * Launches the app, opens the navigation drawer and click the home item to
	 * load the thumb grid
	 * 
	 * @throws RemoteException
	 * @throws UiObjectNotFoundException
	 */
	private void openThumbGrid() throws RemoteException,
			UiObjectNotFoundException {
		startTSAP();

		UiObject navDrawer = new UiObject(
				new UiSelector().description("navigation_drawer"));
		if (!navDrawer.exists()) {
			UiObject upButton = new UiObject(
					new UiSelector().description("Navigate up"));
			upButton.click();
		}

		UiObject homeItem = navDrawer.getChild(new UiSelector().text("Home"));
		homeItem.click();
	}

}
