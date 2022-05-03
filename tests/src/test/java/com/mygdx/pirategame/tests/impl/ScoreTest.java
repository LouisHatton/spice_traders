package com.mygdx.pirategame.tests.impl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.mygdx.pirategame.display.HUD;
import com.mygdx.pirategame.entity.Player;
import com.mygdx.pirategame.entity.college.College;
import com.mygdx.pirategame.entity.college.CollegeType;
import com.mygdx.pirategame.entity.ship.EnemyShip;
import com.mygdx.pirategame.screen.ActiveGameScreen;
import com.mygdx.pirategame.tests.lib.GdxTestRunner;
import com.mygdx.pirategame.tests.utils.MockUtilities;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(GdxTestRunner.class)
public class ScoreTest {

	@BeforeClass
	public static void mockGraphics() {
		Gdx.gl20 = Mockito.mock(GL20.class);
		Gdx.gl = Gdx.gl20;

		MockUtilities.createDefaultScoreAndPoints();
	}

	@Test
	public void collegeDestroyTest() {
		MockUtilities.createDefaultScoreAndPoints();

		College college = new College(MockUtilities.createScreen(), CollegeType.ANNE_LISTER);

		college.setToDestroy(true);
		college.update(5); // ticks since last action.

		assertEquals(HUD.getScore(), 100);
	}

	@Test
	public void shipDestroyTest() {
		MockUtilities.createDefaultScoreAndPoints();
		ActiveGameScreen activeGameScreen = (ActiveGameScreen) MockUtilities.createGameAndScreen().getScreen();

		EnemyShip enemyShip = new EnemyShip(activeGameScreen, 0, 0, "unaligned_ship.png", "Unaligned" );

		enemyShip.setToDestroy(true);
		enemyShip.update(5);

		assertEquals(20, HUD.getScore());
		assertEquals(10, HUD.getCoins());
	}

	@Test
	public void maximumScoreTest() {
		MockUtilities.createDefaultScoreAndPoints();
		ActiveGameScreen activeGameScreen = (ActiveGameScreen) MockUtilities.createGameAndScreen().getScreen();

		assertEquals("Not default!", 1, activeGameScreen.player.burstAmountForUltimateFire);

		HUD.changePoints(1300);

		assertEquals("Not enabled!", 2, activeGameScreen.player.burstAmountForUltimateFire);
	}

	@Test
	public void abilitiesTest() {
		MockUtilities.createDefaultScoreAndPoints();
		HUD hud = new HUD(MockUtilities.mockStage());
		ActiveGameScreen activeGameScreen = (ActiveGameScreen) MockUtilities.createGameAndScreen().getScreen();

		Player.rayEnabled = true;
		Player.shieldEnabled = true;
		Player.burstFire = true;
		Player.ultimateFirerEnabled = true;

		hud.update(1f);

		assertTrue("Abilities not valid!", Player.rayEnabled);
	}
}