package org.tribler.tsap.thumbgrid.tests;

import java.util.ArrayList;

import org.tribler.tsap.MainActivity;
import org.tribler.tsap.R;
import org.tribler.tsap.thumbgrid.TORRENT_HEALTH;
import org.tribler.tsap.thumbgrid.ThumbAdapter;
import org.tribler.tsap.thumbgrid.ThumbItem;

import android.test.ActivityInstrumentationTestCase2;
import android.test.UiThreadTest;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

public class ThumbAdapterTest extends ActivityInstrumentationTestCase2<MainActivity> {
	ThumbAdapter adapter;
	ThumbItem a = new ThumbItem("infohash1", "a", TORRENT_HEALTH.GREEN, 1, "other", 1, 1);
	ThumbItem b = new ThumbItem("infohash2", "b", TORRENT_HEALTH.YELLOW, 12, "other", 1, 1);
	ThumbItem c = new ThumbItem("infohash3" ,"c", TORRENT_HEALTH.RED, 123, "other", 1, 1);
	ThumbItem d = new ThumbItem("infohash4", "d", TORRENT_HEALTH.UNKNOWN, 1234, "other", 1, 1);
	
	public ThumbAdapterTest()
	{
		super(MainActivity.class);
	}
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		ArrayList<ThumbItem> list = new ArrayList<ThumbItem>();
		list.add(a);
		list.add(b);
		list.add(c);
		list.add(d);
		adapter = new ThumbAdapter(this.getActivity(), R.layout.thumb_grid_item, list);
	}
	
	private void checkView(View view, CharSequence title, CharSequence size, int healthProgress)
	{
		CharSequence foundTitle = ((TextView)view.findViewById(R.id.ThumbTitle)).getText();
		assertEquals("incorrect title in view", title, foundTitle);
		CharSequence foundSize = ((TextView) view.findViewById(R.id.ThumbSize)).getText();
		assertEquals("incorrect size in view", size, foundSize);
		int foundHealthProgress = ((ProgressBar) view.findViewById(R.id.ThumbHealth)).getProgress();
		assertEquals("incorrect health progress", healthProgress, foundHealthProgress);
	}
	
	@UiThreadTest
	public void testGetView() {
		checkView(adapter.getView(0, null, null), "a", "1 B", 3);
		checkView(adapter.getView(1, null, null), "b", "12 B", 2);
		checkView(adapter.getView(2, null, null), "c", "123 B", 1);
		checkView(adapter.getView(3, null, null), "d", "1.2 kB", 0);
	}
}