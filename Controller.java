/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package orbital;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Rohans
 */
public class Controller implements KeyListener {

	public static AtomicBoolean firing = new AtomicBoolean(false);

	@Override
	public void keyTyped(KeyEvent e) {
		//throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	/**
	 * Speed in blocks per event
	 * @param e
	 */

	@Override
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
			case KeyEvent.VK_LEFT:
				if (GamePanel.p != null) {
					GamePanel.p.turretAngle -= Math.PI / 24;
				}
				break;
			case KeyEvent.VK_RIGHT:
				if (GamePanel.p != null) {
					GamePanel.p.turretAngle += Math.PI / 24;
				}
				break;

		}
//the following ensures that the angle will be in the range of [0,2pi) radians
		GamePanel.p.turretAngle += 2 * Math.PI;
		GamePanel.p.turretAngle %= 2 * Math.PI;
	}

	@Override
	public void keyReleased(KeyEvent e) {
		switch (e.getKeyCode()) {
			case KeyEvent.VK_SPACE:
				GamePanel.items.get().add(new Bullet(GamePanel.p.x+8*Math.cos(GamePanel.p.turretAngle), GamePanel.p.y+8*Math.sin(GamePanel.p.turretAngle), GamePanel.p.dX + 1.5 * Math.cos(GamePanel.p.turretAngle), GamePanel.p.dY + 1.5 * Math.sin(GamePanel.p.turretAngle)));
				GamePanel.p.dX -= .25 * Math.cos(GamePanel.p.turretAngle);
				GamePanel.p.dY -= .25 * Math.sin(GamePanel.p.turretAngle);
				break;
				case KeyEvent.VK_Q:
				GamePanel.p.dX -= 1 * Math.cos(GamePanel.p.turretAngle);
				GamePanel.p.dY -= 1 * Math.sin(GamePanel.p.turretAngle);	
		}
	}
}
