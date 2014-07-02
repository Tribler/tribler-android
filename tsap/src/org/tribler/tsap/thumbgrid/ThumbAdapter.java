package org.tribler.tsap.thumbgrid;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.tribler.tsap.AbstractArrayListAdapter;
import org.tribler.tsap.R;
import org.tribler.tsap.Torrent;
import org.tribler.tsap.settings.Settings;
import org.tribler.tsap.util.ThumbnailUtils;
import org.tribler.tsap.util.Utility;

import android.app.Activity;
import android.graphics.PorterDuff.Mode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * Adapter belonging to the ThumbGridFragment that holds the thumb items
 * 
 * @author Wendo Sabée
 */
public class ThumbAdapter extends AbstractArrayListAdapter<Torrent> {
	private int layoutResourceId;

	/**
	 * Constructor: initializes the instance variables
	 * 
	 * @param context
	 *            The context of this adapter
	 * @param layoutResourceId
	 *            The resource id of the layout
	 */
	public ThumbAdapter(Activity activity, int layoutResourceId) {
		super(activity);
		this.layoutResourceId = layoutResourceId;
	}

	/**
	 * Constructor: initializes the instance variables
	 * 
	 * @param context
	 *            The context of this adapter
	 * @param layoutResourceId
	 *            The resource id of the layout
	 * @param data
	 *            The list of thumbitems
	 */
	public ThumbAdapter(Activity activity, int layoutResourceId,
			ArrayList<Torrent> data) {
		super(activity, data);
		this.layoutResourceId = layoutResourceId;
	}

	/**
	 * Returns the view belonging to the specified position in the thumb grid
	 * 
	 * @param position
	 *            The position of which the view should be returned
	 * @param convertView
	 *            The view necessary update the text and image views and the
	 *            progress bar
	 * @param parent
	 *            The parent view group
	 * @return The view belonging to position
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Torrent item = this.getItem(position);

		if (convertView == null) {
			LayoutInflater inflater = (mActivity).getLayoutInflater();
			convertView = inflater.inflate(layoutResourceId, parent, false);
		}

		TextView title = (TextView) convertView.findViewById(R.id.ThumbTitle);
		title.setText(item.getName() + "\n");

		ImageView image = (ImageView) convertView.findViewById(R.id.ThumbImage);
		if (item.getThumbnailFile() != null) {
			ThumbnailUtils.loadThumbnail(item.getThumbnailFile(), image,
					mActivity);
		} else {
			File file = ThumbnailUtils.getThumbnailLocation(item.getInfoHash());
			if(file != null) {
				ThumbnailUtils.loadThumbnail(file, image, mActivity);
				item.setThumbnailFile(file);
			} else {
				ThumbnailUtils.loadDefaultThumbnail(image, mActivity);
			}
		}

		ProgressBar health = (ProgressBar) convertView
				.findViewById(R.id.ThumbHealth);
		health.setProgress(item.getHealth().ordinal());
		health.getProgressDrawable().setColorFilter(
				TORRENT_HEALTH.toColor(item.getHealth()), Mode.SRC_IN);

		TextView size = (TextView) convertView.findViewById(R.id.ThumbSize);
		size.setText(Utility.convertBytesToString(item.getSize()));

		return convertView;
	}

	/**
	 * Adds all items from a list that were not in the adapter already.
	 * 
	 * @param list
	 *            list of items to add
	 */
	public void addNew(List<Torrent> list) {
		synchronized (mLock) {
			for (Torrent item : list) {
				if (!mContent.contains(item)) {
					mContent.add(item);
				}
			}
		}
		Log.e("", "Torrents added!");
		notifyChangesToUiThread();
	}
}