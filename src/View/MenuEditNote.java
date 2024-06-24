package View;

import Control.ActionsOnMenuEditNote;
import Control.Movements;
import java.awt.Dimension;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

public class MenuEditNote extends JMenu{
    JMenuItem menuitemRenameNote, menuitemChangeCategory, menuitemDeleteNote, menuitemLookInfo;
    Movements movementActs;
    ActionsOnMenuEditNote actsForThis;
    
    public MenuEditNote() {
        super("Düzenle");
        add(getMenuitemRenameNote());
        add(getMenuitemChangeCategory());
        add(getMenuitemDeleteNote());
        add(getMenuitemLookInfo());
        setPreferredSize(new Dimension(80, 20));
        setSize(new Dimension(80, 20));
//        addActionListener(getActsForThis()); BU LAZIM MI?
        addMouseListener(getMovementActs());
    }
    
    
//ERİŞİM YÖNTEMLERİ:

    public JMenuItem getMenuitemRenameNote() {
        if(menuitemRenameNote == null){
            menuitemRenameNote = new JMenuItem("İsimlendir");
            menuitemRenameNote.addActionListener(getActsForThis());
            menuitemRenameNote.addMouseListener(getMovementActs());
        }
        return menuitemRenameNote;
    }

    public void setMenuitemRenameNote(JMenuItem menuitemRenameNote) {
        this.menuitemRenameNote = menuitemRenameNote;
    }

    public JMenuItem getMenuitemChangeCategory() {
        if(menuitemChangeCategory == null){
            menuitemChangeCategory = new JMenuItem("Kategori değiştir");
            menuitemChangeCategory.addActionListener(getActsForThis());
            menuitemChangeCategory.addMouseListener(getMovementActs());
        }
        return menuitemChangeCategory;
    }

    public void setMenuitemChangeCategory(JMenuItem menuitemChangeCategory) {
        this.menuitemChangeCategory = menuitemChangeCategory;
    }

    public JMenuItem getMenuitemDeleteNote() {
        if(menuitemDeleteNote == null){
            menuitemDeleteNote = new JMenuItem("Notu sil");
            menuitemDeleteNote.addActionListener(getActsForThis());
            menuitemDeleteNote.addMouseListener(getMovementActs());
        }
        return menuitemDeleteNote;
    }

    public void setMenuitemDeleteNote(JMenuItem menuitemDeleteNote) {
        this.menuitemDeleteNote = menuitemDeleteNote;
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

    public ActionsOnMenuEditNote getActsForThis() {
        if(actsForThis == null){
            actsForThis = new ActionsOnMenuEditNote(this);
        }
        return actsForThis;
    }

    public void setActsForThis(ActionsOnMenuEditNote actsForThis) {
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