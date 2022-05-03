package com.mygdx.pirategame.tests.impl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.mygdx.pirategame.PirateGame;
import com.mygdx.pirategame.display.HUD;
import com.mygdx.pirategame.entity.Player;
import com.mygdx.pirategame.entity.cannon.CannonFire;
import com.mygdx.pirategame.entity.coin.Coin;
import com.mygdx.pirategame.entity.college.version.CollegeFire;
import com.mygdx.pirategame.entity.ship.EnemyShip;
import com.mygdx.pirategame.entity.tile.type.CollegeWalls;
import com.mygdx.pirategame.listener.WorldContactListener;
import com.mygdx.pirategame.screen.ActiveGameScreen;
import com.mygdx.pirategame.tests.utils.MockUtilities;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.*;

public class ListenerTest {

	private final WorldContactListener worldContactListener = new WorldContactListener();

	@BeforeClass
	public static void mockGraphics() {
		Gdx.gl20 = Mockito.mock(GL20.class);
		Gdx.gl = Gdx.gl20;

		MockUtilities.createDefaultScoreAndPoints();
	}

	@Test
	public void testPlayerAndCoin() {
		PirateGame pirateGame = MockUtilities.createGameAndScreen();
		ActiveGameScreen activeGameScreen = (ActiveGameScreen) pirateGame.getScreen();

		Fixture fixtureA = mockFixture(PirateGame.COIN_BIT, new Coin(activeGameScreen, 0, 0));
		Fixture fixtureB = mockFixture(PirateGame.PLAYER_BIT, MockUtilities.mockPlayer(activeGameScreen));

		worldContactListener.selectAppropriateResponse(fixtureA, fixtureB, PirateGame.COIN_BIT | PirateGame.PLAYER_BIT);

		assertEquals(HUD.getCoins(), 1);
	}

	@Test
	public void testPlayerAndCoinReversed() {
		PirateGame pirateGame = MockUtilities.createGameAndScreen();
		ActiveGameScreen activeGameScreen = (ActiveGameScreen) pirateGame.getScreen();

		Fixture fixtureA = mockFixture(PirateGame.COIN_BIT, new Coin(activeGameScreen, 0, 0));
		Fixture fixtureB = mockFixture(PirateGame.PLAYER_BIT, MockUtilities.mockPlayer(activeGameScreen));

		worldContactListener.selectAppropriateResponse(fixtureB, fixtureA, PirateGame.COIN_BIT | PirateGame.PLAYER_BIT);

		assertEquals(HUD.getCoins(), 1);
	}

	@Test
	public void testWallAndPlayer() {
		PirateGame pirateGame = MockUtilities.createGameAndScreen();
		ActiveGameScreen activeGameScreen = (ActiveGameScreen) pirateGame.getScreen();

		CollegeWalls collegeWalls = new CollegeWalls(activeGameScreen, "Alcuin", Mockito.mock(Rectangle.class));

		Fixture fixtureA = mockFixture(PirateGame.DEFAULT_BIT, collegeWalls);
		Fixture fixtureB = mockFixture(PirateGame.PLAYER_BIT, MockUtilities.mockPlayer(activeGameScreen));

		worldContactListener.selectAppropriateResponse(fixtureA, fixtureB, PirateGame.DEFAULT_BIT | PirateGame.PLAYER_BIT);

		assertEquals(activeGameScreen.getCollege("Alcuin").getHealth(), 98);
	}

	@Test
	public void testWallAndPlayerReversed() {
		PirateGame pirateGame = MockUtilities.createGameAndScreen();
		ActiveGameScreen activeGameScreen = (ActiveGameScreen) pirateGame.getScreen();

		CollegeWalls collegeWalls = new CollegeWalls(activeGameScreen, "Alcuin", Mockito.mock(Rectangle.class));

		Fixture fixtureA = mockFixture(PirateGame.DEFAULT_BIT, collegeWalls);
		Fixture fixtureB = mockFixture(PirateGame.PLAYER_BIT, MockUtilities.mockPlayer(activeGameScreen));

		worldContactListener.selectAppropriateResponse(fixtureB, fixtureA, PirateGame.DEFAULT_BIT | PirateGame.PLAYER_BIT);

		assertEquals(activeGameScreen.getCollege("Alcuin").getHealth(), 98);
	}

	@Test
	public void testEnemyAndPlayer() {
		PirateGame pirateGame = MockUtilities.createGameAndScreen();
		ActiveGameScreen activeGameScreen = (ActiveGameScreen) pirateGame.getScreen();

		EnemyShip enemyShip = new EnemyShip(activeGameScreen, 0, 0, "unaligned_ship.png", "Unaligned");

		Fixture fixtureA = mockFixture(PirateGame.ENEMY_BIT, enemyShip);
		Fixture fixtureB = mockFixture(PirateGame.PLAYER_BIT, MockUtilities.mockPlayer(activeGameScreen));

		worldContactListener.selectAppropriateResponse(fixtureA, fixtureB, PirateGame.ENEMY_BIT | PirateGame.PLAYER_BIT);

		assertEquals(enemyShip.getHealth(), 80);
	}

