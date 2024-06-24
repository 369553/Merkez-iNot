package Base;

import java.util.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;
//Sonraki aşamalarda tag (etiket) eklenebilir, yapılacak not, metin notu gibi tasnifler de yapılabilir.
public class Note implements IEntity{
    protected int ID;
    protected String name;
    protected String malumat;
    protected Date lastChangeTime;
    protected Date produceTime;
    protected Category category;
    protected ArrayList<NoteTag> tags;
    protected User user;
    
//YAPICI YÖNTEMLER:
    public Note (User user, String name, String malumat){
        this.name = name;
        this.malumat = malumat;
        this.user = user;
        this.category = DataBase.getDatabase().getCtGeneral();
        this.ID = DataBase.getDatabase().getNewIDForNote();
        getProduce_time();
        getLast_change_time();
        //this.id = requestid();
    }
    
    public Note(User user, String name, String malumat, Category category){
        this(user, name, malumat);
//        System.out.println("Gelen bilgiler:\nKullanıcı" +user.getName() + "\nmalûmât : " + malumat + "kategori : " + category.getName());
        this.category = category;
    }
    
    public Note(int ID, User user, String name, String malumat, Date produce_time, Date last_change_time){
        this.ID = ID;
        this.name = name;
        this.malumat = malumat;
        this.user = user;
        this.produceTime = produce_time;
        this.lastChangeTime = last_change_time;
        this.ID = ID;
    }
    
    public Note(int ID, User user, String name, String malumat, Category category, Date produce_time, Date last_change_time){
        this(ID, user, name, malumat, produce_time, last_change_time);
        this.category = category;
    }
    
    public Note (String name) {
        this.name = name;
        this.malumat = "";
        this.user = DataBase.getDatabase().getUserGeneral();
        this.category = DataBase.getDatabase().getCtGeneral();
        this.ID = DataBase.getDatabase().getNewIDForNote();
    }

//İŞLEM YÖNTEMLERİ:
    public void renameNote(String name){ //İSİM DEĞİŞTİR
        if(name.isEmpty())
            return;
        setName(name);
        updateNoteInfo();
    }
    
    public void changeContent(String text){  //İÇERİĞİ DEĞİŞTİR
        if(getMalumat().length() == text.length()){
            if(getMalumat().equals(text))
                return;
        }
        setMalumat(text);
        updateNoteInfo();
    }
    
    public void changeCategory(Category category){  //KATEGORİYİ DEĞİŞTİR
        if(getCategory().equals(category))
            return;
        getCategory().removeANote(ID);
        setCategory(category);
        getCategory().addANote(ID);
        updateNoteInfo();
    }
    
    public void addTag(NoteTag tag){ //ETİKET EKLE
        for(NoteTag temp : getTags()){
            if(temp.equals(tag))
                return;
        }
        getTags().add(tag);
        tag.addNote(this.ID);
        updateNoteInfo();
    }
    
    public void removeTag(NoteTag tag){ //ETİKET SİL
        boolean delete = false;
        for(NoteTag temp : getTags()){
            if(temp.equals(tag)){
                delete = true;
            }
        }
        getTags().remove(tag);
        tag.removeNote(this.ID);
        updateNoteInfo();
    }
    //NoteTag temp : getTags()
    public void removeAllTags(){
        if(getTags().isEmpty())
            return;
        for(int index = 0; index < getTags().size(); index++){
            removeTag(getTags().get(index));//ID yazmakla this.ID yazmak arasında fark var mı, araştır?
        }
        getTags().clear();
    }
    
    private void updateNoteInfo(){
//!        ZAMÂNI ŞİMDİKİ ZAMÂN YAP;
        setLast_change_time(Calendar.getInstance(TimeZone.getTimeZone("GMT+3:00")).getTime());
        if(DataBase.getDatabase().getMainDB() != null){
            System.out.println("Veritabanı var.");
            DataBase.getDatabase().getMainDB().updateNote(this);
        }
        //ŞU AN TEK KULLANICI DESTEKLENİYOR; O YÜZDEN DEĞİŞİKLİĞİ YAPAN BİLGİSİ İSTENMİYOR
    }
    public void printInfos(){
        System.out.println("NOT BİLGİLERİ : \n"
                + "Not adı : " + name
                + "\nNot numarası : " + ID
                + "\nNotu üreten kullanıcı : " + user.getName()
                + "\nNotun bulunduğu kategori : " + category.getName()
                + "\nNot üzerindeki son değişiklik : " + Services.DateTimeService.getDtService().showDateTimeAsString(lastChangeTime)
                + "\nNot üretim târihi : " + Services.DateTimeService.getDtService().showDateTimeAsString(produceTime));
        for(NoteTag temp: getTags()){
            System.out.println("\nNotta bulunan etiket : " + temp.getName());
        }
    }
    
//ERİŞİM YÖNTEMLERİ:
    public String getName() {
        return name;
    }

    private void setName(String name) {
        this.name = name;
    }

    public String getMalumat() {
        if (malumat == null){
            malumat = new String("");
        }
        return malumat;
    }

    private void setMalumat(String malumat) {
        this.malumat = malumat;
    }

    public Date getLast_change_time() {
        if (lastChangeTime == null){
            lastChangeTime = getProduce_time();
        }
        return lastChangeTime;
    }

    private void setLast_change_time(Date last_change_time) {
        this.lastChangeTime = last_change_time;
    }

    public Date getProduce_time() {
        if (produceTime == null){
            produceTime = Calendar.getInstance(TimeZone.getTimeZone("GMT+3:00")).getTime();
        }
        return produceTime;
    }

    private void setProduce_time(Date produce_time) {
        this.produceTime = produce_time;
    }

    public Category getCategory() {
        if (category == null){
            System.err.println("Bu not herhangi bir kategoride değil");//Kategori kelimesinin Türkçe'sini bul
            return null;//Dönüş tipi başka bir şey olabilir: eğer ana ekrandakileri kategorisiz kategorisi olarak tanımlammışsak
        }
        return category;
    }

    private void setCategory(Category inCategory) {
        this.category = inCategory;
    }

    public ArrayList<NoteTag> getTags() {
        if(tags == null){
           tags = new ArrayList<NoteTag>();
        }
        return tags;
    }

    private void setTags(ArrayList<NoteTag> tags) {
        this.tags = tags;
    }

    public User getUser(){
        return user;
    }

    private void setUser(User user) {
        this.user = user;
    }

    public int getID() {
        return ID;
    }

    private void setID(int ID) {
        this.ID = ID;
    }
}