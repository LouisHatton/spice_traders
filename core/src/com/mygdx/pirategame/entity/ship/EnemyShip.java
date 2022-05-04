package com.mygdx.pirategame.entity.ship;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.utils.Array;
import com.mygdx.pirategame.PirateGame;
import com.mygdx.pirategame.display.HUD;
import com.mygdx.pirategame.entity.Enemy;
import com.mygdx.pirategame.entity.cannon.CannonFire;
import com.mygdx.pirategame.entity.cannon.CannonManager;
import com.mygdx.pirategame.screen.ActiveGameScreen;

/**
 * Enemy Ship
 * Generates enemy ship data
 * Instantiates an enemy ship
 *
 * @author Faris Alblooki
 * @version 2.0
 */
public class EnemyShip extends Enemy {

	public static Rectangle detectBox;
	public transient static EnemyShip NPCTarget;
	private final Sound destroy;
	private final Sound hit;
	public String college;
	public Rectangle shootBox;
	public Rectangle stoppingDistanceBox;
	public Rectangle leaveBox;
	public Rectangle enemyDetectBox;
	public boolean isFollowing = false;
	Body body;
	private transient Texture enemyShip;
	private Array<CannonFire> cannonBalls = new Array<CannonFire>();
	private float maxLinearSpeed = 5;
	private float firingCoolDown = 0;
	private float ogFiringCoolDown = 0.5f;

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


		this.detectBox = new com.badlogic.gdx.math.Rectangle(body.getPosition().x, body.getPosition().y, 11, 11);
		this.enemyDetectBox = new com.badlogic.gdx.math.Rectangle(body.getPosition().x, body.getPosition().y, 1f, 1f);
		this.stoppingDistanceBox = new com.badlogic.gdx.math.Rectangle(body.getPosition().x, body.getPosition().y, 5.5f, 5.5f);
		this.shootBox = new com.badlogic.gdx.math.Rectangle(body.getPosition().x, body.getPosition().y, 7, 7);
		this.leaveBox = new com.badlogic.gdx.math.Rectangle(body.getPosition().x, body.getPosition().y, 17, 17);
		setBody(body);
		super.initHealthBar();

		setDamage(20);

		body.setLinearDamping(1);

