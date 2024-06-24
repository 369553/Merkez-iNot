package View;

import Control.InterfaceController;
import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainView {
    MainPanel MP;

    public MainView (){
        InterfaceController.getGuiManaging().setMP((MainPanel) getMain_Panel());
        ((MainPanel) getMain_Panel()).addPanels();
        getFrame_main().add( getMain_Panel(), BorderLayout.CENTER ) ;
//TUŞA BASILDIĞINDA AÇILMASI LAZIM        getFrame_main().add( getMenu(), BorderLayout.WEST);//konumu sağa alınabilir şekilde olabilir.
        getFrame_main().setVisible( true ) ;
    }
    
    
    public JFrame getFrame_main() {
        return MainFrame.getFrame_Main();
    }
    
    public JPanel getMain_Panel (){
        if (MP == null){
            MP = new MainPanel();
        }
        return MP;
    }
    
}
