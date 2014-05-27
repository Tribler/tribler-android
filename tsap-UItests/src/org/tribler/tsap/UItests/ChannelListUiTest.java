package org.tribler.tsap.UItests;

import android.os.RemoteException;

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
	 * Launches the app, opens the navigation drawer and click the channels item to
	 * load the channel list
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

		UiObject channelsItem = navDrawer.getChild(new UiSelector().text("Channels"));
		channelsItem.click();
	}

}
