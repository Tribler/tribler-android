package org.tribler.tsap.tests;

import java.util.Map;
import java.util.HashMap;

import org.tribler.tsap.util.Utility;

import android.test.AndroidTestCase;

public class UtilityTest extends AndroidTestCase {
	
	public Map<String, Object> testMap;
	
	@Override
	protected void setUp(){
		testMap = new HashMap<String, Object>();
		
		testMap.put("integertest", 33);
		testMap.put("stringtest", "hello");
		testMap.put("booltest", false);
		testMap.put("doubletest", 33.2);
	}
	
	public void testBytesToStringConversion()
	{
		assertEquals("bytes formatted incorrectly", "0 B",
				Utility.convertBytesToString(0));
		assertEquals("bytes formatted incorrectly", "44 B",
				Utility.convertBytesToString(44));
		assertEquals("bytes formatted incorrectly", "-347 B",
				Utility.convertBytesToString(-347.1829));
		assertEquals("bytes formatted incorrectly", "77.7 kB",
				Utility.convertBytesToString(77708.99));
		assertEquals("bytes formatted incorrectly", "-439.8 MB",
				Utility.convertBytesToString(-439875670.0));
		assertEquals("bytes formatted incorrectly", "43.9 GB",
				Utility.convertBytesToString(43987567444.0));
		assertEquals("bytes formatted incorrectly", "-43.9 TB",
				Utility.convertBytesToString(-43987567444888.0));
		assertEquals("bytes formatted incorrectly", "43.9 PB",
				Utility.convertBytesToString(43987567444888777.0));
		assertEquals("bytes formatted incorrectly", "-439875.6 PB",
				Utility.convertBytesToString(-439875674448887771234.0));
	}
	
	public void testBytesPerSecondToStringConversion()
	{
		assertEquals("bytes formatted incorrectly", "0 B/s",
				Utility.convertBytesPerSecToString(0));
		assertEquals("bytes formatted incorrectly", "-44 B/s",
				Utility.convertBytesPerSecToString(-44));
		assertEquals("bytes formatted incorrectly", "347 B/s",
				Utility.convertBytesPerSecToString(347.1829));
		assertEquals("bytes formatted incorrectly", "-77.7 kB/s",
				Utility.convertBytesPerSecToString(-77708.99));
		assertEquals("bytes formatted incorrectly", "439.8 MB/s",
				Utility.convertBytesPerSecToString(439875670.0));
		assertEquals("bytes formatted incorrectly", "-43.9 GB/s",
				Utility.convertBytesPerSecToString(-43987567444.0));
		assertEquals("bytes formatted incorrectly", "43.9 TB/s",
				Utility.convertBytesPerSecToString(43987567444888.0));
		assertEquals("bytes formatted incorrectly", "-43.9 PB/s",
				Utility.convertBytesPerSecToString(-43987567444888777.0));
		assertEquals("bytes formatted incorrectly", "439875.6 PB/s",
				Utility.convertBytesPerSecToString(439875674448887771234.0));
	}
	
	public void testDownloadStateConversionInvalid()
	{
		assertEquals("state converted incorrectly", "Invalid state",
				Utility.convertDownloadStateIntToMessage(0));
		assertEquals("state converted incorrectly", "Invalid state",
				Utility.convertDownloadStateIntToMessage(-1));
		assertEquals("state converted incorrectly", "Invalid state",
				Utility.convertDownloadStateIntToMessage(98093));
	}
	
	public void testDownloadStateConversionValid()
	{
		assertEquals("state converted incorrectly", "Allocating disk space",
				Utility.convertDownloadStateIntToMessage(1));
		assertEquals("state converted incorrectly", "Waiting for hash check",
				Utility.convertDownloadStateIntToMessage(2));
		assertEquals("state converted incorrectly", "Downloading",
				Utility.convertDownloadStateIntToMessage(3));
		assertEquals("state converted incorrectly", "Seeding",
				Utility.convertDownloadStateIntToMessage(4));
		assertEquals("state converted incorrectly", "Stopped",
				Utility.convertDownloadStateIntToMessage(5));
		assertEquals("state converted incorrectly", "Stopped because of an error",
				Utility.convertDownloadStateIntToMessage(6));
		assertEquals("state converted incorrectly", "Acquiring metadata",
				Utility.convertDownloadStateIntToMessage(7));
	}
	
	public void testGetFromMapInteger()
	{
		assertTrue(Utility.getFromMap(testMap, "integertest", (int) -1) == (int) 33);
		assertTrue(Utility.getFromMap(testMap, "invalid_int", (int) -1) == (int) -1);
	}
	
	public void testGetFromMapDouble()
	{
		assertTrue(Utility.getFromMap(testMap, "doubletest", (double) -1.1) == (double) 33.2);
		assertTrue(Utility.getFromMap(testMap, "invalid_double", (double) -1.1) == (double) -1.1);
	}
	
	public void testGetFromMapString()
	{
		assertTrue(Utility.getFromMap(testMap, "stringtest", "fail").equals("hello"));
		assertTrue(Utility.getFromMap(testMap, "invalid_string", "fail").equals("fail"));
	}
	
	public void testGetFromMapBool()
	{
		assertTrue(Utility.getFromMap(testMap, "booltest", true) == false);
		assertTrue(Utility.getFromMap(testMap, "invalid_bool", true) == true);
	}
}