import java.sql.*;
import java.io.*;
public class Image{
    public static Image getimage(){
        //Statement stmtprimary;
        try{
            //stmtprimary=MyConnection.getConnection().createStatement();
            File imgfile = new File("C:\\Users\\kanon\\IdeaProjects\\NormalExit\\image\\x.jpg");

            FileInputStream fin = new FileInputStream(imgfile);
            int imgSize=(int) imgfile.length();

            PreparedStatement pre =
                    MyConnection.getConnection().prepareStatement("insert into `admin` SET `image`=? ");


            pre.setBinaryStream(3, fin,(int)imgfile.length());
            pre.executeUpdate();
            System.out.println("Successfully inserted the file into the database!");

            pre.close();
            //con.close();
        }catch (Exception e1){
            System.out.println(e1.getMessage());
        }
        return getimage();
    }
}