import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;

public class EmployeeShowInfo {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EmployeeShowInfo window = new EmployeeShowInfo();
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
	public EmployeeShowInfo() {
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
		
		JButton btnOk = new JButton("OK");
		btnOk.setBounds(714, 783, 89, 30);
		frame.getContentPane().add(btnOk);
		
		JLabel lblEmployeeId = new JLabel("Employee ID :");
		lblEmployeeId.setBounds(612, 602, 90, 30);
		frame.getContentPane().add(lblEmployeeId);
		
		JLabel employeeid = new JLabel("10");
		employeeid.setBounds(825, 602, 120, 30);
		frame.getContentPane().add(employeeid);
		
		JLabel lblBalanceUsed = new JLabel("Balance Used :");
		lblBalanceUsed.setBounds(612, 524, 90, 30);
		frame.getContentPane().add(lblBalanceUsed);
		
		JLabel balanceused = new JLabel("500");
		balanceused.setBounds(825, 524, 120, 30);
		frame.getContentPane().add(balanceused);
		
		JLabel lblBalanceRemaing = new JLabel("Balance Remaing :");
		lblBalanceRemaing.setBounds(612, 451, 90, 30);
		frame.getContentPane().add(lblBalanceRemaing);
		
		JLabel balanceremaing = new JLabel("500");
		balanceremaing.setBounds(825, 451, 120, 30);
		frame.getContentPane().add(balanceremaing);
		
		JLabel lblHolderName = new JLabel("Holder Name :");
		lblHolderName.setBounds(612, 376, 90, 30);
		frame.getContentPane().add(lblHolderName);
		
		JLabel holdername = new JLabel("xxxxxxxxxx");
		holdername.setBounds(825, 376, 120, 30);
		frame.getContentPane().add(holdername);
		
		JLabel lblCardNo = new JLabel("Card No :");
		lblCardNo.setBounds(612, 304, 90, 30);
		frame.getContentPane().add(lblCardNo);
		
		JLabel cardno = new JLabel("xxxxxxxxxxx");
		cardno.setBounds(825, 304, 120, 30);
		frame.getContentPane().add(cardno);
		
		JLabel lblCardType = new JLabel("Card Type :");
		lblCardType.setBounds(612, 237, 90, 30);
		frame.getContentPane().add(lblCardType);
		
		JLabel cardtype = new JLabel("Normal");
		cardtype.setBounds(825, 237, 120, 30);
		frame.getContentPane().add(cardtype);
	}

}
