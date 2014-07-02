package org.tribler.tsap.settings;


import org.tribler.tsap.R;
import org.tribler.tsap.XMLRPC.XMLRPCCallTask;
import org.tribler.tsap.XMLRPC.XMLRPCConnection;

import android.app.Activity;
import android.widget.Toast;

/**
 * Singleton class for adding and accessing downloads. 
 * @author Dirk Schut
 */
public class XMLRPCSettingsManager {

	protected XMLRPCConnection mConnection;
	private Activity mActivity;
	
	public XMLRPCSettingsManager(XMLRPCConnection connection, Activity activity) {
		mConnection = connection;
		mActivity = activity;
	}
	
	public void setFamilyFilter(boolean onOff) {
		new XMLRPCCallTask() {
			@Override
			public void onSucces(Object result) {
				if(!(Boolean)result) {
					Toast.makeText(mActivity, R.string.settings_manager_fail_familyfilter, Toast.LENGTH_LONG).show();
				}
			}
		}.call("settings.set_family_filter", mConnection, onOff);
	}
	public void setMaxDownloadSpeed(int maxSpeed) {
		new XMLRPCCallTask() {
			@Override
			public void onSucces(Object result) {
				if(!(Boolean)result) {
					Toast.makeText(mActivity, R.string.settings_manager_fail_download, Toast.LENGTH_LONG).show();
				}
			}
		}.call("settings.set_max_download", mConnection, maxSpeed);
	}
	public void setMaxUploadSpeed(int maxSpeed) {
		new XMLRPCCallTask() {
			@Override
			public void onSucces(Object result) {
				if(!(Boolean)result) {
					Toast.makeText(mActivity, R.string.settings_manager_fail_upload, Toast.LENGTH_LONG).show();
				}
			}
		}.call("settings.set_max_upload", mConnection, maxSpeed);
	}
}