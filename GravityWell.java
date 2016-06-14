package orbital;

/**
 * Simple structure for holding the location of a gravity well
 * @author rohan
 */
public class GravityWell {
/**
	* acceleration due to gravity per tick at a radius of 1 pixel
	*/
	public double g;
	/**
		* X coordinate of the source 
		*/
	public double x;
	/**
		* Y coordinate
		*/
	public double y;

	public GravityWell(int inX, int inY, double inG) {
		x = inX;
		y = inY;
		g = inG;
	}
}
