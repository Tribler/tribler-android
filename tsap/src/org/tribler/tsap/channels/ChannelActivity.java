package org.tribler.tsap.channels;

import org.tribler.tsap.R;
import org.tribler.tsap.SimpleTabListener;
import org.tribler.tsap.channels.ChannelDescriptionFragment;
import org.tribler.tsap.thumbgrid.ThumbGridFragment;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

/**
 * Fragment that shows the info and torrents of a channel on two separate tabs
 * 
 * @author Dirk Schut
 */
public class ChannelActivity extends Activity {

	private ActionBar mActionBar;
	private ChannelDescriptionFragment mInfoFragment = new ChannelDescriptionFragment();
	private ThumbGridFragment mThumbFragment = new ThumbGridFragment();
	private Channel mChannel = null;
	//private ChannelDescriptionFragment mInfoFragment3 = new ChannelDescriptionFragment();
	
	public final static String INTENT_MESSAGE = "org.tribler.tsap.ChannelActivity.IntentMessage";

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
	/**
	 * Adds the tabs and listeners for the tabs
	 */
	private void setupTabs() {
		mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		SimpleTabListener listener1 = new SimpleTabListener(mInfoFragment, R.id.channel_tab_fragment_container);
		SimpleTabListener listener2 = new SimpleTabListener(mThumbFragment, R.id.channel_tab_fragment_container);
		//SimpleTabListener listener3 = new SimpleTabListener(mInfoFragment3, R.id.channel_tab_fragment_container);
		
		Tab DescriptionTab = mActionBar.newTab().setText("Description").setTabListener(listener1);
		Tab TorrentTab = mActionBar.newTab().setText("Torrents").setTabListener(listener2);
		//Tab CommentsTab = mActionBar.newTab().setText("Comments").setTabListener(listener3);

		mActionBar.addTab(DescriptionTab);
		mActionBar.addTab(TorrentTab);
		//mActionBar.addTab(CommentsTab);
		
		mActionBar.selectTab(TorrentTab);
	}
	
	/**
	 * 
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_channel);
		
		Intent intent = getIntent();
		mChannel = (Channel)intent.getSerializableExtra(INTENT_MESSAGE);
		
		setupActionBar(mChannel.getName());
		setupTabs();
		
		mInfoFragment.setDescription(mChannel.getDescription());
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
