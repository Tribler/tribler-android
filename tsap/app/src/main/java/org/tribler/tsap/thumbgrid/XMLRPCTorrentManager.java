package org.tribler.tsap.thumbgrid;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Map;

import org.tribler.tsap.R;
import org.tribler.tsap.StatusViewer;
import org.tribler.tsap.Torrent;
import org.tribler.tsap.XMLRPC.XMLRPCCallTask;
import org.tribler.tsap.XMLRPC.XMLRPCConnection;
import org.tribler.tsap.settings.Settings;
import org.tribler.tsap.util.Poller;
import org.tribler.tsap.util.Utility;

import android.util.Log;

/**
 * Class for receiving torrents over XMPRPC using the aXMLRPC library
 * 
 * @author Dirk Schut
 */
public class XMLRPCTorrentManager implements Poller.IPollListener {
	private ThumbAdapter mAdapter;
	private XMLRPCConnection mConnection;
	private StatusViewer mStatusViewer;
	private boolean mJustStarted = true, mInBetweenSearches = false;
	private int mLastFoundResults = 0;

	/**
	 * Constructor: Makes a connection with an XMLRPC server and starts a
	 * polling loop
	 * 
	 * @param url
	 *            The url of the XMLRPC server
	 */
	public XMLRPCTorrentManager(XMLRPCConnection connection,
			ThumbAdapter adapter, StatusViewer statusViewer) {
		mConnection = connection;
		mAdapter = adapter;
		mStatusViewer = statusViewer;
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
		Log.v("XMPLRCTorrentManager", "Remote search for \"" + keywords
				+ "\" launched.");
		new XMLRPCCallTask() {
			@Override
			public void onSucces(Object result) {
				mInBetweenSearches = false;
				mStatusViewer.setMessage(R.string.thumb_grid_search_submitted, keywords, true);
			}
		}.call("torrents.search_remote", mConnection,
				keywords);
		// TODO: communicate if the search fails.
	}

	/**
	 * It will send an Integer to all observers describing the amount of found
	 * results.
	 */
	private int getRemoteResultsCount() {
		return (Integer) mConnection.call("torrents.get_remote_results_count");
	}

	/**
	 * Applies filter to Torrent
	 * 
	 * @param item
	 *            The item which should be filtered (or not)
	 * @param filter
	 *            The filter which should be applied
	 * @return True if the item should be let through, false if the item should
	 *         be filtered
	 */
	public static boolean applyResultFilter(Torrent item,
			Settings.TorrentType filter) {
		String category = (item != null) ? item.getCategory().toLowerCase(
				Locale.US) : null;

		// Something went wrong here
		if (category == null)
			return false;

		switch (filter) {
		// True when: Video, VideoClip, XXX, Other.
		// "XXX" isn't filtered because we have a family filter, and most are
		// video anyway
		// "Other" isn't filtered, as not all torrents have a correct category
		// set
		case VIDEO:
			return (category.startsWith("video") || category.equals("other") || category
					.equals("xxx"));

			// ALL or any unknown filter should just let them all through
		case ALL:
		default:
			return true;
		}
	}

	/**
	 * It will send the found torrents to the adapter.
	 */
	private void addRemoteResults() {
		Object[] arrayResult = (Object[]) mConnection
				.call("torrents.get_remote_results");
		ArrayList<Torrent> resultsList = new ArrayList<Torrent>();
		Settings.TorrentType localFilter = Settings.getFilteredTorrentTypes();

		Log.v("XMPLRCTorrentManager", "Got " + arrayResult.length + " results");

		for (int i = 0; i < arrayResult.length; i++) {
			@SuppressWarnings("unchecked")
			Torrent item = convertMapToTorrent((Map<String, Object>) arrayResult[i]);

			if (applyResultFilter(item, localFilter)) {
				resultsList.add(item);
			} else {
				Log.w("TorrentFilter",
						"Filtered remote result because of category filter ("
								+ item.getName() + ", " + item.getCategory()
								+ ")");
			}
		}
		// if the thumbgrid was empty, remove the StatusViewer
		if (mAdapter.getCount() == 0 && !resultsList.isEmpty()) {
			mStatusViewer.disable();
		}
		mAdapter.addNew(resultsList);
	}

	/**
	 * Convert the result map to a Torrent object
	 * 
	 * @param map
	 *            the map in which the XML-RPC results are stored
	 * @return a Torrent object containing the data of a torrent
	 */
	private Torrent convertMapToTorrent(Map<String, Object> map) {
		int seeders = Utility.getFromMap(map, "num_seeders", (int) -1);
		int leechers = Utility.getFromMap(map, "num_leechers", (int) -1);
		long size = Long.parseLong(Utility.getFromMap(map, "length", "-1")
				.trim());
		String infoHash = Utility.getFromMap(map, "infohash", "unknown");
		String name = Utility.getFromMap(map, "name", "unknown");
		String category = Utility.getFromMap(map, "category", "Unknown");

		return new Torrent(name, infoHash, size, seeders, leechers, category);
	}

	/**
	 * Start searching for torrents
	 * 
	 * @param keywords
	 *            The query of the torrents to look for
	 */
	public void search(String keywords) {
		mInBetweenSearches = true;
		mAdapter.clear();
		mLastFoundResults = 0;
		mStatusViewer.enable();
		searchRemote(keywords);
		Log.i("XMPLRCTorrentManager", "Search for \"" + keywords
				+ "\" launched.");
	}

	/**
	 * Called when the poller expires: if any new results are found, they are
	 * added to the thumb grid
	 */
	@Override
	public void onPoll() {
		int foundResults = getRemoteResultsCount();
		if (foundResults > mLastFoundResults && !mInBetweenSearches) {
			mLastFoundResults = foundResults;
			addRemoteResults();
			mJustStarted = false;
			Log.e("TorrentManager", "New torrents found!");
		} else if (mJustStarted) {
			mJustStarted = false;
			mStatusViewer.setMessage(R.string.thumb_grid_server_started, null, false);
		}
	}
}