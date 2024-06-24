package Control;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import View.ListPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ActKeyOnListPanel implements KeyListener, ActionListener{
    ListPanel pnlComponent;

    public ActKeyOnListPanel(ListPanel pnlComponent) {
        this.pnlComponent = pnlComponent;
    }
//İŞLEM YÖNTEMLERİ:
    @Override
    public void keyTyped(KeyEvent e) {
        System.err.println("Girilen harf: " + e.getKeyChar());
    //.;.
    }
    @Override
    public void keyPressed(KeyEvent e) {
    //.;.
    }
    @Override
    public void keyReleased(KeyEvent e) {
    //.;.
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(pnlComponent != null){
            if(e.getSource() == pnlComponent.getBtnApp()){
                
            }
        }
    }
}