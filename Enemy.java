package orbital;

import java.awt.Color;
import java.io.File;
import javax.imageio.ImageIO;

/**
 *
 * @author rohan
 */
public class Enemy extends Movable {

	static int planetsHealth;
	static int shipHealth;
	static int score;

	public Enemy() {
		super(-1000, -1000, 8, Color.red);
		score++;
		switch ((int) (Math.random() * 4)) {
			case 0:
				x = Math.random() * GamePanel.screenlength;
				y = -50 * Math.random();
				break;
			case 1:
				x = Math.random() * GamePanel.screenlength;
				;
				y = GamePanel.screenheight + 50 * Math.random();
				break;
			case 2:
				y = Math.random() * GamePanel.screenheight;
				x = -50 * Math.random();
				break;
			case 3:
				y = Math.random() * GamePanel.screenheight;
				;
				x = GamePanel.screenlength + 50 * Math.random();
				break;
		}
		try {
			sprite = ImageIO.read(new File("asteroid.gif"));
		} catch (Exception ex) {

		}
	}

	/**
	 * Allows the enemy (Asteroid) to affect the score as well as health of both
	 * the planet and player
	 *
	 * @return
	 */
	@Override
	public boolean noCollisions() {
		int i = 0;
		for (Circle circle : GamePanel.items.get()) {
			if (circle != null && circle != this && circle.alive.get()) {
				if (this.isTouching(circle)) {
					this.alive.set(false);
					if ((circle instanceof Movable && !(circle instanceof MovingPlanet))) {
						circle.alive.set(false);

					} else if (i == 0) {
						planetsHealth--;

						if (planetsHealth <= 0) {
							Orbital.playerIsAlive.set(false);
						}
					}

					return false;
				}
			}
			i++;
		}
		if (this.isTouching(GamePanel.p)) {
			this.alive.set(false);
			//	Orbital.playerIsAlive.set(false);
			shipHealth--;
			if (shipHealth <= 0) {
				Orbital.playerIsAlive.set(false);
			}
			return false;
		}
		return true;
	}

	/**
	 * Asteroids experience significantly reduced gravity to allow for game-play to
	 * be more fair
	 */
	@Override
	public void applyOutwardForces() {
		GamePanel.wells.get().stream().filter((well) -> (well != null)).forEach((well) -> {
			double a = this.x - well.x;
			double b = this.y - well.y;
			double dist = Math.sqrt(a * a + b * b);
			double gForce = well.g / (35 * dist * dist);
			double angle = Math.atan(b / a);
			if (a < 0) {
				angle = Math.PI + angle;
			}
			dX -= gForce * Math.cos(angle);
			dY -= gForce * Math.sin(angle);
		});
	}
}
