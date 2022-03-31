package com.mygdx.pirategame.tests.impl;

import com.badlogic.gdx.math.Rectangle;
import com.mygdx.pirategame.entity.cannon.CannonFire;
import com.mygdx.pirategame.entity.college.College;
import com.mygdx.pirategame.entity.college.CollegeType;
import com.mygdx.pirategame.entity.ship.EnemyShip;
import com.mygdx.pirategame.entity.tile.type.CollegeWalls;
import com.mygdx.pirategame.screen.ActiveGameScreen;
import com.mygdx.pirategame.tests.lib.GdxTestRunner;
import com.mygdx.pirategame.tests.utils.MockUtilities;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import static org.junit.Assert.*;

@RunWith(GdxTestRunner.class)
public class CollegeWallTest {

	@BeforeEach
	public void init() {
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

		assertEquals("Did not take expected damage.", 90, college.getHealth());
	}
}