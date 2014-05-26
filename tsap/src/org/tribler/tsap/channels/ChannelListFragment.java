package org.tribler.tsap.channels;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;

import org.tribler.tsap.R;
import org.tribler.tsap.R.array;
import org.tribler.tsap.R.id;
import org.tribler.tsap.R.layout;
import org.tribler.tsap.R.menu;

import de.timroes.axmlrpc.XMLRPCCallback;
import de.timroes.axmlrpc.XMLRPCClient;
import de.timroes.axmlrpc.XMLRPCException;
import de.timroes.axmlrpc.XMLRPCServerException;
import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.util.Log;
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
 * 
 * @author Dirk Schut
 */
public class ChannelListFragment extends ListFragment implements
		OnQueryTextListener {

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
		
		ChannelListAdapter adapter = new ChannelListAdapter(getActivity(),
				R.layout.list_item);

		// storing string resources into Array
		//String[] channelNames = getResources().getStringArray(
		//		R.array.channelNames);
		
		this.setListAdapter(adapter);

		
	}

	/**
	 * Launches a new ChannelActivity with the data of the clicked channel
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
	 * Adds channel fragment specific options to the options menu. In this case,
	 * the search action is added and enabled.
	 * 
	 * @param menu
	 *            The menu that will be created
	 * @param inflater
	 *            The inflater belonging to the menu
	 */
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.channel_fragment, menu);
		MenuItem searchMenuItem = menu.findItem(R.id.action_search_channel);
		SearchView searchView = (SearchView) MenuItemCompat
				.getActionView(searchMenuItem);
		searchView.setOnQueryTextListener(this);
		searchView.setQueryHint("Search channels");
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

	/**
	 * Filters the items in the list according to the query
	 * 
	 * @param query
	 *            The query that the user has typed in the search field
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
	 * Filters the items in the list according to the query and show a dialog
	 * showing the submitted query
	 * 
	 * @param query
	 *            The query that the user has typed in the search field
	 * @return True iff the action belonging to submitting a query has been
	 *         processed correctly
	 */
	@Override
	public boolean onQueryTextSubmit(String query)
	{
		ChannelListAdapter adapter = new ChannelListAdapter(getActivity(),
				R.layout.list_item);
		/*for (int i = 0; i < channelNames.length; i++) {
			adapter.add(new Channel(channelNames[i]));
		}*/
		
		///////
		
		final ArrayList<Channel> tempList = new ArrayList<Channel>();
		final ArrayList<Channel> waitList = new ArrayList<Channel>();
		XMLRPCCallback listener = new XMLRPCCallback()
		{
			@Override
		    public void onResponse(long id, Object result)
			{
				Log.i("XMLRPC", "Got response");
				Object[] castResult = (Object[]) result;
				Log.i("XMLRPC", "Got " + castResult.length + "results" );
		        for(int i=0; i<castResult.length; i++)
		        {
		        	Log.i("XMLRPC", "creating channel");
		        	Channel c = new Channel((Map<String, Object>)castResult[i]);
		        	Log.i("XMLRPC", "Channel name: " + c.getName());
		        	Log.i("XMLRPC", "KeySet:"+((Map<String, Object>)castResult[i]).keySet());
		        	tempList.add(c);
		        }
		        waitList.add(null);
		    }
			@Override
		    public void onError(long id, XMLRPCException error) {
				Log.w("XMLRPC", error.getMessage());
				Log.w("XMLRPC", "Error!");
		        // Handling any error in the library
		    }
			@Override
			public void onServerError(long id, XMLRPCServerException error) {
				Log.w("XMLRPC", "ServerError!");
				// TODO Auto-generated method stub
				
			}
		};

		XMLRPCClient client = null;
		try {
			client = new XMLRPCClient(new URL("http://localhost:8000/tribler"));
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		

		client.callAsync(listener, "channels.get_local", query);
		Log.w("XMLRPC", "started waiting");
		while(waitList.size() == 0)
		{
		
		}
		Log.w("XMLRPC", "stopped waiting");
		Log.i("XMLRPC", "tempListsize: " + tempList.size());
		for(int i=0; i<tempList.size(); i++)
		{
			adapter.add(tempList.get(i));
			Log.i("XMLRPC", "Channel name: " + tempList.get(i).getName());
		}
		Log.w("XMLRPC", "finished loading");
		
		//////
		this.setListAdapter(adapter);
		// Don't care about this.
		return true;
	}
}
