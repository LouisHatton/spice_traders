package com.mygdx.pirategame.tests.impl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.mygdx.pirategame.PirateGame;
import com.mygdx.pirategame.screen.*;
import com.mygdx.pirategame.tests.lib.GdxTestRunner;
import com.mygdx.pirategame.tests.utils.MockUtilities;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.internal.util.reflection.Whitebox;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(GdxTestRunner.class)
public class ScreenTest {

	@BeforeClass
	public static void mockGraphics() {
		Gdx.gl20 = Mockito.mock(GL20.class);
		Gdx.gl = Gdx.gl20;
	}

	@Test
	public void testChangeVictoryScreen() {
		PirateGame pirateGame = MockUtilities.createGameAndScreen();

		Whitebox.setInternalState(pirateGame, "victoryScreen", Mockito.mock(VictoryScreen.class));
		this.mockito(pirateGame);

		pirateGame.changeScreen(PirateGame.VICTORY);

		assertTrue(pirateGame.getScreen() instanceof VictoryScreen);
	}

	@Test
	public void testChangeMenuScreen() {
		PirateGame pirateGame = MockUtilities.createGameAndScreen();

		Whitebox.setInternalState(pirateGame, "menuScreen", Mockito.mock(MainMenuScreen.class));
		this.mockito(pirateGame);

		pirateGame.changeScreen(PirateGame.MENU);

		assertTrue(pirateGame.getScreen() instanceof MainMenuScreen);
	}

	@Test
	public void testChangeGameScreen() {
		PirateGame pirateGame = MockUtilities.createGameAndScreen();

		Whitebox.setInternalState(pirateGame, "gameScreen", Mockito.mock(ActiveGameScreen.class));
		Whitebox.setInternalState(pirateGame, "skillTreeScreen", Mockito.mock(SkillsScreen.class));

		this.mockito(pirateGame);

		pirateGame.changeScreen(PirateGame.GAME);

		assertTrue(pirateGame.getScreen() instanceof ActiveGameScreen);
	}

	@Test
	public void testChangeSkillTreeScreen() {
		PirateGame pirateGame = MockUtilities.createGameAndScreen();

		Whitebox.setInternalState(pirateGame, "skillTreeScreen", Mockito.mock(SkillsScreen.class));
		this.mockito(pirateGame);

		pirateGame.changeScreen(PirateGame.SKILL);

		assertTrue(pirateGame.getScreen() instanceof SkillsScreen);
	}

	@Test
	public void testChangeShopScreen() {
		PirateGame pirateGame = MockUtilities.createGameAndScreen();

		Whitebox.setInternalState(pirateGame, "shopScreen", Mockito.mock(ShopScreen.class));
		this.mockito(pirateGame);

		pirateGame.changeScreen(PirateGame.SHOP);

		assertTrue(pirateGame.getScreen() instanceof ShopScreen);
	}

	@Test
	public void testChangeDeathScreen() {
		PirateGame pirateGame = MockUtilities.createGameAndScreen();

		Whitebox.setInternalState(pirateGame, "deathScreen", Mockito.mock(DeathScreen.class));
		this.mockito(pirateGame);

		pirateGame.changeScreen(PirateGame.DEATH);

		assertTrue(pirateGame.getScreen() instanceof DeathScreen);
	}

	@Test
	public void testChangeHelpScreen() {
		PirateGame pirateGame = MockUtilities.createGameAndScreen();

		Whitebox.setInternalState(pirateGame, "helpScreen", Mockito.mock(HelpScreen.class));
		this.mockito(pirateGame);

		pirateGame.changeScreen(PirateGame.HELP);

		assertTrue(pirateGame.getScreen() instanceof HelpScreen);
	}

	@Test
	public void testChangeDifficultyScreen() {
		PirateGame pirateGame = MockUtilities.createGameAndScreen();

		Whitebox.setInternalState(pirateGame, "difficultyScreen", Mockito.mock(DifficultyScreen.class));
		this.mockito(pirateGame);

		pirateGame.changeScreen(PirateGame.DIFFICULTY);

		assertTrue(pirateGame.getScreen() instanceof DifficultyScreen);
	}

	@Test
	public void testChangeBloodyScreen() {
		PirateGame pirateGame = MockUtilities.createGameAndScreen();

		Whitebox.setInternalState(pirateGame, "bloodyScreen", Mockito.mock(BloodiedScreen.class));
		this.mockito(pirateGame);

		pirateGame.changeScreen(PirateGame.BLOODIED);

		assertTrue(pirateGame.getScreen() instanceof BloodiedScreen);
	}

	private void mockito(PirateGame pirateGame) {
		Mockito.doCallRealMethod().when(pirateGame).setScreen(Mockito.any(Screen.class));
		Mockito.doCallRealMethod().when(pirateGame).changeScreen(Mockito.anyInt());
		Mockito.doCallRealMethod().when(pirateGame).getScreen();
		Mockito.doCallRealMethod().when(pirateGame).getCurrentScreen();
	}
}