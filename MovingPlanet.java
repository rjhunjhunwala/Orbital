/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package orbital;

import java.awt.Color;
import java.io.File;
import java.util.ConcurrentModificationException;
import javax.imageio.ImageIO;

/**
 * Allows for planets to move
 *
 * @author rohan
 */
public class MovingPlanet extends Movable {
public int planetAttractedTo=0;
	/**
	 * Shows the location and magnitude of the well created by this object
	 */
	private GravityWell well;

	public MovingPlanet(double inX, double inY, int inR, double inDx, double inDY) {
		super((int) inX, (int) inY, inR, Color.gray, inDx, inDY);
		well = new GravityWell((int) inX, (int) inY, inR * inR);
		GamePanel.wells.get().add(well);
		if (radius == 25) {
			//The moon has a radius of 25 units
			try {
				sprite = ImageIO.read(new File("moon.png"));
			} catch (Exception ex) {

			}
		}else		if (radius == 18) {
			//Europa has a radius of 25 units
			try {
				sprite = ImageIO.read(new File("europa.png"));
			} catch (Exception ex) {

			}
		}
	}
	/**
		*This consctructor allows planets other than earth to have satelites 
		* @param inX
		* @param inY
		* @param inR
		* @param inDx
		* @param inDY 
		* @param inPlanet an integer which represents the planet to be attracted to
		*/
	public MovingPlanet(double inX, double inY, int inR, double inDx, double inDY,
									int inPlanet) {
		super((int) inX, (int) inY, inR, Color.gray, inDx, inDY);
		planetAttractedTo=inPlanet;
		well = new GravityWell((int) inX, (int) inY, inR * inR);
		GamePanel.wells.get().add(well);
		if (radius == 25) {
			//The moon has a radius of 25 units
			try {
				sprite = ImageIO.read(new File("moon.png"));
			} catch (Exception ex) {

			}
		}else		if (radius == 18) {
			//Europa has a radius of 25 units
			try {
				sprite = ImageIO.read(new File("europa.png"));
			} catch (Exception ex) {

			}
		}
	}

	/**
	 * Allows the well to move with the object
	 */
	@Override
	public void step() {
		try {
			if (noCollisions()) {
				applyOutwardForces();

				x += dX;
				y += dY;
				well.x = x;
				well.y = y;
			}
		} catch (ConcurrentModificationException ex) {
			//my sincere apologies
		}
	}

	/**
	 * Moving planets take some liberties with typical gravitational forces They
	 * are only attracted to the first (central) planet
	 */
	@Override
	public void applyOutwardForces() {
GravityWell mainWell = GamePanel.wells.get().get(planetAttractedTo);
			double a = this.x - mainWell.x;
			double b = this.y - mainWell.y;
			double dist = Math.sqrt(a * a + b * b);
			double gForce = mainWell.g / (3 * dist * dist);
			double angle = Math.atan(b / a);
			if (a < 0) {
				angle = Math.PI + angle;
			}
			dX -= gForce * Math.cos(angle);
			dY -= gForce * Math.sin(angle);
	}

	/**
	 * Moving planets will "die" whenever they hit another planet 
		* (moving or otherwise)
	 *
	 * @return
	 */
	@Override
	public boolean noCollisions() {
for(Circle C:GamePanel.items.get()){
	if(C!=this&&C.alive.get()&&!(C instanceof Bullet||C instanceof Enemy)&&C.isTouching(this)){
		this.alive.set(false);
for(int i=0;i<GamePanel.wells.get().size();i++){
	if(GamePanel.wells.get().get(i)==this.well){
		GamePanel.wells.get().set(i, null);
	}
}
		return false;
	}
}
return true;
	}

}
