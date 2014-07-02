package org.tribler.tsap.util;

import java.io.File;
import java.io.FilenameFilter;

import org.tribler.tsap.R;
import org.tribler.tsap.settings.Settings;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class ThumbnailUtils {

	public static void loadThumbnail(File file, ImageView mImageView,
			Context context) {
		float dens = context.getResources().getDisplayMetrics().density;
		int thumbWidth = (int) (100 * dens);
		int thumbHeight = (int) (150 * dens);
		Picasso.with(context).load(file).placeholder(R.drawable.default_thumb)
				.resize(thumbWidth, thumbHeight).into(mImageView);
	}

	public static void loadDefaultThumbnail(ImageView imageView, Context context) {
		loadThumbnail(null, imageView, context);
	}

	public static File getThumbnailLocation(final String infoHash) {
		File baseDirectory = Settings.getThumbFolder();
		if (baseDirectory == null || !baseDirectory.isDirectory()) {
			Log.e("ThumbnailUtils",
					"The collected_torrent_files thumbnailfolder could not be found");
			return null;
		}

		File thumbsDirectory = new File(baseDirectory, "thumbs-" + infoHash);
		if (!thumbsDirectory.exists()) {
			Log.d("ThumbnailUtils", "No thumbnailfolder found for " + infoHash);
			return null;
		}

		File thumbsSubDirectory = null;
		for (File file : thumbsDirectory.listFiles()) {
			if (file.isDirectory()) {
				thumbsSubDirectory = file;
				break;
			}
		}

		if (thumbsSubDirectory == null) {
			Log.d("ThumbnailUtils", "No thumbnail subfolder found for "
					+ infoHash);
			return null;
		}

		return findImage(thumbsSubDirectory);
	}

	private static File findImage(File directory) {
		File[] foundImages = directory.listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File file, String name) {
				return name.endsWith(".png") || name.endsWith(".gif")
						|| name.endsWith(".jpg") || name.endsWith(".jpeg");
			}
		});

		// TODO: Find the best one
		if (foundImages.length > 0) {
			return foundImages[0];
		} else {
			Log.d("ThumbnailUtils", "No thumbnailimages found: "
					+ foundImages.length);
			return null;
		}
	}
}
