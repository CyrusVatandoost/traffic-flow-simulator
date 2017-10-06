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
		
		int xWidth = windowWidth / width;
		int yHeight = windowHeight / height;
        int xstart, ystart, i, x, y, x1, y1, x2, y2;
        
        ArrayList <Integer> widthList = new ArrayList <Integer> ();
        ArrayList <Integer> heightList = new ArrayList <Integer> ();
        
        int extraWidth = windowWidth - (xWidth * width);
        int extraHeight = windowHeight - (yHeight * height);
        
		if(extraWidth < 0)
			extraWidth = width - 1;
		
		if(extraHeight < 0)
			extraHeight = height - 1;
		
		widthList.add(0);
		heightList.add(0);
		
		System.out.println("width = " + width + " windowWidth = " + windowWidth + " xWidth = " + xWidth + " extraWidth = " + extraWidth);
		
		int num1 = 0;
		
		for(i = 0; i <= width; i++) {
			if(extraWidth > 0)
				num1 = num1 + 1;
			widthList.add(num1);
			
			if(extraWidth > 0)
				extraWidth--;
		}
		
		num1 = 0;
		
		for(i = 0; i < height; i++) {
			if(extraHeight > 0)
				num1 = num1 + 1;
			heightList.add(num1);
			
			if(extraHeight > 0)
				extraHeight--;
		}
        
        //This is where the road tiles are pain1ted.
        for(i = 0; i < roadTileList.size(); i++) {
        	x = roadTileList.get(i).getX();
        	y = roadTileList.get(i).getY();

        	x1 = x * xWidth;
        	y1 = y * yHeight;
        	
        	if(roadTileList.get(i).getCondition() == 1) {
        		gg.setColor(Color.LIGHT_GRAY);
            	gg.fillRect(x1, y1, xWidth, yHeight);
        	}
        	else if(roadTileList.get(i).getCondition() == 2) {
        		gg.setColor(Color.CYAN);
            	gg.fillRect(x1, y1, xWidth, yHeight);
        	}
        	else if(roadTileList.get(i).getCondition() == 3) {
        		gg.setColor(Color.DARK_GRAY);
            	gg.fillRect(x1, y1, xWidth, yHeight);
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
        	
        	x1 = x * xWidth;
        	y1 = y * yHeight;
        	
        	gg.fillRect(x1, y1, xWidth, yHeight);
        }
        
        //This is where the traffic lights are painted.
        for(i = 0; i < trafficLightList.size(); i++) {
        	x = trafficLightList.get(i).getX();
        	y = trafficLightList.get(i).getY();
        	
        	if(trafficLightList.get(i).getGo())
        		gg.setColor(Color.GREEN);
        	else
        		gg.setColor(Color.RED);
        	
        	x1 = x * xWidth;
        	y1 = y * yHeight;
        	
        	gg.fillRect(x1, y1, xWidth, yHeight);
        }
        
        //This is where the spawners are created.
        gg.setColor(Color.PINK);
        for(i = 0; i < spawnerList.size(); i++) {
        	x = spawnerList.get(i).getX();
        	y = spawnerList.get(i).getY();
        	
        	x1 = x * xWidth;
        	y1 = y * yHeight + ( yHeight / 2);
        	
        	x2 = x1 + xWidth;
        	y2 = y1;
        	
        	gg.drawLine(x1, y1, x2, y2);
        	
        	x1 = x1 + ( xWidth / 2);
        	y1 = y1 - ( yHeight / 2);
        	
        	x2 = x1;
        	y2 = y1 + yHeight;
        	
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
        		x1 = x * xWidth;
        		y1 = y * yHeight;
        		x2 = x1 + xWidth;
        		y2 = y1 + yHeight;
        				
        		gg.drawLine(x1, y1, x2, y2);
        		
        		y1 = y2;
        		y2 = y1 - yHeight;
        		
        		gg.drawLine(x1, y1, x2, y2);
        	}

        	//This makes an arrow pointing up.
        	else if(direction == 0) {
        		x1 = x * xWidth + ( xWidth / 2);
        		y1 = y * yHeight;
        		
        		x2 = x1 - ( xWidth / 2);
        		y2 = y1 + ( yHeight);
        		
        		gg.drawLine(x1, y1, x2, y2);
        		
        		x2 = x2 + ( xWidth);

        		gg.drawLine(x1, y1, x2, y2);
        	}
        	
        	//This makes an arrow pointing down.
        	else if(direction == 1) {
        		x1 = x * xWidth + ( xWidth / 2);
        		y1 = y * yHeight + yHeight;
        		
        		x2 = x1 - ( xWidth / 2);
        		y2 = y1 - yHeight;
        		
        		gg.drawLine(x1, y1, x2, y2);
        		
        		x2 = x1 + ( xWidth / 2);
        		
        		gg.drawLine(x1, y1, x2, y2);
        	}
        	
        	//This makes an arrow pointing left.
        	else if(direction == 2) {
        		x1 = x * xWidth;
        		y1 = y * yHeight + ( yHeight / 2);
        		
        		x2 = x1 + xWidth;
        		y2 = y1 - ( yHeight / 2);
        		
        		gg.drawLine(x1, y1, x2, y2);
        		
        		y2 = y2 + (yHeight);
        		
        		gg.drawLine(x1, y1, x2, y2);
        	}
        	
        	//This makes an arrow pointing right.
        	else if(direction == 3) {
        		x1 = x * xWidth + xWidth;
        		y1 = y * yHeight + (yHeight / 2);
        		
        		x2 = x1 - xWidth;
        		y2 = y1 - (yHeight / 2);
        		
        		gg.drawLine(x1, y1, x2, y2);
        		
        		y2 = y2 + yHeight;
        		
        		gg.drawLine(x1, y1, x2, y2);
        	}
        	
        	//This makes an intersection.
        	else if(direction == 4) {
        		x1 = x * xWidth + (xWidth / 2);
        		y1 = y * yHeight + (yHeight - (yHeight / 8 ) );
        		
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
            	x1 = wallList.get(i).getX() * xWidth;
            	y1 = wallList.get(i).getY() * yHeight;
            	x2 = xWidth;
            	y2 = yHeight;
            	
            	gg.fillRect(x1, y1, x2, y2);
            }
            
            //This is where the cars are printed.
            for(i = 0; i < carList.size(); i++) {
            	x = carList.get(i).getX();
            	y = carList.get(i).getY();
            	
            	gg.setColor(carList.get(i).getColor());
            	
            	x1 = x * xWidth + (xWidth / 4);
            	y1 = y * yHeight + (yHeight / 4);
            	
            	gg.fillRect(x1, y1, xWidth / 2, yHeight / 2);
            }
            
            //This is where the bus are painted.
            for(i = 0; i < busList.size(); i++) {
	        	x = busList.get(i).getX();
	        	y = busList.get(i).getY();
	        	x2 = busList.get(i).getX2();
	        	y2 = busList.get(i).getY2();
	        	
	        	gg.setColor(busList.get(i).getColor());
	        	
	        	x1 = x * xWidth + (xWidth / 4);
	        	y1 = y * yHeight + (yHeight / 4);
	        	
	        	//Paint this if the bus is going SOUTH.
	        	if(x == x2 && y > y2) {
	        		gg.fillRect(x1, y1 - yHeight, xWidth / 2, 3 * (yHeight / 2));
	        	}
	        	
	        	//Paint this if the bus is going NORTH.
	        	else if(x == x2 && y < y2) {
	        		gg.fillRect(x1, y1, xWidth / 2, 3 * (yHeight / 2));
	        	}
	        	
	        	//Paint this if the bus is going EAST.
	        	else if(x > x2 && y == y2) {
	        		gg.fillRect(x1 - xWidth, y1, 3 * (xWidth / 2), yHeight / 2);
	        	}
	        	
	        	//Paint this if the bus is going WEST.
	        	else if(x < x2 && y == y2) {
	        		gg.fillRect(x1, y1, 3*(xWidth / 2), yHeight / 2);
	        	}
	        	else
	        		gg.fillRect(x1, y1, xWidth / 2, yHeight / 2);
            }
            
            repaint();
	}
}
