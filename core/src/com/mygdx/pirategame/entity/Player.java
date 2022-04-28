package com.mygdx.pirategame.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.utils.Array;
import com.mygdx.pirategame.PirateGame;
import com.mygdx.pirategame.display.HUD;
import com.mygdx.pirategame.entity.cannon.CannonFire;
import com.mygdx.pirategame.screen.ActiveGameScreen;
import com.mygdx.pirategame.screen.ShopScreen;
import com.mygdx.pirategame.screen.SkillsScreen;

/**
 * Creates the class of the player. Everything that involves actions coming from the player boat
 *
 * @author Ethan Alabaster, Edward Poulter
 * @version 1.0
 */
public class Player extends Entity {
	public static boolean healBubble = false;
	public static boolean burstHeal = false;
	public static boolean bursted = false;
	public static boolean ultimateFirerEnabled = false;
	public static boolean isBloodied = false;
	public static int amountOfShotsInUltimateFire = 10;
	public static int burstAmountForUltimateFire = 1;
	public static float ultimateAmount = 0;
	public static float ultimateAmountMultiplier = 1f;
	public static float collegesCaptured = 0;
	public static float boatsKilled = 0;
	public static float collegesKilled = 0;
	public static boolean shieldEnabled = false;
	public static float protectedTimer = 0f;
	public static float normalNumberOfShips = 4;
	public static float numberOfShipsFollowing = 0;
	public static float maxNumberOfShipsFollowing = 4;
	public static float disablingRayCooldownOg = 6f;
	public static boolean rayLvl2 = false;
	public static boolean stopFollowing = false;
	static float cannonBallSpeedLvl = 0;
	static float fireRateLvl = 0;
	static float rangeMultiplier = 0.7f;
	static boolean fireRateChanged = false;
	private final Texture ship;
	private final Sound breakSound;
	private final Array<CannonFire> cannonBalls;
	public Rectangle hitBox;
	public float shieldCoolDown = 0f;
	public float shieldCoolDownOG = 8f;
	public float protectTime = 2f;
	public boolean rayEnabled = false;
	float firingCoolDown = 0.2f;
	float ogFiringCoolDown = 0.2f;
	int burstShotsUF = 0;
	float ultimateBurstCoolDown = 0f;
	float ultimateBurstOGCoolDown = 0.5f;
	Sprite shield = new Sprite(new Texture("bubble.png"));
	Sprite emp = new Sprite(new Texture("EMP.png"));
	float ultimateTimer = 0.5f;
	float disablingRayCooldown = 0f;
	float rayActiveFor = 0;
	boolean empExplosion = false;
	float empExplosionCooldown = 0;
	float empExplosionCooldownOG = 0.4f;
	boolean empSet = false;
	private float turnDirection;
	private float turnSpeed;
	private float driveDirection;
	private float driftFactor;
	private float maximumSpeed;
	private float originalSpeed;
	private float speed;
	private Camera cam;


	/**
	 * Instantiates a new Player. Constructor only called once per game
	 *
	 * @param screen visual data
	 */
	public Player(ActiveGameScreen screen, float spawnSpeed, float maxSpeed, float driftFactor, float turnSpeed, Camera cam) {
		super(screen, 0, 0);

		this.originalSpeed = spawnSpeed;
		this.cam = cam;
		this.maximumSpeed = maxSpeed;
		this.driftFactor = driftFactor;
		this.turnSpeed = turnSpeed;
		this.turnDirection = 0;
		this.driveDirection = 0;
		this.speed = 80f;
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


		this.hitBox = new Rectangle(getBody().getPosition().x, getBody().getPosition().y, 64 / PirateGame.PPM, 110 / PirateGame.PPM);
		shield.setSize(2f, 2f);
	}

	public static void upgradeCannonBallSpeed() {
		cannonBallSpeedLvl++;
	}

	public static void upgradeFireRate() {
		fireRateLvl++;
		fireRateChanged = true;
	}

	public static void upgradeRange(float multiplier) {
		rangeMultiplier += multiplier;
	}

	public static void resetStats() {
		normalNumberOfShips = 4;
		numberOfShipsFollowing = 0;
		maxNumberOfShipsFollowing = 4;
		collegesCaptured = 0;
		boatsKilled = 0;
		collegesKilled = 0;
		PirateGame.difficultyMultiplier = 0;
		PirateGame.difficultySet = false;
		ShopScreen.resetStats();
		HUD.respawnProtection = 4f;
	}

