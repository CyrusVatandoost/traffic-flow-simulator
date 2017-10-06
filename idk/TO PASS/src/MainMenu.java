import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * This class displays the Main Menu where the user can choose to do two things which is run a map or alter and create a new map.
 */
public class MainMenu extends JFrame {

	private JPanel contentPane;
	private SimulationMenu m1 = new SimulationMenu();
	private EditorMenu m2 = new EditorMenu();

	/**
	 * Constructor method for Main Menu class
	 */
	public MainMenu() {
		setTitle("Traffic Flow Simulator");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);
		
		JLabel lblTrafficFlowSimulator = new JLabel("Traffic Flow Simulator");
		lblTrafficFlowSimulator.setFont(new Font("Tahoma", Font.PLAIN, 30));
		lblTrafficFlowSimulator.setBounds(5, 5, 429, 50);
		lblTrafficFlowSimulator.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblTrafficFlowSimulator);
		
		JLabel lblNewLabel = new JLabel("Cyrus A. Vatandoost Kakhki");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(5, 211, 429, 14);
		contentPane.add(lblNewLabel);
		
		JLabel lblJuliusCeasarLibrada = new JLabel("Julius Ceasar Librada");
		lblJuliusCeasarLibrada.setHorizontalAlignment(SwingConstants.CENTER);
		lblJuliusCeasarLibrada.setBounds(5, 236, 429, 14);
		contentPane.add(lblJuliusCeasarLibrada);
		
		JButton btnNewButton = new JButton("Simulator");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				m1.run();
			}
		});
		btnNewButton.setBounds(5, 66, 204, 134);
		contentPane.add(btnNewButton);
		
		JButton btnRunLayoutManager = new JButton("Layout Manager");
		btnRunLayoutManager.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				m2.run();
			}
		});
		btnRunLayoutManager.setBounds(224, 66, 210, 134);
		contentPane.add(btnRunLayoutManager);
	}
	
	/**
	 * This method runs the simulation based on the input of the user
	 */
	public void run() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainMenu frame = new MainMenu();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
