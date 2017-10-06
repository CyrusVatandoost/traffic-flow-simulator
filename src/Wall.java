/**
 * This class creates a Wall.
 */
public class Wall {
	private int x;
	private int y;
	
	/**
	 * Constructor method for class Wall
	 * @param x - x coordinate of the wall block
	 * @param y - y coordinate of the wall block
	 */
	public Wall(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * This method returns the x coordinate of the wall
	 * @return x coordinate of the wall
	 */
	public int getX() {
		return x;
	}
	
	/**
	 * This method returns the y coordinate of the wall
	 * @return y coordinate of the wall
	 */
	public int getY() {
		return y;
	}
}
