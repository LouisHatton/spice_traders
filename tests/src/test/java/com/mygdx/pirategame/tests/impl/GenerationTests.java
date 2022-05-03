package com.mygdx.pirategame.tests.impl;

import com.mygdx.pirategame.screen.ActiveGameScreen;
import com.mygdx.pirategame.tests.lib.GdxTestRunner;
import com.mygdx.pirategame.tests.utils.MockUtilities;
import com.mygdx.pirategame.utils.Location;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

@RunWith(GdxTestRunner.class)
public class GenerationTests {

	@BeforeClass
	public static void scoreAndPoints() {
		MockUtilities.createDefaultScoreAndPoints();
	}

	@Test
	public void generateLocations() {
		ActiveGameScreen activeGameScreen = (ActiveGameScreen) MockUtilities.createGameAndScreen().getScreen();
		boolean anyMatch = false;

		Set<Location> randomLocations = activeGameScreen.generateRandomLocations(20);

		assertEquals("Random Locations not generated!", 20, randomLocations.size());

		for (int i = 0; i < randomLocations.size(); i++) {
			Location foundLocation = randomLocations.stream().findAny().get();

			randomLocations.remove(foundLocation);
			anyMatch = randomLocations.contains(foundLocation); // override equals() method to solely check x, y
		}

		assertFalse("Duplicate random location found!", anyMatch);
	}

	@Test
	public void generateShips() {
		ActiveGameScreen activeGameScreen = (ActiveGameScreen) MockUtilities.createGameAndScreen().getScreen();

		assertEquals("Ships were not generated!", 20, activeGameScreen.generateShips(20).size());
	}

	@Test
	public void generateCoins() {
		ActiveGameScreen activeGameScreen = (ActiveGameScreen) MockUtilities.createGameAndScreen().getScreen();

		assertEquals("Coins were not generated!", 10, activeGameScreen.generateCoins(10).size());
	}
}