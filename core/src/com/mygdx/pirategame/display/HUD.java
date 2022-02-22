package com.mygdx.pirategame.display;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.pirategame.screen.SkillsScreen;

/**
 * Hud
 * Produces a hud for the player
 *
 * @author Ethan Alabaster, Sam Pearson
 * @version 1.0
 */
public class HUD implements Disposable {

	public static Stage stage;
	private static Integer score;
	private static Integer health;
	private static Label scoreLabel;
	private static Label healthLabel;
	private static Label coinLabel;
	private static Label pointsText;
	private static Integer coins;
	private static Integer coinMulti;
	private final Viewport viewport;
	private float timeCount;
	private final Texture hp;
	private final Texture boxBackground;
	private final Texture coinPic;
	private final Image hpImg;
	private final Image box;
	private final Image coin;

	/**
	 * Retrieves information and displays it in the hud
	 * Adjusts hud with viewport
	 *
	 * @param sb Batch of images used in the hud
	 */
	public HUD(SpriteBatch sb) {
		health = 100;
		score = 0;
		coins = 0;
		coinMulti = 1;
		//Set images
		hp = new Texture("hp.png");
		boxBackground = new Texture("hudBG.png");
		coinPic = new Texture("coin.png");

		hpImg = new Image(hp);
		box = new Image(boxBackground);
		coin = new Image(coinPic);

		viewport = new ScreenViewport();
		stage = new Stage(viewport, sb);

		//Creates tables
		Table table1 = new Table(); //Counters
		Table table2 = new Table(); //Pictures or points label
		Table table3 = new Table(); //Background

		table1.top().right();
		table1.setFillParent(true);
		table2.top().right();
		table2.setFillParent(true);
		table3.top().right();
		table3.setFillParent(true);

		scoreLabel = new Label(String.format("%03d", score), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		healthLabel = new Label(String.format("%03d", health), new Label.LabelStyle(new BitmapFont(), Color.RED));
		coinLabel = new Label(String.format("%03d", coins), new Label.LabelStyle(new BitmapFont(), Color.YELLOW));
		pointsText = new Label("Points:", new Label.LabelStyle(new BitmapFont(), Color.WHITE));

		table3.add(box).width(140).height(140).padBottom(15).padLeft(30);
		table2.add(hpImg).width(32).height(32).padTop(16).padRight(90);
		table2.row();
		table2.add(coin).width(32).height(32).padTop(8).padRight(90);
		table2.row();
		table2.add(pointsText).width(32).height(32).padTop(6).padRight(90);
		table1.add(healthLabel).padTop(20).top().right().padRight(40);
		table1.row();
		table1.add(coinLabel).padTop(20).top().right().padRight(40);
		table1.row();
		table1.add(scoreLabel).padTop(22).top().right().padRight(40);
		stage.addActor(table3);
		stage.addActor(table2);
		stage.addActor(table1);
	}

	/**
	 * Changes health by value increase
	 *
	 * @param value Increase to health
	 */
	public static void changeHealth(int value) {
		health += value;
		healthLabel.setText(String.format("%02d", health));
	}

	/**
	 * Changes coins by value increase
	 *
	 * @param value Increase to coins
	 */
	public static void changeCoins(int value) {
		if (value > 0) {
			coins += value * coinMulti;
			coinLabel.setText(String.format("%03d", coins));
		}
	}

	/**
	 * Changes points by value increase
	 *
	 * @param value Increase to points
	 */
	public static void changePoints(int value) {
		score += value;
		scoreLabel.setText(String.format("%03d", score));
		//Check if a points boundary is met
		SkillsScreen.pointsCheck(score);
	}

	/**
	 * Changes health by value factor
	 *
	 * @param value Factor of coin increase
	 */
	public static void changeCoinsMulti(int value) {
		coinMulti = coinMulti * value;
	}

	/**
	 * Changes the camera size, Scales the hud to match the camera
	 *
	 * @param width  the width of the viewable area
	 * @param height the height of the viewable area
	 */
	public static void resize(int width, int height) {
		stage.getViewport().update(width, height, true);
	}

	/**
	 * Returns health value
	 *
	 * @return health : returns health value
	 */
	public static Integer getHealth() {
		return health;
	}

	/**
	 * (Not Used)
	 * Returns coins value
	 *
	 * @return health : returns coins value
	 */
	public static Integer getCoins() {
		return coins;
	}

	/**
	 * Updates the state of the hud
	 *
	 * @param dt Delta time (elapsed time since last game tick)
	 */
	public void update(float dt) {
		timeCount += dt;
		if (timeCount >= 1) {
			//Regen health every second
			if (health != 100) {
				health += 1;
				healthLabel.setText(String.format("%02d", health));
			}
			//Gain point every second
			score += 1;
			scoreLabel.setText(String.format("%03d", score));
			timeCount = 0;

			//Check if a points boundary is met
			SkillsScreen.pointsCheck(score);
		}
	}

	/**
	 * Disposes game data
	 */
	@Override
	public void dispose() {
		stage.dispose();
	}
}