		setCollege(college);
	}

	public static void setNPCTarget(EnemyShip target) {
		NPCTarget = target;

	}

	/**
	 * Updates the state of each object with delta time
	 * Checks for ship destruction
	 *
	 * @param dt Delta time (elapsed time since last game tick)
	 */
	public void update(float dt) {
		if(getHealth() <= 0){
			setToDestroy(true);
		}
		if (isSetToDestroy() && !isDestroyed()) {
			//Play death noise
			if (ActiveGameScreen.game.getPreferences().isEffectsEnabled()) {
				destroy.play(ActiveGameScreen.game.getPreferences().getEffectsVolume());
			}
			getWorld().destroyBody(body);
			setDestroyed(true);
			//Change player coins and points
			HUD.changePoints(20);
			HUD.changeCoins(10);
			ActiveGameScreen.player.setBoatsKilled(1);
			ActiveGameScreen.player.ultimateAmount += 5 * ActiveGameScreen.player.ultimateAmountMultiplier;
		}
		getBar().update();
		if (ActiveGameScreen.player.stopFollowing && isFollowing) {
			isFollowing = false;
			ActiveGameScreen.player.numberOfShipsFollowing = 0;
			if (ActiveGameScreen.player.rayLvl2) {
				takeDamage(getMaxHealth() / 2);
			}
		}
		if (college == "Alcuin" && isFollowing) {
			isFollowing = false;
			ActiveGameScreen.player.numberOfShipsFollowing--;
		}
		for (CannonFire ball : cannonBalls) {
			ball.update(dt);
			if (ball.isDestroyed())
				cannonBalls.removeValue(ball, true);
		}


		if (firingCoolDown > 0) {
			firingCoolDown -= dt;
		}


		this.detectBox.setCenter(this.body.getPosition());
		this.stoppingDistanceBox.setCenter(this.body.getPosition());
		this.shootBox.setCenter(this.body.getPosition());
		this.leaveBox.setCenter(this.body.getPosition());
		//If ship is set to destroy and isnt, destroy it
		 if (!isDestroyed() && !isSetToDestroy()) {
			//Update position and angle of ship
			 //code below is the AI it checks if the player is within a certain range and applies the range type to the player (if in shooting range -> shoot)
			setPosition(body.getPosition().x - getWidth() / 2f, body.getPosition().y - getHeight() / 2f);
			float angle = (float) Math.atan2(body.getLinearVelocity().y, body.getLinearVelocity().x);
			if ((this.detectBox.overlaps(ActiveGameScreen.player.hitBox) || isFollowing) && college != "Alcuin" && !ActiveGameScreen.player.stopFollowing) {
				if (ActiveGameScreen.player.numberOfShipsFollowing < ActiveGameScreen.player.maxNumberOfShipsFollowing) {
					if (!isFollowing) ActiveGameScreen.player.numberOfShipsFollowing++;
					isFollowing = true;
				}
				if (!isFollowing) return;
				if (!this.stoppingDistanceBox.overlaps(ActiveGameScreen.player.hitBox)) {
					followTarget(ActiveGameScreen.player.getBody());
				} else {
					if (firingCoolDown <= 0) {
						shootTarget(ActiveGameScreen.player.getBody());
						firingCoolDown = ogFiringCoolDown;
					}
				}
				if (this.shootBox.overlaps(ActiveGameScreen.player.hitBox)) {
					if (firingCoolDown <= 0) {
						shootTarget(ActiveGameScreen.player.getBody());
						firingCoolDown = ogFiringCoolDown;
					}
				}
				if (!this.leaveBox.overlaps(ActiveGameScreen.player.hitBox)) {
					isFollowing = false;
					ActiveGameScreen.player.numberOfShipsFollowing--;
				}

			} else if (NPCTarget != null) {

				if ((this.detectBox.overlaps(NPCTarget.detectBox) || !isFollowing) && college != NPCTarget.college) {
					if (!this.stoppingDistanceBox.overlaps(NPCTarget.stoppingDistanceBox)) {
						this.followTarget(NPCTarget.getBody());
					} else {
						if (firingCoolDown <= 0) {
							shootTarget(NPCTarget.getBody());
							firingCoolDown = ogFiringCoolDown;
						}
					}
					if (this.shootBox.overlaps(NPCTarget.shootBox)) {
						if (firingCoolDown <= 0) {
							shootTarget(NPCTarget.getBody());
							firingCoolDown = ogFiringCoolDown;
						}
					}


				}

			} else {
				body.setTransform(body.getWorldCenter(), angle - ((float) Math.PI) / 2.0f);
			}

			setRotation((float) (body.getAngle() * 180 / Math.PI));
			//Update health bar
			getBar().update();
		}
		if (getHealth() <= 0) {
			if (isFollowing) {
				ActiveGameScreen.player.numberOfShipsFollowing--;
				isFollowing = false;
			}
			setToDestroy(true);
		}

		// below code is to move the ship to a coordinate (target)
		//Vector2 target = new Vector2(960 / PirateGame.PPM, 2432 / PirateGame.PPM);
		//target.sub(body.getPosition());
		//target.nor();
		//float speed = 1.5f;
		//body.setLinearVelocity(target.scl(speed));
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
		body = getWorld().createBody(bdef);

		//Sets collision boundaries
		FixtureDef fdef = new FixtureDef();
		CircleShape shape = new CircleShape();
		shape.setRadius(55 / PirateGame.PPM);
		// setting BIT identifier
		fdef.filter.categoryBits = PirateGame.ENEMY_BIT;
		// determining what this BIT can collide with
		fdef.filter.maskBits = PirateGame.DEFAULT_BIT | PirateGame.PLAYER_BIT | PirateGame.ENEMY_BIT | PirateGame.CANNON_BIT | PirateGame.COLLEGEFIRE_BIT;
		fdef.shape = shape;
		fdef.restitution = 0.7f;
		body.createFixture(fdef).setUserData(this);
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

	public void followTarget(Body target) {
		if (this.getBody().getLinearVelocity().len() < maxLinearSpeed)
			this.body.applyForceToCenter(this.body.getWorldVector(new Vector2(0, 3)), true);
		float newOrientation = (float) Math.atan2(this.body.getPosition().x - target.getPosition().x, target.getPosition().y - this.body.getPosition().y);
		body.setTransform(body.getPosition(), newOrientation);
	}

	public void shootTarget(Body target) {
		CannonManager.insert(new CannonFire(getScreen(), getBody().getPosition().x, getBody().getPosition().y, getBody(), 0, target.getPosition(), 0, 0.7f, (short) (PirateGame.ENEMY_BIT | PirateGame.PLAYER_BIT), PirateGame.COLLEGEFIRE_BIT, college));
	}

}