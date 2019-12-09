//package payment;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;

public class CarValidityPopup {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void validiy() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CarValidityPopup window = new CarValidityPopup();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public CarValidityPopup() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("CarParkingManagementSystem");
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblSetTime = new JLabel("Set Time");
		lblSetTime.setBounds(59, 114, 88, 26);
		frame.getContentPane().add(lblSetTime);
		
		JTextField settime = new JTextField();
		settime.setBounds(171, 116, 148, 23);
		frame.getContentPane().add(settime);
		settime.setColumns(10);
		
		JLabel lblMin = new JLabel("min");
		lblMin.setBounds(329, 120, 46, 14);
		frame.getContentPane().add(lblMin);
		
		JButton ok = new JButton("ok");
		ok.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		ok.setBounds(169, 202, 89, 23);
		frame.getContentPane().add(ok);
	}

}
