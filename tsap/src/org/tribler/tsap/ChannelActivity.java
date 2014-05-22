package org.tribler.tsap;
 
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
 
/**
 * Activity that is started when a channel within the channel list fragment is clicked
 * @author Dirk Schut
 */
public class ChannelActivity extends Activity{
	
	/**
	 * OnCreate callback: sets the content view and sets the title to the values passed in with the intent
	 */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.channel_activity_view);         
        Intent i = getIntent();
        String product = i.getStringExtra("product");
        setTitle(product);
    }
}