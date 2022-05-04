package com.mygdx.pirategame.entity.cannon;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.Array;



/**
 * Handle all spawned in cannonballs.
 *
 * @author Faris Alblooki
 * @version 2.0
 */
public class CannonManager {

	public static Array<CannonFire> cannonBalls = new Array<CannonFire>();

	/**
	 * Cache a cannonball.
	 *
	 * @param cf Cannonball you wish to cache.
	 */
	public static void insert(CannonFire cf) {
		cannonBalls.add(cf);
	}

	/**
	 * Update our cache and update/remove any cannonballs that should have expired.
	 *
	 * @param dt Tick of the game.
	 * @param batch Batch you wish to use to remove the body.
	 */
	public static void update(float dt, Batch batch) {
		// Updates cannonball data
		for (CannonFire ball : cannonBalls) {
			ball.update(dt);
			draw(batch, ball);
			if (ball.isDestroyed())
				cannonBalls.removeValue(ball, true);
		}

	}

	/**
	 * Draw the cannonball on the screen.
	 *
	 * @param batch Batch you wish to use for this operation.
	 * @param ball Ball you wish to draw.
	 */
	public static void draw(Batch batch, CannonFire ball) {
		ball.draw(batch);
	}
}
