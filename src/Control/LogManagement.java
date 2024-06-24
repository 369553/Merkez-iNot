package Control;

import Base.DataBase;
import Base.SystemSettings;
import Services.DateTimeService;
import Services.ReadSaveSystem;
import java.util.Date;

public class LogManagement{
    private static LogManagement logManagement;
    private static StringBuilder strbuiLogs = null;
    private String logFile = null;
    /*
    Not oluşturma, not düzenleme, not silme, etiket ekleme gibi verileri kaydeder bi iznillâh.
    */
    private LogManagement() {
    }

//İŞLEM YÖNTEMLERİ:
    protected static void writeEror(String text){
        addRow("<Error>" + text + "</Error>");
    }
    protected static void writeDBError(String DBType, String text){
        addRow("<DBError, DBType:" + DBType + ">" + text + "</DBError>");
    }
    protected boolean logToTextFile(String path){
        String content = getStrbuiLogs().toString();
        StringBuilder fileName = new StringBuilder("HâtırNot sistem hareket kayıtları dosyası");
        System.out.println("konum : " + SystemSettings.getSettings().getPATHPosition());
        String[] fileList = ReadSaveSystem.usesaveReadSystem().getFileList(path);
        int index = 0;
        boolean isUseName = checkFileName(fileName.toString(), fileList);
        while(isUseName == false){
            fileName.append(" - " + index);
            isUseName = checkFileName(fileName.toString(), fileList);
            index++;
        }
        ReadSaveSystem.usesaveReadSystem().saveFile(content, path, fileName + ".txt");
        logFile = fileName.toString();
        return true;
    }
    protected boolean getLogsBetweenChoosedTimes(Date startTime, Date finishTime){
        //.;.
        return true;
    }
    public boolean takeLogFromText(String path){
        //Dosyayı kontrol et
        //Eğer dosya kayıt dosyası deüğilse hatâ döndür ve dosyayı içe aktarma
        return true;
    }
    //ANA YÖNTEMLER:
    public static void added(String dataType, String name){
        String strText = name + " isimli " + dataType + " eklendi.";
        addRow(strText);
    }
    public static void deleted(String dataType, String name){
        String strText = name + " isimli " + dataType + " silindi.";
        addRow(strText);
    }
    public static void updated(String dataType, String name){
        String strText = name + " isimli " + dataType + " tâzelendi.";
        addRow(strText);
    }
    public static void updated(String dataType, String name, String explanation){
        String strText = name + " isimli " + dataType + "tâzelendi : " + explanation;
        addRow(strText);
    }

//ERİŞİM YÖNTEMLERİ:
    //ANA ERİŞİM YÖNTEMİ:
    private static LogManagement getLogManagement(){
        if(logManagement == null){
            logManagement = new LogManagement();
        }
        return logManagement;
    }
    private static StringBuilder getStrbuiLogs(){
        if(strbuiLogs == null){
            strbuiLogs = new StringBuilder();
        }
        return strbuiLogs;
    }
    private static void setStrbuiLogs(StringBuilder strbuiLogs){
        LogManagement.strbuiLogs = strbuiLogs;
    }
    //ARKAPLAN İŞLEM YÖNTEMLERİ:
    private static void addRow(String strText){
        //En başa şu anın saâtini ekle
        getStrbuiLogs().append(DateTimeService.getDtService().showDateTimeAsString(SystemSettings.getCurrentTime()) + " " + strText +
                " İşlemi yapan kullanıcı: " + DataBase.getDatabase()
                .getUserName() + "\n");
        updateFile();
    }
    private boolean checkFileName(String fileName, String[] fileList){
        if(fileList == null)
            return true;
        if(fileList.length == 0)
            return true;
        for(String str : fileList){
            if(str.equals(fileName)){
                return false;
            }
        }
        return true;
    }
    private static boolean updateFile(){
        boolean isSuccessful = false;
        if(getLogManagement().logFile == null)
            isSuccessful = getLogManagement().logToTextFile(SystemSettings.getSettings().getPATHPosition());
        else
            isSuccessful = ReadSaveSystem.usesaveReadSystem().saveFile(getLogManagement().getStrbuiLogs().toString(), SystemSettings.getSettings().getPATHPosition(), getLogManagement().logFile + ".txt");
        return isSuccessful;
    }
}