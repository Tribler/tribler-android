package org.tribler.tsap.XMLRPC.tests;

import java.util.HashMap;
import java.util.Map;

import org.tribler.tsap.XMLRPC.XMLRPCConnection;

class StubbedXMLRPCConnection extends XMLRPCConnection{
	Map<String, Object> returnValues;
	
	public void setupStub() {
		returnValues = new HashMap<String, Object>();
	}
	
	public void addReturnValue(String functionName, Object returnValue) {
		returnValues.put(functionName, returnValue);
	}
	
	@Override
	public Object call(String functionName, Object... params) {
		return returnValues.get(functionName);
	}
}