/**
 * 
 */
package org.tribler.tsap;

import org.tribler.tsap.videoInfoScreen.Torrent;
import org.tribler.tsap.videoInfoScreen.TorrentManager;

import com.squareup.picasso.Picasso;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @author niels
 * 
 */
public class VideoInfoFragment extends Fragment {

	private int torrentID = 5;
	private View view;
	private Context context;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_video_info, container,
				false);
		context = container.getContext();
		TorrentManager.initiliazeTorrents();
		setValues();
		return view;
	}

	/**
	 * Changes the values of the views according to the torrent metadata
	 */
	private void setValues() {
		Torrent selectedTorrent = getCurrentTorrent();
		TextView title = (TextView) view.findViewById(
				R.id.video_info_title);
		title.setText(selectedTorrent.getName());

		TextView type = (TextView) view.findViewById(
				R.id.video_details_type);
		type.setText(selectedTorrent.getType());

		TextView date = (TextView) view.findViewById(
				R.id.video_details_upload_date);
		date.setText(selectedTorrent.getUploadDate());

		TextView size = (TextView) view.findViewById(
				R.id.video_details_filesize);
		size.setText(selectedTorrent.getFilesize());

		TextView seeders = (TextView) view.findViewById(
				R.id.video_details_seeders);
		seeders.setText(selectedTorrent.getSeeders());

		TextView leechers = (TextView) view.findViewById(
				R.id.video_details_leechers);
		leechers.setText(selectedTorrent.getLeechers());

		TextView descr = (TextView) view.findViewById(
				R.id.video_info_description);
		descr.setText(selectedTorrent.getDescription());

		ImageView thumb = (ImageView) view.findViewById(
				R.id.video_info_thumbnail);
		//thumb.setImageResource(selectedTorrent.getThumbnailID());
		loadBitmap(selectedTorrent.getThumbnailID(), thumb);
	}

	/**
	 * @return the torrent with id=torrentID iff torrent with id=torrentID
	 *         exists, otherwise torrent with id=0 is returned
	 */
	private Torrent getCurrentTorrent() {
		if (TorrentManager.containsTorrentWithID(torrentID))
			return TorrentManager.getTorrentById(torrentID);
		return TorrentManager.getTorrentById(0);
	}
	
	private void loadBitmap(int resId, ImageView mImageView) {
		float dens = context.getResources().getDisplayMetrics().density;
    	int thumbWidth = (int)(100 * dens);
    	int thumbHeight = (int)(150 * dens);
		Picasso.with(context)
			.load(resId)
			.placeholder(R.drawable.default_thumb)
			.resize(thumbWidth, thumbHeight)
			.into(mImageView);		
	}

}
