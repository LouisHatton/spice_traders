package com.mygdx.pirategame.entity.tile;

import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.pirategame.PirateGame;
import com.mygdx.pirategame.entity.Entity;
import com.mygdx.pirategame.screen.ActiveGameScreen;

/**
 * Interactive Tile Object
 * Generates the world with interactive tiles
 *
 * @author Faris Alblooki
 * @version 2.0
 */
public abstract class InteractiveTileObject extends Entity {

	protected TiledMapTile tile;
	protected Rectangle bounds;
	protected Fixture fixture;

	/**
	 * Instantiates world data
	 *
	 * @param screen Visual data
	 * @param bounds Rectangle boundary (world boundary)
	 */
	public InteractiveTileObject(ActiveGameScreen screen, Rectangle bounds) {
		super(screen, bounds.getX(), bounds.getY());
		this.bounds = bounds;

		//Create objects used for dimensions
		BodyDef bDef = new BodyDef();
		FixtureDef fDef = new FixtureDef();
		PolygonShape shape = new PolygonShape();

		//Set position
		bDef.type = BodyDef.BodyType.StaticBody;
		bDef.position.set((bounds.getX() + bounds.getWidth() / 2) / PirateGame.PPM, (bounds.getY() + bounds.getHeight() / 2) / PirateGame.PPM);

		setBody(getWorld().createBody(bDef));

		shape.setAsBox(bounds.getWidth() / 2 / PirateGame.PPM, bounds.getHeight() / 2 / PirateGame.PPM);
		fDef.shape = shape;
		fDef.restitution = 0.7f;
		fixture = getBody().createFixture(fDef);
		defineEntity();
	}

	/**
	 * Set filter
	 */
	public void setCategoryFilter(short filterBit) {
		Filter filter = new Filter();
		filter.categoryBits = filterBit;
		fixture.setFilterData(filter);
	}
}
