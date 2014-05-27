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
	private String mCurrentQuery = "";
	private boolean mIsSearching = false;

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

	void searchRemote(final String query) {
		mCurrentQuery = query;
		XMLRPCCallTask task = new XMLRPCCallTask() {
			@Override
			protected void onPostExecute(Object result) {
				Boolean hasPeers = (Boolean)result;
				if(hasPeers)
				{
					mIsSearching = true;
					Log.v("XMLRPC", "Looking for query " + query + " across peers.");
				}
				else
				{
					mIsSearching = false;
					Log.v("XMLRPC", "Not enough peers found for query " + query + ".");
				}
			}
		};
		Log.v("XMLRPC", "Calling channels.search_remote");
		task.execute(mClient, "channels.search_remote", query);
	}

	public void getRemoteResultsCount() {
		XMLRPCCallTask task = new XMLRPCCallTask() {
			@Override
			protected void onPostExecute(Object result) {
				Integer count = (Integer) result;
				setChanged();
				notifyObservers(count);
			}
		};
		task.execute(mClient, "torrents.get_remote_results_count");
	}
	
	void getRemoteResults()
	{
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
		task.execute(mClient, "channels.get_remote_results");
	}

	public boolean isSearching() {
		return mIsSearching;
	}

	public String getCurrentQuery() {
		return mCurrentQuery;
	}
}