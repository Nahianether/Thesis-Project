import java.sql.*;

public class MyConnection {

	public static Connection getConnection() {
		Connection conn=null;
		try {
			//Class.forName("com.mysql.jdbc.driver");
			conn=DriverManager.getConnection("jdbc:mysql://localhost/carparking","root","");
		}
		catch(Exception e) {
			System.out.println("not found");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return conn;
	}
}
