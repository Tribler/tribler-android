package org.tribler.tsap;

import java.util.Random;

class Channel
{
	private String name;
	private boolean following;
	private int torrentAmount;
	private int rating;
	
	public String getName()
	{
		return name;
	}
	public boolean getFollowing()
	{
		return following;
	}
	public int getTorrentAmount()
	{
		return torrentAmount;
	}
	public int getRating()
	{
		return rating;
	}
	
	Channel(String name, boolean following, int torrentAmount, int rating)
	{
		this.name = name;
		this.following = following;
		this.torrentAmount = torrentAmount;
		this.rating = rating;
	}
	Channel(String name)
	{
		Random rand = new Random();
		this.name = name;
		this.following = rand.nextBoolean();
		this.torrentAmount = rand.nextInt(10000);
		this.rating = rand.nextInt(6);
	}
	public String toString()
	{
		return name;
	}
}