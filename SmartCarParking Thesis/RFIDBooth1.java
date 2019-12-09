

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

//import com.sun.xml.internal.ws.resources.SenderMessages;

import java.io.*;
import java.net.*;
import java.util.*;

public class RFIDBooth1 {
        String boothid="1";
        int count=0;
        String cartype;
        String cardNo,requestMessage="Entry";
        static String ipAddress;
        static int portNumer;
       
        private JFrame frame;
        JTextField cardno;
       
        
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RFIDBooth1 window = new RFIDBooth1();
					IpPortConfiguration ipConfig=new IpPortConfiguration();
					ipAddress=ipConfig.getIpAddress();
					portNumer=ipConfig.getPortNumber();
					
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	public RFIDBooth1() {
		initialize();
	}

	private void initialize() {
		frame = new JFrame("CarParkingManagementSystem");
		frame.setBounds(100, 100, 600, 450);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton btnMiniBus = new JButton("Mini Bus");
		btnMiniBus.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { 
                            count=3;
                            connectServer();
			}
		}); 
		btnMiniBus.setBounds(224, 220, 105, 39);
		frame.getContentPane().add(btnMiniBus);
		
		JButton btnCarMicro = new JButton("Car / Micro");
		btnCarMicro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { 
                            count=2;
                            connectServer();
			}
		});
		btnCarMicro.setBounds(224, 140, 105, 39);
		frame.getContentPane().add(btnCarMicro);
		
		JButton btnBike = new JButton("Bike");
		btnBike.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { 
                            count=1;
                            connectServer();
			}
		});
		btnBike.setBounds(224, 60, 105, 38);
		frame.getContentPane().add(btnBike);
		
		 cardno = new JTextField();
	        cardno.setBounds(180, 300, 195, 30);
	        frame.getContentPane().add(cardno);
	        cardno.setColumns(10);
	        
	        JLabel lblCardNo = new JLabel("Card No :");
	        lblCardNo.setBounds(100, 300, 70, 22);
	        frame.getContentPane().add(lblCardNo);
	        
	        JButton btnok = new JButton("OK");
	        btnok.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent arg0) {
	            	cardNo=cardno.getText();
	            	connectServerVip();
	            }

				
	        });
	        btnok.setBounds(400, 300, 90, 30);
	        frame.getContentPane().add(btnok);
		
	} 
	
	
	public void connectServerVip() {
		// TODO Auto-generated method stub
	    try {
	    	Socket server=new Socket(ipAddress,portNumer);
	        //Socket server=new Socket("localhost",8800);
	        BufferedReader sin=new BufferedReader(new InputStreamReader(server.getInputStream()));
	        PrintStream sout=new PrintStream(server.getOutputStream());
	        BufferedReader stdin=new BufferedReader(new InputStreamReader(System.in));
	        
	        
	        String sendMessage =requestMessage+'-'+','+boothid+','+cardNo; 
	        
	        sout.println(sendMessage);
	        
	        String s1=sin.readLine(); 
	       
	        if(s1.equals("0s")) {
	        	System.out.println("Barrier Gate is Closed");
	        	
	        }
	        else {
	        	
	        	System.out.println("Barrier Gate is Opened For : "+ s1+"econd");
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
	
	
        private void connectServer()
        {
             
             try {
            	 Socket server=new Socket(ipAddress,portNumer);
            //Socket server=new Socket("localhost",8800);
            BufferedReader sin=new BufferedReader(new InputStreamReader(server.getInputStream()));
            PrintStream sout=new PrintStream(server.getOutputStream());
            BufferedReader stdin=new BufferedReader(new InputStreamReader(System.in));
            Random rand = new Random();
            int n = rand.nextInt(1000) + 1;
            String number = Integer.toString(n);  
             
		     if(count==1)
		     {
		       cartype="Bike"; 
		      
		     } 
		     else if(count==2)
		     { 
		         cartype="Car"; 
		         
		     }
		     else 
		     { 
		         cartype="Mini"; 
		     }
            
            String sendMessage =requestMessage+'-'+','+boothid+','+cartype+','+number; 
            System.out.println("Message"+sendMessage);
            sout.println(sendMessage);
            
            String s1=sin.readLine(); 
            //System.out.println("RESULT Booth"+s1);
            if(s1.equals("0s")) {
            	System.out.println("Barrier Gate is Closed");
            }
            else {
            	System.out.println("Barrier Gate is Opened for : "+s1+"econd");
            }
            
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
