import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import javax.swing.JFrame;

/**
 * This class sets the settings for the simulation.
 */
public class Simulation extends Thread {
	private int time;
	
	/*
	 *	0 - road direction down
	 *	1 - road direction up
	 *	2 - road direction left
	 *	3 - road direction right
	 *	4 - road intersection
	 *	5 - vehicle
	 *	6 - traffic light
	 *	7 - drop off
	 *	8 - spawner
	 *	9 - wall
	 *	10 - road tile
	 */

	private Map m = new Map();
	
	private boolean pause;
	private boolean opened;
	
	private int timeSpeed;
	
	private JFrame f;
	private Thread t;
	
	/**
	 * Constructor method for Simulation class
	 */
	public Simulation() {
		pause = false;
		opened = false;
		timeSpeed = 500;
	}
	
	/**
	 * This method sets the elements of the simulation
	 * @param width - width of the simulator
	 * @param height - height of the simulator
	 */
	public void setDisplay(int width, int height) {
		f = new JFrame("Traffic Flow Simulation");
		//f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setSize(width, height);
		f.setLocationRelativeTo(null);
		f.add(new Display(m));
		f.setVisible(true);
	}

	/**
	 * This method updates the simulation events based on the simulation clock
	 */
	public void updateSimulation() {
		int i, x, y;
		
		ArrayList <Car> carList = new ArrayList <Car> (m.getCarList());
		ArrayList <Bus> busList = new ArrayList <Bus> (m.getBusList());
		ArrayList <Road> roadList = new ArrayList <Road> (m.getRoadList());
		ArrayList <Wall> wallList = new ArrayList <Wall> (m.getWallList());
		ArrayList <DropOff> dropOffList = new ArrayList <DropOff> (m.getDropOffList());
		ArrayList <RoadTile> roadTileList = new ArrayList <RoadTile> (m.getRoadTileList());
		ArrayList <TrafficLight> trafficLightList = new ArrayList <TrafficLight> (m.getTrafficLightList());
		ArrayList <Spawner> spawnerList = new ArrayList <Spawner> (m.getSpawnerList());
		
		int width = m.getWidth();
		int height = m.getHeight();
		int objectMap[][] = m.getObjectMap();
		int map[][] = m.getNumMap();
		Block block[][] = m.getBlockMap();
		
		//This updates the road tiles.
		for(i = 0; i < roadTileList.size(); i++) {
			roadTileList.get(i).update();
		}
		
		//This updates the drop off points.
		for(i = 0; i < dropOffList.size(); i++) {
			dropOffList.get(i).update();
		}
		
		//This updates the traffic lights into the Block.
		for(i = 0; i < trafficLightList.size(); i++) {
			trafficLightList.get(i).update();
		}
		
		//This clears the object map.
		for(x = 0; x < width; x++) {
			for(y = 0; y < height; y++) {
				objectMap[x][y] = 0;
			}
		}
		
		//This adds walls into the object map.
		for(i = 0; i < wallList.size(); i++) {
			x = wallList.get(i).getX();
			y = wallList.get(i).getY();
			
			objectMap[x][y] = 5;
		}
		
		//This will reset all the cars.
		for(i = 0; i < carList.size(); i++) {
			carList.get(i).setHasMoved(false);
			x = carList.get(i).getX();
			y = carList.get(i).getY();
			
			objectMap[x][y] = 5;
		}
		
		for(i = 0; i < busList.size(); i++) {
			busList.get(i).setHasMoved(false);
			x = busList.get(i).getX();
			y = busList.get(i).getY();
			
			objectMap[x][y] = 5;
			
			x = busList.get(i).getX2();
			y = busList.get(i).getY2();
			
			objectMap[x][y] = 5;
		}
		
		boolean remove = false;
		
		//This is where the cars will move.
		for(i = 0; i < carList.size(); i++) {
			
			carList = m.getCarList();
			
			if(remove)
				i = 0;
			remove = false;
			
			carList.get(i).setNeighbors(checkNeighbors(carList.get(i)));
			carList.get(i).setRoad(checkRoads(carList.get(i)));
			
			x = carList.get(i).getX();
			y = carList.get(i).getY();
			
			if(!carList.get(i).getHasMoved()) {

				if(block[x][y].hasRoadTile() && block[x][y].getRoadTile().getCondition() == 3){
					carList.get(i).setHasMoved(true);
				}
				else {
					if(block[x][y].hasRoadTile() && (block[x][y].getRoadTile().getCondition() == 1 || block[x][y].getRoadTile().getCondition() == 2 )){
						if(block[x][y].getRoadTile().isOccupied()) {
							if(block[x][y].getRoadTile().isFree()) {
								carList.get(i).move(map[x][y]);
								block[x][y].getRoadTile().unOccupy();
							}
						}
						else if(block[x][y].getRoadTile().isFree() && !block[x][y].getRoadTile().isOccupied())
							block[x][y].getRoadTile().occupy();
						else
							carList.get(i).move(map[x][y]);
					}
					
					else if(block[x][y].hasDropOff()) {
						if(block[x][y].getDropOff().isOccupied()) {
							if(block[x][y].getDropOff().isFree()) {
								carList.get(i).move(map[x][y]);
								block[x][y].getDropOff().unOccupy();
							}
						}
						else if(block[x][y].getDropOff().isFree() && !block[x][y].getDropOff().isOccupied())
							block[x][y].getDropOff().occupy();
						else
							carList.get(i).move(map[x][y]);
					}
					
					else if(block[x][y].getHasTrafficLight()) {
						if(block[x][y].getTrafficLight().getGo())
							carList.get(i).move(map[x][y]);
					}
					else
						carList.get(i).move(map[x][y]);
					
					carList.get(i).setHasMoved(true);
					
					if(carList.get(i).getX() < 0 || carList.get(i).getX() > width - 1 || carList.get(i).getY() < 0 || carList.get(i).getY() > height - 1) {
						m.removeCar(i);
						
						remove = true;
					}
					else {
						x = carList.get(i).getX();
						y = carList.get(i).getY();
						
						objectMap[x][y] = 5;
					}
				}

			}
			else {
				x = carList.get(i).getX();
				y = carList.get(i).getY();
				
				objectMap[x][y] = 5;
				carList.get(i).setHasMoved(true);
			}
		}
		
		//This is where the buses will move.
		for(i = 0; i < busList.size(); i++) {
			
			busList = m.getBusList();
			
			if(remove)
				i = 0;
			remove = false;
			
			busList.get(i).setNeighbors(checkNeighbors(busList.get(i)));
			busList.get(i).setRoad(checkRoads(busList.get(i)));
			
			x = busList.get(i).getX();
			y = busList.get(i).getY();
			
			if(!busList.get(i).getHasMoved()) {
				
				if(block[x][y].hasRoadTile() && (block[x][y].getRoadTile().getCondition() == 1 || block[x][y].getRoadTile().getCondition() == 2)){
					if(block[x][y].getRoadTile().isOccupied()) {
						if(block[x][y].getRoadTile().isFree()) {
							busList.get(i).move(map[x][y]);
							block[x][y].getRoadTile().unOccupy();
						}
					}
					else if(block[x][y].getRoadTile().isFree() && !block[x][y].getRoadTile().isOccupied())
						block[x][y].getRoadTile().occupy();
					else
						busList.get(i).move(map[x][y]);
				}
				
				else if(block[x][y].hasDropOff()) {
					if(block[x][y].getDropOff().isOccupied()) {
						if(block[x][y].getDropOff().isFree()) {
							busList.get(i).move(map[x][y]);
							block[x][y].getDropOff().unOccupy();
						}
					}
					else if(block[x][y].getDropOff().isFree() && !block[x][y].getDropOff().isOccupied())
						block[x][y].getDropOff().occupy();
					else
						busList.get(i).move(map[x][y]);
				}
				
				else if(block[x][y].getHasTrafficLight()) {
					if(block[x][y].getTrafficLight().getGo())
						busList.get(i).move(map[x][y]);
				}
				else
					busList.get(i).move(map[x][y]);
				
				busList.get(i).setHasMoved(true);
				
				if(busList.get(i).getX() < 0 || busList.get(i).getX() > width - 1 ||busList.get(i).getY() < 0 || busList.get(i).getY() > height - 1) {
					m.removeBus(i);
					remove = true;
				}
				else {
					x = busList.get(i).getX();
					y = busList.get(i).getY();
					
					objectMap[x][y] = 5;
					
					x = busList.get(i).getX2();
					y = busList.get(i).getY2();
					
					objectMap[x][y] = 5;
				}
			}
			else {
				x = busList.get(i).getX();
				y = busList.get(i).getY();
				
				objectMap[x][y] = 5;
				
				x = busList.get(i).getX2();
				y = busList.get(i).getY2();
				
				objectMap[x][y] = 5;
			}
		}

		//This is where the spawners will spawn cars.
		for(i = 0; i < spawnerList.size(); i++) {
			x = spawnerList.get(i).getX();
			y = spawnerList.get(i).getY();
			spawnerList.get(i).update();
			
			if(spawnerList.get(i).getSpawn() && objectMap[x][y] != 5) {
				int num1;
				
				num1 = chooseNum(0, 1, 2);
				
				if(num1 == 0)
					m.addBus(x, y);
				else
					m.addCar(x, y);
				spawnerList.get(i).setSpawn(false);
			}
		}
		
		time++; //This adds 1 to the simulation clock.
		
		m.setNumMap(map);
		m.setObjectMap(objectMap);
		m.setBlockMap(block);
		/*
		m.setCarList(carList);
		m.setBusList(busList);
		m.setRoadList(roadList);
		m.setWallList(wallList);
		m.setTrafficList(trafficLightList);
		m.setDropOffList(dropOffList);
		m.setRoadTiles(roadTileList);
		m.setSpawnerList(spawnerList);
		*/
	}
	
