package View;

import Base.Category;
import Base.DataBase;
import Base.GUISeeming;
import Control.ActOpenClose;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import javax.swing.JList;
import javax.swing.JPanel;

public class pnlCategory extends JPanel{
   btnCategory btnMain;
   private Category category;
   GridBagLayout compOrder;
   JList liNotes;
   ArrayList<String> arliNoteNames;
   ActOpenClose actBtnMain;
   public boolean boolIsListOpen = false;
   
    public pnlCategory(Category category){
        this.category = category;
        setLayout(getCompOrder());
        Add.ADDCOMP(this, getBtnMain(), 0, 0, 1, 1, Add.Insetser(0, 0, 0, 0), GridBagConstraints.BOTH, 1.0, 0.0);
        Add.ADDCOMP(this, getLiNotes(), 0, 1, 1, 2, Add.Insetser(4, 1, 4, 1), GridBagConstraints.BOTH, 1.0, 2.5);
        getBtnMain().setPreferredSize(new Dimension(this.getWidth(), 40));
        getBtnMain().setMinimumSize(new Dimension(this.getWidth(), 40));
        getLiNotes().setVisible(false);
        GUISeeming.appGUI(this);
    }

//İŞLEM YÖNTEMLERİ:
    public void appSizeLine(int size){
        liNotes.setPreferredSize(new Dimension(size - 10, calculateBestHeightForList()));
        liNotes.setMaximumSize(new Dimension(size - 10, 2500));
    }
    //ARKAPLAN İŞLEM YÖNTEMLERİ:
    private boolean updateNoteNames(){
        String[] strNames = DataBase.getDatabase().getNoteNames(getCategory().getName());
        if(strNames != null){
            if(arliNoteNames != null)
                getArliNoteNames().clear();
            else
                arliNoteNames = new ArrayList<String>();
            for(int index = 0; index < strNames.length; index++){
                arliNoteNames.add(strNames[index]);
            }
            return true;
        }
        return false;
    }
    private int calculateBestHeightForList(){
        return (getLiNotes().getModel().getSize() * 20);
    }

//ERİŞİM YÖNTEMLERİ:
    public btnCategory getBtnMain() {
        if(btnMain == null){
            btnMain = new btnCategory(this.getCategory().getName(), this.getCategory().getNumberOfNotes(), this.getCategory().getCategoryColor());
            btnMain.getBtnOpenClose().addActionListener(getActBtnMain());
        }
        return btnMain;
    }
    private Category getCategory() {
        return category;
    }
    public GridBagLayout getCompOrder() {
        if(compOrder == null){
            compOrder = new GridBagLayout();
        }
        return compOrder;
    }
    public JList getLiNotes(){
        if(liNotes == null){
            liNotes = new JList(getArliNoteNames().toArray());
        }
        return liNotes;
    }
    public ArrayList<String> getArliNoteNames(){
        if(arliNoteNames == null){
            arliNoteNames = new ArrayList<String>();
            updateNoteNames();
        }
        return arliNoteNames;
    }
    private void setArliNoteNames(ArrayList<String> arliNoteNames) {
        this.arliNoteNames = arliNoteNames;
    }
    public ActOpenClose getActBtnMain() {
        if(actBtnMain == null){
            actBtnMain = new ActOpenClose(this);
        }
        return actBtnMain;
    }
}