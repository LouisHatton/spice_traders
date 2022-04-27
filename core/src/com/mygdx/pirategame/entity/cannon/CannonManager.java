package com.mygdx.pirategame.entity.cannon;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.Array;

public class CannonManager {

	public static Array<CannonFire> cannonBalls = new Array<CannonFire>();

	public static void insert(CannonFire cf) {
		cannonBalls.add(cf);
	}

	public static void update(float dt, Batch batch) {
		// Updates cannonball data
		for (CannonFire ball : cannonBalls) {
			ball.update(dt);
			draw(batch, ball);
			if (ball.isDestroyed())
				cannonBalls.removeValue(ball, true);
		}

	}

	public static void draw(Batch batch, CannonFire ball) {
		ball.draw(batch);
	}
}
