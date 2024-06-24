
package Control;

import View.EditingPanel;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.BadLocationException;

public class KeySignalFromNoting implements KeyListener{
    EditingPanel editPanel;
    int length = 0;

    public KeySignalFromNoting(EditingPanel panel){
        this.editPanel = panel;
    }

//İŞLEM YÖNTEMLERİ:
    @Override
    public void keyTyped(KeyEvent e){
        if(editPanel != null){
            if(e.getSource() == editPanel.getTextArea_writingArea()){
                //Herhangi bir tuşa basıldığında değil; sadece içerik değiştiğinde kaydetme işlevi aktif olmalı
//                System.out.println("kod : " + (int) e.getKeyChar());
//                System.err.println("basılan tuş: " + e.getKeyChar());
             //   System.out.println("yazı : " + editPanel.getTextArea_writingArea().getText());
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e){
        if(e.getSource() == editPanel.getTextArea_writingArea()){
            /*if(e.getKeyChar() == ' '){
                if(!(editPanel.getTextArea_writingArea().getText().charAt(e.getKeyLocation() - 1) == ' ')){
                    editPanel.getCpSystem().addNewCheckPoint(editPanel.getTextArea_writingArea().getText());
                }
            }*/
        }
    }

    @Override
    public void keyReleased(KeyEvent e){
        if(editPanel.getTextArea_writingArea().getText().length() != length){
            editPanel.isChanged = true;
            editPanel.updateForChangeAnyThing();
        }
        length = editPanel.getTextArea_writingArea().getText().length();
        //Otomatik kaydetme açık ise burada tetikle bi iznillâh
    //TUŞ BIRAKILDIĞINDA
    }
}