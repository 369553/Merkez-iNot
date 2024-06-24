package Control;

import Base.DataBase;
import Base.Note;
import Base.NoteTag;
import View.ContactPanel;
import View.ProduceNoteTag;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ActsOnNoteTagAdding implements ActionListener{
   ProduceNoteTag screen;

    public ActsOnNoteTagAdding(ProduceNoteTag screen){
        this.screen = screen;
    }

    @Override
    public void actionPerformed(ActionEvent e){
        if(e.getSource() == screen.getBtnAddToNote()){
            Object[] noteCategoryNames = screen.getLiNotes().getSelectedValuesList().toArray();
            if(noteCategoryNames.length != 0){
                for(int index = 0; index < noteCategoryNames.length; index++){
                    screen.getMdlListModelForCurrentNotes().addElement((String) noteCategoryNames[index]);
                    screen.getMdlListModelForNotes().removeElement(noteCategoryNames[index]);
                }
            }
        }
        else if(e.getSource() == screen.getBtnSubstractFromNote()){
            int[] list = screen.getLiCurrentNotes().getSelectedIndices();
            Object[] noteCategoryNames = screen.getLiCurrentNotes().getSelectedValuesList().toArray();
            if(noteCategoryNames.length != 0){
                for(int index = 0; index < noteCategoryNames.length; index++){
                    screen.getMdlListModelForNotes().addElement((String) noteCategoryNames[index]);
                    screen.getMdlListModelForCurrentNotes().removeElement(noteCategoryNames[index]);
                }
            }
        //    MainFrame.getFrame_Main().dispose();
        //    MainFrame.getFrame_Main().repaint();
        //    MainFrame.getFrame_Main().setVisible(true);
        //    screen.openScreen();
        }
        else if(e.getSource() == screen.getBtnComplete()){
            String tagName = screen.getTxtName().getText();
            if(tagName.length() == 0){
                ContactPanel.getContactPanel().showMessage("Not etiketi için isim giriniz!", "Warning");
                screen.getTxtName().requestFocus();
            //    InteractiveGUIStructs.getActiveManager().showNotAcceptableError("İsim alanı boş bırakılamaz!");
                return;
            }
            String[] noteCategoryNames = new String[screen.getLiCurrentNotes().getModel().getSize()];
            for(int index = 0; index < screen.getLiCurrentNotes().getModel().getSize(); index++){
                noteCategoryNames[index] = (String) screen.getLiCurrentNotes().getModel().getElementAt(index);
            }
            NoteTag tag = new NoteTag(screen.getTxtName().getText().trim());
            for(int index = 0; index < noteCategoryNames.length; index++){
                Note temp = DataBase.getDatabase().getNoteByName(noteCategoryNames[index].split(":")[0].trim(),
                        noteCategoryNames[index].split(" : ")[1].trim());
/*                System.err.println("split : " + noteCategoryNames[index].split(":")[0].trim() +
                        noteCategoryNames[index].split(":")[1].trim());*/
                if(temp != null)
                    tag.addNote(temp.getID());
            }
            IDARE.addNoteTagToSys(tag);
            screen.getTxtName().requestFocus();
        }
    }

}