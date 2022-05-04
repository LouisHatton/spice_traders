package com.mygdx.pirategame.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.pirategame.PirateGame;
import com.mygdx.pirategame.display.HUD;
import com.mygdx.pirategame.entity.Player;
import com.mygdx.pirategame.entity.cannon.CannonManager;
import com.mygdx.pirategame.entity.coin.Coin;
import com.mygdx.pirategame.entity.college.College;
import com.mygdx.pirategame.entity.college.CollegeType;
import com.mygdx.pirategame.entity.ship.EnemyShip;
import com.mygdx.pirategame.listener.WorldContactListener;
import com.mygdx.pirategame.utils.Location;
import com.mygdx.pirategame.utils.Persistence;
import com.mygdx.pirategame.utils.SpawnUtils;
import com.mygdx.pirategame.utils.WorldCreator;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;


/**
 * Game Screen
 * Class to generate the various screens used to play the game.
 * Instantiates all screen types and displays current screen.
 *
 * @author Faris Alblooki
 * @version 2.0
 */
public class ActiveGameScreen implements Screen {

	public static final int GAME_RUNNING = 0;
	public static final int GAME_PAUSED = 1;
	public static PirateGame game;
	public static List<Coin> Coins = new ArrayList<>();
	public static Player player;
	public static Rectangle BoundsAL = new Rectangle();
	public static Rectangle BoundsC = new Rectangle();
	public static Rectangle BoundsG = new Rectangle();
	private static float accel = 0.05f;
	protected static Map<String, College> colleges = new HashMap<>();
	public static List<EnemyShip> ships = new ArrayList<>();
	protected static int gameStatus;
	private final Stage stage;
	private final OrthographicCamera camera;
	private final Viewport viewport;
	private final TmxMapLoader maploader;
	private final TiledMap map;
	private final OrthogonalTiledMapRenderer renderer;
	protected final World world;
	private final Box2DDebugRenderer b2dr;
	public HUD hud;
	float zoomAmount = 0;
	private float stateTime;
	private Table pauseTable;
	private Table table;
	private Table saveTable;
	private TextButton pauseButton;
	private TextButton skillButton;
	private TextButton shopButton;
	private TextButton difficultyButton;
	private TextButton startButton;
	private TextButton optionsButton;
	private TextButton exitButton;
	private TextButton saveButton;
	private TextButton yes;
	private TextButton no;
	private TextButton cancel;
	Label savedGame;
	float saveTimer = 0f;
	private Box2DDebugRenderer debugger;
	private ShaderProgram shader;
	SpriteBatch batch;
	public static boolean badWeather = false;
	public static Music weatherSoundEffect;
	Label wantToSave;
	Table wantToSaveTable;
	Label careful;
	Table carefulMsg = new Table();
	boolean gameSaved = false;
	boolean onSaveMenu = false;
	public static float carefulTimer = 0f;
	public static boolean carefulNotSet = true;

