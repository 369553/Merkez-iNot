package Control;

import View.pnlLocation;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ActOnPnlLocation implements ActionListener{
    pnlLocation pnl;
    boolean inEditing = false;
    public ActOnPnlLocation(pnlLocation panel) {
        this.pnl = panel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(pnl == null)
            return;
        if(IDARE.getLocationName().split("/").length == 3)
            inEditing = true;
        else
            inEditing = false;
        if(e.getSource() == pnl.getBtnHomePage()){
            if(IDARE.getLocationName().equals("Anasayfa"))
                return;//Anasayfadayken Anasayfa ya tıklanıldıysa sayfayı yenileme
            if(!inEditing){
                IDARE.openHomePage();
                return;
            }
            else{
                if(InterfaceController.getGuiManaging().getMP().getEditPanel().isChanged == false){
                    IDARE.openHomePage();
                    return;
                }
                boolean confirm = WriteAction.requestExit(InterfaceController.getGuiManaging().getMP().getEditPanel().getEditTools().getWrAction());
                if(confirm)
                    IDARE.openHomePage();
                else
                    InterfaceController.getGuiManaging().getMP().getEditPanel().getTextArea_writingArea().requestFocus(true);
            }
        }
        else if(e.getSource().getClass().getName().equals("javax.swing.JButton")){
            if(!inEditing)
                return;
            if(InterfaceController.getGuiManaging().getMP().getEditPanel().isChanged == false){
                IDARE.goToUpLocation();
                return;
            }
            boolean confirm = WriteAction.requestExit(InterfaceController.getGuiManaging().getMP().getEditPanel().getEditTools().getWrAction());
            if(confirm)
                IDARE.goToUpLocation();
            else
                InterfaceController.getGuiManaging().getMP().getEditPanel().getTextArea_writingArea().requestFocus(true);
        }
    }
}