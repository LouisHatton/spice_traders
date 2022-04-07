package com.mygdx.pirategame.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.pirategame.PirateGame;

public class DifficulityScreen implements Screen {
    private final Screen parent;
    private final com.mygdx.pirategame.PirateGame pirateGame;
    private final Stage stage;

    String textForDiff = " ";

    public DifficulityScreen(PirateGame pirateGame, Screen parent) {
        this.pirateGame = pirateGame;
        this.parent = parent;
        stage = new Stage(new ScreenViewport());
    }


    @Override
    public void show() {
        //Set the input processor
        Gdx.input.setInputProcessor(stage);
        // Create a table for the buttons
        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        //The skin for the actors
        Skin skin = new Skin(Gdx.files.internal("skin/uiskin.json"));

        if(pirateGame.getDifficulity() != 0){
            if(pirateGame.getDifficulity() == 0.5f) textForDiff = "Easy";
            if(pirateGame.getDifficulity() == 1) textForDiff = "Normal";
            if(pirateGame.getDifficulity() == 1.5) textForDiff = "Hard";
            if(pirateGame.getDifficulity() == 2) textForDiff = "Impossible";
            Label difficulityMsg = new Label("Current Difficulity is " + textForDiff, new Label.LabelStyle(new BitmapFont(), Color.WHITE));
            difficulityMsg.setFontScale(1.3f);
            table.add(difficulityMsg).center();
            stage.addActor(table);
            table.row();
            table.row();
        }

        //create buttons
        TextButton Easy = new TextButton("Easy", skin);
        TextButton Normal = new TextButton("Normal", skin);
        TextButton Hard = new TextButton("Hard", skin);
        TextButton Impossible = new TextButton("Impossible", skin);
        TextButton Back = new TextButton("Back", skin);

        //add buttons to table
        table.add(Easy).fillX().uniformX();
        table.row().pad(10, 0, 10, 0);
        table.add(Normal).fillX().uniformX();
        table.row();
        table.add(Hard).fillX().uniformX();
        table.row();
        table.add(Impossible).fillX().uniformX();
        table.row();
        table.add(Back).fillX().uniformX();

        //add listeners to the buttons

        //Start a game
        Easy.addListener(new ChangeListener() {

            @Override
            public void changed(ChangeEvent event, Actor actor) {
                PirateGame.difficulityMultiplier = 0.5f;
                pirateGame.changeScreen(PirateGame.GAME);
            }
        });
        //Help Screen
        Normal.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                PirateGame.difficulityMultiplier = 1f;
                pirateGame.changeScreen(PirateGame.GAME);
            }
        });
        //Help Screen
        Hard.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                PirateGame.difficulityMultiplier = 1.5f;
                pirateGame.changeScreen(PirateGame.GAME);
            }
        });

        Impossible.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                PirateGame.difficulityMultiplier = 2f;
                pirateGame.changeScreen(PirateGame.GAME);
            }
        });



        Back.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                pirateGame.setScreen(parent);
            }
        });
    }


    @Override
    public void render(float dt) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }


    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }


    @Override
    public void pause() {
    }


    @Override
    public void resume() {
    }


    @Override
    public void hide() {
    }


    @Override
    public void dispose() {
        stage.dispose();
    }
}
