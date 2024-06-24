package View;

import Base.GUISeeming;
import java.awt.Color;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

public class ListCellRender extends JLabel implements ListCellRenderer{
    static Color[] colors;

    public ListCellRender(){
        colors = GUISeeming.getColorsForRenderer();
    }
//İŞLEM YÖNTEMLERİ:
    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus){
        ColorInf x=(ColorInf) value;
        setIcon(x.getA());
        setText(x.getB());
        if(isSelected){
            setForeground(colors[3]);
            setBackground(colors[0]);
        }
        else{
            setBackground(colors[2]);
            setForeground(colors[1]);
        }
       return this;
    }

//İŞLEM YÖNTEMLERİ:
    public static void updateColors(){
       colors = GUISeeming.getColorsForRenderer();
    }
}
