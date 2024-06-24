package View;

import Base.GUISeeming;
import Control.Movements;
import Control.WriteAction;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

public class EditTools extends JPanel{
    FlowLayout layoutMain;
    JButton btnInParagraph, btnOutParagraph, btnSave, btnExit, btnSaveThenExit, btnBackTo, btnNextTo/*, btnBold, btnUnderline*/;
    JSpinner spinSize;
    WriteAction wrAction;
    Movements movementActs;
    private static EditTools editTools;

    public EditTools(){
        setLayout(getLayoutMain());
        this.add(getBtnInParagraph());
        this.add(getBtnOutParagraph());
        this.add(getSpinSize());
        this.add(getBtnSave());
        this.add(getBtnExit());
        this.add(getBtnSaveThenExit());
    //    this.add(getBtnBackTo());
    //    this.add(getBtnNextTo());
    //    this.add(getBtnBold());
    //    this.add(getBtnUnderline());
        this.setPreferredSize(new Dimension(250, 31));
   //     this.setPreferredSize(new Dimension(MainFrame.getFrame_Main().getContentPane().getComponent(0).getHeight(), MainFrame.getFrame_Main().getContentPane().getComponent(0).getWidth()));
        MainFrame.getFrame_Main().setVisible(true);
    }

//İŞLEM YÖNTEMLERİ:
    public void addingListeners(){
        EditTools.getEditTools().getBtnInParagraph().addActionListener(getWrAction());
        EditTools.getEditTools().getBtnOutParagraph().addActionListener(getWrAction());
        EditTools.getEditTools().getBtnSave().addActionListener(getWrAction());
        EditTools.getEditTools().getBtnExit().addActionListener(getWrAction());
        EditTools.getEditTools().getBtnSaveThenExit().addActionListener(getWrAction());
        EditTools.getEditTools().getSpinSize().addChangeListener(getWrAction());
    //    EditTools.getEditTools().getBtnBackTo().addActionListener(getWrAction());
    //    EditTools.getEditTools().getBtnNextTo().addActionListener(getWrAction());
    /*    EditTools.getEditTools().getB3_bold().addActionListener(getWrAction());
        EditTools.getEditTools().getB4_underline().addActionListener(getWrAction());*/
    }

//ERİŞİM YÖNTEMLERİ:
    public JButton getBtnInParagraph(){
        if (btnInParagraph == null){
            btnInParagraph = new JButton(">>");
            btnInParagraph.addMouseListener(getMovementActs());
            btnInParagraph.setPreferredSize(new Dimension(30, 21));
        }
        return btnInParagraph;
    }
    public void setBtnInParagraph(JButton btnInParagraph){
        this.btnInParagraph = btnInParagraph;
    }
    public JButton getBtnOutParagraph(){
        if (btnOutParagraph == null){
            btnOutParagraph = new JButton("<<");
            btnOutParagraph.addMouseListener(getMovementActs());
            btnOutParagraph.setPreferredSize(new Dimension(30, 21));
        }
        return btnOutParagraph;
    }
    public void setBtnOutParagraph(JButton btnOutParagraph){
        this.btnOutParagraph = btnOutParagraph;
    }
    public JButton getBtnSave(){
        if (btnSave == null){
            btnSave = new JButton("KAYDET");
            btnSave.setEnabled(false);
            btnSave.addMouseListener(getMovementActs());
            btnSave.setPreferredSize(new Dimension(60, 21));
        }
        return btnSave;
    }
    public void setBtnSave(JButton btnSave){
        this.btnSave = btnSave;
    }
    public JButton getBtnExit(){
        if (btnExit == null){
            btnExit = new JButton("ÇIK");
            btnExit.addMouseListener(getMovementActs());
            btnExit.setPreferredSize(new Dimension(40, 21));
        }
        return btnExit;
    }
    public void setBtnExit(JButton btnExit){
        this.btnExit = btnExit;
    }
    public JButton getBtnSaveThenExit(){
        if (btnSaveThenExit == null){
            btnSaveThenExit = new JButton("KAYDET ve ÇIK");
            btnSaveThenExit.addMouseListener(getMovementActs());
            btnSaveThenExit.setPreferredSize(new Dimension(100, 21));
        }
        return btnSaveThenExit;
    }
    public void setBtnSaveThenExit(JButton btnSaveThenExit){
        this.btnSaveThenExit = btnSaveThenExit;
    }
    public JButton getBtnBackTo(){
        if(btnBackTo == null){
            btnBackTo = new JButton("GERİ AL");
            btnBackTo.addMouseListener(getMovementActs());
            btnBackTo.setPreferredSize(new Dimension(100, 21));
        }
        return btnBackTo;
    }
    public JButton getBtnNextTo(){
        if(btnNextTo == null){
            btnNextTo = new JButton("YİNELE");
            btnNextTo.addMouseListener(getMovementActs());
            btnNextTo.setPreferredSize(new Dimension(100, 21));
        }
        return btnNextTo;
    }
    public JSpinner getSpinSize(){
        if(spinSize == null){
            spinSize = new JSpinner(new SpinnerNumberModel(14, 8, 34, 1));
            spinSize.setPreferredSize(new Dimension(40, 21));
      //      spinSize.addChangeListener(getWrAction());
        }
        return spinSize;
    }
    public void setSpinSize(JSpinner spinSize){
        this.spinSize = spinSize;
    }
    /*public JButton getBtnBold() {
    if (btnBold == null){
    btnBold = new JButton("B");
    btnBold.addMouseListener(getMovementActs());
    }
    return btnBold;
    }
    public void setBtnBold(JButton btnBold) {
    this.btnBold = btnBold;
    }
    public JButton getBtnUnderline() {
    if (btnUnderline == null){
    btnUnderline = new JButton("_");
    btnUnderline.addMouseListener(getMovementActs());
    }
    return btnUnderline;
    }
    public void setBtnUnderline(JButton btnUnderline) {
    this.btnUnderline = btnUnderline;
    }*/
    public FlowLayout getLayoutMain(){
        if (layoutMain == null){
            layoutMain = new FlowLayout(FlowLayout.LEFT, 3, 3);
        }
        return layoutMain;
    }
    public void setLayoutMain(FlowLayout layoutMain){
        this.layoutMain = layoutMain;
    }
    public Movements getMovementActs(){
        if(movementActs == null){
            movementActs = new Movements();
        }
        return movementActs;
    }
    public void setMovementActs(Movements movementActs){
        this.movementActs = movementActs;
    }
    public WriteAction getWrAction(){
        if (wrAction == null){
            wrAction = new WriteAction(EditingPanel.getEditPanel());
        }
        return wrAction;
    }
    public void setWrAction(WriteAction wrAction){
        this.wrAction = wrAction;
    }
    public static EditTools getEditTools(){
        if (editTools == null){
            editTools = new EditTools();
        }
        return editTools;
    }
    public static void setEditTools(EditTools editTools){
        EditTools.editTools = editTools;
    }
}