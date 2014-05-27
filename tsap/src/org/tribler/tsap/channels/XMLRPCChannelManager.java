package org.tribler.tsap.channels;

import java.net.URL;
import java.util.ArrayList;
import java.util.Map;

import android.util.Log;
import de.timroes.axmlrpc.XMLRPCClient;

import org.tribler.tsap.XMLRPCCallTask;

/**
 * Class for receiving channels over XMPRPC using the aXMLRPC library
 * 
 * @author Dirk Schut
 * @since 26-5-2014
 */
class XMLRPCChannelManager extends AbstractChannelManager {
	private XMLRPCClient mClient = null;

	/**
	 * Constructor: Makes a connection with an XMLRPC server
	 * 
	 * @param url
	 *            The url of the XMLRPC server
	 */
	XMLRPCChannelManager(URL url) {
		mClient = new XMLRPCClient(url);
		logAvailableFunctions();
	}

	/**
	 * Retrieves all functions that are callable with XMLRPC and writes them to
	 * the log
	 */
	private void logAvailableFunctions() {
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
	 * Searches the local dispersy data for channels fitting a certain query.
	 * Once the results are found it will send them as an ArrayList<Channel> to
	 * all observers.
	 * 
	 * @param query
	 *            The query that Tribler will look for in the names of the
	 *            channels
	 */
	@Override
	void getLocal(final String query) {
		XMLRPCCallTask task = new XMLRPCCallTask() {
			@Override
			protected void onPostExecute(Object result) {
				Object[] arrayResult = (Object[]) result;
				ArrayList<Channel> resultsList = new ArrayList<Channel>();
				Log.v("XMLRPC", "Got " + arrayResult.length + "results");
				for (int i = 0; i < arrayResult.length; i++) {
					@SuppressWarnings("unchecked")
					Channel c = new Channel(
							(Map<String, Object>) arrayResult[i]);
					resultsList.add(c);
				}
				setChanged();
				notifyObservers(resultsList);
				Log.v("XMLRPC", "Observers notified!");
			}
		};
		task.execute(mClient, "channels.get_local", query);
	}

	/**
	 * Searches the remote dispersy data for channels fitting a certain query.
	 * If there are enough peers for a search it will send true to all
	 * observers, else it will send false. The results can be retrieved by
	 * calling getRemoteResults(). The amount of found results can be retrieved
	 * by calling getRemoteResultsCount().
	 * 
	 * @param query
	 *            The query that Tribler will look for in the names of the
	 *            channels
	 */
	void searchRemote(final String query) {
		XMLRPCCallTask task = new XMLRPCCallTask() {
			@Override
			protected void onPostExecute(Object result) {
				Boolean hasPeers = (Boolean) result;
				if (hasPeers) {
					Log.v("XMLRPC", "Looking for query " + query
							+ " across peers.");
				} else {
					Log.v("XMLRPC", "Not enough peers found for query " + query
							+ ".");
				}
				setChanged();
				notifyObservers(hasPeers);
			}
		};
		task.execute(mClient, "channels.search_remote", query);
	}

	/**
	 * It will send an Integer to all observers describing the amount of found
	 * results.
	 */
	public void getRemoteResultsCount() {
		XMLRPCCallTask task = new XMLRPCCallTask() {
			@Override
			protected void onPostExecute(Object result) {
				Integer count = (Integer) result;
				setChanged();
				notifyObservers(count);
			}
		};
		task.execute(mClient, "channels.get_remote_results_count");
	}

	/**
	 * It will send an ArrayList<Channel> all observers containing the found
	 * channels.
	 */
	void getRemoteResults() {
		XMLRPCCallTask task = new XMLRPCCallTask() {
			@Override
			protected void onPostExecute(Object result) {
				Object[] arrayResult = (Object[]) result;
				ArrayList<Channel> resultsList = new ArrayList<Channel>();
				Log.v("XMLRPC", "Got " + arrayResult.length + " results");
				for (int i = 0; i < arrayResult.length; i++) {
					@SuppressWarnings("unchecked")
					Channel c = new Channel(
							(Map<String, Object>) arrayResult[i]);
					resultsList.add(c);
				}
				setChanged();
				notifyObservers(resultsList);
				Log.v("XMLRPC", "Observers notified!");
			}
		};
		task.execute(mClient, "channels.get_remote_results");
	}
}