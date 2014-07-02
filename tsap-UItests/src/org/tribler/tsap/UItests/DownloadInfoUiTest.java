package org.tribler.tsap.UItests;

import android.os.RemoteException;

import com.android.uiautomator.core.UiCollection;
import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.core.UiScrollable;
import com.android.uiautomator.core.UiSelector;

/**
 * UI test cases for the download info screen
 * 
 * @author Niels Spruit
 */
public class DownloadInfoUiTest extends BasicUiTestCase {

	/**
	 * Tests whether the download info view exists and is enabled
	 * 
	 * @throws RemoteException
	 * @throws UiObjectNotFoundException
	 */
	public void testDownloadInfoViewExists() throws RemoteException,
			UiObjectNotFoundException {
		openDownloadInfoScreen();
		UiObject infoView = new UiObject(
				new UiSelector().className("android.widget.RelativeLayout"));
		assertTrue("Download info view doesn't exist", infoView.exists());
		assertTrue("Download info view isn't enabled", infoView.isEnabled());
	}

	/**
	 * Tests whether the download info screen contains the size TextView
	 * 
	 * @throws RemoteException
	 * @throws UiObjectNotFoundException
	 */
	public void testDownloadInfoSizeTitle() throws RemoteException,
			UiObjectNotFoundException {
		openDownloadInfoScreen();

		UiObject infoView = new UiObject(
				new UiSelector().className("android.widget.RelativeLayout"));
		UiObject parent = infoView.getChild(new UiSelector().index(0));
		UiObject filesizeTitle = parent.getChild(new UiSelector().index(0));

		assertTrue("Filesize title view doesn't exist", filesizeTitle.exists());
		assertTrue("Filesize title view isn't enabled",
				filesizeTitle.isEnabled());
		assertEquals("Filesize title view isn't a TextView",
				"android.widget.TextView", filesizeTitle.getClassName());
		assertEquals("Filesize title is incorrect string", "Size:",
				filesizeTitle.getText());
	}

	/**
	 * Tests whether the download info screen contains the speed down TextView
	 * 
	 * @throws RemoteException
	 * @throws UiObjectNotFoundException
	 */
	public void testDownloadInfoSpeedDownTitle() throws RemoteException,
			UiObjectNotFoundException {
		openDownloadInfoScreen();

		UiObject infoView = new UiObject(
				new UiSelector().className("android.widget.RelativeLayout"));
		UiObject parent = infoView.getChild(new UiSelector().index(0));
		UiObject speedDownTitle = parent.getChild(new UiSelector().index(1));

		assertTrue("Speed down title view doesn't exist",
				speedDownTitle.exists());
		assertTrue("Speed down title view isn't enabled",
				speedDownTitle.isEnabled());
		assertEquals("Speed down title view isn't a TextView",
				"android.widget.TextView", speedDownTitle.getClassName());
		assertEquals("Speed down title is incorrect string", "Speed down:",
				speedDownTitle.getText());
	}

	/**
	 * Tests whether the download info screen contains the speed up TextView
	 * 
	 * @throws RemoteException
	 * @throws UiObjectNotFoundException
	 */
	public void testDownloadInfoSpeedUpTitle() throws RemoteException,
			UiObjectNotFoundException {
		openDownloadInfoScreen();

		UiObject infoView = new UiObject(
				new UiSelector().className("android.widget.RelativeLayout"));
		UiObject parent = infoView.getChild(new UiSelector().index(0));
		UiObject speedUpTitle = parent.getChild(new UiSelector().index(2));

		assertTrue("Speed up title view doesn't exist", speedUpTitle.exists());
		assertTrue("Speed up title view isn't enabled",
				speedUpTitle.isEnabled());
		assertEquals("Speed up title view isn't a TextView",
				"android.widget.TextView", speedUpTitle.getClassName());
		assertEquals("Speed up title is incorrect string", "Speed up:",
				speedUpTitle.getText());
	}

