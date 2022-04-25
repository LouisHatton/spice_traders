package com.mygdx.pirategame.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.pirategame.PirateGame;

/**
 * Main menu is the first screen the player sees. Allows them to navigate where they want to go to
 *
 * @author Sam Pearson
 * @version 1.0
 */
public class MainMenuScreen implements Screen {

	private final PirateGame parent;
	private final Stage stage;
	public static Sprite background = new Sprite(new Texture(Gdx.files.internal("background.PNG")));;
	static SpriteBatch batch = new SpriteBatch();

	/**
	 * Instantiates a new Main menu.
	 *
	 * @param PirateGame the main starting body of the game. Where screen swapping is carried out.
	 */
	public MainMenuScreen(PirateGame PirateGame) {
		parent = PirateGame;
		stage = new Stage(new ScreenViewport());
	}

	/**
	 * What should be displayed on the options screen
	 */
	@Override
	public void show() {
		background.setSize(1920,1080);
		//Set the input processor
		Gdx.input.setInputProcessor(stage);
		// Create a table for the buttons
		Table table = new Table();
		table.setFillParent(true);
		stage.addActor(table);

		//The skin for the actors
		Skin skin = new Skin(Gdx.files.internal("skin/uiskin.json"));

		//create buttons
		TextButton newGame = new TextButton("New Game", skin);
		TextButton resumeGame = new TextButton("Resume Game", skin);
		TextButton help = new TextButton("Help", skin);
		TextButton options = new TextButton("Options", skin);
		TextButton exit = new TextButton("Exit", skin);

		//add buttons to table
		table.add(newGame).fillX().uniformX();
		table.row().pad(10, 0, 10, 0);
		table.add(resumeGame).fillX().uniformX();
		table.row();
		table.add(help).fillX().uniformX();
		table.row();
		table.add(options).fillX().uniformX();
		table.row();
		table.add(exit).fillX().uniformX();

		//add listeners to the buttons

		//Start a game
		newGame.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				parent.setScreen(new DifficultyScreen(parent, parent.getScreen()));
			}
		});
		//Help Screen
		help.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				parent.changeScreen(PirateGame.HELP);
			}
		});

		//Go to edit options
		options.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				parent.setScreen(new SettingsScreen(parent, parent.getScreen()));
			}
		});


		//Quit game
		exit.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				Gdx.app.exit();
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

	public static void renderBackground(){
		batch.begin();
		background.draw(batch);
		batch.end();
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
}




