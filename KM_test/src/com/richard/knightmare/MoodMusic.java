package com.richard.knightmare;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class MoodMusic {

	private static String[] lastPlayed = new String[3];
	private static MusikPlayer player;
	private static HashMap<String, ArrayList<String>> moodMusic = new HashMap<>();
	private static float volume;

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
			index = (int) (Math.random() * names.size());
			if (names.size() < 4) {
				break;
			}
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
		Timer timer = new Timer(true);
		timer.schedule(new TimerTask() {

			@Override
			public void run() {
				try {
					TimeUnit.SECONDS.sleep(duration);
				} catch (InterruptedException e) {
					// Ignore
				}
				changeMood(mood);
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
		setMood("Default");
	}

	public static void changeMood(String mood) {
		player.stop();
		setMood(mood);
	}

	public void changeVolume(Float change) {
		volume += change;
		player.setVolume(volume);
	}
}
