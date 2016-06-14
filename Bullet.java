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

}
