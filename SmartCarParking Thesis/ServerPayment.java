import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class ServerPayment {
	String exit_time,sncard,query,entry_time,stay_time,total_fair,returnResult="0";
	String nowDate;
	LocalDateTime now;
	Double totalFare;
	String[] card;
	ResultSet rs, rs2;;
	PreparedStatement stmt;
	int tk;
	//private static final DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	String timeDiff;
	Double[] fare=new Double[5];
	

	public String payment(String rfid) {
		now=LocalDateTime.now();
		nowDate=dtf.format(now);
		String[] card = rfid.split(",");
		String sncard=card[1]; 
		//payment show exit time
		 LocalDateTime now = LocalDateTime.now();
	        exit_time = dtf.format(now);
	      
		
		ResultSet rs;

        String query ="SELECT timeDiff(now(),`entry_time`),`entry_time` FROM `rfid_info` WHERE `rfid_no`='" + sncard + "' and `rfid_info_status`=' 1 ' ";


        PreparedStatement stmt;
        try {
            stmt = MyConnection.getConnection().prepareStatement(query);
            rs = stmt.executeQuery();

            if (rs.next()) {
            	
            	timeDiff=rs.getString(1);
            	
            	String[] timeSplit=timeDiff.split(":");
        		
        		fare[0]=Double.parseDouble(timeSplit[0]);
        		
        		fare[1]=Double.parseDouble(timeSplit[1]);
        		fare[1]=fare[1]+1;
        		fare[1]=fare[1]/60;
        		fare[2]=Double.parseDouble(timeSplit[2]);
            	
                entry_time = rs.getString("entry_time");
              

                staytime_calculation(sncard); 
               // returnResult= entry_time;
               // cout.println(returnResult);

            }

        } catch (SQLException e1) {

            e1.printStackTrace();
        }  
        
       
		return returnResult;
	}
	private void staytime_calculation(String sncard) 
    
    {  
	
        
        String rent = " SELECT*\n" +
"                from\n" +
"                (SELECT button_id\n" +
"                FROM rfid_info \n" +
"                where rfid_no='" + sncard + "' and `rfid_info_status`=' 1 ')\n" +
"                as t1,vehicle\n" +
"                where button_id=vehicle_type";

        PreparedStatement stmt1;
        try {
            stmt1 = MyConnection.getConnection().prepareStatement(rent);
            rs2 = stmt1.executeQuery();

           if (rs2.next()) {
            	
                 tk= rs2.getInt("vehicle_farePerHr");  
                 
            }

        } catch (SQLException e1) {

            e1.printStackTrace();
        }
        
        
      
    
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

       //SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      
        Date d1 = null;
        Date d2 = null;

        try { 
        	
            d1 = format.parse(entry_time);
            d2 = format.parse(exit_time);
            System.out.println("d1"+d1);
            System.out.println("d2"+d2);

            long diff = d2.getTime() - d1.getTime();
            long diffSeconds = diff / 1000 % 60;
            long diffMinutes = diff / (60 * 1000) % 60;
            long diffHours = diff / (60 * 60 * 1000) % 24;
            long diffDays = diff / (24 * 60 * 60 * 1000);
            

            stay_time = diffDays + " d " + diffHours + " hr " + diffMinutes + " m " + diffSeconds + " s";
            int d, hr, m;
            d = (int) (diffDays * 24 * tk);
            hr = (int) (diffHours * tk);
            m = (int) ((tk * diffMinutes) / 60);
            int total = d + hr + m;
            fare[0]=fare[0]*tk;
			fare[1]=fare[1]*tk;
			totalFare=fare[0]+fare[1];
			totalFare=Math.ceil(totalFare);
			
            total_fair = Integer.toString(total);
            
            total_fair =Double.toString(totalFare);
            returnResult= total_fair+','+entry_time+','+exit_time+','+ timeDiff;
            //returnResult= totalFare+','+exit_time+','+ timeDiff;
           
          
          

        } catch (Exception e) {
            e.printStackTrace();
        } 
      
       

    } 
	public String paymentdone(String rfid)
	 {  
		now=LocalDateTime.now();
		nowDate=dtf.format(now);
		
		 LocalDateTime now = LocalDateTime.now();
	     exit_time = dtf.format(now);
		 
		 String[] card = rfid.split(",");
			String sncard=card[1]; 
			
	        ResultSet rs22;
	         String query2 = "update rfid_info set exit_time= ?,paymentdone=' 1 ',`settime`=' 0 ' where rfid_no = ? and `rfid_info_status`=' 1 '";


	         PreparedStatement stmt2;

	        try
	        {

	            // set the corresponding param
	        	System.out.println("Exit time here: "+exit_time);

	            stmt2 = MyConnection.getConnection().prepareStatement(query2);
	            stmt2.setString(1,exit_time);
	          //  stmt.setString(2, exit_time);
	            stmt2.setString(2, sncard);
	            returnResult="Payment Done";
	            // update
	            stmt2.executeUpdate();
	        } catch (SQLException e) {
	        	returnResult="Exception found";
	            System.out.println(e.getMessage());
	        }
	        
	        
	         /*
	        ResultSet r;
	       //  String q = "update rfid_info set paymentdone= ?, entry_time= ?,`settime`=' 0 ' where rfid_no = ? and `rfid_info_status`=' 1 ' ";
	        String q = "update rfid_info set paymentdone= ?, `settime`=' 0 ' where rfid_no = ? and `rfid_info_status`=' 1 ' ";

	         PreparedStatement s;

	        try
	        {

	            // set the corresponding param

	            s= MyConnection.getConnection().prepareStatement(q);
	            s.setInt(1, 1);
	            System.out.println("Entry time here : "+entry_time);

	           //   s.setString(2, exitttime);
	         //      s.setString(2, entrytime);

	            s.setString(2, sncard);
	            // update
	            s.executeUpdate();
	        } catch (SQLException e) {
	            System.out.println(e.getMessage());
	        }
	        //exit time
	        //System.out.println(entrytime);
	       ResultSet rr;
	       //  String qq = "update rfid_info set exit_time= ?, entry_time= ? where rfid_no = ? and `rfid_info_status`=' 1 ' ";
	       String qq = "update rfid_info set exit_time= ? where rfid_no = ? and `rfid_info_status`=' 1 ' ";

	         PreparedStatement ss;

	        try
	        {

	            // set the corresponding param

	            ss= MyConnection.getConnection().prepareStatement(qq);
	          //  ss.setInt(1, exittime);

	            ss.setString(1, exit_time);
	            //   ss.setString(2, entrytime);

	            ss.setString(1, sncard);
	            // update
	            ss.executeUpdate();
	        } catch (SQLException e) {
	            System.out.println(e.getMessage());
	        }
	        */
           return returnResult;
		
	 }
	    


}
