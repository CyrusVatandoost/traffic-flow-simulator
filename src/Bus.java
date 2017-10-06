import java.awt.Color;
import java.util.concurrent.ThreadLocalRandom;

/**
 * This class creates a bus.
 */
public class Bus extends Vehicle {
	private int x;
	private int y;
	
	private int x2;
	private int y2;
	
	private int lastDirection;
	private boolean hasMoved;
	private boolean[] neighbor;	//This array holds the status of the neighbors of the car.
	private int[] road; //This array holds the directions of the surrounding roads.
	
	private int time;
	private int speed = chooseNum(1, 2, 3);
	private boolean canMove;
	private boolean sideStepped;
	
	/**
	 * Constructor method for class Bus.
	 * @param x x coordinate of front part of bus
	 * @param y y coordinate of front part of bus
	 */
	public Bus(int x, int y) {
		this.x = x;
		this.y = y;
		x2 = x;
		y2 = y;
		super.chooseColor();
		sideStepped = false;
	}
	
	/**
	 * This methods updates the values of the Bus.
	 */
	public void update() {
		if(!canMove) {
			if(time > speed) {
				canMove = true;
				time = 0;
			}
			time++;
		}
	}

	/** 	
	 * This method gives the bus the direction it should go.
	 * @param direction integer that represents direction
	 */
	public void move(int direction) {
		update();
		if(canMove) {
			canMove = false;
			
			switch(direction) {
			
			//If the direction is up, move up.
			case 0 :
				if(road[0] != 1 && neighbor[0] == false) {
					x2 = x;
					y2 = y;
					y--;
					lastDirection = 0;
					sideStepped = false;
				}
				else if(road[2] == 0 && !neighbor[2] && !sideStepped && x2 != x - 1) {
					x2 = x;
					y2 = y;
					x--;
					lastDirection = 0;
					sideStepped = true;
				}
				else if(road[3] == 0 && !neighbor[3] && !sideStepped && x2 != x + 1) {
					x2 = x;
					y2 = y;
					x++;
					lastDirection = 0;
					sideStepped = true;
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
				else if(road[2] == 1 && !neighbor[2] && !sideStepped && x2 != x - 1) {
					x2 = x;
					y2 = y;
					x--;
					lastDirection = 1;
					sideStepped = true;
				}
				else if(road[3] == 1 && !neighbor[3] && !sideStepped && x2 != x + 1) {
					x2 = x;
					y2 = y;
					x++;
					lastDirection = 1;
					sideStepped = true;
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
				else if(road[0] == 2 && !neighbor[0] && !sideStepped && y2 != y - 1) {
					x2 = x;
					y2 = y;
					y--;
					lastDirection = 2;
					sideStepped = true;
				}
				else if(road[1] == 2 && !neighbor[1] && !sideStepped && y2 != y - 1) {
					x2 = x;
					y2 = y;
					y++;
					lastDirection = 2;
					sideStepped = true;
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
				else if(road[0] == 3 && !neighbor[0] && !sideStepped && y2 != y - 1) {
					x2 = x;
					y2 = y;
					y--;
					lastDirection = 3;
					sideStepped = true;
				}
				else if(road[1] == 3 && !neighbor[1] && !sideStepped && y2 != y - 1) {
					x2 = x;
					y2 = y;
					y++;
					lastDirection = 3;
					sideStepped = true;
				}
				break;
				
			//If the car is on an intersection, have fun.
			case 4:
				canMove = true;
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
	}

	/**
	 * this method checks if the bus had moved to the next block
	 * @param moved settings to check if the bus had moved
	 */
	public void setHasMoved(boolean moved) {
		hasMoved = moved;
	}
	
	/**
	 * this method checks if the blocks before and after the bus have vehicles
	 * @param neighbor vehicle on the blocks adjacent to the bus
	 */
	public void setNeighbors(boolean[] neighbor) {
		this.neighbor = neighbor;
	}
	
	/**
	 * this method sets the path of the bus
	 * @param road array of the path the bus had took
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
