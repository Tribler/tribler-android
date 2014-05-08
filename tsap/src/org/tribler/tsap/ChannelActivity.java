package org.tribler.tsap;
 
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
 
public class ChannelActivity extends Activity{
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.channel_activity_view);
         
        TextView txtProduct = (TextView) findViewById(R.id.product_label);
         
        Intent i = getIntent();
        // getting attached intent data
        String product = i.getStringExtra("product");
        // displaying selected product name
        setTitle(product);
    }
}