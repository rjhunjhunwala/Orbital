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
	* An endless game similar to asteroids, but with a twist!
	* Planets!
	* - Planets will be randomly placed at the start of each game 
	* - 0-4 planets
	* - Each With realistic gravity
	* Thrust
	* - Thrust is provided by shooting projectiles at different speeds at different angles
	* - (Optional implementation of standard thrusters may be provided)
	* Enemies
	* - Adaptive difficulty
	* - (Mechanics to allow return fire optional)
	* Death
	* - Leave the solar system (we might wrap the screen) we might not
	* - Crash into a planet 
	* - Crash into an enemy
	* - Take fire
	* - Controls
	* - Space to shoot
	* - Q for rear thrusters (Standard)
	* -Left and right to aim turret
	* Graphics
	* - Title screen
	*        - How to play
	* - Planets singular colored with black background
	*            - Second iteration with planet colors
	* - Spaceship may be circular or shaped like a real space ship
	* 
	*/
	    public static AtomicBoolean playerIsAlive=new AtomicBoolean(true);
    public static AtomicBoolean won=new AtomicBoolean(false);
	public static void playOneGame() throws IOException{
        playerIsAlive=new AtomicBoolean(true);
        won=new AtomicBoolean(false);
      new Thread(new Game()).start();
        GameFrame g = new GameFrame();
        while(playerIsAlive.get()) {
            g.repaint();
        }
        if(won.get()){
          popUp("You win!");
        }
        else
        {
            popUp("You lose");
        }
								HighScore.manageScore(Enemy.score);
								GamePanel.p=null;
								GamePanel.items.set(new ArrayList<>());
								GamePanel.wells.set(new ArrayList<>());
    g.setVisible(false);
    TitleFrame.playing.set(false);
	}
public static void popUp(String prompt){
    JOptionPane.showMessageDialog(null, prompt);
}
}
