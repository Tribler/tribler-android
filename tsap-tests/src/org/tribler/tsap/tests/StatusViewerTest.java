package org.tribler.tsap.tests;

import org.tribler.tsap.MainActivity;
import org.tribler.tsap.R;
import org.tribler.tsap.StatusViewer;

import android.test.ActivityInstrumentationTestCase2;
import android.test.UiThreadTest;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

public class StatusViewerTest extends
		ActivityInstrumentationTestCase2<MainActivity> {

	private ProgressBar progressBar;
	private TextView message;
	private StatusViewer statusViewer;

	public StatusViewerTest() {
		super(MainActivity.class);
	}

	@Override
	protected void setUp() {
		progressBar = new ProgressBar(getActivity());
		message = new TextView(getActivity());
		statusViewer = new StatusViewer(getActivity());
		statusViewer.updateViews(progressBar, message);
	}

	private void checkSingleMessage(int messageResource, int messageVisibility,
			int progressBarvisibility) {
		assertEquals("message is not visible.", messageVisibility,
				message.getVisibility());
		assertEquals("Message text is incorrect.", getActivity().getResources()
				.getText(messageResource), message.getText());
		assertEquals("ProgressBar visibility is not correct.",
				progressBarvisibility, progressBar.getVisibility());
	}

	private void singleMessageTest(int messageResource, boolean visibility) {
		statusViewer.setMessage(messageResource, null, visibility);
		int progressBarVisibility;
		if (visibility) {
			progressBarVisibility = View.VISIBLE;
		} else {
			progressBarVisibility = View.INVISIBLE;
		}
		checkSingleMessage(messageResource, View.VISIBLE, progressBarVisibility);
	}

	@UiThreadTest
	public void testSetMessage() {
		singleMessageTest(R.string.connection_loading, true);
		singleMessageTest(R.string.hardware_acceleration_error_title, false);
		singleMessageTest(R.string.empty, true);
		singleMessageTest(R.string.empty, false);
	}

	@UiThreadTest
	public void testDisable() {
		singleMessageTest(R.string.connection_failed, true);
		statusViewer.disable();
		statusViewer.setMessage(R.string.cancel, null, false);
		checkSingleMessage(R.string.connection_failed, View.GONE, View.GONE);
	}

	@UiThreadTest
	public void testEnable() {
		testDisable();
		statusViewer.enable();
		checkSingleMessage(R.string.empty, View.VISIBLE, View.INVISIBLE);
		singleMessageTest(R.string.app_name, false);
	}
}
