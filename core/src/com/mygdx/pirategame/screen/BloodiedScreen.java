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
import com.mygdx.pirategame.PirateGame;


/**
 * Bloodied Info Screen
 * Creates a screen with information about the Bloodied ability
 *
 *
 * @author Faris Alblooki
 * @version 2.0
 */
public class BloodiedScreen implements Screen {


	final Table Other = new Table();
	private final PirateGame parent;
	Table table = new Table();
	Table titleTable = new Table();
	Image progressBar;
	private float currentCollegesKilled = 0;
	private int collegesKilledObjective = 1;
	private float percentage = 0;
	private Stage stage;
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
	 * Instantiates a new BloodiedScreen
	 *
	 * @param pirateGame the main starting body of the game. Where screen swapping is carried out.
	 */
	public BloodiedScreen(PirateGame pirateGame, Stage stage) {
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

		currentCollegesKilled = ActiveGameScreen.player.getCollegesKilled();
		collegesKilledObjective = 2;
		percentage = ((currentCollegesKilled / collegesKilledObjective));
		percentage = percentage * 100;

		if (collegesKilledObjective <= currentCollegesKilled) {
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

		lvl1ButtonLabel = new Label("Damage increases as health decreases", skin);
		lvl2ButtonLabel = new Label("                Maximum damage multiplier increases from 50% to 75% more damage", skin);
		lvl1ButtonLabel2 = new Label("Destroy 1 college", skin);
		lvl2ButtonLabel2 = new Label("Destroy 2 colleges", skin);
		Title = new Label("Bloodied", label1Style);
		lvl1ButtonLabel.setColor(PirateGame.selectedColour);
		lvl2ButtonLabel.setColor(PirateGame.selectedColour);
		lvl1ButtonLabel2.setColor(PirateGame.selectedColour);
		lvl2ButtonLabel2.setColor(PirateGame.selectedColour);


		int collegesKilledPercever = (int) currentCollegesKilled;
		int collegesKilledObjectivePercever = (int) collegesKilledObjective;
		int percentagePercever = (int) percentage;

		progressBarLabel = new Label(" Progress : Colleges Destroyed: " + collegesKilledPercever + " / " + collegesKilledObjectivePercever, skin);
		percentageLabel = new Label("         " + percentagePercever + " % " + completion, skin);


		if (currentCollegesKilled < 2) {
			lvl2Button.setDisabled(true);
		} else {
			lvl2Button.setDisabled(false);
		}

		Title.setColor(PirateGame.selectedColour);
		progressBarLabel.setColor(PirateGame.selectedColour);
		percentageLabel.setColor(PirateGame.selectedColour);
		titleTable.center().top();
		titleTable.add(Title);


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
		table.add(progressBar).left().pad(20, 8, 0, 20);
		table.add(percentageLabel).padLeft(10).padRight(10);



		Label Strategy1 = new Label("Strategy : Ensuring your health is under 50% will give the most optimal damage output without too much risk. keep an eye on your health", new Label.LabelStyle(new BitmapFont(Gdx.files.internal("textFont.fnt")), PirateGame.selectedColour));
		//add return button
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
}

