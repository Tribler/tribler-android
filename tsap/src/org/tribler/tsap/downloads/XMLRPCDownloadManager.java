package org.tribler.tsap.downloads;

import java.net.URL;
import java.util.Observable;

import org.tribler.tsap.AbstractXMLRPCManager;
import org.tribler.tsap.downloads.DownloadListAdapter;;

class XMLRPCDownloadManager extends AbstractXMLRPCManager {
	DownloadListAdapter mAdapter;

	public XMLRPCDownloadManager(URL url, DownloadListAdapter adapter) {
		super(url);
		mAdapter = adapter;
	}

	@Override
	public void update(Observable observable, Object data) {
		// TODO Auto-generated method stub

	}

}