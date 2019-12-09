import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import javax.swing.JOptionPane;

public class ServerNormalExit {
	String time,exittime,entrytime,exit_boothid,returnResult="0",datediff; 
	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	//String datediff;
	//String[] timeDiff;
	int hour,min,totalTime;

	public String exit(String rfid) {
		// TODO Auto-generated method stub
		
		String[] exitcard = rfid.split(",");
		String exitcardnbr=exitcard[2];  
		exit_boothid=exitcard[1];
		
		
		ResultSet rs1;

        String query1 ="SELECT * FROM `rfid_info` WHERE `rfid_no`='" + exitcardnbr + "' and `rfid_info_status` = ' 1 ' ";
        System.out.println("exitcardnumber: ");
        System.out.println(exitcardnbr);


        PreparedStatement stmt1;
        try { 
             
            stmt1 = MyConnection.getConnection().prepareStatement(query1);
       
            rs1 = stmt1.executeQuery();
          
            System.out.println("what");
            if (rs1.next()) {
             
                int paymentdone = rs1.getInt("paymentdone");  
                int setTime=rs1.getInt("settime");
                System.out.println("paymentdone:"+paymentdone);
              
                if(paymentdone==1)
                {   
                	System.out.println("HELLO");
                    query(exitcardnbr);
                    System.out.println("Result : "+returnResult);
                }
                else if(paymentdone==0 && setTime==0)
                {  
                   
                returnResult="0s"; 
               System.out.println(returnResult);
               
                }
                else if(paymentdone==0 && setTime==1)
                {  
                   
                returnResult="-1s"; 
               System.out.println(returnResult);
               
                }
                
              

            }
            else {
            	System.out.println("what the");
            	returnResult="-2s";
            }

        } catch (SQLException e1) {
        	
        } 
		return returnResult;
	}
	public  void query(String exitcardnbr)
    { 
         
        //String returnResult="0s"; 
		String carType = null;
		//String en;
		System.out.println("exit card number function: ");
         LocalDateTime now = LocalDateTime.now();
        time = dtf.format(now); 
        System.out.println("exit booth exit time:"+time);  
        System.out.println(exitcardnbr);
      ResultSet res; 
       String ans="SELECT *,timeDiff(now(),`exit_time`) FROM `rfid_info` WHERE `rfid_no`='" + exitcardnbr + "' and `rfid_info_status` = ' 1 ' ";
        PreparedStatement ment;
      try {
           ment=MyConnection.getConnection().prepareStatement(ans); 
            res=ment.executeQuery(); 
            System.out.println("HIIIII");
            
            if ( res.next() ) {
            	
            	datediff=res.getString("timeDiff(now(),`exit_time`)");
            	String[] timeDiff=datediff.split(":");
            	hour=(Integer.parseInt(timeDiff[0]))*60;
            	min=Integer.parseInt(timeDiff[1]);
            	totalTime=hour+min;
            	
            	System.out.println("Date Diff: "+datediff);
            	System.out.println("Time Diff: "+totalTime);
                exittime = res.getString("exit_time");
                carType=res.getString("button_id");
                System.out.println("payment done exittime"+exittime);
             //   en = res.getString("entry_time");
              //  System.out.println("entry_time"+en);
            }
            ment.close();
        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        } 
      
    System.out.println(exittime);
    
   
   
            System.out.println("Car Type: "+carType);
           
    		int set=1; 
            ResultSet timeset;
   		 	String settime= " SELECT * FROM `settime` where `Type`=? ";   
   		 	PreparedStatement st;
           try {
               st= MyConnection.getConnection().prepareStatement(settime);
               st.setString(1, carType);
               timeset = st.executeQuery();
               if (timeset.next()) 
               {
               	
               	 set= timeset.getInt("set_time"); 
               //	returnResult= cardnbr+","+VIPrfid_payment_type;
               	System.out.println("set_time"+set);

               } else {
               	System.out.println("outside query value");
             
               }
           } catch (SQLException e1) {
               e1.printStackTrace();
           }  
           
           
           
            //end
           
            
            
           // System.out.println("total minute "+total);
           // System.out.println("Set time "+set);
            
          //if( total > set )
            if( totalTime > set )
          { 
              
        
         
         ResultSet rs2;
         String query2 = "update rfid_info set settime= ? , paymentdone= ? , entry_time= ?, exit_time= ? where rfid_no = ? and `rfid_info_status` = ' 1 ' ";
         PreparedStatement stmt2; 
        try   
        
        {   
        	
            stmt2 = MyConnection.getConnection().prepareStatement(query2);
            stmt2.setInt(1,1);
             stmt2.setInt(2,0);
              stmt2.setString(3,exittime);
            stmt2.setString(4,time);

            stmt2.setString(5, exitcardnbr);
            stmt2.executeUpdate();
        } catch (SQLException e) 
        {
            System.out.println(e.getMessage());
        }
        
            //  ExitDelayPopup ex1=new ExitDelayPopup();
            //  ex1.extrtime();
       // System.out.println("exit delay pop up active"); 
        returnResult="-1s"; 
        System.out.println("Return Result: "+returnResult);

             
              
          } 
         // else if(total <= set) 
            else if(totalTime <= set) 
          {
        	  
        	  int open_barrier=0;
    	       ResultSet rs2;
   	        String rent = " SELECT*\n" +
   	"                from\n" +
   	"                (SELECT button_id\n" +
   	"                FROM rfid_info \n" +
   	"                where rfid_no='" + exitcardnbr + "' and `rfid_info_status` = ' 1 '  )\n" +
   	"                as t1,vehicle\n" +
   	"                where button_id=vehicle_type";

   	        PreparedStatement stmt;
   	        try {
   	            stmt = MyConnection.getConnection().prepareStatement(rent);
   	            rs2 = stmt.executeQuery();

   	           if (rs2.next()) {
   	            	 System.out.println("next one");

   	            	 open_barrier= rs2.getInt("open_barrier");  
   	                 System.out.println("open_barrier");
   	                 System.out.println(open_barrier);
   	                 returnResult=open_barrier+"";

   	            }

   	        } catch (SQLException e1) {

   	            e1.printStackTrace();
   	        } 
   	        String qq = "update rfid_info set rfid_info_status	= ?,exit_booth_id=?,exit_time= ? where rfid_no = ? and `rfid_info_status` = ' 1 ' ";

	         PreparedStatement ss;

	        try
	        {

	         //  System.out.println("en ex" +en+"    "+ex); 
	         //  System.out.println("rfid info stst and exit booth set:");
	            ss= MyConnection.getConnection().prepareStatement(qq);
	            ss.setInt(1, 0); 
	           ss.setString(2,exit_boothid);
	          // ss.setString(3,en);
	            ss.setString(3,time);
	            ss.setString(4,exitcardnbr);
	            // update
	            ss.executeUpdate();
	        } catch (SQLException e) {
	            System.out.println(e.getMessage());
	        }
	        String rfidUp = "update `rfid` set `isParked`	= ' 0 ' where `generateRFID_NO`= ? and `isParked` = ' 1 ' ";

	         PreparedStatement ssUp;

	        try
	        {

	         //  System.out.println("en ex" +en+"    "+ex); 
	         //  System.out.println("rfid info stst and exit booth set:");
	            ssUp= MyConnection.getConnection().prepareStatement(rfidUp);
	           
	            ssUp.setString(1,exitcardnbr);
	            // update
	            ssUp.executeUpdate();
	        } catch (SQLException e) {
	            System.out.println(e.getMessage());
	        }
   	        
    	        
                /*
        	  
        	 String en="0",ex="0";
               ResultSet res1; 
             String ans1="SELECT * FROM `rfid_info` WHERE `rfid_no`='" + exitcardnbr + "' and `rfid_info_status` = ' 1 '  ";
              PreparedStatement ment1; 
            //  System.out.println("hellow world 1");
            try {
                 ment1=MyConnection.getConnection().prepareStatement(ans1); 
                  res1=ment1.executeQuery(); 
                  
              
                  if ( res1.next() ) {
             
                      en= res1.getString("entry_time");
               
                      ex = res1.getString("exit_time");
              
                  }
                  ment1.close();
              } catch (Exception e) {
                  System.err.println("Got an exception! ");
                  System.err.println(e.getMessage());
              } 
              
                 //exit
        	  ResultSet rr;
 	         String qq = "update rfid_info set rfid_info_status	= ?,exit_booth_id=?,entry_time= ?, exit_time= ? where rfid_no = ? and `rfid_info_status` = ' 1 ' ";

 	         PreparedStatement ss;

 	        try
 	        {

 	           System.out.println("en ex" +en+"    "+ex); 
 	         //  System.out.println("rfid info stst and exit booth set:");
 	            ss= MyConnection.getConnection().prepareStatement(qq);
 	            ss.setInt(1, 0); 
 	           ss.setString(2,exit_boothid);
 	           ss.setString(3,en);
 	            ss.setString(4,ex);
 	            ss.setString(5,exitcardnbr);
 	            // update
 	            ss.executeUpdate();
 	        } catch (SQLException e) {
 	            System.out.println(e.getMessage());
 	        }
 	        */
             //find barrier gate open time   
 	        
 	        
 	        //end 
        	  //end
        	  
              System.out.println("Payment Done");
              //JOptionPane.showMessageDialog(null, "Payment Done"); 
             // returnResult=open_barrier+"s"; 
              System.out.println(returnResult);
              
          }
           

           

        
        //add clear panel
       
        System.out.println("Return Result: "+returnResult);
       
    } 
    

}
