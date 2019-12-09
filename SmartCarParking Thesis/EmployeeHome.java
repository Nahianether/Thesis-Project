//package payment;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JMenu;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JLabel;


public class EmployeeHome {

	JFrame frame;

	/**
	 * Launch the application.
	 */ 
      
       public void run()
        {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EmployeeHome window = new EmployeeHome();
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
	public EmployeeHome() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("CarParkingManagementSystem");
		frame.setBounds(0, 0, 1500, 900);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JButton payment = new JButton("Payment");
		payment.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {  
                            System.out.println("radiobutton page");
                            Payment1 pay=new Payment1();
                            frame.setVisible(false);
                            pay.userpayment(); 
                            
			}
		});
		menuBar.add(payment);
		
		JButton vipregistration = new JButton("Vip Registration");
		vipregistration.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
                            VipRegistration vreg=new VipRegistration();
                            frame.setVisible(false);
                            vreg.vregistartion();
			}
		});
		menuBar.add(vipregistration);
		
		JButton logout = new JButton("Logout");
		logout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
                         //  window.frame.setVisible(false);
				EmployeeLogin el=new EmployeeLogin();
				frame.setVisible(false);
				el.run();
			}
		});
		menuBar.add(logout);
		

	}
}
