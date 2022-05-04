package com.mygdx.pirategame.entity.tile.type;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.pirategame.PirateGame;
import com.mygdx.pirategame.entity.tile.InteractiveTileObject;
import com.mygdx.pirategame.screen.ActiveGameScreen;

/**
 * College Walls (Alcuin)
 * Checks interaction with walls from map
 *
 * @author Faris Alblooki
 * @version 2.0
 */
public class CollegeWalls extends InteractiveTileObject {

	private final ActiveGameScreen screen;
	private final String college;

	/**
	 * Sets bounds of college walls
	 *
	 * @param screen Visual data
	 * @param bounds Wall bounds
	 */
	public CollegeWalls(ActiveGameScreen screen, String college, Rectangle bounds) {
		super(screen, bounds);
		this.screen = screen;
		this.college = college;
		fixture.setUserData(this);
		//Set the category bit
		setCategoryFilter(PirateGame.COLLEGE_BIT);
	}

	@Override
	public void defineEntity() {

	}

	/**
	 * Checks for contact with cannonball
	 */
	@Override
	public void onContact() {
		Gdx.app.log("wall", "collision");
		//Deal damage to the assigned college
		screen.getCollege(college).onContact();
	}
}
