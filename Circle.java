package orbital;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.imageio.ImageIO;

/**
 *
 * @author rohan
 */
public class Circle {

	public static Color getRandomColor() {
		return new Color((int) (Math.random() * 186) + 70, (int) (Math.random() * 186) + 70, (int) (Math.random() * 186) + 70);
	}
	public BufferedImage sprite;
	/**
	 * radius is in pixels
	 */
	public int radius;
	/**
	 * X coordinate of the center of this circle measured in pixels
	 */
	public double x;
	/**
	 * the y coordinate in pixels This represents the distance in pixels the center
	 * is from the origin located in the top left corner
	 */
	public double y;
	/**
	 * This is used if the sprite is not found
	 */
	public Color c;
	/**
	 * Represents whether or not this entity is alive and needs to be rendered This
	 * does not imply that it is animate just whether or not it still needs to be
	 * rendered
	 */
	public AtomicBoolean alive = new AtomicBoolean(true);

	/**
	 * Constructs a new circle with the given x/y pair radius and color
	 *
	 * @param inX x coordinate
	 * @param inY y coordinate
	 * @param inR radius
	 * @param inC Color
	 */
	public Circle(int inX, int inY, int inR, Color inC) {
		c = inC;
		x = inX;
		y = inY;
		radius = inR;

	}

	/**
	 * Constructs the given circle with a random color Also assigns the sprite of
	 * Earth if the circle is of the given size
	 *
	 * @param inX x coordinate
	 * @param inY y coordinate
	 * @param inR radius
	 */
	public Circle(int inX, int inY, int inR) {
		c = getRandomColor();
		x = inX;
		y = inY;
		radius = inR;
		if (radius == 60) {
			//Earth has a radius of 60 units
			try {
				sprite = ImageIO.read(new File("SmallEarth.png"));
			} catch (Exception ex) {

			}
		} else 		if (radius == 32) {
			//Mars has a radius of 120 units (not to scale)
			try {
				sprite = ImageIO.read(new File("mars.png"));
			} catch (Exception ex) {

			}
		}
	}

	public boolean isTouching(Circle ci) {
		double a = this.x - ci.x;
		double b = this.y - ci.y;
		int c = ci.radius + this.radius;
		return (Math.sqrt(a * a + b * b) < Math.sqrt(c * c));
	}

	public void drawSelf(Graphics g) {
		if (sprite == null) {
			g.setColor(c);
			g.fillOval((int) x - radius, (int) y - radius, 2 * radius, 2 * radius);
		} else {
			g.drawImage(sprite, (int) x - radius, (int) y - radius, null);
		}
	}

}
