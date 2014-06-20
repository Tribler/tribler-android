package org.tribler.tsap.thumbgrid;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;

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
	private Context context;
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
	public ThumbAdapter(Context context, int layoutResourceId) {
		super();
		this.layoutResourceId = layoutResourceId;
		this.context = context;

		float s = context.getResources().getDisplayMetrics().density;
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
	public ThumbAdapter(Context context, int layoutResourceId,
			ArrayList<ThumbItem> data) {
		super(data);
		this.layoutResourceId = layoutResourceId;
		this.context = context;

		float s = context.getResources().getDisplayMetrics().density;
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
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
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
	

	private File findImage(File directory) {
		File[] foundImages = directory.listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File file, String name) {
				return name.endsWith(".png") || name.endsWith(".gif") || name.endsWith(".jpg") || name.endsWith(".jpeg");
			}
		});
		
		// TODO: Find the best one
		if(foundImages.length > 0) {
			Log.d("ThumbAdapter", foundImages[0].getAbsolutePath());
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
			Log.e("ThumbAdapter", "The base thumbnailfolder " + baseDirectory.getAbsolutePath() + " could not be found");
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
	 * Loads the thumbnail of the thumb item
	 * 
	 * @param resId
	 *            The resource id of the thumbnail
	 * @param mImageView
	 *            The ImageView in which the thumbnail should be loaded
	 */
	private void loadBitmap(int resId, ImageView imageView) {
		Picasso.with(context).load(resId).placeholder(R.drawable.default_thumb)
				.resize(mThumbWidth, mThumbHeight).into(imageView);
	}
	
	private void loadBitmap(File file, ImageView imageView) {
		Picasso.with(context).load(file).placeholder(R.drawable.default_thumb)
				.resize(mThumbWidth, mThumbHeight).into(imageView);
	}
}