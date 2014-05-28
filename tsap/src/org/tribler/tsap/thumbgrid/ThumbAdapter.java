package org.tribler.tsap.thumbgrid;

import java.util.ArrayList;

import org.tribler.tsap.AbstractArrayListAdapter;
import org.tribler.tsap.R;

import com.squareup.picasso.Picasso;

import android.app.Activity;
import android.content.Context;
import android.graphics.PorterDuff.Mode;
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

		initializeHolderView(position, holder);

		return row;
	}

	/**
	 * Sets the ThumbHolder views to the correct values (the values of the thumb
	 * item)
	 * 
	 * @param position
	 *            The postion of the thumb item
	 * @param holder
	 *            The holder of which the view need to updated
	 */
	private void initializeHolderView(int position, ThumbHolder holder) {
		ThumbItem item = this.getItem(position);
		holder.txtTitle.setText(item.getTitle());
		holder.pbHealth.setProgress(item.getHealth().ordinal());
		holder.pbHealth.getProgressDrawable().setColorFilter(
				item.getHealthColor(), Mode.SRC_IN);
		holder.txtSize.setText(item.getSize() + " MiB");
		loadBitmap(item.getThumbnailId(), holder.imageItem);
	}

	/**
	 * Loads the thumbnail of the thumb item
	 * 
	 * @param resId
	 *            The resource id of the thumbnail
	 * @param mImageView
	 *            The ImageView in which the thumbnail should be loaded
	 */
	private void loadBitmap(int resId, ImageView mImageView) {
		Picasso.with(context).load(resId).placeholder(R.drawable.default_thumb)
				.resize(mThumbWidth, mThumbHeight).into(mImageView);
	}

	/**
	 * Class that holds the views of a ThumbItem
	 * 
	 * @author Wendo Sabée
	 */
	private static class ThumbHolder {
		TextView txtTitle;
		ImageView imageItem;
		ProgressBar pbHealth;
		TextView txtSize;
	}

}