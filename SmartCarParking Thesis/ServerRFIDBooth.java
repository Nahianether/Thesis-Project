import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class ServerRFIDBooth {
	int clientCount,timeExit;
	int position,commaCount=0;
	String[] splitInfoNormal,splitInfoVIP;
	String nowDate;
	String res="0",type; 
	LocalDateTime now;
	private static final DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	
	
	public String rfidbooth(String rfid) {
		now=LocalDateTime.now();
		nowDate=dtf.format(now);
		//System.out.println("RFID Booth is Connected");
		clientCount++;
		
		commaCount=0;
		//System.out.println("Message:"+rfid);
		res="0";
		
		//try
		int i;
		for(i=0;i<rfid.length();i++) {
			char c=rfid.charAt(i);
			//System.out.println("Character:"+c);
			if(c==',') {
				commaCount++;
			}
		}
		
		if(commaCount==3) {
			splitInfoNormal=rfid.split(",");
		}
		else if(commaCount==2) {
			splitInfoVIP=rfid.split(",");
		}
		//try end
		dbConnectionRFIDEntry();
		//System.out.println("Return Result is: "+res);
		
		return res;
	}
	
	private void dbConnectionRFIDEntry() {
		// TODO Auto-generated method stub
		ResultSet rsPosition,rsFinal,rsValidRFID,rsReturn;
		PreparedStatement stmt,stmtStatus,stmtPosition,stmtFinal,stmtValidRFID,stmtUpdate,stmtReturn;
		String queryPosition,query,queryStatus,queryFinal,queryValidRFID,queryUpdate,queryReturn;
		
		if(commaCount==2) {
			queryValidRFID="SELECT * FROM `vip_rfid` WHERE `generateRFID_NO`=? and isParked=' 0 ' ";
			String value=splitInfoVIP[2];
			//System.out.println(value);
			
			try {
				
				stmtValidRFID = MyConnection.getConnection().prepareStatement(queryValidRFID);
				
				stmtValidRFID.setString(1, value);
				
				rsValidRFID=stmtValidRFID.executeQuery();
				
				if(rsValidRFID.next()) {
					
					type=rsValidRFID.getString(7);
					
					//System.out.println("VIP Card Found...card type:"+type);
					queryPosition=" SELECT max(`vip_rfid_info_id`) FROM `vip_rfid_info`";
					
					query=" INSERT INTO `vip_rfid_info`(`vip_rfid_info_id`, `booth_id`, `carType`, `rfid_no`, `entry_time`, `image`, `rfid_info_status`,`exit_time`,`exit_booth_id` ) VALUES (?,?,?,?,?,?,?,?,?) ";
					stmtPosition = MyConnection.getConnection().prepareStatement(queryPosition);
					rsPosition=stmtPosition.executeQuery();
					if(rsPosition.next()) {
						
						position=rsPosition.getInt(1);
						position+=1;
										
					}
					stmt = MyConnection.getConnection().prepareStatement(query);
					stmt.setInt(1, position);
					stmt.setString(2, splitInfoVIP[1]);
					stmt.setString(3, type);
					stmt.setString(4,splitInfoVIP[2]);
					stmt.setString(5, nowDate);
					stmt.setString(6, null);
					stmt.setInt(7, 0);
					stmt.setString(8, nowDate);
					stmt.setString(9, "0");
					if(stmt.executeUpdate()>0) {
						
						System.out.println("Insert Done");
						
						
					}
					else {
						
						System.out.println("Insert error");
					}
					try {
						
						WebcamTutorial.setOption(1);
						WebcamTutorial.dbConnection();
						
					} catch (IOException e) {
						
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					queryStatus="UPDATE `vip_rfid_info` SET `rfid_info_status`=? WHERE `rfid_info_status`=' 0 ' and  `image`!='+null+' limit 1";
					
					stmtStatus=MyConnection.getConnection().prepareStatement(queryStatus);
					
					stmtStatus.setInt(1, 1);
					
					stmtStatus.executeUpdate();
					
					
					value=splitInfoVIP[2];
					
					queryFinal="SELECT * FROM `vip_rfid_info` WHERE `rfid_no`=? and `image`!='+null+' and `rfid_info_status`=' 1 ' ";
					
					stmtFinal = MyConnection.getConnection().prepareStatement(queryFinal);
					
					stmtFinal.setString(1, value);
					
					rsFinal=stmtFinal.executeQuery();
					//System.out.println("What is");
				
					if(rsFinal.next()) {
						
						queryUpdate ="UPDATE `vip_rfid` SET `isParked`=' 1 ' WHERE `generateRFID_NO`=? ";
						stmtUpdate = MyConnection.getConnection().prepareStatement(queryUpdate);
						stmtUpdate.setString(1, splitInfoVIP[2]);
						stmtUpdate.executeUpdate();
					
						//res="1";
						queryReturn=" SELECT * FROM `vehicle` WHERE `vehicle_type`=? " ;
						stmtReturn = MyConnection.getConnection().prepareStatement(queryReturn);
						
						stmtReturn.setString(1, type);
						
						
						rsReturn=stmtReturn.executeQuery();
						if(rsReturn.next()) {
							timeExit=rsReturn.getInt(5);
							res=timeExit+"s";
						}
					}
					
				}
				else {
					
					res="0s";
					System.out.println("VIP Card Not Found OR already Parked");
				}
			} catch (SQLException e) {
				
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		
		else if(commaCount==3){
			
			queryValidRFID="SELECT * FROM `rfid` WHERE `generateRFID_NO`=? and isParked=' 0 ' ";
			String value=splitInfoNormal[3];
			//value="0F008092382";
			//System.out.println(value);
			try {
				
				stmtValidRFID = MyConnection.getConnection().prepareStatement(queryValidRFID);
				
				stmtValidRFID.setString(1, value);
				
				rsValidRFID=stmtValidRFID.executeQuery();
				
				if(rsValidRFID.next()) {
			
					type=splitInfoNormal[2];
		
					queryPosition=" SELECT max(`rfid_info_id`) FROM `rfid_info`";
					
					query=" INSERT INTO `rfid_info`(`rfid_info_id`, `booth_id`, `button_id`, `rfid_no`, `entry_time`, `image`, `rfid_info_status`, `paymentdone`, `settime`, `exit_time`,`exit_booth_id`) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
					
					try {
						
						
						
						stmtPosition = MyConnection.getConnection().prepareStatement(queryPosition);
						rsPosition=stmtPosition.executeQuery();
						if(rsPosition.next()) {
							
							position=rsPosition.getInt(1);
							position+=1;
											
						}
						
						stmt = MyConnection.getConnection().prepareStatement(query);
						stmt.setInt(1, position);
						stmt.setString(2, splitInfoNormal[1]);
						stmt.setString(3, splitInfoNormal[2]);
						stmt.setString(4,value);
						stmt.setString(5, nowDate);
						stmt.setString(6, null);
						stmt.setInt(7, 0);
						stmt.setInt(8, 0);
						stmt.setInt(9, 0);
						stmt.setString(10, nowDate);
						stmt.setString(11, "0");
						if(stmt.executeUpdate()>0) {
							
							System.out.println("Insert Done");
							
						}
						else {
							
							System.out.println("Insert error");
						}
					
						try {
							
							WebcamTutorial.setOption(2);
							WebcamTutorial.dbConnection();
						} catch (IOException e) {
							
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						System.out.println("Image Inserted");
						queryStatus="UPDATE `rfid_info` SET `rfid_info_status`=? WHERE `rfid_info_status`=' 0 ' and  `image`!='+null+' limit 1";
						
						stmtStatus=MyConnection.getConnection().prepareStatement(queryStatus);
						stmtStatus.setInt(1, 1);
						stmtStatus.executeUpdate();
						
						String rfidValue=value;
						
						queryFinal="SELECT * FROM `rfid_info` WHERE `rfid_no`=? and `image`!='+null+' and `rfid_info_status`=' 1 ' ";
						
						stmtFinal = MyConnection.getConnection().prepareStatement(queryFinal);
						stmtFinal.setString(1, rfidValue);
						rsFinal=stmtFinal.executeQuery();
						if(rsFinal.next()) {
							queryUpdate ="UPDATE `rfid` SET `isParked`=' 1 ' WHERE `generateRFID_NO`=? ";
							stmtUpdate = MyConnection.getConnection().prepareStatement(queryUpdate);
							stmtUpdate.setString(1,value);
							stmtUpdate.executeUpdate();
							
							//res="1";
							queryReturn=" SELECT * FROM `vehicle` WHERE `vehicle_type`=? " ;
							stmtReturn = MyConnection.getConnection().prepareStatement(queryReturn);
							
							stmtReturn.setString(1, type);
							//System.out.println("Type of Vehicle: "+type);
							
							rsReturn=stmtReturn.executeQuery();
							if(rsReturn.next()) {
								//System.out.println("Hello There");
								timeExit=rsReturn.getInt(5);
								res=timeExit+"s";
								
							}
						}
						
					} catch (SQLException e) {
					
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				else {
					
					res="0s";
					System.out.println("Card Not Found or is Parked");
				}
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}
		
	}

}
