package com.mygdx.pirategame.tests.impl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.mygdx.pirategame.PirateGame;
import com.mygdx.pirategame.display.HUD;
import com.mygdx.pirategame.entity.Player;
import com.mygdx.pirategame.entity.college.College;
import com.mygdx.pirategame.entity.college.CollegeType;
import com.mygdx.pirategame.entity.ship.EnemyShip;
import com.mygdx.pirategame.screen.ActiveGameScreen;
import com.mygdx.pirategame.screen.DeathScreen;
import com.mygdx.pirategame.screen.VictoryScreen;
import com.mygdx.pirategame.tests.lib.GdxTestRunner;
import com.mygdx.pirategame.tests.utils.MockUtilities;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.internal.util.reflection.Whitebox;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(GdxTestRunner.class)
public class DamageTest {

	@BeforeClass
	public static void mockGraphics() {
		Gdx.gl20 = Mockito.mock(GL20.class);
		Gdx.gl = Gdx.gl20;

		MockUtilities.createDefaultScoreAndPoints();
	}

	@Test
	public void testChangeDamage() {
		ActiveGameScreen activeGameScreen = (ActiveGameScreen) MockUtilities.createGameAndScreen().getScreen();
		List<EnemyShip> generatedShips = activeGameScreen.generateShips(10);

		Whitebox.setInternalState(activeGameScreen, "ships", generatedShips);
		//Mockito.doCallRealMethod().when(ActiveGameScreen.getShips());

		assertEquals(ActiveGameScreen.getShips().size(), 10);
		assertTrue("Ships not default damage!", ActiveGameScreen.getShips().stream().allMatch(enemyShip -> enemyShip.getDamage() == 20));

		ActiveGameScreen.changeDamage(5);

		assertTrue("Ships damage not updated!", ActiveGameScreen.getShips().stream().allMatch(enemyShip -> enemyShip.getDamage() == 25));
	}

	@Test
	public void testChangeHealth() {
		ActiveGameScreen activeGameScreen = (ActiveGameScreen) MockUtilities.createGameAndScreen().getScreen();

		HUD.changeHealth(-1);

		assertEquals(HUD.getHealth(), 99);

		HUD.changeHealth(1);

		assertEquals("Health is not full!", 100, HUD.getHealth());
	}

	@Test
	public void testOverWithNoHealth() {
		PirateGame pirateGame = MockUtilities.createGameAndScreen();
		ActiveGameScreen activeGameScreen = (ActiveGameScreen) pirateGame.getScreen();

		PirateGame.difficultyMultiplier = 1;
		Whitebox.setInternalState(pirateGame, "deathScreen", Mockito.mock(DeathScreen.class));

		Mockito.doCallRealMethod().when(pirateGame).setScreen(Mockito.any(Screen.class));
		Mockito.doCallRealMethod().when(pirateGame).changeScreen(Mockito.anyInt());
		Mockito.doCallRealMethod().when(pirateGame).getCurrentScreen();
		Mockito.doCallRealMethod().when(activeGameScreen).gameOverCheck();

		HUD.changeHealth(-130);
		activeGameScreen.gameOverCheck();

		HUD.respawnProtection = 0f;

		assertEquals("You are not dead!", PirateGame.DEATH, pirateGame.getCurrentScreen());
	}

	@Test
	public void testOverAllDestroyed() {
		PirateGame pirateGame = MockUtilities.createGameAndScreen();
		ActiveGameScreen activeGameScreen = (ActiveGameScreen) pirateGame.getScreen();

		Set<College> colleges = new HashSet<>();
		colleges.add(new College(activeGameScreen, CollegeType.ALCUIN));

		College college = new College(activeGameScreen, CollegeType.CONSTANTINE);
		college.setToDestroy(true);
		colleges.add(college);

		Player player = MockUtilities.mockPlayer(activeGameScreen);

		Whitebox.setInternalState(activeGameScreen, "player", player);
		Whitebox.setInternalState(activeGameScreen, "colleges", colleges.stream().collect(Collectors.toMap(col -> col.getType().getName(), col -> col)));
		Whitebox.setInternalState(pirateGame, "victoryScreen", Mockito.mock(VictoryScreen.class));

		Mockito.doCallRealMethod().when(pirateGame).setScreen(Mockito.any(Screen.class));
		Mockito.doCallRealMethod().when(pirateGame).changeScreen(Mockito.anyInt());
		Mockito.doCallRealMethod().when(pirateGame).getCurrentScreen();
		Mockito.doCallRealMethod().when(activeGameScreen).gameOverCheck();
		Mockito.doCallRealMethod().when(activeGameScreen).update(Mockito.anyFloat());

		activeGameScreen.update(1f);
		activeGameScreen.gameOverCheck();

		assertEquals("Not defined as a victory", PirateGame.VICTORY, pirateGame.getCurrentScreen());
	}
}