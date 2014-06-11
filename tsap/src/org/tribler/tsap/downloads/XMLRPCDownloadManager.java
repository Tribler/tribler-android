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

public class XMLRPCDownloadManager extends AbstractXMLRPCManager {
	
	private static DownloadListAdapter mAdapter = null;
	private static URL mUrl = null;
	private static XMLRPCDownloadManager instance = null;
	private static Context mContext;
	
	/**
	 * private constructor exists only so the class is only accessible through getInstance
	 */
	private XMLRPCDownloadManager() {
	}
	
	public static XMLRPCDownloadManager getInstance()
	{
		if(instance == null)
		{
			instance = new XMLRPCDownloadManager();
		}
		return instance;
	}
	
	public void setUp(DownloadListAdapter adapter, URL url, Context context)
	{
		mAdapter = adapter;
		this.mClient = new XMLRPCClient(url);
		this.logAvailableFunctions();
		this.mContext = context;
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
		return new Download((String)map.get("name"),
				(String)map.get("infohash"),
				(Integer)map.get("status"),
				(Double)map.get("speed_down"),
				(Double)map.get("speed_up"),
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
					Log.v("DownloadManager", arrayResult.length + " resultaten");
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
			}
		};
		task.execute(mClient, "downloads.get_all_progress_info");
	}

	@Override
	public void update(Observable observable, Object data) {
		getAllProgressInfo();
	}
	public void downloadTorrent(String infoHash, String name)
	{
		Log.i("XMLRPCDownloadManager", "Adding download with infohash: " + infoHash + " and name: " + name);
		XMLRPCCallTask task = new XMLRPCCallTask() {
			@Override
			protected void onPostExecute(Object result) {
				Toast.makeText(mContext, "Download started!", Toast.LENGTH_SHORT).show();
				Log.i("XMLRPCDownloadManager", "Download added!");
			}
		};
		task.execute(mClient, "downloads.add", infoHash, name);
	}
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
		task.execute(mClient, "downloads.start_vod", infoHash);
	}
}