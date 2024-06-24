package Control;

import Base.Category;
import Base.DataBase;
import Base.GUISeeming;
import Base.Note;
import View.ContactPanel;
import View.InfoPanel;
import View.InteractiveGUIStructs;
import View.MenuEditNote;
import View.btnNote;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

public class ActionsOnMenuEditNote implements ActionListener{
    MenuEditNote menuEditNote;
    private boolean sameName = false;
    public ActionsOnMenuEditNote(MenuEditNote menuEditNote) {
        this.menuEditNote = menuEditNote;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == menuEditNote.getMenuitemRenameNote()){
            String name = InteractiveGUIStructs.getActiveManager().showJustTextField("İsimlendir", "Yeni isim giriniz: ");
            if(renameNote(name) != true){
                if(sameName){
                    InteractiveGUIStructs.getActiveManager().showNotAcceptableError("İşlem başarısız!");
                    sameName = true;
                }
            }
            else{
                IDARE.updateGUIData();
                GUISeeming.refreshGUI();
            }
        }
        
        else if(e.getSource() == menuEditNote.getMenuitemDeleteNote()){
            boolean isSuccesful = false;
            Note currentNote = (Note) InterfaceController.getGuiManaging().getChoosedThing().getThing();
            isSuccesful = IDARE.deleteNoteFromSys(currentNote);
            if(isSuccesful){
                Component deleted = (Component) InterfaceController.getGuiManaging().getChoosedThing().getGuiComponent();
                deleted.getParent().remove(deleted);
                /*InterfaceController.getGuiManaging().getCurrentComp().
                    remove((Component) InterfaceController.getGuiManaging().getChoosedThing().getGuiComponent());*/
                InterfaceController.getGuiManaging().setChoosedThing(null);
                IDARE.updateGUIData();
                GUISeeming.refreshGUI();
            }
            else
                ContactPanel.getContactPanel().showMessage("Silme işlemi başarısız oldu.", "Warning");
        }
        
        else if(e.getSource() == menuEditNote.getMenuitemChangeCategory()){
            Note choosedNote = (Note) InterfaceController.getGuiManaging().getChoosedThing().getThing();
            JPanel pnlChooseCategory = new JPanel();
            BoxLayout layoutForPanel = new BoxLayout(pnlChooseCategory, BoxLayout.Y_AXIS);
            pnlChooseCategory.setLayout(layoutForPanel);
            JList<String> categories;
            categories = new JList<String>(DataBase.getDatabase().getCategoryNames());
            categories.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            JScrollPane scrPaneCategories = new JScrollPane(categories, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            JButton btnComplete = new JButton("Tamam");
            scrPaneCategories.setPreferredSize(new Dimension(170, 70));
            btnComplete.setPreferredSize(new Dimension(70, 25));
            Movements movementsActs = new Movements();
            btnComplete.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Category toCategory = DataBase.getDatabase().getCategoryByName(categories.getSelectedValue());
                    if(analyzeChangeCategory(choosedNote, toCategory) != false){
                        choosedNote.changeCategory(toCategory);
                        ContactPanel.getContactPanel().showMessage("Kategori değişimi işlemi başarılı", "Info");
                        IDARE.updateGUIData();
                        IDARE.openCategoryInFolderSeeming(IDARE.getLocationName().split("/")[1]);
                    }
                    else{
                        ContactPanel.getContactPanel().showMessage("Kategori değişimi işlemi başarısız", "Warning");
                    }
                    //İLETİŞİM KUTUSUNU KAPAT!
                }
            });
            btnComplete.addMouseListener(movementsActs);
            scrPaneCategories.getHorizontalScrollBar().addMouseListener(movementsActs);
            scrPaneCategories.getVerticalScrollBar().addMouseListener(movementsActs);
            pnlChooseCategory.add(scrPaneCategories, 0);
            pnlChooseCategory.add(btnComplete, 1);
            GUISeeming.appGUI(pnlChooseCategory);
            InteractiveGUIStructs.getActiveManager().showSpecialGUI(pnlChooseCategory, "Kategori değişimi", "Kategori seçin");
            
        }
        else if(e.getSource() == menuEditNote.getMenuitemLookInfo()){
        InteractiveGUIStructs.getActiveManager().showSpecialGUI(InfoPanel.prepareNoteInfoPanel(
            (Note) InterfaceController.getGuiManaging().getChoosedThing().getThing()),
            "Bilgileri göster", "Not bilgileri");
        }
    }
    
//İŞLEM YÖNTEMLERİ:
    public boolean renameNote(String noteName){
        if(noteName == null)
            return false;
        String name = noteName.trim();
        if(name.isEmpty())
            return false;
        for(int index = 0; index < name.length(); index++){
            char character = name.charAt(index);
            boolean isAccept = KeySignalControl.isAcceptableForNote(character);
            if(isAccept == false){
                ContactPanel.getContactPanel().showMessage(character + " karakteri burada kullanılamaz", "Warning");
                return false;
            }
        }
        Note note = (Note) InterfaceController.getGuiManaging().getChoosedThing().getThing();
        Note fetchedNote;
        fetchedNote = DataBase.getDatabase().getNoteByName(name, note.getCategory().getName());
        if(fetchedNote != null){
            if(fetchedNote.getID() != note.getID()){
                ContactPanel.getContactPanel().showMessage("Bu kategoride bu isimde not var!", "Warning");
                sameName = true;
                return false;
            }
            return true;
        }
        note.renameNote(name);
        ContactPanel.getContactPanel().showMessage("İşlem başarılı", "Successful");
        btnNote choosedBtnNote = (btnNote) InterfaceController.getGuiManaging().getChoosedThing().guiComponent;
        choosedBtnNote.setText(name);
    //    Organizer.getOrgMenu().updateOrganizer();
        return true;
    }
    
    public boolean analyzeChangeCategory(Note note, Category targetCategory){
        //O isimdeki kategori duruyor mu?
        //O kategoride aynı isimde not var mı?
        /*Varsa kullanıcıya işlemi iptal etmek isteyip istemediğini, yeniden adlandırmayı vb. yapılacak
        işlemi sor*/
        return true;
    }
    
}
