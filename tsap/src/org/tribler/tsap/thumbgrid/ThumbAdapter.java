package org.tribler.tsap.thumbgrid;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;

import org.tribler.tsap.AbstractArrayListAdapter;
import org.tribler.tsap.R;
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
 * Adapter belonging to the ThumbGridFragment that holds the thumbitems
 * 
 * @author Wendo Sabée
 */
public class ThumbAdapter extends AbstractArrayListAdapter<ThumbItem> {
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
			ArrayList<ThumbItem> data) {
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
		ThumbItem item = this.getItem(position);

		if (convertView == null) {
			LayoutInflater inflater = (mActivity).getLayoutInflater();
			convertView = inflater.inflate(layoutResourceId, parent, false);
		}
		
		TextView title = (TextView) convertView.findViewById(R.id.ThumbTitle);
		title.setText(item.getTitle() + "\n");
		
		ImageView image = (ImageView) convertView.findViewById(R.id.ThumbImage);
		if(item.getThumbImageFile() != null) {
			ThumbnailUtils.loadThumbnail(item.getThumbImageFile(), image, mActivity);
		} else {
			File file = getImageLocation(item.getInfoHash());
			if(file != null) {
				ThumbnailUtils.loadThumbnail(file, image, mActivity);
				item.setThumbImageFile(file);
			}
			else {
				ThumbnailUtils.loadDefaultThumbnail(image, mActivity);
			}
		}
		
		ProgressBar health = (ProgressBar) convertView.findViewById(R.id.ThumbHealth);
		health.setProgress(item.getHealth().ordinal());
		health.getProgressDrawable().setColorFilter(item.getHealthColor(), Mode.SRC_IN);
		
		TextView size = (TextView) convertView.findViewById(R.id.ThumbSize);
		size.setText(Utility.convertBytesToString(item.getSize()));

		return convertView;
	}

	private File findImage(File directory) {
		File[] foundImages = directory.listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File file, String name) {
				return name.endsWith(".png") || name.endsWith(".gif") || name.endsWith(".jpg") || name.endsWith(".jpeg");
			}
		});
		
		// TODO: Find the best one
		if(foundImages.length > 0) {
			return foundImages[0];
		} else {
			Log.d("ThumbAdapter", "No thumbnailimages found: " + foundImages.length);
			return null;
		}
	}
	
	private File getImageLocation(final String infoHash) {
		File baseDirectory = Settings.getThumbFolder();
		if(baseDirectory == null || !baseDirectory.isDirectory())
		{
			Log.e("ThumbAdapter", "The collected_torrent_files thumbnailfolder could not be found");
			return null;
		}
		
		File thumbsDirectory = new File(baseDirectory, "thumbs-" + infoHash);
		if(!thumbsDirectory.exists()) {
			Log.d("ThumbAdapter", "No thumbnailfolder found for " + infoHash);
			return null;
		}
				
		File thumbsSubDirectory = null;
		for(File file : thumbsDirectory.listFiles()){
			if(file.isDirectory())
			{
				thumbsSubDirectory = file;
				break;
			}
		}

		if(thumbsSubDirectory == null)
		{
			Log.d("ThumbAdapter", "No thumbnail subfolder found for " + infoHash);
			return null;
		}
		
		return findImage(thumbsSubDirectory);
	}
	
	/**
	 * Adds all items from a list that were not in the adapter already.
	 * 
	 * @param list
	 *            list of items to add
	 */
	public void addNew(List<ThumbItem> list) {
		synchronized (mLock) {
			for (ThumbItem item : list)
			{
				if(!mContent.contains(item))
				{
					mContent.add(item);
				}
			}
		}
		Log.e("", "Torrents added!");
		notifyChangesToUiThread();
	}
}