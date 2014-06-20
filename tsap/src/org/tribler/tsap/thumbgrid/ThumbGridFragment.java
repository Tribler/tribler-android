package org.tribler.tsap.thumbgrid;

import java.net.MalformedURLException;
import java.net.URL;

import org.tribler.tsap.ISearchListener;
import org.tribler.tsap.R;
import org.tribler.tsap.settings.Settings;
import org.tribler.tsap.videoInfoScreen.VideoInfoFragment;

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
import android.widget.TextView;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.Toast;

/**
 * Fragment that shows a grid of available torrents and handles its behaviour
 * 
 * @author Wendo Sabée
 */
public class ThumbGridFragment extends Fragment implements OnQueryTextListener, ISearchListener {

	private XMLRPCTorrentManager mTorrentManager = null;
	private ThumbAdapter mThumbAdapter;
	private View mView;
	private boolean mJustOpened = true; 

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
		mThumbAdapter = new ThumbAdapter(getActivity(), R.layout.thumb_grid_item);
		try {
			mTorrentManager = new XMLRPCTorrentManager(new URL("http://127.0.0.1:8000/tribler"), mThumbAdapter, this);
		} catch (MalformedURLException e) {
			Log.e("ChannelListFragment", "URL was malformed.\n" + e.getStackTrace());
		}
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

		mView = inflater.inflate(R.layout.fragment_thumb_grid, container, false);
		GridView gridView = (GridView) mView.findViewById(R.id.ThumbsGrid);
		gridView.setAdapter(mThumbAdapter);
		gridView.setOnItemClickListener(initiliazeOnItemClickListener());
		
		TextView message = (TextView)mView.findViewById(R.id.thumbgrid_text_view);
		if(mJustOpened)
		{
			message.setText(R.string.thumb_grid_loading_tribler);
			mJustOpened = false;
		}
		else
		{
			message.setText(R.string.thumb_grid_loading_results);
		}
		return mView;
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
				args.putSerializable("thumbData", mThumbAdapter.getItem(position));
				vidFrag.setArguments(args);

				FragmentTransaction transaction = getFragmentManager().beginTransaction();
				transaction.replace(R.id.container, vidFrag);
				transaction.addToBackStack(null);
				transaction.commit();
			}
		};
	}

	/**
	 * Removes the search menu item so that the app doesn't crash when selecting the channel list fragment from the
	 * navigation drawer. Also stops the polling loop.
	 */
	@Override
	public void onPause() {
		super.onPause();
		mTorrentManager.stopPolling();
	}
	
	/**
	 * Starts the polling loop. 
	 */
	@Override
	public void onResume() {
		super.onResume();
		mTorrentManager.startPolling();
	}

	/**
	 * Adds thumb grid fragment specific options to the options menu and stores the menu. In this case, the search
	 * action is added and enabled.
	 * 
	 * @param menu
	 *            The menu that will be created
	 * @param inflater
	 *            The inflater belonging to the menu
	 */
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
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
	 * Called when the action bar search text has changed, currently does nothing.
	 * 
	 * @param query
	 *            The query that the user has typed in the search field
	 * @return True iff the text change has been processed correctly
	 */
	public boolean onQueryTextChange(String query) {
		return true;
	}

	/**
	 * Filters the items in the grid according to the query and show a dialog showing the submitted query
	 * 
	 * @param query
	 *            The query that the user has typed in the search field
	 * @return True iff the action belonging to submitting a query has been processed correctly
	 */
	@Override
	public boolean onQueryTextSubmit(String query) {
		Toast.makeText(getActivity(), query, Toast.LENGTH_SHORT).show();
		mTorrentManager.search(query);
		// Don't care about this.
		return true;
	}
	
	@Override
	public void onSearchSubmit(String keywords) {
		View progressBar = mView.findViewById(R.id.thumbgrid_progress_bar);
		progressBar.setVisibility(View.VISIBLE);
		TextView message = (TextView)mView.findViewById(R.id.thumbgrid_text_view);
		message.setText(R.string.thumb_grid_search_submitted);
		message.setVisibility(View.VISIBLE);
	}

	@Override
	public void onSearchResults() {
		View progressBar = mView.findViewById(R.id.thumbgrid_progress_bar);
		progressBar.setVisibility(View.GONE);
		View message = mView.findViewById(R.id.thumbgrid_text_view);
		message.setVisibility(View.GONE);
	}

	@Override
	public void onServerStarted() {
		View progressBar = mView.findViewById(R.id.thumbgrid_progress_bar);
		progressBar.setVisibility(View.INVISIBLE);
		TextView message = (TextView)mView.findViewById(R.id.thumbgrid_text_view);
		message.setText(R.string.thumb_grid_server_started);
		message.setVisibility(View.VISIBLE);
		mTorrentManager.logAvailableFunctions();
		Settings.loadThumbFolder();
	}

	@Override
	public void onConnectionFailed(Exception exception) {
		View progressBar = mView.findViewById(R.id.thumbgrid_progress_bar);
		progressBar.setVisibility(View.INVISIBLE);
		TextView message = (TextView)mView.findViewById(R.id.thumbgrid_text_view);
		message.setText(R.string.thumb_grid_connection_failed);
		message.setVisibility(View.VISIBLE);
	}
}