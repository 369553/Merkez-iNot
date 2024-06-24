package View;

import Base.GUISeeming;
import Base.SystemSettings;
import Control.ActsOnPreparingGUISeeming;
import Control.ChangesOnScreenPrepareGUISeeming;
import Control.Movements;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.DEFAULT_OPTION;
import static javax.swing.JOptionPane.PLAIN_MESSAGE;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ScreenPrepareGUISeeming extends JPanel{
    String[] strComponentNames;
    JLabel lblChooseComponent, lblHexCode, lblShowing;
    JComboBox cmboxComponents;
    JButton btnSave, btnCancel;
    GridBagLayout layoutMain;
    JPanel pnlShowingArea;
    JTextField txtHexCodes;
    JOptionPane optpnlGUIName;
    ActsOnPreparingGUISeeming actMain;
    ChangesOnScreenPrepareGUISeeming changeOncmbox;
    Movements movementActs;
    GUISeeming editableGUI;
    
    public ScreenPrepareGUISeeming() {
        setLayout(getLayoutMain());
        Add.setAnchor(GridBagConstraints.CENTER);
        Add.ADDCOMP(this, getLblChooseComponent(), 0, 0, 1, 1, Add.Insetser(5, 5, 2, 5), GridBagConstraints.CENTER, 0.1, 0.1);
        Add.ADDCOMP(this, getLblHexCode(), 0, 1, 1, 1, Add.Insetser(5, 5, 5, 5), GridBagConstraints.CENTER, 0.1, 0.1);
        Add.ADDCOMP(this, getLblShowing(), 0, 2, 2, 1, Add.Insetser(5, 5, 5, 5), GridBagConstraints.CENTER, 0.1, 0.1);
        Add.ADDCOMP(this, getCmboxComponents(), 1, 0, 1, 1, Add.Insetser(2, 5, 5, 5), GridBagConstraints.BOTH, 0.1, 0.1);
        Add.ADDCOMP(this, getTxtHexCodes(), 1, 1, 1, 1, Add.Insetser(5, 5, 5, 5), GridBagConstraints.BOTH, 0.1, 0.1);
        Add.ADDCOMP(this, getPnlShowingArea(), 0, 3, 2, 2, Add.Insetser(2, 5, 2, 5), GridBagConstraints.BOTH, 3, 3);
        Add.ADDCOMP(this, getBtnCancel(), 0, 5, 1, 1, Add.Insetser(2, 5, 2, 5), GridBagConstraints.BOTH, 0.1, 0.1);
        Add.ADDCOMP(this, getBtnSave(), 1, 5, 1, 1, Add.Insetser(2, 5, 2, 5), GridBagConstraints.BOTH, 0.1, 0.1);
        getOptpnlGUIName();
        editableGUI = SystemSettings.getSettings().getCurrentGuiSeeming();
        GUISeeming.appGUI(this);
        getPnlShowingArea().setBackground(Color.red.darker());
    }

//İŞLEM YÖNTEMLERİ:
    public String askGUIName(){
        return getOptpnlGUIName().showInputDialog(this, "Görsel görünüm için isim giriniz");
    }
    
    /*public void drawChosedComponent(){
        switch(this.getCmboxComponents().getSelectedItem().toString()){
            case "backgroundColor":{
            }
            case "buttonColor":{
            }
            case "buttonTextColor":{
            }
            case "buttonOnMouseColor":{
            }
            case "buttonTextOnMouseColor":{
            }
            case "textColor":{
            }
            case "menuColor":{
            }
            case "notingAreaColor":{
            }
            case "notingAreaTextColor":{
            }
            case "notingAreaCursorColor":{
            }
            case "notingAreaSelectedColor":{
            }
            case "notingAreaSelectedTextColor":{
            }
            case "componentBorderColor":{
            }
            case "topMenuGroundBackgroundColor":{
            }
            case "releasebleMenuTextColor":{
            }
            case "releasebleMenuItemColor":{
            }
            case "releasebleMenuItemTextColor":{
            }
            case "releasebleMenuItemOnMouseColor":{
            }
            case "releasebleMenuItemOnMouseTextColor":{
            }
            case "editToolsBackgroundColor":{
            }
        }
    }*/
    
//ERİŞİM YÖNTEMLERİ:
    public String[] getStrComponentNames() {
        if(strComponentNames == null){
            strComponentNames = new String[]{
                "backgroundColor",
                "buttonColor",
                "buttonTextColor",
                "buttonOnMouseColor",
                "buttonTextOnMouseColor",
                "textColor",
                "menuColor",
                "notingAreaColor",
                "notingAreaTextColor",
                "notingAreaCursorColor",
                "notingAreaSelectedColor",
                "notingAreaSelectedTextColor",
                "componentBorderColor",
                "topMenuGroundBackgroundColor",
                "releasebleMenuTextColor",
                "releasebleMenuItemColor",
                "releasebleMenuItemTextColor",
                "releasebleMenuItemOnMouseColor",
                "releasebleMenuItemOnMouseTextColor",
                "editToolsBackgroundColor"
            };
        }
        return strComponentNames;
    }

    public void setStrComponentNames(String[] strComponentNames) {
        this.strComponentNames = strComponentNames;
    }

    public JLabel getLblChooseComponent() {
        if(lblChooseComponent == null){
            lblChooseComponent = new JLabel("Görüntü bileşeni seçiniz: ");
//            lblChooseComponent.setPreferredSize(new Dimension(70, 25));
        }
        return lblChooseComponent;
    }

    public void setLblChooseComponent(JLabel lblChooseComponent) {
        this.lblChooseComponent = lblChooseComponent;
    }

    public JLabel getLblHexCode() {
        if(lblHexCode == null){
            lblHexCode = new JLabel("Renk kodunu (onaltılık): ");
        }
        return lblHexCode;
    }

    public void setLblHexCode(JLabel lblHexCode) {
        this.lblHexCode = lblHexCode;
    }

    public JLabel getLblShowing() {
        if(lblShowing == null){
            lblShowing = new JLabel("Önizleme");
        }
        return lblShowing;
    }

    public void setLblShowing(JLabel lblShowing) {
        this.lblShowing = lblShowing;
    }

    public JComboBox getCmboxComponents() {
        if(cmboxComponents == null){
            cmboxComponents = new JComboBox(getStrComponentNames());
            cmboxComponents.setPreferredSize(new Dimension(170, 35));
            cmboxComponents.setMaximumSize(new Dimension(210, 45));
            cmboxComponents.addMouseListener(getMovementActs());
            cmboxComponents.addItemListener(getChangeOncmbox());
        }
        return cmboxComponents;
    }

    public void setCmboxComponents(JComboBox cmboxComponents) {
        this.cmboxComponents = cmboxComponents;
    }

    public JButton getBtnSave() {
        if(btnSave == null){
            btnSave = new JButton("KAYDET");
            btnSave.setPreferredSize(new Dimension(70, 35));
            btnSave.addActionListener(getActMain());
            btnSave.addMouseListener(getMovementActs());
        }
        return btnSave;
    }

    public void setBtnSave(JButton btnSave) {
        this.btnSave = btnSave;
    }

    public JButton getBtnCancel() {
        if(btnCancel == null){
            btnCancel = new JButton("İPTAL ET");
            btnCancel.addActionListener(getActMain());
            btnCancel.setPreferredSize(new Dimension(70, 35));
            btnCancel.addMouseListener(getMovementActs());
        }
        return btnCancel;
    }

    public void setBtnCancel(JButton btnCancel) {
        this.btnCancel = btnCancel;
    }

    public JPanel getPnlShowingArea() {
        if(pnlShowingArea == null){
            pnlShowingArea = new JPanel(new BorderLayout());
            pnlShowingArea.setPreferredSize(new Dimension(270, 225));
//            drawChosedComponent();
        }
        return pnlShowingArea;
    }

    public void setPnlShowingArea(JPanel pnlShowingArea) {
        this.pnlShowingArea = pnlShowingArea;
    }

    public JTextField getTxtHexCodes() {
        if(txtHexCodes == null){
            txtHexCodes = new JTextField();
            txtHexCodes.setPreferredSize(new Dimension(170, 35));
            //txtHexCodes.set İpucu yazısı: hintText = ?
        }
        return txtHexCodes;
    }

    public void setTxtHexCodes(JTextField txtHexCodes) {
        this.txtHexCodes = txtHexCodes;
    }

    public JOptionPane getOptpnlGUIName() {
        if(optpnlGUIName == null){
            optpnlGUIName = new JOptionPane("Görsel görünüm ismi giriniz", PLAIN_MESSAGE, DEFAULT_OPTION);
        }
        return optpnlGUIName;
    }

    public void setOptpnlGUIName(JOptionPane optpnlGUIName) {
        this.optpnlGUIName = optpnlGUIName;
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

    public GUISeeming getEditableGUI() {
        return editableGUI;
    }

    public void setEditableGUI(GUISeeming editableGUI) {
        this.editableGUI = editableGUI;
    }

    public ActsOnPreparingGUISeeming getActMain() {
        if(actMain == null){
            actMain = new ActsOnPreparingGUISeeming(this);
        }
        return actMain;
    }

    public void setActMain(ActsOnPreparingGUISeeming actMain) {
        this.actMain = actMain;
    }

    public ChangesOnScreenPrepareGUISeeming getChangeOncmbox() {
        if(changeOncmbox == null){
            changeOncmbox = new ChangesOnScreenPrepareGUISeeming(this);
        }
        return changeOncmbox;
    }

    public void setChangeOncmbox(ChangesOnScreenPrepareGUISeeming changeOncmbox) {
        this.changeOncmbox = changeOncmbox;
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
