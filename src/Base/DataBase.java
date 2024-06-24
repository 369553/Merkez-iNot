package Base;

import Control.IDARE;
import Services.CryptingService;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.TimeZone;

public class DataBase{
    private static DataBase database;
    public ArrayList<Category> categories;
    private ArrayList<Note> notes;
    public ArrayList<GUISeeming> seemings;
    public ArrayList<NoteTag> tags;
    private HashMap<Integer, HashMap> notePageDatas;
    private HashMap<String, Object> DBTypes;
    private User user;
    private static Category ctGeneral;
    ArrayList<Integer> uselessIDsForNote = null;
    ArrayList<Integer> uselessIDsForCategory = null;
    ArrayList<Integer> uselessIDsForNoteTag = null;
    private int IDCounterForNote = 0;
    private int IDCounterForCategory = 0;
    private int IDCounterForNoteTag = 0;
    private SysInfo sys;
    MainDB DB = null;

    private DataBase(SysInfo sys, MainDB DB, User user){
        this.sys = sys;
        IDCounterForCategory = sys.getIDCounterForCategory();
        IDCounterForNote = sys.getIDCounterForNote();
        IDCounterForNoteTag = sys.getIDCounterForNoteTag();
        this.DB = DB;
        this.user = user;
        /*
        GEREK YOK:
        
        if(user != null)
            DB.saveUserInfo(user);
        DB.saveSysInfo(sys);
        */
    }
    private DataBase(SysInfo sys, User user){
        this.sys = sys;
        IDCounterForCategory = sys.getIDCounterForCategory();
        IDCounterForNote = sys.getIDCounterForNote();
        this.user = user;
//        getCategories().add(getCtGeneral());
    }

//ÇALIŞTIRMA YÖNTEMİ:
    public static boolean runDatabase(SysInfo sys, MainDB DB, User user, String code){
        if(code == null)
            return false;
        if(code.isEmpty())
            return false;
        if(sys == null)
            return false;
        if(CryptingService.getMd5(code).equals(IDARE.getMd5Code())){
            database = new DataBase(sys, DB, user);
            return true;
        }
        return false;
    }
    public static boolean runDatabase(SysInfo sys, String code){
        if(code == null)
            return false;
        if(code.isEmpty())
            return false;
        if(sys == null)
            return false;
        if(CryptingService.getMd5(code).equals(IDARE.getMd5Code())){
            database = new DataBase(sys, new User(0, "Ziyâretçi", "", SystemSettings.getSettings()));
           // database.getHatırNotDBInstance();
            return true;
        }
        return false;
    }

//ERİŞİM YÖNTEMLERİ:
    //ANA ERİŞİM YÖNTEMİ:
    public static DataBase getDatabase (){
        return database ;
    }
    public MainDB getMainDB(){
        return DB;
    }
    public Category getCtGeneral(){
        if(ctGeneral == null){
            ctGeneral = new Category(0, "HÂtırNOTGenel", "#233455");
        }
        return ctGeneral;
    }
    public User getUserGeneral(){
        if(user == null){
            user = new User(0, "Ziyâretçi", "", SystemSettings.getSettings());
        }
        return user;
    }
    public ArrayList<Integer> getUselessIDsForNote(){
        if(uselessIDsForNote == null){
            uselessIDsForNote = new ArrayList<Integer>();
        }
        return uselessIDsForNote;
    }
    public ArrayList<Integer> getUselessIDsForCategory(){
        if(uselessIDsForCategory == null){
            uselessIDsForCategory = new ArrayList<Integer>();
        }
        return uselessIDsForCategory;
    }
    public ArrayList<Integer> getUselessIDsForNoteTag(){
        if(uselessIDsForNoteTag == null){
            uselessIDsForNoteTag = new ArrayList<Integer>();
        }
        return uselessIDsForNoteTag;
    }

//YEREL VERİTABANINDA İŞLEMLER: EKLEME - SİLME - GÜNCELLEME:
    public boolean addNote(Note note, String code){
        if(note == null){
            //Ekleme hatası = Boş veri
            return false;
        }
        if(CryptingService.getMd5(code).equals(IDARE.getMd5Code())){
            getNotes().add(note);
            note.getCategory().addANote(note.getID());
            if(DB != null){
                DB.saveNote(note);
                DB.updateSysInfo(sys);
            }
            return true;
        }
        return false;
    }
    public boolean addCategory(Category category, String code){
        if(category == null){
            //Ekleme hatâsı = Boş veri
            return false;
        }
        if(CryptingService.getMd5(code).equals(IDARE.getMd5Code())){
            if(DB != null){
                DB.saveCategory(category);
            }
            if(category.getNumberOfNotes() != 0){
                for(int index = 0; index < category.getNumberOfNotes(); index++){
                    int noteID = category.getNoteIDs().get(index);
                    if(findNoteByID(noteID) != null){
                        //Çakışma yönetilmeli; yeni eklenene göre mi düzenlensin,eski dosya mı kalsın?
                        boolean boolChange = true ;//= .;.
                        if(boolChange){
                            findNoteByID(noteID).getCategory().removeANote(noteID);
                            findNoteByID(noteID).changeCategory(category);
                            if(DB != null){
                                if(category != DataBase.getDatabase().getCtGeneral())
                                    DB.updateCategory(category);
                            }
                        }
                    }
                }
            }
            if(DB != null){
                DB.updateSysInfo(sys);
                DB.updateCategory(category);
            }
            getCategories().add(category);
            return true;
        }
        return false;
    }
    public boolean addTag(NoteTag tag, String code, boolean isFromBackup){
        if(tag == null){
            //Ekleme hatâsı = Boş veri
            return false;
        }
        if(CryptingService.getMd5(code).equals(IDARE.getMd5Code())){
            getNoteTags().add(tag);
            if(DB != null){
                if(!isFromBackup)
                    DB.saveNoteTag(tag);
                else
                    DB.updateNoteTag(tag);
            }
            addTagIDToNotesOfTag(tag, true);
            if(DB != null){
                DB.updateNoteTag(tag);
            }
            return true;
        }
        return false;
    }
    public boolean addTag(NoteTag tag, String code){
        return addTag(tag, code, true);
    }
    public boolean addUser(User user, String code){
        if(user == null){
            //Ekleme hatâsı = Boş veri
            return false;
        }
        if(CryptingService.getMd5(code).equals(IDARE.getMd5Code())){
            return false;   
        }
        return false;
    }
    public boolean addGUISeeming(GUISeeming guiOrder, String code){
        if(guiOrder == null){
            //Ekleme hatâsı = Boş veri
            return false;
        }
        if(CryptingService.getMd5(code).equals(IDARE.getMd5Code())){
            getGUISeemings().add(guiOrder);
            return true;
        }
        return false;
    }
    public boolean addDBType(String DBTypeName, Object DBClass, String code){
        if(DBTypeName == null)
            return false;
        if(DBTypeName.isEmpty())
            return false;
        if(DBClass == null)
            return false;
        if(CryptingService.getMd5(code).equals(IDARE.getMd5Code())){
            DBTypes.put(DBTypeName, DBClass);
            return true;
        }
        return false;
    }
    public boolean deleteNote(int noteID, String code){
        Note note = findNoteByID(noteID);
        if(note == null){
            //Silme hatâsı = Silmek istenen veri yok
            return false;
        }
        if(CryptingService.getMd5(code).equals(IDARE.getMd5Code())){
            if(!note.getTags().isEmpty())
                note.removeAllTags();
            note.getCategory().removeANote(note.ID);
            getDatabase().getNotes().remove(note);
            if(DB != null){
                DB.deleteNote(note);
            }
            note = null;
            getUselessIDsForNote().add(noteID);
            return true;
        }
        return false;
    }
    public boolean deleteCategory(int categoryID, String code){
        Category ctDeleteCategory = getDatabase().findCategoryByID(categoryID);
        if(ctDeleteCategory == null){
            //Silme hatâsı = Silmek istenen veri yok
            return false;
        }
        if(CryptingService.getMd5(code).equals(IDARE.getMd5Code())){
            if(ctDeleteCategory == getDatabase().getCtGeneral())
                return false;
            if(ctDeleteCategory.getNumberOfNotes() > 0){
                for(int index = 0; index < ctDeleteCategory.getNumberOfNotes(); index++){
                    int id = ctDeleteCategory.getNoteIDs().get(0);
                    deleteNote(ctDeleteCategory.getNoteIDs().get(0), code);
                }
            }
            getCategories().remove(ctDeleteCategory);
            if(DB != null){
                DB.deleteCategory(ctDeleteCategory);
            }
            ctDeleteCategory = null;
            getUselessIDsForNote().add(categoryID);
            return true;
        }
        return false;
    }
    public boolean deleteNoteTag(String noteTagName, String code){
        NoteTag tag = getNoteTagByName(noteTagName);
        if(tag == null)
            return false;
        if(CryptingService.getMd5(code).equals(IDARE.getMd5Code())){
            while(!tag.getNoteIDs().isEmpty()){
                findNoteByID(tag.getNoteIDs().get(0)).removeTag(tag);
            }
            getDatabase().getNoteTags().remove(tag);
            if(DB != null){
                DB.deleteNoteTag(tag);
            }
            tag = null;
            return true;
        }
        return false;
    }
    public boolean removeDBType(String DBTypeName, String code){
        if(DBTypeName == null)
            return false;
        if(DBTypeName.isEmpty())
            return false;
        if(CryptingService.getMd5(code).equals(IDARE.getMd5Code())){
            for(String DBName : getDBTypes().keySet()){
                if(DBName.equals(DBTypeName)){
                    getDBTypes().remove(DBName);
                    return true;
                }
            }
        }
        return false;
    }
    /*public boolean deleteUser(int userID){
        //Kullanıcı veriler silinsin mi? Dilerseniz verileriniz anonim kullanıcıya geçirilebilir, dilerseniz verilerinizi dosya olarak kaydedebilirsiniz bi iznillâh
        //Kullanıcıya âit tüm bilgiler silinmeli
        //Sistem ayarları varsayılana döndürülmeli
        //Tüm görsel arayüz yenilenmeli ve başlangıçtaki ekrana dönülmeli (eğer orada değilse)
        return true;
    }*/
    
