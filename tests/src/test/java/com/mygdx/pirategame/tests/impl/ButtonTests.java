package com.mygdx.pirategame.tests.impl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.pirategame.PirateGame;
import com.mygdx.pirategame.display.HUD;
import com.mygdx.pirategame.screen.*;
import com.mygdx.pirategame.tests.lib.GdxTestRunner;
import com.mygdx.pirategame.tests.utils.MockUtilities;
import org.junit.BeforeClass;
import org.junit.Test;
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

		MockUtilities.createDefaultScoreAndPoints();
	}

	@Test
	public void testDifficultyButton() {
		PirateGame pirateGame = MockUtilities.createGameAndScreen();
		DifficultyScreen difficultyScreen = new DifficultyScreen(pirateGame, pirateGame.getScreen(), MockUtilities.mockStage());
		PirateGame.difficultySet = true;

		Whitebox.setInternalState(pirateGame, "difficultyScreen", difficultyScreen);
		Whitebox.setInternalState(pirateGame, "gameScreen", Mockito.mock(ActiveGameScreen.class));
		Whitebox.setInternalState(pirateGame, "skillTreeScreen", Mockito.mock(SkillsScreen.class));

		pirateGame.changeScreen(PirateGame.DIFFICULTY);
		difficultyScreen.show();

		difficultyScreen.getEasyButton().toggle();

		assertEquals("Difficulty not as expected!", 0.5f, PirateGame.difficultyMultiplier, 0.0);

		difficultyScreen.getNormalButton().toggle();

		assertEquals("Difficulty not as expected!", 1f, PirateGame.difficultyMultiplier, 0.0);

		difficultyScreen.getHardButton().toggle();

		assertEquals("Difficulty not as expected!", 1.5f, PirateGame.difficultyMultiplier, 0.0);

		difficultyScreen.getImpossibleButton().toggle();

		assertEquals("Difficulty not as expected!", 2f, PirateGame.difficultyMultiplier, 0.0);

		difficultyScreen.getBackButton().toggle();

		PirateGame.difficultyMultiplier = 1f;

		assertTrue(true); // no errors occur.
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
		Whitebox.setInternalState(deathScreen, "stage", MockUtilities.mockStage());
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
		ShopScreen shopScreen = new ShopScreen(pirateGame, MockUtilities.mockStage());

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

	@Test()
	public void testUltimateButtons() {
		PirateGame pirateGame = MockUtilities.createGameAndScreen();
		UltimateScreen ultimateScreen = new UltimateScreen(pirateGame, MockUtilities.mockStage());

		Whitebox.setInternalState(pirateGame, "ultimateScreen", ultimateScreen);
		Whitebox.setInternalState(pirateGame, "skillTreeScreen", Mockito.mock(SkillsScreen.class));

		pirateGame.changeScreen(PirateGame.ULTIMATE);

		ultimateScreen.show();

		ultimateScreen.getLvl1Button().toggle();
		ultimateScreen.getLvl2Button().toggle();
		ultimateScreen.getLvl3Button().toggle();
		ultimateScreen.getLvl4Button().toggle();
		ultimateScreen.getLvl5Button().toggle();
		ultimateScreen.getLvl6Button().toggle();
		ultimateScreen.getReturnButton().toggle();

		assertTrue(true); // no errors occur.
	}

	@Test()
	public void testSkillButtons() {
		PirateGame pirateGame = MockUtilities.createGameAndScreen();
		SkillsScreen skillsScreen = new SkillsScreen(pirateGame, MockUtilities.mockStage());

		Whitebox.setInternalState(pirateGame, "skillTreeScreen", skillsScreen);
		Whitebox.setInternalState(pirateGame, "ultimateScreen", Mockito.mock(UltimateScreen.class));
		Whitebox.setInternalState(pirateGame, "bloodyScreen", Mockito.mock(BloodiedScreen.class));
		Whitebox.setInternalState(pirateGame, "disablingrayScreen", Mockito.mock(DisablingrayScreen.class));
		Whitebox.setInternalState(pirateGame, "burstScreen", Mockito.mock(BurstScreen.class));
		Whitebox.setInternalState(pirateGame, "shieldScreen", Mockito.mock(ShieldScreen.class));

		pirateGame.changeScreen(PirateGame.SKILL);

		skillsScreen.show();

		skillsScreen.getBloodiedButton().toggle();
		skillsScreen.getDisablingRayButton().toggle();
		skillsScreen.getShieldButton().toggle();
		skillsScreen.getSecondaryAbilityButton().toggle();
		skillsScreen.getUltimateAbilityButton().toggle();

		assertTrue(true); // no errors occur.
	}

	@Test()
	public void testSettingsButtons() {
		PirateGame pirateGame = MockUtilities.createGameAndScreen();
		SettingsScreen settingsScreen = new SettingsScreen(pirateGame, pirateGame.getScreen(), MockUtilities.mockStage());

		Whitebox.setInternalState(pirateGame, "song", Mockito.mock(Music.class));
		Whitebox.setInternalState(pirateGame, "settingsScreen", settingsScreen);
		pirateGame.changeScreen(PirateGame.SETTINGS);

		settingsScreen.show();

		settingsScreen.getBackButton().toggle();
		settingsScreen.getEffectCheckbox().toggle();
		settingsScreen.getMusicCheckbox().toggle();
		settingsScreen.getVolumeEffectSlider().fire(new ChangeListener.ChangeEvent());
		settingsScreen.getVolumeMusicSlider().fire(new ChangeListener.ChangeEvent());

		assertTrue(true); // no errors occur.
	}

	@Test()
	public void testBurstButtons() {
		PirateGame pirateGame = MockUtilities.createGameAndScreen();
		BurstScreen burstScreen = new BurstScreen(pirateGame, MockUtilities.mockStage());

		Whitebox.setInternalState(pirateGame, "burstScreen", burstScreen);
		Whitebox.setInternalState(pirateGame, "skillTreeScreen", Mockito.mock(SkillsScreen.class));

		pirateGame.changeScreen(PirateGame.BURST);

		HUD.setScore(1300);
		burstScreen.show();

		burstScreen.getLvl1Button().toggle();
		burstScreen.getLvl2Button().toggle();
		burstScreen.getLvl3Button().toggle();
		burstScreen.getLvl4Button().toggle();
		burstScreen.getLvl5Button().toggle();
		burstScreen.getLvl6Button().toggle();
		burstScreen.getReturnButton().toggle();

		assertTrue(true); // no errors occur.
	}

	@Test()
	public void testShieldScreen() {
		PirateGame pirateGame = MockUtilities.createGameAndScreen();
		ShieldScreen shieldScreen = new ShieldScreen(pirateGame, MockUtilities.mockStage());

		Whitebox.setInternalState(pirateGame, "shieldScreen", shieldScreen);
		Whitebox.setInternalState(pirateGame, "skillTreeScreen", Mockito.mock(SkillsScreen.class));

		pirateGame.changeScreen(PirateGame.SHIELD);
		shieldScreen.show();

		shieldScreen.getLvl1Button().toggle();
		shieldScreen.getLvl2Button().toggle();
		shieldScreen.getLvl3Button().toggle();
		shieldScreen.getLvl4Button().toggle();
		shieldScreen.getLvl5Button().toggle();
		shieldScreen.getLvl6Button().toggle();
		shieldScreen.getReturnButton().toggle();

		assertTrue(true); // no errors occur.
	}

	@Test()
	public void testDisablingRayScreen() {
		PirateGame pirateGame = MockUtilities.createGameAndScreen();
		DisablingrayScreen disablingrayScreen = new DisablingrayScreen(pirateGame, MockUtilities.mockStage());

		Whitebox.setInternalState(pirateGame, "disablingrayScreen", disablingrayScreen);
		Whitebox.setInternalState(pirateGame, "skillTreeScreen", Mockito.mock(SkillsScreen.class));

		pirateGame.changeScreen(PirateGame.DISABLINGRAY);
		disablingrayScreen.show();

		disablingrayScreen.getLvl1Button().toggle();
		disablingrayScreen.getLvl2Button().toggle();
		disablingrayScreen.getReturnButton().toggle();

		assertTrue(true); // no errors occur.
	}

	@Test
	public void testVictoryButton() {
		PirateGame pirateGame = MockUtilities.createGame();
		VictoryScreen victoryScreen = new VictoryScreen(pirateGame, MockUtilities.mockStage());

		Whitebox.setInternalState(pirateGame, "menuScreen", Mockito.mock(MainMenuScreen.class));
		Whitebox.setInternalState(pirateGame, "victoryScreen", victoryScreen);

		pirateGame.changeScreen(PirateGame.VICTORY);
		victoryScreen.show();

		victoryScreen.getBackButton().toggle();

		assertTrue(true); // no errors occur.
	}

	@Test
	public void testMainMenuButtons() {
		PirateGame pirateGame = MockUtilities.createGame();
		MainMenuScreen mainMenuScreen = new MainMenuScreen(pirateGame, MockUtilities.mockStage());

		Whitebox.setInternalState(pirateGame, "menuScreen", mainMenuScreen);
		Whitebox.setInternalState(pirateGame, "difficultyScreen", Mockito.mock(DifficultyScreen.class));
		Whitebox.setInternalState(pirateGame, "settingsScreen", Mockito.mock(SettingsScreen.class));
		Whitebox.setInternalState(pirateGame, "helpScreen", Mockito.mock(HelpScreen.class));

		pirateGame.changeScreen(PirateGame.MENU);
		mainMenuScreen.show();

		mainMenuScreen.getHelpButton().toggle();
		mainMenuScreen.getNewGameButton().toggle();
		//mainMenuScreen.getExitButton().toggle();
		//mainMenuScreen.getResumeGameButton().toggle();
		mainMenuScreen.getOptionsButton().toggle();

		assertTrue(true); // no errors occur.
	}

	@Test
	public void testHelpButton() {
		PirateGame pirateGame = MockUtilities.createGame();
		HelpScreen helpScreen = new HelpScreen(pirateGame, MockUtilities.mockStage());

		Whitebox.setInternalState(pirateGame, "menuScreen", Mockito.mock(MainMenuScreen.class));
		Whitebox.setInternalState(pirateGame, "helpScreen", helpScreen);

		pirateGame.changeScreen(PirateGame.HELP);
		helpScreen.show();

		helpScreen.getBackButton().toggle();

		assertTrue(true); // no errors occur.
	}
}