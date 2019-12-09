//package payment;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;

public class PayBillPopup {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public void payfast() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PayBillPopup window = new PayBillPopup();
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
	public PayBillPopup() {
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
		
		JLabel lblPayTheBill = new JLabel("Pay The Bill First");
		lblPayTheBill.setBounds(239, 112, 119, 45);
		frame.getContentPane().add(lblPayTheBill);
		
		JButton ok = new JButton("Ok");
		ok.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		ok.setBounds(239, 209, 89, 23);
		frame.getContentPane().add(ok);
	}

}
