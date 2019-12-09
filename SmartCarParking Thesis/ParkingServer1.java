import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ExecutorService;

public class ParkingServer1 {
    static int port;
    ServerSocket server=null;
    static ServerSocket server1=null;
    static Socket client=null;
    ExecutorService pool=null;
    int clientCount;
    static String name,s;
    static DateTimeFormatter dtf=DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    static LocalDateTime now;
    static BufferedReader cin;
    static PrintStream cout;
    static int position,commaCount=0;
    static String[]splitRequest,splitAdminInsert,splitEmployeeInsert;
    static String nowDate;
    static String res="0";
    InputStream is;
    InputStream isr;
    public BufferedReader br;
    public ParkingServer1() {
        // TODO Auto-generated constructor stub
        //this.port=port;
    }

    public static void main(String[] args) {
        now=LocalDateTime.now();
        nowDate=dtf.format(now);
        ParkingServer1 parkingserver=new ParkingServer1();

        //ParkingServer parkingserver=new ParkingServer(8800);
        IpPortConfiguration ipConfig=new IpPortConfiguration();
        port=ipConfig.getPortNumber();
        //ParkingServer parkingserverVIPCreate=new ParkingServer(8801);
        //parkingserverVIPCreate.createAccount();
        parkingserver.startAction();



    }

    private void startAction() {
        // TODO Auto-generated method stub
        try {
            server=new ServerSocket(port);

            while(true) {
                client=server.accept();

                cin=new BufferedReader(new InputStreamReader(client.getInputStream()));

                cout=new PrintStream(client.getOutputStream());
                if(client.isConnected()) {

                    //System.out.println("Client port:"+client.getPort());
                    name=cin.readLine();
                    splitRequest=name.split("-");
                    if(splitRequest[0].equals("Create New Admin")) {
                        insertAdmin();
                    }
                    else if(splitRequest[0].equals("Create New Employee")) {
                        insertEmployee();
                    }

                }

            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }


    private void insertAdmin() {
        // TODO Auto-generated method stub

        splitAdminInsert=name.split(",");

        String returnResult="0";
        ResultSet rsIndex,rsPrimary;
        PreparedStatement stmtIndex,stmtInsert,stmtPrimary;
        String queryInsert,queryIndex,queryPrimary;
        int position=0;
        int possible=1;
        boolean looping=false;

        do {
            queryPrimary="SELECT * FROM `admin` WHERE `admin_name`=?";
            try {
                stmtPrimary=MyConnection.getConnection().prepareStatement(queryPrimary);
                rsPrimary=stmtPrimary.executeQuery();
                if(rsPrimary.next()) {

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
            queryIndex="SELECT max(`admin_id`) FROM `admin` ";
            try {
                stmtIndex = MyConnection.getConnection().prepareStatement(queryIndex);
                rsIndex=stmtIndex.executeQuery();
                if(rsIndex.next()) {
                    position=rsIndex.getInt(1);
                    position+=1;

                }
                queryInsert="INSERT INTO `admin`(`admin_id`, `admin_name`, `admin_mail`, `admin_phoneNo`, `admin_type`, `admin_pass`, `Image`) VALUES (?,?,?,?,?,?,?)";
                stmtInsert = MyConnection.getConnection().prepareStatement(queryInsert);
                try  {
                    is = new FileInputStream(new File(s));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                stmtInsert.setInt(1, position);
                stmtInsert.setString(2,splitAdminInsert[1]);
                stmtInsert.setString(3, splitAdminInsert[2]);
                stmtInsert.setString(4,splitAdminInsert[3]);
                stmtInsert.setString(5, AdminRegistration.admintype);
                stmtInsert.setString(6, "123456789");
                stmtInsert.setBlob(7,is);
                //  stmtInsert.setString(7, browse.getUIClassID());

                if(stmtInsert.executeUpdate()>0) {
                    System.out.println("Insert Done");
                }
                else {
                    System.out.println("Insert error");
                }
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
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

    private void insertEmployee() {
        // TODO Auto-generated method stub

        splitEmployeeInsert=name.split(",");

        String returnResult="0";
        ResultSet rsIndex,rsPrimary;
        PreparedStatement stmtIndex,stmtInsert,stmtPrimary;
        String queryInsert,queryIndex,queryPrimary;
        int position=0;
        int possible=1;
        boolean looping=false;

        do {
            queryPrimary="SELECT * FROM `employ` WHERE `employ_name`=?";
            try {
                stmtPrimary=MyConnection.getConnection().prepareStatement(queryPrimary);
                rsPrimary=stmtPrimary.executeQuery();
                if(rsPrimary.next()) {

                }
                else {
                    break;
                }
            } catch (SQLException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }while(!looping);
        if (possible == 1) {
            queryIndex = "SELECT max(`employ_id`) FROM `employ` ";
            try {
                stmtIndex = MyConnection.getConnection().prepareStatement(queryIndex);
                rsIndex = stmtIndex.executeQuery();
                if (rsIndex.next()) {
                    position = rsIndex.getInt(1);
                    position += 1;

                }
                queryInsert = "INSERT INTO `employ`(`employ_id`, `employ_name`, `employ_mail`, `employ_phoneNo`, `employ_pass`, `admin_id`, `Image`) VALUES (?,?,?,?,?,?,?)";
                stmtInsert = MyConnection.getConnection().prepareStatement(queryInsert);
                try {
                    isr = new FileInputStream(new File(s));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                stmtInsert.setInt(1, position);
                stmtInsert.setString(2, splitEmployeeInsert[1]);
                stmtInsert.setString(3, splitEmployeeInsert[2]);
                stmtInsert.setString(4, splitEmployeeInsert[3]);
                stmtInsert.setString(5, "123456");
                stmtInsert.setString(6, "1");
                stmtInsert.setBinaryStream(7,isr);
                //  stmtInsert.setString(7, browse.getUIClassID());

                if (stmtInsert.executeUpdate() > 0) {
                    System.out.println("Insert Done");
                } else {
                    System.out.println("Insert error");
                }
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
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



}
