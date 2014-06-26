package org.tribler.tsap.settings;

import java.io.File;

import org.tribler.tsap.XMLRPC.XMLRPCCallTask;
import org.tribler.tsap.XMLRPC.XMLRPCConnection;

import android.util.Log;
import de.timroes.axmlrpc.XMLRPCException;
/**
 * Singleton class for adding and accessing downloads. 
 * @author Dirk Schut
 */
public class XMLRPCThumbFolderManager {

	protected XMLRPCConnection mConnection;
	
	public XMLRPCThumbFolderManager(XMLRPCConnection connection) {
		mConnection = connection;
	}
	
	public void getThumbFolder() {
		new XMLRPCCallTask() {
			@Override
			public void onSucces(Object result) {
					Settings.XMLRPCSetThumbFolder(new File((String)result));
					Log.e("", "added thumbs folder:" +Settings.getThumbFolder().getPath());
				}
			@Override
			public void onFailure(XMLRPCException result) {
					Log.e("", "adding thumbs folder failed");
				}
		}.call("settings.get_thumbs_directory", mConnection);
	}
}