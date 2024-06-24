package Base;

import Control.IDARE;

public class SysInfo{
    private int IDCounterForNote;
    private int IDCounterForCategory;
    private int IDCounterForNoteTag;
    private String strDBType;

    private SysInfo(int IDCounterForNote, int IDCounterForCategory, int IDCounterForNoteTag, String DBType){
        this.strDBType = DBType;
        this.IDCounterForCategory = IDCounterForCategory;
        this.IDCounterForNote = IDCounterForNote;
        this.IDCounterForNoteTag = IDCounterForNoteTag;
    }

//İŞLEM YÖNTEMLERİ:
    public static SysInfo produceSysInfo(int IDCounterForNote, int IDCounterForCategory, int IDCounterForNoteTag, String DBType, String code){
        if(DBType == null)
            return null;
        if(DBType.isEmpty())
            return null;
        if(code == null)
            return null;
        if(Services.CryptingService.getMd5(code).equals(IDARE.getMd5Code())){
            return new SysInfo(IDCounterForNote, IDCounterForCategory, IDCounterForNoteTag, DBType);
        }
        return null;
    }

//ERİŞİM YÖNTEMLERİ:
    public int getIDCounterForNote(){
        return IDCounterForNote;
    }
    public void setIDCounterForNote(int IDCounterForNote){
        this.IDCounterForNote = IDCounterForNote;
    }
    public int getIDCounterForCategory(){
        return IDCounterForCategory;
    }
    public void setIDCounterForCategory(int IDCounterForCategory){
        this.IDCounterForCategory = IDCounterForCategory;
    }
    public int getIDCounterForNoteTag(){
        return IDCounterForNoteTag;
    }
    public void setIDCounterForNoteTag(int IDCounterForNoteTag){
        this.IDCounterForNoteTag = IDCounterForNoteTag;
    }
    public String getStrDBType(){
        return strDBType;
    }
    public void setStrDBType(String strDBType){
        this.strDBType = strDBType;
    }
}