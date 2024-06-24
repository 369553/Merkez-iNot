package Control;

import View.TopMenuGround;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TopMenuActions implements ActionListener{
    TopMenuGround topMenu;
    
    public TopMenuActions(TopMenuGround topMenu) {
        this.topMenu = topMenu;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == topMenu.getBtnOpenMenu()){
            InterfaceController.getGuiManaging().openCloseMenu();
        }
        else if(e.getSource() == topMenu.getBtnOpenOrganizer()){
            InterfaceController.getGuiManaging().openCloseOrganizer();
        }
        else if(e.getSource() == topMenu.getMenuitemAddNote()){
            InterfaceController.getGuiManaging().setChoosedThing(null);
            InterfaceController.getGuiManaging().openAddingNotePanel();
        }
        else if(e.getSource() == topMenu.getMenuitemAddCategory()){
            InterfaceController.getGuiManaging().setChoosedThing(null);
            InterfaceController.getGuiManaging().openAddingCategoryPanel();
        }
        else if(e.getSource() == topMenu.getMenuitemAddTag()){
            InterfaceController.getGuiManaging().setChoosedThing(null);
            InterfaceController.getGuiManaging().openAddingNoteTagPanel();
        }
        else if(e.getSource() == topMenu.getBtnEditNoteTags()){
            InterfaceController.getGuiManaging().setChoosedThing(null);
            InterfaceController.getGuiManaging().openEditNoteTagPanel();
        }
    }
}
