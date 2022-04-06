package com.mygdx.pirategame.entity.ship;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.mygdx.pirategame.PirateGame;
import com.mygdx.pirategame.display.HUD;
import com.mygdx.pirategame.entity.Enemy;
import com.mygdx.pirategame.screen.ActiveGameScreen;

/**
 * Enemy Ship
 * Generates enemy ship data
 * Instantiates an enemy ship
 *
 * @author Ethan Alabaster, Sam Pearson, Edward Poulter
 * @version 1.0
 */
public class EnemyShip extends Enemy {

	public String college;
	private Texture enemyShip;
	private final Sound destroy;
	private final Sound hit;

	/**
	 * Instantiates enemy ship
	 *
	 * @param screen     Visual data
	 * @param x          x coordinates of entity
	 * @param y          y coordinates of entity
	 * @param path       path of texture file
	 * @param assignment College ship is assigned to
	 */
	public EnemyShip(ActiveGameScreen screen, float x, float y, String path, String assignment) {
		super(screen, x, y);
		enemyShip = new Texture(path);
		//Assign college
		college = assignment;
		//Set audios
		destroy = Gdx.audio.newSound(Gdx.files.internal("ship-explosion-2.wav"));
		hit = Gdx.audio.newSound(Gdx.files.internal("ship-hit.wav"));
		//Set the position and size of the college
		setBounds(x, y, 64 / PirateGame.PPM, 110 / PirateGame.PPM);
		setRegion(enemyShip);
		setOrigin(32 / PirateGame.PPM, 55 / PirateGame.PPM);
		defineEntity();
		super.initHealthBar();

		setDamage(20);
	}

	/**
	 * Updates the state of each object with delta time
	 * Checks for ship destruction
	 *
	 * @param dt Delta time (elapsed time since last game tick)
	 */
	public void update(float dt) {
		//If ship is set to destroy and isnt, destroy it
		if (isSetToDestroy() && !isDestroyed()) {
			//Play death noise
			if (ActiveGameScreen.game.getPreferences().isEffectsEnabled()) {
				destroy.play(ActiveGameScreen.game.getPreferences().getEffectsVolume());
			}
			getWorld().destroyBody(getBody());
			setDestroyed(true);
			//Change player coins and points
			HUD.changePoints(20);
			HUD.changeCoins(10);
		} else if (!isDestroyed()) {
			//Update position and angle of ship
			setPosition(getBody().getPosition().x - getWidth() / 2f, getBody().getPosition().y - getHeight() / 2f);
			float angle = (float) Math.atan2(getBody().getLinearVelocity().y, getBody().getLinearVelocity().x);
			getBody().setTransform(getBody().getWorldCenter(), angle - ((float) Math.PI) / 2.0f);
			setRotation((float) (getBody().getAngle() * 180 / Math.PI));
			//Update health bar
			getBar().update();
		}
		if (getHealth() <= 0) {
			setToDestroy(true);
		}

		// below code is to move the ship to a coordinate (target)
		//Vector2 target = new Vector2(960 / PirateGame.PPM, 2432 / PirateGame.PPM);
		//target.sub(getBody().getPosition());
		//target.nor();
		//float speed = 1.5f;
		//getBody().setLinearVelocity(target.scl(speed));
	}

	/**
	 * Constructs the ship batch
	 *
	 * @param batch The batch of visual data of the ship
	 */
	public void draw(Batch batch) {
		if (!isDestroyed()) {
			super.draw(batch);
			//Render health bar
			getBar().render(batch);
		}
	}

	/**
	 * Defines the ship as an enemy
	 * Sets data to act as an enemy
	 */
	@Override
	public void defineEntity() {
		//sets the body definitions
		BodyDef bdef = new BodyDef();
		bdef.position.set(getX(), getY());
		bdef.type = BodyDef.BodyType.DynamicBody;
		setBody(getWorld().createBody(bdef));

		//Sets collision boundaries
		FixtureDef fdef = new FixtureDef();
		CircleShape shape = new CircleShape();
		shape.setRadius(55 / PirateGame.PPM);
		// setting BIT identifier
		fdef.filter.categoryBits = PirateGame.ENEMY_BIT;
		// determining what this BIT can collide with
		fdef.filter.maskBits = PirateGame.DEFAULT_BIT | PirateGame.PLAYER_BIT | PirateGame.ENEMY_BIT | PirateGame.CANNON_BIT;
		fdef.shape = shape;
		fdef.restitution = 0.7f;
		getBody().createFixture(fdef).setUserData(this);
	}

	/**
	 * Checks contact
	 * Changes health in accordance with contact and damage
	 */
	@Override
	public void onContact() {
		Gdx.app.log("enemy", "collision");
		//Play collision sound
		if (ActiveGameScreen.game.getPreferences().isEffectsEnabled()) {
			hit.play(ActiveGameScreen.game.getPreferences().getEffectsVolume());
		}
		//Deal with the damage
		takeDamage(getDamage());
		getBar().changeHealth(getDamage());
		HUD.changePoints(5);
	}

	/**
	 * Updates the ship image. Particuarly change texture on college destruction
	 *
	 * @param alignment Associated college
	 * @param path      Path of new texture
	 */
	public void updateTexture(String alignment, String path) {
		college = alignment;
		enemyShip = new Texture(path);
		setRegion(enemyShip);
	}
}
