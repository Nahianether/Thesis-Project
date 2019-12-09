//package payment;

import java.awt.EventQueue;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.awt.event.ActionEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException; 



import java.util.Calendar;
import java.util.Date;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;



public class NormalExit { 
    

     private JFrame frame;
    public String exitcardnbr;
    String requestMessage="Exit";
	 static String ipAddress;
    static int portNumer; 
    String boothid="192.168.3.36";
  
     
    
    public static final DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    JTextField cardno;

    /**
     * Launch the application.
     */
    static NormalExit win;
    public static void main(String args[]) {
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
			win = new NormalExit();
			
			win.frame.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public NormalExit() {
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

        JLabel lblCardNo = new JLabel("Card No :");
        lblCardNo.setBounds(532, 406, 94, 23);
        frame.getContentPane().add(lblCardNo);

        cardno = new JTextField();
        cardno.setBounds(654, 402, 289, 31);
        frame.getContentPane().add(cardno);
        cardno.setColumns(10);

        JButton exit = new JButton("Exit");
        exit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                
                 exitcardnbr = cardno.getText();
                exitfunction(exitcardnbr);

             
            }
        });
        exit.setBounds(727, 534, 94, 31);
        frame.getContentPane().add(exit);
    }

  

 
    void exitfunction(String exitcardnbr)
    { 

    	
    	try {
    		Socket server=new Socket(ipAddress,portNumer);
            //Socket server=new Socket("localhost",8800);
            BufferedReader sin=new BufferedReader(new InputStreamReader(server.getInputStream()));
            PrintStream sout=new PrintStream(server.getOutputStream());
            BufferedReader stdin=new BufferedReader(new InputStreamReader(System.in)); 
            
	      
	        System.out.println("exitcardnbr"+exitcardnbr);
	        String sendMessage =requestMessage+'-'+','+boothid+','+exitcardnbr; 
	        

	        sout.println(sendMessage);
	        
	        String s1=sin.readLine();
	        
	        System.out.println(s1);
	       String[] splitResult=s1.split(","); 
	       
	      System.out.println("Backmsg from server:"+s1);
	      
	      System.out.println("Hellow world");
	      
	      if(splitResult[0].equals("0s")) {
             
	    	  JOptionPane.showMessageDialog(null, "Pay the bill 1st"); 
	    	  System.out.println("barrier gate closed");
	        } 
	      else  if(splitResult[0].equals("-1s")) {
	             
	    	  JOptionPane.showMessageDialog(null, "Extra Time Added Please, Pay The Bill");
		        }
	      else  if(splitResult[0].equals("-2s")) {
	          
	    	  JOptionPane.showMessageDialog(null, "Card not found");
		        }
	      else  if(splitResult[0].equals("1s")) {
	         
	    	  JOptionPane.showMessageDialog(null, "Insufficient Balance ; Deposit First");
		        }
	      else  if(splitResult[0].equals("2s")) {
	       
	    	  JOptionPane.showMessageDialog(null, "Date Over ; Pay the bill");
		        }
	      else  if(splitResult[0].equals("3s")) {
	          
	    	  JOptionPane.showMessageDialog(null,"Low Balance ; Deposit First");
		        }
	      
	      else {
	    	 
	    	  JOptionPane.showMessageDialog(null, "Payment Done,you can exit");
	    	  
		        }
	      	cardno.setText(null);
	     
	        
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
    
    
}
