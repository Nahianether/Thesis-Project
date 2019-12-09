import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.event.CellEditorListener;

public class VipExit {

	private static JFrame frame;
	static String requestMessage="Exit";
	static String ipAddress;
    static int portNumer;
    static String booth_id="1";

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		vipExit();
	}

	/**
	 * Create the application.
	 */
	public static void vipExit() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
			
				try {
					IpPortConfiguration ipConfig=new IpPortConfiguration();
					ipAddress=ipConfig.getIpAddress();
					portNumer=ipConfig.getPortNumber();
					//VipExit window = new VipExit();
					
					
					
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
		});
		
		
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private static void initialize() {
		frame = new JFrame("CarParkingManagementSystem");
		frame.setBounds(200, 100, 600, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		
		JLabel lblCardNo = new JLabel("Card No :");
		lblCardNo.setBounds(150,100, 70, 23);
		frame.getContentPane().add(lblCardNo);
		
		JTextField cardno = new JTextField();
		cardno.setBounds(240, 100, 200, 31);
		frame.getContentPane().add(cardno);
		cardno.setColumns(10);
		
		JButton exit = new JButton("Exit");
		exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					Socket server=new Socket(ipAddress,portNumer);
			        //Socket server=new Socket("localhost",8800);
			        BufferedReader sin=new BufferedReader(new InputStreamReader(server.getInputStream()));
			        PrintStream sout=new PrintStream(server.getOutputStream());
			        BufferedReader stdin=new BufferedReader(new InputStreamReader(System.in));
			        
			        String sendMessage =requestMessage+'-'+','+booth_id+','+cardno.getText();
			        //System.out.println(sendMessage);

			        sout.println(sendMessage);
			        
			        String s1=sin.readLine(); 
			        String[] splitResult=new String[5];
			        int i,commaCount=0;
			        for(i=0;i<s1.length();i++) {
						char c=s1.charAt(i);
						//System.out.println("Character:"+c);
						if(c==',') {
							commaCount++;
						}
					}
			        if(commaCount==1) {
			        JOptionPane.showMessageDialog(null, s1);
			        }
			        else if(commaCount==2) {
			        	splitResult=s1.split(",");
			        	frame.setVisible(false);
			        	VipExitPopup vexp=new VipExitPopup(splitResult[0],splitResult[1]);
			        	System.out.println("Barrier Gate is opened for : "+ splitResult[2]+"econd");
			        }
			        else if(commaCount==3) {
			        	splitResult=s1.split(",");
			        	frame.setVisible(false);
			        	VipExitPopup vexp=new VipExitPopup(splitResult[0],splitResult[1],splitResult[2]);
			        	System.out.println("Barrier Gate is opened for : "+ splitResult[3]+"econd");
			        }
			      
			    
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
		exit.setBounds(250, 150, 80, 31);
		frame.getContentPane().add(exit);
		frame.setVisible(true);
		
			
	}

}
