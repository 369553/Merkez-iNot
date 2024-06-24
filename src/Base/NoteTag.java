package Base;

import java.util.ArrayList;

public class NoteTag implements IEntity{
    int ID;
    String name;
    ArrayList<Integer> noteIDs = null;

    public NoteTag(String name){
        this.name = name;
        this.ID = DataBase.getDatabase().getNewIDForNoteTag();
    }
    public NoteTag(String name, int tagID){
        this.name = name;
        this.ID = tagID;
    }

//ÜRETİCİ YÖNTEM:
    public NoteTag produceNoteTagWithID(String name, int tagID, DataBase dbForIDControl){
        if(dbForIDControl == null)
            return null;
        if(dbForIDControl.findNoteTagByID(tagID) != null)
            return null;
        return new NoteTag(name, tagID);
    }

//İŞLEM YÖNTEMLERİ:
    public boolean renameTag(String name){
        if(DataBase.getDatabase().getNoteTagByName(name) != null)
            return false;
        this.name = name;
        updateNoteTag();
        return true;
    }
    public int getNumberOfNotes(){
        if(getNoteIDs().isEmpty())
            return 0;
        return getNoteIDs().size();
    }
    public boolean addNote(int noteID){
        for(int temp : getNoteIDs()){
            if(temp == noteID)
                return false;
        }
        getNoteIDs().add(noteID);
        updateNoteTag();
        return true;
    }
    public boolean removeNote(int noteID){
        int cycleCount = getNoteIDs().size();
        for(int index = 0; index < cycleCount; index++){
            if(getNoteIDs().get(index) == noteID){
                getNoteIDs().remove(index);
                if(DataBase.getDatabase().findNoteByID(noteID).getTags().contains(this))
                    DataBase.getDatabase().findNoteByID(noteID).getTags().remove(this);
                updateNoteTag();
                return true;
            }
        }
        return false;
    }
    public String[] getNoteNames(){
        if(getNumberOfNotes() <= 0)
            return null;
        String[] names = new String[getNumberOfNotes()];
        for(int index = 0; index < names.length; index++){
            names[index] = DataBase.getDatabase().findNoteByID(getNoteIDs().get(index)).getName();
        }
        return names;
    }
    //ARKAPLAN YÖNTEMLERİ:
    private void updateNoteTag(){
        if(DataBase.getDatabase().getMainDB() != null){
            System.out.println("Burassdı çalıştırıldı mı _?");
            DataBase.getDatabase().getMainDB().updateNoteTag(this);
        }
    }
    private void setName(String name){
        this.name = name;
    }
    private void setNoteIDs(ArrayList<Integer> noteIDs){
        this.noteIDs = noteIDs;
    }

//ERİŞİM YÖNTEMLERİ:
    public String getName(){
        return name;
    }
    @Override
    public int getID(){
        return ID;
    }
    public ArrayList<Integer> getNoteIDs(){
        if(noteIDs == null){
            noteIDs = new ArrayList<Integer>();
        }
        return noteIDs;
    }
}