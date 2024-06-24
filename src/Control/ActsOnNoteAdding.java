package Control;

import Base.Category;
import Base.DataBase;
import Base.Note;
import View.ContactPanel;
import View.ProduceNotePanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ActsOnNoteAdding implements ActionListener{
    ProduceNotePanel pnlAddNote;
    
    public ActsOnNoteAdding(ProduceNotePanel pnlAddNote){
        this.pnlAddNote = pnlAddNote;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(pnlAddNote != null){
            if(e.getSource() == pnlAddNote.getBtnAddTag()){
            Object[] tagNames = pnlAddNote.getLiTags().getSelectedValuesList().toArray();
                if(tagNames.length != 0){
                    for(int index = 0; index < tagNames.length; index++){
                        pnlAddNote.getMdlLiCurrentTags().addElement((String) tagNames[index]);
                        pnlAddNote.getMdlLiTags().removeElement(tagNames[index]);
                    }
                }
            }
            else if(e.getSource() == pnlAddNote.getBtnDelTag()){
            Object[] substractList = pnlAddNote.getLiCurrentTags().getSelectedValuesList().toArray();
                if(substractList.length != 0){
                    for(int index = 0; index < substractList.length; index++){
                        pnlAddNote.getMdlLiTags().addElement((String) substractList[index]);
                        pnlAddNote.getMdlLiCurrentTags().removeElement(substractList[index]);
                    }
                }
            }
            else if(e.getSource() == pnlAddNote.getBtnAdd()){
                if(pnlAddNote.getCmboxCategories().getModel().getSelectedItem() == null){
                    Category category = new Category("Genel", "#D87E7E", "Genel notlar");
                    IDARE.addCategoryToSys(category);
                    pnlAddNote.getCmboxCategories().addItem(category.getName());
                    pnlAddNote.getCmboxCategories().setSelectedItem(category.getName());
                }
                Note note = new Note(DataBase.getDatabase().getUser(), pnlAddNote.getTxtName().getText().trim(), "", DataBase.getDatabase().getCategoryByName(((String)pnlAddNote.getCmboxCategories().getSelectedItem())));
                
                if(IDARE.addNoteToSys(note)){
                    for(int index = 0; index < pnlAddNote.getLiCurrentTags().getModel().getSize(); index++){
                        note.addTag(DataBase.getDatabase().getNoteTagByName((String) pnlAddNote.getLiCurrentTags().getModel().getElementAt(index)));
                    }
    //KONTROL İÇİNDİ                note.printInfos();
                    if(!IDARE.isInProcessing())
                        IDARE.openNote(note);
                    pnlAddNote.resetPanel();
                    IDARE.updateGUIData();
                    //Açılır pencereyi kapat BURADA KALINDI
    //JOptionPane leri kapatmak için bir yöntem, ama maliyyeti yüksek :                 MainFrame.getFrame_Main().dispose();
                    //MainFrame.getFrame_Main().dispose();
                }
                pnlAddNote.getTxtName().requestFocus();
            }
        }
    }
    
    public boolean checkSameTags(String tagText){//Eğer gönderilen isimdeki not etiketi zâten ekleme listesine eklenmişse olumsuz cevâb döner.
        for(int index = 0; index < pnlAddNote.getMdlLiCurrentTags().getSize(); index++){
            if(pnlAddNote.getMdlLiCurrentTags().elementAt(index).equals(tagText))
                return false;
        }
        return true;
    }
    
}
