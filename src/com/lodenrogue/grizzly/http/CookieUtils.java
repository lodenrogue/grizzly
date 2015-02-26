package com.lodenrogue.grizzly.http;

import org.apache.http.Header;

import android.content.Context;
import android.content.SharedPreferences;

import com.lodenrogue.grizzly.SharedPrefsUtils;

/**
 * Utility class used to handle cookie tasks.
 * 
 * @author Miguel Hernandez
 *
 */

public class CookieUtils {

	private CookieUtils() {

	}

	/**
	 * Retrieves cookie data from a {@link Header} object and writes a
	 * key/value pair to a {@link SharedPreferences} file. This atomically
	 * performs the requested modifications, replacing whatever is currently
	 * in the SharedPreferences file.
	 * 
	 * @param header Header object containing the data to be written to
	 *                file.
	 * @param context Context used to get SharedPreferences object.
	 * @param cookiesFileName The name of the file where key/value pair is
	 *                to be written.
	 * @return Returns true if the new value was successfully written to
	 *         persistent storage.
	 */
	public static boolean storeCookie(Header header, Context context, String cookiesFileName) {

		String hString = header.toString();
		boolean success = true;

		if (hString.startsWith("Set-Cookie:")) {

			int indexOfKey = hString.indexOf(": ") + 2;
			int indexOfEquals = hString.indexOf("=");

			String key = hString.substring(indexOfKey, indexOfEquals);
			String value = hString.substring(indexOfEquals + 1);

			success = SharedPrefsUtils.putString(context, cookiesFileName, key, value);
		}

		return success;
	}

	/**
	 * Retrieves cookie data from a {@link Header} array and writes all
	 * cookie key/value pairs to a {@link SharedPreferences} file. This
	 * atomically performs the requested modifications, replacing whatever
	 * is currently in the SharedPreferences file.
	 * 
	 * @param headers Header array containing the data to be written to
	 *                file.
	 * @param context Context used to get SharedPreferences object.
	 * @param cookiesFileName The name of the file where key/value pairs are
	 *                to be written.
	 * @return Returns true if the new values were successfully written to
	 *         persistent storage.
	 */
	public static boolean storeAllCookies(Header[] headers, Context context, String cookiesFileName) {

		for (Header header : headers) {
			boolean stored = CookieUtils.storeCookie(header, context, cookiesFileName);

			if (stored == false) {
				return false;
			}
		}
		return true;
	}
}