	/**
	 * Initialises the Game Screen,
	 * generates the world data and data for entities that exist upon it,
	 *
	 * @param game passes game data to current class,
	 */
	public ActiveGameScreen(PirateGame game) {

		badWeather = false;
		gameStatus = GAME_RUNNING;
		ActiveGameScreen.game = game;
		// Initialising camera and extendable viewport for viewing game
		camera = new OrthographicCamera();
		camera.zoom = 0.0155f * 2f;
		viewport = new ScreenViewport(camera);
		camera.position.set(viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2, 0);

		// Initialize a hud
		hud = new HUD(new Stage(new ScreenViewport(), game.batch));

		// Initialising box2d physics
		world = new World(new Vector2(0, 0), true);
		b2dr = new Box2DDebugRenderer();
		player = new Player(this, 1.3f, 3f, 0.3f, 3f, camera);

		// making the Tiled tmx file render as a map
		maploader = new TmxMapLoader();
		map = maploader.load("map.tmx");

		renderer = new OrthogonalTiledMapRenderer(map, 1 / PirateGame.PPM);

		new WorldCreator(this);

		// Setting up contact listener for collisions
		world.setContactListener(new WorldContactListener());

		// Spawning enemy ship and coin. x and y is spawn location
		colleges = new HashMap<>();
		colleges.put("Alcuin", new College(this, CollegeType.ALCUIN));
		colleges.put("Anne Lister", new College(this, CollegeType.ANNE_LISTER));
		colleges.put("Constantine", new College(this, CollegeType.CONSTANTINE));
		colleges.put("Goodricke", new College(this, CollegeType.GOODRICKE));

		ships = colleges.values().stream().flatMap(col -> col.getFleet().stream()).collect(Collectors.toList());
		ships.addAll(this.generateShips(20));

		//Random coins
		Coins = this.generateCoins(60);

		//Setting stage
		batch = new SpriteBatch();
		stage = new Stage(new ScreenViewport());


		BoundsG.setCenter(1760 / PirateGame.PPM, 6767 / PirateGame.PPM);
		BoundsAL.setCenter(5304 / PirateGame.PPM, 899 / PirateGame.PPM);
		BoundsC.setCenter(6240 / PirateGame.PPM, 6703 / PirateGame.PPM);
		BoundsC.setSize(35.5f, 50.5f);
		BoundsAL.setSize(35.5f, 50.5f);
		BoundsG.setSize(35.5f, 50.5f);

		debugger = new Box2DDebugRenderer();
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
	public static void changeMaxSpeed(float percentage) {
		player.changeMaxSpeed(percentage);
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

		colleges.values().forEach(col -> {
			if (col.getType().equals(CollegeType.ALCUIN)) {
				return;
			}

			col.changeDamageReceived(value);
		});
	}

	/**
	 * @return All current ships, alive and dead.
	 */
	public static List<EnemyShip> getShips() {
		return ships;
	}

	/**
	 * Makes this the current screen for the game.
	 * Generates the buttons to be able to interact with what screen is being displayed.
	 * Creates the escape menu and pause button
	 */
	@Override
	public void show() {
		weatherSoundEffect = Gdx.audio.newMusic(Gdx.files.internal("thunderstorm.mp3"));
		ShaderProgram.pedantic = false;
		shader = new ShaderProgram(Gdx.files.internal("vignette.vsh"), Gdx.files.internal("vignette.fsh"));
		System.out.println(shader.isCompiled() ? "yay" : shader.getLog());
		savedGame = new Label("Game Saved!", new Label.LabelStyle(new BitmapFont(Gdx.files.internal("textFont.fnt")), PirateGame.selectedColour2));
		wantToSave = new Label("Save Game?", new Label.LabelStyle(new BitmapFont(Gdx.files.internal("textFont.fnt")), PirateGame.selectedColour2));
		careful = new Label("Careful! Dont shoot your own college! Killing your own college will result in a defeat!", new Label.LabelStyle(new BitmapFont(Gdx.files.internal("textFont.fnt")), PirateGame.selectedColour2));



		Gdx.input.setInputProcessor(stage);
		Skin skin = new Skin(Gdx.files.internal("skin/uiskin.json"));

		yes = new TextButton("Yes!", skin);
		no = new TextButton("No, I do not want to Save!", skin);
		//GAME BUTTONS
		this.pauseButton = new TextButton("Pause", skin);
		this.skillButton = new TextButton("Skill Tree", skin);
		this.shopButton = new TextButton("Shop", skin);
		this.difficultyButton = new TextButton("Change Difficulty", skin);
		this.cancel = new TextButton("Cancel", skin);

		//PAUSE MENU BUTTONS
		this.startButton = new TextButton("Resume", skin);
		this.optionsButton = new TextButton("Options", skin);
		this.exitButton = new TextButton("Exit", skin);
		this.saveButton = new TextButton("Save Game", skin);

		startButton.getStyle().fontColor = PirateGame.selectedColour;


		//Create main table and pause tables
		table = new Table();
		saveTable = new Table();
		table.setFillParent(true);
		saveTable.setFillParent(true);
		stage.addActor(table);
		stage.addActor(saveTable);

		pauseTable = new Table();
		pauseTable.setFillParent(true);
		stage.addActor(pauseTable);
		wantToSaveTable = new Table();
		wantToSaveTable.setFillParent(true);
		wantToSaveTable.setVisible(false);
		carefulMsg = new Table();
		carefulMsg.setFillParent(true);
		carefulMsg.setVisible(false);
		stage.addActor(wantToSaveTable);
		stage.addActor(carefulMsg);

		saveTable.setVisible(false);
		saveTable.center().top();
		carefulMsg.center().top();

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

		pauseTable.add(this.startButton).fillX().uniformX();
		pauseTable.row().pad(20, 0, 10, 0);
		pauseTable.add(this.saveButton).fillX().uniformX();
		pauseTable.row().pad(20, 0, 10, 0);
		pauseTable.add(this.skillButton).fillX().uniformX();
		pauseTable.row().pad(20, 0, 10, 0);
		pauseTable.add(this.shopButton).fillX().uniformX();
		pauseTable.row().pad(20, 0, 10, 0);
		pauseTable.add(this.difficultyButton).fillX().uniformX();
		pauseTable.row().pad(20, 0, 10, 0);
		pauseTable.add(this.optionsButton).fillX().uniformX();
		pauseTable.row().pad(20, 0, 10, 0);
		pauseTable.add(this.exitButton).fillX().uniformX();


		wantToSaveTable.center();
		wantToSaveTable.add(wantToSave).padBottom(80);
		wantToSaveTable.row();
		wantToSaveTable.add(yes);
		wantToSaveTable.row();
		wantToSaveTable.add(no);
		wantToSaveTable.row();
		wantToSaveTable.add(cancel);

		saveTable.add(savedGame);
		carefulMsg.add(careful).padTop(25f);


		this.pauseButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				table.setVisible(false);
				pauseTable.setVisible(true);
				pause();

			}
		});

		this.skillButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				pauseTable.setVisible(false);
				game.changeScreen(PirateGame.SKILL);
			}
		});

		this.yes.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				save();
				player.resetStats();
				pauseTable.setVisible(false);
				game.changeScreen(PirateGame.MENU);
				game.killGame();
				player.resetStats();
			}
		});

		this.no.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				player.resetStats();
				pauseTable.setVisible(false);
				game.changeScreen(PirateGame.MENU);
				game.killGame();
				player.resetStats();
			}
		});

		this.shopButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				pauseTable.setVisible(false);
				game.changeScreen(PirateGame.SHOP);
			}
		});

		this.difficultyButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				pauseTable.setVisible(false);
				game.changeScreen(PirateGame.DIFFICULTY);
			}
		});

		this.startButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				pauseTable.setVisible(false);
				table.setVisible(true);
				resume();
			}
		});

		this.optionsButton.addListener(new ChangeListener() {
										   @Override
										   public void changed(ChangeEvent event, Actor actor) {
			   pauseTable.setVisible(false);
			   game.changeScreen(PirateGame.SETTINGS);
		   }
	   }
		);

		this.cancel.addListener(new ChangeListener() {
			   @Override
			   public void changed(ChangeEvent event, Actor actor) {
				   wantToSaveTable.setVisible(false);
				   pauseTable.setVisible(true);
				   table.setVisible(false);
			   }
		   }
		);



		this.exitButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				if(!gameSaved){
					onSaveMenu = true;
					wantToSaveTable.setVisible(true);
					pauseTable.setVisible(false);
					table.setVisible(false);
				}
				else{
					player.resetStats();
					pauseTable.setVisible(false);
					game.changeScreen(PirateGame.MENU);
					game.killGame();
				}

			}
		});

		this.saveButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				// Make sure to load data in the MainMenuScreen too!
				save();
				saveTimer = 3f;
				saveTable.setVisible(true);
			}
		});

		game.batch.setShader(batch.getShader());
		badWeather = false;
	}

	/**
	 * Checks for input and performs an action
	 * Applies to keys "W" "A" "S" "D" "E" "Esc"
	 * <p>
	 * Caps player velocity
	 */

	public void inputUpdate() {
		if (player.getBody().getLinearVelocity().len() > .5f) {
			if (Gdx.input.isKeyPressed(Input.Keys.LEFT) | Gdx.input.isKeyPressed(Input.Keys.A)) {
				player.setTurnDirection(2);
			} else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) | Gdx.input.isKeyPressed(Input.Keys.D)) {
				player.setTurnDirection(1);
			} else {
				player.setTurnDirection(0);
			}
		}


		if (Gdx.input.isKeyPressed(Input.Keys.UP) | Gdx.input.isKeyPressed(Input.Keys.W)) {
			player.setDriveDirection(1);
		} else if (Gdx.input.isKeyPressed(Input.Keys.DOWN) | Gdx.input.isKeyPressed(Input.Keys.S)) {
			player.setDriveDirection(2);
		} else {
			player.setDriveDirection(0);
		}

		if (Gdx.input.isKeyPressed(Input.Keys.NUM_1)) {
			if (camera.zoom < 0.0155f * 2) camera.zoom += 0.02f / 100;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.NUM_2)) {
			if (camera.zoom > 0.0155f) camera.zoom -= 0.02f / 100;
		}



		if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
			System.out.println(PirateGame.difficultyMultiplier);
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
	 * Process a specific input, allowing us to handle velocity / direction.
	 */
	private void processInput() {
		Vector2 baseVector = new Vector2(0, 0);

		/** apllying liner velocity to the player based on input*/
		float turnPercentage = 0;
		if (player.getBody().getLinearVelocity().len() < (player.getMaximumSpeed() / 2)) {
			turnPercentage = player.getBody().getLinearVelocity().len() / (player.getMaximumSpeed());
		} else {
			turnPercentage = 1;
		}

		float currentTurnSpeed = player.getTurnSpeed() * turnPercentage;


		/** applying angular velocity to the player based on input*/
		if (player.getTurnDirection() == 1) {
			player.getBody().setAngularVelocity(-currentTurnSpeed);
		} else if (player.getTurnDirection() == 2) {
			player.getBody().setAngularVelocity(currentTurnSpeed);
		} else if (player.getTurnDirection() == 0 && player.getBody().getAngularVelocity() != 0) {
			player.getBody().setAngularVelocity(0);
		}

		/** applies speed to the player based on input*/
		if (player.getDriveDirection() == 1) {
			baseVector.set(0, player.getSpeed());
		} else if (player.getDriveDirection() == 2) {
			baseVector.set(0, -player.getSpeed() * 4 / 5);
		}
		if (player.getBody().getLinearVelocity().len() > 0 && Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
			player.getBody().setLinearDamping(1.75f);
		} else {
			player.getBody().setLinearDamping(0.5f);
		}
		//recordedSpeed = player.getBody()().getLinearVelocity().len();
		if (player.getBody().getLinearVelocity().len() > player.getMaximumSpeed() / 3f) {
			player.setSpeed(player.getOriginalSpeed() * 2);
		} else {
			player.setSpeed(player.getOriginalSpeed());
		}
		if (!baseVector.isZero() && (player.getBody().getLinearVelocity().len() < player.getMaximumSpeed())) {
			player.getBody().applyForceToCenter(player.getBody().getWorldVector(baseVector), true);
		}
	}

	private void handleDirft() {
		/** handles drifts of the boat */
		Vector2 forwardSpeed = player.getForwardVelocity();
		Vector2 lateralSpeed = player.getLateralVelocity();

		player.getBody().setLinearVelocity(forwardSpeed.x + lateralSpeed.x * player.getDriftFactor(), forwardSpeed.y + lateralSpeed.y * player.getDriftFactor());
	}

	/**
	 * Updates the state of each object with delta time
	 *
	 * @param dt Delta time (elapsed time since last game tick)
	 */
	public void update(float dt) {



		if (zoomAmount < 0.0155f) {
			camera.zoom -= 0.0005f;
			zoomAmount += 0.0005f;
		}

		inputUpdate();
		processInput();
		handleDirft();
		stateTime += dt;
		//handleInput(dt);
		// Stepping the physics engine by time of 1 frame
		world.step(1 / 60f, 6, 2);

		// Update all players and entities
		player.update(dt);

		colleges.values().forEach(col -> col.update(dt));

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
			for (College college : colleges.values()) {
				if (college.getType().equals(CollegeType.ALCUIN)) {
					continue;
				}

				if (!college.isDestroyed()) {
					college.fire();
				}
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
		if(saveTimer > 0){
			saveTable.setVisible(true);
			saveTimer -= dt;
		}
		else {
			saveTable.setVisible(false);
		}

		if(carefulTimer > 0){
			carefulMsg.setVisible(true);
			carefulTimer -= dt;
		}
		else if(!carefulNotSet) {
			carefulMsg.setVisible(false);
		}
		if (gameStatus == GAME_PAUSED && !onSaveMenu) {
			pauseTable.setVisible(true);
			table.setVisible(false);
		}

		if (gameStatus == GAME_RUNNING) {
			update(dt);
			gameSaved = false;
			table.setVisible(true);
			pauseTable.setVisible(false);
			onSaveMenu = false;
		}

		if(player.getCollegesKilled() >= 2){
			Gdx.gl.glClearColor(26 / 255f, 115 / 255f, 63 / 255f, 1);
			if(!badWeather){
				game.batch.setShader(shader);
				renderer.getBatch().setShader(shader);
				badWeather = true;

				weatherSoundEffect.setLooping(true);
				if (PirateGame.getPreferences().isEffectsEnabled()) {
					weatherSoundEffect.play();
				}
				weatherSoundEffect.setVolume(PirateGame.getPreferences().getEffectsVolume());
			}

		}else {
			Gdx.gl.glClearColor(46 / 255f, 204 / 255f, 113 / 255f, 1);
			if(badWeather){
				game.batch.setShader(batch.getShader());
				badWeather = false;

			}

			if(weatherSoundEffect.isPlaying()){
				weatherSoundEffect.pause();
			}
		}

		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		renderer.render();
		// b2dr is the hitbox shapes, can be commented out to hide
		//b2dr.render(world, camera.combined);

		game.batch.setProjectionMatrix(camera.combined);
		game.batch.begin();
		// Order determines layering

		//Renders coins
		for (Coin coin : Coins) {
			coin.draw(game.batch);
		}

		//Renders colleges
		player.draw(game.batch);

		colleges.values().forEach(col -> col.draw(game.batch));

		//Updates all ships
		for (EnemyShip ship : ships) {
			if (!Objects.equals(ship.college, "Unaligned")) {
				//Flips a colleges allegence if their college is destroyed
				if (colleges.get(ship.college).isDestroyed() || colleges.get(ship.college).isCaptured()) {
					ship.updateTexture("Alcuin", "alcuin_ship.png");
				}
			}


			ship.draw(game.batch);

		}
		CannonManager.update(dt, game.batch);
		game.batch.end();
		HUD.stage.draw();

		stage.act();
		stage.draw();
		//Checks game over conditions
		gameOverCheck();
		debugger.render(world, camera.combined.scl(PirateGame.PPM));
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
		shader.begin();
		shader.setUniformf("u_resolution", width, height);
		shader.end();


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
			player.resetStats();


			game.changeScreen(PirateGame.DEATH);
			game.killGame();
			return;
		}

		//Win game if all colleges destroyed
		boolean allDestroyed = colleges.values().stream().allMatch(col -> {
			if (col.getType().equals(CollegeType.ALCUIN)) {
				return true;
			}

			return col.isDestroyed();
		});

		if (allDestroyed || player.getCollegesCaptured() >= 3) {
			player.resetStats();
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
	public boolean checkGenPos(int x, int y) {
		if (SpawnUtils.get().tileBlocked.containsKey(x)) {
			ArrayList<Integer> yTest = SpawnUtils.get().tileBlocked.get(x);
			return !yTest.contains(y);
		}

		return true;
	}

	/**
	 * Generate an array of coins - these haven't been drawn yet!
	 *
	 * @param amount Amount of coins you wish to spawn.
	 * @return List with coins at pre-determined, unique positions.
	 */
	public List<Coin> generateCoins(int amount) {
		List<Coin> generatedCoins = new ArrayList<>();

		this.generateRandomLocations(amount).forEach(loc -> {
			generatedCoins.add(new Coin(this, loc.getX(), loc.getY()));
		});

		return generatedCoins;
	}

	/**
	 * Generate an array of ships - these haven't been drawn yet!
	 *
	 * @param amount Amount of ships you wish to spawn.
	 * @return List with ships at pre-determined, unique positions.
	 */
	public List<EnemyShip> generateShips(int amount) {
		List<EnemyShip> enemyShips = new ArrayList<>();

		this.generateRandomLocations(amount).forEach(loc -> {
			enemyShips.add(new EnemyShip(this, loc.getX(), loc.getY(), "unaligned_ship.png", "Unaligned"));
		});

		return enemyShips;
	}

	/**
	 * Generate a set of locations - these are unique.
	 *
	 * @param amount Amount of locations you wish to fetch.
	 * @return List of unique locations.
	 */
	public Set<Location> generateRandomLocations(int amount) {
		Set<Location> locations = new HashSet<>();

		boolean validLoc;
		int a = 0;
		int b = 0;

		for (int i = 0; i < amount; i++) {
			validLoc = false;
			while (!validLoc) {
				//Get random x and y coords
				a = ThreadLocalRandom.current().nextInt(SpawnUtils.get().xCap - SpawnUtils.get().xBase) + SpawnUtils.get().xBase;
				b = ThreadLocalRandom.current().nextInt(SpawnUtils.get().yCap - SpawnUtils.get().yBase) + SpawnUtils.get().yBase;
				//Check if valid
				validLoc = checkGenPos(a, b);
			}

			locations.add(new Location(a, b));
			SpawnUtils.get().add(a, b);
		}

		return locations;
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
		debugger.dispose();
	}

	public HUD getHud() {
		return hud;
	}

	/**
	 * @return Current Pause Button.
	 */
	public TextButton getPauseButton() {
		return pauseButton;
	}

	/**
	 * @return Current Skill Button.
	 */
	public TextButton getSkillButton() {
		return skillButton;
	}

	/**
	 * @return Current Shop Button.
	 */
	public TextButton getShopButton() {
		return shopButton;
	}

	/**
	 * @return Current Difficulty Button.
	 */
	public TextButton getDifficultyButton() {
		return difficultyButton;
	}

	/**
	 * @return Current Start Button.
	 */
	public TextButton getStartButton() {
		return startButton;
	}

	/**
	 * @return Current Options Button.
	 */
	public TextButton getOptionsButton() {
		return optionsButton;
	}

	/**
	 * @return Current Exit Button.
	 */
	public TextButton getExitButton() {
		return exitButton;
	}

	/**
	 * saves game state
	 */
	public void save(){
		Persistence persistence = Persistence.get();
		persistence.reset();

		persistence.set("points", HUD.getScore());
		persistence.set("coins", HUD.getCoins());
		persistence.set("health", HUD.getHealth());
		persistence.set("boatsKilled", player.getBoatsKilled());
		persistence.set("collegesKilled", player.getCollegesKilled());
		persistence.set("collegesCaptured", player.getCollegesCaptured());
		persistence.set("ultimateFirerEnabled", player.ultimateFirerEnabled);
		persistence.set("isBloodied", player.isBloodied);
		persistence.set("shieldEnabled", player.shieldEnabled);
		persistence.set("rayEnabled", player.rayEnabled);
		persistence.set("burstFire", player.burstFire);
		persistence.set("playerX", player.getBody().getPosition().x);
		persistence.set("playerY", player.getBody().getPosition().y);
		persistence.set("playerAngle", player.getBody().getAngle());
		persistence.set("disablingRayCooldown", player.disablingRayCooldown);
		persistence.set("protectedTimer", player.protectedTimer);
		persistence.set("ultimateAmount", player.ultimateAmount);
		persistence.set("shieldCoolDown", player.shieldCoolDown);
		persistence.set("burstCooldown", player.burstCooldown);
		persistence.set("damage1Price", ShopScreen.damage1Price);
		persistence.set("health1Price", ShopScreen.health1Price);
		persistence.set("dps1Price", ShopScreen.dps1Price);
		persistence.set("range1Price", ShopScreen.range1Price);
		persistence.set("GoldMulti1Price", ShopScreen.GoldMulti1Price);
		persistence.set("resistancePrice", ShopScreen.resistancePrice);
		persistence.set("bulletSpeedPrice", ShopScreen.bulletSpeedPrice);
		persistence.set("movement1Price", ShopScreen.movement1Price);
		persistence.set("bulletSpeedCounter", ShopScreen.bulletSpeedCounter);
		persistence.set("dpsCounter", ShopScreen.dpsCounter);
		persistence.set("rangeCounter", ShopScreen.rangeCounter);
		persistence.set("resistanceCounter", ShopScreen.resistanceCounter);
		persistence.set("damageCounter", ShopScreen.damageCounter);
		persistence.set("movementCounter", ShopScreen.movementCounter);
		persistence.set("healthCounter", ShopScreen.healthCounter);
		persistence.set("goldMultiplierCounter", ShopScreen.goldMultiplierCounter);




		persistence.set("difficulty", PirateGame.difficultyMultiplier);

		for (College college : colleges.values()) {
			if (college.isCaptured()) {
				persistence.set("college_captured_" + college.getType().getName(), true);
			}

			if (college.isDestroyed()) {
				persistence.set("college_destroyed_" + college.getType().getName(), true);
			}
		}

		for (int i = 0; i < SkillsScreen.states.size(); i++) {
			int state = SkillsScreen.states.get(i);

			persistence.set("skill_" + i, state);
		}

		for(int i = 0; i < ships.size(); i++){
			persistence.set("Health_" + i, ships.get(i).getHealth());
			persistence.set("College_" + i, ships.get(i).college);
			persistence.set("Position_X_" + i, ships.get(i).getBody().getPosition().x);
			persistence.set("Position_Y_" + i, ships.get(i).getBody().getPosition().y);
			persistence.set("Position_Angle_" + i, ships.get(i).getBody().getAngle());
			persistence.set("isDestroyed_" + i, ships.get(i).isDestroyed());
		}

		persistence.set("Health_Alcuin" , colleges.get("Alcuin").getHealth());
		persistence.set("Health_Anne_Lister" , colleges.get("Anne Lister").getHealth());
		persistence.set("Health_Constantine" , colleges.get("Constantine").getHealth());
		persistence.set("Health_Goodricke" , colleges.get("Goodricke").getHealth());



		gameSaved = true;
	}
}
