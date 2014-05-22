package org.tribler.tsap;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Adapter belonging to the ChannelListFragment that holds the channels
 * 
 * @author Dirk Schut
 */
public class ChannelListAdapter extends ArrayAdapter<Channel> {
	private int resource;
	private LayoutInflater inflater;

	/**
	 * Constructor: initializes the instance variables
	 * 
	 * @param context
	 *            The context of this adapter
	 * @param resource
	 *            The resource id of the layout
	 */
	public ChannelListAdapter(Context context, int resource) {
		super(context, resource);
		this.resource = resource;
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	/**
	 * Returns the view belonging to the specified position in the channel list
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
}