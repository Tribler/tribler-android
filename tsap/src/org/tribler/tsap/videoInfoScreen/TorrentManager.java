package org.tribler.tsap.videoInfoScreen;

import java.util.ArrayList;

import org.tribler.tsap.R;
import org.tribler.tsap.thumbgrid.ThumbItem;

/**
 * Used to retrieve torrents by their id
 * @author niels *
 */
public class TorrentManager {
	private static ArrayList<Torrent> torrents;
	
	/**
	 * Initializes the torrent list with some torrent stubs
	 */
	public static void initializeTorrents()
	{
		torrents = new ArrayList<Torrent>();
		torrents.add(new Torrent("Sintel","Video","2010-06-08",400.51,73,3,"Sintel is a short computer animated film by the Blender Institute, part of the Blender Foundation. Like the foundation's previous films Elephants Dream and Big Buck Bunny, the film was made using Blender, a free software application for animation created and supported by the same foundation. Sintel was produced by Ton Roosendaal, chairman of the Foundation, and directed by Colin Levy, an artist at Pixar Animation Studios. (code-named Durian)",R.drawable.sintel));
		torrents.add(new Torrent("Dracula","Video","2010-10-08",500,56,6,"Dracula",R.drawable.dracula));
		torrents.add(new Torrent("King Kong","Video","2010-07-23",600.59,66,2,"King Kong",R.drawable.kingkong));
	
	
//		gridArray.add(new ThumbItem("Sintel", R.drawable.sintel, ThumbItem.TORRENT_HEALTH.GREEN, 500));
//		gridArray.add(new ThumbItem("Dracula", R.drawable.dracula, ThumbItem.TORRENT_HEALTH.YELLOW, 4321));
//		gridArray.add(new ThumbItem("King Kong", R.drawable.kingkong, ThumbItem.TORRENT_HEALTH.UNKNOWN, 12353));
//		gridArray.add(new ThumbItem("Tears of Steal", R.drawable.tos, ThumbItem.TORRENT_HEALTH.RED, 423));
//		gridArray.add(new ThumbItem("To The Last Man", R.drawable.lastman, ThumbItem.TORRENT_HEALTH.UNKNOWN, 12353));
//		gridArray.add(new ThumbItem("Attack of the 50 ft woman", R.drawable.fiftyft, ThumbItem.TORRENT_HEALTH.UNKNOWN, 12353));
//		gridArray.add(new ThumbItem("Draculas Daughter", R.drawable.dracula_daughter, ThumbItem.TORRENT_HEALTH.RED, 423));
//		gridArray.add(new ThumbItem("Lusty Men", R.drawable.lustymen, ThumbItem.TORRENT_HEALTH.RED, 423));
//		gridArray.add(new ThumbItem("Mantis", R.drawable.mantis, ThumbItem.TORRENT_HEALTH.RED, 423));
//		gridArray.add(new ThumbItem("Son of Frankenstein", R.drawable.sof, ThumbItem.TORRENT_HEALTH.RED, 423));
//	
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