	/**
	 * Update the position of the player. Also updates any cannon balls the player generates
	 *
	 * @param dt Delta Time
	 */
	public void update(float dt) {
		emp.setCenter(getBody().getPosition().x, getBody().getPosition().y);
		if (empExplosion) {
			if (!empSet) {

				empExplosionCooldown = empExplosionCooldownOG;
				emp.setSize(0.5f, 0.5f);
				empSet = true;
			}
			empExplosionCooldown -= dt;

			if (empExplosionCooldown <= 0) {
				empExplosion = false;
			}
			emp.setSize(emp.getWidth() + (emp.getWidth() / 2), emp.getHeight() + (emp.getHeight() / 2));
		}


		if (rayEnabled) {
			if (stopFollowing) {
				rayActiveFor -= dt;
				if (rayActiveFor <= 0) {
					stopFollowing = false;
				}
			}
			if (Gdx.input.isKeyJustPressed(Input.Keys.SHIFT_LEFT) && disablingRayCooldown <= 0) {
				disablingRayCooldown = disablingRayCooldownOg;
				stopFollowing = true;
				rayActiveFor = 3f;

				empExplosion = true;
				empSet = false;
			}
			if (disablingRayCooldown >= 0) {
				disablingRayCooldown -= dt;
			}
		}


		if (ultimateTimer < 0) {
			ultimateAmount += 1 * ultimateAmountMultiplier;
			ultimateTimer = 0.5f;
		} else {
			ultimateTimer -= dt;
		}

		if (!ultimateFirerEnabled) ultimateAmount = 100;

		//System.out.println(ultimateAmount);

		maxNumberOfShipsFollowing = normalNumberOfShips * PirateGame.difficultyMultiplier;

		shield.setCenter(this.getBody().getPosition().x, this.getBody().getPosition().y);


		maxNumberOfShipsFollowing = normalNumberOfShips * PirateGame.difficultyMultiplier;
		//System.out.println(numberOfShipsFollowing);


		this.hitBox.setPosition(this.getBody().getPosition());
		if (shieldEnabled) {
			if (protectedTimer > 0) {
				protectedTimer -= dt;
				if (!bursted && burstHeal) {
					HUD.changeHealth((int) (HUD.maxHealth * 0.10f));
					bursted = true;
				}
				if (shield.getHeight() < 2.7f) {
					shield.setSize(shield.getWidth() + 0.1f, shield.getHeight() + 0.1f);
				}
			}
			if (Gdx.input.isKeyJustPressed(Input.Keys.E) && shieldCoolDown <= 0) {
				protectedTimer = protectTime;
				shieldCoolDown = shieldCoolDownOG;
				bursted = false;
			}
			if (shieldCoolDown > 0) {
				shieldCoolDown -= dt;
				if (protectedTimer <= 0) {
					shield.setSize(2f, 2f);
				}
			}
		}


		if (fireRateChanged) {
			ogFiringCoolDown = ogFiringCoolDown / (fireRateLvl * 0.1f);
			fireRateChanged = false;
		}
		// Updates position and orientation of player
		setPosition(getBody().getPosition().x - getWidth() / 2f, getBody().getPosition().y - getHeight() / 2f);
		//float angle = (float) Math.atan2(getBody().getLinearVelocity().y, getBody().getLinearVelocity().x);
		//getBody().setTransform(getBody().getWorldCenter(), angle - ((float) Math.PI) / 2.0f);
		setRotation((float) (getBody().getAngle() * 180 / Math.PI));

		// Updates cannonball data
		for (CannonFire ball : cannonBalls) {
			ball.update(dt);
			if (ball.isDestroyed())
				cannonBalls.removeValue(ball, true);
		}

		if (Gdx.input.isButtonPressed(Input.Buttons.LEFT) && firingCoolDown <= 0) {
			fire();
			firingCoolDown = ogFiringCoolDown;
		} else if (firingCoolDown > 0) {
			firingCoolDown -= dt;
		}

		if (Gdx.input.isKeyJustPressed(Input.Keys.Q) && ultimateAmount >= 100 && ultimateFirerEnabled) {
			burstShotsUF = burstAmountForUltimateFire;
			ultimateAmount = 0;
		}

		if (ultimateBurstCoolDown <= 0) {
			if (burstShotsUF > 0) {
				ultimateFirer();
			}
		} else {
			ultimateBurstCoolDown -= Gdx.graphics.getDeltaTime();
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

	public void changeMaxSpeed(float percentage) {
		this.maximumSpeed = this.maximumSpeed * (1 + (percentage / 100));
		this.turnSpeed = this.turnSpeed * (1 + (percentage / 100));
		this.originalSpeed = this.originalSpeed * (1 + (percentage / 100));
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

	public void burstShot() {

	}

	/**
	 * Called when E is pushed. Causes 1 cannon ball to spawn on both sides of the ships wih their relative velocity
	 */
	public void fire() {
		// Fires cannons
		Vector3 mouse_position = new Vector3(0, 0, 0);
		mouse_position.set(Gdx.input.getX(), Gdx.input.getY(), 0); /** gets mouse position*/
		cam.unproject(mouse_position);
		cannonBalls.add(new CannonFire(getScreen(), getBody().getPosition().x, getBody().getPosition().y, getBody(), 0, new Vector2(mouse_position.x, mouse_position.y), cannonBallSpeedLvl, rangeMultiplier, (short) (PirateGame.ENEMY_BIT | PirateGame.PLAYER_BIT | PirateGame.COLLEGE_BIT), PirateGame.CANNON_BIT));


		// Cone fire below
        /*cannonBalls.add(new CannonFire(screen, getBody().getPosition().x, getBody().getPosition().y, (float) (getBody().getAngle() - Math.PI / 6), -5, getBody().getLinearVelocity()));
        cannonBalls.add(new CannonFire(screen, getBody().getPosition().x, getBody().getPosition().y, (float) (getBody().getAngle() - Math.PI / 6), 5, getBody().getLinearVelocity()));
        cannonBalls.add(new CannonFire(screen, getBody().getPosition().x, getBody().getPosition().y, (float) (getBody().getAngle() + Math.PI / 6), -5, getBody().getLinearVelocity()));
        cannonBalls.add(new CannonFire(screen, getBody().getPosition().x, getBody().getPosition().y, (float) (getBody().getAngle() + Math.PI / 6), 5, getBody().getLinearVelocity()));
        }
         */
	}

	public void ultimateFirer() {
		if (!ultimateFirerEnabled) return;
		for (int i = 0; i <= amountOfShotsInUltimateFire; i++) {
			cannonBalls.add(new CannonFire(getScreen(), getBody().getPosition().x, getBody().getPosition().y, getBody(), 0, i * (360 / amountOfShotsInUltimateFire), cannonBallSpeedLvl, rangeMultiplier, (short) (PirateGame.ENEMY_BIT | PirateGame.PLAYER_BIT | PirateGame.COLLEGE_BIT), PirateGame.CANNON_BIT));

		}
		burstShotsUF--;
		ultimateBurstCoolDown = ultimateBurstOGCoolDown;
	}

	public Vector2 getForwardVelocity() {
		Vector2 currentNormal = this.getBody().getWorldVector(new Vector2(0, 1));
		float dotProduct = currentNormal.dot(this.getBody().getLinearVelocity());

		return multiply(dotProduct, currentNormal);
	}

	public Vector2 multiply(float a, Vector2 v) {
		return new Vector2(a * v.x, a * v.y);
	}

	public Vector2 getLateralVelocity() {
		Vector2 currentNormal = this.getBody().getWorldVector(new Vector2(1, 0));
		float dotProduct = currentNormal.dot(this.getBody().getLinearVelocity());

		return multiply(dotProduct, currentNormal);
	}

	public float getTurnDirection() {
		return this.turnDirection;
	}

	public void setTurnDirection(float turnDirection) {
		this.turnDirection = turnDirection;
	}

	public float getTurnSpeed() {
		return this.turnSpeed;
	}

	public float getDriveDirection() {
		return this.driveDirection;
	}

	public void setDriveDirection(float driveDirection) {
		this.driveDirection = driveDirection;
	}

	public float getDriftFactor() {
		return this.driftFactor;
	}

	public float getSpeed() {
		return this.speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

	public float getOriginalSpeed() {
		return this.originalSpeed;
	}

	public float getMaximumSpeed() {
		return this.maximumSpeed;
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

		if (protectedTimer > 0) shield.draw(batch);
		if (empExplosion) emp.draw(batch);

		for (CannonFire ball : cannonBalls)
			ball.draw(batch);
	}

	public float getCollegesCaptured() {
		return collegesCaptured;
	}

	public void setCollegesCaptured(float collegesCaptured) {
		this.collegesCaptured += collegesCaptured;
		updateStats();
	}

	public float getCollegesKilled() {
		return collegesKilled;
	}

	public void setCollegesKilled(float collegesKilled) {
		this.collegesKilled += collegesKilled;
		updateStats();
	}

	public float getBoatsKilled() {
		return boatsKilled;
	}

	public void setBoatsKilled(float boatsKilled) {
		this.boatsKilled += boatsKilled;
		updateStats();
	}

	void updateStats() {
		if (boatsKilled >= 5) {
			SkillsScreen.unlock(2);
			shieldEnabled = true;

			if (boatsKilled >= 7) {
				protectTime = 3f;
			}
			if (boatsKilled >= 9) {
				burstHeal = true;
			}
			if (boatsKilled >= 11) {
				protectTime = 4f;
			}
			if (boatsKilled >= 13) {
				shieldCoolDownOG = 7;
			}
			if (boatsKilled >= 15) {
				healBubble = true;
			}


		} else {
			SkillsScreen.lock(2);
		}
		if (collegesKilled >= 1) {
			SkillsScreen.unlock(0);
			if (collegesKilled >= 2) HUD.bloodyAmount = 0.75f;
			isBloodied = true;
		} else {
			SkillsScreen.lock(0);
		}
		if (collegesCaptured >= 1) {
			SkillsScreen.unlock(1);
			rayEnabled = true;
			if (collegesCaptured >= 2) {
				rayLvl2 = true;
			} else {
				rayLvl2 = false;
			}
		} else {
			rayEnabled = false;
			rayLvl2 = false;
			SkillsScreen.lock(1);
		}
	}
}