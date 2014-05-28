package org.tribler.tsap.UItests;

import android.os.RemoteException;

import com.android.uiautomator.core.UiCollection;
import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.core.UiSelector;

/**
 * UI test cases for the video info screen
 * 
 * @author Niels Spruit
 */
public class VideoInfoUiTest extends BasicUiTestCase {

	/**
	 * Tests whether the Video info view exists and is enabled
	 * 
	 * @throws RemoteException
	 * @throws UiObjectNotFoundException
	 */
	public void testVideoInfoViewExists() throws RemoteException,
			UiObjectNotFoundException {
		openVideoInfoScreen();

		UiObject infoView = new UiObject(
				new UiSelector().className("android.widget.RelativeLayout"));
		assertTrue("Video info view doesn't exist", infoView.exists());
		assertTrue("Video info view isn't enabled", infoView.isEnabled());
	}

	/**
	 * Tests whether the video info screen contains a non-empty title TextView
	 * 
	 * @throws RemoteException
	 * @throws UiObjectNotFoundException
	 */
	public void testVideoInfoTitle() throws RemoteException,
			UiObjectNotFoundException {
		openVideoInfoScreen();

		UiObject infoView = new UiObject(
				new UiSelector().className("android.widget.RelativeLayout"));
		UiObject title = infoView.getChild(new UiSelector().index(0));

		assertTrue("Title view doesn't exist", title.exists());
		assertTrue("Title view isn't enabled", title.isEnabled());
		assertEquals("Title view isn't a TextView", "android.widget.TextView",
				title.getClassName());
		assertFalse("Title is empty string", title.getText().equals(""));
	}
	
	/**
	 * Tests whether the video info screen contains the type TextView
	 * 
	 * @throws RemoteException
	 * @throws UiObjectNotFoundException
	 */
	public void testVideoInfoTypeTitle() throws RemoteException,
			UiObjectNotFoundException {
		openVideoInfoScreen();

		UiObject infoView = new UiObject(
				new UiSelector().className("android.widget.RelativeLayout"));
		UiObject parent = infoView.getChild(new UiSelector().index(1));
		UiObject typeTitle = parent.getChild(new UiSelector().index(0));

		assertTrue("Type title view doesn't exist", typeTitle.exists());
		assertTrue("Type title view isn't enabled", typeTitle.isEnabled());
		assertEquals("Type title view isn't a TextView", "android.widget.TextView",
				typeTitle.getClassName());
		assertEquals("Type title is empty string","Type:", typeTitle.getText());
	}
	
	/**
	 * Tests whether the video info screen contains the uploaded TextView
	 * 
	 * @throws RemoteException
	 * @throws UiObjectNotFoundException
	 */
	public void testVideoInfoUploadedTitle() throws RemoteException,
			UiObjectNotFoundException {
		openVideoInfoScreen();

		UiObject infoView = new UiObject(
				new UiSelector().className("android.widget.RelativeLayout"));
		UiObject parent = infoView.getChild(new UiSelector().index(1));
		UiObject uploadedTitle = parent.getChild(new UiSelector().index(1));

		assertTrue("Uploaded title view doesn't exist", uploadedTitle.exists());
		assertTrue("Uploaded title view isn't enabled", uploadedTitle.isEnabled());
		assertEquals("Uploaded title view isn't a TextView", "android.widget.TextView",
				uploadedTitle.getClassName());
		assertEquals("Uploaded title is empty string","Uploaded:", uploadedTitle.getText());
	}
	
	/**
	 * Tests whether the video info screen contains the filesize TextView
	 * 
	 * @throws RemoteException
	 * @throws UiObjectNotFoundException
	 */
	public void testVideoInfoFilesizeTitle() throws RemoteException,
			UiObjectNotFoundException {
		openVideoInfoScreen();

		UiObject infoView = new UiObject(
				new UiSelector().className("android.widget.RelativeLayout"));
		UiObject parent = infoView.getChild(new UiSelector().index(1));
		UiObject filesizeTitle = parent.getChild(new UiSelector().index(2));

		assertTrue("Filesize title view doesn't exist", filesizeTitle.exists());
		assertTrue("Filesize title view isn't enabled", filesizeTitle.isEnabled());
		assertEquals("Filesize title view isn't a TextView", "android.widget.TextView",
				filesizeTitle.getClassName());
		assertEquals("Filesize title is empty string","Filesize:", filesizeTitle.getText());
	}
	
	/**
	 * Tests whether the video info screen contains the seeders TextView
	 * 
	 * @throws RemoteException
	 * @throws UiObjectNotFoundException
	 */
	public void testVideoInfoSeedersTitle() throws RemoteException,
			UiObjectNotFoundException {
		openVideoInfoScreen();

		UiObject infoView = new UiObject(
				new UiSelector().className("android.widget.RelativeLayout"));
		UiObject parent = infoView.getChild(new UiSelector().index(1));
		UiObject seedersTitle = parent.getChild(new UiSelector().index(3));

		assertTrue("Seeders title view doesn't exist", seedersTitle.exists());
		assertTrue("Seeders title view isn't enabled", seedersTitle.isEnabled());
		assertEquals("Seeders title view isn't a TextView", "android.widget.TextView",
				seedersTitle.getClassName());
		assertEquals("Seeders title is empty string","Seeders:", seedersTitle.getText());
	}
	
	/**
	 * Tests whether the video info screen contains the leechers TextView
	 * 
	 * @throws RemoteException
	 * @throws UiObjectNotFoundException
	 */
	public void testVideoInfoLeechersTitle() throws RemoteException,
			UiObjectNotFoundException {
		openVideoInfoScreen();

		UiObject infoView = new UiObject(
				new UiSelector().className("android.widget.RelativeLayout"));
		UiObject parent = infoView.getChild(new UiSelector().index(1));
		UiObject leechersTitle = parent.getChild(new UiSelector().index(4));

		assertTrue("Leechers title view doesn't exist", leechersTitle.exists());
		assertTrue("Leechers title view isn't enabled", leechersTitle.isEnabled());
		assertEquals("Leechers title view isn't a TextView", "android.widget.TextView",
				leechersTitle.getClassName());
		assertEquals("Leechers title is empty string","Leechers:", leechersTitle.getText());
	}

	/**
	 * Launches the app, opens the navigation drawer and click the home item to
	 * load the thumb grid and then clicks the first item
	 * 
	 * @throws RemoteException
	 * @throws UiObjectNotFoundException
	 */
	private void openVideoInfoScreen() throws RemoteException,
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

		UiCollection grid = new UiCollection(
				new UiSelector().className("android.widget.GridView"));
		UiObject firstItem = grid.getChild(new UiSelector().index(0));
		firstItem.click();
	}

}
