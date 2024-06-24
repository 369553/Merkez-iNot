package View;

import Base.GUISeeming;
import Base.Note;
import Control.InterfaceController;
import java.awt.GridBagLayout;
import javax.swing.JPanel;
//Layout ve diğer ayarlamalar yapılacak

public class MainPanel extends JPanel{
    private GridBagLayout layout;
    private ManagementPanel managPanel;
    private EditingPanel editPanel;
    private OptionsPanel optionsPanel;
    private ListPanel listPanel;
    private TopMenuGround topMenu;
    
    public MainPanel(){
        setLayout(getPanelLayout()) ;
    }
    
    public void addPanels(){
        InterfaceController.getGuiManaging().addToMP(this.getManagPanel());
     //   InterfaceController.getGuiManaging().addToMP(this.getEditPanel());
       // InterfaceController.getGuiManaging().addToMP(getListPanel());
        InterfaceController.getGuiManaging().addContactPanel();
        GUISeeming.appGUI(this);
    }

//ERİŞİM YÖNTEMLERİ:
    public ManagementPanel getManagPanel() {
        if (managPanel == null){
            managPanel = new ManagementPanel();
        }
        return managPanel;
    }

    public void setManagPanel(ManagementPanel managPanel) {
        this.managPanel = managPanel;
    }

    public EditingPanel getEditPanel() {
        if (editPanel == null){
            editPanel = EditingPanel.getEditPanel();
        }
        return editPanel;
    }
    
    public EditingPanel getEditPanel(Note note) {
        if (editPanel == null){
            editPanel = EditingPanel.getEditPanel();
        }
        return editPanel;
    }

    public void setEditPanel(EditingPanel editPanel) {
        this.editPanel = editPanel;
    }
    
    public GridBagLayout getPanelLayout() {
        if ( layout == null ) {
            layout = new GridBagLayout();
        }
        return layout;
    }

    public OptionsPanel getOptionsPanel() {
        if(optionsPanel == null){
            optionsPanel = new OptionsPanel();
        }
        return optionsPanel;
    }

    public void setOptionsPanel(OptionsPanel optionsPanel) {
        this.optionsPanel = optionsPanel;
    }

    public ListPanel getListPanel() {
        if(listPanel == null){
            listPanel = new ListPanel();
        }
        return listPanel;
    }

    public void setListPanel(ListPanel listPanel) {
        this.listPanel = listPanel;
    }

    public TopMenuGround getTopMenu() {
        if(topMenu == null){
            topMenu = TopMenuGround.getTopMenu();
        }
        topMenu.updateLocation();
        return topMenu;
    }

    public void setTopMenu(TopMenuGround topMenu) {
        this.topMenu = topMenu;
    }
}