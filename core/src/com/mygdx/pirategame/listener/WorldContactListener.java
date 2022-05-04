package com.mygdx.pirategame.listener;

import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.pirategame.PirateGame;
import com.mygdx.pirategame.display.HUD;
import com.mygdx.pirategame.entity.Enemy;
import com.mygdx.pirategame.entity.Entity;
import com.mygdx.pirategame.entity.Player;
import com.mygdx.pirategame.entity.cannon.CannonFire;
import com.mygdx.pirategame.entity.college.version.CollegeFire;
import com.mygdx.pirategame.entity.tile.InteractiveTileObject;

/**
 * Tells the game what to do when certain entities come into contact with each other
 *
 * @author Faris Alblooki
 * @version 2.0
 */
public class WorldContactListener implements ContactListener {

	/**
	 * The start of the collision. Tells the game what should happen when the contact begins
	 *
	 * @param contact The object that contains information about the collision
	 */
	@Override
	public void beginContact(Contact contact) {
		// Finds contact
		Fixture fixA = contact.getFixtureA();
		Fixture fixB = contact.getFixtureB();

		int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;

		this.selectAppropriateResponse(fixA, fixB, cDef);
	}

	/**
	 * Run when contact is ended. Nearly empty since nothing special needs to happen when a contact is ended
	 *
	 * @param contact The object that contains information about the collision
	 */
	@Override
	public void endContact(Contact contact) {
	}

