package org.tribler.tsap.videoInfoScreen;

import java.util.ArrayList;

import org.tribler.tsap.R;

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
		torrents.add(new Torrent("Sintel","Video","2010-06-08",400.51,73,3,"Sintel is a short computer animated film by the Blender Institute, part of the Blender Foundation. Like the foundation's previous films Elephants Dream and Big Buck Bunny, the film was made using Blender, a free software application for animation created and supported by the same foundation. Sintel was produced by Ton Roosendaal, chairman of the Foundation, and directed by Colin Levy, an artist at Pixar Animation Studios. (code-named Durian)",R.drawable.sintel));
		torrents.add(new Torrent("Dracula","Video","2010-10-08",500,56,6,"Dracula",R.drawable.dracula));
		torrents.add(new Torrent("King Kong","Video","2010-07-23",600.59,66,2,"King Kong",R.drawable.kingkong));
	}
	
	/** 
	 * @param id the position of the torrent in the list
	 * @return the torrent belonging to the id
	 */
	public static Torrent getTorrentById(int id)
	{
		return torrents.get(id);
	}
	
	/** 
	 * @return the number of torrents in the list
	 */
	public static int getNumberOfTorrents()
	{
		if(torrents != null)
			return torrents.size();
		return 0;
	}

	/**
	 * @param id the id of the torrent
	 * @return True iff the list contains a torrent at index=id
	 */
	public static boolean containsTorrentWithID(int id)
	{
		return (id>=0 && id<torrents.size() && torrents.get(id) != null);
	}
}