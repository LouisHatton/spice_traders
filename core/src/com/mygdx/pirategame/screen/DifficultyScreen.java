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

import static com.mygdx.pirategame.screen.MainMenuScreen.renderBackground;

/**
 * Difficulty Screen
 * To select or change the difficulty
 *
 *
 * @author Faris Alblooki
 * @version 2.0
 */
public class DifficultyScreen implements Screen {


	private Screen parent;
	private final com.mygdx.pirategame.PirateGame pirateGame;
	private final Stage stage;
	String textForDiff = " ";

	private TextButton easyButton;
	private TextButton normalButton;
	private TextButton hardButton;
	private TextButton impossibleButton;
	private TextButton backButton;
	Table table = new Table();

	private Table info = new Table();

	/**
	 * Instantiates a new Difficulty Screen
	 *
	 * @param pirateGame the main starting body of the game. Where screen swapping is carried out.
	 * @param parent     the screen that called the options screen. Allows for easy return
	 */
	public DifficultyScreen(PirateGame pirateGame, Screen parent, Stage stage) {
		this.pirateGame = pirateGame;
		this.parent = parent;
		this.stage = stage;
	}


	@Override
	public void show() {
		info.clear();
		info.clearChildren();
		table.reset();
		table.clear();
		table.clearChildren();
		info.reset();
		Label Strategy1 = new Label("Note: if playing on easy the coin pick ups will not grant coin until the (CoinMultiplier) has been upgraded from the shop", new Label.LabelStyle(new BitmapFont(Gdx.files.internal("textFont.fnt")), PirateGame.selectedColour2));
		info.setFillParent(true);
		info.add(Strategy1);
		info.center().bottom();
		stage.addActor(info);
		//Set the input processor
		Gdx.input.setInputProcessor(stage);
		// Create a table for the buttons
		table.setFillParent(true);
		stage.addActor(table);

		//The skin for the actors
		Skin skin = new Skin(Gdx.files.internal("skin/uiskin.json"));

		if (PirateGame.difficultySet) {
			if (pirateGame.getDifficulty() == 0.5f) textForDiff = "Easy";
			if (pirateGame.getDifficulty() == 1) textForDiff = "Normal";
			if (pirateGame.getDifficulty() == 1.5) textForDiff = "Hard";
			if (pirateGame.getDifficulty() == 2) textForDiff = "Impossible";
			Label difficultyMsg = new Label("Current Difficulty is " + textForDiff, skin);
			difficultyMsg.setColor(PirateGame.selectedColour);
			table.add(difficultyMsg).center();
			stage.addActor(table);
			table.row();
			table.row();
		}

		//create buttons
		this.easyButton = new TextButton("Easy", skin);
		this.normalButton = new TextButton("Normal", skin);
		this.hardButton = new TextButton("Hard", skin);
		this.impossibleButton = new TextButton("Impossible", skin);
		this.backButton = new TextButton("Back", skin);
		this.easyButton.getStyle().fontColor = PirateGame.selectedColour;

		//add buttons to table
		table.add(this.easyButton).fillX().uniformX();
		table.row().pad(10, 0, 10, 0);
		table.add(this.normalButton).fillX().uniformX();
		table.row();
		table.add(this.hardButton).fillX().uniformX();
		table.row();
		table.add(this.impossibleButton).fillX().uniformX();
		table.row();
		table.add(this.backButton).fillX().uniformX();

		//add listeners to the buttons

		//Start a game
		this.easyButton.addListener(new ChangeListener() {

			@Override
			public void changed(ChangeEvent event, Actor actor) {
				PirateGame.difficultyMultiplier = 0.5f;
				PirateGame.difficultySet = true;
				pirateGame.changeScreen(PirateGame.GAME);
			}
		});
		//Help Screen
		this.normalButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				PirateGame.difficultyMultiplier = 1f;
				PirateGame.difficultySet = true;
				pirateGame.changeScreen(PirateGame.GAME);
			}
		});
		//Help Screen
		this.hardButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				PirateGame.difficultyMultiplier = 1.5f;
				PirateGame.difficultySet = true;
				pirateGame.changeScreen(PirateGame.GAME);
			}
		});

		this.impossibleButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				PirateGame.difficultyMultiplier = 2f;
				PirateGame.difficultySet = true;
				pirateGame.changeScreen(PirateGame.GAME);
			}
		});


		this.backButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				pirateGame.setScreen(parent);
			}
		});
	}


	@Override
	public void render(float dt) {
		stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
		if (!PirateGame.difficultySet) {
			Gdx.gl.glClearColor(46 / 255f, 204 / 255f, 113 / 255f, 1);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			renderBackground();
		} else {
			Gdx.gl.glClearColor(0f, 0f, 0f, 1);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		}

		stage.draw();
	}


	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height, true);
	}


	@Override
	public void pause() {
	}


	@Override
	public void resume() {
	}


	@Override
	public void hide() {
	}


	@Override
	public void dispose() {
		stage.dispose();
	}

	public TextButton getEasyButton() {
		return easyButton;
	}

	public TextButton getNormalButton() {
		return normalButton;
	}

	public TextButton getHardButton() {
		return hardButton;
	}

	public TextButton getImpossibleButton() {
		return impossibleButton;
	}

	public TextButton getBackButton() {
		return backButton;
	}

	public String getTextForDiff() {
		return textForDiff;
	}

	public void setParent(Screen parent) {
		this.parent = parent;
	}
}
