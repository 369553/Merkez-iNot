package View;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridBagLayoutInfo;
import java.awt.Rectangle;
/*
MODE:
0 : TAM ÖZELLİK ATFI
1 : YARI - ÖZELLİK ATFI
2 : BASİT MOD
*/
public class SpecialLayout extends GridBagLayout{
    int MODE = 2;
    
    public SpecialLayout(int MODE) {
        checkMODE();
        startLayout();
    }

    public SpecialLayout() {
        startLayout();
    }
    
//YENİ İŞLEM YÖNTEMLERİ:
    private void checkMODE(){
        if(MODE <0 || MODE > 2)
            MODE = 2;
    }
    
    private void startLayout(){
        switch(MODE){
            case 0: {
                //.;.
                break;
            }
            case 1: {
                //.;.
                break;
            }
            default: {
                
                break;
            }
        }
    }
    
//ÜST SINIF YÖNTEMLERİ:
    @Override
    protected void ArrangeGrid(Container parent) {
        super.ArrangeGrid(parent); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected Dimension GetMinSize(Container parent, GridBagLayoutInfo info) {
        return super.GetMinSize(parent, info); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void addLayoutComponent(Component comp, Object constraints) {
        super.addLayoutComponent(comp, constraints); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void addLayoutComponent(String name, Component comp) {
        super.addLayoutComponent(name, comp); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void adjustForGravity(GridBagConstraints constraints, Rectangle r) {
        super.adjustForGravity(constraints, r); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void arrangeGrid(Container parent) {
        super.arrangeGrid(parent); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Dimension preferredLayoutSize(Container parent) {
        return super.preferredLayoutSize(parent); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void removeLayoutComponent(Component comp) {
        super.removeLayoutComponent(comp); //To change body of generated methods, choose Tools | Templates.
    }

    public void setColumnWeights(double[] columnWeights) {
        this.columnWeights = columnWeights;
    }

    public void setColumnWidths(int[] columnWidths) {
        this.columnWidths = columnWidths;
    }

    @Override
    public void setConstraints(Component comp, GridBagConstraints constraints) {
        super.setConstraints(comp, constraints); //To change body of generated methods, choose Tools | Templates.
    }

    public void setDefaultConstraints(GridBagConstraints defaultConstraints) {
        this.defaultConstraints = defaultConstraints;
    }

    public void setRowHeights(int[] rowHeights) {
        this.rowHeights = rowHeights;
    }

    public void setRowWeights(double[] rowWeights) {
        this.rowWeights = rowWeights;
    }
    
}