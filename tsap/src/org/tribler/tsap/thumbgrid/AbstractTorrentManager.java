package org.tribler.tsap.thumbgrid;

import java.util.Observable;

public abstract class AbstractTorrentManager extends Observable
{
	/**
	 * Searches the remote dispersy data for torrents fitting a certain query.
	 * If there are enough peers for a search it will send true to all
	 * observers, else it will send false. Results can be retrieved by calling
	 * getRemoteResults(). The amount of found results can be retrieved by
	 * calling getRemoteResultsCount().
	 * 
	 * @param query
	 *            The query that Tribler will look for in the names of the
	 *            torrents
	 */
	public abstract void searchRemote(String query);

	/**
	 * It will send an Integer to all observers describing the amount of found
	 * results.
	 */
	public abstract void getRemoteResultsCount();

	/**
	 * It will send an ArrayList<ThumbItem> to all observers containing the found
	 * torrents.
	 */
	//abstract void getRemoteResults();
}