import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminRegistration {

    private JFrame frame;
    JComboBox comboBox;
    JLabel lblName,email,phoneno,lblType,image,lblNewLabel,lblQues;
    JTextField textField_1,textField_2,textField_3,textField_4,textField_5;
    JButton browse;
    static String admintype,requestMessage="Create new Admin";
    File fimage,f;
    FileInputStream fioimage;
    FileInputStream fis;
    int size;
    String s;
    InputStream is;
    public BufferedReader br;
    public FileReader reader;
    int possible;
    static String ipAddress;
    static int portNumer;
    String s1;

    /**
     * Launch the application.
     */

   
    public void run()
    
    {

        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {

                    AdminRegistration window = new AdminRegistration();
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
    public AdminRegistration() {
        
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */

    public void initialize() {
    	IpPortConfiguration ipConfig=new IpPortConfiguration();
		ipAddress=ipConfig.getIpAddress();
		portNumer=ipConfig.getPortNumber();

        frame = new JFrame("CarParkingManagementSystem");
        frame.setBounds(300, 100,700, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JButton ok = new JButton("Create");
        ok.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	GmailCheck gcheck=new GmailCheck();
            	boolean value=gcheck.isAddressValid(textField_2.getText()) ;
            	if(value==true) {
            		possible=1;
            		int indexSelected=comboBox.getSelectedIndex();
                    if(indexSelected==0) {
                        admintype="Super Admin";
                    }
                    else if(indexSelected==1) {
                        admintype="Admin";
                    }
            
                    databaseInsertServer();
                    if(s1.equals("New Admin Enrolled")) {
                    	AdminHome adminemploy=new AdminHome(); 
        	        	frame.setVisible(false);
        	        	adminemploy.run2();
                    }
            	}
            	else {
            		JOptionPane.showMessageDialog(null, "Gmail is not Valid");
            	}
            	
                
                
                
            }
        });
        ok.setBounds(300,450, 106, 30);
        frame.getContentPane().add(ok);

        lblName = new JLabel("Name :");
        lblName.setBounds(150, 100, 200, 30);
        frame.getContentPane().add(lblName);

        textField_1 = new JTextField();
        textField_1.setBounds(250, 100, 200, 30);
        frame.getContentPane().add(textField_1);
        textField_1.setColumns(10);

        email = new JLabel("Email :");
        email.setBounds(150, 150, 200, 30);
        frame.getContentPane().add(email);

        textField_2 = new JTextField();
        textField_2.setBounds(250, 150, 200, 30);
        frame.getContentPane().add(textField_2);
        textField_2.setColumns(10);

        phoneno = new JLabel("Phone No :");
        phoneno.setBounds(150, 200, 200, 30);
        frame.getContentPane().add(phoneno);

        textField_3 = new JTextField();
        textField_3.setBounds(250, 200, 200, 30);
        frame.getContentPane().add(textField_3);
        textField_3.setColumns(10);

         lblType = new JLabel("Type :");
        lblType.setBounds(150,300, 200, 30);
        frame.getContentPane().add(lblType);

        comboBox = new JComboBox();
        comboBox.setBounds(250,300, 200, 30);
        frame.getContentPane().add(comboBox);
        comboBox.addItem("Super Admin");
        comboBox.addItem("Admin");
        
        lblQues= new JLabel("Security Ques. :");
        lblQues.setBounds(150,350, 200, 30);
        frame.getContentPane().add(lblQues);

        textField_5 = new JTextField();
        textField_5.setBounds(250, 350, 200, 30);
        frame.getContentPane().add(textField_5);
        textField_5.setColumns(10);

        image = new JLabel("Image :");
        image.setBounds(150,250, 200, 30);
        frame.getContentPane().add(image);

        textField_4 = new JTextField();
        textField_4.setBounds(250,250, 200, 30);
        frame.getContentPane().add(textField_4);
        textField_4.setColumns(10);

        /*lblNewLabel = new JLabel("Images");
        lblNewLabel.setBounds(743, 466, 274, 187);
        frame.getContentPane().add(lblNewLabel);*/

        browse = new JButton("Browse");
        browse.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
                FileNameExtensionFilter filter = new FileNameExtensionFilter("*.IMAGE", "jpg","gif","png");
                fileChooser.addChoosableFileFilter(filter);
                int result = fileChooser.showSaveDialog(null);
                if(result == JFileChooser.APPROVE_OPTION){
                    File selectedFile = fileChooser.getSelectedFile();
                    String path = selectedFile.getAbsolutePath();
                    //textField_4.set(path);
                    s = path;
                    try {
                        reader = new FileReader(s);
                        br = new BufferedReader(reader);
                       // textField_4.read(br, null);
                        textField_4.setText(s);
                       // f=new File(s);
                       // size=(int)f.length();
        		       // fis=new FileInputStream(f);
        		        //stmtImage.setBinaryStream(1,fis,size);
                        br.close();
                        textField_4.requestFocus();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
                else if(result == JFileChooser.CANCEL_OPTION){
                    System.out.println("No Data");
                }
            }
        });
        browse.setBounds(500,250, 95, 30);
        frame.getContentPane().add(browse);

    }
    
    protected void databaseInsertServer() {
        // TODO Auto-generated method stub
        try {
        	Socket server=new Socket(ipAddress,portNumer);
	        //Socket server=new Socket("localhost",8800);
	        BufferedReader sin=new BufferedReader(new InputStreamReader(server.getInputStream()));
	        PrintStream sout=new PrintStream(server.getOutputStream());
	        BufferedReader stdin=new BufferedReader(new InputStreamReader(System.in));
	        
            String sendMessage =requestMessage+'-'+','+textField_1.getText()+','+textField_2.getText()+','+textField_3.getText()+','+admintype+','+s+','+textField_5.getText();

            sout.println(sendMessage);

            s1=sin.readLine();

           // System.out.println("Return Message:"+s1);
            
            JOptionPane.showMessageDialog(null, s1);
            
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
