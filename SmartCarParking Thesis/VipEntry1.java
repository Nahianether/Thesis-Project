
import java.awt.EventQueue;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Random;
import java.awt.event.ActionEvent;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;

public class VipEntry1 {

    private JFrame frame;
    String cardNo;
    String boothid="1";

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    VipEntry1 window = new VipEntry1();
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
    public VipEntry1() {
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
        
        JTextField cardno = new JTextField();
        cardno.setBounds(196, 109, 195, 30);
        frame.getContentPane().add(cardno);
        cardno.setColumns(10);
        
        JLabel lblCardNo = new JLabel("Card No :");
        lblCardNo.setBounds(196, 76, 80, 22);
        frame.getContentPane().add(lblCardNo);
        
        JButton btnok = new JButton("OK");
        btnok.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
            	cardNo=cardno.getText();
            	connectServer();
            }
        });
        btnok.setBounds(240, 200, 90, 30);
        frame.getContentPane().add(btnok);
    }
    
    
    private void connectServer()
    {
         
         try {
        Socket server=new Socket("localhost",8800);
        BufferedReader sin=new BufferedReader(new InputStreamReader(server.getInputStream()));
        PrintStream sout=new PrintStream(server.getOutputStream());
        BufferedReader stdin=new BufferedReader(new InputStreamReader(System.in));
        
        String sendMessage =boothid+','+cardNo; 

        sout.println(sendMessage);
        
        String s1=sin.readLine(); 
        if(s1.equals("1")) {
        	System.out.println("Barrier Gate is opened");
        }
        else {
        	System.out.println("Barrier Gate is Closed");
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
