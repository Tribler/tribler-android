package org.tribler.tsap;

import java.net.URL;
import java.util.Observer;

import android.util.Log;
import de.timroes.axmlrpc.XMLRPCClient;

/**
 * Abstract class for connecting to an XMLRPC server through aXMLRPC. It
 * provides functionality for setting up the connection and for polling
 * regularly for search results.
 * 
 * @author Dirk Schut
 * 
 */
public abstract class AbstractXMLRPCManager implements Observer {
	protected XMLRPCClient mClient;
	private Poller mPoller = new Poller(this);

	/**
	 * Constructor: Makes a connection with an XMLRPC server and starts a
	 * polling loop
	 * 
	 * @param url
	 *            The url of the XMLRPC server
	 */
	public AbstractXMLRPCManager(URL url) {
		mClient = new XMLRPCClient(url);
		// logAvailableFunctions();
	}

	/**
	 * Retrieves all functions that are callable with XMLRPC and writes them to
	 * the log
	 */
	public void logAvailableFunctions() {
		XMLRPCCallTask task = new XMLRPCCallTask() {
			@Override
			protected void onPostExecute(Object result) {
				Object[] arrayResult = (Object[]) result;
				Log.i("XMLRPC", "Listing available functions");
				for (int i = 0; i < arrayResult.length; i++) {
					Log.i("XMLRPC", "    " + (String) arrayResult[i]);
				}
			}
		};
		task.execute(mClient, "system.listMethods");
	}

	/**
	 * Starts polling for search results
	 */
	public void startPolling() {
		mPoller.start();
	}

	/**
	 * Stops polling for search results
	 */
	public void stopPolling() {
		mPoller.pause();
	}
}