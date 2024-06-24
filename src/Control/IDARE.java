package Control;

import Base.DataBase;
import static Base.DataBase.getDatabase;
import Base.SystemSettings;
import Base.User;
import Base.Note;
import Base.Category;
import Base.DBHelper;
import Base.NoteTag;
import Base.GUISeeming;
import Base.HatirDB;
import Base.MainDB;
import Base.MySqlDB;
import Base.SysInfo;
import Base.TestClass;
import Services.CryptingService;
import Services.DateTimeService;
import Services.ReadSaveSystem;
import View.BackupPanel;
import View.ContactPanel;
import View.EntryPage;
import View.InteractiveGUIStructs;
import View.MainView;
import View.OptionsPanel;
import View.Organizer;
import View.btnFolder;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class IDARE {
    static boolean flagPushNotification = true;
    static boolean flagSaveLog = false;//SystemSettings.getSettings().getIs_logging()
    private static String code = "IDAREHÂTIRNOT--...";
    private static String strLocation = "Anasayfa";
    private static Note inProcessing = null;
    private static User user = null;
//    private static String path = null;
    private IDARE() {
        
    }
    
//İŞLEM YÖNTEMLERİ:
    public static boolean addNoteToSys(Note note){
        if(note.getName().isEmpty()){
            if(flagPushNotification)
                ContactPanel.getContactPanel().showMessage("Not ismi yok", "Warning");
            if(flagSaveLog)
                LogManagement.writeEror("Geçersiz not ismi : İsim verisi yok");
            return false;
        }
        if(note.getName().length() > 120){
            if(flagPushNotification)
                ContactPanel.getContactPanel().showMessage("Not ismi 120 karakterden uzun olamaz", "Warning");
            if(flagSaveLog)
                LogManagement.writeEror("Geçersiz not ismi : Not ismi çok uzun (120+ harf) ");
            return false;
        }
        if(DataBase.getDatabase().getNoteByName(note.getName(), note.getCategory().getName()) != null){
            if(flagPushNotification)
                ContactPanel.getContactPanel().showMessage("Aynı kategoride aynı isimde not olmamalı", "Warning");
            if(flagSaveLog)
                LogManagement.writeEror("Not ismi çakışması : Aynı kategoride aynı isimde not olmamalı");
            return false;
        }
        if(DataBase.getDatabase().findNoteByID(note.getID()) != null){
            if(flagPushNotification)
                ContactPanel.getContactPanel().showMessage("Not numarası çakışıyor; sistemde aynı numarada bir not var", "Warning");
            if(flagSaveLog)
                LogManagement.writeEror("Not numarası çakışması : Sistemde aynı numarada bir not var");
        }
        if(DataBase.getDatabase().addNote(note, code)){
            if(flagPushNotification)
                ContactPanel.getContactPanel().showMessage("Not sisteme eklendi", "Successful");
            if(flagSaveLog)
                LogManagement.added("not", note.getName());
            
            /*if(InterfaceController.getGuiManaging().getCurrentComp().getClass().getName().equals("View.ListPanel")){
                InterfaceController.getGuiManaging().getMP().getListPanel().updateTableForAffected(note);
            }*/
            return true;
        }
        return false;
    }
    public static boolean addCategoryToSys(Category category){
        if(category.getName().isEmpty()){
            if(flagPushNotification)
                ContactPanel.getContactPanel().showMessage("Kategori ismi girilmemiş", "Warning");
            if(flagSaveLog)
                LogManagement.writeEror("Geçersiz kategori ismi : isim verisi yok");
            return false;
        }
        if(category.getName().length() > 60){
            if(flagPushNotification)
                ContactPanel.getContactPanel().showMessage("Kategori ismi 60 harften uzun olmamalı", "Warning");
            if(flagSaveLog)
                LogManagement.writeEror("Geçersiz kategori ismi : kategori ismi çok uzun (60+ harf)");
            return false;
        }
        if(DataBase.getDatabase().getCategoryByName(category.getName()) != null){
            if(flagPushNotification)
                ContactPanel.getContactPanel().showMessage("Aynı isimde kategori var", "Warning");
            if(flagSaveLog)
                LogManagement.writeEror("Kategori ismi çakışması : sistemde aynı isimde kategori var");
            return false;
        }
        if(DataBase.getDatabase().findCategoryByID(category.getID()) != null){
            if(flagPushNotification)
                ContactPanel.getContactPanel().showMessage("Kategori numarası çakışması : Sistemde aynı numarada kategori var", "Warning");
            if(flagSaveLog)
                LogManagement.writeEror("Kategori numarası çakışması : sistemde aynı numarada kategori var");
            return false;
        }
        //Aynı renkte kategori olmaması eklenebilir.
        if(DataBase.getDatabase().addCategory(category, code)){
            if(flagPushNotification)
                ContactPanel.getContactPanel().showMessage("Kategori sisteme eklendi", "Successful");
            if(flagSaveLog)
                LogManagement.added("kategori", category.getName());
            return true;
        }
        return false;
    }
    public static boolean addNoteTagToSys(NoteTag tag){
        if(tag.getName().isEmpty()){
            if(flagPushNotification)
                ContactPanel.getContactPanel().showMessage("Not etiketi ismi girilmemiş", "Warning");
            if(flagSaveLog)
                LogManagement.writeEror("Geçersiz not etiketi : isim verisi yok");
            return false;
        }
        if(tag.getName().length() > 60){
            if(flagPushNotification)
                ContactPanel.getContactPanel().showMessage("Not etiketi çok uzun", "Warning");
            if(flagSaveLog)
                LogManagement.writeEror("Geçersiz not etiketi verisi : not etiketi çok uzun(60+ harf)");
            return false;
        }
        if(DataBase.getDatabase().getNoteTagByName(tag.getName()) != null){
            if(flagPushNotification)
                ContactPanel.getContactPanel().showMessage("Bu isimde bir not etiketi zaten var", "Warning");
            if(flagSaveLog)
                LogManagement.writeEror("Not etiketi çakışması : sistemde bu isimde bir not etiketi var");
            return false;
        }
        if(DataBase.getDatabase().addTag(tag, code)){
            if(flagPushNotification)
                ContactPanel.getContactPanel().showMessage("Not etiketi sisteme eklendi", "Successful");
            if(flagSaveLog)
                LogManagement.added("not etiketi", tag.getName());
            return true;
        }
        return false;
    }
    public static boolean addGUISeemingsToSys(GUISeeming guiOrder){
        if(guiOrder.getGuiSeemingName().isEmpty()){
            if(flagPushNotification)
                ContactPanel.getContactPanel().showMessage("Görünüm için isim girilmemiş", "Warning");
            if(flagSaveLog)
                LogManagement.writeEror("Geçersiz görsel görünüm ismi : İsim verisi yok");
            return false;
        }
        if(DataBase.getDatabase().getGUISeemingByName(guiOrder.getGuiSeemingName()) != null){
            if(flagPushNotification)
                ContactPanel.getContactPanel().showMessage("Bu isimde bir görsel görünüm var", "Warning");
            if(flagSaveLog)
                LogManagement.writeEror("Görsel görünüm ismi çakışması : sistemde bu isimde bir görsel görünüm var");
            return false;
        }
        boolean flagEmptyField = false;
        for(int index = 0; index < guiOrder.getClass().getFields().length; index++){
            if(guiOrder.getClass().getFields()[index] == null)
                flagEmptyField = true;
        }
        if(flagEmptyField == true){
            if(flagPushNotification)
                ContactPanel.getContactPanel().showMessage("Eksik veri, bâzı alanların bilgisi girilmemiş", "Warning");
            if(flagSaveLog)
                LogManagement.writeEror("Eksik veri : Görsel görünümün renk verisinde eksik var");
            return false;
        }
        if(DataBase.getDatabase().addGUISeeming(guiOrder, code)){
            if(flagPushNotification)
                ContactPanel.getContactPanel().showMessage("Görsel görüünüm eklendi", "Successful");
            if(flagSaveLog)
                LogManagement.added("görsel görümüm", guiOrder.getGuiSeemingName());
            return true;
        }
        return false;
    }
    public static boolean deleteNoteFromSys(Note note){
        return DataBase.getDatabase().deleteNote(note.getID(), code);
    }
    public static boolean deleteCategoryFromSys(Category category){
        return DataBase.getDatabase().deleteCategory(category.getID(), code);
    }
    public static boolean deleteNoteTagFromSys(NoteTag tag){
        return DataBase.getDatabase().deleteNoteTag(tag.getName(), code);
    }
    protected static void changeUser(User user){
        getDatabase().setUser(user, code);
        IDARE.startAgain();
    }
    public static void openNote(Note note){
        InterfaceController.getGuiManaging().setChoosedThing(null);
        InterfaceController.getGuiManaging().addToMP(InterfaceController.getGuiManaging().getMP().getEditPanel());
        HashMap<String, Integer> pageData = DataBase.getDatabase().getNotePageData(note.getID());
        int caretPosition = 0, textSize = 14;
        if(pageData != null){
            if(pageData.get("caretPosition") != null)
                caretPosition = pageData.get("caretPosition");
            if(pageData.get("textSize") != null)
                textSize = pageData.get("textSize");
        }
        IDARE.inProcessing = note;
        InterfaceController.getGuiManaging().getMP().getEditPanel().fillDataToPage(note, caretPosition, textSize);
        IDARE.strLocation = "Anasayfa/" + note.getCategory().getName() + "/" + note.getName();
        InterfaceController.getGuiManaging().updateLocation();
        InterfaceController.getGuiManaging().getMP().getEditPanel().isChanged = false;
       InterfaceController.getGuiManaging().getMP().getEditPanel().getEditTools().getBtnSave().setEnabled(false);
        InterfaceController.getGuiManaging().getMP().getEditPanel().getTextArea_writingArea().requestFocus();
        if(flagPushNotification)
            ContactPanel.getContactPanel().showMessage(inProcessing.getName() + " isimli not açıldı.", "Info");
    }
     public static void openCategoryInFolderSeeming(Category category){
        IDARE.inProcessing = null;
        IDARE.strLocation = "Anasayfa/" + category.getName();
        InterfaceController.getGuiManaging().getMP().getManagPanel().updatePanel();
        if(!InterfaceController.getGuiManaging().getCurrentComp().getClass().getName().equals("View.ManagementPanel"))
            InterfaceController.getGuiManaging().addToMP(InterfaceController.getGuiManaging().getMP().getManagPanel());
        InterfaceController.getGuiManaging().getMP().getTopMenu().updateLocation();
    }
    public static void openCategoryInFolderSeeming(String categoryName){
        Category ct = DataBase.getDatabase().getCategoryByName(categoryName);
        if(ct == null)
            return;
        IDARE.openCategoryInFolderSeeming(ct);
    }
    public static void goToUpLocation(){
        IDARE.inProcessing = null;
        if(IDARE.strLocation.split("/").length == 3)
            IDARE.openCategoryInFolderSeeming(IDARE.strLocation.split("/")[1]);
        else
            IDARE.openHomePage();
    }
    protected static void startAgain(){
        /*.;.
        closeHatırNOT();
        startHatırNOT(code);*/
    }
    protected static void changeSystemSettings(){
        app(DataBase.getDatabase().getUser().getPersonalSettings());
        if(flagPushNotification)
            ContactPanel.getContactPanel().showMessage("Sistem ayarlarındaki değişiklikler uygulandı", "Info");
    }
    public static boolean changeExplanationOfCategory(int categoryID, String text){
        Category choosedCategory = DataBase.getDatabase().findCategoryByID(categoryID);
        if(choosedCategory == null){
            if(flagPushNotification)
                ContactPanel.getContactPanel().showMessage("Not sistemde bulunamadı", "Warning");
            if(flagSaveLog)
                LogManagement.writeEror("Açıklaması değiştirilmesi istenen kategori veritabanında bulunamadı");
            return false;
        }
        choosedCategory.changeExplanation(text);
        if(flagPushNotification)
            ContactPanel.getContactPanel().showMessage("Kategori açıklaması değiştirildi", "Successful");
        if(flagSaveLog)
            LogManagement.updated("kategori", choosedCategory.getName(), "Kategori açıklaması değiştirildi.");
        return true;
    }
    public static boolean renameCategory(int categoryID, String name){
        System.err.println("name :" + name);
        Category currentCategory = DataBase.getDatabase().findCategoryByID(categoryID);
        if(currentCategory == null){
            if(flagPushNotification)
                ContactPanel.getContactPanel().showMessage("Kategori sistemde bulunamadı", "Warning");
            if(flagSaveLog)
                LogManagement.writeEror("İsmi değiştirilmek istenen kategori sistemde bulunamadı");
            return false;
        }
        if(name == null){
            if(flagPushNotification)
                ContactPanel.getContactPanel().showMessage("Bir kategori ismi girmelisiniz!", "Warning");
            if(flagSaveLog)
                LogManagement.writeEror(currentCategory.getName() + " kategorisinin ismi değiştirilmek istendi; lâkin kategori ismi noksan. Kategori etiket numarası : " + currentCategory.getID());
            return false;
        }
        if(name.isEmpty()){
            if(flagPushNotification)
                ContactPanel.getContactPanel().showMessage("Bir kategori ismi girmelisiniz!", "Warning");
            if(flagSaveLog)
                LogManagement.writeEror(currentCategory.getName() + " kategorisinin ismi değiştirilmek istendi; lâkin kategori ismi noksan. Kategori etiket numarası : " + currentCategory.getID());
            return false;
        }
        if(name.length() > 60){
            if(flagPushNotification)
                ContactPanel.getContactPanel().showMessage("Kategori ismi 60 harften uzun olmamalı", "Warning");
            if(flagSaveLog)
                LogManagement.writeEror("Geçersiz kategori ismi : kategori ismi çok uzun (60+ harf)");
            return false;
        }
        Category fetchedCategory = DataBase.getDatabase().getCategoryByName(name);
        if(fetchedCategory != null){
            if(fetchedCategory.getID() != currentCategory.getID()){
                if(flagPushNotification)
                    ContactPanel.getContactPanel().showMessage("Bu isimde kategori var!", "Warning");
                if(flagSaveLog)
                    LogManagement.writeEror("Kategori ismi çakışması : sistemde aynı isimde kategori var");
        //        sameName = true;
                return false;
            }
            return true;//Sistemdeki aynı isimde olan kategori bu kaegori ile aynı etiket numarasına sâhipse kategorinin yeni ismi eski ismiyle aynı demektir.
        }
        String oldName = currentCategory.getName();
        currentCategory.renameCategory(name);
        btnFolder choosedBtnFolder = (btnFolder) InterfaceController.getGuiManaging().getChoosedThing().guiComponent;
        choosedBtnFolder.setText(name);
        if(flagPushNotification)
            ContactPanel.getContactPanel().showMessage("Yeniden isimlendirme işlemi başarılı", "Successful");
        if(flagSaveLog)
            LogManagement.updated("kategori", oldName, "Kategori ismi değiştirildi, yeni isim : " + currentCategory.getName());
        return true;
    }
    public static boolean renameNote(int noteID, String name){
        Note currentNote = DataBase.getDatabase().findNoteByID(noteID);
        if(currentNote == null){
            if(flagPushNotification)
                ContactPanel.getContactPanel().showMessage("Not sistemde bulunamadı", "Warning");
            if(flagSaveLog)
                LogManagement.writeEror("İsmi değiştirilmek istenen not sistemde bulunamadı");
            return false;
        }
        if(name == null){
            if(flagPushNotification)
                ContactPanel.getContactPanel().showMessage("Bir not ismi girmelisiniz!", "Warning");
            if(flagSaveLog)
                LogManagement.writeEror(currentNote.getName() + " notunun ismi değiştirilmek istendi; lâkin not ismi noksan. Not etiket numarası : " + currentNote.getID());
            return false;
        }
        if(name.length() > 120){
            if(flagPushNotification)
                ContactPanel.getContactPanel().showMessage("Not ismi 120 harften uzun olmamalı", "Warning");
            if(flagSaveLog)
                LogManagement.writeEror("Geçersiz not ismi : not ismi çok uzun (120+ harf)");
            return false;
        }
        Note fetchedNote = DataBase.getDatabase().getNoteByName(name, currentNote.getCategory().getName());
        if(fetchedNote != null){//BURADA
            if(fetchedNote.getID() != currentNote.getID()){
                if(flagPushNotification)
                    ContactPanel.getContactPanel().showMessage("Aynı kategoride aynı isimde not olmamalı", "Warning");
                if(flagSaveLog)
                    LogManagement.writeEror("Not ismi çakışması : Aynı kategoride aynı isimde not olmamalı");
        //        sameName = true;
                return false;
            }
            return true;//Sistemdeki aynı kategoride aynı isimde olan not bu not ile aynı etiket numarasına sâhipse notun yeni ismi eski ismiyle aynı demektir.
        }
        String oldName = currentNote.getName();
        currentNote.renameNote(name);
        btnFolder choosedBtnFolder = (btnFolder) InterfaceController.getGuiManaging().getChoosedThing().guiComponent;
        choosedBtnFolder.setText(name);
        if(flagPushNotification)
            ContactPanel.getContactPanel().showMessage("Yeniden isimlendirme işlemi başarılı", "Successful");
        if(flagSaveLog)
            LogManagement.updated("not", oldName, "Not ismi değiştirildi, yeni isim : " + currentNote.getName());
        return true;
    }
    public static void openHomePage(){
        IDARE.strLocation = "Anasayfa";
        IDARE.inProcessing = null;
        if(InterfaceController.getGuiManaging().getChoosedThing() != null)
            InterfaceController.getGuiManaging().setChoosedThing(null);
        InterfaceController.getGuiManaging().getMP().getManagPanel().updatePanel();
        if(!InterfaceController.getGuiManaging().getCurrentComp().getClass().getName().equals("View.ManagementPanel"))
            InterfaceController.getGuiManaging().addToMP(InterfaceController.getGuiManaging().getMP().getManagPanel());
        InterfaceController.getGuiManaging().updateLocation();
    }
    public static void openListSeeming(){
        IDARE.strLocation = "Tablo";
        IDARE.inProcessing = null;
        if(InterfaceController.getGuiManaging().getChoosedThing() != null)
            InterfaceController.getGuiManaging().setChoosedThing(null);
        if(InterfaceController.getGuiManaging().getMP().getListPanel() != null)
            InterfaceController.getGuiManaging().getMP().getListPanel().updateListPanel();
        InterfaceController.getGuiManaging().addToMP(InterfaceController.getGuiManaging().getMP().getListPanel());
        InterfaceController.getGuiManaging().updateLocation();
    }
    public static void openOptionsPanel(){
        IDARE.strLocation = "Ayarlar";
        InterfaceController.getGuiManaging().addToMP(new OptionsPanel());
        InterfaceController.getGuiManaging().setChoosedThing(null);
    }

//ARKAPLAN İŞLEM YÖNTEMLERİ:
    public static void app(SystemSettings settings, boolean updateDB){
        if(settings == null)
            return;
        SystemSettings.appUserSettings(settings);
        if(!GUISeeming.getCurrentGUISeeming().getGuiSeemingName().equals(settings.getCurrentGuiSeeming().getGuiSeemingName())){
        //    GUISeeming.setCurrentGuiSeeming(settings.getCurrentGuiSeeming(), code);
//            System.out.println("Görünümün değişmesi lazım");
            GUISeeming.updateGUISeeming();
        }
        flagSaveLog = SystemSettings.getSettings().getIs_logging();
//        System.out.println("flagSaveLog : " + flagSaveLog);
        if(updateDB){
            if(DataBase.getDatabase().getMainDB() != null)
                DataBase.getDatabase().getMainDB().updateUserInfo(DataBase.getDatabase().getUser());
        }
    }
    public static void app(SystemSettings settings){
        app(settings, false);
    }
    public static void startHatırNOT(String code){
        if(code.equals(IDARE.code)){
            ActEntryPage.ProduceActEntryPage(EntryPage.getEntryPage(), code);
            EntryPage.getEntryPage().setVisible(true);
/*ESKİ YÖNTEM :*/           //MainView view = new MainView();
          //  TestClass test = new TestClass(code);
        }
    }
    /*protected static void closeHatırNOT(){
        //Ana veritabanına kaydet
        DataBase.resetDB(code);
        InterfaceController.resetInterface(code);
        SystemSettings.resetSetting(code);
        System.gc();
        System.exit(0);
        System.out.println("Çıkıştan sonraki satır");
            //en son pencerenin default close operation fonksiyonunu çağır
    }*/
    public static boolean saveNote(String malumat, int caretPoint, int textSize){
        boolean dataWasNull = false;
        if(inProcessing == null)
            return false;
        if(!inProcessing.getName().equals(IDARE.strLocation.split("/")[2]))
            return false;
        inProcessing.changeContent(malumat);
        HashMap<String, Integer> data = DataBase.getDatabase().getNotePageData(inProcessing.getID());
        if(data == null){
            dataWasNull = true;
    //        System.err.println("Sistemde verisi bulunamadı");
            data = new HashMap<String, Integer>();
            DataBase.getDatabase().getNotePageDatas().put(inProcessing.getID(), data);
        }
        data.clear();
        data.put("caretPosition", caretPoint);
        data.put("textSize", textSize);
        MainDB DB = DataBase.getDatabase().getMainDB();
        if(dataWasNull){
            if(DB != null)
                DB.saveNoteInfo(inProcessing.getID(), data);
        }
        if(DataBase.getDatabase().getNotePageData(inProcessing.getID()) == null)
            System.err.println("Sistemde kayıt yok");
        if(DataBase.getDatabase().getMainDB() != null)
           DataBase.getDatabase().getMainDB().updateNoteInfo(inProcessing.getID(), data);
//        System.err.println("Kaydedilen konum : " + DataBase.getDatabase().getNotePageData(inProcessing.getID()).get("caretPosition"));
        if(flagPushNotification)
            ContactPanel.getContactPanel().showMessage("Not kaydetme işlemi tamâmlandı", "Successful");
        if(flagSaveLog)
            LogManagement.updated("not", inProcessing.getName());
        return true;
    }
    public static void exitInProcessing(){
        if(inProcessing == null)
            return;
        inProcessing = null;
    /*    String[] strLocations = strLocation.split("/");
        strLocation = strLocations[0] + "/" + strLocations[1];*/
    }
    public static boolean isInProcessing(){
        if(inProcessing == null)
            return false;
        return true;
    }
    public static boolean changeColorOfCategory(String categoryName, Color color){
        Category category = DataBase.getDatabase().getCategoryByName(categoryName);
        if(category == null)
            return false;
        String currentColor = category.getCategoryColor();
        category.changeColor(Services.ColorProcess.getColorService().getAsHexCode(color));
        if(!category.getCategoryColor().equals(currentColor)){
            if(flagPushNotification)
                ContactPanel.getContactPanel().showMessage("Kategori renk değişimi işlemi başarılı", "Successful");
            if(flagSaveLog)
                LogManagement.updated("category", categoryName, categoryName + " isimli kategorinin rengi " + category.getCategoryColor() + " olarak değiştirildi.");
        //    IDARE.openHomePage();
            IDARE.updateGUIData();
            return true;
        }
        return false;
    }
    public static void updateGUIData(){
        if(Organizer.isWorkBefore)
            Organizer.getOrgMenu().updateOrganizer();
    }
    private static boolean installSession(String DBType, String code, HashMap<String, String> loginInf){
        if(code == null)
            return false;
        if(code.isEmpty())
            return false;
        MainDB DB = null;
        /*GEREK YOK GİBİ : if(DBType == null)
            return false;
        if(DBType.isEmpty())
            return false;*/
        if(CryptingService.getMd5(code).equals(IDARE.getMd5Code())){
            if(DBType.equals("mysql")){
                String username = loginInf.get("username");
                String password = loginInf.get("password");
                if(username == null || password == null)
                    return false;
                DBHelper.setEntryInfo(username, password, code);
                Connection connext = DBHelper.getFirstConnection();
                if(connext == null)
                    return false;
                System.out.println("İlk bağlantı başarılı");
                ArrayList<String> tables = DBHelper.getCatalogNames(DBHelper.getFirstConnection());
                if(tables.isEmpty()){
                    ContactPanel.getContactPanel().showMessage("MySQL sisteminde ilgili veritabanı yok", "Warning");
                    return false;
                }
                ArrayList<String> ourDBs = new ArrayList<String>();
                for(String dbName : tables){
                    if(dbName.startsWith("hatirnot"))
                        ourDBs.add(dbName);
                }
                if(ourDBs.size() == 0){
                    ContactPanel.getContactPanel().showMessage("Sisteme âit veritabanı bulunamadı", "Warning");
                    return false;
                }
                String[] liStrOurDBs = new String[ourDBs.size()];
                String choosedDB = null;
                ourDBs.toArray(liStrOurDBs);
                int number = -1;
                if(ourDBs.size() > 1){
                    InteractiveGUIStructs activeManager = InteractiveGUIStructs.runActiveManagerForTemporary((Component)EntryPage.getEntryPage());
                    JPanel pnl = activeManager.prepareSpecialListView(liStrOurDBs, true, "Veritabanını seç");
                    activeManager.showSpecialGUI(pnl, "Veritabanı seçimi", "Yüklemek istediğiniz veritabanını seçiniz");
                    choosedDB = activeManager.getSelectedOne();
                    InteractiveGUIStructs.deleteInteractiveGUIStructs(activeManager);
                    if(choosedDB == null)
                        return false;
                    if(choosedDB.length() == 8)
                        number = 1;
                    else
                        number = Integer.parseInt(choosedDB.substring(8));
                }
                DB = new MySqlDB(connext);
                    System.out.println("numara, sayı olarak : " + number);
                    System.out.println("Seçilen veritabanı : " + choosedDB);
                if(choosedDB != null)
                    DBHelper.setDBUrlNumber(number, (MySqlDB) DB);
                else
                    return false;
                SysInfo info = DB.readSysInfo(code);
                if(info == null)
                    return false;
                System.out.println("info başarıyla okunmuş(YY : IDARE, 520)");
                User userInfo = DB.readUserInfo();
                if(userInfo == null)
                    return false;
                System.out.println("user başarıyla okunmuş(YY : IDARE, 561)");
                if(userInfo.getPersonalSettings().getIs_logging()){
                    String path = userInfo.getPersonalSettings().getPATHPosition();
                    boolean isSuccessful = false;
                    if(path == null)
                        isSuccessful = false;
                    if(path.isEmpty())
                        isSuccessful = false;
                    if(Services.ReadSaveSystem.usesaveReadSystem().checkFilePositionForRW(path))
                        isSuccessful = true;
                    if(!isSuccessful){
                        userInfo.getPersonalSettings().setIs_logging(false);
                        ContactPanel.getContactPanel().showMessage("Sistem kaydı, kayıt adresine bağlı bir sıkıntıdan dolayı yazılamadığından devre dışı bırakıldı.", "Warning");
                    }
                }
                boolean isSuccesful = DataBase.runDatabase(info, DB, userInfo, code);
                if(!isSuccesful)
                    return false;
                System.out.println("Sistem yüklemesine kadar başarılı");
                if(DB.installSystem(code, DataBase.getDatabase()))
                    return true;
                return false;
            }
            else if(DBType.equals("hatirdb")){
                String path = loginInf.get("path");
                String password = loginInf.get("password");
                if(path == null)
                    return false;
                DB = new HatirDB(path);/*Burada DB'nin yedek adresi veriliyor; halbuki yedeği okuma fonk
                siyonuna yedek adresi verilip, yedekte yazan DB çalışma dizini bilgisiyle yeni bir DB
                        oluşturulmalı; bunu düzelt; sonra da alttakini düzelt*/
                SysInfo info = DB.readSysInfo(code);
                User userInfo = DB.readUserInfo();
                /*boolean correctUser = DB.checkUserInfo(code, loginInf.get("username"), CryptingService.getMd5(loginInf.get("password")), userInfo);
                if(!correctUser){
                    ContactPanel.getContactPanel().showMessage("Kullanıcı bilgilerinde yanlışlık var", "Warning");
                    return false;
                }*/
                if(!DataBase.runDatabase(info, DB ,userInfo, code))
                    return false;
                if(DB.installSystem(code, DataBase.getDatabase())){
                    if(userInfo.getPersonalSettings().getPATHPosition() != null)
                        DB.backupSystem(code, userInfo.getPersonalSettings().getPATHPosition(), false);
                    return true;
                }
                else
                    return false;
            }
        }
        return false;
    }
    public static boolean startInstallationOfSystem(String code, String DBType, HashMap<String, String> info, boolean runFromBackup, boolean runWithoutDatabase){
        if(!code.equals(IDARE.code))
            return false;
        if(runFromBackup){//YEDEKTEN DÖNÜLÜYORSA
            if(DBType == null)
                return false;
            if(DBType.isEmpty())
                return false;
            if(DBType.equals("mysql")){
                HashMap<String, String> loginInfo = new HashMap<String, String>();
                loginInfo.put("username", info.get("username"));
                loginInfo.put("password", info.get("password"));
                if(installSession(DBType, code, loginInfo)){
                    IDARE.app(DataBase.getDatabase().getUser().getPersonalSettings(), false);
                    EntryPage.getEntryPage().setEnabled(false);
                    EntryPage.getEntryPage().setVisible(false);
                    /*Veriler yüklendiyse*/ MainView view = new MainView();
                    ContactPanel.getContactPanel().updateSeeming();
                    ContactPanel.getContactPanel().showMessage("Veriler başarıyla içe aktarıldı", "Successful");
                    return true;
                }
                else
                    return false;
            }
            else if(DBType.equals("hatirdb")){
                String path = info.get("path");
                String password = info.get("password");
                if(path == null)
                    return false;
                if(path.isEmpty())
                    return false;
                HashMap<String, String> loginInfo = new HashMap<String, String>();
                loginInfo.put("path", path);
                if(info.get("password") != null)
                    loginInfo.put("password", info.get("password"));
                if(installSession(DBType, code, loginInfo)){
                    IDARE.app(DataBase.getDatabase().getUser().getPersonalSettings());
                    EntryPage.getEntryPage().setEnabled(false);
                    EntryPage.getEntryPage().setVisible(false);
                    /*Veriler yüklendiyse*/ MainView view = new MainView();
                    ContactPanel.getContactPanel().updateSeeming();
                    ContactPanel.getContactPanel().showMessage("Veriler başarıyla içe aktarıldı", "Successful");
                    return true;
                }
                else
                    return false;
            }
            return false;
        }
        if(runWithoutDatabase){//SİSTEMİ VERİTABANSIZ ÇALIŞTIRMA SEÇENEĞİ SEÇİLDİYSE VERİTABANI BAĞLANTISI YAPMA
            EntryPage.getEntryPage().setEnabled(false);
            EntryPage.getEntryPage().setVisible(false);
            SysInfo sys = SysInfo.produceSysInfo(1, 1, 1, "hatirdb", code);
            DataBase.runDatabase(sys, code);
            MainView view = new MainView();//Sistemden çıkarken uyarı yapılmalı : tüm verileriniz silinecek bi iznillâh
            return true;
        }
        //YEDEKTEN DÖNÜLMÜYOR ve SİSTEM VERİTABANI KULLANILARAK SIFIRDAN BAŞLATILMAK İSTENİYORSA
        if(DBType == null)
            return false;
        if(DBType.isEmpty())
            return false;
        if(DBType.equals("mysql")){
            MainView view = null;
            //Kullanıcıdan giriş bilgilerini al, eğer almadıysan; bunları HashMap'e aktar
            HashMap<String, String> infData = new HashMap<String, String>();
            infData.put("username", info.get("username"));
            infData.put("password", info.get("password"));
            if(IDARE.setupNewConnection("mysql", info)){
                EntryPage.getEntryPage().setEnabled(false);
                EntryPage.getEntryPage().setVisible(false);
                view = new MainView();
                return true;
            }
            return false;
        }
        if(DBType.equals("hatirdb")){
                String path = info.get("path");
                String password = info.get("password");
                MainView view = null;
                //Kullanıcıdan dosya yolunu al, eğer almadıysan; bunu bir HashMap'e aktar
                HashMap<String, String> loginInfo = new HashMap<String, String>();
                loginInfo.put("path", path);
                if(password != null)
                    loginInfo.put("password", password);
            if(IDARE.setupNewConnection("hatirdb", loginInfo)){
                    EntryPage.getEntryPage().setEnabled(false);
                    EntryPage.getEntryPage().setVisible(false);
                view = new MainView();
                return true;
            }
            return false;
        }
        return false;
    }
    public static void notifyToIDARE(Object source, String message, String messageType, String logText){
        if(source.getClass().getName().equals("Base.HatirDB")){
            if(flagPushNotification){
                ContactPanel.getContactPanel().showMessage(message, messageType);
            }
            if(flagSaveLog){
                if(messageType.equals("Warning")){
                    if(!logText.isEmpty())
                        LogManagement.writeDBError("HatirDB", logText);
                }
            }
        }
    }
    private static boolean setupNewConnection(String DBType, HashMap<String, String> info){
        if(DBType.equals("mysql")){
            String userName = info.get("username");
            String pass = info.get("password");
            DBHelper.setEntryInfo(userName, pass, code);
            Connection connext = DBHelper.getFirstConnection();
            if(connext == null)
                return false;
            String hatirNotUserName = JOptionPane.showInputDialog(EntryPage.getEntryPage(), "Kullanıcı ismi belirleyiniz", "Yeni kullanıcı kaydı", JOptionPane.QUESTION_MESSAGE);
            String hatirNotPassword = JOptionPane.showInputDialog(EntryPage.getEntryPage(), "Şifre belirleyiniz", "Yeni kullanıcı kaydı", JOptionPane.QUESTION_MESSAGE);
            SysInfo sys = SysInfo.produceSysInfo(1, 1, 1, "mysql", code);
            MainDB DB = new MySqlDB(connext);
            SystemSettings settings = SystemSettings.produceSystemSettings("Standard", "", false, false, code);
            User user = new User(1, hatirNotUserName, CryptingService.getMd5(hatirNotPassword), settings);
            if(user == null)
                return false;
            DataBase.runDatabase(sys, DB, user, code);
            if(DB.setupFirstConnection(code, DataBase.getDatabase())){
                IDARE.app(user.getPersonalSettings());
                DataBase.getDatabase().saveAll(code);
                return true;
            }
            return false;
        }
        if(DBType.equals("hatirdb")){
            String path = info.get("path");
            String password = info.get("password");
            if(path == null)
                return false;
            if(path.isEmpty())
                return false;
            if(!ReadSaveSystem.usesaveReadSystem().checkFilePositionForRW(path)){
                ContactPanel.getContactPanel().showMessage("Seçilen dizin yazma ve okuma işlevi için uygun değil (Disk korumalı olabilir)", "Warning");
                return false;
            }
            if(password != null){
                if(!password.isEmpty()){
                    IDARE.code = password;
                    System.err.println("Şifre değişimi başarılı");
                }
            }
            String userName = JOptionPane.showInputDialog(EntryPage.getEntryPage(), "Kullanıcı ismi belirleyiniz", "Yeni kullanıcı kaydı", JOptionPane.QUESTION_MESSAGE);
            if(userName == null)
                userName = "Ziyâretçi";
            if(userName.isEmpty())
                userName = "Ziyâretçi";
            SysInfo sys = SysInfo.produceSysInfo(1, 1, 1, "hatirdb", code);
            HatirDB DB = new HatirDB(path);
            User user = new User(1, userName, IDARE.code, SystemSettings.produceSystemSettings("Standard", path, false, false, code));
            DataBase.runDatabase(sys, DB, user, code);
            IDARE.app(user.getPersonalSettings());
            if(user == null)
                return false;
            if(DB.setupFirstConnection(code, DataBase.getDatabase()))
                return true;
            return false;
        }
        return false;
    }
    public static boolean takeBackup(boolean isCrypt, String path){
        if(DataBase.getDatabase().getMainDB() == null){
            MainDB DB = null;
            DB = new HatirDB(path);
            if(DB.backupSystem(code, path, isCrypt)){
                ContactPanel.getContactPanel().showMessage("Veriler başarıyla yedeklendi", "Successful");
                return true;
            }
            else
                return false;
        }
        if(DataBase.getDatabase().getMainDB().getClass().getName().equals("Base.HatirDB")){
            MainDB DB = null;
            DB = DataBase.getDatabase().getMainDB();
            if(DB.backupSystem(code, path, isCrypt)){
                ContactPanel.getContactPanel().showMessage("Veriler başarıyla yedeklendi", "Successful");
                return true;
            }
            else
                return false;
        }
        return false;
    }
    public static void openBackupPanel(){
        IDARE.strLocation = "Yedekleme";
        InterfaceController.getGuiManaging().addToMP(new BackupPanel());
        InterfaceController.getGuiManaging().setChoosedThing(null);
    }
    public static boolean exportNotesWithoutCrypting(Object from, String path){
        if(!from.getClass().getName().equals("Control.ActOnBackup"))
            return false;
        int index = 0;
        for(Note note : DataBase.getDatabase().getNotes()){
            StringBuilder text = new StringBuilder();
            text.append("Not ismi : " + note.getName() + "\n" +
                    "Not kategorisi : " + note.getCategory().getName());
            for(NoteTag tag : note.getTags()){
                text.append("\nNot etiketi : " + tag.getName());
            }
            text.append("\nNotun oluşturulduğu târih : " + DateTimeService.getDtService().showDateTimeAsString(note.getProduce_time()));
            text.append("\nNot içeriği : " + note.getMalumat());
            boolean isSuccessful = ReadSaveSystem.usesaveReadSystem().saveFile(text.toString(), path, index + ".txt");
            if(!isSuccessful){
                if(flagPushNotification)
                    ContactPanel.getContactPanel().showMessage("Notlar dışarı aktarılamadı", "Warning");
                if(flagSaveLog)
                    LogManagement.writeEror("Notlar dışarı aktarılamadı");
                return false;
            }
            index++;
        }
        return true;
    }

//ERİŞİM YÖNTEMLERİ:
    public static String getLocationName(){
        return strLocation;
    }
    public static String getMd5Code(){
        return code;
    }
}