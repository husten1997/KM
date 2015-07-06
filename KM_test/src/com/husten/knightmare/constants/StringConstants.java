package com.husten.knightmare.constants;

public interface StringConstants {

	public interface Material_t {
		public static String WATER = "WATER";
		public static String SAND = "SAND";
		public static String GRAS = "GRAS";
		public static String ROCK = "ROCK";
		public static String MOOR = "MOOR";
		
		public static String IRON = "IRON";
		public static String COAL = "COAL";
	}

	public interface MeshType {
		public static String EINHEIT = "EINHEIT";
		public static String GEBÄUDE = "GEBÄUDE";
		public static String GROUND = "GROUND";
	}

	public interface state {
		public static String NOTHING = "NOTHING";
		public static String S_BUILDINGS = "S_BUILDINGS";
		public static String S_TRUPS = "S_TRUPS";
		public static String N_BUILDINGS = "N_BUILDINGS";
		public static String N_TRUPS = "N_TRUPS";
		public static String ABREIßEN = "Abreißen";
		public static String NF_TROOP = "NF_TROOP";
	}
	
	public interface names{
		public static String R = "Richard Kellnberger";
		public static String M = "Matthias Kettl";
		public static String H = "Julian Dietlmeier";
	}
	
	public static String VERSION = "a0.1.0";

}