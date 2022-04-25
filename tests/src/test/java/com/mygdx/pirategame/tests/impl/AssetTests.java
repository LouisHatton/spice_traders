package com.mygdx.pirategame.tests.impl;

import com.badlogic.gdx.Gdx;
import com.mygdx.pirategame.tests.lib.GdxTestRunner;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.internal.util.collections.Sets;

import java.util.Set;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

@RunWith(GdxTestRunner.class)
public class AssetTests {

	private final static Set<String> COLLEGE_SHIP_FOR_FLAG = Sets.newSet(
			"alcuin",
			"anne_lister",
			"constantine",
			"derwent",
			"goodricke"
	);

	private final static Set<String> IMPORTANT_ASSETS = Sets.newSet(
			// Skin Assets
			"skin/default.fnt",
			"skin/uiskin.atlas",
			"skin/uiskin.json",
			"skin/uiskin.png",

			// Ship Assets
			"unaligned_ship.png",
			"ship.png",
			"ship1.png",
			"enemyShip1.png",
			"player_ship.png",

			// Flag Assets
			"james_flag.png",
			"langwith_flag.png",
			"vanbrugh_flag.png"
	);

	@Test
	public void testAssetsExist() {
		boolean hasPassed = true;

		for (String requiredAsset : IMPORTANT_ASSETS) {
			if (Gdx.files.internal(requiredAsset).exists()) {
				continue;
			}

			fail("Missing asset!" + requiredAsset);
		}

		Assert.assertTrue("Valid assets", true);
	}

	@Test
	public void testShipHasFlag() {
		for (String collegeName : COLLEGE_SHIP_FOR_FLAG) {
			assertTrue("Checking that " + collegeName + " has ship", Gdx.files.internal(collegeName + "_ship.png").exists());
			assertTrue("Checking that " + collegeName + " has flag", Gdx.files.internal(collegeName + "_flag.png").exists());
		}
	}
}