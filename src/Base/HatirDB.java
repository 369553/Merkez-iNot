package Base;

import Control.IDARE;
import Services.CryptingService;
import Services.ReadSaveSystem;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.TimeZone;
/*errCode lar:
1 : YAZMA HATÂSI
2 : SİLME HATÂSI
*/
public class HatirDB implements MainDB{
    public static String DB_PATH = "";

    public HatirDB(String workingPath){
        this.DB_PATH = workingPath;
        
        //.;.
    }

//İŞLEM YÖNTEMLERİ:
    @Override
    public boolean installSystem(String code, DataBase database){
        if(!CryptingService.getMd5(code).equals(IDARE.getMd5Code()))
            return false;
        if(database == null)
            return false;
        User user = this.readUserInfo();
        if(user == null)
            return false;
        DataBase.getDatabase().setUser(user, code);
        ArrayList<Category> categories = this.readCategories();
        if(categories == null)
            return false;
        DataBase.getDatabase().setCategories(categories, code);
        ArrayList<NoteTag> tags = this.readNoteTags();
        ArrayList<Note> notes = this.readNotes();
        if(notes == null)
            return false;
        DataBase.getDatabase().setNotes(notes, code);
        if(tags != null)
            DataBase.getDatabase().setNoteTags(tags, code);
        HashMap<Integer, HashMap> pageDatas = this.readNotePageData();
        if(pageDatas != null)
            DataBase.getDatabase().setNotePageDatas(pageDatas);
        DataBase.getDatabase().lastChecks(this, code);
        if(checkSystemForConsistency()){
//            backupSystem(code, DB_PATH, false);//Yedek adresiyle sistem oluşturulduğunda bu işlem burada yapılamıyor; IDARE!de yapılıyor ama Elhamdülillâh
            return true;
        }
        return false;
    }
    @Override
    public boolean backupSystem(String code, String path, boolean isCrypt){
        DB_PATH = path;
        if(!CryptingService.getMd5(code).equals(IDARE.getMd5Code()))
            return false;
        boolean isSuccessful = false;
        for(Category ct : DataBase.getDatabase().getCategories()){
            isSuccessful = saveCategory(ct);
            if(!isSuccessful)
                return false;
        }
        for(Note note : DataBase.getDatabase().getNotes()){
            isSuccessful = saveNoteWithNoteInfo(note, DataBase.getDatabase().getNotePageData(note.getID()));
            if(!isSuccessful)
                return false;
        }
        for(NoteTag tag : DataBase.getDatabase().getNoteTags()){
            isSuccessful = saveNoteTag(tag);
            if(!isSuccessful)
                return false;
        }
        isSuccessful = saveSysInfo(DataBase.getDatabase().getSysInfo(code));
            if(!isSuccessful)
                return false;
        isSuccessful = saveUserInfo(DataBase.getDatabase().getUser());
            if(!isSuccessful)
                return false;
        return true;
    }
    @Override
    public boolean checkUserInfo(String code, String userName, String md5Password, User user){
        if(!CryptingService.getMd5(code).equals(IDARE.getMd5Code()))
            return false;
        if(!userName.equals(user.getName()))
            return false;
        if(!md5Password.equals(user.getPassword()))
            return false;
        return true;
    }
    @Override
    public boolean setupFirstConnection(String code, DataBase database){
        if(this.backupSystem(code, DB_PATH, false))
            return true;
        return false;
    }
    @Override
    public boolean saveNote(Note note){
        StringBuilder strbuild = new StringBuilder();
        strbuild.append("\n<note>");//NOT BAŞI
        //NOT BİLGİLERİNİ YAZ:
        strbuild.append("\n<id>" + note.getID() + "\n</id>");//Not özel numarası
        strbuild.append("\n<name>" + note.getName() + "\n</name>");//Notun ismi
        strbuild.append("\n<content>" + note.getMalumat() + "\n</content>");//Notun içeriği
        strbuild.append("\n<lastChangeTime>" + note.getLast_change_time().toString() + "\n</lastChangeTime>");//Notun son değiştirilme târihi
        strbuild.append("\n<produceTime>" + note.getProduce_time().toString() + "\n</produceTime>");//Notun oluşturulduğu târih
        strbuild.append("\n<onCategoryName>" + note.getCategory().getName() + "\n</onCategoryName>");//Notun bulunduğu kategorinin ismi
        for(NoteTag tempTag : note.getTags()){
            strbuild.append("\n<onTagName>" + tempTag.getName() + "\n</onTagName>");//Nota eklenen not etiketi
        }
        strbuild.append("\n<byUserID>" + note.getUser().getID() + "\n</byUserID>");//Notu oluşturan kullanıcının hüviyyet numarası
        strbuild.append("\n</note>");//NOT SONU
        //NOT BİLGİLERİ YAZILDI
        //NOT İÇİN BİR DOSYA OLUŞTUR:
        if(Services.ReadSaveSystem.usesaveReadSystem().saveFile(strbuild.toString(), DB_PATH, note.getID() + ".data"))
            return true;
        else{
            showAndHandleError(1, note.getName());
            return false;
        }
    }
    @Override
    public boolean saveNoteInfo(int noteID, HashMap<String, Integer> notePageData){
        StringBuilder strbuild = new StringBuilder();
        if(notePageData == null){
            System.err.println("Sıkıntı değikl; bu, nota daha önce girilmediğine işâret ediyoer");
            return false;
        }
        strbuild.append("\n<notePageDatas>");//Not sayfası bilgileri başlangıcı
        strbuild.append("\n<noteID>" + noteID + "\n</noteID>");
        strbuild.append("\n<caretPosition>" + notePageData.get("caretPosition") + "\n</caretPosition>");
        strbuild.append("\n<textSize>" + notePageData.get("textSize") + "\n</textSize>");
        strbuild.append("\n</notePageDatas>");//Not sayfası bilgileri sonu
        //DOSYAYA YAZ:
        if(Services.ReadSaveSystem.usesaveReadSystem().saveFile(strbuild.toString(), DB_PATH, noteID + ".pageinfo"))
            return true;
        else{
            showAndHandleError(1, "");
            return false;
        }
    }
    @Override
    public boolean saveNoteWithNoteInfo(Note note, HashMap<String, Integer> notePageData){
        if(saveNote(note)){
            saveNoteInfo(note.getID(), DataBase.getDatabase().getNotePageData(note.getID()));
            return true;
        }
        return false;
    }
    @Override
    public boolean saveCategory(Category category){
        StringBuilder strbuild = new StringBuilder();
        strbuild.append("\n<category>");//KATEGORİ BİLGİLERİ BAŞI
        strbuild.append("\n<id>" + category.getID() + "\n</id>");
        strbuild.append("\n<name>" + category.getName() + "\n</name>");
        strbuild.append("\n<explanation>" + category.getExplanation() + "\n</explanation>");
        strbuild.append("\n<lastChangeTime>" + category.getLast_change_time() + "\n</lastChangeTime>");
        strbuild.append("\n<produceTime>" + category.getProduce_time() + "\n</produceTime>");
        strbuild.append("\n<colorInHex>" + category.getCategoryColor() + "\n</colorInHex>");
        for(int index = 0; index < category.getNumberOfNotes(); index++){
            strbuild.append("\n<noteID>" + category.getNoteIDs().get(index) + "\n</noteID>");
        }
        strbuild.append("\n</category>");//KATEGORİ BİLGİLERİ SONU
        //BİLGİLER YAZILDI Bİ İZNİLLÂH
        //ŞİMDİ BUNU DOSYAYA KAYDET:
        if(Services.ReadSaveSystem.usesaveReadSystem().saveFile(strbuild.toString(), DB_PATH, category.getID() + ".CatData"))
            return true;
        else{
            showAndHandleError(1, category.getName());
            System.err.println("Hatâ mı?");
            return false;
        }
    }
    @Override
    public boolean saveNoteTag(NoteTag tag){
        StringBuilder strbuild = new StringBuilder();
        strbuild.append("\n<noteTag>");//NOT ETİKETİ BİLGİLERİ BAŞI
        strbuild.append("\n<name>" + tag.getName() + "\n</name>");
        strbuild.append("\n<id>" + tag.getID()+ "\n</id>");
        for(int index = 0; index < tag.getNumberOfNotes(); index++){
            strbuild.append("\n<noteID>" + tag.getNoteIDs().get(index) + "\n</noteID>");
        }
        strbuild.append("\n</noteTag>");//NOT ETİKETİ BİLGİLERİ SONU
        //VERİLERİ DOSYAYA YAZ:
        if(Services.ReadSaveSystem.usesaveReadSystem().saveFile(strbuild.toString(), DB_PATH, tag.getName() + ".Tdata"))
            return true;
        else{
            showAndHandleError(1, tag.getName());
            return false;
        }
    }
    /*@Override
    public boolean saveGUISeeming(GUISeeming seeming) {
        StringBuilder strbuild = new StringBuilder();
        strbuild.append("\n<>" +  + "\n<>");
    }*/
    @Override
    public boolean saveUserInfo(User user){
        StringBuilder strbuild = new StringBuilder();
        strbuild.append("\n<user>");//KULLANICI BİLGİLERİ BAŞI
        strbuild.append("\n<id>" + user.getID() + "\n</id>");
        strbuild.append("\n<name>" + user.getName() + "\n</name>");
        strbuild.append("\n<passMd5>" + user.getPassword() + "\n</passMd5>");
        strbuild.append("\n<guiSeeming>" + user.getPersonalSettings().getGuiSeemingName() + "\n</guiSeeming>");
        if(user.getPersonalSettings().getIs_logging())
            strbuild.append("\n<isLogging>" + 1 + "\n</isLogging>");
        else
            strbuild.append("\n<isLogging>" + 0 + "\n</isLogging>");
        if(user.getPersonalSettings().getIs_autoSaving())
            strbuild.append("\n<isaAtoSaving>" + 1 + "\n</isAutoSaving>");
        else
            strbuild.append("\n<isAutoSaving>" + 0 + "\n</isAutoSaving>");
        strbuild.append("\n<path>" + user.getPersonalSettings().getPATHPosition() + "\n</path>");
        strbuild.append("\n</user>");//KULLANICI BİLGİLERİ SONU
        //DOSYAYA YAZ:
        if(Services.ReadSaveSystem.usesaveReadSystem().saveFile(strbuild.toString(), DB_PATH, "es.Data"))
            return true;
        else{
            showAndHandleError(1, user.getName());
            return false;
        }
    }
    @Override
    public boolean saveSysInfo(SysInfo info){
        StringBuilder strbuild = new StringBuilder();
        strbuild.append("\n<sys>");//MÜHİM VERİTABANI BİLGİSİ BAŞI
        strbuild.append("\n<idCounterForNote>" + info.getIDCounterForNote() + "\n</idCounterForNote>");
        strbuild.append("\n<idCounterForCategory>" + info.getIDCounterForCategory() + "\n</idCounterForCategory>");
        strbuild.append("\n<idCounterForNoteTag>" + info.getIDCounterForNoteTag() + "\n</idCounterForNoteTag>");
        strbuild.append("\n<dbType>" + info.getStrDBType() + "\n</dbType>");
        strbuild.append("\n</sys>");//MÜHİM VERİTABANI BİLGİSİ SONU
        //DOSYAYA YAZ:
        if(Services.ReadSaveSystem.usesaveReadSystem().saveFile(strbuild.toString(), DB_PATH, "info.YData"))
            return true;
        else{
            showAndHandleError(1, "");
            return false;
        }
    }
    @Override
    public boolean deleteNote(Note note){
        String str = ReadSaveSystem.usesaveReadSystem().readFile(DB_PATH, note.getID() + ".data");
        if(str == null){
            showAndHandleError(2, note.getName());
            return false;
        }
        if(str.isEmpty()){
            showAndHandleError(2, note.getName());
            return false;
        }
        if(isTrueNoteFile(str, note.getID())){
            if(Services.ReadSaveSystem.usesaveReadSystem().deleteFile(DB_PATH, note.getID()+ ".data"))
                return true;
            else{
                showAndHandleError(2, note.getName());
                return false;
            }
        }
        else{
            showAndHandleError(2, note.getName());
            return false;
        }
    }
    @Override
    public boolean deleteNoteInfo(Note note){
        if(Services.ReadSaveSystem.usesaveReadSystem().deleteFile(DB_PATH, note.getID()+ ".pageinfo"))
            return true;
        else{
            showAndHandleError(2, "");
            return false;
        }
    }
    @Override
    public boolean deleteCategory(Category category){
        if(Services.ReadSaveSystem.usesaveReadSystem().deleteFile(DB_PATH, category.getName()+ ".CatData"))
            return true;
        else{
            showAndHandleError(2, category.getName());
            return false;
        }
    }
    @Override
    public boolean deleteNoteTag(NoteTag tag){
        if(Services.ReadSaveSystem.usesaveReadSystem().deleteFile(DB_PATH, tag.getName()+ ".Tdata"))
            return true;
        else{
            showAndHandleError(2, tag.getName());
            return false;
        }
    }
    /*@Override
    public boolean deleteGUISeeming(GUISeeming seeming) {
    
    }*/
    @Override
    public boolean updateNote(Note note){
        return saveNote(note);
    }
    @Override
    public boolean updateNoteInfo(int noteID, HashMap<String, Integer> notePageData){
        return saveNoteInfo(noteID, notePageData);
    }
    @Override
    public boolean updateNoteWithNoteInfo(Note note, HashMap<String, Integer> notePageData){
        return saveNoteWithNoteInfo(note, notePageData);
    }
    @Override
    public boolean updateCategory(Category category){
        return saveCategory(category);
    }
    @Override
    public boolean updateNoteTag(NoteTag tag){
        return saveNoteTag(tag);
    }
    /*@Override
    public boolean updateGUISeeming(GUISeeming seeming) {
    
    }*/
    @Override
    public boolean updateUserInfo(User user){
        return saveUserInfo(user);
    }
    @Override
    public boolean updateSysInfo(SysInfo info){
        return saveSysInfo(info);
    }
    @Override
    public ArrayList<Category> readCategories(){
        ArrayList<Category> categories = new ArrayList<Category>();
        String[] fileList = ReadSaveSystem.usesaveReadSystem().getFileList(DB_PATH);
        if(fileList == null){
            showAndHandleError(4, DB_PATH);
            return null;
        }
        int categoryNumber = 0;
        for(String str : fileList){
            if(str.endsWith(".CatData"))
                categoryNumber++;
        }
        String[] categoryFileNames = new String[categoryNumber];
        for(int index = 0; index < fileList.length; index++){
            if(fileList[index].endsWith(".CatData")){
                Category ct = extractCategory(ReadSaveSystem.usesaveReadSystem().readFile(DB_PATH, fileList[index]));
                if(ct != null){
                    categories.add(ct);
            //        System.err.println("Kategori başarıyla okundu");
                }
            }
        }
        return categories;
    }

