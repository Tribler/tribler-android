package org.tribler.tsap.downloads;

import java.net.URL;
import java.util.ArrayList;
import java.util.Map;
import java.util.Observable;

import org.tribler.tsap.AbstractXMLRPCManager;
import org.tribler.tsap.XMLRPCCallTask;
import org.tribler.tsap.downloads.DownloadListAdapter;
import org.videolan.vlc.gui.video.VideoPlayerActivity;

import de.timroes.axmlrpc.XMLRPCClient;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

/**
 * Singleton class for adding and accessing downloads. 
 * @author Dirk Schut
 */
public class XMLRPCDownloadManager extends AbstractXMLRPCManager {
	
	private static DownloadListAdapter mAdapter = null;
	private static XMLRPCDownloadManager mInstance = null;
	private static Context mContext;
	
	/**
	 * private constructor exists only so the class is only accessible through getInstance
	 */
	private XMLRPCDownloadManager() {
		super(2000);
	}
	
	/**
	 * Returns the singleton instance of the class
	 * @return
	 */
	public static XMLRPCDownloadManager getInstance()
	{
		if(mInstance == null)
		{
			mInstance = new XMLRPCDownloadManager();
		}
		return mInstance;
	}
	
	/**
	 * Sets up the class with initial values. This function should be called before using any other function from this class.
	 * @param adapter
	 * 			The adapter for storing the downloads in.
	 * @param url
	 * 			Url of the Tribler XML-RPC server to connect with.
	 * @param context
	 * 			Context for creating toasts and intents.
	 */
	public static void setUp(DownloadListAdapter adapter, URL url, Context context)
	{
		getInstance();
		mAdapter = adapter;
		mContext = context;
		mInstance.mClient = new XMLRPCClient(url);
		mInstance.logAvailableFunctions();
	}
	
	public DownloadListAdapter getAdapter()
	{
		return mAdapter;
	}
	
	/**
	 * Converts a Map as returned by XMLRPC to a Download object
	 */
	private Download convertMapToDownload(Map<String, Object> map)
	{
		double downloadSpeed;
		if(map.get("speed_down") instanceof Double)
			downloadSpeed = (Double)map.get("speed_down");
		else
			downloadSpeed = (Integer)map.get("speed_down");
		double uploadSpeed;
		if(map.get("speed_down") instanceof Double)
			uploadSpeed = (Double)map.get("speed_up");
		else
			uploadSpeed = (Integer)map.get("speed_up");
		return new Download((String)map.get("name"),
				(String)map.get("infohash"),
				(Integer)map.get("status"),
				downloadSpeed,
				uploadSpeed,
				(Double)map.get("progress"));
	}
	
	/**
	 * Retrieves the list of downloads.
	 */
	@SuppressWarnings("unchecked")
	private void getAllProgressInfo() {
		Log.v("DownloadManager", "Fetching results");
		XMLRPCCallTask task = new XMLRPCCallTask() {
			@Override
			protected void onPostExecute(Object result) {
				if (result != null) {
					Object[] arrayResult = (Object[]) result;
					Log.v("DownloadManager", arrayResult.length + " result(s)");
					ArrayList<Download> resultsList = new ArrayList<Download>();
					for (int i = 0; i < arrayResult.length; i++) {
						if (arrayResult[i] == null)
							Log.e("DownloadManager", "result is null");
						else
						{
							Log.v("DownloadManager", "result != null");
							Log.v("DownloadManager", ((Map<String, Object>)arrayResult[i]).keySet().toString());
							resultsList.add(convertMapToDownload((Map<String, Object>)arrayResult[i]));
						}
					}
					mAdapter.replaceAll(resultsList);
				}
				Log.v("DownloadManager", "fetch returned");
				startPolling();
			}
		};
		stopPolling();
		task.execute(mClient, "downloads.get_all_progress_info");
	}
	
	@Override
	public void update(Observable observable, Object data) {
		getAllProgressInfo();
	}
	/**
	 * Starts downloading a torrent.
	 * @param infoHash
	 *			Infohash of the torrent that has to be downloaded.
	 * @param name
	 * 			Name to be displayed in the downloadslist as long as no metadata has been found.
	 */
	public void downloadTorrent(String infoHash, String name)
	{
		Log.i("XMLRPCDownloadManager", "Adding download with infohash: " + infoHash + " and name: " + name);
		XMLRPCCallTask task = new XMLRPCCallTask() {
			@Override
			protected void onPostExecute(Object result) {
				if(result == null)	
				{
					Log.e("XMLRPCDownloadManager", "Error in retrieving result from XMLRPC after adding download");
				}
				else
				{
					boolean succes = (Boolean)result;
					if(succes)
					{
						Toast.makeText(mContext, "Download started!", Toast.LENGTH_SHORT).show();
						Log.i("XMLRPCDownloadManager", "Download started!");
					}
					else
					{
						Toast.makeText(mContext, "Could not start downloading.", Toast.LENGTH_LONG).show();
						Log.e("XMLRPCDownloadManager", "Tribler could not add the download.");
					}
				}
			}
		};
		task.execute(mClient, "downloads.add", infoHash, name);
	}
	/**
	 * Starts streaming a torrent with the VLC videoPlayerActivity class.
	 * @param infoHash
	 * 			infohash of the torrent that has to be 
	 */
	public void startVOD(String infoHash)
	{
		Log.i("XMLRPCDownloadManager", "Making a VODlink with infohash: " + infoHash);
		XMLRPCCallTask task = new XMLRPCCallTask() {
			@Override
			protected void onPostExecute(Object result) {
				if (result != null)
				{
					if (result instanceof Boolean)
					{
						Log.e("XMLRPCDownloadManager", "Making a VODlink failed. result:" + (Boolean)result);
					}
					else
					{
						String VODString = (String)result;
						Toast.makeText(mContext, "VODlink =" + VODString, Toast.LENGTH_LONG).show();
						Log.i("XMLRPCDownloadManager", "VODlink created!");
						Uri link = Uri.parse(VODString);
						Intent intent = new Intent(Intent.ACTION_VIEW, link,
								mContext.getApplicationContext(),
								VideoPlayerActivity.class);
						mContext.startActivity(intent);
					}
				}
			}
		};
		task.execute(mClient, "downloads.get_vod_uri", infoHash);
	}
	public void deleteTorrent(String infoHash, boolean deleteFiles, final DownloadActivity activity)
	{
		Log.i("XMLRPCDownloadManager", "Removing torrent with infohash: " + infoHash);
		XMLRPCCallTask task = new XMLRPCCallTask() {
			@Override
			protected void onPostExecute(Object result) {
				activity.onBackPressed();
			}
		};
		task.execute(mClient, "downloads.remove", infoHash, deleteFiles);
	}
}