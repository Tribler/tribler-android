package org.tribler.tsap.downloads.tests;

import java.util.ArrayList;

import org.tribler.tsap.MainActivity;
import org.tribler.tsap.R;
import org.tribler.tsap.Torrent;
import org.tribler.tsap.downloads.Download;
import org.tribler.tsap.downloads.DownloadListAdapter;
import org.tribler.tsap.downloads.DownloadStatus;

import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

public class DownloadListAdapterTest extends
		ActivityInstrumentationTestCase2<MainActivity> {
	private DownloadListAdapter adapter;
	private Download a, b, c, d;
	private Torrent one = new Torrent("a", "hash1", 756, 8, 5, "Unknown");
	private Torrent two = new Torrent("b", "hash2", 86, 6, 5, "Other");
	private Torrent three = new Torrent("c", "hash3", 23, 75, 5, "Video");
	private Torrent four = new Torrent("d", "hash4", -1, 0, 0, "Unknown");
	private DownloadStatus status1 = new DownloadStatus(1, 1234.5, 5.4321, 0.2, 15.4);
	private DownloadStatus status2 = new DownloadStatus(2, 123.45, 54.321, 0.3, 896.42);
	private DownloadStatus status3 = new DownloadStatus(3, 12.345, 543.21, 0.4, 88.8);
	private DownloadStatus status4 = new DownloadStatus(4, 1.2345, 5432.1, 0.5, 54.45);

	public DownloadListAdapterTest() {
		super(MainActivity.class);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		a = new Download(one, status1, 121.56, false, 0);
		b = new Download(two, status2, 586.2, true, 2);
		c = new Download(three, status3, 565, false, 1);
		d = new Download(four, status4, 85.65, true, 0);
		adapter = new DownloadListAdapter(this.getActivity(),
				R.layout.download_list_item);
	}

	public void testReplaceAll() {
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

	public void checkView(View view, CharSequence name, CharSequence status,
			CharSequence downloadSpeed, CharSequence uploadSpeed, int progress) {
		CharSequence foundName = ((TextView) view
				.findViewById(R.id.download_name)).getText();
		assertEquals("incorrect name in view", name, foundName);
		CharSequence foundStatus = ((TextView) view
				.findViewById(R.id.download_status)).getText();
		assertEquals("incorrect status in view", status, foundStatus);
		CharSequence foundDownSpeed = ((TextView) view
				.findViewById(R.id.download_speed_down)).getText();
		assertEquals("incorrect download speed in view", downloadSpeed,
				foundDownSpeed);
		CharSequence foundUpSpeed = ((TextView) view
				.findViewById(R.id.download_speed_up)).getText();
		assertEquals("incorrect upload speed in view", uploadSpeed,
				foundUpSpeed);
		int foundProgress = ((ProgressBar) view
				.findViewById(R.id.download_progress_bar)).getProgress();
		assertEquals("incorrect progress in view", progress, foundProgress);
	}

	public void testGetView() {
		ArrayList<Download> list = new ArrayList<Download>();
		list.add(a);
		list.add(b);
		list.add(c);
		list.add(d);
		adapter.replaceAll(list);
		checkView(adapter.getView(0, null, null), "a",
				"Status: Allocating disk space", "Down: 1.2 kB/s", "Up: 5 B/s",
				20);
		checkView(adapter.getView(1, null, null), "b",
				"Status: Waiting for hash check (30%)", "Down: 123 B/s",
				"Up: 54 B/s", 30);
		checkView(adapter.getView(2, null, null), "c",
				"Status: Downloading (40%)", "Down: 12 B/s", "Up: 543 B/s", 40);
		checkView(adapter.getView(3, null, null), "d", "Status: Seeding",
				"Down: 1 B/s", "Up: 5.4 kB/s", 50);
	}
}