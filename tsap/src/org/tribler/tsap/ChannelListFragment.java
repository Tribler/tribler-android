package org.tribler.tsap;

import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.TextView;
import android.widget.Toast;

public class ChannelListFragment extends ListFragment  implements OnQueryTextListener
{
	@Override
    public void onCreate(Bundle savedInstanceState)
	{
        super.onCreate(savedInstanceState);
         
        // storing string resources into Array
        String[] channelNames = getResources().getStringArray(R.array.channelNames);
         
        // Binding resources Array to ListAdapter
        //this.setListAdapter(new ArrayAdapter<String>(getActivity(), R.layout.list_item, R.id.channelname, channelNames));
        
        ChannelListAdapter adapter = new ChannelListAdapter(getActivity(), R.layout.list_item);
        for(int i=0; i<channelNames.length; i++)
        {
        	adapter.add(new Channel(channelNames[i]));
        }
        this.setListAdapter(adapter);
        
	}
	@Override
	public void onListItemClick(ListView l, View v, int position, long id)
	{
		// selected item
        String product = ((TextView)((RelativeLayout) v).findViewById(R.id.channel_name)).getText().toString();
         
        // Launching new Activity on selecting single List Item
        Intent i = new Intent(getActivity().getApplicationContext(), ChannelActivity.class);
        // sending data to new activity
        i.putExtra("product", product);
        startActivity(i);
		
	}
	
	public boolean onQueryTextChange(String query)
	{
        // Called when the action bar search text has changed.  Update
        // the search filter, and restart the loader to do a new query
        // with this filter.
		((ChannelListAdapter) getListAdapter()).getFilter().filter(query);
        return true;
    }
	
	@Override
	public boolean onQueryTextSubmit(String query)
	{
		((ChannelListAdapter) getListAdapter()).getFilter().filter(query);
		Toast.makeText(getActivity(), query, Toast.LENGTH_SHORT).show();
        // Don't care about this.
        return true;
    }
}
