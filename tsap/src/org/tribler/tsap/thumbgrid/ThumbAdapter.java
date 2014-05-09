package org.tribler.tsap.thumbgrid;

import java.util.ArrayList;

import org.tribler.tsap.R;

import com.squareup.picasso.Picasso;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.PorterDuff.Mode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;


public class ThumbAdapter extends ArrayAdapter<ThumbItem> {
	Context context;
	int layoutResourceId;
	
	private int mThumbWidth;
	private int mThumbHeight;
	
	ThumbCache mThumbCache;
	
	public ThumbAdapter(Context context, int layoutResourceId, ArrayList<ThumbItem> data) {
		super(context, layoutResourceId, data);
		this.layoutResourceId = layoutResourceId;
		this.context = context;
		this.addAll(data);
		
		float s = getContext().getResources().getDisplayMetrics().density;
    	mThumbWidth = (int)(100 * s);
    	mThumbHeight = (int)(150 * s);
		
	    // Get max available VM memory, exceeding this amount will throw an
	    // OutOfMemory exception. Stored in kilobytes as LruCache takes an
	    // int in its constructor.
	    final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);

	    // Use 1/8th of the available memory for this memory cache.
		this.mThumbCache = new ThumbCache(maxMemory / 8, maxMemory * 2, "thumbgrid");
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		ThumbHolder holder = null;
		
		if (row == null) {
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			row = inflater.inflate(layoutResourceId, parent, false);
			
			holder = new ThumbHolder();
			holder.txtTitle = (TextView) row.findViewById(R.id.ThumbTitle);
			holder.imageItem = (ImageView) row.findViewById(R.id.ThumbImage);
			holder.pbHealth = (ProgressBar) row.findViewById(R.id.ThumbHealth);
			holder.txtSize = (TextView) row.findViewById(R.id.ThumbSize);
			row.setTag(holder);
		} else {
			holder = (ThumbHolder) row.getTag();
		}
    	
		ThumbItem item = this.getItem(position);
		holder.txtTitle.setText(item.getTitle());
		holder.pbHealth.setProgress(item.getHealth().ordinal());
		holder.pbHealth.getProgressDrawable().setColorFilter(item.getHealthColor(), Mode.SRC_IN);
		holder.txtSize.setText(item.getSize() + " MiB");
		loadBitmap(item.getThumbnailId(), holder.imageItem);

		return row;
	}
	
	public void loadBitmap(int resId, ImageView mImageView) {
	    final String imageKey = String.valueOf(resId);

	    final Bitmap bitmap = mThumbCache.getThumb(imageKey);
	    if (bitmap != null) {
	        mImageView.setImageBitmap(bitmap);
	    } else {
	        //mImageView.setImageResource(R.drawable.image_placeholder);
	        //BitmapWorkerTask task = new BitmapWorkerTask(mImageView);
	        //task.execute(resId);
	    	
	    	ThumbLoader mThumbLoader = new ThumbLoader(this.context, resId, mThumbWidth, mThumbHeight);
	    	Bitmap mNewBitmap = mThumbLoader.getThumb();
	    	mThumbCache.addThumb(imageKey, mNewBitmap);
	    	
	    	mImageView.setImageBitmap(mNewBitmap);
	    }
	}

	static class ThumbHolder {
		TextView txtTitle;
		ImageView imageItem;
		ProgressBar pbHealth;
		TextView txtSize;
	}
	

}