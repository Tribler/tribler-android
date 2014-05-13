package org.tribler.tsap.videoInfoScreen;


/**
 * A class that stores the metadata of a torrent
 * 
 * @author niels
 */
public class Torrent {
	private String name;
	private String type;
	private String uploadDate;
	private double filesize;
	private int seeders;
	private int leechers;
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
			int leech, String descr, int tID) {
		this.name = nm;
		this.type = tp;
		this.uploadDate = upDate;
		this.filesize = fsize;
		this.seeders = seed;
		this.leechers = leech;
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
	public String getFilesize() {
		return Double.toString(filesize)+" MB";
	}

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

}
