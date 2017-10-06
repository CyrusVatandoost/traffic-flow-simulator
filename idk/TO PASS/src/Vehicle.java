import java.awt.Color;
import java.util.concurrent.ThreadLocalRandom;
/**
 * This class has the settings for inheritance for Bus and Car.
 */
public class Vehicle {
	protected int x;
	protected int y;
	protected int lastDirection;
	protected boolean hasMoved;
	protected boolean[] neighbor;
	protected int[] road;
	protected Color color;

	/**
	 * Constructor method for Vehicle class.
	 */
	public Vehicle() {
		chooseColor();
	}

	/**
	 * This method chooses a color for the vehicle.
	 */
	protected void chooseColor() {
		int num;
		
		num = ThreadLocalRandom.current().nextInt(0, 6);
		
		Color PURPLE = new Color(153, 51, 255);
		Color DARKGREEN = new Color(0, 204, 102);
		
		switch(num) {
		case 0:
			color = Color.PINK;
			break;
		case 1:
			color = Color.ORANGE;
			break;
		case 2:
			color = DARKGREEN;
			break;
		case 3:
			color = PURPLE;
			break;
		case 4:
			color = Color.MAGENTA;
			break;
		case 5:
			color = Color.BLUE;
			break;
		}
	}
	
	/**
	 * This method randomized between two numbers that represent a color.
	 * @param a 1st number
	 * @param b 2nd number
	 * @return randomized number
	 */
	protected int chooseNum(int a, int b) {
		int rand;
		
		rand = ThreadLocalRandom.current().nextInt(1, 3);
		
		if(rand == 1)
			return a;
		else if(rand == 2)
			return b;
		return a;
	}
	
	/**
	 * This method randomizes between three numbers.
	 * @param a 1st number
	 * @param b 2nd number
	 * @param c 3rd number
	 * @return randomized number
	 */
	protected int chooseNum(int a, int b, int c) {
		int rand;
		
		rand = ThreadLocalRandom.current().nextInt(1, 4);
		
		if(rand == 1)
			return a;
		else if(rand == 2)
			return b;
		else if(rand == 3)
			return c;
		return a;
	}
}
