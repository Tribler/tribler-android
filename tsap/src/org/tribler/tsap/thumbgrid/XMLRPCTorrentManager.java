package org.tribler.tsap.thumbgrid;

import java.net.URL;
import java.util.ArrayList;
import java.util.Map;
import java.util.Observable;

import android.util.Log;

import org.tribler.tsap.AbstractXMLRPCManager;
import org.tribler.tsap.ISearchListener;
import org.tribler.tsap.R;
import org.tribler.tsap.XMLRPCCallTask;

/**
 * Class for receiving torrents over XMPRPC using the aXMLRPC library
 * 
 * @author Dirk Schut
 */
public class XMLRPCTorrentManager extends AbstractXMLRPCManager {
	private ThumbAdapter mAdapter;
	private int mLastFoundResultsCount = 0;
	private ISearchListener mSearchListener;

	/**
	 * Constructor: Makes a connection with an XMLRPC server and starts a
	 * polling loop
	 * 
	 * @param url
	 *            The url of the XMLRPC server
	 */
	public XMLRPCTorrentManager(URL url, ThumbAdapter adapter, ISearchListener searchListener) {
		super(url, 500);
		mAdapter = adapter;
		mSearchListener = searchListener;
	}

	/**
	 * TODO: implement on server side (so code can be uncommented).
	 * 
	 * Searches the local dispersy data for torrents fitting a certain query.
	 * Once the results are found it will send them as an ArrayList<ThumbItem>
	 * to all observers.
	 * 
	 * @param query
	 *            The query that Tribler will look for in the names of the
	 *            channels
	 */
	/*
	 * private void getLocal(final String query) { Log.v("XMPLRCChannelManager",
	 * "Local search for \"" + query + "\" launched."); XMLRPCCallTask task =
	 * new XMLRPCCallTask() {
	 * 
	 * @Override protected void onPostExecute(Object result) {
	 * Log.v("XMPLRCChannelManager", "Local search returned."); Object[]
	 * arrayResult = (Object[]) result; ArrayList<Channel> resultsList = new
	 * ArrayList<Channel>(); Log.v("XMLRPC", "Got " + arrayResult.length +
	 * "results"); for (int i = 0; i < arrayResult.length; i++) {
	 * 
	 * @SuppressWarnings("unchecked") Channel c = new Channel( (Map<String,
	 * Object>) arrayResult[i]); resultsList.add(c); }
	 * mSearchListener.onSearchResults();
	 * mAdapter.addNew(resultsList); } }; task.execute(mClient,
	 * "channels.get_local", query); }
	 */

	/**
	 * Searches the remote dispersy data for torrents fitting a certain query.
	 * The results can be retrieved by calling getRemoteResults(). The amount of
	 * found results can be retrieved by calling getRemoteResultsCount().
	 * 
	 * @param keywords
	 *            The keywords that Tribler will look for in the names of the
	 *            channels
	 */
	private void searchRemote(final String keywords) {
		Log.v("XMPLRCChannelManager", "Remote search for \"" + keywords
				+ "\" launched.");
		XMLRPCCallTask task = new XMLRPCCallTask() {
			@Override
			protected void onPostExecute(Object result) {
				Log.v("XMPLRCChannelManager", "Remote search returned.");
				// TODO: do something with the output of this function call. To
				// be able to do this first a bug in the Tribler core has to be
				// fixed. Right now the function allways returns None, while it
				// should return a Boolean just like XMLRPCChannelManager
				/*
				 * Boolean hasPeers = (Boolean) result; if (hasPeers) {
				 * Log.v("XMLRPC", "Looking for query " + query +
				 * " across peers."); } else { Log.v("XMLRPC",
				 * "Not enough peers found for query " + query + "."); }
				 */
			}
		};
		task.execute(mClient, "torrents.search_remote", keywords);
	}

	/**
	 * It will send an Integer to all observers describing the amount of found
	 * results.
	 */
	private void getRemoteResultsCount() {
		XMLRPCCallTask task = new XMLRPCCallTask() {
			@Override
			protected void onPostExecute(Object result) {
				if (result != null) {
					Integer count = (Integer) result;
					// Log.v("XMLRPCTorrentManager", "Torrents found = " +
					// count);
					if (count > mLastFoundResultsCount) {
						mLastFoundResultsCount = count;
						getRemoteResults();
					}
					else {
						startPolling();
					}
				}
				else {
					startPolling();
				}
			}
		};
		stopPolling();
		task.execute(mClient, "torrents.get_remote_results_count");
	}
	
	private ThumbItem convertMapToThumbItem(Map<String, Object> map)
	{
		return new ThumbItem((String) map.get("name"),
				R.drawable.default_thumb,
				TORRENT_HEALTH.YELLOW,
				1000,
				(String)map.get("infohash"));
	}

	/**
	 * It will send an ArrayList to all observers containing the found torrents.
	 */
	private void getRemoteResults() {
		XMLRPCCallTask task = new XMLRPCCallTask() {
			@Override
			protected void onPostExecute(Object result) {
				if (result != null) {
					Object[] arrayResult = (Object[]) result;
					ArrayList<ThumbItem> resultsList = new ArrayList<ThumbItem>();
					Log.v("XMLRPC", "Got " + arrayResult.length + " results");
					for (int i = 0; i < arrayResult.length; i++) {
						@SuppressWarnings("unchecked")
						ThumbItem item = convertMapToThumbItem(
								(Map<String, Object>) arrayResult[i]);
						resultsList.add(item);
					}
					/*Map<String, Object> firstResult = (Map<String,
					 Object>)arrayResult[0];
					Log.v("XMPLRCChannelManager",
					"KeySet: "+firstResult.keySet());*/
					mSearchListener.onSearchResults();
					mAdapter.addNew(resultsList);
				}
				startPolling();
			}
		};
		task.execute(mClient, "torrents.get_remote_results");
	}

	public void search(String keywords) {
		mLastFoundResultsCount = 0;
		mAdapter.clear();
		mSearchListener.onSearchSubmit(keywords);
		// getLocal(keywords);
		searchRemote(keywords);
		Log.i("XMPLRCChannelManager", "Search for \"" + keywords + "\" launched.");
	}

	@Override
	public void update(Observable observable, Object data) {
		getRemoteResultsCount();
		//Log.i("TorrentPoll","Poll");
	}
}