package orbital;

import java.awt.Color;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * Orbital JFrame to house the game
 *
 */
public final class GameFrame extends JFrame {

	public JPanel p;

	GameFrame() throws IOException {

		super("Defend your home!");

		p = new GamePanel();

		this.add(p);

		this.pack();

		this.setBackground(new Color(255, 255, 255));

		this.setVisible(true);

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.addKeyListener(new Controller());
		this.setLocationRelativeTo(null);
	}
}
