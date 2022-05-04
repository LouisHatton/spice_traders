package com.mygdx.pirategame.entity;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.pirategame.screen.ActiveGameScreen;

/**
 * Entity
 * Defines an entity
 * Instantiates an entity
 *
 * @author Faris Alblooki
 * @version 2.0
 */
public abstract class Entity extends Sprite {

	private final World world;
	private final ActiveGameScreen screen;
	private Body b2body;

	/**
	 * Instantiates an entity
	 * Sets position in world
	 *
	 * @param screen Visual data
	 * @param x      x position of entity
	 * @param y      y position of entity
	 */
	public Entity(ActiveGameScreen screen, float x, float y) {
		this.world = screen.getWorld();
		this.screen = screen;

		setPosition(x, y);
	}

	/**
	 * Defines an entity
	 */
	public abstract void defineEntity();

	/**
	 * Defines contact
	 */
	public abstract void onContact();

	/**
	 * Update the entity.
	 */
	public void update() {
	}

	/**
	 * Grab the current world the entity is associated with.
	 *
	 * @return World the entity is in.
	 */
	public World getWorld() {
		return world;
	}

	/**
	 * Find the game the entity is currently associated with.
	 *
	 * @return Current instance.
	 */
	public ActiveGameScreen getScreen() {
		return screen;
	}

	/**
	 * Grab the body of the entity that's currently spawned in.
	 *
	 * @return Current body.
	 */
	public Body getBody() {
		return b2body;
	}

	/**
	 * Set the body of the entity that should be associated with this instance.
	 *
	 * @param b2body New body
	 */
	public void setBody(Body b2body) {
		this.b2body = b2body;
	}
}
