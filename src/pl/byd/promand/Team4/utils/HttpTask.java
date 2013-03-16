package pl.byd.promand.Team4.utils;

import java.io.BufferedInputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.os.AsyncTask;
import android.widget.TextView;

public class HttpTask extends AsyncTask<String, Void, String> {

	private TextView responseView; 

	public TextView getResponseView() {
		return responseView;
	}

	public void setResponseView(TextView responseView) {
		this.responseView = responseView;
	}

	private final static int TIMEOUT_MILLIS = 1000;

	@Override
	protected String doInBackground(String... params) {

		String urlString = params[0];
		HttpURLConnection urlConnection = null;
		String response = "";
		try {
			URL url = new URL(urlString);
			urlConnection = (HttpURLConnection) url.openConnection();
			urlConnection.setConnectTimeout(TIMEOUT_MILLIS);
			urlConnection.setReadTimeout(TIMEOUT_MILLIS);
			Scanner scanner = new Scanner(new BufferedInputStream(
					urlConnection.getInputStream())).useDelimiter("\\A");
			if (scanner.hasNext())
				response = scanner.next();
		} catch (Exception e) {
			response = e.getMessage();
		} finally {
			try {
				if (urlConnection != null)
					urlConnection.disconnect();
			} catch (Exception e) {
				// do nothing
			}
		}
		return response;
	}

	
	@Override
	protected void onPostExecute(String response) {
		super.onPostExecute(response);
		if (responseView == null)
			return; 
		if (response == null || response.length() == 0) {
			responseView.setText("No data received");
			return;
		}
		JSONTokener jsonTokener = new JSONTokener(response);
		String patientName = "";
		String patientSurname = "";
		try {
			JSONObject object = (JSONObject) jsonTokener.nextValue();
//			object = object.getJSONObject("patient");
//			patientName = object.getString("name");
//			patientSurname = object.getString("surname");
		} catch (JSONException e) {
			response = e.getMessage() + " " + response;
		}
		responseView.setText("name = '" + patientName + "', surname = '"
				+ patientSurname + "', response = '" + response + "'");
	}
};