	/**
	 * Tests whether the download info screen contains the availability TextView
	 * 
	 * @throws RemoteException
	 * @throws UiObjectNotFoundException
	 */
	public void testDownloadInfoAvailabilityTitle() throws RemoteException,
			UiObjectNotFoundException {
		openDownloadInfoScreen();

		UiObject infoView = new UiObject(
				new UiSelector().className("android.widget.RelativeLayout"));
		UiObject parent = infoView.getChild(new UiSelector().index(0));
		UiObject availabilityTitle = parent.getChild(new UiSelector().index(3));

		assertTrue("Availability title view doesn't exist",
				availabilityTitle.exists());
		assertTrue("Availability title view isn't enabled",
				availabilityTitle.isEnabled());
		assertEquals("Availability title view isn't a TextView",
				"android.widget.TextView", availabilityTitle.getClassName());
		assertEquals("Availability title is incorrect string", "Availability",
				availabilityTitle.getText());
	}

	/**
	 * Tests whether the download info screen contains the size info TextView
	 * 
	 * @throws RemoteException
	 * @throws UiObjectNotFoundException
	 */
	public void testDownloadInfoSize() throws RemoteException,
			UiObjectNotFoundException {
		openDownloadInfoScreen();

		UiObject infoView = new UiObject(
				new UiSelector().className("android.widget.RelativeLayout"));
		UiObject parent = infoView.getChild(new UiSelector().index(1));
		UiObject filesize = parent.getChild(new UiSelector().index(0));

		assertTrue("Filesize view doesn't exist", filesize.exists());
		assertTrue("Filesize view isn't enabled", filesize.isEnabled());
		assertEquals("Filesize view isn't a TextView",
				"android.widget.TextView", filesize.getClassName());
		assertFalse("Filesize is empty string", filesize.getText().equals(""));
	}

	/**
	 * Tests whether the download info screen contains the speed down info
	 * TextView
	 * 
	 * @throws RemoteException
	 * @throws UiObjectNotFoundException
	 */
	public void testDownloadInfoSpeedDown() throws RemoteException,
			UiObjectNotFoundException {
		openDownloadInfoScreen();

		UiObject infoView = new UiObject(
				new UiSelector().className("android.widget.RelativeLayout"));
		UiObject parent = infoView.getChild(new UiSelector().index(1));
		UiObject speedDown = parent.getChild(new UiSelector().index(1));

		assertTrue("Speed down view doesn't exist", speedDown.exists());
		assertTrue("Speed down view isn't enabled", speedDown.isEnabled());
		assertEquals("Speed down view isn't a TextView",
				"android.widget.TextView", speedDown.getClassName());
		assertFalse("Speed down is empty string", speedDown.getText()
				.equals(""));
	}

	/**
	 * Tests whether the download info screen contains the speed up info
	 * TextView
	 * 
	 * @throws RemoteException
	 * @throws UiObjectNotFoundException
	 */
	public void testDownloadInfoSpeedUp() throws RemoteException,
			UiObjectNotFoundException {
		openDownloadInfoScreen();

		UiObject infoView = new UiObject(
				new UiSelector().className("android.widget.RelativeLayout"));
		UiObject parent = infoView.getChild(new UiSelector().index(1));
		UiObject speedUp = parent.getChild(new UiSelector().index(2));

		assertTrue("Speed up view doesn't exist", speedUp.exists());
		assertTrue("Speed up view isn't enabled", speedUp.isEnabled());
		assertEquals("Speed up view isn't a TextView",
				"android.widget.TextView", speedUp.getClassName());
		assertFalse("Speed up is empty string", speedUp.getText().equals(""));
	}

	/**
	 * Tests whether the download info screen contains the speed up info
	 * TextView
	 * 
	 * @throws RemoteException
	 * @throws UiObjectNotFoundException
	 */
	public void testDownloadInfoAvailability() throws RemoteException,
			UiObjectNotFoundException {
		openDownloadInfoScreen();

		UiObject infoView = new UiObject(
				new UiSelector().className("android.widget.RelativeLayout"));
		UiObject parent = infoView.getChild(new UiSelector().index(1));
		UiObject availability = parent.getChild(new UiSelector().index(3));

		assertTrue("Availability view doesn't exist", availability.exists());
		assertTrue("Availability view isn't enabled", availability.isEnabled());
		assertEquals("Availability view isn't a TextView",
				"android.widget.TextView", availability.getClassName());
		assertFalse("Availability is empty string", availability.getText()
				.equals(""));
	}

