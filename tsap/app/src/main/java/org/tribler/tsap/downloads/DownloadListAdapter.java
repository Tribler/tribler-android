package org.tribler.tsap.downloads;

import java.util.ArrayList;

import org.tribler.tsap.AbstractArrayListAdapter;
import org.tribler.tsap.R;
import org.tribler.tsap.util.Utility;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * Adapter holding the data of the download list fragment
 * 
 * @author Dirk Schut
 * 
 */
public class DownloadListAdapter extends AbstractArrayListAdapter<Download> {
	protected int resource;
	protected LayoutInflater inflater;

	/**
	 * Constructor: initializes the instance variables
	 * 
	 * @param context
	 *            The context of this adapter
	 * @param resource
	 *            The resource id of the layout
	 */
	public DownloadListAdapter(Activity activity, int resource) {
		super(activity);
		this.resource = resource;
		inflater = activity.getLayoutInflater();
	}

	/**
	 * Returns the view of a list item at a certain position. It re-uses the
	 * view from convertView if possible.
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view;
		Download download = getItem(position);

		if (convertView == null) {
			view = inflater.inflate(resource, parent, false);
		} else {
			view = convertView;
		}

		setStatusView(view, download);
		return view;
	}

	/**
	 * Sets the values of the values to the current status of the download
	 * 
	 * @param view
	 * @param download
	 */
	private void setStatusView(View view, Download download) {
		int downStatus = download.getDownloadStatus().getStatus();

		((TextView) view.findViewById(R.id.download_name)).setText(download
				.getTorrent().getName());
		((TextView) view.findViewById(R.id.download_status))
				.setText("Status: "
						+ Utility.convertDownloadStateIntToMessage(downStatus)
						+ ((downStatus == 2 || downStatus == 3) ? " ("
								+ Math.round(download.getDownloadStatus()
										.getProgress() * 100) + "%)" : ""));
		((TextView) view.findViewById(R.id.download_speed_down))
				.setText("Down: "
						+ Utility.convertBytesPerSecToString(download
								.getDownloadStatus().getDownloadSpeed()));
		((TextView) view.findViewById(R.id.download_speed_up)).setText("Up: "
				+ Utility.convertBytesPerSecToString(download
						.getDownloadStatus().getUploadSpeed()));
		((TextView) view.findViewById(R.id.download_eta))
				.setText((downStatus == 3) ? "ETA: "
						+ Utility.convertSecondsToString(download
								.getDownloadStatus().getETA()) : "");
		((ProgressBar) view.findViewById(R.id.download_progress_bar))
				.setProgress((int) (download.getDownloadStatus().getProgress() * 100));
	}

	/**
	 * Replace the current list of downloads with a new one with more current
	 * information.
	 * 
	 * @param newList
	 *            The new list of downloads
	 */
	public void replaceAll(ArrayList<Download> newList) {
		synchronized (mLock) {
			mContent = newList;
			notifyChangesToUiThread();
		}
	}

	/**
	 * @param infoHash
	 *            The infohash we are looking for
	 * @return The download with infoHash as its infohash
	 */
	public Download getDownload(String infoHash) {
		Download currItem;
		for (int i = 0; i < getCount(); i++) {
			currItem = getItem(i);
			if (currItem.getTorrent().getInfoHash() == infoHash)
				return currItem;
		}
		return null;
	}
}