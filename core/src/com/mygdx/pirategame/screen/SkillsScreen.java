package com.mygdx.pirategame.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
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

import java.util.ArrayList;
import java.util.List;

/**
 * The type for the skill tree screen.
 * It is a visual representation for the skills that the game automatically unlocks for the player.
 * Automatically unlocked when a points threshold is reached
 *
 * @author Sam Pearson
 * @version 1.0
 */
public class SkillsScreen implements Screen {

	//To store whether buttons are enabled or disabled
	private static final List<Integer> states = new ArrayList<Integer>();
	private static TextButton movement1;
	private final PirateGame parent;
	private final Stage stage;
	private TextButton damage1;
	private TextButton GoldMulti1;
	private TextButton movement2;

	/**
	 * Instantiates a new Skill tree.
	 *
	 * @param pirateGame the main starting body of the game. Where screen swapping is carried out.
	 */
//In the constructor, the parent and stage are set. Also the states list is set
	public SkillsScreen(PirateGame pirateGame) {
		parent = pirateGame;
		stage = new Stage(new ScreenViewport());

		//0 = enabled, 1 = disabled
		states.add(1);
		states.add(1);
		states.add(1);
		states.add(1);
	}

	/**
	 * Allows the game to check whether a points threshold has been reached
	 *
	 * @param points the current amount of points
	 */
	public static void pointsCheck(int points) {

		//States.get() checks whether it has already been unlocked. 1 = not unlocked, 0 = unlocked
		if (states.get(0) == 1 && points >= 100) {
			//Change acceleration
			GameScreen.changeAcceleration(20F);
			//Change Max speed
			GameScreen.changeMaxSpeed(20F);
			states.set(0, 0);

		} else if (states.get(1) == 1 && points >= 200) {
			//Change multiplier
			HUD.changeCoinsMulti(2);
			states.set(1, 0);
		} else if (states.get(2) == 1 && points >= 300) {
			//Change acceleration
			GameScreen.changeAcceleration(20F);
			//Change Max speed
			GameScreen.changeMaxSpeed(20F);
			states.set(2, 0);

		} else if (states.get(3) == 1 && points >= 400) {
			//Increase damage
			GameScreen.changeDamage(5);
			states.set(3, 0);
		}
	}

	/**
	 * What should be displayed on the skill tree screen
	 */
	@Override
	public void show() {
		//Set the input processor
		Gdx.input.setInputProcessor(stage);
		// Create a table that fills the screen
		Table table = new Table();
		table.setFillParent(true);
		stage.addActor(table);


		// Table for the return button
		final Table Other = new Table();
		Other.setFillParent(true);
		stage.addActor(Other);


		//The skin for the actors
		Skin skin = new Skin(Gdx.files.internal("skin/uiskin.json"));

		//create skill tree buttons
		movement1 = new TextButton("Movement Speed + 20%", skin);

		//Sets enabled or disabled
		if (states.get(0) == 1) {
			movement1.setDisabled(true);
		}
		GoldMulti1 = new TextButton("Gold Multiplier x2", skin);
		if (states.get(1) == 1) {
			GoldMulti1.setDisabled(true);
		}
		movement2 = new TextButton("Movement Speed + 20%", skin);
		if (states.get(2) == 1) {
			movement2.setDisabled(true);
		}

		damage1 = new TextButton("Damage + 5", skin);

		if (states.get(3) == 1) {
			damage1.setDisabled(true);

		}

		//Point unlock labels
		final Label unlock100 = new Label("100 points", skin);
		final Label unlock200 = new Label("200 points", skin);
		final Label unlock300 = new Label("300 points", skin);
		final Label unlock400 = new Label("400 points", skin);

		//Return Button
		TextButton backButton = new TextButton("Return", skin);

		backButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {

				parent.changeScreen(PirateGame.GAME); //Return to game
			}
		});

		//add buttons and labels to main table
		table.add(movement1);
		table.add(unlock100);
		table.row().pad(10, 0, 10, 0);
		table.add(GoldMulti1);
		table.add(unlock200);
		table.row().pad(10, 0, 10, 0);
		table.add(movement2);
		table.add(unlock300);
		table.row().pad(10, 0, 10, 0);
		table.add(damage1);
		table.add(unlock400);
		table.top();

		//add return button
		Other.add(backButton);
		Other.bottom().left();
	}

	/**
	 * Renders the visual data for all objects
	 *
	 * @param delta Delta Time
	 */

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0f, 0f, 0f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// tell our stage to do actions and draw itself
		stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
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
}




