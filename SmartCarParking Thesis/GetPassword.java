import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;

public class GetPassword {

	private JFrame frame;
	String requestMsg="Forget Pass";
	 static String ipAddress;
     static int portNumer;

	/**
	 * Launch the application.
	 */
     public void run() {
	//public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					System.out.println("running");
					GetPassword window = new GetPassword();
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
	public GetPassword() {
		System.out.println("What?");
		IpPortConfiguration ipConfig=new IpPortConfiguration();
		ipAddress=ipConfig.getIpAddress();
		portNumer=ipConfig.getPortNumber();
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
		
		JLabel lblEmail = new JLabel("Email :");
		lblEmail.setBounds(93, 108, 162, 29);
		frame.getContentPane().add(lblEmail);
		
		JTextField email = new JTextField();
		email.setBounds(265, 108, 237, 29);
		frame.getContentPane().add(email);
		email.setColumns(10);
		
		JLabel lblSecurityQuestion = new JLabel("Security Question :");
		lblSecurityQuestion.setBounds(93, 161, 162, 29);
		frame.getContentPane().add(lblSecurityQuestion);
		
		JTextField securityquestion = new JTextField();
		securityquestion.setBounds(265, 161, 237, 29);
		frame.getContentPane().add(securityquestion);
		securityquestion.setColumns(10);
		
		JButton getpassword = new JButton("Get Password");
		getpassword.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					Socket server=new Socket(ipAddress,portNumer);
			        //Socket server=new Socket("localhost",8800);
			        BufferedReader sin=new BufferedReader(new InputStreamReader(server.getInputStream()));
			        PrintStream sout=new PrintStream(server.getOutputStream());
			        BufferedReader stdin=new BufferedReader(new InputStreamReader(System.in));
			        
			        String sendMessage =requestMsg+'-'+','+email.getText()+','+securityquestion.getText();
			        //System.out.println(sendMessage);

			        sout.println(sendMessage);
			        
			        String s1=sin.readLine(); 
			        //JOptionPane.showMessageDialog(null, s1);
			        //password1.setName(null);
			        
			        if(s1.equals("Not Matched" )) {
			        	JOptionPane.showMessageDialog(null, "Not Matched the Word");
			        }
			        else if(s1.equals("Not Found")) {
			        	JOptionPane.showMessageDialog(null, "Invalid Email");
			        }
			        else {
			        	JOptionPane.showMessageDialog(null, "New Password: "+s1);
			        	EmployeeLogin el=new EmployeeLogin();
						frame.setVisible(false);
						el.run();
			        }
			        
			       // LoginSessionEmployee loginSessionEmployee=new LoginSessionEmployee();
			       // System.out.println("Now Logged in"+loginSessionEmployee.getName());
			     //   System.out.println("Return Message:"+s1);
			        /*
			        if(s1.equals("Aleadt")) {
			        	System.out.println("");
			        	
			        }
			        else {
			        	System.out.println("Insert is not done");
			        }*/
			       // cardno.setText(null);
			        
			        server.close();
			        sin.close();
			        sout.close();
			        stdin.close();
			    } catch (UnknownHostException e1) {
			        // TODO Auto-generated catch block
			        e1.printStackTrace();
			    } catch (IOException e2) {
			        // TODO Auto-generated catch block
			        e2.printStackTrace();
			    }
				
			}
		});
		getpassword.setBounds(205, 284, 162, 29);
		frame.getContentPane().add(getpassword);
	}
}
