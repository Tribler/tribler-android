package org.tribler.tsap.util;

import java.io.File;

import org.tribler.tsap.R;

import android.content.Context;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class ThumbnailUtils {

	public static void loadThumbnail(File file, ImageView mImageView, Context context) {
		float dens = context.getResources().getDisplayMetrics().density;
		int thumbWidth = (int) (100 * dens);
		int thumbHeight = (int) (150 * dens);
		Picasso.with(context).load(file).placeholder(R.drawable.default_thumb)
			.resize(thumbWidth, thumbHeight).into(mImageView);	
	}

}