	/**
	 * Tests whether the download info screen contains a thumbnail ImageView
	 * 
	 * @throws RemoteException
	 * @throws UiObjectNotFoundException
	 */
	public void testDownloadInfoThumbnail() throws RemoteException,
			UiObjectNotFoundException {
		openDownloadInfoScreen();

		UiObject thumbnail = new UiObject(
				new UiSelector().description("Video info thumbnail"));

		assertTrue("Thumbnail view doesn't exist", thumbnail.exists());
		assertTrue("Thumbnail view isn't enabled", thumbnail.isEnabled());
		assertEquals("Thumbnail view isn't a ImageView",
				"android.widget.ImageView", thumbnail.getClassName());
	}

	/**
	 * Tests whether the download info screen contains the status TextView
	 * 
	 * @throws RemoteException
	 * @throws UiObjectNotFoundException
	 */
	public void testDownloadInfoStatusTitle() throws RemoteException,
			UiObjectNotFoundException {
		openDownloadInfoScreen();

		UiObject infoView = new UiObject(
				new UiSelector().className("android.widget.RelativeLayout"));
		UiObject parent = infoView.getChild(new UiSelector().index(3));
		UiObject statusTitle = parent.getChild(new UiSelector().index(0));

		assertTrue("Status title view doesn't exist", statusTitle.exists());
		assertTrue("Status title view isn't enabled", statusTitle.isEnabled());
		assertEquals("Status title view isn't a TextView",
				"android.widget.TextView", statusTitle.getClassName());
		assertEquals("Status title is incorrect string", "Status:",
				statusTitle.getText());
	}

	/**
	 * Tests whether the download info screen contains the ETA TextView
	 * 
	 * @throws RemoteException
	 * @throws UiObjectNotFoundException
	 */
	public void testDownloadInfoETATitle() throws RemoteException,
			UiObjectNotFoundException {
		openDownloadInfoScreen();

		UiObject infoView = new UiObject(
				new UiSelector().className("android.widget.RelativeLayout"));
		UiObject parent = infoView.getChild(new UiSelector().index(3));
		UiObject etaTitle = parent.getChild(new UiSelector().index(1));

		assertTrue("ETA title view doesn't exist", etaTitle.exists());
		assertTrue("ETA title view isn't enabled", etaTitle.isEnabled());
		assertEquals("ETA title view isn't a TextView",
				"android.widget.TextView", etaTitle.getClassName());
		assertEquals("ETA title is incorrect string", "ETA:",
				etaTitle.getText());
	}

	/**
	 * Tests whether the download info screen contains the status info TextView
	 * 
	 * @throws RemoteException
	 * @throws UiObjectNotFoundException
	 */
	public void testDownloadInfoStatus() throws RemoteException,
			UiObjectNotFoundException {
		openDownloadInfoScreen();

		UiObject infoView = new UiObject(
				new UiSelector().className("android.widget.RelativeLayout"));
		UiObject parent = infoView.getChild(new UiSelector().index(4));
		UiObject status = parent.getChild(new UiSelector().index(0));

		assertTrue("Status view doesn't exist", status.exists());
		assertTrue("Status view isn't enabled", status.isEnabled());
		assertEquals("Status view isn't a TextView", "android.widget.TextView",
				status.getClassName());
		assertFalse("Status is empty string", status.getText().equals(""));
	}

	/**
	 * Tests whether the download info screen contains the ETA info TextView
	 * 
	 * @throws RemoteException
	 * @throws UiObjectNotFoundException
	 */
	public void testDownloadInfoETA() throws RemoteException,
			UiObjectNotFoundException {
		openDownloadInfoScreen();

		UiObject infoView = new UiObject(
				new UiSelector().className("android.widget.RelativeLayout"));
		UiObject parent = infoView.getChild(new UiSelector().index(4));
		UiObject eta = parent.getChild(new UiSelector().index(1));

		assertTrue("ETA view doesn't exist", eta.exists());
		assertTrue("ETA view isn't enabled", eta.isEnabled());
		assertEquals("ETA view isn't a TextView", "android.widget.TextView",
				eta.getClassName());
		assertFalse("ETA is empty string", eta.getText().equals(""));
	}

