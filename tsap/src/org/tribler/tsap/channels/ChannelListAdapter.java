package org.tribler.tsap.channels;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.tribler.tsap.R;
import org.tribler.tsap.ThreadPreconditions;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Adapter for managing the data and views for a list of channels.
 * 
 * @author Dirk Schut
 */
public class ChannelListAdapter extends BaseAdapter {
	private int resource;
	private LayoutInflater inflater;
	private ArrayList<Channel> content;

	/**
	 * Constructor: initializes the instance variables
	 * 
	 * @param context
	 *            The context of this adapter
	 * @param resource
	 *            The resource id of the layout
	 */
	public ChannelListAdapter(Context context, int resource) {
		content = new ArrayList<Channel>();
		this.resource = resource;
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	/**
	 * Constructor: initializes the instance variables and fills the adapter
	 * with data from a list
	 * 
	 * @param context
	 *            The context of this adapter
	 * @param resource
	 *            The resource id of the layout
	 * @param content
	 *            The data to fill the list with
	 */
	public ChannelListAdapter(Context context, int resource,
			Collection<Channel> initialContent) {
		content = new ArrayList<Channel>();
		content.addAll(initialContent);
		this.resource = resource;
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	/**
	 * Returns the item at a certain position.
	 * 
	 * @param position
	 * 	the position from which you want to retrieve the channel
	 */
	@Override
	public Channel getItem(int position) {
		return content.get(position);
	}

	/**
	 * Returns the view belonging to the specified position in the channel list.
	 * 
	 * @param position
	 *            The position of which the view should be returned
	 * @param convertView
	 *            The view necessary update the text and image views
	 * @param parent
	 *            The parent view group
	 * @return The view belonging to position
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view;
		Channel channel = getItem(position);

		view = convertView;
		if (view == null) {
			view = inflater.inflate(resource, parent, false);
		}

		((TextView) view.findViewById(R.id.channel_name)).setText(channel
				.getName());
		((CheckBox) view.findViewById(R.id.channel_favoritetoggle))
				.setChecked(channel.getFollowing());
		((TextView) view.findViewById(R.id.channel_torrentamount))
				.setText("Torrents: " + channel.getTorrentAmount());

		setRatingView(view, channel);
		return view;
	}

	/**
	 * Displays the star images according to the rating of the channel
	 * 
	 * @param view
	 *            The parent view
	 * @param channel
	 *            The channel of which the rating should be shown
	 */
	private void setRatingView(View view, Channel channel) {
		int rating = channel.getRating();
		if (rating >= 1)
			((ImageView) view.findViewById(R.id.channel_firststar))
					.setImageResource(R.drawable.rating_star_selected);
		else
			((ImageView) view.findViewById(R.id.channel_firststar))
					.setImageResource(R.drawable.rating_star_not_selected);
		if (rating >= 2)
			((ImageView) view.findViewById(R.id.channel_secondstar))
					.setImageResource(R.drawable.rating_star_selected);
		else
			((ImageView) view.findViewById(R.id.channel_secondstar))
					.setImageResource(R.drawable.rating_star_not_selected);
		if (rating >= 3)
			((ImageView) view.findViewById(R.id.channel_thirdstar))
					.setImageResource(R.drawable.rating_star_selected);
		else
			((ImageView) view.findViewById(R.id.channel_thirdstar))
					.setImageResource(R.drawable.rating_star_not_selected);
		if (rating >= 4)
			((ImageView) view.findViewById(R.id.channel_fourthstar))
					.setImageResource(R.drawable.rating_star_selected);
		else
			((ImageView) view.findViewById(R.id.channel_fourthstar))
					.setImageResource(R.drawable.rating_star_not_selected);
		if (rating >= 5)
			((ImageView) view.findViewById(R.id.channel_fifthstar))
					.setImageResource(R.drawable.rating_star_selected);
		else
			((ImageView) view.findViewById(R.id.channel_fifthstar))
					.setImageResource(R.drawable.rating_star_not_selected);
	}
	
	/**
	 * Adds all channels from a list to the channelList that were not in the channelList already
	 * @param channelList
	 * 	list of channels to add
	 */
	public void addNew(List<Channel> channelList) {
		ThreadPreconditions.checkOnMainThread();
		Log.v("ChannelListFragment", "Adding items");
		for (int i = 0; i < channelList.size(); i++) {
			if (!content.contains(channelList.get(i)))
			{
				content.add(channelList.get(i));
				Log.v("ChannelListFragment", "item added");
			}
		}
		notifyDataSetChanged();
	}
	
	/**
	 * Returns the amount of channels.
	 */
	@Override
	public int getCount() {
		return content.size();
	}

	/**
	 * Returns the Id of the item at a certain position.
	 * @param
	 *  the position
	 */
	@Override
	public long getItemId(int position) {
		return position;
	}
	
	/**
	 * Removes all data from the list.
	 */
	public void clear()
	{
		ThreadPreconditions.checkOnMainThread();
		content.clear();
		notifyDataSetChanged();
	}
}