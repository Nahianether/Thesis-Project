import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class ServerInsertVIP {
	String[] splitVIPInsert;
	LocalDateTime now;
	String nowDate;
	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

	public String insertVIPMember(String rfid) {
		now=LocalDateTime.now();
		// TODO Auto-generated method stub

		// TODO Auto-generated method stub
		
		splitVIPInsert=rfid.split(",");
		
		String returnResult="0";
		ResultSet rsIndex,rsPrimary;
		PreparedStatement stmtIndex,stmtInsert,stmtPrimary;
		String queryInsert,queryIndex,queryPrimary;
		int position=0;
		now=LocalDateTime.now();
		int possible=1;
		nowDate=dtf.format(now);
		Random rand = new Random();
        int rfidVIP = rand.nextInt(1000) + 1;
        boolean looping=false;
        String number = Integer.toString(rfidVIP);
		do {
		queryPrimary="SELECT * FROM `vip_rfid` where `generateRFID_NO`=? ";
		try {
			stmtPrimary=MyConnection.getConnection().prepareStatement(queryPrimary);
			//stmtPrimary.setString(1,splitVIPInsert[1]);
			stmtPrimary.setString(1,number);
			rsPrimary=stmtPrimary.executeQuery();
			if(rsPrimary.next()) {
				/*
				possible=0;
				System.out.println("Already exists");
				returnResult="Already exists";*/
				
								
			}
			else {
				break;
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		}while(!looping);
		if(possible==1) {
			queryIndex="SELECT max(`VIPrfid_No`) FROM `vip_rfid` ";
			
			
			try {
				stmtIndex = MyConnection.getConnection().prepareStatement(queryIndex);
				rsIndex=stmtIndex.executeQuery();
				if(rsIndex.next()) {
					position=rsIndex.getInt(1);
					position+=1;
									
				}
				queryInsert="INSERT INTO `vip_rfid`(`VIPrfid_No`, `VIPrfid_issue_date`, `VIPrfid_remaining_balance`,"
						+ " `VIPrfid_balance_used`, `VIPrfid_mobile_no`, `VIPrfid_lastPayment`, `VIPrfid_vehicle_type`,"
						+ " `VIPrfid_payment_type`,  `generateRFID_NO`,`isParked`) VALUES (?,?,?,?,?,?,?,?,?,?)";
				
				stmtInsert = MyConnection.getConnection().prepareStatement(queryInsert);
				stmtInsert.setInt(1, position);
				stmtInsert.setString(2, nowDate);
				double vipBalance=Double.parseDouble(splitVIPInsert[2]);
				stmtInsert.setDouble(3, vipBalance);
				stmtInsert.setDouble(4,0);
				stmtInsert.setString(5, splitVIPInsert[4]);
				stmtInsert.setString(6, nowDate);
				stmtInsert.setString(7, splitVIPInsert[1]);
				stmtInsert.setString(8, splitVIPInsert[3]);
				stmtInsert.setString(9, number);
				stmtInsert.setInt(10, 0);
				//stmtInsert.setString(9, splitVIPInsert[1]);
				
				if(stmtInsert.executeUpdate()>0) {
					//System.out.println("Insert Done");
					returnResult="Insert Done,"+number;
				}
				else {
					//System.out.println("Insert error");
					returnResult="Insert Error,"+0;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	return returnResult;
}

}
