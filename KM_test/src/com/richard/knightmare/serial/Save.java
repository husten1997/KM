package com.richard.knightmare.serial;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Save {

	public static boolean save(String path, Object obj){
		try {
			FileOutputStream fileOut = new FileOutputStream(new File(path));
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(obj);
			out.close();
			fileOut.close();
			return true;
		} catch (IOException e) {
			return false;
		}	
	}
	
	public static Object load(String path){
		try {
			FileInputStream fileIn = new FileInputStream(new File(path));
			ObjectInputStream in = new ObjectInputStream(fileIn);
			Object obj = in.readObject();
			in.close();
			fileIn.close();
			return obj;
		} catch (IOException | ClassNotFoundException e) {
			return null;
		}
	}
}
