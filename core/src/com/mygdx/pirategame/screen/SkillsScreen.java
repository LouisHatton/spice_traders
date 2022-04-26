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

import java.util.Arrays;
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
	private static final List<Integer> states = Arrays.asList(1, 1, 1, 1, 1);
	private static TextButton bloodied;
	private final PirateGame parent;
	private final Stage stage;
	private TextButton shield;
	private TextButton ultimateAbility;
	private TextButton secondaryAbility;
	private TextButton critHit;

	Table table = new Table();
	final Table Other = new Table();

	/**
	 * Instantiates a new Skill tree.
	 *
	 * @param pirateGame the main starting body of the game. Where screen swapping is carried out.
	 */
//In the constructor, the parent and stage are set. Also the states list is set
	public SkillsScreen(PirateGame pirateGame) {
		parent = pirateGame;
		stage = new Stage(new ScreenViewport());
	}

	public static void unlock(int i){
		states.set(i, 0);
	}


	public static void lock(int i){
		states.set(i, 1);
	}

	/**
	 * Allows the game to check whether a points threshold has been reached
	 *
	 * @param points the current amount of points
	 */
	public static void pointsCheck(int points) {


		 if (states.get(3) == 1 && points >= 500) {

			states.set(3, 0);

		}
		 else if (states.get(4) == 1 && points >= 1000) {

			states.set(4, 0);
		}
	}

	/**
	 * What should be displayed on the skill tree screen
	 */
	@Override
	public void show() {
		table.reset();
		table.clearChildren();
		table.clear();
		Other.clear();
		Other.clearChildren();
		Other.reset();

		//Set the input processor
		Gdx.input.setInputProcessor(stage);


		table.setFillParent(true);
		stage.addActor(table);


		Other.setFillParent(true);
		stage.addActor(Other);



		//The skin for the actors
		Skin skin = new Skin(Gdx.files.internal("skin/uiskin.json"));

		//create skill tree buttons
		bloodied = new TextButton("Bloodied", skin);
		bloodied.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {

				parent.changeScreen(PirateGame.BLOODIED);
			}
		});

		//Sets enabled or disabled
		if (states.get(0) == 1) {
			bloodied.setDisabled(true);
		}
		critHit = new TextButton("Crit Hit", skin);
		if (states.get(1) == 1) {
			critHit.setDisabled(true);
		}
		shield = new TextButton("Shield", skin);
		if (states.get(2) == 1) {
			shield.setDisabled(true);
		}

		secondaryAbility = new TextButton("Secondary Ability", skin);

		if (states.get(3) == 1) {
			secondaryAbility.setDisabled(true);

		}

		ultimateAbility = new TextButton("Ultimate Ability", skin);

		if (states.get(4) == 1) {
			ultimateAbility.setDisabled(true);

		}


		//Return Button
		TextButton backButton = new TextButton("Return", skin);

		backButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {

				parent.changeScreen(PirateGame.GAME); //Return to game
			}
		});

		//add buttons and labels to main table
		table.add(bloodied);

		table.row().pad(10, 0, 10, 0);
		table.add(critHit);

		table.row().pad(10, 0, 10, 0);
		table.add(shield);

		table.row().pad(10, 0, 10, 0);
		table.add(secondaryAbility);

		table.row().pad(10, 0, 10, 0);
		table.add(ultimateAbility);

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

