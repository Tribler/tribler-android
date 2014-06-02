package org.tribler.tsap.unpackertest;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

public class MainActivity extends Activity {

    private ResourceManager resourceManager;

	@SuppressLint("NewApi")
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        resourceManager = new ResourceManager(this);
        //setContentView(R.layout.activity_main);
        setContentView(resourceManager.getIdentifier("activity_main", "layout"));

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(resourceManager.getIdentifier("container", "id"), new PlaceholderFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.main, menu);
    	getMenuInflater().inflate(resourceManager.getIdentifier("main", "menu"), menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
//        if (id == R.id.action_settings) {
//            return true;
//        }
        if (id == resourceManager.getIdentifier("action_settings", "id"));
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    @SuppressLint("NewApi")
	public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
//            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        	ResourceManager resManager = new ResourceManager(getActivity());
        	View rootView = inflater.inflate(resManager.getIdentifier("fragment_main", "layout"), container, false);
            return rootView;
        }
    }

}
