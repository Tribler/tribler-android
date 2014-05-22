package org.tribler.tsap;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

public class ChannelListAdapter extends ArrayAdapter<Channel>
{
	private int resource;
	private LayoutInflater inflater;
	
	public ChannelListAdapter(Context context, int resource) {
		super(context, resource);
		this.resource = resource;
		inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		View view;
		Channel channel = getItem(position);
		
		view = convertView;
		if (view == null)
		{
            view = inflater.inflate(resource, parent, false);
        }
		
		((TextView)view.findViewById(R.id.channel_name)).setText(channel.getName());
		((CheckBox)view.findViewById(R.id.channel_favoritetoggle)).setChecked(channel.getFollowing());
		((TextView)view.findViewById(R.id.channel_torrentamount)).setText("Torrents: " + channel.getTorrentAmount());
		
		setRatingView(view, channel);				
		return view;
	}

	/**
	 * @param view
	 * @param channel
	 */
	private void setRatingView(View view, Channel channel) {
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
	}
}