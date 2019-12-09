//package payment;

import java.awt.EventQueue;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;

public class VipPaymentCacuation {

	JFrame frame; 
	static String sncard;
    static String ipAddress;
    static int portNumer; 
    String requestMessage="vipPayment"; 
   public  static String blanaceused;
  public   static String  blanaceremaining ; 
   public  static String vipcartyp;

	/**
	 * Launch the application.
	 */
	public void calculation()
	{
	EventQueue.invokeLater(new Runnable() 
		{
			public void run()
			{
				try {
					
			 		IpPortConfiguration ipConfig=new IpPortConfiguration();
			 		ipAddress=ipConfig.getIpAddress();
			 		portNumer=ipConfig.getPortNumber();
			 		
			 		initialize();
			 		frame.setVisible(true);
			 		
					
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
		
	}

	/**
	 * Create the application.
	 */
	public VipPaymentCacuation () {
		
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("CarParkingManagementSystem");
		frame.setBounds(50, 50, 1200, 700);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null); 
		
		 paymentinfo();
		
		JButton btnOk = new JButton("OK");
		btnOk.setBounds(714, 783, 89, 30);
		frame.getContentPane().add(btnOk);
		
		JLabel lblEmployeeId = new JLabel("Employee ID :");
		lblEmployeeId.setBounds(612, 602, 90, 30);
		frame.getContentPane().add(lblEmployeeId);
		
		JLabel employeeid = new JLabel(EmployeeLogin.username);
		employeeid.setBounds(825, 602, 120, 30);
		frame.getContentPane().add(employeeid);
		
		JLabel lblBalanceUsed = new JLabel("Balance Used :");
		lblBalanceUsed.setBounds(612, 524, 90, 30);
		frame.getContentPane().add(lblBalanceUsed);
		
		JLabel balanceused = new JLabel(blanaceused);
		balanceused.setBounds(825, 524, 120, 30);
		frame.getContentPane().add(balanceused);
		
		JLabel lblBalanceRemaing = new JLabel("Balance Remaing :");
		lblBalanceRemaing.setBounds(612, 451, 90, 30);
		frame.getContentPane().add(lblBalanceRemaing);
		
		JLabel balanceremaing = new JLabel(blanaceremaining);
		balanceremaing.setBounds(825, 451, 120, 30);
		frame.getContentPane().add(balanceremaing);
		
		JLabel lblHolderName = new JLabel("Car Typ :");
		lblHolderName.setBounds(612, 376, 90, 30);
		frame.getContentPane().add(lblHolderName);
		
		JLabel holdername = new JLabel(vipcartyp);
		holdername.setBounds(825, 376, 120, 30);
		frame.getContentPane().add(holdername);
		
		JLabel lblCardNo = new JLabel("Card No :");
		lblCardNo.setBounds(612, 304, 90, 30);
		frame.getContentPane().add(lblCardNo);
		
		JLabel cardno = new JLabel(sncard);
		cardno.setBounds(825, 304, 120, 30);
		frame.getContentPane().add(cardno);
		
		JLabel lblCardType = new JLabel("Card Type :");
		lblCardType.setBounds(612, 237, 90, 30);
		frame.getContentPane().add(lblCardType);
		
		JLabel cardtype = new JLabel("VIP Card");
		cardtype.setBounds(825, 237, 120, 30);
		frame.getContentPane().add(cardtype);
		
		// paymentinfo();
                
               
	} 
        
      public void  paymentinfo()
      {  
    	  
    	  
    	
      	
      	try {
      		Socket server=new Socket(ipAddress,portNumer);
              //Socket server=new Socket("localhost",8800);
              BufferedReader sin=new BufferedReader(new InputStreamReader(server.getInputStream()));
              PrintStream sout=new PrintStream(server.getOutputStream());
              BufferedReader stdin=new BufferedReader(new InputStreamReader(System.in)); 
              
  	       sncard=SelectVip.cardnbr;
  	     //  System.out.println("crdnbr"+SelectNormal.cardnbr);
  	        String sendMessage =requestMessage+'-'+','+sncard; 
  	       System.out.println("sncard"+sncard);

  	        sout.println(sendMessage);
  	        
  	        String s1=sin.readLine(); 
  	       // System.out.println(s1);
  	        String[] splitResult=s1.split(","); 
  	     
  	     // System.out.println("Backmsg from server:"+splitResult[0]);
  	  blanaceused=splitResult[0];
  	     System.out.println("blanaceused:"+splitResult[0]);
  	blanaceremaining=splitResult[1]; 
  	      System.out.println("blanaceremaining:"+blanaceremaining);
  	vipcartyp =splitResult[2]; 
  	       System.out.println("vipcartyp:"+vipcartyp);
  	        
  	     
  	        
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
