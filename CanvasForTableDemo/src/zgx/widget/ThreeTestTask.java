package zgx.widget;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import android.os.AsyncTask;

public class ThreeTestTask extends AsyncTask<String, String, NurseThreeTestRecord> {
	private static final String TAG = "CoordinatesTest ThreeTestTask.java";

	@Override
	protected NurseThreeTestRecord doInBackground(String... params) {
		URL url;
		try {
			url = new URL(params[0]);
			URLConnection conn = url.openConnection();
			return new NurseThreeTestRecord().domParse(conn.getInputStream());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	protected void onPostExecute(NurseThreeTestRecord result) {
		super.onPostExecute(result);
	}

	@Override
	protected void onProgressUpdate(String... values) {
		super.onProgressUpdate(values);
	}

}
