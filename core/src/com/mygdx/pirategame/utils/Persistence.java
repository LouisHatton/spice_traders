package com.mygdx.pirategame.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

import java.util.HashMap;
import java.util.Map;

public class Persistence {

	private final Preferences storage;

	private static Persistence INSTANCE;

	/**
	 * Allows us to ensure Persistence is a Singleton class.
	 *
	 * @return Instance of Persistence Singleton.
	 */
	public static Persistence get() {
		if (INSTANCE == null) {
			INSTANCE = new Persistence();
		}

		return INSTANCE;
	}

	private Persistence() {
		this.storage = Gdx.app.getPreferences("pirates_data");
	}

	/**
	 * @param key Key you wish to retrieve.
	 * @return Value attributed to that key.
	 */
	public int getInt(String key) {
		return this.storage.getInteger(key);
	}

	/**
	 * @param key Key you wish to retrieve.
	 * @return Value attributed to that key.
	 */
	public boolean getBool(String key) {
		return this.storage.getBoolean(key);
	}

	public String getString(String key) {
		return this.storage.getString(key);
	}

	/**
	 * @param key Key you wish to retrieve.
	 * @return Value attributed to that key.
	 */
	public float getFloat(String key) {
		return this.storage.getFloat(key);
	}

	/**
	 * @param key Key you wish to retrieve.
	 * @return Value attributed to that key.
	 */
	public Object get(String key) {
		return this.storage.get().getOrDefault(key, null);
	}

	/**
	 * @param key Key you wish to set.
	 * @param data Data to attribute to the key.
	 */
	public <T> void set(String key, T data) {
		Map<String, T> formatted = new HashMap<>();
		formatted.put(key, data);

		this.storage.put(formatted);
		this.storage.flush();
	}

	/**
	 * @param key Key you wish to check
	 * @return Whether that key has been defined.
	 */
	public boolean isSet(String key) {
		return this.storage.contains(key);
	}

	/**
	 * Beware! Any changes to this map outside the Persistence class may cause issues.
	 *
	 * @return The cache used for Key/Value retrieval.
	 */
	public Map<String, ?> raw() {
		return this.storage.get();
	}

	/**
	 * Flush all data from the cache.
	 */
	public void reset() {
		this.storage.clear();
		this.storage.flush();
	}
}