    //****YEREL VERİTABANINDAN VERİ ÇEKME YÖNTEMLERİ****\\
    //****TOPTAN VERİ ÇEKME****\\
    public ArrayList<Category> getCategories(){
        if(categories == null){
            categories = new ArrayList<Category>();
//ANA VERİTABANINDAN            SystemDB.getDatabase().getDBCategories();
        }
        return categories;
    }
    public ArrayList<Note> getNotes(){
        if(notes == null){
            notes = new ArrayList<Note>();
        }
        return notes;
    }
    public User getUser(){
        if(user == null){
//ANA VERİTABANINDAN                        getDBUser();
        }
        return user;
    }
    public ArrayList<GUISeeming> getGUISeemings(){
        if(seemings == null){
//ANA VERİTABANINDAN                        getDBThemes();
        }
        return seemings;
    }
    public ArrayList<NoteTag> getNoteTags(){
        if(tags == null){
            tags = new ArrayList<NoteTag>();
        }
        return tags;
    }
    public HashMap<Integer, HashMap> getNotePageDatas() {
        if(notePageDatas == null){
            notePageDatas = new HashMap<Integer, HashMap>();
        }
        return notePageDatas;
    }
    public void setNotePageDatas(HashMap<Integer, HashMap> notePageDatas) {
        this.notePageDatas = notePageDatas;
    }
    public SystemSettings getSettings(){
        return user.getPersonalSettings();
    }
    public HashMap<String, Object> getDBTypes(){
        if(DBTypes == null){
            DBTypes = new HashMap<String, Object>();
            DBTypes.put("mysql", MySqlDB.class);
            DBTypes.put("hatır", HatirDB.class);
        }
        return DBTypes;
    }

