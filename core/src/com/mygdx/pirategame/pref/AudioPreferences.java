package com.mygdx.pirategame.pref;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

/**
 * Audio Controls
 * Creates the interface to write and read options
 *
 * @author Faris Alblooki
 * @version 2.0
 */
public class AudioPreferences {

	//Set keys that are stored in the file
	private static final String MUSIC_VOLUME = "volume";
	private static final String MUSIC_ENABLED = "music.enabled";
	private static final String SOUND_ENABLED = "sound.enabled";
	private static final String EFFECT_VOL = "sound";
	private static final String PAGE = "audio";

	/**
	 * Interface to access the file
	 *
	 * @return the preferences of that page
	 */
	protected Preferences optionsObtains() {
		return Gdx.app.getPreferences(PAGE);
	}

	/**
	 * See if effect sound is enabled
	 *
	 * @return the boolean
	 */
	public boolean isEffectsEnabled() {
		return optionsObtains().getBoolean(SOUND_ENABLED, true);
	}

	/**
	 * Sets effects enabled or disabled
	 *
	 * @param EffectsOn The value that needs to be stored.
	 */
	public void setEffectsEnabled(boolean EffectsOn) {
		optionsObtains().putBoolean(SOUND_ENABLED, EffectsOn);
		optionsObtains().flush();
	}

	/**
	 * See if music sound is enabled
	 *
	 * @return the boolean
	 */
	public boolean isMusicEnabled() {
		return optionsObtains().getBoolean(MUSIC_ENABLED, true);
	}

	/**
	 * Sets music enabled or disabled
	 *
	 * @param musicOn value that needs to be stored.
	 */
	public void setMusicEnabled(boolean musicOn) {
		optionsObtains().putBoolean(MUSIC_ENABLED, musicOn);
		optionsObtains().flush();
	}

	/**
	 * Gets music volume.
	 *
	 * @return the music volume
	 */
	public float getMusicVolume() {
		return optionsObtains().getFloat(MUSIC_VOLUME, 0.5f);
	}

	/**
	 * Sets music volume.
	 *
	 * @param volume the volume to set
	 */
	public void setMusicVolume(float volume) {
		optionsObtains().putFloat(MUSIC_VOLUME, volume);
		optionsObtains().flush();
	}

	/**
	 * Gets effects volume.
	 *
	 * @return the effects volume
	 */
	public float getEffectsVolume() {
		return optionsObtains().getFloat(EFFECT_VOL, 0.5f);
	}

	/**
	 * Sets effects volume.
	 *
	 * @param volume the volume to set
	 */
	public void setEffectsVolume(float volume) {
		optionsObtains().putFloat(EFFECT_VOL, volume);
		optionsObtains().flush();
	}
}
