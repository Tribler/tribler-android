package org.tribler.tsap;

import java.util.Arrays;

import de.timroes.axmlrpc.XMLRPCClient;
import de.timroes.axmlrpc.XMLRPCException;
import android.os.AsyncTask;
import android.util.Log;

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
public class XMLRPCCallTask extends AsyncTask<Object, Void, Object> {
	/**
	 * Calls a remote function and returns the result. In the normal use case
	 * this result will be send to the onPostExecutre function.
	 * 
	 * The first two arguments of this function should be an XMLRPCClient and a
	 * string containing the name of the function.
	 */
	@Override
	protected Object doInBackground(Object... params){
		XMLRPCClient client = null;
		String functionName = null;
		if (params[0] instanceof XMLRPCClient)
			client = (XMLRPCClient) params[0];
		else
			Log.e("XMLRPCCallTask",
					"First argument to XMLRPCCallTask should be an XMLRPCClient.");
		if (params[1] instanceof String)
			functionName = (String) params[1];
		else
			Log.e("XMLRPCCallTask",
					"Second argument to XMLRPCCallTask should be a String.");

		Object result;
		try {
			result = client.call(functionName,
					Arrays.copyOfRange(params, 2, params.length));

		} catch (XMLRPCException e) {
			result = e;
		}
		return result;
	}
}