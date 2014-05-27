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
