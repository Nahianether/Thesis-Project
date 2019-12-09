
import javax.swing.*;

import java.awt.event.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;


public class EmployeeLogin {
    public static String  username;
	private JFrame frame;
	private JTextField name;
	private JPasswordField password1;
	String requestMessage="Login";
	 static String ipAddress;
     static int portNumer;
	/**
	 * Launch the application.
	 */
	static EmployeeLogin window;
	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			
//		});
		//System.out.println(2<<4);
		IpPortConfiguration ipConfig=new IpPortConfiguration();
		ipAddress=ipConfig.getIpAddress();
		portNumer=ipConfig.getPortNumber();
		run();
	}

	/**
	 * Create the application.
	 */
	public static void run() {
		try {
			window = new EmployeeLogin();
			
			window.frame.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public EmployeeLogin() {
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
		
		JLabel Lname = new JLabel("User ID:");
		Lname.setBounds(196, 76, 80, 22);
		frame.getContentPane().add(Lname);
		
		name = new JTextField();
		name.setBounds(196, 109, 195, 30);
		frame.getContentPane().add(name);
		name.setColumns(10);
		
		JLabel Lpassword = new JLabel("PassWord:");
		Lpassword.setBounds(196, 160, 80, 22);
		frame.getContentPane().add(Lpassword);
		
		password1 = new JPasswordField();
		password1.setBounds(196, 193, 195, 30);
		frame.getContentPane().add(password1);
		
		JButton login = new JButton("Login");
		login.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				username=name.getText(); 
				String password=String.valueOf(password1.getPassword());
				
				
				try {
					Socket server=new Socket(ipAddress,portNumer);
			        //Socket server=new Socket("localhost",8800);
			        BufferedReader sin=new BufferedReader(new InputStreamReader(server.getInputStream()));
			        PrintStream sout=new PrintStream(server.getOutputStream());
			        BufferedReader stdin=new BufferedReader(new InputStreamReader(System.in));
			        
			        String sendMessage =requestMessage+'-'+','+username+','+password;
			        //System.out.println(sendMessage);

			        sout.println(sendMessage);
			        
			        String s1=sin.readLine(); 
			        //password1.setName(null);
			        
			        if(s1.equals("Employee Logged In" )) {
			        	EmployeeHome employ=new EmployeeHome(); 
			        	frame.setVisible(false);
                        employ.run();
			        }
			        else if(s1.equals("Admin Logged In")) {
			        	AdminHome adminemploy=new AdminHome(); 
			        	frame.setVisible(false);
                        adminemploy.run2();
			        }
			        else {
			        	JOptionPane.showMessageDialog(null, s1);
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
				
				
				//PreparedStatement st;
				
					
			}
		});
		
		login.setBounds(163, 293, 97, 30);
		frame.getContentPane().add(login);
		
		JButton reset = new JButton("Reset");
		reset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				name.setText(null);
				password1.setText(null);
			}
		});
		reset.setBounds(332, 293, 97, 30);
		frame.getContentPane().add(reset);
		
		JButton forget = new JButton("Forget");
		forget.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Button CLicked");
				GetPassword gps=new GetPassword();
				frame.setVisible(false);

				gps.run();
			}
		});
		forget.setBounds(250, 350, 97, 30);
		frame.getContentPane().add(forget);
		
	}
}
