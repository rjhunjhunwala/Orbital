package orbital;

import java.awt.Color;

/**
 *
 * @author rohan
 */
public class Bullet extends Movable {

	/**
	 * Creates a new bullet with input of x/y coordinates and x/y speed
	 *
	 * @param inX x coordinate
	 * @param inY y coord
	 * @param inDx x speed
	 * @param inDy y speed
	 */
	public Bullet(double inX, double inY, double inDx, double inDy) {
		super((int) inX, (int) inY, 2, Color.blue);
		dX = inDx;
		dY = inDy;

	}
/**
	* Removed screen wrapping for bullets
	* @return 
	*/
	@Override
	public boolean noCollisions() {
		for (Circle circle : GamePanel.items.get()) {
			if (circle != null && circle != this && circle.alive.get()) {
				if (this.isTouching(circle)) {
					this.alive.set(false);
					if ((circle instanceof Movable) && !(circle instanceof MovingPlanet)) {
						circle.alive.set(false);
					}
					return false;
				}
			}
		}
		if (this.isTouching(GamePanel.p)) {
			this.alive.set(false);
			return false;
		}
		if (x < -6 || y < -6 || y > GamePanel.screenheight + 6 || x > GamePanel.screenlength + 6) {
this.alive.set(false);
return false;
		}
		return true;
	}
}
