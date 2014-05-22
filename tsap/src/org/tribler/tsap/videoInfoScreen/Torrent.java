package org.tribler.tsap.videoInfoScreen;

import org.tribler.tsap.thumbgrid.TORRENT_HEALTH;


/**
 * A class that stores the detailed information of a torrent 
 * @author Niels Spruit
 */
public class Torrent {
	private String name;
	private String type;
	private String uploadDate;
	private double filesize;
	private int seeders;
	private int leechers;
	private TORRENT_HEALTH health;
	private String description;
	private int thumbnailID;

	/**
	 * Constructor: initializes the class variables
	 * 
	 * @param nm
	 *            The name of the torrent
	 * @param tp
	 *            The type of the torrent
	 * @param upDate
	 *            The upload date of the torrent
	 * @param fsize
	 *            The filesize of the torrent in MB
	 * @param seed
	 *            The numbers of seeders for the torrent
	 * @param leech
	 *            The number of leechers for the torrent
	 * @param descr
	 *            The description of the torrent
	 * @param tID
	 *            The id of the torrent's thumbnail
	 */
	public Torrent(String nm, String tp, String upDate, double fsize, int seed,
			int leech, TORRENT_HEALTH health, String descr, int tID) {
		this.name = nm;
		this.type = tp;
		this.uploadDate = upDate;
		this.filesize = fsize;
		this.seeders = seed;
		this.leechers = leech;
		this.health = health;
		this.description = descr;
		this.thumbnailID = tID;
	}

	/**
	 * Returns the name of the torrent
	 * @return The name of the torrent
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns the type of the torrent
	 * @return The type of the torrent
	 */
	public String getType() {
		return type;
	}

	/**
	 * Returns a string representing the upload date of the torrent
	 * @return The upload date of the torrent as a String
	 */
	public String getUploadDate() {
		return uploadDate;
	}

	/**
	 * Returns the file size of the torrent
	 * @return The file size of the torrent
	 */
	public double getFilesize()
	{
		return filesize;
	}

	/**
	 * Returns the number of seeder of this torrent
	 * @return The number of seeders of this torrent
	 */
	public int getSeeders() {
		return seeders;
	}

	/**
	 * Returns the number of leechers of this torrent
	 * @return The number of leechers of this torrent
	 */
	public int getLeechers() {
		return leechers;
	}

	/**
	 * Returns the health of this torrent
	 * @return The health of the torrent
	 */
	public TORRENT_HEALTH getHealth() {
		return health;
	}

	/**
	 * Returns the description of this torrent
	 * @return The torrent description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Returns the resource id of the thumbnail of this torrent
	 * @return The id of the thumbnail resource of this torrent
	 */
	public int getThumbnailID() {
		return thumbnailID;
	}
	
	/**
	 * Returns the string representation of this torrent
	 * @return The name of the torrent
	 */
	public String toString()
	{
		return name;
	}

}
