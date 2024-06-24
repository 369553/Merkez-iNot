package View;

import Base.DataBase;
import Base.GUISeeming;
import Base.SystemSettings;
import Control.ActionsOnMenu;
import Control.Movements;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
//Menunun pozisyonunu değiştirme eklenebilir
public class Menu extends JPanel{//ayarlar, category, sırasıyla notlar, yedekleme merkezi, isim
    private static Menu menu;
    JButton btnShowCategoryScreen, btnShowNotesScreen, btnOpenSettings, btnOpenBackuping, btnNew;
    JLabel lblName/*, L2_logoname*/;
    GridBagLayout layoutMain;
    ActionsOnMenu actionsMain;
    Movements movementActs;
     
    public Menu (){
        this.setLayout( getLayoutMain() ) ;
        Add.setAnchor(GridBagConstraints.PAGE_START);
        reflectUserParameters();
        Add.setAnchor(GridBagConstraints.CENTER);
        Add.ADDCOMP(this, getBtnShowCategoryScreen(), 0, 2, 1, 1, Add.getInsets(), GridBagConstraints.NONE, 0.0, 0.0);
        Add.ADDCOMP(this, getBtnShowNotesScreen(), 0, 3, 1, 1, Add.getInsets(), GridBagConstraints.NONE, 0.0, 0.0);
        Add.ADDCOMP(this, getBtnOpenSettings(), 0, 4, 1, 1, Add.getInsets(), GridBagConstraints.NONE, 0.0, 0.0);
        Add.setAnchor(GridBagConstraints.PAGE_END);
        Add.ADDCOMP(this, getBtnOpenBackuping(), 0, 5, 1, 1, Add.getInsets(), GridBagConstraints.NONE, 1.0, 1.0);
        addingListener();
        GUISeeming.appGUI(this);
        MainFrame.getFrame_Main().setVisible(true);
    }
//İŞLEM YÖNTEMLERİ:
    public void addingListener(){
        getBtnShowCategoryScreen().addActionListener(getActionsMain());
        getBtnShowNotesScreen().addActionListener(getActionsMain());
        getBtnOpenSettings().addActionListener(getActionsMain());
        getBtnOpenBackuping().addActionListener(getActionsMain());
        getBtnShowCategoryScreen().addMouseListener(getMovementActs());
        getBtnShowNotesScreen().addMouseListener(getMovementActs());
        getBtnOpenSettings().addMouseListener(getMovementActs());
        getBtnOpenBackuping().addMouseListener(getMovementActs());
    }
    
    private void reflectUserParameters(){
        Add.ADDCOMP(this, getUserName(), 0, 0, 1, 1, Add.getInsets(), GridBagConstraints.NONE, 0.0, 1.0);
    }
    
//ERİŞİM YÖNTEMLERİ:
    //ANA ERİŞİM YÖNTEMİ:
    public static Menu getMenu(){
        if (menu == null){
            menu = new Menu();
        }
        return menu;
    }
    
    public JButton getBtnShowCategoryScreen() {
        if (btnShowCategoryScreen == null){
            btnShowCategoryScreen = new JButton("Kategori görünümü");
            btnShowCategoryScreen.setPreferredSize(new Dimension(115, 25));
        }
        return btnShowCategoryScreen;
    }

    public void setBtnShowCategoryScreen(JButton btnShowCategoryScreen) {
        this.btnShowCategoryScreen = btnShowCategoryScreen;
    }

    public JButton getBtnShowNotesScreen() {
        if (btnShowNotesScreen == null){
            btnShowNotesScreen = new JButton("Liste görünümü");
            btnShowNotesScreen.setPreferredSize(new Dimension(115, 25));
        }
        return btnShowNotesScreen;
    }

    public void setBtnShowNotesScreen(JButton btnShowNotesScreen) {
        this.btnShowNotesScreen = btnShowNotesScreen;
    }

    public JButton getBtnOpenSettings() {
        if (btnOpenSettings == null){
            btnOpenSettings = new JButton("Ayarlar");
            btnOpenSettings.setPreferredSize(new Dimension(115, 25));
        }
        return btnOpenSettings;
    }

    public void setBtnOpenSettings(JButton btnOpenSettings) {
        this.btnOpenSettings = btnOpenSettings;
    }

    public JButton getBtnOpenBackuping() {
        if (btnOpenBackuping == null){
            btnOpenBackuping = new JButton("Yedekleyin");
            btnOpenBackuping.setPreferredSize(new Dimension(115, 25));
        }
        return btnOpenBackuping;
    }

    public void setBtnOpenBackuping(JButton btnOpenBackuping) {
        this.btnOpenBackuping = btnOpenBackuping;
    }

    public JLabel getLblName() {
        if (lblName == null){
            lblName = new JLabel("Giriş yapılmadı");
        }
        return lblName;
    }

    public void setLblName(JLabel lblName) {
        this.lblName = lblName;
    }
    
    public JLabel getUserName(){
        if(DataBase.getDatabase().getUser() != null)
            return new JLabel(DataBase.getDatabase().getUserName());
        else
            return new JLabel("Giriş yapılmadı");
    }
    public GridBagLayout getLayoutMain() {
        if ( layoutMain == null ) {
            layoutMain = new GridBagLayout();
        }
        return layoutMain;
    }
    
    public void setLayoutMain(GridBagLayout layoutMain) {
        this.layoutMain = layoutMain;
    }

    public ActionsOnMenu getActionsMain() {
        if(actionsMain == null){
            actionsMain = new ActionsOnMenu(this);
        }
        return actionsMain;
    }

    public void setActionsMain(ActionsOnMenu actionsMain) {
        this.actionsMain = actionsMain;
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
    
}
