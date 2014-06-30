package org.tribler.tsap.downloads;

import java.util.ArrayList;
import java.util.Map;

import org.tribler.tsap.Utility;
import org.tribler.tsap.Poller.IPollListener;
import org.tribler.tsap.XMLRPC.XMLRPCCallTask;
import org.tribler.tsap.XMLRPC.XMLRPCConnection;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

/**
 * Singleton class for adding and accessing downloads.
 * 
 * @author Dirk Schut
 */
public class XMLRPCDownloadManager implements IPollListener {

	private DownloadListAdapter mAdapter = null;
	private static XMLRPCDownloadManager mInstance = null;
	private Context mContext;
	private Download currProgressDownload;
	private Uri videoLink;
	private XMLRPCConnection mConnection;

	/**
	 * private constructor exists only so the class is only accessible through
	 * getInstance
	 */
	private XMLRPCDownloadManager() {
	}

	/**
	 * Returns the singleton instance of the class
	 * 
	 * @return
	 */
	public static XMLRPCDownloadManager getInstance() {
		if (mInstance == null) {
			mInstance = new XMLRPCDownloadManager();
		}
		return mInstance;
	}

	/**
	 * Sets up the class with initial values. This function should be called
	 * before using any other function from this class.
	 * 
	 * @param adapter
	 *            The adapter for storing the downloads in.
	 * @param url
	 *            Url of the Tribler XML-RPC server to connect with.
	 * @param context
	 *            Context for creating toasts and intents.
	 */
	public void setUp(DownloadListAdapter adapter, XMLRPCConnection connection,
			Context context) {
		getInstance();
		mAdapter = adapter;
		mContext = context;
		mConnection = connection;
	}

	public DownloadListAdapter getAdapter() {
		return mAdapter;
	}

	/**
	 * Converts a Map as returned by XMLRPC to a Download object
	 */
	private Download convertMapToDownload(Map<String, Object> map) {
		double downloadSpeed;
		if (map.get("speed_down") instanceof Double)
			downloadSpeed = (Double) map.get("speed_down");
		else
			downloadSpeed = (Integer) map.get("speed_down");

		double uploadSpeed;
		if (map.get("speed_down") instanceof Double)
			uploadSpeed = (Double) map.get("speed_up");
		else
			uploadSpeed = (Integer) map.get("speed_up");

		int seeders = Utility.getFromMap(map, "num_seeders", (int) -1);
		int leechers = Utility.getFromMap(map, "num_leechers", (int) -1);
		String size = Utility.getFromMap(map, "length", "-1");

		return new Download((String) map.get("name"),
				(String) map.get("infohash"), (Integer) map.get("status"),
				downloadSpeed, uploadSpeed, (Double) map.get("progress"),
				(Double) map.get("eta"), (Double) map.get("vod_eta"),
				(Boolean) map.get("vod_playable"), seeders, leechers,
				Utility.getFromMap(map, "category", "Unknown"),
				Long.parseLong(size.trim()));
	}

	/**
	 * Retrieves the list of downloads.
	 */
	@SuppressWarnings("unchecked")
	private void replaceAllProgressInfo() {

		Object result = mConnection.call("downloads.get_all_progress_info");
		Log.e("", "Result is: " + result.toString());
		Object[] arrayResult = (Object[]) result;
		ArrayList<Download> resultsList = new ArrayList<Download>();

		for (int i = 0; i < arrayResult.length; i++) {
			resultsList
					.add(convertMapToDownload((Map<String, Object>) arrayResult[i]));
		}
		mAdapter.replaceAll(resultsList);
	}

	@Override
	public void onPoll() {
		replaceAllProgressInfo();
	}

	/**
	 * Starts downloading a torrent.
	 * 
	 * @param infoHash
	 *            Infohash of the torrent that has to be downloaded.
	 * @param name
	 *            Name to be displayed in the downloadslist as long as no
	 *            metadata has been found.
	 */
	public void downloadTorrent(String infoHash, String name) {
		Log.i("XMLRPCDownloadManager", "Adding download with infohash: "
				+ infoHash + " and name: " + name);
		new XMLRPCCallTask() {
			@Override
			public void onSucces(Object result) {
				if ((Boolean) result) {
					Toast.makeText(mContext, "Download started!",
							Toast.LENGTH_SHORT).show();
					Log.i("XMLRPCDownloadManager", "Download started!");
				} else {
					Toast.makeText(mContext, "Could not start downloading.",
							Toast.LENGTH_LONG).show();
					Log.e("XMLRPCDownloadManager",
							"Tribler could not add the download.");
				}
			}
		}.call("downloads.add", mConnection, infoHash, name);
	}

	/**
	 * Starts streaming a torrent with the VLC videoPlayerActivity class.
	 * 
	 * @param infoHash
	 *            infohash of the torrent that has to be
	 */
	public void startVOD(String infoHash) {
		Log.i("XMLRPCDownloadManager", "Making a VODlink with infohash: "
				+ infoHash);
		new XMLRPCCallTask() {
			@Override
			public void onSucces(Object result) {
				if (result instanceof Boolean) {
					Log.e("XMLRPCDownloadManager",
							"Starting in VOD mode failed. result:"
									+ (Boolean) result);
				} else {
					String VODString = (String) result;
					Toast.makeText(mContext, "VODlink =" + VODString,
							Toast.LENGTH_LONG).show();
					Log.i("XMLRPCDownloadManager", "VODlink created!");
					videoLink = Uri.parse(VODString);
				}
			}
		}.call("downloads.start_vod", mConnection, infoHash);
	}

	/**
	 * Retrieves the progress of the torrent specified by the infohash
	 */
	@SuppressWarnings("unchecked")
	public void getProgressInfo(String infoHash) {
		Log.v("DownloadManager", "Fetching progress for torrent: " + infoHash);
		new XMLRPCCallTask() {
			@Override
			public void onSucces(Object result) {
				if (!(result instanceof Boolean)) {
					Map<String, Object> map = (Map<String, Object>) result;
					currProgressDownload = convertMapToDownload(map);
				}
				Log.v("DownloadManager", "fetch returned");
			}
		}.call("downloads.get_progress_info", mConnection, infoHash);
	}

	public Download getCurrentDownload() {
		return currProgressDownload;
	}

	public Uri getVideoUri() {
		return videoLink;
	}

	public void deleteTorrent(String infoHash, boolean deleteFiles) {
		Log.i("XMLRPCDownloadManager", "Removing torrent with infohash: "
				+ infoHash);
		XMLRPCCallTask task = new XMLRPCCallTask();
		task.call("downloads.remove", mConnection, infoHash, deleteFiles);
	}
}