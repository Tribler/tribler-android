package org.tribler.tsap.downloads;

import java.io.Serializable;

public class Download implements Serializable {

	private static final long serialVersionUID = 5511967201437733003L;
	private String name;
	private long size;
	private String infoHash;

	Download() {
	}

	Download(String name, long size, String infoHash) {
		this.name = name;
		this.size = size;
		this.infoHash = infoHash;
	}

	public String getName() {
		return name;
	}

	public long getSize() {
		return size;
	}
	
	public String getInfoHash() {
		return infoHash;
	}
}