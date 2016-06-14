package orbital;

import java.io.File;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author rohan
 */
public class Game implements Runnable {

	private long steps = 0;
	private boolean startEnemies = false;

	/**
	 * A background game loop that constantly allows updates to all entities
	 * on-screen
	 */
	@Override
	public void run() {

		startEnemies = false;
		Enemy.planetsHealth = 10;
		Enemy.shipHealth = 10;
		Enemy.score = 0;
		while (Orbital.playerIsAlive.get()) {
			try {
				Thread.sleep(20);
			} catch (InterruptedException ex) {
				Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
			}
			steps++;
			if (steps == 500) {
				startEnemies = true;
			}
			if (startEnemies && steps % (400 - (Enemy.score * 5 > 200 ? 200 : Enemy.score * 5)) == 0) {
				GamePanel.items.get().add(new Enemy());
			}
			if (GamePanel.p != null) {
				GamePanel.p.step();
			}
			ArrayList<Circle> items = GamePanel.items.get();
			try {
				items.stream().filter((c) -> (c != null && c.alive.get())).filter((c) -> (c instanceof Movable)).forEach((c) -> {
					((Movable) c).step();
				});
			} catch (ConcurrentModificationException ex) {
				//sorry
			}
		}
	}
}
