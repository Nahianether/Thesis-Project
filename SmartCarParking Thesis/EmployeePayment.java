import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JRadioButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;

public class EmployeePayment {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EmployeePayment window = new EmployeePayment();
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
	public EmployeePayment() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("CarParkingManagementSystem");
		frame.setBounds(100, 100, 1500, 900);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JRadioButton radionormal = new JRadioButton("Normal");
		radionormal.setBounds(642, 268, 109, 23);
		frame.getContentPane().add(radionormal);
		
		JRadioButton radiovip = new JRadioButton("VIP");
		radiovip.setBounds(793, 268, 109, 23);
		frame.getContentPane().add(radiovip);
		
		JLabel lblCardNo = new JLabel("Card No :");
		lblCardNo.setBounds(573, 396, 69, 21);
		frame.getContentPane().add(lblCardNo);
		
		JTextField carno = new JTextField();
		carno.setBounds(681, 392, 152, 28);
		frame.getContentPane().add(carno);
		carno.setColumns(10);
		
		JRadioButton radioshowinfo = new JRadioButton("Show Info");
		radioshowinfo.setBounds(681, 548, 109, 23);
		frame.getContentPane().add(radioshowinfo);
		
		JRadioButton radiopaybill = new JRadioButton("Pay Bill");
		radiopaybill.setBounds(681, 606, 109, 23);
		frame.getContentPane().add(radiopaybill);
		
		JButton next = new JButton("Next");
		next.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		next.setBounds(681, 765, 109, 28);
		frame.getContentPane().add(next);
	}
}
