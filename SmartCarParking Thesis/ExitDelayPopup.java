//package payment;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;

public class ExitDelayPopup {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public void extrtime() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ExitDelayPopup window = new ExitDelayPopup();
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
	public ExitDelayPopup() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("CarParkingManagementSystem");
		frame.setBounds(100, 100, 600, 450);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblExtraTimeAdded = new JLabel("Extra Time Added Please, Pay The Bill");
		lblExtraTimeAdded.setBounds(196, 123, 236, 32);
		frame.getContentPane().add(lblExtraTimeAdded);
		
		JButton ok = new JButton("Ok");
		ok.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		ok.setBounds(239, 236, 89, 23);
		frame.getContentPane().add(ok);
	}

}
