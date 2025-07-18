package org.mrbag.ProxyController.Utils;

import java.util.HashMap;
import java.util.Map;

public class UtilParseToken {

	public static Map<String, String> parseToMap(String str){
		if (str == null || str.isBlank() || str.isEmpty())
			return null;
		HashMap<String, String> map = new HashMap<String, String>();
		
		for (String s : str.split("&")) {
			String[] ss = s.split("=");
			if (ss.length > 1)
				map.put(ss[0], ss[1]);
		}
		
		return map;
	}
	
	public static String getOrNull(String id, Map<String, String> map) {
		if (map == null || map.isEmpty())
			return null;
		return map.getOrDefault(id, null);
	}

	public static String getToken(String query) {
		return getOrNull("token", parseToMap(query));
	}
	
}
