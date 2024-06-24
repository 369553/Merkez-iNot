package View;

import Base.DataBase;
import Base.GUISeeming;
import Control.ActsOnNoteTagAdding;
import Control.KeySignalControl;
import Control.Movements;
import Services.Lister;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.NO_OPTION;
import static javax.swing.JOptionPane.QUESTION_MESSAGE;
import static javax.swing.JOptionPane.showOptionDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class ProduceNoteTag extends JOptionPane{
    private MainPanel MP;
    JLabel lblName, lblNotes, lblTags;
    JButton btnComplete, btnAddToNote, btnSubstractFromNote;
    JList liNotes, liCurrentNotes;
    JScrollPane scrpaneNotes, scrpaneCurrentNotes;
    JPanel pnlScreen;
    JTextField txtName;
    Movements mov;
    ActsOnNoteTagAdding act;
    KeySignalControl keySignal;
    DefaultListModel<String> mdlListModelForCurrentNotes, mdlListModelForNotes;
    
    public ProduceNoteTag(MainPanel MP) {
        super("YENİ", PLAIN_MESSAGE, DEFAULT_OPTION);
        this.MP = MP;
        GUISeeming.appGUI(this);
        openScreen();
        getTxtName().requestFocus();
    }

//İŞLEM YÖNTEMLERİ:
    public void addInterfaceObjects(){
        if(pnlScreen != null){
            Add.ADDCOMP(getPnlScreen(), getLblName(), 0, 0, 1, 1, Add.Insetser(2, 5, 5, 5), GridBagConstraints.HORIZONTAL, 0.3, 1.0);
            Add.ADDCOMP(getPnlScreen(), getTxtName(), 1, 0, 1, 1, Add.Insetser(5, 5, 5, 5), GridBagConstraints.HORIZONTAL, 1.0, 1.0);
            Add.ADDCOMP(getPnlScreen(), getLblNotes(), 0, 1, 1, 1, Add.Insetser(2, 5, 5, 5), GridBagConstraints.HORIZONTAL, 0.3, 1.0);
            Add.ADDCOMP(getPnlScreen(), getScrpaneNotes(), 1, 1, 1, 2, Add.Insetser(2, 5, 5, 5), GridBagConstraints.BOTH, 1.0, 1.0);
            Add.ADDCOMP(getPnlScreen(), getBtnAddToNote(), 0, 2, 1, 1, Add.Insetser(2, 5, 5, 5), GridBagConstraints.HORIZONTAL, 0.3, 1.0);
            Add.ADDCOMP(getPnlScreen(), getLblTags(), 0, 3, 1, 1, Add.Insetser(2, 5, 5, 5), GridBagConstraints.HORIZONTAL, 0.3, 1.0);
            Add.ADDCOMP(getPnlScreen(), getScrpaneCurrentNotes(), 1, 3, 1, 2, Add.Insetser(2, 5, 5, 5), GridBagConstraints.BOTH, 1.0, 1.0);
            Add.ADDCOMP(getPnlScreen(), getBtnSubstractFromNote(), 0, 4, 1, 1, Add.Insetser(2, 5, 5, 5), GridBagConstraints.HORIZONTAL, 0.3, 1.0);
            Add.ADDCOMP(getPnlScreen(), getBtnComplete(), 0, 5, 2, 1, Add.Insetser(5, 5, 0, 5), GridBagConstraints.HORIZONTAL, 1.0, 1.0);
        }
    }
    public void openScreen(){
        showOptionDialog(MainFrame.getFrame_Main(), "", "",
                NO_OPTION, QUESTION_MESSAGE,
                new ImageIcon(), 
                new Object[]{(Object)getPnlScreen()}, null);
    }

//ERİŞİM YÖNTEMLERİ:
    public JLabel getLblName() {
        if(lblName == null){
            lblName = new JLabel("Not etiketi ismi:");
            lblName.setPreferredSize(new Dimension(70, 30));
        }
        return lblName;
    }

    public JLabel getLblNotes() {
        if(lblNotes == null){
            lblNotes = new JLabel("Notlar");
        }
        return lblNotes;
    }

    public JLabel getLblTags() {
        if(lblTags == null){
            lblTags = new JLabel("Şu notlara bu etiketi ekle:");
        }
        return lblTags;
    }

    public JScrollPane getScrpaneNotes() {
        if(scrpaneNotes == null){
            scrpaneNotes = new JScrollPane(getLiNotes());
            scrpaneNotes.setPreferredSize(new Dimension(120, 70));
            scrpaneNotes.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            scrpaneNotes.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            scrpaneNotes.getHorizontalScrollBar().addMouseListener(getMov());
            scrpaneNotes.getVerticalScrollBar().addMouseListener(getMov());
            scrpaneNotes.addMouseListener(getMov());
        }
        return scrpaneNotes;
    }

    public JScrollPane getScrpaneCurrentNotes() {
        if(scrpaneCurrentNotes == null){
            scrpaneCurrentNotes = new JScrollPane(getLiCurrentNotes());
            scrpaneCurrentNotes.setPreferredSize(new Dimension(120, 70));
            scrpaneCurrentNotes.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            scrpaneCurrentNotes.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            scrpaneCurrentNotes.getHorizontalScrollBar().addMouseListener(getMov());
            scrpaneCurrentNotes.getVerticalScrollBar().addMouseListener(getMov());
            scrpaneCurrentNotes.addMouseListener(getMov());
        }
        return scrpaneCurrentNotes;
    }

    public Movements getMov() {
        if(mov == null){
            mov = new Movements();
        }
        return mov;
    }

    public JButton getBtnComplete() {
        if(btnComplete == null){
            btnComplete = new JButton("Not etiketini kaydet");
            btnComplete.setPreferredSize(new Dimension(70, 30));
            btnComplete.addMouseListener(getMov());
            btnComplete.addActionListener(getAct());
        }
        return btnComplete;
    }

    public JButton getBtnAddToNote() {
        if(btnAddToNote == null){
            btnAddToNote = new JButton("EKLE");
            btnAddToNote.setPreferredSize(new Dimension(70, 30));
            btnAddToNote.addMouseListener(getMov());
            btnAddToNote.addActionListener(getAct());
        }
        return btnAddToNote;
    }

    public JButton getBtnSubstractFromNote() {
        if(btnSubstractFromNote == null){
            btnSubstractFromNote = new JButton("ÇIKAR");
            btnSubstractFromNote.setPreferredSize(new Dimension(70, 30));
            btnSubstractFromNote.addMouseListener(getMov());
            btnSubstractFromNote.addActionListener(getAct());
        }
        return btnSubstractFromNote;
    }

    public JList getLiNotes() {
        if(liNotes == null){
            String[] strValues = new String[DataBase.getDatabase().getNoteIDs().length];
            for(int index = 0; index < strValues.length; index++){
                strValues[index] = DataBase.getDatabase().getNoteNamesWithCategoryNames()[index][0] +
                        " : " + DataBase.getDatabase().getNoteNamesWithCategoryNames()[index][1];
            }
            String[] strList = Lister.getLister().listStringByAlphabeticalOrder(strValues);
            for(int index = 0; index < strValues.length; index++){
                getMdlListModelForNotes().add(index, strList[index]);
            }
            liNotes = new JList(getMdlListModelForNotes());
        }
        return liNotes;
    }

    public JList getLiCurrentNotes() {
        if(liCurrentNotes == null){
            liCurrentNotes = new JList(getMdlListModelForCurrentNotes());
        }
        return liCurrentNotes;
    }

    public JPanel getPnlScreen() {
        if(pnlScreen == null){
            pnlScreen = new JPanel(new GridBagLayout());
            pnlScreen.setPreferredSize(new Dimension((2*(MainFrame.getFrame_Main().getWidth()/3)), (2*(MainFrame.getFrame_Main().getHeight()/3))));
            addInterfaceObjects();
        }
        return pnlScreen;
    }

    public JTextField getTxtName() {
        if(txtName == null){
            txtName = new JTextField();
            txtName.setPreferredSize(new Dimension(120, 30));
            txtName.addKeyListener(getKeySignals());
        }
        return txtName;
    }

    public ActsOnNoteTagAdding getAct() {
        if(act == null){
            act = new ActsOnNoteTagAdding(this);
        }
        return act;
    }
    public KeySignalControl getKeySignals() {
        if(keySignal == null){
            keySignal = new KeySignalControl("notetag");
        }
        return keySignal;
    }

    public DefaultListModel<String> getMdlListModelForCurrentNotes() {
        if(mdlListModelForCurrentNotes == null){
            mdlListModelForCurrentNotes = new DefaultListModel<String>();
        }
        return mdlListModelForCurrentNotes;
    }
    public DefaultListModel<String> getMdlListModelForNotes() {
        if(mdlListModelForNotes == null){
            mdlListModelForNotes = new DefaultListModel<String>();
        }
        return mdlListModelForNotes;
    }
    
}
