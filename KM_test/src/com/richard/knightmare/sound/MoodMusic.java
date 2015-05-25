package com.richard.knightmare.sound;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import com.richard.knightmare.util.Environment;

public class MoodMusic {

	private static String[] lastPlayed = new String[3];
	private static MusikPlayer player;
	private static HashMap<String, ArrayList<String>> moodMusic = new HashMap<>();
	private static float volume;
	private static String mood;

	public static void addMood(String name) {
		if (!moodMusic.containsKey(name)) {
			moodMusic.put(name, new ArrayList<>());
		}
	}

	public static void addClipToMood(String mood, String music) {
		if (!moodMusic.containsKey(mood)) {
			addMood(mood);
		}
		if (!moodMusic.get(mood).contains(music)) {
			moodMusic.get(mood).add(music);
		}
	}

	private static void setMood(String mood) {
		ArrayList<String> names = moodMusic.get(mood);

		int index = (int) (Math.random() * names.size());

		while (!((!names.get(index).equals(lastPlayed[0])) && (!names.get(index).equals(lastPlayed[1])) && (!names.get(index).equals(lastPlayed[2])))) {
			if (names.size() < 4) {
				break;
			}
			index = (int) (Math.random() * names.size());
		}

		String name = names.get(index);

		if (lastPlayed[0] == null) {
			lastPlayed[0] = name;
		} else if (lastPlayed[1] == null) {
			lastPlayed[1] = name;
		} else if (lastPlayed[2] == null) {
			lastPlayed[2] = name;
		} else {
			for (int i = 0; i < 2; i++) {
				lastPlayed[i] = lastPlayed[i + 1];
			}
			lastPlayed[2] = name;
		}
		player = new MusikPlayer(name);
		player.setVolume(volume);
		long duration = player.start();
		new Timer(true).schedule(new TimerTask() {

			@Override
			public void run() {
				try {
					TimeUnit.SECONDS.sleep(duration);
				} catch (InterruptedException e) {
					// Ignore
				}
				if (mood.equals(MoodMusic.mood)) {
					changeMood(mood);
				}
			}
		}, 0);
	}

	public static void init() {
		String[] list = Environment.getMusicList();
		for (int i = 0; i < list.length; i++) {
			addClipToMood("Default", list[i]);
		}
		setMoodToDefault();
	}

	public static void setMoodToDefault() {
		mood = "Default";
		setMood(mood);
	}

	public static void changeMood(String mood) {
		if (!mood.equals(MoodMusic.mood)) {
			ausblenden();
			MoodMusic.mood = mood;
			setMood(mood);
		}
	}
	
	public static void nextClip(){
		ausblenden();
		setMood(mood);
	}
	
	private static void ausblenden(){
		for (float i = volume; i > -30; i--) {
			player.changeVolume(-1.0f);
			try {
				TimeUnit.MILLISECONDS.sleep(100);
			} catch (InterruptedException e) {
				// Let's just pretend that everything is fine
			}
		}
		player.stop();
	}

	public static void changeVolume(Float change) {
		volume += change;
		player.setVolume(volume);
	}
}
