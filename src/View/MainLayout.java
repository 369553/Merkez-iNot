package View;

import java.awt.GridBagLayout;

public class MainLayout {
    private static GridBagLayout Mlayout ;
    
    
    public static GridBagLayout getMlayout () {
        if ( Mlayout == null ) {
            Mlayout = new GridBagLayout() ;
        }
        return Mlayout ;
    }
}
