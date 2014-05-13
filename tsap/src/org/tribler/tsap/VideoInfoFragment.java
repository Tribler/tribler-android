/**
 * 
 */
package org.tribler.tsap;

import org.tribler.tsap.videoInfoScreen.Torrent;
import org.tribler.tsap.videoInfoScreen.TorrentManager;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @author niels
 * 
 */
public class VideoInfoFragment extends Fragment {

	private int torrentID = 0;
	private View view;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_video_info, container,
				false);
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
		thumb.setImageResource(selectedTorrent.getThumbnailID());
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

}
