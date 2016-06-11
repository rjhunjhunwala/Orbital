package orbital;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author rohan
 */
public class Game implements Runnable{
	@Override
	public void run(){
		while(Orbital.playerIsAlive.get()){
			try {
				Thread.sleep(20);
			} catch (InterruptedException ex) {
				Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
			}
			if(GamePanel.p!=null) {
				GamePanel.p.step();
			}
			ArrayList<Circle> items=GamePanel.items.get();
			try{
				items.stream().filter((c) -> (c!=null&&c.alive.get())).filter((c) -> (c instanceof Movable)).forEach((c) -> {
					((Movable) c).step();
				});
			}catch(ConcurrentModificationException ex){
				//sorry
			}
		}
	}
}