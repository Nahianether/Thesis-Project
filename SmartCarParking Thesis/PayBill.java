import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class PayBill {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PayBill window = new PayBill();
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
	public PayBill() {
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
		
		JButton payprint = new JButton("Pay & Print");
		payprint.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		payprint.setBounds(633, 639, 104, 29);
		frame.getContentPane().add(payprint);
		
		JLabel lblCardType = new JLabel("Card Type :");
		lblCardType.setBounds(504, 275, 104, 29);
		frame.getContentPane().add(lblCardType);
		
		JLabel lblHolderName = new JLabel("Holder Name :");
		lblHolderName.setBounds(504, 329, 104, 29);
		frame.getContentPane().add(lblHolderName);
		
		JLabel lblCardNo = new JLabel("Card No :");
		lblCardNo.setBounds(504, 384, 104, 29);
		frame.getContentPane().add(lblCardNo);
		
		JLabel lblTotalBill = new JLabel("Total Bill :");
		lblTotalBill.setBounds(504, 451, 104, 29);
		frame.getContentPane().add(lblTotalBill);
		
		JLabel cardtype = new JLabel("VIP or Normal");
		cardtype.setBounds(736, 275, 350, 29);
		frame.getContentPane().add(cardtype);
		
		JLabel holdername = new JLabel("Name...");
		holdername.setBounds(736, 328, 350, 30);
		frame.getContentPane().add(holdername);
		
		JLabel cardno = new JLabel("xxxxxxxx");
		cardno.setBounds(736, 383, 350, 30);
		frame.getContentPane().add(cardno);
		
		JLabel bill = new JLabel("100");
		bill.setBounds(736, 450, 350, 30);
		frame.getContentPane().add(bill);
	}
}
