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
 *
 * @author rohan
 */
public class MovingPlanet extends Movable{
private GravityWell well;
	public MovingPlanet(int inX, int inY, int inR, double inDx, double inDY) {
		super(inX, inY, inR, Color.gray, inDx, inDY);
		well=new GravityWell(inX,inY,inR*inR);
		GamePanel.wells.get().add(well);
			if(radius==25){
		//The moon has a radius of 25 units
		try{
			sprite=ImageIO.read(new File("moon.png"));
		}catch(Exception ex){
			
		}
	}
	}
@Override
			public void step(){
			try{
						if(noCollisions()){
				applyOutwardForces();
			
			x+=dX;
			y+=dY;
			well.x=x;
			well.y=y;
						}
			}catch(ConcurrentModificationException ex){
				//my sincere apologies
			}
			}
@Override
	public void applyOutwardForces() {
		GamePanel.wells.get().stream().filter((someWell) -> (someWell!=null&&someWell!=this.well)).forEach((someWell) -> {
			double a =this.x-someWell.x;
			double b= this.y-someWell.y;
			double dist=Math.sqrt(a*a+b*b);
			double gForce = someWell.g/(3*dist*dist);
			double angle=Math.atan(b/a);
			if(a<0){
				angle=Math.PI+angle;
			}
			dX-=gForce*Math.cos(angle);
			dY-=gForce*Math.sin(angle);
	});
	}
			@Override
public boolean noCollisions(){
//well.x=this.x;
//well.y=this.y;
return true;
}
	
}
