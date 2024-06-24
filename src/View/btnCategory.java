package View;

import Base.GUISeeming;
import Control.Movements;
import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class btnCategory extends JButton{
    JButton btnOpenClose;
    JLabel lblCategoryName, lblNumberOfNotes;
    BorderLayout compOrder;
    String hexColor;
    Movements movButton;
    
    public btnCategory(String categoryName, int numberOfNotes, String hexColor) {
        super();
        setLayout(getCompOrder());
        lblCategoryName = new JLabel(categoryName);
        lblNumberOfNotes = new JLabel(Integer.toString(numberOfNotes));
        this.hexColor = hexColor;
    //    setPreferredSize(new Dimension(150, 40));
        add(getBtnOpenClose(), BorderLayout.LINE_START);
        add(lblCategoryName, BorderLayout.CENTER);
        add(lblNumberOfNotes, BorderLayout.LINE_END);
        lblNumberOfNotes.setVerticalAlignment(CENTER);
        lblNumberOfNotes.setHorizontalAlignment(SwingConstants.CENTER);
        addMouseListener(getMovButton());
        GUISeeming.appGUI(this);
    }

//ERİŞİM YÖNTEMLERİ:
    public JButton getBtnOpenClose() {
        if(btnOpenClose == null){
            btnOpenClose = new JButton(">");
            btnOpenClose.setPreferredSize(new Dimension(17, 25));
            btnOpenClose.addMouseListener(getMovButton());
        }
        return btnOpenClose;
    }
    public JLabel getLblNumberOfNotes() {
        return lblNumberOfNotes;
    }
    public BorderLayout getCompOrder() {
        if(compOrder == null){
            compOrder = new BorderLayout(2, 5);
        }
        return compOrder;
    }
    public JLabel getLblCategoryName(){
        return lblCategoryName;
    }
    public void setCompOrder(BorderLayout compOrder) {
        this.compOrder = compOrder;
    }
    public String getHexColor() {
        return hexColor;
    }
    /*public void setHexColor(String hexColor) {
        this.hexColor = hexColor;
    }*/
    public Movements getMovButton() {
        if(movButton == null){
            movButton = new Movements();
        }
        return movButton;
    }
}