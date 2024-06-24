package Control;

import Base.Category;
import View.ProduceCategoryPanel;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ActsOnCategoryAdding implements ActionListener{
    ProduceCategoryPanel pnlAddCategory;
    Color categoryColor = null;

    public ActsOnCategoryAdding(ProduceCategoryPanel pnlAddCategory){
        this.pnlAddCategory = pnlAddCategory;
        categoryColor = null;
    }

//İŞLEM YÖNTEMLERİ:
    @Override
    public void actionPerformed(ActionEvent e) {
        if(pnlAddCategory != null){
            if(e.getSource() == pnlAddCategory.getBtnColorChooser()){
                categoryColor = pnlAddCategory.openColorChooser();
            }
            else if(e.getSource() == pnlAddCategory.getBtnAdd()){
                String hexCode = "#002354";
                if(categoryColor != null){
                    hexCode = Services.ColorProcess.getColorService().getAsHexCode(categoryColor);
                    System.err.println("categoryColor 'ın gösterdiği değişken boş değil");
                }
                Category category = new Category(pnlAddCategory.getTxtCategoryName().getText().trim(), hexCode, pnlAddCategory.getTxtExplanation().getText());
                
                if(IDARE.addCategoryToSys(category)){
                    if(!IDARE.isInProcessing())
                        IDARE.openCategoryInFolderSeeming(category);
                    pnlAddCategory.resetPanel();
                    pnlAddCategory.getTxtCategoryName().requestFocus();
                    IDARE.updateGUIData();
                }
                pnlAddCategory.getTxtCategoryName().requestFocus();
            }
        }
    }
    private String getHexCode(Color color){
        if(color == null)
            return null;
        //.;.
        return null;
    }

}
