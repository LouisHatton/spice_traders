package com.mygdx.pirategame.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.pirategame.PirateGame;
import com.mygdx.pirategame.display.HUD;

import java.util.Arrays;
import java.util.List;

/**
 * The type for the skill tree screen.
 * It is a visual representation for the skills that the game automatically unlocks for the player.
 * Automatically unlocked when a points threshold is reached
 *
 * @author Sam Pearson
 * @version 1.0
 */
public class Shop implements Screen {

    //To store whether buttons are enabled or disabled
    private static TextButton movement1;
    private final PirateGame parent;
    private final Stage stage;
    private TextButton damage1;
    private TextButton health1;
    private TextButton dps1;
    private TextButton range1;
    private TextButton GoldMulti1;
    private TextButton resistance;
    private TextButton bulletSpeed;

    private float damage1Price = 200f;
    private float health1Price = 150f;
    private float dps1Price = 200f;
    private float range1Price = 200f;
    private float GoldMulti1Price = 50f;
    private float resistancePrice = 150f;
    private float bulletSpeedPrice = 200f;
    private float movement1Price = 100f;

    public float percentPerPurchase = 15f;



    //The skin for the actors
    Skin skin = new Skin(Gdx.files.internal("skin/uiskin.json"));


    //Point unlock labels
    Label bulletSpeedLabel = new Label(bulletSpeedPrice + " Gold", skin);
    Label dps1Label = new Label(dps1Price + " Gold", skin);
    Label range1Label = new Label(range1Price + " Gold", skin);
    Label resistanceLabel = new Label(resistancePrice + " Gold", skin);
    Label damage1Label = new Label(damage1Price + " Gold", skin);
    Label movement1Label = new Label(movement1Price + " Gold", skin);
    Label health1Label = new Label(health1Price + " Gold", skin);
    Label GoldMulti1Label = new Label(GoldMulti1Price + " Gold", skin);
    final Table Other = new Table();
    Table table = new Table();
    /**
     * Instantiates a new Skill tree.
     *
     * @param pirateGame the main starting body of the game. Where screen swapping is carried out.
     */
//In the constructor, the parent and stage are set. Also the states list is set
    public Shop(PirateGame pirateGame) {
        parent = pirateGame;
        stage = new Stage(new ScreenViewport());
    }

    /**
     * Allows the game to check whether a points threshold has been reached
     *
     * @param points the current amount of points
     */
    public static void pointsCheck(int points) {
        //code for skills here
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


        //Set the input processor
        Gdx.input.setInputProcessor(stage);
        // Create a table that fills the screen

        table.setFillParent(true);
        stage.addActor(table);


        // Table for the return button

        Other.setFillParent(true);
        stage.addActor(Other);




        bulletSpeed = new TextButton("Increase Bullet Speed", skin);
        bulletSpeed.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(HUD.purchase(bulletSpeedPrice)){
                    bulletSpeedPrice = bulletSpeedPrice + (int)(bulletSpeedPrice * (percentPerPurchase/100));
                    bulletSpeedLabel.setText("");
                    bulletSpeedLabel.setText(bulletSpeedPrice + " Gold");
                }

            }
        });
        dps1 = new TextButton("Increase Fire Rate", skin);
        dps1.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(HUD.purchase(dps1Price)){
                    dps1Price = dps1Price + (int)(dps1Price * (percentPerPurchase/100));
                    dps1Label.setText("");
                    dps1Label.setText(dps1Price + " Gold");
                }


            }
        });
        range1 = new TextButton("Increase Bullet Range", skin);

        range1.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(HUD.purchase(range1Price)){
                    HUD.changeCoinsMulti(1.1f);
                    range1Price = range1Price + (int)(range1Price * (percentPerPurchase/100));
                    range1Label.setText("");
                    range1Label.setText(range1Price + " Gold");
                }


            }
        });
        resistance = new TextButton("Increase Resistance", skin);

        resistance.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(HUD.purchase(resistancePrice)){
                    resistancePrice = resistancePrice + (int)(resistancePrice * (percentPerPurchase/100));
                    resistanceLabel.setText("");
                    resistanceLabel.setText(resistancePrice + " Gold");
                }


            }
        });
        damage1 = new TextButton("Increase Damage", skin);

        damage1.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(HUD.purchase(damage1Price)){
                    damage1Price = damage1Price + (int)(damage1Price * (percentPerPurchase/100));
                    damage1Label.setText("");
                    damage1Label.setText(damage1Price + " Gold");
                }


            }
        });
        movement1 = new TextButton("Increase Movement Speed", skin);
        movement1.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(HUD.purchase(movement1Price)){
                    movement1Price = movement1Price + (int)(movement1Price * (percentPerPurchase/100));
                    movement1Label.setText("");
                    movement1Label.setText(movement1Label + " Gold");
                }


            }
        });
        health1 = new TextButton("Increase Health", skin);

        health1.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(HUD.purchase(health1Price)){
                    health1Price = health1Price + (int)(health1Price * (percentPerPurchase/100));
                    health1Label.setText("");
                    health1Label.setText(health1Price + " Gold");
                }


            }
        });
        GoldMulti1 = new TextButton("Increase Gold multiplier", skin);
        GoldMulti1.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(HUD.purchase(GoldMulti1Price)){
                    HUD.changeCoinsMulti(1.1f);
                    GoldMulti1Price = GoldMulti1Price + (int)(GoldMulti1Price * (percentPerPurchase/100));
                    GoldMulti1Label.setText("");
                    GoldMulti1Label.setText(GoldMulti1Price + " Gold");
                }


            }
        });




        //Return Button
        TextButton backButton = new TextButton("Return", skin);

        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

                parent.changeScreen(PirateGame.GAME); //Return to game
            }
        });


        //add buttons and labels to main table
        table.add(bulletSpeed);
        table.add(bulletSpeedLabel);
        table.row().pad(10, 0, 10, 0);
        table.add(dps1);
        table.add(dps1Label);
        table.row().pad(10, 0, 10, 0);
        table.add(range1);
        table.add(range1Label);
        table.row().pad(10, 0, 10, 0);
        table.add(resistance);
        table.add(resistanceLabel);
        table.row().pad(10, 0, 10, 0);
        table.add(damage1);
        table.add(damage1Label);
        table.row().pad(10, 0, 10, 0);
        table.add(movement1);
        table.add(movement1Label);
        table.row().pad(10, 0, 10, 0);
        table.add(health1);
        table.add(health1Label);
        table.row().pad(10, 0, 10, 0);
        table.add(GoldMulti1);
        table.add(GoldMulti1Label);
        table.top();

        //add return button
        Other.add(backButton);
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
}




