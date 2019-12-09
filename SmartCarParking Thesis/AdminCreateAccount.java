import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminCreateAccount{

    private JFrame frame;
    JRadioButton admin,employee;
    JButton create;
    private final ButtonGroup buttonGroup = new ButtonGroup();

    /**
     * Launch the application.
     */
    public void run() {
    //public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    AdminCreateAccount window = new AdminCreateAccount();
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
    public AdminCreateAccount() {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    public void initialize() {
        frame = new JFrame("CarParkingManagementSystem");
        frame.setBounds(100, 100,450, 250);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        admin = new JRadioButton("Admin", Boolean.parseBoolean(Integer.toString(1)));
        buttonGroup.add(admin);
        admin.setSelected(true);
        admin.setBounds(120, 50, 100, 23);
        frame.getContentPane().add(admin);

        employee = new JRadioButton("Employee", Boolean.parseBoolean(Integer.toString(2)));
        buttonGroup.add(employee);
        employee.setSelected(true);
        employee.setBounds(240, 50, 100, 23);
        frame.getContentPane().add(employee);

        create = new JButton("Create");
        create.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //int indexSelected=buttonGroup.getButtonCount();
               // System.out.println("HI:"+indexSelected);
                if(admin.isSelected()) {
                    AdminRegistration x=new AdminRegistration();
                    frame.setVisible(false);
                    x.run();
                }
                else if(employee.isSelected()) {
                    EmployeeRegistration y=new EmployeeRegistration();
                    frame.setVisible(false);
                    y.run();
                }
            }
        });
        create.setBounds(170, 120, 89, 23);
        frame.getContentPane().add(create);
    }
}
