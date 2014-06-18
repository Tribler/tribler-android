package org.tribler.tsap;

import java.util.Observable;
import java.util.Observer;

import org.tribler.tsap.downloads.Download;
import org.tribler.tsap.downloads.XMLRPCDownloadManager;
import org.tribler.tsap.thumbgrid.ThumbItem;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class PlayButtonListener implements OnClickListener, Observer {

	private ThumbItem thumbData;
	private Poller mPoller;
	private FragmentManager mFragManager;
	private VODDialogFragment dialog;
	private boolean inVODMode = false;

	public PlayButtonListener(ThumbItem thumbData, FragmentManager fragManager) {
		this.thumbData = thumbData;
		this.mPoller = new Poller(this, 1000);
		this.mFragManager = fragManager;
	}

	@Override
	public void onClick(View buttonClicked) {
		// start downloading the torrent
		Button button = (Button) buttonClicked;
		XMLRPCDownloadManager.getInstance().downloadTorrent(
				thumbData.getInfoHash(), thumbData.getTitle());

		// disable the play button
		button.setEnabled(false);

		// start waiting for VOD
		mPoller.start();
		dialog = new VODDialogFragment();
		dialog.show(mFragManager, "wait_vod");
	}

	@Override
	public void update(Observable observable, Object data) {
		XMLRPCDownloadManager.getInstance().getProgressInfo(
				thumbData.getInfoHash());
		Download dwnld = XMLRPCDownloadManager.getInstance()
				.getCurrentDownload();
		AlertDialog aDialog = (AlertDialog) dialog.getDialog();
		if (dwnld != null) {
			switch (dwnld.getStatus()) {
			case 3:
				//if state is downloading, start vod mode if not done already:
				if(!inVODMode)
				{
					XMLRPCDownloadManager.getInstance().startVOD(thumbData.getInfoHash());
					inVODMode = true;
				}
				Double vod_eta = dwnld.getVOD_ETA();
				Log.d("PlayButtonListener", "VOD_ETA is: " + vod_eta);

				if (aDialog != null)
					aDialog.setMessage("Video starts playing in about "
							+ Math.round(vod_eta) + " seconds");
				break;
			default:
				if (aDialog != null)
					aDialog.setMessage(Utility.convertDownloadStateIntToMessage(dwnld.getStatus()));
				break;
			}
		}
	}
}
