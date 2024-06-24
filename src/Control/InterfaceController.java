package Control;

import Base.Category;
import Base.GUISeeming;
import Services.CryptingService;
import View.Add;
import View.ContactPanel;
import View.InteractiveGUIStructs;
import View.MainFrame;
import View.MainPanel;
import View.Menu;
import View.Organizer;
import View.ProduceCategoryPanel;
import View.ProduceNotePanel;
import View.ProduceNoteTag;
import View.PnlManageTags;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JComponent;

public class InterfaceController{
    private static InterfaceController guiManaging;
    private JComponent currentComp = null;
    private HashMap<String, JComponent> compAndNames;
    private ArrayList<String> lastComps;
    private boolean isOrganizerOpened = false;
    private boolean isMenuOpened = false;
    private ManaGUIRelation choosedThing = null;
    private MainPanel MP;

    private InterfaceController(){
    }

    public static void resetInterface(String code){
        if(CryptingService.getMd5(code).equals(IDARE.getMd5Code())){
            System.err.println("Doğru kod.\nArayüz sıfırlanacak inşâAllÂH");
            getGuiManaging().getMP().removeAll();
            MainFrame.resetFrame(code);
        }
            
    }
    public void addToMP(JComponent comp){
        getCompAndNames().put(comp.getClass().getName(), comp);
        if(currentComp != null){
            MP.remove(currentComp);
            removePanelWithThem(currentComp.getClass().getName());
        }
        Add.setAnchor(GridBagConstraints.CENTER);
        Add.ADDCOMP(MP, comp, 0, 1, 4, 1, Add.Insetser(7, 7, 7, 7), GridBagConstraints.BOTH, 1.0, 1.0);
        addPanelWithThem(comp.getClass().getName());
        currentComp = comp;
        GUISeeming.refreshGUI();
    }

    public void openCloseMenu(){
        if(isMenuOpened){
            MainFrame.getFrame_Main().getContentPane().remove(Menu.getMenu());
            isMenuOpened = false;
        }
        else{
            MainFrame.getFrame_Main().add(Menu.getMenu(), BorderLayout.WEST);
            isMenuOpened = true;
        }
        GUISeeming.refreshGUI();
    }

    public void openCloseOrganizer(){
        if(isOrganizerOpened){
            MainFrame.getFrame_Main().getContentPane().remove(Organizer.getOrgMenu());
            isOrganizerOpened = false;
            MP.getTopMenu().getBtnOpenOrganizer().setText("<--");
        }
        else{
            MainFrame.getFrame_Main().add(Organizer.getOrgMenu(), BorderLayout.EAST);
            isOrganizerOpened = true;
            MP.getTopMenu().getBtnOpenOrganizer().setText("-->");
        }
        GUISeeming.refreshGUI();
    }

    public Container[] getAllPages(){//BURADA KALINDI. ÇALIŞMIYOR!
        //UZUN YÖNTEM:
        /*Object[] obContainers = getCompAndNames().values().toArray();
        Container[] containers = new Container[obContainers.length];
        for(int index = 0; index < getCompAndNames().size(); index++){
            containers[index] = (Container) obContainers[index];
        }*/
        //KISA YÖNTEM
        Container[] containers = getCompAndNames().values().toArray(new Container[0]);
        return containers;
    }
    public static void openCategory(Category category){
        //.;.
      // IDARE.getLocation("Anasayfa\n" + category.getName());
    }

