//package payment;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
//import static payment.FareTypePopup.biketyp;
//import static payment.NormalPayment.exit_time;

public class FareTypePopup {

    private JFrame frame;
    static String biketyp;
    static String microtyp;
    static String minityp;
    
   


    /**
     * Launch the application.
     */
    public static void fare()
    {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    FareTypePopup window = new FareTypePopup();
                    //NormalPayment np=new NormalPayment();
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
    public FareTypePopup() {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JFrame("CarParkingManagementSystem");
        frame.setBounds(100, 100, 450, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JLabel lblBike = new JLabel("Bike");
        lblBike.setBounds(69, 47, 81, 26);
        frame.getContentPane().add(lblBike);

        JTextField bike = new JTextField();
        bike.setBounds(210, 50, 86, 20);
        frame.getContentPane().add(bike);
        bike.setColumns(10);   
        
       

        JLabel lblMicro = new JLabel("Micro");
        lblMicro.setBounds(69, 99, 81, 26);
        frame.getContentPane().add(lblMicro);

        JTextField micro = new JTextField();
        micro.setBounds(210, 102, 86, 20);
        frame.getContentPane().add(micro);
        micro.setColumns(10);

        JLabel lblMini = new JLabel("Mini");
        lblMini.setBounds(69, 160, 81, 26);
        frame.getContentPane().add(lblMini);

        JTextField mini = new JTextField();
        mini.setBounds(210, 163, 86, 20);
        frame.getContentPane().add(mini);
        mini.setColumns(10);

        JButton ok = new JButton("ok");
        ok.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) { 
             //   biketyp="null";
              //  System.out.println(biketyp);
             //   System.out.println("hellow world");
               
                 biketyp=bike.getText(); 
        // System.out.println("biketyp "+biketyp); 
                  microtyp=micro.getText();  
                  minityp=mini.getText(); 
                System.out.println(bike.getText());
         
                query1();
                 query2(); 
                 query3();
            }
        });
        ok.setBounds(167, 227, 89, 23);
        frame.getContentPane().add(ok);

        

    } 
    
    private void query1() 
    { 
        ResultSet rs;
         String query = "UPDATE vehicle SET vehicle_farePerHr = ?"
                
                + "WHERE vehicle_type = ?";
         
         PreparedStatement stmt; 
 
        try 
        {
 
            // set the corresponding param
            stmt = MyConnection.getConnection().prepareStatement(query);
            stmt.setString(1, biketyp);
            stmt.setString(2, "Bike");
            // update 
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

private void query2() 
    { 
        ResultSet rs;
         String query = "UPDATE vehicle SET vehicle_farePerHr = ?"
                
                + "WHERE vehicle_type = ?";
         
         PreparedStatement stmt; 
 
        try 
        {
 
            // set the corresponding param
            stmt = MyConnection.getConnection().prepareStatement(query);
            stmt.setString(1, microtyp);
            stmt.setString(2, "Micro");
            // update 
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
 
private void query3() 
    { 
        ResultSet rs;
         String query = "UPDATE vehicle SET vehicle_farePerHr = ?"
                
                + "WHERE vehicle_type = ?";
         
         PreparedStatement stmt; 
 
        try 
        {
 
            // set the corresponding param
            stmt = MyConnection.getConnection().prepareStatement(query);
            stmt.setString(1,minityp);
            stmt.setString(2, "Mini");
            // update 
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
    
   