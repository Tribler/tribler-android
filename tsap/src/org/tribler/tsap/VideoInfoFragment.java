/**
 * 
 */
package org.tribler.tsap;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author niels
 * 
 */
public class VideoInfoFragment extends Fragment {
	
	private int torrentID=0;	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {		
		return inflater.inflate(R.layout.fragment_video_info, container, false);
	}
	
	/**
	 * Changes the values of the views accorcing to the torrent metadata
	 */
	private void setValues()
	{
		
	}

}
