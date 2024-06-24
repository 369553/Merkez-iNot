package Base;

import java.util.ArrayList;

public class TestClass {
    HatirDB DB;
    String code;
    public TestClass(String code) {
        DB = new HatirDB("C:\\HâtırNOT");
        this.code = code;
        /*saveNote();
        saveCategory();
        saveNotePageData();
        saveNoteTag();
        saveSysInfo();
        saveUserInfo();*/
        //readCategories();
        //readUserInfo();
        //readSysInfo();
       // readNotes();
       readCategories();
       //readAllDB();
    }

//TEST YÖNTEMLERİ:
    public void saveNote(){
        DB.saveNote(DataBase.getDatabase().getNoteByName("Pazar alışveri", "Yapılacaklar"));
        DB.saveNote(DataBase.getDatabase().getNoteByName("Şu kitâpları al inşâAllâh", "Yapılacaklar"));
    }
    public void saveCategory(){
        DB.saveCategory(DataBase.getDatabase().getCategoryByName("Yapılacaklar"));
    }
    public void saveNoteTag(){
        DB.saveNoteTag(DataBase.getDatabase().getNoteTagByName("Yazılım"));
    }
    public void saveUserInfo(){
        DB.saveUserInfo(DataBase.getDatabase().getUserGeneral());
    }
    public void saveNotePageData(){
        DB.saveNoteInfo(1, DataBase.getDatabase().getNotePageData(1));
    }
    public void saveSysInfo(){
        DB.saveSysInfo(SysInfo.produceSysInfo(0, 0, 0, "mysql", code));
    }
    public void deleteNoteTag(){
        boolean result = DB.deleteNoteTag(DataBase.getDatabase().getNoteTagByName("Yazılım"));
        if(result)
            System.err.println("Silme işlemi başarılı");
    }
    public void readCategories(){
        ArrayList<Category> categories = DB.readCategories();
        for(Category ct : categories){
            System.out.println("isim : " + ct.getName());
            System.out.println("açıklama : " + ct.getExplanation());
            System.out.println("renk kodu : " + ct.getCategoryColor());
            System.out.println("üretim târihi : " + ct.getProduce_time());
            System.out.println("son değiştirilme târihi : " + ct.getLast_change_time());
            System.out.println("not sayısı : " + ct.getNumberOfNotes());
        }
    }
    public void readUserInfo(){
        User user = DB.readUserInfo();
        if(user != null){
            System.out.println("kullanıcı ismi : " + user.getName());
            System.out.println("kullanıcı şifresi (md5) : " + user.getPassword());
            System.out.println("kullanıcının tercih ettiği görsel görünüm : " + user.getPersonalSettings().getGuiSeemingName());
            System.out.println("Kullanıcın tercih ettiği kaydetme konumu : " + user.getPersonalSettings().getPATHPosition());
            if(user.getPersonalSettings().getIs_logging())
                System.out.println("'Sistem kaydı tut' seçeneği açık");
            else
                System.out.println("'Sistem kaydı tut' seçeneği kapalı");
            if(user.getPersonalSettings().getIs_autoSaving())
                System.out.println("'Otomatik kaydet' seçeneği açık");
            else
                System.out.println("'Otomatik kaydet' seçeneği kapalı");
            System.out.println("Kullanıcı numarası : " + user.getID());
        }
    }
    public void readSysInfo(){
        SysInfo sys = DB.readSysInfo(code);
        System.out.println("sys IDCounterForNote : " + sys.getIDCounterForNote());
        System.err.println("sys IDCounterForCategory : " + sys.getIDCounterForCategory());
        System.out.println("sys DBSystem : " + sys.getStrDBType());
    }
    public void readNotes(){
        ArrayList<Note> notes = DB.readNotes();
        for(Note note : notes){
            System.out.println("not ismi : " + note.getName());
            System.out.println("not içeriği:\n" + note.getMalumat());
            System.out.println("notun üretim târihi : " + note.getProduce_time());
            System.out.println("notun son değiştirilme târihi : " + note.getLast_change_time());
            System.out.println("notun kategorisinin ismi : " + note.getCategory().getName());
            System.out.println("notu oluşturan kullanıcı : " + note.getUser().getName());
            for(int index = 0; index < note.getTags().size(); index++){
                System.out.println("not etiketi : " + note.getTags().get(index));
            }
            System.out.println("not numarası : " + note.getID());
        }
    }
    public void readAllDB(){
       // DB.installSystem(code);
    }
}
