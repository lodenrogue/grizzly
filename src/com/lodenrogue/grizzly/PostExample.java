package com.lodenrogue.grizzly;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;

import com.lodenrogue.grizzly.http.PostHandler;
import com.lodenrogue.grizzly.http.request.BasicRequest;
import com.lodenrogue.grizzly.http.request.RequestType;
import com.lodenrogue.grizzly.http.request.ResponseRequest;

public class PostExample extends ActionBarActivity {

	private static final String POST_URL = "http://website.com/post";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.post_example);

		// Create an AsyncTask subclass to post data.
		class PostAsyncTask extends AsyncTask<String, Void, String> {

			// Override doInBackground(...)
			@Override
			protected String doInBackground(String... params) {

				// Get the data you want to post
				String userName = params[0];
				String password = params[1];

				// Add it to a NameValuePair list
				List<NameValuePair> postData = new ArrayList<NameValuePair>();
				postData.add(new BasicNameValuePair("userName", userName));
				postData.add(new BasicNameValuePair("password", password));

				// Post the data and get the response code
				PostHandler postHandler = new PostHandler();
				int responseCode = postHandler.postAndStoreCookies(POST_URL, postData, getApplicationContext(), SharedFileNames.COOKIE_STORE);

				// Use the response code however you wish
				Log.d("responseCode", String.valueOf(responseCode));

				// Build a response request
				ResponseRequest responseRequest = new ResponseRequest();
				responseRequest.addRequest(new BasicRequest("userName", RequestType.BASIC));
				responseRequest.addRequest(new BasicRequest("sessionId", RequestType.BASIC));

				// Get the response
				LinkedHashMap<String, String> response = postHandler.getResponse(responseRequest);

				// Use the response however you wish
				for (String key : response.keySet()) {
					Log.d(key, response.get(key));
				}

				// return some value
				return "done";
			}
		}

		// execute the AsyncTask
		new PostAsyncTask().execute("user1", "userPassword");
	}
}
