package org.tribler.tsap.thumbgrid;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;

import org.tribler.tsap.AbstractArrayListAdapter;
import org.tribler.tsap.R;
import org.tribler.tsap.Utility;
import org.tribler.tsap.settings.Settings;

import com.squareup.picasso.Picasso;

import android.app.Activity;
import android.content.Context;
import android.graphics.PorterDuff.Mode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * Adapter belonging to the ThumbGridFragment that holds the thumbitems
 * 
 * @author Wendo Sabée
 */
public class ThumbAdapter extends AbstractArrayListAdapter<ThumbItem> {
	private int layoutResourceId;

	private int mThumbWidth;
	private int mThumbHeight;

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

		float s = mActivity.getResources().getDisplayMetrics().density;
		mThumbWidth = (int) (100 * s);
		mThumbHeight = (int) (150 * s);
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
			ArrayList<ThumbItem> data) {
		super(activity, data);
		this.layoutResourceId = layoutResourceId;

		float s = activity.getResources().getDisplayMetrics().density;
		mThumbWidth = (int) (100 * s);
		mThumbHeight = (int) (150 * s);
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
		ThumbItem item = this.getItem(position);

		if (convertView == null) {
			LayoutInflater inflater = (mActivity).getLayoutInflater();
			convertView = inflater.inflate(layoutResourceId, parent, false);
		}
		
		TextView title = (TextView) convertView.findViewById(R.id.ThumbTitle);
		title.setText(item.getTitle());
		
		ImageView image = (ImageView) convertView.findViewById(R.id.ThumbImage);
		if(item.getThumbImageFile() != null) {
			loadBitmap(item.getThumbImageFile(), image);
		} else {
			File file = getImageLocation(item.getInfoHash());
			if(file != null) {
				loadBitmap(file, image);
				item.setThumbImageFile(file);
			}
			else {
				loadBitmap(R.drawable.default_thumb, image);
			}
		}
		
		ProgressBar health = (ProgressBar) convertView.findViewById(R.id.ThumbHealth);
		health.setProgress(item.getHealth().ordinal());
		health.getProgressDrawable().setColorFilter(item.getHealthColor(), Mode.SRC_IN);
		
		TextView size = (TextView) convertView.findViewById(R.id.ThumbSize);
		size.setText(Utility.convertBytesToString(item.getSize()));

		return convertView;
	}
	
	private File findDirectory(File directory, String name) {
		File thumbdirectory = new File(directory, "thumbs-" + name);
		if(thumbdirectory.exists()) {
			return thumbdirectory;
		} else {
			//Log.e("ThumbAdapter", "Could not find thumb folder for this infohash.");
			return null;
		}
	}
	private File findImage(File directory) {
		File[] foundImages = directory.listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File file, String name) {
				return name.endsWith(".png") || name.endsWith(".gif") || name.endsWith(".jpg") || name.endsWith(".jpeg");
			}
		});
		if(foundImages.length > 0) {
			return foundImages[0];
		} else {
			//Log.e("ThumbAdapter", "No images found: " + foundImages.length);
			return null;
		}
	}
	
	private File getImageLocation(final String infoHash) {
		File baseDirectory = Settings.getThumbFolder();
		if(baseDirectory == null || !baseDirectory.isDirectory())
		{
			Log.e("ThumbAdapter", "The base folder is not a folder.");
			return null;
		}
		File thumbsDirectory = findDirectory(baseDirectory, infoHash);
		if(thumbsDirectory == null || !thumbsDirectory.isDirectory())
		{
			//Log.e("ThumbAdapter", "The thumbs folder is not a folder.");
			return null;
		}
		File thumbnailDirectory = thumbsDirectory.listFiles()[0];
		if(thumbnailDirectory == null || !thumbnailDirectory.isDirectory())
		{
			//Log.e("ThumbAdapter", "The thumb folder is not a folder.");
			return null;
		}
		return findImage(thumbnailDirectory);
	}
	
	/**
	 * Adds all items from a list that were not in the adapter already.
	 * 
	 * @param list
	 *            list of items to add
	 */
	public void addNew(List<ThumbItem> list) {
		synchronized (mLock) {
			for (int i = 0; i < list.size(); i++) {
				if (!mContent.contains(list.get(i))) {
					mContent.add(list.get(i));
				}
			}
		}
		Log.e("", "Torrents added!");
		notifyChangesToUiThread();
	}

	/**
	 * Loads the thumbnail of the thumb item
	 * 
	 * @param resId
	 *            The resource id of the thumbnail
	 * @param mImageView
	 *            The ImageView in which the thumbnail should be loaded
	 */
	private void loadBitmap(int resId, ImageView imageView) {
		Picasso.with(mActivity).load(resId).placeholder(R.drawable.default_thumb)
				.resize(mThumbWidth, mThumbHeight).into(imageView);
	}
	
	private void loadBitmap(File file, ImageView imageView) {
		Picasso.with(mActivity).load(file).placeholder(R.drawable.default_thumb)
				.resize(mThumbWidth, mThumbHeight).into(imageView);
	}
}