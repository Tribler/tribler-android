package org.tribler.tsap.thumbgrid;

import java.util.Map;

import org.tribler.tsap.R;

/**
 * Class that holds the information of the items in the thumb grid
 * 
 * @author Wendo Sabée
 */
public class ThumbItem {

	private int thumbnailId;
	private String title;
	private TORRENT_HEALTH health;
	private int size;

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
	public ThumbItem(String title, int thumbnailId, TORRENT_HEALTH health,
			int size) {
		this.thumbnailId = thumbnailId;
		this.title = title;
		this.health = health;
		this.size = size;
	}

	public ThumbItem(Map<String, Object> map) {
		this.thumbnailId = R.drawable.dracula;
		this.title = (String)map.get("name");
		this.health = TORRENT_HEALTH.YELLOW;
		this.size = 1000;
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
	public int getSize() {
		return size;
	}

	/**
	 * Updates the size of the torrent
	 * 
	 * @param size
	 *            The new size of the torrent
	 */
	public void setSize(int size) {
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

	/**
	 * Returns a string representation of the ThumbItem
	 * 
	 * @return The title of the torrent
	 */
	public String toString() {
		return this.title;
	}

}
