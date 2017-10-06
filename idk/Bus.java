import java.awt.Color;
import java.util.concurrent.ThreadLocalRandom;

public class Bus extends Vehicle {
	private int x;
	private int y;
	
	private int x2;
	private int y2;
	
	private int lastDirection;
	private boolean hasMoved;
	private boolean[] neighbor;	//This array holds the status of the neighbors of the car.
	private int[] road; //This array holds the directions of the surrounding roads.
	
	/**
	 * This is the constructor method for class Bus.
	 * @param x - x coordinate of front part of bus
	 * @param y - y coordinate of front part of bus
	 */
	public Bus(int x, int y) {
		this.x = x;
		this.y = y;
		x2 = x;
		y2 = y;
		super.chooseColor();
	}
	
	/**
	 * This method gives the bus the direction it should go.
	 * @param direction - integer that represents direction
	 */
	public void move(int direction) {
		switch(direction) {
		
		//If the direction is up, move up.
		case 0 :
			if(road[0] != 1 && neighbor[0] == false) {
				x2 = x;
				y2 = y;
				y--;
				lastDirection = 0;
			}
			break;
		
		//If the direction is down, move down.
		case 1:
			if(road[1] != 0 && neighbor[1] == false) {
				x2 = x;
				y2 = y;
				y++;
				lastDirection = 1;
			}
			break;
		
		//if the direction is left, move left.
		case 2:
			if(road[2] != 3 && neighbor[2] == false) {
				x2 = x;
				y2 = y;
				x--;
				lastDirection = 2;
			}
			break;
			
		//If the direction is right, move right.
		case 3:
			if(road[3] != 2 && neighbor[3] == false) {
				x2 = x;
				y2 = y;
				x++;
				lastDirection = 3;
			}
			break;
			
		//If the car is on an intersection, have fun.
		case 4:
			
			switch(lastDirection) {
				
			//If the car was from SOUTH, do this.
			case 0:
				if(road[0] != 1 && road[2] != 3 && road[3] != 2 && !neighbor[0] && !neighbor[2] && !neighbor[3])
					move(chooseNum(0, 2, 3));
				
				else if(road[0] != 1 && road[2] != 3 && !neighbor[0] && !neighbor[2])
					move(chooseNum(0, 2));
				else if(road[0] != 1 && road[3] != 2 && !neighbor[0] && !neighbor[3])
					move(chooseNum(0, 3));
				else if(road[2] != 3 && road[3] != 2 && !neighbor[2] && !neighbor[3])
					move(chooseNum(2, 3));

				else if(road[0] != 1 && (road[2] == 3 || neighbor[2]) && (road[3] == 2 || neighbor[3]) && !neighbor[0])
					move(0);
				else if((road[0] == 1 || neighbor[0]) && road[2] != 3 && (road[3] == 2 || neighbor[3]) && !neighbor[2])
					move(2);
				else if((road[0] == 1 || neighbor[0]) && (road[2] == 3 || neighbor[2]) && road[3] != 2 && !neighbor[3])
					move(3);
				
				else if(road[0] != 1 && !neighbor[0])
					move(0);
				else if(road[2] != 3 && !neighbor[2])
					move(2);
				else if(road[3] != 2 && !neighbor[3])
					move(3);
				
				break;
				
			//If the car was from NORTH, do this.
			case 1:
				if(road[1] != 0 && road[2] != 3 && road[3] != 2 && !neighbor[1] && !neighbor[2] && !neighbor[3])
					move(chooseNum(1, 2, 3));
				
				else if(road[1] != 0 && road[2] != 3 && !neighbor[1] && !neighbor[2])
					move(chooseNum(1, 2));
				else if(road[1] != 0 && road[3] != 2 && !neighbor[1] && !neighbor[3])
					move(chooseNum(1, 3));
				else if(road[2] != 3 && road[3] != 2 && !neighbor[2] && !neighbor[3])
					move(chooseNum(2, 3));
				
				else if((road[1] == 0 || !neighbor[1]) && road[2] != 3 && (road[3] == 2 || !neighbor[3]) && !neighbor[3])
					move(3);
				else if((road[1] == 0 || !neighbor[1]) && (road[2] == 3 || !neighbor[2]) && road[3] != 2 && !neighbor[2])
					move(2);
				else if(road[1] != 0 && (road[2] == 3 || !neighbor[2]) && (road[3] == 2 || !neighbor[3]) && !neighbor[1])
					move(1);
				
				else if(road[1] != 0 && !neighbor[1])
					move(1);
				else if(road[2] != 3 && !neighbor[2])
					move(2);
				else if(road[3] != 2 && !neighbor[3])
					move(3);
				
				break;
				
			//If the car was from EAST, do this.
			case 2:
				if(road[0] != 1 && road[1] != 0 && road[2] != 3 && !neighbor[0] && !neighbor[1] && !neighbor[2])
					move(chooseNum(0, 1, 2));
				
				else if(road[0] != 1 && road[1] != 0 && !neighbor[0] && !neighbor[1])
					move(chooseNum(0, 1));
				else if(road[0] != 1 && road[2] != 3 && !neighbor[0] && !neighbor[2])
					move(chooseNum(0, 2));
				else if(road[1] != 0 && road[2] != 3 && !neighbor[1] && !neighbor[2])
					move(chooseNum(1, 2));
				
				else if((road[0] == 1 || !neighbor[0]) && (road[1] == 0 || !neighbor[1]) && road[2] != 3 && !neighbor[2])
					move(2);
				else if((road[0] == 1 || !neighbor[0]) && road[1] != 0 && (road[2] == 3 || !neighbor[2]) && !neighbor[1])
					move(1);
				else if(road[0] != 1 && (road[1] == 0 || !neighbor[1]) && (road[2] == 3 || !neighbor[2]) && !neighbor[0])
					move(0);
				
				else if(road[0] != 1 && !neighbor[0])
					move(0);
				else if(road[1] != 0 && !neighbor[1])
					move(1);
				else if(road[2] != 3 && !neighbor[2])
					move(2);
				
				break;
				
			//If the car was from WEST, do this.
			case 3:
				if(road[0] != 1 && road[1] != 0 && road[3] != 2 && !neighbor[0] && !neighbor[1] && !neighbor[3])
					move(chooseNum(0, 1, 3));
				
				else if(road[0] != 1 && road[1] != 0 && !neighbor[0] && !neighbor[1])
					move(chooseNum(0, 1));
				else if(road[0] != 1 && road[3] != 2 && !neighbor[0] && !neighbor[3])
					move(chooseNum(0, 3));
				else if(road[1] != 0 && road[3] != 2 && !neighbor[1] && !neighbor[3])
					move(chooseNum(1, 3));
				
				else if((road[0] == 1 || !neighbor[0]) && (road[1] == 0 || !neighbor[1]) && road[3] != 2 && !neighbor[3])
					move(3);
				else if(road[0] != 1 && (road[1] == 0 || !neighbor[1]) && (road[3] == 2 || !neighbor[3]) && !neighbor[0])
					move(0);
				else if((road[0] == 1 || !neighbor[0]) && road[1] != 0 && (road[3] == 2 || !neighbor[3]) && !neighbor[1])
					move(1);
				
				else if(road[0] != 1 && !neighbor[0])
					move(0);
				else if(road[1] != 0 && !neighbor[1])
					move(1);
				else if(road[3] != 2 && !neighbor[3])
					move(3);
					
				break;
			}
			
			break;
		}
		
	}
	
