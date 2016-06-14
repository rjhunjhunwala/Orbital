package orbital;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Rohans
 */
public class TitleController implements KeyListener {
	
	@Override
	public void keyTyped(KeyEvent e) {
		//throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		//throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		if (!TitleFrame.playing.get()) {
			switch (e.getKeyChar()) {
				case 'R':
				case 'r':
					TitleFrame.TitlePanel.screen.set(Screens.title);
					break;
				case 'i':
				case 'I':
					if (TitleFrame.TitlePanel.screen.get() == Screens.title) {
						TitleFrame.TitlePanel.screen.set(Screens.help);
					}
					break;				
				case 'p':
				case 'P':
					if (TitleFrame.TitlePanel.screen.get() == Screens.title) {
						TitleFrame.playing.set(true);
					}
					
					break;
				case 'h':
				case 'H':
					if (TitleFrame.TitlePanel.screen.get() == Screens.title) {
						TitleFrame.TitlePanel.screen.set(Screens.highscore);
					}
					
					break;
			}
		}
	}
	
}
