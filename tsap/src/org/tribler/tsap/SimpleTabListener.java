package org.tribler.tsap;

import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentTransaction;

/**
 * Minimal listener class for a single Android tab. If the tab is selected a chosen containerResource will be filled by a chosen fragment. 
 * @author Dirk Schut
 */
public class SimpleTabListener implements ActionBar.TabListener
{
	Fragment mFragment;
	int mContainerResource;
	
	/**
	 * Constructor: Sets 
	 * @param fragment
	 * @param containerResource
	 */
	public SimpleTabListener(Fragment fragment, int containerResource)
	{
		mFragment = fragment;
		mContainerResource = containerResource;
	}
	/**
	 * This function is called when this tab is selected: Replaces the fragment that is currently in mContainerResource with mFragment.
	 */
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
    	ft.replace(mContainerResource, mFragment);
    }
    
    /**
     * This function is called when this tab is no longer selected: Removes mFragment from the mContainerResource.
     */
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
    	ft.remove(mFragment);
    }
    
    /**
     * This function is called when the tab is selected again when it's already selected: Nothing happens.
     */
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {
        //ignore this event
    }
};