	public void setVisible(boolean visibility) {
			f.setVisible(visibility);
	}
	
	public void toggleVisibility() {
		if(f.isVisible())
			f.setVisible(false);
		else
			f.setVisible(true);
	}
	
	/**
	 * This method randomizes between three numbers
	 * @param a - 1st number
	 * @param b - 2nd number
	 * @param c - 3rd number
	 * @return randomized number
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
	 * This method checks for adjacent cars
	 * @param c - car
	 * @return adjacent cars
	 */
	public boolean[] checkNeighbors(Car c) {
		int x, y;
		boolean[] neighbor = new boolean[4];
		int height = m.getHeight();
		int width = m.getWidth();
		int objectMap[][] = m.getObjectMap();
		
		x = c.getX();
		y = c.getY();
		
		neighbor[0] = false;
		neighbor[1] = false;
		neighbor[2] = false;
		neighbor[3] = false;
		
		if(y > 0) {
			if(objectMap[x][y-1] == 5)
				neighbor[0] = true;
		}
		
		if(y < height - 1) {
			if(objectMap[x][y+1] == 5)
				neighbor[1] = true;
		}
		
		if(x > 0) {
			if(objectMap[x-1][y] == 5)
				neighbor[2] = true;
		}
		
		if(x < width - 1) {
			if(objectMap[x+1][y] == 5)
				neighbor[3] = true;
		}
		return neighbor;
	}
	
