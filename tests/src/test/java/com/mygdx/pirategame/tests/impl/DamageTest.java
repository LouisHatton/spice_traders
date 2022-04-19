package com.mygdx.pirategame.tests.impl;

import apple.laf.JRSUIConstants;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.mygdx.pirategame.PirateGame;
import com.mygdx.pirategame.display.HUD;
import com.mygdx.pirategame.entity.Enemy;
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
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.util.reflection.Whitebox;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(GdxTestRunner.class)
public class DamageTest {

	@BeforeClass
	public static void mockGraphics() {
		Gdx.gl20 = Mockito.mock(GL20.class);
		Gdx.gl = Gdx.gl20;
	}

	@BeforeEach
	public void scoreAndPoints() {
		MockUtilities.createDefaultScoreAndPoints();
	}

	@Test
	public void testChangeDamage() {
		ActiveGameScreen activeGameScreen = (ActiveGameScreen) MockUtilities.createGameAndScreen().getScreen();
		List<EnemyShip> generatedShips = activeGameScreen.generateShips(10);

		Whitebox.setInternalState(activeGameScreen, "ships", generatedShips);
		Mockito.when(activeGameScreen.getShips()).thenCallRealMethod();

		assertTrue("Ships not default damage!", activeGameScreen.getShips().stream().allMatch(enemyShip -> enemyShip.getDamage() == 20));

		ActiveGameScreen.changeDamage(5);

		assertTrue("Ships damage not updated!", activeGameScreen.getShips().stream().allMatch(enemyShip -> enemyShip.getDamage() == 25));
	}

	@Test
	public void testOverWithNoHealth() {
		PirateGame pirateGame = MockUtilities.createGameAndScreen();
		ActiveGameScreen activeGameScreen = (ActiveGameScreen) pirateGame.getScreen();

		Whitebox.setInternalState(pirateGame, "deathScreen", Mockito.mock(DeathScreen.class));

		Mockito.doCallRealMethod().when(pirateGame).setScreen(Mockito.any(Screen.class));
		Mockito.doCallRealMethod().when(pirateGame).changeScreen(Mockito.anyInt());
		Mockito.doCallRealMethod().when(pirateGame).getCurrentScreen();
		Mockito.doCallRealMethod().when(activeGameScreen).gameOverCheck();

		HUD.changeHealth(-110);
		activeGameScreen.gameOverCheck();

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