package org.tribler.tsap;

import android.app.ListFragment;
import android.os.Bundle;
import android.widget.ArrayAdapter;

public class ChannelListFragment extends ListFragment {

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         
        // storing string resources into Array
        String[] channelNames = getResources().getStringArray(R.array.channelNames);
         
        // Binding resources Array to ListAdapter
        this.setListAdapter(new ArrayAdapter<String>(getActivity(), R.layout.list_item, R.id.label, channelNames));
         
	}
}
