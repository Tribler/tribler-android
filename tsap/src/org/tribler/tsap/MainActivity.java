package org.tribler.tsap;

import java.net.MalformedURLException;
import java.net.URL;

import org.tribler.tsap.channels.ChannelListFragment;
import org.tribler.tsap.downloads.DownloadListAdapter;
import org.tribler.tsap.downloads.DownloadListFragment;
import org.tribler.tsap.downloads.XMLRPCDownloadManager;
import org.tribler.tsap.settings.Settings;
import org.tribler.tsap.settings.SettingsFragment;
import org.tribler.tsap.thumbgrid.ThumbGridFragment;
import org.videolan.vlc.VLCApplication;

import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;

/**
 * The activity that is started when the application is launched
 * 
 * @author Dirk Schut, Wendo Sabée and Niels Spruit
 * 
 */
public class MainActivity extends Activity implements
		NavigationDrawerFragment.NavigationDrawerCallbacks {

	/**
	 * Fragments managing the behaviours, interactions and presentation of the
	 * navigation drawer.
	 */
	private NavigationDrawerFragment mNavigationDrawerFragment;
	private ThumbGridFragment mThumbGridFragment = new ThumbGridFragment();
	private ChannelListFragment mChannelFragment = new ChannelListFragment();
	private DownloadListFragment mDownloadFragment = new DownloadListFragment();
	private SettingsFragment mSettingsFragment = new SettingsFragment();

	/**
	 * Used to store the last screen title. For use in
	 * {@link #restoreActionBar()}.
	 */
	private CharSequence mTitle;

	/**
	 * Initializes the navigation drawer and sends the context to VLC
	 * 
	 * @param savedInstanceState
	 *            The state of the saved instance
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mNavigationDrawerFragment = (NavigationDrawerFragment) getFragmentManager()
				.findFragmentById(R.id.navigation_drawer);
		mTitle = getTitle();

		// Set up the drawer.
		mNavigationDrawerFragment.setUp(R.id.navigation_drawer,
				(DrawerLayout) findViewById(R.id.drawer_layout));

		// Send the current context to VLC
		VLCApplication.setContext(getApplicationContext());
		try {
			URL url = new URL("http://127.0.0.1:8000/tribler");
			DownloadListAdapter adapter = new DownloadListAdapter(this,
					R.layout.download_list_item);
			XMLRPCDownloadManager.setUp(adapter, url, this);
			Settings.setup(url, this);
		} catch (MalformedURLException e) {
			Log.e("DownloadListFragment", "URL was malformed:\n");
			e.printStackTrace();
		}
	}

	/**
	 * When the user presses back when the thumb grid or the channel list is
	 * visible, the home screen activity is launched and this activity and the
	 * service is finished. If none of these screens are visible, the default
	 * behaviour is used.
	 */
	@Override
	public void onBackPressed() {
		if (mThumbGridFragment.isVisible() || mChannelFragment.isVisible()
				|| mDownloadFragment.isVisible() || mSettingsFragment.isVisible()) {
			new OnQuitDialog(this).show();
		} else
			super.onBackPressed();
	}

	/**
	 * Updates the GUI according to the selected navigation drawer item by
	 * loading the correct fragment
	 * 
	 * @param position
	 *            The position of the navigation drawer item that has been
	 *            clicked
	 */
	@Override
	public void onNavigationDrawerItemSelected(int position) {
		FragmentManager fragmentManager = getFragmentManager();
		fragmentManager.popBackStack(null,
				FragmentManager.POP_BACK_STACK_INCLUSIVE);
		switch (position) {
		case 0:
			fragmentManager.beginTransaction()
					.replace(R.id.container, mThumbGridFragment).commit();
			mTitle = getString(R.string.title_section_home);
			break;
		case 1:
			fragmentManager.beginTransaction()
					.replace(R.id.container, mDownloadFragment).commit();
			mTitle = getString(R.string.title_section_downloads);
			break;
		case 2:
			fragmentManager.beginTransaction()
					.replace(R.id.container, mSettingsFragment).commit();
			mTitle = getString(R.string.title_section_settings);
			break;
		}
	}

	/**
	 * Restores the action bar (e.g. the title is set to the current fragment)
	 */
	public void restoreActionBar() {
		ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		actionBar.setDisplayShowTitleEnabled(true);
		actionBar.setTitle(mTitle);
	}

	/**
	 * Creates the options menu containing the settings
	 * 
	 * @param menu
	 *            The menu that will be created
	 * @return True iff the menu has been set up correctly
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		if (!mNavigationDrawerFragment.isDrawerOpen()) {
			// Only show items in the action bar relevant to this screen
			// if the drawer is not showing. Otherwise, let the drawer
			// decide what to show in the action bar.
			restoreActionBar();
			return true;
		}

		return super.onCreateOptionsMenu(menu);
	}
	
	/**
	 * Returns the instance of the ChannelListFragment
	 * 
	 * @return This Activity's ChannelListFragment instance
	 */
	public ChannelListFragment getChannelListFragment() {
		return mChannelFragment;
	}

	/**
	 * Returns the instance of the ThumbGridFragment
	 * 
	 * @return This Activity's ThumbGridFragment instance
	 */
	public ThumbGridFragment getThumbGridFragment() {
		return mThumbGridFragment;
	}

	/**
	 * Returns the instance of the DownloadListFragment
	 * 
	 * @return This Activity's DownloadListFragment instance
	 */
	public DownloadListFragment getDownloadListFragment() {
		return mDownloadFragment;
	}
}
