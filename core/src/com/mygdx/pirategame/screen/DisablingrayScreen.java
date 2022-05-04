package com.mygdx.pirategame.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.pirategame.PirateGame;

/**
 * Disabling Ray Info Screen
 * Creates a screen with information about the Disabling Ray ability
 *
 *
 * @author Faris Alblooki
 * @version 2.0
 */
public class DisablingrayScreen implements Screen {


	final Table Other = new Table();
	private final PirateGame parent;
	private final Stage stage;
	Table table = new Table();
	Table titleTable = new Table();
	Image progressBar;
	private float currentCollegesCaptured = 0;
	private int collegesCapturedObjective = 1;
	private float percentage = 0;
	private TextButton returnButton;
	private TextButton lvl1Button;
	private TextButton lvl2Button;
	private Label lvl1ButtonLabel;
	private Label lvl2ButtonLabel;
	private Label lvl1ButtonLabel2;
	private Label lvl2ButtonLabel2;
	private Label Title;
	private Label progressBarLabel;
	private Label percentageLabel;

	/**
	 * Instantiates a new DisablingrayScreen screen
	 *
	 * @param pirateGame the main starting body of the game. Where screen swapping is carried out.
	 */
	public DisablingrayScreen(PirateGame pirateGame, Stage stage) {
		parent = pirateGame;
		this.stage = stage;
	}


	@Override
	public void show() {
		Label.LabelStyle label1Style = new Label.LabelStyle();
		BitmapFont font = new BitmapFont(Gdx.files.internal("font.fnt"));
		label1Style.font = font;
		table.reset();
		table.clearChildren();
		table.clear();
		titleTable.reset();
		titleTable.clearChildren();
		titleTable.clear();
		Other.clear();
		Other.clearChildren();
		Other.reset();
		table.setFillParent(true);
		titleTable.setFillParent(true);
		Other.setFillParent(true);
		stage.addActor(titleTable);
		stage.addActor(table);
		stage.addActor(Other);



		String completion = "";


		progressBar = new Image(new Sprite(new Texture("blank.png")));


		currentCollegesCaptured = ActiveGameScreen.player.getCollegesCaptured();
		collegesCapturedObjective = 2;
		percentage = ((currentCollegesCaptured / collegesCapturedObjective));
		percentage = percentage * 100;

		if (collegesCapturedObjective <= currentCollegesCaptured) {
			percentage = 100;
			completion = "- Completed!";
		}
		progressBar.scaleBy(500 * (percentage / 100), 22);
		Gdx.input.setInputProcessor(stage);

		//The skin for the actors
		Skin skin = new Skin(Gdx.files.internal("skin/uiskin.json"));

		//Return Button
		this.returnButton = new TextButton("Return", skin);
		this.returnButton.getStyle().fontColor = PirateGame.selectedColour;

		this.returnButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				parent.changeScreen(PirateGame.SKILL); //Return to game
			}
		});


		lvl1Button = new TextButton("Level 1 - Base ability", skin);
		lvl2Button = new TextButton("Level 2 - Max Level", skin);

		lvl1ButtonLabel = new Label("On button press (left Shift) creates a ray that stops all ships from targeting the player for 3 seconds (6 second cool down)", skin);
		lvl2ButtonLabel = new Label("The ships that are targeting the player will lose 50% of their health when this ability is activated (drops cool down from 6 to 5)", skin);
		lvl1ButtonLabel2 = new Label("Capture 1 college", skin);
		lvl2ButtonLabel2 = new Label("Capture 2 colleges", skin);
		Title = new Label("Disabling Ray", label1Style);



		int collegesKilledPercever = (int) currentCollegesCaptured;
		int collegesKilledObjectivePercever = (int) collegesCapturedObjective;
		int percentagePercever = (int) percentage;

		progressBarLabel = new Label(" Progress : Colleges Captured: " + collegesKilledPercever + " / " + collegesKilledObjectivePercever, skin);
		percentageLabel = new Label(percentagePercever + " % " + completion, skin);


		if (currentCollegesCaptured < 2) {
			lvl2Button.setDisabled(true);
		} else {
			lvl2Button.setDisabled(false);
		}


		titleTable.center().top();
		titleTable.add(Title);

		lvl1ButtonLabel.setColor(PirateGame.selectedColour);
		lvl2ButtonLabel.setColor(PirateGame.selectedColour);
		lvl1ButtonLabel2.setColor(PirateGame.selectedColour);
		lvl2ButtonLabel2.setColor(PirateGame.selectedColour);
		Title.setColor(PirateGame.selectedColour);
		progressBarLabel.setColor(PirateGame.selectedColour);
		percentageLabel.setColor(PirateGame.selectedColour);
		table.center();
		table.add(lvl1ButtonLabel2).padLeft(10);
		table.add(lvl1Button).padLeft(10);
		table.add(lvl1ButtonLabel);
		table.row().pad(20, 0, 20, 0);
		table.add(lvl2ButtonLabel2).padLeft(10);
		table.add(lvl2Button).padLeft(10);
		table.add(lvl2ButtonLabel);
		table.row().pad(20, 0, 20, 0);
		table.add(progressBarLabel);
		table.add(progressBar).left().pad(20, 8, 0, 8);
		table.add(percentageLabel);


		//add return button
		Label Strategy1 = new Label("Strategy: This ability can be used to get out of sticky situations or to engage the college for a few seconds without the fleet targeting the player", new Label.LabelStyle(new BitmapFont(Gdx.files.internal("textFont.fnt")), Color.WHITE));
		Strategy1.setColor(PirateGame.selectedColour);
		Other.add(this.returnButton);
		Other.add(Strategy1);
		Other.bottom().left();
	}


	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0f, 0f, 0f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// tell our stage to do actions and draw itself
		stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
		stage.draw();


	}


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

	public TextButton getReturnButton() {
		return returnButton;
	}

	public TextButton getLvl1Button() {
		return lvl1Button;
	}

	public TextButton getLvl2Button() {
		return lvl2Button;
	}
}

