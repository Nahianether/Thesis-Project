
import com.github.sarxos.webcam.*;

import java.io.File;
import java.io.FileInputStream;

import java.io.IOException;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.imageio.ImageIO;

public class WebcamTutorial {
	
	static int option;
	
	public static void setOption(int option1) {
		option=option1;
	}

    /*
    public static void main(String[] args) throws IOException {
        // TODO code application logic here

        dbConnection();
        
          

        
    }*/

    public static void dbConnection() throws IOException {

        ResultSet rs;
        PreparedStatement stmt,stmtImage;
        String query="";
        
        if(option==2) {
        	query = "SELECT * from `rfid_info` WHERE `rfid_no` IS NOT NULL and `image` IS NULL limit 1";
        }
        else if(option==1) {
        	query = "SELECT * from `vip_rfid_info` WHERE `rfid_no` IS NOT NULL and `image` IS NULL limit 1";
        }
        
        try {
            stmt = MyConnection.getConnection().prepareStatement(query);
            rs = stmt.executeQuery();

            while (rs.next()) {
               
                String firstName = rs.getString("booth_id");
                String lastName = rs.getString("rfid_no");
                
                Date dateCreated = rs.getDate("entry_time");
                
                String  queryImage="";
                if(option==2) {
                queryImage="UPDATE `rfid_info` SET `image`=? WHERE `rfid_no`='"+lastName+"' and  `image` is NULL limit 1";
                }
                else if(option==1) {
                	queryImage="UPDATE `vip_rfid_info` SET `image`=? WHERE `rfid_no`='"+lastName+"' and  `image` is NULL limit 1";
                }
                stmtImage=MyConnection.getConnection().prepareStatement(queryImage);
                String name= firstName+"-"+lastName+"-"+dateCreated+"-"+".png";  
                
                Webcam webcam = Webcam.getDefault();
		        webcam.open();
		        ImageIO.write(webcam.getImage(), "PNG", new File(name));
		       // File f=new File("H:\\\\\\\\Study\\\\\\\\DBMS LAB\\\\\\\\Car Parking Project Data\\\\\\\\project\\\\\\\\Socket\\\\\\\\WebCamTutorial\\\\\\\\"+name);
		        
		        File f=new File(name);
		        
		        int size=(int)f.length();
		        FileInputStream fis=new FileInputStream(f);
		        stmtImage.setBinaryStream(1,fis,size);
		        stmtImage.executeUpdate();
		        System.out.println("Image Done");
		        
		    }

        } catch (SQLException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
            System.out.println("Error:"+e1.getMessage());
        }  
           
         
         
        
       

    }

}