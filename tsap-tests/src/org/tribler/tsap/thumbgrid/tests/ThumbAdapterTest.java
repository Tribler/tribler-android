package org.tribler.tsap.thumbgrid.tests;

import java.util.ArrayList;

import org.tribler.tsap.MainActivity;
import org.tribler.tsap.R;
import org.tribler.tsap.Torrent;
import org.tribler.tsap.thumbgrid.ThumbAdapter;

import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

public class ThumbAdapterTest extends
		ActivityInstrumentationTestCase2<MainActivity> {
	private ThumbAdapter adapter;
	private Torrent a, b, c, d;

	public ThumbAdapterTest() {
		super(MainActivity.class);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();

		a = new Torrent("a", "infohash1", 1, 1, 1, "other");
		b = new Torrent("b", "infohash2", 12, 1, 1, "other");
		c = new Torrent("c", "infohash3", 123, 1, 1, "other");
		d = new Torrent("d", "infohash4", 1234, 1, 1, "other");

		ArrayList<Torrent> list = new ArrayList<Torrent>();
		list.add(a);
		list.add(b);
		list.add(c);
		list.add(d);
		adapter = new ThumbAdapter(this.getActivity(),
				R.layout.thumb_grid_item, list);
	}

	private void checkView(View view, CharSequence title, CharSequence size,
			int healthProgress) {
		CharSequence foundTitle = ((TextView) view
				.findViewById(R.id.ThumbTitle)).getText();
		// The \n is added to compensate the size of single line ThumbGrid items
		assertEquals("incorrect title in view", title + "\n", foundTitle);
		CharSequence foundSize = ((TextView) view.findViewById(R.id.ThumbSize))
				.getText();
		assertEquals("incorrect size in view", size, foundSize);
		int foundHealthProgress = ((ProgressBar) view
				.findViewById(R.id.ThumbHealth)).getProgress();
	}

	public void testGetView() {
		checkView(adapter.getView(0, null, null), "a", "1 B", 3);
		checkView(adapter.getView(1, null, null), "b", "12 B", 2);
		checkView(adapter.getView(2, null, null), "c", "123 B", 1);
		checkView(adapter.getView(3, null, null), "d", "1.2 kB", 0);
	}

	public void testAddNew() {
		ArrayList<Torrent> list1 = new ArrayList<Torrent>();
		list1.add(a);
		list1.add(b);
		list1.add(c);

		ArrayList<Torrent> list2 = new ArrayList<Torrent>();
		list2.add(d);
		list2.add(c);
		list2.add(b);

		ThumbAdapter adapter1 = new ThumbAdapter(this.getActivity(),
				R.layout.thumb_grid_item);
		adapter1.addNew(list1);
		assertEquals("Added count incorrect", 3, adapter1.getCount());

		ThumbAdapter adapter2 = new ThumbAdapter(this.getActivity(),
				R.layout.thumb_grid_item, list1);
		adapter2.addNew(list1);
		assertEquals("Added count incorrect", 3, adapter2.getCount());

		ThumbAdapter adapter3 = new ThumbAdapter(this.getActivity(),
				R.layout.thumb_grid_item, list1);
		adapter3.addNew(list2);
		assertEquals("Added count incorrect", 4, adapter3.getCount());
	}
}