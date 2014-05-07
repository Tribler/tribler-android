package org.tribler.tsap;

import android.graphics.Bitmap;
import android.graphics.Color;


public class ThumbItem {
	
	public static enum TORRENT_HEALTH {
		UNKNOWN, RED, YELLOW, GREEN;

		public static int toColor(TORRENT_HEALTH health)
		{
			switch(health)
			{
			case RED:
				return Color.RED;
			case YELLOW:
				return Color.YELLOW;
			case GREEN:
				return Color.GREEN;
			default:
				return Color.GRAY;
			}
		}
	}
	
	
	Bitmap thumbnail;
	String title;
	TORRENT_HEALTH health;
	int size;
	
	public ThumbItem(String title, Bitmap thumbnail, TORRENT_HEALTH health, int size) {
		super();
		this.thumbnail = thumbnail;
		this.title = title;
		this.health = health;
		this.size = size;
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
	
	public int getSize() {
		return size;
	}
	
	public void setSize(int size) {
		this.size = size;
	}
	
	public void setHealth(TORRENT_HEALTH health)
	{
		this.health = health;
	}
	
	public TORRENT_HEALTH getHealth()
	{
		return this.health;
	}
	
	public int getHealthColor()
	{
		return TORRENT_HEALTH.toColor(this.health);
	}
	
}
