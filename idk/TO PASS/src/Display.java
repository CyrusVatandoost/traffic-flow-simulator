import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import javax.swing.JComponent;

/**
 * This class displays the elements of the simulation.
 */
public class Display extends JComponent {
	
	private int width;
	private int height;
	private int windowWidth;
	private int windowHeight;
	
	private Map s;
	
	private ArrayList <Car> carList;
	private ArrayList <Bus> busList;
	private ArrayList <Wall> wallList;
	private ArrayList <Spawner> spawnerList;
	private ArrayList <Road> roadList;
	private ArrayList <DropOff> dropOffList;
	private ArrayList <TrafficLight> trafficLightList;
	private ArrayList <RoadTile> roadTileList;
	
	/**
	 * Constructor method for Display class
	 * @param s settings from Simulation class
	 */
	public Display(Map s) {
		this.s = s;
		
        Thread animationThread = new Thread(new Runnable() {
            public void run() {
                while (true) {
                    repaint();
                    try {Thread.sleep(10);} catch (Exception ex) {}
                }
            }
        });
        animationThread.start();
	}
	
	/**
	 * This method displays("paints") the elements of the traffic simulation
	 */
	public void paintComponent(Graphics gg) {
		Graphics2D g = (Graphics2D) gg;
        super.paintComponent(gg);
		
		width = s.getWidth();
		height = s.getHeight();
		
		windowWidth = this.getWidth();
		windowHeight = this.getHeight();
		
		carList = new ArrayList <Car> (s.getCarList());
		busList = new ArrayList <Bus> (s.getBusList());
		wallList = new ArrayList <Wall> (s.getWallList());
		spawnerList = new ArrayList <Spawner> (s.getSpawnerList());
		roadList = new ArrayList <Road> (s.getRoadList());
		dropOffList = new ArrayList <DropOff> (s.getDropOffList());
		trafficLightList = new ArrayList <TrafficLight> (s.getTrafficLightList());
		roadTileList = new ArrayList <RoadTile> (s.getRoadTileList());
		
		float xWidth = windowWidth / width;
		float yHeight = windowHeight / height;
		
        int xstart;
        int ystart;
        int i;
        int x, y, x1, y1, x2, y2;
        
        //This is where the road tiles are painted.
        for(i = 0; i < roadTileList.size(); i++) {
        	x = roadTileList.get(i).getX();
        	y = roadTileList.get(i).getY();

        	x1 = x * (int) xWidth;
        	y1 = y * (int) yHeight;
        	
        	if(roadTileList.get(i).getCondition() == 1) {
        		gg.setColor(Color.LIGHT_GRAY);
            	gg.fillRect(x1, y1, (int) xWidth, (int) yHeight);
        	}
        	else if(roadTileList.get(i).getCondition() == 2) {
        		gg.setColor(Color.CYAN);
            	gg.fillRect(x1, y1, (int) xWidth, (int) yHeight);
        	}
        	else if(roadTileList.get(i).getCondition() == 3) {
        		gg.setColor(Color.DARK_GRAY);
            	gg.fillRect(x1, y1, (int) xWidth, (int) yHeight);
        	}
        }
        
        //This is where the drop off points are painted.
        for(i = 0; i < dropOffList.size(); i++) {
        	x = dropOffList.get(i).getX();
        	y = dropOffList.get(i).getY();
        	
        	if(dropOffList.get(i).isOccupied())
        		gg.setColor(Color.GRAY);
        	else
        		gg.setColor(Color.LIGHT_GRAY);
        	
        	x1 = x * (int) xWidth;
        	y1 = y * (int) yHeight;
        	
        	gg.fillRect(x1, y1, (int) xWidth, (int) yHeight);
        }
        
        //This is where the traffic lights are painted.
        for(i = 0; i < trafficLightList.size(); i++) {
        	x = trafficLightList.get(i).getX();
        	y = trafficLightList.get(i).getY();
        	
        	if(trafficLightList.get(i).getGo())
        		gg.setColor(Color.GREEN);
        	else
        		gg.setColor(Color.RED);
        	
        	x1 = x * (int) xWidth;
        	y1 = y * (int) yHeight;
        	
        	gg.fillRect(x1, y1, (int) xWidth, (int) yHeight);
        }
        
        //This is where the spawners are created.
        gg.setColor(Color.PINK);
        for(i = 0; i < spawnerList.size(); i++) {
        	x = spawnerList.get(i).getX();
        	y = spawnerList.get(i).getY();
        	
        	x1 = x * (int) xWidth;
        	y1 = y * (int) yHeight + ( (int) yHeight / 2);
        	
        	x2 = x1 + (int) xWidth;
        	y2 = y1;
        	
        	gg.drawLine(x1, y1, x2, y2);
        	
        	x1 = x1 + ( (int) xWidth / 2);
        	y1 = y1 - ( (int) yHeight / 2);
        	
        	x2 = x1;
        	y2 = y1 + (int) yHeight;
        	
        	gg.drawLine(x1, y1, x2, y2);
        }
        
        //This is where the road directions are created.
        gg.setColor(Color.ORANGE);
        for(i = 0; i < roadList.size(); i++) {
        	int direction;
        	
        	x = roadList.get(i).getX();
        	y = roadList.get(i).getY();
        	direction = roadList.get(i).getDirection();
        	
        	if(direction == 4) {
        		x1 = x * (int) xWidth;
        		y1 = y * (int) yHeight;
        		x2 = x1 + (int) xWidth;
        		y2 = y1 + (int) yHeight;
        				
        		gg.drawLine(x1, y1, x2, y2);
        		
        		y1 = y2;
        		y2 = y1 - (int) yHeight;
        		
        		gg.drawLine(x1, y1, x2, y2);
        	}

        	//This makes an arrow pointing up.
        	else if(direction == 0) {
        		x1 = x * (int) xWidth + ( (int) xWidth / 2);
        		y1 = y * (int) yHeight;
        		
        		x2 = x1 - ( (int) xWidth / 2);
        		y2 = y1 + ( (int) yHeight);
        		
        		gg.drawLine(x1, y1, x2, y2);
        		
        		x2 = x2 + ( (int) xWidth);

        		gg.drawLine(x1, y1, x2, y2);
        	}
        	
        	//This makes an arrow pointing down.
        	else if(direction == 1) {
        		x1 = x * (int) xWidth + ( (int) xWidth / 2);
        		y1 = y * (int) yHeight + (int) yHeight;
        		
        		x2 = x1 - ( (int) xWidth / 2);
        		y2 = y1 - (int) yHeight;
        		
        		gg.drawLine(x1, y1, x2, y2);
        		
        		x2 = x1 + ( (int) xWidth / 2);
        		
        		gg.drawLine(x1, y1, x2, y2);
        	}
        	
        	//This makes an arrow pointing left.
        	else if(direction == 2) {
        		x1 = x * (int) xWidth;
        		y1 = y * (int) yHeight + ( (int) yHeight / 2);
        		
        		x2 = x1 + (int) xWidth;
        		y2 = y1 - ( (int) yHeight / 2);
        		
        		gg.drawLine(x1, y1, x2, y2);
        		
        		y2 = y2 + ( (int) yHeight);
        		
        		gg.drawLine(x1, y1, x2, y2);
        	}
        	
        	//This makes an arrow pointing right.
        	else if(direction == 3) {
        		x1 = x * (int) xWidth + (int) xWidth;
        		y1 = y * (int) yHeight + ( (int) yHeight / 2);
        		
        		x2 = x1 - (int) xWidth;
        		y2 = y1 - ((int) yHeight / 2);
        		
        		gg.drawLine(x1, y1, x2, y2);
        		
        		y2 = y2 + (int) yHeight;
        		
        		gg.drawLine(x1, y1, x2, y2);
        	}
        	
        	//This makes an intersection.
        	else if(direction == 4) {
        		x1 = x * (int) xWidth + ( (int) xWidth / 2);
        		y1 = y * (int) yHeight + ( (int) yHeight - ( (int) yHeight / 8 ) );
        		
        		gg.drawString("" + direction, x1, y1);
        	}
        }

            //This is where the vertical lines are printed.
            gg.setColor(Color.BLACK);
            for(i = 0; i < width; i++) {
                xstart = i*(windowWidth/width);
                gg.drawLine(xstart, 0, xstart, windowHeight);	//x1, y1, x2, y2
            }
            
            //This is where the horizontal lines are printed.
            gg.setColor(Color.BLACK);
            for(i = 0; i < height; i++) {
                ystart = i*(windowHeight/height);
                gg.drawLine(0, ystart, windowWidth, ystart);		
            }
            
            //This is where the walls are printed.
            gg.setColor(Color.BLACK);
            for(i = 0; i < wallList.size(); i++) {
            	x1 = wallList.get(i).getX() * (int) xWidth;
            	y1 = wallList.get(i).getY() * (int) yHeight;
            	x2 = (int) xWidth;
            	y2 = (int) yHeight;
            	
            	gg.fillRect(x1, y1, x2, y2);
            }
            
            //This is where the cars are printed.
            for(i = 0; i < carList.size(); i++) {
            	x = carList.get(i).getX();
            	y = carList.get(i).getY();
            	
            	gg.setColor(carList.get(i).getColor());
            	
            	x1 = x * (int) xWidth + ( (int) xWidth / 4);
            	y1 = y * (int) yHeight + ( (int) yHeight / 4);
            	
            	gg.fillRect(x1, y1, (int) xWidth / 2, (int) yHeight / 2);
            }
            
            //This is where the bus are painted.
            for(i = 0; i < busList.size(); i++) {
	        	x = busList.get(i).getX();
	        	y = busList.get(i).getY();
	        	x2 = busList.get(i).getX2();
	        	y2 = busList.get(i).getY2();
	        	
	        	gg.setColor(busList.get(i).getColor());
	        	
	        	x1 = x * (int) xWidth + ( (int) xWidth / 4);
	        	y1 = y * (int) yHeight + ( (int) yHeight / 4);
	        	
	        	//Paint this if the bus is going SOUTH.
	        	if(x == x2 && y > y2) {
	        		gg.fillRect(x1, y1 - (int) yHeight, (int) xWidth / 2, 3*((int) yHeight / 2));
	        	}
	        	
	        	//Paint this if the bus is going NORTH.
	        	else if(x == x2 && y < y2) {
	        		gg.fillRect(x1, y1, (int) xWidth / 2, 3 * ((int) yHeight / 2));
	        	}
	        	
	        	//Paint this if the bus is going EAST.
	        	else if(x > x2 && y == y2) {
	        		gg.fillRect(x1 - (int) xWidth, y1, 3 * ((int) xWidth / 2), (int) yHeight / 2);
	        	}
	        	
	        	//Paint this if the bus is going WEST.
	        	else if(x < x2 && y == y2) {
	        		gg.fillRect(x1, y1, 3*((int) xWidth / 2), (int) yHeight / 2);
	        	}
	        	else
	        		gg.fillRect(x1, y1, (int) xWidth / 2, (int) yHeight / 2);
            }
            
            repaint();
	}
}
