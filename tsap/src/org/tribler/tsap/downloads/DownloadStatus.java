package org.tribler.tsap.downloads;

/**
 * Class representing the download status of a torrent, including its
 * upload/download speed, progress and ETA.
 * 
 * @author Niels Spruit
 * 
 */
public class DownloadStatus {

	private int status;
	private double downloadSpeed;
	private double uploadSpeed;
	private double progress;
	private double eta;

	/**
	 * Constructor: initializes the instance variables
	 * 
	 * @param status	
	 * 			The status code of the download
	 * @param downloadSpeed
	 *            The download speed of the download
	 * @param uploadSpeed
	 *            The upload speed of the download
	 * @param progress
	 *            The progress of the download
	 * @param eta
	 *            The ETA of the download
	 */
	public DownloadStatus(int status, double downloadSpeed, double uploadSpeed,
			double progress, double eta) {
		this.status = status;
		this.downloadSpeed = downloadSpeed;
		this.uploadSpeed = uploadSpeed;
		this.progress = progress;
		this.eta = eta;
	}

	/**
	 * @return the downloadSpeed
	 */
	public double getDownloadSpeed() {
		return downloadSpeed;
	}

	/**
	 * @return the uploadSpeed
	 */
	public double getUploadSpeed() {
		return uploadSpeed;
	}

	/**
	 * @return the progress
	 */
	public double getProgress() {
		return progress;
	}

	/**
	 * @return the ETA
	 */
	public double getETA() {
		return eta;
	}
	
	/**
	 * @return the status code of the download
	 */
	public int getStatus()
	{
		return status;
	}

}
