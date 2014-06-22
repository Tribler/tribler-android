package org.tribler.tsap;

import java.net.URL;
import java.util.Observable;
import java.util.Observer;

import android.util.Log;
import de.timroes.axmlrpc.XMLRPCClient;
import de.timroes.axmlrpc.XMLRPCException;

public class XMLRPCShutdownManager extends Observable{
	
protected XMLRPCClient mClient;
	
	public XMLRPCShutdownManager(URL url, Observer obs) {
		mClient = new XMLRPCClient(url);
		this.addObserver(obs);
	}
	
	public void shutdown() {
		XMLRPCCallTask task = new XMLRPCCallTask() {
			@Override
			protected void onPostExecute(Object result) {
//				if (result instanceof XMLRPCException) {
//					Log.e("XMLRPCShutdownManager", "Could not shutdown Tribler");
//				}
//				else
//				{
					try {
						Thread.sleep(5000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					setChanged();
					notifyObservers();
//				}
			}
		};
		task.execute(mClient, "tribler.shutdown");
	}

}
