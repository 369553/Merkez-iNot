package View;

import Base.DataBase;
import Base.GUISeeming;
import Control.ActOnBackup;
import Control.Movements;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class BackupPanel extends JPanel{
    JLabel lblBackupPosition, lblSaveWithPassword;
    JButton btnBackupPosition, btnComplete, btnHatirDB, btnExport, btnBackTo, btnExportNotes;
    JCheckBox chIsSaveWithPassword;
    GridBagLayout compOrder;
    Movements mov;
    ActOnBackup act;
    boolean isChoosedHatirDBBackup = false;

    public BackupPanel() {
        this.setLayout(getCompOrder());
        installFirstScreen();
        GUISeeming.appGUI(this);
    }

//İŞLEM YÖNTEMLERİ:
    public void installFirstScreen(){
        Add.ADDCOMP(this, getBtnHatirDB(), 0, 0, 1, 1, Add.Insetser(2, 2, 5, 2), GridBagConstraints.BOTH, 0.0, 0.0, 60, 40);//
        Add.ADDCOMP(this, getBtnExport(), 0, 1, 1, 1, Add.Insetser(5, 2, 2, 2), GridBagConstraints.BOTH, 0.0, 0.0, 60, 40);//
        Add.setIpads(0, 0);
    }
    public void installGUIForHatirDB(){
        Add.setAnchor(GridBagConstraints.PAGE_START);
        Add.ADDCOMP(this, getBtnBackTo(), 0, 0, 2, 1, Add.Insetser(2, 2, 50, 2), GridBagConstraints.HORIZONTAL, 0.0, 0.0);
        Add.setIpads(30, 20);
        Add.setAnchor(GridBagConstraints.CENTER);
        Add.ADDCOMP(this, getLblBackupPosition(), 0, 1, 1, 1, Add.Insetser(2, 2, 5, 5), GridBagConstraints.CENTER, 0.0, 0.0);
        Add.ADDCOMP(this, getLblSaveWithPassword(), 0, 2, 1, 1, Add.Insetser(5, 2, 2, 5), GridBagConstraints.CENTER, 0.0, 0.0);
        Add.ADDCOMP(this, getBtnBackupPosition(), 1, 1, 1, 1, Add.Insetser(2, 5, 5, 2), GridBagConstraints.BOTH, 0.0, 0.0);
        Add.ADDCOMP(this, getChIsSaveWithPassword(), 1, 2, 1, 1, Add.Insetser(5, 5, 2, 2), GridBagConstraints.CENTER, 0.0, 0.0);
        Add.setAnchor(GridBagConstraints.SOUTH);
        Add.ADDCOMP(this, getBtnComplete(), 0, 3, 2, 1, Add.Insetser(20, 2, 2, 2), GridBagConstraints.HORIZONTAL, 0.0, 0.0);
        Add.setIpads(0, 0);
        Add.setAnchor(GridBagConstraints.CENTER);
    }
    public void installGUIForExportNotesAsTxt(){
        Add.setAnchor(GridBagConstraints.NORTH);
        Add.setIpads(30, 20);
        Add.ADDCOMP(this, getBtnBackTo(), 0, 0, 2, 1, Add.Insetser(20, 2, 5, 2), GridBagConstraints.HORIZONTAL, 0.0, 0.0);
        Add.setAnchor(GridBagConstraints.CENTER);
        Add.ADDCOMP(this, getBtnBackupPosition(), 0, 1, 1, 1, Add.Insetser(5, 2, 5, 2), GridBagConstraints.BOTH, 0.0, 0.0);
        Add.ADDCOMP(this, getBtnExportNotes(), 0, 2, 1, 1, Add.Insetser(5, 2, 2, 2), GridBagConstraints.BOTH, 0.0, 0.0);
        GUISeeming.appGUI(this);
        Add.setIpads(0, 0);
    }

//ERİŞİM YÖNTEMLERİ:
    public JLabel getLblBackupPosition() {
        if(lblBackupPosition == null){
            lblBackupPosition = new JLabel("Yedekleme konumu : ");
        }
        return lblBackupPosition;
    }
    public JLabel getLblSaveWithPassword() {
        if(lblSaveWithPassword == null){
            lblSaveWithPassword = new JLabel("Kullanıcı şifresiyle şifrele : ");
        }
        return lblSaveWithPassword;
    }
    public JButton getBtnBackupPosition() {
        if(btnBackupPosition == null){
            btnBackupPosition = new JButton("GÖZAT");
            btnBackupPosition.addMouseListener(getMov());
            btnBackupPosition.addActionListener(getAct());
        }
        return btnBackupPosition;
    }
    public JCheckBox getChIsSaveWithPassword() {
        if(chIsSaveWithPassword == null){
            chIsSaveWithPassword = new JCheckBox();
            chIsSaveWithPassword.setBorderPaintedFlat(false);
            chIsSaveWithPassword.setSelected(true);
        }
        return chIsSaveWithPassword;
    }
    public GridBagLayout getCompOrder() {
        if(compOrder == null){
            compOrder = new GridBagLayout();
        }
        return compOrder;
    }
    public Movements getMov() {
        if(mov == null){
            mov = new Movements();
        }
        return mov;
    }
    public JButton getBtnComplete() {
        if(btnComplete == null){
            btnComplete = new JButton("YEDEKLE");
            btnComplete.addMouseListener(getMov());
            btnComplete.addActionListener(getAct());
            btnComplete.setPreferredSize(new Dimension(70, 65));
        }
        return btnComplete;
    }
    public ActOnBackup getAct() {
        if(act == null){
            act = new ActOnBackup(this);
        }
        return act;
    }
    public JButton getBtnHatirDB() {
        if(btnHatirDB == null){
            btnHatirDB = new JButton("Notlarınızı şifreli olarak yedekleyin (HâtırDB)");
            btnHatirDB.addMouseListener(getMov());
            btnHatirDB.addActionListener(getAct());
            //btnHatirDB.setPreferredSize(new Dimension(170, 55));
        }
        return btnHatirDB;
    }
    public JButton getBtnExport() {
        if(btnExport == null){
            btnExport = new JButton("Notları metin belgesi olarak dışarı aktarmak için tıklayın");
            btnExport.addMouseListener(getMov());
            btnExport.addActionListener(getAct());
            //btnExport.setPreferredSize(new Dimension(170, 55));
        }
        return btnExport;
    }
    public JButton getBtnBackTo() {
        if(btnBackTo == null){
            btnBackTo = new JButton("ÖNCEKİ SAYFAYA DÖN");
            btnBackTo.addMouseListener(getMov());
            btnBackTo.addActionListener(getAct());
            btnBackTo.setPreferredSize(new Dimension(60, 30));
        }
        return btnBackTo;
    }
    public JButton getBtnExportNotes() {
        if(btnExportNotes == null){
            btnExportNotes = new JButton("Notları dışarı aktar");
            btnExportNotes.addActionListener(getAct());
            btnExportNotes.addMouseListener(getMov());
            btnExportNotes.setPreferredSize(new Dimension(140, 40));
        }
        return btnExportNotes;
    }
}