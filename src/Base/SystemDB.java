package Base;

import static Base.DBHelper.showErrorMessage;
import java.awt.Font;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class SystemDB {
    private static SystemDB database;
    public ArrayList<Category> categories;
    public ArrayList<Note> notes;
    public ArrayList<GUISeeming> themes;
    public ArrayList<NoteTag> tags;
    private User user;

    private SystemDB() {
        /*getDBCategories();
        getDBThemes();
        getDBUser();
        getDBNotes();*/
    }
    //Etiketlerin not yazısının içerisinden alınması; not yazısındaki etiketlerin Note nesnesinin malumat kısmında görünmemesi gerekiyor
//SORGULAMA YÖNTEMLERİ:
    
    
    
//DEĞİŞTİRME YÖNTEMLERİ:
    //Bir kolonun güncellenmesi gerekirken tüm kolonların güncellenmesi yanlış olur, doğru mu?
    

//ERİŞİM YÖNTEMLERİ:
    public static SystemDB getDatabase(){
        if(database == null){
            database = new SystemDB();
        }
        return database;
    }
    
    public static void setDatabase(SystemDB database) {
        SystemDB.database = database;
    }

    
    
    
    
//DİĞER YÖNTEMLER:
    /*public Font fetchFont(int id){
        String ask = new String("SELECT fontName, isItalic, isBold, textSize FROM FONTS WHERE ID=" + id);
        ResultSet fontResult;
        try{
            getSTQuery().clearBatch();
            fontResult = getSTQuery().executeQuery(ask);
            return new Font(fontResult.getString("fontName"), extractFontStyle(fontResult.getBoolean("isBold"), 
                    fontResult.getBoolean("isItalic")), fontResult.getInt("fontSize"));
        }
        catch(SQLException DBException){
            showErrorMessage(DBException);
        }
        return null;
    }
    
    public ArrayList<NoteTag> extractTags(){
        //.;.
        return tags;
    }
    
    public int extractFontStyle(boolean isBold, boolean isItalic){
        if(isBold == true && isItalic == true){
            return 3;
        }
        else if(isBold == true)
            return 1;
        else if(isItalic == true)
            return 2;
        else
            return 0;
    }
    
    public SystemSettings solveSettings(String text){//Çağrılan dosyadan okumalar yaparak ayarları çıkarma
        return SystemSettings.getSettings();
    }*/
}

/*
    //İNCELENECEK:
    /*public ResultSet getDBItem(String query){
        ResultSet resultCurrentItem = null;
        try{
            resultCurrentItem = getSTQuery().executeQuery(query);
        }
        catch(SQLException DBException){
            showErrorMessage(DBException);
        }
        return resultCurrentItem;
    }*/
    
    /*public ResultSet getDBItemByIDandTable(int ID, String tableName){
        ResultSet resultCurrentItem = null;
        String ask = new String("select * from " + tableName + " where id=\"" + ID + "\"");
        try{
            resultCurrentItem = getSTQuery().executeQuery(ask);
        }
        catch(SQLException DBException){
            showErrorMessage(DBException);
        }
        return resultCurrentItem;
    }*/
