package View;
import Base.GUISeeming;
import Control.ActOnPnlLocation;
import Control.IDARE;
import Control.Movements;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;

public class pnlLocation extends JPanel{
    JButton btnHomePage;
    Movements mov;
    ActOnPnlLocation act;
    GridBagLayout seemingOrder;
    
    public pnlLocation() {
        setLayout(getSeemingOrder());
        updatePanel();
    }

//İŞLEM YÖNTEMLERİ:
    public void updatePanel(){
        removeAll();
        String[] locations = IDARE.getLocationName().split("/");
    //    System.err.println("makâm : " + IDARE.getLocationName());
        switch(locations.length){
            case 1 :{
                if(!locations[0].equals("Tablo"))
                    appHomePageOrder();
                else
                    appTablePageOrder();
                break;
            }
            case 2 :{
                appFolderOrder(locations[1]);
                break;
            }
            case 3 :{
                appNoteOrder(locations[1], locations[2]);
                break;
            }
        }
        GUISeeming.appGUI(this);
    }
    //ARKAPLAN İŞLEM YÖNTEMLERİ:
    private void appFolderOrder(String categoryName){
        Add.ADDCOMP(this, getBtnHomePage(), 0, 0, 1, 1, Add.Insetser(2, 2, 2, 5), GridBagConstraints.BOTH, 1.0, 1.0);
        Add.ADDCOMP(this, new JLabel("/"), 1, 0, 1, 1, Add.Insetser(2, 0, 2, 0), GridBagConstraints.NONE, 0.0, 0.0);
        Add.ADDCOMP(this, produceFolderButton(categoryName), 2, 0, 1, 1, Add.Insetser(2, 5, 2, 5), GridBagConstraints.BOTH, 1.0, 1.0);
    }
    private void appNoteOrder(String categoryName, String noteName){
        Add.ADDCOMP(this, getBtnHomePage(), 0, 0, 1, 1, Add.Insetser(2, 2, 2, 5), GridBagConstraints.BOTH, 1.0, 1.0);
        Add.ADDCOMP(this, new JLabel("/"), 1, 0, 1, 1, Add.Insetser(2, 0, 2, 0), GridBagConstraints.NONE, 0.0, 0.0);
        Add.ADDCOMP(this, produceFolderButton(categoryName), 2, 0, 1, 1, Add.Insetser(2, 5, 2, 5), GridBagConstraints.BOTH, 1.0, 1.0);
        Add.ADDCOMP(this, new JLabel("/"), 3, 0, 1, 1, Add.Insetser(2, 0, 2, 0), GridBagConstraints.NONE, 0.0, 0.0);
        Add.ADDCOMP(this, new JLabel(noteName), 4, 0, 1, 1, Add.Insetser(2, 5, 2, 5), GridBagConstraints.BOTH, 1.0, 1.0);
    }
    private void appHomePageOrder(){
        Add.ADDCOMP(this, getBtnHomePage(), 0, 0, 1, 1, Add.Insetser(2, 2, 2, 5), GridBagConstraints.BOTH, 1.0, 1.0);
    }
    private void appTablePageOrder(){
        Add.ADDCOMP(this, new JLabel("Tablo"), 0, 0, 1, 1, Add.Insetser(2, 2, 2, 5), GridBagConstraints.BOTH, 1.0, 1.0);
    }
    private JButton produceFolderButton(String name){
        JButton folder = new JButton(name);
        folder.setPreferredSize(new Dimension(60, 25));
        folder.addMouseListener(getMov());
        folder.addActionListener(getAct());
        return folder;
    }

//ERİŞİM YÖNTEMLERİ:
    public JButton getBtnHomePage() {
        if(btnHomePage == null){
            btnHomePage = new JButton("Anasayfa");
            btnHomePage.setPreferredSize(new Dimension(60, 25));
            btnHomePage.addMouseListener(getMov());
            btnHomePage.addActionListener(getAct());
        }
        return btnHomePage;
    }

    public Movements getMov() {
        if(mov == null){
            mov = new Movements();
        }
        return mov;
    }

    public ActOnPnlLocation getAct() {
        if(act == null){
            act = new ActOnPnlLocation(this);
        }
        return act;
    }

    public GridBagLayout getSeemingOrder() {
        if(seemingOrder == null){
            seemingOrder = new GridBagLayout();
        }
        return seemingOrder;
    }
    
}