    //İSİM VERİLERİNİ ÇEKME:
    public String[] getCategoryNames(){
 /*ANA VERİTABANINDAN                   String[] names = new String[getDBCategories().size()];
        for(int index = 0; index < names.length; index++){
        names[index] = getDBCategories().get(index).name;
        }*/
        String[] names = new String[getCategories().size()];
        for(int index = 0; index < getCategories().size(); index++){
            names[index] = getCategories().get(index).getName();
        }
        return names;
    }
    public String[] getNoteNames(){
        if(getNotes().isEmpty())
            return new String[]{};
        String[] strNoteNames = new String[getNotes().size()];
        for(int index = 0; index < strNoteNames.length; index++){
            strNoteNames[index] = getNotes().get(index).getName();
        }
        return strNoteNames;
    }
    public String[][] getNoteNamesWithCategoryNames(){
        if(getNotes().isEmpty())
            return new String[][]{};
        String[][] values = new String[getDatabase().getNoteIDs().length][2];
        for(int index = 0; index < values.length; index++){
            values[index][0] = getDatabase().getNotes().get(index).getName();
            values[index][1] = getDatabase().getNotes().get(index).getCategory().getName();
//            values[index][0] = getDatabase().findNoteByID(getDatabase().getNoteIDs()[index]).getName();
//            values[index][1] = getDatabase().findNoteByID(getDatabase().getNoteIDs()[index]).getCategory().getName();
        }
        return values;
    }
    public String[] getNoteTagNames(){
        if(getNoteTags().isEmpty())
            return null;
        String[] strTagNames = new String[getNoteTags().size()];
        for(int index = 0; index < strTagNames.length; index++){
            strTagNames[index] = getNoteTags().get(index).getName();
        }
        return strTagNames;
    }
    public String getUserName(){
        return getUser().getName();
    }

