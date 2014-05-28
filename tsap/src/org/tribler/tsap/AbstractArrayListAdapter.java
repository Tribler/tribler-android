package org.tribler.tsap;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.tribler.tsap.ThreadPreconditions;

import android.widget.BaseAdapter;

/**
 * Adapter for managing the data and views for a list of channels.
 * 
 * @author Dirk Schut
 */

public abstract class AbstractArrayListAdapter<T> extends BaseAdapter {
	protected ArrayList<T> content;

	/**
	 * Constructor: Initializes the AbstractArrayListAdapter with an empty ArrayList.
	 */
	public AbstractArrayListAdapter() {
		content = new ArrayList<T>();
	}

	/**
	 * Constructor: Initializes the AbstractArrayListAdapter with existing data.
	 * 
	 * @param initialContent
	 *            The initial content
	 */
	public AbstractArrayListAdapter(Collection<T> initialContent) {
		content = new ArrayList<T>();
		content.addAll(initialContent);
	}
	
	/**
	 * Returns the item at a certain position.
	 * 
	 * @param position
	 * 	the position from which you want to retrieve the item
	 */
	@Override
	public T getItem(int position) {
		return content.get(position);
	}
	
	/**
	 * Adds all items from a list that were not in the adapter already.
	 * @param list
	 * 	list of items to add
	 */
	public void addNew(List<T> list) {
		ThreadPreconditions.checkOnMainThread();
		for (int i = 0; i < list.size(); i++) {
			if (!content.contains(list.get(i)))
			{
				content.add(list.get(i));
			}
		}
		notifyDataSetChanged();
	}
	
	/**
	 * Returns the amount of items.
	 */
	@Override
	public int getCount() {
		return content.size();
	}

	/**
	 * Returns the Id of the item at a certain position.
	 * @param
	 *  the position
	 */
	@Override
	public long getItemId(int position) {
		return position;
	}
	
	/**
	 * Removes all data from the adpater.
	 */
	public void clear()
	{
		ThreadPreconditions.checkOnMainThread();
		content.clear();
		notifyDataSetChanged();
	}
}