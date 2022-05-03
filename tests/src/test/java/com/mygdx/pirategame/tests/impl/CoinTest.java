package com.mygdx.pirategame.tests.impl;

import com.mygdx.pirategame.display.HUD;
import com.mygdx.pirategame.entity.coin.Coin;
import com.mygdx.pirategame.screen.ActiveGameScreen;
import com.mygdx.pirategame.tests.lib.GdxTestRunner;
import com.mygdx.pirategame.tests.utils.MockUtilities;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(GdxTestRunner.class)
public class CoinTest {

	@BeforeClass
	public static void init() {
		MockUtilities.createDefaultScoreAndPoints();
	}

	@Test
	public void testCoinSpawn() {
		ActiveGameScreen activeGameScreen = (ActiveGameScreen) MockUtilities.createGameAndScreen().getScreen();

		activeGameScreen.update(1);
		activeGameScreen.hud.update(1);

		assertEquals("Coins are not empty.", 0, HUD.getCoins());

		Coin coin = new Coin(activeGameScreen, 0, 0);
		coin.onContact();

		assertTrue("Coin is not going to be destroyed!", coin.isSetToDestroyed());
		assertEquals("Coin did not redeem.", 1, HUD.getCoins());

		coin.update();
		assertTrue("Coin has not been destroyed.", coin.isDestroyed());
	}
}