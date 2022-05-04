package com.mygdx.pirategame.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.mygdx.pirategame.PirateGame;
import com.mygdx.pirategame.display.HUD;
import com.mygdx.pirategame.entity.Player;

/**
 * The type for the skill tree screen.
 * It is a visual representation for the skills that the game automatically unlocks for the player.
 * Automatically unlocked when a points threshold is reached
 *
 * @author Faris Alblooki
 * @version 2.0
 */
public class ShopScreen implements Screen {

	//The skin for the actors
	private static Skin skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
	public static float damage1Price = 200f;
	private static Label damage1Label = new Label(damage1Price + " Gold", skin);
	public static float health1Price = 150f;
	private static Label health1Label = new Label(health1Price + " Gold", skin);
	public static float dps1Price = 200f;
	private static Label dps1Label = new Label(dps1Price + " Gold", skin);
	public static float range1Price = 200f;
	private static Label range1Label = new Label(range1Price + " Gold", skin);
	public static float GoldMulti1Price = 50f;
	private static Label GoldMulti1Label = new Label(GoldMulti1Price + " Gold", skin);
	public static float resistancePrice = 150f;
	private static Label resistanceLabel = new Label(resistancePrice + " Gold", skin);
	public static float bulletSpeedPrice = 200f;
	//Point unlock labels
	private static Label bulletSpeedLabel = new Label(bulletSpeedPrice + " Gold", skin);
	public static float movement1Price = 175f;
	private static Label movement1Label = new Label(movement1Price + " Gold", skin);
	private final Table Other = new Table();
	//To store whether buttons are enabled or disabled
	private final PirateGame parent;
	private final Stage stage;
	public float percentPerPurchase = 15f;
	private Label currentGold = new Label(" Gold: " + HUD.getCoins(), skin);
	private Table table = new Table();
	private Table titleTable = new Table();

	private TextButton bulletSpeedButton;
	private TextButton dpsButton;
	private TextButton rangeButton;
	private TextButton resistanceButton;
	private TextButton damageButton;
	private TextButton movementButton;
	private TextButton healthButton;
	private TextButton goldMultiplierButton;
	private TextButton backButton;



	public static int bulletSpeedCounter = 0;
	public static int dpsCounter = 0;
	public static int rangeCounter = 0;
	public static int resistanceCounter = 0;
	public static int damageCounter = 0;
	public static int movementCounter = 0;
	public static int healthCounter = 0;
	public static int goldMultiplierCounter = 0;




	/**
	 * Instantiates a new ShopScreen.
	 *
	 * @param pirateGame the main starting body of the game. Where screen swapping is carried out.
	 */
//In the constructor, the parent and stage are set. Also the states list is set
	public ShopScreen(PirateGame pirateGame, Stage stage) {
		parent = pirateGame;
		this.stage = stage;
	}

	/**
	 * Allows the game to check whether a points threshold has been reached
	 *
	 * @param points the current amount of points
	 */
	public static void pointsCheck(int points) {
		//code for skills here
	}

	public static void resetStats() {
		damage1Price = 200f;
		health1Price = 150f;
		dps1Price = 200f;
		range1Price = 200f;
		GoldMulti1Price = 50f;
		resistancePrice = 150f;
		bulletSpeedPrice = 200f;
		movement1Price = 175f;
		bulletSpeedLabel.setText(bulletSpeedPrice + " Gold");
		dps1Label.setText(dps1Price + " Gold");
		range1Label.setText(range1Price + " Gold");
		resistanceLabel.setText(resistancePrice + " Gold");
		damage1Label.setText(damage1Price + " Gold");
		movement1Label.setText(movement1Price + " Gold");
		health1Label.setText(health1Price + " Gold");
		GoldMulti1Label.setText(GoldMulti1Price + " Gold");
	}

