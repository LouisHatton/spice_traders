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
 * The type for the victory screen
 *
 * @author Faris Alblooki
 * @version 2.0
 */
public class VictoryScreen implements Screen {

	private final PirateGame parent;
	private final Stage stage;

	private TextButton backButton;


	/**
	 * Instantiates a new Victory screen.
	 *
	 * @param pirateGame the main starting body of the game. Where screen swapping is carried out.
	 */
	public VictoryScreen(PirateGame pirateGame, Stage stage) {
		parent = pirateGame;
		this.stage = stage;
	}

	/**
	 * What should be displayed on the victory screen
	 */
	@Override
	public void show() {
		//Creates the skin for the buttons and labels to use
		Skin skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
		//Sets stage to be the input processor
		Gdx.input.setInputProcessor(stage);

		//Creates 2 tables for actors to go into, one for text and one for the return button
		Table table = new Table();
		table.center();
		table.setFillParent(true);

		Table table2 = new Table();
		table2.center();
		table2.setFillParent(true);

		//Set the message and add to table
		Label victoryMsg = new Label("YOU WON", new Label.LabelStyle(new BitmapFont(Gdx.files.internal("font.fnt")), PirateGame.selectedColour3));
		Label victoryMsg2 = new Label("CONGRATULATIONS PIRATE", new Label.LabelStyle(new BitmapFont(Gdx.files.internal("font.fnt")), PirateGame.selectedColour3));
		table.add(victoryMsg).center();
		table.row();
		table.add(victoryMsg2).center().padTop(20);
		stage.addActor(table);

		//Create return button
		this.backButton = new TextButton("Return To Menu", skin);

		//Set the click response
		backButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {

				//Return to main screen
				parent.changeScreen(PirateGame.MENU);
				parent.killEndScreen();
			}
		});

		//add to table
		table2.add(backButton).fillX().uniformX();
		table2.bottom();

		stage.addActor(table2);

	}


	/**
	 * (Not Used since there is no updating features)
	 * Update the screen every delta time
	 *
	 * @param dt Delta Time
	 */
	public void update(float dt) {

	}

	/**
	 * Renders the visual data for all objects
	 *
	 * @param dt Delta Time
	 */

	@Override
	public void render(float dt) {
		// Renders visual data
		Gdx.gl.glClearColor(0f, 0f, 0f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
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
		//update the viewport
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

	public TextButton getBackButton() {
		return backButton;
	}
}
