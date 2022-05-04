package com.mygdx.pirategame.entity.cannon;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.mygdx.pirategame.PirateGame;
import com.mygdx.pirategame.entity.Entity;
import com.mygdx.pirategame.screen.ActiveGameScreen;

/**
 * Cannon Fire
 * Combat related cannon fire
 * Used by player and colleges,
 * Use should extend to enemy ships when implementing ship combat
 *
 * @author Faris Alblooki
 * @version 2.0
 */
public class CannonFire extends Entity {

	private final Texture cannonBall;
	private final float angle;
	private final float velocity;
	private final Sound fireNoise;
	public String college;
	private Body body;
	private Vector2 target;
	private boolean rotated = false;
	private float speed = 500f;
	private boolean applyForce = false;
	private float rangeMultiplier = 1f;
	private float bulletSpeedLvls;
	private short maskbit;
	private float stateTime;
	private boolean destroyed;
	private boolean setToDestroy;

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
	public CannonFire(ActiveGameScreen screen, float x, float y, Body body, float velocity, Vector2 target, float bulletSpeedLvls, float rangeMultiplier, short maskBit, short CatBit) {
		super(screen, x, y);
		this.velocity = velocity;
		this.rangeMultiplier = rangeMultiplier;
		//sets the angle and velocity
		angle = body.getAngle();
		this.target = target;
		this.bulletSpeedLvls = bulletSpeedLvls;
		this.maskbit = maskBit;


		//set cannonBall dimensions for the texture
		cannonBall = new Texture("cannonBall.png");
		setRegion(cannonBall);
		setBounds(x, y, 12 / PirateGame.PPM, 12 / PirateGame.PPM);
		defineEntity(maskBit, CatBit);

		//set collision bounds
		//set sound for fire and play if on
		fireNoise = Gdx.audio.newSound(Gdx.files.internal("explosion.wav"));
		if (ActiveGameScreen.game.getPreferences().isEffectsEnabled()) {
			fireNoise.play(ActiveGameScreen.game.getPreferences().getEffectsVolume());
		}
	}

	public CannonFire(ActiveGameScreen screen, float x, float y, Body body, float velocity, Vector2 target, float bulletSpeedLvls, float rangeMultiplier, short maskBit, short catBit, String college) {
		this(screen, x, y, body, velocity, target, bulletSpeedLvls, rangeMultiplier, maskBit, catBit);

		this.college = college;
	}

	public CannonFire(ActiveGameScreen screen, float x, float y, Body body, float velocity, float target, float bulletSpeedLvls, float rangeMultiplier, short maskBit, short catBit) {
		this(screen, x, y, body, velocity, null, bulletSpeedLvls, rangeMultiplier, maskBit, catBit);
		this.rotated = true;
		this.body.setTransform(this.body.getPosition().x, this.body.getPosition().y, target);
	}

	/**
	 * Defines the existance, direction, shape and size of a cannonball
	 */

	public void defineEntity(short maskBit, short catBit) {
		//sets the body definitions
		BodyDef bDef = new BodyDef();
		bDef.position.set(getX(), getY());
		bDef.type = BodyDef.BodyType.DynamicBody;
		setBody(getWorld().createBody(bDef));
		this.body = getBody();

		//Sets collision boundaries
		FixtureDef fDef = new FixtureDef();
		PolygonShape shape = new PolygonShape();
		shape.setAsBox((10 / 2f) / PirateGame.PPM, (10 * 3f) / PirateGame.PPM);

		// setting BIT identifier
		fDef.filter.categoryBits = catBit;
		// determining what this BIT can collide with
		fDef.filter.maskBits = maskBit;
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

		if (!rotated) {
			rotate((float) Math.toDegrees(Math.atan2(getBody().getPosition().x - target.x, target.y - getBody().getPosition().y)));
			rotated = true;
			getBody().setTransform(getBody().getPosition().x, getBody().getPosition().y, (float) Math.atan2(getBody().getPosition().x - target.x, target.y - getBody().getPosition().y));
		}

		if (!applyForce) {
			this.body.applyForceToCenter(this.body.getWorldVector(new Vector2(0, speed + (speed * (this.bulletSpeedLvls * 0.15f)))), true);
			applyForce = true;
		}
		setPosition(getBody().getPosition().x - getWidth() / 2, getBody().getPosition().y - getHeight() / 2);

		//If ball is set to destroy and isnt, destroy it
		if ((setToDestroy) && !destroyed) {
			getWorld().destroyBody(getBody());
			destroyed = true;
		}
		// determines cannonball range
		if (stateTime > (1f * rangeMultiplier)) {
			setToDestroy();
		}
	}

	/**
	 * Changes destruction state
	 */
	public void setToDestroy() {
		setToDestroy = true;
	}

	public void changeRangeMultiplier(float multiplier) {
		this.rangeMultiplier += multiplier;
	}

	/**
	 * Returns destruction status
	 */
	public boolean isDestroyed() {
		return destroyed;
	}

	@Override
	public void defineEntity() {

	}

	@Override
	public void onContact() {

	}

	public boolean isSetToDestroy() {
		return setToDestroy;
	}
}
