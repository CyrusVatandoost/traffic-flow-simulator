/**
 * This method sets the condition of a Road.
 */
public class RoadTile {
	private int x;
	private int y;
	private int condition;
	private int time1;
	private int time2;
	
	private boolean occupied;
	private boolean free;
	
	/*
	 * conditions
	 * 0 - normal - 100% speed rate
	 * 1 - damaged - 50% speed rate
	 * 2 - flooded - 50% speed rate
	 * 3 - closed - 0% speed rate
	 */
	
	/**
	 * Constructor method for RoadTile class.
	 * @param x x coordinate of the road
	 * @param y y coordinate of the road
	 * @param condition chosen condition of the road
	 */
	public RoadTile(int x, int y, int condition) {
		this.x = x;
		this.y = y;
		this.condition = condition;
		time1 = 0;
		time2 = 0;
		free = true;
		occupied = false;
	}
	
	/**
	 * This method updates the settings of the road.
	 */
	public void update() {
		if(time1 > 99 && condition == 2) {
			condition = 0;
			time1 = 0;
		}
		time1 = time1 + 1;
		
		if(occupied) {
			if(time2 > 0) {
				free = true;
			}
			time2 = time2 + 1;
		}
		
		if(condition == 3) {
			free = false;
			occupied = true;
		}
	}
	
	/**
	 * This method makes a road unpassable.
	 */
	public void occupy() {
		free = false;
		occupied = true;
		time2 = 0;
	}
	
	/**
	 * This method makes a road passable.
	 */
	public void unOccupy() {
		free = true;
		occupied = false;
	}
	
	/**
	 * This method sets the condition of a road.
	 * @param condition integer that represents a condition (e.g. Broken, Flooded, Blocked)
	 */
	public void setCondition(int condition) {
		this.condition = condition;
	}
	
	/**
	 * This method returns the current setting of the road.
	 * @return current setting of the road
	 */
	public boolean isFree() {
		return free;
	}
	
	/**
	 * This method returns the current setting of the road.
	 * @return current setting of the road
	 */
	public boolean isOccupied() {
		return occupied;
	}
	
	/**
	 * This method returns the current condition of the road.
	 * @return current condition of the road
	 */
	public int getCondition() {
		return condition;
	}
	
	/**
	 * This method returns the x coordinate of the road.
	 * @return x coordinate of the road
	 */
	public int getX() {
		return x;
	}
	
	/**
	 * This method returns the y coordinate of the road.
	 * @return y coordinate of the road
	 */
	public int getY() {
		return y;
	}
}
