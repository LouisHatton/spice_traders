package com.mygdx.pirategame.entity.cannon;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.mygdx.pirategame.PirateGame;
import com.mygdx.pirategame.entity.Entity;
import com.mygdx.pirategame.screen.ActiveGameScreen;

/**
 * Cannon Fire
 * Combat related cannon fire
 * Used by player and colleges,
 * Use should extend to enemy ships when implementing ship combat
 *
 * @author Ethan Alabaster
 * @version 1.0
 */
public class CannonFire extends Entity {

	private final Texture cannonBall;
	private float stateTime;
	private boolean destroyed;
	private boolean setToDestroy;
	private final float angle;
	private final float velocity;
	private final Sound fireNoise;

	/**
	 * Instantiates cannon fire
	 * Determines general cannonball data
	 * Determines firing sound
	 *
	 * @param screen   visual data
	 * @param x        x value of origin
	 * @param y        y value of origin
	 * @param body     body of origin
	 * @param velocity velocity of the cannon ball
	 */
	public CannonFire(ActiveGameScreen screen, float x, float y, Body body, float velocity) {
		super(screen, x, y);
		this.velocity = velocity;
		//sets the angle and velocity
		angle = body.getAngle();

		//set cannonBall dimensions for the texture
		cannonBall = new Texture("cannonBall.png");
		setRegion(cannonBall);
		setBounds(x, y, 10 / PirateGame.PPM, 10 / PirateGame.PPM);
		defineEntity();

		//set collision bounds
		//set sound for fire and play if on
		fireNoise = Gdx.audio.newSound(Gdx.files.internal("explosion.wav"));
		if (ActiveGameScreen.game.getPreferences().isEffectsEnabled()) {
			fireNoise.play(ActiveGameScreen.game.getPreferences().getEffectsVolume());
		}
	}

	/**
	 * Defines the existance, direction, shape and size of a cannonball
	 */
	@Override
	public void defineEntity() {
		//sets the body definitions
		BodyDef bDef = new BodyDef();
		bDef.position.set(getX(), getY());
		bDef.type = BodyDef.BodyType.DynamicBody;
		setBody(getWorld().createBody(bDef));

		//Sets collision boundaries
		FixtureDef fDef = new FixtureDef();
		CircleShape shape = new CircleShape();
		shape.setRadius(5 / PirateGame.PPM);

		// setting BIT identifier
		fDef.filter.categoryBits = PirateGame.CANNON_BIT;
		// determining what this BIT can collide with
		fDef.filter.maskBits = PirateGame.ENEMY_BIT | PirateGame.PLAYER_BIT | PirateGame.COLLEGE_BIT;
		fDef.shape = shape;
		fDef.isSensor = true;
		getBody().createFixture(fDef).setUserData(this);

		//Velocity maths
		float velX = MathUtils.cos(angle) * velocity + getBody().getLinearVelocity().x;
		float velY = MathUtils.sin(angle) * velocity + getBody().getLinearVelocity().y;
		getBody().applyLinearImpulse(new Vector2(velX, velY), getBody().getWorldCenter(), true);
	}

	/**
	 * Updates state with delta time
	 * Defines range of cannon fire
	 *
	 * @param dt Delta time (elapsed time since last game tick)
	 */
	public void update(float dt) {
		stateTime += dt;
		//Update position of ball
		setPosition(getBody().getPosition().x - getWidth() / 2, getBody().getPosition().y - getHeight() / 2);

		//If ball is set to destroy and isnt, destroy it
		if ((setToDestroy) && !destroyed) {
			getWorld().destroyBody(getBody());
			destroyed = true;
		}
		// determines cannonball range
		if (stateTime > 0.98f) {
			setToDestroy();
		}
	}

	/**
	 * Changes destruction state
	 */
	public void setToDestroy() {
		setToDestroy = true;
	}

	/**
	 * Returns destruction status
	 */
	public boolean isDestroyed() {
		return destroyed;
	}

	@Override
	public void onContact() {

	}
}
