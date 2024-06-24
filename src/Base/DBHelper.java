package Base;

import Control.IDARE;
import Services.CryptingService;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DBHelper{
    private static String dbUrl = "jdbc:mysql://localhost:3306/hatirnot";
    private static String userName = "root";
    private static String secret = "LINQSE.1177";
    private static boolean isChanged = true;
    private static Connection connext;
    private static Statement STquery;
    private static int lastErrorCode = -1;
    
    public static Connection getConnection(){
        if(isChanged)
            connext = null;
        if(connext == null){
            try{
                connext = DriverManager.getConnection(dbUrl, userName, secret);
                System.out.println("Bağlantı kuruldu");
                isChanged = false;
                return connext;
            }
            catch(SQLException DBException){
                showErrorMessage(DBException);
                return null;
            }
        }
        return connext;
    }
//    public static void yazdir(){
//        System.out.println("DBURL :\n");
//        System.err.println("--------------");
//        System.out.println(":" + dbUrl);
//    }
    public static Connection getFirstConnection(){
        Connection connextFirst = null;
        String dbMUrl = "jdbc:mysql://localhost:3306";
        try{
            connextFirst = DriverManager.getConnection(dbMUrl, userName, secret);
        }
        catch(SQLException DBException){
            showErrorMessage(DBException);
        }
        return connextFirst;
    }
    public static ArrayList<String> getCatalogNames(Connection connection){
        String order = "SHOW DATABASES;";
        Statement sent = null;
        ResultSet rs = null;
        String[] value;
        ArrayList<String> schemaNames = new ArrayList<String>();
        try{
            sent = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            rs = sent.executeQuery(order);
            while(rs.next()){
                schemaNames.add(rs.getString("Database"));
            }
        }
        catch (SQLException DBException){
            DBHelper.showErrorMessage(DBException);
        }
        if(sent == null)
            return null;
        if(rs == null)
            return null;
        return schemaNames;
    }
    public static boolean FinishConnection(){//VERİTABANI İŞLEMLERİ SINGLETON KURALINA UYGUN OLARAK YAPILMALI
        if(connext !=  null){
            try{
                connext.close();
                return true;
            }
            catch(SQLException DBException){
                showErrorMessage(DBException);
                return false;
            }
        }
            System.err.println("Bağlantı kurulmamış!");
            return true;
    }
    public static void showErrorMessage(SQLException DBException){//BURASI GELİŞTİRİLEBİLİR, HATALAR DAHA İYİ YÖNETİLEBİLİR Bİ İZNİLLÂH
        System.out.println("Hatâ kodu : " + DBException.getErrorCode() + "\n" + DBException.getMessage());
    }
    public static Statement getSTQuery(){
        if(STquery == null){
            try{
                STquery = getConnection().createStatement();
            }
            catch(SQLException DBException){
                showErrorMessage(DBException);
            }
        }
        return STquery;
    }
    public static void setEntryInfo(String userName, String password, String code){
        if(!CryptingService.getMd5(code).equals(IDARE.getMd5Code()))
            return;
        DBHelper.userName = userName;
        DBHelper.secret = password;
    }
    public static void setDBUrlNumber(int number, MySqlDB DB){
        if(DB == null)
            return;
//        if(dbUrl.endsWith("t"))
//            dbUrl += "2";
        if(number == 1)
            return;
        else{
            int point = dbUrl.lastIndexOf("t");
            dbUrl = dbUrl.substring(0, point + 1);
            dbUrl += String.valueOf(number);
        }
        isChanged = true;
    }
}