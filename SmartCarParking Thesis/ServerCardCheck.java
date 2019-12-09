import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

public class ServerCardCheck {

	public String cardCheck(String rfid)
	{    
		
		String[] card = rfid.split(",");
		String cardnbr=card[1]; 
		
		String returnResult="0";
		
		 ResultSet normal;
        String querynormal = " SELECT * FROM `rfid_info` WHERE `rfid_no`='" + cardnbr + "' ";
      
        PreparedStatement stmtEmployee;
        try {
            stmtEmployee = MyConnection.getConnection().prepareStatement(querynormal);
            normal = stmtEmployee.executeQuery();
            if (normal.next()) 
            {
            
            	returnResult= cardnbr;

            } else {
                JOptionPane.showMessageDialog(null, "Invalid Card Number");
            }
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        return returnResult;
	}

}
