package org.tribler.tsap.downloads;

import java.io.Serializable;

import org.tribler.tsap.Torrent;

/**
 * Class for storing data on a download that is finished or currently running.
 * 
 * @author Dirk Schut & Niels Spruit
 * 
 */
public class Download implements Serializable {

	private static final long serialVersionUID = 5511967201437733003L;

	private Torrent torrent;
	private DownloadStatus downStatus;
	private double vodETA;
	private boolean vodPlayable;
	private int availability;

	/**
	 * Constructor: initializes the instance variables
	 * 
	 * @param torrent
	 *            The torrent that is downloaded
	 * @param downStatus
	 *            The status of the download
	 * @param vodETA
	 *            The VOD ETA of the download
	 * @param vodPlayable
	 *            Whether the download is ready for VOD
	 * @param availability
	 *            The availabiltiy of the torrent
	 */
	public Download(Torrent torrent, DownloadStatus downStatus, double vodETA,
			boolean vodPlayable, int availability) {
		this.torrent = torrent;
		this.downStatus = downStatus;
		this.vodETA = vodETA;
		this.vodPlayable = vodPlayable;
		this.availability = availability;
	}

	/**
	 * @return the torrent that is being downloaded
	 */
	public Torrent getTorrent() {
		return torrent;
	}

	/**
	 * @return the status of the download
	 */
	public DownloadStatus getDownloadStatus() {
		return downStatus;
	}

	/**
	 * @return the VOD ETA
	 */
	public double getVOD_ETA() {
		return vodETA;
	}

	/**
	 * @return the vodPlayable
	 */
	public boolean isVODPlayable() {
		return vodPlayable;
	}

	/**
	 * @return the availability
	 */
	public int getAvailability() {
		return availability;
	}

}