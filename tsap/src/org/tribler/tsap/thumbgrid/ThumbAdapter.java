package org.tribler.tsap.thumbgrid;

import java.util.ArrayList;

import org.tribler.tsap.R;
import org.tribler.tsap.R.drawable;
import org.tribler.tsap.R.id;
import org.tribler.tsap.util.DiskLruCache;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PorterDuff.Mode;
import android.support.v4.util.LruCache;
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
	
	LruCache<String, Bitmap> memCache;
	DiskLruCache dskCache;
	
	public ThumbAdapter(Context context, int layoutResourceId, ArrayList<ThumbItem> data) {
		super(context, layoutResourceId, data);
		this.layoutResourceId = layoutResourceId;
		this.context = context;
		this.addAll(data);
		
	    // Get max available VM memory, exceeding this amount will throw an
	    // OutOfMemory exception. Stored in kilobytes as LruCache takes an
	    // int in its constructor.
	    final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);

	    // Use 1/8th of the available memory for this memory cache.
	    final int cacheSize = maxMemory / 2; // TODO: make it 8!

	    memCache = new LruCache<String, Bitmap>(cacheSize) {
	        @Override
	        protected int sizeOf(String key, Bitmap bitmap) {
	            // The cache size will be measured in kilobytes rather than
	            // number of items.
	            return bitmap.getByteCount() / 1024;
	        }
	    };
	}
	
	private void addBitmapToMemoryCache(String key, Bitmap bitmap) {
	    if (getBitmapFromMemCache(key) == null) {
	        memCache.put(key, bitmap);
	    }
	}

	private Bitmap getBitmapFromMemCache(String key) {
	    return (Bitmap) memCache.get(key);
	}
	
	public void loadBitmap(int resId, ImageView mImageView) {
	    final String imageKey = String.valueOf(resId);

	    final Bitmap bitmap = getBitmapFromMemCache(imageKey);
	    if (bitmap != null) {
	        mImageView.setImageBitmap(bitmap);
	    } else {
	        //mImageView.setImageResource(R.drawable.image_placeholder);
	        //BitmapWorkerTask task = new BitmapWorkerTask(mImageView);
	        //task.execute(resId);
			float s = this.getContext().getResources().getDisplayMetrics().density;
	    	int w = (int)(100 * s);
	    	int h = (int)(150 * s);
	    	
	    	Bitmap mNewBitmap = decodeSampledBitmapFromResource(this.context.getResources(), resId, w, h);
	    	addBitmapToMemoryCache(imageKey, mNewBitmap);
	    	
	    	mImageView.setImageBitmap(mNewBitmap);
	    }
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

	static class ThumbHolder {
		TextView txtTitle;
		ImageView imageItem;
		ProgressBar pbHealth;
		TextView txtSize;
	}
	
	private static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
			int reqWidth, int reqHeight) {

		// First decode with inJustDecodeBounds=true to check dimensions
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeResource(res, resId, options);

		// Calculate inSampleSize
		options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

		// Decode bitmap with inSampleSize set
		options.inJustDecodeBounds = false;
		return BitmapFactory.decodeResource(res, resId, options);
	}

	private static int calculateInSampleSize(
			BitmapFactory.Options options, int reqWidth, int reqHeight) {
		// Raw height and width of image
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;

		if (height > reqHeight || width > reqWidth) {

			final int halfHeight = height / 2;
			final int halfWidth = width / 2;

			// Calculate the largest inSampleSize value that is a power of 2 and keeps both
			// height and width larger than the requested height and width.
			while ((halfHeight / inSampleSize) > reqHeight
					&& (halfWidth / inSampleSize) > reqWidth) {
				inSampleSize *= 2;
			}
		}

		return inSampleSize;
	} 
}