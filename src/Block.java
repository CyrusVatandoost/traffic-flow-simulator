/**
 * This class creates a normal road block.
 */
public class Block {
	private TrafficLight t;
	private DropOff d;
	private RoadTile r;
	private boolean hasTrafficLight;
	private boolean hasDropOff;
	private boolean hasRoadTile;
	
	/**
	 * This method sets a normal block.
	 */
	public Block() {
		hasTrafficLight = false;
		hasDropOff = false;
		hasRoadTile = false;
	}
	
	/**
	 * This method sets a traffic light block.
	 * @param t retrieves traffic light settings from TrafficLight class
	 */
	public void setTrafficLight(TrafficLight t) {
		this.t = t;
		hasTrafficLight = true;
	}
	
	/**
	 * This method sets a drop off point block.
	 * @param d settings from DropOff class
	 */
	public void setDropOff(DropOff d) {
		this.d = d;
		hasDropOff = true;
	}
	
	/**
	 * This method sets up a RoadTile block.
	 * @param r settings from RoadTile class
	 */
	public void setRoadTile(RoadTile r) {
		this.r = r;
		hasRoadTile = true;
	}

	/**
	 * This method returns traffic light settings.
	 * @return traffic light settings
	 */
	public boolean getHasTrafficLight() {
		return hasTrafficLight;
	}
	
	/**
	 * This method returns drop off point settings.
	 * @return drop off point settings
	 */
	public boolean hasDropOff() {
		return hasDropOff;
	}
	
	/**
	 * This method returns road tile settings.
	 * @return road tile settings
	 */
	public boolean hasRoadTile() {
		return hasRoadTile;
	}
	
	/**
	 * This method returns a traffic light block.
	 * @return traffic light block
	 */
	public TrafficLight getTrafficLight() {
		return t;
	}
	
	/**
	 * This method returns a drop off point block.
	 * @return drop off point block
	 */
	public DropOff getDropOff() {
		return d;
	}
	
	/**
	 * This method returns a road tile block.
	 * @return road tile block
	 */
	public RoadTile getRoadTile() {
		return r;
	}
}
