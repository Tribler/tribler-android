package org.tribler.tsap.python;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

import org.tribler.tsap.MainActivity;
import org.tribler.tsap.R;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

public class PythonActivity extends Activity implements Runnable {
	private static String TAG = "PythonActivity";
	public static ApplicationInfo mInfo = null;

	// Did we launch our thread?
	private boolean mLaunchedThread = false;

	// The path to the directory containing our external storage.
	private File externalStorage;

	// The path to the directory containing the python code.
	private File mPath = null;

	// Intent used to start the PythonService (needed for stopping the service)
	private Intent serviceIntent = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		externalStorage = new File(Environment.getExternalStorageDirectory(),
				getPackageName());

		// Figure out the directory where the game is. If the game was
		// given to us via an intent, then we use the scheme-specific
		// part of that intent to determine the file to launch. We
		// also use the android.txt file to determine the orientation.
		//
		// Otherwise, we use the public data, if we have it, or the
		// private data if we do not.
		if (getIntent() != null && getIntent().getAction() != null
				&& getIntent().getAction().equals("org.renpy.LAUNCH")) {
			mPath = new File(getIntent().getData().getSchemeSpecificPart());
		} else if (getString(R.string.public_version) != null) {
			mPath = externalStorage;
		} else {
			mPath = getFilesDir();
		}

		super.setContentView(R.layout.activity_python);
	}

	/**
	 * Show an error using a toast. (Only makes sense from non-UI threads.)
	 */
	public void toastError(final String msg) {

		final Activity thisActivity = this;

		runOnUiThread(new Runnable() {
			public void run() {
				Toast.makeText(thisActivity, msg, Toast.LENGTH_LONG).show();
			}
		});

		// Wait to show the error.
		synchronized (this) {
			try {
				this.wait(1000);
			} catch (InterruptedException e) {
			}
		}
	}

	public void recursiveDelete(File f) {
		if (f.isDirectory()) {
			for (File r : f.listFiles()) {
				recursiveDelete(r);
			}
		}
		f.delete();
	}

	/**
	 * This determines if unpacking one the zip files included in the .apk is
	 * necessary. If it is, the zip file is unpacked.
	 */
	public void unpackData(final String resource, File target) {
		int resId = 0;
		if (resource == "public")
			resId = R.string.public_version;
		else if (resource == "private")
			resId = R.string.private_version;

		// The version of data in memory and on disk.
		String data_version = getString(resId);
		String disk_version = null;

		// If no version, no unpacking is necessary.
		if (data_version == null) {
			return;
		}

		// Check the current disk version, if any.
		String filesDir = target.getAbsolutePath();
		String disk_version_fn = filesDir + "/" + resource + ".version";

		try {
			byte buf[] = new byte[64];
			InputStream is = new FileInputStream(disk_version_fn);
			int len = is.read(buf);
			disk_version = new String(buf, 0, len);
			is.close();
		} catch (Exception e) {
			disk_version = "";
		}

		// If the disk data is out of date, extract it and write the
		// version file.
		if (!data_version.equals(disk_version)) {
			Log.v(TAG, "Extracting " + resource + " assets.");

			recursiveDelete(target);
			target.mkdirs();

			AssetExtract ae = new AssetExtract(this);
			if (!ae.extractTar(resource + ".mp3", target.getAbsolutePath())) {
				toastError("Could not extract " + resource + " data.");
			}

			try {
				// Write .nomedia.
				new File(target, ".nomedia").createNewFile();

				// Write version file.
				FileOutputStream os = new FileOutputStream(disk_version_fn);
				os.write(data_version.getBytes());
				os.close();
			} catch (Exception e) {
				Log.w("python", e);
			}
		}
	}

	public void run() {
		unpackData("private", getFilesDir());
		unpackData("public", externalStorage);

		System.loadLibrary("sdl");
		System.loadLibrary("sdl_image");
		System.loadLibrary("sdl_ttf");
		System.loadLibrary("sdl_mixer");
		System.loadLibrary("python2.7");
		System.loadLibrary("application");
		System.loadLibrary("sdl_main");

		System.load(getFilesDir() + "/lib/python2.7/lib-dynload/_io.so");
		System.load(getFilesDir() + "/lib/python2.7/lib-dynload/unicodedata.so");

		try {
			System.loadLibrary("sqlite3");
			System.load(getFilesDir()
					+ "/lib/python2.7/lib-dynload/_sqlite3.so");
		} catch (UnsatisfiedLinkError e) {
		}

		try {
			System.load(getFilesDir()
					+ "/lib/python2.7/lib-dynload/_imaging.so");
			System.load(getFilesDir()
					+ "/lib/python2.7/lib-dynload/_imagingft.so");
			System.load(getFilesDir()
					+ "/lib/python2.7/lib-dynload/_imagingmath.so");
		} catch (UnsatisfiedLinkError e) {
		}

		start_service("PythonService", "Service running python code", "");
		Log.d(TAG, "Service started");
		startActivity(new Intent(this, MainActivity.class));
	}

	@Override
	protected void onResume() {
		super.onResume();

		if (!mLaunchedThread) {
			mLaunchedThread = true;
			new Thread(this).start();
		}
	}

	protected void onDestroy() {
		Log.d(TAG, "Stopping service...");
		stopService(serviceIntent);
		Log.d(TAG, "Service stopped");

		System.exit(0);
	}

	public void start_service(String serviceTitle, String serviceDescription,
			String pythonServiceArgument) {
		Log.d(TAG, "Starting python service...");
		serviceIntent = new Intent(this, PythonService.class);
		String argument = getFilesDir().getAbsolutePath();
		String filesDirectory = mPath.getAbsolutePath();
		serviceIntent.putExtra("androidPrivate", argument);
		serviceIntent.putExtra("androidArgument", filesDirectory);
		serviceIntent.putExtra("pythonHome", argument);
		serviceIntent.putExtra("pythonPath", argument + ":" + filesDirectory
				+ "/lib");
		serviceIntent.putExtra("serviceTitle", serviceTitle);
		serviceIntent.putExtra("serviceDescription", serviceDescription);
		serviceIntent.putExtra("pythonServiceArgument", pythonServiceArgument);
		startService(serviceIntent);
	}
}
