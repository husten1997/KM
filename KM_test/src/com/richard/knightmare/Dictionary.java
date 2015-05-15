package com.richard.knightmare;

import java.util.HashMap;

public class Dictionary {

	private static HashMap<String, String> entries = new HashMap<>();
	
	public static void addEntry(String shortName, String fullName){
		entries.put(shortName, fullName);
	}
	
	public static String getFullName(String shortName){
		return entries.get(shortName);
	}
}
