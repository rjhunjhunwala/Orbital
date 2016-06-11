/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package orbital;

import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author rohan
 */
public class Player extends Movable{
double turretAngle;
	public Player(int inX, int inY) {
		super(inX, inY, 5, Color.GREEN);
	}
@Override
	public void drawSelf(Graphics g){
		super.drawSelf(g);
		g.setColor(Color.blue);
		g.drawLine((int)x,(int) y,(int)(x+6*Math.cos(turretAngle)),(int) (y+6*Math.sin(turretAngle)));
	}
@Override
public void checkCollisions(){
	for (Circle circle : GamePanel.items.get()) {
		if(circle!=null&&circle!=this&&circle.alive.get()) {
			if(this.isTouching(circle)&&!(circle instanceof Bullet)){
				Orbital.playerIsAlive.set(false);
				return;
			}
		}
	}
	if(x<-6||y<-6||y>GamePanel.screenheight+6||x>GamePanel.screenlength+6){
		Orbital.playerIsAlive.set(false);
	}
}
}