    //ETİKET NUMARASI VERİLERİNİ ÇEKME:
    public int[] getNoteIDs(){
        if(getNotes().isEmpty())
            return new int[]{};
        int[] noteIDs = new int[getNotes().size()];
        for(int index = 0; index < noteIDs.length; index++){
            noteIDs[index] = getNotes().get(index).getID();
        }
        return noteIDs;
    }
    public int[] getCategoryIDs(){
        int[] categoryIDs = new int[getCategories().size()];
        for(int index = 0; index < categoryIDs.length; index++){
            categoryIDs[index] = getCategories().get(index).getID();
        }
        return categoryIDs;
    }
    protected int getUserID(){
        return getUser().getID();
    }

//****SORGULAYARAK VERİ ÇEKME****\\
    //ETİKET NUMARASIYLA SORGULAMA:
    public Category findCategoryByID(int id){
        for(Category category : getCategories()){
            if(category.getID() == id){
                return category;
            }
        }
        return null;
    }
    public Note findNoteByID(int id){
        for(Note note : getNotes()){
            if(note.getID() == id)
                return note;
        }
        return null;
    }
    public NoteTag findNoteTagByID(int id){
        for(NoteTag tag : getNoteTags()){
            if(tag.getID() == id)
                return tag;
        }
        return null;
    }
    public User findUserByID(int id){
        if(user.getID() == id)
            return user;
        return null;
    }