	/**
	 * This method checks for adjacent buses
	 * @param c - bus
	 * @return adjacent buses
	 */
	public boolean[] checkNeighbors(Bus c) {
		int x, y;
		boolean[] neighbor = new boolean[4];
		int height = m.getHeight();
		int width = m.getWidth();
		int objectMap[][] = m.getObjectMap();
		
		x = c.getX();
		y = c.getY();
		
		neighbor[0] = false;
		neighbor[1] = false;
		neighbor[2] = false;
		neighbor[3] = false;
		
		if(y > 0) {
			if(objectMap[x][y-1] == 5)
				neighbor[0] = true;
		}
		
		if(y < height - 1) {
			if(objectMap[x][y+1] == 5)
				neighbor[1] = true;
		}
		
		if(x > 0) {
			if(objectMap[x-1][y] == 5)
				neighbor[2] = true;
		}
		
		if(x < width - 1) {
			if(objectMap[x+1][y] == 5)
				neighbor[3] = true;
		}
		return neighbor;
	}
	
	/**
	 * This method checks the road a car is in
	 * @param c - car
	 * @return the road the car is in
	 */
	public int[] checkRoads(Car c) {
		int x, y;
		int road[] = new int[4];
		int height = m.getHeight();
		int width = m.getWidth();
		int map[][] = m.getNumMap();
		
		x = c.getX();
		y = c.getY();
		
		road[0] = 0;
		road[1] = 1;
		road[2] = 2;
		road[3] = 3;
		
		if(y > 0)
			road[0] = map[x][y-1];
		
		if(y < height - 1)
			road[1] = map[x][y+1];
		
		if(x > 0)
			road[2] = map[x-1][y];
		
		if(x < width - 1)
			road[3] = map[x+1][y];
		
		return road;
	}
	
