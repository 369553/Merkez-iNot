package Control;

import Base.Category;
import Base.GUISeeming;
import View.ContactPanel;
import View.InfoPanel;
import View.InteractiveGUIStructs;
import View.MainFrame;
import View.MenuEditCategory;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JColorChooser;

public class ActionsOnMenuEditCategory implements ActionListener{
    MenuEditCategory menuEditCategory;
    boolean sameName = false;
    
    public ActionsOnMenuEditCategory(MenuEditCategory menuEditCategory) {
        this.menuEditCategory = menuEditCategory;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == menuEditCategory.getMenuitemRenameCategory()){
            String name = InteractiveGUIStructs.getActiveManager().showJustTextField("İsimlendir", "Yeni isim giriniz: ");
            if(InterfaceController.getGuiManaging().getChoosedThing().dataType.equals("Category")){
                if(renameCategory(name) != true){
                    if(sameName){
                        sameName = false;
                    }
                }
                else{
                    IDARE.updateGUIData();
                    GUISeeming.refreshGUI();
                }
            }
        }
        
        else if(e.getSource() == menuEditCategory.getMenuitemDeleteCategory()){
            boolean isSuccesful = false;
            Category currentCategory = (Category) InterfaceController.getGuiManaging().getChoosedThing().getThing();
            isSuccesful = IDARE.deleteCategoryFromSys(currentCategory);
            if(isSuccesful){
                Component deleted = (Component) InterfaceController.getGuiManaging().getChoosedThing().getGuiComponent();
                deleted.getParent().remove(deleted);
                /*InterfaceController.getGuiManaging().getCurrentComp().
                    remove((Component) InterfaceController.getGuiManaging().getChoosedThing().getGuiComponent());*/
                InterfaceController.getGuiManaging().setChoosedThing(null);
                        IDARE.updateGUIData();
                GUISeeming.refreshGUI();
            }
            else
                ContactPanel.getContactPanel().showMessage("Silme işlemi başarısız oldu.", "Warning");
        }
        
        else if(e.getSource() == menuEditCategory.getMenuitemChangeColor()){
            Component guiComponent = InterfaceController.getGuiManaging().getChoosedThing().getGuiComponent();
            Category currentCategory = (Category) InterfaceController.getGuiManaging().getChoosedThing().getThing();
            if(currentCategory == null)
                return;
            JColorChooser clrchooser = new JColorChooser(Color.BLACK);
            Color choosedColor = clrchooser.showDialog(InterfaceController.getGuiManaging().getMP(), "Renk seçiniz", Color.GREEN);
            if(choosedColor != null){
                if(IDARE.changeColorOfCategory(currentCategory.getName(), choosedColor)){
                    guiComponent.setBackground(Color.decode(currentCategory.getCategoryColor()));
                    guiComponent.setForeground(GUISeeming.findHarmonicTextColor(currentCategory.getCategoryColor()));
                }
            }
           // clrchooser.setVisible(true);
        }
        
        else if(e.getSource() == menuEditCategory.getMenuitemChangeExplanation()){
            Category currentCategory = (Category) InterfaceController.getGuiManaging().getChoosedThing().getThing();
            //AÇIKLAMADA KARAKTER SINIRI YOK; EKLENMELİ!
            String explanation = InteractiveGUIStructs.getActiveManager().showJustTextField("Kategori açıklaması değişimi",
                "Kategori açıklamasını değiştirebilirsiniz:");
            if(explanation == null)
                return;
            if(IDARE.changeExplanationOfCategory(currentCategory.getID(), explanation) == false)
                InteractiveGUIStructs.getActiveManager().showNotAcceptableError("İşlem başarısız!");
            //VERİTABANINA KAYDEDİLMELİ
            //AŞAĞIDAKİ YAPI KURULABİLİR Mİ, ÜZERİNDE ÇALIŞILMALI
            /*InteractiveGUIStructs.getActiveManager().showSpecialGUI(BasicPanel.prepareBasicPanel("JTextArea", 
                currentCategory.getExplanation()), "Kategori açıklaması değişimi",
                "Kategori açıklamasını değiştirebilirsiniz", 5, 5);*/
        }
        
        else if(e.getSource() == menuEditCategory.getMenuitemLookInfo()){
            InteractiveGUIStructs.getActiveManager().showSpecialGUI(
                InfoPanel.prepareCategoryInfoPanel((Category) InterfaceController.getGuiManaging().
                getChoosedThing().getThing()), "Bilgileri göster", "Kategori bilgileri");
        }
    }
    
//İŞLEM YÖNTEMLERİ:
    public boolean renameCategory(String categoryName){
        Category category = (Category) InterfaceController.getGuiManaging().getChoosedThing().getThing();
        if(category == null)
            return false;
        if(categoryName == null)
            return false;
        String name = categoryName.trim();
        for(int index = 0; index < name.length(); index++){
            boolean isAccept = KeySignalControl.isAcceptableForNote(name.charAt(index));
            if(isAccept == false){
                ContactPanel.getContactPanel().showMessage(name.charAt(index) + " karakteri burada kullanılmamalı", "Warning");
                return false;
            }
        }
        return IDARE.renameCategory(category.getID(), name);
    }
    
}
