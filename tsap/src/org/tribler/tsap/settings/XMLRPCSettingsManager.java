package org.tribler.tsap.settings;


import org.tribler.tsap.XMLRPC.XMLRPCCallTask;
import org.tribler.tsap.XMLRPC.XMLRPCConnection;

/**
 * Singleton class for adding and accessing downloads. 
 * @author Dirk Schut
 */
public class XMLRPCSettingsManager {

	protected XMLRPCConnection mConnection;
	
	public XMLRPCSettingsManager(XMLRPCConnection connection) {
		mConnection = connection;
	}
	
	public void setFamilyFilter(boolean onOff) {
		new XMLRPCCallTask().call("settings.set_family_filter", mConnection, onOff);
	}
}