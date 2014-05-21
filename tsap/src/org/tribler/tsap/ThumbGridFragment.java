package org.tribler.tsap;

import java.util.ArrayList;

import org.tribler.tsap.thumbgrid.TORRENT_HEALTH;
import org.tribler.tsap.thumbgrid.ThumbAdapter;
import org.tribler.tsap.thumbgrid.ThumbItem;
import org.tribler.tsap.videoInfoScreen.TorrentManager;

import android.os.Bundle;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SearchView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.SearchView.OnQueryTextListener;

public class ThumbGridFragment extends Fragment implements OnQueryTextListener {

	//stores the menu handler to remove the search item in onPause()
	private Menu menu;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	super.onCreateView(inflater, container, savedInstanceState);
    	    	
    	View v = inflater.inflate(R.layout.fragment_thumb_grid, container, false);    		
    	GridView gridView = (GridView) v.findViewById(R.id.ThumbsGrid);
    	
    	ArrayList<ThumbItem> gridArray = TorrentManager.getInstance().getThumbItems();
		ThumbAdapter customThumbs = new ThumbAdapter(container.getContext(), R.layout.thumb_grid_item, gridArray);
		gridView.setAdapter(customThumbs);
		gridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View v, int position, long id){
				VideoInfoFragment vidFrag = new VideoInfoFragment();
				Bundle args = new Bundle();
				args.putInt("torrentID", (int) id);
				vidFrag.setArguments(args);
				
				FragmentTransaction transaction = getFragmentManager().beginTransaction();
				transaction.replace(R.id.container,vidFrag);
				transaction.addToBackStack(null);
				transaction.commit();
			}
		});       
        return v;
    }
    
    /**
     * Removes the search menu item so that the app doesn't crash when selecting the channel list fragment from the navigation drawer.
     */
    @Override
    public void onPause()
    {
    	menu.removeItem(R.id.action_search_thumbgrid);
    	super.onPause();
    }
        
    /**
	 * Adds thumb grid fragment specific options to the options menu and stores the menu.
	 * In this case, the search action is added and enabled.
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

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_search_thumbgrid:
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
    
	public boolean onQueryTextChange(String query)
	{
        // Called when the action bar search text has changed.  Update
        // the search filter, and restart the loader to do a new query
        // with this filter.
		GridView gridView = (GridView) this.getView().findViewById(R.id.ThumbsGrid);
		((ThumbAdapter) gridView.getAdapter()).getFilter().filter(query);
        return true;
    }
	
	@Override
	public boolean onQueryTextSubmit(String query)
	{
		GridView gridView = (GridView) this.getView().findViewById(R.id.ThumbsGrid);
		((ThumbAdapter) gridView.getAdapter()).getFilter().filter(query);
		Toast.makeText(getActivity(), query, Toast.LENGTH_SHORT).show();
        // Don't care about this.
        return true;
    }
    
}