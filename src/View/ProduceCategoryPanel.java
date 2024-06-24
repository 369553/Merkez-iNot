package View;

import Base.GUISeeming;
import Control.ActsOnCategoryAdding;
import Control.KeySignalControl;
import Control.Movements;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.DEFAULT_OPTION;
import static javax.swing.JOptionPane.NO_OPTION;
import static javax.swing.JOptionPane.PLAIN_MESSAGE;
import static javax.swing.JOptionPane.QUESTION_MESSAGE;
import static javax.swing.JOptionPane.showOptionDialog;
import javax.swing.JPanel;
import javax.swing.JComboBox;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ProduceCategoryPanel extends JOptionPane{
    MainPanel MP;
    GridBagLayout layoutForThis;
    JPanel pnlBase;
    JButton btnColorChooser, btnAdd;
    JLabel lblCategoryName, lblColor, lblSymbol, lblExplanation;
    JColorChooser clrchooserCategory;
    JTextArea txtaExplanation;
    JTextField txtCategoryName;
    JComboBox cmboxSymbols;
    ActsOnCategoryAdding allActions;
    Movements movementActs;
    KeySignalControl keySignals;

    public ProduceCategoryPanel(MainPanel MP){
        super("YENİ", PLAIN_MESSAGE, DEFAULT_OPTION);
        this.MP = MP;
        GUISeeming.appGUI(this);
        openScreen();
        getTxtCategoryName().requestFocus();
    }
    
    public void openScreen(){
        showOptionDialog(MainFrame.getFrame_Main(), "", "", NO_OPTION, QUESTION_MESSAGE,
            new ImageIcon(), 
            new Object[]{getPnlBase()}, null);
    }
    
    
    
    public void addInterfaceObjects(){
        Add.ADDCOMP(pnlBase, getLblCategoryName(), 0, 0, 1, 1, Add.Insetser(2, 5, 5, 5), GridBagConstraints.BOTH, 1.0, 2.0);
        Add.ADDCOMP(pnlBase, getLblColor(), 0, 1, 1, 1, Add.Insetser(2, 5, 5, 5), GridBagConstraints.BOTH, 0.3, 1.0);
        Add.ADDCOMP(pnlBase, getLblExplanation(), 0, 2, 1, 1, Add.Insetser(2, 5, 5, 5), GridBagConstraints.BOTH, 0.3, 1.0);
    //    Add.ADDCOMP(pnlBase, getLblSymbol(), 0, 4, 1, 1, Add.Insetser(2, 5, 5, 5), GridBagConstraints.BOTH, 0.3, 1.0);
    //    Add.ADDCOMP(pnlBase, getCmboxSymbols(), 1, 4, 1, 1, Add.Insetser(5, 5, 5, 5), GridBagConstraints.BOTH, 1.0, 1.0);
        Add.ADDCOMP(pnlBase, getTxtCategoryName(), 1, 0, 1, 1, Add.Insetser(5, 5, 5, 5), GridBagConstraints.BOTH, 1.0, 1.0);
        Add.ADDCOMP(pnlBase, getBtnColorChooser(), 1, 1, 1, 1, Add.Insetser(5, 5, 5, 5), GridBagConstraints.BOTH, 1.0, 1.0);
        Add.ADDCOMP(pnlBase, getTxtExplanation(), 1, 2, 1, 2, Add.Insetser(5, 5, 5, 5), GridBagConstraints.BOTH, 3.0, 3.0);
        Add.ADDCOMP(pnlBase, getBtnAdd(), 0, 5, 2, 1, Add.Insetser(5, 5, 2, 5), GridBagConstraints.BOTH, 1.0, 1.0);
    }
    public void resetPanel(){
        if(txtCategoryName != null){
            txtCategoryName.setText("");
            txtaExplanation.setText("");
    //        cmboxSymbols.setSelectedIndex(0);
        }
    }
    public Color openColorChooser(){
        return JColorChooser.showDialog(MP, "Renk seçimi", Color.decode("#000000"));
    }
    //ERİŞİM YÖNTEMLERİ:
    public JPanel getPnlBase() {
        if(pnlBase == null){
            pnlBase = new JPanel();
            pnlBase.setLayout(getLayoutForThis());
            pnlBase.setPreferredSize(new Dimension((2*(MainFrame.getFrame_Main().getWidth()/3)), (2*(MainFrame.getFrame_Main().getHeight()/3))));
            addInterfaceObjects();
        }
        return pnlBase;
    }

    public void setPnlBase(JPanel pnlBase) {
        this.pnlBase = pnlBase;
    }

    public JLabel getLblCategoryName() {
        if(lblCategoryName == null){
            lblCategoryName = new JLabel("Kategori ismi: ");
            lblCategoryName.setPreferredSize(new Dimension(70, 30));
        }
        return lblCategoryName;
    }

    public void setLblCategoryName(JLabel lblCategoryName) {
        this.lblCategoryName = lblCategoryName;
    }

    public JLabel getLblColor() {
        if(lblColor == null){
            lblColor = new JLabel("Renk: ");
            lblColor.setPreferredSize(new Dimension(70, 30));
        }
        return lblColor;
    }

    public void setLblColor(JLabel lblColor) {
        this.lblColor = lblColor;
    }

    public JLabel getLblSymbol() {
        if(lblSymbol == null){
            lblSymbol = new JLabel("Simge: ");
            lblSymbol.setPreferredSize(new Dimension(70, 30));
        }
        return lblSymbol;
    }

    public void setLblSymbol(JLabel lblSymbol) {
        this.lblSymbol = lblSymbol;
    }

    public JLabel getLblExplanation() {
        if(lblExplanation == null){
            lblExplanation = new JLabel("Kategori açıklaması: ");
            lblExplanation.setPreferredSize(new Dimension(70, 30));
        }
        return lblExplanation;
    }

    public void setLblExplanation(JLabel lblExplanation) {
        this.lblExplanation = lblExplanation;
    }

    public JColorChooser getClrchooserCategory() {
        if(clrchooserCategory == null){
            clrchooserCategory = new JColorChooser();
        }
        return clrchooserCategory;
    }

    public void setClrchooserCategory(JColorChooser clrchooserCategory) {
        this.clrchooserCategory = clrchooserCategory;
    }

    public JComboBox getCmboxSymbols() {
        if(cmboxSymbols == null){
            cmboxSymbols = new JComboBox();
            cmboxSymbols.setPreferredSize(new Dimension(70, 30));
            cmboxSymbols.addMouseListener(getMovementActs());
        }
        return cmboxSymbols;
    }

    public void setCmboxSymbols(JComboBox cmboxSymbols) {
        this.cmboxSymbols = cmboxSymbols;
    }

    public GridBagLayout getLayoutForThis() {
        if(layoutForThis == null){
            layoutForThis = new GridBagLayout();
        }
        return layoutForThis;
    }

    public void setLayoutForThis(GridBagLayout layoutForThis) {
        this.layoutForThis = layoutForThis;
    }

    public JButton getBtnColorChooser() {
        if(btnColorChooser == null){
            btnColorChooser = new JButton("Renk seçin");
            btnColorChooser.addActionListener(getAllActions());
            btnColorChooser.addMouseListener(getMovementActs());
        }
        return btnColorChooser;
    }

    public void setBtnColorChooser(JButton btnColorChooser) {
        this.btnColorChooser = btnColorChooser;
    }

    public JButton getBtnAdd() {
        if(btnAdd == null){
            btnAdd = new JButton("Kategori ekle");
            btnAdd.addActionListener(getAllActions());
            btnAdd.setPreferredSize(new Dimension(70, 30));
            btnAdd.setSize(new Dimension(70, 30));
            btnAdd.addMouseListener(getMovementActs());
        }
        return btnAdd;
    }

    public void setBtnAdd(JButton btnAdd) {
        this.btnAdd = btnAdd;
    }

    public JTextArea getTxtExplanation() {
        if(txtaExplanation == null){
            txtaExplanation = new JTextArea(3, 10);
            txtaExplanation.setPreferredSize(new Dimension(70, 30));
        }
        return txtaExplanation;
    }

    public void setTxtaExplanation(JTextArea txtaExplanation) {
        this.txtaExplanation = txtaExplanation;
    }

    public JTextField getTxtCategoryName() {
        if(txtCategoryName == null){
            txtCategoryName = new JTextField();
            txtCategoryName.setPreferredSize(new Dimension(70, 30));
            txtCategoryName.addKeyListener(getKeySignals());
        }
        return txtCategoryName;
    }

    public void setTxtCategoryName(JTextField txtCategoryName) {
        if(txtCategoryName == null){
            txtCategoryName = new  JTextField();
        }
        this.txtCategoryName = txtCategoryName;
    }

    public ActsOnCategoryAdding getAllActions() {
        if(allActions == null){
            allActions = new ActsOnCategoryAdding(this);
        }
        return allActions;
    }

    public void setAllActions(ActsOnCategoryAdding allActions) {
        this.allActions = allActions;
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
    public KeySignalControl getKeySignals() {
        if(keySignals == null){
            keySignals = new KeySignalControl("category");
        }
        return keySignals;
    }
}
    
