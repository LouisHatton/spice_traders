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
	private static final List<Integer> states = Arrays.asList(1, 1, 1, 1, 1, 1, 1, 1);
	private static TextButton movement1;
	private final PirateGame parent;
	private final Stage stage;
	private TextButton damage1;
	private TextButton health1;
	private TextButton dps1;
	private TextButton range1;
	private TextButton GoldMulti1;
	private TextButton resistance;
	private TextButton bulletSpeed;

	private float healthPrice = 100f;
	private float DamagehPrice = 200f;
	private float SpeedPrice = 150f;
	private float goldPrice = 50f;

	public int[] tracker = {0,0,0,0,0,0,0,0};
	public float percentPerPurchase = 20f;

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

	/**
	 * Allows the game to check whether a points threshold has been reached
	 *
	 * @param points the current amount of points
	 */
	public static void pointsCheck(int points) {
		//code for skills here
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


		bulletSpeed = new TextButton("Increase Bullet Speed", skin);
		bulletSpeed.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {

				//code for purchases!
			}
		});
		dps1 = new TextButton("Increase Fire Rate", skin);
		dps1.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {


			}
		});
		range1 = new TextButton("Increase Bullet Range", skin);

		range1.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {


			}
		});
		resistance = new TextButton("Increase Resistance", skin);

		resistance.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {


			}
		});
		damage1 = new TextButton("Increase Damage", skin);

		damage1.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {


			}
		});
		movement1 = new TextButton("Increase Movement Speed", skin);
		movement1.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {


			}
		});
		health1 = new TextButton("Increase Health", skin);

		health1.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {


			}
		});
		GoldMulti1 = new TextButton("Increase Gold multiplier", skin);
		GoldMulti1.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {


			}
		});


		//Point unlock labels
		final Label bulletSpeedLabel = new Label(DamagehPrice + " Gold", skin);
		final Label dps1Label = new Label(DamagehPrice + " Gold", skin);
		final Label range1Label = new Label(DamagehPrice + " Gold", skin);
		final Label resistanceLabel = new Label(healthPrice + " Gold", skin);
		final Label damage1Label = new Label(DamagehPrice + " Gold", skin);
		final Label movement1Label = new Label(SpeedPrice + " Gold", skin);
		final Label health1Label = new Label(healthPrice + " Gold", skin);
		final Label GoldMulti1Label = new Label(goldPrice + " Gold", skin);

		//Return Button
		TextButton backButton = new TextButton("Return", skin);

		backButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {

				parent.changeScreen(PirateGame.GAME); //Return to game
			}
		});

		//add buttons and labels to main table
		table.add(bulletSpeed);
		table.add(bulletSpeedLabel);
		table.row().pad(10, 0, 10, 0);
		table.add(dps1);
		table.add(dps1Label);
		table.row().pad(10, 0, 10, 0);
		table.add(range1);
		table.add(range1Label);
		table.row().pad(10, 0, 10, 0);
		table.add(resistance);
		table.add(resistanceLabel);
		table.row().pad(10, 0, 10, 0);
		table.add(damage1);
		table.add(damage1Label);
		table.row().pad(10, 0, 10, 0);
		table.add(movement1);
		table.add(movement1Label);
		table.row().pad(10, 0, 10, 0);
		table.add(health1);
		table.add(health1Label);
		table.row().pad(10, 0, 10, 0);
		table.add(GoldMulti1);
		table.add(GoldMulti1Label);
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




