
package View;

import Base.Category;
import Base.DataBase;
import Base.GUISeeming;
import Control.Movements;
import Control.OrganizerActions;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

public class Organizer extends JPanel{
    private static Organizer orgMenu;
    public static boolean isWorkBefore = false;
    GridBagLayout LayoutMain;
    OrganizerActions actOnOrganizer;
    Movements movementsActs;
    ArrayList<pnlCategory> pnlCategories;
    ArrayList<Category> ctCategories;
    JScrollPane scrpaneMain;
    JPanel pnlMain;
    //ERİŞİM BELİRTEÇLERİ DÜZENLENMELİ
    
    public Organizer(){
        this.setLayout(getLayoutMain());
        setPreferredSize(new Dimension(170, MainFrame.getFrame_Main().getHeight()));
        Add.ADDCOMP(this, getScrpaneMain(), 0, 0, 1, 1, Add.Insetser(0, 0, 0, 0), GridBagConstraints.BOTH, 1.0, 1.0);
        GUISeeming.appGUI(this);
    }
    
/*Fonksiyonların statik olup olmaması gerektiği, statik olmayan fonksiyondan 
    statik bir şeye erişilmesinin ne demek olduğu ve bunun risk içerip içermediği hakkında araştırma yap*/
//İŞLEM YÖNTEMLERİ:
    public void addPnlCategories(){
        for(int index = 0; index < getPnlCategories().size(); index++){
            Add.ADDCOMP(getPnlMain(), getPnlCategories().get(index), 0, index, 1, 1, Add.Insetser(12, 2, 2, 2), GridBagConstraints.HORIZONTAL, 1.0, 0.0);
        }
        isWorkBefore = true;
    }
    public void updateOrganizer(){
    //    this.removeAll();
        pnlMain.removeAll();
        pnlCategories.removeAll(pnlCategories);
        pnlCategories = null;
        addPnlCategories();
        Add.ADDCOMP(this, getScrpaneMain(), 0, 0, 1, 1, Add.Insetser(0, 0, 0, 0), GridBagConstraints.BOTH, 1.0, 1.0);
    }
    private void setCategories(ArrayList<Category> ctCategories) {
        this.ctCategories = ctCategories;
    }

//ERİŞİM YÖNTEMLERİ:
    //ANA ERİŞİM YÖNTEMİ:
    public static Organizer getOrgMenu() {
        if(orgMenu == null){
            orgMenu = new Organizer();
        }
        return orgMenu;
    }
    public GridBagLayout getLayoutMain() {
        if (LayoutMain == null){
            LayoutMain = new GridBagLayout();
        }
        return LayoutMain;
    }
    public Movements getMovementsActs() {
        if(movementsActs == null){
            movementsActs = new Movements();
        }
        return movementsActs;
    }
    public ArrayList<pnlCategory> getPnlCategories(){
        if(pnlCategories == null){
            pnlCategories = new ArrayList<pnlCategory>();
            for(Category ct : getCategories()){
                pnlCategory pnlCt = new pnlCategory(ct);
                pnlCt.appSizeLine(this.getWidth() - 20);
                pnlCategories.add(pnlCt);
            }
        }
        return pnlCategories;
    }
    public ArrayList<Category> getCategories() {
        if(ctCategories == null){
            ctCategories = DataBase.getDatabase().getCategories();
        }
        return ctCategories;
    }

    public JPanel getPnlMain(){
        if(pnlMain == null){
            pnlMain = new JPanel();
            pnlMain.setLayout(new GridBagLayout());
            addPnlCategories();
        }
        return pnlMain;
    }
    
    public JScrollPane getScrpaneMain() {
        if(scrpaneMain == null){
            scrpaneMain = new JScrollPane(getPnlMain(), ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                    ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            scrpaneMain.setPreferredSize(new Dimension(this.getWidth() - 15, 200));
            scrpaneMain.getHorizontalScrollBar().setUnitIncrement(26);
            scrpaneMain.getVerticalScrollBar().setUnitIncrement(36);
        }
        return scrpaneMain;
    }
}