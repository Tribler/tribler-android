package org.tribler.tsap.XMLRPC;

import de.timroes.axmlrpc.XMLRPCException;
import android.os.AsyncTask;

/**
 * Class for calling a single remote function asynchronously with aXMLRPC. To
 * call the function first make a child of this class with an overridden
 * onPostExecute function. That function should include the code that handles
 * the return value of the RPC. Then call the execute function on an instance of
 * the child-class with an XMLRPCClient and a String containing the name of the
 * remote function as arguments.
 * 
 * @author Dirk Schut
 * 
 */
public class XMLRPCCallTask {

	public void onSucces(Object result) {
	};

	public void onFailure(XMLRPCException result) {
	};

	/**
	 * Calls the function specified by functionName in the Tribler service using
	 * the connection and optional arguments
	 * 
	 * @param functionName
	 *            The function to call
	 * @param connection
	 *            The XML-RPC connection to use
	 * @param params
	 *            The (optional) arguments of the function
	 */
	public void call(final String functionName,
			final XMLRPCConnection connection, final Object... params) {
		AsyncTask<Void, Void, Object> backgroundTask = new AsyncTask<Void, Void, Object>() {

			@Override
			protected Object doInBackground(Void... voidParams) {
				return connection.call(functionName, params);
			}

			protected void onPostExecute(Object result) {
				if (result instanceof XMLRPCException) {
					onFailure((XMLRPCException) result);
				} else {
					onSucces(result);
				}
			}
		};
		backgroundTask.execute();
	}
}