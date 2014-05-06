package org.tribler.tsap;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


public class ThumbAdapter extends ArrayAdapter<ThumbItem> {
	Context context;
	int layoutResourceId;
	ArrayList<ThumbItem> data = new ArrayList<ThumbItem>();
	
	public ThumbAdapter(Context context, int layoutResourceId, ArrayList<ThumbItem> data) {
		super(context, layoutResourceId, data);
		this.layoutResourceId = layoutResourceId;
		this.context = context;
		this.data = data;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		ThumbHolder holder = null;
		
		if (row == null) {
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			row = inflater.inflate(layoutResourceId, parent, false);
			
			holder = new ThumbHolder();
			holder.txtTitle = (TextView) row.findViewById(R.id.ThumbTitle);
			holder.imageItem = (ImageView) row.findViewById(R.id.ThumbImage);
			holder.txtHealth = (TextView) row.findViewById(R.id.ThumbHealth);
			row.setTag(holder);
		} else {
			holder = (ThumbHolder) row.getTag();
		}
		
		ThumbItem item = data.get(position);
		holder.txtTitle.setText(item.getTitle());
		holder.txtHealth.setText("Health: " + item.getHealth().toString());
		holder.imageItem.setImageBitmap(item.getThumbnail());

		return row;
	}

	static class ThumbHolder {
		TextView txtTitle;
		ImageView imageItem;
		TextView txtHealth;
	}
}