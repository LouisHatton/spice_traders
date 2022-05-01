package com.mygdx.pirategame.entity.college.version;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.mygdx.pirategame.PirateGame;
import com.mygdx.pirategame.entity.Entity;
import com.mygdx.pirategame.screen.ActiveGameScreen;

/**
 * College Fire
 * Defines college attack method
 * Defines college cannonball projectiles
 *
 * @author Ethan Alabaster
 * @version 1.0
 */

public class CollegeFire extends Entity {

	private transient final Texture cannonBall;
	private final Vector2 playerPos;
	public String college;
	private float stateTime;
	private boolean destroyed;
	private boolean setToDestroy;

	/**
	 * Defines player position
	 * Defines cannonballs
	 *
	 * @param screen Visual data
	 * @param x      x position of player
	 * @param y      y position of player
	 */
	public CollegeFire(ActiveGameScreen screen, float x, float y, String college) {
		super(screen, x, y);
		this.college = college;
		playerPos = screen.getPlayerPos();
		cannonBall = new Texture("cannonBall.png");
		//Set the position and size of the ball
		setRegion(cannonBall);
		setBounds(x, y, 10 / PirateGame.PPM, 10 / PirateGame.PPM);
		defineEntity();
	}

	/**
	 * Defines cannonball data
	 * Defines cannonball shape
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
		fDef.filter.categoryBits = PirateGame.COLLEGEFIRE_BIT;
		// determining what this BIT can collide with
		fDef.filter.maskBits = PirateGame.PLAYER_BIT;

		fDef.shape = shape;
		getBody().createFixture(fDef).setUserData(this);

		// Math for firing the cannonball at the player
		if (playerPos != null) {
			playerPos.sub(getBody().getPosition());
			playerPos.nor();
			float speed = 5f;
			getBody().setLinearVelocity(playerPos.scl(speed));
		}
	}

	@Override
	public void onContact() {

	}

	/**
	 * Updates state with delta time
	 * Defines range of cannon fire
	 *
	 * @param dt Delta time (elapsed time since last game tick)
	 */
	public void update(float dt) {
		stateTime += dt;
		//If college is set to destroy and isnt, destroy it
		setPosition(getBody().getPosition().x - getWidth() / 2, getBody().getPosition().y - getHeight() / 2);
		if ((setToDestroy) && !destroyed) {
			getWorld().destroyBody(getBody());
			destroyed = true;
		}
		// determines cannonball range
		if (stateTime > 2f) {
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
}
