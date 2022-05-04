package com.mygdx.pirategame.entity.college;

import com.mygdx.pirategame.PirateGame;

/**
 * Define each College and the appropriate attributes that belong to them.
 *
 * @author Faris Alblooki
 * @version 2.0
 */
public enum CollegeType {

	ALCUIN("Alcuin", "alcuin_ship.png", "alcuin_flag.png", 1900 / PirateGame.PPM, 2100 / PirateGame.PPM, 0),
	ANNE_LISTER("Anne Lister", "anne_lister_ship.png", "anne_lister_flag.png", 6304 / PirateGame.PPM, 1199 / PirateGame.PPM, 8),
	CONSTANTINE("Constantine", "constantine_ship.png", "constantine_flag.png", 6240 / PirateGame.PPM, 6703 / PirateGame.PPM, 8),
	GOODRICKE("Goodricke", "goodricke_ship.png", "goodricke_flag.png", 1760 / PirateGame.PPM, 6767 / PirateGame.PPM, 8);

	private final String friendlyName;
	private String shipTexture;
	private final String flagTexture;
	private final float x;
	private final float y;
	private int shipSpawns;

	/**
	 * Define a new College.
	 *
	 * @param friendlyName Name to be shown to the player.
	 * @param shipTexture Texture to be used for the ship.
	 * @param flagTexture Texture to be used for the college flag.
	 * @param x Static X position of the college
	 * @param y Static Y position of the college.
	 * @param shipSpawns How many ships should spawn associated with this college.
	 */
	CollegeType(String friendlyName, String shipTexture, String flagTexture, float x, float y, int shipSpawns) {
		this.friendlyName = friendlyName;
		this.shipTexture = shipTexture;
		this.flagTexture = flagTexture;
		this.x = x;
		this.y = y;
		this.shipSpawns = shipSpawns;
	}

	/**
	 * @return Friendly name of the college.
	 */
	public String getName() {
		return friendlyName;
	}

	/**
	 * @return Ship texture of the college.
	 */
	public String getShipTexture() {
		return shipTexture;
	}

	/**
	 * @return Flag texture of the college.
	 */
	public String getFlagTexture() {
		return flagTexture;
	}

	/**
	 * @return X coordinate of the college.
	 */
	public float getX() {
		return x;
	}

	/**
	 * @return Y coordinate of the college.
	 */
	public float getY() {
		return y;
	}

	/**
	 * @return How many ships should spawn to defend the college.
	 */
	public int getShipSpawns() {
		return shipSpawns;
	}
}