package org.tribler.tsap.tests;

import java.util.ArrayList;
import java.util.Collection;

import org.tribler.tsap.AbstractArrayListAdapter;
import org.tribler.tsap.MainActivity;

import android.test.ActivityInstrumentationTestCase2;
import android.test.UiThreadTest;
import android.view.View;
import android.view.ViewGroup;

public class AbstractArrayListAdapterTest extends
		ActivityInstrumentationTestCase2<MainActivity> {
	public AbstractArrayListAdapterTest(Class<MainActivity> activityClass) {
		super(MainActivity.class);
	}

	class MinimalArrayListAdapter extends AbstractArrayListAdapter<Integer> {

		public View getView(int position, View convertView, ViewGroup parent) {
			return null;
		}

		public MinimalArrayListAdapter() {
			super(getActivity());
		}

		public MinimalArrayListAdapter(Collection<Integer> initialContent) {
			super(getActivity(), initialContent);
		}
	}

	public void testInitalEmpty() {
		MinimalArrayListAdapter initAdapter = new MinimalArrayListAdapter();
		assertEquals("Initial count incorrect", 0, initAdapter.getCount());
	}

	public void testInitalData() {
		ArrayList<Integer> intList = new ArrayList<Integer>();
		intList.add(4);
		intList.add(5);
		intList.add(6);
		MinimalArrayListAdapter initAdapter = new MinimalArrayListAdapter(
				intList);
		assertEquals("Initial count incorrect", 3, initAdapter.getCount());
		assertEquals("Initial value incorrect", Integer.valueOf(4),
				initAdapter.getItem(0));
		assertEquals("Initial value incorrect", Integer.valueOf(5),
				initAdapter.getItem(1));
		assertEquals("Initial value incorrect", Integer.valueOf(6),
				initAdapter.getItem(2));
	}

	public void testOutOfBoundsException() {
		MinimalArrayListAdapter adapter = new MinimalArrayListAdapter();
		try {
			adapter.getItem(1);
			fail("No exception thrown when accessing an element that was out of bounds.");
		} catch (IndexOutOfBoundsException e) {
		} catch (Exception e) {
			fail("Wrong type of exception thrown when accessing an element that was out of bounds.");
		}
	}

	public void testGetId() {
		MinimalArrayListAdapter initAdapter = new MinimalArrayListAdapter();
		assertEquals("ID incorrect", -34234, initAdapter.getItemId(-34234));
		assertEquals("ID incorrect", -1, initAdapter.getItemId(-1));
		assertEquals("ID incorrect", 0, initAdapter.getItemId(0));
		assertEquals("ID incorrect", 1, initAdapter.getItemId(1));
		assertEquals("ID incorrect", 456456, initAdapter.getItemId(456456));
	}

	@UiThreadTest
	public void testClear() {
		ArrayList<Integer> intList = new ArrayList<Integer>();
		intList.add(4);
		intList.add(5);
		intList.add(6);
		MinimalArrayListAdapter initAdapter = new MinimalArrayListAdapter(
				intList);
		initAdapter.clear();
		assertEquals("Count incorrect after clearing", 0,
				initAdapter.getCount());
	}
}