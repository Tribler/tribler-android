package org.tribler.tsap;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

class ChannelListAdapter extends ArrayAdapter<Channel>
{
	Context context;
	int resource;
	LayoutInflater inflater;
	
	public ChannelListAdapter(Context context, int resource) {
		super(context, resource);
		this.context = context;
		this.resource = resource;
		inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		View view;
		Channel channel = getItem(position);
		
		if (convertView == null)
		{
            view = inflater.inflate(resource, parent, false);
        }
		else
		{
            view = convertView;
        }
		
		((TextView)view.findViewById(R.id.channel_name)).setText(channel.getName());
		((CheckBox)view.findViewById(R.id.channel_favoritetoggle)).setChecked(channel.getFollowing());
		((TextView)view.findViewById(R.id.channel_torrentamount)).setText("Torrents: " + channel.getTorrentAmount());
		
		int rating = channel.getRating();
		if(rating >= 1)
			((ImageView)view.findViewById(R.id.channel_firststar)).setImageResource(R.drawable.rating_star_selected);
		else
			((ImageView)view.findViewById(R.id.channel_firststar)).setImageResource(R.drawable.rating_star_not_selected);
		if(rating >= 2)
			((ImageView)view.findViewById(R.id.channel_secondstar)).setImageResource(R.drawable.rating_star_selected);
		else
			((ImageView)view.findViewById(R.id.channel_secondstar)).setImageResource(R.drawable.rating_star_not_selected);
		if(rating >= 3)
			((ImageView)view.findViewById(R.id.channel_thirdstar)).setImageResource(R.drawable.rating_star_selected);
		else
			((ImageView)view.findViewById(R.id.channel_thirdstar)).setImageResource(R.drawable.rating_star_not_selected);
		if(rating >= 4)
			((ImageView)view.findViewById(R.id.channel_fourthstar)).setImageResource(R.drawable.rating_star_selected);
		else
			((ImageView)view.findViewById(R.id.channel_fourthstar)).setImageResource(R.drawable.rating_star_not_selected);
		if(rating >= 5)
			((ImageView)view.findViewById(R.id.channel_fifthstar)).setImageResource(R.drawable.rating_star_selected);
		else
			((ImageView)view.findViewById(R.id.channel_fifthstar)).setImageResource(R.drawable.rating_star_not_selected);		
		
		return view;
	}
}