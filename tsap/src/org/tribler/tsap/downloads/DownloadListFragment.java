package org.tribler.tsap.downloads;

import java.io.Serializable;

import org.tribler.tsap.R;

import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

public class DownloadListFragment extends ListFragment {

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
	}
	
	/**
	 * Starts polling
	 */
	@Override
	public void onResume()
	{
		super.onResume();
		XMLRPCDownloadManager.getInstance().startPolling();
		Log.i("DownloadListFragment","Started polling");
	}
	
	/**
	 * Pauses polling
	 */
	@Override
	public void onPause()
	{
		super.onPause();
		XMLRPCDownloadManager.getInstance().stopPolling();
		Log.i("DownloadListFragment","Stopped polling");
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

	/**
	 * Defines the behaviour of selecting a menu item
	 * 
	 * @param item
	 *            The menu item that has been clicked
	 * @return True iff the menu item's behaviour is executed correctly
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_search_channel:
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
}