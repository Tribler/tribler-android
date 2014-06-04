package org.tribler.tsap.channels;

import java.net.URL;
import java.util.ArrayList;
import java.util.Map;
import java.util.Observable;

import android.util.Log;

import org.tribler.tsap.XMLRPCCallTask;
import org.tribler.tsap.AbstractXMLRPCManager;

/**
 * Class for receiving channels over XMPRPC using the aXMLRPC library
 * 
 * @author Dirk Schut
 * @since 26-5-2014
 */
class XMLRPCChannelManager extends AbstractXMLRPCManager {

	private ChannelListAdapter mAdapter;
	private int mLastFoundResultsCount = 0;

	/**
	 * Constructor: Sets up the AbstractXMLRPCManager and the adapter
	 * 
	 * @param url
	 *            The url of the XMLRPC server
	 */
	XMLRPCChannelManager(URL url, ChannelListAdapter adapter) {
		super(url);
		mAdapter = adapter;
	}

	/**
	 * Searches the local dispersy data for channels fitting a certain query. Once the results are found it will send
	 * them as an ArrayList<Channel> to all observers.
	 * 
	 * @param query
	 *            The query that Tribler will look for in the names of the channels
	 */
	private void getLocal(final String query) {
		Log.v("XMPLRCChannelManager", "Local search for \"" + query + "\" launched.");
		XMLRPCCallTask task = new XMLRPCCallTask() {
			@Override
			protected void onPostExecute(Object result) {
				Log.v("XMPLRCChannelManager", "Local search returned.");
				if (result != null) {
					Object[] arrayResult = (Object[]) result;
					ArrayList<Channel> resultsList = new ArrayList<Channel>();
					Log.v("XMLRPC", "Got " + arrayResult.length + "results");
					for (int i = 0; i < arrayResult.length; i++) {
						@SuppressWarnings("unchecked")
						Channel c = new Channel((Map<String, Object>) arrayResult[i]);
						resultsList.add(c);
					}
					mAdapter.addNew(resultsList);
					// Map<String, Object> firstResult = (Map<String, Object>)
					// arrayResult[0];
					// Log.v("XMPLRCChannelManager", "KeySet: " +
					// firstResult.keySet());
				}
			}
		};
		task.execute(mClient, "channels.get_local", query);
	}

	/**
	 * Searches the remote dispersy data for channels fitting a certain query. If there are enough peers for a search it
	 * will send true to all observers, else it will send false. The results can be retrieved by calling
	 * getRemoteResults(). The amount of found results can be retrieved by calling getRemoteResultsCount().
	 * 
	 * @param query
	 *            The query that Tribler will look for in the names of the channels
	 */
	private void searchRemote(final String query) {
		Log.v("XMPLRCChannelManager", "Remote search for \"" + query + "\" launched.");
		XMLRPCCallTask task = new XMLRPCCallTask() {
			@Override
			protected void onPostExecute(Object result) {
				Log.v("XMPLRCChannelManager", "Remote search returned.");
				if (result != null) {
					Boolean hasPeers = (Boolean) result;
					if (hasPeers) {
						Log.v("XMLRPC", "Looking for query " + query + " across peers.");
					} else {
						Log.v("XMLRPC", "Not enough peers found for query " + query + ".");
					}
				}
			}
		};
		task.execute(mClient, "channels.search_remote", query);
	}

	/**
	 * It will send an Integer to all observers describing the amount of found results.
	 */
	private void getRemoteResultsCount() {
		XMLRPCCallTask task = new XMLRPCCallTask() {
			@Override
			protected void onPostExecute(Object result) {
				if (result != null) {
					Integer count = (Integer) result;
					if (count > mLastFoundResultsCount) {
						mLastFoundResultsCount = count;
						getRemoteResults();
					}
				}
			}
		};
		task.execute(mClient, "channels.get_remote_results_count");
	}

	/**
	 * It will send an ArrayList<Channel> all observers containing the found channels.
	 */
	private void getRemoteResults() {
		XMLRPCCallTask task = new XMLRPCCallTask() {
			@Override
			protected void onPostExecute(Object result) {
				if (result != null) {
					Object[] arrayResult = (Object[]) result;
					ArrayList<Channel> resultsList = new ArrayList<Channel>();
					Log.v("XMLRPC", "Got " + arrayResult.length + " results");
					for (int i = 0; i < arrayResult.length; i++) {
						@SuppressWarnings("unchecked")
						Channel c = new Channel((Map<String, Object>) arrayResult[i]);
						resultsList.add(c);
					}
					mAdapter.addNew(resultsList);
				}
			}
		};
		task.execute(mClient, "channels.get_remote_results");
	}

	/**
	 * Launches a search in both the local and the remote Dispersy databases. It also removes the current values from
	 * the list. Once search results are found, new values are added.
	 * 
	 * @param query
	 *            the query to look for in the database
	 */
	public void search(String query) {
		mLastFoundResultsCount = 0;
		mAdapter.clear();
		getLocal(query);
		searchRemote(query);
		Log.v("XMPLRCChannelManager", "Search for \"" + query + "\" launched.");
	}

	/**
	 * Will look for new search results. This function will be called regularly by the poller.
	 */
	@Override
	public void update(Observable observable, Object data) {
		getRemoteResultsCount();
		// Log.i("ChannelPoll","Log");
	}
}