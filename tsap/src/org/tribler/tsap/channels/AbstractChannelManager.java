package org.tribler.tsap.channels;

import java.util.Observable;

/**
 * Interface for searching channels.
 * Results of search queries will be sent as an arrayList<Channel> to all observers.
 * 
 * @author Dirk Schut
 * @since 26-5-2014
 */
abstract class AbstractChannelManager extends Observable
{
	/**
	 * Searches the local dispersy data for channels fitting a certain query.
	 * Once the results are found it will send them as an ArrayList<Channel> to all observers.
	 * @param query
	 * 		The query that Tribler will look for in the names of the channels
	 */
	abstract void getLocal(String query);
	/**
	 * Searches the remote dispersy data for channels fitting a certain query.
	 * Results can be retrieved by calling getRemoteResults().
	 * The amount of found results can be retrieved by calling getRemoteResultsCount().
	 * @param query
	 * 		The query that Tribler will look for in the names of the channels
	 */
	abstract void searchRemote(String query);
	
	abstract void getRemoteResultsCount();
	
	abstract void getRemoteResults();
}