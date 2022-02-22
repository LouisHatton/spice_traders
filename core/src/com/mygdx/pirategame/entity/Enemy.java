package com.mygdx.pirategame.entity;

import com.mygdx.pirategame.display.HealthBar;
import com.mygdx.pirategame.screen.GameScreen;

/**
 * Enemy
 * Class to generate enemies
 * Instantiates enemies
 *
 * @author Ethan Alabaster
 * @version 1.0
 */
public abstract class Enemy extends Entity {

	private boolean setToDestroy;
	private boolean destroyed;
	private int health;
	private int damage;
	private HealthBar bar;

	/**
	 * Instantiates an enemy
	 *
	 * @param screen Visual data
	 * @param x      x position of entity
	 * @param y      y position of entity
	 */
	public Enemy(GameScreen screen, float x, float y) {
		super(screen, x, y);

		this.setToDestroy = false;
		this.destroyed = false;
		this.health = 100;
	}

	public abstract void update(float dt);

	/**
	 * Checks recieved damage
	 * Increments total damage by damage received
	 *
	 * @param value Damage received
	 */
	public void changeDamageReceived(int value) {
		damage += value;
	}

	public boolean isSetToDestroy() {
		return setToDestroy;
	}

	public void setToDestroy(boolean setToDestroy) {
		this.setToDestroy = setToDestroy;
	}

	public boolean isDestroyed() {
		return destroyed;
	}

	public void setDestroyed(boolean destroyed) {
		this.destroyed = destroyed;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public int getDamage() {
		return damage;
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}

	public HealthBar getBar() {
		return bar;
	}

	public void takeDamage(double damage) {
		this.health -= damage;
	}

	public void initHealthBar() {
		this.bar = new HealthBar(this);
	}
}
