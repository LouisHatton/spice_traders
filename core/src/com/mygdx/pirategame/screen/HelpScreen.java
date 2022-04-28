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

/**
 * Screen with instructions for the user
 *
 * @author Sam Pearson
 * @version 1.0
 */
public class HelpScreen implements Screen {
	private final PirateGame parent;
	private final Stage stage;
	private Table titleTable = new Table();

	/**
	 * In the constructor, the parent and stage are set. Also the states list is set
	 *
	 * @param pirateGame Game data
	 */
	public HelpScreen(PirateGame pirateGame) {
		parent = pirateGame;
		stage = new Stage(new ScreenViewport());
	}

	/**
	 * Displays help data
	 */
	@Override
	public void show() {
		titleTable.clear();
		titleTable.clearChildren();
		titleTable.reset();
		Label.LabelStyle label1Style = new Label.LabelStyle();
		BitmapFont font = new BitmapFont(Gdx.files.internal("font.fnt"));
		label1Style.font = font;

		Label Title = new Label("Help", label1Style);
		titleTable.center().top();
		titleTable.setFillParent(true);
		titleTable.add(Title);
		stage.addActor(titleTable);
		//Set the input processor
		Gdx.input.setInputProcessor(stage);
		// Create a table that fills the screen
		Table table = new Table();
		table.setFillParent(true);
		stage.addActor(table);

		// Table for the return button
		final Table Other = new Table();
		Other.setFillParent(true);
		stage.addActor(Other);

		//The skin for the actors
		Skin skin = new Skin(Gdx.files.internal("skin/uiskin.json"));

		//Text
		Label Controls1 = new Label("WASD to move", new Label.LabelStyle(new BitmapFont(Gdx.files.internal("textFont.fnt")), Color.WHITE));
		Label Controls2 = new Label("Left click to fire (fires at the direction of the mouse click)", new Label.LabelStyle(new BitmapFont(Gdx.files.internal("textFont.fnt")), Color.WHITE));
		Label Controls3 = new Label("ESCAPE to see menu", new Label.LabelStyle(new BitmapFont(Gdx.files.internal("textFont.fnt")), Color.WHITE));
		Label objective1 = new Label("The objective is to take over or destroy all other colleges", new Label.LabelStyle(new BitmapFont(Gdx.files.internal("textFont.fnt")), Color.WHITE));
		Label objective2 = new Label("Destroy the college flag with cannons", new Label.LabelStyle(new BitmapFont(Gdx.files.internal("textFont.fnt")), Color.WHITE));
		Label objective3 = new Label("Collect coins on the way and spend them in the shop to get upgrades", new Label.LabelStyle(new BitmapFont(Gdx.files.internal("textFont.fnt")), Color.WHITE));
		Label skillInfo1 = new Label("Automatically unlock abilities as you complete objectives", new Label.LabelStyle(new BitmapFont(Gdx.files.internal("textFont.fnt")), Color.WHITE));
		Label skillInfo2 = new Label("See your abilities and objectives look at the skills tab", new Label.LabelStyle(new BitmapFont(Gdx.files.internal("textFont.fnt")), Color.WHITE));
		Label Strategy1 = new Label("Strategy to kill boats : shooting the direction you are moving will give the player less reach. so try shooting the other way to get better reach on the enemy", new Label.LabelStyle(new BitmapFont(Gdx.files.internal("textFont.fnt")), Color.WHITE));
		Label Strategy2 = new Label("Strategy to destroy colleges : ensure that the colleges fleet has been destroyed and circle the college while shooting it", new Label.LabelStyle(new BitmapFont(Gdx.files.internal("textFont.fnt")), Color.WHITE));
		Label Strategy2Con = new Label("that way you can destroy the college without disruptions", new Label.LabelStyle(new BitmapFont(Gdx.files.internal("textFont.fnt")), Color.WHITE));

		//Return Button
		TextButton backButton = new TextButton("Return", skin);
		backButton.addListener(new ChangeListener() {

			@Override
			public void changed(ChangeEvent event, Actor actor) {
				parent.changeScreen(PirateGame.MENU);
			}
		});

		table.add(backButton);
		table.row().pad(10, 0, 10, 0);
		table.left().top();

		//add return button
		Other.add(Controls1);
		Other.row();
		Other.add(Controls2);
		Other.row();
		Other.add(Controls3).padBottom((40));
		Other.row();
		Other.add(objective1);
		Other.row();
		Other.add(objective2);
		Other.row();
		Other.add(objective3).padBottom((40));
		Other.row();
		Other.add(skillInfo1);
		Other.center();
		Other.row();
		Other.add(skillInfo2).padBottom((80));
		Other.center();
		Other.row();
		Other.add(Strategy1).padBottom((20));
		Other.row();
		Other.add(Strategy2);
		Other.row();
		Other.add(Strategy2Con);
		Other.center();
	}

	/**
	 * Renders visual data with delta time
	 *
	 * @param dt Delta time (elapsed time since last game tick)
	 */
	@Override
	public void render(float dt) {
		Gdx.gl.glClearColor(0f, 0f, 0f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// tell our stage to do actions and draw itself
		stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
		stage.draw();
		// TODO Auto-generated method stub
	}

	/**
	 * Changes the camera size
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




