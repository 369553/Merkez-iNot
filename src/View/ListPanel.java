package View;

import Base.GUISeeming;
import Base.DataBase;
import Base.Note;
import Control.KeySignalControl;
import Control.ActKeyOnListPanel;
import Control.Movements;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableRowSorter;

public class ListPanel extends JPanel{
    GridBagLayout layoutForThis;
    JTable tblNotes;
    DefaultTableModel tblmdlNotesTable;
    JScrollPane scrpaneTable;
    JTextField txtSearch, txtNameForTable;
    JComboBox cmboxCategoriesForTable;
    JButton btnApp;
    ActKeyOnListPanel actsAndKeyAct;
    Movements mov;
    KeySignalControl keyAct;
    private String[] strColumnNames;
    private Object[][] data;
    TableCellEditor[] cellEditors;
    private TableRowSorter<? extends DefaultTableModel> srtSearch;
    
    public ListPanel(){
        this.setLayout(getLayoutForThis());
        addGUIComponents();
    //    getTblNotes().setRowSorter(getSrtSearch());
    }

//İŞLEM YÖNTEMLERİ:
    public void addGUIComponents(){
        Add.setAnchor(GridBagConstraints.FIRST_LINE_START);
        Add.ADDCOMP(this, getTxtSearch(), 0, 0, 1, 1, Add.Insetser(0, 0, 5, 5), GridBagConstraints.BOTH, 1.0, 0.1);
        Add.setAnchor(GridBagConstraints.FIRST_LINE_END);
        Add.ADDCOMP(this, getBtnApp(), 1, 0, 1, 1, Add.Insetser(0, 5, 5, 0), GridBagConstraints.BOTH, 0.1, 0.1);
        Add.setAnchor(GridBagConstraints.CENTER);
        Add.ADDCOMP(this, getScrpaneForTable(), 0, 1, 2, 2, Add.Insetser(0, 0, 0, 0), GridBagConstraints.BOTH, 1.0, 6.0);
        GUISeeming.appGUI(this);
    }
    public void updateListPanel(){
        System.err.println("Tablo güncelleniyor, bi iznillâh");
        data = null;
        tblmdlNotesTable.setDataVector(getDataForTable(), getStrColumnNames());
    }

//İŞLEM YÖNTEMLERİ:
    //ARKAPLAN İŞLEM YÖNTEMLERİ:
    private void fetchAndFillData(){
        int index = 0;
        if(data == null)
            data = new Object[DataBase.getDatabase().getNoteNames().length][6];
        for(Note note : DataBase.getDatabase().getNotes()){
            data[index] = extractTableDataFromNote(note);
            index++;
        }
    }
    private Object[] extractTableDataFromNote(Note note){
        Object[] noteData = new Object[6];
        noteData[0] = note.getName();
        noteData[1] = note.getCategory().getName();
        noteData[2] = Services.DateTimeService.getDtService().showDateTimeAsString(note.getLast_change_time());
        noteData[3] = Services.DateTimeService.getDtService().showDateTimeAsString(note.getProduce_time());
        String[] noteTagNames = DataBase.getDatabase().getNoteTagNamesOfNote(note.getID());
        StringBuilder strBuilder = new StringBuilder();
        for(int index = 0; index < noteTagNames.length - 1; index++){
            strBuilder.append(noteTagNames[index] + ", ");
        }
        if(noteTagNames.length > 0)
            strBuilder.append(noteTagNames[noteTagNames.length - 1]);
        noteData[4] = strBuilder.toString();
        noteData[5] = note.getUser().getName();
        return noteData;
    }
    public TableCellEditor getCellEditorForTable(String nameOfClass){
        switch(nameOfClass){
            case "Base.Note" :{
                return getCellEditors()[0];
            }
            case "Base.Category" :{
                return getCellEditors()[1];
            }
            default :{
                return getCellEditors()[2];
            }
        }
    }

//ERİŞİM YÖNTEMLERİ:
    public JTable getTblNotes() {
        if (tblNotes == null){
            tblNotes = new JTable();
            tblNotes.setModel(getTableModelNotesTable());
    //GEREK YOK, VARSAYILAN OLARAK SEÇİLİYMİŞ        tblNotes.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
         //   tblNotes.setCursor(new Cursor(java.awt.Cursor.DEFAULT_CURSOR));
            tblNotes.setColumnSelectionAllowed(false);
            tblNotes.setDefaultEditor(tblNotes.getColumnClass(0), getCellEditorForTable(tblNotes.getColumnClass(0).getName()));
            tblNotes.setDefaultEditor(tblNotes.getColumnClass(1), getCellEditorForTable(tblNotes.getColumnClass(1).getName()));
            tblNotes.setDefaultEditor(java.lang.String.class, getCellEditorForTable("java.lang.String"));
            tblNotes.setRowSorter(getSrtSearch());
            /*
            JTableHeader header = new JTableHeader();
            header.setBackground(Color.red);
            header.setTable(tblNotes);
            tblNotes.setTableHeader(header);
            tblNotes.setShowVerticalLines(false);
            tblNotes.setShowHorizontalLines(true);
            header.setVisible(true);
            header.resizeAndRepaint();
            */
        }
        return tblNotes;
    }
    private Object[][] getDataForTable(){
        if(data == null){
            data = new Object[DataBase.getDatabase().getNoteNames().length][6];
            fetchAndFillData();
        }
        return data;
    }
    public DefaultTableModel getTableModelNotesTable(){
        if(tblmdlNotesTable == null){
            tblmdlNotesTable = new DefaultTableModel(
            getDataForTable(),
            getStrColumnNames()){
            Class[] types = new Class [] {
                Base.Note.class, Base.Category.class, java.lang.String.class,
                     java.lang.String.class,  java.lang.String.class,  java.lang.String.class
            };
            @Override
            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }
            @Override
            public boolean isCellEditable(int row, int column){
                switch(column){
                    case 0 :{
                        return true;
                    }
                    case 1 :{
                        return true;
                    }
                    default: return false;
                }
            }
            public void addRow(Note data){
                Object[] noteData = extractTableDataFromNote(data);
                this.addRow(noteData);
            }
            };
        }
        return tblmdlNotesTable;
    }
    public JScrollPane getScrpaneForTable(){
        if(scrpaneTable == null){
            scrpaneTable = new JScrollPane(getTblNotes(),
                    ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
            scrpaneTable.setPreferredSize(new Dimension(450, 190));
            //İHTİYAÇ YOKSA SİLLLLL:scrTable.setViewportView(getTblNotes());
        }
        return scrpaneTable;
    }
    private String[] getStrColumnNames(){
        if(strColumnNames == null){
            strColumnNames = new String [] {
                "İSİM", "KATEGORİ", "SON DEĞİŞTİRİLME TÂRİHİ", "OLUŞTURULMA TÂRİHİ",
                   "ETİKETLER", "OLUŞTURAN KULLANICI", 
            };
        }
        return strColumnNames;
    }
    public JTextField getTxtSearch(){
        if(txtSearch == null){
            txtSearch = new JTextField("");
            txtSearch.setPreferredSize(new Dimension(250, 45));
            txtSearch.setHorizontalAlignment(JTextField.CENTER);
    //        txtSearch.addKeyListener(getActsAndKeyAct());
            txtSearch.getDocument().addDocumentListener(new DocumentListener(){
                @Override
                public void insertUpdate(DocumentEvent e){
                    updateTableForSort(e);
                }

                @Override
                public void removeUpdate(DocumentEvent e){
                    updateTableForSort(e);
                }

                @Override
                public void changedUpdate(DocumentEvent e){
                    updateTableForSort(e);
                }
            });
        }
        return txtSearch;
    }
    private void updateTableForSort(DocumentEvent event){
        String text = getTxtSearch().getText();
        if(text.trim().length() == 0)
            srtSearch.setRowFilter(null);
        else
            srtSearch.setRowFilter(RowFilter.regexFilter("(?i)" + text));
    }
    /*public void setTxtSearch(JTextField txtSearch) {
        this.txtSearch = txtSearch;
    }*/
    public GridBagLayout getLayoutForThis() {
        if(layoutForThis == null){
            layoutForThis = new GridBagLayout();
        }
        return layoutForThis;
    }
    public ActKeyOnListPanel getActsAndKeyAct() {
        if(actsAndKeyAct == null){
            actsAndKeyAct = new ActKeyOnListPanel(this);
        }
        return actsAndKeyAct;
    }
    public JTextField getTxtNameForTable() {
        if(txtNameForTable == null){
            txtNameForTable = new JTextField();
            txtNameForTable.setEditable(true);
            txtNameForTable.addKeyListener(getKeyAct());
        }
        return txtNameForTable;
    }
    public JComboBox getCmboxCategoriesForTable(){
        if(cmboxCategoriesForTable == null){
            cmboxCategoriesForTable = new JComboBox<String>(DataBase.getDatabase().getCategoryNames());
         //   cmboxCategoriesForTable.setPreferredSize(new Dimension(50, 55));
        }
        return cmboxCategoriesForTable;
    }
    public Movements getMov() {
        if(mov == null){
            mov = new Movements();
        }
        return mov;
    }
    public KeySignalControl getKeyAct(){
        if(keyAct == null){
            keyAct = new KeySignalControl("note");
        }
        return keyAct;
    }
    public TableCellEditor[] getCellEditors() {
        if(cellEditors == null){
            JTextField txt = new JTextField();
            txt.setEditable(false);
            cellEditors = new TableCellEditor[3];
            cellEditors[0] = new DefaultCellEditor(getTxtNameForTable());
            cellEditors[1] = new DefaultCellEditor(getCmboxCategoriesForTable());
            cellEditors[2] = new DefaultCellEditor(txt);
        }
        return cellEditors;
    }
    public JButton getBtnApp() {
        if(btnApp == null){
            btnApp = new JButton("Değişiklikleri kaydet");
            btnApp.setPreferredSize(new Dimension(120, 25));
            btnApp.addMouseListener(getMov());
            btnApp.setEnabled(false);
            btnApp.addActionListener(getActsAndKeyAct());
        }
        return btnApp;
    }
    public TableRowSorter<? extends DefaultTableModel> getSrtSearch() {
        if(srtSearch == null){
            srtSearch = (TableRowSorter<? extends DefaultTableModel>) getTblNotes().getRowSorter();
            if(srtSearch == null){
                getTblNotes().setAutoCreateRowSorter(true);
                srtSearch = (TableRowSorter<? extends DefaultTableModel>) getTblNotes().getRowSorter();
            }
        }
        return srtSearch;
    }
    public void updateTableForAffected(Note note){
        getTxtSearch().setText(null);
        Object[] dataToAdd = extractTableDataFromNote(note);
        getTableModelNotesTable().addRow(dataToAdd);
    }
}