package orbital;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;
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

	final static int screenheight;
	/**
	 * The user
	 */
	public static Player p;
	static final int screenlength;

	/**
	 * The following static block is used courtesy of stack overflow creative
	 * commons liscence
	 * http://stackoverflow.com/questions/3680221/how-can-i-get-the-monitor-size-in-java
	 */
	static {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		double width = screenSize.getWidth();
		double height = screenSize.getHeight();
		screenheight = (int) height + 2;
//screenheight=768;		
screenlength = (int) width + 2;
	//screenlength=1024;
	}
	/**
	 * Coordinate of the midpoint of the axis
	 */
	final static int middle = screenlength / 2;
	/**
	 * Y coordinate of the horizon
	 */
	final static int horizon = screenheight / 2;
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
	public static boolean sandboxModeEnabled;

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
	 * The following double affects how much deviation the orbits follow from a
	 * true circle. For perfect circles use 0 and for realistic elipses use .1
	 *
	 */
	public static final double eccentricity = .1;

	/**
	 * Creates a new GamePanel as well as spawn the starting items
	 *
	 * @param sandbox whether or not the user desires to play in a sandbox
	 * environment
	 * @throws IOException
	 */
	public GamePanel(boolean sandbox) throws IOException {
		sandboxModeEnabled = sandbox;
		items.set(new ArrayList<>());
		wells.set(new ArrayList<>());
		items.get().add(new Circle(middle, horizon, 60));
		wells.get().add(new GravityWell(middle, horizon, 60 * 60));
		p = new Player(middle, horizon - 80);
		p.turretAngle = Math.PI / 2;
		if (sandboxModeEnabled) {
			p.safeLandingsEnforced = true;
			int planets = (int) (Math.random() * 4) + 1;
			for (int i = 0; i < planets; i++) {
				double theta = Math.random() * 2 * Math.PI;
				//creates a radius around the planet to spawn satelites
				double r = 200 + Math.random() * (((screenheight < screenlength) ? screenheight - 800 : screenlength - 800) / 2);
				//^^ allows other planets to have their own satelites if necessary
				double speed = Math.sqrt(wells.get().get(0).g / r / 3) + eccentricity;

				items.get().add(new MovingPlanet(middle + Math.cos(theta) * r,
												horizon + Math.sin(theta) * r, (Math.random() * 2 < 1) ? 25 : 18,
												speed * Math.cos(theta - Math.PI / 2),
												speed * Math.sin(theta - Math.PI / 2)));
				//It creates a moving planet and sends around in orbit of the main planet 
				//This planet is either big or small and is at a random angle and distance
			}
			int marsX=-1;
			int marsY=-1;
switch((int)(4*Math.random())){
	case 0:
		marsX=middle*3/2;
		marsY=horizon*3/2;
		break;
	case 1:
				marsX=middle*3/2;
		marsY=horizon*1/2;
		break;
	case 2:
			marsX=middle*1/2;
		marsY=horizon*3/2;
		break;
	case 3:
				marsX=middle*1/2;
		marsY=horizon*1/2;
		break;
	default:
		
		//impossible case
System.err.println("Something\"impossible\" occured");
		System.exit(2);
}
items.get().add(new Circle(marsX,marsY,32));
wells.get().add(new GravityWell(marsX,marsY,32*32));
		int martianMoons=(int) (Math.random()*3+1);
						int marsIndex=wells.get().size()-1;
		for(int i=0;i<martianMoons;i++){
							double theta = Math.random() * 2 * Math.PI;
		
				double r = 100+Math.random()*50;
		

			
				double speed = Math.sqrt(wells.get().get(marsIndex).g / r / 3)
												+ eccentricity;
						
				items.get().add(new MovingPlanet(marsX + Math.cos(theta) * r,
												marsY + Math.sin(theta) * r, (Math.random() * 2 < 1) ? 25 : 18,
												speed * Math.cos(theta + Math.PI / 2),
												speed * Math.sin(theta + Math.PI / 2),marsIndex));
		}
		} else {
			p.safeLandingsEnforced = false;
			items.get().add(new MovingPlanet(middle, horizon - 300, 25, 2, 0));
		}
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
		if (!sandboxModeEnabled) {
			g.drawString("Planet: " + Enemy.planetsHealth * 10, screenlength - 80, 10);

			g.drawString("Score: " + Enemy.score * 100, screenlength / 2 - 37, 10);
		}
	}

}
