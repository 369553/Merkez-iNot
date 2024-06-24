package View;
//Add.setInsets(70, 70, 70, 70);Ekran büyüdüğünde insets ler de ona göre ayarlanmalı; yânî containerListener kullanılmalı
import Base.GUISeeming;
import Base.SystemSettings;
import Control.ActOnOptions;
import Control.Movements;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class OptionsPanel extends JPanel{
    JLabel /*lblLanguage,*/ lblLog, lblLogLocation, lblGUISeeming, lblAutosave;
    JCheckBox chboxIsLogging, chboxIsAutosave ;
    JComboBox /*cmboxLanguage,*/ cmboxGUISeeming ;
    JButton btnChooseLocation, btnSave, btnExit;
    GridBagLayout layoutMain;
    JFileChooser chooser;
    ActOnOptions acts; 
    Movements movementsActs;
    
    public OptionsPanel(){
        layoutMain = new GridBagLayout();
        this.setLayout(layoutMain);
        Add.setInsets(7, 7, 7, 7);
        Add.setAnchor(GridBagConstraints.CENTER);
//        Add.ADDCOMP(this, getL1_language(), 0, 0, 1, 1, Add.getInsets(), GridBagConstraints.CENTER, 0.1, 0.1);
//        Add.ADDCOMP(this, getComB1_language(), 1, 0, 1, 1, Add.getInsets(), GridBagConstraints.CENTER, 0.1, 0.1);
        Add.ADDCOMP(this, getLblLog(), 0, 1, 1, 1, Add.Insetser(2,5,5,5), GridBagConstraints.CENTER, 0.3, 0.5);
        Add.ADDCOMP(this, getChboxIsLogging(), 1, 1, 1, 1, Add.Insetser(2,5,5,5), GridBagConstraints.CENTER, 1.0, 0.5);
        Add.ADDCOMP(this, getLblLogLocation(), 0, 2, 1, 1, Add.Insetser(5,5,5,5), GridBagConstraints.CENTER, 0.3, 0.5);
        Add.ADDCOMP(this, getBtnChooseLocation(), 1, 2, 1, 1, Add.Insetser(5,5,5,5), GridBagConstraints.BOTH, 1.0, 0.5);
        Add.ADDCOMP(this, getLblGUISeeming(), 0, 3, 1, 1, Add.Insetser(5,5,5,5), GridBagConstraints.CENTER, 0.3, 0.5);
        Add.ADDCOMP(this, getCmboxGUISeeming(), 1, 3, 1, 1, Add.Insetser(5,5,5,5), GridBagConstraints.BOTH, 3.0, 0.5);
        Add.ADDCOMP(this, getLblAutosave(), 0, 4, 1, 1, Add.Insetser(5,5,5,5), GridBagConstraints.CENTER, 0.3, 0.5);
        Add.ADDCOMP(this, getChboxIsAutosave(), 1, 4, 1, 1, Add.Insetser(5,5,5,5), GridBagConstraints.CENTER, 0.3, 0.5);
        Add.ADDCOMP(this, getBtnSave(), 0, 5, 1, 1, Add.Insetser(5,5,2,5), GridBagConstraints.BOTH, 1.0, 0.5);
        Add.ADDCOMP(this, getBtnExit(), 1, 5, 1, 1, Add.Insetser(5,5,2,5), GridBagConstraints.BOTH, 1.0, 0.5);
        applySettings();
        GUISeeming.appGUI(this);
    }
    
//İŞLEM YÖNTEMLERİ:
    public void openChooser () {
        getChooser().showDialog(this, "Klasörü seç" ) ;
    }
    public void applySettings(){
        getChboxIsAutosave().setSelected(SystemSettings.getSettings().getIs_autoSaving());
        getChboxIsLogging().setSelected(SystemSettings.getSettings().getIs_logging());
        switch(SystemSettings.getSettings().getGuiSeemingName()){
            case "Standard" :{
                getCmboxGUISeeming().setSelectedIndex(0);
                break;
            }
            case "Blue" :{
                getCmboxGUISeeming().setSelectedIndex(1);
                break;
            }
            case "Pink" :{
                getCmboxGUISeeming().setSelectedIndex(2);
                break;
            }
            case "Dark" :{
                getCmboxGUISeeming().setSelectedIndex(3);
                break;
            }
        }
    }
//ERİŞİM YÖNTEMLERİ:
    /*public JLabel getLblLanguage() {
        if (lblLanguage == null){
            lblLanguage = new JLabel("Dil:");
        }
        return lblLanguage;
    }

    public void setLblLanguage(JLabel lblLanguage) {
        this.lblLanguage = lblLanguage;
    }*/
    public JLabel getLblLog() {
        if (lblLog == null){
            lblLog = new JLabel("Sistem hareket kaydı tut:");
        }
        return lblLog;
    }
    public void setLblLog(JLabel lblLog) {
        this.lblLog = lblLog;
    }
    public JLabel getLblLogLocation() {//not ve kayıtların onumu her zaman seçilebilr olmalı ve default değeri olmalı
        if (lblLogLocation == null){
            lblLogLocation = new JLabel("Not ve kayıtların konumu:");
        }
        return lblLogLocation;
    }
    public void setLblLogLocation(JLabel lblLogLocation) {
        this.lblLogLocation = lblLogLocation;
    }
    public JLabel getLblGUISeeming() {
        if (lblGUISeeming == null){
            lblGUISeeming = new JLabel("Görünüm:");
        }
        return lblGUISeeming;
    }
    public void setLblGUISeeming(JLabel lblGUISeeming) {
        this.lblGUISeeming = lblGUISeeming;
    }
    public JLabel getLblAutosave() {
        if (lblAutosave == null){
            lblAutosave = new JLabel("Not yazarken otomatik kaydet:");
        }
        return lblAutosave;
    }
    public void setLblAutosave(JLabel lblAutosave) {
        this.lblAutosave = lblAutosave;
    }
    public JCheckBox getChboxIsLogging() {
        if (chboxIsLogging == null){
            chboxIsLogging = new JCheckBox();
        }
        return chboxIsLogging;
    }
    public void setChboxIsLogging(JCheckBox chboxIsLogging) {
        this.chboxIsLogging = chboxIsLogging;
    }
    public JCheckBox getChboxIsAutosave() {
        if (chboxIsAutosave == null){
            chboxIsAutosave = new JCheckBox();
        }
        return chboxIsAutosave;
    }
    public void setChboxIsAutosave(JCheckBox CheckB2_isAutosave) {
        this.chboxIsAutosave = CheckB2_isAutosave;
    }
    /*public JComboBox getCmboxLanguage() {
        if (cmboxLanguage == null){
            cmboxLanguage = new JComboBox(new String[]{"TÜRKÇE"});//DATABASE'den AL
            cmboxLanguage.setPreferredSize(new Dimension(100, 25));
        }
        return cmboxLanguage;
    }

    public void setCmboxLanguage(JComboBox cmboxLanguage) {
        this.cmboxLanguage = cmboxLanguage;
    }*/
    public JComboBox getCmboxGUISeeming() {
        if (cmboxGUISeeming == null){
            cmboxGUISeeming = new JComboBox();
            cmboxGUISeeming.setPreferredSize(new Dimension(100, 25));
            cmboxGUISeeming.setModel( getColormodel() ) ;
            cmboxGUISeeming.setFont(new Font("Times New Roman", Font.BOLD, 13));
            cmboxGUISeeming.setRenderer(new ListCellRender() ) ;
            cmboxGUISeeming.setSelectedIndex(0);
            cmboxGUISeeming.addMouseListener(getMovementsActs());
        }
        return cmboxGUISeeming;
    }
    public void setCmboxGUISeeming(JComboBox cmboxGUISeeming) {
        this.cmboxGUISeeming = cmboxGUISeeming;
    }
    public JButton getBtnChooseLocation() {
        if(btnChooseLocation == null){
            btnChooseLocation = new JButton("Gözat");
            btnChooseLocation.setPreferredSize(new Dimension(100, 25));
            btnChooseLocation.addActionListener( getActs() ) ;
            btnChooseLocation.addMouseListener(getMovementsActs());
        }
        return btnChooseLocation;
    }
    public void setBtnChooseLocation(JButton btnChooseLocation) {
        this.btnChooseLocation = btnChooseLocation;
    }
    public JButton getBtnSave() {
        if(btnSave == null){
            btnSave = new JButton("KAYDET");
            btnSave.setPreferredSize(new Dimension(100, 25));
            btnSave.addActionListener( getActs() ) ;
            btnSave.addMouseListener(getMovementsActs());
        }
        return btnSave;
    }
    public void setBtnSave(JButton btnSave) {
        this.btnSave = btnSave;
    }
    public JButton getBtnExit() {
        if(btnExit == null){
            btnExit = new JButton("Kaydetmeden çık");
            btnExit.setPreferredSize(new Dimension(100, 25));
            btnExit.addActionListener( getActs() ) ;
            btnExit.addMouseListener(getMovementsActs());
        }
        return btnExit;
    }
    public void setBtnExit(JButton btnExit) {
        this.btnExit = btnExit;
    }
    public JFileChooser getChooser() {
        if(chooser == null){
            chooser = new JFileChooser() ;
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            chooser.addActionListener( getActs() ) ;
            chooser.setAcceptAllFileFilterUsed( false ) ;
            GUISeeming.appGUI(chooser);
        }
        return chooser;
    }

    public void setChooser(JFileChooser chooser) {
        this.chooser = chooser;
    }
    public ActOnOptions getActs() {
        if(acts == null){
            acts = new ActOnOptions(this);
        }
        return acts;
    }
    public void setActs(ActOnOptions acts) {
        this.acts = acts;
    }
    public Movements getMovementsActs() {
        if(movementsActs == null){
            movementsActs = new Movements();
        }
        return movementsActs;
    }
    public void setMovementsActs(Movements movementsActs) {
        this.movementsActs = movementsActs;
    }
    public GridBagLayout getLayoutMain() {
        if(layoutMain == null){
            layoutMain = new GridBagLayout();
        }
        return layoutMain;
    }
    public void setLayoutMain(GridBagLayout layoutMain) {
        this.layoutMain = layoutMain;
    }

//DİĞER YÖNTEMLER:
    DefaultComboBoxModel getColormodel(){
        DefaultComboBoxModel model= new DefaultComboBoxModel();
        model.addElement(new ColorInf(new ImageIcon(getClass().getResource("GUISeemingSymbols/01Standard.png")),"Ortalama"));
        model.addElement(new ColorInf(new ImageIcon(getClass().getResource("GUISeemingSymbols/02Blue.png")),"Mavi"));
        model.addElement(new ColorInf(new ImageIcon(getClass().getResource("GUISeemingSymbols/03Pink.png")),"Pembe"));
        model.addElement(new ColorInf(new ImageIcon(getClass().getResource("GUISeemingSymbols/04Dark.png")),"Koyu"));
        return model;
    }
}
