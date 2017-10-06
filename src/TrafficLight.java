/**
 * This class creates a Traffic Light.
 */
public class TrafficLight {
	private int x;
	private int y;
	private int time;
	private int rate;
	private int start;
	private boolean go;
	
	/**
	 * Constructor method for TrafficLight class
	 * @param x - x coordinate of traffic light block
	 * @param y - y coordinate of traffic light block
	 * @param rate - rate at which the color of the traffic light changes
	 * @param start - start time after light change
	 */
	public TrafficLight(int x, int y, int rate, int start) {
		this.x = x;
		this.y = y;
		this.rate = rate;
		time = 0;
		
		if(start > 0)
			go = false;
		else
			go = true;
		
		this.start = start;
	}
	
	/**
	 * This method updates the color of the traffic light depending on the rate
	 */
	public void update() {
		if(time > 450) {
			changeLight();
			time = 0;
		}
		time += rate;
	}
	
	/**
	 * This method changes the color of the traffic light
	 */
	public void changeLight() {
		if(go) {
			go = false;
		}
		else {
			go = true;
		}
	}
	
	/**
	 * This method returns traffic light permissions
	 * @return traffic light permissions
	 */
	public boolean getGo() {
		return go;
	}
	
	/**
	 * This method returns the x coordinate of the traffic light block
	 * @return x coordinate of traffic light block
	 */
	public int getX() {
		return x;
	}
	
	/**
	 * This method returns the y coordinate of the traffic light block
	 * @return y coordinate of traffic light block
	 */
	public int getY() {
		return y;
	}
	
	/**
	 * This method returns the rate at which the color of the traffic light changes
	 * @return rate at which the color of the traffic light changes
	 */
	public int getRate() {
		return rate;
	}
	
	/**
	 * This method returns the start time after a light changes
	 * @return start time after light changes
	 */
	public int getStart() {
		return start;
	}
}
