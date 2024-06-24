package Control;

import Base.GUISeeming;
import View.BackupPanel;
import View.ContactPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFileChooser;

public class ActOnBackup implements ActionListener{
    BackupPanel pnl = null;
    JFileChooser chooser = null;
    String path;

    public ActOnBackup(BackupPanel panel){
        this.pnl = panel;
    }

//İŞLEM YÖNTEMLERİ:
    @Override
    public void actionPerformed(ActionEvent e){
        if(pnl != null){
            if(e.getSource() == pnl.getBtnHatirDB()){
                pnl.removeAll();
                pnl.installGUIForHatirDB();
                pnl.repaint();
            }
            else if(e.getSource() == pnl.getBtnExport()){
                pnl.removeAll();
                pnl.installGUIForExportNotesAsTxt();
                pnl.repaint();
            }
            else if(e.getSource() == pnl.getBtnBackTo()){
                if(chooser != null){
                    if(chooser.getSelectedFile() != null){
                       chooser.setSelectedFile(null);
                    }
                }
                pnl.removeAll();
                pnl.installFirstScreen();
                pnl.repaint();
            }
            else if(e.getSource() == pnl.getBtnBackupPosition()){
                openFileChooser();
            }
            else if(e.getSource() == pnl.getBtnComplete()){
                if(chooser == null){
                    ContactPanel.getContactPanel().showMessage("Lütfen dosya konumu seçin", "Warning");
                    return;
                }
                if ( chooser.getSelectedFile() != null ) {
                    path = chooser.getSelectedFile().getAbsolutePath() ;
                    chooser.setSelectedFile( null ) ;
                }
                else{
                    ContactPanel.getContactPanel().showMessage("Lütfen dosya konumu seçin", "Warning");
                    return;
                }
                boolean isCrypt = pnl.getChIsSaveWithPassword().isSelected();
                IDARE.takeBackup(isCrypt, path);
            }
            else if(e.getSource() == pnl.getBtnExportNotes()){
                if(chooser == null){
                    ContactPanel.getContactPanel().showMessage("Lütfen dosya konumu seçin", "Warning");
                    return;
                }
                if ( chooser.getSelectedFile() != null ) {
                    path = chooser.getSelectedFile().getAbsolutePath();
                    chooser.setSelectedFile( null );
                }
                else{
                    ContactPanel.getContactPanel().showMessage("Lütfen dosya konumu seçin", "Warning");
                    return;
                }
                boolean isSuccesful = IDARE.exportNotesWithoutCrypting(this, path);
                if(isSuccesful)
                    ContactPanel.getContactPanel().showMessage("Notlar başarıyla dışarı aktarıldı.", "Successful");
            }
        }
    }
    private void openFileChooser(){
        chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.addActionListener(this) ;
        chooser.setAcceptAllFileFilterUsed( true ) ;
        GUISeeming.appGUI(chooser);
        chooser.showDialog(pnl, "Klasörü seç" );
    }
}
