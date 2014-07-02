package org.tribler.tsap.streaming;

import org.tribler.tsap.Torrent;
import org.tribler.tsap.downloads.Download;
import org.tribler.tsap.downloads.XMLRPCDownloadManager;
import org.tribler.tsap.util.MainThreadPoller;
import org.tribler.tsap.util.Poller.IPollListener;
import org.tribler.tsap.util.Utility;
import org.videolan.vlc.gui.video.VideoPlayerActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class PlayButtonListener implements OnClickListener, IPollListener {

	private Torrent thumbData;
	private String infoHash;
	private boolean needsToBeDownloaded;
	private MainThreadPoller mPoller;
	private FragmentManager mFragManager;
	private VODDialogFragment dialog;
	private boolean inVODMode = false;
	private Activity mActivity;

	public PlayButtonListener(Torrent thumbData, FragmentManager fragManager,
			Activity activity) {
		this.thumbData = thumbData;
		this.infoHash = thumbData.getInfoHash();
		this.needsToBeDownloaded = true;
		this.mFragManager = fragManager;
		this.mActivity = activity;
		this.mPoller = new MainThreadPoller(this, 1000, mActivity);
	}

	public PlayButtonListener(String infoHash, FragmentManager fragManager,
			Activity activity) {
		this.thumbData = null;
		this.infoHash = infoHash;
		this.needsToBeDownloaded = false;
		this.mFragManager = fragManager;
		this.mActivity = activity;
		this.mPoller = new MainThreadPoller(this, 1000, mActivity);
	}

	@Override
	public void onClick(View buttonClicked) {
		// start downloading the torrent
		Button button = (Button) buttonClicked;
		if (needsToBeDownloaded) {
			XMLRPCDownloadManager.getInstance().downloadTorrent(infoHash,
					thumbData.getName());
		}

		// disable the play button
		button.setEnabled(false);

		// start waiting for VOD
		mPoller.start();
		dialog = new VODDialogFragment(mPoller, button);
		dialog.show(mFragManager, "wait_vod");
	}

	@Override
	public void onPoll() {
		XMLRPCDownloadManager.getInstance().getProgressInfo(infoHash);
		Download dwnld = XMLRPCDownloadManager.getInstance()
				.getCurrentDownload();
		AlertDialog aDialog = (AlertDialog) dialog.getDialog();
		if (dwnld != null) {
			if(!dwnld.getInfoHash().equals(infoHash))
			{
				return;
			}
			
			if (dwnld.isVODPlayable()) {
				Intent intent = new Intent(Intent.ACTION_VIEW,
						XMLRPCDownloadManager.getInstance().getVideoUri(),
						mActivity.getApplicationContext(),
						VideoPlayerActivity.class);
				mActivity.startActivity(intent);
				mPoller.stop();
				aDialog.cancel();

			} else {
				switch (dwnld.getStatus()) {
				case 3:
					// if state is downloading, start vod mode if not done
					// already:
					if (!inVODMode) {
						XMLRPCDownloadManager.getInstance().startVOD(infoHash);
						inVODMode = true;
					}

					Double vod_eta = dwnld.getVOD_ETA();
					Log.d("PlayButtonListener", "VOD_ETA is: " + vod_eta);

					if (aDialog != null)
						aDialog.setMessage("Video starts playing in about "
								+ Utility.convertSecondsToString(vod_eta) + " ("
								+ Utility.convertBytesPerSecToString(dwnld.getDownloadSpeed()) + ").");

					break;
				default:
					if (aDialog != null)
					{
						int dlstatus = dwnld.getStatus();
						aDialog.setMessage("Download status: "
								+ Utility.convertDownloadStateIntToMessage(dwnld.getStatus())
								+ ((dlstatus == 2) ? " (" + Math.round(dwnld.getProgress() * 100) + "%)" : ""));
					}
					break;
				}

			}
		}
	}
}
