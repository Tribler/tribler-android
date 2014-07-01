package org.tribler.tsap.downloads;

import java.io.Serializable;

import org.tribler.tsap.R;
import org.tribler.tsap.XMLRPC.XMLRPCConnection;
import org.tribler.tsap.XMLRPC.XMLRPCConnection.IConnectionListener;
import org.tribler.tsap.util.Poller;

import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

public class DownloadListFragment extends ListFragment implements IConnectionListener {

	private XMLRPCConnection mConnection;
	private Poller mPoller;
	private TextView mEmptyTextView;
	//private StatusViewer mStatusViewer;
	public final static long POLLING_INTERVAL = 2000;
	/**
	 * Initializes the channel adapter
	 * 
	 * @param savedInstanceState
	 *            The state of the saved instance
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
		this.setListAdapter(XMLRPCDownloadManager.getInstance().getAdapter());
		//mStatusViewer = new StatusViewer(getActivity());
		mPoller = new Poller(XMLRPCDownloadManager.getInstance(), POLLING_INTERVAL);
		mConnection = XMLRPCConnection.getInstance();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_download_list, null);
		mEmptyTextView = (TextView)v.findViewById(android.R.id.empty);
		mEmptyTextView.setText("Loading Tribler...");
		return v;
	}
	
	/**
	 * Starts polling
	 */
	@Override
	public void onResume()
	{
		super.onResume();
		mConnection.addListener(this);
	}
	
	/**
	 * Pauses polling
	 */
	@Override
	public void onPause()
	{
		super.onPause();
		mConnection.removeListener(this);
		mPoller.stop();
	}

	/**
	 * Launches a new DownloadActivity with the data of the clicked channel
	 * 
	 * @param l
	 *            The ListView belonging to this fragment
	 * @param v
	 *            The View of this fragment
	 * @param position
	 *            The position of the selected item in the list
	 * @param id
	 *            The ID of the selected item
	 */
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		// Launching new Activity on tapping a single List Item
		Intent i = new Intent(getActivity().getApplicationContext(), DownloadActivity.class);
		i.putExtra(DownloadActivity.INTENT_MESSAGE, (Serializable) ((DownloadListAdapter) getListAdapter()).getItem(position));
		startActivity(i);
	}

	@Override
	public void onConnectionEstablished() {
		mEmptyTextView.setText("Currently no downloads are active.");
		mPoller.start();
	}
	@Override
	public void onConnectionLost() {
		mEmptyTextView.setText("Could not connect to Tribler.");
		mPoller.stop();
	}
}