	@Test
	public void testEnemyAndPlayerReversed() {
		PirateGame pirateGame = MockUtilities.createGameAndScreen();
		ActiveGameScreen activeGameScreen = (ActiveGameScreen) pirateGame.getScreen();

		EnemyShip enemyShip = new EnemyShip(activeGameScreen, 0, 0, "unaligned_ship.png", "Unaligned");

		Fixture fixtureA = mockFixture(PirateGame.ENEMY_BIT, enemyShip);
		Fixture fixtureB = mockFixture(PirateGame.PLAYER_BIT, MockUtilities.mockPlayer(activeGameScreen));

		worldContactListener.selectAppropriateResponse(fixtureB, fixtureA, PirateGame.ENEMY_BIT | PirateGame.PLAYER_BIT);

		assertEquals(enemyShip.getHealth(), 80);
	}

	@Test
	public void testCollegeAndCannon() {
		PirateGame pirateGame = MockUtilities.createGameAndScreen();
		ActiveGameScreen activeGameScreen = (ActiveGameScreen) pirateGame.getScreen();

		CollegeWalls collegeWalls = new CollegeWalls(activeGameScreen, "Alcuin", Mockito.mock(Rectangle.class));
		CannonFire cannonFire = new CannonFire(activeGameScreen, 0, 0, collegeWalls.getBody(), 5, new Vector2(3, 3), 1f, 1f, (short)(PirateGame.ENEMY_BIT | PirateGame.PLAYER_BIT | PirateGame.COLLEGE_BIT), PirateGame.CANNON_BIT );

		Fixture fixtureA = mockFixture(PirateGame.COLLEGE_BIT, collegeWalls);
		Fixture fixtureB = mockFixture(PirateGame.CANNON_BIT, cannonFire);

		worldContactListener.selectAppropriateResponse(fixtureA, fixtureB, PirateGame.COLLEGE_BIT | PirateGame.CANNON_BIT);

		assertTrue(cannonFire.isSetToDestroy());
	}

	@Test
	public void testCollegeAndCannonReversed() {
		PirateGame pirateGame = MockUtilities.createGameAndScreen();
		ActiveGameScreen activeGameScreen = (ActiveGameScreen) pirateGame.getScreen();

		CollegeWalls collegeWalls = new CollegeWalls(activeGameScreen, "Alcuin", Mockito.mock(Rectangle.class));
		CannonFire cannonFire = new CannonFire(activeGameScreen, 0, 0, collegeWalls.getBody(), 5, new Vector2(3, 3), 1f, 1f, (short)(PirateGame.ENEMY_BIT | PirateGame.PLAYER_BIT | PirateGame.COLLEGE_BIT), PirateGame.CANNON_BIT );

		Fixture fixtureA = mockFixture(PirateGame.COLLEGE_BIT, collegeWalls);
		Fixture fixtureB = mockFixture(PirateGame.CANNON_BIT, cannonFire);

		worldContactListener.selectAppropriateResponse(fixtureB, fixtureA, PirateGame.COLLEGE_BIT | PirateGame.CANNON_BIT);

		assertTrue(cannonFire.isSetToDestroy());
	}

	@Test
	public void testEnemyAndCannon() {
		PirateGame pirateGame = MockUtilities.createGameAndScreen();
		ActiveGameScreen activeGameScreen = (ActiveGameScreen) pirateGame.getScreen();

		EnemyShip enemyShip = new EnemyShip(activeGameScreen, 0, 0, "unaligned_ship.png", "Unaligned");
		CannonFire cannonFire = new CannonFire(activeGameScreen, 0, 0, enemyShip.getBody(), 5, new Vector2(3, 3), 1f, 1f, (short)(PirateGame.ENEMY_BIT | PirateGame.PLAYER_BIT | PirateGame.COLLEGE_BIT), PirateGame.CANNON_BIT );

		Fixture fixtureA = mockFixture(PirateGame.ENEMY_BIT, enemyShip);
		Fixture fixtureB = mockFixture(PirateGame.CANNON_BIT, cannonFire);

		worldContactListener.selectAppropriateResponse(fixtureA, fixtureB, PirateGame.ENEMY_BIT | PirateGame.CANNON_BIT);

		assertTrue(cannonFire.isSetToDestroy());
	}

