package View;

import Base.DataBase;
import Base.GUISeeming;
import Base.Note;
import Base.NoteTag;
import Control.ActOnPnlManageTags;
import Control.Movements;
import Services.Lister;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.NO_OPTION;
import static javax.swing.JOptionPane.QUESTION_MESSAGE;
import static javax.swing.JOptionPane.showOptionDialog;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;

public class PnlManageTags extends JOptionPane{
    JList<String> liTags, liNotes, liTagNotes;
    JScrollPane scrpaneTags, scrpaneTagNotes, scrpaneAllNotes;
    JButton btndeleteTag, btnRemoveTag, btnAddTagToNotes;
    JLabel lblTags, lblTagNotes, lblAllNotes;
    ActOnPnlManageTags act;
    Movements mov;
    public NoteTag choosedTag;
    DefaultListModel<String> mdlliTags, mdlliNotes, mdlliTagNotes;
    GridBagLayout compOrder;
    JPanel pnlMain;
    private MainPanel MP;

    public PnlManageTags(MainPanel MP) {
        super("", PLAIN_MESSAGE, NO_OPTION);
        this.setLayout(getCompOrder());
        this.MP = MP;
        addGUIComponents();
        liTags.setSelectedIndex(0);
        chooseTag();
        GUISeeming.appGUI(this);
        openScreen();
        getScrpaneTags().requestFocus();
    }

//İŞLEM YÖNTEMLERİ:
    private void addGUIComponents(){
        Add.ADDCOMP(getPnlMain(), getLblTags(), 0, 0, 1, 1, Add.Insetser(2, 0, 5, 5), GridBagConstraints.CENTER, 0.0, 0.1);
        Add.ADDCOMP(getPnlMain(), getScrpaneTags(), 1, 0, 2, 2, Add.Insetser(2, 5, 5, 2), GridBagConstraints.BOTH, 1.0, 0.1);
        Add.ADDCOMP(getPnlMain(), getBtndeleteTag(), 0, 1, 1, 1, Add.Insetser(2, 5, 5, 5), GridBagConstraints.BOTH, 0.3, 0.1);
        Add.ADDCOMP(getPnlMain(), getLblTagNotes(), 0, 2, 1, 1, Add.Insetser(5, 2, 5, 5), GridBagConstraints.CENTER, 0.0, 0.1);
        Add.ADDCOMP(getPnlMain(), getScrpaneTagNotes(), 1, 2, 2, 2, Add.Insetser(5, 5, 5, 2), GridBagConstraints.BOTH, 1.0, 0.1);
        Add.ADDCOMP(getPnlMain(), getBtnRemoveTag(), 0, 3, 1, 1, Add.Insetser(5, 2, 5, 5), GridBagConstraints.BOTH, 0.3, 0.1);
        Add.ADDCOMP(getPnlMain(), getLblAllNotes(), 1, 4, 1, 1, Add.Insetser(5, 2, 5, 2), GridBagConstraints.CENTER, 0.0, 0.1);
        Add.ADDCOMP(getPnlMain(), getScrpaneAllNotes(), 0, 5, 3, 2, Add.Insetser(5, 5, 5, 2), GridBagConstraints.BOTH, 1.0, 0.1);
        Add.ADDCOMP(getPnlMain(), getBtnAddTagToNotes(), 0, 7, 3, 2, Add.Insetser(5, 5, 2, 2), GridBagConstraints.BOTH, 0.3, 0.1);
    }
    public void openScreen(){
        showOptionDialog(MainFrame.getFrame_Main(), "", "",
                NO_OPTION, QUESTION_MESSAGE,
                new ImageIcon(getClass().getResource("InterfaceSymbols//symbolNote.png")), 
                new Object[]{(Object) getPnlMain()}, null);
    }
    public void refreshLiTagNotes(){
        getMdlliTagNotes().removeAllElements();
        prepareModelOfLiTagNotes();
    }
    public void refreshLiTags(){
        getMdlliTags().removeAllElements();
        prepareModelOfLiTags();
        if(getLiTags().getSelectedValue() != null)
            getLiTags().setSelectedIndex(0);
        chooseTag();
    }
    public void refreshLiNotes(){
        getMdlliNotes().removeAllElements();
        prepareModelOfLiNotes();
    }
    public void chooseTag(){
        if(getLiTags().getSelectedValue() == null)
            choosedTag = null;
        else
            choosedTag = DataBase.getDatabase().getNoteTagByName(getLiTags().getSelectedValue());
        this.refreshLiTagNotes();
    }
    private void prepareModelOfLiTags(){
        String[] noteTagNames = DataBase.getDatabase().getNoteTagNames();
        if(noteTagNames == null)
            return;
        noteTagNames = Lister.getLister().listStringByAlphabeticalOrder(noteTagNames);
        for(int index = 0; index < noteTagNames.length; index++){
            getMdlliTags().addElement(noteTagNames[index]);
        }
    }
    private void prepareModelOfLiNotes(){
        String[] strValues = new String[DataBase.getDatabase().getNoteIDs().length];
        for(int index = 0; index < strValues.length; index++){
            strValues[index] = DataBase.getDatabase().getNoteNamesWithCategoryNames()[index][0] +
                    " : " + DataBase.getDatabase().getNoteNamesWithCategoryNames()[index][1];
        }
        String[] strList = Lister.getLister().listStringByAlphabeticalOrder(strValues);
        for(int index = 0; index < strValues.length; index++){
            getMdlliNotes().add(index, strList[index]);
        }
    }
    private void prepareModelOfLiTagNotes(){
        if(choosedTag == null)
            return;
    //    System.err.println("seçili etiket var");
        String[] notesWithCategory = new String[choosedTag.getNumberOfNotes()];
        for(int index = 0; index < choosedTag.getNumberOfNotes(); index++){
            Note note = DataBase.getDatabase().findNoteByID(choosedTag.getNoteIDs().get(index));
            notesWithCategory[index] = note.getName() + " : " + note.getCategory().getName();
        }
        notesWithCategory = Lister.getLister().listStringByAlphabeticalOrder(notesWithCategory);
        for(int index = 0; index < notesWithCategory.length; index++){
            getMdlliTagNotes().addElement(notesWithCategory[index]);
        }
    }

//ERİŞİM YÖNTEMLERİ:
    public JList<String> getLiTags() {
        if(liTags == null){
            liTags = new JList<>();
            liTags.addListSelectionListener(getAct());
            prepareModelOfLiTags();
            liTags.setModel(getMdlliTags());
            liTags.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        }
        return liTags;
    }
    public JList<String> getLiNotes() {
        if(liNotes == null){
            liNotes = new JList(getMdlliNotes());
            prepareModelOfLiNotes();
            liNotes.setModel(getMdlliNotes());
        }
        return liNotes;
    }
    public JList<String> getLiTagNotes() {
        if(liTagNotes == null){
            liTagNotes = new JList<>();
            prepareModelOfLiTagNotes();
            liTagNotes.setModel(getMdlliTagNotes());
        }
        return liTagNotes;
    }
    public JScrollPane getScrpaneTags() {
        if(scrpaneTags == null){
            scrpaneTags = new JScrollPane(getLiTags());
            scrpaneTags.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            scrpaneTags.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
            scrpaneTags.setPreferredSize(new Dimension(260, 70));
        }
        return scrpaneTags;
    }
    public JScrollPane getScrpaneTagNotes() {
        if(scrpaneTagNotes == null){
            scrpaneTagNotes = new JScrollPane(getLiTagNotes());
            scrpaneTagNotes.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            scrpaneTagNotes.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
            scrpaneTagNotes.setPreferredSize(new Dimension(260, 70));
        }
        return scrpaneTagNotes;
    }
    public JScrollPane getScrpaneAllNotes() {
        if(scrpaneAllNotes == null){
            scrpaneAllNotes = new JScrollPane(getLiNotes());
            scrpaneAllNotes.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            scrpaneAllNotes.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
            scrpaneAllNotes.setPreferredSize(new Dimension(290, 70));
        }
        return scrpaneAllNotes;
    }
    public JButton getBtndeleteTag() {
        if(btndeleteTag == null){
            btndeleteTag = new JButton("Seçili etiketi sil");
            btndeleteTag.addMouseListener(getMov());
            btndeleteTag.addActionListener(getAct());
        }
        return btndeleteTag;
    }
    public JButton getBtnRemoveTag() {
        if(btnRemoveTag == null){
            btnRemoveTag = new JButton("Bu nottan seçili etiketi kaldır");
            btnRemoveTag.addMouseListener(getMov());
            btnRemoveTag.addActionListener(getAct());
        }
        return btnRemoveTag;
    }
    public JButton getBtnAddTagToNotes() {
        if(btnAddTagToNotes == null){
            btnAddTagToNotes = new JButton("Seçili notlara seçili etiketi ekle");
            btnAddTagToNotes.addMouseListener(getMov());
            btnAddTagToNotes.addActionListener(getAct());
            btnAddTagToNotes.setPreferredSize(new Dimension(60, 35));
        }
        return btnAddTagToNotes;
    }
    public JLabel getLblTags() {
        if(lblTags == null){
            lblTags = new JLabel("Etiketler:");
        }
        return lblTags;
    }
    public JLabel getLblTagNotes() {
        if(lblTagNotes == null){
            lblTagNotes = new JLabel("Etiketin bulunduğu notlar:");
        }
        return lblTagNotes;
    }
    public JLabel getLblAllNotes() {
        if(lblAllNotes == null){
            lblAllNotes = new JLabel("Tüm notlar:");
        }
        return lblAllNotes;
    }
    public ActOnPnlManageTags getAct() {
        if(act == null){
            act = new ActOnPnlManageTags(this);
        }
        return act;
    }
    public Movements getMov() {
        if(mov == null){
            mov = new Movements();
        }
        return mov;
    }
    public DefaultListModel<String> getMdlliNotes() {
        if(mdlliNotes == null){
            mdlliNotes = new DefaultListModel<String>();
        }
        return mdlliNotes;
    }
    public DefaultListModel<String> getMdlliTagNotes() {
        if(mdlliTagNotes == null){
            mdlliTagNotes = new DefaultListModel<String>();
        }
        return mdlliTagNotes;
    }
    public DefaultListModel<String> getMdlliTags() {
        if(mdlliTags == null){
            mdlliTags = new DefaultListModel<String>();
        }
        return mdlliTags;
    }
    public NoteTag getChoosedTag() {
        return choosedTag;
    }
    public void setChoosedTag(NoteTag tag){
        this.choosedTag = tag;
    }
    public GridBagLayout getCompOrder() {
        if(compOrder == null){
            compOrder = new GridBagLayout();
        }
        return compOrder;
    }
    public JPanel getPnlMain() {
        if(pnlMain == null){
            pnlMain = new JPanel(getCompOrder());
        }
        return pnlMain;
    }
}