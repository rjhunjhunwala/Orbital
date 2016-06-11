package orbital;

import java.awt.Color;
import java.util.ConcurrentModificationException;
import java.util.Iterator;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author rohan
 */
public class Movable extends Circle{
double dX;
double dY;
	public Movable(int inX, int inY, int inR, Color inC) {
		super(inX, inY, inR, inC);
	}
		public Movable(int inX, int inY, int inR, Color inC,double inDx,double inDY) {
			super(inX, inY, inR, inC);
			dX=inDx;
			dY=inDY;
	}
		public void step(){
			try{
						if(noCollisions()){
				applyOutwardForces();
			x+=dX;
			y+=dY;
						}
			}catch(ConcurrentModificationException ex){
				//my sincere apologies
			}
			}
public boolean noCollisions(){
	for (Circle circle : GamePanel.items.get()) {
		if(circle!=null&&circle!=this&&circle.alive.get()) {
			if(this.isTouching(circle)){
				this.alive.set(false);
				if((circle instanceof Movable)&&!(circle instanceof MovingPlanet)) {
					circle.alive.set(false);
				}
				return false;
			}
		}
	}
	if(this.isTouching(GamePanel.p)){
		this.alive.set(false);
	return false;
	}
	if(x<-6||y<-6||y>GamePanel.screenheight+6||x>GamePanel.screenlength+6){
x+=GamePanel.screenlength;
y+=GamePanel.screenheight;
x%=GamePanel.screenlength;
y%=GamePanel.screenheight;
	}
	return true;
}


	public void applyOutwardForces() {
for(GravityWell well:GamePanel.wells.get()){
	if(well!=null){
		double a =this.x-well.x;
		double b= this.y-well.y;
		double dist=Math.sqrt(a*a+b*b);
		double gForce = well.g/(3*dist*dist);
		double angle=Math.atan(b/a);
		if(a<0){
			angle=Math.PI+angle;
		}
		dX-=gForce*Math.cos(angle);
		dY-=gForce*Math.sin(angle);
	}
}
	}
}
