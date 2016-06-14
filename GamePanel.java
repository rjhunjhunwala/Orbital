package orbital;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

public final class GamePanel extends JPanel {

	final static int screenheight = 768;
	/**
	 * Y coordinate of the horizon
	 */
	final static int horizon = screenheight / 2;
	/**
	 * The user
	 */
	public static Player p;
	static final int screenlength = screenheight;
	/**
	 * Coordinate of the midpoint of the axis
	 */
	final static int middle = screenlength / 2;
	/**
	 * The on-screen items
	 */
	public static final AtomicReference<ArrayList<Circle>> items = new AtomicReference<>();
	/**
	 * The spots emitting gravity
	 */
	public static final AtomicReference<ArrayList<GravityWell>> wells = new AtomicReference<>();

	public static final Font monospacedNormal = new Font("Monospaced", 18, 12);
	public static final Color orbitalBlue = new Color(153, 217, 234);

	/**
	 * Checks to ensure the player is not touching any other planets
	 *
	 * @return
	 */
	public static boolean badSpot() {
		for (Circle c : items.get()) {
			if (c.isTouching(p)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Creates a new GamePanel as well as spawn the starting items
	 *
	 * @throws IOException
	 */
	public GamePanel() throws IOException {
		items.set(new ArrayList<>());
		wells.set(new ArrayList<>());
		int planets = (int) (Math.random() * 10 + Math.random() * 10);
//					for(int i=0;i<planets;i++){
//						int x,y,g;
//						items.get().add(new Circle(x=(int) (Math.random()*screenlength),y=(int) (Math.random()*screenheight),g=(int) (Math.random()*14)+10));
//					wells.get().add(new GravityWell(x,y,g*g));
//					}
		items.get().add(new Circle(middle, horizon, 60));
		wells.get().add(new GravityWell(middle, horizon, 60 * 60));
		items.get().add(new MovingPlanet(middle, horizon - 300, 25, 2, 0));
		p = new Player(middle / 2, horizon / 2);
		p.turretAngle = Math.PI / 4;
		this.setBackground(Color.BLACK);
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(screenlength, screenheight);
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		try {
			items.get().stream().filter((c) -> (c != null)).forEach((Circle c) -> {
				if (c.alive.get()) {
					c.drawSelf(g);
				}
			});
		} catch (ConcurrentModificationException ex) {
	//oops!
		}
		p.drawSelf(g);
		g.setColor(orbitalBlue);
		g.setFont(monospacedNormal);
		g.drawString("Shields: " + Enemy.shipHealth * 10, 0, 10);
		g.drawString("Planet: " + Enemy.planetsHealth * 10, screenlength - 80, 10);
		g.drawString("Score: " + Enemy.score * 100, screenlength / 2 - 35, 10);
	}

}