	/**
	 * What should be displayed on the skill tree screen
	 */
	@Override
	public void show() {
		table.reset();
		table.clearChildren();
		table.clear();
		Other.clear();
		Other.clearChildren();
		Other.reset();
		titleTable.clear();
		titleTable.clearChildren();
		titleTable.reset();

		bulletSpeedLabel.setText(bulletSpeedPrice + " Gold");
		dps1Label.setText(dps1Price + " Gold");
		range1Label.setText(range1Price + " Gold");
		resistanceLabel.setText(resistancePrice + " Gold");
		damage1Label.setText(damage1Price + " Gold");
		movement1Label.setText(movement1Price + " Gold");
		health1Label.setText(health1Price + " Gold");
		GoldMulti1Label.setText(GoldMulti1Price + " Gold");

		Label.LabelStyle label1Style = new Label.LabelStyle();
		BitmapFont font = new BitmapFont(Gdx.files.internal("font.fnt"));
		label1Style.font = font;
		label1Style.font.setColor(PirateGame.selectedColour);
		Label Title = new Label("Shop", label1Style);
		titleTable.center().top();
		titleTable.setFillParent(true);
		titleTable.add(Title);
		stage.addActor(titleTable);
		Title.setColor(PirateGame.selectedColour);
		bulletSpeedLabel.setColor(PirateGame.selectedColour);
		dps1Label.setColor(PirateGame.selectedColour);
		range1Label.setColor(PirateGame.selectedColour);
		resistanceLabel.setColor(PirateGame.selectedColour);
		damage1Label.setColor(PirateGame.selectedColour);
		movement1Label.setColor(PirateGame.selectedColour);
		health1Label.setColor(PirateGame.selectedColour);
		GoldMulti1Label.setColor(PirateGame.selectedColour);
		currentGold.setColor(PirateGame.selectedColour);


		//Set the input processor
		Gdx.input.setInputProcessor(stage);
		// Create a table that fills the screen

		table.setFillParent(true);
		stage.addActor(table);


		// Table for the return button

		Other.setFillParent(true);
		stage.addActor(Other);



		this.bulletSpeedButton = new TextButton("Increase Bullet Speed", skin);
		this.bulletSpeedButton.getStyle().fontColor = PirateGame.selectedColour;
		this.bulletSpeedButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				if (HUD.purchase(bulletSpeedPrice)) {
					Player.upgradeCannonBallSpeed();
					bulletSpeedPrice = bulletSpeedPrice + (int) (bulletSpeedPrice * (percentPerPurchase / 100));
					bulletSpeedLabel.setText("");
					bulletSpeedLabel.setText(bulletSpeedPrice + " Gold");
					currentGold.setText(" Gold: " + HUD.getCoins());
					bulletSpeedCounter ++;
				}

			}
		});

		this.dpsButton = new TextButton("Increase Fire Rate", skin);
		this.dpsButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				if (HUD.purchase(dps1Price)) {
					Player.upgradeFireRate();
					dps1Price = dps1Price + (int) (dps1Price * (percentPerPurchase / 100));
					dps1Label.setText("");
					dps1Label.setText(dps1Price + " Gold");
					currentGold.setText(" Gold: " + HUD.getCoins());
					dpsCounter ++;
				}
			}
		});

		this.rangeButton = new TextButton("Increase Bullet Range", skin);

		this.rangeButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				if (HUD.purchase(range1Price)) {
					Player.upgradeRange(0.1f);
					range1Price = range1Price + (int) (range1Price * (percentPerPurchase / 100));
					range1Label.setText("");
					range1Label.setText(range1Price + " Gold");
					currentGold.setText(" Gold: " + HUD.getCoins());
					rangeCounter ++;
				}
			}
		});

		this.resistanceButton = new TextButton("Increase Resistance", skin);
		this.resistanceButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				if (HUD.purchase(resistancePrice)) {
					HUD.upgradResistance(10f);
					resistancePrice = resistancePrice + (int) (resistancePrice * (percentPerPurchase / 100));
					resistanceLabel.setText("");
					resistanceLabel.setText(resistancePrice + " Gold");
					currentGold.setText(" Gold: " + HUD.getCoins());
					resistanceCounter ++;
				}


			}
		});

		this.damageButton = new TextButton("Increase Damage", skin);
		this.damageButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				if (HUD.purchase(damage1Price)) {
					ActiveGameScreen.changeDamage(1);
					damage1Price = damage1Price + (int) (damage1Price * (percentPerPurchase / 100));
					damage1Label.setText("");
					damage1Label.setText(damage1Price + " Gold");
					currentGold.setText(" Gold: " + HUD.getCoins());
					damageCounter ++;
				}


			}
		});

		this.movementButton = new TextButton("Increase Movement Speed", skin);
		this.movementButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				if (HUD.purchase(movement1Price)) {
					ActiveGameScreen.changeAcceleration(10F);
					ActiveGameScreen.changeMaxSpeed(10F);
					movement1Price = movement1Price + (int) (movement1Price * (percentPerPurchase / 100));
					movement1Label.setText("");
					movement1Label.setText(movement1Price + " Gold");
					currentGold.setText(" Gold: " + HUD.getCoins());
					movementCounter++;
				}


			}
		});

		this.healthButton = new TextButton("Increase Health", skin);
		this.healthButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				if (HUD.purchase(health1Price)) {
					HUD.upgradMaxHealth(10f);
					health1Price = health1Price + (int) (health1Price * (percentPerPurchase / 100));
					health1Label.setText("");
					health1Label.setText(health1Price + " Gold");
					currentGold.setText(" Gold: " + HUD.getCoins());
					healthCounter++;
				}


			}
		});

		this.goldMultiplierButton = new TextButton("Increase Gold multiplier", skin);
		this.goldMultiplierButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				if (HUD.purchase(GoldMulti1Price)) {
					HUD.changeCoinsMulti(1.1f);
					GoldMulti1Price = GoldMulti1Price + (int) (GoldMulti1Price * (percentPerPurchase / 100));
					GoldMulti1Label.setText("");
					GoldMulti1Label.setText(GoldMulti1Price + " Gold");
					currentGold.setText(" Gold: " + HUD.getCoins());
					goldMultiplierCounter++;
				}
			}
		});


		//Return Button
		this.backButton = new TextButton("Return", skin);
		this.backButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				parent.changeScreen(PirateGame.GAME); //Return to game
			}
		});

		currentGold.setText(" Gold: " + HUD.getCoins());

		//add buttons and labels to main table
		table.add(this.bulletSpeedButton);
		table.add(bulletSpeedLabel);
		table.row().pad(10, 0, 10, 0);
		table.add(this.dpsButton);
		table.add(dps1Label);
		table.row().pad(10, 0, 10, 0);
		table.add(this.rangeButton);
		table.add(range1Label);
		table.row().pad(10, 0, 10, 0);
		table.add(this.resistanceButton);
		table.add(resistanceLabel);
		table.row().pad(10, 0, 10, 0);
		table.add(this.damageButton);
		table.add(damage1Label);
		table.row().pad(10, 0, 10, 0);
		table.add(this.movementButton);
		table.add(movement1Label);
		table.row().pad(10, 0, 10, 0);
		table.add(this.healthButton);
		table.add(health1Label);
		table.row().pad(10, 0, 10, 0);
		table.add(this.goldMultiplierButton);
		table.add(GoldMulti1Label);
		table.center();

		//add return button
		Other.add(this.backButton);
		Other.add(currentGold);
		Other.bottom().left();
	}

	/**
	 * Renders the visual data for all objects
	 *
	 * @param delta Delta Time
	 */

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0f, 0f, 0f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// tell our stage to do actions and draw itself
		stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
		stage.draw();
	}

	/**
	 * Changes the camera size, Scales the hud to match the camera
	 *
	 * @param width  the width of the viewable area
	 * @param height the height of the viewable area
	 */
	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height, true);
	}

	/**
	 * (Not Used)
	 * Pauses game
	 */
	@Override
	public void pause() {
	}

	/**
	 * (Not Used)
	 * Resumes game
	 */
	@Override
	public void resume() {
	}

	/**
	 * (Not Used)
	 * Hides game
	 */
	@Override
	public void hide() {
	}

	/**
	 * Disposes game data
	 */
	@Override
	public void dispose() {
		stage.dispose();
	}

	public TextButton getBulletSpeedButton() {
		return bulletSpeedButton;
	}

	public TextButton getDpsButton() {
		return dpsButton;
	}

	public TextButton getRangeButton() {
		return rangeButton;
	}

	public TextButton getResistanceButton() {
		return resistanceButton;
	}

	public TextButton getDamageButton() {
		return damageButton;
	}

	public TextButton getMovementButton() {
		return movementButton;
	}

	public TextButton getHealthButton() {
		return healthButton;
	}

	public TextButton getGoldMultiplierButton() {
		return goldMultiplierButton;
	}

	public TextButton getBackButton() {
		return backButton;
	}
}




