package org.tribler.tsap.settings;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.tribler.tsap.R;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.text.util.Linkify;
import android.view.Window;
import android.widget.TextView;

/**
 * Dialog showing some useful information about the app
 * 
 * @author Niels Spruit
 * 
 */
public class AboutDialog extends Dialog {

	private static Context mContext = null;

	/**
	 * Constructor
	 * 
	 * @param context
	 *            The context in which the dialog is shown
	 */
	public AboutDialog(Context context) {
		super(context);
		mContext = context;
	}

	/**
	 * This is the standard Android on create method that gets called when the
	 * activity initialized.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.pref_about);
		TextView tv = (TextView) findViewById(R.id.legal_text);
		tv.setText(readRawTextFile(R.raw.legal));
		Linkify.addLinks(tv, Linkify.WEB_URLS);
		tv = (TextView) findViewById(R.id.info_text);
		tv.setText(Html.fromHtml(readRawTextFile(R.raw.info)));
		tv.setLinkTextColor(Color.BLUE);
		Linkify.addLinks(tv, Linkify.WEB_URLS);
	}

	/**
	 * Opens the a text resource file specified by id and reads its contents
	 * 
	 * @param id
	 *            The resource id
	 * @return the contents of the raw text file
	 */
	public static String readRawTextFile(int id) {
		InputStream inputStream = mContext.getResources().openRawResource(id);
		InputStreamReader in = new InputStreamReader(inputStream);
		BufferedReader buf = new BufferedReader(in);
		String line;
		StringBuilder text = new StringBuilder();
		try {
			while ((line = buf.readLine()) != null)
				text.append(line);
		} catch (IOException e) {
			return null;
		}
		return text.toString();
	}

}
