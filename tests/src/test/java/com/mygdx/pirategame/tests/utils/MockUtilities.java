package com.mygdx.pirategame.tests.utils;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.pirategame.PirateGame;
import com.mygdx.pirategame.display.HUD;
import com.mygdx.pirategame.entity.Player;
import com.mygdx.pirategame.entity.college.College;
import com.mygdx.pirategame.entity.college.CollegeType;
import com.mygdx.pirategame.pref.AudioPreferences;
import com.mygdx.pirategame.screen.ActiveGameScreen;
import org.mockito.Mockito;
import org.mockito.internal.util.reflection.Whitebox;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class MockUtilities {

	public static HUD createDefaultScoreAndPoints() {
		//HUD display = Mockito.mock(HUD.class);
		HUD display = new HUD(mockStage());

		// Default data.
		Whitebox.setInternalState(display, "scoreLabel", new Label("0", new Label.LabelStyle(new BitmapFont(), Color.WHITE)));
		Whitebox.setInternalState(display, "coinLabel", new Label("0", new Label.LabelStyle(new BitmapFont(), Color.YELLOW)));
		Whitebox.setInternalState(display, "coinMulti", 1);

		HUD.respawnProtection = 0f;

		HUD.setScore(0);
		HUD.setCoins(0);

		return display;
	}

	public static PirateGame createGame() {
		PirateGame pirateGame = Mockito.mock(PirateGame.class);

		AudioPreferences audioPreferences = new AudioPreferences();

		Whitebox.setInternalState(pirateGame, "options", audioPreferences);
		//Mockito.when(pirateGame.getPreferences()).thenReturn(audioPreferences);
		Mockito.doCallRealMethod().when(pirateGame).setScreen(Mockito.any(Screen.class));
		Mockito.doCallRealMethod().when(pirateGame).changeScreen(Mockito.anyInt());
		Mockito.doCallRealMethod().when(pirateGame).getScreen();
		Mockito.doCallRealMethod().when(pirateGame).getCurrentScreen();

		PirateGame.selectedColour = Color.WHITE;

		return pirateGame;
	}

	public static ActiveGameScreen createScreen() {
		ActiveGameScreen screen = Mockito.mock(ActiveGameScreen.class);
		World world = new World(new Vector2(0, 0), true);

		Whitebox.setInternalState(screen, "world", world);
		Whitebox.setInternalState(screen, "camera", new OrthographicCamera());
		Whitebox.setInternalState(screen, "renderer", Mockito.mock(OrthogonalTiledMapRenderer.class));
		Mockito.when(screen.getWorld()).thenCallRealMethod();

		ActiveGameScreen.player = mockPlayer(screen);
		//Whitebox.setInternalState(screen, "player", mockPlayer(screen));

		return screen;
	}

	public static PirateGame createGameAndScreen() {
		createDefaultScoreAndPoints();

		PirateGame pirateGame = createGame();
		ActiveGameScreen activeGameScreen = createScreen();
		PirateGame.difficultyMultiplier = 1f;

		Whitebox.setInternalState(pirateGame, "gameScreen", activeGameScreen);
		Mockito.when(pirateGame.getScreen()).thenReturn(activeGameScreen);

		Whitebox.setInternalState(pirateGame, "batch", Mockito.mock(SpriteBatch.class));
		Whitebox.setInternalState(activeGameScreen, "game", pirateGame);

		ActiveGameScreen.game = pirateGame;

		Whitebox.setInternalState(activeGameScreen, "hud", createDefaultScoreAndPoints());
		Whitebox.setInternalState(activeGameScreen, "batch", Mockito.mock(SpriteBatch.class));
		Whitebox.setInternalState(activeGameScreen, "weatherSoundEffect", Mockito.mock(Music.class));

		//Mockito.when(activeGameScreen.getHud()).thenReturn(new HUD(pirateGame.batch));

		Mockito.when(activeGameScreen.checkGenPos(Mockito.anyInt(), Mockito.anyInt())).thenCallRealMethod();
		Mockito.when(activeGameScreen.generateCoins(Mockito.anyInt())).thenCallRealMethod();
		Mockito.when(activeGameScreen.generateShips(Mockito.anyInt())).thenCallRealMethod();
		Mockito.when(activeGameScreen.generateRandomLocations(Mockito.anyInt())).thenCallRealMethod();
		Mockito.when(activeGameScreen.getCollege(Mockito.anyString())).thenCallRealMethod();

		Set<College> colleges = Arrays.stream(CollegeType.values()).map(type -> new College(activeGameScreen, type)).collect(Collectors.toSet());
		Whitebox.setInternalState(activeGameScreen, "colleges", colleges.stream().collect(Collectors.toMap(col -> col.getType().getName(), col -> col)));

		pirateGame.setScreen(activeGameScreen); // game screen.

		return pirateGame;
	}

	public static Player mockPlayer(ActiveGameScreen activeGameScreen) {
		return new Player(activeGameScreen, 1, 1, 1, 1, new OrthographicCamera());
	}

	public static Stage mockStage() {
		Stage stage = Mockito.mock(Stage.class);
		ScreenViewport screenViewport = Mockito.mock(ScreenViewport.class);

		Mockito.when(stage.getViewport()).thenReturn(screenViewport);

		return stage;
	}
}