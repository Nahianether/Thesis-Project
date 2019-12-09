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
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JTextField;

import com.toedter.calendar.JDateChooser;

public class VipRegistration {

	private JFrame frame;
	JLabel lblRfid ,lblCarType ,lblDate ,lblSystem,lblPhoneNo,lblBalance ;
	JTextField rfid,phoneno,balance;
	JComboBox system ,cartype;
	JDateChooser jdate ;
	static DateTimeFormatter dtf=DateTimeFormatter.ofPattern("yyyy-MM-dd HH-mm-ss");
	static LocalDateTime now;
	static String nowDate,paymentType,carType,requestMessage="New VIP User Entry",balancePost;
	 static String ipAddress;
     static int portNumer;

	/**
	 * Launch the application.
	 */
	//public static void main(String[] args) {
		//EventQueue.invokeLater(new Runnable() {
     
     public void vregistartion() {
    	 //System.out.println("VIP Registration");
 		EventQueue.invokeLater(new Runnable() {
 			public void run() {
 				try {
 					initialize();
 			 		IpPortConfiguration ipConfig=new IpPortConfiguration();
 			 		ipAddress=ipConfig.getIpAddress();
 			 		portNumer=ipConfig.getPortNumber();
 			 		frame.setVisible(true);
 					
 				} catch (Exception e) {
 					e.printStackTrace();
 				}
 			}
 		});
 	}
     
     /*public void run()
     {
    	 System.out.println("VIP Registration");
		qEventQueue.invokeLater(new Runnable() {
			 public void run() {
		    	 initialize();
		 		IpPortConfiguration ipConfig=new IpPortConfiguration();
		 		ipAddress=ipConfig.getIpAddress();
		 		portNumer=ipConfig.getPortNumber();
		 		frame.setVisible(true);
//					try {
//						VipRegistration window = new VipRegistration();
//						
//					} catch (Exception e) {
//						e.printStackTrace();
//					}
				}		
		});
	}
    */
		//});
	//}

	/**
	 * Create the application.
	 */
	public VipRegistration() {
		
		//window.frame.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("CarParkingManagementSystem");
		frame.setBounds(0, 0, 800, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		Date currentDate = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("mm-dd-yyyy");
		
		//UtilDateModel model = new UtilDateModel();
		//JDatePanelImpl datePanel = new JDatePanelImpl(model);
		/*
		 jdate = new JDateChooser();
		jdate.setBounds(734,330,220,30);
		frame.getContentPane().add(jdate);
		*/
		
		/*lblRfid = new JLabel("RFID :");
		lblRfid.setBounds(200, 150, 90, 30);
		frame.getContentPane().add(lblRfid);
		
		rfid = new JTextField();
		rfid.setBounds(350, 150, 220, 30);
		frame.getContentPane().add(rfid);
		rfid.setColumns(10);*/
		
		lblCarType = new JLabel("Car Type :");
		lblCarType.setBounds(200, 200, 90, 30);
		frame.getContentPane().add(lblCarType);
		
		/*cartype = new JTextField();
		cartype.setColumns(10);
		cartype.setBounds(734, 278, 220, 30);
		frame.getContentPane().add(cartype);*/
		
		cartype = new JComboBox();
		cartype.setBounds(350, 200, 220, 30);
		frame.getContentPane().add(cartype);
		cartype.addItem("Car");
		cartype.addItem("Bike");
		cartype.addItem("Micro");
		
		
		lblBalance = new JLabel("Balance:");
		lblBalance.setBounds(200, 250, 90, 30);
		frame.getContentPane().add(lblBalance);
		
		balance= new JTextField();
		balance.setBounds(350, 250, 220, 30);
		frame.getContentPane().add(balance);
		balance.setColumns(10);
		
		/*
		lblDate = new JLabel("Date :");
		lblDate.setBounds(553, 330, 90, 30);
		frame.getContentPane().add(lblDate);*/
		//System.out.println("Date:"+lblDate.());
		
		lblSystem = new JLabel("System :");
		lblSystem.setBounds(200, 300, 90, 30);
		frame.getContentPane().add(lblSystem);
		
		system = new JComboBox();
		system.setBounds(350, 300, 90, 30);
		frame.getContentPane().add(system);
		system.addItem("Prepaid");
		system.addItem("Postpaid");
		
		
		lblPhoneNo = new JLabel("Phone No :");
		lblPhoneNo.setBounds(200, 350, 90, 30);
		frame.getContentPane().add(lblPhoneNo);
		
		phoneno = new JTextField();
		phoneno.setColumns(10);
		phoneno.setBounds(350, 350, 220, 30);
		frame.getContentPane().add(phoneno);
		
		JButton create = new JButton("Create");
		create.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int indexSelected=system.getSelectedIndex();
				if(indexSelected==0) {
					paymentType="prepaid";
					balancePost=balance.getText();
				}
				else if(indexSelected==1) {
					paymentType="postpaid";
					balance.setText("0");
					balancePost=balance.getText();
					//JOptionPane.showMessageDialog(null, arg1, arg2, arg3);
					
				}
				//String val=system.
				//System.out.println("option:"+indexSelected);
				int typeSelected=cartype.getSelectedIndex();
				if(typeSelected==0) {
					carType="Car";
				}
				else if(typeSelected==1) {
					carType="Bike";
				}
				else if(typeSelected==2) {
					carType="Micro";
				}
				//insertNewMember();
				databaseInsertServer();
				EmployeeHome emH=new EmployeeHome();
				frame.setVisible(false);
				emH.run();
				
			}
		});
		create.setBounds(350, 450, 100, 30);
		frame.getContentPane().add(create);
		
	}
	
	
	
	
	protected void databaseInsertServer() {
		// TODO Auto-generated method stub
		try {
			 Socket server=new Socket(ipAddress,portNumer);
	        //Socket server=new Socket("localhost",8800);
	        BufferedReader sin=new BufferedReader(new InputStreamReader(server.getInputStream()));
	        PrintStream sout=new PrintStream(server.getOutputStream());
	        BufferedReader stdin=new BufferedReader(new InputStreamReader(System.in));
	        
	        String sendMessage =requestMessage+'-'+','+carType+','+balancePost+','+paymentType+
	        		','+phoneno.getText(); 

	        sout.println(sendMessage);
	        
	        String s1=sin.readLine(); 
	        String[] splitResult=s1.split(",");
	        
	        //System.out.println("Return Message:"+s1);
	        if(splitResult[0].equals("Insert Done")) {
	        	JOptionPane.showMessageDialog(null,"New VIP User Is Registered with RFID No: "+splitResult[1]);
	        }
	        else if(splitResult[0].equals("Insert Error")) {
	        	JOptionPane.showMessageDialog(null,"Registration Cannot be done;Try Again");
	        }
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
	    } catch (UnknownHostException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	    } catch (IOException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	    }
	}

	
	
