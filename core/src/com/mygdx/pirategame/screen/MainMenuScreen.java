package com.mygdx.pirategame.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.pirategame.PirateGame;
import com.mygdx.pirategame.display.HUD;
import com.mygdx.pirategame.entity.Player;
import com.mygdx.pirategame.entity.coin.Coin;
import com.mygdx.pirategame.entity.college.College;
import com.mygdx.pirategame.entity.ship.EnemyShip;
import com.mygdx.pirategame.utils.Location;
import com.mygdx.pirategame.utils.Persistence;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Main menu is the first screen the player sees. Allows them to navigate where they want to go to
 *
 * @author Sam Pearson
 * @version 1.0
 */
public class MainMenuScreen implements Screen {

	public static Sprite background = new Sprite(new Texture(Gdx.files.internal("background.PNG")));
	private final PirateGame parent;
	;
	private final Stage stage;
	private Table titleTable = new Table();

	private TextButton newGameButton;
	private TextButton resumeGameButton;
	private TextButton helpButton;
	private TextButton optionsButton;
	private TextButton exitButton;

	/**
	 * Instantiates a new Main menu.
	 *
	 * @param PirateGame the main starting body of the game. Where screen swapping is carried out.
	 */
	public MainMenuScreen(PirateGame PirateGame, Stage stage) {
		parent = PirateGame;
		this.stage = stage;
	}

	public static void renderBackground() {
		SpriteBatch batch = new SpriteBatch();

		batch.begin();
		background.draw(batch);
		batch.end();
	}

	/**
	 * What should be displayed on the options screen
	 */
	@Override
	public void show() {
		titleTable.clear();
		titleTable.clearChildren();
		titleTable.reset();
		Label.LabelStyle label1Style = new Label.LabelStyle();
		BitmapFont font = new BitmapFont(Gdx.files.internal("font.fnt"));
		label1Style.font = font;

		Label Title = new Label("Spice Traders", new Label.LabelStyle(new BitmapFont(Gdx.files.internal("font.fnt")), Color.FIREBRICK));
		titleTable.center().top();
		titleTable.setFillParent(true);
		titleTable.add(Title);
		stage.addActor(titleTable);

		background.setSize(1920, 1080);
		//Set the input processor
		Gdx.input.setInputProcessor(stage);
		// Create a table for the buttons
		Table table = new Table();
		table.setFillParent(true);
		stage.addActor(table);

		//The skin for the actors
		Skin skin = new Skin(Gdx.files.internal("skin/uiskin.json"));

		//create buttons
		this.newGameButton = new TextButton("New Game", skin);
		this.resumeGameButton = new TextButton("Resume Game", skin);
		this.helpButton = new TextButton("Help", skin);
		this.optionsButton = new TextButton("Options", skin);
		this.exitButton = new TextButton("Exit", skin);

		//add buttons to table
		table.add(this.newGameButton).fillX().uniformX();
		table.row().pad(10, 0, 10, 0);
		table.add(this.resumeGameButton).fillX().uniformX();
		table.row();
		table.add(this.helpButton).fillX().uniformX();
		table.row();
		table.add(this.optionsButton).fillX().uniformX();
		table.row();
		table.add(this.exitButton).fillX().uniformX();

		//add listeners to the buttons

		//Start a game
		this.newGameButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				parent.changeScreen(PirateGame.DIFFICULTY);
			}
		});
		//Help Screen
		this.helpButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				parent.changeScreen(PirateGame.HELP);
			}
		});

		//Go to edit options
		this.optionsButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				parent.changeScreen(PirateGame.SETTINGS);
			}
		});


		//Quit game
		this.exitButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				Gdx.app.exit();
			}
		});

		this.resumeGameButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				ActiveGameScreen activeGameScreen = (ActiveGameScreen) parent.changeScreen(PirateGame.GAME); // initialise.
				Persistence persistence = Persistence.get();

				if (persistence.raw() == null || persistence.raw().isEmpty()) {
					return;
				}

				System.out.println(persistence.raw().keySet());

				HUD.changePoints(persistence.getInt("points"));
				HUD.changeCoins(persistence.getInt("coins"));
				HUD.changeHealth(persistence.getInt("health"));

				PirateGame.difficultySet = true;
				PirateGame.difficultyMultiplier = persistence.getFloat("difficulty");

				List<Integer> skills = new ArrayList<>();

				for (int i : SkillsScreen.states) {
					skills.add(persistence.getInt("skill_" + i));
				}

				for (College college : ActiveGameScreen.colleges.values()) {
					if (persistence.isSet("college_captured_" + college.getType().getName())) {
						college.setHealth(0);
						college.setSurrended(true);
					}

					if (persistence.isSet("college_destroyed_" + college.getType().getName())) {
						college.setHealth(0);
						college.setDestroyed(true);
					}
				}

				float x = persistence.getFloat("player_x");
				float y = persistence.getFloat("player_x");

				Player player = ActiveGameScreen.player;
				Body body = player.getBody();
				activeGameScreen.world.destroyBody(body);

				player.setSpawnLocation(new Location(x, y));
				player.defineEntity();
			}
		});
	}

	/**
	 * Renders the visual data for all objects
	 *
	 * @param delta Delta Time
	 */
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(46 / 255f, 204 / 255f, 113 / 255f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
		renderBackground();
		stage.draw();
	}

	/**
	 * Changes the camera size, Scales the hud to match the camera
	 *
	 * @param width  the width of the viewable area
	 * @param height the height of the viewable area
	 */
	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height, true);
	}

	/**
	 * (Not Used)
	 * Pauses game
	 */
	@Override
	public void pause() {
	}

	/**
	 * (Not Used)
	 * Resumes game
	 */
	@Override
	public void resume() {
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
		stage.dispose();
	}

	public TextButton getNewGameButton() {
		return newGameButton;
	}

	public TextButton getResumeGameButton() {
		return resumeGameButton;
	}

	public TextButton getHelpButton() {
		return helpButton;
	}

	public TextButton getOptionsButton() {
		return optionsButton;
	}

	public TextButton getExitButton() {
		return exitButton;
	}
}




