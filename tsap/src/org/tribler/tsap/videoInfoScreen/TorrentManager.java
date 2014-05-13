package org.tribler.tsap.videoInfoScreen;

import java.util.ArrayList;
import java.util.Date;

/**
 * Used to retrieve torrents by their id
 * @author niels *
 */
public class TorrentManager {
	private static ArrayList<Torrent> torrents;
	
	/**
	 * Initializes the torrent list with some torrent stubs
	 */
	public static void initiliazeTorrents()
	{
		torrents = new ArrayList<Torrent>();
		torrents.add(new Torrent("Sintel","Video",new Date(2010,6,8),400.51,73,3,"Sintel is a short computer animated film by the Blender Institute, part of the Blender Foundation. Like the foundation's previous films Elephants Dream and Big Buck Bunny, the film was made using Blender, a free software application for animation created and supported by the same foundation. Sintel was produced by Ton Roosendaal, chairman of the Foundation, and directed by Colin Levy, an artist at Pixar Animation Studios. (code-named Durian)"));
		torrents.add(new Torrent("Dracula","Video",new Date(2008,10,8),500,56,6,"Dracula"));
		torrents.add(new Torrent("King Kong","Video",new Date(2007,7,8),600.59,66,2,"King Kong"));
	}
	
	/** 
	 * @param id the position of the torrent in the list
	 * @return the torrent belonging to the id
	 */
	public static Torrent getTorrentById(int id)
	{
		return torrents.get(id);
	}

}
