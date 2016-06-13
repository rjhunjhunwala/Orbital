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
import javax.imageio.ImageIO;

/**
 *
 * @author rohan
 */
public class Player extends Movable {

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

	public double calculateAngle() {
		double angle = Math.atan(dY / dX);
		if (dX < 0) {
			angle = Math.PI + angle;
		}
		return angle;
	}
public static final boolean safeLandingsEnforced=false;
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

					double speed = Math.sqrt(dX * dX - dY * dY);

					if (circle instanceof MovingPlanet) {
						x -= dX * 1.1;
						y -= dY * 1.1;
						dX += ((Movable) circle).dX;
						dY += ((Movable) circle).dY;
						x += dX;
						y += dY;
						dX = 0;
						dY = 0;
						Movable m=(Movable) circle;
					double angleDiff=Math.abs(oldAngle - turretAngle);
					if ( safeLandingsEnforced &&angleDiff> .433&&speed-Math.sqrt(m.dX*m.dX+m.dY*m.dY)>.5){
						Enemy.shipHealth -=(int) ((angleDiff*3));
						if (Enemy.shipHealth <= 0) {
							Orbital.playerIsAlive.set(false);
						}
					}
					} else {

						x -= dX;
						y -= dY;
						dX =0;
						dY = 0;
					double angleDiff=Math.abs(oldAngle - turretAngle);
					if ( safeLandingsEnforced&&angleDiff> .433&&speed>.5) {
						Enemy.shipHealth -=(int) ((angleDiff*3)*speed);
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