	/**
	 * This method returns a random number.
	 * @param a - 1st number
	 * @param b - 2nd number
	 * @return - randomized number
	 */
	public int chooseNum(int a, int b) {
		int rand;
		
		rand = ThreadLocalRandom.current().nextInt(1, 3);
		
		if(rand == 1)
			return a;
		else if(rand == 2)
			return b;
		return a;
	}
	
	/**
	 * This method returns a random number.
	 * @param a - 1st number
	 * @param b - 2nd number
	 * @param c - 3rd number
	 * @return - randomized number
	 */
	public int chooseNum(int a, int b, int c) {
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

	/**
	 * this method checks if the bus had moved to the next block
	 * @param moved - settings to check if the bus had moved
	 */
	public void setHasMoved(boolean moved) {
		hasMoved = moved;
	}
	
	/**
	 * this method checks if the blocks before and after the bus have vehicles
	 * @param neighbor - vehicle on the blocks adjacent to the bus
	 */
	public void setNeighbors(boolean[] neighbor) {
		this.neighbor = neighbor;
	}
	
	/**
	 * this method sets the path of the bus
	 * @param road - array of the path the bus had took
	 */
	public void setRoad(int[] road) {
		this.road = road;
	}
	
	/**
	 * this method returns the current x coordinate of the front part of the bus
	 * @return x coordinate of the front part of the bus
	 */
	public int getX() {
		return x;
	}
	
	/**
	 * This method returns the current y coordinate of the front part of the bus
	 * @return y coordinate of the front part of the bus
	 */
	public int getY() {
		return y;
	}
	
	/**
	 * this method returns the x coordinate of the back part of the bus
	 * @return x coordinate of the back part of the bus
	 */
	public int getX2() {
		return x2;
	}
	
	/**
	 * this method returns the y coordinate of the back part of the bus
	 * @return y coordinate of the back part of the bus
	 */
	public int getY2() {
		return y2;
	}
	
	/**
	 * This method checks if the bus had moved to the next block
	 * @return if the bus had moved
	 */
	public boolean getHasMoved() {
		return hasMoved;
	}
	
	/**
	 * This method returns the color of the bus
	 * @return color of the bus
	 */
	public Color getColor() {
	return super.color;
}
}
