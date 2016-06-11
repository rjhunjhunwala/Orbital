package orbital;

import java.awt.Color;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author rohan
 */
public class Enemy extends Movable{
static int planetsHealth=10;
static int shipHealth=10;
public Enemy(){
				super(-1000,-1000, 10, Color.red);
		switch((int) (Math.random()*4)){
			case 0:
				x=Math.random()*GamePanel.screenlength;
				y=-50*Math.random();
		break;
			case 1:
				x=		Math.random()*GamePanel.screenlength;;
				y=GamePanel.screenheight+50*Math.random();
				break;
							case 2:
				y=Math.random()*GamePanel.screenheight;
				x=-50*Math.random();
		break;
											case 3:
				y=		Math.random()*GamePanel.screenheight;;
				x=GamePanel.screenlength+50*Math.random();
				break;
		}

	}
@Override
	public boolean noCollisions(){
	int i=0;
		for (Circle circle : GamePanel.items.get()) {
		if(circle!=null&&circle!=this&&circle.alive.get()) {
			if(this.isTouching(circle)){
				this.alive.set(false);
				if((circle instanceof Movable&&!(circle instanceof MovingPlanet))) {
					circle.alive.set(false);
				
				}
				else if(i==0){
		   planetsHealth--;

				
				if(planetsHealth<=0){
							Orbital.playerIsAlive.set(false);			
				}
				}
				
return false;
			}
		}
	}
	if(this.isTouching(GamePanel.p)){
		this.alive.set(false);
	//	Orbital.playerIsAlive.set(false);
	shipHealth--;
					if(shipHealth<=0){
							Orbital.playerIsAlive.set(false);			
				}
	return false;
	}
	return true;
	}
@Override
	public void applyOutwardForces() {
		GamePanel.wells.get().stream().filter((well) -> (well!=null)).forEach((well) -> {
			double a =this.x-well.x;
			double b= this.y-well.y;
			double dist=Math.sqrt(a*a+b*b);
			double gForce = well.g/(15*dist*dist);
			double angle=Math.atan(b/a);
			if(a<0){
				angle=Math.PI+angle;
			}
			dX-=gForce*Math.cos(angle);
			dY-=gForce*Math.sin(angle);
	});
	}
}

