package Base;
//not istatistikleri, çizim, etiketleme, zaman çizelgesi sonra ekle
//Dil seçeneği eklenmeli

import Control.IDARE;
/**
 *
 * @author SOLAKOĞULLARI, MEHMET AKİF SOLAK
 */
public class MerkezINotesApp{
    
    public static void main(String[] args){
     //   MainView view = new MainView();
        IDARE.startHatırNOT("IDAREHÂTIRNOT--...");
//GEÇİCİ DEĞİŞKENLER:
   // JFrame f = new JFrame("Kontrol");
    
   // f.setLayout(null);
  /*  f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    f.setBounds(400, 140, 615, 500);
    f.setMaximumSize(new Dimension(600, 400));*/
    //GUISeeming.appGUI(f);
//ListPanel:
/*
    ListPanel pnl = new ListPanel();
    f.add(pnl);
    f.add(pnl);
    f.setVisible(true);*/
    //SpringLayout:
    /*JFrame e = new JFrame("SpringLayout");
    SpringLayout w = new SpringLayout();
    e.setLayout(w);
    e.setBounds(450, 190, 450, 190);
    JButton btn01 = new JButton("btn01");
    JButton btn02 = new JButton("btn02");
    JTextField txt01 = new JTextField("txt01");
    e.add(btn01);
    e.add(btn02);
    e.add(txt01);
    e.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    e.setVisible(true);
    */
    //GridLayout:
/*    JFrame e = new JFrame("GridLayout");
    GridLayout w = new GridLayout(2, 2, 5, 5);
    e.setLayout(w);
    JButton btn01 = new JButton("btn01");
    JButton btn02 = new JButton("btn02");
    JTextField txt01 = new JTextField("txt01");
    e.add(btn01);
    e.add(btn02, 1);
    e.add(txt01, 0);
    e.setBounds(400, 170, 400, 170);
   // e.setSize(new Dimension(400, 170));
    e.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    e.setVisible(true);*/
    
    //GridBagLayout:
        /*JFrame e = new JFrame("GridBagLayout");
        GridBagLayout w = new GridBagLayout();
        e.setLayout(w);
        GridBagConstraints t;
        t = new GridBagConstraints(0, 0, 0, 1, 0.1, 0.1, GridBagConstraints.FIRST_LINE_START, GridBagConstraints.NONE, Add.Insetser(1, 2, 2, 2), 0, 0);
        e.add(new JButton("f"), t);
        e.setBounds(300, 500, 200, 70);
        e.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        e.setVisible(true);*/
    }
    
}
