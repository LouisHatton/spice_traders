package com.mygdx.pirategame.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.pirategame.PirateGame;
import com.mygdx.pirategame.display.HUD;
import com.mygdx.pirategame.entity.Player;
import com.mygdx.pirategame.entity.coin.Coin;
import com.mygdx.pirategame.entity.college.College;
import com.mygdx.pirategame.entity.ship.EnemyShip;
import com.mygdx.pirategame.listener.WorldContactListener;
import com.mygdx.pirategame.utils.SpawnUtils;
import com.mygdx.pirategame.utils.WorldCreator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;


/**
 * Game Screen
 * Class to generate the various screens used to play the game.
 * Instantiates all screen types and displays current screen.
 *
 * @author Ethan Alabaster, Adam Crook, Joe Dickinson, Sam Pearson, Tom Perry, Edward Poulter
 * @version 1.0
 */
public class GameScreen implements Screen {
	public static final int GAME_RUNNING = 0;
	public static final int GAME_PAUSED = 1;
	public static PirateGame game;
	private static float maxSpeed = 2.5f;
	private static float accel = 0.05f;
	private static HashMap<String, College> colleges = new HashMap<>();
	private static ArrayList<EnemyShip> ships = new ArrayList<>();
	private static ArrayList<Coin> Coins = new ArrayList<>();
	private static int gameStatus;
	private final Stage stage;
	public Random rand = new Random();
	private float stateTime;
	private final OrthographicCamera camera;
	private final Viewport viewport;
	private final TmxMapLoader maploader;
	private final TiledMap map;
	private final OrthogonalTiledMapRenderer renderer;
	private final World world;
	private final Box2DDebugRenderer b2dr;
	private final Player player;
	private final SpawnUtils invalidSpawn = new SpawnUtils();
	private final HUD hud;
	private Table pauseTable;
	private Table table;

	/**
	 * Initialises the Game Screen,
	 * generates the world data and data for entities that exist upon it,
	 *
	 * @param game passes game data to current class,
	 */
	public GameScreen(PirateGame game) {
		gameStatus = GAME_RUNNING;
		GameScreen.game = game;
		// Initialising camera and extendable viewport for viewing game
		camera = new OrthographicCamera();
		camera.zoom = 0.0155f;
		viewport = new ScreenViewport(camera);
		camera.position.set(viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2, 0);

		// Initialize a hud
		hud = new HUD(game.batch);

		// Initialising box2d physics
		world = new World(new Vector2(0, 0), true);
		b2dr = new Box2DDebugRenderer();
		player = new Player(this);

		// making the Tiled tmx file render as a map
		maploader = new TmxMapLoader();
		map = maploader.load("map.tmx");
		renderer = new OrthogonalTiledMapRenderer(map, 1 / PirateGame.PPM);
		new WorldCreator(this);

		// Setting up contact listener for collisions
		world.setContactListener(new WorldContactListener());

		// Spawning enemy ship and coin. x and y is spawn location
		colleges = new HashMap<>();
		colleges.put("Alcuin", new College(this, "Alcuin", 1900 / PirateGame.PPM, 2100 / PirateGame.PPM,
				"alcuin_flag.png", "alcuin_ship.png", 0, invalidSpawn));
		colleges.put("Anne Lister", new College(this, "Anne Lister", 6304 / PirateGame.PPM, 1199 / PirateGame.PPM,
				"anne_lister_flag.png", "anne_lister_ship.png", 8, invalidSpawn));
		colleges.put("Constantine", new College(this, "Constantine", 6240 / PirateGame.PPM, 6703 / PirateGame.PPM,
				"constantine_flag.png", "constantine_ship.png", 8, invalidSpawn));
		colleges.put("Goodricke", new College(this, "Goodricke", 1760 / PirateGame.PPM, 6767 / PirateGame.PPM,
				"goodricke_flag.png", "goodricke_ship.png", 8, invalidSpawn));
		ships = new ArrayList<>();
		ships.addAll(colleges.get("Alcuin").fleet);
		ships.addAll(colleges.get("Anne Lister").fleet);
		ships.addAll(colleges.get("Constantine").fleet);
		ships.addAll(colleges.get("Goodricke").fleet);

		//Random ships
		Boolean validLoc;
		int a = 0;
		int b = 0;
		for (int i = 0; i < 20; i++) {
			validLoc = false;
			while (!validLoc) {
				//Get random x and y coords
				a = rand.nextInt(SpawnUtils.xCap - SpawnUtils.xBase) + SpawnUtils.xBase;
				b = rand.nextInt(SpawnUtils.yCap - SpawnUtils.yBase) + SpawnUtils.yBase;
				//Check if valid
				validLoc = checkGenPos(a, b);
			}
			//Add a ship at the random coords
			ships.add(new EnemyShip(this, a, b, "unaligned_ship.png", "Unaligned"));
		}

		//Random coins
		Coins = new ArrayList<>();
		for (int i = 0; i < 100; i++) {
			validLoc = false;
			while (!validLoc) {
				//Get random x and y coords
				a = rand.nextInt(SpawnUtils.xCap - SpawnUtils.xBase) + SpawnUtils.xBase;
				b = rand.nextInt(SpawnUtils.yCap - SpawnUtils.yBase) + SpawnUtils.yBase;
				validLoc = checkGenPos(a, b);
			}
			//Add a coins at the random coords
			Coins.add(new Coin(this, a, b));
		}

		//Setting stage
		stage = new Stage(new ScreenViewport());
	}

