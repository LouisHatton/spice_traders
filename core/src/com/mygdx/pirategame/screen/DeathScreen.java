package com.mygdx.pirategame.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.pirategame.PirateGame;

/**
 * Death Screen
 * Produces a death screen on player death
 *
 * @author Faris Alblooki
 * @version 2.0
 */
public class DeathScreen implements Screen {

	private final PirateGame parent;
	private final Stage stage;

	private TextButton returnButton;

	/**
	 * Creates a new screen
	 *
	 * @param pirateGame Game data
	 */
	public DeathScreen(PirateGame pirateGame) {
		parent = pirateGame;
		stage = new Stage(new ScreenViewport());
	}

	/**
	 * Shows new screen
	 */
	@Override
	public void show() {
		Skin skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
		Gdx.input.setInputProcessor(stage);

		// Create tables for the text and button
		Table table = new Table();
		table.center();
		table.setFillParent(true);

		Table table2 = new Table();
		table2.center();
		table2.setFillParent(true);

		Label deathMsg = new Label("YOU  DIED", new Label.LabelStyle(new BitmapFont(Gdx.files.internal("font.fnt")), PirateGame.selectedColour4));
		table.add(deathMsg).center();
		stage.addActor(table);

		//Creat button
		this.returnButton = new TextButton("Return To Menu", skin);

		//Return to main menu and kill screen
		this.returnButton.addListener(new ChangeListener() {
			/**
			 * Switches screen
			 * Returns to menu
			 *
			 * @param event Updates system event state to meny
			 * @param actor updates scene
			 */
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				parent.changeScreen(PirateGame.MENU);
				parent.killEndScreen();
			}
		});

		table2.add(this.returnButton).fillX().uniformX();
		table2.bottom();

		stage.addActor(table2);

	}

	/**
	 * (Not Used)
	 * Updates the state of each object
	 */
	public void update() {
	}

	/**
	 * Renders visual data with delta time
	 *
	 * @param dt Delta time (elapsed time since last game tick)
	 */
	@Override
	public void render(float dt) {
		Gdx.gl.glClearColor(0f, 0f, 0f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.draw();
	}

	/**
	 * Changes the camera size
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
	 * (Not Used)
	 * Disposes game data
	 */
	@Override
	public void dispose() {
	}

	public TextButton getReturnButton() {
		return returnButton;
	}
}
