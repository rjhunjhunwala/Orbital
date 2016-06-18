
package orbital;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 *
 * @author Rohans
 */
public class Controller implements KeyListener {

	/**
	 * Unused keyTyped event
	 *
	 * @param e
	 */
	@Override
	public void keyTyped(KeyEvent e) {
//nil
	}

	/**
	 * Allows controls that can be held down These currently include left and right
	 * arrows as well as the a/d keys Turns the spacecraft
	 *
	 * @param e
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
			case KeyEvent.VK_LEFT:
			case KeyEvent.VK_A:
				if (GamePanel.p != null && GamePanel.p.noCollisions()) {
					GamePanel.p.turretAngle -= Math.PI / 24;
				}
				break;
			case KeyEvent.VK_RIGHT:
			case KeyEvent.VK_D:
				if (GamePanel.p != null && GamePanel.p.noCollisions()) {
					GamePanel.p.turretAngle += Math.PI / 24;
				}
				break;

		}
//the following ensures that the angle will be in the range of [0,2pi) radians
		GamePanel.p.turretAngle += 2 * Math.PI;
		GamePanel.p.turretAngle %= 2 * Math.PI;
	}

	/**
	 * This is for controls that can not be held down. Such as Shooting (With
	 * space) Thrusters QWE, and rearwards thruster S
	 *
	 * @param e
	 */
	@Override
	public void keyReleased(KeyEvent e) {
		switch (e.getKeyCode()) {
			case KeyEvent.VK_SPACE:
			if(!GamePanel.sandboxModeEnabled){
				Bullet b = new Bullet(GamePanel.p.x +(GamePanel.p.radius+1) * Math.cos(Math.PI + GamePanel.p.turretAngle), GamePanel.p.y + (GamePanel.p.radius + 1) * Math.sin(Math.PI + GamePanel.p.turretAngle), GamePanel.p.dX + (GamePanel.p.radius + 1) * Math.cos(GamePanel.p.turretAngle + Math.PI), GamePanel.p.dY + 9 * Math.sin(GamePanel.p.turretAngle + Math.PI));
				GamePanel.items.get().add(b);
				b.x -= GamePanel.p.dX - b.dX;
				b.y -= GamePanel.p.dY - b.dY;
				GamePanel.p.dX -= 0 * Math.cos(GamePanel.p.turretAngle);
				GamePanel.p.dY -= 0 * Math.sin(GamePanel.p.turretAngle);
//				GamePanel.p.x+=GamePanel.p.dX;
//				GamePanel.p.y+=GamePanel.p.dY;
			}
				break;
			case KeyEvent.VK_S:
				GamePanel.p.dX += 1 * Math.cos(GamePanel.p.turretAngle);
				GamePanel.p.dY += 1 * Math.sin(GamePanel.p.turretAngle);
				break;
			case KeyEvent.VK_Q:
				GamePanel.p.dX -= 1 * Math.cos(GamePanel.p.turretAngle);
				GamePanel.p.dY -= 1 * Math.sin(GamePanel.p.turretAngle);
				GamePanel.p.x += 2 * GamePanel.p.dX;
				GamePanel.p.y += 2 * GamePanel.p.dY;
				break;
			case KeyEvent.VK_W:
				GamePanel.p.dX -= 3 * Math.cos(GamePanel.p.turretAngle);
				GamePanel.p.dY -= 3 * Math.sin(GamePanel.p.turretAngle);
				GamePanel.p.x += 2 * GamePanel.p.dX;
				GamePanel.p.y += 2 * GamePanel.p.dY;
				break;
			case KeyEvent.VK_E:
				GamePanel.p.dX -= 5 * Math.cos(GamePanel.p.turretAngle);
				GamePanel.p.dY -= 5 * Math.sin(GamePanel.p.turretAngle);
				GamePanel.p.x += 2 * GamePanel.p.dX;
				GamePanel.p.y += 2 * GamePanel.p.dY;
				break;

		}
	}
}
