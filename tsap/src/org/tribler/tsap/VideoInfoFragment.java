/**
 * 
 */
package org.tribler.tsap;

import org.tribler.tsap.videoInfoScreen.Torrent;
import org.tribler.tsap.videoInfoScreen.TorrentManager;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author niels
 * 
 */
public class VideoInfoFragment extends Fragment {

	private int torrentID = 0;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		setValues();
		return inflater.inflate(R.layout.fragment_video_info, container, false);
	}

	/**
	 * Changes the values of the views accorcing to the torrent metadata
	 */
	private void setValues() {
		Torrent selectedTorrent = getCurrentTorrent();
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
