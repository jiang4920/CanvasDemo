package zgx.widget;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import android.os.AsyncTask;

public class ThreeTestTask extends
		AsyncTask<String, String, NurseThreeTestRecord> {
	private static final String TAG = "CoordinatesTest ThreeTestTask.java";

	@Override
	protected NurseThreeTestRecord doInBackground(String... params) {
		return new NurseThreeTestRecord().domParse(params[0]);
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
