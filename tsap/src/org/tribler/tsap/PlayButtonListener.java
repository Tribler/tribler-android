package org.tribler.tsap;

import java.util.Observable;
import java.util.Observer;

import org.tribler.tsap.downloads.Download;
import org.tribler.tsap.downloads.XMLRPCDownloadManager;
import org.tribler.tsap.thumbgrid.ThumbItem;

import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class PlayButtonListener implements OnClickListener, Observer {
	
	private ThumbItem thumbData;
	private Poller mPoller;
	
	public PlayButtonListener(ThumbItem thumbData)
	{
		this.thumbData=thumbData;
		this.mPoller = new Poller(this, 1000);
	}

	@Override
	public void onClick(View buttonClicked) {
		// start downloading the torrent
		Button button = (Button) buttonClicked;
		XMLRPCDownloadManager.getInstance().downloadTorrent(thumbData.getInfoHash(), thumbData.getTitle());
		
		//disable the play button
		button.setEnabled(false);
		
		//start waiting for VOD
		mPoller.start();
	}

	@Override
	public void update(Observable observable, Object data) {
		XMLRPCDownloadManager.getInstance().getProgressInfo(thumbData.getInfoHash());
		Download dwnld = XMLRPCDownloadManager.getInstance().getCurrentDownload();
		if(dwnld != null)
		{
			Double vod_eta = dwnld.getVOD_ETA();
			Log.d("PlayButtonListener", "VOD_ETA is: "+vod_eta);
		}		
	}

}
