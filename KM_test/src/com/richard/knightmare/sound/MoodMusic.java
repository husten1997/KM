package com.richard.knightmare.sound;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import com.richard.knightmare.util.Environment;

public class MoodMusic {

	private static ArrayList<String> lastPlayed = new ArrayList<>();
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
		editListLenght(names.size());
		int index = (int) (Math.random() * names.size());
		while (isPlayed(names.get(index))) {
			index = (int) (Math.random() * names.size());
		}
		setPlayed(names.get(index), names.size());
		player = new MusikPlayer(names.get(index));
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

	public static void nextClip() {
		ausblenden();
		setMood(mood);
	}

	private static void ausblenden() {
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

	private static void setPlayed(String name, int clipcount) {
		lastPlayed.add(name);
		if (lastPlayed.size() > (int) (clipcount / 2)) {
			lastPlayed.remove(0);
		}
	}

	private static void editListLenght(int clipcount) {
		while (lastPlayed.size() > (int) (clipcount / 2)) {
			lastPlayed.remove(0);
		}
	}

	private static boolean isPlayed(String name) {
		return lastPlayed.contains(name);
	}

	public static void changeVolume(Float change) {
		volume += change;
		player.setVolume(volume);
	}
}
