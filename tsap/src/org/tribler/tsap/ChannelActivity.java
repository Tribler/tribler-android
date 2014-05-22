package org.tribler.tsap;
 
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
 
public class ChannelActivity extends Activity{
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.channel_activity_view);         
        Intent i = getIntent();
        // getting attached izntent data
        String product = i.getStringExtra("product");
        // displaying selected product name
        setTitle(product);
    }
}