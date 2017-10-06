/**
 * This class creates a spawner.
 */
public class Spawner {
	private int x;
	private int y;
	private int rate;
	private int time;
	private boolean spawn;
	
	/**
	 * Constructor method for Spawner class
	 * @param x - x coordinate of spawner
	 * @param y - y cooridnate of spawner
	 * @param rate - rate at which a car spawns
	 */
	public Spawner(int x, int y, int rate) {
		this.x = x;
		this.y = y;
		this.rate = rate;
		time = 0;
		spawn = false;
	}
	
	/**
	 * This method spawns a car after a certain time
	 */
	public void update() {
		if(time > 99) {
			spawn = true;
			time = 0;
		}
		time += rate;
	}
	
	/**
	 * This method identifies a block as a spawner
	 * @param spawn - identifies a certain block as spawner
	 */
	public void setSpawn(boolean spawn) {
		this.spawn = spawn;
	}
	
	/**
	 * This method returns the x coordinate of a spawner
	 * @return x coordinate of the spawner
	 */
	public int getX() {
		return x;
	}
	
	/**
	 * This method returns the y coordinate of the spawner
	 * @return y coordinate of the spawner
	 */
	public int getY() {
		return y;
	}
	
	/**
	 * This method returns the position of the spawner
	 * @return the position of the spawner
	 */
	public boolean getSpawn() {
		return spawn;
	}
	
	/**
	 * This method returns the rate at which cars spawn
	 * @return rate at which cars spawn
	 */
	public int getRate() {
		return rate;
	}
}
