/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package orbital;

import java.awt.Color;

/**
 *
 * @author rohan
 */
public class Bullet extends Movable{
		public Bullet(double inX, double inY,double inDx,double inDy){
		super((int)inX, (int) inY, 2, Color.blue);
	dX=inDx;
	dY=inDy;

		}
		
}
