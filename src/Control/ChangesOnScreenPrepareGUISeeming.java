package Control;

import View.ScreenPrepareGUISeeming;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class ChangesOnScreenPrepareGUISeeming implements ItemListener{
    ScreenPrepareGUISeeming screen;
    public ChangesOnScreenPrepareGUISeeming(ScreenPrepareGUISeeming screen) {
        this.screen = screen;
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
/*        if(e.getStateChange() == ItemEvent.SELECTED)
            screen.drawChosedComponent();*/
    }
     
}
