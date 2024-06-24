package View;
import Base.GUISeeming;
import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.SwingConstants;

public class btnNote extends JButton{
    String strName;
    int noteID;

    public btnNote(String strName, int noteID) {
        super(strName);
        this.strName = strName;
        this.noteID = noteID;
        this.setHorizontalTextPosition(SwingConstants.CENTER);
        this.setVerticalTextPosition(SwingConstants.BOTTOM);
        setPreferredSize(new Dimension(152, 97));
        setBorder(GUISeeming.getComponentBorder(true, true, true, true, 2));
        setFocusPainted(false);
    //    setIconTextGap(15);
    }

//ERİŞİM YÖNTEMLERİ:
    public String getStrName() {
        return strName;
    }
    public void setStrName(String strName) {
        this.strName = strName;
    }
    public int getNoteID() {
        return noteID;
    }
    public void setNoteID(int noteID) {
        this.noteID = noteID;
    }
}
