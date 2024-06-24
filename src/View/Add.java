package View;

import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JComponent;
import javax.swing.JPanel;

public class Add {
    private static Insets insets = new Insets(7,7,7,7); //Insets(5,5,5,5);
    private static GridBagConstraints ConVal = null;
    
    private Add (){
        
    }
    
    public static void ADDCOMP (JPanel panel, JComponent comp, int gridx, int gridy, int gridwidth, int gridheight, Insets insets, int fill, double weightx, double weighty){
        startConstraints();
        ConVal.fill = fill ;
        ConVal.gridwidth = gridwidth ;
        ConVal.gridheight = gridheight ;
        ConVal.weightx = weightx;
        ConVal.weighty = weighty;
        ConVal.insets = insets ;
        ConVal.gridx = gridx ;
        ConVal.gridy = gridy ;
        panel.add(comp, ConVal);
    }
    
    public static void ADDCOMP (Container container, JComponent comp, int gridx, int gridy, int gridwidth, int gridheight, Insets insets, int fill, double weightx, double weighty){
        startConstraints();
        ConVal.fill = fill ;
        ConVal.gridwidth = gridwidth ;
        ConVal.gridheight = gridheight ;
        ConVal.weightx = weightx;
        ConVal.weighty = weighty;
        ConVal.insets = insets ;
        ConVal.gridx = gridx ;
        ConVal.gridy = gridy ;
        container.add(comp, ConVal);
    }
    //YENİ YÖNTEM : İÇ BOŞLUKLARI DA BELİRTEBİLİRİM Bİ İZNİLLÂH:
    public static void ADDCOMP (Container container, JComponent comp, int gridx, int gridy, int gridwidth, int gridheight, Insets insets, int fill, double weightx, double weighty, int internalPadX, int internalPadY){
        startConstraints();
        ConVal.fill = fill ;
        ConVal.gridwidth = gridwidth ;
        ConVal.gridheight = gridheight ;
        ConVal.weightx = weightx;
        ConVal.weighty = weighty;
        ConVal.insets = insets ;
        ConVal.gridx = gridx ;
        ConVal.gridy = gridy ;
        setIpads(internalPadX, internalPadY);
        container.add(comp, ConVal);
    }
    
/* ESKİ:    public static void ADDCOMP (JPanel panel, JComponent comp, int x, int y, int width, int height, int location, int flexibility ) {
        startConstraints();
        ConVal.gridx = x ;
        ConVal.gridy = y ;
        ConVal.gridwidth = width ;
        ConVal.insets = insets ;
        ConVal.gridheight = height ;
        ConVal.anchor = location; //GridBagConstraints.CENTER;
        ConVal.fill = flexibility; //GridBagConstraints.BOTH;
        panel.add(comp, ConVal );
    }*/
    
    private static void startConstraints(){
        if (ConVal == null){
            ConVal = new GridBagConstraints();
        }
    }
    
    public static Insets Insetser(int top, int left, int bottom, int right){
        return new Insets(top, left, bottom, right);
    }
    
//ERİŞİM YÖNTEMLERİ:
    public static Insets getInsets(){
        return insets;
    }
    
    public static void setInsets(int top, int left, int bottom, int right){
        insets.set(top, left, bottom, right);
    }
    
    public static void setAnchor(int anchor){
       getConVal().anchor = anchor;
    }

    public static GridBagConstraints getConVal() {
        if (ConVal == null){
            ConVal = new GridBagConstraints();
        }
        return ConVal;
    }
    
    public static void setConVal(GridBagConstraints constraints){
        ConVal = constraints;
    }
    
    public static void setWeight(double weightx, double weighty){
       getConVal().weightx = weightx;
       getConVal().weighty = weighty;
    }
    
    public static void setIpads(int ipadx, int ipady){
       getConVal().ipadx = ipadx;
       getConVal().ipady = ipady;
    }
}