package com.mygdx.pirategame.entity.coin;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.mygdx.pirategame.PirateGame;
import com.mygdx.pirategame.display.HUD;
import com.mygdx.pirategame.entity.Entity;
import com.mygdx.pirategame.screen.ActiveGameScreen;

/**
 * Coin
 * Creates an object for each coin
 * Extends the entity class to define coin as an entity
 *
 * @author Faris Alblooki
 * @version 2.0
 */
public class Coin extends Entity {

	private final Texture coin;
	private final Sound coinPickup;
	private boolean setToDestroyed;
	private boolean destroyed;

	/**
	 * Instantiates a new Coin.
	 *
	 * @param screen the screen its going onto
	 * @param x      the x value to be placed at
	 * @param y      the y value to be placed at
	 */
	public Coin(ActiveGameScreen screen, float x, float y) {
		super(screen, x, y);
		//Set coin image
		coin = new Texture("coin.png");
		//Set the position and size of the coin
		setBounds(x, y, 48 / PirateGame.PPM, 48 / PirateGame.PPM);
		//Set the texture
		setRegion(coin);
		//Sets origin of the coin
		setOrigin(24 / PirateGame.PPM, 24 / PirateGame.PPM);
		defineEntity();
		coinPickup = Gdx.audio.newSound(Gdx.files.internal("coin-pickup.mp3"));
	}

	/**
	 * Updates the coins state. If needed, deletes the coin if picked up.
	 */
	public void update() {
		//If coin is set to destroy and isnt, destroy it
		if (setToDestroyed && !destroyed) {
			getWorld().destroyBody(getBody());
			destroyed = true;
		}
		//Update position of coin
		else if (!destroyed) {
			setPosition(getBody().getPosition().x - getWidth() / 2f, getBody().getPosition().y - getHeight() / 2f);
		}
	}

	/**
	 * Defines all the parts of the coins physical model. Sets it up for collisons
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
		shape.setRadius(24 / PirateGame.PPM);
		// setting BIT identifier
		fdef.filter.categoryBits = PirateGame.COIN_BIT;
		// determining what this BIT can collide with
		fdef.filter.maskBits = PirateGame.DEFAULT_BIT | PirateGame.PLAYER_BIT | PirateGame.ENEMY_BIT;
		fdef.shape = shape;
		fdef.isSensor = true;
		getBody().createFixture(fdef).setUserData(this);
	}

	/**
	 * What happens when an entity collides with the coin. The only entity that is able to do so is the player ship
	 */
	@Override
	public void onContact() {
		//Add a coin
		HUD.changeCoins(1);
		//Set to destroy
		setToDestroyed = true;
		Gdx.app.log("coin", "collision");
		//Play pickup sound
		if (ActiveGameScreen.game.getPreferences().isEffectsEnabled()) {
			coinPickup.play(ActiveGameScreen.game.getPreferences().getEffectsVolume());
		}

	}

	/**
	 * Draws the coin using batch
	 *
	 * @param batch The batch of the program
	 */
	public void draw(Batch batch) {
		if (!destroyed) {
			super.draw(batch);
		}
	}

	public boolean isSetToDestroyed() {
		return setToDestroyed;
	}

	public boolean isDestroyed() {
		return destroyed;
	}
}
