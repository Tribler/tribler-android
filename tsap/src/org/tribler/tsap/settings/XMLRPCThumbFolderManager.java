package org.tribler.tsap.settings;

import java.io.File;

import org.tribler.tsap.XMLRPC.XMLRPCCallTask;
import org.tribler.tsap.XMLRPC.XMLRPCConnection;

import android.util.Log;
import de.timroes.axmlrpc.XMLRPCException;

/**
 * Singleton class for adding and accessing downloads.
 * 
 * @author Dirk Schut
 */
public class XMLRPCThumbFolderManager implements
		XMLRPCConnection.IConnectionListener {

	protected XMLRPCConnection mConnection;

	/**
	 * Constructor
	 * 
	 * @param connection
	 *            The connection to the Tribler service
	 */
	public XMLRPCThumbFolderManager(XMLRPCConnection connection) {
		mConnection = connection;
		mConnection.addListener(this);
	}

	/**
	 * Makes an XML-RPC to Tribler to get the location of the thumbnail
	 * directory
	 */
	private void getThumbFolder() {
		new XMLRPCCallTask() {
			@Override
			public void onSucces(Object result) {
				Settings.XMLRPCSetThumbFolder(new File((String) result));
				Log.e("", "added thumbs folder:"
						+ Settings.getThumbFolder().getPath());
			}

			@Override
			public void onFailure(XMLRPCException result) {
				Log.e("", "adding thumbs folder failed");
			}
		}.call("settings.get_thumbs_directory", mConnection);
	}

	/**
	 * Is called when the connection with the Tribler service is establishes:
	 * retrieves the thumbnail folder location
	 */
	@Override
	public void onConnectionEstablished() {
		getThumbFolder();
	}

	/**
	 * Is called when the connection with the Tribler service is lost
	 */
	@Override
	public void onConnectionLost() {
		// do nothing
	}
}