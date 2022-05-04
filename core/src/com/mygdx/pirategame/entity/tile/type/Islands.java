package com.mygdx.pirategame.entity.tile.type;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.pirategame.PirateGame;
import com.mygdx.pirategame.display.HUD;
import com.mygdx.pirategame.entity.tile.InteractiveTileObject;
import com.mygdx.pirategame.screen.ActiveGameScreen;

/**
 * Sets up the class for all the Islands. Deals with what happens on collision and its properties
 *
 * @author Faris Alblooki
 * @version 2.0
 */
public class Islands extends InteractiveTileObject {

	/**
	 * Instantiates a new Islands.
	 *
	 * @param screen visual data
	 * @param bounds Rectangle boundary (world boundary)
	 */
	public Islands(ActiveGameScreen screen, Rectangle bounds) {
		super(screen, bounds);
		fixture.setUserData(this);
		//Set the category bit
		setCategoryFilter(PirateGame.DEFAULT_BIT);
	}

	@Override
	public void defineEntity() {

	}

	/**
	 * When contact occurs between the ship and island. deal damage to the ship
	 */
	@Override
	public void onContact() {
		Gdx.app.log("island", "collision");
		//Deal damage to the boat
		HUD.changeHealth(-10);
	}
}
