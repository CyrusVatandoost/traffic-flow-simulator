import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * This class retrieves the settings from different components of the simulation. 
 */
public class EditorModel extends Thread{
	private String mapName;
	private int mouseX;
	private int mouseY;
	private int[][] map;
	private int[][] objectMap;
	private Block[][] block;
	private Map m = new Map();
	private JFrame f;
	
	/**
	 * This is the constructor method of EditorModel.
	 */
	public EditorModel() {

	}
	
	/**
	 * This method saves the alterations the user made to the map.
	 */
	public void save() throws FileNotFoundException, UnsupportedEncodingException {
		Printer p = new Printer(m);
		p.setMapName(mapName);
		p.print();
	}
	
	/**
	 * This method lets the user choose a map.
	 */
	public void setMap() throws URISyntaxException {
		int i, x, y, direction;

		m.readMap();
		
		int width, height;
		
		width = m.getWidth();
		height = m.getHeight();
		
		ArrayList <Wall> wallList = new ArrayList <Wall> (m.getWallList());
		ArrayList <Road> roadList = new ArrayList <Road> (m.getRoadList());
		ArrayList <TrafficLight> trafficLightList = new ArrayList <TrafficLight> (m.getTrafficLightList());
		
		map = new int[m.getWidth()][m.getHeight()];
		objectMap = new int[m.getWidth()][m.getHeight()];
		block = new Block[m.getWidth()][m.getHeight()];
		
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
	 * This method sets the area of the display.
	 * @param width1 width of the simulation
	 * @param height1 height of the simulation
	 */
	public void setDisplay(int width1, int height1) {
		f = new JFrame("Map Editor");
		f.setSize(width1, height1);
		f.setLocationRelativeTo(null);
		f.getContentPane().add(new Display(m));
		f.getContentPane().addMouseListener(new MouseListener() {
		    @Override
		    public void mouseClicked(MouseEvent e) {
		        mouseX = e.getX();
		        mouseY = e.getY();
		        
				int xWidth = (int) f.getWidth() / m.getWidth();
				int yHeight = (int) f.getHeight() / m.getHeight();
		        
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
	
	/**
	 * This method sets the visibility of the Map.
	 * @param visibility
	 */
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
	 * This method creates a new map.
	 * @param mapName
	 * @param width
	 * @param height
	 * @throws FileNotFoundException
	 * @throws UnsupportedEncodingException
	 */
	public void newMap(String mapName, int width, int height) throws FileNotFoundException, UnsupportedEncodingException {
		m.setMapName(mapName);
		m.newMap(width, height);
		save();
	}

	/**
	 * This method sets a map name
	 * @param mapName name of the map
	 */
	public void setMapName(String mapName) {
		m.setMapName(mapName);
	}
	
	/**
	 * This method checks if the map is empty or not.
	 * @param mapName name of the map
	 * @return boolean of the state of the text file
	 * @throws FileNotFoundException
	 */
	public boolean isEmpty(String mapName) throws FileNotFoundException {
		Reader r = new Reader();
		return r.isEmpty(mapName);
	}
	
	/**
	 * This method gets the assigned Map.
	 * @return Map
	 */
	public Map getMap() {
		return m;
	}
	
	/**
	 * This method returns the map
	 * @return structure of the map
	 */
	public int[][] getMap1() {
		return map;
	}
	
	/**
	 * This method returns the x value of the mouse pointer in relative to the frame and the map.
	 * @return x value of the mouse
	 */
	public int getMouseX() {
		return mouseX;
	}
	
	/**
	 * This method returns the y value of the mouse pointer in relative to the frame and the map.
	 * @return y value of the mouse
	 */
	public int getMouseY() {
		return mouseY;
	}
	
	/**
	 * This method returns all the coordinates of the walls.
	 * @return the coordinates of the walls
	 */
	public ArrayList <Wall> getWallList() {
		return m.getWallList();
	}
	
	/**
	 * This method returns all the coordinate of the spawners
	 * @return the coordinates of the spawners
	 */
	public ArrayList <Spawner> getSpawnerList() {
		return m.getSpawnerList();
	}
	
	/**
	 * This method returns the coordinates of all the roads
	 * @return the coordinates of the roads
	 */
	public ArrayList <Road> getRoadList() {
		return m.getRoadList();
	}
	
	/**
	 * This method returns the coordinates of all the Drop Off blocks
	 * @return the coordinates of the Drop Off blocks
	 */
	public ArrayList <DropOff> getDropOffList() {
		return m.getDropOffList();
	}
	
	/**
	 * This method returns the coordinates of all the Traffic Light blocks
	 * @return the coordinates of the Traffic Light blocks
	 */
	public ArrayList <TrafficLight> getTrafficLightList() {
		return m.getTrafficLightList();
	}
	
	public ArrayList <RoadTile> getRoadTileList() {
		return m.getRoadTileList();
	}
}