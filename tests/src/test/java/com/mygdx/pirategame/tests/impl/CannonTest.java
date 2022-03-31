package com.mygdx.pirategame.tests.impl;

import com.mygdx.pirategame.display.HUD;
import com.mygdx.pirategame.entity.cannon.CannonFire;
import com.mygdx.pirategame.entity.coin.Coin;
import com.mygdx.pirategame.entity.college.College;
import com.mygdx.pirategame.entity.college.CollegeType;
import com.mygdx.pirategame.entity.ship.EnemyShip;
import com.mygdx.pirategame.screen.ActiveGameScreen;
import com.mygdx.pirategame.tests.lib.GdxTestRunner;
import com.mygdx.pirategame.tests.utils.MockUtilities;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import static org.junit.Assert.*;

@RunWith(GdxTestRunner.class)
public class CannonTest {

	@BeforeEach
	public void init() {
		MockUtilities.createDefaultScoreAndPoints();
	}

	@Test
	public void testCannonFire() {
		ActiveGameScreen activeGameScreen = (ActiveGameScreen) MockUtilities.createGameAndScreen().getScreen();
		EnemyShip enemyShip = new EnemyShip(activeGameScreen, 0, 0, "unaligned_ship.png", "Unaligned");

		CannonFire cannonFire = new CannonFire(activeGameScreen, 0, 0, enemyShip.getBody(), 5);

		assertFalse("Cannon is spawned destroyed.", cannonFire.isDestroyed());

		cannonFire.update(0.99f); // max delta time to destroy.

		assertTrue("Cannon is not set to destroy", cannonFire.isSetToDestroy());

		cannonFire.update(0.1f);

		assertTrue("Cannon is not destroyed.", cannonFire.isDestroyed());
	}

	@Test
	public void testCollegeFire() {
		ActiveGameScreen activeGameScreen = (ActiveGameScreen) MockUtilities.createGameAndScreen().getScreen();

		College college = new College(activeGameScreen, CollegeType.ALCUIN);
		Mockito.when(activeGameScreen.getCollege("Alcuin")).thenReturn(college);

		assertTrue("CannonFire not empty to begin with!", college.getCannonBalls().isEmpty());

		college.fire();

		assertTrue("CannonFire empty when fired!", college.getCannonBalls().notEmpty());

		college.update(2.5f); // set to remove
		college.update(0.1f); // remove

		assertTrue("CannonFire not empty when expired", college.getCannonBalls().isEmpty());
	}
}