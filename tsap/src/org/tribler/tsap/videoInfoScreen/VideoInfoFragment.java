package org.tribler.tsap.videoInfoScreen;

import org.tribler.tsap.R;
import org.tribler.tsap.Torrent;
import org.tribler.tsap.downloads.DownloadActivity;
import org.tribler.tsap.downloads.XMLRPCDownloadManager;
import org.tribler.tsap.streaming.PlayButtonListener;
import org.tribler.tsap.util.ThumbnailUtils;
import org.tribler.tsap.util.Utility;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Fragment that shows the detailed info belonging to the selected torrent in
 * the thumb grid
 * 
 * @author Niels Spruit
 */
public class VideoInfoFragment extends Fragment {

	private Torrent thumbData;
	private View view;
	private Context context;

	/**
	 * Initializes the video info fragment's layout according to the selected
	 * torrent
	 * 
	 * @param inflater
	 *            The inflater used to inflate the video info layout
	 * @param container
	 *            The container view of this fragment
	 * @param savedInstanceState
	 *            The state of the saved instance
	 * @return The created view
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		view = inflater.inflate(R.layout.fragment_video_info, container, false);
		context = container.getContext();
		if (getArguments() != null) {
			thumbData = (Torrent) getArguments().getSerializable("thumbData");
			setHasOptionsMenu(true);
		}
		
		setValues();
		return view;
	}

	/**
	 * Changes the values of the views according to the torrent metadata
	 */
	private void setValues() {
		TextView title = (TextView) view.findViewById(R.id.video_info_title);
		title.setText(thumbData.getName());

		TextView type = (TextView) view.findViewById(R.id.video_details_type);
		type.setText(thumbData.getCategory());

		TextView size = (TextView) view
				.findViewById(R.id.video_details_filesize);
		size.setText(Utility.convertBytesToString(thumbData.getSize()));

		TextView seeders = (TextView) view
				.findViewById(R.id.video_details_seeders);
		seeders.setText(thumbData.getSeeders() == -1 ? "unknown" : ""
				+ thumbData.getSeeders());

		TextView leechers = (TextView) view
				.findViewById(R.id.video_details_leechers);
		leechers.setText(thumbData.getLeechers() == -1 ? "unknown" : ""
				+ thumbData.getLeechers());

		TextView descr = (TextView) view
				.findViewById(R.id.video_info_description);
		descr.setText("");

		ImageView thumb = (ImageView) view
				.findViewById(R.id.video_info_thumbnail);
		ThumbnailUtils.loadThumbnail(thumbData.getThumbnailFile(), thumb,
				context);
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.menu_video_info_fragment, menu);
		//super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle presses on the action bar items
		switch (item.getItemId()) {
		case R.id.action_download:
			onDownloadPressed();
			return true;
		case R.id.action_stream:
			onStreamPressed();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	public void onStreamPressed() {
		DownloadActivity.onStreamPressed(thumbData, getActivity());
	}

	public void onDownloadPressed() {
		DownloadActivity.onDownloadPressed(thumbData);
	}
}
