package Control;

import Base.DataBase;
import Base.Note;
import View.PnlManageTags;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class ActOnPnlManageTags implements ActionListener, ListSelectionListener{
    PnlManageTags pnl;
    Color clrSelectedBackground, clrSelectedForeground;

    public ActOnPnlManageTags(PnlManageTags pnl) {
        this.pnl = pnl;
    }

//İŞLEM YÖNTEMLERİ:
    @Override
    public void actionPerformed(ActionEvent e) {
        if(pnl != null){
            if(e.getSource() == pnl.getBtnRemoveTag()){//Etiket kaldır
                if(pnl.getLiTagNotes().getSelectedValuesList().isEmpty())
                    return;
                String[] strSelectedValues = new String[pnl.getLiTagNotes().getSelectedValuesList().size()];
                for(int index = 0; index < strSelectedValues.length; index++){
                    strSelectedValues[index] = pnl.getLiTagNotes().getSelectedValuesList().get(index);
                }
                for(int index = 0; index < strSelectedValues.length; index++){
                    Note temp = DataBase.getDatabase().getNoteByName(strSelectedValues[index].split(":")[0].trim(),
                            strSelectedValues[index].split(" : ")[1].trim());
                    if(temp != null){
                        if(pnl.getChoosedTag() != null){
                            temp.removeTag(pnl.getChoosedTag());
                            pnl.refreshLiTagNotes();
                        }
                    }
                }
            }
            else if(e.getSource() == pnl.getBtndeleteTag()){//Etiketi sil
                if(pnl.getChoosedTag()!= null){
                    if(IDARE.deleteNoteTagFromSys(pnl.getChoosedTag())){
                        pnl.refreshLiTags();
                        pnl.refreshLiTagNotes();
                    }
                }
            }
            else if(e.getSource() == pnl.getBtnAddTagToNotes()){//Etiketi notlara ekle
                if(pnl.getChoosedTag()!= null){
                    if(pnl.getLiNotes().getSelectedValuesList().isEmpty())
                        return;
                    String[] strSelectedValues = new String[pnl.getLiNotes().getSelectedValuesList().size()];
                    for(int index = 0; index < strSelectedValues.length; index++){
                        strSelectedValues[index] = pnl.getLiNotes().getSelectedValuesList().get(index);
          //              System.out.println(": " + strSelectedValues[index]);
                    }
                    for(int index = 0; index < strSelectedValues.length; index++){
                        String choosedText = strSelectedValues[index];
                        String[] splittedText = strSelectedValues[index].split(" : ");
                        String noteName = splittedText[0];
                        String categoryName = splittedText[1];
                        if(splittedText.length > 2){
                            String[] values = parseText(choosedText, splittedText.length - 1);
                            noteName = values[0];
                            categoryName = values[1];
                            if(DataBase.getDatabase().getNoteByName(noteName.trim(), categoryName.trim()) == null){
                                values = parseText(choosedText, splittedText.length - 2);
                                noteName = values[0];
                                categoryName = values[1];
                            }
                        }   
                        Note temp = DataBase.getDatabase().getNoteByName(noteName.trim(),
                                categoryName.trim());
                        if(temp != null){
                            temp.addTag(pnl.getChoosedTag());
                        }
                    }
                    pnl.chooseTag();
                }
            }
        }
        return;
    }

    @Override
    public void valueChanged(ListSelectionEvent e){
    //    System.err.println("Sinyal geldi bi iznillâh");
        if(e.getSource() == pnl.getLiTags()){
            pnl.chooseTag();
        }
    }
//ARKAPLAN İŞLEM YÖNTEMLERİ:
    private String[] parseText(String text, int indexOfEndOfNoteName){//Cümlede kaç tâne notkalı virgül varsa gönderilen noktadan öncesi ve sonrası olmak üzere cümleye ikiye böler bi iznillâh
        int n = text.split(":").length - 1;
        int[] locations = new int[n];
        int index = 0;
        for(int sayac = 0; sayac < text.length(); sayac++){
            if(text.charAt(sayac) == ':'){
                locations[index] = sayac;
                index++;
            }
        }
        String[] values = new String[2];
        values[0] = text.substring(0, locations[indexOfEndOfNoteName - 1]);//not ismi
        values[1] = text.substring(locations[indexOfEndOfNoteName - 1] + 1, text.length());//kategori ismi ; ÖNEMLİ NOKTA : Son noktalı virgülün yeri bulunduktan sonraki harften itibâren almak gerekiyor
        return values;
    }
    private String[] parseText(String text){
        return parseText(text, 1);
    }
}
