package com.mygdx.pirategame.display;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.pirategame.PirateGame;
import com.mygdx.pirategame.entity.Player;
import com.mygdx.pirategame.screen.ActiveGameScreen;
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
	public static float resistanceMultiplier = 100;
	public static float maxHealth = 100;
	public static float respawnProtection = 4f;
	public static float bloodyAmount = 0.5f;
	private static int score;
	private static int health;
	private static Label scoreLabel;
	private static Label healthLabel;
	private static Label coinLabel;
	private static Label pointsText;
	private static int coins;
	private static float coinMulti;
	private final Texture hp;
	private final Texture boxBackground;
	private final Texture coinPic;
	private final Image hpImg;
	private final Image box;
	private final Image coin;
	private float timeCount;

	Table abilities1 = new Table();
	Table abilities2 = new Table();
	Table abilities3 = new Table();

	Image shotLogo = new Image(new Texture("shot.png"));
	Image burstLogo = new Image(new Texture("burst.png"));
	Image ultimateLogo = new Image(new Texture("ultimate.png"));
	Image shieldLogo = new Image(new Texture("shield.png"));
	Image empLogo = new Image(new Texture("empicon.png"));
	Image darkshotLogo = new Image(new Texture("shot_dark.png"));
	Image darkburstLogo = new Image(new Texture("burst_dark.png"));
	Image darkultimateLogo = new Image(new Texture("ultimate_dark.png"));
	Image darkshieldLogo = new Image(new Texture("shield_dark.png"));
	Image darkempLogo = new Image(new Texture("empicon_dark.png"));

	Stack burstStack = new Stack();
	Stack ultimateStack = new Stack();
	Stack shieldStack = new Stack();
	Stack empStack = new Stack();

	boolean burstStackAdded = false;
	boolean ultimateStackAdded = false;
	boolean shieldStackAdded = false;
	boolean empStackAdded = false;

	boolean burstStackOnCd = false;
	boolean ultimateStackOnCd = false;
	boolean shieldStackOnCd = false;
	boolean empStackOnCd = false;

	boolean burstStackOnCd2 = false;
	boolean ultimateStackOnCd2 = false;
	boolean shieldStackOnCd2 = false;
	boolean empStackOnCd2 = false;

	Label burstCooldown;
	Label ultimateCooldown;
	Label shieldCooldown;
	Label empCooldown;
	/**
	 * Retrieves information and displays it in the hud
	 * Adjusts hud with viewport
	 *
	 *
	 */
	public HUD(Stage stage) {
		Label.LabelStyle label1Style = new Label.LabelStyle();
		BitmapFont font = new BitmapFont(Gdx.files.internal("font.fnt"));
		label1Style.font = font;

		HUD.stage = stage;
		abilities3.setFillParent(true);
		abilities2.setFillParent(true);
		abilities1.setFillParent(true);
		abilities1.add(shotLogo);

		burstCooldown = new Label("0", label1Style);
		ultimateCooldown = new Label("0",label1Style);
		shieldCooldown = new Label("0", label1Style);
		empCooldown = new Label("0", label1Style);

		burstStack.add(darkburstLogo);
		burstStack.add(burstCooldown);
		ultimateStack.add(darkultimateLogo);
		ultimateStack.add(ultimateCooldown);
		shieldStack.add(darkshieldLogo);
		shieldStack.add(shieldCooldown);
		empStack.add(darkempLogo);
		empStack.add(empCooldown);

		abilities1.bottom().left();
		abilities2.center().bottom();
		abilities3.bottom().right();

		stage.addActor(abilities1);
		stage.addActor(abilities2);
		stage.addActor(abilities3);


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
		if (respawnProtection > 0) return;
		if (value < 0 && Player.protectedTimer > 0f) return;
		if (value > 0) {
			if (health + value > maxHealth) {
				health = (int) maxHealth;
				healthLabel.setText(String.format("%02d", health));
				return;
			}
			health += value;
			healthLabel.setText(String.format("%02d", health));
			return;
		}


		health = (int) (health + ((value * (resistanceMultiplier / 100)) * PirateGame.difficultyMultiplier));
		healthLabel.setText(String.format("%02d", health));
	}

	public static void upgradResistance(float multiplier) {
		resistanceMultiplier -= multiplier;
	}

	public static void upgradMaxHealth(float amount) {
		maxHealth += amount;
	}

	public static boolean purchase(float value) {
		if (coins >= value) {
			coins -= value;
			coinLabel.setText(String.format("%03d", coins));
			return true;
		}
		return false;
	}

	/**
	 * Changes coins by value increase
	 *
	 * @param value Increase to coins
	 */
	public static void changeCoins(int value) {
		if (value > 0) {
			coins = (int) (coins + (value * PirateGame.difficultyMultiplier) * coinMulti);
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

		if (score >= 200) {
			if(!ActiveGameScreen.player.burstFire)ActiveGameScreen.player.burstCooldown = 0;
			ActiveGameScreen.player.burstFire = true;

		}
		if (score >= 400) {
			ActiveGameScreen.player.amountToTheSide = 2;
		}
		if (score >= 500) {
			ActiveGameScreen.player.ultimateFirerEnabled = true;
		}
		if (score >= 600) {
			ActiveGameScreen.player.amountOfBullets = 4;
			ActiveGameScreen.player.ogburstTimer = 7;
		}
		if (score >= 650) {
			ActiveGameScreen.player.ultimateAmountMultiplier = 1.5f;
		}
		if (score >= 800) {
			ActiveGameScreen.player.amountOfShotsInUltimateFire = 15;
			ActiveGameScreen.player.amountToTheSide = 3;
		}
		if (score >= 950) {
			ActiveGameScreen.player.ultimateAmountMultiplier = 2f;
		}
		if (score >= 1000) {
			ActiveGameScreen.player.amountOfBullets = 5;
		}
		if (score >= 1100) {
			ActiveGameScreen.player.amountOfShotsInUltimateFire = 20;
		}
		if (score >= 1200) {
			ActiveGameScreen.player.ultOnShot = true;
			ActiveGameScreen.player.ogburstTimer = 6;
		}
		if (score >= 1250) {
			ActiveGameScreen.player.burstAmountForUltimateFire = 2;
		}
	}

	/**
	 * Changes health by value factor
	 *
	 * @param value Factor of coin increase
	 */
	public static void changeCoinsMulti(float value) {
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
	public static int getHealth() {
		return health;
	}

	/**
	 * (Not Used)
	 * Returns coins value
	 *
	 * @return health : returns coins value
	 */
	public static int getCoins() {
		return coins;
	}

	public static void setCoins(int value) {
		coins = value;
		coinLabel.setText(String.format("%03d", coins));
	}

	public static int getScore() {
		return score;
	}

	public static void setScore(int value) {
		score = value;
		scoreLabel.setText(String.format("%03d", score));
	}

	/**
	 * Updates the state of the hud
	 *
	 * @param dt Delta time (elapsed time since last game tick)
	 */
	public void update(float dt) {
		String empcoolDown2 = "" + ActiveGameScreen.player.disablingRayCooldown;
		empCooldown.setText("  " + empcoolDown2.substring(0,3));
		String shieldtext = "" + ActiveGameScreen.player.shieldCoolDown;
		shieldCooldown.setText("  " + shieldtext.substring(0,3));
		int ultimatetext = (int) ActiveGameScreen.player.ultimateAmount;

		if(ActiveGameScreen.player.ultimateAmount < 10){
			ultimateCooldown.setText(" 0" + ultimatetext + "%");
		}
		else{
			ultimateCooldown.setText(" " + ultimatetext + "%");
		}

		String bursttext = "" + ActiveGameScreen.player.burstCooldown;
		burstCooldown.setText("  " + bursttext.substring(0,3));



		if (respawnProtection >= 0) {
			respawnProtection -= dt;
		}

		if (Player.protectedTimer > 0 && Player.healBubble) {
			timeCount += dt * 3;
		}
		timeCount += dt;
		if (timeCount >= 1) {
			//Regen health every second
			if (health < maxHealth) {
				health += 1;
				healthLabel.setText(String.format("%02d", health));
			}
			//Gain point every second
			score += 1;
			scoreLabel.setText(String.format("%03d", score));
			timeCount = 0;

			//Check if a points boundary is met
		}


		if(ActiveGameScreen.player.rayEnabled && !empStackAdded){
			abilities3.add(empLogo).right();
			empStackAdded = true;
			empStackOnCd = true;
		}
		if(!ActiveGameScreen.player.rayEnabled && empStackAdded){
			abilities3.removeActor(empLogo);
			abilities3.removeActor(empStack);
			empStackAdded = false;
			empStackOnCd = false;
			empStackOnCd2 = false;
		}
		if(ActiveGameScreen.player.burstFire && !burstStackAdded){
			abilities1.add(burstLogo);
			burstStackAdded = true;
			burstStackOnCd = true;
		}
		if(ActiveGameScreen.player.ultimateFirerEnabled && !ultimateStackAdded){
			abilities2.add(ultimateLogo);
			ultimateStackAdded = true;
			ultimateStackOnCd = true;
		}
		if(ActiveGameScreen.player.shieldEnabled && !shieldStackAdded){
			abilities3.add(shieldLogo).left();
			shieldStackAdded = true;
			shieldStackOnCd = true;
		}

		if(ActiveGameScreen.player.rayEnabled && !empStackOnCd){
			if(ActiveGameScreen.player.disablingRayCooldown <= 0){
				abilities3.add(empLogo).right();
				abilities3.removeActor(empStack);
				empStackOnCd = true;
				empStackOnCd2 = false;
			}
		}
		if(ActiveGameScreen.player.burstFire && !burstStackOnCd){
			if(ActiveGameScreen.player.burstCooldown <= 0) {
				abilities1.add(burstLogo);
				abilities1.removeActor(burstStack);
				burstStackOnCd = true;
				burstStackOnCd2 = false;
			}
		}
		if(ActiveGameScreen.player.ultimateFirerEnabled && !ultimateStackOnCd){
			if(ActiveGameScreen.player.ultimateAmount >= 100){
				abilities2.add(ultimateLogo);
				abilities2.removeActor(ultimateStack);
				ultimateStackOnCd = true;
				ultimateStackOnCd2 = false;
			}
		}
		if(ActiveGameScreen.player.shieldEnabled && !shieldStackOnCd){
			if(ActiveGameScreen.player.shieldCoolDown <= 0){
				abilities3.add(shieldLogo).left();
				abilities3.removeActor(shieldStack);
				shieldStackOnCd = true;
				shieldStackOnCd2 = false;
			}
		}

		if(ActiveGameScreen.player.shieldEnabled && ActiveGameScreen.player.shieldCoolDown > 0 && !shieldStackOnCd2){
			abilities3.removeActor(shieldLogo);
			abilities3.add(shieldStack).left();
			shieldStackOnCd = false;
			shieldStackOnCd2 = true;
		}
		if(ActiveGameScreen.player.ultimateFirerEnabled && !ultimateStackOnCd2 && ActiveGameScreen.player.ultimateAmount < 100){
			abilities2.removeActor(ultimateLogo);
			abilities2.add(ultimateStack);
			ultimateStackOnCd = false;
			ultimateStackOnCd2 = true;
		}
		if(ActiveGameScreen.player.burstFire && !burstStackOnCd2 && ActiveGameScreen.player.burstCooldown > 0){
			abilities1.removeActor(burstLogo);
			abilities1.add(burstStack);
			burstStackOnCd = false;
			burstStackOnCd2 = true;
		}
		if(ActiveGameScreen.player.rayEnabled && !empStackOnCd2 && ActiveGameScreen.player.disablingRayCooldown >= 0){
			abilities3.removeActor(empLogo);
			abilities3.add(empStack).right();
			empStackOnCd = false;
			empStackOnCd2 = true;
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

