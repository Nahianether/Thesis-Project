import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ServerVIPExit {

	public String vipExit(String rfid) {
		// TODO Auto-generated method stub
		String[] splitVIP = rfid.split(",");
		String[] timeSplit;
		String cardno=splitVIP[2];
		String exitBooth=splitVIP[1];
		String querynormal,queryTime,fareCalculate,updatePostpaid,updatePrepaid,updateRfidInfo,queryReturn ;
		Double[] fare;
		String returnResult="0",carType,paymentType,val;
		Double remaintingBalance,usedBalance,perHourFare,totalFare;
		int datediff,timeExit=0;
		
		ResultSet normal,rsqueryTime,rsfareCalculate,rsReturn;
        querynormal = " SELECT *,datediff(now(),`VIPrfid_lastPayment`) FROM `vip_rfid` WHERE `generateRFID_NO`=? and `isParked`=' 1 ' ";
      
        PreparedStatement stmtEmployee,stmtqueryTime,stmtfareCalculate,stmtupdatePostpaid,stmtupdatePrepaid,stmtupdateRfidInfo,stmtReturn;
        try {
            stmtEmployee = MyConnection.getConnection().prepareStatement(querynormal);
            stmtEmployee.setString(1, cardno);
            normal = stmtEmployee.executeQuery();
            int isOk=1;
            if (normal.next()) 
            {           	
            	System.out.println("First try");
            	carType=normal.getString("VIPrfid_vehicle_type");
            	datediff=normal.getInt("datediff(now(),`VIPrfid_lastPayment`)");
            	
            	paymentType=normal.getString("VIPrfid_payment_type");
            	remaintingBalance=normal.getDouble("VIPrfid_remaining_balance");
            	usedBalance=normal.getDouble("VIPrfid_balance_used");
            	System.out.println("pay type : "+paymentType);
            	
            	queryReturn=" SELECT * FROM `vehicle` WHERE `vehicle_type`=? " ;
				stmtReturn = MyConnection.getConnection().prepareStatement(queryReturn);				
				stmtReturn.setString(1, carType);								
				rsReturn=stmtReturn.executeQuery();
				if(rsReturn.next()) {
					System.out.println("2nd Try");
					timeExit=rsReturn.getInt(5);
				}
            	
            	if(paymentType.equalsIgnoreCase("Prepaid") && remaintingBalance==0.0) {
            		
            		isOk=0;
            		//returnResult="Insufficient Balance ; Deposit First"+","+"0s";
            		returnResult="1s";
            		
            	}
            	else if(paymentType.equalsIgnoreCase("postpaid") && datediff>30) {
            		isOk=0;
            		//returnResult="Date Over ; Pay the bill"+","+"0s";
            		returnResult="2s";
            		
            	}
            	queryTime="SELECT timeDiff(now(),`entry_time`) FROM `vip_rfid_info` WHERE `rfid_no`=? and `rfid_info_status`=' 1 ' ";
            	stmtqueryTime=MyConnection.getConnection().prepareStatement(queryTime);
            	stmtqueryTime.setString(1,cardno);
            	rsqueryTime=stmtqueryTime.executeQuery();
            	
            	
            	if(rsqueryTime.next() && isOk==1) {
            		System.out.println("3rd try");
            		val=rsqueryTime.getString(1);
            		
            		timeSplit=val.split(":");
            		fare=new Double[5];
            		
            		fare[0]=Double.parseDouble(timeSplit[0]);
            		
            		fare[1]=Double.parseDouble(timeSplit[1]);
            		fare[1]=fare[1]+1;
            		fare[1]=fare[1]/60;
            		fare[2]=Double.parseDouble(timeSplit[2]);		
            		
            		fareCalculate="SELECT `vehicle_farePerHr` FROM `vehicle` WHERE `vehicle_type`=? ";
            		stmtfareCalculate=MyConnection.getConnection().prepareStatement(fareCalculate);
            		stmtfareCalculate.setString(1, carType);
            		rsfareCalculate=stmtfareCalculate.executeQuery();
            		if(rsfareCalculate.next()) {
            			perHourFare=rsfareCalculate.getDouble("vehicle_farePerHr");
            			
            			fare[0]=fare[0]*perHourFare;
            			fare[1]=fare[1]*perHourFare;
            			totalFare=fare[0]+fare[1];
            			totalFare=Math.ceil(totalFare);
            			int isupdate=1;
            			
            			if(paymentType.equalsIgnoreCase("Prepaid")&& remaintingBalance<totalFare) {
            				//returnResult= "Low Balance ; Deposit First"+","+"0s";
            				returnResult="3s";
            				isupdate=0;
            				
            			}
            			
            			else if(paymentType.equalsIgnoreCase("Postpaid")) {
            				usedBalance=usedBalance+totalFare;
            				updatePostpaid="UPDATE `vip_rfid` SET "
            						+ "`VIPrfid_balance_used`=?,"
            						+ "`isParked`=' 0 '  WHERE `generateRFID_NO`=? ";
            				stmtupdatePostpaid=MyConnection.getConnection().prepareStatement(updatePostpaid);
            				
            				stmtupdatePostpaid.setDouble(1,usedBalance);
            				stmtupdatePostpaid.setString(2, cardno);
            				stmtupdatePostpaid.executeUpdate();
            				//returnResult=usedBalance+","+totalFare+","+timeExit+"s";
            				returnResult=timeExit+"s";
            			}
            			else if(paymentType.equalsIgnoreCase("Prepaid")&& remaintingBalance>=totalFare) {
            				remaintingBalance=remaintingBalance-totalFare;
            				
            				usedBalance=usedBalance+totalFare;
            				updatePrepaid="UPDATE `vip_rfid` SET `VIPrfid_remaining_balance`=?,"
            						+ "`VIPrfid_balance_used`=?,"
            						+ "`isParked`=' 0 '  WHERE `generateRFID_NO`=? ";
            				stmtupdatePrepaid=MyConnection.getConnection().prepareStatement(updatePrepaid);
            			
            				stmtupdatePrepaid.setDouble(1, remaintingBalance);
            				stmtupdatePrepaid.setDouble(2,usedBalance);
            				stmtupdatePrepaid.setString(3, cardno);
            				stmtupdatePrepaid.executeUpdate();
            				//returnResult=remaintingBalance+","+usedBalance+","+totalFare+","+timeExit+"s";   
            				returnResult=timeExit+"s";
            			}
            			if(isupdate==1) {
            				updateRfidInfo="UPDATE `vip_rfid_info` SET `rfid_info_status`=' 0 ',`exit_time`=now() , `exit_booth_id`=? "
            						+ " WHERE `rfid_no`=? "
            						+ "AND `rfid_info_status`=' 1 ' ";
            				stmtupdateRfidInfo=MyConnection.getConnection().prepareStatement(updateRfidInfo);
            				stmtupdateRfidInfo.setString(1, exitBooth);
            				stmtupdateRfidInfo.setString(2, cardno);
            				stmtupdateRfidInfo.executeUpdate();
            				
            			}
            			
            		}
            		
            	}
               
              // met.paymentt();
              
            } else {
            	returnResult="0s";
                //JOptionPane.showMessageDialog(null, "Invalid Card Number Or Not Parked");
            }
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
		
        System.out.println("Return msg: "+returnResult);
		return returnResult;
	}

}
