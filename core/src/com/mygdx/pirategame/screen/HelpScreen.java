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
 * @author Faris Alblooki
 * @version 2.0
 */
public class HelpScreen implements Screen {
	private final PirateGame parent;
	private final Stage stage;
	private Table titleTable = new Table();
	Label tiplabel;
	Label tip2label;
	Table tip = new Table();

	private TextButton backButton;

	/**
	 * In the constructor, the parent and stage are set. Also the states list is set
	 *
	 * @param pirateGame Game data
	 */
	public HelpScreen(PirateGame pirateGame, Stage stage) {
		parent = pirateGame;
		this.stage = stage;
	}

	/**
	 * Displays help data
	 */
	@Override
	public void show() {
		titleTable.clear();
		titleTable.clearChildren();
		titleTable.reset();
		tip.clear();
		tip.clearChildren();
		tip.reset();
		Label.LabelStyle label1Style = new Label.LabelStyle();
		BitmapFont font = new BitmapFont(Gdx.files.internal("font.fnt"));
		label1Style.font = font;

		Label Title = new Label("Help", label1Style);
		titleTable.center().top();
		titleTable.setFillParent(true);
		titleTable.add(Title);
		stage.addActor(titleTable);

		tip.center().bottom();
		tip.setFillParent(true);
		stage.addActor(tip);
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
		Label Controls1 = new Label("W accelerates, S reverse / decelerate , D steer right, A steer left and Space-bar to drop the anchor (Break)", new Label.LabelStyle(new BitmapFont(Gdx.files.internal("textFont.fnt")), Color.WHITE));
		Label Controls2 = new Label("Left click to fire (fires at the direction of the mouse click)", new Label.LabelStyle(new BitmapFont(Gdx.files.internal("textFont.fnt")), Color.WHITE));
		Label Controls3 = new Label("ESCAPE to see menu", new Label.LabelStyle(new BitmapFont(Gdx.files.internal("textFont.fnt")), Color.WHITE));
		Label Controls4 = new Label("The keybindings for the abilities will be shown on the ability icon (or the skill screen)", new Label.LabelStyle(new BitmapFont(Gdx.files.internal("textFont.fnt")), Color.WHITE));
		Label Controls5 = new Label(" (To get to skill screen load up a game then pause and click skill tree)", new Label.LabelStyle(new BitmapFont(Gdx.files.internal("textFont.fnt")), Color.WHITE));
		Label objective1 = new Label("The objective is to capture or destroy all other colleges. Take a college down to 1 hp for them to surrender! (Be captured)", new Label.LabelStyle(new BitmapFont(Gdx.files.internal("textFont.fnt")), Color.WHITE));
		Label objective2 = new Label("Destroy the college by shooting a college that has surrendered", new Label.LabelStyle(new BitmapFont(Gdx.files.internal("textFont.fnt")), Color.WHITE));
		Label objective3 = new Label("Collect coins on the way and spend them in the shop to get upgrades", new Label.LabelStyle(new BitmapFont(Gdx.files.internal("textFont.fnt")), Color.WHITE));
		Label objective4 = new Label("Do not destroy your own college as that will result in a defeat (Alcuin)(The college you start next to)", new Label.LabelStyle(new BitmapFont(Gdx.files.internal("textFont.fnt")), Color.WHITE));
		Label skillInfo1 = new Label("Automatically unlock abilities as you complete objectives", new Label.LabelStyle(new BitmapFont(Gdx.files.internal("textFont.fnt")), Color.WHITE));
		Label skillInfo2 = new Label("To get more information about your abilities and objectives (Progress) look at the skills tab", new Label.LabelStyle(new BitmapFont(Gdx.files.internal("textFont.fnt")), Color.WHITE));
		Label Strategy1 = new Label("Strategy to kill boats : shooting the direction you are moving will give the player less reach.", new Label.LabelStyle(new BitmapFont(Gdx.files.internal("textFont.fnt")), Color.WHITE));
		Label Strategy1Con = new Label("so try shooting the other way to get better reach on the enemy", new Label.LabelStyle(new BitmapFont(Gdx.files.internal("textFont.fnt")), Color.WHITE));
		Label Strategy2 = new Label("Strategy to destroy colleges : ensure that the colleges fleet has been destroyed and circle the college while shooting it", new Label.LabelStyle(new BitmapFont(Gdx.files.internal("textFont.fnt")), Color.WHITE));
		Label Strategy2Con = new Label("that way you can destroy the college without disruptions. Additionally, destroying a college will disable its fleet", new Label.LabelStyle(new BitmapFont(Gdx.files.internal("textFont.fnt")), Color.WHITE));
		Controls1.setColor(PirateGame.selectedColour);
		Controls2.setColor(PirateGame.selectedColour);
		Controls3.setColor(PirateGame.selectedColour);
		Controls4.setColor(PirateGame.selectedColour);
		Controls5.setColor(PirateGame.selectedColour);
		objective1.setColor(PirateGame.selectedColour);
		objective2.setColor(PirateGame.selectedColour);
		objective3.setColor(PirateGame.selectedColour);
		objective4.setColor(PirateGame.selectedColour);
		skillInfo1.setColor(PirateGame.selectedColour);
		skillInfo2.setColor(PirateGame.selectedColour);
		Strategy1.setColor(PirateGame.selectedColour);
		Strategy2.setColor(PirateGame.selectedColour);
		Strategy2Con.setColor(PirateGame.selectedColour);
		Strategy1Con.setColor(PirateGame.selectedColour);
		//Return Button
		this.backButton = new TextButton("Return", skin);
		backButton.addListener(new ChangeListener() {

			@Override
			public void changed(ChangeEvent event, Actor actor) {
				parent.changeScreen(PirateGame.MENU);
			}
		});
		Title.setColor(PirateGame.selectedColour);
		backButton.getStyle().fontColor = PirateGame.selectedColour;

		table.add(backButton);
		table.row().pad(10, 0, 10, 0);
		table.left().top();

		tiplabel = new Label("Tip: Getting a pacifist victory (not destroying colleges but capturing them) will help the player avoid the bad weather.", new Label.LabelStyle(new BitmapFont(Gdx.files.internal("textFont.fnt")), Color.WHITE));
		tip2label = new Label("which activates from destroying two colleges.", new Label.LabelStyle(new BitmapFont(Gdx.files.internal("textFont.fnt")), Color.WHITE));
		tiplabel.setColor(PirateGame.selectedColour);
		tip.add(tiplabel);
		tip.row();
		tip.add(tip2label);

		//add return button
		Other.add(Controls1);
		Other.row();
		Other.add(Controls2);
		Other.row();
		Other.add(Controls3);
		Other.row();
		Other.add(Controls4);
		Other.row();
		Other.add(Controls5).padBottom((40));
		Other.row();
		Other.add(objective1);
		Other.row();
		Other.add(objective2);
		Other.row();
		Other.add(objective3);
		Other.row();
		Other.add(objective4).padBottom((40));
		Other.row();
		Other.add(skillInfo1);
		Other.center();
		Other.row();
		Other.add(skillInfo2).padBottom((80));
		Other.center();
		Other.row();
		Other.add(Strategy1);
		Other.row();
		Other.add(Strategy1Con).padBottom((20));
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

	public TextButton getBackButton() {
		return backButton;
	}
}




