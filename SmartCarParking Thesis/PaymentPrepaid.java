import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class PaymentPrepaid {

	private JFrame frame;
	private JTextField textField;
	static String ipAddress;
    static int portNumer;
    static String sncard;
    static String deposite;
    String requestMessage="prepaidpayment"; 
    public  static String  remaining;
    public   static String  mbl ; 
     public  static String vipcartyp;
     public String exittime;
     public String rem;
    // private JTextField name;


	/**
	 * Launch the application.
	 */
	public  void prepaid() {
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
	public PaymentPrepaid() {
		//initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("CarParkingManagementSystem");
		frame.setBounds(0, 0, 1500, 900);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton payprint = new JButton("Pay & Print");
		payprint.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) { 
				vippreprintPayment();
				JOptionPane.showMessageDialog(null, "Payment Done Successfully");
				EmployeeHome ehm=new EmployeeHome();
				frame.setVisible(false);
				ehm.run();
			}
		}); 
		
		prepaidpayment();
		
		payprint.setBounds(633, 639, 104, 29);
		frame.getContentPane().add(payprint);
		
		JLabel lblTotalBill = new JLabel("Deposite Ammount :");
		lblTotalBill.setBounds(525, 229, 136, 29);
		frame.getContentPane().add(lblTotalBill);
		
		
		JLabel lblVehicleType = new JLabel("Vehicle Type :");
		lblVehicleType.setBounds(525, 288, 136, 29);
		frame.getContentPane().add(lblVehicleType);
		
		JLabel vehicletype = new JLabel(vipcartyp);
		vehicletype.setBounds(756, 288, 136, 29);
		frame.getContentPane().add(vehicletype);
		
		JLabel lblPhoneNumber = new JLabel("Remaining Balance:");
		lblPhoneNumber.setBounds(525, 347, 136, 29);
		frame.getContentPane().add(lblPhoneNumber);
		
		JLabel phone = new JLabel(remaining);
		phone.setBounds(756, 347, 136, 29);
		frame.getContentPane().add(phone);
		
		JLabel lblRfidNumber = new JLabel("RFID Number :");
		lblRfidNumber.setBounds(525, 407, 136, 35);
		frame.getContentPane().add(lblRfidNumber);
		
		JLabel rfid = new JLabel(sncard);
		rfid.setBounds(756, 407, 136, 35);
		frame.getContentPane().add(rfid);
		
		JLabel lblSystem = new JLabel("System :");
		lblSystem.setBounds(525, 472, 136, 29);
		frame.getContentPane().add(lblSystem);
		
		JLabel system = new JLabel("Prepaid");
		system.setBounds(756, 472, 136, 29);
		frame.getContentPane().add(system);
		
		textField = new JTextField();
		textField.setBounds(753, 233, 192, 29);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
	}
	
	 public void  vippreprintPayment() {
		 System.out.println("hellow world");
	        String msg="vipprepaymentdone";
	    	try {
	    		Socket server=new Socket(ipAddress,portNumer);
	            BufferedReader sin=new BufferedReader(new InputStreamReader(server.getInputStream()));
	            PrintStream sout=new PrintStream(server.getOutputStream());
	            BufferedReader stdin=new BufferedReader(new InputStreamReader(System.in)); 
	             
	            deposite=textField.getText();
		       sncard=SelectVip.cardnbr;  
		       System.out.println("deposite amount"+deposite);
		       String sendMessage =msg+'-'+','+sncard+','+deposite; 
		        sout.println(sendMessage);  
		        
		        String s1=sin.readLine(); 
	  	        String[] splitResult=s1.split(","); 
	  	     
	  	        exittime=splitResult[0];
	  	        rem=splitResult[1]; 
	  	        System.out.println(rem);
	    	 
	    	
		    } catch (UnknownHostException e) {
		        // TODO Auto-generated catch block
		        e.printStackTrace();
		    } catch (IOException e) {
		        // TODO Auto-generated catch block
		        e.printStackTrace();
		    }  
	    	
	    	
	    	//pdf calculaion 
			
		    Document document = new Document();
	     try {

	         String exitTime = exittime;
	         String[] dateValue = exitTime.split(":");

	         String pdfName = sncard + "_" + dateValue[0] + "-" + dateValue[1] + "-" + dateValue[2] + ".pdf";
	         PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(pdfName));
	         document.open();
	         document.add(new Paragraph("Vip Payment Info"));

	         PdfPTable table = new PdfPTable(2);
	         table.setWidthPercentage(70);
	         table.setSpacingBefore(8f);
	         table.setSpacingAfter(8f);
	         float[] colWidth
	                 = {5f, 5f};
	         table.setWidths(colWidth);
	       
	        PdfPCell c1 = new PdfPCell(new Paragraph("Deposite:"));
	         PdfPCell c2 = new PdfPCell(new Paragraph(deposite));
	         PdfPCell c3 = new PdfPCell(new Paragraph("Remaining:"));
	         PdfPCell c4 = new PdfPCell(new Paragraph(rem));
	         PdfPCell c5 = new PdfPCell(new Paragraph("Card Type:"));
	         PdfPCell c6 = new PdfPCell(new Paragraph(vipcartyp));
	        PdfPCell c7 = new PdfPCell(new Paragraph("Card Number:"));
	         PdfPCell c8 = new PdfPCell(new Paragraph(sncard));
	         PdfPCell c9 = new PdfPCell(new Paragraph("Payment Type:"));
	         PdfPCell c10 = new PdfPCell(new Paragraph("postpaid"));
	         PdfPCell c11 = new PdfPCell(new Paragraph("Payment Clear Time:"));
	         PdfPCell c12 = new PdfPCell(new Paragraph(exittime));
	        table.addCell(c1);
	         table.addCell(c2);
	         table.addCell(c3);
	         table.addCell(c4);
	         table.addCell(c5);
	         table.addCell(c6);
	         table.addCell(c7);
	         table.addCell(c8);
	         table.addCell(c9);
	         table.addCell(c10);
	         table.addCell(c11);
	         table.addCell(c12);
	         document.add(table);

	         document.close();
	         writer.close();
	     } catch (Exception e) {
	         e.printStackTrace();
	     } 
	     
		   
		    	
		        
		
		//end

	    
	   
	        
	    }
	
	void prepaidpayment()
	{  
		
		
		try {
      		Socket server=new Socket(ipAddress,portNumer);
              BufferedReader sin=new BufferedReader(new InputStreamReader(server.getInputStream()));
              PrintStream sout=new PrintStream(server.getOutputStream());
              BufferedReader stdin=new BufferedReader(new InputStreamReader(System.in)); 
              
  	      sncard=SelectVip.cardnbr;
          
         
           String sendMessage =requestMessage+'-'+','+sncard; 
  	       System.out.println("sncard"+sncard);
  	        sout.println(sendMessage);
  	       String s1=sin.readLine(); 
  	       String[] splitResult=s1.split(","); 
  	     
  	      remaining =splitResult[0];
  //	 mbl=splitResult[1];  
      	 vipcartyp =splitResult[2]; 
  	      
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
