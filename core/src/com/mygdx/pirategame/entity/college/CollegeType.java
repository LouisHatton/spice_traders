package com.mygdx.pirategame.entity.college;

import com.mygdx.pirategame.PirateGame;

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

	CollegeType(String friendlyName, String shipTexture, String flagTexture, float x, float y, int shipSpawns) {
		this.friendlyName = friendlyName;
		this.shipTexture = shipTexture;
		this.flagTexture = flagTexture;
		this.x = x;
		this.y = y;
		this.shipSpawns = shipSpawns;
	}


	public String getName() {
		return friendlyName;
	}

	public String getShipTexture() {
		return shipTexture;
	}

	public String getFlagTexture() {
		return flagTexture;
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public int getShipSpawns() {
		return shipSpawns;
	}
}