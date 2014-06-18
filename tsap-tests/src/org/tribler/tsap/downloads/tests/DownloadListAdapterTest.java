package org.tribler.tsap.downloads.tests;

import java.util.ArrayList;

import org.tribler.tsap.MainActivity;
import org.tribler.tsap.R;
import org.tribler.tsap.downloads.Download;
import org.tribler.tsap.downloads.DownloadListAdapter;

import android.test.ActivityInstrumentationTestCase2;
import android.test.UiThreadTest;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

public class DownloadListAdapterTest extends ActivityInstrumentationTestCase2<MainActivity> {
	DownloadListAdapter adapter;
	Download a = new Download("a", "hash1", 1, 1234.5, 5.4321, 0.2);
	Download b = new Download("b", "hash2", 2, 123.45, 54.321, 0.3);
	Download c = new Download("c", "hash3", 3, 12.345, 543.21, 0.4);
	Download d = new Download("d", "hash4", 4, 1.2345, 5432.1, 0.5);
	
	public DownloadListAdapterTest()
	{
		super(MainActivity.class);
	}
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		adapter = new DownloadListAdapter(this.getActivity(), R.layout.download_list_item);
	}
	
	@UiThreadTest
	public void testReplaceAll()
	{
		ArrayList<Download> list1 = new ArrayList<Download>();
		list1.add(a);
		list1.add(b);
		list1.add(c);
		
		ArrayList<Download> list2 = new ArrayList<Download>();
		list2.add(d);
		list2.add(c);
		list2.add(b);
		list2.add(a);
		
		adapter.replaceAll(list1);
		assertEquals("Added count incorrect", 3, adapter.getCount());
		assertEquals("replaced item incorrect", a, adapter.getItem(0));
		assertEquals("replaced item incorrect", b, adapter.getItem(1));
		assertEquals("replaced item incorrect", c, adapter.getItem(2));
		
		adapter.replaceAll(list2);
		assertEquals("Added count incorrect", 4, adapter.getCount());
		assertEquals("replaced item incorrect", d, adapter.getItem(0));
		assertEquals("replaced item incorrect", c, adapter.getItem(1));
		assertEquals("replaced item incorrect", b, adapter.getItem(2));
		assertEquals("replaced item incorrect", a, adapter.getItem(3));
	}
	
	public void testMainThreadCheck()
	{	
		ArrayList<Download> list = new ArrayList<Download>();
		try {
			adapter.replaceAll(list);
			fail("No exception thrown when replacing from a non-UI thread.");
		}
		catch(IllegalStateException e)
		{ }
		catch(Exception e)
		{
			fail("Wrong exception thrown when replacing from a non-UI thread.");
		}
	}
	
	public void checkView(View view, CharSequence name, CharSequence status, CharSequence downloadSpeed, CharSequence uploadSpeed, int progress)
	{
		CharSequence foundName = ((TextView)view.findViewById(R.id.download_name)).getText();
		assertEquals("incorrect name in view", name, foundName);
		CharSequence foundStatus = ((TextView) view.findViewById(R.id.download_status)).getText();
		assertEquals("incorrect status in view", status, foundStatus);
		CharSequence foundDownSpeed = ((TextView) view.findViewById(R.id.download_speed_down)).getText();
		assertEquals("incorrect download speed in view", downloadSpeed, foundDownSpeed);
		CharSequence foundUpSpeed = ((TextView) view.findViewById(R.id.download_speed_up)).getText();
		assertEquals("incorrect upload speed in view", uploadSpeed, foundUpSpeed);
		int foundProgress = ((ProgressBar) view.findViewById(R.id.download_progress_bar)).getProgress();
		assertEquals("incorrect progress in view", progress, foundProgress);
	}
	
	@UiThreadTest
	public void testGetView() {
		ArrayList<Download> list = new ArrayList<Download>();
		list.add(a);
		list.add(b);
		list.add(c);
		list.add(d);
		adapter.replaceAll(list);
		checkView(adapter.getView(0, null, null), "a", "Status: Allocating disk space", "Down: 1.2kB/s", "Up: 5B/s", 20);
		checkView(adapter.getView(1, null, null), "b", "Status: Waiting on the hash check", "Down: 123B/s", "Up: 54B/s", 30);
		checkView(adapter.getView(2, null, null), "c", "Status: Downloading", "Down: 12B/s", "Up: 543B/s", 40);
		checkView(adapter.getView(3, null, null), "d", "Status: Seeding", "Down: 1B/s", "Up: 5.4kB/s", 50);
	}
}