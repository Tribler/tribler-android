package org.tribler.tsap.channels;

import java.net.URL;
import java.util.ArrayList;
import java.util.Map;

import android.os.AsyncTask;
import android.util.Log;
import de.timroes.axmlrpc.XMLRPCClient;
import de.timroes.axmlrpc.XMLRPCException;

/**
 * Class for receiving channels over XMPRPC using the aXMLRPC library
 * @author Dirk Schut
 * @since 26-5-2014
 */
class XMLRPCChannelManager extends AbstractChannelManager
{
	XMLRPCClient client = null;
	/**
	 * Constructor: Makes a connection with an XMLRPC server
	 * @param url
	 * 	The url of the XMLRPC server
	 */
	XMLRPCChannelManager(URL url)
	{
		client = new XMLRPCClient(url);
	}
	
	@Override
	void getLocal(final String query)
	{
		/*XMLRPCCallback listener = new XMLRPCCallback()
		{
			@Override
		    public void onResponse(long id, Object result)
			{
				ArrayList<Channel> resultsList = new ArrayList<Channel>();
				Log.i("XMLRPC", "Got response");
				Object[] castResult = (Object[]) result;
				Log.i("XMLRPC", "Got " + castResult.length + "results" );
		        for(int i=0; i<castResult.length; i++)
		        {
		        	Channel c = new Channel((Map<String, Object>)castResult[i]);
		        	Log.i("XMLRPC", "Channel name: " + c.getName());
		        	resultsList.add(c);
		        }
		        setChanged();
		        notifyObservers(resultsList);
		        Log.i("XMLRPC", "Observers notified!");
		    }
			@Override
		    public void onError(long id, XMLRPCException error) {
				Log.w("XMLRPC", error.getMessage());
				Log.w("XMLRPC", "Error!");
		        // Handling any error in the library
		    }
			@Override
			public void onServerError(long id, XMLRPCServerException error) {
				Log.w("XMLRPC", "ServerError!");
				// TODO Auto-generated method stub
				
			}
		};
		client.callAsync(listener, "channels.get_local", query);
		Log.w("XMLRPC",""+countObservers());
		*/
		AsyncTask<Void, Void, Object> task = new AsyncTask<Void, Void, Object>()
		{

			@Override
			protected Object doInBackground(Void... params) {
				Object[] castResult = null;
				try {
					castResult = (Object[])client.call("channels.get_local", query);
				} catch (XMLRPCException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				ArrayList<Channel> resultsList = new ArrayList<Channel>();
				Log.v("XMLRPC", "Got " + castResult.length + "results" );
		        for(int i=0; i<castResult.length; i++)
		        {
		        	@SuppressWarnings("unchecked")
		        	Channel c = new Channel((Map<String, Object>)castResult[i]);
		        	//Log.v("XMLRPC", "Channel name: " + c.getName());
		        	resultsList.add(c);
		        }
		        
		        return resultsList;
			}
			@Override
			protected void onPostExecute(Object result) {
				setChanged();
		        notifyObservers(result);
		        Log.v("XMLRPC", "Observers notified!");
		     }

		};
		task.execute();
	}	
}