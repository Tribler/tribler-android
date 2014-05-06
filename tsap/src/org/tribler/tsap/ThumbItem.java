package org.tribler.tsap;

import android.graphics.Bitmap;


public class ThumbItem {
	
	public enum TORRENT_HEALTH {
		UNKNOWN, RED, YELLOW, GREEN
	}
	
	Bitmap thumbnail;
	String title;
	TORRENT_HEALTH health;
	
	public ThumbItem(String title, Bitmap thumbnail, TORRENT_HEALTH health) {
		super();
		this.thumbnail = thumbnail;
		this.title = title;
		this.health = health;
	}
	
	public Bitmap getThumbnail() {
		return thumbnail;
	}
	
	public void setThumbnail(Bitmap thumbnail) {
		this.thumbnail = thumbnail;
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	public void setHealth(TORRENT_HEALTH health)
	{
		this.health = health;
	}
	
	public TORRENT_HEALTH getHealth()
	{
		return this.health;
	}
	

}
