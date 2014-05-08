package org.tribler.tsap;

import java.util.ArrayList;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.app.Fragment;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class ThumbGridFragment extends Fragment {

	private ArrayList<ThumbItem> gridArray = new ArrayList<ThumbItem>();
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	super.onCreateView(inflater, container, savedInstanceState);
    	
    	View v = inflater.inflate(R.layout.fragment_thumb_grid, container, false);
    		
    	GridView gridView = (GridView) v.findViewById(R.id.ThumbsGrid);
        
    	float s = v.getContext().getResources().getDisplayMetrics().density;
    	int w = (int)(100 * s);
    	int h = (int)(150 * s);
    	
    	//set grid view item
    	Bitmap bmSintel = decodeSampledBitmapFromResource(this.getResources(), R.drawable.sintel, w, h);
    	Bitmap bmTOS = decodeSampledBitmapFromResource(this.getResources(), R.drawable.tos, w, h);
    	Bitmap bmDracula = decodeSampledBitmapFromResource(this.getResources(), R.drawable.dracula, w, h);
    	Bitmap bmKingkong = decodeSampledBitmapFromResource(this.getResources(), R.drawable.kingkong, w, h);
    	Bitmap bmLastman = decodeSampledBitmapFromResource(this.getResources(), R.drawable.lastman, w, h);
    	Bitmap bmDracD = decodeSampledBitmapFromResource(this.getResources(), R.drawable.dracula_daughter, w, h);
    	Bitmap bm50ft = decodeSampledBitmapFromResource(this.getResources(), R.drawable.fiftyft, w, h);
    	Bitmap bmLustymen = decodeSampledBitmapFromResource(this.getResources(), R.drawable.lustymen, w, h);
    	Bitmap bmMantis = decodeSampledBitmapFromResource(this.getResources(), R.drawable.mantis, w, h);
    	Bitmap bmSof = decodeSampledBitmapFromResource(this.getResources(), R.drawable.sof, w, h);

    	for(int i = 0; i < 11; i++)
    	{
    		gridArray.add(new ThumbItem("Sintel", bmSintel, ThumbItem.TORRENT_HEALTH.GREEN, 500));
    		gridArray.add(new ThumbItem("Dracula", bmDracula, ThumbItem.TORRENT_HEALTH.YELLOW, 4321));
    		gridArray.add(new ThumbItem("King Kong", bmKingkong, ThumbItem.TORRENT_HEALTH.UNKNOWN, 12353));
    		gridArray.add(new ThumbItem("Tears of Steal", bmTOS, ThumbItem.TORRENT_HEALTH.RED, 423));
    		gridArray.add(new ThumbItem("To The Last Man", bmLastman, ThumbItem.TORRENT_HEALTH.UNKNOWN, 12353));
    		gridArray.add(new ThumbItem("Attack of the 50 ft woman", bm50ft, ThumbItem.TORRENT_HEALTH.UNKNOWN, 12353));
    		gridArray.add(new ThumbItem("Draculas Daughter", bmDracD, ThumbItem.TORRENT_HEALTH.RED, 423));
    		gridArray.add(new ThumbItem("Lusty Men", bmLustymen, ThumbItem.TORRENT_HEALTH.RED, 423));
    		gridArray.add(new ThumbItem("Mantis", bmMantis, ThumbItem.TORRENT_HEALTH.RED, 423));
    		gridArray.add(new ThumbItem("Son of Frankenstein", bmSof, ThumbItem.TORRENT_HEALTH.RED, 423));
    	}
		
		ThumbAdapter customThumbs = new ThumbAdapter(container.getContext(), R.layout.thumb_grid_item, gridArray);
		gridView.setAdapter(customThumbs);
		gridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View v, int position, long id){
				ThumbItem clickItem = (ThumbItem) parent.getItemAtPosition(position);
				Toast.makeText(v.getContext() , clickItem.getTitle() + " (id: " + id + ")", Toast.LENGTH_SHORT).show();
			}
		});
       
        return v;
    }
    
    private static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
            int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        
        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }
    
    private static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
    // Raw height and width of image
    final int height = options.outHeight;
    final int width = options.outWidth;
    int inSampleSize = 1;

    if (height > reqHeight || width > reqWidth) {

        final int halfHeight = height / 2;
        final int halfWidth = width / 2;

        // Calculate the largest inSampleSize value that is a power of 2 and keeps both
        // height and width larger than the requested height and width.
        while ((halfHeight / inSampleSize) > reqHeight
                && (halfWidth / inSampleSize) > reqWidth) {
            inSampleSize *= 2;
        }
    }

    return inSampleSize;
}    
    
}