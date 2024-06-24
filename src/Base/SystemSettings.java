package Base;
//KOD YAZIM STANDARTLARINI BELİRLE, kullanıcı özelliği ekle

import Control.IDARE;
import Services.CryptingService;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;


public class SystemSettings implements IEntity{
    private static SystemSettings settings;
    static User currentUser;
    int ID;
    String guiSeemingName, PATHPosition = "";
    GUISeeming currentGuiSeeming;
//    static boolean isUser_entry;
    boolean is_logging = false;
    boolean is_autoSaving = false;
    
    protected SystemSettings (String guiSeemingName, String PATH) {
        this.guiSeemingName = guiSeemingName;
        this.currentGuiSeeming = GUISeeming.GUIOrderProducer(guiSeemingName);
        this.PATHPosition = PATH;
        is_autoSaving = false;
        is_logging = false;
//        SystemSettings.isUser_entry = false;
    }
    protected SystemSettings(String guiSeemingName, String PATH, boolean isLogging, boolean isAutoSaving){
        this(guiSeemingName, PATH);
        this.is_logging = isLogging;
        this.is_autoSaving = isAutoSaving;
    }
    public SystemSettings(String guiSeemingName, boolean isLogging, boolean isAutoSaving){
        this(guiSeemingName, null);
        this.is_logging = isAutoSaving;
        this.is_autoSaving = isLogging;
    }

//İŞLEM YÖNTEMLERİ:
    //ÜRETİCİ YÖNTEM:
    public static SystemSettings produceSystemSettings(String guiSeemingName, String PATH, boolean isLogging, boolean isAutoSaving, String code){
        if(CryptingService.getMd5(code).equals(IDARE.getMd5Code()))
            return new SystemSettings(guiSeemingName, PATH, isLogging, isAutoSaving);
        return null;
    }
    public static void appUserSettings(SystemSettings userOptions){
        SystemSettings.settings = userOptions;
    }
    public static Date getCurrentTime(){
        return Calendar.getInstance(TimeZone.getTimeZone("GMT+3:00")).getTime();
    }
    public void setCurrentGuiSeeming(GUISeeming currentSeeming){
        this.currentGuiSeeming = currentSeeming;
        this.guiSeemingName = currentSeeming.getGuiSeemingName();
    }
    public void setGuiSeemingName(String guiSeeming){
        this.guiSeemingName = guiSeeming;
    }
    public void setPATHPosition(String PATHPosition){
        this.PATHPosition = PATHPosition;
    }
    public void setIs_logging(boolean is_logging) {
        this.is_logging = is_logging;
    }
    public void setIs_autoSaving(boolean is_autoSaving) {
        this.is_autoSaving = is_autoSaving;
    }

//ERİŞİM YÖNTEMLERİ:
    //ANA ERİŞİM YÖNTEMİ:
    public static SystemSettings getSettings() {
        if (settings == null){
            settings = new SystemSettings("Standard", null) ;
        }
        return settings;
    }
    public GUISeeming getCurrentGuiSeeming(){
        if (currentGuiSeeming == null){
            currentGuiSeeming = GUISeeming.GUIOrderProducer("Standard");
        }
        return currentGuiSeeming;
    }
    public String getGuiSeemingName(){
        if (settings == null){
            getSettings();
        }
        return guiSeemingName;
    }
    public String getPATHPosition() {
        if (settings == null){
            getSettings();
        }
        return PATHPosition;
    }
/*
    public static boolean isUser_Entry() {
        return SystemSettings.isUser_entry;
    }

    public static void setIs_User_Entry(boolean isUser_entry) {
        SystemSettings.isUser_entry = isUser_entry;
    }
*/
    public boolean getIs_logging() {
        return is_logging;
    }
    public boolean getIs_autoSaving() {
        return is_autoSaving;
    }@Override
    public int getID() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    /*public static User getCurrentUser() {
        if ( settings == null ) {
            getSettings();
        }
        if (isUser_entry == false){
            return null;
        }
        return currentUser;
    }

    public static void setCurrentUser(User currentUser) {
        SystemSettings.currentUser = currentUser;
    }*/
    
    @Override //DEĞİŞTİRİLECEK
    public String toString () {
        StringBuilder backValue = new StringBuilder() ;
        backValue.append("Dil: " + "TÜRKÇE" /*getSettings().getLanguage()*/ + "\nGörsel görünüm: " + getSettings().getGuiSeemingName());
        backValue.append( "\nKayıt tut: " ) ;
        if (getSettings().getIs_logging() == true )
            backValue.append ( "Evet" ) ;
        else
            backValue.append ( "Hayır" ) ;
        backValue.append( "\nOtomatik kaydet: " ) ;
        if (getSettings().getIs_autoSaving()== true )
            backValue.append ( "Evet" ) ;
        else
            backValue.append ( "Hayır" ) ;
        backValue.append( "\nDosya yolu: " + getSettings().getPATHPosition() ) ;
        return backValue.toString() ;
    }
}