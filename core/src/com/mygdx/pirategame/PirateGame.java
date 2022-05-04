package com.mygdx.pirategame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.pirategame.pref.AudioPreferences;
import com.mygdx.pirategame.screen.*;


/**
 * The start of the program. Sets up the main back bone of the game.
 * This includes most constants used throught for collision and changing screens
 * Provides access for screens to interact with eachother and the options interface
 *
 * @author Faris Alblooki
 * @version 2.0
 */
public class PirateGame extends Game {
	public static Color selectedColour = Color.WHITE;
	public static Color selectedColour2 = Color.FIREBRICK;
	public static Color selectedColour3 = Color.YELLOW;
	public static Color selectedColour4 = Color.RED;
	public static int selectedIndex = 0;
	public static final float PPM = 100;
	//Bits used in collisions
	public static final short DEFAULT_BIT = 1;
	public static final short PLAYER_BIT = 2;
	public static final short COLLEGEFIRE_BIT = 4;
	public static final short COIN_BIT = 8;
	public static final short CANNON_BIT = 16;
	public static final short ENEMY_BIT = 32;
	public static final short COLLEGE_BIT = 64;
	public static final short COLLEGESENSOR_BIT = 128;
	//Constant for swapping between screens
	public final static int MENU = 0;
	public final static int GAME = 1;
	public final static int SKILL = 2;
	public final static int SHOP = 3;
	public final static int DEATH = 4;
	public final static int HELP = 5;
	public final static int VICTORY = 6;
	public final static int BLOODIED = 7;
	public final static int SHIELD = 8;
	public final static int ULTIMATE = 10;
	public final static int BURST = 11;
	public final static int DIFFICULTY = 12;
	public final static int SETTINGS = 13;
	public final static int DISABLINGRAY = 14;
	public static float difficultyMultiplier = 1;
	public static boolean difficultySet = false;

	public SpriteBatch batch;
	public Music song;
	//Variable for each screen
	private MainMenuScreen menuScreen;
	private ActiveGameScreen gameScreen;
	private SkillsScreen skillTreeScreen;
	private ShopScreen shopScreen;
	private DeathScreen deathScreen;
	private HelpScreen helpScreen;
	private VictoryScreen victoryScreen;
	private static AudioPreferences options;
	private DifficultyScreen difficultyScreen;
	private BloodiedScreen bloodyScreen;
	private DisablingrayScreen disablingrayScreen;
	private SettingsScreen settingsScreen;
	private ShieldScreen shieldScreen;
	private UltimateScreen ultimateScreen;
	private BurstScreen burstScreen;


	private int currentScreen;

	/**
	 * Creates the main body of the game.
	 * Establises the batch for the whole game as well as sets the first screen
	 * Also sets up audio interface
	 */
	@Override
	public void create() {
		batch = new SpriteBatch();
		//Set starting screen
		MainMenuScreen mainMenu = new MainMenuScreen(this, new Stage(new ScreenViewport()));
		setScreen(mainMenu);
		//Create options
		options = new AudioPreferences();

		//Set background music and play if valid
		song = Gdx.audio.newMusic(Gdx.files.internal("pirate-music.mp3"));
		song.setLooping(true);
		if (getPreferences().isMusicEnabled()) {
			song.play();
		}
		song.setVolume(getPreferences().getMusicVolume());
	}

