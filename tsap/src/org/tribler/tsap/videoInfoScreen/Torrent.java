package org.tribler.tsap.videoInfoScreen;

import org.tribler.tsap.thumbgrid.TORRENT_HEALTH;


/**
 * A class that stores the metadata of a torrent
 * 
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
	 * @return the name of the torrent
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the type of the torrent
	 */
	public String getType() {
		return type;
	}

	/**
	 * @return the upload date of the torrent
	 */
	public String getUploadDate() {
		return uploadDate;
	}

	/**
	 * @return the filesize of the torrent
	 */
	public double getFilesize()
	{
		return filesize;
	}
	
//	/**
//	 * @return the filesize of the torrent in string format
//	 */
//	public String getFilesizeString() {
//		return Double.toString(filesize)+" MB";
//	}

	/**
	 * @return the number of seeders
	 */
	public String getSeeders() {
		return Integer.toString(seeders);
	}

	/**
	 * @return the number of leechers
	 */
	public String getLeechers() {
		return Integer.toString(leechers);
	}

	/**
	 * @return the health of the torrent
	 */
	public TORRENT_HEALTH getHealth() {
		return health;
	}

	/**
	 * @return the torrent description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @return the id of the thumbnail of this torrent
	 */
	public int getThumbnailID() {
		return thumbnailID;
	}
	
	/**
	 * @return the string representation of this torrent
	 */
	public String toString()
	{
		return name;
	}

}
