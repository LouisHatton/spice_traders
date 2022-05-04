package com.mygdx.pirategame.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.pirategame.PirateGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.height = 1060;
		config.width = 1900;
		//config.useGL30 = true;
		config.foregroundFPS = 60;
		config.backgroundFPS = 60;
		//config.addIcon("models/Black_Flag.png", Files.FileType.Internal);
		new LwjglApplication(new PirateGame(), config);
	}
}