    //İSİMLE SORGULAMA:
    public Note getNoteByName(String noteName, String categoryName){
        ArrayList<Note> value = getNotesByName(noteName, categoryName);
        if(value != null)
            return value.get(0);
        return null;
    }
    public ArrayList<Note> getNoteByName(String noteName){
        return getNotesByName(noteName, null);
    }
    private ArrayList<Note> getNotesByName(String noteName, String secondParameter){
        ArrayList <Note> noteList = null;
        for(Note note : getNotes()){
            if(note.getName().equals(noteName)){
                if(secondParameter != null){
                    if(note.getCategory().getName().equals(secondParameter)){
                        noteList = new ArrayList<Note>();
                        noteList.add(note);
                        return noteList;
                    }
                }
                else{
                    if(noteList == null)
                        noteList = new ArrayList<Note>();
                    noteList.add(note);
                }
            }
        }
        return noteList;
    }
    public Category getCategoryByName(String categoryName){
        for(Category category : getCategories()){
            if(category.getName().equals(categoryName)){
                return category;
            }
        }
        return null;
    }
    public GUISeeming getGUISeemingByName(String guiOrderName){
        for(GUISeeming guiOrder : seemings){
            if(guiOrder.getGuiSeemingName().equals(guiOrderName))
                return guiOrder;
        }
        return null;
    }
    public NoteTag getNoteTagByName(String tagName){
        for(NoteTag tag : getNoteTags()){
            if(tag.getName().equals(tagName))
                return tag;
        }
        return null;
    }
    public String[] getNoteNames(String categoryName){
        Category ct = getCategoryByName(categoryName);
        if(ct != null){
            if(ct.getNotes() == null)
                return null;
            String[] names = new String[ct.getNotes().size()];
            for(int index = 0; index < names.length; index++){
                names[index] = ct.getNotes().get(index).getName();
            }
            return names;
        }
        return null;
    }
    public HashMap<String, Integer> getNotePageData(int noteID){
        Note note = findNoteByID(noteID);
        if(note == null)
            return null;
        HashMap<String, Integer> data = getDatabase().getNotePageDatas().get(note.ID);
        return data;
    }
    public String[] getNoteTagNamesOfNote(int noteID){
        Note note = getDatabase().findNoteByID(noteID);
        if(note == null)
            return null;
        if(note.getTags().isEmpty())
            return new String[0];
        String[] strTagNames = new String[note.getTags().size()];
        for(int index = 0; index < strTagNames.length; index++){
            strTagNames[index] = note.getTags().get(index).getName();
        }
        return strTagNames;
    }
    public String[] getNoteTagsOfCategory(int categoryID){
        Category ct = findCategoryByID(categoryID);
        if(ct == null)
            return null;
        ArrayList<String> arlistr = new ArrayList<String>();
        ArrayList<String> arliAdded = new ArrayList<String>();
        boolean notAdded = true;
        for(int index = 0; index < ct.getNumberOfNotes(); index++){
            String[] tagsOfCurrentNotes = getDatabase().getNoteTagNamesOfNote(ct.getNoteIDs().get(index));
            if(tagsOfCurrentNotes.length > 0){
                for(String str : tagsOfCurrentNotes){
                    for(String temp : arliAdded){
                        if(str == temp)
                            notAdded = false;
                    }
                    if(notAdded){
                        arlistr.add(str);
                        arliAdded.add(str);
                    }
                    notAdded = true;
                }
            }
            
        }
        String[] tagsOfCategory = new String[arlistr.size()];
        tagsOfCategory = arlistr.toArray(tagsOfCategory);
        return tagsOfCategory;
    }

//DİĞER YÖNTEMLER:
    /*public static void resetDB(String code){
        if(CryptingService.getMd5(code).equals(IDARE.getMd5Code())){
            System.out.println("Doğru kod");
            database = null;
        }
    }*/
    public SysInfo getSysInfo(String code){
        if(CryptingService.getMd5(code).equals(IDARE.getMd5Code())){
            return this.sys;
        }
        return null;
    }
    public synchronized int getNewIDForNote(){
        if(getUselessIDsForNote().isEmpty()){
            IDCounterForNote++;
            sys.setIDCounterForNote(IDCounterForNote);
            return IDCounterForNote - 1;
        }
        int value = getUselessIDsForNote().get(0);
        getUselessIDsForNote().remove(0);
        return value;
    }
    public synchronized int getNewIDForCategory(){
        if(getUselessIDsForCategory().isEmpty()){
            IDCounterForCategory++;
            sys.setIDCounterForCategory(IDCounterForCategory);
            return IDCounterForCategory - 1;
        }
        int value = getUselessIDsForCategory().get(0);
        getUselessIDsForCategory().remove(0);
        return value;
    }
    public synchronized int getNewIDForNoteTag(){
        if(getUselessIDsForNoteTag().isEmpty()){
            IDCounterForNoteTag++;
            sys.setIDCounterForNoteTag(IDCounterForNoteTag);
            return IDCounterForNoteTag - 1;
        }
        int value = getUselessIDsForCategory().get(0);
        getUselessIDsForCategory().remove(0);
        return value;
    }
    public boolean saveAll(String code){
        if(!CryptingService.getMd5(code).equals(IDARE.getMd5Code()))
            return false;
        if(DB == null)
            return true;
        if(DB.updateUserInfo(user))
            return DB.updateSysInfo(sys);
        return false;
    }

//YEDEKTEN DÖNEBİLMEK İÇİN GÜVENLİK DOĞRULAMALI YÖNTEMLER:
    private void setUselessIDsForCategory(ArrayList<Integer> uselessIDsForCategory, String code) {
        if(CryptingService.getMd5(code).equals(IDARE.getMd5Code()))
            this.uselessIDsForCategory = uselessIDsForCategory;
    }
    private void setUselessIDsForNote(ArrayList<Integer> uselessIDsForNote, String code) {
        if(CryptingService.getMd5(code).equals(IDARE.getMd5Code()))
        this.uselessIDsForNote = uselessIDsForNote;
    }
    public void lastChecks(MainDB DB, String code){
        if(!CryptingService.getMd5(code).equals(IDARE.getMd5Code()))
            return;
        if(!DB.equals(this.DB))
            return;
        this.uselessIDsForCategory = findUselessIDs(this.getCategories(), uselessIDsForCategory);
        this.uselessIDsForNote = findUselessIDs(this.getNotes(), uselessIDsForNote);
        this.uselessIDsForNoteTag = findUselessIDs(this.getNoteTags(), uselessIDsForNoteTag);
//        System.out.println("kullanılmayan kategori numara sayısı : " + uselessIDsForCategory.size() +
//                "\nilk kullanılmayan kategori numarası : " + uselessIDsForCategory.get(0));
//        System.out.println("kullanılmayan not numara sayısı : " + uselessIDsForNote.size() +
//                "\n ilk kullanılmayan not numaraso : " + uselessIDsForNote.get(0));
//        System.out.println("kullanılmayan etiket numara sayısı : " + uselessIDsForNoteTag.size() +
//                "\n ilk kullanılmayan etiket numarası : " + uselessIDsForNoteTag.get(0));
    }
    public void setUser(User user, String code){
        setUser(user, code, true);
    }
    public void setUser(User user, String code, boolean isNewUser){
        setUser(user, code, isNewUser, false);
    }
    public void setUser(User user, String code, boolean isNewUser, boolean isFromBackup){
        if(CryptingService.getMd5(code).equals(IDARE.getMd5Code())){
            this.user = user;
            SystemSettings.appUserSettings(user.getPersonalSettings());
            if(!isFromBackup)
                if(DB != null){
                    if(isNewUser)
                        DB.saveUserInfo(user);
                    else
                        DB.updateUserInfo(user);
                }
        }
    }
    public void setCategories(ArrayList<Category> categories, String code) {
        if(CryptingService.getMd5(code).equals(IDARE.getMd5Code()))
            this.categories = categories;
    }
    public void setNotes(ArrayList<Note> notes, String code){
        if(CryptingService.getMd5(code).equals(IDARE.getMd5Code())){
//            System.err.println("setNotes- - - - - - - - - - -- KOD DOĞRU!!!!!!!!!!!!!!!!!!");
            this.notes = notes;
            for(Note n : notes){
                System.out.println("not ismi : " + n.getName());
                System.out.println("not numarası : " + n.getID());
            }
        }
    }
    public void setNoteTags(ArrayList<NoteTag> tags, String code){
        if(CryptingService.getMd5(code).equals(IDARE.getMd5Code())){
            this.tags = tags;
            for(NoteTag tag : tags){
                addTagIDToNotesOfTag(tag, false);
            }
        }
    }

//ARKAPLAN İŞLEM YÖNTEMLERİ:
    private void getHatırNotDBInstance(){
        getDatabase().notes = new ArrayList<Note>();
        getDatabase().tags = new ArrayList<NoteTag>();
        getDatabase().notePageDatas = new HashMap<Integer, HashMap>();
        getDatabase().categories = new ArrayList<Category>();
        getDatabase().notePageDatas = new HashMap<Integer, HashMap>();
        Category cat01 = new Category(2, "Yapılacaklar", "Yakın zamânda bunları yap inşâAllâh", 0,
                Calendar.getInstance(TimeZone.getTimeZone("GMT+3:00")).getTime(),
                Calendar.getInstance(TimeZone.getTimeZone("GMT+3:00")).getTime(), "#F24514");
        Category cat02 = new Category(3, "Okul işleri", "Sınavlar yaklaşıyor, bir an önce işleri bitir inşâAllâh", 0,
                Calendar.getInstance(TimeZone.getTimeZone("GMT+3:00")).getTime(),
                Calendar.getInstance(TimeZone.getTimeZone("GMT+3:00")).getTime(), "#235511");
        Category cat03 = new Category(4, "İlgi alanları", "Bunlarla ilgilen inşâAllâh", 0,
                Calendar.getInstance(TimeZone.getTimeZone("GMT+3:00")).getTime(),
                Calendar.getInstance(TimeZone.getTimeZone("GMT+3:00")).getTime(), "#323434");
        
        Note note01 = new Note(1, getDatabase().getUserGeneral(),
                "Pazar alışveri", "Şunları al inşâAllâh:\n3 kg domates\n2kg patlıcan\n1 kg salatalık\n1 kg incir\nEğer soğan uygunsa 3 kg soğan al.",
                cat01, Calendar.getInstance(TimeZone.getTimeZone("GMT+3:00")).getTime(),
                Calendar.getInstance(TimeZone.getTimeZone("GMT+3:00")).getTime());
        Note note02 = new Note(2, getDatabase().getUserGeneral(),
                "Bilgisayar ayarlarında şu değişiklikleri yap", "Şunları yap inşâAllâh:\nUygulamaların mikrofona erişimini sınırlar\nOturuma şifre istemeden girme özelliğini devre dışı bırak",
                cat01, Calendar.getInstance(TimeZone.getTimeZone("GMT+3:00")).getTime(),
                Calendar.getInstance(TimeZone.getTimeZone("GMT+3:00")).getTime());
        Note note03 = new Note(3, getDatabase().getUserGeneral(),
                "Şu kitâpları al inşâAllâh", "Efendimiz'in (Sallâllâhu Aleyhi ve Sellem) hayâtı : Sâlih Suruç Hocaefendi\nFıkhu's-Siyre : Ramazân el-Buti\n",
                cat01, Calendar.getInstance(TimeZone.getTimeZone("GMT+3:00")).getTime(),
                Calendar.getInstance(TimeZone.getTimeZone("GMT+3:00")).getTime());
        
        Note note04 = new Note(4, getDatabase().getUserGeneral(),
                "Defter noksanlıkları", "Şu derslerin defterlerini tamâmla inşâAllâh:\nYazılım tasarım ve mimârisi\nBilgi güvenliği ve kriptoloji\nAyrık matematik\nBiçimsel diller ve otomata",
                cat02, Calendar.getInstance(TimeZone.getTimeZone("GMT+3:00")).getTime(),
                Calendar.getInstance(TimeZone.getTimeZone("GMT+3:00")).getTime());
        
        Note note05 = new Note(5, getDatabase().getUserGeneral(),
                "DoguTurkistan", "Doğu Türkistan ile ilgili şunları yap inşâAllâh:\nHüküm dergisi özel sayısını oku,\nİhsan Şenocak Hocamın kitâbını oku,\nTürk Yurdundaki yazıyı oku,\nHaberleri oku,\n\n\nÖNEMLİ : Okurken notlar al, yeni isimleri, yerleri, yayınları da not al; Onlar üzerinden araştırma yapmaya devâm et!",
                cat03, Calendar.getInstance(TimeZone.getTimeZone("GMT+3:00")).getTime(),
                Calendar.getInstance(TimeZone.getTimeZone("GMT+3:00")).getTime());
        Note note06 = new Note(6, getDatabase().getUserGeneral(),
                "Helâl Gıda", "Helâl gıdayla ilgili şunları yap inşâAllâh:\nGİMDES'in sitesindeki videoları not alarak izle,\nİnternetteki gıda raporu sitesindeki ve buna benzer güvenilir yerlerdeki yayınları, makâleleri oku,\nAldığın kitâbları oku,\n\nDaha sonra bu konuda neler yapabiliriz, düşün, tasarla ve yap inşâAllâh!",
                cat03, Calendar.getInstance(TimeZone.getTimeZone("GMT+3:00")).getTime(),
                Calendar.getInstance(TimeZone.getTimeZone("GMT+3:00")).getTime());
        
        NoteTag tag01 = new NoteTag("DoguTurkistan");
        NoteTag tag02 = new NoteTag("Yazılım");
        NoteTag tag03 = new NoteTag("Helâl gıdâ");
        NoteTag tag04 = new NoteTag("Kitâb");
        
        HashMap<String, Integer> notePageData01 = new HashMap<String, Integer>();
        HashMap<String, Integer> notePageData02 = new HashMap<String, Integer>();
        HashMap<String, Integer> notePageData03 = new HashMap<String, Integer>();
        HashMap<String, Integer> notePageData04 = new HashMap<String, Integer>();
        HashMap<String, Integer> notePageData05 = new HashMap<String, Integer>();
        
        
        notePageData01.put("caretPosition", 5);
        notePageData01.put("textSize", 16);
        
        notePageData02.put("caretPosition", 5);
        notePageData02.put("textSize", 17);
        
        notePageData03.put("caretPosition", 5);
        notePageData04.put("textSize", 18);
        
        notePageData04.put("caretPosition", 5);
        notePageData04.put("textSize", 19);
        
        notePageData05.put("caretPosition", 5);
        notePageData05.put("textSize", 20);
        //EKLEMELER:
        //Notlara bulunduğu kategori bilgisi aktarıldı.
        //Kategorilere içerisindeki notID bilgilerini aktarmak lazım:
        notePageDatas.put(note01.ID, notePageData01);
        notePageDatas.put(note02.ID, notePageData02);
        notePageDatas.put(note03.ID, notePageData03);
        notePageDatas.put(note04.ID, notePageData04);
        notePageDatas.put(note05.ID, notePageData05);
        
        getDatabase().notes.add(note01);
        getDatabase().notes.add(note02);
        getDatabase().notes.add(note03);
        getDatabase().notes.add(note04);
        getDatabase().notes.add(note05);
        getDatabase().notes.add(note06);
        
        getDatabase().categories.add(cat01);
        getDatabase().categories.add(cat02);
        getDatabase().categories.add(cat03);
        
        getDatabase().tags.add(tag01);
        getDatabase().tags.add(tag02);
        getDatabase().tags.add(tag03);
        getDatabase().tags.add(tag04);
        
        cat01.addANote(note01.getID());
        cat01.addANote(note02.getID());
        cat01.addANote(note03.getID());
        
        cat02.addANote(note04.getID());
        
        cat03.addANote(note05.getID());
        cat03.addANote(note06.getID());
        
        note05.addTag(tag01);
        note03.addTag(tag04);
        note06.addTag(tag03);
        note04.addTag(tag02);
        
        IDCounterForNote = 7;
        IDCounterForCategory = 5;
    }
    private void findUselessIDsForCategory(){//Yedekten dönerken gerekiyor
        int max = 0, s1 = 0;
        int[] IDs = new int[getCategories().size()];
        for(Category ct : getCategories()){
            if(ct.getID() > max)
                max = ct.getID();
            IDs[s1] = ct.getID();
            s1++;
        }
        if(getCategories().size() == max)
            return;
        ArrayList<Integer> useless = new ArrayList<Integer>();
        boolean isFinded = false;
        for(int sayac = 0; sayac < max; sayac++){
            isFinded = false;
            for(int s2 = 0; s2 < getCategories().size(); s2++){
                if(IDs[s2] == sayac){
                    isFinded = true;
                    break;
                }
            }
            if(!isFinded)
                useless.add(sayac);
        }
        uselessIDsForCategory = useless;
    }
    private void findUselessIDsForNote(){//Yedekten dönerken gerekiyor
       int max = 0, s1 = 0;
        int[] IDs = new int[getNotes().size()];
        for(Note note : getNotes()){
            if(note.getID() > max)
                max = note.getID();
            IDs[s1] = note.getID();
            s1++;
        }
        if(getNotes().size() == max)
            return;
        ArrayList<Integer> useless = new ArrayList<Integer>();
        boolean isFinded = false;
        for(int sayac = 0; sayac < max; sayac++){
            isFinded = false;
            for(int s2 = 0; s2 < getNotes().size(); s2++){
                if(IDs[s2] == sayac){
                    isFinded = true;
                    break;
                }
            }
            if(!isFinded)
                useless.add(sayac);
        }
        uselessIDsForNote = useless;
    }
    private void findUselessIDsForNoteTag(){//Yedekten dönerken gerekiyor
       int max = 0, s1 = 0;
        int[] IDs = new int[getNoteTags().size()];
        for(NoteTag tag : getNoteTags()){
            if(tag.getID() > max)
                max = tag.getID();
            IDs[s1] = tag.getID();
            s1++;
        }
        if(getNoteTags().size() == max)
            return;
        ArrayList<Integer> useless = new ArrayList<Integer>();
        boolean isFinded = false;
        for(int sayac = 0; sayac < max; sayac++){
            isFinded = false;
            for(int s2 = 0; s2 < getNoteTags().size(); s2++){
                if(IDs[s2] == sayac){
                    isFinded = true;
                    break;
                }
            }
            if(!isFinded)
                useless.add(sayac);
        }
        uselessIDsForNoteTag = useless;
    }
    private ArrayList<Integer> findUselessIDs(ArrayList sourceList, ArrayList<Integer> targetList){//Yedekten dönerken gerekiyor
        int max = 0, sayac = 0;
        int[] IDs = new int[sourceList.size()];
        ArrayList<IEntity> converted = (ArrayList<IEntity>) sourceList;
        for(IEntity entity : converted){
            if(entity.getID() > max)
                max = entity.getID();
            IDs[sayac] = entity.getID();
            sayac++;
        }
        if(getCategories().size() == max)
            return null;
        ArrayList<Integer> useless = new ArrayList<Integer>();
        boolean isFinded = false;
        for(int sayac2 = 1; sayac2 < max; sayac2++){
            isFinded = false;
            for(int s3 = 0; s3 < sourceList.size(); s3++){
                if(IDs[s3] == sayac2){
                    isFinded = true;
                    break;
                }
            }
            if(!isFinded)
                useless.add(sayac2);
        }
        return useless;
    }
    private void addTagIDToNotesOfTag(NoteTag tag, boolean updateDB){
        if(tag.getNumberOfNotes() > 0){
            for(int index = 0; index < tag.getNumberOfNotes(); index++){
                Note note = getDatabase().findNoteByID(tag.getNoteIDs().get(index));
                if(note != null){
                    note.addTag(tag);
                    if(updateDB)
                        if(DB != null){
                            DB.updateNote(note);
                        }
                }
            }
        }
    }
}