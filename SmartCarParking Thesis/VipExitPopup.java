import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;

public class VipExitPopup {

	private JFrame frame;
	int isPrepaid=0;
	String remainingBalance,usedBalance,totalFare;
	/**
	 * Launch the application.
	 */
	/*public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VipExitPopup window = new VipExitPopup();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}*/

	public VipExitPopup(String string, String string2) {
		
		usedBalance=string;
		totalFare=string2;
		exitPopup();
		// TODO Auto-generated constructor stub
	}

	public VipExitPopup(String string, String string2, String string3) {
		isPrepaid=1;
		remainingBalance=string;
		usedBalance=string2;
		totalFare=string3;
		//System.out.println("HHHHIII");
		exitPopup();
		// TODO Auto-generated constructor stub
	}

	/**
	 * Create the application.
	 */
	public void exitPopup() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		//frame.setVisible(true);
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("CarParkingManagementSystem");
		frame.setBounds(200, 100, 600, 450);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblBillOfToday = new JLabel("Today's Bill:");
		lblBillOfToday.setBounds(100, 110, 250, 30);
		frame.getContentPane().add(lblBillOfToday);
		
		JLabel label = new JLabel(totalFare);
		label.setBounds(300, 110, 100, 30);
		frame.getContentPane().add(label);
		
		JLabel lblNewTotalBill = new JLabel("Used Balance:");
		lblNewTotalBill.setBounds(100, 150, 250, 30);
		frame.getContentPane().add(lblNewTotalBill);
		
		JLabel label_1 = new JLabel(usedBalance);
		label_1.setBounds(300, 150, 100, 30);
		frame.getContentPane().add(label_1);
		
		if(isPrepaid==1) {
		//System.out.println("HHHHHEEEE");
		
		JLabel lblRemainingBalance = new JLabel("Remaining Account Balance:");
		lblRemainingBalance .setBounds(100, 190, 250, 30);
		frame.getContentPane().add(lblRemainingBalance);
		
		JLabel label_2 = new JLabel(remainingBalance);
		label_2.setBounds(300, 190, 100, 30);
		frame.getContentPane().add(label_2);
		}
		
		
		JButton btnOk = new JButton("Ok");
		
		btnOk.setBounds(200, 240, 80, 30);
		frame.getContentPane().add(btnOk);
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				VipExit vexit=new VipExit();
				VipExit.vipExit();
				
			}
		});
			
		
		
		
	}
}
