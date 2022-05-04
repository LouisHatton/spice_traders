package com.mygdx.pirategame.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
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
 * @author Faris Alblooki
 * @version 2.0
 */
public class SettingsScreen implements Screen {

	private final com.mygdx.pirategame.PirateGame PirateGame;
	private Screen parent;
	private final Stage stage;

	private Slider volumeMusicSlider;
	private CheckBox musicCheckbox;
	private Slider volumeEffectSlider;
	private CheckBox effectCheckbox;
	private TextButton backButton;
	private Table titleTable = new Table();
	public static SelectBox<String> selectBox;


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
		Title.setColor(PirateGame.selectedColour);


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

		selectBox = new SelectBox<String>(skin);
		selectBox.setItems("Default","Blue","Black","Red","White","Yellow","Brown","Cyan","Gold","Gray","Orange","Purple","Pink");
		selectBox.setSelectedIndex(PirateGame.selectedIndex);

		this.backButton = new TextButton("Back", skin);
		Label titleLabel = new Label("Options", skin);
		Label musicLabel = new Label("Music Volume", skin);
		Label effectLabel = new Label("Effect Volume", skin);
		Label musicOnLabel = new Label("Music On/Off", skin);
		Label effectOnLabel = new Label("Effect On/Off", skin);
		Label selectBoxLabel = new Label("Colour settings", skin);
		selectBox.addListener(new EventListener() {
			@Override
			public boolean handle(Event event) {
				if(selectBox.getSelectedIndex() == 0){
					PirateGame.selectedColour = Color.WHITE;
					PirateGame.selectedColour2 = Color.FIREBRICK;
					PirateGame.selectedColour3 = Color.YELLOW;
					PirateGame.selectedColour4 = Color.RED;
				}
				else if(selectBox.getSelectedIndex() == 1){
					PirateGame.selectedColour = Color.BLUE;
					PirateGame.selectedColour2 = Color.BLUE;
					PirateGame.selectedColour3 = Color.BLUE;
					PirateGame.selectedColour4 = Color.BLUE;
				}
				else if(selectBox.getSelectedIndex() == 2){
					PirateGame.selectedColour = Color.BLACK;
					PirateGame.selectedColour2 = Color.BLACK;
					PirateGame.selectedColour3 = Color.BLACK;
					PirateGame.selectedColour4 = Color.BLACK;
				}
				else if(selectBox.getSelectedIndex() == 3){
					PirateGame.selectedColour = Color.RED;
					PirateGame.selectedColour2 = Color.RED;
					PirateGame.selectedColour3 = Color.RED;
					PirateGame.selectedColour4 = Color.RED;
				}
				else if(selectBox.getSelectedIndex() == 4){
					PirateGame.selectedColour = Color.WHITE;
					PirateGame.selectedColour2 = Color.WHITE;
					PirateGame.selectedColour3 = Color.WHITE;
					PirateGame.selectedColour4 = Color.WHITE;
				}
				else if(selectBox.getSelectedIndex() == 5){
					PirateGame.selectedColour = Color.YELLOW;
					PirateGame.selectedColour2 = Color.YELLOW;
					PirateGame.selectedColour3 = Color.YELLOW;
					PirateGame.selectedColour4 = Color.YELLOW;
				}
				else if(selectBox.getSelectedIndex() == 6){
					PirateGame.selectedColour = Color.BROWN;
					PirateGame.selectedColour2 = Color.BROWN;
					PirateGame.selectedColour3 = Color.BROWN;
					PirateGame.selectedColour4 = Color.BROWN;
				}
				else if(selectBox.getSelectedIndex() == 7){
					PirateGame.selectedColour = Color.CYAN;
					PirateGame.selectedColour2 = Color.CYAN;
					PirateGame.selectedColour3 = Color.CYAN;
					PirateGame.selectedColour4 = Color.CYAN;
				}
				else if(selectBox.getSelectedIndex() == 8){
					PirateGame.selectedColour = Color.GOLD;
					PirateGame.selectedColour2 = Color.GOLD;
					PirateGame.selectedColour3 = Color.GOLD;
					PirateGame.selectedColour4 = Color.GOLD;
				}
				else if(selectBox.getSelectedIndex() == 9){
					PirateGame.selectedColour = Color.GRAY;
					PirateGame.selectedColour2 = Color.GRAY;
					PirateGame.selectedColour3 = Color.GRAY;
					PirateGame.selectedColour4 = Color.GRAY;
				}
				else if(selectBox.getSelectedIndex() == 10){
					PirateGame.selectedColour = Color.ORANGE;
					PirateGame.selectedColour2 = Color.ORANGE;
					PirateGame.selectedColour3 = Color.ORANGE;
					PirateGame.selectedColour4 = Color.ORANGE;
				}
				else if(selectBox.getSelectedIndex() == 11){
					PirateGame.selectedColour = Color.PURPLE;
					PirateGame.selectedColour2 = Color.PURPLE;
					PirateGame.selectedColour3 = Color.PURPLE;
					PirateGame.selectedColour4 = Color.PURPLE;
				}
				else if(selectBox.getSelectedIndex() == 12){
					PirateGame.selectedColour = Color.PINK;
					PirateGame.selectedColour2 = Color.PINK;
					PirateGame.selectedColour3 = Color.PINK;
					PirateGame.selectedColour4 = Color.PINK;
				}

				titleLabel.setColor(PirateGame.selectedColour);
				musicLabel.setColor(PirateGame.selectedColour);
				effectLabel.setColor(PirateGame.selectedColour);
				musicOnLabel.setColor(PirateGame.selectedColour);
				effectOnLabel.setColor(PirateGame.selectedColour);
				selectBoxLabel.setColor(PirateGame.selectedColour);
				PirateGame.selectedIndex = selectBox.getSelectedIndex();
				backButton.getStyle().fontColor = PirateGame.selectedColour;
				Title.setColor(PirateGame.selectedColour);
				return false;
			}
		});


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

		this.backButton.getStyle().fontColor = PirateGame.selectedColour;
		backButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				PirateGame.setScreen(parent);
			}
		});




		titleLabel.setColor(PirateGame.selectedColour);
		musicLabel.setColor(PirateGame.selectedColour);
		effectLabel.setColor(PirateGame.selectedColour);
	    musicOnLabel.setColor(PirateGame.selectedColour);
		effectOnLabel.setColor(PirateGame.selectedColour);
		selectBoxLabel.setColor(PirateGame.selectedColour);
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
		table.row().pad(10, 0, 0, 0);
		table.add(selectBoxLabel).left();
		table.add(selectBox);
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

	public void setParent(Screen parent) {
		this.parent = parent;
	}
}




