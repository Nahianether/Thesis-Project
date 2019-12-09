import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ServerLogin {
	String[] splitLogin;

	public String loginUser(String rfid) {

		// TODO Auto-generated method stub
		
		splitLogin=rfid.split(",");
		
		//System.out.println("Inside Function"+splitLogin);
		
		
		String returnResult="0";
		
		ResultSet rsEmployee,rsAdmin;
		//String query=" SELECT * FROM `employee` WHERE `name`=? and `pass`=? ";
			LoginSessionEmployee loginSessionEmployee=new LoginSessionEmployee();
			
		    //loggedUser=splitLogin[1];
			String queryEmployee=" SELECT * FROM `employ` WHERE `employ_mail`='"+splitLogin[1]+"' and `employ_pass`='"+splitLogin[2]+"' ";
			String queryAdmin=" SELECT * FROM `admin` WHERE `admin_mail`='"+splitLogin[1]+"' and `admin_pass`='"+splitLogin[2]+"' ";
			PreparedStatement stmtEmployee,stmtAdmin;
			try {
				System.out.println("Hi 1");
				stmtEmployee = MyConnection.getConnection().prepareStatement(queryEmployee);
				System.out.println("Hi 2");
				stmtAdmin = MyConnection.getConnection().prepareStatement(queryAdmin);
				rsEmployee=stmtEmployee.executeQuery();
				rsAdmin=stmtAdmin.executeQuery();
				if(rsEmployee.next()){
					//JOptionPane.showMessageDialog(null, "Employee Logged In");
					
					returnResult= "Employee Logged In";
					loginSessionEmployee.setName(splitLogin[1]);
					//System.out.println("here is"+loginSessionEmployee.getName());
				}
				else if(rsAdmin.next()) {
					//JOptionPane.showMessageDialog(null,"Admin Logged In");
					returnResult="Admin Logged In";				
					//loginSession.setName(splitLogin[1]);
				}
				else {
					//JOptionPane.showMessageDialog(null, "Invalid username or password");
					returnResult="Invalid username or password";
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			
			
		// TODO Auto-generated method stub
		return returnResult;
	}

}
