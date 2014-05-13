package org.tribler.tsap.videoInfoScreen;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * A class that stores the metadata of a torrent
 * 
 * @author niels
 */
public class Torrent {
	private String name;
	private String type;
	private Date uploadDate;
	private double filesize;
	private int seeders;
	private int leechers;
	private String description;

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
	 *            The filesize of the torrent
	 * @param seed
	 *            The numbers of seeders for the torrent
	 * @param leech
	 *            The number of leechers for the torrent
	 * @param descr
	 *            The description of the torrent
	 */
	public Torrent(String nm, String tp, Date upDate, double fsize, int seed,
			int leech, String descr) {
		this.name = nm;
		this.type = tp;
		this.uploadDate = upDate;
		this.filesize = fsize;
		this.seeders = seed;
		this.leechers = leech;
		this.description = descr;
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
		return formatDate();
	}

	/**
	 * @return the formatted string containing the date
	 */
	private String formatDate() {
		return SimpleDateFormat.getDateInstance().format(uploadDate);
	}

	/**
	 * @return the filesize of the torrent
	 */
	public double getFilesize() {
		return filesize;
	}

	/**
	 * @return the number of seeders
	 */
	public int getSeeders() {
		return seeders;
	}

	/**
	 * @return the number of leechers
	 */
	public int getLeechers() {
		return leechers;
	}

	/**
	 * @return the torrent description
	 */
	public String getDescription() {
		return description;
	}

}
