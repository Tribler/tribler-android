package org.tribler.tsap.thumbgrid;

import java.net.URL;

import android.util.Log;
import de.timroes.axmlrpc.XMLRPCClient;

import org.tribler.tsap.XMLRPCCallTask;

/**
 * Class for receiving channels over XMPRPC using the aXMLRPC library
 * 
 * @author Dirk Schut
 * @since 26-5-2014
 */
public class XMLRPCTorrentManager extends AbstractTorrentManager {
	private XMLRPCClient mClient = null;

	/**
	 * Constructor: Makes a connection with an XMLRPC server
	 * 
	 * @param url
	 *            The url of the XMLRPC server
	 */
	public XMLRPCTorrentManager(URL url) {
		mClient = new XMLRPCClient(url);
	}
	
	/**
	 * Searches the remote dispersy data for torrents fitting a certain query.
	 * If there are enough peers for a search it will send true to all
	 * observers, else it will send false. The results can be retrieved by
	 * calling getRemoteResults(). The amount of found results can be retrieved
	 * by calling getRemoteResultsCount().
	 * 
	 * @param query
	 *            The query that Tribler will look for in the names of the
	 *            torrents
	 */
	public void searchRemote(final String query) {
		XMLRPCCallTask task = new XMLRPCCallTask() {
			@Override
			protected void onPostExecute(Object result) {
				/*Boolean hasPeers = (Boolean) result;
				if (hasPeers) {
					Log.v("XMLRPC", "Looking for query " + query
							+ " across peers.");
				} else {
					Log.v("XMLRPC", "Not enough peers found for query " + query
							+ ".");
				}*/
				//TODO: process the return value from torrents.search_remote
				//At the moment the returned value is None, because of errors in the Tribler core,
				//so we always return true.
				Boolean hasPeers = true;
				setChanged();
				notifyObservers(hasPeers);
			}
		};
		task.execute(mClient, "torrents.search_remote", query);
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
				Log.i("ChannelListFragment", "Notifying observers");
			}
		};
		task.execute(mClient, "torrents.get_remote_results_count");
		Log.i("ChannelListFragment", "executing task");
	}

	/**
	 * It will send an ArrayList<Channel> all observers containing the found
	 * channels.
	 */
	/*void getRemoteResults() {
		XMLRPCCallTask task = new XMLRPCCallTask() {
			@Override
			protected void onPostExecute(Object result) {
				Object[] arrayResult = (Object[]) result;
				ArrayList<ThumbItem> resultsList = new ArrayList<ThumbItem>();
				Log.v("XMLRPC", "Got " + arrayResult.length + " results");
				for (int i = 0; i < arrayResult.length; i++) {
					@SuppressWarnings("unchecked")
					ThumbItem c = new ThumbItem(
							(Map<String, Object>) arrayResult[i]);
					resultsList.add(c);
				}
				setChanged();
				notifyObservers(resultsList);
				Log.v("XMLRPC", "Observers notified!");
			}
		};
		task.execute(mClient, "channels.get_remote_results");
	}*/
}