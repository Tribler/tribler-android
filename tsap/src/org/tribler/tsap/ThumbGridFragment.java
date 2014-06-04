package org.tribler.tsap;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.tribler.tsap.thumbgrid.XMLRPCTorrentManager;
import org.tribler.tsap.thumbgrid.ThumbAdapter;
import org.tribler.tsap.thumbgrid.ThumbItem;
import org.tribler.tsap.videoInfoScreen.TorrentManager;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.Toast;

/**
 * Fragment that shows a grid of available torrents and handles its behaviour
 * 
 * @author Wendo Sabée
 */
public class ThumbGridFragment extends Fragment implements OnQueryTextListener {

	private XMLRPCTorrentManager mTorrentManager = null;
	private ThumbAdapter mThumbAdapter;
	int mLastFoundResultAmount = 0;
	// stores the menu handler to remove the search item in onPause()
	private Menu menu;

	/**
	 * Defines that this fragment has an own option menu
	 * 
	 * @param savedInstanceState
	 *            The state of the saved instance
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);

	}

	/**
	 * Initializes the thumb grid fragment's GridView
	 * 
	 * @param inflater
	 *            The inflater used to inflate the thumb grid layout
	 * @param container
	 *            The container view of this fragment
	 * @param savedInstanceState
	 *            The state of the saved instance
	 * @return The created view
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);

		View v = inflater.inflate(R.layout.fragment_thumb_grid, container, false);
		GridView gridView = (GridView) v.findViewById(R.id.ThumbsGrid);

		ArrayList<ThumbItem> gridArray = TorrentManager.getInstance().getThumbItems();
		mThumbAdapter = new ThumbAdapter(container.getContext(), R.layout.thumb_grid_item, gridArray);
		gridView.setAdapter(mThumbAdapter);
		gridView.setOnItemClickListener(initiliazeOnItemClickListener());
		try {
			mTorrentManager = new XMLRPCTorrentManager(new URL("http://localhost:8000/tribler"), mThumbAdapter);
		} catch (MalformedURLException e) {
			Log.e("ThumbGridFragment", "URL was malformed.\n" + e.getStackTrace());
		}
		return v;
	}

	/**
	 * Starts/resumes polling for search results.
	 */
	@Override
	public void onResume() {
		super.onResume();
		mTorrentManager.startPolling();
		Log.i("ThumbGridFragment","Started polling");
	}

	/**
	 * Removes the search menu item so that the app doesn't crash when selecting
	 * the channel list fragment from the navigation drawer. And pauses the
	 * polling for search results.
	 */
	@Override
	public void onPause() {
		menu.removeItem(R.id.action_search_thumbgrid);
		super.onPause();
		mTorrentManager.stopPolling();
		Log.i("ThumbGridFragment","Stopped polling");
	}

	/**
	 * Initializes the OnItemClickListener of the GridView
	 * 
	 * @return The newly created OnItemClickListener
	 */
	private OnItemClickListener initiliazeOnItemClickListener() {
		return new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
				VideoInfoFragment vidFrag = new VideoInfoFragment();
				Bundle args = new Bundle();
				args.putInt("torrentID", (int) id);
				vidFrag.setArguments(args);

				FragmentTransaction transaction = getFragmentManager().beginTransaction();
				transaction.replace(R.id.container, vidFrag);
				transaction.addToBackStack(null);
				transaction.commit();
			}
		};
	}

	/**
	 * Adds thumb grid fragment specific options to the options menu and stores
	 * the menu. In this case, the search action is added and enabled.
	 * 
	 * @param menu
	 *            The menu that will be created
	 * @param inflater
	 *            The inflater belonging to the menu
	 */
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		this.menu = menu;
		inflater.inflate(R.menu.thumbgrid_fragment, menu);
		MenuItem searchMenuItem = menu.findItem(R.id.action_search_thumbgrid);
		SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchMenuItem);
		searchView.setOnQueryTextListener(this);
		searchView.setQueryHint("Search videos");
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
		case R.id.action_search_thumbgrid:
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	/**
	 * Filters the items in the grid according to the query
	 * 
	 * @param query
	 *            The query that the user has typed in the search field
	 * @return True iff the text change has been processed correctly
	 */
	public boolean onQueryTextChange(String query) {
		// Called when the action bar search text has changed. Update
		// the search filter, and restart the loader to do a new query
		// with this filter.
		// GridView gridView = (GridView)
		// this.getView().findViewById(R.id.ThumbsGrid);
		// ((ThumbAdapter) gridView.getAdapter()).getFilter().filter(query);
		return true;
	}

	/**
	 * Filters the items in the grid according to the query and show a dialog
	 * showing the submitted query
	 * 
	 * @param query
	 *            The query that the user has typed in the search field
	 * @return True iff the action belonging to submitting a query has been
	 *         processed correctly
	 */
	@Override
	public boolean onQueryTextSubmit(String query) {
		// GridView gridView = (GridView) this.getView().findViewById(
		// R.id.ThumbsGrid);
		// ((ThumbAdapter) gridView.getAdapter()).getFilter().filter(query);
		Toast.makeText(getActivity(), query, Toast.LENGTH_SHORT).show();
		mLastFoundResultAmount = 0;
		mTorrentManager.search(query);
		// Don't care about this.
		return true;
	}
}