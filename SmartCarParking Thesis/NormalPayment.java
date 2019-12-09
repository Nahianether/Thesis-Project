//package payment;

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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
//import static payment.FareTypePopup.biketyp;
//import static payment.FareTypePopup.microtyp;
//import static SelectNormal.cardnbr;


public class NormalPayment {

    private JFrame frame;
    static String exit_time;
    static String entry_time;
    static String stay_time;
  
    static int tk;
    static String total_fair;
    static String sncard;
    static String ipAddress;
    static int portNumer; 
    String requestMessage="Payment";


    JLabel lblEmployeeId, lblTotalFair, lblStayTime, lblExitTime,lblentryTime, lblCardNumber, lblCardType, lblCardInformation,
            employeeid, totalfair, staytime, exittime, cardnumber, cardtype,entryTime;

    private static final DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
   private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * Launch the application.
     */
    public void npayment() {
    	
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
    public NormalPayment(String cardnbr) {
    	
    	
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
    	
       
        frame = new JFrame("CarParkingManagementSystem");
        frame.setBounds(50, 100, 1500, 900);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JButton payprint = new JButton("Pay & Print");
        payprint.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
               
                              printPayment();
                              JOptionPane.showMessageDialog(null, "Bill Printed");
                              EmployeeHome emh=new EmployeeHome();
                              frame.setVisible(false);
                              emh.run();
            }
        });
        payprint.setBounds(668, 643, 114, 29);
        frame.getContentPane().add(payprint);

        lblEmployeeId = new JLabel("Employee ID :");
        lblEmployeeId.setBounds(584, 500, 114, 29);
        frame.getContentPane().add(lblEmployeeId);

        lblTotalFair = new JLabel("Total Fair :");
        lblTotalFair.setBounds(584, 450, 117, 29);
        frame.getContentPane().add(lblTotalFair);

        lblStayTime = new JLabel("Stay Time :");
        lblStayTime.setBounds(584, 400, 114, 29);
        frame.getContentPane().add(lblStayTime);

        lblExitTime = new JLabel("Exit Time :");
        lblExitTime.setBounds(584, 350, 114, 29);
        frame.getContentPane().add(lblExitTime);
        
        lblentryTime = new JLabel("Entry Time :");
        lblentryTime.setBounds(584, 300, 114, 29);
        frame.getContentPane().add(lblentryTime);

        lblCardNumber = new JLabel("Card Number :");
        lblCardNumber.setBounds(584, 250, 114, 29);
        frame.getContentPane().add(lblCardNumber);

        lblCardType = new JLabel("Card Type :");
        lblCardType.setBounds(584, 200, 114, 29);
        frame.getContentPane().add(lblCardType);

        query();
        
        lblCardInformation = new JLabel("Card Information :");
        lblCardInformation.setBounds(504, 120, 117, 29);
        frame.getContentPane().add(lblCardInformation);

      
       

        employeeid = new JLabel(EmployeeLogin.username);
       employeeid.setBounds(762, 500, 114, 29);
       frame.getContentPane().add(employeeid);

        totalfair = new JLabel(total_fair);
        totalfair.setBounds(762, 450, 114, 29);
        frame.getContentPane().add(totalfair);

        staytime = new JLabel(stay_time);
        staytime.setBounds(762, 400, 400, 29);
        frame.getContentPane().add(staytime);

        exittime = new JLabel(exit_time);
        exittime.setBounds(762, 350, 300, 29);
        frame.getContentPane().add(exittime);
        
        entryTime = new JLabel(entry_time);
        entryTime.setBounds(762, 300, 300, 29);
        frame.getContentPane().add(entryTime);

        cardnumber = new JLabel(sncard);
        cardnumber.setBounds(762, 250, 300, 29);
        frame.getContentPane().add(cardnumber);

        cardtype = new JLabel("Normal");
        cardtype.setBounds(762, 200, 300, 29);
        frame.getContentPane().add(cardtype);
        
       
    }
    public void printPayment() {
        String msg="payment done";
    	try {
    		Socket server=new Socket(ipAddress,portNumer);
            //Socket server=new Socket("localhost",8800);
            BufferedReader sin=new BufferedReader(new InputStreamReader(server.getInputStream()));
            PrintStream sout=new PrintStream(server.getOutputStream());
            BufferedReader stdin=new BufferedReader(new InputStreamReader(System.in)); 
            
	       sncard=SelectNormal.cardnbr;
	    
	       String sendMessage =msg+'-'+','+sncard; 
	     
	        sout.println(sendMessage); 
    	 
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

        Document document = new Document();
        try {

            String exitTime = exittime.getText();
            String[] dateValue = exitTime.split(":");

            String pdfName = cardnumber.getText() + "_" + dateValue[0] + "-" + dateValue[1] + "-" + dateValue[2] + ".pdf";
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(pdfName));
            document.open();
            document.add(new Paragraph("Payment Info"));

            PdfPTable table = new PdfPTable(2);
            table.setWidthPercentage(70);
            table.setSpacingBefore(8f);
            table.setSpacingAfter(8f);
            float[] colWidth
                    = {4f, 4f};
            table.setWidths(colWidth);
            PdfPCell c1 = new PdfPCell(new Paragraph(lblCardType.getText()));
            PdfPCell c2 = new PdfPCell(new Paragraph(cardtype.getText()));
            PdfPCell c3 = new PdfPCell(new Paragraph(lblCardNumber.getText()));
            PdfPCell c4 = new PdfPCell(new Paragraph(cardnumber.getText()));
            PdfPCell c5 = new PdfPCell(new Paragraph(lblentryTime.getText()));
            PdfPCell c6 = new PdfPCell(new Paragraph(entryTime.getText()));
            
            PdfPCell c7 = new PdfPCell(new Paragraph(lblExitTime.getText()));
            PdfPCell c8 = new PdfPCell(new Paragraph(exittime.getText()));
            PdfPCell c9 = new PdfPCell(new Paragraph(lblStayTime.getText()));
            PdfPCell c10 = new PdfPCell(new Paragraph(staytime.getText()));
            PdfPCell c11 = new PdfPCell(new Paragraph(lblTotalFair.getText()));
            PdfPCell c12 = new PdfPCell(new Paragraph(totalfair.getText()));
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
        
    }

   

    private void query() { 
    	
    	
    	try {
    		Socket server=new Socket(ipAddress,portNumer);
            BufferedReader sin=new BufferedReader(new InputStreamReader(server.getInputStream()));
            PrintStream sout=new PrintStream(server.getOutputStream());
            BufferedReader stdin=new BufferedReader(new InputStreamReader(System.in)); 
            
            sncard=SelectNormal.cardnbr;
	        String sendMessage =requestMessage+'-'+','+sncard; 

	        sout.println(sendMessage);
	        
	        String s1=sin.readLine(); 
	   
	        String[] splitResult=s1.split(","); 
	    
	        total_fair=splitResult[0]+ " Tk";
	        entry_time=splitResult[1];
	    
	        exit_time=splitResult[2]; 
	     
	        stay_time=splitResult[3]; 
	      
	        
	     
	        
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
