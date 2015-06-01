package com.richard.knightmare.sound;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import com.richard.knightmare.util.Loader;

public class MoodMusic {

	private static ArrayList<String> lastPlayed = new ArrayList<>();
	private static MusikPlayer player;
	private static HashMap<String, ArrayList<String>> moodMusic = new HashMap<>();
	private static float volume;
	private static String mood;
	private static Timer timer;

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
		volume = Float.parseFloat(Loader.getCfgValue("Volume"));
		player.setVolume(volume);
		long duration = player.start();
		timer = new Timer(true);
		timer.schedule(new TimerTask() {

			@Override
			public void run() {
				setMood(mood);
			}
		}, duration * 1000);
	}

	public static void init(String startinMood) {
		volume = Float.parseFloat(Loader.getCfgValue("Volume"));
		String[] list = Loader.getMusicList();
		for (int i = 0; i < list.length; i++) {
			addClipToMood("Default", list[i]);
		}
		mood = startinMood;
		setMood(mood);
	}

	public static void setMoodToDefault() {
		changeMood("Default");
	}

	public static void changeMood(String mood) {
		if (!mood.equals(MoodMusic.mood)) {
			new Timer(true).schedule(new TimerTask() {

				@Override
				public void run() {
					ausblenden();
					timer.cancel();
					MoodMusic.mood = mood;
					setMood(mood);
				}
			}, 0);
		}
	}

	public static void nextClip() {
		new Timer(true).schedule(new TimerTask() {

			@Override
			public void run() {
				ausblenden();
				setMood(mood);
			}
		}, 0);
	}

	private static void ausblenden() {
		for (float i = volume; i > -50; i--) {
			player.changeVolume(-1.0f);
			try {
				TimeUnit.MILLISECONDS.sleep(100);
			} catch (InterruptedException e) {
				// Let's just pretend that everything is fine
			}
		}
		player.stop();
	}

	public static float getVolume() {
		return volume;
	}

	public static void abwürgen() {
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
		Loader.changeCfgValue("Volume", String.valueOf(volume));
		player.setVolume(volume);
	}

	public static void setVolume(Float set) {
		volume = set;
		Loader.changeCfgValue("Volume", String.valueOf(volume));
		player.setVolume(set);
	}
}
