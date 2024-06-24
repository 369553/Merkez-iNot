package View;

import Base.DataBase;
import Base.GUISeeming;
import Base.Note;
import Control.ActsOnNoteAdding;
import Control.IDARE;
import Control.KeySignalControl;
import Control.Movements;
import Services.Lister;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.DEFAULT_OPTION;
import static javax.swing.JOptionPane.PLAIN_MESSAGE;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class ProduceNotePanel extends JOptionPane{
    DefaultListModel<String> mdlLiTags, mdlLiCurrentTags;
    ActsOnNoteAdding acts;
    KeySignalControl keySignals;
    public MainPanel MP;
    Note note;
    JPanel pnlMain;
    JList<String> liTags, liCurrentTags;
    JTextField txtName;
    JScrollPane scrpaneTags, scrPaneCurrentTags;
    JComboBox cmboxCategories;
    JButton btnAdd, btnAddTag, btnDelTag;
    JLabel lblUser, lblAdd, lblName, lblCategory;
    GridBagLayout layout;
    Movements movementActs;
//    ArrayList<NoteTag> tags; Sonra ekle
    
    public ProduceNotePanel(MainPanel MP){
        super("YENİ", PLAIN_MESSAGE, DEFAULT_OPTION);
        this.MP = MP;
        GUISeeming.appGUI(this);
        openScreen();
        getTxtName().requestFocus();
    }

//İŞLEM YÖNTEMLERİ:
    public void openScreen(){
        showOptionDialog(MainFrame.getFrame_Main(), "", "",
                NO_OPTION, QUESTION_MESSAGE,
                new ImageIcon(), 
                new Object[]{(Object) PnlADD()}, null);
    }
    public JPanel PnlADD(){
        if(pnlMain == null){
            pnlMain = new JPanel();
            pnlMain.setLayout(getLayoutPanel());
            pnlMain.setPreferredSize(new Dimension((2*(MainFrame.getFrame_Main().getWidth()/3)), (2*(MainFrame.getFrame_Main().getHeight()/3))));
            addInterfaceObjects();
        }
        return pnlMain;
    }
    public void addInterfaceObjects(){
        if(pnlMain != null){
            Add.ADDCOMP(pnlMain, getLblName(), 0, 0, 1, 1, Add.Insetser(2, 5, 5, 5), GridBagConstraints.HORIZONTAL, 0.3, 1.0);
            Add.ADDCOMP(pnlMain, getTxtName(), 1, 0, 2, 1, Add.Insetser(5, 5, 5, 5), GridBagConstraints.BOTH, 1.0, 1.0);
            Add.ADDCOMP(pnlMain, getLblCategory(), 0, 1, 1, 1, Add.Insetser(5, 5, 5, 5), GridBagConstraints.HORIZONTAL, 0.3, 1.0);
            Add.ADDCOMP(pnlMain, getCmboxCategories(), 1, 1, 2, 1, Add.Insetser(5, 5, 5, 5), GridBagConstraints.BOTH, 1.0, 1.0);
            Add.ADDCOMP(pnlMain, getBtnAddTag(), 0, 2, 1, 1, Add.Insetser(5, 5, 5, 5), GridBagConstraints.BOTH, 0.3, 1.0);
            Add.ADDCOMP(pnlMain, getBtnDelTag(), 0, 4, 1, 1, Add.Insetser(5, 5, 5, 5), GridBagConstraints.BOTH, 0.3, 1.0);
            Add.ADDCOMP(pnlMain, getScrpaneTags(), 1, 2, 2, 2, Add.Insetser(5, 5, 5, 5), GridBagConstraints.BOTH, 0.3, 1.0);
            Add.ADDCOMP(pnlMain, getScrPaneCurrentTags(), 1, 4, 2, 2, Add.Insetser(5, 5, 5, 5), GridBagConstraints.BOTH, 0.3, 1.0);
            Add.ADDCOMP(pnlMain, getBtnAdd(), 0, 6, 3, 1, Add.Insetser(5, 5, 5, 5), GridBagConstraints.BOTH, 0.3, 1.0);
        }
    }
    public void resetPanel(){
        if(txtName != null){
            txtName.setText("");
            cmboxCategories.setSelectedIndex(0);
            mdlLiCurrentTags.removeAllElements();
            mdlLiTags.removeAllElements();
            String[] namesOfTags = DataBase.getDatabase().getNoteTagNames();
            for(int index = 0; index < DataBase.getDatabase().getNoteTags().size(); index++){
                mdlLiTags.add(index, namesOfTags[index]);
            }
        }
    }

    //ERİŞİM YÖNTEMLERİ:
    public JTextField getTxtName() {
        if(txtName == null){
            txtName = new JTextField("");
            txtName.setPreferredSize(new Dimension(70, 40));
            txtName.addKeyListener(getKeySignals());
        }
        return txtName;
    }
    public void setTxtName(JTextField txtName) {
        this.txtName = txtName;
    }
    public MainPanel getMP() {
        return MP;
    }
    public JComboBox getCmboxCategories() {
        if(cmboxCategories == null){
            cmboxCategories = new JComboBox(DataBase.getDatabase().getCategoryNames());
            if(IDARE.getLocationName().split("/").length == 2)
                cmboxCategories.setSelectedItem(IDARE.getLocationName().split("/")[1]);
            cmboxCategories.setPreferredSize(new Dimension(70, 30));
            cmboxCategories.addMouseListener(getMovementActs());
        }
        return cmboxCategories;
    }
    public void setCmboxCategories(JComboBox cmboxCategories) {
        this.cmboxCategories = cmboxCategories;
    }
    public JButton getBtnAdd() {
        if(btnAdd == null){
            btnAdd = new JButton("EKLE");
            btnAdd.setPreferredSize(new Dimension(170, 30));
            btnAdd.addActionListener(getActs());
            btnAdd.addMouseListener(getMovementActs());
        }
        return btnAdd;
    }
    public void setBtnAdd(JButton btnAdd) {
        this.btnAdd = btnAdd;
    }
    public JButton getBtnAddTag() {
        if(btnAddTag == null){
            btnAddTag = new JButton("Etiket ekle");
            btnAddTag.setPreferredSize(new Dimension(70, 30));
            btnAddTag.addActionListener(getActs());
            btnAddTag.addMouseListener(getMovementActs());
        }
        return btnAddTag;
    }
    public void setBtnAddTag(JButton btnAddTag) {
        this.btnAddTag = btnAddTag;
    }
    public JButton getBtnDelTag() {
        if(btnDelTag == null){
            btnDelTag = new JButton("Etiketi sil");
            btnDelTag.setPreferredSize(new Dimension(70, 30));
            btnDelTag.addActionListener(getActs());
            btnDelTag.addMouseListener(getMovementActs());
        }
        return btnDelTag;
    }
    public void setBtnDelTag(JButton btnDelTag) {
        this.btnDelTag = btnDelTag;
    }
    public JLabel getLblName() {
        if(lblName == null){
            lblName = new JLabel("İsim:");
            lblName.setPreferredSize(new Dimension(70, 30));
        }
        return lblName;
    }
    public void setLblName(JLabel lblName) {
        this.lblName = lblName;
    }
    public JLabel getLblCategory() {
        if(lblCategory == null){
            lblCategory = new JLabel("Kategori:");
            lblCategory.setPreferredSize(new Dimension(70, 30));
        }
        return lblCategory;
    }
    public void setLblCategory(JLabel lblCategory) {
        this.lblCategory = lblCategory;
    }
    public JList<String> getLiCurrentTags() {
        if(liCurrentTags == null){
            liCurrentTags = new JList<String>(getMdlLiCurrentTags());
        }
        return liCurrentTags;
    }
    public void setLiCurrentTags(JList<String> liCurrentTags) {
        this.liCurrentTags = liCurrentTags;
    }
    public JList<String> getLiTags() {
        if(liTags == null){
            liTags = new JList<String>(getMdlLiTags());
        }
        return liTags;
    }
    public void setLiTags(JList<String> liTags) {
        this.liTags = liTags;
    }
    public DefaultListModel<String> getMdlLiCurrentTags() {
        if(mdlLiCurrentTags == null){
            mdlLiCurrentTags = new DefaultListModel<String>();
        }
        return mdlLiCurrentTags;
    }
    public void setMdlLiCurrentTags(DefaultListModel<String> mdlLiTags) {
        this.mdlLiCurrentTags = mdlLiTags;
    }
    public DefaultListModel<String> getMdlLiTags() {
        if(mdlLiTags == null){
            mdlLiTags = new DefaultListModel<String>();
        //    String[] namesOfTags = Lister.getLister().listStringByAlphabeticalOrder(DataBase.getDatabase().getNoteTagNames());
            String[] namesOfTags = DataBase.getDatabase().getNoteTagNames();
            for(int index = 0; index < DataBase.getDatabase().getNoteTags().size(); index++){
                mdlLiTags.add(index, namesOfTags[index]);
            }
        }
        return mdlLiTags;
    }
    public void setMdlLiTags(DefaultListModel<String> mdlLiTags) {
        this.mdlLiTags = mdlLiTags;
    }
    public GridBagLayout getLayoutPanel() {
        if(layout == null){
            layout = new GridBagLayout();
        }
        return layout;
    }
    public void setLayout(GridBagLayout layout) {
        this.layout = layout;
    }
    public KeySignalControl getKeySignals() {
        if(keySignals == null){
            keySignals = new KeySignalControl("note");
        }
        return keySignals;
    }
    public ActsOnNoteAdding getActs() {
        if(acts == null){
            acts = new ActsOnNoteAdding(this);
        }
        return acts;
    }
    public void setActs(ActsOnNoteAdding acts) {
        this.acts = acts;
    }
    public JScrollPane getScrpaneTags() {
        if(scrpaneTags == null){
            scrpaneTags = new JScrollPane(getLiTags());
        }
        return scrpaneTags;
    }
    public void setScrpaneTags(JScrollPane scrpaneTags) {
        this.scrpaneTags = scrpaneTags;
    }
    public JScrollPane getScrPaneCurrentTags() {
        if(scrPaneCurrentTags == null){
            scrPaneCurrentTags = new JScrollPane(getLiCurrentTags());
            scrPaneCurrentTags.setPreferredSize(new Dimension(70, 70));
            scrPaneCurrentTags.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            scrPaneCurrentTags.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            scrPaneCurrentTags.getHorizontalScrollBar().addMouseListener(getMovementActs());
            scrPaneCurrentTags.getVerticalScrollBar().addMouseListener(getMovementActs());
            scrPaneCurrentTags.addMouseListener(getMovementActs());
        }
        return scrPaneCurrentTags;
    }
    public void setScrPaneCurrentTags(JScrollPane scrPaneCurrentTags) {
        this.scrPaneCurrentTags = scrPaneCurrentTags;
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