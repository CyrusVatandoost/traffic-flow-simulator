/**
 * This class creates a new road.
 */
public class Road {
	private int x;
	private int y;
	private int direction;
	
	/**
	 * Constructor method for Road class
	 * @param x - x coordinate of the road
	 * @param y - y coordinate of the road
	 * @param direction - direction the road goes
	 */
	public Road(int x, int y, int direction) {
		this.x = x;
		this.y = y;
		this.direction = direction;
	}

	/**
	 * This method returns the x coordinate of the road
	 * @return x coordinate of the road
	 */
	public int getX() {
		return x;
	}

	/**
	 * This method returns the y coordinate of the road
	 * @return y coordinate of the road
	 */
	public int getY() {
		return y;
	}

	/**
	 * This method returns the direction of the road
	 * @return direction of the road
	 */
	public int getDirection() {
		return direction;
	}
}
