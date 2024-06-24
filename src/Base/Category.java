package Base;
//Kategori oluşturma aşamasında tasarım desenlerinden builder ı kullan.
import Control.IDARE;
import Services.CryptingService;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
//Date sınıfı kullanımını öğren, klasör son değiştirme tarihi içerisindeki en son notun değiştirilme tarihi oalrak yapılabilir, düşün

public class Category implements IEntity{//number_of_notes
    protected int ID;
    protected String name;
    protected String explanation; 
    int numberOfNotes = 0;
    protected Date lastChangeTime;
    protected Date produceTime;
    protected String categoryColor;
    ArrayList<Integer> noteIDs;

//YAPICI YÖNTEMLER:
    public Category (String name, String categoryColorHexCode){
        this.name = name;
        this.ID = DataBase.getDatabase().getNewIDForCategory();
        this.categoryColor = categoryColorHexCode;
        getProduce_time();
        getLast_change_time();
    }
    public Category (String name, String categoryColorHexCode, String explanation){
        this(name, categoryColorHexCode);
        this.explanation = explanation;
    }
    public Category (int ID, String name, String categoryColorHexCode){
        this.ID = ID;
        this.name = name;
        if(!checkHexCode(categoryColorHexCode))
            categoryColor = "#F5F412";
        else
            this.categoryColor = categoryColorHexCode;
    }
    public Category(int ID, String name, String explanation, int number_of_notes, Date produce_time, Date last_change_time, String categoryColor) {
        this.ID = ID;
        this.name = name;
        this.explanation = explanation;
        this.numberOfNotes = number_of_notes;
        this.lastChangeTime = last_change_time;
        this.produceTime = produce_time;
        this.categoryColor = categoryColor;
    }
    public Category(int ID, String name, String explanation, Date produce_time, Date last_change_time, String categoryColor){
        this(ID, name, explanation, 0, produce_time, last_change_time, categoryColor);
    }
    public Category(int ID, String name, String explanation, Date produce_time, Date last_change_time, String categoryColor, ArrayList<Integer> noteIDs) {
        this(ID, name, explanation, noteIDs.size(), produce_time, last_change_time, categoryColor);
        this.noteIDs = noteIDs;
    }

//İŞLEM YÖNTEMLERİ:
    public boolean checkHexCode(String text){
        if(text.isEmpty())
            return false;
        //.;.
        return true;
    }
    
    public void renameCategory(String name){
        if(name.isEmpty())
           return;
        setName(name);
        updateCategoryInfo();
    }
    
    public void changeExplanation(String text){
        if(getExplanation().equals(text))
            return;
        setExplanation(text);
        updateCategoryInfo();
    }
    
