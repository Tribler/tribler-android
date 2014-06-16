package org.tribler.tsap;

/**
 * Interface for reacting to search requests. When a search is submitted onSearchSubmit is called. When a search returns results onSearchResults is called.
 * @author Dirk Schut
 *
 */
public interface ISearchListener
{
	/**
	 * Callback function, called when a search is submitted.
	 * @param keywords
	 * 		The keywords of that search
	 */
	void onSearchSubmit(String keywords);
	/**
	 * Callback function, called when a search returns results for the first time.
	 */
	void onSearchResults();
}