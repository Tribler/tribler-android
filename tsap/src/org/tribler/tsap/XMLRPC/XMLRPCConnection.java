package org.tribler.tsap.XMLRPC;

import java.util.ArrayList;

import org.tribler.tsap.downloads.XMLRPCDownloadManager;
import org.tribler.tsap.util.Poller;

import android.app.Activity;
import android.util.Log;
import de.timroes.axmlrpc.XMLRPCClient;
import de.timroes.axmlrpc.XMLRPCException;

public class XMLRPCConnection implements Poller.IPollListener {
	private XMLRPCClient mClient;
	ArrayList<IConnectionListener> mListeners;
	boolean mConnected = false, mJustStarted = true;
	Poller mPoller;
	Activity mActivity;
	static XMLRPCConnection mInstance = null;

	private XMLRPCConnection() {
	};

	public void setup(XMLRPCClient client, Activity activity) {
		mClient = client;
		mPoller = new Poller(this, 500);
		mPoller.start();
		mListeners = new ArrayList<IConnectionListener>();
		mActivity = activity;
	}

	public static XMLRPCConnection getInstance() {
		if (mInstance == null) {
			mInstance = new XMLRPCConnection();
		}
		return mInstance;
	}

	public Object call(String functionName, Object... params) {
		if (!mConnected) {
			Log.e("XMLRPCConnection",
					"Tried calling xml-rpc function while the connection was not established.");
			return null;
		}
		try {
			Object result;
			result = mClient.call(functionName, params);
			return result;

		} catch (XMLRPCException e) {
			notifyConnectionLost();
			Log.i("XMLRPCConnection",
					"Connection Lost. Trying to re-establish connection.");
			mPoller.start();
			mConnected = false;
			return e;
		}
	}

	public void addListener(IConnectionListener listener) {
		mListeners.add(listener);
		if (mConnected)
			listener.onConnectionEstablished();
		else if (!mJustStarted)
			listener.onConnectionLost();
	}

	public void removeListener(IConnectionListener listener) {
		mListeners.remove(listener);
	}

	private void notifyConnectionEstablished() {
		new UIThreadListenerNotifier() {
			@Override
			void notifyListener(IConnectionListener listener) {
				listener.onConnectionEstablished();
			}
		}.notifyAll(mActivity);
	}

	private void notifyConnectionLost() {
		new UIThreadListenerNotifier() {
			@Override
			void notifyListener(IConnectionListener listener) {
				listener.onConnectionLost();
			}
		}.notifyAll(mActivity);
	}

	@Override
	public void onPoll() {
		try {
			Object[] arrayResult = (Object[]) mClient
					.call("system.listMethods");

			Log.i("XMLRPCConnection", "Listing available functions");
			for (int i = 0; i < arrayResult.length; i++) {
				Log.i("XMLRPCConnection", " " + (String) arrayResult[i]);
			}

			notifyConnectionEstablished();
			mConnected = true;
			mJustStarted = false;
			mPoller.stop();
			Log.i("XMLRPCConnection", "Stopped poller.");
		} catch (XMLRPCException e) {
		}
	}

	public boolean isConnected() {
		return mConnected;
	}

	public boolean isJustStarted() {
		return mJustStarted;
	}

	public interface IConnectionListener {
		public void onConnectionEstablished();

		public void onConnectionLost();
	}

	private abstract class UIThreadListenerNotifier implements Runnable {
		abstract void notifyListener(IConnectionListener listener);

		@Override
		public void run() {
			for (int i = mListeners.size() - 1; i >= 0; i--) {
				notifyListener(mListeners.get(i));
			}
		}

		public void notifyAll(Activity activity) {
			activity.runOnUiThread(this);
		}
	}
}