    public void removeANote(int noteID){  //KATEGORİDEN BİR NOT SİL
        if(getNoteIDs().size() > 0){
            for(int index = 0; index < getNoteIDs().size(); index++){
                if(getNoteIDs().get(index) == noteID){
                    getNoteIDs().remove((Object) getNoteIDs().get(index));
                    DataBase.getDatabase().findNoteByID(noteID).changeCategory(DataBase.getDatabase().getCtGeneral());
                    numberOfNotes --;
                    updateCategoryInfo();
                }
            }
        }
    }
    public void addANote(int noteID){ //KATEGORİYE BİR NOT EKLE
        for(int index = 0; index < getNoteIDs().size(); index++){
            if(getNoteIDs().get(index) == noteID)
                return;
        }
        getNoteIDs().add(noteID);
    /*    System.out.println("!!!!!!!\n" + this.getName() + " kategorisine " +
                DataBase.getDatabase().findNoteByID(noteID).getName() + " notu eklendi.\nNot numarası : " + noteID);*/
        numberOfNotes++;
        updateCategoryInfo();
    }
    public void changeColor(String hexCodeColor){   //KATEGORİ RENGİNİ DEĞİŞTİR
        if(!getCategoryColor().equals(hexCodeColor)){
            if(isHexCodeColor(hexCodeColor))
                setCategoryColor(hexCodeColor);
                updateCategoryInfo();
        }
    }
    private void updateCategoryInfo(){
//!        ZAMÂNI ŞİMDİKİ ZAMÂN YAP;
        setLast_change_time(Calendar.getInstance(TimeZone.getTimeZone("GMT+3:00")).getTime());
        if(DataBase.getDatabase().getMainDB() != null){
            if(this != DataBase.getDatabase().getCtGeneral())
                DataBase.getDatabase().getMainDB().updateCategory(this);
        }
        //ŞU AN TEK KULLANICI DESTEKLENİYOR; O YÜZDEN DEĞİŞİKLİĞİ YAPAN BİLGİSİ İSTENMİYOR
    }
    protected boolean addANoteFromDB(MainDB DB, String code, int noteID){
        if(!CryptingService.getMd5(code).equals(IDARE.getMd5Code()))
            return false;
        if(DB == null)
            return false;
        boolean res;
        res = getNoteIDs().add(noteID);
        if(res)
            numberOfNotes++;
        return res;
    }
    public boolean isHexCodeColor(String text){
        return Services.ColorProcess.getColorService().isHexCode(text);
    }
    public ArrayList<Note> getNotes(){
        ArrayList<Note> notes = new ArrayList<Note>();
        if(getNoteIDs() != null){
            int[] arrNoteIDs = new int[getNoteIDs().size()];
            for(int index = 0; index < getNoteIDs().size(); index++){
                arrNoteIDs[index] = getNoteIDs().get(index);
            }
            for(int index : arrNoteIDs){
                notes.add(DataBase.getDatabase().findNoteByID(index));
            }
        }
        return notes;
    }
    public void writeInfo(){
        System.out.println("Kategori: " + name);
        System.out.println("\tNumara : " + ID);
        System.out.println("\tAçıklama : " + explanation);
        System.out.println("\tRenk : " + categoryColor);
        System.out.println("\tNot sayısı : " + numberOfNotes);
        System.out.println("\tNot isimleri: ");
        for(int index : getNoteIDs()){
            System.out.println("\t\tNot ismi : " + DataBase.getDatabase().findNoteByID(index).name);
        }
    }

//ERİŞİM YÖNTEMLERİ:
    public String getName() {
        return name;
    }
    private void setName(String name) {
        this.name = name;
    }
    public String getExplanation() {
        if(explanation == null){
            explanation = new String("");
        }
        return explanation;
    }
    private void setExplanation(String explanation) {
        this.explanation = explanation;
    }
    public int getNumberOfNotes() {
        return numberOfNotes;
    }
    private void setNumberOfNotes(int numberOfNotes) {
        this.numberOfNotes = numberOfNotes;
    }
    /*public Theme getTheme() {
        return theme;
    }
    public void setTheme(Theme theme) {
        this.theme = theme;
    }*/
    public Date getLast_change_time() {
        if(lastChangeTime == null){
            lastChangeTime = getProduce_time();
        }
        return lastChangeTime;
    }
    private void setLast_change_time(Date last_change_time) {
        this.lastChangeTime = last_change_time;
    }
    public Date getProduce_time() {
        if(produceTime == null){
            produceTime = Calendar.getInstance(TimeZone.getTimeZone("GMT+3:00")).getTime();
        }
        return produceTime;
    }
    private void setProduce_time(Date produce_time) {
        this.produceTime = produce_time;
    }
    public String getCategoryColor() {
        if(categoryColor == null){
            categoryColor = "#F5F412";
        }
        return categoryColor;
    }
    private void setCategoryColor(String strCategoryColor) {
        this.categoryColor = strCategoryColor;
    }
    public int getID() {
        return ID;
    }
    private void setID(int ID) {
        this.ID = ID;
    }
    public ArrayList<Integer> getNoteIDs() {
        if(noteIDs == null){
            noteIDs = new ArrayList<Integer>();
        }
        return noteIDs;
    }
    public void setNoteIDs(ArrayList<Integer> noteIDs) {
        this.noteIDs = noteIDs;
    }
}