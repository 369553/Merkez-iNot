package Control;

import Base.GUISeeming;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JMenuItem;
import javax.swing.JScrollBar;

public class Movements implements MouseListener{

    @Override
    public void mouseClicked(MouseEvent e) {//TEKRAR DÜZENLEME GEREKEBİLİR
        if(e.getSource().getClass().getName().contains("JScrollBar")){
            Component comp = (Component) e.getSource();
            GUISeeming.mouseClickedRefresh(comp);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(e.getSource().getClass().getName().contains("JButton")){
            Component comp = (Component) e.getSource();
            GUISeeming.mouseClickedRefresh(comp);
        }
        else if(e.getSource().getClass().getName().contains("JMenuItem")){
            Component comp = (Component) e.getSource();
            GUISeeming.mouseClickedRefresh(comp);
        }
        else if(e.getSource().getClass().getName().contains("JScrollBar")){
            Component comp = (Component) e.getSource();
            GUISeeming.mouseClickedRefresh(comp);
        }
        else if(e.getSource().getClass().getName().contains("btnNote")){
            InterfaceController.getGuiManaging().changeMenuType();
            Component comp = (Component) e.getSource();
            comp.setBackground(Color.red);
        }
        else if(e.getSource().getClass().getName().contains("View.btnCategory")){
            GUISeeming.mouseClickedRefresh((Component)e.getSource());
        }
        else if(e.getSource().getClass().getName().equals("javax.swing.JScrollPane")){//Boşluğa tıklandıysa seçme işlemini sıfırla
            if(e.getSource().equals(InterfaceController.getGuiManaging().getMP().getManagPanel().getScrpaneMain())){
                if(InterfaceController.getGuiManaging().getChoosedThing() != null){
          //          GUISeeming.mouseUnClickedRefresh(InterfaceController.getGuiManaging().getChoosedThing().getGuiComponent());
                    InterfaceController.getGuiManaging().setChoosedThing(null);
                }
            }
//            System.out.println("AAA: " + InterfaceController.getGuiManaging().getCurrentComp().getClass().getName());
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        switch(e.getSource().getClass().getName()){
            case "javax.swing.JButton" : {
                GUISeeming.mouseOnMovementRefresh((JButton) e.getSource());
                return;
            }
            case "javax.swing.JComboBox" : {
                GUISeeming.mouseOnMovementRefresh((JComboBox) e.getSource());
                return;
            }
            case "javax.swing.JMenuItem" : {
                GUISeeming.mouseOnMovementRefresh((JMenuItem) e.getSource());
                return;
            }
            case "javax.swing.JScrollBar" : {
                GUISeeming.mouseOnMovementRefresh((JScrollBar) e.getSource());
                return;
            }
            case "View.btnCategory" : {
                GUISeeming.mouseOnMovementRefresh((JButton) e.getSource());
                return;
            }
            case "javax.swing.plaf.metal.MetalScrollButton" :{
                GUISeeming.mouseOnMovementRefresh((javax.swing.plaf.metal.MetalScrollButton) e.getSource());
                return;
            }
            case "javax.swing.plaf.basic.BasicArrowButton" :{
                GUISeeming.mouseOnMovementRefresh((javax.swing.plaf.basic.BasicArrowButton) e.getSource());
                return;
            }
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        switch(e.getSource().getClass().getName()){
            case "javax.swing.JButton" : {
                GUISeeming.mouseOffMovementRefresh((JButton) e.getSource());
                return;
            }
            case "javax.swing.JComboBox" : {
                GUISeeming.mouseOffMovementRefresh((JComboBox) e.getSource());
                return;
            }
            case "javax.swing.JMenuItem" : {
                GUISeeming.mouseOffMovementRefresh((JMenuItem) e.getSource());
                return;
            }
            case "javax.swing.JScrollBar" : {
                GUISeeming.mouseOffMovementRefresh((JScrollBar) e.getSource());
                return;
            }
            case "View.btnCategory" : {
                GUISeeming.mouseOffMovementRefresh((Component) e.getSource());
                return;
            }
            case "javax.swing.plaf.metal.MetalScrollButton" :{
                GUISeeming.mouseOffMovementRefresh((javax.swing.plaf.metal.MetalScrollButton) e.getSource());
                return;
            }
            case "javax.swing.plaf.basic.BasicArrowButton" :{
                GUISeeming.mouseOffMovementRefresh((javax.swing.plaf.basic.BasicArrowButton) e.getSource());
                return;
            }
        }
            
    }
    
}
