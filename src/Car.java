import java.awt.Color;
import java.util.concurrent.ThreadLocalRandom;

/**
 * This class creates a car.
 */
public class Car extends Vehicle{
	private int x;
	private int y;
	private int lastDirection;
	private boolean hasMoved;
	private boolean[] neighbor;	//This array holds the status of the neighbors of the car.
	private int[] road; //This array holds the directions of the surrounding roads.
	private boolean sideStepped;
	
	private int time;
	private int speed = chooseNum(1, 2, 3);
	private boolean canMove;
	
	/**
	 * Constructor method for Car class.
	 * @param x current x coordinate of the car
	 * @param y current y coordinate of the car
	 */
	public Car(int x, int y) {
		this.x = x;
		this.y = y;
		super.chooseColor();
		sideStepped = false;
		
		time = 0;
		canMove = true;
	}
	
	/**
	 * This method updates the values of the Car.
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
	 * This method sets the direction of the car based on the block.
	 * @param direction integer that represents the direction the car needs to move
	 */
	public void move(int direction) {
		update();
		if(canMove) {
			canMove = false;
			
			switch(direction) {
			
			//If the direction is up, move up.
			case 0 :
				if(road[0] != 1 && neighbor[0] == false) {
					y--;
					lastDirection = 0;
					sideStepped = false;
				}
				else if(road[2] == 0 && !neighbor[2] && !sideStepped) {
					x--;
					lastDirection = 0;
					sideStepped = true;
				}
				else if(road[3] == 0 && !neighbor[3] && !sideStepped) {
					x++;
					lastDirection = 0;
					sideStepped = true;
				}
				break;
			
			//If the direction is down, move down.
			case 1:
				if(road[1] != 0 && neighbor[1] == false) {
					y++;
					lastDirection = 1;
					sideStepped = false;
				}
				else if(road[2] == 1 && !neighbor[2] && !sideStepped) {
					x--;
					lastDirection = 1;
					sideStepped = true;
				}
				else if(road[3] == 1 && !neighbor[3] && !sideStepped) {
					x++;
					lastDirection = 1;
					sideStepped = true;
				}
				break;
			
			//if the direction is left, move left.
			case 2:
				if(road[2] != 3 && neighbor[2] == false) {
					x--;
					lastDirection = 2;
					sideStepped = false;
				}
				else if(road[0] == 2 && !neighbor[0] && !sideStepped) {
					y--;
					lastDirection = 2;
					sideStepped = true;
				}
				else if(road[1] == 2 && !neighbor[1] && !sideStepped) {
					y++;
					lastDirection = 2;
					sideStepped = true;
				}
				break;
				
			//If the direction is right, move right.
			case 3:
				if(road[3] != 2 && neighbor[3] == false) {
					x++;
					lastDirection = 3;
					sideStepped = false;
				}	
				else if(road[0] == 3 && !neighbor[0] && !sideStepped) {
					y--;
					lastDirection = 3;
					sideStepped = true;
				}
				else if(road[1] == 3 && !neighbor[1] && !sideStepped) {
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
	 * This method checks if the car had moved to the next block.
	 * @param moved settings to check if the car had moved
	 */
	public void setHasMoved(boolean moved) {
		hasMoved = moved;
	}
	
	/**
	 * This method checks adjacent blocks.
	 * @param neighbor nearby cars or buses
	 */
	public void setNeighbors(boolean[] neighbor) {
		this.neighbor = neighbor;
	}
	
	/**
	 * This method sets the path the car takes.
	 * @param road array that stores the path the car took
	 */
	public void setRoad(int[] road) {
		this.road = road;
	}
	
	/**
	 * This method returns the current x coordinate of the car.
	 * @return x coordinate of the car
	 */
	public int getX() {
		return x;
	}
	
	/**
	 * This method returns the current y coordinate of the car.
	 * @return y coordinate of the car
	 */
	public int getY() {
		return y;
	}
	
	/**
	 * Checks if the car had already moved
	 * @return settings if the car had already moved
	 */
	public boolean getHasMoved() {
		return hasMoved;
	}
	
	/**
	 * This method returns the color of the car
	 * @return color of the car
	 */
	public Color getColor() {
		return super.color;
	}
}
