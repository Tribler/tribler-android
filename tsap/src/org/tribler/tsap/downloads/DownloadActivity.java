package org.tribler.tsap.downloads;

import org.tribler.tsap.R;
import org.tribler.tsap.Utility;
import org.videolan.vlc.gui.video.VideoPlayerActivity;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class DownloadActivity extends Activity {
	private ActionBar mActionBar;
	private Download mDownload;
	private View mView;
	
	public final static String INTENT_MESSAGE = "org.tribler.tsap.DownloadActivity.IntentMessage";
	/**
	 * Sets the desired options in the action bar
	 * @param title
	 * 			The title to be displayed in the action bar
	 */
	private void setupActionBar(String title) {
		mActionBar = getActionBar();
		mActionBar.setTitle(title);
		mActionBar.setDisplayHomeAsUpEnabled(true);
	}
	
	private void fillLayout() {
		//TextView title = (TextView) mView.findViewById(R.id.download_info_title);
		//title.setText(mDownload.getName());

		TextView type = (TextView) mView.findViewById(R.id.download_info_type);
		type.setText("Video");

		TextView date = (TextView) mView
				.findViewById(R.id.download_info_upload_date);
		date.setText("5-11-1998");

		TextView size = (TextView) mView
				.findViewById(R.id.download_info_filesize);
		size.setText(String.valueOf(1)+"KB");

		TextView download = (TextView) mView
				.findViewById(R.id.download_info_down_speed);
		download.setText(Utility.convertBytesPerSecToString(mDownload.getDownloadSpeed()));

		TextView upload = (TextView) mView
				.findViewById(R.id.download_info_up_speed);
		upload.setText(Utility.convertBytesPerSecToString(mDownload.getUploadSpeed()));

		ProgressBar bar = (ProgressBar) mView
				.findViewById(R.id.download_info_progress_bar);
		bar.setProgress((int)(100*mDownload.getProgress()));
	}
	
	private void setStreamButtonListener()
	{
		Button streamButton = (Button) mView
				.findViewById(R.id.download_info_stream_button);
		View.OnClickListener streamButtonOnClickListener = new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				String URL = "http://inventos.ru/video/sintel/sintel-q3.mp4";
				Uri link = Uri.parse(URL);
				Intent intent = new Intent(Intent.ACTION_VIEW, link,
						getApplicationContext(),
						VideoPlayerActivity.class);
				startActivity(intent);
			}
		};
		streamButton.setOnClickListener(streamButtonOnClickListener);
	}
	private void setRemoveButtonListener()
	{
		Button removeButton = (Button) mView
				.findViewById(R.id.download_info_delete_button);
		final Activity a = this;
		View.OnClickListener removeButtonOnClickListener = new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				//TODO: actually remove the torrent
				a.onBackPressed();
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
		mDownload = (Download)intent.getSerializableExtra(INTENT_MESSAGE);
		
		setupActionBar(mDownload.getName());
		fillLayout();
		setStreamButtonListener();
		setRemoveButtonListener();
	}
	
	/**
	 * Called when one of the icons in the start bar is tapped:
	 * When the home icon is tapped, go back.
	 * If any other icon is tapped do the default action.
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem menuItem)
	{
		if(menuItem.getItemId() == android.R.id.home)
		{
			onBackPressed();
			return true;
		}
		else
		{
			return super.onOptionsItemSelected(menuItem);
		}
	}
}