    @Override
    public ArrayList<Note> readNotes(){
        ArrayList<Note> notes = new ArrayList<Note>();
        String[] fileList = ReadSaveSystem.usesaveReadSystem().getFileList(DB_PATH);
        if(fileList == null){
            showAndHandleError(4, DB_PATH);
            return null;
        }
        int noteNumber = 0;
        for(int index = 0; index < fileList.length; index++){
            if(fileList[index].endsWith(".data"))
                noteNumber++;
        }
        for(int index = 0; index < fileList.length; index++){
            if(fileList[index].endsWith(".data")){
                Note note = extractNote(ReadSaveSystem.usesaveReadSystem().readFile(DB_PATH, fileList[index]));
                if(note != null){
                    notes.add(note);
                    System.err.println("Not başarıyla okundu");
                }
            }
        }
        return notes;
    }

    @Override
    public ArrayList<NoteTag> readNoteTags(){
        ArrayList<NoteTag> tags = new ArrayList<NoteTag>();
        String[] fileList = ReadSaveSystem.usesaveReadSystem().getFileList(DB_PATH);
        if(fileList == null){
            showAndHandleError(4, DB_PATH);
            return null;
        }
        int tagNumber = 0;
        for(int index = 0; index < fileList.length; index++){
            if(fileList[index].endsWith(".Tdata")){
                tagNumber++;
                NoteTag tag = extractNoteTag(ReadSaveSystem.usesaveReadSystem().readFile(DB_PATH, fileList[index]));
                if(tag != null){
                    tags.add(tag);
                    System.out.println("Not etiketi başarıyla okundu");
                }
            }
        }
        return tags;
    }

