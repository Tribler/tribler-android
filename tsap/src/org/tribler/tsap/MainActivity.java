package org.tribler.tsap;

import org.videolan.vlc.VLCApplication;

import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;

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
	private ChannelListFragment channelFragment = new ChannelListFragment();

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
					.replace(R.id.container, channelFragment).commit();
			mTitle = getString(R.string.title_section_channels);
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
			getMenuInflater().inflate(R.menu.main_activity, menu);
			restoreActionBar();
			return true;
		}

		return super.onCreateOptionsMenu(menu);
	}

	/**
	 * Defines the behaviour of selecting a menu item
	 * 
	 * @param item
	 *            The menu item that the user has clicked
	 * @return True iff the action belonging to the menu item has been handled
	 *         correcly
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * Returns the instance of the ChannelListFragment
	 * 
	 * @return This Activity's ChannelListFragment instance
	 */
	public ChannelListFragment getChannelListFragment() {
		return channelFragment;
	}

	/**
	 * Returns the instance of the ThumbGridFragment
	 * 
	 * @return This Activity's ThumbGridFragment instance
	 */
	public ThumbGridFragment getThumbGridFragment() {
		return mThumbGridFragment;
	}
}
