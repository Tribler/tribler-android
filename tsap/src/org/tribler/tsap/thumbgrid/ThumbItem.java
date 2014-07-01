package org.tribler.tsap.thumbgrid;

import java.io.File;
import java.io.Serializable;

/**
 * Class that holds the information of the items in the thumb grid
 * 
 * @author Wendo Sabée
 */
public class ThumbItem implements Serializable{

	private static final long serialVersionUID = 1619276011406943212L;
	private int thumbnailId;
	private String title;
	private TORRENT_HEALTH health;
	private long size;
	private String infoHash;
	private File thumbImageFile;
	private int seeders;
	private int leechers;
	private String category;

	/**
	 * Constructor: initializes the instance variables
	 * 
	 * @param title
	 *            The title of the torrent
	 * @param thumbnailId
	 *            The thumbnail belonging to the torrent
	 * @param health
	 *            The health of the torrent
	 * @param size
	 *            The size of the torrent
	 */
	public ThumbItem(String infoHash, String title, TORRENT_HEALTH health,
			long size, String category, int seeders, int leechers) {
		this.title = title;
		this.health = health;
		this.size = size;
		this.infoHash = infoHash;
		this.seeders = seeders;
		this.leechers = leechers;
		this.category = category;
	}

	/**
	 * Returns the id of the thumbnail resource
	 * 
	 * @return The id of the thumbnail resource
	 */
	public int getThumbnailId() {
		return thumbnailId;
	}

	/**
	 * Set the resource id of the thumbnail to thumbnailId
	 * 
	 * @param thumbnailId
	 *            The id of the new thumbnail resource
	 */
	public void setThumbnail(int thumbnailId) {
		this.thumbnailId = thumbnailId;
	}

	/**
	 * Returns the title of the torrent
	 * 
	 * @return The title of the torrent
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Updates the title of the torrent
	 * 
	 * @param title
	 *            The new title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Returns the size of the torrent
	 * 
	 * @return The size of the torrent
	 */
	public long getSize() {
		return size;
	}

	/**
	 * Updates the size of the torrent
	 * 
	 * @param size
	 *            The new size of the torrent
	 */
	public void setSize(long size) {
		this.size = size;
	}

	/**
	 * Updates the health of the torrent
	 * 
	 * @param health
	 *            The new health of the torrent
	 */
	public void setHealth(TORRENT_HEALTH health) {
		this.health = health;
	}
	
	public void setThumbImageFile(File file)
	{
		thumbImageFile = file;
	}

	public void setLeechers(int leechers){
		this.leechers = leechers;
	}
	
	public void setSeeders(int seeders)
	{
		this.seeders = seeders;
	}
	
	public void setCategory(String category)
	{
		this.category = category;
	}
	
	/**
	 * Returns the health of the torrent
	 * 
	 * @return The health of the torrent
	 */
	public TORRENT_HEALTH getHealth() {
		return this.health;
	}

	/**
	 * Returns the color belonging to the health of the torrent
	 * 
	 * @return The color belonging to the health of the torrent
	 */
	public int getHealthColor() {
		return TORRENT_HEALTH.toColor(this.health);
	}
	
	public String getInfoHash() {
		return infoHash;
	}
	
	public File getThumbImageFile() {
		return thumbImageFile;
	}

	/**
	 * Returns a string representation of the ThumbItem
	 * 
	 * @return The title of the torrent
	 */
	public String toString() {
		return this.title;
	}

	public int getSeeders() {
		return this.seeders;
	}
	
	public int getLeechers() { 
		return this.leechers;
	}
	
	public String getCategory() {
		return this.category;
	}
	
	/**
	 * The default equals function, only checks on infohash
	 * @param obj The object to be compared with
	 * @return True if equal infohashes, otherwise false.
	 */
	public boolean equals(Object obj)
	{
		// Pre-check if we are the same
		if (this == obj)
			return true;
		
		// Only check infohash if it's actually a ThumbItem
		if (!(obj instanceof ThumbItem))
			return false;
		
		// Compare infohashes
		return ((ThumbItem) obj).getInfoHash().equals(this.getInfoHash());
	}
}
