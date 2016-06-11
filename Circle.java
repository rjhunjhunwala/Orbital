package orbital;

import java.awt.Color;
import java.awt.Graphics;
import java.util.concurrent.atomic.AtomicBoolean;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author rohan
 */
public class Circle {
	public int radius;
	public double x;
	public double y;
	public Color c;
	public AtomicBoolean alive=new AtomicBoolean(true);
	public Circle(int inX,int inY,int inR,Color inC){
		c=inC;
	x=inX;
	y=inY;
	radius=inR;
	}
	public Circle(int inX,int inY,int inR){
		c=getRandomColor();
	x=inX;
	y=inY;
	radius=inR;
	}
	public boolean isTouching(Circle ci){
		double a=this.x-ci.x;
		double b=this.y-ci.y;
		int c=ci.radius+this.radius;
		return(Math.sqrt(a*a+b*b)<Math.sqrt(c*c));
	}
	public void drawSelf(Graphics g){
	g.setColor(c);
g.fillOval((int) x-radius, (int) y-radius, 2*radius, 2*radius);
	}
	public static Color getRandomColor(){
		return new Color((int) (Math.random()*186)+70,(int) (Math.random()*186)+70,(int) (Math.random()*186)+70);
	}


}
