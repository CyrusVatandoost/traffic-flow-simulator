import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

/**
 * This class retrieves the settings and components of a map. 
 */
public class Map {
	private String mapName;
	private boolean hasMap;
	private int width;
	private int height;
	private int[][] numMap;
	private int[][] objectMap;
	private Block[][] blockMap;
	private ArrayList <Car> carList = new ArrayList <Car> ();
	private ArrayList <Bus> busList = new ArrayList <Bus> ();
	private ArrayList <Wall> wallList = new ArrayList <Wall> ();
	private ArrayList <Road> roadList = new ArrayList <Road> ();
	private ArrayList <Spawner> spawnerList = new ArrayList <Spawner> ();
	private ArrayList <DropOff> dropOffList = new ArrayList <DropOff> ();
	private ArrayList <TrafficLight> trafficLightList = new ArrayList <TrafficLight> ();
	private ArrayList <RoadTile> roadTileList = new ArrayList <RoadTile> ();
	
	/**
	 * Constructor method for Map class.
	 */
	public Map() {
		mapName = null;
		hasMap = false;
	}
	
	/**
	 * This method reads a map.
	 * @throws URISyntaxException
	 */
	public void readMap() throws URISyntaxException {
		int i, x, y, direction;
		if(mapName != null) {
			Reader r = new Reader();
			
			try {
				r.read(mapName);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			width = r.getWidth();
			height = r.getHeight();
			
			//This gets the values of the different entities from the text file.
			wallList = new ArrayList <Wall> (r.getWallList());
			spawnerList = new ArrayList <Spawner> (r.getSpawnerList());
			roadList = new ArrayList <Road> (r.getRoadList());
			dropOffList = new ArrayList <DropOff> (r.getdropOffList());		
			trafficLightList = new ArrayList <TrafficLight> (r.getTrafficLightList());
			roadTileList = new ArrayList <RoadTile> (r.getRoadTileList());
			numMap = new int[width][height];
			objectMap = new int[width][height];
			blockMap = new Block[width][height];
			hasMap = true;
			
			//This clears the map.
			for(x = 0; x < width; x++) {
				for(y = 0; y < height; y++) {
					numMap[x][y] = 0;
					objectMap[x][y] = 0;
					blockMap[x][y] = new Block();
				}
			}
			
			//This adds walls into the map.
			for(i = 0; i < wallList.size(); i++) {
				x = wallList.get(i).getX();
				y = wallList.get(i).getY();
				
				numMap[x][y] = 9;
				objectMap[x][y] = 9;
			}
			
			//This adds roads into the map.
			for(i = 0; i < roadList.size(); i++) {
				x = roadList.get(i).getX();
				y = roadList.get(i).getY();
				direction = roadList.get(i).getDirection();
				
				numMap[x][y] = direction;
			}
			
			//This adds traffic lights into the map.
			for(i = 0; i < trafficLightList.size(); i++) {
				x = trafficLightList.get(i).getX();
				y = trafficLightList.get(i).getY();
				
				blockMap[x][y].setTrafficLight(trafficLightList.get(i));;
			}
			
			for(i = 0; i < dropOffList.size(); i++) {
				x = dropOffList.get(i).getX();
				y = dropOffList.get(i).getY();
				
				blockMap[x][y].setDropOff(dropOffList.get(i));
			}
			
			for(i = 0; i < roadTileList.size(); i++) {
				x = roadTileList.get(i).getX();
				y = roadTileList.get(i).getY();
				
				blockMap[x][y].setRoadTile(roadTileList.get(i));
			}
		}
		else {
			JOptionPane frame = new JOptionPane();
			JOptionPane.showMessageDialog(frame, "Error! No assigned map to be read.");
		}
	}
	
	/**
	 * This method deletes all the elements of the map.
	 */
	public void delete() {
		width = 0;
		height = 0;
		carList.clear();
		busList.clear();
		wallList.clear();
		roadList.clear();
		spawnerList.clear();
		dropOffList.clear();
		trafficLightList.clear();
		roadTileList.clear();
		hasMap = false;
	}
	
	/**
	 * This method clears the map.
	 */
	public void clear() {
		int x, y;

		wallList.clear();
		
		for(y = 0; y < height; y++) {
			for(x = 0; x < width; x++) {
				addWall(x, y);
			}
		}
		
		carList.clear();
		busList.clear();
		roadList.clear();
		spawnerList.clear();
		dropOffList.clear();
		trafficLightList.clear();
		roadTileList.clear();
	}
	
	/**
	 * This method converts the elements of the map into integers.
	 */
	public void convertToNumMap() {
		int i, x, y;
		
		for(y = 0; y < height; y++) {
			for(x = 0; x < width; x++) {
				numMap[x][y] = 9;
			}
		}
		
		for(i = 0; i < roadList.size(); i++) {
			x = roadList.get(i).getX();
			y = roadList.get(i).getY();
			
			numMap[x][y] = roadList.get(i).getDirection();
		}
	}
	
	/**
	 * This method retrieves elements using the integers stored in the map file.
	 */
	public void convertFromNumMap() {
		int x, y;
		
		for(x = 0; x < width; x++) {
			for(y = 0; y < height; y++) {
				if(numMap[x][y] == 9)
					addWall(x, y);
				else {
					addRoad(x, y, numMap[x][y]);
				}
			}
		}
	}
	
	/**
	 * This method opens a new map.
	 * @param width width of the map
	 * @param height height of the map
	 */
	public void newMap(int width, int height) {
		carList = new ArrayList <Car> ();
		busList = new ArrayList <Bus> ();
		wallList = new ArrayList <Wall> ();
		roadList = new ArrayList <Road> ();
		spawnerList = new ArrayList <Spawner> ();
		dropOffList = new ArrayList <DropOff> ();
		trafficLightList = new ArrayList <TrafficLight> ();
		roadTileList = new ArrayList <RoadTile> ();
		
		this.width = width;
		this.height = height;
		numMap = new int [width][height];
		hasMap = true;
		convertFromNumMap();
		clear();
	}
	
	/**
	 * This method adds a new car.
	 * @param x x coordinate of the car
	 * @param y y coordinate of the car
	 */
	public void addCar(int x, int y) {
		Car c = new Car(x, y);
		carList.add(c);
		c = null;
	}
	
	/**
	 * This method adds a new bus.
	 * @param x x coordinate of the bus
	 * @param y y coordinate of the bus
	 */
	public void addBus(int x, int y) {
		Bus b = new Bus(x, y);
		busList.add(b);
		b = null;
	}
	
	/**
	 * This method adds a new wall.
	 * @param x x coordinate of the wall
	 * @param y y coordinate of the wall
	 */
	public void addWall(int x, int y) {
		int i, x2, y2;
		if(x >= 0 && x < width && y >= 0 && y < height) {
			for(i = 0; i < wallList.size(); i++) {
				x2 = wallList.get(i).getX();
				y2 = wallList.get(i).getY();
				
				if(x == x2 && y == y2)
					wallList.remove(i);
			}
			Wall w = new Wall(x, y);
			for(i = 0; i < roadList.size(); i++) {
				x2 = roadList.get(i).getX();
				y2 = roadList.get(i).getY();
				
				if(x == x2 && y == y2)
					roadList.remove(i);
			}
			wallList.add(w);
			w = null;
		}
		else {
			JOptionPane frame = new JOptionPane();
			JOptionPane.showMessageDialog(frame, "Error! Invalid values dectected.");
			frame = null;
		}
	}
	
	/**
	 * This method adds a new road.
	 * @param x x coordinate of the road
	 * @param y y coordinate of the road
	 * @param direction direction pointed to by the road
	 */
	public void addRoad(int x, int y, int direction) {
		int i, x1, y1, x2, y2;
		
		if(x >= 0 && x < width && y >= 0 && y < height) {
			for(i = 0; i < roadList.size(); i++) {
				x2 = roadList.get(i).getX();
				y2 = roadList.get(i).getY();
				
				if(x == x2 && y == y2)
					roadList.remove(i);
			}
			
			Road r = new Road(x, y, direction);
			x1 = r.getX();
			y1 = r.getY();
			for(i = 0; i < wallList.size(); i++) {
				x2 = wallList.get(i).getX();
				y2 = wallList.get(i).getY();
				
				if(x1 == x2 && y1 == y2)
					wallList.remove(i);
			}
			roadList.add(r);
			r = null;
		}
		else {
			JOptionPane frame = new JOptionPane();
			JOptionPane.showMessageDialog(frame, "Error! Invalid values dectected.");
			frame = null;
		}
	}
	
	/**
	 * This method adds a new spawner.
	 * @param x x coordinate of the spawner
	 * @param y y coordinate of the spawner
	 * @param rate rate at which vehicles spawn
	 */
	public void addSpawner(int x, int y, int rate) {
		int i, x1, y1;
		if(x >= 0 && x < width && y >= 0 && y < height) {
				for(i = 0; i < spawnerList.size(); i++) {
				x1 = spawnerList.get(i).getX();
				y1 = spawnerList.get(i).getY();
				
				if(x == x1 && y == y1)
					spawnerList.remove(i);
			}
			Spawner s = new Spawner(x, y, rate);
			spawnerList.add(s);
			s = null;
		}
		else {
			JOptionPane frame = new JOptionPane();
			JOptionPane.showMessageDialog(frame, "Error! Invalid values dectected.");
			frame = null;
		}
	}
	
	/**
	 * This method adds a new traffic light.
	 * @param x x coordinate of the traffic light
	 * @param y y coordinate of the traffic light
	 * @param rate rate at which the light changes
	 * @param start start time of the simulation
	 */
	public void addTrafficLight(int x, int y, int rate, int start) {
		int i, x1, y1;
		
		if(x >= 0 && x < width && y >= 0 && y < height) {
			for(i = 0; i < trafficLightList.size(); i++) {
				x1 = trafficLightList.get(i).getX();
				y1 = trafficLightList.get(i).getY();
				
				if(x == x1 && y == y1)
					trafficLightList.remove(i);
			}
			
			TrafficLight t = new TrafficLight(x, y, rate, start);
			trafficLightList.add(t);
			t = null;
		}
		else {
			JOptionPane frame = new JOptionPane();
			JOptionPane.showMessageDialog(frame, "Error! Invalid values dectected.");
			frame = null;
		}
	}
	
	/**
	 * This method adds a new drop off point.
	 * @param x x coordinate of the drop off point
	 * @param y y coordinate of the drop off point
	 * @param rate rate at which the cars stay at the drop off point
	 */
	public void addDropOff(int x, int y, int rate) {
		int i, x1, y1;
		
		if(x >= 0 && x < width && y >= 0 && y < height) {
			for(i = 0; i < dropOffList.size(); i++) {
				x1 = dropOffList.get(i).getX();
				y1 = dropOffList.get(i).getY();
				
				if(x == x1 && y == y1)
					dropOffList.remove(i);
			}
			
			DropOff d = new DropOff(x, y, rate);
			dropOffList.add(d);
			d = null;
		}
		else {
			JOptionPane frame = new JOptionPane();
			JOptionPane.showMessageDialog(frame, "Error! Invalid values dectected.");
		}
	}
	
	/**
	 * This adds a new road tile.
	 * @param x x coordinate of the road tile
	 * @param y y coordinate of the road tile
	 * @param condition condition of the road tile (e.g. Broken, Flooded, Blocked)
	 */
	public void addRoadTile(int x, int y, int condition) {
		int i, x1, y1;
		
		if(x >= 0 && x < width && y >= 0 && y < height) {
			for(i = 0; i < roadTileList.size(); i++) {
				x1 = roadTileList.get(i).getX();
				y1 = roadTileList.get(i).getY();
				
				if(x == x1 && y == y1)
					roadTileList.remove(i);
			}
			
			RoadTile rt = new RoadTile(x, y, condition);
			roadTileList.add(rt);
			rt = null;
		}
		else {
			JOptionPane frame = new JOptionPane();
			JOptionPane.showMessageDialog(frame, "Error! Invalid values dectected.");
		}
	}
	
	/**
	 * This method removes a car.
	 * @param x x coordinate of the car
	 * @param y y coordinate of the car
	 */
	public void removeCar(int x, int y) {
		int i, x1, y1;
		for(i = 0; i < carList.size(); i++) {
			x1 = carList.get(i).getX();
			y1 = carList.get(i).getY();
			if(x == x1 && y == y1)
				carList.remove(i);
		}
	}
	
	/**
	 * This method removes a car from the car list
	 * @param i position of the car
	 */
	public void removeCar(int i) {
		carList.remove(i);
	}
	
	/**
	 * This method removes a bus.
	 * @param x x coordinate of the bus.
	 * @param y y coordinate of the bus.
	 */
	public void removeBus(int x, int y) {
		int i, x1, y1;
		for(i = 0; i < busList.size(); i++) {
			x1 = busList.get(i).getX();
			y1 = busList.get(i).getY();
			if(x == x1 && y == y1)
				busList.remove(i);
		}
	}
	
	/**
	 * This method removes a bus from the bus list.
	 * @param i position of the bus
	 */
	public void removeBus(int i) {
		busList.remove(i);
	}
	
	/**
	 * This method removes a road.
	 * @param x x coordinate of the road
	 * @param y y coordinate of the road
	 */
	public void removeRoad(int x, int y) {
		int i, x1, y1;
		if(x >= 0 && x < width && y >= 0 && y < height) {
			for(i = 0; i < roadList.size(); i++) {
				x1 = roadList.get(i).getX();
				y1 = roadList.get(i).getY();
				if(x == x1 && y == y1)
					roadList.remove(i);
			}
		}
		else {
			JOptionPane frame = new JOptionPane();
			JOptionPane.showMessageDialog(frame, "Error! Invalid values dectected.");
			frame = null;
		}
	}
	
	/**
	 * This method removes a wall.
	 * @param x x coordinate of the wall
	 * @param y y coordinate of the wall
	 */
	public void removeWall(int x, int y) {
		int i, x1, y1;
		if(x >= 0 && x < width && y >= 0 && y < height) {
			for(i = 0; i < wallList.size(); i++) {
				x1 = wallList.get(i).getX();
				y1 = wallList.get(i).getY();
				
				if(x == x1 && y == y1)
					wallList.remove(i);
			}
		}
		else {
			JOptionPane frame = new JOptionPane();
			JOptionPane.showMessageDialog(frame, "Error! Invalid values dectected.");
			frame = null;
		}
	}
	
	/**
	 * This removes a spawner.
	 * @param x x coordinate of the spawner
	 * @param y y coordinate of the spawner
	 */
	public void removeSpawner(int x, int y) {
		int i, x1, y1;
		if(x >= 0 && x < width && y >= 0 && y < height) {
			for(i = 0; i < spawnerList.size(); i++) {
				x1 = spawnerList.get(i).getX();
				y1 = spawnerList.get(i).getY();
				
				if(x == x1 && y == y1)
					spawnerList.remove(i);
			}
		}
		else {
			JOptionPane frame = new JOptionPane();
			JOptionPane.showMessageDialog(frame, "Error! Invalid values dectected.");
			frame = null;
		}
	}
	
	/**
	 * This method removes a drop off point.
	 * @param x x coordinate of the drop off point
	 * @param y y coordinate of the drop off point
	 */
	public void removeDropOff(int x, int y) {
		int i, x1, y1;
		if(x >= 0 && x < width && y >= 0 && y < height) {
			for(i = 0; i < dropOffList.size(); i++) {
				x1 = dropOffList.get(i).getX();
				y1 = dropOffList.get(i).getY();
				
				if(x == x1 && y == y1)
					dropOffList.remove(i);
			}
		}
		else {
			JOptionPane frame = new JOptionPane();
			JOptionPane.showMessageDialog(frame, "Error! Invalid values dectected.");
			frame = null;
		}
	}
	
	/**
	 * This method removes a traffic light.
	 * @param x x coordinate of the traffic light
	 * @param y y coordinate of the traffic light
	 */
	public void removeTrafficLight(int x, int y) {
		int i, x1, y1;
		if(x >= 0 && x < width && y >= 0 && y < height) {
			for(i = 0; i < trafficLightList.size(); i++) {
				x1 = trafficLightList.get(i).getX();
				y1 = trafficLightList.get(i).getY();
				
				if(x == x1 && y == y1)
					trafficLightList.remove(i);
			}
		}
		else {
			JOptionPane frame = new JOptionPane();
			JOptionPane.showMessageDialog(frame, "Error! Invalid values dectected.");
			frame = null;
		}
	}
	
	/**
	 * This method removes a road tile.
	 * @param x x coordinate of the road tile
	 * @param y y coordinate of the road tile
	 */
	public void removeRoadTile(int x, int y) {
		int i, x1, y1;
		if(x >= 0 && x < width && y >= 0 && y < height) {
			for(i = 0; i < roadTileList.size(); i++) {
				x1 = roadTileList.get(i).getX();
				y1 = roadTileList.get(i).getY();
				
				if(x == x1 && y == y1)
					roadTileList.remove(i);
			}
		}
		else {
			JOptionPane frame = new JOptionPane();
			JOptionPane.showMessageDialog(frame, "Error! Invalid values dectected.");
			frame = null;
		}
	}
	
	/**
	 * This method sets a map name.
	 * @param mapName name of the map
	 */
	public void setMapName(String mapName) {
		this.mapName = mapName;
	}
	
	/**
	 * This method sets a width of the map.
	 * @param width width of the map
	 */
	public void setWidth(int width) {
		this.width = width;
	}
	
	/**
	 * This method sets a height of the map.
	 * @param height height of the map
	 */
	public void setHeight(int height) {
		this.height = height;
	}
	
	/**
	 * This method sets an integer based map.
	 * @param numMap integer based map
	 */
	public void setNumMap(int[][] numMap) {
		this.numMap = numMap;
	}
	
	/**
	 * This method sets the objects of the map.
	 * @param objectMap objects of the map
	 */
	public void setObjectMap(int[][] objectMap) {
		this.objectMap = objectMap;
	}
	
	/**
	 * This method sets the blocks of the map.
	 * @param blockMap blocks of the map
	 */
	public void setBlockMap(Block[][] blockMap) {
		this.blockMap = blockMap;
	}
	
	/**
	 * This method sets the list of cars.
	 * @param carList list of cars
	 */
	public void setCarList(ArrayList <Car> carList) {
		this.carList = carList;
	}
	
	/**
	 * This method sets the list of buses.
	 * @param busList list of buses
	 */
	public void setBusList(ArrayList <Bus> busList) {
		this.busList = busList;
	}
	
	/**
	 * This method sets the list of roads.
	 * @param roadList list of roads
	 */
	public void setRoadList(ArrayList <Road> roadList) {
		this.roadList = roadList;
	}
	
	/**
	 * This method sets the list of walls.
	 * @param wallList list of walls
	 */
	public void setWallList(ArrayList <Wall> wallList) {
		this.wallList = wallList;
	}
	
	/**
	 * This method sets the list of traffic lights.
	 * @param trafficLightList list of traffic lights
	 */
	public void setTrafficList(ArrayList <TrafficLight> trafficLightList) {
		this.trafficLightList = trafficLightList;
	}
	
	/**
	 * This method sets the list of drop off points.
	 * @param dropOffList list of drop off points
	 */
	public void setDropOffList(ArrayList <DropOff> dropOffList) {
		this.dropOffList = dropOffList;
	}
	
	/**
	 * This method sets the list of road tiles.
	 * @param roadTileList list of road tiles
	 */
	public void setRoadTiles(ArrayList <RoadTile> roadTileList) {
		this.roadTileList = roadTileList;
	}
	
	/**
	 * This method sets the list of spawners.
	 * @param spawnerList list of spawners
	 */
	public void setSpawnerList(ArrayList <Spawner> spawnerList) {
		this.spawnerList = spawnerList;
	}
	
	/**
	 * This method returns the current map.
	 * @return current map
	 */
	public boolean hasMap() {
		return hasMap;
	}
	
	/**
	 * This method returns the map name.
	 * @return map name
	 */
	public String getMapName() {
		return mapName;
	}
	
	/**
	 * This method returns the integer based map.
	 * @return integer based map
	 */
	public int[][] getNumMap() {
		convertToNumMap();
		return numMap;
	}
	
	/**
	 * This method returns the objects of the map.
	 * @return objects of the map
	 */
	public int[][] getObjectMap() {
		return objectMap;
	}
	
	/**
	 * This method returns the blocks of the map.
	 * @return blocks of the map
	 */
	public Block[][] getBlockMap() {
		return blockMap;
	}
	
	/**
	 * This method returns the width of the map.
	 * @return width of the map
	 */
	public int getWidth() {
		return width;
	}
	
	/**
	 * This method returns the height of the map.
	 * @return height of the map
	 */
	public int getHeight() {
		return height;
	}
	
	/**
	 * This method returns the list of cars.
	 * @return list of cars
	 */
	public ArrayList <Car> getCarList() {
		return carList;
	}
	
	/**
	 * This method returns the list of buses.
	 * @return list of buses
	 */
	public ArrayList <Bus> getBusList() {
		return busList;
	}
	
	/**
	 * This method returns the list of walls.
	 * @return list of walls
	 */
	public ArrayList <Wall> getWallList() {
		return wallList;
	}
	
	/**
	 * This method returns the list of roads
	 * @return list of roads
	 */
	public ArrayList <Road> getRoadList() {
		return roadList;
	}
	
	/**
	 * This method returns the list of spawners.
	 * @return list of spawners
	 */
	public ArrayList <Spawner> getSpawnerList() {
		return spawnerList;
	}
	
	/**
	 * This method returns the list of drop off points.
	 * @return list of drop off points
	 */
	public ArrayList <DropOff> getDropOffList() {
		return dropOffList;
	}

	/**
	 * This method returns the list of traffic lights.
	 * @return list of traffic lights
	 */
	public ArrayList <TrafficLight> getTrafficLightList() {
		return trafficLightList;
	}
	/**
	 * This method returns the list of road tiles.
	 * @return list of road tiles
	 */
	public ArrayList <RoadTile> getRoadTileList() {
		return roadTileList;
	}
}
