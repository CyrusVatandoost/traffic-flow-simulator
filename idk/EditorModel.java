import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class EditorModel extends Thread{
	private String mapName;
	
	private int width;
	private int height;
	
	private int mouseX;
	private int mouseY;
	
	private ArrayList <Wall> wallList;
	private ArrayList <Spawner> spawnerList;
	private ArrayList <Road> roadList;
	private ArrayList <DropOff> dropOffList;
	private ArrayList <TrafficLight> trafficLightList;
	private ArrayList <RoadTile> roadTileList;
	
	private int[][] map;
	private int[][] objectMap;
	private Block[][] block;

	private JFrame f;
	
	public EditorModel() {

	}
	
	/**
	 * This method saves the alerations the user made to the map
	 */
	public void save() throws FileNotFoundException, UnsupportedEncodingException {
		convertToMap();
		Printer p = new Printer(this);
		p.setMapName(mapName);
		p.print();
	}
	
	/**
	 * This method lets the user choose a map
	 */
	public void setMap() throws URISyntaxException {
		Reader r = new Reader();
		
		try {
			r.read(mapName);
		} catch (IOException e1) {
			System.out.println("ERROR!");
			e1.printStackTrace();
		}
		
		int i, x, y, direction;
		
		width = r.getWidth();
		height = r.getHeight();
		
		//This gets the values of the different entities from the text file.
		wallList = new ArrayList <Wall> (r.getWallList());
		spawnerList = new ArrayList <Spawner> (r.getSpawnerList());
		roadList = new ArrayList <Road> (r.getRoadList());
		dropOffList = new ArrayList <DropOff> (r.getdropOffList());
		trafficLightList = new ArrayList <TrafficLight> (r.getTrafficLightList());
		roadTileList = new ArrayList <RoadTile> (r.getRoadTileList());
		
		map = new int[width][height];
		objectMap = new int[width][height];
		block = new Block[width][height];
		
		//This clears the map.
		for(x = 0; x < width; x++) {
			for(y = 0; y < height; y++) {
				map[x][y] = 0;
				objectMap[x][y] = 0;
				block[x][y] = new Block();
			}
		}
		
		//This adds walls into the map.
		for(i = 0; i < wallList.size(); i++) {
			x = wallList.get(i).getX();
			y = wallList.get(i).getY();
			
			map[x][y] = 9;
			objectMap[x][y] = 9;
		}
		
		//This adds roads into the map.
		for(i = 0; i < roadList.size(); i++) {
			x = roadList.get(i).getX();
			y = roadList.get(i).getY();
			direction = roadList.get(i).getDirection();
			
			map[x][y] = direction;
		}
		
		//This adds traffic lights into the map.
		for(i = 0; i < trafficLightList.size(); i++) {
			x = trafficLightList.get(i).getX();
			y = trafficLightList.get(i).getY();
			
			block[x][y].setTrafficLight(trafficLightList.get(i));;
		}
	}
	
	/**
	 * This method sets the area of the display
	 * @param width - width of the simulation
	 * @param height - height of the simulation
	 */
	public void setDisplay(int width1, int height1) {
		f = new JFrame("Map Editor");
		f.setSize(width1, height1);
		f.setLocationRelativeTo(null);
		f.getContentPane().add(new EditorDisplay(this));
		f.getContentPane().addMouseListener(new MouseListener() {
		    @Override
		    public void mouseClicked(MouseEvent e) {
		        mouseX = e.getX();
		        mouseY = e.getY();
		        
				int xWidth = (int) f.getWidth() / width;
				int yHeight = (int) f.getHeight() / height;
		        
				mouseX = mouseX / xWidth;
				mouseY = mouseY / yHeight;
				
		        System.out.println(mouseX + "," + mouseY);//these co-ords are relative to the component
		    }

			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		f.setVisible(true);
	}
	
	public void setVisible() {
		if(f.isVisible())
			f.setVisible(false);
		else
			f.setVisible(true);
	}
	
	/**
	 * This method displays the simulation
	 */
	public void displayMap() {
		
		Thread t = new Thread() {
			public void run() {
				setDisplay(500, 500);
			}
		};
		
		t.start();
	}

	/**
	 * This method lets the user erase all elements of the simulation
	 */
	public void deleteAll() {
		int x, y;
		
		for(y = 0; y < height; y++) {
			for(x = 0; x < height; x++) {
				addWall(x, y);
			}
		}
	}

	/**
	 * This method lets the user change from one map to another
	 */
	public void convertToMap() {
		int i, x, y;
		
		for(y = 0; y < height; y++) {
			for(x = 0; x < width; x++) {
				map[x][y] = 9;
			}
		}
		
		for(i = 0; i < roadList.size(); i++) {
			x = roadList.get(i).getX();
			y = roadList.get(i).getY();
			
			map[x][y] = roadList.get(i).getDirection();
		}
	}
	
	public void newMap(String mapName, int width, int height) throws FileNotFoundException, UnsupportedEncodingException {
		int x, y;
		
		this.width = width;
		this.height = height;
		this.mapName = mapName;
		map = new int[width][height];
		
		wallList = new ArrayList <Wall> ();
		spawnerList = new ArrayList <Spawner> ();
		roadList = new ArrayList <Road> ();
		dropOffList = new ArrayList <DropOff> ();
		trafficLightList = new ArrayList <TrafficLight> ();
		
		for(y = 0; y < height; y++) {
			for(x = 0; x < height; x++) {
				addWall(x, y);
			}
		}
		
		save();
	}

	/**
	 * This method sets a map name
	 * @param mapName - name of the map
	 */
	public void setMapName(String mapName) {
		this.mapName = mapName;
	}

	/**
	 * This method lets the user add a road block
	 * @param x - x coordinate where the user wants to place the road
	 * @param y - y coordinate where the user  wants to place the road
	 * @param direction - the direction the road is assigned to
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
	 * This method lets the user add a wall
	 * @param x - x coordinate where the user wants to place the wall
	 * @param y - y coordinate where the user wants to place the wall
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
	 * This method lets the user add a spawner to the simulation
	 * @param x - x coordinate where the user wants to place the spawner
	 * @param y - y coordinate where the user wants to place the spawner
	 * @param rate - rate at which cars spawn
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
	 * This method lets the user add a Traffic Light block
	 * @param x - x coordinate where the user wants to add the Traffic Light
	 * @param y - y coordinate where the user wants to add the Traffic Light
	 * @param rate - rate at which the color of the Traffic Light changes
	 * @param start - time when the simulation started
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
	
	public boolean isEmpty(String mapName) throws FileNotFoundException {
		Reader r = new Reader();
		return r.isEmpty(mapName);
	}
	
	/**
	 * This method returns the width of the simulator
	 * @return width of the simulator
	 */
	public int getWidth() {
		return width;
	}
	
	/**
	 * This method returns the height of the simulator
	 * @return the height of the simulator
	 */
	public int getHeight() {
		return height;
	}
	
	/**
	 * This method returns the map
	 * @return structure of the map
	 */
	public int[][] getMap() {
		return map;
	}
	
	public int getMouseX() {
		return mouseX;
	}
	
	public int getMouseY() {
		return mouseY;
	}
	
	/**
	 * This method returns all the coordinates of the walls
	 * @return the coordinates of the walls
	 */
	public ArrayList <Wall> getWallList() {
		return wallList;
	}
	
	/**
	 * This method returns all the coordinate of the spawners
	 * @return the coordinates of the spawners
	 */
	public ArrayList <Spawner> getSpawnerList() {
		return spawnerList;
	}
	
	/**
	 * This method returns the coordinates of all the roads
	 * @return the coordinates of the roads
	 */
	public ArrayList <Road> getRoadList() {
		return roadList;
	}
	
	/**
	 * This method returns the coordinates of all the Drop Off blocks
	 * @return the coordinates of the Drop Off blocks
	 */
	public ArrayList <DropOff> getDropOffList() {
		return dropOffList;
	}
	
	/**
	 * This method returns the coordinates of all the Traffic Light blocks
	 * @return the coordinates of the Traffic Light blocks
	 */
	public ArrayList <TrafficLight> getTrafficLightList() {
		return trafficLightList;
	}
	
	public ArrayList <RoadTile> getRoadTileList() {
		return roadTileList;
	}
}