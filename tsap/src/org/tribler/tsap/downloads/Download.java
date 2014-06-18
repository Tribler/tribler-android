package org.tribler.tsap.downloads;

import java.io.Serializable;

/**
 * Class for storing data on a download that is finished or currently running.
 * @author Dirk Schut
 *
 */
public class Download implements Serializable {

	private static final long serialVersionUID = 5511967201437733003L;
	private String name;
	private String infoHash;
	private int status;
	private double uploadSpeed;
	private double downloadSpeed;
	private double progress;
	private double vodETA;

	/**
	 * empty constructor for serialization
	 */
	Download() {
	}
	
	/**
	 * Constructor to fill in all the fields
	 * @param name
	 * @param infoHash
	 * @param status
	 * @param downloadSpeed
	 * @param uploadSpeed
	 * @param progress
	 * @param vodETA
	 */
	Download(String name, String infoHash, int status, double downloadSpeed, double uploadSpeed, double progress, double vodETA) {
		this.name = name;
		this.infoHash = infoHash;
		this.status = status;
		this.downloadSpeed = downloadSpeed;
		this.uploadSpeed = uploadSpeed;
		this.progress = progress;
		this.vodETA = vodETA;
	}

	public String getName() {
		return name;
	}
	
	public String getInfoHash() {
		return infoHash;
	}
	
	public int getStatus() {
		return status;
	}
	
	public double getDownloadSpeed() {
		return downloadSpeed;
	}
	
	public double getUploadSpeed() {
		return uploadSpeed;
	}
	
	public double getProgress() {
		return progress;
	}
	
	public double getVOD_ETA()
	{
		return vodETA;
	}
}