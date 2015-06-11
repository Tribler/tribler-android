package org.tribler.tsap.XMLRPC;

import java.util.ArrayList;

import org.tribler.tsap.util.Poller;

import android.app.Activity;
import android.util.Log;
import de.timroes.axmlrpc.XMLRPCClient;
import de.timroes.axmlrpc.XMLRPCException;

/**
 * Represent a connection with the Tribler service using XML-RPC
 * 
 * @author Dirk Schut
 * 
 */
public class XMLRPCConnection implements Poller.IPollListener {
	private XMLRPCClient mClient;
	private ArrayList<IConnectionListener> mListeners;
	private boolean mConnected = false, mJustStarted = true;
	private Poller mPoller;
	private Activity mActivity;
	private static XMLRPCConnection mInstance = null;

	protected XMLRPCConnection() {
	};

	/**
	 * Initializes the connection
	 * 
	 * @param client
	 * @param activity
	 */
	public void setup(XMLRPCClient client, Activity activity) {
		mClient = client;
		mPoller = new Poller(this, 500);
		mPoller.start();
		mListeners = new ArrayList<IConnectionListener>();
		mActivity = activity;
	}

	/**
	 * Returns the singleton instance of this class
	 * 
	 * @return
	 */
	public static XMLRPCConnection getInstance() {
		if (mInstance == null) {
			mInstance = new XMLRPCConnection();
		}
		return mInstance;
	}

	/**
	 * Calls the function <functionName> on the other side of the connection
	 * with optional parameters
	 * 
	 * @param functionName
	 *            The function to call
	 * @param params
	 *            The (optional) parameters of the function to call
	 * @return
	 */
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

	/**
	 * Adds listener to the listeners of this connection
	 * 
	 * @param listener
	 *            The listener to add
	 */
	public void addListener(IConnectionListener listener) {
		mListeners.add(listener);
		if (mConnected)
			listener.onConnectionEstablished();
		else if (!mJustStarted)
			listener.onConnectionLost();
	}

	/**
	 * Removes listener from the listeners list
	 * 
	 * @param listener
	 *            The listener to remove
	 */
	public void removeListener(IConnectionListener listener) {
		mListeners.remove(listener);
	}

	/**
	 * Notifies that the connection with the Tribler service is establishes
	 * (notifies the listeners)
	 */
	private void notifyConnectionEstablished() {
		new UIThreadListenerNotifier() {
			@Override
			void notifyListener(IConnectionListener listener) {
				listener.onConnectionEstablished();
			}
		}.notifyAll(mActivity);
	}

	/**
	 * Notifies that the connection with the Tribler service is lost (notifies
	 * the listeners)
	 */
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

	/**
	 * @return true iff it is connected with the Tribler service
	 */
	public boolean isConnected() {
		return mConnected;
	}

	/**
	 * @return true iff the connecting is just started
	 */
	public boolean isJustStarted() {
		return mJustStarted;
	}

	/**
	 * Specifies the interface that listener to the connection must implement to
	 * listen to the connection
	 * 
	 * @author Dirk Schut
	 * 
	 */
	public interface IConnectionListener {
		public void onConnectionEstablished();

		public void onConnectionLost();
	}

	/**
	 * Class to notify the listeners on the UI thread
	 * 
	 * @author Dirk Schut
	 * 
	 */
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