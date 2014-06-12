package org.tribler.tsap.UItests;

import android.os.RemoteException;

import com.android.uiautomator.core.UiCollection;
import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.core.UiScrollable;
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
		assertEquals("Type title view isn't a TextView",
				"android.widget.TextView", typeTitle.getClassName());
		assertEquals("Type title is incorrect string", "Type:",
				typeTitle.getText());
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
		assertTrue("Uploaded title view isn't enabled",
				uploadedTitle.isEnabled());
		assertEquals("Uploaded title view isn't a TextView",
				"android.widget.TextView", uploadedTitle.getClassName());
		assertEquals("Uploaded title is incorrect string", "Uploaded:",
				uploadedTitle.getText());
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
		assertTrue("Filesize title view isn't enabled",
				filesizeTitle.isEnabled());
		assertEquals("Filesize title view isn't a TextView",
				"android.widget.TextView", filesizeTitle.getClassName());
		assertEquals("Filesize title is incorrect string", "Filesize:",
				filesizeTitle.getText());
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
		assertEquals("Seeders title view isn't a TextView",
				"android.widget.TextView", seedersTitle.getClassName());
		assertEquals("Seeders title is incorrect string", "Seeders:",
				seedersTitle.getText());
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
		assertTrue("Leechers title view isn't enabled",
				leechersTitle.isEnabled());
		assertEquals("Leechers title view isn't a TextView",
				"android.widget.TextView", leechersTitle.getClassName());
		assertEquals("Leechers title is incorrect string", "Leechers:",
				leechersTitle.getText());
	}

	/**
	 * Tests whether the video info screen contains the type info TextView
	 * 
	 * @throws RemoteException
	 * @throws UiObjectNotFoundException
	 */
	public void testVideoInfoType() throws RemoteException,
			UiObjectNotFoundException {
		openVideoInfoScreen();

		UiObject infoView = new UiObject(
				new UiSelector().className("android.widget.RelativeLayout"));
		UiObject parent = infoView.getChild(new UiSelector().index(2));
		UiObject type = parent.getChild(new UiSelector().index(0));

		assertTrue("Type view doesn't exist", type.exists());
		assertTrue("Type view isn't enabled", type.isEnabled());
		assertEquals("Type view isn't a TextView", "android.widget.TextView",
				type.getClassName());
		assertFalse("Type is empty string", type.getText().equals(""));
	}

	/**
	 * Tests whether the video info screen contains the uploaded info TextView
	 * 
	 * @throws RemoteException
	 * @throws UiObjectNotFoundException
	 */
	public void testVideoInfoUploaded() throws RemoteException,
			UiObjectNotFoundException {
		openVideoInfoScreen();

		UiObject infoView = new UiObject(
				new UiSelector().className("android.widget.RelativeLayout"));
		UiObject parent = infoView.getChild(new UiSelector().index(2));
		UiObject uploaded = parent.getChild(new UiSelector().index(1));

		assertTrue("Uploaded view doesn't exist", uploaded.exists());
		assertTrue("Uploaded view isn't enabled", uploaded.isEnabled());
		assertEquals("Uploaded view isn't a TextView",
				"android.widget.TextView", uploaded.getClassName());
		assertFalse("Uploaded is empty string", uploaded.getText().equals(""));
	}

	/**
	 * Tests whether the video info screen contains the filesize info TextView
	 * 
	 * @throws RemoteException
	 * @throws UiObjectNotFoundException
	 */
	public void testVideoInfoFilesize() throws RemoteException,
			UiObjectNotFoundException {
		openVideoInfoScreen();

		UiObject infoView = new UiObject(
				new UiSelector().className("android.widget.RelativeLayout"));
		UiObject parent = infoView.getChild(new UiSelector().index(2));
		UiObject filesize = parent.getChild(new UiSelector().index(2));

		assertTrue("Filesize view doesn't exist", filesize.exists());
		assertTrue("Filesize view isn't enabled", filesize.isEnabled());
		assertEquals("Filesize view isn't a TextView",
				"android.widget.TextView", filesize.getClassName());
		assertFalse("Filesize is empty string", filesize.getText().equals(""));
	}

	/**
	 * Tests whether the video info screen contains the seeders info TextView
	 * 
	 * @throws RemoteException
	 * @throws UiObjectNotFoundException
	 */
	public void testVideoInfoSeeders() throws RemoteException,
			UiObjectNotFoundException {
		openVideoInfoScreen();

		UiObject infoView = new UiObject(
				new UiSelector().className("android.widget.RelativeLayout"));
		UiObject parent = infoView.getChild(new UiSelector().index(2));
		UiObject seeders = parent.getChild(new UiSelector().index(3));

		assertTrue("Seeders view doesn't exist", seeders.exists());
		assertTrue("Seeders view isn't enabled", seeders.isEnabled());
		assertEquals("Seeders view isn't a TextView",
				"android.widget.TextView", seeders.getClassName());
		assertFalse("Seeders is empty string", seeders.getText().equals(""));
	}

	/**
	 * Tests whether the video info screen contains the leechers info TextView
	 * 
	 * @throws RemoteException
	 * @throws UiObjectNotFoundException
	 */
	public void testVideoInfoLeechers() throws RemoteException,
			UiObjectNotFoundException {
		openVideoInfoScreen();

		UiObject infoView = new UiObject(
				new UiSelector().className("android.widget.RelativeLayout"));
		UiObject parent = infoView.getChild(new UiSelector().index(2));
		UiObject leechers = parent.getChild(new UiSelector().index(4));

		assertTrue("Leechers view doesn't exist", leechers.exists());
		assertTrue("Leechers view isn't enabled", leechers.isEnabled());
		assertEquals("Leechers view isn't a TextView",
				"android.widget.TextView", leechers.getClassName());
		assertFalse("Leechers is empty string", leechers.getText().equals(""));
	}

	/**
	 * Tests whether the video info screen contains a thumbnail ImageView
	 * 
	 * @throws RemoteException
	 * @throws UiObjectNotFoundException
	 */
	public void testVideoInfoThumbnail() throws RemoteException,
			UiObjectNotFoundException {
		openVideoInfoScreen();

		UiObject thumbnail = new UiObject(
				new UiSelector().description("Video info thumbnail"));

		assertTrue("Thumbnail view doesn't exist", thumbnail.exists());
		assertTrue("Thumbnail view isn't enabled", thumbnail.isEnabled());
		assertEquals("Thumbnail view isn't a ImageView",
				"android.widget.ImageView", thumbnail.getClassName());
	}

	/**
	 * Tests whether the video info screen contains a 'Play video' Button with
	 * the correct properties
	 * 
	 * @throws RemoteException
	 * @throws UiObjectNotFoundException
	 */
	public void testVideoInfoPlayButtonProperties() throws RemoteException,
			UiObjectNotFoundException {
		openVideoInfoScreen();

		UiObject playButton = new UiObject(
				new UiSelector().description("Play video"));

		assertTrue("Play button doesn't exist", playButton.exists());
		assertTrue("Play button isn't enabled", playButton.isEnabled());
		assertEquals("Play button isn't a Button", "android.widget.Button",
				playButton.getClassName());
		assertTrue("Play button isn't clickable", playButton.isClickable());
		assertEquals("Play button text is incorrect", "Play video",
				playButton.getText());
	}

	/**
	 * Tests whether the video info screen contains a 'Download video' Button
	 * with the correct properties
	 * 
	 * @throws RemoteException
	 * @throws UiObjectNotFoundException
	 */
	public void testVideoInfoDownloadButtonProperties() throws RemoteException,
			UiObjectNotFoundException {
		openVideoInfoScreen();

		UiObject downloadButton = new UiObject(
				new UiSelector().description("Download video"));

		assertTrue("Download button doesn't exist", downloadButton.exists());
		assertTrue("Download button isn't enabled", downloadButton.isEnabled());
		assertEquals("Download button isn't a Button", "android.widget.Button",
				downloadButton.getClassName());
		assertTrue("Download button isn't clickable",
				downloadButton.isClickable());
		assertEquals("Download button text is incorrect", "Download video",
				downloadButton.getText());
	}

	/**
	 * Tests whether the video info screen contains a non-empty description
	 * TextView
	 * 
	 * @throws RemoteException
	 * @throws UiObjectNotFoundException
	 */
	public void testVideoInfoDescription() throws RemoteException,
			UiObjectNotFoundException {
		openVideoInfoScreen();

		// scroll down
		UiScrollable scrollview = new UiScrollable(
				new UiSelector().className("android.widget.ScrollView"));
		scrollview.scrollToEnd(20);

		UiObject description = new UiObject(
				new UiSelector().description("Video description"));

		assertTrue("Description view doesn't exist", description.exists());
		assertTrue("Description view isn't enabled", description.isEnabled());
		assertEquals("Description view isn't a TextView",
				"android.widget.TextView", description.getClassName());
		assertFalse("Description is empty string", description.getText()
				.equals(""));
	}

	/**
	 * Tests whether the video info screen doesn't contain the search option
	 * 
	 * @throws RemoteException
	 * @throws UiObjectNotFoundException
	 */
	public void testVideoInfoSearchDoesNotExists() throws RemoteException,
			UiObjectNotFoundException {
		openVideoInfoScreen();

		UiObject search = new UiObject(new UiSelector().description("Search"));

		assertFalse("Search view exists", search.exists());
	}

	/**
	 * Tests whether the 'Play video' Button behaves correctly when it is
	 * pressed (=opens VLC)
	 * 
	 * @throws RemoteException
	 * @throws UiObjectNotFoundException
	 */
	public void testVideoInfoPlayButtonClick() throws RemoteException,
			UiObjectNotFoundException {
		openVideoInfoScreen();

		UiObject playButton = new UiObject(
				new UiSelector().description("Play video"));

		assertTrue("Play button click failed",
				playButton.clickAndWaitForNewWindow());
		assertFalse("Play button exists after clicking it", playButton.exists());

		UiObject waitText = new UiObject(new UiSelector().text("Please wait…"));
		assertTrue("VLC wait text doesn't exist", waitText.exists());
		assertTrue("VLC wait text isn't enabled", waitText.isEnabled());

		// navigate back to video info screen
		getUiDevice().pressBack();
	}

	/**
	 * Launches the app, opens the navigation drawer and click the home item to
	 * load the thumb grid and then clicks the first item
	 * 
	 * @throws RemoteException
	 * @throws UiObjectNotFoundException
	 */
	protected void openVideoInfoScreen() throws RemoteException,
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

	/**
	 * Navigates back to the thumb grid view to make the next test run correctly
	 */
	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
		getUiDevice().pressBack();
	}

}
