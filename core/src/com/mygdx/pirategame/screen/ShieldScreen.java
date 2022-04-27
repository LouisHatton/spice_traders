package com.mygdx.pirategame.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.pirategame.PirateGame;

public class ShieldScreen implements Screen {


    private float boatsKilled = 0;
    private int boatsKilledObjective = 1;
    private float percentage = 0;
    private final PirateGame parent;
    private final Stage stage;

    private TextButton returnButton;
    private TextButton lvl1Button;
    private TextButton lvl2Button;
    private TextButton lvl3Button;
    private TextButton lvl4Button;
    private TextButton lvl5Button;
    private TextButton lvl6Button;

    private Label lvl1ButtonLabel;
    private Label lvl2ButtonLabel;
    private Label lvl1ButtonLabel2;
    private Label lvl2ButtonLabel2;
    private Label lvl3ButtonLabel;
    private Label lvl4ButtonLabel;
    private Label lvl3ButtonLabel2;
    private Label lvl4ButtonLabel2;
    private Label lvl5ButtonLabel;
    private Label lvl6ButtonLabel;
    private Label lvl5ButtonLabel2;
    private Label lvl6ButtonLabel2;
    private Label Title;
    private Label progressBarLabel;
    private Label percentageLabel;

    Table table = new Table();
    Table titleTable = new Table();
    final Table Other = new Table();

    Image progressBar;

    public ShieldScreen(PirateGame pirateGame) {
        parent = pirateGame;
        stage = new Stage(new ScreenViewport());
    }


    @Override
    public void show() {
        table.reset();
        table.clearChildren();
        table.clear();
        titleTable.reset();
        titleTable.clearChildren();
        titleTable.clear();
        Other.clear();
        Other.clearChildren();
        Other.reset();
        table.setFillParent(true);
        titleTable.setFillParent(true);
        Other.setFillParent(true);
        stage.addActor(titleTable);
        stage.addActor(table);
        stage.addActor(Other);


        String completion = "";


        progressBar =  new Image(new Sprite(new Texture("blank.png")));


        boatsKilled = ActiveGameScreen.player.getBoatsKilled();




        Gdx.input.setInputProcessor(stage);

        //The skin for the actors
        Skin skin = new Skin(Gdx.files.internal("skin/uiskin.json"));

        //Return Button
        this.returnButton = new TextButton("Return", skin);

        this.returnButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                parent.changeScreen(PirateGame.SKILL); //Return to game
            }
        });



        lvl1Button = new TextButton("Level 1 - Base ability", skin);
        lvl2Button = new TextButton("Level 2", skin);
        lvl3Button = new TextButton("Level 3", skin);
        lvl4Button = new TextButton("Level 4", skin);
        lvl5Button = new TextButton("Level 5", skin);
        lvl6Button = new TextButton("Level 6 - Max Level", skin);

        lvl2Button.setDisabled(true);
        lvl3Button.setDisabled(true);
        lvl4Button.setDisabled(true);
        lvl5Button.setDisabled(true);
        lvl6Button.setDisabled(true);

        lvl1ButtonLabel = new Label("On button press E creates a bubble that protects the player from damage lasts for 2 seconds (8 second cool down)", skin);
        lvl2ButtonLabel = new Label("Extend shield duration to 3 seconds", skin);
        lvl1ButtonLabel2 = new Label("Destroy 5 ships", skin);
        lvl2ButtonLabel2 = new Label("Destroy 7 ships", skin);
        lvl3ButtonLabel = new Label("When shield is used instantly gain 10% of the player back", skin);
        lvl4ButtonLabel = new Label("Extend shield duration to 4 seconds", skin);
        lvl3ButtonLabel2 = new Label("Destroy 9 ships", skin);
        lvl4ButtonLabel2 = new Label("Destroy 11 ships", skin);
        lvl5ButtonLabel = new Label("Decrease cool down from 8 seconds to 7", skin);
        lvl6ButtonLabel = new Label("When bubble is active healing is increased by 300%", skin);
        lvl5ButtonLabel2 = new Label("Destroy 13 ships", skin);
        lvl6ButtonLabel2 = new Label("Destroy 15 ships", skin);
        Title = new Label("Bubble", skin);





        boatsKilledObjective = 7;
        if(boatsKilled >= 7){
            lvl2Button.setDisabled(false);
            boatsKilledObjective = 9;
        }
        if(boatsKilled >= 9){
            lvl3Button.setDisabled(false);
            boatsKilledObjective = 11;
        }
        if(boatsKilled >= 11){
            lvl4Button.setDisabled(false);
            boatsKilledObjective = 13;
        }
        if(boatsKilled >= 13){
            lvl5Button.setDisabled(false);
            boatsKilledObjective = 15;
        }
        if(boatsKilled >= 15){
            lvl6Button.setDisabled(false);
        }

        if(boatsKilledObjective <= boatsKilled){
            percentage = 100;
            completion = "- Completed!";
        }
        else{
            percentage = ((boatsKilled / boatsKilledObjective) );
            percentage = percentage * 100;
        }


        int boatsKilled1 = (int) boatsKilled;
        int boatsKilledObjective1 = (int) boatsKilledObjective;
        int percentagePercever = (int) percentage;

        progressBarLabel = new Label(" Progress : Ships Destroyed: " + boatsKilled1 + " / " + boatsKilledObjective1 , skin);
        percentageLabel = new Label( "         " + percentagePercever + " % " + completion, skin);

        progressBar.scaleBy(500 * (percentage/100),22);

        titleTable.center().top();
        Title.setFontScale(3);
        titleTable.add(Title);


        table.center();
        table.add(lvl1ButtonLabel2).padLeft(10);
        table.add(lvl1Button).padLeft(10);
        table.add(lvl1ButtonLabel);
        table.row().pad(20, 0, 20, 0);
        table.add(lvl2ButtonLabel2).padLeft(10);
        table.add(lvl2Button).padLeft(10);
        table.add(lvl2ButtonLabel);
        table.row().pad(20, 0, 20, 0);
        table.add(lvl3ButtonLabel2).padLeft(10);
        table.add(lvl3Button).padLeft(10);
        table.add(lvl3ButtonLabel);
        table.row().pad(20, 0, 20, 0);
        table.add(lvl4ButtonLabel2).padLeft(10);
        table.add(lvl4Button).padLeft(10);
        table.add(lvl4ButtonLabel);
        table.row().pad(20, 0, 20, 0);
        table.add(lvl5ButtonLabel2).padLeft(10);
        table.add(lvl5Button).padLeft(10);
        table.add(lvl5ButtonLabel);
        table.row().pad(20, 0, 20, 0);
        table.add(lvl6ButtonLabel2).padLeft(10);
        table.add(lvl6Button).padLeft(10);
        table.add(lvl6ButtonLabel);
        table.row().pad(20, 0, 20, 0);
        table.add(progressBarLabel);
        table.add(progressBar).left().pad(20, 8, 0 ,20);
        table.add(percentageLabel).padLeft(10).padRight(10);



        //add return button
        Other.add(this.returnButton);
        Other.bottom().left();
    }



    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // tell our stage to do actions and draw itself
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();


    }


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

    public TextButton getReturnButton() {
        return returnButton;
    }
}

