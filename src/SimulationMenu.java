import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.BoxLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import net.miginfocom.swing.MigLayout;
import java.awt.FlowLayout;
import javax.swing.JToggleButton;
import javax.swing.JSlider;
import javax.swing.JLabel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Insets;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JSpinner;
import javax.swing.JTree;
import javax.swing.SpinnerNumberModel;
import java.awt.event.InputMethodListener;
import java.awt.event.InputMethodEvent;
import javax.swing.JTextField;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;
import java.awt.CardLayout;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.SwingConstants;
import java.awt.Font;
/**
 * This class controls the Simulation Model.
 */
public class SimulationMenu extends JFrame {

	private MainMenu mm;
	private JPanel contentPane;
	private Simulation s = new Simulation();
	private boolean running;

	/**
	 * Constructor method for SimulationMenu class.
	 */
	public SimulationMenu() {
		setAutoRequestFocus(false);
		setResizable(false);
		setTitle("Traffic Flow Simulator");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 455, 220);
		setLocationRelativeTo(null);

		JMenuItem map1 = new JMenuItem("Map 1");
		JMenuItem map2 = new JMenuItem("Map 2");
		JMenuItem map3 = new JMenuItem("Map 3");
		JMenuItem map4 = new JMenuItem("Map 4");
		JMenuItem map5 = new JMenuItem("Map 5");
		
		JButton startButton = new JButton("Start");

		JButton stop = new JButton("Stop");
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("Select Map");
		menuBar.add(mnFile);
		
		JLabel mapName = new JLabel("no file selected");
		mapName.setFont(new Font("Tahoma", Font.ITALIC, 11));
		mapName.setBounds(10, 10, 100, 25);

		JLabel lblSpeed = new JLabel("Speed");
		lblSpeed.setBounds(10, 45, 100, 25);

		JLabel speedLabel = new JLabel("");
		speedLabel.setHorizontalAlignment(SwingConstants.CENTER);
		speedLabel.setBounds(10, 90, 200, 25);
		
