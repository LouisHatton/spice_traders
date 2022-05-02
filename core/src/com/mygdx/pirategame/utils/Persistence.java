package com.mygdx.pirategame.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

import java.util.HashMap;
import java.util.Map;

public class Persistence {

	private final Preferences storage;

	private static Persistence INSTANCE;

	public static Persistence get() {
		if (INSTANCE == null) {
			INSTANCE = new Persistence();
		}

		return INSTANCE;
	}

	private Persistence() {
		this.storage = Gdx.app.getPreferences("pirates_data");
	}

	public int getInt(String key) {
		return this.storage.getInteger(key);
	}

	public boolean getBool(String key) {
		return this.storage.getBoolean(key);
	}


	public float getFloat(String key) {
		return this.storage.getFloat(key);
	}

	public Object get(String key) {
		return this.storage.get().getOrDefault(key, null);
	}

	public <T> void set(String key, T data) {
		Map<String, T> formatted = new HashMap<>();
		formatted.put(key, data);

		this.storage.put(formatted);
		this.storage.flush();
	}

	public boolean isSet(String key) {
		return this.storage.contains(key);
	}

	public Map<String, ?> raw() {
		return this.storage.get();
	}

	public void reset() {
		this.storage.clear();
		this.storage.flush();
	}
}