	/**
	 * Changes the screen without killing the prior screen. Allows for the screens to be returned to without making new ones
	 *
	 * @param screen the number of the screen that the user wants to swap to
	 */
	public Screen changeScreen(int screen) {
		//Depending on which value given, change the screen
		this.currentScreen = screen;

		switch (screen) {
			case MENU:
				if (menuScreen == null) menuScreen = new MainMenuScreen(this, new Stage(new ScreenViewport()));
				this.setScreen(menuScreen);
				break;

			case GAME:
				if (gameScreen == null) gameScreen = new ActiveGameScreen(this);
				if (skillTreeScreen == null) skillTreeScreen = new SkillsScreen(this, new Stage(new ScreenViewport()));
				this.setScreen(gameScreen);
				break;

			case SKILL:
				if (skillTreeScreen == null) skillTreeScreen = new SkillsScreen(this, new Stage(new ScreenViewport()));
				this.setScreen(skillTreeScreen);
				break;

			case BLOODIED:
				if (bloodyScreen == null) bloodyScreen = new BloodiedScreen(this, new Stage(new ScreenViewport()));
				this.setScreen(bloodyScreen);
				break;

			case DISABLINGRAY:
				if (disablingrayScreen == null)
					disablingrayScreen = new DisablingrayScreen(this, new Stage(new ScreenViewport()));
				this.setScreen(disablingrayScreen);
				break;

			case SHIELD:
				if (shieldScreen == null) shieldScreen = new ShieldScreen(this, new Stage(new ScreenViewport()));
				this.setScreen(shieldScreen);
				break;
			case ULTIMATE:
				if (ultimateScreen == null) ultimateScreen = new UltimateScreen(this, new Stage(new ScreenViewport()));
				this.setScreen(ultimateScreen);
				break;
			case BURST:
				if (burstScreen == null) burstScreen = new BurstScreen(this, new Stage(new ScreenViewport()));
				this.setScreen(burstScreen);
				break;

			case SHOP:
				if (shopScreen == null) shopScreen = new ShopScreen(this, new Stage(new ScreenViewport()));
				this.setScreen(shopScreen);
				break;

			case DEATH:
				if (deathScreen == null) deathScreen = new DeathScreen(this);
				this.setScreen(deathScreen);
				break;

			case HELP:
				if (helpScreen == null) helpScreen = new HelpScreen(this, new Stage(new ScreenViewport()));
				this.setScreen(helpScreen);
				break;

			case VICTORY:
				if (victoryScreen == null) victoryScreen = new VictoryScreen(this, new Stage(new ScreenViewport()));
				this.setScreen(victoryScreen);
				break;

			case DIFFICULTY:
				if (difficultyScreen == null) difficultyScreen = new DifficultyScreen(this, this.getScreen(), new Stage(new ScreenViewport()));
				difficultyScreen.setParent(this.screen);
				this.setScreen(difficultyScreen);
				break;

			case SETTINGS:
				if (settingsScreen == null) settingsScreen = new SettingsScreen(this, this.screen, new Stage(new ScreenViewport()));
				settingsScreen.setParent(this.screen);
				this.setScreen(settingsScreen);

				break;
		}
			return this.getScreen();
		}


	/**
	 * Allows the user to interact with the audio options
	 *
	 * @return the options object
	 */
	public static AudioPreferences getPreferences() {
		return options;
	}

	/**
	 * Kills the game screen and skill tree so they can be refreshed on next game start
	 */
	public void killGame() {
		gameScreen = null;
		skillTreeScreen = null;
	}

	/**
	 * Kill end screens so they can be made again.
	 */
	public void killEndScreen() {
		deathScreen = null;
		victoryScreen = null;
	}

	/**
	 * (Not Used)
	 * Renders the visual data for all objects
	 */
	@Override
	public void render() {
		super.render();


		if(selectedColour == null){
			selectedColour = Color.WHITE;
			selectedColour2 = Color.FIREBRICK;
			selectedColour3 = Color.YELLOW;
			selectedColour4 = Color.RED;
		}


		if(ActiveGameScreen.weatherSoundEffect == null || ActiveGameScreen.badWeather) return;
		if(ActiveGameScreen.weatherSoundEffect.isPlaying()){
			ActiveGameScreen.weatherSoundEffect.pause();
		}
	}

	/**
	 * Disposes game data
	 */
	@Override
	public void dispose() {
		batch.dispose();
	}

	public float getDifficulty() {
		return difficultyMultiplier;
	}

	public int getCurrentScreen() {
		return currentScreen;
	}
}