	/**
	 * Updates acceleration by a given percentage. Accessed by skill tree
	 *
	 * @param percentage percentage increase
	 */
	public static void changeAcceleration(Float percentage) {
		accel = accel * (1 + (percentage / 100));
	}

	/**
	 * Updates max speed by a given percentage. Accessed by skill tree
	 *
	 * @param percentage percentage increase
	 */
	public static void changeMaxSpeed(Float percentage) {
		maxSpeed = maxSpeed * (1 + (percentage / 100));
	}

	/**
	 * Changes the amount of damage done by each hit. Accessed by skill tree
	 *
	 * @param value damage dealt
	 */
	public static void changeDamage(int value) {

		for (int i = 0; i < ships.size(); i++) {
			ships.get(i).changeDamageReceived(value);
		}
		colleges.get("Anne Lister").changeDamageReceived(value);
		colleges.get("Constantine").changeDamageReceived(value);
		colleges.get("Goodricke").changeDamageReceived(value);

	}

	/**
	 * Makes this the current screen for the game.
	 * Generates the buttons to be able to interact with what screen is being displayed.
	 * Creates the escape menu and pause button
	 */
	@Override
	public void show() {
		Gdx.input.setInputProcessor(stage);
		Skin skin = new Skin(Gdx.files.internal("skin/uiskin.json"));

		//GAME BUTTONS
		final TextButton pauseButton = new TextButton("Pause", skin);
		final TextButton skill = new TextButton("Skill Tree", skin);

		//PAUSE MENU BUTTONS
		final TextButton start = new TextButton("Resume", skin);
		final TextButton options = new TextButton("Options", skin);
		TextButton exit = new TextButton("Exit", skin);


		//Create main table and pause tables
		table = new Table();
		table.setFillParent(true);
		stage.addActor(table);

		pauseTable = new Table();
		pauseTable.setFillParent(true);
		stage.addActor(pauseTable);


		//Set the visability of the tables. Particuarly used when coming back from options or skillTree
		if (gameStatus == GAME_PAUSED) {
			table.setVisible(false);
			pauseTable.setVisible(true);
		} else {
			pauseTable.setVisible(false);
			table.setVisible(true);
		}

		//ADD TO TABLES
		table.add(pauseButton);
		table.row().pad(10, 0, 10, 0);
		table.left().top();

		pauseTable.add(start).fillX().uniformX();
		pauseTable.row().pad(20, 0, 10, 0);
		pauseTable.add(skill).fillX().uniformX();
		pauseTable.row().pad(20, 0, 10, 0);
		pauseTable.add(options).fillX().uniformX();
		pauseTable.row().pad(20, 0, 10, 0);
		pauseTable.add(exit).fillX().uniformX();
		pauseTable.center();


		pauseButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				table.setVisible(false);
				pauseTable.setVisible(true);
				pause();

			}
		});
		skill.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				pauseTable.setVisible(false);
				game.changeScreen(PirateGame.SKILL);
			}
		});
		start.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				pauseTable.setVisible(false);
				table.setVisible(true);
				resume();
			}
		});
		options.addListener(new ChangeListener() {
								@Override
								public void changed(ChangeEvent event, Actor actor) {
									pauseTable.setVisible(false);
									game.setScreen(new SettingsScreen(game, game.getScreen()));
								}
							}
		);
		exit.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				Gdx.app.exit();
			}
		});
	}

	/**
	 * Checks for input and performs an action
	 * Applies to keys "W" "A" "S" "D" "E" "Esc"
	 * <p>
	 * Caps player velocity
	 *
	 * @param dt Delta time (elapsed time since last game tick)
	 */
	public void handleInput(float dt) {
		if (gameStatus == GAME_RUNNING) {
			// Left physics impulse on 'A'
			if (Gdx.input.isKeyPressed(Input.Keys.A)) {
				player.getBody().applyLinearImpulse(new Vector2(-accel, 0), player.getBody().getWorldCenter(), true);
			}
			// Right physics impulse on 'D'
			if (Gdx.input.isKeyPressed(Input.Keys.D)) {
				player.getBody().applyLinearImpulse(new Vector2(accel, 0), player.getBody().getWorldCenter(), true);
			}
			// Up physics impulse on 'W'
			if (Gdx.input.isKeyPressed(Input.Keys.W)) {
				player.getBody().applyLinearImpulse(new Vector2(0, accel), player.getBody().getWorldCenter(), true);
			}
			// Down physics impulse on 'S'
			if (Gdx.input.isKeyPressed(Input.Keys.S)) {
				player.getBody().applyLinearImpulse(new Vector2(0, -accel), player.getBody().getWorldCenter(), true);
			}
			// Cannon fire on 'E'
			if (Gdx.input.isKeyJustPressed(Input.Keys.E)) {
				player.fire();
			}
			// Checking if player at max velocity, and keeping them below max
			if (player.getBody().getLinearVelocity().x >= maxSpeed) {
				player.getBody().applyLinearImpulse(new Vector2(-accel, 0), player.getBody().getWorldCenter(), true);
			}
			if (player.getBody().getLinearVelocity().x <= -maxSpeed) {
				player.getBody().applyLinearImpulse(new Vector2(accel, 0), player.getBody().getWorldCenter(), true);
			}
			if (player.getBody().getLinearVelocity().y >= maxSpeed) {
				player.getBody().applyLinearImpulse(new Vector2(0, -accel), player.getBody().getWorldCenter(), true);
			}
			if (player.getBody().getLinearVelocity().y <= -maxSpeed) {
				player.getBody().applyLinearImpulse(new Vector2(0, accel), player.getBody().getWorldCenter(), true);
			}
		}
		if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
			if (gameStatus == GAME_PAUSED) {
				resume();
				table.setVisible(true);
				pauseTable.setVisible(false);
			} else {
				table.setVisible(false);
				pauseTable.setVisible(true);
				pause();
			}
		}
	}

	/**
	 * Updates the state of each object with delta time
	 *
	 * @param dt Delta time (elapsed time since last game tick)
	 */
	public void update(float dt) {
		stateTime += dt;
		handleInput(dt);
		// Stepping the physics engine by time of 1 frame
		world.step(1 / 60f, 6, 2);

		// Update all players and entities
		player.update(dt);
		colleges.get("Alcuin").update(dt);
		colleges.get("Anne Lister").update(dt);
		colleges.get("Constantine").update(dt);
		colleges.get("Goodricke").update(dt);

		//Update ships
		for (int i = 0; i < ships.size(); i++) {
			ships.get(i).update(dt);
		}

		//Updates coin
		for (int i = 0; i < Coins.size(); i++) {
			Coins.get(i).update();
		}
		//After a delay check if a college is destroyed. If not, if can fire
		if (stateTime > 1) {
			if (!colleges.get("Anne Lister").isDestroyed()) {
				colleges.get("Anne Lister").fire();
			}
			if (!colleges.get("Constantine").isDestroyed()) {
				colleges.get("Constantine").fire();
			}
			if (!colleges.get("Goodricke").isDestroyed()) {
				colleges.get("Goodricke").fire();
			}
			stateTime = 0;
		}

		hud.update(dt);

		// Centre camera on player boat
		camera.position.x = player.getBody().getPosition().x;
		camera.position.y = player.getBody().getPosition().y;
		camera.update();
		renderer.setView(camera);
	}

	/**
	 * Renders the visual data for all objects
	 * Changes and renders new visual data for ships
	 *
	 * @param dt Delta time (elapsed time since last game tick)
	 */
	@Override
	public void render(float dt) {
		if (gameStatus == GAME_RUNNING) {
			update(dt);
		} else {
			handleInput(dt);
		}

		Gdx.gl.glClearColor(46 / 255f, 204 / 255f, 113 / 255f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		renderer.render();
		// b2dr is the hitbox shapes, can be commented out to hide
		//b2dr.render(world, camera.combined);

		game.batch.setProjectionMatrix(camera.combined);
		game.batch.begin();
		// Order determines layering

		//Renders coins
		for (int i = 0; i < Coins.size(); i++) {
			Coins.get(i).draw(game.batch);
		}

		//Renders colleges
		player.draw(game.batch);
		colleges.get("Alcuin").draw(game.batch);
		colleges.get("Anne Lister").draw(game.batch);
		colleges.get("Constantine").draw(game.batch);
		colleges.get("Goodricke").draw(game.batch);

		//Updates all ships
		for (int i = 0; i < ships.size(); i++) {
			if (ships.get(i).college != "Unaligned") {
				//Flips a colleges allegence if their college is destroyed
				if (colleges.get(ships.get(i).college).isDestroyed()) {

					ships.get(i).updateTexture("Alcuin", "alcuin_ship.png");
				}
			}
			ships.get(i).draw(game.batch);
		}
		game.batch.end();
		HUD.stage.draw();
		stage.act();
		stage.draw();
		//Checks game over conditions
		gameOverCheck();
	}

	/**
	 * Changes the camera size, Scales the hud to match the camera
	 *
	 * @param width  the width of the viewable area
	 * @param height the height of the viewable area
	 */
	@Override
	public void resize(int width, int height) {
		viewport.update(width, height);
		stage.getViewport().update(width, height, true);
		HUD.resize(width, height);
		camera.update();
		renderer.setView(camera);
	}

	/**
	 * Returns the map
	 *
	 * @return map : returns the world map
	 */
	public TiledMap getMap() {
		return map;
	}

	/**
	 * Returns the world (map and objects)
	 *
	 * @return world : returns the world
	 */
	public World getWorld() {
		return world;
	}

	/**
	 * Returns the college from the colleges hashmap
	 *
	 * @param collegeName uses a college name as an index
	 * @return college : returns the college fetched from colleges
	 */
	public College getCollege(String collegeName) {
		return colleges.get(collegeName);
	}

	/**
	 * Checks if the game is over
	 * i.e. goal reached (all colleges bar "Alcuin" are destroyed)
	 */
	public void gameOverCheck() {
		//Lose game if ship on 0 health or Alcuin is destroyed
		if (HUD.getHealth() <= 0 || colleges.get("Alcuin").isDestroyed()) {
			game.changeScreen(PirateGame.DEATH);
			game.killGame();
		}
		//Win game if all colleges destroyed
		else if (colleges.get("Anne Lister").isDestroyed() && colleges.get("Constantine").isDestroyed() && colleges.get("Goodricke").isDestroyed()) {
			game.changeScreen(PirateGame.VICTORY);
			game.killGame();
		}
	}

	/**
	 * Fetches the player's current position
	 *
	 * @return position vector : returns the position of the player
	 */
	public Vector2 getPlayerPos() {
		return new Vector2(player.getBody().getPosition().x, player.getBody().getPosition().y);
	}

	/**
	 * Tests validity of randomly generated position
	 *
	 * @param x random x value
	 * @param y random y value
	 */
	private Boolean checkGenPos(int x, int y) {
		if (invalidSpawn.tileBlocked.containsKey(x)) {
			ArrayList<Integer> yTest = invalidSpawn.tileBlocked.get(x);
			return !yTest.contains(y);
		}
		return true;
	}

	/**
	 * Pauses game
	 */
	@Override
	public void pause() {
		gameStatus = GAME_PAUSED;
	}

	/**
	 * Resumes game
	 */
	@Override
	public void resume() {
		gameStatus = GAME_RUNNING;
	}

	/**
	 * (Not Used)
	 * Hides game
	 */
	@Override
	public void hide() {

	}

	/**
	 * Disposes game data
	 */
	@Override
	public void dispose() {
		map.dispose();
		renderer.dispose();
		world.dispose();
		b2dr.dispose();
		hud.dispose();
		stage.dispose();
	}
}
