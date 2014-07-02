package org.tribler.tsap.XMLRPC.tests;

import java.util.HashMap;
import java.util.Map;

import org.tribler.tsap.XMLRPC.XMLRPCConnection;

public class StubbedXMLRPCConnection extends XMLRPCConnection{
	private Map<String, Object> returnValues;
	private static StubbedXMLRPCConnection mInstance = null;
	
	private StubbedXMLRPCConnection () {
	}
	
	public static StubbedXMLRPCConnection getInstance() {
		if (mInstance == null) {
			mInstance = new StubbedXMLRPCConnection();
		}
		return mInstance;
	}
	
	public void setupStub() {
		returnValues = new HashMap<String, Object>();
	}
	public void clearReturnValues() {
		returnValues.clear();
	}
	
	public void addReturnValue(String functionName, Object returnValue) {
		if(returnValues.containsKey(functionName)) {
			returnValues.remove(functionName);
		}
		returnValues.put(functionName, returnValue);
	}
	
	@Override
	public Object call(String functionName, Object... params) {
		return returnValues.get(functionName);
	}
	
	public void addListener(IConnectionListener listener) { }
}