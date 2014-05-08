package org.tribler.tsap;

class Channel
{
	private String name;
	private int torrentAmount;
	private int rating;
	
	public String getName()
	{
		return name;
	}
	public int getTorrentAmount()
	{
		return torrentAmount;
	}
	public int getRating()
	{
		return rating;
	}
	
	Channel(String name, int torrentAmount, int rating)
	{
		this.name = name;
		this.torrentAmount = torrentAmount;
		this.rating = rating;
	}
}