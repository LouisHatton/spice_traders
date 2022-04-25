package com.mygdx.pirategame.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.pirategame.PirateGame;

import static com.mygdx.pirategame.screen.MainMenuScreen.renderBackground;


public class DifficultyScreen implements Screen {


    private final Screen parent;
    private final com.mygdx.pirategame.PirateGame pirateGame;
    private final Stage stage;
    String textForDiff = " ";

    boolean difficultySet = false;

    public DifficultyScreen(PirateGame pirateGame, Screen parent) {
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

        if(PirateGame.difficultySet){
            if(pirateGame.getDifficulty() == 0.5f) textForDiff = "Easy";
            if(pirateGame.getDifficulty() == 1) textForDiff = "Normal";
            if(pirateGame.getDifficulty() == 1.5) textForDiff = "Hard";
            if(pirateGame.getDifficulty() == 2) textForDiff = "Impossible";
            Label difficultyMsg = new Label("Current Difficulty is " + textForDiff, new Label.LabelStyle(new BitmapFont(), Color.WHITE));
            difficultyMsg.setFontScale(1.3f);
            table.add(difficultyMsg).center();
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
                PirateGame.difficultyMultiplier = 0.5f;
                PirateGame.difficultySet = true;
                pirateGame.changeScreen(PirateGame.GAME);
            }
        });
        //Help Screen
        Normal.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                PirateGame.difficultyMultiplier = 1f;
                PirateGame.difficultySet = true;
                pirateGame.changeScreen(PirateGame.GAME);
            }
        });
        //Help Screen
        Hard.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                PirateGame.difficultyMultiplier = 1.5f;
                PirateGame.difficultySet = true;
                pirateGame.changeScreen(PirateGame.GAME);
            }
        });

        Impossible.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                PirateGame.difficultyMultiplier = 2f;
                PirateGame.difficultySet = true;
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
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        if(!PirateGame.difficultySet) {
            Gdx.gl.glClearColor(46 / 255f, 204 / 255f, 113 / 255f, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            renderBackground();
        }
        else{
            Gdx.gl.glClearColor(0f, 0f, 0f, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        }

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
