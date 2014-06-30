package org.renpy.android;

import org.tribler.tsap.MainActivity;
import org.tribler.tsap.settings.Settings;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.os.Process;
import android.util.Log;

public class PythonService extends Service implements Runnable {

	// Thread for Python code
	private Thread pythonThread = null;

	// Python environment variables
	private String androidPrivate;
	private String androidArgument;
	private String pythonHome;
	private String pythonPath;

	// Argument to pass to Python code,
	private String pythonServiceArgument;

	private Notification notification;
	private String serviceTitle;
	private static PythonService pyService;

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		if (pythonThread != null) {
			Log.v("python service", "service exists, do not start again");
			return START_NOT_STICKY;
		}

		pyService = this;
		Bundle extras = intent.getExtras();
		androidPrivate = extras.getString("androidPrivate");
		// service code is located in current directory (not in /service
		// anymore!)
		androidArgument = extras.getString("androidArgument");
		pythonHome = extras.getString("pythonHome");
		pythonPath = extras.getString("pythonPath");
		pythonServiceArgument = extras.getString("pythonServiceArgument");
		serviceTitle = extras.getString("serviceTitle");
		String serviceDescription = extras.getString("serviceDescription");

		pythonThread = new Thread(this);
		pythonThread.start();

		updateNotification(serviceDescription);

		return START_NOT_STICKY;
	}

	private void updateNotification(CharSequence text) {
		Context context = getApplicationContext();
		notification = new Notification(context.getApplicationInfo().icon,
				serviceTitle, System.currentTimeMillis());
		Intent contextIntent = new Intent(context, MainActivity.class);
		PendingIntent pIntent = PendingIntent.getActivity(context, 0,
				contextIntent, PendingIntent.FLAG_UPDATE_CURRENT);
		notification.setLatestEventInfo(context, serviceTitle, text, pIntent);
		startForeground(1, notification);
	}

	public static void updateNotificationText(CharSequence newText) {
		pyService.updateNotification(newText);
	}

	@Override
	public void onDestroy() {
		Log.d("python service", "Service destroyed");
		super.onDestroy();
		pythonThread = null;
		Process.killProcess(Process.myPid());
	}

	@Override
	public void run() {

		// libraries loading
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

		nativeInitJavaEnv();
		nativeSetEnv("ANDROID_SDK", Integer.toString(Build.VERSION.SDK_INT));
		nativeSetEnv(
				"ANDROID_DOWNLOAD_DIRECTORY",
				Environment.getExternalStoragePublicDirectory(
						Environment.DIRECTORY_DOWNLOADS).getAbsolutePath());
		setSettings();
		nativeStart(androidPrivate, androidArgument, pythonHome, pythonPath,
				pythonServiceArgument);
	}

	private void setSettings() {
		Log.e("service", "TRIBLER_SETTING_FAMILYFILTER is: "+Boolean.toString(Settings.getFamilyFilterOn()));
		nativeSetEnv("TRIBLER_SETTING_FAMILYFILTER",
				Boolean.toString(Settings.getFamilyFilterOn()));
		// set max download
		// set max upload
	}

	public static void stop() {
		pyService.stopSelf();
	}

	// Native part (don't remove!)
	public static native void nativeStart(String androidPrivate,
			String androidArgument, String pythonHome, String pythonPath,
			String pythonServiceArgument);

	public static native void nativeInitJavaEnv();

	public static native void nativeSetEnv(String name, String value);

}
