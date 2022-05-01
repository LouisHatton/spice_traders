package com.mygdx.pirategame.utils;

import java.util.Objects;

public class Location {

	private final float x;
	private final float y;

	public Location(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	@Override
	public int hashCode() {
		return Objects.hash(x, y);
	}
}