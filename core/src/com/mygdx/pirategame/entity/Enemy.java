package com.mygdx.pirategame.entity;

import com.mygdx.pirategame.display.HUD;
import com.mygdx.pirategame.display.HealthBar;
import com.mygdx.pirategame.screen.ActiveGameScreen;

/**
 * Enemy
 * Class to generate enemies
 * Instantiates enemies
 *
 * @author Faris Alblooki
 * @version 2.0
 */
public abstract class Enemy extends Entity {

	public String college;
	private boolean setToDestroy;
	private boolean destroyed;
	private int health;
	private int damage = 20;
	private HealthBar bar;
	private boolean justDied = false;
	private float maxHealth = 100;

	/**
	 * Instantiates an enemy
	 *
	 * @param screen Visual data
	 * @param x      x position of entity
	 * @param y      y position of entity
	 */
	public Enemy(ActiveGameScreen screen, float x, float y) {
		super(screen, x, y);

		this.setToDestroy = false;
		this.destroyed = false;
		this.health = 100;
		this.maxHealth = 100f;
	}

	/**
	 * Update the entity based on a specific time.
	 *
	 * @param dt Tick of the current game.
	 */
	public abstract void update(float dt);

	/**
	 * Checks received damage
	 * Increments total damage by damage received
	 *
	 * @param value Damage received
	 */
	public void changeDamageReceived(int value) {
		damage += value;
	}

	/**
	 * Set whether we should destroy the college on next update.
	 *
	 * @return Whether the college should be destroyed.
	 */
	public boolean isSetToDestroy() {
		return setToDestroy;
	}

	/**
	 * Set whether it should be destroyed on next {@link #update(float)}
	 *
	 * @param setToDestroy Whether we should destroy the entity.
	 */
	public void setToDestroy(boolean setToDestroy) {
		this.setToDestroy = setToDestroy;
	}

	/**
	 * Whether the entity is dead or not.
	 *
	 * @return Whether it's alive.
	 */
	public boolean isDestroyed() {
		return destroyed;
	}

	/**
	 * Set the validity of the college.
	 *
	 * @param destroyed Set the validity of the entity.
	 */
	public void setDestroyed(boolean destroyed) {
		this.destroyed = destroyed;
	}

	/**
	 * Grab the current health of the entity.
	 *
	 * @return Current health.
	 */
	public int getHealth() {
		return health;
	}

	/**
	 * Set the health of the entity.
	 *
	 * @param health New health value.
	 */
	public void setHealth(int health) {
		this.health = health;
	}

	/**
	 * Grab the current damage the entity inflicts on others.
	 *
	 * @return Current damage rate.
	 */
	public int getDamage() {
		return damage;
	}

	/**
	 * Set the damage the entity inflicts on others.
	 *
	 * @param damage New damage rate.
	 */
	public void setDamage(int damage) {
		this.damage = damage;
	}

	/**
	 * Display showing the entities current health to the player.
	 *
	 * @return Instance of the Health Bar.
	 */
	public HealthBar getBar() {
		return bar;
	}

	/**
	 * Force the entity to take damage. This is scaled dependent on whether bloodied has been enabled.
	 *
	 * @param damage Raw damage to inflict on the entity.
	 */
	public void takeDamage(double damage) {
		float multiplier = 1f;
		if (Player.isBloodied) {
			if (HUD.getHealth() == HUD.maxHealth) {
				multiplier = 1f;
			} else {
				multiplier = 1 + ((1 - (HUD.getHealth() / HUD.maxHealth) * HUD.bloodyAmount));
			}
		}

		if (!justDied) {
			this.health -= damage * multiplier;
			System.out.println("pp " + health);
		}

		this.getBar().update();
		this.getBar().setHealth(health / maxHealth);
	}

	/**
	 * Get the maximum health this entity can possibly have.
	 *
	 * @return Maximum value of health.
	 */
	public float getMaxHealth() {
		return maxHealth;
	}

	/**
	 * Change whether the entity just died, or had been dead for a while.
	 *
	 * @param input New value
	 */
	public void changeIustDied(boolean input) {
		this.justDied = input;
	}

	/**
	 * Create a health bar for the entity.
	 */
	public void initHealthBar() {
		this.bar = new HealthBar(this);
	}

	/**
	 * Set the college the entity has allegiance to.
	 *
	 * @param college New college.
	 */
	public void setCollege(String college) {
		this.college = college;
	}
}
