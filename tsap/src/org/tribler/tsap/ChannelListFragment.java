package org.tribler.tsap;

import android.app.ActionBar;
import android.app.ListFragment;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

public class ChannelListFragment extends ListFragment
{
	@Override
    public void onCreate(Bundle savedInstanceState)
	{
        super.onCreate(savedInstanceState);
         
        // storing string resources into Array
        String[] channelNames = getResources().getStringArray(R.array.channelNames);
         
        // Binding resources Array to ListAdapter
        this.setListAdapter(new ArrayAdapter<String>(getActivity(), R.layout.list_item, R.id.channelname, channelNames));
        //((ArrayAdapter)getListAdapter()).getFilter().filter("Open");
        
        
        /*ActionBar actionBar = getActivity().getActionBar();
        EditText search = (EditText) actionBar.getCustomView().findViewById(R.id.searchfield);
        search.setOnEditorActionListener(new OnEditorActionListener()
        {
	        @Override
	        public boolean onEditorAction(TextView v, int actionId, KeyEvent event)
	        {
	        	Toast.makeText(getActivity(), "Search triggered", Toast.LENGTH_LONG).show();
	        	return false;
	        }
        });
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM
            | ActionBar.DISPLAY_SHOW_HOME);*/
	}
	@Override
	public void onListItemClick(ListView l, View v, int position, long id)
	{
		// selected item
        String product = ((TextView)((RelativeLayout) v).findViewById(R.id.channelname)).getText().toString();
         
        // Launching new Activity on selecting single List Item
        Intent i = new Intent(getActivity().getApplicationContext(), ChannelActivity.class);
        // sending data to new activity
        i.putExtra("product", product);
        startActivity(i);
		
	}
}
