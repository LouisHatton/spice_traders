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
import com.mygdx.pirategame.display.HUD;

import java.util.Arrays;
import java.util.List;

/**
 * The type for the skill tree screen.
 * It is a visual representation for the skills that the game automatically unlocks for the player.
 * Automatically unlocked when a points threshold is reached
 *
 * @author Faris Alblooki
 * @version 2.0
 */
public class SkillsScreen implements Screen {

	//To store whether buttons are enabled or disabled
	protected static List<Integer> states = Arrays.asList(1, 1, 1, 1, 1);
	private static TextButton bloodied;
	final Table Other = new Table();
	private final PirateGame parent;
	private final Stage stage;
	Table table = new Table();
	Table titleTable = new Table();
	private static TextButton shield;
	private static TextButton ultimateAbility;
	private static TextButton secondaryAbility;
	private static TextButton disablingRay;

	/**
	 * Instantiates a new Skill tree.
	 *
	 * @param pirateGame the main starting body of the game. Where screen swapping is carried out.
	 */
//In the constructor, the parent and stage are set. Also the states list is set
	public SkillsScreen(PirateGame pirateGame, Stage stage) {
		parent = pirateGame;
		this.stage = stage;
	}

	public static void unlock(int i) {
		states.set(i, 0);
	}


	public static void lock(int i) {
		states.set(i, 1);
	}

	/**
	 * Allows the game to check whether a points threshold has been reached
	 *
	 * @param points the current amount of points
	 */
	public static void pointsCheck(int points) {
		if (states.get(3) == 1 && points >= 200) {

			states.set(3, 0);

		} else if (states.get(4) == 1 && points >= 500) {

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
		titleTable.clear();
		titleTable.clearChildren();
		titleTable.reset();


		//Set the input processor
		Gdx.input.setInputProcessor(stage);


		table.setFillParent(true);
		stage.addActor(table);


		Other.setFillParent(true);
		stage.addActor(Other);


		//The skin for the actors
		Skin skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
		Label.LabelStyle label1Style = new Label.LabelStyle();
		BitmapFont font = new BitmapFont(Gdx.files.internal("font.fnt"));
		label1Style.font = font;




		Label Title = new Label("Skills Screen", label1Style);
		titleTable.center().top();
		titleTable.setFillParent(true);
		Title.setFontScale(1);
		titleTable.add(Title);
		stage.addActor(titleTable);
		Title.setColor(PirateGame.selectedColour);



		//create skill tree buttons
		bloodied = new TextButton("Bloodied", skin);
		bloodied.getStyle().fontColor = PirateGame.selectedColour;

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
		disablingRay = new TextButton("Disabling Ray", skin);
		disablingRay.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {

				parent.changeScreen(PirateGame.DISABLINGRAY);
			}
		});

		if (states.get(1) == 1) {
			disablingRay.setDisabled(true);
		}
		shield = new TextButton("Shield", skin);
		shield.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {

				parent.changeScreen(PirateGame.SHIELD);
			}
		});


		if (states.get(2) == 1) {
			shield.setDisabled(true);
		}

		secondaryAbility = new TextButton("Secondary Ability", skin);

		secondaryAbility.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {

				parent.changeScreen(PirateGame.BURST);
			}
		});

		if (states.get(3) == 1) {
			secondaryAbility.setDisabled(true);

		}

		ultimateAbility = new TextButton("Ultimate Ability", skin);

		ultimateAbility.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {

				parent.changeScreen(PirateGame.ULTIMATE);
			}
		});

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
		if (ActiveGameScreen.player.getCollegesKilled() == 0) {
			Label bloodiedLabel = new Label("Destroy a college to unlock ", skin);
			bloodiedLabel.setColor(PirateGame.selectedColour);
			table.add(bloodiedLabel);
		}
		table.add(bloodied);

		table.row().pad(10, 0, 10, 0);
		if (ActiveGameScreen.player.getCollegesCaptured() == 0) {
			Label disablingRayLabel = new Label("Capture a college to unlock ", skin);
			disablingRayLabel.setColor(PirateGame.selectedColour);
			table.add(disablingRayLabel);
		}
		table.add(disablingRay);

		table.row().pad(10, 0, 10, 0);
		if (ActiveGameScreen.player.getBoatsKilled() < 5) {
			Label shieldLabel = new Label("Destroy 5 Ships to unlock ", skin);
			shieldLabel.setColor(PirateGame.selectedColour);
			table.add(shieldLabel);
		}
		table.add(shield);

		table.row().pad(10, 0, 10, 0);
		if (HUD.getScore() < 200) {
			Label secondaryLabel = new Label("Gather 200 Score to unlock ", skin);
			secondaryLabel.setColor(PirateGame.selectedColour);
			table.add(secondaryLabel);
		}
		table.add(secondaryAbility);

		table.row().pad(10, 0, 10, 0);
		if (HUD.getScore() < 500) {
			Label ultimateLabel = new Label("Gather 500 Score to unlock ", skin);
			ultimateLabel.setColor(PirateGame.selectedColour);
			table.add(ultimateLabel);
		}
		table.add(ultimateAbility);

		table.center();

		//add return button
		Label Strategy1 = new Label("Click on the ability once unlocked to get more information and see the progression of the ability", new Label.LabelStyle(new BitmapFont(Gdx.files.internal("textFont.fnt")), PirateGame.selectedColour));
		Other.add(backButton);
		Other.add(Strategy1);
		Other.bottom().left();

		if(ActiveGameScreen.player.getCollegesKilled() > 0){
			states.set(0, 1);
			bloodied.setDisabled(false);
		}
		if(ActiveGameScreen.player.getCollegesCaptured() > 0){
			states.set(1, 1);
			disablingRay.setDisabled(false);
		}
		if(ActiveGameScreen.player.getBoatsKilled() >= 5){
			states.set(2, 1);
			shield.setDisabled(false);
		}
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
	 *
	 * resets all the stats
	 */
	public static void resetStats(){
		states = Arrays.asList(1, 1, 1, 1, 1);

		if (bloodied == null) { // if one is null, all are!
			return;
		}

		bloodied.setDisabled(true);
		ultimateAbility.setDisabled(true);
		shield.setDisabled(true);
		secondaryAbility.setDisabled(true);
		disablingRay.setDisabled(true);
	}

	/**
	 * Disposes game data
	 */
	@Override
	public void dispose() {
		stage.dispose();
	}

	public TextButton getBloodiedButton() {
		return bloodied;
	}

	public TextButton getShieldButton() {
		return shield;
	}

	public TextButton getUltimateAbilityButton() {
		return ultimateAbility;
	}

	public TextButton getSecondaryAbilityButton() {
		return secondaryAbility;
	}

	public TextButton getDisablingRayButton() {
		return disablingRay;
	}
}

