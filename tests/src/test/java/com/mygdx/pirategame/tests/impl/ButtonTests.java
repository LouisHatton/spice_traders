package com.mygdx.pirategame.tests.impl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.pirategame.PirateGame;
import com.mygdx.pirategame.screen.ActiveGameScreen;
import com.mygdx.pirategame.tests.lib.GdxTestRunner;
import com.mygdx.pirategame.tests.utils.MockUtilities;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.internal.util.reflection.Whitebox;

import static org.junit.Assert.*;

@RunWith(GdxTestRunner.class)
public class ButtonTests {

	@BeforeClass
	public static void mockGraphics() {
		Gdx.gl20 = Mockito.mock(GL20.class);
		Gdx.gl = Gdx.gl20;
	}

	@Test(expected = Test.None.class)
	public void testButton() {
		PirateGame pirateGame = MockUtilities.createGameAndScreen();
		ActiveGameScreen activeGameScreen = (ActiveGameScreen) pirateGame.getScreen();

		Whitebox.setInternalState(activeGameScreen, "stage", Mockito.mock(Stage.class));
		Mockito.doCallRealMethod().when(activeGameScreen).show();
		Mockito.when(activeGameScreen.getStartButton()).thenCallRealMethod();
		Mockito.when(activeGameScreen.getSkillButton()).thenCallRealMethod();
		Mockito.when(activeGameScreen.getPauseButton()).thenCallRealMethod();
		Mockito.when(activeGameScreen.getShopButton()).thenCallRealMethod();
		Mockito.when(activeGameScreen.getOptionsButton()).thenCallRealMethod();
		Mockito.when(activeGameScreen.getDifficultyButton()).thenCallRealMethod();
		Mockito.when(activeGameScreen.getExitButton()).thenCallRealMethod();


		activeGameScreen.show();

		activeGameScreen.getStartButton().toggle();
		activeGameScreen.getSkillButton().toggle();
		activeGameScreen.getPauseButton().toggle();
		activeGameScreen.getShopButton().toggle();
		activeGameScreen.getOptionsButton().toggle();
		activeGameScreen.getDifficultyButton().toggle();
		activeGameScreen.getExitButton().toggle();

		assertTrue(true); // no errors occur.
	}
}