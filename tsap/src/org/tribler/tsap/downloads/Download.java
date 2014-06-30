package org.tribler.tsap.downloads;

import java.io.File;
import java.io.Serializable;

/**
 * Class for storing data on a download that is finished or currently running.
 * 
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
	private double ETA;
	private boolean vodPlayable;
	
	//needed for updating the download info
	private File thumbImageFile;
	private int seeders;
	private int leechers;
	private String category;
	private long size;


	/**
	 * empty constructor for serialization
	 */
	public Download() {
	}

	/**
	 * Download status object
	 * @param name Download name
	 * @param infoHash Torrent infohash
	 * @param status Download status
	 * @param downloadSpeed Download speed
	 * @param uploadSpeed Upload speed
	 * @param progress Download progress (0.0-1.0)
	 * @param ETA ETA in seconds
	 * @param vodETA VOD ETA in seconds
	 * @param vodPlayable Is it okay to start streaming
	 */
	public Download(String name, String infoHash, int status, double downloadSpeed,
			double uploadSpeed, double progress, double ETA, double vodETA,
			boolean vodPlayable, File thumbFile, int seeders, int leechers, String category, long size) {
		this.name = name;
		this.infoHash = infoHash;
		this.status = status;
		this.downloadSpeed = downloadSpeed;
		this.uploadSpeed = uploadSpeed;
		this.progress = progress;
		this.ETA = ETA;
		this.vodETA = vodETA;
		this.vodPlayable = vodPlayable;
		
		this.thumbImageFile = thumbFile;
		this.seeders = seeders;
		this.leechers = leechers;
		this.category = category;
		this.size = size;
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

	public double getETA()
	{
		return ETA;
	}
	
	public double getVOD_ETA() {
		return vodETA;
	}

	public boolean isVODPlayable() {
		return vodPlayable;
	}
	
	public File getThumbImageFile() {
		return thumbImageFile;
	}

	public int getSeeders() {
		return seeders;
	}

	public int getLeechers() {
		return leechers;
	}

	public String getCategory() {
		return category;
	}

	public long getSize() {
		return size;
	}
}