package org.tribler.tsap;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Observable;
import java.util.Observer;
import java.util.Timer;
import java.util.TimerTask;

import org.renpy.android.PythonService;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.util.Log;

/**
 * Class that creates a Dialog that asks the user whether he wants to shutdown
 * the app. When he 'says' yes, Tribler is shut down and afterwards the app is
 * closed.
 * 
 * @author Niels Spruit
 * 
 */
public class OnQuitDialog extends AlertDialog implements OnClickListener,
		Observer {

	private final int MAX_SHUTDOWN_TIMEOUT = 7; // in seconds
	
	private Context mContext;

	/**
	 * Constructor: set the properties of the dialog, which is shown afterwards
	 * 
	 * @param context
	 *            The context of this dialog
	 */
	public OnQuitDialog(Context context) {
		super(context);
		mContext = context;
		setTitle(context.getString(R.string.on_quit_title));
		setMessage(context.getString(R.string.on_quit_initial_message));
		setButton(BUTTON_POSITIVE, "Yes", this);
		setButton(BUTTON_NEGATIVE, "No", this);
	}

	/**
	 * When the yes or no button is clicked, this method gets called. When the
	 * yes button is pressed, a new dialog is shown and an XML-RPC call to
	 * shutdown Tribler is performed.
	 */
	@Override
	public void onClick(DialogInterface arg0, int buttonClicked) {
		if (buttonClicked == BUTTON_POSITIVE) {
			// show new non-cancelable dialog
			AlertDialog shutdownProgress = new AlertDialog.Builder(mContext)
					.setMessage(mContext.getString(R.string.on_quit_shutdown_message))
					.setCancelable(false)
					.setTitle(mContext.getString(R.string.on_quit_title))
					.show();
			
			// perform XML-RPC call to shutdown tribler, which notifies this
			// dialog as soon as shutdown is done
			try {
				URL url = new URL("http://127.0.0.1:8000/tribler");
				new XMLRPCShutdownManager(url, this).shutdown();
			} catch (MalformedURLException e) {
				Log.e("OnQuitDialog", "URL was malformed:\n");
				e.printStackTrace();
			}
			
			// To prevent the app from hanging, force a shutdown after
			// MAX_SHUTDOWN_TIMEOUT seconds
			Timer timeoutTimer = new Timer();
			timeoutTimer.schedule(new TimerTask() {
				@Override
				public void run()
				{
					Log.w("XMLRPCShutdownManager", "Tribler took too long to close, shutting down anyway..");
					shutdownService();
				}
			}, MAX_SHUTDOWN_TIMEOUT * 1000);
		}
	}

	/**
	 * Shuts down the Tribler service and this app (when the XML-RPC call
	 * returns)
	 */
	@Override
	public void update(Observable observable, Object data) {
		shutdownService();
	}
	
	private void shutdownService()
	{
		Intent startMain = new Intent(Intent.ACTION_MAIN);
		startMain.addCategory(Intent.CATEGORY_HOME);
		startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		mContext.startActivity(startMain);
		PythonService.stop();
		((MainActivity) mContext).finish();	
	}

}
