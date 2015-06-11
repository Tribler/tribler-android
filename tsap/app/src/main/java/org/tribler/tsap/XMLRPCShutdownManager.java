package org.tribler.tsap;

import java.net.URL;
import java.util.Observable;
import java.util.Observer;

import org.tribler.tsap.XMLRPC.XMLRPCCallTask;
import org.tribler.tsap.XMLRPC.XMLRPCConnection;

import android.util.Log;

/**
 * Class that is responsible for performing the XML-RPC call to shut down
 * Tribler.
 * 
 * @author Niels Spruit
 * 
 */
public class XMLRPCShutdownManager extends Observable {

	/**
	 * Constructor: initializes the XMLRPCClient and add obs to this class'
	 * observers
	 * 
	 * @param url
	 *            Url of the XML-RPC server (in this case Tribler)
	 * @param obs
	 *            Observer to add to this class' observers
	 */
	public XMLRPCShutdownManager(URL url, Observer obs) {
		this.addObserver(obs);
	}

	/**
	 * Performs the tribler.shutdown XML-RPC call. When this call returns, this
	 * method waits 5 seconds to let Tribler shut itself down and then it
	 * notifies the observers.
	 */
	public void shutdown() {
		new XMLRPCCallTask() {
			@Override
			public void onSucces(Object result) {
				try {
					// Call is blocking, but give Tribler a second extra
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				Log.i("XMLRPCShutdownManager",
						"Tribler was gracefully shutdown..");
				setChanged();
				notifyObservers();
			}
		}.call("tribler.stop_session", XMLRPCConnection.getInstance());
	}

}
