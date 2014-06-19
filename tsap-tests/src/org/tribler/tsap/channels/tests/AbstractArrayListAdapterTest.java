package org.tribler.tsap.channels.tests;

import java.util.ArrayList;
import java.util.Collection;
import org.tribler.tsap.AbstractArrayListAdapter;
import android.annotation.SuppressLint;
import android.test.InstrumentationTestCase;
import android.test.UiThreadTest;
import android.view.View;
import android.view.ViewGroup;


public class AbstractArrayListAdapterTest extends InstrumentationTestCase{
	class MinimalArrayListAdapter extends AbstractArrayListAdapter<Integer>{

		public View getView(int position, View convertView, ViewGroup parent) {
			return null;
		}
		public MinimalArrayListAdapter()
		{
			super();
		}
		public MinimalArrayListAdapter(Collection<Integer> initialContent)
		{
			super(initialContent);
		}
	}
	
	public void testInitalEmpty()
	{
		MinimalArrayListAdapter initAdapter = new MinimalArrayListAdapter();
		assertEquals("Initial count incorrect", 0, initAdapter.getCount());
	}
	
	public void testInitalData()
	{
		ArrayList<Integer> intList = new ArrayList<Integer>();
		intList.add(4);
		intList.add(5);
		intList.add(6);
		MinimalArrayListAdapter initAdapter = new MinimalArrayListAdapter(intList);
		assertEquals("Initial count incorrect", 3, initAdapter.getCount());
		assertEquals("Initial value incorrect", Integer.valueOf(4), initAdapter.getItem(0));
		assertEquals("Initial value incorrect", Integer.valueOf(5), initAdapter.getItem(1));
		assertEquals("Initial value incorrect", Integer.valueOf(6), initAdapter.getItem(2));
	}
	
	public void testOutOfBoundsException()
	{
		MinimalArrayListAdapter adapter = new MinimalArrayListAdapter();
		try {
			adapter.getItem(1);
			fail("No exception thrown when accessing an element that was out of bounds.");
		}
		catch(IndexOutOfBoundsException e)
		{ }
		catch(Exception e)
		{
			fail("Wrong type of exception thrown when accessing an element that was out of bounds.");
		}
	}
	
	public void testGetId()
	{
		MinimalArrayListAdapter initAdapter = new MinimalArrayListAdapter();
		assertEquals("ID incorrect", -34234, initAdapter.getItemId(-34234));
		assertEquals("ID incorrect", -1, initAdapter.getItemId(-1));
		assertEquals("ID incorrect", 0, initAdapter.getItemId(0));
		assertEquals("ID incorrect", 1, initAdapter.getItemId(1));
		assertEquals("ID incorrect", 456456, initAdapter.getItemId(456456));
	}
	
	@UiThreadTest
	public void testClear()
	{
		ArrayList<Integer> intList = new ArrayList<Integer>();
		intList.add(4);
		intList.add(5);
		intList.add(6);
		MinimalArrayListAdapter initAdapter = new MinimalArrayListAdapter(intList);
		initAdapter.clear();
		assertEquals("Count incorrect after clearing", 0, initAdapter.getCount());
	}
	
	@SuppressLint("UseValueOf") @UiThreadTest
	public void testAddNew()
	{
		Integer one = new Integer(1), two = new Integer(2), three = new Integer(3),
				four = new Integer(4);  
		ArrayList<Integer> list1 = new ArrayList<Integer>();
		list1.add(one);
		list1.add(two);
		list1.add(three);
		
		ArrayList<Integer> list2 = new ArrayList<Integer>();
		list2.add(four);
		list2.add(three);
		list2.add(two);
		
		MinimalArrayListAdapter adapter1 = new MinimalArrayListAdapter();
		adapter1.addNew(list1);
		assertEquals("Added count incorrect", 3, adapter1.getCount());
		
		MinimalArrayListAdapter adapter2 = new MinimalArrayListAdapter(list1);
		adapter2.addNew(list1);
		assertEquals("Added count incorrect", 3, adapter2.getCount());
		
		MinimalArrayListAdapter adapter3 = new MinimalArrayListAdapter(list1);
		adapter3.addNew(list2);
		assertEquals("Added count incorrect", 4, adapter3.getCount());
	}
	
	public void testMainThreadCheck()
	{
		MinimalArrayListAdapter adapter = new MinimalArrayListAdapter();
		ArrayList<Integer> list = new ArrayList<Integer>();
		
		try {
			adapter.addNew(list);
			fail("No exception thrown when adding from a non-UI thread.");
		}
		catch(IllegalStateException e)
		{ }
		catch(Exception e)
		{
			fail("Wrong exception thrown when adding from a non-UI thread.");
		}
		
		try {
			adapter.clear();
			fail("No exception thrown when clearing from a non-UI thread.");
		}
		catch(IllegalStateException e)
		{ }
		catch(Exception e)
		{
			fail("Wrong exception thrown when clearing from a non-UI thread.");
		}
	}
}