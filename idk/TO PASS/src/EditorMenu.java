import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.JSeparator;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JToolBar;
import javax.swing.JInternalFrame;
import javax.swing.JDesktopPane;
import javax.swing.JFileChooser;
import javax.swing.JToggleButton;
import org.eclipse.wb.swing.FocusTraversalOnArray;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.Font;

/**
 * This class provides the settings for the menu where the user can edit the components of the simulation.
 */
public class EditorMenu extends JFrame{

	private JPanel contentPane;
	private String mapName;
	private int mouseX;
	private int mouseY;
	
	EditorModel m = new EditorModel();
	MainMenu mm;

	private JTextField roadX;
	private JTextField wallY;
	private JTextField wallX;
	private JTextField roadY;
	private JTextField spawnerX;
	private JTextField spawnerY;
	private JTextField spawnerRate;
	private JTextField trafficLightX;
	private JTextField trafficLightY;
	private JTextField trafficLightRate;
	private JTextField dropOffX;
	private JTextField dropOffY;
	private JTextField dropOffRate;
	private JTextField width;
	private JTextField height;
	private JTextField roadTileX;
	private JTextField roadTileY;
	
	private boolean running;
	
	/**
	 * Constructor method for EditorMenu class
	 */
	public EditorMenu() {
		setResizable(false);
		setTitle("Editor Menu");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 700, 600);
		contentPane = new JPanel();
		contentPane.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if(e.getButton() == e.BUTTON3) {
					mouseX = m.getMouseX();
					mouseY = m.getMouseY();
					
					roadX.setText(mouseX + "");
					wallX.setText(mouseX + "");
					spawnerX.setText(mouseX + "");
					trafficLightX.setText(mouseX + "");
					dropOffX.setText(mouseX + "");
					roadTileX.setText(mouseX + "");
					
					roadY.setText(mouseY + "");
					wallY.setText(mouseY + "");
					spawnerY.setText(mouseY + "");
					trafficLightY.setText(mouseY + "");
					dropOffY.setText(mouseY + "");
					roadTileY.setText(mouseY + "");
				}
			}
		});
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);

		JButton newButton = new JButton("New");
		JButton clear = new JButton("Clear");
		JButton deselect = new JButton("Deselect");
		JButton display = new JButton("Display");
		
		JButton addRoadTile = new JButton("Add");
		JButton addDropOff = new JButton("Add");
		
		JButton removeRoad = new JButton("Remove");
		JButton removeWall = new JButton("Remove");
		JButton removeSpawner = new JButton("Remove");
		JButton removeDropOff = new JButton("Remove");
		JButton removeTrafficLight = new JButton("Remove");
		JButton removeRoadTile = new JButton("Remove");
		
		JLabel message = new JLabel("");
		message.setBounds(10, 535, 660, 25);
		contentPane.add(message);
		
		JLabel mapNameLabel = new JLabel("no file selected");
		mapNameLabel.setFont(new Font("Tahoma", Font.ITALIC, 11));
		mapNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		mapNameLabel.setBounds(10, 27, 100, 25);
		contentPane.add(mapNameLabel);
		
		JButton roadDirection = new JButton("Up");
		roadDirection.setFont(new Font("Tahoma", Font.PLAIN, 10));
		roadDirection.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(roadDirection.getText() == "Up")
					roadDirection.setText("Down");
				else if(roadDirection.getText() == "Down")
					roadDirection.setText("Left");
				else if(roadDirection.getText() == "Left")
					roadDirection.setText("Right");
				else if(roadDirection.getText() == "Right")
					roadDirection.setText("Intersection");
				else if(roadDirection.getText() == "Intersection")
					roadDirection.setText("Up");
			}
		});
		roadDirection.setBounds(230, 136, 100, 25);
		contentPane.add(roadDirection);
		
		JButton addRoad = new JButton("Add");
		addRoad.setEnabled(false);
		addRoad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(roadDirection.getText() == "Up")
					m.getMap().addRoad(Integer.parseInt(roadX.getText()), Integer.parseInt(roadY.getText()), 0);
				else if(roadDirection.getText() == "Down")
					m.getMap().addRoad(Integer.parseInt(roadX.getText()), Integer.parseInt(roadY.getText()), 1);
				else if(roadDirection.getText() == "Left")
					m.getMap().addRoad(Integer.parseInt(roadX.getText()), Integer.parseInt(roadY.getText()), 2);
				else if(roadDirection.getText() == "Right")
					m.getMap().addRoad(Integer.parseInt(roadX.getText()), Integer.parseInt(roadY.getText()), 3);
				else if(roadDirection.getText() == "Intersection")
					m.getMap().addRoad(Integer.parseInt(roadX.getText()), Integer.parseInt(roadY.getText()), 4);
			}
		});
		addRoad.setBounds(230, 171, 100, 25);
		contentPane.add(addRoad);
		
		JButton addWall = new JButton("Add");
		addWall.setEnabled(false);
		addWall.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				m.getMap().addWall(Integer.parseInt(wallX.getText()), Integer.parseInt(wallY.getText()));
			}
		});
		addWall.setBounds(400, 171, 100, 25);
		contentPane.add(addWall);
		
		JButton addSpawner = new JButton("Add");
		addSpawner.setEnabled(false);
		addSpawner.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				m.getMap().addSpawner(Integer.parseInt(spawnerX.getText()), Integer.parseInt(spawnerY.getText()), Integer.parseInt(spawnerRate.getText()));
			}
		});
		addSpawner.setBounds(570, 171, 100, 25);
		contentPane.add(addSpawner);
		
		JButton trafficLightStart = new JButton("Go");
		trafficLightStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(trafficLightStart.getText() == "Go")
					trafficLightStart.setText("Stop");
				else
					trafficLightStart.setText("Go");
			}
		});
		trafficLightStart.setBounds(400, 423, 100, 25);
		contentPane.add(trafficLightStart);
		
		JButton addTrafficLight = new JButton("Add");
		addTrafficLight.setEnabled(false);
		addTrafficLight.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(trafficLightStart.getText() == "Go")
					m.getMap().addTrafficLight(Integer.parseInt(trafficLightX.getText()), Integer.parseInt(trafficLightY.getText()), Integer.parseInt(trafficLightRate.getText()), 0);
				else
					m.getMap().addTrafficLight(Integer.parseInt(trafficLightX.getText()), Integer.parseInt(trafficLightY.getText()), Integer.parseInt(trafficLightRate.getText()), 1);
			}
		});
		addTrafficLight.setBounds(400, 459, 100, 25);
		contentPane.add(addTrafficLight);
		
		JLabel lblAddSpawner = new JLabel("Add Spawner");
		lblAddSpawner.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblAddSpawner.setHorizontalAlignment(SwingConstants.CENTER);
		lblAddSpawner.setBounds(570, 27, 100, 25);
		contentPane.add(lblAddSpawner);
		
		JLabel lblAddWall = new JLabel("Add Wall");
		lblAddWall.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblAddWall.setHorizontalAlignment(SwingConstants.CENTER);
		lblAddWall.setBounds(397, 32, 88, 14);
		contentPane.add(lblAddWall);
		
		JLabel lblAddDropOff = new JLabel("Add Drop Off");
		lblAddDropOff.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblAddDropOff.setHorizontalAlignment(SwingConstants.CENTER);
		lblAddDropOff.setBounds(230, 279, 100, 25);
		contentPane.add(lblAddDropOff);
		
		JLabel lblAddTrafficLight = new JLabel("Add Traffic Light");
		lblAddTrafficLight.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblAddTrafficLight.setHorizontalAlignment(SwingConstants.CENTER);
		lblAddTrafficLight.setBounds(400, 279, 100, 25);
		contentPane.add(lblAddTrafficLight);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 694, 21);
		contentPane.add(menuBar);
		
		JMenu mnTools = new JMenu("File");
		menuBar.add(mnTools);
		
		JButton scan = new JButton("Scan");
		scan.setEnabled(false);
		scan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if(!m.isEmpty(mapName)) {
						m.setMapName(mapName);
						try {
							m.setMap();
						} catch (URISyntaxException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
					else {
						message.setText("file is empty");
					}
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				m.displayMap();
				
				addRoad.setEnabled(true);
				addWall.setEnabled(true);
				addSpawner.setEnabled(true);
				addDropOff.setEnabled(true);
				addRoadTile.setEnabled(true);
				addTrafficLight.setEnabled(true);
				removeRoad.setEnabled(true);
				removeWall.setEnabled(true);
				removeSpawner.setEnabled(true);
				removeDropOff.setEnabled(true);
				removeRoadTile.setEnabled(true);
				removeTrafficLight.setEnabled(true);
				
				scan.setEnabled(false);
				newButton.setEnabled(true);
				display.setEnabled(true);
				clear.setEnabled(true);
				deselect.setEnabled(true);
				
				running = true;
			}
		});
		
		JMenuItem mntmSave = new JMenuItem("Save");
		mntmSave.setEnabled(false);
		mntmSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					m.save();
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				} catch (UnsupportedEncodingException e1) {
					e1.printStackTrace();
				}
			}
		});
		mnTools.add(mntmSave);
		
		JMenu mnSelectMap = new JMenu("Select Map");
		menuBar.add(mnSelectMap);
		
		JMenuItem map1 = new JMenuItem("Map 1");
		mnSelectMap.add(map1);
		
		JMenuItem map2 = new JMenuItem("Map 2");
		mnSelectMap.add(map2);
		
		JMenuItem map3 = new JMenuItem("Map 3");
		mnSelectMap.add(map3);
		
		JMenuItem map4 = new JMenuItem("Map 4");
		mnSelectMap.add(map4);
		
		JMenuItem map5 = new JMenuItem("Map 5");
		mnSelectMap.add(map5);
		map5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mapNameLabel.setText("map_005.txt");
				mapName = mapNameLabel.getText();
				message.setText("map_005.txt selected");
				scan.setEnabled(true);
				mntmSave.setEnabled(true);
			}
		});
		map4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mapNameLabel.setText("map_004.txt");
				mapName = mapNameLabel.getText();
				message.setText("map_004.txt selected");
				scan.setEnabled(true);
				mntmSave.setEnabled(true);
			}
		});
		map3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mapNameLabel.setText("map_003.txt");
				mapName = mapNameLabel.getText();
				message.setText("map_003.txt selected");
				scan.setEnabled(true);
				mntmSave.setEnabled(true);
			}
		});
		map2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mapNameLabel.setText("map_002.txt");
				mapName = mapNameLabel.getText();
				message.setText("map_002.txt selected");
				scan.setEnabled(true);
				mntmSave.setEnabled(true);
			}
		});
		map1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mapNameLabel.setText("map_001.txt");
				mapName = mapNameLabel.getText();
				message.setText("map_001.txt selected");
				scan.setEnabled(true);
				mntmSave.setEnabled(true);
			}
		});
		
		JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);
		
		JMenuItem mntmHelp = new JMenuItem("Help Contents");
		mntmHelp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane frame = new JOptionPane();
				JOptionPane.showMessageDialog(frame,
						"STEPS TO EDIT:\n" +
						"1. Select a map from the Select Menu.\n" +
						"2. Scan the map.\n" +
						"3. To display the map, press the display button\n" +
						"4. You can use the add and remove buttons to add the respective entities\n" +
						"5. If you would left click on the map and right click in the Layout Manager Menu, it would automatically input the x and y for you." +
						"6. Don't forget to save!"
											);
			}
		});
		mnHelp.add(mntmHelp);
		
		display.setEnabled(false);
		display.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				m.toggleVisibility();
			}
		});
		
		scan.setBounds(10, 63, 100, 25);
		contentPane.add(scan);
		display.setBounds(10, 99, 100, 25);
		contentPane.add(display);
		
		roadX = new JTextField();
		roadX.setBounds(230, 63, 100, 25);
		contentPane.add(roadX);
		roadX.setColumns(10);
		
		wallY = new JTextField();
		wallY.setBounds(400, 99, 100, 25);
		contentPane.add(wallY);
		wallY.setColumns(10);
		
		wallX = new JTextField();
		wallX.setBounds(400, 63, 100, 25);
		contentPane.add(wallX);
		wallX.setColumns(10);
		
		JLabel lblY = new JLabel("y");
		lblY.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblY.setHorizontalAlignment(SwingConstants.RIGHT);
		lblY.setBounds(120, 99, 100, 25);
		contentPane.add(lblY);
		
		clear.setEnabled(false);
		clear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				m.getMap().clear();
			}
		});
		clear.setBounds(10, 171, 100, 25);
		contentPane.add(clear);
		
		roadY = new JTextField();
		roadY.setBounds(230, 99, 100, 25);
		contentPane.add(roadY);
		roadY.setColumns(10);
		
		JLabel lblX = new JLabel("x");
		lblX.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblX.setHorizontalAlignment(SwingConstants.RIGHT);
		lblX.setBounds(120, 63, 100, 25);
		contentPane.add(lblX);
		
		JLabel lblDirection = new JLabel("direction");
		lblDirection.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblDirection.setHorizontalAlignment(SwingConstants.RIGHT);
		lblDirection.setBounds(120, 135, 100, 25);
		contentPane.add(lblDirection);
		
		JLabel label = new JLabel("x");
		label.setFont(new Font("Tahoma", Font.PLAIN, 10));
		label.setHorizontalAlignment(SwingConstants.RIGHT);
		label.setBounds(340, 63, 50, 25);
		contentPane.add(label);
		
		JLabel lblY_1 = new JLabel("y");
		lblY_1.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblY_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblY_1.setBounds(340, 99, 50, 25);
		contentPane.add(lblY_1);
		
		spawnerX = new JTextField();
		spawnerX.setColumns(10);
		spawnerX.setBounds(570, 63, 100, 25);
		contentPane.add(spawnerX);
		
		spawnerY = new JTextField();
		spawnerY.setColumns(10);
		spawnerY.setBounds(570, 99, 100, 25);
		contentPane.add(spawnerY);
		
		JLabel label_1 = new JLabel("x");
		label_1.setFont(new Font("Tahoma", Font.PLAIN, 10));
		label_1.setHorizontalAlignment(SwingConstants.RIGHT);
		label_1.setBounds(510, 63, 50, 25);
		contentPane.add(label_1);
		
		JLabel label_2 = new JLabel("y");
		label_2.setFont(new Font("Tahoma", Font.PLAIN, 10));
		label_2.setHorizontalAlignment(SwingConstants.RIGHT);
		label_2.setBounds(510, 99, 50, 25);
		contentPane.add(label_2);
		
		spawnerRate = new JTextField();
		spawnerRate.setColumns(10);
		spawnerRate.setBounds(570, 135, 100, 25);
		contentPane.add(spawnerRate);
		
		JLabel lblRate = new JLabel("rate");
		lblRate.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblRate.setHorizontalAlignment(SwingConstants.RIGHT);
		lblRate.setBounds(510, 135, 50, 25);
		contentPane.add(lblRate);
		
		trafficLightX = new JTextField();
		trafficLightX.setColumns(10);
		trafficLightX.setBounds(400, 315, 100, 25);
		contentPane.add(trafficLightX);
		
		trafficLightY = new JTextField();
		trafficLightY.setColumns(10);
		trafficLightY.setBounds(400, 351, 100, 25);
		contentPane.add(trafficLightY);
		
		JLabel label_3 = new JLabel("x");
		label_3.setFont(new Font("Tahoma", Font.PLAIN, 10));
		label_3.setHorizontalAlignment(SwingConstants.RIGHT);
		label_3.setBounds(340, 315, 50, 25);
		contentPane.add(label_3);
		
		JLabel label_4 = new JLabel("y");
		label_4.setFont(new Font("Tahoma", Font.PLAIN, 10));
		label_4.setHorizontalAlignment(SwingConstants.RIGHT);
		label_4.setBounds(340, 351, 50, 25);
		contentPane.add(label_4);
		
		trafficLightRate = new JTextField();
		trafficLightRate.setColumns(10);
		trafficLightRate.setBounds(400, 387, 100, 25);
		contentPane.add(trafficLightRate);
		
		JLabel lblRate_1 = new JLabel("rate");
		lblRate_1.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblRate_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblRate_1.setBounds(340, 387, 50, 25);
		contentPane.add(lblRate_1);
		
		JLabel lblStart = new JLabel("start");
		lblStart.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblStart.setHorizontalAlignment(SwingConstants.RIGHT);
		lblStart.setBounds(340, 423, 50, 25);
		contentPane.add(lblStart);
		
		dropOffX = new JTextField();
		dropOffX.setColumns(10);
		dropOffX.setBounds(230, 315, 100, 25);
		contentPane.add(dropOffX);
		
		dropOffY = new JTextField();
		dropOffY.setColumns(10);
		dropOffY.setBounds(230, 351, 100, 25);
		contentPane.add(dropOffY);
		
		dropOffRate = new JTextField();
		dropOffRate.setColumns(10);
		dropOffRate.setBounds(230, 387, 100, 25);
		contentPane.add(dropOffRate);
		
		JLabel label_5 = new JLabel("x");
		label_5.setFont(new Font("Tahoma", Font.PLAIN, 10));
		label_5.setHorizontalAlignment(SwingConstants.RIGHT);
		label_5.setBounds(120, 315, 100, 25);
		contentPane.add(label_5);
		
		JLabel label_6 = new JLabel("y");
		label_6.setFont(new Font("Tahoma", Font.PLAIN, 10));
		label_6.setHorizontalAlignment(SwingConstants.RIGHT);
		label_6.setBounds(134, 352, 86, 23);
		contentPane.add(label_6);
		
		JButton btnMenu = new JButton("Menu");
		btnMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(running) {
					m.setVisible(false);
				}
				
				running = false;
				dispose();
				mm = new MainMenu();
				mm.run();
				
			}
		});
		btnMenu.setBounds(10, 495, 100, 25);
		contentPane.add(btnMenu);
		
		newButton.setEnabled(false);
		newButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					m.newMap(mapName, Integer.parseInt(width.getText()), Integer.parseInt(height.getText()));
				} catch (NumberFormatException | FileNotFoundException | UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		newButton.setBounds(10, 315, 100, 25);
		contentPane.add(newButton);
		
		JLabel lblRate_2 = new JLabel("rate");
		lblRate_2.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblRate_2.setHorizontalAlignment(SwingConstants.RIGHT);
		lblRate_2.setBounds(120, 387, 100, 25);
		contentPane.add(lblRate_2);
		

		addDropOff.setEnabled(false);
		addDropOff.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				m.getMap().addDropOff(Integer.parseInt(dropOffX.getText()), Integer.parseInt(dropOffY.getText()), Integer.parseInt(dropOffRate.getText()));
			}
		});
		addDropOff.setBounds(230, 459, 100, 25);
		contentPane.add(addDropOff);
		
		width = new JTextField();
		width.setColumns(10);
		width.setBounds(70, 243, 40, 25);
		contentPane.add(width);
		
		JLabel label_7 = new JLabel("Add Road");
		label_7.setFont(new Font("Tahoma", Font.PLAIN, 10));
		label_7.setHorizontalAlignment(SwingConstants.CENTER);
		label_7.setBounds(230, 27, 100, 25);
		contentPane.add(label_7);
		
		height = new JTextField();
		height.setColumns(10);
		height.setBounds(70, 279, 40, 25);
		contentPane.add(height);
		
		JLabel lblWidth = new JLabel("width");
		lblWidth.setHorizontalAlignment(SwingConstants.RIGHT);
		lblWidth.setBounds(10, 243, 50, 25);
		contentPane.add(lblWidth);
		
		JLabel lblY_2 = new JLabel("height");
		lblY_2.setHorizontalAlignment(SwingConstants.RIGHT);
		lblY_2.setBounds(10, 279, 50, 25);
		contentPane.add(lblY_2);
		
		removeRoad.setEnabled(false);
		removeRoad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				m.getMap().removeRoad(Integer.parseInt(roadX.getText()), Integer.parseInt(roadY.getText()));
			}
		});
		removeRoad.setBounds(230, 207, 100, 25);
		contentPane.add(removeRoad);

		removeWall.setEnabled(false);
		removeWall.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				m.getMap().removeWall(Integer.parseInt(wallX.getText()), Integer.parseInt(wallY.getText()));
			}
		});
		removeWall.setBounds(400, 207, 100, 25);
		contentPane.add(removeWall);

		removeSpawner.setEnabled(false);
		removeSpawner.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				m.getMap().removeSpawner(Integer.parseInt(spawnerX.getText()), Integer.parseInt(spawnerY.getText()));
			}
		});
		removeSpawner.setBounds(570, 208, 100, 25);
		contentPane.add(removeSpawner);

		removeDropOff.setEnabled(false);
		removeDropOff.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				m.getMap().removeDropOff(Integer.parseInt(dropOffX.getText()), Integer.parseInt(dropOffY.getText()));
			}
		});
		removeDropOff.setBounds(230, 495, 100, 25);
		contentPane.add(removeDropOff);

		removeTrafficLight.setEnabled(false);
		removeTrafficLight.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				m.getMap().removeTrafficLight(Integer.parseInt(trafficLightX.getText()), Integer.parseInt(trafficLightY.getText()));
			}
		});
		removeTrafficLight.setBounds(400, 495, 100, 25);
		contentPane.add(removeTrafficLight);
		
		JLabel lblAddRoadTile = new JLabel("Add Road Condition");
		lblAddRoadTile.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblAddRoadTile.setHorizontalAlignment(SwingConstants.CENTER);
		lblAddRoadTile.setBounds(570, 279, 100, 25);
		contentPane.add(lblAddRoadTile);
		
		roadTileX = new JTextField();
		roadTileX.setColumns(10);
		roadTileX.setBounds(570, 315, 100, 25);
		contentPane.add(roadTileX);
		
		roadTileY = new JTextField();
		roadTileY.setColumns(10);
		roadTileY.setBounds(570, 351, 100, 25);
		contentPane.add(roadTileY);
		
		JLabel label_9 = new JLabel("x");
		label_9.setFont(new Font("Tahoma", Font.PLAIN, 10));
		label_9.setHorizontalAlignment(SwingConstants.RIGHT);
		label_9.setBounds(510, 315, 50, 25);
		contentPane.add(label_9);
		
		JLabel label_10 = new JLabel("y");
		label_10.setFont(new Font("Tahoma", Font.PLAIN, 10));
		label_10.setHorizontalAlignment(SwingConstants.RIGHT);
		label_10.setBounds(510, 351, 50, 25);
		contentPane.add(label_10);
		
		JLabel lblCondition = new JLabel("condition");
		lblCondition.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblCondition.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCondition.setBounds(510, 387, 50, 25);
		contentPane.add(lblCondition);
		
		JButton roadTileCondition = new JButton("Damaged");
		roadTileCondition.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(roadTileCondition.getText() == "Damaged")
					roadTileCondition.setText("Flooded");
				else if(roadTileCondition.getText() == "Flooded")
					roadTileCondition.setText("Closed");
				else if(roadTileCondition.getText() == "Closed")
					roadTileCondition.setText("Damaged");
			}
		});
		roadTileCondition.setBounds(570, 387, 100, 25);
		

		addRoadTile.setEnabled(false);
		addRoadTile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(roadTileCondition.getText() == "Damaged")
					m.getMap().addRoadTile(Integer.parseInt(roadTileX.getText()), Integer.parseInt(roadTileY.getText()), 1);
				else if(roadTileCondition.getText() == "Flooded")
					m.getMap().addRoadTile(Integer.parseInt(roadTileX.getText()), Integer.parseInt(roadTileY.getText()), 2);
				else if(roadTileCondition.getText() == "Closed")
					m.getMap().addRoadTile(Integer.parseInt(roadTileX.getText()), Integer.parseInt(roadTileY.getText()), 3);
			}
		});
		addRoadTile.setBounds(570, 459, 100, 25);
		contentPane.add(addRoadTile);
		
		removeRoadTile.setEnabled(false);
		removeRoadTile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				m.getMap().removeRoadTile(Integer.parseInt(roadTileX.getText()), Integer.parseInt(roadTileY.getText()));
			}
		});
		removeRoadTile.setBounds(570, 495, 100, 25);
		contentPane.add(removeRoadTile);
		contentPane.add(roadTileCondition);
		
		deselect.setEnabled(false);
		deselect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				scan.setEnabled(false);
				mapNameLabel.setText("no map selected");
				mapName = mapNameLabel.getText();
				message.setText("no file selected");
				
				addRoad.setEnabled(false);
				addWall.setEnabled(false);
				addSpawner.setEnabled(false);
				addDropOff.setEnabled(false);
				addRoadTile.setEnabled(false);
				addTrafficLight.setEnabled(false);
				removeRoad.setEnabled(false);
				removeWall.setEnabled(false);
				removeSpawner.setEnabled(false);
				removeDropOff.setEnabled(false);
				removeRoadTile.setEnabled(false);
				removeTrafficLight.setEnabled(false);
				
				deselect.setEnabled(false);
				display.setEnabled(false);
				clear.setEnabled(false);
				newButton.setEnabled(false);
				
				if(running) {
					m.setVisible(false);
				}
				
				running = false;
				
				running = false;
			}
		});
		deselect.setBounds(10, 136, 100, 25);
		contentPane.add(deselect);
		contentPane.setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{message, mapNameLabel, addWall, addSpawner, lblAddDropOff, trafficLightStart, addRoad, addTrafficLight, lblAddSpawner, lblAddWall, lblAddTrafficLight, menuBar, mnTools, mntmSave, map1, map2, map3, scan, display, roadX, wallY, wallX, lblY, clear, roadY, lblX, lblDirection, label, lblY_1, spawnerX, spawnerY, label_1, label_2, spawnerRate, lblRate, trafficLightX, trafficLightY, label_3, label_4, trafficLightRate, lblRate_1, lblStart, dropOffX, dropOffY, dropOffRate, label_5, label_6, btnMenu, newButton}));
	}
	
	/**
	 * This method lets the user edit elements of the traffic simulation
	 */
	public void run() {
		EventQueue.invokeLater(new Runnable() {
		public void run() {
			try {
				EditorMenu frame = new EditorMenu();
				frame.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	});
	}
}