    public void openAddingNotePanel(){
        ContactPanel.getContactPanel().showMessage("Etiket eklemek ve silmek için ilgili tuşları kullanın", "Info");
        ProduceNotePanel notePanel = new ProduceNotePanel(MP);
        GUISeeming.refreshGUI();
    }
    public void openAddingCategoryPanel(){
        ProduceCategoryPanel categoryPanel = new ProduceCategoryPanel(MP);//HATALI ÇALIŞIYOR; yapıcı fonksiyon zamânında çalışmıyor gibi bir şey
        GUISeeming.refreshGUI();
    }
    public void openAddingNoteTagPanel(){
        ProduceNoteTag addingNoteTagPanel = new ProduceNoteTag(MP);
        GUISeeming.refreshGUI();
    }
    private void addPanelWithThem(String compName){
        switch(compName){
            case "View.ManagementPanel":{
                addTopMenu();
                Add.setAnchor(GridBagConstraints.CENTER);
                break;
            }
            case "View.ListPanel":{
                addTopMenu();
                Add.setAnchor(GridBagConstraints.CENTER);
                break;
            }
            case "View.EditingPanel":{
                addTopMenu();
                Add.setAnchor(GridBagConstraints.CENTER);
                if(MP.getEditPanel().getEditTools().getBtnSaveThenExit().getActionListeners().length == 0)
                    MP.getEditPanel().getEditTools().addingListeners();
                break;
            }
        }
    }
    private void removePanelWithThem(String compName){
        switch(compName){
            case "View.ManagementPanel":{
                MP.remove(MP.getTopMenu());
                return;
            }
            case "View.ListPanel":{
                MP.remove(MP.getTopMenu());
                break;
            }
            case "View.EditingPanel":{
                MP.remove(MP.getTopMenu());
                break;
            }
            case "View.BackupPanel":{
                MP.remove(MP.getTopMenu());
                break;
            }
        }
    }
    public void addTopMenu(){
        Add.setAnchor(GridBagConstraints.FIRST_LINE_START);
        Add.ADDCOMP(MP, MP.getTopMenu(), 0, 0, 4, 1, Add.Insetser(7,7,7,7), GridBagConstraints.HORIZONTAL, 1.0, 0.0);
    }
    public void addContactPanel(){
        Add.setAnchor(GridBagConstraints.FIRST_LINE_END);
        Add.ADDCOMP(MP, ContactPanel.getContactPanel(), 0, 2, 4, 1, Add.Insetser(3, 2, 0, 2), GridBagConstraints.HORIZONTAL, 1.0, 0.0);
    }
    public void resetComponentOrders(){
        
    }
    protected void changeMenuType(){
        if(getChoosedThing() != null){
            if(getChoosedThing().dataType.equals("Note")){
                if(MP.getTopMenu().getActiveMenu() != null){
                    if(MP.getTopMenu().getActiveMenu() == MP.getTopMenu().getMenuEditCategory())
                        MP.getTopMenu().remove(MP.getTopMenu().getActiveMenu());
                }
                MP.getTopMenu().add(MP.getTopMenu().getMenuEditNote(), 3);
                MP.getTopMenu().setActiveMenu(MP.getTopMenu().getMenuEditNote());
                GUISeeming.appGUI(MP.getTopMenu().getMenuEditNote());
                GUISeeming.refreshGUI();
            }
            else if(getChoosedThing().dataType.equals("Category")){
                if(MP.getTopMenu().getActiveMenu() != null){
                    if(MP.getTopMenu().getActiveMenu() == MP.getTopMenu().getMenuEditNote())
                        MP.getTopMenu().remove(MP.getTopMenu().getActiveMenu());
                }
                MP.getTopMenu().add(MP.getTopMenu().getMenuEditCategory(), 3);
                MP.getTopMenu().setActiveMenu(MP.getTopMenu().getMenuEditCategory());
                GUISeeming.appGUI(MP.getTopMenu().getMenuEditCategory());
                GUISeeming.refreshGUI();
            }
        }
        else
            removeActiveMenu();
    }
    protected void removeActiveMenu(){
        if(MP.getTopMenu().getActiveMenu() != null){
            MP.getTopMenu().remove(MP.getTopMenu().getActiveMenu());
            MP.getTopMenu().setActiveMenu(null);
            GUISeeming.refreshGUI();
        }
    }
    protected void prepareInteractiveGUIStructs(){
        InteractiveGUIStructs.startActiveManager(MP);
    }
    
    public void updateLocation(){
        if(MP.getTopMenu().isVisible()){
            MP.getTopMenu().updateLocation();
            MainFrame.getFrame_Main().setVisible(true);
        }
    }
    public void openEditNoteTagPanel(){
        PnlManageTags pnl = new PnlManageTags(this.MP);
    }
    public void updateGUIForClicked(ManaGUIRelation choosedNewThing){
        if(this.choosedThing != null)
            GUISeeming.mouseUnClickedRefresh(this.choosedThing.getGuiComponent());
        if(choosedNewThing != null)
            GUISeeming.mouseClickedRefresh(choosedNewThing.getGuiComponent());
    }

//ERİŞİM YÖNTEMLERİ:
    public static InterfaceController getGuiManaging(){
        if(guiManaging == null){
            guiManaging = new InterfaceController();
        }
        return guiManaging;
    }

    /*public static void setGuiManaging(InterfaceController guiManaging) {
        InterfaceController.guiManaging = guiManaging;
    }*/

    public JComponent getCurrentComp() {
        return currentComp;
    }
    public void setCurrentComp(JComponent currentComp) {
        this.currentComp = currentComp;
    }
    public HashMap<String, JComponent> getCompAndNames() {
        if(compAndNames == null){
            compAndNames = new HashMap<String, JComponent>();
        }
        return compAndNames;
    }
    public void setCompAndNames(HashMap<String, JComponent> compAndNames) {
        this.compAndNames = compAndNames;
    }
    public ArrayList<String> getLastComps() {
        if(lastComps == null){
            lastComps = new ArrayList<String>();
        }
        return lastComps;
    }
    public void setLastComps(ArrayList<String> lastComps) {
        this.lastComps = lastComps;
    }
    public MainPanel getMP() {
        return MP;
    }
    public void setMP(MainPanel MP){
        this.MP = MP;
        prepareInteractiveGUIStructs();
    }
    public ManaGUIRelation getChoosedThing() {
        /*if(choosedThing == null){
            choosedThing = new ManaGUIRelation();
        }*/
        return choosedThing;
    }
    public void setChoosedThing(ManaGUIRelation choosedThing){
        updateGUIForClicked(choosedThing);
        this.choosedThing = choosedThing;
        if(this.choosedThing == null){
            removeActiveMenu();
            return;
        }
        switch(InterfaceController.getGuiManaging().getCurrentComp().getClass().getName()){
            case "View.ManagementPanel" :{
                changeMenuType();
                break;
            }
            case "View.ListPanel" :{
                changeMenuType();
                break;
            }
        }
    }
}