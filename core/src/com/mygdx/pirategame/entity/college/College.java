package com.mygdx.pirategame.entity.college;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.utils.Array;
import com.mygdx.pirategame.PirateGame;
import com.mygdx.pirategame.display.HUD;
import com.mygdx.pirategame.entity.Enemy;
import com.mygdx.pirategame.entity.college.version.CollegeFire;
import com.mygdx.pirategame.entity.ship.EnemyShip;
import com.mygdx.pirategame.screen.GameScreen;
import com.mygdx.pirategame.utils.SpawnUtils;

import java.util.ArrayList;
import java.util.Random;

/**
 * College
 * Class to generate the enemy entity college
 * Instantiates colleges
 * Instantiates college fleets
 *
 * @author Ethan Alabaster, Edward Poulter
 * @version 1.0
 */

public class College extends Enemy {

	public Random rand = new Random();
	public ArrayList<EnemyShip> fleet = new ArrayList<>();
	private final Texture enemyCollege;
	private final String currentCollege;
	private final Array<CollegeFire> cannonBalls;
	private final SpawnUtils noSpawn;

	/**
	 * @param screen       Visual data
	 * @param college      College name i.e. "Alcuin" used for fleet assignment
	 * @param x            College position on x-axis
	 * @param y            College position on y-axis
	 * @param flag         College flag sprite (image name)
	 * @param ship         College ship sprite (image name)
	 * @param ship_no      Number of college ships to produce
	 * @param invalidSpawn Spawn data to check spawn validity when generating ships
	 */
	public College(GameScreen screen, String college, float x, float y, String flag, String ship, int ship_no, SpawnUtils invalidSpawn) {
		super(screen, x, y);
		noSpawn = invalidSpawn;
		currentCollege = flag;
		enemyCollege = new Texture(flag);
		//Set the position and size of the college
		setBounds(0, 0, 64 / PirateGame.PPM, 110 / PirateGame.PPM);
		setRegion(enemyCollege);
		setOrigin(32 / PirateGame.PPM, 55 / PirateGame.PPM);
		defineEntity();
		super.initHealthBar();

		setDamage(10);
		cannonBalls = new Array<>();
		int ranX = 0;
		int ranY = 0;
		boolean spawnIsValid;

		//Generates college fleet
		for (int i = 0; i < ship_no; i++) {
			spawnIsValid = false;
			while (!spawnIsValid) {
				ranX = rand.nextInt(2000) - 1000;
				ranY = rand.nextInt(2000) - 1000;
				ranX = (int) Math.floor(x + (ranX / PirateGame.PPM));
				ranY = (int) Math.floor(y + (ranY / PirateGame.PPM));
				spawnIsValid = getCoord(ranX, ranY);
			}
			fleet.add(new EnemyShip(screen, ranX, ranY, ship, college));
		}
	}

	/**
	 * Checks ship spawning in at a valid location
	 *
	 * @param x x position to test
	 * @param y y position to test
	 * @return isValid : returns the validity of the proposed spawn point
	 */
	public boolean getCoord(int x, int y) {
		if (x < SpawnUtils.xBase || x >= SpawnUtils.xCap || y < SpawnUtils.yBase || y >= SpawnUtils.yCap) {
			return false;
		} else if (noSpawn.tileBlocked.containsKey(x)) {
			return !noSpawn.tileBlocked.get(x).contains(y);
		}
		return true;
	}

	/**
	 * Updates the state of each object with delta time
	 * Checks for college destruction
	 * Checks for cannon fire
	 *
	 * @param dt Delta time (elapsed time since last game tick)
	 */
	public void update(float dt) {
		//If college is set to destroy and isnt, destroy it
		if (isSetToDestroy() && !isDestroyed()) {
			getWorld().destroyBody(getBody());
			setDestroyed(true);

			//If it is the player ally college, end the game for the player
			if (currentCollege.equals("alcuin_flag.png")) {
				getScreen().gameOverCheck();
			}
			//Award the player coins and points for destroying a college
			if (!currentCollege.equals("alcuin_flag.png")) {
				HUD.changePoints(100);
				HUD.changeCoins(rand.nextInt(10));
			}
		}
		//If not destroyed, update the college position
		else if (!isDestroyed()) {
			setPosition(getBody().getPosition().x - getWidth() / 2f, getBody().getPosition().y - getHeight() / 2f);

		}

		if (getHealth() <= 0) {
			setToDestroy(true);
		}

		this.getBar().update();

		if (getHealth() <= 0) {
			setToDestroy(true);
		}
		//Update cannon balls
		for (CollegeFire ball : cannonBalls) {
			ball.update(dt);
			if (ball.isDestroyed())
				cannonBalls.removeValue(ball, true);
		}
	}

	/**
	 * Draws the batch of cannonballs
	 */
	public void draw(Batch batch) {
		if (!isDestroyed()) {
			super.draw(batch);
			//Render health bar
			getBar().render(batch);
			//Render balls
			for (CollegeFire ball : cannonBalls)
				ball.draw(batch);
		}
	}

	/**
	 * Sets the data to define a college as an enemy
	 */
	@Override
	public void defineEntity() {
		//sets the body definitions
		BodyDef bdef = new BodyDef();
		bdef.position.set(getX(), getY());
		bdef.type = BodyDef.BodyType.StaticBody;
		setBody(getWorld().createBody(bdef));
		//Sets collision boundaries
		FixtureDef fdef = new FixtureDef();
		CircleShape shape = new CircleShape();
		shape.setRadius(55 / PirateGame.PPM);
		// setting BIT identifier
		fdef.filter.categoryBits = PirateGame.COLLEGESENSOR_BIT;
		// determining what this BIT can collide with
		fdef.filter.maskBits = PirateGame.PLAYER_BIT;
		fdef.shape = shape;
		fdef.isSensor = true;
		getBody().createFixture(fdef).setUserData(this);
	}

	/**
	 * Contact detection
	 * Allows for the college to take damage
	 */
	@Override
	public void onContact() {
		//Damage the college and lower health bar
		Gdx.app.log("enemy", "collision");
		takeDamage(getDamage());
		getBar().changeHealth(getDamage());
	}

	/**
	 * Fires cannonballs
	 */
	public void fire() {
		cannonBalls.add(new CollegeFire(getScreen(), getBody().getPosition().x, getBody().getPosition().y));
	}
}