	/**
	 * This method checks the road the bus is in
	 * @param c bus
	 * @return road the bus is in
	 */
	public int[] checkRoads(Bus c) {
		int x, y;
		int road[] = new int[4];
		int height = m.getHeight();
		int width = m.getWidth();
		int map[][] = m.getNumMap();
		
		x = c.getX();
		y = c.getY();
		
		road[0] = 0;
		road[1] = 1;
		road[2] = 2;
		road[3] = 3;
		
		if(y > 0)
			road[0] = map[x][y-1];
		
		if(y < height - 1)
			road[1] = map[x][y+1];
		
		if(x > 0)
			road[2] = map[x-1][y];
		
		if(x < width - 1)
			road[3] = map[x+1][y];
		
		return road;
	}
	
	/**
	 * This method runs the simulation
	 */
	public void displaySimulation() {
		runSimulation();
		pause = true;
	}
	
	/**
	 * This method returns the greatest common factor of the two selected numbers.
	 * @param a number 1
	 * @param b number 2
	 * @return the greatest common factor of the two selected numbers
	 */
	public int gcf(int a, int b)
	{
	    while (a != b) // while the two numbers are not equal...
	    { 
	        // ...subtract the smaller one from the larger one

	        if (a > b) a -= b; // if a is larger than b, subtract b from a
	        else b -= a; // if b is larger than a, subtract a from b
	    }

	    return a; // or return b, a will be equal to b either way
	}

	/**
	 * This method returns the least common multiple of the two selected numbers.
	 * @param a the first number
	 * @param b the second number
	 * @return the least common multiple of the two selected numbers.
	 */
	public int lcm(int a, int b)
	{
	    // the lcm is simply (a * b) divided by the gcf of the two

	    return (a * b) / gcf(a, b);
	}
	
