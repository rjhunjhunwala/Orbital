/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package orbital;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.swing.JOptionPane;

/**
 *
 * @author rohan
 */
public class Orbital {

	/**
	 * An endless game similar to asteroids, but with a twist! Planets! Please see
	 * attached how to play gif for more information
	 */
	public static AtomicBoolean playerIsAlive = new AtomicBoolean(true);

	/**
	 * plays one game then hides the window and pops up the main menu
	 *
	 * @param sandbox is a boolean flag determining whether or not asteroids will
	 * spawn. Sandbox mode allows the user to experiment with orbits and proper landings
	 *
	 * @throws IOException
	 */
	public static void playOneGame(boolean sandbox) throws IOException {
		playerIsAlive = new AtomicBoolean(true);
		new Thread(sandbox?new SandboxGame():new Game()).start();
		GameFrame g = new GameFrame(sandbox);
		while (playerIsAlive.get()) {
			g.repaint();
		}
		popUp("You lose");

		HighScore.manageScore(Enemy.score);
		GamePanel.p = null;
		GamePanel.items.set(new ArrayList<>());
		GamePanel.wells.set(new ArrayList<>());
		g.setVisible(false);
		TitleFrame.playing.set(false);
	}

	public static void popUp(String prompt) {
		JOptionPane.showMessageDialog(null, prompt);
	}
}
