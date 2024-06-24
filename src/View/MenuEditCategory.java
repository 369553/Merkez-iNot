package View;

import Control.ActionsOnMenuEditCategory;
import Control.Movements;
import java.awt.Dimension;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.plaf.basic.BasicInternalFrameTitlePane;

public class MenuEditCategory extends JMenu{
    JMenuItem menuitemRenameCategory, menuitemDeleteCategory, menuitemChangeColor,
        menuitemChangeExplanation, menuitemLookInfo;
    ActionsOnMenuEditCategory actsForThis;    
    Movements movementActs;
        
    public MenuEditCategory() {
        super("Düzenle");
        add(getMenuitemRenameCategory());
        add(getMenuitemDeleteCategory());
        add(getMenuitemChangeColor());
        add(getMenuitemChangeExplanation());
        add(getMenuitemLookInfo());
        setPreferredSize(new Dimension(80, 20));
        setSize(new Dimension(80, 20));
//        addActionListener(getActsForThis()); BU LAZIM MI?
        addMouseListener(getMovementActs());
        
    }
    
    
//ERİŞİM YÖNTEMLERİ:
    public JMenuItem getMenuitemRenameCategory(){
        if(menuitemRenameCategory == null){
            menuitemRenameCategory = new JMenuItem("İsimlendir");
            menuitemRenameCategory.addActionListener(getActsForThis());
            menuitemRenameCategory.addMouseListener(getMovementActs());
        }
        return menuitemRenameCategory;
    }

    public void setMenuitemRenameCategory(JMenuItem menuitemRenameCategory) {
        this.menuitemRenameCategory = menuitemRenameCategory;
    }

    public JMenuItem getMenuitemDeleteCategory() {
        if(menuitemDeleteCategory == null){
            menuitemDeleteCategory = new JMenuItem("Kategoriyi sil");
            menuitemDeleteCategory.addActionListener(getActsForThis());
            menuitemDeleteCategory.addMouseListener(getMovementActs());
        }
        return menuitemDeleteCategory;
    }

    public void setMenuitemDeleteCategory(JMenuItem menuitemDeleteCategory) {
        this.menuitemDeleteCategory = menuitemDeleteCategory;
    }

    public JMenuItem getMenuitemChangeColor() {
        if(menuitemChangeColor == null){
            menuitemChangeColor = new JMenuItem("Kategori rengini değiştir");
            menuitemChangeColor.addActionListener(getActsForThis());
            menuitemChangeColor.addMouseListener(getMovementActs());
        }
        return menuitemChangeColor;
    }

    public void setMenuitemChangeColor(JMenuItem menuitemChangeColor) {
        this.menuitemChangeColor = menuitemChangeColor;
    }

    public JMenuItem getMenuitemChangeExplanation() {
        if(menuitemChangeExplanation == null){
            menuitemChangeExplanation = new JMenuItem("Kategori açıklamasını değiştir");
            menuitemChangeExplanation.addActionListener(getActsForThis());
            menuitemChangeExplanation.addMouseListener(getMovementActs());
        }
        return menuitemChangeExplanation;
    }

    public void setMenuitemChangeExplanation(JMenuItem menuitemChangeExplanation) {
        this.menuitemChangeExplanation = menuitemChangeExplanation;
    }

    public JMenuItem getMenuitemLookInfo() {
        if(menuitemLookInfo == null){
            menuitemLookInfo = new JMenuItem("Bilgilere bak");
            menuitemLookInfo.addActionListener(getActsForThis());
            menuitemLookInfo.addMouseListener(getMovementActs());
        }
        return menuitemLookInfo;
    }

    public void setMenuitemLookInfo(JMenuItem menuitemLookInfo) {
        this.menuitemLookInfo = menuitemLookInfo;
    }

    public ActionsOnMenuEditCategory getActsForThis() {
        if(actsForThis == null){
            actsForThis = new ActionsOnMenuEditCategory(this);
        }
        return actsForThis;
    }

    public void setActsForThis(ActionsOnMenuEditCategory actsForThis) {
        this.actsForThis = actsForThis;
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
