package Control;

import Base.Category;
import Base.DataBase;
import Base.GUISeeming;
import Base.Note;
import View.ManagementPanel;
import View.btnFolder;
import View.btnNote;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ManagementPanelActions implements ActionListener{
    ManagementPanel pnlMain;
    
    public ManagementPanelActions(ManagementPanel pnlMain) {
        this.pnlMain = pnlMain;
    }

    @Override
    public void actionPerformed(ActionEvent e){
        if(e.getSource().getClass().getName().equals("View.btnNote")){
            btnNote btnData = (btnNote) e.getSource();
            if(InterfaceController.getGuiManaging().getChoosedThing() == null){
//                System.err.println("İlk kez tıklandı");
/*
BURASI KONTROL İÇİN:


                System.err.println("btnData: " + btnData.getStrName());
                System.out.println("LL: " + btnData.getNoteID());
                ArrayList<Category> categoryy = DataBase.getDatabase().getCategories();
                for(Category ct : categoryy){
                    System.out.println("ismi: " + ct.getName() + "\n" + 
                            "numarası: "+ ct.getID());
                }
*/
                InterfaceController.getGuiManaging().
                    setChoosedThing(new ManaGUIRelation(DataBase.getDatabase().
                    findNoteByID(btnData.getNoteID()), btnData));
        //        GUISeeming.mouseClickedRefresh(btnData);
            }
            else{
//                System.out.println("İkinci kez tıklandı");
                if(InterfaceController.getGuiManaging().getChoosedThing().getGuiComponent().equals(btnData)){
//                    System.out.println("Aynı bileşene tıklandı");
              //      GUISeeming.mouseUnClickedRefresh(btnData);
                    if(InterfaceController.getGuiManaging().getChoosedThing() == null)
                        return;
                    IDARE.openNote((Note) InterfaceController.getGuiManaging().getChoosedThing().getThing());
                    InterfaceController.getGuiManaging().setChoosedThing(null);
                }
                else{
//                    System.out.println("Farklı bileşene tıklandı");
          //          GUISeeming.mouseUnClickedRefresh(InterfaceController.getGuiManaging().getChoosedThing().getGuiComponent());
                    InterfaceController.getGuiManaging().setChoosedThing(new ManaGUIRelation(DataBase.getDatabase().
                        findNoteByID(btnData.getNoteID()), btnData));
       //             GUISeeming.mouseClickedRefresh(btnData);
                }
            }
        }
        else if(e.getSource().getClass().getName().equals("View.btnFolder")){
            btnFolder btnData = (btnFolder) e.getSource();
            if(InterfaceController.getGuiManaging().getChoosedThing() == null){
                InterfaceController.getGuiManaging().setChoosedThing(new ManaGUIRelation(DataBase.getDatabase().
                    findCategoryByID(btnData.getCategoryID()), btnData));
         //           GUISeeming.mouseClickedRefresh(btnData);
            }
            else{
                if(InterfaceController.getGuiManaging().getChoosedThing().getGuiComponent().equals(btnData)){
            //        GUISeeming.mouseUnClickedRefresh(btnData);
                    IDARE.openCategoryInFolderSeeming((Category) InterfaceController.getGuiManaging().getChoosedThing().getThing());
                    InterfaceController.getGuiManaging().setChoosedThing(null);
                }
                else{
           //         GUISeeming.mouseUnClickedRefresh(InterfaceController.getGuiManaging().getChoosedThing().getGuiComponent());
                    InterfaceController.getGuiManaging().setChoosedThing(new ManaGUIRelation(DataBase.getDatabase().
                        findCategoryByID(btnData.getCategoryID()), btnData));
           //         GUISeeming.mouseClickedRefresh(btnData);
                }
            }
        }
        /*System.err.println("e.getSource().getClass().getName(): " + e.getSource().getClass().getName() + 
                "\n" + e.getSource().toString());
        if(e.getSource() == pnlMain.getB1deneme()){
            InterfaceController.getGuiManaging().addToMP(new EditingPanel());*/
        }
}