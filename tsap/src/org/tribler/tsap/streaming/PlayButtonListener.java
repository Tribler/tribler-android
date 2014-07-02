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
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * Class that makes sure that a torrent is downloaded (if needed), shows a
 * dialog with the download status and starts VLC when the video is ready to be
 * streamed.
 * 
 * @author Niels Spruit
 * 
 */
public class PlayButtonListener implements OnClickListener, IPollListener {

	private final int POLLER_INTERVAL = 1000; // in ms

	private String infoHash;
	private boolean needsToBeDownloaded;
	private MainThreadPoller mPoller;
	private AlertDialog aDialog;
	private boolean inVODMode = false;
	private Activity mActivity;
	private Download mDownload;

	public PlayButtonListener(Torrent thumbData, Activity activity) {
		this.infoHash = thumbData.getInfoHash();
		this.needsToBeDownloaded = true;
		this.mActivity = activity;
		this.mPoller = new MainThreadPoller(this, POLLER_INTERVAL, mActivity);
	}

	public PlayButtonListener(String infoHash, Activity activity) {
		this.infoHash = infoHash;
		this.needsToBeDownloaded = false;
		this.mActivity = activity;
		this.mPoller = new MainThreadPoller(this, POLLER_INTERVAL, mActivity);
	}

	@Override
	public void onClick(View buttonClicked) {
		// start downloading the torrent
		Button button = (Button) buttonClicked;
		if (needsToBeDownloaded) {
			startDownload();
		}

		// disable the play button
		button.setEnabled(false);

		// start waiting for VOD
		mPoller.start();
		VODDialogFragment dialog = new VODDialogFragment(mPoller, button);
		dialog.show(mActivity.getFragmentManager(), "wait_vod");
		aDialog = (AlertDialog) dialog.getDialog();
	}

	/**
	 * Starts downloading the torrent
	 */
	private void startDownload() {
		XMLRPCDownloadManager.getInstance().downloadTorrent(infoHash, "");
	}

	@Override
	public void onPoll() {
		XMLRPCDownloadManager.getInstance().getProgressInfo(infoHash);
		mDownload = XMLRPCDownloadManager.getInstance().getCurrentDownload();
		// AlertDialog aDialog = (AlertDialog) dialog.getDialog();
		if (mDownload != null) {
			if (!mDownload.getTorrent().getInfoHash().equals(infoHash)) {
				return;
			}

			if (mDownload.isVODPlayable()) {
				startStreaming();

			} else {
				int statusCode = mDownload.getDownloadStatus().getStatus();

				if (statusCode == 3 && !inVODMode) {
					XMLRPCDownloadManager.getInstance().startVOD(infoHash);
					inVODMode = true;
				}
				if (aDialog != null)
					updateDialog();

			}
		}
	}

	/**
	 * Starts streaming the video with VLC and cleans up the dialog and poller
	 */
	private void startStreaming() {
		Intent intent = new Intent(Intent.ACTION_VIEW, XMLRPCDownloadManager
				.getInstance().getVideoUri(),
				mActivity.getApplicationContext(), VideoPlayerActivity.class);
		mActivity.startActivity(intent);
		mPoller.stop();
		aDialog.cancel();
	}

	/**
	 * @param downStat
	 * @param vod_eta
	 */
	private void updateDialog() {
		int statusCode = mDownload.getDownloadStatus().getStatus();
		if (statusCode == 3) {
			aDialog.setMessage("Video starts playing in about "
					+ Utility.convertSecondsToString(mDownload.getVOD_ETA())
					+ " ("
					+ Utility.convertBytesPerSecToString(mDownload
							.getDownloadStatus().getDownloadSpeed()) + ").");
		} else {
			aDialog.setMessage("Download status: "
					+ Utility.convertDownloadStateIntToMessage(statusCode)
					+ ((statusCode == 2) ? " ("
							+ Math.round(mDownload.getDownloadStatus()
									.getProgress() * 100) + "%)" : ""));
		}
	}
}
