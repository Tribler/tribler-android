package org.tribler.tsap;

import java.util.LinkedList;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;

class ChannelListAdapter extends BaseAdapter// implements Filterable
{
	LinkedList<Channel> contentList;
	@Override
	public int getCount() {
		return contentList.size();
	}

	@Override
	public Object getItem(int position) {
		return contentList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public boolean areAllItemsEnabled()
	{
		return true;
	}
}