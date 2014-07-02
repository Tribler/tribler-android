package org.tribler.tsap.downloads;

import org.tribler.tsap.R;
import org.tribler.tsap.Torrent;
import org.tribler.tsap.streaming.PlayButtonListener;
import org.tribler.tsap.util.MainThreadPoller;
import org.tribler.tsap.util.Poller.IPollListener;
import org.tribler.tsap.util.ThumbnailUtils;
import org.tribler.tsap.util.Utility;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class DownloadActivity extends Activity implements IPollListener {
	private ActionBar mActionBar;
	private Download mDownload;
	private Torrent mTorrent;
	private View mView;
	private MainThreadPoller mPoller;

	public final static String INTENT_MESSAGE = "org.tribler.tsap.DownloadActivity.IntentMessage";

	/**
	 * Sets the desired options in the action bar
	 * 
	 * @param title
	 *            The title to be displayed in the action bar
	 */
	private void setupActionBar(String title) {
		mActionBar = getActionBar();
		mActionBar.setTitle(title);
		mActionBar.setDisplayHomeAsUpEnabled(true);
	}

	private void fillLayout() {
		DownloadStatus downStat = mDownload.getDownloadStatus();
		int statusCode = downStat.getStatus();

		TextView size = (TextView) mView
				.findViewById(R.id.download_info_filesize);
		size.setText(Utility.convertBytesToString(mTorrent.getSize()));

		TextView download = (TextView) mView
				.findViewById(R.id.download_info_down_speed);
		download.setText(Utility.convertBytesPerSecToString(downStat
				.getDownloadSpeed()));

		TextView upload = (TextView) mView
				.findViewById(R.id.download_info_up_speed);
		upload.setText(Utility.convertBytesPerSecToString(downStat
				.getUploadSpeed()));

		TextView availability = (TextView) mView
				.findViewById(R.id.download_info_availability);
		availability.setText(Integer.toString(mDownload.getAvailability()));

		TextView descr = (TextView) mView
				.findViewById(R.id.download_info_description);
		descr.setText("");

		ImageView thumb = (ImageView) mView
				.findViewById(R.id.download_info_thumbnail);

		ThumbnailUtils.loadThumbnail(
				ThumbnailUtils.getThumbnailLocation(mTorrent.getInfoHash()),
				thumb, this);

		TextView status = (TextView) mView
				.findViewById(R.id.download_info_status_text);
		status.setText(Utility.convertDownloadStateIntToMessage(statusCode)
				+ ((statusCode == 2 || statusCode == 3) ? " ("
						+ Math.round(downStat.getProgress() * 100) + "%)" : ""));

		TextView eta = (TextView) mView
				.findViewById(R.id.download_info_eta_text);
		eta.setText((statusCode == 3) ? Utility.convertSecondsToString(downStat
				.getETA()) : "Unknown");

		ProgressBar bar = (ProgressBar) mView
				.findViewById(R.id.download_info_progress_bar);
		bar.setProgress((int) (100 * downStat.getProgress()));
	}

	private void setStreamButtonListener() {
		Button streamButton = (Button) mView
				.findViewById(R.id.download_info_stream_button);
		View.OnClickListener streamButtonOnClickListener = new PlayButtonListener(
				mTorrent.getInfoHash(), getFragmentManager(), this);
		streamButton.setOnClickListener(streamButtonOnClickListener);
	}

	private void setTorrentRemoveButtonListener(int resource) {
		Button removeButton = (Button) mView.findViewById(resource);
		final DownloadActivity a = this;

		View.OnClickListener removeButtonOnClickListener = new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// Show dialog
				AlertDialog.Builder alertRemove = new AlertDialog.Builder(
						v.getContext());
				alertRemove.setTitle(R.string.remove_download_dialog_title)
						.setMessage(R.string.remove_download_dialog_message)
						// Android.R.string.yes == Ok -
						// https://code.google.com/p/android/issues/detail?id=3713
						.setPositiveButton(R.string.yes,
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int which) {
										XMLRPCDownloadManager.getInstance()
												.deleteTorrent(
														mTorrent.getInfoHash(),
														true);
										a.onBackPressed();
									}
								})
						// Android.R.string.no == Cancel -
						// https://code.google.com/p/android/issues/detail?id=3713
						.setNegativeButton(R.string.no,
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int which) {
										XMLRPCDownloadManager.getInstance()
												.deleteTorrent(
														mTorrent.getInfoHash(),
														false);
										a.onBackPressed();
									}
								})
						// .setIcon(android.R.drawable.ic_dialog_alert)
						.setNeutralButton(android.R.string.cancel,
								new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog,
											int which) {
									}
								}).show();
			}
		};
		removeButton.setOnClickListener(removeButtonOnClickListener);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mView = getWindow().getDecorView().getRootView();
		setContentView(R.layout.activity_download);

		Intent intent = getIntent();
		mDownload = (Download) intent.getSerializableExtra(INTENT_MESSAGE);
		mTorrent = mDownload.getTorrent();

		setupActionBar(mTorrent.getName());
		fillLayout();
		setStreamButtonListener();
		setTorrentRemoveButtonListener(R.id.download_info_delete_torrent_button);

		mPoller = new MainThreadPoller(this, 2000, this);
		mPoller.start();
	}

	/**
	 * Pauses polling
	 */
	@Override
	public void onPause() {
		super.onPause();
		mPoller.stop();
	}

	/**
	 * Resumes the poller
	 */
	@Override
	public void onResume() {
		super.onResume();
		mPoller.start();
	}

	/**
	 * Called when one of the icons in the start bar is tapped: When the home
	 * icon is tapped, go back. If any other icon is tapped do the default
	 * action.
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem menuItem) {
		if (menuItem.getItemId() == android.R.id.home) {
			onBackPressed();
			return true;
		} else {
			return super.onOptionsItemSelected(menuItem);
		}
	}

	@Override
	public void onPoll() {
		String infohash = mTorrent.getInfoHash();
		XMLRPCDownloadManager.getInstance().getProgressInfo(infohash);
		Download currDownload = XMLRPCDownloadManager.getInstance()
				.getCurrentDownload();
		if (currDownload != null
				&& currDownload.getTorrent().getInfoHash().equals(infohash)) {
			mDownload = currDownload;
			fillLayout();
		}
	}
}