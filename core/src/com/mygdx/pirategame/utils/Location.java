package com.mygdx.pirategame.utils;

import java.util.Objects;

/**
 * Utility class that allows us store X/Y Coordinates easily.
 */
public class Location {

	private final float x;
	private final float y;

	public Location(float x, float y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * @return X Coordinate (centered from 0)
	 */
	public float getX() {
		return x;
	}

	/**
	 * @return Y Coordinate (centered from 0)
	 */
	public float getY() {
		return y;
	}

	@Override
	public int hashCode() {
		return Objects.hash(x, y);
	}
}