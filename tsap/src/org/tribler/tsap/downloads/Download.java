package org.tribler.tsap.downloads;

import java.io.Serializable;

public class Download implements Serializable {

	private static final long serialVersionUID = 5511967201437733003L;
	private String name;
	private String infoHash;
	private int status;
	private double uploadSpeed;
	private double downloadSpeed;
	private double progress;

	Download() {
	}

	Download(String name, String infoHash, int status, double downloadSpeed, double uploadSpeed, double progress) {
		this.name = name;
		this.infoHash = infoHash;
		this.status = status;
		this.downloadSpeed = downloadSpeed;
		this.uploadSpeed = uploadSpeed;
		this.progress = progress;
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
}