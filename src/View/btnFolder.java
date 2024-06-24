package View;

import Base.GUISeeming;
import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.SwingConstants;

public class btnFolder extends JButton{
    String strName, strColor;
    int categoryID;

    public btnFolder(String strName, String strColor, int categoryID){
        super(strName);
        this.strName = strName;
        this.strColor = strColor;
        this.categoryID = categoryID;
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
    public String getStrColor() {
        return strColor;
    }
    public void setStrColor(String strColor) {
        this.strColor = strColor;
    }
    public int getCategoryID() {
        return categoryID;
    }
    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }
}
