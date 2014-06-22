package org.tribler.tsap;

import java.net.URL;
import java.util.Observable;
import java.util.Observer;

import android.util.Log;
import de.timroes.axmlrpc.XMLRPCClient;
import de.timroes.axmlrpc.XMLRPCException;

/**
 * Class that is responsible for performing the XML-RPC call to shut down
 * Tribler.
 * 
 * @author Niels Spruit
 * 
 */
public class XMLRPCShutdownManager extends Observable {

	private XMLRPCClient mClient;

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
		mClient = new XMLRPCClient(url);
		this.addObserver(obs);
	}

	/**
	 * Performs the tribler.shutdown XML-RPC call. When this call returns, this
	 * method waits 5 seconds to let Tribler shut itself down and then it
	 * notifies the observers.
	 */
	public void shutdown() {
		XMLRPCCallTask task = new XMLRPCCallTask() {
			@Override
			protected void onPostExecute(Object result) {
				if (result instanceof XMLRPCException) {
					Log.e("XMLRPCShutdownManager", "Could not shutdown Tribler");
				} else {
					try {
						Thread.sleep(5000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					setChanged();
					notifyObservers();
				}
			}
		};
		task.execute(mClient, "tribler.shutdown");
	}

}
