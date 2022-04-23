package com.mygdx.pirategame.tests.impl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.mygdx.pirategame.display.HUD;
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

@RunWith(GdxTestRunner.class)
public class ScoreTest {

	@BeforeClass
	public static void mockGraphics() {
		Gdx.gl20 = Mockito.mock(GL20.class);
		Gdx.gl = Gdx.gl20;
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
}