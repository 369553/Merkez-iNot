
package View;

import Base.Category;
import Base.GUISeeming;
import Base.Note;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JButton;

public class btnOrgButton{
    private static int newNumber = 0;//Bunun yerine "hash" tekniği kullan
    int number;
    JButton button;
    private btnOrgButton(int number, JButton button) {
        this.number = number;
        this.button = button;
    }
    
//İŞLEM YÖNTEMLERİ:
    public btnOrgButton getButtonForCategory(Category category){
        JButton btnPrepared = new JButton(category.getName());
        btnPrepared.setPreferredSize(new Dimension(category.getName().length() * 4, 40));
        btnPrepared.setBackground(Color.decode(category.getCategoryColor()));
        btnPrepared.setForeground(GUISeeming.findHarmonicTextColor(category.getCategoryColor()));
        return new btnOrgButton(btnOrgButton.getNumberForButton(), btnPrepared);
    }
    
    public btnOrgButton getButtonForNote(Note note){
        JButton btnPrepared = new JButton(note.getName());
        btnPrepared.setPreferredSize(new Dimension(note.getName().length() * 4, 40));
        btnPrepared.setBackground(Color.decode(note.getCategory().getCategoryColor()));
        btnPrepared.setForeground(GUISeeming.findHarmonicTextColor(note.getCategory().getCategoryColor()));
        return new btnOrgButton(btnOrgButton.getNumberForButton(), btnPrepared);
    }
    
//SINIF İÇİ YÖNETİM YÖNTEMLERİ:
    private static int getNumberForButton(){
        return newNumber++;
    }
    
}
