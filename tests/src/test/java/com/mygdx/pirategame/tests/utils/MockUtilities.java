package com.mygdx.pirategame.tests.utils;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.mygdx.pirategame.display.HUD;
import com.mygdx.pirategame.pref.AudioPreferences;
import com.mygdx.pirategame.screen.ActiveGameScreen;
import org.mockito.Mockito;
import org.mockito.internal.util.reflection.Whitebox;

public class MockUtilities {

	public static void createDefaultScoreAndPoints() {
		HUD display = Mockito.mock(HUD.class);

		// Default data.
		Whitebox.setInternalState(display, "scoreLabel", new Label("0", new Label.LabelStyle(new BitmapFont(), Color.WHITE)));
		Whitebox.setInternalState(display, "coinLabel", new Label("0", new Label.LabelStyle(new BitmapFont(), Color.YELLOW)));
		Whitebox.setInternalState(display, "coinMulti", 1);

		HUD.setScore(0);
		HUD.setCoins(0);
	}


	public static ActiveGameScreen createScreen() {
		ActiveGameScreen screen = Mockito.mock(ActiveGameScreen.class);
		Mockito.when(screen.getWorld()).thenReturn(new World(new Vector2(0, 0), true));

		Whitebox.setInternalState(screen.game, "options", new AudioPreferences());
		return screen;
	}

}