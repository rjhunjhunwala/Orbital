/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package orbital;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.imageio.ImageIO;

/**
 * The player has many different features
 *
 * @author rohan
 */
public class Player extends Movable {

	/**
	 * Turn this flag on to enable damage being received upon low quality landings
	 */
	public boolean safeLandingsEnforced;
/**
	* Prevents the ship from continually taking damage
	*/
	public AtomicBoolean canTakeDamage=new AtomicBoolean(false);
	/**
	 * The angle this vehicle is facing
	 */
	double turretAngle;

	public Player(int inX, int inY) {
		super(inX, inY, 17, Color.GREEN);
		try {
			sprite = ImageIO.read(new File("player.png"));
		} catch (Exception ex) {

		}
	}

	@Override
	public void drawSelf(Graphics g) {
		//super.drawSelf(g);
		AffineTransform at = new AffineTransform();

		at.translate(x, y);
		at.rotate(turretAngle - Math.PI / 2);
		at.translate(-sprite.getWidth() / 2, -sprite.getHeight() / 2);

		Graphics2D g2 = (Graphics2D) g;
		g2.drawImage(sprite, at, null);
		g.setColor(Color.black);

	}

	/**
	 * Currently unused method which would calculate a realistic angle to render
	 * this object. We now render the object based not on what direction is
	 * traveling but rather how the user chooses to orient it
	 *
	 * @return
	 */
	public double calculateAngle() {
		double angle = Math.atan(dY / dX);
		if (dX < 0) {
			angle = Math.PI + angle;
		}
		return angle;
	}

	/**
	 * Allows the Player to land on planets
	 *
	 * @return
	 */
	@Override
	public boolean noCollisions() {
		for (Circle circle : GamePanel.items.get()) {
			if (circle != null && circle != this && circle.alive.get()) {
				if (this.isTouching(circle) && !(circle instanceof Bullet || circle instanceof Enemy)) {
					double oldAngle = this.turretAngle;
					double angle = Math.atan((this.y - circle.y) / (this.x - circle.x));
					if (this.x - circle.x > 0) {
						angle += Math.PI;
					}
					turretAngle = angle;

					if (circle instanceof MovingPlanet) {
						x -= dX * 1.1;
						y -= dY * 1.1;
						double pastDX=dX;
						double pastDY=dY;
						dX += ((Movable) circle).dX;
						dY += ((Movable) circle).dY;
						x += dX;
						y += dY;
						dX = 0;
						dY = 0;
						Movable m = (Movable) circle;
						double dXDiff = pastDX - m.dX;
						double dYDiff = pastDY - m.dY;
						double collisionSpeed =pastDX==0&&pastDY==0?0: Math.sqrt(dXDiff * dXDiff + dYDiff * dYDiff);
						double angleDiff =(oldAngle - turretAngle);
angleDiff=Math.abs(angleDiff%(2*Math.PI));
if(angleDiff>Math.PI){
	angleDiff=Math.PI*2-angleDiff;
}
						if (safeLandingsEnforced && (angleDiff > .7 || collisionSpeed > 7)) {
							Enemy.shipHealth -= 1 + (int) ((angleDiff>.7?angleDiff-.6:0 * 3))+(collisionSpeed>3?2:0);
							if (Enemy.shipHealth <= 0) {
								Orbital.playerIsAlive.set(false);
							}
						}
					} else {
						double speed = Math.sqrt(dX * dX - dY * dY);
						x -= dX;
						y -= dY;
						dX = 0;
						dY = 0;
						double angleDiff =(oldAngle - turretAngle);
angleDiff=Math.abs(angleDiff%(2*Math.PI));
if(angleDiff>Math.PI){
	angleDiff=Math.PI*2-angleDiff;
}
						if (safeLandingsEnforced && (angleDiff > .6 || speed > 1.5)) {
							Enemy.shipHealth -=  1 + (int) ((angleDiff>.6?(angleDiff-.3)*4:0))+(speed>1.5?speed:0) ;
							if (Enemy.shipHealth <= 0) {
								Orbital.playerIsAlive.set(false);
							}
						}
					}

					return false;
				}

			}

		}
		if (x < -6 || y < -6 || y > GamePanel.screenheight + 6 || x > GamePanel.screenlength + 6) {
			x += GamePanel.screenlength;
			y += GamePanel.screenheight;
			x %= GamePanel.screenlength;
			y %= GamePanel.screenheight;
		}
		return true;
	}

}
