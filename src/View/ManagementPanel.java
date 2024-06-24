package View;

import Base.Category;
import Base.DataBase;
import Base.GUISeeming;
import Control.IDARE;
import Control.ManagementPanelActions;
import Control.Movements;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

public class ManagementPanel extends JPanel {
    ArrayList<btnNote> notes;
    ArrayList<btnFolder> categories;
    FlowLayout layout;
    ManagementPanelActions actionsForThis;
    JScrollPane scrpaneMain;
    BorderLayout pageOrder;
    Movements movementActions;
    JPanel pnlContent;

    public ManagementPanel() {
        this.setLayout(getPageOrder());
    //    getPnlContent().addMouseListener(getMovementActions());
        prepareHomeScreen();
        addScreenGUIComponents(true);
    //    this.addMouseListener(getMovementActions());
        this.add(getScrpaneMain(), BorderLayout.CENTER);
        GUISeeming.appGUI(this);
    }
    
//İŞLEM YÖNTEMLERİ:
    public void updatePanel(){
        /*if(getScrpaneMain() == null){
            this.add(getScrpaneMain(), BorderLayout.CENTER);
            System.err.println("üretilmemiş");
        }
            
        else if(getScrpaneMain().getParent() == null){
            System.err.println("Eklenmemiş");
            this.add(getScrpaneMain(), BorderLayout.CENTER);
        }*/
        getPnlContent().removeAll();
        if(IDARE.getLocationName().equals("Anasayfa")){
            categories = null;
            prepareHomeScreen();
            addScreenGUIComponents(true);
        }
        else{
            notes = null;
            String ctName = IDARE.getLocationName().split("/")[1];
            prepareScreen(DataBase.getDatabase().getCategoryByName(ctName));
            addScreenGUIComponents(false);
        }
    //    this.addMouseListener(getMovementActions());
        GUISeeming.appGUI(this);
        GUISeeming.refreshGUI();
    }
    private void openPanel(){
        resetPanel();
        prepareHomeScreen();
        addScreenGUIComponents(true);
        GUISeeming.appGUI(this);
    }
    private void openPanel(Category category){
        resetPanel();
        prepareScreen(category);
        addScreenGUIComponents(false);
        GUISeeming.appGUI(this);
    }
    private void prepareHomeScreen(){
        categories = new ArrayList<btnFolder>();
        for(int index = 0; index < DataBase.getDatabase().getCategories().size(); index++){
            btnFolder category = new btnFolder(DataBase.getDatabase().getCategories().get(index).getName(),
                    DataBase.getDatabase().getCategories().get(index).getCategoryColor(),
                    DataBase.getDatabase().getCategories().get(index).getID());
            category.addActionListener(getActionsForThis());
            getCategories().add(category);
        }
    }
    private void prepareScreen(Category category){
        for(int index = 0; index < category.getNumberOfNotes(); index++){
            System.err.println("bu kategoride not var : " + index);
            btnNote btnNt = new btnNote(DataBase.getDatabase().findNoteByID(category.getNoteIDs().get(index)).getName(), category.getNoteIDs().get(index));
            getNotes().add(btnNt);
            btnNt.addActionListener(getActionsForThis());
        }
    }
    public void addScreenGUIComponents(boolean isHome){
        int maxHeight = 100;
        if(isHome){
            if(!getCategories().isEmpty()){
                for(btnFolder btnData : getCategories()){
                    getPnlContent().add(btnData);
                }
            }
        }
        else{
            if(!getNotes().isEmpty()){
                for(btnNote btnData : getNotes()){
                    getPnlContent().add(btnData);
                }
            }
        }
        int width = MainFrame.getFrame_Main().getWidth()/ 2;
        if(getPnlContent().getComponentCount() > 0)
            maxHeight = (int) (((pnlContent.getComponentCount()) / (width / 162)) * 60);
        pnlContent.setPreferredSize(new Dimension(getScrpaneMain().getWidth() - 20, maxHeight));
    }

//ERİŞİM YÖNTEMLERİ:
    public FlowLayout getPanelLayout() {
        if (layout == null) {
            layout = new FlowLayout(FlowLayout.LEADING, 10, 10);
        }
        return layout;
    }

    public void setPanelLayout(FlowLayout layout) {
        this.layout = layout;
    }

    public ArrayList<btnNote> getNotes() {
        if (notes == null) {
            notes = new ArrayList<btnNote>();
            //VERİTABANINDAN VERİ ÇEKİLİP, BU DEĞİŞKENE AKTARILACAK
        }
        return notes;
    }

    public void setNotes(ArrayList<btnNote> notes) {
        this.notes = notes;
    }

    public ArrayList<btnFolder> getCategories() {
        if(categories == null){
            categories = new ArrayList<btnFolder>();
        }
        return categories;
    }

    public void setCategories(ArrayList<btnFolder> categories) {
        this.categories = categories;
    }

    public ManagementPanelActions getActionsForThis() {
        if (actionsForThis == null) {
            actionsForThis = new ManagementPanelActions(this);
        }
        return actionsForThis;
    }

    public void setActionsForThisP(ManagementPanelActions actionsForThisP) {
        this.actionsForThis = actionsForThisP;
    }

    public Movements getMovementActions() {
        if(movementActions == null){
            movementActions = new Movements();
        }
        return movementActions;
    }

    public void setMovementActions(Movements movementActions) {
        this.movementActions = movementActions;
    }

//ARKAPLAN İŞLEM YÖNTEMLERİ:
    private void resetPanel(){
        //Bunun yerine removeAll() kullanılabilir belki.
        getPnlContent().removeAll();
        if(notes != null)
            notes = null;
        if(categories != null)
            categories = null;
    }

    public JScrollPane getScrpaneMain() {
        if(scrpaneMain == null){
            scrpaneMain = new JScrollPane(getPnlContent());
            scrpaneMain.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
            scrpaneMain.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            scrpaneMain.addMouseListener(getMovementActions());
            scrpaneMain.getHorizontalScrollBar().addMouseListener(getMovementActions());
            scrpaneMain.getVerticalScrollBar().addMouseListener(getMovementActions());
        }
        return scrpaneMain;
    }

    public JPanel getPnlContent() {
        if(pnlContent == null){
            pnlContent = new JPanel();
            pnlContent.setLayout(getPanelLayout());
        }
        return pnlContent;
    }

    public BorderLayout getPageOrder() {
        if(pageOrder == null){
            pageOrder = new BorderLayout();
        }
        return pageOrder;
    }
}