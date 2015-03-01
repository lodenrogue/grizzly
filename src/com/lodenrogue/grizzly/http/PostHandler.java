package com.lodenrogue.grizzly.http;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.SharedPreferences;

import com.lodenrogue.grizzly.http.request.ObjectRequest;
import com.lodenrogue.grizzly.http.request.Request;
import com.lodenrogue.grizzly.http.request.RequestType;
import com.lodenrogue.grizzly.http.request.ResponseRequest;

/**
 * PostHandler handles posting and requesting data to/from http.
 * 
 * @author Miguel Hernandez
 *
 */
public class PostHandler {
	private LinkedHashMap<String, String> response;
	private String rawStringResponse;

	public PostHandler() {
		response = new LinkedHashMap<String, String>();
	}

	/**
	 * Post the {@link NameValuePair} data to the given uri.
	 * 
	 * @param uri String uri address where post is being sent.
	 * @param postData NameValuePair data to be posted.
	 * 
	 * @return Returns http response status code
	 */
	public int post(String uri, List<NameValuePair> postData) {
		return postAndStoreCookies(uri, postData, null, null);
	}

	/**
	 * Post the {@link NameValuePair} data to the given uri and store
	 * cookies. If you have no need to store cookies use post(String uri,
	 * List<NameValuePair> postData) instead.
	 * 
	 * @param context Context used to get {@link SharedPreferences} object
	 *                where cookies are stored.
	 * @param uri String uri address where post is being sent.
	 * @param postData NameValuePair data to be posted.
	 * @param cookiesFileName Name of the file where cookies are to be
	 *                stored.
	 * 
	 * @return Returns http response status code
	 */
	public int postAndStoreCookies(String uri, List<NameValuePair> postData, Context context, String cookiesFileName) {
		HttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(uri);
		HttpResponse httpResponse = null;

		try {
			httpPost.setEntity(new UrlEncodedFormEntity(postData));
			httpResponse = httpClient.execute(httpPost);

			HttpEntity httpEntity = httpResponse.getEntity();
			rawStringResponse = EntityUtils.toString(httpEntity, "UTF-8");

			Header[] headers = httpResponse.getAllHeaders();

			if (cookiesFileName != null && context != null) {
				CookieUtils.storeAllCookies(headers, context, cookiesFileName);
			}

		}
		catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return httpResponse != null ? httpResponse.getStatusLine().getStatusCode() : -1;
	}

	/**
	 * Returns the requested responses as a {@link LinkedHashMap} where the
	 * keys are the {@link ResponseRequest}'s request list.
	 * 
	 */
	public LinkedHashMap<String, String> getResponse(ResponseRequest responseRequest) {
		try {
			JSONObject jsonObject = new JSONObject(rawStringResponse);
			String key = "";
			String value = "";

			for (Request request : responseRequest.getRequests()) {

				if (request.getRequestType().equals(RequestType.BASIC)) {
					value = jsonObject.get(request.getKey()).toString();
					key = request.getKey();
				}
				else if (request.getRequestType().equals(RequestType.OBJECT)) {
					ObjectRequest objectRequestKey = (ObjectRequest) request;
					Request child = objectRequestKey.getChild();

					JSONObject subObject = jsonObject.getJSONObject(objectRequestKey.getKey());

					while (child.getRequestType().equals(RequestType.OBJECT)) {
						objectRequestKey = (ObjectRequest) child;
						child = ((ObjectRequest) child).getChild();

						subObject = subObject.getJSONObject(objectRequestKey.getKey());
					}

					value = subObject.get(child.getKey()).toString();
					key = child.getKey();
				}

				response.put(key, value);
			}
		}
		catch (JSONException e) {
			e.printStackTrace();
		}

		return response;
	}
}