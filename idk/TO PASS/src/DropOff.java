/**
 * These are tiles of the map where vehicles can stop for a specified time duration.
 * The time is assigned as the drop-off time points is placed on the road map.
 * 
 * @author user
 *
 */
public class DropOff {
	private int x;
	private int y;
	private int limit;
	private int time;
	private boolean occupied;
	private boolean free;
	
	/**
	 * This method sets a Drop Off block.
	 * @param x x coordinate of the block
	 * @param y y coordinate of the block
	 * @param limit time the vehicle has to drop off
	 */
	public DropOff(int x, int y, int limit) {
		this.x = x;
		this.y = y;
		this.limit = limit;
		time = 0;
		free = true;
		occupied = false;
	}
	
	/**
	 * This method updates the drop off point synchronized to the simulation time.
	 */
	public void update() {
		if(time >= limit && !free) {
			free = true;
			time = 0;
		}
		time++;
	}
	
	/**
	 * This method checks if the drop off point block has a car standing on top of it.
	 */
	public void occupy() {
		free = false;
		occupied = true;
	}
	
	/**
	 * This method moves the car on top off it after a period of time which is preset by us or set by the user.
	 */
	public void unOccupy() {
		occupied = false;
		free = true;
	}
	
	/**
	 * This method checks if the drop off point is free.
	 * @return the current condition of the block.
	 */
	public boolean isFree() {
		return free;
	}
	
	/**
	 * This method checks and returns if the block is free.
	 * @return the current condition of the block.
	 */
	public boolean isOccupied() {
		return occupied;
	}
	
	/**
	 * This method returns the x coordinate of the block.
	 * @return x coordinate of the block
	 */
	public int getX() {
		return x;
	}
	
	/**
	 * This method returns the y coordinate of the block.
	 * @return y coordinate of the block
	 */
	public int getY() {
		return y;
	}
	
	/**
	 * This returns the limit (or rate for dropping off) of the drop off.
	 * @return limit of the drop off
	 */
	public int getLimit() {
		return limit;
	}
}