	@Test
	public void testEnemyAndCannonReversed() {
		PirateGame pirateGame = MockUtilities.createGameAndScreen();
		ActiveGameScreen activeGameScreen = (ActiveGameScreen) pirateGame.getScreen();

		EnemyShip enemyShip = new EnemyShip(activeGameScreen, 0, 0, "unaligned_ship.png", "Unaligned");
		CannonFire cannonFire = new CannonFire(activeGameScreen, 0, 0, enemyShip.getBody(), 5, new Vector2(3, 3), 1f, 1f, (short)(PirateGame.ENEMY_BIT | PirateGame.PLAYER_BIT | PirateGame.COLLEGE_BIT), PirateGame.CANNON_BIT );

		Fixture fixtureA = mockFixture(PirateGame.ENEMY_BIT, enemyShip);
		Fixture fixtureB = mockFixture(PirateGame.CANNON_BIT, cannonFire);

		worldContactListener.selectAppropriateResponse(fixtureB, fixtureA, PirateGame.ENEMY_BIT | PirateGame.CANNON_BIT);

		assertTrue(cannonFire.isSetToDestroy());
	}

	@Test
	public void testPlayerAndCannon() {
		PirateGame pirateGame = MockUtilities.createGameAndScreen();
		ActiveGameScreen activeGameScreen = (ActiveGameScreen) pirateGame.getScreen();

		Player player = MockUtilities.mockPlayer(activeGameScreen);
		CannonFire cannonFire = new CannonFire(activeGameScreen, 0, 0, player.getBody(), 5, new Vector2(3, 3), 1f, 1f, (short)(PirateGame.ENEMY_BIT | PirateGame.PLAYER_BIT | PirateGame.COLLEGE_BIT), PirateGame.CANNON_BIT );

		Fixture fixtureA = mockFixture(PirateGame.PLAYER_BIT, player);
		Fixture fixtureB = mockFixture(PirateGame.CANNON_BIT, cannonFire);

		worldContactListener.selectAppropriateResponse(fixtureA, fixtureB, PirateGame.PLAYER_BIT | PirateGame.CANNON_BIT);

		assertTrue(cannonFire.isSetToDestroy());
	}

	@Test
	public void testPlayerAndCannonReversed() {
		PirateGame pirateGame = MockUtilities.createGameAndScreen();
		ActiveGameScreen activeGameScreen = (ActiveGameScreen) pirateGame.getScreen();

		Player player = MockUtilities.mockPlayer(activeGameScreen);
		CannonFire cannonFire = new CannonFire(activeGameScreen, 0, 0, player.getBody(), 5, new Vector2(3, 3), 1f, 1f, (short)(PirateGame.ENEMY_BIT | PirateGame.PLAYER_BIT | PirateGame.COLLEGE_BIT), PirateGame.CANNON_BIT );

		Fixture fixtureA = mockFixture(PirateGame.PLAYER_BIT, player);
		Fixture fixtureB = mockFixture(PirateGame.CANNON_BIT, cannonFire);

		worldContactListener.selectAppropriateResponse(fixtureB, fixtureA, PirateGame.PLAYER_BIT | PirateGame.CANNON_BIT);

		assertTrue(cannonFire.isSetToDestroy());
	}

	@Test
	public void testCollegeFireAndPlayer() {
		PirateGame pirateGame = MockUtilities.createGameAndScreen();
		ActiveGameScreen activeGameScreen = (ActiveGameScreen) pirateGame.getScreen();

		Player player = MockUtilities.mockPlayer(activeGameScreen);
		CollegeFire collegeFire = new CollegeFire(activeGameScreen, 0, 0, "Alcuin");

		Fixture fixtureA = mockFixture(PirateGame.COLLEGEFIRE_BIT, collegeFire);
		Fixture fixtureB = mockFixture(PirateGame.PLAYER_BIT, player);

		System.out.println(HUD.respawnProtection);
		worldContactListener.selectAppropriateResponse(fixtureA, fixtureB, PirateGame.COLLEGEFIRE_BIT | PirateGame.PLAYER_BIT);

		assertEquals(91, HUD.getHealth());
	}

	@Test
	public void testCollegeFireAndPlayerReversed() {
		PirateGame pirateGame = MockUtilities.createGameAndScreen();
		ActiveGameScreen activeGameScreen = (ActiveGameScreen) pirateGame.getScreen();

		Player player = MockUtilities.mockPlayer(activeGameScreen);
		CollegeFire collegeFire = new CollegeFire(activeGameScreen, 0, 0, "Alcuin");

		Fixture fixtureA = mockFixture(PirateGame.COLLEGEFIRE_BIT, collegeFire);
		Fixture fixtureB = mockFixture(PirateGame.PLAYER_BIT, player);

		System.out.println(HUD.respawnProtection);
		worldContactListener.selectAppropriateResponse(fixtureB, fixtureA, PirateGame.COLLEGEFIRE_BIT | PirateGame.PLAYER_BIT);

		assertEquals(91, HUD.getHealth());
	}

	private Fixture mockFixture(short category, Object userData) {
		Fixture fixture = Mockito.mock(Fixture.class);

		Filter filter = new Filter();
		filter.categoryBits = category;

		Mockito.when(fixture.getFilterData()).thenReturn(filter);
		Mockito.when(fixture.getUserData()).thenReturn(userData);

		return fixture;
	}
}