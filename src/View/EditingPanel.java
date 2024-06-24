package View;

import Base.DataBase;
import Base.GUISeeming;
import Base.Note;
import Base.chkPointSystem;
import Control.IDARE;
import Control.KeySignalFromNoting;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class EditingPanel extends JPanel{
    private static EditingPanel editPanel;
    protected GridBagLayout layoutMain;
    EditTools editTools;
    JTextArea TextArea_writingArea;
    KeySignalFromNoting catchKey;
    JScrollPane scrpaneWritingArea;
    public boolean isChanged = false;
    private chkPointSystem cpSystem;
    
    public EditingPanel(){
        this.setLayout(getLayoutMain());
        //notte = new JTextArea("Bismillâh", 30, 30);
        Add.setInsets(3, 3, 3, 3);
        Add.setAnchor(GridBagConstraints.LINE_START);
        Add.ADDCOMP(this, getEditTools(), 0, 0, 2, 1, Add.getInsets(), GridBagConstraints.HORIZONTAL, 1.0, 0.0);
        Add.setAnchor(GridBagConstraints.CENTER);
        Add.ADDCOMP(this, getScrpaneWritingArea(), 0, 1, 2, 1, Add.getInsets(), GridBagConstraints.BOTH, 1.0, 1.0);
        GUISeeming.appGUI(this);
        MainFrame.getFrame_Main().setVisible(true);
    }

//İŞLEM YÖNTEMLERİ:
    public void updatePanel(){
        String[] names = IDARE.getLocationName().split("/");
        if(names.length == 3)
            getTextArea_writingArea().setText(DataBase.getDatabase().getNoteByName(names[2], names[1]).getMalumat());
        isChanged = false;
        this.getEditTools().getBtnSave().setEnabled(false);
    }
    public void fillDataToPage(Object data, int locationOnTheText, int textSize){
        if(!data.getClass().getName().equals("Base.Note"))
            return;
        Note ntCurrent = (Note) data;
        this.getTextArea_writingArea().setText(ntCurrent.getMalumat());
        this.getTextArea_writingArea().setFont(this.getTextArea_writingArea().getFont().deriveFont((float) textSize));
        this.getEditTools().getSpinSize().setValue(textSize);
        if( locationOnTheText < getTextArea_writingArea().getText().length())
            this.getTextArea_writingArea().setCaretPosition(locationOnTheText);
        else
            this.getTextArea_writingArea().setCaretPosition(this.getTextArea_writingArea().getText().length());
    }
    public void updateForChangeAnyThing(){
        getEditTools().getBtnSave().setEnabled(true);
    }

//ERİŞİM YÖNTEMLERİ ve VERİ AYARLAMA (SET) YÖNTEMLERİ:
    public EditTools getEditTools() {
        if (editTools == null){
            editTools = EditTools.getEditTools();
        }
        return editTools;
    }

    public void setEditTools(EditTools editTools){
        this.editTools = editTools;
    }

    public JTextArea getTextArea_writingArea(){
        if (TextArea_writingArea == null){
            TextArea_writingArea = new JTextArea("Bismillâh", 1, 1)/*{
                 @Override
                 public void setText(String str){
                     this.select(0, this.getText().length());
                     this.replaceSelection("");
                     for(char i : str.toCharArray()){
//                         System.out.println("karakter :" + i + "\tkodu :" + ((int) i));
                         if(((int)i) == 10){System.err.println("isâbet");
                             this.insert(("\n"), this.getText().length());}
                         else if(((int )i) == 9)
                             this.insert(("\t"), this.getText().length());
                         else
                             this.insert(("" + i), this.getText().length());
                     }
                 }
            }*/;
            TextArea_writingArea.setAutoscrolls(true);
            TextArea_writingArea.setLineWrap(true);
            TextArea_writingArea.addKeyListener(getCatchKey());
        }
        return TextArea_writingArea;
    }

    public void setTextArea_writingArea(JTextArea TextArea_writingArea) {
        this.TextArea_writingArea = TextArea_writingArea;
    }

    public static EditingPanel getEditPanel(){
        if (editPanel == null){
            editPanel = new EditingPanel();
        }
        return editPanel;
    }

    public static void setEditPanel(EditingPanel editPanel) {
        EditingPanel.editPanel = editPanel;
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

    public KeySignalFromNoting getCatchKey() {
        if(catchKey == null){
            catchKey = new KeySignalFromNoting(this);
        }
        return catchKey;
    }

    public void setCatchKey(KeySignalFromNoting catchKey) {
        this.catchKey = catchKey;
    }

    public JScrollPane getScrpaneWritingArea() {
        if(scrpaneWritingArea == null){
            scrpaneWritingArea = new JScrollPane(getTextArea_writingArea());
        }
        return scrpaneWritingArea;
    }

    public void setScrpaneWritingArea(JScrollPane scrpaneWritingArea) {
        this.scrpaneWritingArea = scrpaneWritingArea;
    }
    
    /*public void addListeners(){
        EditingPanel.getEditPanel().getTextArea_writingArea().addKeyListener(getCatchKey());
    }*/
    public chkPointSystem getCpSystem() {
        if(cpSystem == null){
            cpSystem = new chkPointSystem();
        }
        return cpSystem;
    }
}