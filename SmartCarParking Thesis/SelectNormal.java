//package payment;

import java.awt.EventQueue;
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

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JOptionPane;
//import static payment.FareTypePopup.biketyp;

public class SelectNormal {

    JFrame frame;
    public static String cardnbr;
    
    static String ipAddress;
    static int portNumer; 
    String requestMessage="Card Check";


    /**
     * Launch the application.
     */
   public void normal() 
   // public static void main(String[] args)
    {

        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try { 
                	
                    SelectNormal window = new SelectNormal();
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
    public SelectNormal() { 
    	    IpPortConfiguration ipConfig=new IpPortConfiguration();
	 		ipAddress=ipConfig.getIpAddress();
	 		portNumer=ipConfig.getPortNumber();
	 		//frame.setVisible(true);
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JFrame("CarParkingManagementSystem");
        frame.setBounds(100, 100, 400, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JTextField card = new JTextField();
        card.setBounds(130, 50, 150, 30);
        frame.getContentPane().add(card);
        card.setColumns(10);

        JLabel lblCardNo = new JLabel("Card No :");
        lblCardNo.setBounds(70, 50, 130, 23);
        frame.getContentPane().add(lblCardNo);

        JButton btnok = new JButton("ok");
        btnok.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                cardnbr = card.getText();
                //  System.out.println("selectNormal" + cardnbr);
                // System.out.println(cardnbr);  
              // NormalPayment pay=new NormalPayment(cardnbr); 
              // pay.npayment();
                try {
					nbrcheck();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

            }
        });

        btnok.setBounds(175, 90, 55, 30);
        frame.getContentPane().add(btnok);

    }

    public void nbrcheck() throws UnknownHostException, IOException 
    {
    	try
    	{
        
    	Socket server=new Socket(ipAddress,portNumer);
        //Socket server=new Socket("localhost",8800);
        BufferedReader sin=new BufferedReader(new InputStreamReader(server.getInputStream()));
        PrintStream sout=new PrintStream(server.getOutputStream());
        BufferedReader stdin=new BufferedReader(new InputStreamReader(System.in)); 
        
        String sendMessage =requestMessage+'-'+','+cardnbr ; 
      

        sout.println(sendMessage);
        
        String s1=sin.readLine(); 
        String[] splitResult=s1.split(","); 
       
        if(splitResult[0].equals(cardnbr)) {
        
        	NormalPayment pay=new NormalPayment(cardnbr); 
        	frame.setVisible(false);
              pay.npayment();
        }
        else 
        { 
        	
            JOptionPane.showMessageDialog(null, "Invalid Card Number");

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
