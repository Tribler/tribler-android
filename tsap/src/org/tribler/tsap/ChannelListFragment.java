package org.tribler.tsap;

import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Fragment that shows a list of available channels and handles its behaviour
 * @author Dirk Schut
 */
public class ChannelListFragment extends ListFragment implements
		OnQueryTextListener {
	
	/**
	 * Initializes the channel adapter 
	 * @param savedInstanceState The state of the saved instance
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);

		// storing string resources into Array
		String[] channelNames = getResources().getStringArray(
				R.array.channelNames);

		ChannelListAdapter adapter = new ChannelListAdapter(getActivity(),
				R.layout.list_item);
		for (int i = 0; i < channelNames.length; i++) {
			adapter.add(new Channel(channelNames[i]));
		}
		this.setListAdapter(adapter);
	}

	/**
	 * Launches a new ChannelActivity with the data of the clicked channel
	 * @param l The ListView belonging to this fragment
	 * @param v The View of this fragment
	 * @param position The position of the selected item in the list
	 * @param id The ID of the selected item
	 */
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		// selected item
		String product = ((TextView) ((RelativeLayout) v)
				.findViewById(R.id.channel_name)).getText().toString();

		// Launching new Activity on selecting single List Item
		Intent i = new Intent(getActivity().getApplicationContext(),
				ChannelActivity.class);
		// sending data to new activity
		i.putExtra("product", product);
		startActivity(i);
	}

	/**
	 * Adds channel fragment specific options to the options menu.
	 * In this case, the search action is added and enabled.
	 * @param menu The menu that will be created
	 * @param inflater The inflater belonging to the menu
	 */
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.channel_fragment, menu);
		MenuItem searchMenuItem = menu.findItem(R.id.action_search_channel);
		SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchMenuItem);
		searchView.setOnQueryTextListener(this);
		searchView.setQueryHint("Search channels");
	}

	/**
	 * Defines the behaviour of selecting a menu item
	 * @param item The menu item that has been clicked
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

	/**
	 * Filters the items in the list according to the query
	 * @param query The query that the user has typed in the search field
	 * @return True iff the text change has been processed correctly
	 */
	public boolean onQueryTextChange(String query) {
		// Called when the action bar search text has changed. Update
		// the search filter, and restart the loader to do a new query
		// with this filter.
		((ChannelListAdapter) getListAdapter()).getFilter().filter(query);
		return true;
	}

	/**
	 * Filters the items in the list according to the query and show a dialog showing the submitted query
	 * @param query The query that the user has typed in the search field
	 * @return True iff the action belonging to submitting a query has been processed correctly
	 */
	@Override
	public boolean onQueryTextSubmit(String query) {
		((ChannelListAdapter) getListAdapter()).getFilter().filter(query);
		Toast.makeText(getActivity(), query, Toast.LENGTH_SHORT).show();
		// Don't care about this.
		return true;
	}
}
