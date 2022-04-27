package com.mygdx.pirategame.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.pirategame.PirateGame;
import com.mygdx.pirategame.display.HUD;

public class BurstScreen implements Screen {


	final Table Other = new Table();
	private final PirateGame parent;
	private final Stage stage;
	Table table = new Table();
	Table titleTable = new Table();
	Image progressBar;
	private float score = 0;
	private int scoreTarget = 1;
	private float percentage = 0;
	private TextButton returnButton;
	private TextButton lvl1Button;
	private TextButton lvl2Button;
	private TextButton lvl3Button;
	private TextButton lvl4Button;
	private TextButton lvl5Button;
	private TextButton lvl6Button;
	private Label lvl1ButtonLabel;
	private Label lvl2ButtonLabel;
	private Label lvl1ButtonLabel2;
	private Label lvl2ButtonLabel2;
	private Label lvl3ButtonLabel;
	private Label lvl4ButtonLabel;
	private Label lvl3ButtonLabel2;
	private Label lvl4ButtonLabel2;
	private Label lvl5ButtonLabel;
	private Label lvl6ButtonLabel;
	private Label lvl5ButtonLabel2;
	private Label lvl6ButtonLabel2;
	private Label Title;
	private Label progressBarLabel;
	private Label percentageLabel;

	public BurstScreen(PirateGame pirateGame) {
		parent = pirateGame;
		stage = new Stage(new ScreenViewport());
	}


	@Override
	public void show() {
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


		score = HUD.getScore();


		Gdx.input.setInputProcessor(stage);

		//The skin for the actors
		Skin skin = new Skin(Gdx.files.internal("skin/uiskin.json"));

		//Return Button
		this.returnButton = new TextButton("Return", skin);

		this.returnButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				parent.changeScreen(PirateGame.SKILL); //Return to game
			}
		});


		lvl1Button = new TextButton("Level 1 - Base ability", skin);
		lvl2Button = new TextButton("Level 2", skin);
		lvl3Button = new TextButton("Level 3", skin);
		lvl4Button = new TextButton("Level 4", skin);
		lvl5Button = new TextButton("Level 5", skin);
		lvl6Button = new TextButton("Level 6 - Max Level", skin);

		lvl2Button.setDisabled(true);
		lvl3Button.setDisabled(true);
		lvl4Button.setDisabled(true);
		lvl5Button.setDisabled(true);
		lvl6Button.setDisabled(true);

		lvl1ButtonLabel = new Label("", skin);
		lvl2ButtonLabel = new Label("", skin);
		lvl1ButtonLabel2 = new Label("Gain 200 Score", skin);
		lvl2ButtonLabel2 = new Label("Gain 400 Score", skin);
		lvl3ButtonLabel = new Label("", skin);
		lvl4ButtonLabel = new Label("", skin);
		lvl3ButtonLabel2 = new Label("Gain 600 Score", skin);
		lvl4ButtonLabel2 = new Label("Gain 800 Score", skin);
		lvl5ButtonLabel = new Label("", skin);
		lvl6ButtonLabel = new Label("", skin);
		lvl5ButtonLabel2 = new Label("Gain 1000 Score", skin);
		lvl6ButtonLabel2 = new Label("Gain 1200 Score", skin);
		Title = new Label("Burst Shot", skin);


		scoreTarget = 400;
		if (score >= 400) {
			lvl2Button.setDisabled(false);
			scoreTarget = 600;
		}
		if (score >= 600) {
			lvl3Button.setDisabled(false);
			scoreTarget = 800;
		}
		if (score >= 800) {
			lvl4Button.setDisabled(false);
			scoreTarget = 1000;
		}
		if (score >= 1000) {
			lvl5Button.setDisabled(false);
			scoreTarget = 1200;
		}
		if (score >= 1200) {
			lvl6Button.setDisabled(false);
		}

		if (scoreTarget <= score) {
			percentage = 100;
			completion = "- Completed!";
		} else {
			percentage = ((score / scoreTarget));
			percentage = percentage * 100;
		}


		int score1 = (int) score;
		int scoreTarget1 = (int) scoreTarget;
		int percentagePercever = (int) percentage;

		progressBarLabel = new Label(" Progress : Score: " + score1 + " / " + scoreTarget1, skin);
		percentageLabel = new Label("         " + percentagePercever + " % " + completion, skin);

		progressBar.scaleBy(500 * (percentage / 100), 22);

		titleTable.center().top();
		Title.setFontScale(3);
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
		table.add(lvl3ButtonLabel2).padLeft(10);
		table.add(lvl3Button).padLeft(10);
		table.add(lvl3ButtonLabel);
		table.row().pad(20, 0, 20, 0);
		table.add(lvl4ButtonLabel2).padLeft(10);
		table.add(lvl4Button).padLeft(10);
		table.add(lvl4ButtonLabel);
		table.row().pad(20, 0, 20, 0);
		table.add(lvl5ButtonLabel2).padLeft(10);
		table.add(lvl5Button).padLeft(10);
		table.add(lvl5ButtonLabel);
		table.row().pad(20, 0, 20, 0);
		table.add(lvl6ButtonLabel2).padLeft(10);
		table.add(lvl6Button).padLeft(10);
		table.add(lvl6ButtonLabel);
		table.row().pad(20, 0, 20, 0);
		table.add(progressBarLabel);
		table.add(progressBar).left().pad(20, 8, 0, 20);
		table.add(percentageLabel).padLeft(10).padRight(10);


		//add return button
		Other.add(this.returnButton);
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

