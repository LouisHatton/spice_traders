package com.mygdx.pirategame.tests.impl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.pirategame.PirateGame;
import com.mygdx.pirategame.display.HUD;
import com.mygdx.pirategame.screen.*;
import com.mygdx.pirategame.tests.lib.GdxTestRunner;
import com.mygdx.pirategame.tests.utils.MockUtilities;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.internal.util.reflection.Whitebox;

import static org.junit.Assert.*;

@RunWith(GdxTestRunner.class)
public class ButtonTests {

	@BeforeClass
	public static void mockGraphics() {
		Gdx.gl20 = Mockito.mock(GL20.class);
		Gdx.gl = Gdx.gl20;
	}

	@BeforeEach
	public void init() {
		MockUtilities.createDefaultScoreAndPoints();
	}

	@Test()
	public void testButton() {
		PirateGame pirateGame = MockUtilities.createGameAndScreen();
		ActiveGameScreen activeGameScreen = (ActiveGameScreen) pirateGame.getScreen();

		Whitebox.setInternalState(pirateGame, "skillTreeScreen", Mockito.mock(SkillsScreen.class));
		Whitebox.setInternalState(pirateGame, "shopScreen", Mockito.mock(ShopScreen.class));
		Whitebox.setInternalState(pirateGame, "settingsScreen", Mockito.mock(SettingsScreen.class));
		Whitebox.setInternalState(pirateGame, "difficultyScreen", Mockito.mock(DifficultyScreen.class));

		Whitebox.setInternalState(activeGameScreen, "stage", Mockito.mock(Stage.class));
		Mockito.doCallRealMethod().when(activeGameScreen).show();
		Mockito.when(activeGameScreen.getStartButton()).thenCallRealMethod();
		Mockito.when(activeGameScreen.getSkillButton()).thenCallRealMethod();
		Mockito.when(activeGameScreen.getPauseButton()).thenCallRealMethod();
		Mockito.when(activeGameScreen.getShopButton()).thenCallRealMethod();
		Mockito.when(activeGameScreen.getOptionsButton()).thenCallRealMethod();
		Mockito.when(activeGameScreen.getDifficultyButton()).thenCallRealMethod();
		Mockito.when(activeGameScreen.getExitButton()).thenCallRealMethod();


		activeGameScreen.show();

		activeGameScreen.getStartButton().toggle();
		activeGameScreen.getSkillButton().toggle();
		activeGameScreen.getPauseButton().toggle();
		activeGameScreen.getShopButton().toggle();
		activeGameScreen.getOptionsButton().toggle();
		activeGameScreen.getDifficultyButton().toggle();
		activeGameScreen.getExitButton().toggle();

		assertTrue(true); // no errors occur.
	}

	@Test()
	public void testBloodiedButton() {
		PirateGame pirateGame = MockUtilities.createGameAndScreen();
		BloodiedScreen bloodiedScreen = new BloodiedScreen(pirateGame, MockUtilities.mockStage());

		Whitebox.setInternalState(pirateGame, "skillTreeScreen", Mockito.mock(SkillsScreen.class));
		Whitebox.setInternalState(pirateGame, "bloodyScreen", bloodiedScreen);

		pirateGame.changeScreen(PirateGame.BLOODIED);
		bloodiedScreen.show();

		bloodiedScreen.getReturnButton().toggle();

		assertTrue(true); // no errors occur.
	}

	@Test(expected = Test.None.class)
	public void testDeathButton() {
		PirateGame pirateGame = MockUtilities.createGame();
		DeathScreen deathScreen = Mockito.mock(DeathScreen.class);

		Whitebox.setInternalState(pirateGame, "menuScreen", Mockito.mock(MainMenuScreen.class));
		Whitebox.setInternalState(deathScreen, "stage", Mockito.mock(Stage.class));
		Whitebox.setInternalState(deathScreen, "parent", pirateGame);
		Mockito.doCallRealMethod().when(deathScreen).show();
		Mockito.when(deathScreen.getReturnButton()).thenCallRealMethod();


		Whitebox.setInternalState(pirateGame, "deathScreen", deathScreen);

		pirateGame.changeScreen(PirateGame.DEATH);
		deathScreen.show();

		deathScreen.getReturnButton().toggle();

		assertTrue(true); // no errors occur.
	}

	@Test(expected = Test.None.class)
	public void testShopButtons() {
		PirateGame pirateGame = MockUtilities.createGameAndScreen();
		ShopScreen shopScreen = new ShopScreen(pirateGame, Mockito.mock(Stage.class));

		Whitebox.setInternalState(pirateGame, "menuScreen", Mockito.mock(MainMenuScreen.class));
		Whitebox.setInternalState(pirateGame, "skillTreeScreen", Mockito.mock(SkillsScreen.class));
		Whitebox.setInternalState(pirateGame, "gameScreen", Mockito.mock(ActiveGameScreen.class));

		Whitebox.setInternalState(pirateGame, "shopScreen", shopScreen);

		HUD.setCoins(100000);
		shopScreen.show();

		shopScreen.getResistanceButton().toggle();
		shopScreen.getBulletSpeedButton().toggle();
		shopScreen.getRangeButton().toggle();
		shopScreen.getDamageButton().toggle();
		shopScreen.getHealthButton().toggle();
		shopScreen.getMovementButton().toggle();
		shopScreen.getBackButton().toggle();
		shopScreen.getDpsButton().toggle();
		shopScreen.getGoldMultiplierButton().toggle();

		ShopScreen.resetStats();
		assertTrue(true); // no errors occur.
	}
}