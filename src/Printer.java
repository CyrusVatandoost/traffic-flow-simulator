import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

/**
 * This class displays("prints") the objects of the map.
 */
public class Printer {
	private int width;
	private int height;
	private int[][] map;
	
	private ArrayList <Wall> wallList;
	private ArrayList <Spawner> spawnerList;
	private ArrayList <Road> roadList;
	private ArrayList <DropOff> dropOffList;
	private ArrayList <TrafficLight> trafficLightList;
	private ArrayList <RoadTile> roadTileList;
	
	private String mapName;
	private Map m;
	
	/**
	 * Constructor method for Printer class
	 * @param m settings from EditorModel class
	 * @throws FileNotFoundException
	 * @throws UnsupportedEncodingException
	 */
	public Printer(Map m) throws FileNotFoundException, UnsupportedEncodingException {
		this.m = m;
	}
	
	/**
	 * This method prints the list of elements of the simulation
	 * @throws FileNotFoundException
	 * @throws UnsupportedEncodingException
	 */
	public void print() throws FileNotFoundException, UnsupportedEncodingException {
		int i, x, y;
		
		copy();
	
		PrintWriter p = new PrintWriter(mapName, "UTF-8");
		
		p.println(width);
		p.println(height);
		
		for(y = 0; y < height; y++) {
			for(x = 0; x < width; x++) {
				p.print(map[x][y]);
			}
			p.println();
		}
		
		for(i = 0; i < trafficLightList.size(); i++) {
			p.println("6");
			p.println(trafficLightList.get(i).getX());
			p.println(trafficLightList.get(i).getY());
			p.println(trafficLightList.get(i).getRate());
			p.println(trafficLightList.get(i).getStart());
		}
		
		for(i = 0; i < dropOffList.size(); i++) {
			p.println("7");
			p.println(dropOffList.get(i).getX());
			p.println(dropOffList.get(i).getY());
			p.println(dropOffList.get(i).getLimit());
		}
		
		for(i = 0; i < spawnerList.size(); i++) {
			p.println("8");
			p.println(spawnerList.get(i).getX());
			p.println(spawnerList.get(i).getY());
			p.println(spawnerList.get(i).getRate());
		}
		
		for(i = 0; i < roadTileList.size(); i++) {
			p.println("10");
			p.println(roadTileList.get(i).getX());
			p.println(roadTileList.get(i).getY());
			p.println(roadTileList.get(i).getCondition());
		}
		
		p.println(-1);
		
		p.close();
	}
	
	/**
	 * This method lets the user duplicate a map
	 */
	public void copy() {
		width = m.getWidth();
		height = m.getHeight();
		mapName = m.getMapName();
		map = m.getNumMap();
		spawnerList = new ArrayList <Spawner> (m.getSpawnerList());
		dropOffList = new ArrayList <DropOff> (m.getDropOffList());
		trafficLightList = new ArrayList <TrafficLight> (m.getTrafficLightList());
		roadTileList = new ArrayList <RoadTile> (m.getRoadTileList());
	}
	
	/**
	 * This method sets a map name
	 * @param mapName name of the map
	 */
	public void setMapName(String mapName) {
		this.mapName = mapName;
	}
}
