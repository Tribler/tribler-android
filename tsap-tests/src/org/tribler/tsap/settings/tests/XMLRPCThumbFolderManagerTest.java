package org.tribler.tsap.settings.tests;

import org.tribler.tsap.XMLRPC.tests.StubbedXMLRPCConnection;
import org.tribler.tsap.settings.Settings;
import org.tribler.tsap.settings.XMLRPCThumbFolderManager;

import junit.framework.TestCase;

public class XMLRPCThumbFolderManagerTest extends TestCase {
	private XMLRPCThumbFolderManager mManager;
	private StubbedXMLRPCConnection mConnection;
	private static final String location = "~/blabla";

	public XMLRPCThumbFolderManagerTest() {
		mConnection = StubbedXMLRPCConnection.getInstance();
		mConnection.setupStub();
		mConnection.addReturnValue("settings.get_thumbs_directory", location);
	}

	@Override
	protected void setUp() {
		mManager = new XMLRPCThumbFolderManager(mConnection);
		Settings.XMLRPCSetThumbFolder(null);
	}

	public void testGetThumbFolder() {
		mManager.onConnectionEstablished();
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			fail("Caught InterruptedException, which caused the test to fail.");
		}
		assertEquals("Thumb folder not correct.", location, Settings
				.getThumbFolder().getPath());
	}
}