	/**
	 * Tests whether the download info screen contains a ProgressBar
	 * 
	 * @throws RemoteException
	 * @throws UiObjectNotFoundException
	 */
	public void testDownloadInfoProgress() throws RemoteException,
			UiObjectNotFoundException {
		openDownloadInfoScreen();

		UiObject progress = new UiObject(
				new UiSelector().className("android.widget.ProgressBar"));

		assertTrue("Progress bar doesn't exist", progress.exists());
		assertTrue("Progress bar isn't enabled", progress.isEnabled());
	}

	/**
	 * Tests whether the download info screen contains a 'Play video' Button
	 * with the correct properties
	 * 
	 * @throws RemoteException
	 * @throws UiObjectNotFoundException
	 */
	public void testDownloadInfoPlayButtonProperties() throws RemoteException,
			UiObjectNotFoundException {
		openDownloadInfoScreen();

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
	 * Tests whether the download info screen contains a 'Remove download'
	 * Button with the correct properties
	 * 
	 * @throws RemoteException
	 * @throws UiObjectNotFoundException
	 */
	public void testDownloadInfoRemoveButtonProperties()
			throws RemoteException, UiObjectNotFoundException {
		openDownloadInfoScreen();

		UiObject removeButton = new UiObject(
				new UiSelector().text("Remove download"));

		assertTrue("Remove button doesn't exist", removeButton.exists());
		assertTrue("Remove button isn't enabled", removeButton.isEnabled());
		assertEquals("Remove button isn't a Button", "android.widget.Button",
				removeButton.getClassName());
		assertTrue("Remove button isn't clickable", removeButton.isClickable());
		assertEquals("Remove button text is incorrect", "Remove download",
				removeButton.getText());
	}

	/**
	 * Tests whether the download info screen contains a non-empty description
	 * TextView
	 * 
	 * @throws RemoteException
	 * @throws UiObjectNotFoundException
	 */
	public void testDownloadInfoDescription() throws RemoteException,
			UiObjectNotFoundException {
		openDownloadInfoScreen();

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
	 * Tests whether the download info screen doesn't contain the search option
	 * 
	 * @throws RemoteException
	 * @throws UiObjectNotFoundException
	 */
	public void testDownloadInfoSearchDoesNotExists() throws RemoteException,
			UiObjectNotFoundException {
		openDownloadInfoScreen();

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
	public void testDownloadInfoPlayButtonClick() throws RemoteException,
			UiObjectNotFoundException {
		openDownloadInfoScreen();

		UiObject playButton = new UiObject(
				new UiSelector().description("Play video"));

		assertTrue("Play button click failed",
				playButton.clickAndWaitForNewWindow());
		assertFalse("Play button exists after clicking it", playButton.exists());

		UiObject waitText = new UiObject(new UiSelector().text("Please wait…"));
		assertTrue("VLC wait text doesn't exist", waitText.exists());
		assertTrue("VLC wait text isn't enabled", waitText.isEnabled());

		// navigate back to download info screen
		getUiDevice().pressBack();
	}

	/**
	 * Launches the app, opens the navigation drawer and clicks the downloads
	 * item to load the download list and then clicks the first item
	 * 
	 * @throws RemoteException
	 * @throws UiObjectNotFoundException
	 */
	protected void openDownloadInfoScreen() throws RemoteException,
			UiObjectNotFoundException {
		startTSAP();

		UiObject navDrawer = new UiObject(
				new UiSelector().description("navigation_drawer"));
		if (!navDrawer.exists()) {
			UiObject upButton = new UiObject(
					new UiSelector().description("Navigate up"));
			upButton.click();
		}

		UiObject downloadsItem = navDrawer.getChild(new UiSelector()
				.text("Downloads"));
		downloadsItem.click();

		UiCollection list = new UiCollection(
				new UiSelector().className("android.widget.ListView"));
		UiObject firstItem = list.getChild(new UiSelector().index(0));
		firstItem.click();
	}

	/**
	 * Navigates back to the download list to make the next test run correctly
	 */
	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
		getUiDevice().pressBack();
	}

}
