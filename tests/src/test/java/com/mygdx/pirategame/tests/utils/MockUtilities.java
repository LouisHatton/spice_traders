package com.mygdx.pirategame.tests.utils;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.mygdx.pirategame.PirateGame;
import com.mygdx.pirategame.display.HUD;
import com.mygdx.pirategame.entity.Player;
import com.mygdx.pirategame.pref.AudioPreferences;
import com.mygdx.pirategame.screen.ActiveGameScreen;
import org.mockito.Mockito;
import org.mockito.internal.util.reflection.Whitebox;

public class MockUtilities {

	public static void createDefaultScoreAndPoints() {
		HUD display = Mockito.mock(HUD.class);

		// Default data.
		Whitebox.setInternalState(display, "scoreLabel", new Label("0", new Label.LabelStyle(new BitmapFont(), Color.WHITE)));
		Whitebox.setInternalState(display, "coinLabel", new Label("0", new Label.LabelStyle(new BitmapFont(), Color.YELLOW)));
		Whitebox.setInternalState(display, "coinMulti", 1);

		HUD.setScore(0);
		HUD.setCoins(0);
	}

	public static PirateGame createGame() {
		PirateGame pirateGame = Mockito.mock(PirateGame.class);

		AudioPreferences audioPreferences = new AudioPreferences();

		Whitebox.setInternalState(pirateGame, "options", audioPreferences);
		Mockito.when(pirateGame.getPreferences()).thenReturn(audioPreferences);

		return pirateGame;
	}

	public static ActiveGameScreen createScreen() {
		ActiveGameScreen screen = Mockito.mock(ActiveGameScreen.class);
		World world = new World(new Vector2(0, 0), true);

		Whitebox.setInternalState(screen, "world", world);
		Whitebox.setInternalState(screen, "camera", new OrthographicCamera());
		Whitebox.setInternalState(screen, "renderer", Mockito.mock(OrthogonalTiledMapRenderer.class));
		Mockito.when(screen.getWorld()).thenCallRealMethod();

		Whitebox.setInternalState(screen, "player", mockPlayer(screen));

		return screen;
	}

	public static PirateGame createGameAndScreen() {
		PirateGame pirateGame = createGame();
		ActiveGameScreen activeGameScreen = createScreen();

		Whitebox.setInternalState(pirateGame, "gameScreen", activeGameScreen);
		Mockito.when(pirateGame.getScreen()).thenReturn(activeGameScreen);

		Whitebox.setInternalState(activeGameScreen, "game", pirateGame);
		Whitebox.setInternalState(pirateGame, "batch", Mockito.mock(SpriteBatch.class));

		Whitebox.setInternalState(activeGameScreen, "hud", new HUD(pirateGame.batch));
		//Mockito.when(activeGameScreen.getHud()).thenReturn(new HUD(pirateGame.batch));

		Mockito.when(activeGameScreen.checkGenPos(Mockito.anyInt(), Mockito.anyInt())).thenCallRealMethod();
		Mockito.when(activeGameScreen.generateCoins(Mockito.anyInt())).thenCallRealMethod();
		Mockito.when(activeGameScreen.generateShips(Mockito.anyInt())).thenCallRealMethod();
		Mockito.when(activeGameScreen.generateRandomLocations(Mockito.anyInt())).thenCallRealMethod();

		pirateGame.setScreen(activeGameScreen); // game screen.

		return pirateGame;
	}

	public static Player mockPlayer(ActiveGameScreen activeGameScreen) {
		Player player = new Player(activeGameScreen, 1, 1, 1, 1, new OrthographicCamera());

		return player;
	}
}