package Control;

import View.EditingPanel;
import Base.ChSaver;
import View.InteractiveGUIStructs;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.event.ChangeListener;
import javax.swing.JSpinner;
import javax.swing.event.ChangeEvent;

public class WriteAction implements ActionListener, ChangeListener{
    EditingPanel editPanel;
    ChSaver checkpointSaver;
    
    public WriteAction(EditingPanel panel){
        this.editPanel = panel;
    //    getCheckpointSaver();
    }

    @Override
    public void actionPerformed(ActionEvent e){
        if(editPanel != null){
            if (e.getSource() == editPanel.getEditTools().getBtnInParagraph()){
//                System.err.println("imleç" + editPanel.getTextArea_writingArea().getCaretPosition());
//                System.err.println("imleç konumu : " + editPanel.getTextArea_writingArea().getCaretPosition());
                if(detectParagraph(editPanel.getTextArea_writingArea().getCaretPosition(), false) == true){
                    editPanel.getTextArea_writingArea().setText(applyTab(editPanel.getTextArea_writingArea().getCaretPosition(), true));
                    editPanel.isChanged = true;
                    editPanel.updateForChangeAnyThing();
                }
                editPanel.getTextArea_writingArea().requestFocus();
            }
            else if (e.getSource() == editPanel.getEditTools().getBtnOutParagraph()){
                if (detectParagraph(editPanel.getTextArea_writingArea().getCaretPosition(), true) == true){
                    editPanel.getTextArea_writingArea().setText(applyTab(editPanel.getTextArea_writingArea().getCaretPosition(), false));
                    editPanel.isChanged = true;
                    editPanel.updateForChangeAnyThing();
                }
                editPanel.getTextArea_writingArea().requestFocus();
            }
            else if(e.getSource() == editPanel.getEditTools().getBtnSave()){
                saveData();
                editPanel.getEditTools().getBtnSave().setEnabled(false);
                editPanel.isChanged = false;
                editPanel.getTextArea_writingArea().requestFocus(true);
            }
            else if(e.getSource() == editPanel.getEditTools().getBtnExit()){
                if(editPanel.isChanged == false){
                    exitFromEditingPanel();
                    return;
                }
                boolean confirm = InteractiveGUIStructs.getActiveManager().showYesNoQuestion("Not üzerinde yaptığınız değişiklikler yok sayılsın mı?",
                    "Çıkış yapılsın mı?");
                if(confirm){
                    exitFromEditingPanel();
                }
                else
                    editPanel.getTextArea_writingArea().requestFocus(true);
            }
            else if(e.getSource() == editPanel.getEditTools().getBtnSaveThenExit()){
                if(editPanel.isChanged == false){
                    exitFromEditingPanel();
                    return;
                }
                saveAndExitFromEditingPanel();
            }
            /*else if(e.getSource() == editPanel.getEditTools().getBtnBackTo()){
                String lastText = editPanel.getCpSystem().getLastText();
                if(lastText != null)
                    editPanel.getTextArea_writingArea().setText(lastText);
            }*/
            /*else if(e.getSource() == editPanel.getEditTools().getBtnNextTo()){
                String lastText = editPanel.getCpSystem().getNextText();
                if(lastText != null)
                    editPanel.getTextArea_writingArea().setText(lastText);
                editPanel.getCpSystem().resetNextChPoints();
            }*/
            /*if (e.getSource() == editPanel.getEditTools().getB3_bold()){//Özellikler dosyanın sonuna eklenmeli ya da ayrı bir dosyada saklanmalı; String biçiminde depolanmalı, bu sayee daha az alan kaplar. Bunun için özel bir format ve o formatı okuyabilen bir küçük okuyucu motora ihtiyaç var
                
            }
            if (e.getSource() == editPanel.getEditTools().getB4_underline()){
                
            }*/
        }
    }
    
    protected boolean detectParagraph(int index, boolean isRequestIsContainTab){
        char lastCharacter;
        boolean isContainTab = false;
        String textOn = editPanel.getTextArea_writingArea().getText();
        //        lastCharacter = textOn.charAt(index -1);
        for(int i = index - 1; i >= 0 ;i--){
            lastCharacter = textOn.charAt(i);
            if(lastCharacter == '\n'){
                if (isRequestIsContainTab == false)
                    return true;
                else{
                    if(isContainTab == true)
                        return true;
                    else
                        return false;
                }
            }
            else if(lastCharacter == '\t'){
                if(isRequestIsContainTab == false)
                    continue;
                else
                    isContainTab = true;
            }
            else
                return false;
        }
        if(isRequestIsContainTab == false)
            return true;
        else
            return isContainTab;
    }
    
    
    /*public void applyAttributes(){
        
        
        
    }*/
    
    public String applyTab(int caretPosition, boolean is_addTab){
        StringBuilder textToChange;
        textToChange = new StringBuilder(editPanel.getTextArea_writingArea().getText());
        if (is_addTab == true){
            textToChange.insert((caretPosition), '\t');
        }
        else
            textToChange.deleteCharAt((caretPosition - 1));
        return new String(textToChange);
    }
    private void changeTextSize(int size){
        if(size <= 0)
            return;
        if(size > 34)
            return;
        editPanel.getTextArea_writingArea().setFont(editPanel.getTextArea_writingArea().getFont().deriveFont((float) size));
    }
    @Override
    public void stateChanged(ChangeEvent e){
        JSpinner spinSize = (JSpinner) e.getSource();
        int sizeText = (int) spinSize.getValue();
        changeTextSize(sizeText);
        editPanel.isChanged = true;
        editPanel.updateForChangeAnyThing();
        editPanel.getTextArea_writingArea().requestFocus();
    }
    public static boolean requestExit(WriteAction act){
        if(act == null)
            return false;
        if(act.getEditPanel() == null)
            return false;
        if(IDARE.getLocationName().split("/").length != 3)
            return false;
        int value = InteractiveGUIStructs.getActiveManager().showYesNoCancelQuestion("Değişiklikler kaydedilsin mi?", "Çıkış için cevâplayın");
        boolean saved = false;
        switch(value){
            case 0:{//Evet
                saved = act.saveData();
                break;
            }
            case 1:{//Hayır
                IDARE.exitInProcessing();
                return true;
            }
            default :{return false;}
        }
        if(saved){
            IDARE.exitInProcessing();
            return true;
        }
            return false;
    }
    //ARKAPLAN İŞLEM YÖNTEMLERİ:
    private void exitFromEditingPanel(){
        IDARE.exitInProcessing();
        IDARE.goToUpLocation();
    }
    private boolean saveData(){
     //   System.err.println("İmlecin konumu : " + editPanel.getTextArea_writingArea().getCaretPosition());
        return IDARE.saveNote(editPanel.getTextArea_writingArea().getText(),
                editPanel.getTextArea_writingArea().getCaretPosition(),
                (int) editPanel.getEditTools().getSpinSize().getValue());
    }
    private boolean saveAndExitFromEditingPanel(){
        if(saveData()){
            exitFromEditingPanel();
            return true;
        }
        return false;
    }

//ERİŞİM YÖNTEMLERİ:
    public EditingPanel getEditPanel(){
        return editPanel;
    }
    public ChSaver getCheckpointSaver(){
        if(checkpointSaver == null){
            checkpointSaver = new ChSaver(editPanel.getTextArea_writingArea());
        }
        return checkpointSaver;
    }
}