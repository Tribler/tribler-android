package org.tribler.tsap.settings;

import java.net.URL;

import org.tribler.tsap.XMLRPCCallTask;

import android.util.Log;
import de.timroes.axmlrpc.XMLRPCClient;
import de.timroes.axmlrpc.XMLRPCException;

/**
 * Singleton class for adding and accessing downloads. 
 * @author Dirk Schut
 */
public class XMLRPCSettingsManager {

	protected XMLRPCClient mClient;
	
	public XMLRPCSettingsManager(URL url) {
		mClient = new XMLRPCClient(url);
	}
	
	public void setFamilyFilter(boolean onOff) {
		XMLRPCCallTask task = new XMLRPCCallTask() {
			@Override
			protected void onPostExecute(Object result) {
				if (result instanceof XMLRPCException) {
					Log.e("XMLRPCSettingsManager", "Could not set the family filter.");
				}
			}
		};
		task.execute(mClient, "settings.set_family_filter", onOff);
	}
	
	public void getThumbFolder() {
		XMLRPCCallTask task = new XMLRPCCallTask() {
			@Override
			protected void onPostExecute(Object result) {
				if (!(result instanceof XMLRPCException)) {
					Settings.XMLRPCSetThumbFolder((String)result);
				}
				else {
					Log.e("XMLRPCSettingsManager", "Could not get the thumb folder.");
				}
			}
		};
		task.execute(mClient, "settings.get_thumb_folder");
	}
}