//package payment;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;


public class AdminHome {

	private JFrame frame;

	/**
	 * Launch the application.
	 */ 
      
       public void run2()
        {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AdminHome window = new AdminHome();
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
	public AdminHome() {
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
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JButton payment = new JButton("Fare Type");
		payment.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) { 
                            hourday pay=new hourday();
                            
                            pay.hday(); 
                            
			}
		});
		menuBar.add(payment);
		
		JButton vipregistration = new JButton("Card Validity");
		vipregistration.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
                            CarValidityPopup cvali=new CarValidityPopup(); 
                            cvali.validiy();
			}
		});
		menuBar.add(vipregistration);
		
		JButton userRegistration = new JButton("User Registration");
		userRegistration.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
                            AdminCreateAccount cvali=new AdminCreateAccount(); 
                            frame.setVisible(false);
                            cvali.run();
			}
		});
		menuBar.add(userRegistration );
		
		
		
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
