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

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.awt.Color;
import java.awt.Font;

public class PaymentPostpaid {

	private JFrame frame; 
	static String ipAddress;
    static int portNumer;
    static String sncard;
    String requestMessage="postpaid"; 
    public  static String blanaceused;
    public   static String  mbl ; 
     public  static String vipcartyp;
     public String exittime;


	/**
	 * Launch the application.
	 */ 
     JLabel lblTotalBill,lblVehicleType,lblPhoneNumber,lblRfidNumber,lblSystem,lblexittime;
     
	public void postpaid() {
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

	
	public PaymentPostpaid() {
	
	}

	private void initialize() {
		frame = new JFrame("CarParkingManagementSystem");
		frame.getContentPane().setForeground(new Color(0, 0, 0));
		frame.setBounds(0, 0, 1500, 900);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton payprint = new JButton("Pay & Print");
		payprint.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) { 
				 vippostprintPayment();
				 JOptionPane.showMessageDialog(null, "Payment Done Successfully");
				 EmployeeHome ehm=new EmployeeHome();
				 frame.setVisible(false);
				 ehm.run();
			}
		}); 
		
		postpaidpayment(); 
		
		payprint.setBounds(633, 639, 104, 29);
		frame.getContentPane().add(payprint);
		
		JLabel lblTotalBill = new JLabel("Total Bill :");
		lblTotalBill.setBounds(525, 229, 136, 29);
		frame.getContentPane().add(lblTotalBill);
		
		JLabel bill = new JLabel(blanaceused);
		bill.setFont(new Font("Tahoma", Font.PLAIN, 18));
		bill.setForeground(Color.RED);
		bill.setBounds(756, 226, 136, 29);
		frame.getContentPane().add(bill);
		
		JLabel lblVehicleType = new JLabel("Vehicle Type :");
		lblVehicleType.setBounds(525, 288, 136, 29);
		frame.getContentPane().add(lblVehicleType);
		
		JLabel vehicletype = new JLabel(vipcartyp);
		vehicletype.setBounds(756, 288, 136, 29);
		frame.getContentPane().add(vehicletype);
		
		JLabel lblPhoneNumber = new JLabel("Employee ID :");
		lblPhoneNumber.setBounds(525, 347, 136, 29);
		frame.getContentPane().add(lblPhoneNumber);
		
		JLabel phone = new JLabel(EmployeeLogin.username);
		phone.setBounds(756, 347, 136, 29);
		frame.getContentPane().add(phone);
		
		JLabel lblRfidNumber = new JLabel("RFID Number :");
		lblRfidNumber.setBounds(525, 407, 136, 35);
		frame.getContentPane().add(lblRfidNumber);
		
		JLabel rfid = new JLabel(SelectVip.cardnbr);
		rfid.setBounds(756, 407, 136, 35);
		frame.getContentPane().add(rfid);  

      
		
		JLabel lblSystem = new JLabel("System :");
		lblSystem.setBounds(525, 472, 136, 29);
		frame.getContentPane().add(lblSystem);
		
		JLabel system = new JLabel("Postpaid");
		system.setBounds(756, 472, 136, 29);
		frame.getContentPane().add(system);
		
		
		
	} 
	 public void  vippostprintPayment() {
	        String msg="vippostpayment done";
	    	try {
	    		Socket server=new Socket(ipAddress,portNumer);
	            BufferedReader sin=new BufferedReader(new InputStreamReader(server.getInputStream()));
	            PrintStream sout=new PrintStream(server.getOutputStream());
	            BufferedReader stdin=new BufferedReader(new InputStreamReader(System.in)); 
	            
		       sncard=SelectVip.cardnbr; 
		       String sendMessage =msg+'-'+','+sncard; 
		        sout.println(sendMessage);  
		        
		        String s1=sin.readLine(); 
	  	        String[] splitResult=s1.split(","); 
	  	     
	  	        exittime=splitResult[0];
	    	 
	    	
		    } catch (UnknownHostException e) {
		        // TODO Auto-generated catch block
		        e.printStackTrace();
		    } catch (IOException e) {
		        // TODO Auto-generated catch block
		        e.printStackTrace();
		    } 

	    
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
          
           PdfPCell c1 = new PdfPCell(new Paragraph("Used Balance:"));
            PdfPCell c2 = new PdfPCell(new Paragraph(blanaceused));
            PdfPCell c3 = new PdfPCell(new Paragraph("Card Type:"));
            PdfPCell c4 = new PdfPCell(new Paragraph(vipcartyp));
           PdfPCell c5 = new PdfPCell(new Paragraph("Card Number:"));
            PdfPCell c6 = new PdfPCell(new Paragraph(sncard));
            PdfPCell c7 = new PdfPCell(new Paragraph("Payment Type:"));
            PdfPCell c8 = new PdfPCell(new Paragraph("postpaid"));
            PdfPCell c9 = new PdfPCell(new Paragraph("Payment Clear Time:"));
            PdfPCell c10 = new PdfPCell(new Paragraph(exittime));
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
            document.add(table);

            document.close();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        } 
        
	   
	    	
	        
	        
	    }

	   


	void postpaidpayment() 
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
  	     
  	 blanaceused=splitResult[0];
  	 mbl=splitResult[1];  
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
