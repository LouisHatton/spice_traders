package com.mygdx.pirategame.entity.ship;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.pirategame.screen.ActiveGameScreen;

public class EnemyAiManager {
	static float cooldown = 0;
	static float cooldownOg = 2;
	static double shortestDistance = 10000;
	static EnemyShip closestShip;

	public static void update(float dt) {

		if (true) return;


		if (cooldown > 0) {
			cooldown -= dt;
			return;
		}
		for (int i = 0; i < ActiveGameScreen.getShips().size(); i++) {
			if (ActiveGameScreen.getShips().get(i).college.equals("Alcuin") || ActiveGameScreen.getShips().get(i).college.equals("Unaligned")) {
				findTarget(i);

			}
		}
		cooldown = cooldownOg;
	}

	public static void findTarget(int j) {
		shortestDistance = 100000;
		closestShip = null;
		for (int i = 0; i < ActiveGameScreen.getShips().size(); i++) {
			EnemyShip ship2 = ActiveGameScreen.getShips().get(i);
			System.out.println(ActiveGameScreen.getShips().get(i).college + " , " + ActiveGameScreen.getShips().get(j).college);
			if (ActiveGameScreen.getShips().get(j).shootBox.overlaps(ship2.shootBox) && ActiveGameScreen.getShips().get(i).college != ActiveGameScreen.getShips().get(j).college) {
				if (shortestDistance > distance(ActiveGameScreen.getShips().get(j).body.getPosition(), ActiveGameScreen.getShips().get(i).body.getPosition())) {


					if (ActiveGameScreen.getShips().get(j).college == "Alcuin") {
						System.out.println("pp4");
						shortestDistance = distance(ActiveGameScreen.getShips().get(j).body.getPosition(), ActiveGameScreen.getShips().get(i).body.getPosition());
						closestShip = ActiveGameScreen.getShips().get(i);
					}

					if (ActiveGameScreen.getShips().get(j).college == "Unaligned" && ActiveGameScreen.getShips().get(i).college == "Alcuin") {
						System.out.println("pp5");
						shortestDistance = distance(ActiveGameScreen.getShips().get(j).body.getPosition(), ActiveGameScreen.getShips().get(i).body.getPosition());
						closestShip = ActiveGameScreen.getShips().get(i);
					}


				}
			}
		}

		if (closestShip != null) {
			System.out.println("set");
			ActiveGameScreen.getShips().get(j).setNPCTarget(closestShip);
		}

	}

	static double distance(Vector2 object1, Vector2 object2) {
		return Math.sqrt(Math.pow((object2.x - object1.x), 2) + Math.pow((object2.y - object1.y), 2));
	}
}
