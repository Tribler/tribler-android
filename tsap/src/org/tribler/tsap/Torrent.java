package org.tribler.tsap;

import java.io.File;
import java.io.Serializable;

import org.tribler.tsap.thumbgrid.TORRENT_HEALTH;

/**
 * Class representing a basic Torrent file containing the metadata of a torrent
 * including its name, infohash, etc.
 * 
 * @author Niels Spruit
 * 
 */
public class Torrent implements Serializable {

	private static final long serialVersionUID = 1619276011406943212L;

	private String name;
	private String infoHash;
	private long size;
	private int seeders;
	private int leechers;
	private File thumbnailFile;
	private String category;

	/**
	 * Constructor: initializes the instance variables
	 * 
	 * @param name
	 *            The name of the torrent
	 * @param infoHash
	 *            The infohash of the torrent
	 * @param size
	 *            The size of the torrent
	 * @param seeders
	 *            The number of seeders of the torrent
	 * @param leechers
	 *            The number of leechers of the torrent
	 * @param thumbnailFile
	 *            The file containing the thumbnail of the torrent
	 * @param category
	 *            The category of the torrent
	 */
	public Torrent(String name, String infoHash, long size, int seeders,
			int leechers, File thumbnailFile, String category) {
		this.name = name;
		this.infoHash = infoHash;
		this.size = size;
		this.seeders = seeders;
		this.leechers = leechers;
		this.thumbnailFile = thumbnailFile;
		this.category = category;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the infoHash
	 */
	public String getInfoHash() {
		return infoHash;
	}

	/**
	 * @param infoHash
	 *            the infoHash to set
	 */
	public void setInfoHash(String infoHash) {
		this.infoHash = infoHash;
	}

	/**
	 * @return the size
	 */
	public long getSize() {
		return size;
	}

	/**
	 * @param size
	 *            the size to set
	 */
	public void setSize(long size) {
		this.size = size;
	}

	/**
	 * @return the seeders
	 */
	public int getSeeders() {
		return seeders;
	}

	/**
	 * @param seeders
	 *            the seeders to set
	 */
	public void setSeeders(int seeders) {
		this.seeders = seeders;
	}

	/**
	 * @return the leechers
	 */
	public int getLeechers() {
		return leechers;
	}

	/**
	 * @param leechers
	 *            the leechers to set
	 */
	public void setLeechers(int leechers) {
		this.leechers = leechers;
	}

	/**
	 * @return the thumbnailFile
	 */
	public File getThumbnailFile() {
		return thumbnailFile;
	}

	/**
	 * @param thumbnailFile
	 *            the thumbnailFile to set
	 */
	public void setThumbnailFile(File thumbnailFile) {
		this.thumbnailFile = thumbnailFile;
	}

	/**
	 * @return the category
	 */
	public String getCategory() {
		return category;
	}

	/**
	 * @param category
	 *            the category to set
	 */
	public void setCategory(String category) {
		this.category = category;
	}	
	
	/**
	 * @return the health of the torrent
	 */
	public TORRENT_HEALTH getHealth() {
		if (seeders == -1 || leechers == -1) {
			return TORRENT_HEALTH.UNKNOWN;
		}

		if (seeders == 0) {
			return TORRENT_HEALTH.RED;
		}

		return ((leechers / seeders) > 0.5) ? TORRENT_HEALTH.YELLOW
				: TORRENT_HEALTH.GREEN;
	}

}