/*
	public void insertNewMember() {
		ResultSet rsIndex,rsPrimary;
		PreparedStatement stmtIndex,stmtInsert,stmtPrimary;
		String queryInsert,queryIndex,queryPrimary;
		int position=0;
		now=LocalDateTime.now();
		int possible=1;
		nowDate=dtf.format(now);
		
		queryPrimary="SELECT * FROM `vip_rfid` where `generateRFID_NO`=? ";
		try {
			stmtPrimary=MyConnection.getConnection().prepareStatement(queryPrimary);
			stmtPrimary.setString(1,rfid.getText());
			rsPrimary=stmtPrimary.executeQuery();
			if(rsPrimary.next()) {
				//position=rsIndex.getInt(1);
				//position+=1;
				possible=0;
				System.out.println("Already exists");
								
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		if(possible==1) {
			queryIndex="SELECT max(`VIPrfid_No`) FROM `vip_rfid` ";
			try {
				stmtIndex = MyConnection.getConnection().prepareStatement(queryIndex);
				rsIndex=stmtIndex.executeQuery();
				if(rsIndex.next()) {
					position=rsIndex.getInt(1);
					position+=1;
									
				}
				queryInsert="INSERT INTO `vip_rfid`(`VIPrfid_No`, `VIPrfid_issue_date`, `VIPrfid_remaining_balance`,"
						+ " `VIPrfid_balance_used`, `VIPrfid_mobile_no`, `VIPrfid_lastPayment`, `VIPrfid_vehicle_type`,"
						+ " `VIPrfid_payment_type`,  `generateRFID_NO`) VALUES (?,?,?,?,?,?,?,?,?)";
				
				stmtInsert = MyConnection.getConnection().prepareStatement(queryInsert);
				stmtInsert.setInt(1, position);
				stmtInsert.setString(2, nowDate);
				double vipBalance=Double.parseDouble(balance.getText());
				stmtInsert.setDouble(3, vipBalance);
				stmtInsert.setDouble(4,0);
				stmtInsert.setString(5, phoneno.getText());
				stmtInsert.setString(6, nowDate);
				stmtInsert.setString(7, carType);
				stmtInsert.setString(8, paymentType);
				
				stmtInsert.setString(9, rfid.getText());
				
				if(stmtInsert.executeUpdate()>0) {
					System.out.println("Insert Done");
					
				}
				else {
					System.out.println("Insert error");
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	*/
}

