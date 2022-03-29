package com.mygdx.pirategame.tests.impl;

import com.mygdx.pirategame.display.HUD;
import com.mygdx.pirategame.entity.ship.EnemyShip;
import com.mygdx.pirategame.screen.ActiveGameScreen;
import com.mygdx.pirategame.tests.lib.GdxTestRunner;
import com.mygdx.pirategame.tests.utils.MockUtilities;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

@RunWith(GdxTestRunner.class)
public class ShipTest {

	@BeforeEach
	public void init() {
		MockUtilities.createDefaultScoreAndPoints();
	}

	@Test
	public void testInitialHealth() {
		ActiveGameScreen activeGameScreen = (ActiveGameScreen) MockUtilities.createGameAndScreen().getScreen();
		EnemyShip enemyShip = new EnemyShip(activeGameScreen, 0, 0, "unaligned_ship.png", "Unaligned");

		assertEquals("Health is not equal!", 100, enemyShip.getHealth());
	}

	@Test
	public void testTakeDamage() {
		ActiveGameScreen activeGameScreen = (ActiveGameScreen) MockUtilities.createGameAndScreen().getScreen();
		EnemyShip enemyShip = new EnemyShip(activeGameScreen, 0, 0, "unaligned_ship.png", "Unaligned");

		enemyShip.takeDamage(20);
		enemyShip.update(1);

		assertEquals("Body did not take correct damage.", 80, enemyShip.getHealth());
	}

	@Test
	public void testSetToDestroy() {
		ActiveGameScreen activeGameScreen = (ActiveGameScreen) MockUtilities.createGameAndScreen().getScreen();
		EnemyShip enemyShip = new EnemyShip(activeGameScreen, 0, 0, "unaligned_ship.png", "Unaligned");

		enemyShip.takeDamage(100);
		enemyShip.update(1);

		assertTrue("Health is not equal!", enemyShip.isSetToDestroy());
	}

	@Test
	public void testDestroyed() {
		ActiveGameScreen activeGameScreen = (ActiveGameScreen) MockUtilities.createGameAndScreen().getScreen();
		EnemyShip enemyShip = new EnemyShip(activeGameScreen, 0, 0, "unaligned_ship.png", "Unaligned");

		enemyShip.takeDamage(100);
		enemyShip.update(1);

		enemyShip.update(3); // should now be destroyed.
		assertTrue("Body is not destroyed or is active.", enemyShip.isDestroyed() && enemyShip.getBody().getUserData() == null);
	}

	@Test
	public void testOnContact() {
		ActiveGameScreen activeGameScreen = (ActiveGameScreen) MockUtilities.createGameAndScreen().getScreen();
		EnemyShip enemyShip = new EnemyShip(activeGameScreen, 0, 0, "unaligned_ship.png", "Unaligned");

		enemyShip.onContact(); // collide.

		assertEquals("Body did not take damage.", 80, enemyShip.getHealth());
		assertEquals("Points are not equal after damage.", 5, HUD.getScore());
	}

	@Test
	public void testHUDHealthRegen() {
		ActiveGameScreen activeGameScreen = (ActiveGameScreen) MockUtilities.createGameAndScreen().getScreen();

		activeGameScreen.update(1);
		activeGameScreen.hud.update(1);

		assertEquals("Health is not full", 100, HUD.getHealth());

		HUD.changeHealth(-2);

		assertEquals("Health is not 98.", 98, HUD.getHealth());

		activeGameScreen.hud.update(1);

		assertEquals("Health has not regenned.", 99, HUD.getHealth());
	}
}