package org.tribler.tsap;

import java.util.Arrays;

import de.timroes.axmlrpc.XMLRPCClient;
import de.timroes.axmlrpc.XMLRPCException;
import android.os.AsyncTask;
import android.util.Log;

public class XMLRPCCallTask extends AsyncTask<Object, Void, Object>
{
	@Override
	protected Object doInBackground(Object... params) {
		XMLRPCClient client = null;
		String functionName = null;
		if (params[0] instanceof XMLRPCClient)
			client = (XMLRPCClient)params[0];
		else
			Log.e("XMLRPCCallTask", "First argument to XMLRPCCallTask should be an XMLRPCClient.");
		if (params[1] instanceof String)
			functionName = (String)params[1];
		else
			Log.e("XMLRPCCallTask", "Second argument to XMLRPCCallTask should be a String.");
		
		Object result = null;
		try {
			result = client.call(functionName, Arrays.copyOfRange(params, 2, params.length));
			
		} catch (XMLRPCException e) {
			Log.e("XMLRPC",
					"Error while calling function: " + functionName +  " with aXMLRPC:");
			Log.e("XMLRPC", e.getMessage());
			Log.e("XMLRPC", "" + e.getStackTrace());
		}
		return result;
	}
}