	/**
	 * This method launches the simulation
	 */
	public void runSimulation() {
		
		t = new Thread() {
			
			public void run() {

				if(opened == false) {
					try {
						m.readMap();
					} catch (URISyntaxException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					int lcd;
					int windowWidth;
					int windowHeight;
					int num1;
					int num2 = 750;
					int height = m.getHeight();
					int width = m.getWidth();
					
					lcd = lcm(width, height);
					
					windowWidth  = lcd / width;
					windowHeight = lcd / height;
					
					if(windowWidth > windowHeight) {
						num1 = num2 / windowWidth;
						windowWidth = windowHeight * num1;
						windowHeight = num2;
					}
					else {	
						num1 = num2 / windowHeight;
						windowHeight = windowWidth * num1;
						windowWidth = num2;
					}
					
					setDisplay(windowWidth, windowHeight);
					opened = true;
				}
				
				while(pause == false) {
						try {
							Thread.sleep(timeSpeed);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						updateSimulation();
				}
			}
		};
		
		t.start();
	}
	
	/**
	 * This method lets the user pause and resume the simulation
	 */
	public void togglePausePlay() {
		if(pause == false) 
			pause = true;
		else {
			pause = false;
			runSimulation();
		}
	}
	
	/**
	 * This method lets the pause the simulation and proceed to the next frame
	 */
	public void nextStep() {
		if(pause) {
			updateSimulation();
		}
	}
	
	/**
	 * This method stops the simulation
	 */
	public void stopSimulation() {
		if(t.isAlive()) {
			t.interrupt();
			t.suspend();
			t = null;
		}
		f.dispose();
		opened = false;
		m.delete();
	}
	
	/**
	 * This method sets the speed to update the simulation
	 * @param time - speed to update
	 */
	public void setTimeSpeed(int time) {
		timeSpeed = time;
	}
	
	/**
	 * This sets the name of the map used in the simulation
	 * @param mapName - name of the map
	 */
	public void setMapName(String mapName) {
		m.setMapName(mapName);
	}
	
	/**
	 * This method sets the rate at which a car stays in a road block before moving
	 * @param miliseconds - rate at which the car moves
	 */
	public void wait(int miliseconds) {
		//1000 milliseconds is equal to one second.
		try {
			Thread.sleep(miliseconds);
		} catch(InterruptedException ex) {
			Thread.currentThread().interrupt();
		}
	}
	
	/**
	 * This method checks whether or not the selected map has values in it or not.
	 * @param mapName the name of the text file to check
	 * @return the boolean value of whether or not there is text in that text file or not
	 * @throws FileNotFoundException
	 */
	public boolean isEmpty(String mapName) throws FileNotFoundException {
		Reader r = new Reader();
		return r.isEmpty(mapName);
	}
	
	/**
	 * This method returns the Map.
	 * @return the Map
	 */
	public Map getMap() {
		return m;
	}
	
	/**
	 * This method returns the current status of the simulation
	 * @return current status of the simulation
	 */
	public boolean getPause() {
		return pause;
	}
	
	/**
	 * This method lists all the positions of all the cars present in the simulation
	 * @return cars present in the simulation
	 */
	public ArrayList <Car> getCarList() {
		return m.getCarList();
	}
	
	/**
	 * This method returns all the bus present in the simulation
	 * @return buses present in the simulation
	 */
	public ArrayList <Bus> getBusList() {
		return m.getBusList();
	}
	
	/**
	 * This method returns the position of all the walls in the simulation
	 * @return the walls in the simulation
	 */
	public ArrayList <Wall> getWallList() {
		return m.getWallList();
	}
	
	/**
 * This method returns the positions of all the Spawner in the simulation
	 * @return the spawners in the simulations
	 */
	public ArrayList <Spawner> getSpawnerList() {
		return m.getSpawnerList();
	}
	
	/**
	 * This method returns the positions of all the Roads in the simulations
	 * @return the roads in the simulation
	 */
	public ArrayList <Road> getRoadList() {
		return m.getRoadList();
	}
	
	/**
	 * This method returns the positions of all the Drop Off blocks in the simulation
	 * @return the Drop Off blocks in the simulation
	 */
	public ArrayList <DropOff> getDropOffList() {
		return m.getDropOffList();
	}
	
	/**
	 * This method returns the positions of all the Traffic Light Blocks in the simulation
	 * @return the Traffic Light blocks in the simulation 
	 */
	public ArrayList <TrafficLight> getTrafficLightList() {
		return m.getTrafficLightList();
	}
	
	/**
	 * This method gets all the Road Tiles.
	 * @return the ArrayList of all the Road Tiles.
	 */
	public ArrayList <RoadTile> getRoadTileList() {
		return m.getRoadTileList();
	}
}
