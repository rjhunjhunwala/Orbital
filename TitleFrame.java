package orbital;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Rohans
 */
public class TitleFrame extends JFrame {
public static boolean sandboxModeEnabled;
	public static AtomicBoolean playing = new AtomicBoolean(false);
	private static final long serialVersionUID = 1L;

	public static TitleFrame t;

	public static void main(String[] args) throws Exception {

		t = new TitleFrame();
		while (true) {
			while (!playing.get()) {
				t.repaint();
			}
						t.setVisible(false);
			Orbital.playOneGame(sandboxModeEnabled);

			while (playing.get()) {
				Thread.sleep(10);
			}
			t.setVisible(true);
		}
	}

	public TitleFrame() {
		super("Orbital");

		TitlePanel p = new TitlePanel();

		this.add(p);

		this.pack();

		this.setVisible(true);

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.addKeyListener(new TitleController());
		this.setLocationRelativeTo(null);
		this.repaint();
	}

	public static class TitlePanel extends JPanel {
/**
	* Shows what screen is currently showing
	*/
		public static AtomicReference<Screens> screen = new AtomicReference<>(Screens.title);

		public static BufferedImage titleScreenImage;
		private static final long serialVersionUID = 1L;
		public static BufferedImage helpScreenImage;
		public static BufferedImage highScoreBackground;
		/**
		 * These dots are to be used to separate the names from the scores in the high
		 * score table.
		 */
		private static final String dots = "........................................."
										+ "...............................";
		
		public static final Font big = new Font("Monospaced", 24, 24);

		static {
			try {
				highScoreBackground = ImageIO.read(new File("venus.jpg"));
			} catch (IOException ex) {
				Logger.getLogger(TitleFrame.class.getName()).log(Level.SEVERE, null, ex);
			}
		}

		static {
			try {
				titleScreenImage = ImageIO.read(new File("TitleScreen.gif"));
			} catch (IOException ex) {
				Logger.getLogger(TitleFrame.class.getName()).log(Level.SEVERE, null, ex);
			}
		}

		static {
			try {
				helpScreenImage = ImageIO.read(new File("howToPlay.gif"));

			} catch (IOException ex) {
				Logger.getLogger(TitleFrame.class.getName()).log(Level.SEVERE, null, ex);
			}
		}

		public TitlePanel() {
			TitlePanel.screen.set(Screens.title);
			this.setBackground(Color.black);
		}

		@Override

		public Dimension getPreferredSize() {
			return new Dimension(768, 768);
		}
		public static final Color green = new Color(0, 255, 0);

		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);

			switch (screen.get()) {
				case title:
					g.drawImage(titleScreenImage, 0, 0, null);
					break;
				case help:
					g.drawImage(helpScreenImage, 0, 0, null);
					break;
				case highscore:
					g.drawImage(highScoreBackground, 0, 0, null);
					g.setColor(Color.cyan);
					g.setFont(big);
					g.drawString("High Scores", 768 / 2 - 70, 50);
					int i = 60;
					g.setFont(GamePanel.monospacedNormal);
					for (HighScore s : HighScore.scores) {
						g.drawString(s.name + dots.substring(s.name.length()), 115, 100 + i);
						g.drawString(s.score + "", 620, 100 + i);
						i += 50;

					}
					g.setFont(GamePanel.monospacedNormal);
					g.drawString("Press \"R\" to return to the home screen", 0, 768 - 12);
			}
		}
	}

}
