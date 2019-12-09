import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ServerForgetPass {
	String[] splitData;
	String ques,inputQues;
	String queryReadEmployee,queryReadAdmin,queryUpdate;
	PreparedStatement stmtReadEmployee,stmtReadAdmin,stmtUpdate;
	ResultSet rsReadEmployee,rsReadAdmin,rsUpdate;

	public String getPass(String rfid) {
		// TODO Auto-generated method stub
		splitData=rfid.split(",");
		String returnResult="0";
		queryReadEmployee= "SELECT * FROM `employ` WHERE `employ_mail`='"+splitData[1]+"' ";
		queryReadAdmin= "SELECT * FROM `admin` WHERE `admin_mail`='"+splitData[1]+"' ";
		try {
			stmtReadEmployee= MyConnection.getConnection().prepareStatement(queryReadEmployee);
			stmtReadAdmin=MyConnection.getConnection().prepareStatement(queryReadAdmin);
			rsReadEmployee=stmtReadEmployee.executeQuery();
			rsReadAdmin=stmtReadAdmin.executeQuery();
			if(rsReadEmployee.next()) {
				ques=rsReadEmployee.getString("ques");
				ques=ques.toLowerCase();
				inputQues=splitData[2].toLowerCase();
				if(ques.contains(inputQues)) {
					
					queryUpdate=" UPDATE `employ` SET `employ_pass`='"+123456+"' "
							+ "WHERE `employ_mail`='"+splitData[1]+"' ";
					stmtUpdate=MyConnection.getConnection().prepareStatement(queryUpdate);
					stmtUpdate.executeUpdate();
					returnResult="123456";
				}
				else {
					returnResult="Not Matched";
				}
			}
			else if(rsReadAdmin.next()) {
				ques=rsReadAdmin.getString("ques");
				
				ques=ques.toLowerCase();
				inputQues=splitData[2].toLowerCase();
				if(ques.contains(inputQues)) {
					//returnResult="Ques Matched";
					queryUpdate=" UPDATE `admin` SET `admin_pass`='"+123456789+"' "
							+ "WHERE `admin_mail`='"+splitData[1]+"' ";
					stmtUpdate=MyConnection.getConnection().prepareStatement(queryUpdate);
					stmtUpdate.executeUpdate();
					returnResult="123456789";
				}
				else {
					returnResult="Not Matched";
				}
			}
			else {
				returnResult="Not Found";
			}
			System.out.println("Ques: "+ques);
			System.out.println("Request Ques : "+ splitData[2]);
		}
		catch(Exception e1) {
			e1.printStackTrace();
		}
		
		return returnResult;
	}

}
