package org.tribler.tsap.thumbgrid;

import java.net.URL;
import java.util.ArrayList;
import java.util.Map;
import java.util.Observable;

import android.util.Log;

import org.tribler.tsap.AbstractXMLRPCManager;
import org.tribler.tsap.ISearchListener;
import org.tribler.tsap.R;
import org.tribler.tsap.Utility;
import org.tribler.tsap.XMLRPCCallTask;

import de.timroes.axmlrpc.XMLRPCException;

/**
 * Class for receiving torrents over XMPRPC using the aXMLRPC library
 * 
 * @author Dirk Schut
 */
public class XMLRPCTorrentManager extends AbstractXMLRPCManager {
	private ThumbAdapter mAdapter;
	private int mLastFoundResultsCount = 0;
	private boolean connected = false;
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
	
	private void handleLostConnection(XMLRPCException exception) {
		if (connected)
		{
			mSearchListener.onConnectionFailed(exception);
			connected = false;
			exception.printStackTrace();
		}
	}

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
				if (!(result instanceof XMLRPCException)) {
					Log.v("XMPLRCTorrentManager", "Remote search returned.");
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
				} else {
					handleLostConnection((XMLRPCException)result);
				}
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
				if (!(result instanceof XMLRPCException)) {
					Integer count = (Integer) result;
					if (!connected)
					{
						if (count <= mLastFoundResultsCount)
						{
							mSearchListener.onServerStarted();
						}
						connected = true;
					}
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
					if(connected)
					{
						handleLostConnection((XMLRPCException)result);
					}
				}
			}
		};
		stopPolling();
		task.execute(mClient, "torrents.get_remote_results_count");
	}
	
	private ThumbItem convertMapToThumbItem(Map<String, Object> map)
	{
		int seeders = Utility.getFromMap(map, "num_seeders", (int) -1);
		int leechers = Utility.getFromMap(map, "num_leechers", (int) -1);
		String size = Utility.getFromMap(map, "length", "-1");
		
		return new ThumbItem(Utility.getFromMap(map, "infohash", "unknown"),
				Utility.getFromMap(map, "name", "unknown"),
				Utility.calculateTorrentHealth(seeders, leechers),
				Long.parseLong(size.trim()),
				Utility.getFromMap(map, "category", "Unknown"),
				seeders,
				leechers);
	}
	
	/**
	 * It will send an ArrayList to all observers containing the found torrents.
	 */
	private void getRemoteResults() {
		XMLRPCCallTask task = new XMLRPCCallTask() {
			@Override
			protected void onPostExecute(Object result) {
				if (!(result instanceof XMLRPCException)) {
					Object[] arrayResult = (Object[]) result;
					ArrayList<ThumbItem> resultsList = new ArrayList<ThumbItem>();
					Log.v("XMPLRCTorrentManager", "Got " + arrayResult.length + " results");
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
				} else {
					handleLostConnection((XMLRPCException)result);
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
		searchRemote(keywords);
		Log.i("XMPLRCTorrentManager", "Search for \"" + keywords + "\" launched.");
	}

	@Override
	public void update(Observable observable, Object data) {
		getRemoteResultsCount();
		//Log.i("TorrentPoll","Poll");
	}
}