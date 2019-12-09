import java.io.*;
import java.awt.EventQueue;
import java.net.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.*;


import javax.swing.JOptionPane;




public class ParkingServer {
	static int port;
	ServerSocket server=null;
	static ServerSocket server1=null;
	static Socket client=null;
	ExecutorService pool=null;
	int clientCount;
	static String rfid;
	static String  mbl;
	static float ans;
	static String exit_boothid;
   // static final DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	//public static DateTimeFormatter dtf=DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	static LocalDateTime now;
	static BufferedReader cin;
    static PrintStream cout;
    static int position,commaCount=0;
    static String[] splitInfoNormal,splitInfoVIP,splitRequest,splitVIPInsert,splitLogin;
    static String nowDate;
    static String res="0"; 
    static String entry_time;
    static String VIPrfid_lastPayment; 
    static String VIPrfid_issue_date;
    static String exit_time;
    static String stay_time;
    static String blanaceused;
    static String remaing;
    static String  blanaceremaining ; 
    static String vipcartyp;
    static int tk;
    static String total_fair; 
    private static final DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    
    //exit part veriable 
   
    public static String time; 
    public static String exittime;
     public static String entrytime;
     public String exitcardnbr;
     

    private static void staytime_calculation() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    //

   // private static final DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
   // private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

	public ParkingServer() {
		// TODO Auto-generated constructor stub
		//this.port=port;
	}

	public static void main(String[] args) {
		now=LocalDateTime.now();
		nowDate=dtf.format(now);
		ParkingServer parkingserver=new ParkingServer();
		
		//ParkingServer parkingserver=new ParkingServer(8800);
		IpPortConfiguration ipConfig=new IpPortConfiguration();
		port=ipConfig.getPortNumber();
		//ParkingServer parkingserverVIPCreate=new ParkingServer(8801);
		//parkingserverVIPCreate.createAccount();
		parkingserver.startAction();
		
		
        
	}
	
	

	private void createAccount() {
		// TODO Auto-generated method stub
		
	}

