package com.mygdx.pirategame.tests.impl;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.pirategame.entity.college.College;
import com.mygdx.pirategame.entity.college.CollegeType;
import com.mygdx.pirategame.entity.tile.type.CollegeWalls;
import com.mygdx.pirategame.screen.ActiveGameScreen;
import com.mygdx.pirategame.tests.lib.GdxTestRunner;
import com.mygdx.pirategame.tests.utils.MockUtilities;
import com.mygdx.pirategame.utils.WorldCreator;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;

@RunWith(GdxTestRunner.class)
public class CollegeWallTest {

	@BeforeClass
	public static void init() {
		MockUtilities.createDefaultScoreAndPoints();
	}

	@Test
	public void testContact() {
		ActiveGameScreen activeGameScreen = (ActiveGameScreen) MockUtilities.createGameAndScreen().getScreen();

		College college = new College(activeGameScreen, CollegeType.ALCUIN);
		Mockito.when(activeGameScreen.getCollege("Alcuin")).thenReturn(college);

		CollegeWalls collegeWalls = new CollegeWalls(activeGameScreen, "Alcuin", Mockito.mock(Rectangle.class));

		assertEquals("Did not spawn with full health!", 100, college.getHealth());

		collegeWalls.update();
		collegeWalls.onContact();

		assertEquals("Did not take expected damage.", 98, college.getHealth());
	}

	@Test
	public void testWallsPerCollege() {
		ActiveGameScreen activeGameScreen = (ActiveGameScreen) MockUtilities.createGameAndScreen().getScreen();

		TmxMapLoader tmxMapLoader = new TmxMapLoader();
		TiledMap map = tmxMapLoader.load("map.tmx");

		Mockito.when(activeGameScreen.getMap()).thenReturn(map);

		WorldCreator worldCreator = new WorldCreator(activeGameScreen);
		assertEquals("Not all colleges have walls!", (CollegeType.values().length * 2), worldCreator.getWalls().size()); // each college has two walls.
	}
}