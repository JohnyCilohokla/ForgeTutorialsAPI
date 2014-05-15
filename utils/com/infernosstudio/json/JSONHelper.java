package com.infernosstudio.json;

import java.text.NumberFormat;

import org.json.JSONObject;

import com.infernosstudio.johny.JCUtils;

public class JSONHelper {

	public static String getFromPath(JSONObject object, String def, String... path) {
		for (int i = 0; i < path.length; i++) {
			if (object.has(path[i])) {
				if (i == (path.length - 1)) {
					return object.getString(path[i]);
				} else {
					object = object.getJSONObject(path[i]);
				}
			} else {
				return def;
			}
		}
		return def;
	}

	public static String getFromStringPath(JSONObject object, String def, String path, String seperator) {
		return JSONHelper.getFromPath(object, def, JCUtils.split(path, seperator));
	}

	public static String getOrSet(JSONObject jsonObject, String key, String defaultVal) {
		String value;
		if (jsonObject.has(key)) {
			value = jsonObject.getString(key);
		} else {
			value = defaultVal;
			jsonObject.put(key, defaultVal);
		}
		return value;
	}

	public static String getOrSet(JSONObject jsonObject, String key, int defaultVal, NumberFormat format) {
		String value;
		if (jsonObject.has(key)) {
			value = jsonObject.getString(key);
		} else {
			value = "" + defaultVal;
			jsonObject.put(key, value);
		}
		return format.format(JSONHelper.toInt(value, defaultVal));
	}

	public static String getOrSet(JSONObject jsonObject, String key, int defaultVal, NumberFormat format, int increment) {
		String value;
		if (jsonObject.has(key)) {
			value = "" + (JSONHelper.toInt(jsonObject.getString(key), defaultVal) + increment);
			if (increment != 0) {
				jsonObject.put(key, value);
			}
		} else {
			value = "" + defaultVal;
			jsonObject.put(key, value);
		}
		return format.format(JSONHelper.toInt(value, defaultVal));
	}

	public static Integer toInt(String string, Integer def) {
		int out;
		try {
			out = Integer.parseInt(string);
			return out;
		} catch (Exception e) {
			return def;
		}
	}

	public static boolean getOrSet(JSONObject jsonObject, String key, boolean defaultVal) {
		boolean value;
		if (jsonObject.has(key)) {
			value = jsonObject.getBoolean(key);
		} else {
			value = defaultVal;
			jsonObject.put(key, defaultVal);
		}
		return value;
	}
}