	/**
	 * (Not Used)
	 * Can be called before beginContact to pre emptively solve it
	 *
	 * @param contact     The object that contains information about the collision
	 * @param oldManifold Predicted impulse based on old data
	 */
	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
	}

	/**
	 * (Not Used)
	 * Can be called before beginContact to post emptively solve it
	 *
	 * @param contact The object that contains information about the collision
	 * @param impulse The signal recieved
	 */
	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {

	}

	/**
	 * Detect and respond to a collision between two entities.
	 *
	 * @param fixA First entity to detect.
	 * @param fixB Second entity to detect.
	 * @param cDef Defined bit logic to base decision off.
	 */
	public void selectAppropriateResponse(Fixture fixA, Fixture fixB, int cDef) {
		// Fixes contact to an entity
		switch (cDef) {
			case PirateGame.COLLEGEFIRE_BIT | PirateGame.ENEMY_BIT:
				break;

			case PirateGame.COIN_BIT | PirateGame.PLAYER_BIT:
				this.handlePlayerAndCoin(fixA, fixB);
				break;
			case PirateGame.DEFAULT_BIT | PirateGame.PLAYER_BIT:
				this.handleWallAndPlayer(fixA, fixB);
				break;
			case PirateGame.ENEMY_BIT | PirateGame.PLAYER_BIT:
				this.handleEnemyAndPlayer(fixA, fixB);
				break;
			case PirateGame.COLLEGE_BIT | PirateGame.CANNON_BIT:
				this.handleCollegeAndCannon(fixA, fixB);
				break;
			case PirateGame.ENEMY_BIT | PirateGame.CANNON_BIT:
				this.handleEnemyAndCannon(fixA, fixB);
				break;
			case PirateGame.PLAYER_BIT | PirateGame.CANNON_BIT:
				this.handlePlayerAndCannon(fixA, fixB);
				break;
			case PirateGame.COLLEGEFIRE_BIT | PirateGame.PLAYER_BIT:
				this.handleCollegeFireAndPlayer(fixA, fixB);
				break;
		}
	}

	/**
	 * Handle a collision between a Player and a Coin.
	 *
	 * @param fixA First entity involved.
	 * @param fixB Second entity involved.
	 */
	public void handlePlayerAndCoin(Fixture fixA, Fixture fixB) {
		if (fixA.getFilterData().categoryBits == PirateGame.COIN_BIT) {
			((Entity) fixA.getUserData()).onContact();
		} else {
			((Entity) fixB.getUserData()).onContact();
		}
	}

	/**
	 * Handle a collision between a Wall and a Player.
	 *
	 * @param fixA First entity involved.
	 * @param fixB Second entity involved.
	 */
	public void handleWallAndPlayer(Fixture fixA, Fixture fixB) {
		if (fixA.getFilterData().categoryBits == PirateGame.DEFAULT_BIT) {
			if (fixA.getUserData() != null && InteractiveTileObject.class.isAssignableFrom(fixA.getUserData().getClass())) {
				((InteractiveTileObject) fixA.getUserData()).onContact();
				((Player) fixB.getUserData()).playBreakSound();
			}
		} else {
			if (fixB.getUserData() != null && InteractiveTileObject.class.isAssignableFrom(fixB.getUserData().getClass())) {
				((InteractiveTileObject) fixB.getUserData()).onContact();
			}
		}
	}

	/**
	 * Handle a collision between an Enemy and a Player.
	 *
	 * @param fixA First entity involved.
	 * @param fixB Second entity involved.
	 */
	public void handleEnemyAndPlayer(Fixture fixA, Fixture fixB) {
		if (fixA.getFilterData().categoryBits == PirateGame.ENEMY_BIT) {
			((Enemy) fixA.getUserData()).onContact();
		} else {
			((Enemy) fixB.getUserData()).onContact();
		}
	}

	/**
	 * Handle a collision between a College and a Cannon.
	 *
	 * @param fixA First entity involved.
	 * @param fixB Second entity involved.
	 */
	public void handleCollegeAndCannon(Fixture fixA, Fixture fixB) {
		if (fixA.getFilterData().categoryBits == PirateGame.COLLEGE_BIT) {
			if (fixA.getUserData() != null && InteractiveTileObject.class.isAssignableFrom(fixA.getUserData().getClass())) {
				((InteractiveTileObject) fixA.getUserData()).onContact();
				((CannonFire) fixB.getUserData()).setToDestroy();
			}
		} else {
			if (fixB.getUserData() != null && InteractiveTileObject.class.isAssignableFrom(fixB.getUserData().getClass())) {
				((InteractiveTileObject) fixB.getUserData()).onContact();
				((CannonFire) fixA.getUserData()).setToDestroy();
			}
		}
	}

	/**
	 * Handle a collision between an Enemy and a Cannon.
	 *
	 * @param fixA First entity involved.
	 * @param fixB Second entity involved.
	 */
	public void handleEnemyAndCannon(Fixture fixA, Fixture fixB) {
		if (fixA.getFilterData().categoryBits == PirateGame.ENEMY_BIT) {
			((Enemy) fixA.getUserData()).onContact();
			((CannonFire) fixB.getUserData()).setToDestroy();
		} else {
			((Enemy) fixB.getUserData()).onContact();
			((CannonFire) fixA.getUserData()).setToDestroy();
		}
	}

	/**
	 * Handle a collision between a Player and a Cannon.
	 *
	 * @param fixA First entity involved.
	 * @param fixB Second entity involved.
	 */
	public void handlePlayerAndCannon(Fixture fixA, Fixture fixB) {
		if (fixA.getFilterData().categoryBits == PirateGame.PLAYER_BIT) {
			HUD.changeHealth(-10);
			((CannonFire) fixB.getUserData()).setToDestroy();
		} else {
			HUD.changeHealth(-10);
			((CannonFire) fixA.getUserData()).setToDestroy();
		}
	}

	/**
	 * Handle a collision between a College and a Player.
	 *
	 * @param fixA First entity involved.
	 * @param fixB Second entity involved.
	 */
	public void handleCollegeFireAndPlayer(Fixture fixA, Fixture fixB) {
		if (fixA.getFilterData().categoryBits == PirateGame.COLLEGEFIRE_BIT) {
			HUD.changeHealth(-10);
			if (fixA.getUserData() instanceof CollegeFire) ((CollegeFire) fixA.getUserData()).setToDestroy();
			else if (fixA.getUserData() instanceof CannonFire) {
				((CannonFire) fixA.getUserData()).setToDestroy();
				((CannonFire) fixA.getUserData()).onContact();
			}
		} else {
			HUD.changeHealth(-10);
			if (fixA.getUserData() instanceof CollegeFire) ((CollegeFire) fixB.getUserData()).setToDestroy();
			else if (fixA.getUserData() instanceof CannonFire) {
				((CannonFire) fixB.getUserData()).setToDestroy();
				((CannonFire) fixB.getUserData()).onContact();
			}
		}
	}
}
