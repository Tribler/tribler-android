package org.tribler.tsap.channels;

import org.tribler.tsap.R;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Fragment displaying the channel description
 * 
 * @author Dirk Schut
 */
public class ChannelDescriptionFragment extends Fragment {

	private View mView;
	private String mDescription = null;

	/**
	 * Sets the description to view in this fragment.
	 * 
	 * @param description
	 *            The description text
	 */
	public void setDescription(String description) {
		mDescription = description;
		if (mView != null) {
			TextView field = (TextView) mView.findViewById(R.id.channel_description);
			field.setText(description);
		}
	}

	/**
	 * Creates a view. This function is automatically called by Android when
	 * needed.
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		mView = inflater.inflate(R.layout.fragment_channel_description, container, false);

		if (mDescription != null)
			setDescription(mDescription);

		return mView;
	}

}
