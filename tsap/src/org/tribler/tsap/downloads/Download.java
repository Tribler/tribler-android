package org.tribler.tsap.downloads;

import java.io.Serializable;

public class Download implements Serializable {

	private static final long serialVersionUID = 5511967201437733003L;
	private String name;
	private long size;

	Download() {
	}

	Download(String name, long size) {
		this.name = name;
		this.size = size;
	}

	public String getName() {
		return name;
	}

	public long getSize() {
		return size;
	}
}