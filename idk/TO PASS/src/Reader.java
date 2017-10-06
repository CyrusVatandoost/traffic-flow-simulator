import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

import org.omg.CORBA.portable.InputStream;

/**
 * This method reads the integer based map from a file.
 */
public class Reader {

	private int width;
	private int height;
	
	private ArrayList <Road> roadList = new ArrayList <Road> ();
	private ArrayList <TrafficLight> trafficLightList = new ArrayList <TrafficLight> ();
	private ArrayList <Spawner> spawnerList = new ArrayList <Spawner> ();
	private ArrayList <DropOff> dropOffList = new ArrayList <DropOff> ();
	private ArrayList <Wall> wallList = new ArrayList <Wall> ();
	private ArrayList <RoadTile> roadTileList = new ArrayList <RoadTile> ();
	
	/**
	 * Constructor method for Reader class.
	 */
	public Reader() {
		
	}
	
	/**
	 * This method reads a file
	 * @param fileName name of the file
	 * @throws IOException
	 */
	public void read(String fileName) throws IOException, URISyntaxException {
		int y;
		int num;

		Road r;
		Spawner p;
		DropOff d;
		TrafficLight t;
		Wall w;
		RoadTile rt;
		
		File f = new File(fileName);
		
		try {
			Scanner s = new Scanner(f);

			width = s.nextInt();	//This scans the width.
			height = s.nextInt();	//This scans the height.
			
			for(y = 0; y <= height; y++) {
				
				if(s.hasNextLine()) {
				
					String line = s.nextLine();

						for(int i = 0; i < line.length(); i++) {
							num = Character.getNumericValue(line.charAt(i));
							
							//This adds a new Road.
							if(num >= 0 && num <= 4) {
								r = new Road(i, y - 1, num);
								roadList.add(r);
								r = null;
							}
							
							//This adds a new Wall.
							else if(num == 9) {
								w = new Wall(i, y - 1);
								wallList.add(w);
								w = null;
							}
						}
				}
			}
			
			while(s.hasNextLine()) {
				num = s.nextInt();
				
				if(num == -1) {
					break;
				}
				//If the object type is a TrafficLight, scan this.
				if(num == 6) {
					t = new TrafficLight(s.nextInt(), s.nextInt(), s.nextInt(), s.nextInt());
					trafficLightList.add(t);
					t = null;
				}
				//If the object type is a DropOff, scan this.
				else if(num == 7) {
					d = new DropOff(s.nextInt(), s.nextInt(), s.nextInt());
					dropOffList.add(d);
					d = null;
				}
				//If the object type is a Spawner, scan this.
				else if(num == 8) {
					p = new Spawner(s.nextInt(), s.nextInt(), s.nextInt());
					spawnerList.add(p);
					p = null;
				}
				//If the object type is a RoadTile, scan this.
				else if(num == 10) {
					rt = new RoadTile(s.nextInt(), s.nextInt(), s.nextInt());
					roadTileList.add(rt);
					rt = null;
				}
			}
			s.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * This method checks if the file contains anything.
	 * @param fileName name of the file
	 * @return the current condition of the file
	 * @throws FileNotFoundException
	 */
	public boolean isEmpty(String fileName) throws FileNotFoundException {
		File f = new File(fileName);
		Scanner s = new Scanner(f);
		
		if(s.hasNext())
			return false;
		else
			return true;
	}
	
	/**
	 * This method returns the width of the map
	 * @return width of the map
	 */
	public int getWidth() {
		return width;
	}
	
	/**
	 * This method returns the height of the map
	 * @return height of the map
	 */
	public int getHeight() {
		return height;
	}
	
	/**
	 * This method returns all the coordinates of the Road Blocks
	 * @return the coordinates of the Road Blocks
	 */
	public ArrayList <Road> getRoadList() {
		return roadList;
	}
	
	/**
	 * This method returns all the coordinates of the Traffic Light blocks
	 * @return the coordinates of the traffic light blocks
	 */
	public ArrayList <TrafficLight> getTrafficLightList() {
		return trafficLightList;
	}
	
	/**
	 * This method returns all the coordinates of the Spawner blocks
	 * @return the coordinates of the spawner blocks
	 */
	public ArrayList <Spawner> getSpawnerList() {
		return spawnerList;
	}
	
	/**
	 * This method returns all the coordinates of the Drop Off blocks
	 * @return the coordinates of the Drop Off blocks
	 */
	public ArrayList <DropOff> getdropOffList() {
		return dropOffList;
	}
	
	/**
	 * This method returns all the coordinates of the Walls
	 * @return the coordinates of the walls
	 */
	public ArrayList <Wall> getWallList() {
		return wallList;
	}
	
	public ArrayList <RoadTile> getRoadTileList() {
		return roadTileList;
	}
}
