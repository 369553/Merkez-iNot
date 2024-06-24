package Control;

import View.ContactPanel;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeySignalControl implements KeyListener{
    String controlTypeFor;

    public KeySignalControl(String controlTypeFor){
        this.controlTypeFor = controlTypeFor.toLowerCase().trim();
    }

    @Override
    public void keyTyped(KeyEvent e){
        if(e.getSource().getClass().getName().equals("javax.swing.JTextField")){
            if(!isAcceptableControl(e.getKeyChar(), this.controlTypeFor)){
                e.consume();
                ContactPanel.getContactPanel().showMessage(e.getKeyChar() + " karakteri burada kullanılmamalı", "Warning");
            }
        }
        else if(e.getSource().getClass().getName().equals("javax.swing.JTextArea")){
            if(!isAcceptableControl(e.getKeyChar(), this.controlTypeFor)){
                e.consume();
                ContactPanel.getContactPanel().showMessage(e.getKeyChar() + " karakteri burada kullanılmamalı", "Warning");
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e){
        if(((int)e.getKeyChar()) == 22)
            e.consume();
        if(((int)e.getKeyChar()) == 24)
            e.consume();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        
    }
    
    public static boolean isAcceptableForNoteTag(char character){
        switch(character){
            case ',': return false;
            case ' ': return false;
            case '~': return false;
            case '?': return false;
            case '=': return false;
            case '%': return false;
            case '&': return false;
            case '\'': return false;
            case '\"': return false;
            case '+': return false;
            case '-': return false;
            case '/': return false;
            case '!': return false;
            case '\\': return false;
            case '<': return false;
            case '>': return false;
            case '$': return false;
            case '|': return false;
            case '¨': return false;
            case '€': return false;
            case ';': return false;
        }
        return true;
    }
    public static boolean isAcceptableForNote(char character){
        switch(character){
    //        case ',': return false;
    //        case ' ': return false;
    //        case '^': return false;
            case '~': return false;
            case '?': return false;
            case '=': return false;
            case '%': return false;
            case '&': return false;
    //        case '\'': return false;
            case '\"': return false;
    //        case '+': return false;
    //        case '-': return false;
            case '/': return false;
            case '!': return false;
            case '\\': return false;
    //        case '<': return false;
    //        case '>': return false;
            case '$': return false;
            case '|': return false;
            case '¨': return false;
            case '€': return false;
            case ';': return false;
        }
        return true;
    }
    public boolean isAcceptableControl(char character, String controlTypeFor){
        if(controlTypeFor.equals("note"))
            return isAcceptableForNote(character);
        else if(controlTypeFor.equals("notetag"))
            return isAcceptableForNoteTag(character);
        else if(controlTypeFor.equals("category"))
            return isAcceptableForNote(character);
        else
            return true;
    }
}