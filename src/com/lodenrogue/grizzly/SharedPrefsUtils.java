package com.lodenrogue.grizzly;

import java.util.Map;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Utility class used to handle writing and reading to Shared Preferences.
 * 
 * @author Miguel Hernandez
 *
 */

public class SharedPrefsUtils {

	private SharedPrefsUtils() {

	}

	/**
	 * Commit the key and value changes to the {@link SharedPreferences}
	 * object. This atomically performs the requested modifications,
	 * replacing whatever is currently in the SharedPreferences.
	 * 
	 * @param context Context used to get the SharedPreferences object.
	 * @param prefFileName Name of the SharedPreferences file to be written.
	 * @param key String key to put in file.
	 * @param value String value to put in file.
	 * @return Returns true if the new value was successfully written to
	 *         persistent storage.
	 */
	public static boolean putString(Context context, String prefFileName, String key, String value) {
		SharedPreferences file = context.getSharedPreferences(prefFileName, 0);
		SharedPreferences.Editor editor = file.edit();
		editor.putString(key, value);

		return editor.commit();
	}

	/**
	 * Retrieve a String value from the preferences.
	 * 
	 * @param context Context used to get the SharedPreferences object.
	 * @param prefFileName Name of the SharedPreferences file to be read.
	 * @param key String key used to find the associated value.
	 * @return Returns the preference value if it exists, or null.
	 */
	public static String getString(Context context, String prefFileName, String key) {
		SharedPreferences file = context.getSharedPreferences(prefFileName, 0);
		return file.getString(key, null);
	}

	/**
	 * Retrieve all values from the preferences file.
	 * 
	 * <p>
	 * Note that you <em>must not</em> modify the collection returned by
	 * this method, or alter any of its contents. The consistency of your
	 * stored data is not guaranteed if you do.
	 * 
	 * @param context Context used to get the SharedPreferences object.
	 * @param prefFileName Name of the SharedPreferences file used to get
	 *                key/value pairs.
	 * @return Returns a map containing a list of pairs key/value
	 *         representing the preferences.
	 */
	public static Map<String, ?> getAll(Context context, String prefFileName) {
		SharedPreferences file = context.getSharedPreferences(prefFileName, 0);
		return file.getAll();
	}

}
