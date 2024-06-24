
package Control;

import Base.DataBase;
import View.MainPanel;
import View.OptionsPanel;
import Base.GUISeeming;
import Base.SystemSettings;
import View.ContactPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ActOnOptions implements ActionListener{
    OptionsPanel optionsPanel;
    MainPanel MP;
    String path = null;
    
    public ActOnOptions(OptionsPanel optionsPanel){
        this.optionsPanel = optionsPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e){
        if (optionsPanel != null){
            if(e.getSource() == optionsPanel.getBtnChooseLocation()){
                optionsPanel.openChooser();
            }
            else if(e.getSource() == optionsPanel.getChooser() ){
                getPath();
            }
            else if(e.getSource() == optionsPanel.getBtnSave()){
                boolean noPath = false;
                if(path == null)
                    noPath = true;
                else if(path.isEmpty())
                    noPath = true;
                if(optionsPanel.getChboxIsLogging().isSelected() && (noPath && (SystemSettings.getSettings().getPATHPosition() == null))){
                    ContactPanel.getContactPanel().showMessage("Sistem kaydı için bir dosya dizini seçin", "Warning");
                    return;
                }
                setSettings(optionsPanel);
                IDARE.openHomePage();
                /*this.MP = (MainPanel) MainFrame.getFrame_Main().getContentPane().getComponent(0);
                //this.MP.remove(this.MP.getComponent(2));
                this.MP.remove(optionsPanel);
                MainFrame.getFrame_Main().setVisible(true);
                Add.setAnchor(GridBagConstraints.CENTER);
                Add.setInsets(7, 7, 7, 7);
                Add.ADDCOMP(this.MP, this.MP.getManagPanel(), 0, 1, 4, 1, Add.getInsets(), GridBagConstraints.BOTH, 1.0, 1.0);
                MainFrame.getFrame_Main().repaint();
                MainFrame.getFrame_Main().setVisible(true);*/
            }
            else if(e.getSource() == optionsPanel.getBtnExit()){
                IDARE.openHomePage();
            }
        }
    }
    
    public void getPath(){
        if (optionsPanel.getChooser().getSelectedFile() != null){
            path = optionsPanel.getChooser().getSelectedFile().getAbsolutePath();
            optionsPanel.getChooser().setSelectedFile(null) ;
    //        System.out.println("path: "  + path ) ;
        }
    }
    
    private boolean setSettings(OptionsPanel panel){//Kullanıcı ayarları değiştiğinde sistem ayarları da değişmeli
        String seeming = extractSeeming(panel.getCmboxGUISeeming().getSelectedIndex());
        DataBase.getDatabase().getUser().getPersonalSettings().setIs_logging(panel.getChboxIsLogging().isSelected());
        DataBase.getDatabase().getUser().getPersonalSettings().setIs_autoSaving(panel.getChboxIsAutosave().isSelected());
        if(path != null)
            DataBase.getDatabase().getUser().getPersonalSettings().setPATHPosition(this.path);
        DataBase.getDatabase().getUser().getPersonalSettings().setCurrentGuiSeeming(GUISeeming.GUIOrderProducer(seeming));
        IDARE.app(DataBase.getDatabase().getUser().getPersonalSettings(), true);
//        System.out.println( SystemSettings.getSettings().toString() ) ;
        return true ;
    }
    
    public static String extractSeeming(int index){
        switch(index){
            case 0: return "Standard"; 
            case 1: return "Blue";
            case 2: return "Pink";
            case 3: return "Dark";
            default: return "Standard";
        }
    }
}