	private void startAction() {
		// TODO Auto-generated method stub
		try {
			server=new ServerSocket(port);

			while(true) {
				client=server.accept();
				//System.out.println("Server Ip+Port : "+ server.getLocalPort()+" "+server.getInetAddress()+" "+server.getLocalSocketAddress());
				//System.out.println("Client Ip+Port : "+client.getPort()+" "+client.getLocalPort()+" "+client.getInetAddress()+" "+client.getLocalAddress()+" "+client.getLocalSocketAddress());
				
				cin=new BufferedReader(new InputStreamReader(client.getInputStream()));
				
				cout=new PrintStream(client.getOutputStream());
				if(client.isConnected()) {
					
					//System.out.println("Client port:"+client.getPort());
					rfid=cin.readLine();
					
					System.out.println("RFID"+rfid);
					splitRequest=rfid.split("-");
					if(splitRequest[0].equals("Entry")) {
						ServerRFIDBooth serverbooth=new ServerRFIDBooth();
						res=serverbooth.rfidbooth(rfid);						
						cout.println(res);
				        cin.close();
				        client.close();
				        cout.close();
						
					}
					else if(splitRequest[0].equals("New VIP User Entry")) {
						ServerInsertVIP serverVip=new ServerInsertVIP();
						res=serverVip.insertVIPMember(rfid);
						cout.println(res);
						cin.close();
						client.close();
				        cout.close();
						//insertVIPMember();
					}
					else if(splitRequest[0].equals("Login")) {
						//System.out.println("Login Part");
						ServerLogin serverLog=new ServerLogin();
						res=serverLog.loginUser(rfid);
						cout.println(res);
						cin.close();
						client.close();
				        cout.close();
						//loginUser();
					}
					else if(splitRequest[0].equals("Forget Pass")) {
						//System.out.println("Login Part");
						ServerForgetPass serverforget=new ServerForgetPass();
						res=serverforget.getPass(rfid);
						cout.println(res);
						cin.close();
						client.close();
				        cout.close();
						//loginUser();
					}
					/*
					else if(splitRequest[0].equals("Exit")) {
						//System.out.println("Login Part");
						ServerVIPExit serverVipExit=new ServerVIPExit();
						res=serverVipExit.vipExit(rfid);
						cout.println(res);
						cin.close();
						client.close();
				        cout.close();
						//vipExit();
					} */
					else if(splitRequest[0].equals("Card Check")) {
						//System.out.println("Login Part");
						ServerCardCheck sck=new ServerCardCheck();
						res=sck.cardCheck(rfid);
						cout.println(res);
						cin.close();
						client.close();
				        cout.close();
						
					} 
					else if(splitRequest[0].equals("Payment")) {
						ServerPayment serverPayment=new ServerPayment();
						
						res=serverPayment.payment(rfid);
						System.out.println("Respose is: "+res);
						cout.println(res);
						cin.close();
						client.close();
				        cout.close();
						
					}
					/*
					else if(splitRequest[0].equals("exit")) {
						//System.out.println("Login Part");
						ServerNormalExit serverNExit=new ServerNormalExit();
						res=serverNExit.exit(rfid);
						//exit();
					}
					*/
					else if(splitRequest[0].equals("exit")) {
						
						exit();
				
					}
					else if(splitRequest[0].equals("Exit")) {
						
						res=checkCard(rfid);
						System.out.println("User:" + res);
						if(res.equalsIgnoreCase("Vip")) {
							ServerVIPExit serverVipExit=new ServerVIPExit();
							res=serverVipExit.vipExit(rfid);
						}
						else if(res.equalsIgnoreCase("Normal")) {
							ServerNormalExit snExit=new ServerNormalExit();
							res=snExit.exit(rfid);
							
						}
						else {
							res="Not Found";
						}
						System.out.println("Return :"+ res);
						cout.println(res);
						cin.close();
						client.close();
				        cout.close();
				
					}
					else if(splitRequest[0].equals("payment done")) {
						//System.out.println("Login Part");
						ServerPayment serverPayment=new ServerPayment();
						
						res=serverPayment.paymentdone(rfid);
						System.out.println("Respose is: "+res);
						cout.println(res);
						cin.close();
						client.close();
				        cout.close();
						//paymentdone();
					} 
					else if(splitRequest[0].equals("vippostpayment done")) {
						
						vippostpaymentdone();
					} 
					else if(splitRequest[0].equals("vipprepaymentdone")) {
						
						vipprepaymentdone();
					} 
					

					else if(splitRequest[0].equals("vipCard Check")) {
						//System.out.println("Login Part");
						vipcardcheck();
					} 
					
					else if(splitRequest[0].equals("postpaid")) {
						
						postvippayment();
					}
					else if(splitRequest[0].equals("prepaidpayment")) {
						
	                	   prepaidpayment();
					}
					
					
 
					else if(splitRequest[0].equals("vipPayment")) {
						//System.out.println("Login Part");
						vippayment();
					}
					
					else if(splitRequest[0].equals("Create new Admin")) {
						ServerCreateAdmin scAdmin=new ServerCreateAdmin();
						res=scAdmin.createAccount(rfid);
						cout.println(res);
				        cin.close();
				        client.close();
				        cout.close();
					}
					else if(splitRequest[0].equals("Create New Employee")) {
						ServerCreateEmployee scEmployee=new ServerCreateEmployee();
						res=scEmployee.createAccount(rfid);
						cout.println(res);
				        cin.close();
				        client.close();
				        cout.close();
					}

				}

				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public String checkCard(String rfid) {
		String returnMsg=null;
		String[] card = rfid.split(",");
		String sncard=card[2]; 
		System.out.println("RFID: "+sncard);
		ResultSet rsCard,rsCard1;
		PreparedStatement stmtCard,stmtCard1;
		String queryCard,queryCard1;
		queryCard="SELECT * FROM `vip_rfid` WHERE `generateRFID_NO`=? and isParked=' 1 ' ";
		queryCard1="SELECT * FROM `rfid` WHERE `generateRFID_NO`=? and isParked=' 1 ' ";
		try {
			stmtCard = MyConnection.getConnection().prepareStatement(queryCard);
		
			stmtCard1=MyConnection.getConnection().prepareStatement(queryCard1);
			stmtCard.setString(1,sncard);
			stmtCard1.setString(1,sncard);
			rsCard=stmtCard.executeQuery();
			rsCard1=stmtCard1.executeQuery();
			if(rsCard.next()) {
				returnMsg="VIP";
				//card=rsCard.getString(7);
			}
			else if(rsCard1.next()) {
				returnMsg="Normal";
			}
			else {
				returnMsg="Not Found";
			}
			System.out.println("Message :"+returnMsg);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return returnMsg;
	}
	private void vipprepaymentdone()
	 {  
		 System.out.println("hellow world");
		 System.out.println("vipprepaymentdone");
		 String[] card = rfid.split(",");
		 String sncard=card[1]; 
		 String deposite=card[2]; 
		 
		 LocalDateTime now = LocalDateTime.now();
		 VIPrfid_lastPayment = dtf.format(now);  
		 
		 //find issue date time 
		 System.out.println("before query data");
		 System.out.println(sncard);
		 ResultSet pay;
	        String vippayment =" SELECT * FROM `vip_rfid` WHERE `generateRFID_NO`='" + sncard + "' ";
	        PreparedStatement vpay;
	      
	        try {
	        	vpay= MyConnection.getConnection().prepareStatement(vippayment);
	            pay = vpay.executeQuery();
	         
	            if (pay.next()) {
	            	
	            	VIPrfid_issue_date = pay.getString("VIPrfid_issue_date");
	            	
	            }

	        } catch (SQLException e1) {

	            e1.printStackTrace();
	        }   
	        
		 
		 //find remaining balance
		 System.out.println("before query data");
		 System.out.println(sncard);
		 ResultSet pay2;
	        String vippayment2 =" SELECT * FROM `vip_rfid` WHERE `generateRFID_NO`='" + sncard + "' ";
	        PreparedStatement vpay2;
	      
	        try {
	        	vpay2= MyConnection.getConnection().prepareStatement(vippayment2);
	            pay2 = vpay2.executeQuery();
	         
	            if (pay2.next()) {
	            	
	            	remaing = pay2.getString("VIPrfid_remaining_balance");
	            	//convert string to int
	            	float remaing2=Float.parseFloat(remaing); 
	            	float deposite2=Float.parseFloat(deposite);
	               ans=remaing2+deposite2; 
	            	
	            	System.out.println("remaining"+ans);
	            	
	            }

	        } catch (SQLException e1) {

	            e1.printStackTrace();
	        }   
	        
	     //update balance and last payment date 
	         ResultSet r;
	         String q = "update vip_rfid set VIPrfid_balance_used= ?, VIPrfid_lastPayment= ?, VIPrfid_issue_date =?, VIPrfid_remaining_balance=? where generateRFID_NO = ?";
	         PreparedStatement s;
	        try
	        {
	            s= MyConnection.getConnection().prepareStatement(q);
	            s.setDouble(1, 0.0);
	            s.setString(2, VIPrfid_lastPayment);
	            s.setString(3, VIPrfid_issue_date);
	            s.setFloat(4, ans);
	            s.setString(5, sncard);
	            s.executeUpdate();
	        } catch (SQLException e) {
	            System.out.println(e.getMessage());
	        }  
	        
	        String returnResult="0";
	        returnResult= VIPrfid_lastPayment+","+ans;
           cout.println(returnResult);
           
           try {
           	
   			cin.close();
   			client.close();
   	        cout.close();
   		} catch (IOException e) {
   			// TODO Auto-generated catch block
   			e.printStackTrace();
   		}
	 }
	
	private void prepaidpayment()
	{  
		System.out.println("prepaidsystme");
	
		String[] card = rfid.split(","); 
		System.out.println(card[1]);
		String cardnbr=card[1]; 
		System.out.println(cardnbr);
		//String amount=card[2]; 
		//System.out.println(amount);
		
	      
		ResultSet pay;
        String vippayment =" SELECT * FROM `vip_rfid` WHERE `generateRFID_NO`='" + cardnbr + "' ";
         String returnResult="0";
        PreparedStatement vpay;
        try {
        	vpay= MyConnection.getConnection().prepareStatement(vippayment);
            pay = vpay.executeQuery();
            if (pay.next()) {
            
                remaing= pay.getString("VIPrfid_remaining_balance");
                blanaceused = pay.getString("VIPrfid_balance_used");
               // mbl = pay.getString("VIPrfid_mobile_no");
                vipcartyp = pay.getString("VIPrfid_vehicle_type");
                returnResult= remaing+','+  blanaceused+','+  vipcartyp;
	          //  cout.println(returnResult);
            //   remaing=remaing+card[2];
                System.out.println(returnResult);
	            cout.println(returnResult);
	          

            }

        } catch (SQLException e1) {

            e1.printStackTrace();
        }   
        try {
        	
			cin.close();
			client.close();
	        cout.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
        
        
	
	private void vippostpaymentdone()
	 {  
		 System.out.println("vippostpaymentdone");
		 String[] card = rfid.split(",");
		 String sncard=card[1]; 
		 
		 LocalDateTime now = LocalDateTime.now();
		 VIPrfid_lastPayment = dtf.format(now); 
		 
		 //find issue date time 
		 System.out.println("before query data");
		 System.out.println(sncard);
		 ResultSet pay;
	        String vippayment =" SELECT * FROM `vip_rfid` WHERE `generateRFID_NO`='" + sncard + "' ";
	        PreparedStatement vpay;
	      
	        try {
	        	vpay= MyConnection.getConnection().prepareStatement(vippayment);
	            pay = vpay.executeQuery();
	         
	            if (pay.next()) {
	            	
	            	VIPrfid_issue_date = pay.getString("VIPrfid_issue_date");
	            	
	            }

	        } catch (SQLException e1) {

	            e1.printStackTrace();
	        }   
	        
	     //update balance and last payment date 
	         ResultSet r;
	         String q = "update vip_rfid set VIPrfid_balance_used= ?, VIPrfid_lastPayment= ?, VIPrfid_issue_date =?  where generateRFID_NO = ?";
	         PreparedStatement s;
	        try
	        {
	            s= MyConnection.getConnection().prepareStatement(q);
	            s.setDouble(1, 0.0);
	            s.setString(2, VIPrfid_lastPayment);
	            s.setString(3, VIPrfid_issue_date);
	            s.setString(4, sncard);
	            s.executeUpdate();
	        } catch (SQLException e) {
	            System.out.println(e.getMessage());
	        }  
	        
	        String returnResult="0";
	        returnResult= VIPrfid_lastPayment;
           cout.println(returnResult);
           
           try {
           	
   			cin.close();
   			client.close();
   	        cout.close();
   		} catch (IOException e) {
   			// TODO Auto-generated catch block
   			e.printStackTrace();
   		}
	 }
	    
	
	private void postvippayment() 
	{  
		
	
		String[] card = rfid.split(","); 
		System.out.println(card[1]);
		String cardnbr=card[1]; 
		System.out.println(cardnbr);
		
	      
		ResultSet pay;
        String vippayment =" SELECT * FROM `vip_rfid` WHERE `generateRFID_NO`='" + cardnbr + "' ";
         String returnResult="0";
        PreparedStatement vpay;
        try {
        	vpay= MyConnection.getConnection().prepareStatement(vippayment);
            pay = vpay.executeQuery();
            if (pay.next()) {
            
                blanaceused = pay.getString("VIPrfid_balance_used");
                mbl = pay.getString("VIPrfid_mobile_no");
                vipcartyp = pay.getString("VIPrfid_vehicle_type");
                returnResult= blanaceused+','+mbl+','+  vipcartyp;
	            cout.println(returnResult);
	          

            }

        } catch (SQLException e1) {

            e1.printStackTrace();
        }   
        try {
        	
			cin.close();
			client.close();
	        cout.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        
       
		
	}
	private void vippayment() 
	{  
		
		System.out.println("Hellow vip card check");
		String[] card = rfid.split(","); 
		System.out.println(card[1]);
		String cardnbr=card[1]; 
		System.out.println(cardnbr);
		//payment show exit time
		// LocalDateTime now = LocalDateTime.now();
	      //  exit_time = dtf.format(now);
	     //  System.out.println("exit time"+exit_time);
	       
		
		
		ResultSet pay;
         System.out.println("here vippayment function");
       // String query ="SELECT * FROM `vip_rfid` WHERE `generateRFID_NO`='" + sncard + "' ";
        String vippayment =" SELECT * FROM `vip_rfid` WHERE `generateRFID_NO`='" + cardnbr + "' ";
	      
         String returnResult="0";
        System.out.println("hellow 1");
        PreparedStatement vpay;
        try {
        	vpay= MyConnection.getConnection().prepareStatement(vippayment);
            pay = vpay.executeQuery();
            System.out.println("hellow 2");
            if (pay.next()) {
            	System.out.println("vip rfid card");
            	//rfid insert entry time
            	
                blanaceused = pay.getString("VIPrfid_balance_used");
                System.out.println("VIPrfid_balance_used"+ blanaceused);
                blanaceremaining = pay.getString("VIPrfid_remaining_balance");
                System.out.println("VIPrfid_balance_used"+  blanaceremaining); 
                
                vipcartyp = pay.getString("VIPrfid_vehicle_type");
                System.out.println("VIPrfid_vehicle_type"+   vipcartyp ); 
                 
                
    	       // System.out.println("exit time"+exit_time);

            //    staytime_calculation(sncard); 
               // returnResult= entry_time;
               // cout.println(returnResult); 
                returnResult= blanaceused+','+blanaceremaining+','+  vipcartyp;
 	           
	            //System.out.println( total_fair);
	          //  System.out.println(stay_time);
	            System.out.println( returnResult);
	            cout.println(returnResult);
	          

            }

        } catch (SQLException e1) {

            e1.printStackTrace();
        }   
        try {
        	
			cin.close();
			client.close();
	        cout.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        
       
		
	}
	private void vipcardcheck() 
	{ 
		System.out.println("Hellow vip card check");
		String[] card = rfid.split(",");
		String cardnbr=card[1]; 
		System.out.println(cardnbr); 
		
		String returnResult="0";
		
		 ResultSet normal2;
     
		 String querynormal2= " SELECT * FROM `vip_rfid` WHERE `generateRFID_NO`='" + cardnbr + "' ";
	      
      
        PreparedStatement stmtEmployee;
        try {
            stmtEmployee = MyConnection.getConnection().prepareStatement(querynormal2);
            normal2 = stmtEmployee.executeQuery();
            if (normal2.next()) 
            {
            	
            	String VIPrfid_payment_type= normal2.getString("VIPrfid_payment_type");
            	returnResult= cardnbr+","+VIPrfid_payment_type;
            	System.out.println("insert query value");

            } else {
            	System.out.println("outside query value");
          
            }
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        try { 
        	System.out.println("vipcardcheck return result value"+returnResult);
        	cout.println(returnResult);
			cin.close();
			client.close();
	        cout.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	private void exit() 
	{ 
		String returnResult="0";
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
                System.out.println("paymentdone:"+paymentdone);
              
                if(paymentdone==1)
                {   
                	System.out.println("HELLO");
                    query(exitcardnbr);
                }
                else if(paymentdone==0)
                {  
                   
                returnResult="0s"; 
               System.out.println(returnResult);
               
                }
              

            }
            else {
            	System.out.println("what the");
            	returnResult="-2s";
            }

        } catch (SQLException e1) {
        	
        } 
        try {
        	cout.println(returnResult);
			cin.close();
			client.close();
	        cout.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       
        

		
	}
	public static void query(String exitcardnbr)
    {     
		String returnResult="0s"; 
		String carType = null;
		//String en;
		System.out.println("exit card number function: ");
         LocalDateTime now = LocalDateTime.now();
        time = dtf.format(now); 
        System.out.println("exit booth exit time:"+time);  
        System.out.println(exitcardnbr);
      ResultSet res; 
       String ans="SELECT * FROM `rfid_info` WHERE `rfid_no`='" + exitcardnbr + "' and `rfid_info_status` = ' 1 ' ";
        PreparedStatement ment;
      try {
           ment=MyConnection.getConnection().prepareStatement(ans); 
            res=ment.executeQuery(); 
            System.out.println("HIIIII");
          
            if ( res.next() ) {
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
    
   
   //time difference find 
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");;

        Date d1 = null;
        Date d2 = null;

        try {
            d1 = format.parse(exittime);
            d2 = format.parse(time);

            long diff = d2.getTime() - d1.getTime();
            long diffSeconds = diff / 1000 % 60;
            long diffMinutes = diff / (60 * 1000) % 60;
            long diffHours = diff / (60 * 60 * 1000) % 24;
            long diffDays = diff / (24 * 60 * 60 * 1000);
             
            String stay_time = diffDays + " d," + diffHours + " hr," + diffMinutes + " m," + diffSeconds + " s";
            System.out.println(stay_time);
            int d, hr, m;
            d = (int) (diffDays * 1440);
            hr = (int) (diffHours * 60);
            m = (int) diffMinutes;
            int total = d + hr + m;
            //here set the time query  
            int set=1; 
            System.out.println("Car Type: "+carType);
           
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
           
            
            
            System.out.println("total minute "+total);
            System.out.println("Set time "+set);
            
          if( total > set )
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
        System.out.println(returnResult);
         cout.println(returnResult);
         //add close part 
         try {
	        	
				cin.close();
				client.close();
		        cout.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

             
              
          } 
          else if(total <= set) 
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
   	                

   	            }

   	        } catch (SQLException e1) {

   	            e1.printStackTrace();
   	        } 
   	        
    	        
                
        	  
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
             //find barrier gate open time   
 	        
 	        
 	        //end 
        	  //end
        	  
              System.out.println("Payment Done");
              //JOptionPane.showMessageDialog(null, "Payment Done"); 
              returnResult=open_barrier+"s"; 
              System.out.println(returnResult);
               cout.println(returnResult);
               //add close part 
               try {
   	        	
   				cin.close();
   				client.close();
   		        cout.close();
   			} catch (IOException e) {
   				// TODO Auto-generated catch block
   				e.printStackTrace();
   			}
          }
           

           

        } catch (Exception e) {
            e.printStackTrace();
        }
        //add clear panel
        try {
        	
			cin.close();
			client.close();
	        cout.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


       
    } 
    

	 
	 

	

	

}
