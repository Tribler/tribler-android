package org.tribler.tsap.thumbgrid;

import org.tribler.tsap.R;
import org.tribler.tsap.StatusViewer;
import org.tribler.tsap.Torrent;
import org.tribler.tsap.XMLRPC.XMLRPCConnection;
import org.tribler.tsap.downloads.DownloadActivity;
import org.tribler.tsap.util.Poller;
import org.tribler.tsap.videoInfoScreen.VideoInfoFragment;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Fragment that shows a grid of available torrents and handles its behaviour
 * 
 * @author Wendo Sabée
 */
public class ThumbGridFragment extends Fragment implements OnQueryTextListener,
		XMLRPCConnection.IConnectionListener {

	private XMLRPCTorrentManager mTorrentManager = null;
	private ThumbAdapter mThumbAdapter;
	private View mView;
	Poller mPoller;
	XMLRPCConnection mConnection;
	StatusViewer mStatusViewer;
	final static long POLLING_INTERVAL = 500;

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
		mConnection = XMLRPCConnection.getInstance();
		mThumbAdapter = new ThumbAdapter(getActivity(),
				R.layout.thumb_grid_item);
		mStatusViewer = new StatusViewer(getActivity());
		mTorrentManager = new XMLRPCTorrentManager(mConnection, mThumbAdapter,
				mStatusViewer);
		mPoller = new Poller(mTorrentManager, POLLING_INTERVAL);
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
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);

		mView = inflater
				.inflate(R.layout.fragment_thumb_grid, container, false);
		GridView gridView = (GridView) mView.findViewById(R.id.ThumbsGrid);
		gridView.setAdapter(mThumbAdapter);
		gridView.setOnItemClickListener(initiliazeOnItemClickListener());
		registerForContextMenu(gridView);
		
		updateStatusViewer();
		return mView;
	}

	/**
	 * Sets the views of the status viewer to the correct text
	 */
	private void updateStatusViewer() {
		mStatusViewer.updateViews(
				(ProgressBar) mView.findViewById(R.id.thumbgrid_progress_bar),
				(TextView) mView.findViewById(R.id.thumbgrid_text_view));
		if (mConnection.isJustStarted()) {
			mStatusViewer.setMessage(R.string.connection_loading, null, true);
		} else {
			mStatusViewer.setMessage(R.string.empty, null, true);
		}
	}

	/**
	 * Initializes the OnItemClickListener of the GridView
	 * 
	 * @return The newly created OnItemClickListener
	 */
	private OnItemClickListener initiliazeOnItemClickListener() {
		return new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {
				showVideoInfo(position);
			}
		};
	}


	/**
	 * Stops listening the the connection and stops the poller
	 */
	@Override
	public void onPause() {
		super.onPause();
		mConnection.removeListener(this);
		mPoller.stop();
	}

	/**
	 * Starts listening the the connection
	 */
	@Override
	public void onResume() {
		super.onResume();
		mConnection.addListener(this);
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
		inflater.inflate(R.menu.menu_thumbgrid_fragment, menu);
		MenuItem searchMenuItem = menu.findItem(R.id.action_search_thumbgrid);
		SearchView searchView = (SearchView) MenuItemCompat
				.getActionView(searchMenuItem);
		searchView.setOnQueryTextListener(this);
		searchView.setQueryHint(getString(R.string.thumb_grid_searchview));
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
	 * Called when the action bar search text has changed, currently does
	 * nothing.
	 * 
	 * @param query
	 *            The query that the user has typed in the search field
	 * @return True iff the text change has been processed correctly
	 */
	public boolean onQueryTextChange(String query) {
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
		Toast.makeText(getActivity(), query, Toast.LENGTH_SHORT).show();
		InputMethodManager inputManager = 
		        (InputMethodManager) getActivity().
		            getSystemService(Context.INPUT_METHOD_SERVICE); 
		inputManager.hideSoftInputFromWindow(
				getActivity().getCurrentFocus().getWindowToken(),
		        InputMethodManager.HIDE_NOT_ALWAYS); 
		mTorrentManager.search(query);
		return true;
	}

	/**
	 * Is called when the connection with Tribler is established: start the
	 * Poller
	 */
	@Override
	public void onConnectionEstablished() {
		mPoller.start();
	}

	/**
	 * Is called when the connection with Tribler is lost: stops the Poller and
	 * displays a message
	 */
	@Override
	public void onConnectionLost() {
		mStatusViewer.setMessage(R.string.connection_failed, null, false);
		mPoller.stop();
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		MenuInflater inflater = getActivity().getMenuInflater();
		inflater.inflate(R.menu.menu_thumb_longclick, menu);
		
		AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
		Torrent torrent = mThumbAdapter.getItem(info.position);
		menu.setHeaderTitle(torrent.getName());
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item
				.getMenuInfo();
		int menuItemIndex = item.getItemId();

		Torrent torrent =  mThumbAdapter.getItem(info.position);
		
		switch (menuItemIndex) {
		case R.id.thumb_download:
			downloadTorrent(torrent);
			break;
		case R.id.thumb_stream:
			streamTorrent(torrent);
			break;
		case R.id.thumb_info:
			infoTorrent(info.position);
			break;
		default:
			return super.onContextItemSelected(item);
		}
		return true;
	}
	
	private void showVideoInfo(int position) {
		VideoInfoFragment vidFrag = new VideoInfoFragment();
		Bundle args = new Bundle();
		args.putSerializable("thumbData",
				mThumbAdapter.getItem(position));
		vidFrag.setArguments(args);

		FragmentTransaction transaction = getFragmentManager()
				.beginTransaction();
		transaction.replace(R.id.container, vidFrag);
		transaction.addToBackStack(null);
		transaction.commit();
	}
	
	private void infoTorrent(int position) {
		showVideoInfo(position);
	}

	private void streamTorrent(Torrent torrent) {
		DownloadActivity.onStreamPressed(torrent, getActivity());
	}

	private void downloadTorrent(Torrent torrent) {
		DownloadActivity.onDownloadPressed(torrent);
	}
}