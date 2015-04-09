/*
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * Please send inquiries to huber AT ut DOT ee
 *
 * author Huber Flores
 * in-mobile, 2014
 */

package com.in.mobile.manager.adfile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import com.in.mobile.database.adcontainer.DatabaseHandler;
import com.in.mobile.gesture.ad.AdContentLoader;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

public class FileDownloader extends AsyncTask<String, Void, String> {

	Context context;

	private String fileSdcardPath;

	DatabaseHandler dataAds;

	public FileDownloader(Context context) {
		this.context = context;
		dataAds = DatabaseHandler.getInstance();
		dataAds.setContext(context);
	}

	@Override
	protected String doInBackground(String... urls) {
		Log.e("FileDownloader", "doInBackground called");
		String response = "";
		for (String pathUrl : urls) {
			if (pathUrl != null) {
				Log.e("FileDownloader", pathUrl);
			} else {
				Log.e("FileDownloader", "pathUrl is null");
			}

			try {
				URL url = new URL(pathUrl);
				HttpURLConnection urlConnection = (HttpURLConnection) url
						.openConnection();
				urlConnection.setRequestMethod("GET");
				urlConnection.setDoOutput(true);
				urlConnection.connect();

				File SDCardRoot = Environment.getExternalStorageDirectory();
				File file = new File(SDCardRoot, "ad.jpg");

				FileOutputStream fileOutput = new FileOutputStream(file);
				InputStream inputStream = urlConnection.getInputStream();

				byte[] buffer = new byte[1024];
				int bufferLength = 0;

				while ((bufferLength = inputStream.read(buffer)) > 0) {
					fileOutput.write(buffer, 0, bufferLength);
				}

				fileSdcardPath = file.getAbsolutePath();
				fileOutput.close();

				dataAds.getInstance().getDatabaseManager()
						.saveData(fileSdcardPath, pathUrl, 1.0f, 1.0f);
			} catch (MalformedURLException e) {
				Log.e("FileDownloader", e.toString());
			} catch (IOException e) {
				Log.e("FileDownloader", e.toString());
			}
		}

		return response;
	}

	@Override
	protected void onPostExecute(String result) {
		Toast.makeText(context, "File downloaded", Toast.LENGTH_SHORT).show();
		AdContentLoader.adUpdate(fileSdcardPath);
		// AdContentLoader.adUpdate("ad_image.png");
	}

	public String getFileSdcardPath() {
		return this.fileSdcardPath;
	}
}