package View;

import Base.GUISeeming;
import Control.Movements;
import Control.TopMenuActions;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class TopMenuGround extends JMenuBar{
    private static TopMenuGround topMenu;
    private FlowLayout layoutForThis;
    private JButton btnOpenMenu, btnOpenOrganizer, btnEditNoteTags;
    private MenuEditNote menuEditNote;
    private MenuEditCategory menuEditCategory;
    private JMenu menuAdd, activeMenu;
    private JMenuItem menuitemAddNote, menuitemAddCategory, menuitemAddTag;
    private JLabel lblLocation;
    TopMenuActions actionsForLayout;
    Movements movementActs;
    pnlLocation pnlLocations;
    
    public TopMenuGround(){
        setLayout(getLayoutForThis()) ;
        this.setMinimumSize(new Dimension(1000, 35));
    //    GridBagConstraints d = new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0, GridBagConstraints.FIRST_LINE_START, GridBagConstraints.NONE, Add.Insetser(2, 5, 5, 2), 0, 0);
        this.add(getBtnOpenMenu());
        this.add(getMenuAdd());
        this.add(getPnlLocations());
        this.add(getBtnEditNoteTags());
        this.add(getBtnOpenOrganizer());
        GUISeeming.appGUI(this);
    }

//İŞLEM YÖNTEMLERİ:
    public void updateLocation(){
        getPnlLocations().updatePanel();
    }

//ERİŞİM YÖNTEMLERİ:
    public static TopMenuGround getTopMenu() {
        if(topMenu == null){
            topMenu = new TopMenuGround();
        }
        return topMenu;
    }
    
    public void setTopMenu(TopMenuGround topMenu) {
        this.topMenu = topMenu;
    }

    public FlowLayout getLayoutForThis() {
        if(layoutForThis == null){
            layoutForThis = new FlowLayout(FlowLayout.LEADING, 5, 2);
        }
        return layoutForThis;
    }

    public void setLayoutForThis(FlowLayout layoutForThis) {
        this.layoutForThis = layoutForThis;
    }

    public JButton getBtnOpenMenu() {
        if(btnOpenMenu == null){
            btnOpenMenu = new JButton("M");
            btnOpenMenu.setPreferredSize(new Dimension(50, 25));
            btnOpenMenu.addActionListener(getActionsForLayout());
            btnOpenMenu.addMouseListener(getMovementActs());
        }
        return btnOpenMenu;
    }

    public void setBtnOpenMenu(JButton btnOpenMenu) {
        this.btnOpenMenu = btnOpenMenu;
    }

    public JButton getBtnOpenOrganizer(){
        if(btnOpenOrganizer == null){
            btnOpenOrganizer = new JButton("<--");
            btnOpenOrganizer.setPreferredSize(new Dimension(50, 25));
            btnOpenOrganizer.addActionListener(getActionsForLayout());
            btnOpenOrganizer.addMouseListener(getMovementActs());
        }
        return btnOpenOrganizer;
    }

    public void setBtnOpenOrganizer(JButton btnOpenOrganizer) {
        this.btnOpenOrganizer = btnOpenOrganizer;
    }

    public JMenu getActiveMenu() {
        return activeMenu;
    }

    public void setActiveMenu(JMenu activeMenu) {
        this.activeMenu = activeMenu;
    }
    
    public JMenu getMenuAdd() {
        if(menuAdd == null){
            menuAdd = new JMenu("EKLE");
            menuAdd.add(getMenuitemAddNote());
            menuAdd.add(getMenuitemAddCategory());
            menuAdd.add(getMenuitemAddTag());
            menuAdd.setPreferredSize(new Dimension(40, 20));
            menuAdd.setSize(new Dimension(40, 20));
            menuAdd.addMouseListener(getMovementActs());
        }
        return menuAdd;
    }

    public void setMenuAdd(JMenu menuAdd) {
        this.menuAdd = menuAdd;
    }

/*    public JMenu getMenuGeneral() {
        if(menuGeneral == null){
            menuGeneral = new JMenu("Genel", true);
            
        }
        return menuGeneral;
    }

    public void setMenuGeneral(JMenu menuGeneral) {
        this.menuGeneral = menuGeneral;
    }*/

    public MenuEditNote getMenuEditNote(){
        if(menuEditNote == null){
            menuEditNote = new MenuEditNote();
        }
        return menuEditNote;
    }

    public void setMenuEditNote(MenuEditNote menuEditNote) {
        this.menuEditNote = menuEditNote;
    }

    public MenuEditCategory getMenuEditCategory(){
        if(menuEditCategory == null){
            menuEditCategory = new MenuEditCategory();
        }
        return menuEditCategory;
    }

    public void setMenuEditCategory(MenuEditCategory menuEditCategory) {
        this.menuEditCategory = menuEditCategory;
    }

    public JMenuItem getMenuitemAddNote() {
        if(menuitemAddNote == null){
            menuitemAddNote = new JMenuItem("Not ekle");
            menuitemAddNote.addActionListener(getActionsForLayout());
            menuitemAddNote.addMouseListener(getMovementActs());
        }
        return menuitemAddNote;
    }

    public void setMenuitemAddNote(JMenuItem menuitemAddNote) {
        this.menuitemAddNote = menuitemAddNote;
    }

    public JMenuItem getMenuitemAddCategory() {
        if(menuitemAddCategory == null){
            menuitemAddCategory = new JMenuItem("Kategori ekle");
            menuitemAddCategory.addActionListener(getActionsForLayout());
            menuitemAddCategory.addMouseListener(getMovementActs());
        }
        return menuitemAddCategory;
    }

    public void setMenuitemAddCategory(JMenuItem menuitemAddCategory) {
        this.menuitemAddCategory = menuitemAddCategory;
    }

    public JMenuItem getMenuitemAddTag() {
        if(menuitemAddTag == null){
            menuitemAddTag = new JMenuItem("Etiket ekle");
            menuitemAddTag.addActionListener(getActionsForLayout());
            menuitemAddTag.addMouseListener(getMovementActs());
        }
        return menuitemAddTag;
    }

    public void setMenuitemAddTag(JMenuItem menuitemAddTag) {
        this.menuitemAddTag = menuitemAddTag;
    }

    public TopMenuActions getActionsForLayout() {
        if(actionsForLayout == null){
            actionsForLayout = new TopMenuActions(this);
        }
        return actionsForLayout;
    }

    public void setActionsForLayout(TopMenuActions actions) {
        this.actionsForLayout = actions;
    }

    public Movements getMovementActs() {
        if(movementActs == null){
            movementActs = new Movements();
        }
        return movementActs;
    }

    public void setMovementActs(Movements movementActs) {
        this.movementActs = movementActs;
    }

    public pnlLocation getPnlLocations() {
        if(pnlLocations == null){
            pnlLocations = new pnlLocation();
        }
        return pnlLocations;
    }

    public JButton getBtnEditNoteTags() {
        if(btnEditNoteTags == null){
            btnEditNoteTags = new JButton("Not etiketlerini düzenle");
            btnEditNoteTags.addActionListener(getActionsForLayout());
            btnEditNoteTags.addMouseListener(getMovementActs());
            btnEditNoteTags.setPreferredSize(new Dimension(150, 25));
        }
        return btnEditNoteTags;
    }
}