package Base;

import java.util.ArrayList;
import java.util.HashMap;

public interface MainDB {

//İŞLEM YÖNTEMLERİ:
    public boolean installSystem(String code, DataBase database);
    public boolean backupSystem(String code, String path, boolean isCrypt);
    public boolean checkUserInfo(String code, String userName, String md5Password, User user);
    public boolean setupFirstConnection(String code, DataBase database);
    
    public abstract boolean saveNote(Note note);
    public abstract boolean saveNoteInfo(int noteID, HashMap<String, Integer> notePageData);
    public abstract boolean saveNoteWithNoteInfo(Note note, HashMap<String, Integer> notePageData);
    public abstract boolean saveCategory(Category category);
    public abstract boolean saveNoteTag(NoteTag tag);
//    public abstract boolean saveGUISeeming(GUISeeming seeming);
    public abstract boolean saveUserInfo(User user);
    public abstract boolean saveSysInfo(SysInfo info);
    
    public abstract boolean deleteNote(Note note);
    public abstract boolean deleteNoteInfo(Note note);
    public abstract boolean deleteCategory(Category category) ;
    public abstract boolean deleteNoteTag(NoteTag tag) ;
//    public abstract boolean deleteGUISeeming(GUISeeming seeming);
//ŞU AN DESTEKLENEMEZ :    public abstract boolean deleteUserInfo(User user);
    
    public abstract boolean updateNote(Note note);
    public abstract boolean updateNoteInfo(int noteID, HashMap<String, Integer> notePageData);
    public abstract boolean updateNoteWithNoteInfo(Note note, HashMap<String, Integer> notePageData);
    public abstract boolean updateCategory(Category category);
    public abstract boolean updateNoteTag(NoteTag tag);
//    public abstract boolean updateGUISeeming(GUISeeming seeming);
    public abstract boolean updateUserInfo(User user);
    public abstract boolean updateSysInfo(SysInfo info);
    
    //VERİTABANINDAN DEĞİŞKENLERE:
    public abstract ArrayList<Category> readCategories();
    public abstract ArrayList<Note> readNotes();
    public abstract ArrayList<NoteTag> readNoteTags();
    public abstract SysInfo readSysInfo(String code);
    public abstract User readUserInfo();
    public abstract HashMap<Integer, HashMap> readNotePageData();

//ERİŞİM YÖNTEMLERİ:
    
}