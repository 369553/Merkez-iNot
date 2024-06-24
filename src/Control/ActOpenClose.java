package Control;

import View.pnlCategory;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ActOpenClose implements ActionListener{
    pnlCategory pnlCt;
    
    public ActOpenClose(pnlCategory pnlCt) {
        this.pnlCt = pnlCt;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == pnlCt.getBtnMain().getBtnOpenClose()){
            if(pnlCt.boolIsListOpen){
                pnlCt.getLiNotes().setVisible(false);
                pnlCt.boolIsListOpen = false;
                //Tuştaki simgeyi değiştirme yapılabilir.
            }
            else{
                pnlCt.getLiNotes().setVisible(true);
                pnlCt.boolIsListOpen = true;
                //Tuştaki simgeyi değiştirme yapılabilir.
            }
        }
    }
}