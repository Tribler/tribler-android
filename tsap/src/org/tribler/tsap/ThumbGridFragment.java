package org.tribler.tsap;

import java.util.ArrayList;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Fragment;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
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
        
    	//set grid view item
    	Bitmap bmSintel = BitmapFactory.decodeResource(this.getResources(), R.drawable.sintel);
    	Bitmap bmAvatar = BitmapFactory.decodeResource(this.getResources(), R.drawable.avatar);
    	Bitmap bmLOTR = BitmapFactory.decodeResource(this.getResources(), R.drawable.lotr);
    	Bitmap bmTuckerdale = BitmapFactory.decodeResource(this.getResources(), R.drawable.tuckerdale);

    	for(int i = 0; i < 11; i++)
    	{
    		gridArray.add(new ThumbItem("Sintel", bmSintel, ThumbItem.TORRENT_HEALTH.GREEN, 500));
    		gridArray.add(new ThumbItem("Avatar", bmAvatar, ThumbItem.TORRENT_HEALTH.RED, 423));
    		gridArray.add(new ThumbItem("Lord of the Rings", bmLOTR, ThumbItem.TORRENT_HEALTH.YELLOW, 4321));
    		gridArray.add(new ThumbItem("Tucker and Dale vs. Evil", bmTuckerdale, ThumbItem.TORRENT_HEALTH.UNKNOWN, 12353));
    	}
		
		ThumbAdapter customThumbs = new ThumbAdapter(container.getContext(), R.layout.thumb_item, gridArray);
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

}