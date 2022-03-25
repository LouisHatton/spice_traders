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
 * @author Ethan Alabaster
 * @version 1.0
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

	public void update() {
	}

	public World getWorld() {
		return world;
	}

	public ActiveGameScreen getScreen() {
		return screen;
	}

	public Body getBody() {
		return b2body;
	}

	public void setBody(Body b2body) {
		this.b2body = b2body;
	}
}
