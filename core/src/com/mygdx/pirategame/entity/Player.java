package com.mygdx.pirategame.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.utils.Array;
import com.mygdx.pirategame.PirateGame;
import com.mygdx.pirategame.entity.cannon.CannonFire;
import com.mygdx.pirategame.screen.ActiveGameScreen;

/**
 * Creates the class of the player. Everything that involves actions coming from the player boat
 *
 * @author Ethan Alabaster, Edward Poulter
 * @version 1.0
 */
public class Player extends Entity {

	private final Texture ship;
	private final Sound breakSound;
	private final Array<CannonFire> cannonBalls;

	/**
	 * Instantiates a new Player. Constructor only called once per game
	 *
	 * @param screen visual data
	 */
	public Player(ActiveGameScreen screen) {
		super(screen, 0, 0);
		// Retrieves world data and creates ship texture
		ship = new Texture("player_ship.png");

		// Defines a player, and the players position on screen and world
		setBounds(0, 0, 64 / PirateGame.PPM, 110 / PirateGame.PPM);
		setRegion(ship);
		setOrigin(32 / PirateGame.PPM, 55 / PirateGame.PPM);
		defineEntity();

		// Sound effect for damage
		breakSound = Gdx.audio.newSound(Gdx.files.internal("wood-bump.mp3"));

		// Sets cannonball array
		cannonBalls = new Array<>();
	}

	/**
	 * Update the position of the player. Also updates any cannon balls the player generates
	 *
	 * @param dt Delta Time
	 */
	public void update(float dt) {
		// Updates position and orientation of player
		setPosition(getBody().getPosition().x - getWidth() / 2f, getBody().getPosition().y - getHeight() / 2f);
		float angle = (float) Math.atan2(getBody().getLinearVelocity().y, getBody().getLinearVelocity().x);
		getBody().setTransform(getBody().getWorldCenter(), angle - ((float) Math.PI) / 2.0f);
		setRotation((float) (getBody().getAngle() * 180 / Math.PI));

		// Updates cannonball data
		for (CannonFire ball : cannonBalls) {
			ball.update(dt);
			if (ball.isDestroyed())
				cannonBalls.removeValue(ball, true);
		}
	}

	/**
	 * Plays the break sound when a boat takes damage
	 */
	public void playBreakSound() {
		// Plays damage sound effect
		if (ActiveGameScreen.game.getPreferences().isEffectsEnabled()) {
			breakSound.play(ActiveGameScreen.game.getPreferences().getEffectsVolume());
		}
	}

	/**
	 * Defines all the parts of the player's physical model. Sets it up for collisons
	 */
	@Override
	public void defineEntity() {
		// Defines a players position
		BodyDef bdef = new BodyDef();
		bdef.position.set(1200 / PirateGame.PPM, 2500 / PirateGame.PPM); // Default Pos: 1800,2500
		bdef.type = BodyDef.BodyType.DynamicBody;
		setBody(getWorld().createBody(bdef));

		// Defines a player's shape and contact borders
		FixtureDef fdef = new FixtureDef();
		CircleShape shape = new CircleShape();
		shape.setRadius(55 / PirateGame.PPM);

		// setting BIT identifier
		fdef.filter.categoryBits = PirateGame.PLAYER_BIT;

		// determining what this BIT can collide with
		fdef.filter.maskBits = PirateGame.DEFAULT_BIT | PirateGame.COIN_BIT | PirateGame.ENEMY_BIT | PirateGame.COLLEGE_BIT | PirateGame.COLLEGESENSOR_BIT | PirateGame.COLLEGEFIRE_BIT;
		fdef.shape = shape;
		getBody().createFixture(fdef).setUserData(this);
	}

	@Override
	public void onContact() {

	}

	/**
	 * Called when E is pushed. Causes 1 cannon ball to spawn on both sides of the ships wih their relative velocity
	 */
	public void fire() {
		// Fires cannons
		cannonBalls.add(new CannonFire(getScreen(), getBody().getPosition().x, getBody().getPosition().y, getBody(), 5));
		cannonBalls.add(new CannonFire(getScreen(), getBody().getPosition().x, getBody().getPosition().y, getBody(), -5));

		// Cone fire below
        /*cannonBalls.add(new CannonFire(screen, getBody().getPosition().x, getBody().getPosition().y, (float) (getBody().getAngle() - Math.PI / 6), -5, getBody().getLinearVelocity()));
        cannonBalls.add(new CannonFire(screen, getBody().getPosition().x, getBody().getPosition().y, (float) (getBody().getAngle() - Math.PI / 6), 5, getBody().getLinearVelocity()));
        cannonBalls.add(new CannonFire(screen, getBody().getPosition().x, getBody().getPosition().y, (float) (getBody().getAngle() + Math.PI / 6), -5, getBody().getLinearVelocity()));
        cannonBalls.add(new CannonFire(screen, getBody().getPosition().x, getBody().getPosition().y, (float) (getBody().getAngle() + Math.PI / 6), 5, getBody().getLinearVelocity()));
        }
         */
	}

	/**
	 * Draws the player using batch
	 * Draws cannonballs using batch
	 *
	 * @param batch The batch of the program
	 */
	public void draw(Batch batch) {
		// Draws player and cannonballs
		super.draw(batch);
		for (CannonFire ball : cannonBalls)
			ball.draw(batch);
	}
}