		JSlider speed = new JSlider();
		speed.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				s.setTimeSpeed(speed.getValue());
				speedLabel.setText(speed.getValue() + "");
			}
		});
		speed.setValue(100);
		speed.setMinimum(1);
		speed.setMaximum(500);
		speed.setBounds(10, 70, 200, 25);
		
		startButton.setEnabled(false);
		startButton.setBounds(340, 10, 100, 25);
		startButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				s.setTimeSpeed(speed.getValue());
				s.runSimulation();
				startButton.setEnabled(false);
				running = true;
			}
		});
		
		JButton display = new JButton("Display");
		display.setEnabled(false);
		display.setBounds(340, 100, 100, 25);
		display.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				s.toggleVisibility();
			}
		});
		
		JButton next = new JButton("Next Step");
		next.setEnabled(false);
		next.setBounds(340, 70, 100, 25);
		next.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				s.nextStep();
			}
		});
		
		JButton pause = new JButton("Pause");
		pause.setEnabled(false);
		pause.setBounds(340, 40, 100, 25);
		pause.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				s.togglePausePlay();
				if(s.getPause()) {
					pause.setText("Play");
					next.setEnabled(true);
				}
				else {
					pause.setText("Pause");
					next.setEnabled(false);
				}
			}
		});
		
		stop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mapName.setText("no map selected");
				startButton.setEnabled(false);
				display.setEnabled(false);
				pause.setEnabled(false);
				stop.setEnabled(false);
				next.setEnabled(false);

				map1.setEnabled(true);
				map2.setEnabled(true);
				map3.setEnabled(true);
				map4.setEnabled(true);
				map5.setEnabled(true);
				
				if(running) {
					s.setVisible(false);
					s.stopSimulation();
				}
				
				running = false;
			}
		});
		stop.setEnabled(false);
		
		map1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				s.setMapName("map_001.txt");
				mapName.setText("map_001.txt");
				startButton.setEnabled(true);
				display.setEnabled(true);
				pause.setEnabled(true);
				stop.setEnabled(true);
				
				map1.setEnabled(false);
				map2.setEnabled(false);
				map3.setEnabled(false);
				map4.setEnabled(false);
				map5.setEnabled(false);
			}
		});
		mnFile.add(map1);
		
		map2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				s.setMapName("map_002.txt");
				mapName.setText("map_002.txt");
				startButton.setEnabled(true);
				display.setEnabled(true);
				pause.setEnabled(true);
				stop.setEnabled(true);
				
				map1.setEnabled(false);
				map2.setEnabled(false);
				map3.setEnabled(false);
				map4.setEnabled(false);
				map5.setEnabled(false);
			}
		});
		mnFile.add(map2);
		
		map3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				s.setMapName("map_003.txt");
				mapName.setText("map_003.txt");
				startButton.setEnabled(true);
				display.setEnabled(true);
				pause.setEnabled(true);
				stop.setEnabled(true);
				
				map1.setEnabled(false);
				map2.setEnabled(false);
				map3.setEnabled(false);
				map4.setEnabled(false);
				map5.setEnabled(false);
			}
		});
		mnFile.add(map3);
		
		map4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				s.setMapName("map_004.txt");
				mapName.setText("map_004.txt");
				startButton.setEnabled(true);
				display.setEnabled(true);
				pause.setEnabled(true);
				stop.setEnabled(true);
				
				map1.setEnabled(false);
				map2.setEnabled(false);
				map3.setEnabled(false);
				map4.setEnabled(false);
				map5.setEnabled(false);
			}
		});
		mnFile.add(map4);
		
		map5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				s.setMapName("map_005.txt");
				mapName.setText("map_005.txt");
				startButton.setEnabled(true);
				display.setEnabled(true);
				pause.setEnabled(true);
				stop.setEnabled(true);
				
				map1.setEnabled(false);
				map2.setEnabled(false);
				map3.setEnabled(false);
				map4.setEnabled(false);
				map5.setEnabled(false);
			}
		});
		mnFile.add(map5);
		
		JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);
		
		JMenuItem mntmHelp = new JMenuItem("Help Contents");
		mntmHelp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane frame = new JOptionPane();
				JOptionPane.showMessageDialog(frame,
						"STEPS TO RUN A MAP:\n" +
						"1. Please make sure that the map text files are on the same directory as the .jar file\n" +
						"2. Select a map from the Select Menu.\n" +
						"3. Start the map.\n" +
						"4. Use the buttons on the right to perform their assigned tasks as shown\n" + 
						"5. Click the Stop button before loading another map\n" +
						"6. Happy Simulating!"
											);
			}
		});
		mnHelp.add(mntmHelp);
		
		JMenuItem mntmAboutProgram = new JMenuItem("About Program");
		mntmAboutProgram.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane frame = new JOptionPane();
				JOptionPane.showMessageDialog(frame,
						"This program was coded by:\n" +
						"Cyrus A. Vatandoost Kakhki and Julius Ceasar Librada\n" +
						"as part of their requirement for OBJECTP."
											);
			}
		});
		mnHelp.add(mntmAboutProgram);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		contentPane.setLayout(null);
		contentPane.add(speedLabel);
		contentPane.add(startButton);
		contentPane.add(display);
		contentPane.add(pause);
		contentPane.add(next);
		contentPane.add(mapName);
		contentPane.add(speed);
		
		JButton btnMenu = new JButton("Menu");
		btnMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(running) {
					s.setVisible(false);
					s.stopSimulation();
				}
				dispose();
				mm = new MainMenu();
				mm.run();
			}
		});
		btnMenu.setBounds(10, 130, 100, 25);
		contentPane.add(btnMenu);
		contentPane.add(lblSpeed);
	
		stop.setBounds(340, 130, 100, 25);
		contentPane.add(stop);
	}

	/**
	 * This method runs the simulation using the settings.
	 */
	public void run() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SimulationMenu frame = new SimulationMenu();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
