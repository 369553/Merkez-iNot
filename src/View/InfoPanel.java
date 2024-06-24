package View;

import Base.Category;
import Base.DataBase;
import Base.GUISeeming;
import Base.Note;
import Control.Movements;
import Services.DateTimeService;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

public class InfoPanel {
    
//İŞLEM YÖNTEMLERİ:
    public static JPanel prepareNoteInfoPanel(Note note){
        JPanel pnlInfoScreen;
        GridBagLayout layoutForInfoScreen;
        layoutForInfoScreen = new GridBagLayout();
        pnlInfoScreen = new JPanel(layoutForInfoScreen);
        JLabel lblName, lblTags, lblLastModificationDate, lblLastChangeByWho, lblProduceTime, lblCategory,
            lblProduceByWho;
        JLabel lblDataName, lblDataLastModificationDate, lblDataLastChangeByWho, lblDataProduceTime,
            lblDataCategory, lblDataProduceByWho;
        Movements movementsActs = new Movements();
        JList liTags;
        String[] strTags = null;
        //GÖRSEL ELEMANLARI DEĞERLERLE DOLDUR
        if(!note.getTags().isEmpty()){
            strTags = new String[note.getTags().size()];
            for(int index = 0; index < note.getTags().size(); index++){
                strTags[index] = note.getTags().get(index).getName();
            }
            liTags = new JList(strTags);
        }
        else
            liTags = new JList();
        JScrollPane scrpaneTags = new JScrollPane(liTags, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrpaneTags.setPreferredSize(new Dimension(70, 70));
        scrpaneTags.getHorizontalScrollBar().addMouseListener(movementsActs);
        scrpaneTags.getVerticalScrollBar().addMouseListener(movementsActs);
        lblName = new JLabel("Not ismi: ", SwingConstants.CENTER);
        lblTags = new JLabel("Etiketler: ", SwingConstants.CENTER);
        lblLastModificationDate = new JLabel("Son değişiklik târihi: ", SwingConstants.CENTER);
        lblLastChangeByWho = new JLabel("Son değişikliği yapan kullanıcı: ", SwingConstants.CENTER);
        lblProduceTime = new JLabel("Not oluşturma târihi: ", SwingConstants.CENTER);
        lblCategory = new JLabel("Kategorisi: ", SwingConstants.CENTER);
        lblProduceByWho = new JLabel("Notu oluşturan kullanıcı: ", SwingConstants.CENTER);
        lblDataName = new JLabel(note.getName(), SwingConstants.CENTER);
        lblDataLastModificationDate = new JLabel(DateTimeService.getDtService().
                showDateTimeAsString(note.getLast_change_time()), SwingConstants.CENTER);
        lblDataLastChangeByWho = new JLabel("Bu özellik henüz eklenmedi", SwingConstants.CENTER);
        lblDataProduceTime = new JLabel(DateTimeService.getDtService().
                showDateTimeAsString(note.getProduce_time()), SwingConstants.CENTER);
        lblDataCategory = new JLabel(note.getCategory().getName(), SwingConstants.CENTER);
        lblDataProduceByWho = new JLabel(note.getUser().getName(), SwingConstants.CENTER);
        Add.ADDCOMP(pnlInfoScreen, lblName, 0, 0, 1, 1, Add.Insetser(2, 5, 2, 5), GridBagConstraints.BOTH, 1.0, 1.0);
        Add.ADDCOMP(pnlInfoScreen, lblTags, 0, 1, 1, 1, Add.Insetser(2, 5, 2, 5), GridBagConstraints.BOTH, 1.0, 1.0);
        Add.ADDCOMP(pnlInfoScreen, lblLastModificationDate, 0, 2, 1, 1, Add.Insetser(2, 5, 2, 5), GridBagConstraints.BOTH, 1.0, 1.0);
        Add.ADDCOMP(pnlInfoScreen, lblLastChangeByWho, 0, 3, 1, 1, Add.Insetser(2, 5, 2, 5), GridBagConstraints.BOTH, 1.0, 1.0);
        Add.ADDCOMP(pnlInfoScreen, lblProduceTime, 0, 4, 1, 1, Add.Insetser(2, 5, 2, 5), GridBagConstraints.BOTH, 1.0, 1.0);
        Add.ADDCOMP(pnlInfoScreen, lblCategory, 0, 5, 1, 1, Add.Insetser(2, 5, 2, 5), GridBagConstraints.BOTH, 1.0, 1.0);
        Add.ADDCOMP(pnlInfoScreen, lblProduceByWho, 0, 6, 1, 1, Add.Insetser(2, 5, 2, 5), GridBagConstraints.BOTH, 1.0, 1.0);
        Add.ADDCOMP(pnlInfoScreen, lblDataName, 1, 0, 1, 1, Add.Insetser(0, 5, 2, 5), GridBagConstraints.BOTH, 1.0, 1.0);
        Add.ADDCOMP(pnlInfoScreen, scrpaneTags, 1, 1, 1, 1, Add.Insetser(0, 5, 2, 5), GridBagConstraints.BOTH, 1.0, 1.0);
        Add.ADDCOMP(pnlInfoScreen, lblDataLastModificationDate, 1, 2, 1, 1, Add.Insetser(0, 5, 2, 5), GridBagConstraints.BOTH, 1.0, 1.0);
        Add.ADDCOMP(pnlInfoScreen, lblDataLastChangeByWho, 1, 3, 1, 1, Add.Insetser(0, 5, 2, 5), GridBagConstraints.BOTH, 1.0, 1.0);
        Add.ADDCOMP(pnlInfoScreen, lblDataProduceTime, 1, 4, 1, 1, Add.Insetser(0, 5, 2, 5), GridBagConstraints.BOTH, 1.0, 1.0);
        Add.ADDCOMP(pnlInfoScreen, lblDataCategory, 1, 5, 1, 1, Add.Insetser(0, 5, 2, 5), GridBagConstraints.BOTH, 1.0, 1.0);
        Add.ADDCOMP(pnlInfoScreen, lblDataProduceByWho, 1, 6, 1, 1, Add.Insetser(0, 5, 2, 5), GridBagConstraints.BOTH, 1.0, 1.0);
        GUISeeming.appGUI(pnlInfoScreen);
        return pnlInfoScreen;
    }
    
    public static JPanel prepareCategoryInfoPanel(Category category){
        int tagNumber = 0;
        String[] strTags;
        JPanel pnlInfoScreen;
        GridBagLayout layoutForInfoScreen;
        layoutForInfoScreen = new GridBagLayout();
        pnlInfoScreen = new JPanel(layoutForInfoScreen);
        JLabel lblName, lblMalumat, lblProduceTime, lblLastChangeTime, lblLastModificationOnCategory,
            lblCategoryColor, lblIncludeTags, lblNoteNumber;
        JLabel lblDataName, lblDataMalumat, lblDataProduceTime, lblDataLastChangeTime,
            lblDataLastModificationOnCategory, lblDataCategoryColor, lblDataNoteNumber;
        Movements movementActs = new Movements();
        JScrollPane scrpaneTags;
        JList<String> liTags = null;
        strTags = DataBase.getDatabase().getNoteTagsOfCategory(category.getID());
        if(strTags.length > 0){
            liTags = new JList(strTags);
        }
        else
            liTags = new JList();
        scrpaneTags = new JScrollPane(liTags, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrpaneTags.setPreferredSize(new Dimension(70, 70));
        scrpaneTags.getVerticalScrollBar().addMouseListener(movementActs);
        scrpaneTags.getHorizontalScrollBar().addMouseListener(movementActs);

        lblName = new JLabel("Kategori ismi: ", SwingConstants.CENTER);
        lblMalumat = new JLabel("Kategori açıklaması", SwingConstants.CENTER);
        lblProduceTime = new JLabel("Kategorinin oluşturulduğu târih: ", SwingConstants.CENTER);
        lblLastChangeTime = new JLabel("En son yapılan değişikliğin târihi: ", SwingConstants.CENTER);
        lblLastModificationOnCategory = new JLabel("Kategori bilgileri üzerindeki son değişiklik: ", SwingConstants.CENTER);
        lblCategoryColor = new JLabel("Kategori rengi: ", SwingConstants.CENTER);
        lblIncludeTags = new JLabel("İçerdiği etiketler: ", SwingConstants.CENTER);
        lblNoteNumber = new JLabel("Not sayısı: ", SwingConstants.CENTER);
        JTextArea txtMalumat = new JTextArea(category.getExplanation());
        txtMalumat.setPreferredSize(new Dimension(70, 70));
        txtMalumat.setLineWrap(true);
        txtMalumat.setEditable(false);
        
        JScrollPane scrpaneExplanation = new JScrollPane(txtMalumat,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrpaneExplanation.setPreferredSize(new Dimension(70, 70));
        scrpaneExplanation.getVerticalScrollBar().addMouseListener(movementActs);
        scrpaneExplanation.getHorizontalScrollBar().addMouseListener(movementActs);

        lblDataName = new JLabel(category.getName(), SwingConstants.CENTER);
        lblDataMalumat = new JLabel(category.getExplanation(), SwingConstants.CENTER);
//        lblDataProduceTime = new JLabel(category.getProduce_time().toString(), SwingConstants.CENTER);
        lblDataProduceTime = new JLabel(DateTimeService.getDtService().
                showDateTimeAsString(category.getProduce_time()), SwingConstants.CENTER);
        lblDataLastChangeTime = new JLabel(DateTimeService.getDtService().
                showDateTimeAsString(category.getLast_change_time()), SwingConstants.CENTER);
        lblDataLastModificationOnCategory = new JLabel("Bu özellik henüz eklenmedi", SwingConstants.CENTER);
        JPanel componentColor;
        componentColor = new JPanel();
        componentColor.setPreferredSize(new Dimension(70, 20));
        lblDataNoteNumber = new JLabel(Integer.toString(category.getNumberOfNotes()), SwingConstants.CENTER);

        Add.ADDCOMP(pnlInfoScreen, lblName, 0, 0, 1, 1, Add.Insetser(2, 5, 5, 2), GridBagConstraints.BOTH, 1.0, 1.0);
        Add.ADDCOMP(pnlInfoScreen, lblMalumat, 0, 1, 1, 1, Add.Insetser(2, 5, 5, 2), GridBagConstraints.BOTH, 1.0, 1.0);
        Add.ADDCOMP(pnlInfoScreen, lblProduceTime, 0, 2, 1, 1, Add.Insetser(2, 5, 5, 2), GridBagConstraints.BOTH, 1.0, 1.0);
        Add.ADDCOMP(pnlInfoScreen, lblLastChangeTime, 0, 3, 1, 1, Add.Insetser(2, 5, 5, 2), GridBagConstraints.BOTH, 1.0, 1.0);
        Add.ADDCOMP(pnlInfoScreen, lblLastModificationOnCategory, 0, 4, 1, 1, Add.Insetser(2, 5, 5, 2), GridBagConstraints.BOTH, 1.0, 1.0);
        Add.ADDCOMP(pnlInfoScreen, lblCategoryColor, 0, 5, 1, 1, Add.Insetser(2, 5, 5, 2), GridBagConstraints.BOTH, 1.0, 1.0);
        Add.ADDCOMP(pnlInfoScreen, lblIncludeTags, 0, 6, 1, 1, Add.Insetser(2, 5, 5, 2), GridBagConstraints.BOTH, 1.0, 1.0);
        Add.ADDCOMP(pnlInfoScreen, lblNoteNumber, 0, 7, 1, 1, Add.Insetser(2, 5, 5, 2), GridBagConstraints.BOTH, 1.0, 1.0);

        Add.ADDCOMP(pnlInfoScreen, lblDataName, 1, 0, 1, 1, Add.Insetser(2, 5, 5, 2), GridBagConstraints.BOTH, 1.0, 1.0);
        Add.ADDCOMP(pnlInfoScreen, scrpaneExplanation, 1, 1, 1, 1, Add.Insetser(2, 5, 5, 2), GridBagConstraints.BOTH, 1.0, 1.0);
        Add.ADDCOMP(pnlInfoScreen, lblDataProduceTime, 1, 2, 1, 1, Add.Insetser(2, 5, 5, 2), GridBagConstraints.BOTH, 1.0, 1.0);
        Add.ADDCOMP(pnlInfoScreen, lblDataLastChangeTime, 1, 3, 1, 1, Add.Insetser(2, 5, 5, 2), GridBagConstraints.BOTH, 1.0, 1.0);
        Add.ADDCOMP(pnlInfoScreen, lblDataLastModificationOnCategory, 1, 4, 1, 1, Add.Insetser(2, 5, 5, 2), GridBagConstraints.BOTH, 1.0, 1.0);
        Add.ADDCOMP(pnlInfoScreen, componentColor, 1, 5, 1, 1, Add.Insetser(2, 5, 5, 2), GridBagConstraints.BOTH, 1.0, 1.0);
        Add.ADDCOMP(pnlInfoScreen, scrpaneTags, 1, 6, 1, 1, Add.Insetser(2, 5, 5, 2), GridBagConstraints.BOTH, 1.0, 1.0);
        Add.ADDCOMP(pnlInfoScreen, lblDataNoteNumber, 1, 7, 1, 1, Add.Insetser(2, 5, 5, 2), GridBagConstraints.BOTH, 1.0, 1.0);
        GUISeeming.appGUI(pnlInfoScreen);
        componentColor.setBackground(Color.decode(category.getCategoryColor()));
        return pnlInfoScreen;
    }
}
