package com.mygdx.pirategame.utils;

import java.util.Objects;

public class Location {

	private final int x;
	private final int y;

	public Location(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Location)) return false;
		Location location = (Location) o;
		return x == location.x && y == location.y;
	}

	@Override
	public int hashCode() {
		return Objects.hash(x, y);
	}
}