    @Override
    public SysInfo readSysInfo(String code){
        if(!Services.CryptingService.getMd5(code).equals(IDARE.getMd5Code()))
            return null;
        String file = ReadSaveSystem.usesaveReadSystem().readFile(DB_PATH, "info.YData");
        if(file == null){
            showAndHandleError(4, DB_PATH);
            return null;
        }
        return extractSys(file, code);
    }

    @Override
    public User readUserInfo(){
        String file = ReadSaveSystem.usesaveReadSystem().readFile(DB_PATH, "es.Data");
        if(file == null){
            showAndHandleError(4, DB_PATH);
        }
        User user = extractUserInfo(file);
        return user;
    }

    @Override
    public HashMap<Integer, HashMap> readNotePageData(){
        HashMap<Integer, HashMap> hash = new HashMap<Integer, HashMap>();
        String[] fileList = ReadSaveSystem.usesaveReadSystem().getFileList(DB_PATH);
        if(fileList == null){
            showAndHandleError(4, DB_PATH);
            return null;
        }
        int pageDataNumber = 0;
        for(int index = 0; index < fileList.length; index++){
            if(fileList[index].endsWith(".pageinfo")){
                HashMap value = extractNotePageData(ReadSaveSystem.usesaveReadSystem().readFile(DB_PATH, fileList[index]));
                if(value != null){
                    int noteID = (int) value.get("noteID");
                    value.remove("noteID");
                    hash.put(noteID, value);
                    System.out.println("Not sayfası bilgisi başarıyla okundu.");
                }
            }
        }
        return hash;
    }
    //ARKAPLAN YÖNTEMLERİ:
    private void showAndHandleError(int errCode, String errSubject){
        String errLogText = "";
        if(errCode == 1){//Dosyaya yazma hâtası
            String errText = "Yazma hatâsı : Dosya kaydedilemedi. Yazma servisi hatâlı olabilir; hâfızada yeterli yer olmayabilir; hâfıza yazılmaya karşı korumalı olabilir";
            if(!errSubject.isEmpty())
                errLogText = "Belleğe yazma hatâsı. Şu dosya kaydedilemedi : " + errSubject;
            IDARE.notifyToIDARE(this, errText, "Warning", errLogText);
        }
        else if(errCode == 2){//Dosya silme hâtası
            String errText = "Silme hatâsı : Dosya silinemedi.";
            if(!errSubject.isEmpty())
                errLogText = "Şununla ilgili dosya silinemedi : " + errSubject;
            IDARE.notifyToIDARE(this, errText, "Warning", errLogText);
        }
        else if(errCode == 3){//Dosya okuma hâtası
            String errText = "Okuma hatâsı! Dosyalar okunamadı. Veritabanı kurulumu başarısız!"; 
            if(!errSubject.isEmpty())
                errLogText = "Şununla ilgili dosya okunamadı : " + errSubject;
            IDARE.notifyToIDARE(this, errText, "Warning", errLogText);
        }
        else if(errCode == 4){//
            String errText = "Dosya(lar) yok. Bulunulan yerde veritabanı dosyaları yok yâ dâ eksik";
            if(!errSubject.isEmpty())
                errLogText = "Seçilen veritabanı konumunda dosya(lar) yok : " + errSubject;
            IDARE.notifyToIDARE(this, errText, "Warning", errLogText);
        }
        else if(errCode == 5){//Târih bilgisinin parse edilememesi
            String errText = "Târih bilgisi ayarlanamadı. Sistemden kaynaklı olabilir.";
            if(!errSubject.isEmpty())
                errLogText = "Şununla ilgili târih bilgisi ayarlanamadı: " + errSubject + "Sistemden kaynaklı olabilir.";
            IDARE.notifyToIDARE(this, errText, "Warning", errLogText);
        }
        else if(errCode == 6){//Târih bilgisinin parse edilememesi
            String errText = "Kategori dosyasında mühim bilgi eksik. Kategori oluşturulamadı / aktarılamadı";
            if(!errSubject.isEmpty())
                errLogText = "Dosyadaki mühim (kritik) veri eksikliğinden dolayı kategori oluşturulamadı / aktarılamadı. Dosya son eki :" + errSubject;
            IDARE.notifyToIDARE(this, errText, "Warning", errLogText);
        }
        else if(errCode == 7){//Şifresi olup, isim bilgisi olmayan kullanıcının verileri yüklenmeye çalışılıyor
            String errText = "Kullanıcı bilgileriyle ilgili sorun var; dışarıdan müdâhale edilmiş olabilir. Sistem devâm edemez! Yalnızca doğru şifre girilirse belgeler kurtarılabilir";
            if(!errSubject.isEmpty())
                errLogText = "Şu kullanıcı dosyasında mühim (kritik) bilgiler eksik olduğundan sistem yüklenmedi: " + errSubject;
            IDARE.notifyToIDARE(this, errText, "Warning", errLogText);
        }
        else if(errCode == 8){
            String errText = "Mühim (kritik) veritabanı verisi eksik";
            if(!errSubject.isEmpty())
                errLogText = "Şu dosyada bulunması gereken mühim (kritik) bilgiler eksik olduğundan sistem yüklenmedi: " + errSubject;
            IDARE.notifyToIDARE(this, errText, "Warning", errLogText);
        }
        else if(errCode == 9){
            String errText = "Not dosyasında mühim (kritik) bilgi eksik";
            if(!errSubject.isEmpty())
                errLogText = "Şu dosyada bulunması gereken mühim (kritik) bilgiler eksik olduğundan sistem yüklenmedi: " + errSubject;
            IDARE.notifyToIDARE(this, errText, "Warning", errLogText);
        }
        else if((errCode == 10)){
            String errText = "Not etiketi dosyasında mühim (kritik) bilgi eksik";
            if(!errSubject.isEmpty())
                errLogText = "Şu dosyada bulunması gereken mühim (kritik) bilgiler eksik olduğundan sistem yüklenmedi: " + errSubject;
            IDARE.notifyToIDARE(this, errText, "Warning", errLogText);
        }
        else if(errCode == 11){
            String errText = "Not dosyasında etiket bilgisi eksik";
            if(!errSubject.isEmpty())
                errLogText = "Not dosyasında etiket bilgisi eksik; o yüzden bâzı etiketler aktarılamamış olabilir " + errSubject;
            IDARE.notifyToIDARE(this, errText, "Warning", errLogText);
        }
        else if(errCode == 12){
            String errText = "Veri tutarsızlığı";
            if(!errSubject.isEmpty())
                errLogText = "Veri tutarsızlığından dolayı sistem yüklenemiyor!" + errSubject;
            IDARE.notifyToIDARE(this, errText, "Warning", errLogText);
        }
        /*else if{
            //.;.
        }*/
    }
    private Note extractNote(String regularText){
//        System.out.println("regularText :\n" + regularText);
        Note note = null;
        ArrayList<NoteTag> tags = new ArrayList<NoteTag>();
        String level1 = extractBetweenText1AndText2InText3("<note>", "</note>", regularText);
        String strID = extractBetweenText1AndText2InText3("<id>", "</id>", level1);
        if(strID == null){
            showAndHandleError(9, "");
            return null;
        }
        int ID = Integer.parseInt(strID);
        String name = extractBetweenText1AndText2InText3("<name>", "</name>", level1);
        String malumat = extractBetweenText1AndText2InText3("<content>", "</content>", level1);
        Date produceTime = null, lastChangeTime = null;
        try {
            produceTime = DateFormat.getDateInstance().parse(extractBetweenText1AndText2InText3("<produceTime>", "</produceTime>", level1));
            lastChangeTime = DateFormat.getDateInstance().parse(extractBetweenText1AndText2InText3("<lastChangeTime>", "</lastChangeTime>", level1));
        }
        catch (ParseException ex) {
            showAndHandleError(5, name);
            System.err.println("zamân bilgisi alınamadı");
            produceTime = Calendar.getInstance(TimeZone.getTimeZone("GMT+3:00")).getTime();
            lastChangeTime = Calendar.getInstance(TimeZone.getTimeZone("GMT+3:00")).getTime();
        }
        String categoryName = extractBetweenText1AndText2InText3("<onCategoryName>", "</onCategoryName>", level1);
        String strUserID = extractBetweenText1AndText2InText3("<byUserID>", "</byUserID>", level1);
        if(strUserID == null){
            showAndHandleError(9, "");
            return null;
        }
        int userID = Integer.parseInt(strUserID);
        String[] strNode = level1.split("onTagName");//Fazladan yer ayrılıyor gibi
        for(String str : strNode){
            if(str.endsWith("</onTagName>")){
                String strNoteTagName = extractBetweenText1AndText2InText3("", "</onTagName>", str);
                if(strNoteTagName == null){
                    showAndHandleError(11, "");
                    return null;
                }
                NoteTag tag = DataBase.getDatabase().getNoteTagByName(strNoteTagName);
                if(tag != null)
                    tags.add(tag);
               // else
                    //.;.
            }
        }
        note = new Note(ID, DataBase.getDatabase().findUserByID(userID), name, malumat, DataBase.getDatabase().getCategoryByName(categoryName), produceTime, lastChangeTime);
        return note;
    }
    private Category extractCategory(String regularText){
        String level1 = extractBetweenText1AndText2InText3("<category>", "</category>", regularText);
        String name, explanation, colorInHex;
        ArrayList<Integer> noteIDs = new ArrayList<Integer>();
        int ID = Integer.parseInt(extractBetweenText1AndText2InText3("<id>", "</id>", level1));
        name = extractBetweenText1AndText2InText3("<name>", "</name>", level1);
        if(name == null){
            showAndHandleError(6, ".CatData");
            return null;
        }
        explanation = extractBetweenText1AndText2InText3("<explanation>", "</explanation>", level1);
        colorInHex = extractBetweenText1AndText2InText3("<colorInHex>", "</colorInHex>", level1);
        Date produceTime = null, lastChangeTime = null;
        try {
            produceTime = DateFormat.getDateInstance().parse(extractBetweenText1AndText2InText3("<produceTime>", "</produceTime>", level1));
            lastChangeTime = DateFormat.getDateInstance().parse(extractBetweenText1AndText2InText3("<lastChangeTime>", "</lastChangeTime>", level1));
        }
        catch (ParseException ex) {
        //    showAndHandleError(5, name);
            System.err.println("zamân bilgisi alınamadı");
            produceTime = Calendar.getInstance(TimeZone.getTimeZone("GMT+3:00")).getTime();
            lastChangeTime = Calendar.getInstance(TimeZone.getTimeZone("GMT+3:00")).getTime();
        }
        String[] strNode = level1.split("<noteID>");
        for(String str : strNode){
            if(str.endsWith("</noteID>")){
                String strNoteID = extractBetweenText1AndText2InText3("", "</noteID>", str);
                if(strNoteID == null){
                    showAndHandleError(6, "");
                    return null;
                }
                int noteID = Integer.parseInt(strNoteID);
                noteIDs.add(noteID);
            }
        }
        if(explanation == null)
            explanation = "";
        if(colorInHex == null)
            colorInHex = "#D87E7E";
        return new Category(ID, name, explanation, produceTime, lastChangeTime, colorInHex, noteIDs);
    }
    private User extractUserInfo(String regularText){
        boolean noName = false;
        String level1 = extractBetweenText1AndText2InText3("<user>", "</user>", regularText);
        int ID = Integer.parseInt(extractBetweenText1AndText2InText3("<id>", "</id>", level1));
        String name = extractBetweenText1AndText2InText3("<name>", "</name>", level1);
        if(name == null){
            noName = true;
        }
        String passMd5 = extractBetweenText1AndText2InText3("<passMd5>", "</passMd5>", level1);
        if(passMd5 != null && name == null){
            showAndHandleError(7, "es.Data");
        }
        if(passMd5 == null)
            passMd5 = "";
        String guiSeeemingName = extractBetweenText1AndText2InText3("<guiSeeming>", "</guiSeeming>", level1);
        if(guiSeeemingName == null) 
            guiSeeemingName = "Standard";
        String path = extractBetweenText1AndText2InText3("<path>", "</path>", level1);
        /*if(path == null){
            if(işletim sistemi == windows)
                C diski olarak ayarla;
            else if(işletim sistemi == linux)
                home dizini olarak ayarla
        }*/
        if(path == null)//Yâ dâ eksik bilgiyi kullanıcıdan iste.
            path = "C:\\";
        if(path.equals("null"))
            path = null;
        boolean isLogging = true, isAutoSaving = false;
        String log = extractBetweenText1AndText2InText3("<isLogging>", "</isLogging>", level1);
        String autoSave = extractBetweenText1AndText2InText3("<isAutoSaving>", "</isAutoSaving>", level1);
        if(log != null){
            if(log.charAt(0) == '0')
                isLogging = false;
            /*Buna gerek yok : else
                isLogging = true;*/
        }
        if(autoSave != null){
            if(autoSave.charAt(0) == '0')
                isAutoSaving = false;
            /*Buna gerek yok : else
                isAutoSaving = true;*/
        }
        return new User(ID, name, passMd5, new SystemSettings(guiSeeemingName, path, isLogging, isAutoSaving));
    }
    private NoteTag extractNoteTag(String regularText){
        NoteTag tag = null;
        ArrayList<Integer> noteIDs = new ArrayList<Integer>();
        String level1 = extractBetweenText1AndText2InText3("<noteTag>", "</noteTag>", regularText);
        if(level1 == null){
            //.;.
        }
        String name = extractBetweenText1AndText2InText3("<name>", "</name>", level1);
        if(name == null){
            showAndHandleError(10, "");
            return null;
        }
        String strID = extractBetweenText1AndText2InText3("<id>", "</id>", level1);
        if(strID == null){
            showAndHandleError(10, "");
            return null;
        }
        int tagID = 0;
        try{
            tagID = Integer.parseInt(strID);
        }
        catch(NumberFormatException exception){
            //.;.
            return null;
        }
        String[] strNode = level1.split("<noteID>");
        for(String str : strNode){
            if(str.endsWith("</noteID>")){
                String strNoteID = extractBetweenText1AndText2InText3("", "</noteID>", str);
                if(strNoteID == null){
                    showAndHandleError(10, "");
                    return null;
                }
                int noteID = Integer.parseInt(strNoteID);
                noteIDs.add(noteID);
            }
        }
        tag = new NoteTag(name, tagID);
        for(int index = 0; index < noteIDs.size(); index++){
             tag.addNote(noteIDs.get(index));
        }
        return tag;
    }
    private SysInfo extractSys(String regularText, String code){
        String level1 = extractBetweenText1AndText2InText3("<sys>", "</sys>", regularText);
        if(!Services.CryptingService.getMd5(code).equals(IDARE.getMd5Code()))
            return null;
        int IDCounterForNote = -1, IDCounterForCategory = -1, IDCounterForNoteTag = -1;
        String DBType;
        String strIDCounterForNote = extractBetweenText1AndText2InText3("<idCounterForNote>", "</idCounterForNote>", regularText);
        String strIDCounterForCategory = extractBetweenText1AndText2InText3("<idCounterForCategory>", "</idCounterForCategory>", level1);
        String strIDCounterForNoteTag = extractBetweenText1AndText2InText3("<idCounterForNoteTag>", "</idCounterForNoteTag>", level1);
        
        if(strIDCounterForNote == null){
            showAndHandleError(8, "info.YData");
            return null;
        }
        else if(strIDCounterForCategory == null){
            showAndHandleError(8, "info.YData");
            return null;
        }
        else if(strIDCounterForNoteTag == null){
            showAndHandleError(8, "info.YData");
            return null;
        }
        System.err.println("ID : " + strIDCounterForNote);
        IDCounterForNote = Integer.parseInt(strIDCounterForNote);
        IDCounterForCategory = Integer.parseInt(strIDCounterForCategory);
        IDCounterForNoteTag = Integer.parseInt(strIDCounterForNoteTag);
        DBType = extractBetweenText1AndText2InText3("<dbType>", "</dbType>", level1);
        if(DBType == null || IDCounterForCategory == -1 || IDCounterForNote == -1){
            showAndHandleError(8, "info.YData");
        }
        SysInfo sys = SysInfo.produceSysInfo(IDCounterForNote, IDCounterForCategory, IDCounterForNoteTag, DBType, code);
        return sys;
    }
    private HashMap<String, Integer> extractNotePageData(String regularText){
        HashMap<String, Integer> hash = new HashMap<String, Integer>();
        String level1 = extractBetweenText1AndText2InText3("<notePageDatas>", "</notePageDatas>", regularText);
        if(level1 == null){
            //.;.
        }
        String strNoteID = extractBetweenText1AndText2InText3("<noteID>", "</noteID>", level1);
        if(strNoteID == null){
            //.;.
        }
        int noteID = Integer.parseInt(strNoteID);
        int caretPosition = 0;
        int textSize = 16;
        String strCaretPosition = extractBetweenText1AndText2InText3("<caretPosition>", "</caretPosition>", level1);
        String strTextSize = extractBetweenText1AndText2InText3("<textSize>", "</textSize>", level1);
        if(strCaretPosition != null)
            caretPosition = Integer.parseInt(strCaretPosition);
        else{
            if(strTextSize != null){
                textSize = Integer.parseInt(strTextSize);
            }
            else{
                //.;.
            }
        }
        hash.put("textSize", textSize);
        hash.put("caretPosition", caretPosition);
        hash.put("noteID", noteID);
        return hash;
    }
    private boolean isTrueNoteFile(String regularText, int noteID){
        String level1 = extractBetweenText1AndText2InText3("<note>", "</note>", regularText);
        if(level1 == null)
            return false;
        String strNoteID = extractBetweenText1AndText2InText3("<id>", "</id>", level1);
        if(strNoteID == null)
            return false;
        int ID = Integer.parseInt(strNoteID);
        if(ID == noteID)
            return true;
        else
            return false;
    }
    private boolean isTrueCategoryFile(String regularText, int categoryID){
        String level1 = extractBetweenText1AndText2InText3("<category>", "</category>", regularText);
        if(level1 == null)
            return false;
        String strCategoryID = extractBetweenText1AndText2InText3("<id>", "</id>", level1);
        if(strCategoryID == null)
            return false;
        int ID = Integer.parseInt(strCategoryID);
        if(ID == categoryID)
            return true;
        else
            return false;
    }
    private boolean isTrueNoteTagFile(String regularText, String tagName){
        String level1 = extractBetweenText1AndText2InText3("<noteTag>", "</noteTag>", regularText);
        if(level1 == null)
            return false;
        String strTagName = extractBetweenText1AndText2InText3("<name>", "</name>", level1);
        if(strTagName == null)
            return false;
        if(strTagName == tagName)
            return true;
        else
            return false;
    }
    private boolean isTrueUserInfoFile(String regularText, String userName){
        String level1 = extractBetweenText1AndText2InText3("<user>", "</user>", regularText);
        if(level1 == null)
            return false;
        String strUserName = extractBetweenText1AndText2InText3("<name>", "</name>", level1);
        if(strUserName == null)
            return false;
        if(strUserName == userName)
            return true;
        else
            return false;
    }
    private boolean isTrueSysInfoFile(String regularText, SysInfo info){//Sadece veritabanı türü ismi kontrolü yeterli olmayabilir.
        String level1 = extractBetweenText1AndText2InText3("<sys>", "</sys>", regularText);
        if(level1 == null)
            return false;
        String strDBType = extractBetweenText1AndText2InText3("<dbType>", "</dbType>", level1);
        if(strDBType == null)
            return false;
        if(strDBType == info.getStrDBType())
            return true;
        else
            return false;
    }
    private boolean isTrueNotePageDataFile(String regularText, int noteID){
        String level1 = extractBetweenText1AndText2InText3("<notePageDatas>", "</notePageDatas>", regularText);
        if(level1 == null)
            return false;
        String strNoteID = extractBetweenText1AndText2InText3("<noteID>", "</noteID>", level1);
        if(strNoteID == null)
            return false;
        int ID = Integer.parseInt(strNoteID);
        if(ID == noteID)
            return true;
        else
            return false;
    }
    private String extractBetweenText1AndText2InText3(String Text1, String Text2, String Text3){
        /*
        Text1 : Bulunması gereken ilk etiket
        Text2 : Bulunması gereken ikinci etiket
        Text3 : Bi iznillâhiki etiketin aranıp, arasındakilerin döndürüleceği ana yazı
        index : Text3 üzerinde aramak için sayaç
        isText1Founded : Birinci etiketin bulunuduğunu ve geçtiğini ifâde eden bit
        isText2Founded : İkinci etiketin bulunuduğunu ve geçtiğini ifâde eden bit
        indexFromMatchingString : Etiket aranırken ilerleme kaydetmek için, etiket ile yazının kaç karakterinin eşleştiğini ifâde eden sayaç
        requestedText : İki etiket arasındaki yazı (istenen yazı) + ikinci etiket (İkinci etiket sonra çıkartılıyor)
        finalPoint = Text3'ün yazı uzunluğu
        */
        int index = 0;
        StringBuilder requestedText = new StringBuilder();
        boolean isText1Founded = false;
        boolean isText2Founded = false;
        int indexFromMatchingString = 0;
        int finalPoint = Text3.length();
        if(Text1.isEmpty()){
            isText1Founded = true;
        }
        while(index <= finalPoint){//YAZININ SONUNA KADAR DEVÂM ET,
            if(isText1Founded && isText2Founded){//İKİ ETİKET ARASINDAKİLER ve İKİNCİ ETİKET ALINMIŞ; İKİNCİ ETİKETİ ÇIKART
                requestedText = requestedText.delete(requestedText.length() - Text2.length(), requestedText.length());
                return requestedText.toString();
            }
            else if(isText1Founded && !isText2Founded){
                requestedText.append(Text3.charAt(index));//BİRİNCİ ETİKET BULUNDU ve İKİNCİ ETİKET HENÜZ BULUNMADIYSA ARADAKİ YAZILARI ve İKİNCİ ETİKETİ KAYDET
                if(Text3.charAt(index) == Text2.charAt(indexFromMatchingString))
                    indexFromMatchingString++;
                else{//EŞLEŞME YOKSA SAYACI SIFIRLA
                    indexFromMatchingString = 0;
            //        System.err.println("Sayaç sıfırlanmış");
                }
                if(indexFromMatchingString == Text2.length()){//İKİNCİ ETİKETİN SON HARFİNE KADAR EŞLEŞME SAĞLANDIYSA İKİNCİ ETİKETİ 'BULUNDU' OLARAK İŞÂRETLE, EŞLEŞME SAYACINI SIFIRLA
                    isText2Founded = true;
                    indexFromMatchingString = 0;
            //        System.out.println("text2 is founded");
                }
            }
            else{//İKİ ETİKETİN DE (HENÜZ) BULUNMADIĞI DURUMLAR
                if(index  == finalPoint)
                    return null;
                if(Text3.charAt(index) == Text1.charAt(indexFromMatchingString)){
                    indexFromMatchingString++;
                }
                else{//EŞLEŞME YOKSA SAYACI SIFIRLA
                    indexFromMatchingString = 0;
                    
                }
            //    System.err.println("indexFromMatchingString : " + indexFromMatchingString);
                if(indexFromMatchingString == Text1.length()){////BİRİNCİ ETİKETİN SON HARFİNE KADAR EŞLEŞME SAĞLANDIYSA BİRİNCİ ETİKETİ 'BULUNDU' OLARAK İŞÂRETLE, EŞLEŞME SAYACINI SIFIRLA
                    isText1Founded = true;
                    indexFromMatchingString = 0;
                }
            }
            index++;
        }
        return null;
    }
    private boolean checkSystemForConsistency(){//Veri tutarlılığı yoksa sistemin yüklenmesini durdur
        for(int index = 0; index < DataBase.getDatabase().getCategories().size(); index++){
            Category category = DataBase.getDatabase().getCategories().get(index);
            for(int index2 = 0; index2 < category.getNoteIDs().size(); index2++){
                Note note = DataBase.getDatabase().findNoteByID(category.getNoteIDs().get(index2));
                if(note == null)
                    return false;
            }
        }
        for(int index = 0; index < DataBase.getDatabase().getNoteTags().size(); index++){
            NoteTag tag = DataBase.getDatabase().getNoteTags().get(index);
            for(int index2 = 0; index2 < tag.getNoteIDs().size(); index2++){
                Note note = DataBase.getDatabase().findNoteByID(tag.getNoteIDs().get(index2));
                if(note == null)
                    return false;
            }
        }
        return true;
    }
}