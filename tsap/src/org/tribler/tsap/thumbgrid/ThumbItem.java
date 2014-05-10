package org.tribler.tsap.thumbgrid;

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
	
	int thumbnailId;
	String title;
	TORRENT_HEALTH health;
	int size;
	
	public ThumbItem(String title, int thumbnailId, TORRENT_HEALTH health, int size) {
		super();
		this.thumbnailId = thumbnailId;
		this.title = title;
		this.health = health;
		this.size = size;
	}
	
	public int getThumbnailId() {
		return thumbnailId;
	}
	
	public void setThumbnail(int thumbnailId) {
		this.thumbnailId = thumbnailId;
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
	
	public String toString()
	{
		return this.title;
	}
	
}
