package org.tribler.tsap;

import java.util.ArrayList;
import java.util.Collection;

import android.app.Activity;
import android.widget.BaseAdapter;

/**
 * Adapter for managing the data and views for a list of channels.
 * 
 * @author Dirk Schut
 */

public abstract class AbstractArrayListAdapter<T> extends BaseAdapter {
	protected ArrayList<T> mContent;
	protected final Object mLock = new Object();
	protected Activity mActivity;

	/**
	 * Constructor: Initializes the AbstractArrayListAdapter with an empty
	 * ArrayList.
	 */
	public AbstractArrayListAdapter(Activity activity) {
		mContent = new ArrayList<T>();
		mActivity = activity;
	}

	/**
	 * Constructor: Initializes the AbstractArrayListAdapter with existing data.
	 * 
	 * @param initialContent
	 *            The initial content
	 */
	public AbstractArrayListAdapter(Activity activity, Collection<T> initialContent) {
		mActivity = activity;
		mContent = new ArrayList<T>();
		mContent.addAll(initialContent);
	}

	/**
	 * Returns the item at a certain position.
	 * 
	 * @param position
	 *            the position from which you want to retrieve the item
	 */
	@Override
	public T getItem(int position) {
		return mContent.get(position);
	}

	/**
	 * Returns the amount of items.
	 */
	@Override
	public int getCount() {
		synchronized (mLock) {
			return mContent.size();
		}
	}

	/**
	 * Returns the Id of the item at a certain position.
	 * Just like in ArrayAdapter this function just returns the position.
	 * 
	 * @param position
	 *            the position to retrieve the item from
	 */
	@Override
	public long getItemId(int position) {
		return position;
	}

	/**
	 * Removes all data from the adapater.
	 */
	public void clear() {
		synchronized (mLock) {
			mContent.clear();
		}
		notifyChangesToUiThread();
	}
	
	protected void notifyChangesToUiThread() {
		mActivity.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				notifyDataSetChanged();
			}
		});
	}
}