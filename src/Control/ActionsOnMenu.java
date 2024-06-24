package Control;

import View.Menu;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ActionsOnMenu implements ActionListener{
    Menu menu;
    
    public ActionsOnMenu(Menu menu) {
        this.menu = menu;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == menu.getBtnShowCategoryScreen()){
            if(!IDARE.getLocationName().equals("Anasayfa"))
                IDARE.openHomePage();
        }
        else if(e.getSource() == menu.getBtnShowNotesScreen()){
            System.err.println("Liste görünümü...");
            IDARE.openListSeeming();
        }
        else if(e.getSource() == menu.getBtnOpenSettings()){
            System.err.println("Ayarlar...");
            IDARE.openOptionsPanel();
        }
        else if(e.getSource() == menu.getBtnOpenBackuping()){
            System.err.println("Yedekleme...");
            IDARE.openBackupPanel();
        }
    }
    
}
