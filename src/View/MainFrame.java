package View;


import Control.IDARE;
import Services.CryptingService;
import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JFrame;

public class MainFrame extends JFrame{
    private static MainFrame Frame_Main ;
    private static BorderLayout layout ;
    
    private MainFrame (){
        
    }
    public static void resetFrame(String code){
        if(CryptingService.getMd5(code).equals(IDARE.getMd5Code())){
            System.err.println("Doğru kod.\nAPencere sıfırlanacak inşâAllÂH");
            Frame_Main.removeAll();
            Frame_Main.setVisible(false);
            Frame_Main = null;
            layout = null;
            System.err.println("Bu satır yazdırılıyorsa bir üst satırda hatâ yok demektir bi iznillâh");
        }
    }
    public static MainFrame getFrame_Main(){
        if ( Frame_Main == null ){
            Frame_Main = new MainFrame();
            Frame_Main.setLayout( getFrameLayout() );
            Frame_Main.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
            /*Dimension systemSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setBounds(systemSize.width / 4, systemSize.height / 4, systemSize.width / 2, systemSize.height / 2);
            Frame_Main.setPreferredSize(new Dimension(systemSize.width / 2, systemSize.height / 2));*/
            Frame_Main.setBounds( 240, 120, 825, 500 );
            //Gerek yok, çünkü frame de boşlu yok Frame_Main.setBackground( Color.decode (SystemSettings.getSettings().getCurrentTheme().getBackgroundColor() ) );
            Frame_Main.setMinimumSize( new Dimension (825, 400) ) ;
        }
        return Frame_Main ;
    }
    
    public static BorderLayout getFrameLayout(){
        if (layout == null){
            layout = new BorderLayout(5, 5);
        }
        return layout;
    }
    
    
}
