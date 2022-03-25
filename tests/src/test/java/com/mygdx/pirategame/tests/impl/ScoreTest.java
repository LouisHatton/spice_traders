package com.mygdx.pirategame.tests.impl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.mygdx.pirategame.PirateGame;
import com.mygdx.pirategame.display.HUD;
import com.mygdx.pirategame.entity.college.College;
import com.mygdx.pirategame.entity.ship.EnemyShip;
import com.mygdx.pirategame.tests.lib.GdxTestRunner;
import com.mygdx.pirategame.tests.utils.MockUtilities;
import com.mygdx.pirategame.utils.SpawnUtils;
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

		College college = new College(MockUtilities.createScreen(), "Anne Lister", 6304 / PirateGame.PPM, 1199 / PirateGame.PPM,
				"anne_lister_flag.png", "anne_lister_ship.png", 8, new SpawnUtils());

		college.setToDestroy(true);
		college.update(5); // ticks since last action.

		assertEquals(HUD.getScore(), 100);
	}

	@Test
	public void shipDestroyTest() {
		MockUtilities.createDefaultScoreAndPoints();

		EnemyShip enemyShip = new EnemyShip(MockUtilities.createScreen(), 0, 0, "unaligned_ship.png", "Unaligned");

		enemyShip.setToDestroy(true);
		enemyShip.update(5);

		assertEquals(HUD.getScore(), 20);
		assertEquals(HUD.getCoins(), 10);
	}
}