package org.tribler.tsap.downloads;

import java.net.URL;
import java.util.ArrayList;
import java.util.Map;
import java.util.Observable;

import org.tribler.tsap.AbstractXMLRPCManager;
import org.tribler.tsap.XMLRPCCallTask;
import org.tribler.tsap.downloads.DownloadListAdapter;

import android.util.Log;

class XMLRPCDownloadManager extends AbstractXMLRPCManager {
	DownloadListAdapter mAdapter;

	public XMLRPCDownloadManager(URL url, DownloadListAdapter adapter) {
		super(url);
		mAdapter = adapter;
	}
	
	/**
	 * Converts a Map as returned by XMLRPC to a Download object
	 */
	private Download convertMapToDownload(Map<String, Object> map)
	{
		return new Download((String)map.get("name"), (Long)map.get("size"), (String)map.get("infohash"));
	}
	
	/**
	 * Retrieves the list of downloads.
	 */
	@SuppressWarnings("unchecked")
	private void getAllProgressInfo() {
		XMLRPCCallTask task = new XMLRPCCallTask() {
			@Override
			protected void onPostExecute(Object result) {
				if (result != null) {
					Log.v("DownloadManager", "resultaten");
					Object[] arrayResult = (Object[]) result;
					ArrayList<Download> resultsList = new ArrayList<Download>();
					for (int i = 0; i < arrayResult.length; i++) {
						resultsList.add(convertMapToDownload((Map<String, Object>)arrayResult[i]));
					}
					mAdapter.replaceAll(resultsList);
				}
			}
		};
		task.execute(mClient, "downloads.get_all_progress_info");
	}

	@Override
	public void update(Observable observable, Object data) {
		getAllProgressInfo();
	}

}