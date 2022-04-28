package com.mygdx.pirategame.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.pirategame.PirateGame;

/**
 * Provides a UI for the user to interact with the audioControls interface
 *
 * @author Sam Pearson
 * @version 1.0
 */
public class SettingsScreen implements Screen {

	private final com.mygdx.pirategame.PirateGame PirateGame;
	private final Screen parent;
	private final Stage stage;

	private Slider volumeMusicSlider;
	private CheckBox musicCheckbox;
	private Slider volumeEffectSlider;
	private CheckBox effectCheckbox;
	private TextButton backButton;
	private Table titleTable = new Table();


	/**
	 * Instantiates a new Options screen
	 *
	 * @param pirateGame the main starting body of the game. Where screen swapping is carried out.
	 * @param parent     the screen that called the options screen. Allows for easy return
	 */
	public SettingsScreen(PirateGame pirateGame, Screen parent, Stage stage) {
		this.PirateGame = pirateGame;
		this.parent = parent;
		this.stage = stage;
	}

	/**
	 * What should be displayed on the options screen
	 */
	@Override
	public void show() {
		titleTable.clear();
		titleTable.clearChildren();
		titleTable.reset();
		Label.LabelStyle label1Style = new Label.LabelStyle();
		BitmapFont font = new BitmapFont(Gdx.files.internal("font.fnt"));
		label1Style.font = font;

		Label Title = new Label("Settings", label1Style);


		//Set the input processor
		Gdx.input.setInputProcessor(stage);
		stage.clear();
		// Create the main table
		Table table = new Table();
		table.setFillParent(true);
		stage.addActor(table);
		titleTable.center().top();
		titleTable.setFillParent(true);
		titleTable.add(Title);
		stage.addActor(titleTable);

		//The skin for the actors
		Skin skin = new Skin(Gdx.files.internal("skin/uiskin.json"));


		//Music Sliders and Check boxes
		this.volumeMusicSlider = new Slider(0f, 1f, 0.1f, false, skin);

		//Set value to current option
		volumeMusicSlider.setValue(PirateGame.getPreferences().getMusicVolume());

		volumeMusicSlider.addListener(new EventListener() {
			@Override
			public boolean handle(Event event) {
				PirateGame.getPreferences().setMusicVolume(volumeMusicSlider.getValue());  //Change music value in options to slider
				PirateGame.song.setVolume(PirateGame.getPreferences().getMusicVolume()); //Change the volume

				return false;
			}
		});

		this.musicCheckbox = new CheckBox(null, skin);

		//Check if it should be checked or unchecked by default
		musicCheckbox.setChecked(PirateGame.getPreferences().isMusicEnabled());

		musicCheckbox.addListener(new EventListener() {
			@Override
			public boolean handle(Event event) {
				boolean enabled = musicCheckbox.isChecked(); //Get checked value
				PirateGame.getPreferences().setMusicEnabled(enabled); //Set

				if (PirateGame.getPreferences().isMusicEnabled()) { //Play or don't
					PirateGame.song.play();
				} else {
					PirateGame.song.pause();
				}

				return false;
			}
		});

		//EFFECTS
		this.volumeEffectSlider = new Slider(0f, 1f, 0.1f, false, skin);
		volumeEffectSlider.setValue(PirateGame.getPreferences().getEffectsVolume()); //Set value to current option
		volumeEffectSlider.addListener(new EventListener() {
			@Override
			public boolean handle(Event event) {
				PirateGame.getPreferences().setEffectsVolume(volumeEffectSlider.getValue()); //Change effect value in options to slider
				return false;
			}
		});

		this.effectCheckbox = new CheckBox(null, skin);
		effectCheckbox.setChecked(PirateGame.getPreferences().isEffectsEnabled());
		effectCheckbox.addListener(new EventListener() {
			@Override
			public boolean handle(Event event) {
				boolean enabled = effectCheckbox.isChecked(); //Get checked value
				PirateGame.getPreferences().setEffectsEnabled(enabled); //Set
				return false;
			}
		});

		// return to main screen button
		this.backButton = new TextButton("Back", skin);
		backButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				PirateGame.setScreen(parent);
			}
		});


		Label titleLabel = new Label("Options", skin);
		Label musicLabel = new Label("Music Volume", skin);
		Label effectLabel = new Label("Effect Volume", skin);
		Label musicOnLabel = new Label("Music On/Off", skin);
		Label effectOnLabel = new Label("Effect On/Off", skin);

		//add buttons,sliders and labels to table
		table.add(titleLabel).colspan(2);
		table.row().pad(10, 0, 0, 0);
		table.add(musicLabel).left();
		table.add(volumeMusicSlider);
		table.row().pad(10, 0, 0, 0);
		table.add(musicOnLabel).left();
		table.add(musicCheckbox);
		table.row().pad(10, 0, 0, 0);
		table.add(effectLabel).left();
		table.add(volumeEffectSlider);
		table.row().pad(10, 0, 0, 0);
		table.add(effectOnLabel).left();
		table.add(effectCheckbox);
		table.row().pad(10, 0, 0, 10);
		table.add(backButton);

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
		// TODO Auto-generated method stub
	}

	/**
	 * (Not Used)
	 * Resumes game
	 */
	@Override
	public void resume() {
		// TODO Auto-generated method stub
	}

	/**
	 * (Not Used)
	 * Hides game
	 */

	@Override
	public void hide() {
		// TODO Auto-generated method stub
	}

	/**
	 * Disposes game data
	 */
	@Override
	public void dispose() {
		// TODO Auto-generated method stub
	}

	public Slider getVolumeMusicSlider() {
		return volumeMusicSlider;
	}

	public CheckBox getMusicCheckbox() {
		return musicCheckbox;
	}

	public Slider getVolumeEffectSlider() {
		return volumeEffectSlider;
	}

	public CheckBox getEffectCheckbox() {
		return effectCheckbox;
	}

	public TextButton getBackButton() {
		return backButton;
	}
}




