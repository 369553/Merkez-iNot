package Control;

import View.ScreenPrepareGUISeeming;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ActsOnPreparingGUISeeming implements ActionListener{
    ScreenPrepareGUISeeming screen;
    
    public ActsOnPreparingGUISeeming(ScreenPrepareGUISeeming screen){
        this.screen = screen;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == screen.getBtnSave()){
            String guiName = screen.askGUIName();
//            checkGUIName(guiName);
        }
        else if(e.getSource() == screen.getBtnCancel()){
            //InterfaceController.getGuiManaging(). .;.
        }
    }
    
/*    public boolean checkGUIName(String guiName){
        //.;.
        return true;
    }*/
}
