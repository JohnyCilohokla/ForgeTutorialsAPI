package com.infernosstudio.json;

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
}
