package org.tribler.tsap.downloads;

import org.tribler.tsap.R;
import org.tribler.tsap.Utility;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class DownloadActivity extends Activity {
	private ActionBar mActionBar;
	private Download mDownload;
	private View mView;

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
		// TextView title = (TextView)
		// mView.findViewById(R.id.download_info_title);
		// title.setText(mDownload.getName());

		TextView type = (TextView) mView.findViewById(R.id.download_info_type);
		type.setText("Video");

		TextView date = (TextView) mView
				.findViewById(R.id.download_info_upload_date);
		date.setText("5-11-1998");

		TextView size = (TextView) mView
				.findViewById(R.id.download_info_filesize);
		size.setText(String.valueOf(1) + "KB");

		TextView download = (TextView) mView
				.findViewById(R.id.download_info_down_speed);
		download.setText(Utility.convertBytesPerSecToString(mDownload
				.getDownloadSpeed()));

		TextView upload = (TextView) mView
				.findViewById(R.id.download_info_up_speed);
		upload.setText(Utility.convertBytesPerSecToString(mDownload
				.getUploadSpeed()));

		ProgressBar bar = (ProgressBar) mView
				.findViewById(R.id.download_info_progress_bar);
		bar.setProgress((int) (100 * mDownload.getProgress()));
	}

	private void setStreamButtonListener() {
		Button streamButton = (Button) mView
				.findViewById(R.id.download_info_stream_button);
		View.OnClickListener streamButtonOnClickListener = new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				XMLRPCDownloadManager.getInstance().startVOD(
						mDownload.getInfoHash());
			}
		};
		streamButton.setOnClickListener(streamButtonOnClickListener);
	}

	private void setTorrentRemoveButtonListener(int resource) {
		Button removeButton = (Button) mView.findViewById(resource);
		final DownloadActivity a = this;

		View.OnClickListener removeButtonOnClickListener = new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// Show dialog
				AlertDialog.Builder alertRemove = new AlertDialog.Builder(v.getContext());
				alertRemove
				    .setTitle(R.string.remove_download_dialog_title)
				    .setMessage(R.string.remove_download_dialog_message)
				     // Android.R.string.yes == Ok - https://code.google.com/p/android/issues/detail?id=3713
				    .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
				        public void onClick(DialogInterface dialog, int which) { 
							XMLRPCDownloadManager.getInstance().deleteTorrent(
									mDownload.getInfoHash(), true, a);
				        }
				     })
				     // Android.R.string.no == Cancel - https://code.google.com/p/android/issues/detail?id=3713
				    .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
				        public void onClick(DialogInterface dialog, int which) { 
							XMLRPCDownloadManager.getInstance().deleteTorrent(
									mDownload.getInfoHash(), false, a);
				        }
				     })
				    //.setIcon(android.R.drawable.ic_dialog_alert)
				    .setNeutralButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// .. nothing
						}
					})
				    .show();
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

		setupActionBar(mDownload.getName());
		fillLayout();
		setStreamButtonListener();
		setTorrentRemoveButtonListener(R.id.download_info_delete_torrent_button);
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
}