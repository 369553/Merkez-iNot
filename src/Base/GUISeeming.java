package Base;

import Control.IDARE;
import Control.InterfaceController;
import Control.Movements;
import Services.CryptingService;
import View.ContactPanel;
import View.ListCellRender;
import View.ListCellRenderStandard;
import View.ListPanel;
import View.MainFrame;
import View.ManagementPanel;
import View.Menu;
import View.ProduceCategoryPanel;
import View.ProduceNotePanel;
import View.btnCategory;
import View.btnFolder;
import View.btnNote;
import View.pnlCategory;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JViewport;
import javax.swing.border.Border;
import View.Organizer;
import View.PnlManageTags;
import View.ProduceNoteTag;
import View.TopMenuGround;
import java.awt.Dimension;
import javax.swing.JFormattedTextField;
import javax.swing.JSpinner;

public class GUISeeming implements IEntity{
    protected static GUISeeming guiOrder = GUISeeming.GUIOrderProducer("Standard");
    String guiSeemingName, backgroundColor, buttonColor, buttonTextColor, buttonOnMouseColor,
buttonTextOnMouseColor, textColor, menuColor, notingAreaColor, notingAreaTextColor, 
notingAreaCursorColor, notingAreaSelectedColor, notingAreaSelectedTextColor,
componentBorderColor, topMenuGroundBackgroundColor, releasebleMenuTextColor, releasebleMenuItemColor, releasebleMenuItemTextColor,
releasebleMenuItemOnMouseColor, releasebleMenuItemOnMouseTextColor, editToolsBackgroundColor;
    Font fontUI;

    //ÜRETİM FONKSİYONU:
    public GUISeeming(String guiSeemingName, String backgroundColor, String buttonColor, String buttonTextColor, String buttonOnMouseColor, String buttonTextOnMouseColor, String textColor, String menuColor, String notingAreaColor, String notingAreaTextColor, String notingAreaCursorColor, String notingAreaSelectedColor, String notingAreaSelectedTextColor, String componentBorderColor, String topMenuGroundBackgroundColor, String releasebleMenuTextColor, String releasebleMenuItemColor, String releasebleMenuItemTextColor, String releasebleMenuItemOnMouseColor, String releasebleMenuItemOnMouseTextColor, String editToolsBackgroundColor, Font fontUI) {
        this.guiSeemingName = guiSeemingName;
        this.backgroundColor = backgroundColor;
        this.buttonColor = buttonColor;
        this.buttonTextColor = buttonTextColor;
        this.buttonOnMouseColor = buttonOnMouseColor;
        this.buttonTextOnMouseColor = buttonTextOnMouseColor;
        this.textColor = textColor;
        this.menuColor = menuColor;
        this.notingAreaColor = notingAreaColor;
        this.notingAreaTextColor = notingAreaTextColor;
        this.notingAreaCursorColor = notingAreaCursorColor;
        this.notingAreaSelectedColor = notingAreaSelectedColor;
        this.notingAreaSelectedTextColor = notingAreaSelectedTextColor;
        this.componentBorderColor = componentBorderColor;
        this.topMenuGroundBackgroundColor = topMenuGroundBackgroundColor;
        this.releasebleMenuTextColor = releasebleMenuTextColor;
        this.releasebleMenuItemColor = releasebleMenuItemColor;
        this.releasebleMenuItemTextColor = releasebleMenuItemTextColor;
        this.releasebleMenuItemOnMouseColor = releasebleMenuItemOnMouseColor;
        this.releasebleMenuItemOnMouseTextColor = releasebleMenuItemOnMouseTextColor;
        this.editToolsBackgroundColor = editToolsBackgroundColor;
        this.fontUI = fontUI;
    }
    public static GUISeeming GUIOrderProducer (String guiSeemingName){
        switch(guiSeemingName){
            case "Standard":{
                return new GUISeeming("Standard",
        /*2*/                "#F5F5DC",     //  backgroundColor
        /*3*/                "#cfcfae",     //  buttonColor 
        /*4*/                "#800000",     //  buttonTextColor
        /*5*/                "#d2d260",     //  buttonOnMouseColor
        /*6*/                "#30d260",     //  buttonTextOnMouseColor
        /*7*/                "#7B3F00",     //  textColor   //9b244b
        /*8*/                "#fc8b8b",     //  menuColor
        /*9*/                "#d2c5b4",     //  notingAreaColor
        /*10*/                "#022249",    //  notingAreaTextColor
        /*11*/                "#9b241b",    //  notingAreaCursorColor
        /*12*/                "#976c88",    //  notingAreaSelectedColor
        /*13*/                "#000000",    //  notingAreaSelectedTextColor
        /*14*/                "#aca090",    //  componentBorderColor
        /*15*/                "#fc8b8b",    //  topMenuGroundBackgroundColor
        /*16*/                "#420808",    //  releasebleMenuTextColor
        /*17*/                "#e8a1a1",    //  releasebleMenuItemColor
        /*18*/                "#3f0303",    //  releasebleMenuItemTextColor
        /*19*/                "#8a0000",    //  releasebleMenuItemOnMouseColor
        /*20*/                "#8a7d00",    //  releasebleMenuItemOnMouseTextColor
        /*21*/                "#efcea4",    //  editToolsBackgroundColor
        /*22*/                new Font("Dialog", Font.ITALIC|Font.BOLD, 12));   //  fontUI
            }
            case "Pink":{
                return new GUISeeming("Pink",
        /*2*/                "#ffb4cd",
        /*3*/                "#f9c1dd",
        /*4*/                "#800000",
        /*5*/                "#d2d260",
        /*6*/                "#30d260", 
        /*7*/                "#0d1c2b",
        /*8*/                "#ff95a4",
        /*9*/                "#ff9dae",
        /*10*/                "#022249",
        /*11*/                "#9b241b",
        /*12*/                "#976c88", 
        /*13*/                "#3f0303",
        /*14*/                "#ba7155",
        /*15*/                "#d56c88", 
        /*16*/                "#3f0303",
        /*17*/                "#ff8bb4",
        /*18*/                "#3f0303", 
        /*19*/                "#8a0000",
        /*20*/                "#8a7d00",
        /*21*/                "#d79d9c",
        /*22*/                new Font("Dialog", Font.ITALIC|Font.BOLD, 12));
            }
            case "Blue":{
                return new GUISeeming("Blue",
        /*2*/                "#82ccff",   //  backgroundColor
        /*3*/                "#cfc3a4",   //  buttonColor 
        /*4*/                "#323fd1",   //  buttonTextColor
        /*5*/                "#b9ddcb",   //  buttonOnMouseColor
        /*6*/                "#7B3F00",   //  buttonTextOnMouseColor
        /*7*/                "#9b241b",   //  textColor   //9b244b
        /*8*/                "#c9d8ff",   //  menuColor
        /*9*/                "#16cadb",   //  notingAreaColor
        /*10*/                "#022249",  //  notingAreaTextColor
        /*11*/                "#8022a8",  //  notingAreaCursorColor
        /*12*/                "#8077dd",  //  notingAreaSelectedColor
        /*13*/                "#b43f00",  //  notingAreaSelectedTextColor
        /*14*/                "#1aa090",  //  componentBorderColor
        /*15*/                "#6e9cb6",  //  topMenuGroundBackgroundColor
        /*16*/                "#1059ae",  //  releasebleMenuTextColor
        /*17*/                "#0a979b",  //  releasebleMenuItemColor
        /*18*/                "#953f13",  //  releasebleMenuItemTextColor
        /*19*/                "#136a9b",  //  releasebleMenuItemOnMouseColor
        /*20*/                "#cf6a21",  //  releasebleMenuItemOnMouseTextColor
        /*21*/                "#cfc3f4",  //  editToolsBackgroundColor
        /*22*/                new Font("Dialog", Font.ITALIC|Font.BOLD, 12)); //  fontUI
            }
            case "Dark":{
                return new GUISeeming("Dark",
        /*2*/                "#2d1c3c",   //  backgroundColor
        /*3*/                "#11287a",   //  buttonColor 
        /*4*/                "#82ba77",   //  buttonTextColor
        /*5*/                "#6a2860",   //  buttonOnMouseColor
        /*6*/                "#4677d4",   //  buttonTextOnMouseColor
        /*7*/                "#eaba55",   //  textColor   //9b244b
        /*8*/                "#0b0023",   //  menuColor
        /*9*/                "#300112",   //  notingAreaColor
        /*10*/                "#eaba55",  //  notingAreaTextColor
        /*11*/                "#a21320",  //  notingAreaCursorColor
        /*12*/                "#9f1345",  //  notingAreaSelectedColor
        /*13*/                "#01aa0a",  //  notingAreaSelectedTextColor
        /*14*/                "#360053",  //  componentBorderColor
        /*15*/                "#280022",  //  topMenuGroundBackgroundColor
        /*16*/                "#1059ae",  //  releasebleMenuTextColor
        /*17*/                "#3b031a",  //  releasebleMenuItemColor
        /*18*/                "#70ba54",  //  releasebleMenuItemTextColor
        /*19*/                "#00020a",  //  releasebleMenuItemOnMouseColor
        /*20*/                "#000296",  //  releasebleMenuItemOnMouseTextColor
        /*21*/                "#100112",  //  editToolsBackgroundColor
        /*22*/                new Font("Dialog", Font.ITALIC|Font.BOLD, 12)); //  fontUI
            }
            default:{
                return new GUISeeming("Standard", "#F5F5DC", "#c0c0c0", "#800000", "#d2d260", "#7c538f", 
                        "#7B3F00", "#c0c0c0", "#a9c0e7", "#0d213a", "#3a0d15", "#976c88", 
                        "#2c021d", "#caa4a4", "#fc8b8b", 
                        "#004080", "#a67a97", "#07233f", 
                        "#731854", "#12020d", "#efcea4", 
                        new Font("Ubuntu", Font.ITALIC|Font.BOLD, 12));
            }
            /*Standard:{
                return new GUISeeming("Standard", "#F5F5DC", "#c0c0c0", "#800000", "#d2d260", "#7c538f", 
                        "#7B3F00", "#c0c0c0", "#a9c0e7", "#0d213a", "#3a0d15", "#976c88", 
                        "#2c021d", "#caa4a4", "#fc8b8b", 
                        "#004080", "#a67a97", "#07233f", 
                        "#731854", "#12020d", "#efcea4", 
                        new Font("Ubuntu", Font.ITALIC|Font.BOLD, 14));
            }*/
        }
    }
    protected static void appGUI(Component comp){
//        System.err.println("\nüst sınıf bilgisi: " + comp.getClass().getSuperclass().getName());
        if(comp.getClass().getSuperclass().getName().contains("JPanel") || comp.getClass().getSuperclass().getName().contains("JMenuBar")){
//            System.out.println("\nüst sınıfı JPanel olan comp.getClass().getName(): " + comp.getClass().getName());
            return;
        }
        switch(comp.getClass().getName()){
            case "javax.swing.JButton":{
                JButton button = (JButton) comp;
                button.setBackground(Color.decode(guiOrder.getButtonColor()));
                button.setForeground(Color.decode(guiOrder.getButtonTextColor()));
                button.setBorderPainted(true);
                button.setBorder(GUISeeming.getComponentBorder(true, true, true, true, 1));
                button.setFont(guiOrder.getFontUI());
                button.setFocusPainted(false);
                return;
            }
            case "javax.swing.JLabel":{
                comp.setForeground(Color.decode(guiOrder.getTextColor()));
                comp.setFont(guiOrder.getFontUI());
                return;
            }
            case "javax.swing.JTextArea":{
                JTextArea txtArea = (JTextArea) comp;
                txtArea.setBackground(Color.decode(guiOrder.getNotingAreaColor()));
                txtArea.setForeground(Color.decode(guiOrder.getNotingAreaTextColor()));
                txtArea.setCaretColor(Color.decode(guiOrder.getNotingAreaCursorColor()));
                txtArea.setSelectionColor(Color.decode(guiOrder.getNotingAreaSelectedColor()));
                txtArea.setSelectedTextColor(Color.decode(guiOrder.getNotingAreaSelectedTextColor()));
                txtArea.setBorder(GUISeeming.getComponentBorder(true, true, true, true));
                txtArea.setFont(guiOrder.getFontUI());
                /*if(txtArea.getBorder() != null){
                    txtArea.setBorder(getComponentBorder(true, true, true, true));
                }*/
                return;
            }
            case "javax.swing.JTextField" :{
                JTextField txtField = (JTextField) comp;
                txtField.setBackground(Color.decode(guiOrder.getNotingAreaColor()));
                txtField.setForeground(Color.decode(guiOrder.getNotingAreaTextColor()));
                txtField.setCaretColor(Color.decode(guiOrder.getNotingAreaCursorColor()));
                txtField.setSelectionColor(Color.decode(guiOrder.getNotingAreaSelectedColor()));
                txtField.setSelectedTextColor(Color.decode(guiOrder.getNotingAreaSelectedTextColor()));
                txtField.setBorder(GUISeeming.getComponentBorder(true, true, true, true, 1));
                txtField.setFont(guiOrder.getFontUI());
                return;
            }
            case "javax.swing.JPasswordField" :{
                JTextField txtField = (JTextField) comp;
                txtField.setBackground(Color.decode(guiOrder.getNotingAreaColor()));
                txtField.setForeground(Color.decode(guiOrder.getNotingAreaTextColor()));
                txtField.setCaretColor(Color.decode(guiOrder.getNotingAreaCursorColor()));
                txtField.setSelectionColor(Color.decode(guiOrder.getNotingAreaSelectedColor()));
                txtField.setSelectedTextColor(Color.decode(guiOrder.getNotingAreaSelectedTextColor()));
                txtField.setBorder(GUISeeming.getComponentBorder(true, true, true, true, 1));
                txtField.setFont(guiOrder.getFontUI());
                return;
            }
            case "javax.swing.JMenuItem" :{
                comp.setBackground(Color.decode(guiOrder.getReleasebleMenuItemColor()));
                comp.setForeground(Color.decode(guiOrder.getReleasebleMenuItemTextColor()));
                comp.setFont(guiOrder.getFontUI());
                return;
            }
            case "javax.swing.JMenu" :{
                JMenu menu = (JMenu) comp;
                menu.setFont(guiOrder.getFontUI());
                menu.setForeground(Color.decode(guiOrder.getReleasebleMenuTextColor()));
                for(int index = 0; index < menu.getItemCount(); index++){
                    GUISeeming.appGUI((Component)menu.getItem(index));
                }
                return;
            }
            case "javax.swing.JComboBox" :{
                JComboBox cmbox = (JComboBox) comp;
                cmbox.setFont(guiOrder.getFontUI());
                cmbox.setBackground(Color.decode(guiOrder.getButtonColor()));
                cmbox.setForeground(Color.decode(guiOrder.getButtonTextColor()));
                if(cmbox.getRenderer().getClass().getName().contains("ComboBoxRenderer")){
                    cmbox.setRenderer(ListCellRenderStandard.getConfiguredRenderer());
                }
                return;
            }
            case "javax.swing.JList" :{
                JList list = (JList) comp;
                list.setBackground(Color.decode(guiOrder.getBackgroundColor()));
                list.setForeground(Color.decode(guiOrder.getTextColor()));
                list.setSelectionBackground(Color.decode(guiOrder.getButtonColor()));
                list.setSelectionForeground(Color.decode(guiOrder.getButtonTextColor()));
                list.setBorder(GUISeeming.getComponentBorder(true, true, true, true, 1));
                return;
            }
            case "javax.swing.JScrollPane" :{
                JScrollPane scrPane= (JScrollPane) comp;
                for(Component sub : scrPane.getComponents()){
                    GUISeeming.appGUI((Component) sub);
                }
                scrPane.setViewportBorder(GUISeeming.getComponentBorder(true, true, true, true, 1));
                scrPane.getViewport().setBackground(Color.decode(guiOrder.backgroundColor));
                scrPane.setBorder(GUISeeming.getComponentBorder(true, true, true, true, 1));
                /*scrPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
                scrPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);*/
                scrPane.getHorizontalScrollBar().setBackground(Color.decode(guiOrder.backgroundColor));
                scrPane.getVerticalScrollBar().setBackground(Color.decode(guiOrder.backgroundColor));
                scrPane.getHorizontalScrollBar().setBorder(GUISeeming.getComponentBorder(true, true, true, true, 1));
                scrPane.getVerticalScrollBar().setBorder(GUISeeming.getComponentBorder(true, true, true, true, 1));
                scrPane.getHorizontalScrollBar().setUnitIncrement(16);
                scrPane.getVerticalScrollBar().setUnitIncrement(16);
                scrPane.getVerticalScrollBar().setPreferredSize(new Dimension(10, 40));
                scrPane.getHorizontalScrollBar().setPreferredSize(new Dimension(10, 10));
                for(Component component : scrPane.getVerticalScrollBar().getComponents()){
                    component.setBackground(Color.decode(guiOrder.notingAreaColor));
                    component.addMouseListener(new Movements());
                }
                for(Component component : scrPane.getHorizontalScrollBar().getComponents()){
                    component.setBackground(Color.decode(guiOrder.notingAreaColor));
                    component.addMouseListener(new Movements());
                }
                return;
            }
            case "javax.swing.JTable" :{
                JTable tblComponent = (JTable) comp;
                tblComponent.setBackground(Color.decode(guiOrder.backgroundColor));
                tblComponent.setForeground(Color.decode(guiOrder.buttonTextColor));
                tblComponent.setGridColor(Color.decode(guiOrder.componentBorderColor));
                return;
            }
            case "javax.swing.JViewport" :{
                JViewport view = (JViewport) comp;
                /*System.err.println("view ismi : " + view.getClass().getName());
                System.err.println("view ın iç sınıfı ismi : " + view.getView().getClass().getName());
                //if(view.getView() != null)*/
                if(view.getView() != null)
                    GUISeeming.appGUI(view.getView());
                return;
            }
            case "View.btnNote":{
                btnNote noteButton = (btnNote) comp;
                noteButton.setBackground(Color.decode(guiOrder.getButtonColor()));
                //RENK UYUŞMAZLIĞI OLMAMASI İÇİN NE YAPMALI?
                noteButton.setForeground(Color.decode(guiOrder.getButtonTextColor()));
                if(noteButton.getText().length() < 17)
                    comp.setFont(guiOrder.getFontUI());
                else
                    comp.setFont(guiOrder.getFontUI().deriveFont(Font.ITALIC | Font.BOLD, 12));
                return;
            }
            
            case "View.btnFolder":{
                btnFolder category= (btnFolder) comp;
                comp.setBackground(Color.decode(category.getStrColor()));
                comp.setForeground(GUISeeming.findHarmonicTextColor(category.getStrColor()));
                if(category.getText().length() < 17)
                    comp.setFont(guiOrder.getFontUI());
                else
                    comp.setFont(guiOrder.getFontUI().deriveFont(Font.ITALIC | Font.BOLD, 12));
                return;
            }
            case "javax.swing.JSpinner" :{
                JSpinner spinComp  =(JSpinner) comp;
                spinComp.setBackground(Color.decode(guiOrder.backgroundColor));
                spinComp.setForeground(Color.decode(guiOrder.textColor));
                spinComp.setBorder(GUISeeming.getComponentBorder(true, true, true, true, 1));
                for(int index = 0; index < spinComp.getComponentCount(); index++){
                    if(spinComp.getComponent(index).getClass().getName().equals("javax.swing.plaf.basic.BasicArrowButton")){
                        javax.swing.plaf.basic.BasicArrowButton btnChanger = (javax.swing.plaf.basic.BasicArrowButton) spinComp.getComponent(index);
                        btnChanger.setBackground(Color.decode(guiOrder.buttonColor));
                        btnChanger.setForeground(Color.decode(guiOrder.buttonTextColor) );
                        btnChanger.addMouseListener(new Movements());
                        btnChanger.setBorderPainted(false);
                        btnChanger.setBorder(GUISeeming.getComponentBorder(true, true, true, true, 1));
                        btnChanger.setBorderPainted(true);
                    }
                }
                if(spinComp.getEditor().getComponent(0).getClass().getName().equals("javax.swing.JFormattedTextField")){
                    JFormattedTextField txtEditor = (JFormattedTextField) spinComp.getEditor().getComponent(0);
                    txtEditor.setBackground(Color.decode(guiOrder.notingAreaColor));
                    txtEditor.setForeground(Color.decode(guiOrder.notingAreaTextColor));
                    txtEditor.setSelectionColor(Color.decode(guiOrder.notingAreaSelectedColor));
                    txtEditor.setSelectedTextColor(Color.decode(guiOrder.notingAreaSelectedTextColor));
                }
                return;
            }
            default:{
                comp.setBackground(Color.decode(guiOrder.getBackgroundColor()));
                comp.setFont(guiOrder.getFontUI());
            }
            /*case "JTable":{
                
            }*/
        }
    }
    public static void appGUI(Container comp){
//        System.out.println("\nContainer comp.getClass().getName: " + comp.getClass().getName());
        if(comp.getClass().getName().equals("View.ManagementPanel")){
            ManagementPanel pnlManagement = (ManagementPanel) comp;
            comp.setBackground(Color.decode(guiOrder.backgroundColor));
            comp.setForeground(Color.decode(guiOrder.textColor));
            appGUI((Container) pnlManagement.getPnlContent());
        }
        if(comp.getClass().getName().equals("View.TopMenuGround")){
            comp.setBackground(Color.decode(guiOrder.getTopMenuGroundBackgroundColor()));
            for(Component compOfClass : comp.getComponents()){
//                System.err.println("!!compOfClass.getClass().getName: " + compOfClass.getClass().getName());
                if(!compOfClass.getClass().getName().equals("View.pnlLocation"))
                    GUISeeming.appGUI((Component) compOfClass);
            }
            JComponent panel = (JComponent) comp;
            panel.setBorder(GUISeeming.getComponentBorder(false, false, true, false));
        }
        else if(comp.getClass().getName().equals("View.EditingPanel")){
            comp.setBackground(Color.decode(guiOrder.getBackgroundColor()));
            for(Component compOfClass : comp.getComponents()){
                if(compOfClass.getClass().getName().equals("View.EditTools"))
                    GUISeeming.appGUI((Container) compOfClass);
                else if(compOfClass.getClass().getName().equals("javax.swing.JScrollPane")){
                    GUISeeming.appGUI((Component) compOfClass);
                }
                else
                    GUISeeming.appGUI(compOfClass);
            }
        }
        else if(comp.getClass().getName().equals("View.EditTools")){
            comp.setBackground(Color.decode(guiOrder.getEditToolsBackgroundColor()));
            JPanel pnlEdit = (JPanel) comp;
            pnlEdit.setBorder(GUISeeming.getComponentBorder(false, false, true, false));
            for(Component compOfClass : comp.getComponents()){
                GUISeeming.appGUI(compOfClass);
            }
        }
        else if(comp.getClass().getName().equals("View.Menu")){
            comp.setBackground(Color.decode(guiOrder.menuColor));
            Menu menu = (Menu) comp;
            for(Component compOfClass : comp.getComponents()){
                GUISeeming.appGUI(compOfClass);
                /*if(compOfClass.getClass().getName().equals("javax.swing.JButton")){
                    JButton button = (JButton) compOfClass;
                    if(button.getText().length() > 14)
                        button.setFont(button.getFont().deriveFont(Font.BOLD | Font.ITALIC, 11));
                }*/
            }
        }
        else if(comp.getClass().getName().equals("View.Organizer")){
            comp.setBackground(Color.decode(guiOrder.menuColor));
            Organizer org = (Organizer) comp;
            /*for(Component compOfClass : comp.getComponents()){
                if(!(compOfClass.getClass().getName().equals("View.pnlCategory") || compOfClass == org.getPnlMain()")))
                    GUISeeming.appGUI((Container) compOfClass);
            }*/
            appGUI((Component) org.getScrpaneMain());
            org.getPnlMain().setBackground(Color.decode(guiOrder.menuColor));
        }
        else if(comp.getClass().getName().equals("View.ProduceNotePanel")){
            comp.setBackground(Color.decode(guiOrder.getBackgroundColor()));
            comp.setForeground(Color.decode(guiOrder.getTextColor()));
            ProduceNotePanel pane = (ProduceNotePanel) comp;
            JPanel panel = pane.PnlADD();
            for(Component compInPanel : panel.getComponents()){
                GUISeeming.appGUI((Component) compInPanel);
            }
        }
        else if(comp.getClass().getName().equals("View.ProduceCategoryPanel")){
            comp.setBackground(Color.decode(guiOrder.getBackgroundColor()));
            comp.setForeground(Color.decode(guiOrder.getTextColor()));
            ProduceCategoryPanel pane = (ProduceCategoryPanel) comp;
            JPanel panel = pane.getPnlBase();
            for(Component compInPanel : panel.getComponents()){
                GUISeeming.appGUI(compInPanel);
            }
        }
        else if(comp.getClass().getName().equals("View.ProduceNoteTag")){
            comp.setBackground(Color.decode(guiOrder.getBackgroundColor()));
            comp.setForeground(Color.decode(guiOrder.getTextColor()));
            ProduceNoteTag pane = (ProduceNoteTag) comp;
            JPanel panel = (JPanel) pane.getPnlScreen();
            /*panel.setBackground(Color.decode(guiOrder.backgroundColor));
            panel.setForeground(Color.decode(guiOrder.textColor));*/
            for(Component compInPanel : panel.getComponents()){
                GUISeeming.appGUI(compInPanel);
            }
        }
        else if(comp.getClass().getName().equals("View.PnlManageTags")){
            comp.setBackground(Color.decode(guiOrder.getBackgroundColor()));
            comp.setForeground(Color.decode(guiOrder.getTextColor()));
            PnlManageTags optionPane = (PnlManageTags) comp;
            for(Component compInPanel : optionPane.getPnlMain().getComponents()){
                GUISeeming.appGUI(compInPanel);
            }
        }
        else if(comp.getClass().getName().equals("View.MenuEditNote") ||
            comp.getClass().getName().equals("View.MenuEditCategory")){
            JMenu menu = (JMenu) comp;
            comp.setBackground(Color.decode(guiOrder.getTopMenuGroundBackgroundColor()));
            comp.setForeground(Color.decode(guiOrder.getReleasebleMenuTextColor()));
            menu.setFont(guiOrder.getFontUI());
            for(int index = 0; index < menu.getItemCount(); index++){
                GUISeeming.appGUI((Component) menu.getItem(index));
            }
        }
        else if(comp.getClass().getName().equals("View.pnlCategory")){
            pnlCategory panel = (pnlCategory) comp;
            panel.setBackground(Color.decode(panel.getBtnMain().getHexColor()));
            panel.setBorder(GUISeeming.getComponentBorder(true, true, true, true));
            for(int index = 0; index < panel.getComponentCount(); index++){
                if(!comp.getComponent(index).getClass().getName().equals("View.btnCategory"))
                    GUISeeming.appGUI((Component) comp.getComponent(index));
            }
        }
        else if(comp.getClass().getName().equals("View.btnCategory")){
            btnCategory btnCt = (btnCategory) comp;
            btnCt.setBackground(Color.decode(btnCt.getHexColor()));
            btnCt.setBorderPainted(false);
        //    btnCt.setBorder(GUISeeming.getComponentBorder(true, true, true, true, 3));
            for (int index = 0; index < btnCt.getComponentCount(); index++){
                GUISeeming.appGUI((Component) btnCt.getComponent(index));
                if(btnCt.getComponent(index).getClass().getName().equals("javax.swing.JButton"))
                    ((JButton) btnCt.getComponent(index)).setBorderPainted(false);
            }
            btnCt.getLblCategoryName().setForeground(GUISeeming.findHarmonicTextColor(btnCt.getHexColor()));
            btnCt.getLblNumberOfNotes().setForeground(GUISeeming.findHarmonicTextColor(btnCt.getHexColor()));
            btnCt.getLblNumberOfNotes().setBorder(GUISeeming.getComponentBorder(true, true, true, true, 1));
            if(btnCt.getLblCategoryName().getText().length() > 11)
                btnCt.getLblCategoryName().setFont(guiOrder.getFontUI().deriveFont(Font.ITALIC | Font.BOLD, 12));
            btnCt.getLblNumberOfNotes().setPreferredSize(new Dimension(17, 25));
            if(btnCt.getLblNumberOfNotes().getText().length() > 2)
                btnCt.getLblNumberOfNotes().setPreferredSize(new Dimension(25, 25));
                
        }
        else if(comp.getClass().getName().equals("View.ListPanel")){
            comp.setBackground(Color.decode(guiOrder.backgroundColor));
            comp.setForeground(Color.decode(guiOrder.textColor));
            ListPanel pnl = (ListPanel) comp;
            for(Component compOfClass : comp.getComponents()){
                appGUI(compOfClass);
            }
            appGUI((Component) pnl.getTxtNameForTable());
            appGUI((Component) pnl.getCmboxCategoriesForTable());
        }
        else{
//            System.err.println("comp.name(): " + comp.getClass().getName());
            comp.setBackground(Color.decode(guiOrder.backgroundColor));
            for(Component compOfClass : comp.getComponents()){
    //            System.out.println("compOfClass.getClass().getName(): " + compOfClass.getClass().getName() + "\n");
                GUISeeming.appGUI(compOfClass);
            }
        }
    }
    public static Color[] getColorsForRenderer(){
        Color[] clrColors = new Color[4];
        clrColors[0] = Color.decode(guiOrder.buttonColor);
        clrColors[1] = Color.decode(guiOrder.buttonTextColor);
        clrColors[2] = Color.decode(guiOrder.buttonOnMouseColor);
        clrColors[3] = Color.decode(guiOrder.buttonTextOnMouseColor);
        return clrColors;
    }
    public static String[] getColorsForContactPanel(){
        String[] strColors = new String[6];
        strColors[0] = guiOrder.backgroundColor;
        System.out.println("NOKTA : " + guiOrder.guiSeemingName);
        switch(guiOrder.guiSeemingName){
            case "Standard" :{
                strColors[1] = "#e1625e";//Warning
                strColors[2] = "#44d794";//Succesful
                strColors[3] = "#e1c5bd";//LittlePoint
                strColors[4] = "#778edd";//Info
                strColors[5] = "#fac35b";//Advice
                break;
            }
            case "Pink" :{
                strColors[1] = "#f25701";//Warning
                strColors[2] = "#8fb701";//Succesful
                strColors[3] = "#ffa38d";//LittlePoint
                strColors[4] = "#76a3ee";//Info
                strColors[5] = "#ebc172";//Advice
                break;
            }
            case "Blue" :{
                strColors[1] = "#eb4b72";//Warning
                strColors[2] = "#5ef59c";//Succesful
                strColors[3] = "#e18c8f";//LittlePoint
                strColors[4] = "#9d98fa";//Info
                strColors[5] = "#f89885";//Advice
                break;
            }
            case "Dark" :{
                strColors[1] = "#53060a";//Warning
                strColors[2] = "#041800";//Succesful
                strColors[3] = "#320b1d";//LittlePoint
                strColors[4] = "#070657";//Info
                strColors[5] = "#5e2213";//Advice
                break;
            }
        }
        return strColors;
    }
    public static void updateGUISeeming(){
        if(guiOrder != SystemSettings.getSettings().getCurrentGuiSeeming()){
            guiOrder = SystemSettings.getSettings().getCurrentGuiSeeming();
            if(InterfaceController.getGuiManaging().getMP() == null)
                return;
            for(Container container : InterfaceController.getGuiManaging().getAllPages()){
                if(container.getClass().getName().equals("View.ListPanel")){
                    InterfaceController.getGuiManaging().getMP().getListPanel().updateListPanel();
                }
                if(container != null){
                    appGUI(container);
                }
            }
            ContactPanel.getContactPanel().updateSeeming();
            appGUI((Container) TopMenuGround.getTopMenu());
            appGUI((Container) Organizer.getOrgMenu());
            appGUI((Container) Menu.getMenu());
            appGUI(InterfaceController.getGuiManaging().getMP());
            appGUI((Container) InterfaceController.getGuiManaging().getCurrentComp());
            for(int index = 0; index < Organizer.getOrgMenu().getPnlCategories().size(); index++){
                for(pnlCategory temp: Organizer.getOrgMenu().getPnlCategories()){
                    temp.setBorder(GUISeeming.getComponentBorder(true, true, true, true));
                    appGUI((Container) Organizer.getOrgMenu().getPnlCategories().get(index));
                    appGUI((Container) Organizer.getOrgMenu().getPnlCategories().get(index).getBtnMain());
                }
            }
            ListCellRenderStandard.getConfiguredRenderer().updateColors();
            ListCellRender.updateColors();
            refreshGUI();
        }
    }
    public static void refreshGUI(){
        MainFrame.getFrame_Main().repaint();
        MainFrame.getFrame_Main().setVisible(true);
    } 
    public static void mouseOnMovementRefresh(Component comp){
        switch(comp.getClass().getName()){
            case "javax.swing.JButton" :{
                comp.setBackground(Color.decode(guiOrder.getButtonOnMouseColor()));
                comp.setForeground(Color.decode(guiOrder.getButtonTextOnMouseColor()));
                return;
            }
            case "javax.swing.JComboBox" :{
                JComboBox cmbox = (JComboBox) comp;
                cmbox.setBackground(Color.decode(guiOrder.getButtonOnMouseColor()));
                cmbox.setForeground(Color.decode(guiOrder.getButtonTextOnMouseColor()));
                return;
            }
            case "javax.swing.JMenuItem" : {
                comp.setBackground(Color.decode(guiOrder.getReleasebleMenuItemOnMouseColor()));
                comp.setForeground(Color.decode(guiOrder.getReleasebleMenuItemOnMouseTextColor()));
                return;
            }
            case "View.btnCategory" :{
                comp.setBackground(Color.decode(guiOrder.getButtonOnMouseColor()));
                comp.setForeground(Color.decode(guiOrder.getButtonTextOnMouseColor()));
                return;
            }
            case "javax.swing.plaf.metal.MetalScrollButton" :{
                comp.setBackground(Color.decode(guiOrder.buttonOnMouseColor));
                comp.setForeground(Color.decode(guiOrder.buttonTextOnMouseColor));
                return;
            }
            default :{
                comp.setBackground(Color.decode(guiOrder.getButtonOnMouseColor()));
                comp.setForeground(Color.decode(guiOrder.getButtonTextOnMouseColor()));
                return;
            }
        }
    }
    public static void mouseOffMovementRefresh(Component comp){
        switch(comp.getClass().getName()){
            case "javax.swing.JButton" :{
                comp.setBackground(Color.decode(guiOrder.getButtonColor()));
                comp.setForeground(Color.decode(guiOrder.getButtonTextColor()));
                return;
            }
            case "javax.swing.JComboBox" :{
                JComboBox cmbox = (JComboBox) comp;
                cmbox.setBackground(Color.decode(guiOrder.buttonColor));
                cmbox.setForeground(Color.decode(guiOrder.buttonTextColor));
                return;
            }
            case "javax.swing.JMenuItem" :{
                comp.setBackground(Color.decode(guiOrder.getReleasebleMenuItemColor()));
                comp.setForeground(Color.decode(guiOrder.getReleasebleMenuItemTextColor()));
                return;
            }
            case "javax.swing.JScrollBar" :{
                comp.setBackground(Color.decode(guiOrder.getReleasebleMenuItemColor()));
                comp.setForeground(Color.decode(guiOrder.getReleasebleMenuItemTextColor()));
                return;
            }
            case "View.btnCategory" :{
                btnCategory btnCt = (btnCategory) comp;
                btnCt.setBackground(Color.decode(btnCt.getHexColor()));
                btnCt.setForeground(Color.decode(guiOrder.getButtonTextColor()));
                return;
            }
            case "javax.swing.plaf.metal.MetalScrollButton" :{
                comp.setBackground(Color.decode(guiOrder.notingAreaColor));
                comp.setForeground(Color.decode(guiOrder.notingAreaTextColor));
                return;
            }
            default :{
                comp.setBackground(Color.decode(guiOrder.getButtonColor()));
                comp.setForeground(Color.decode(guiOrder.getButtonTextColor()));
                return;
            }
        }
    }
    public static void mouseClickedRefresh(Component comp){
        comp.setBackground(comp.getBackground().darker());
    }
    
    public static void mouseUnClickedRefresh(Component comp){
        comp.setBackground(comp.getBackground().brighter());
    }
    
    public static Border getComponentBorder(boolean isTop, boolean isLeft, boolean isBottom, boolean isRight, int size){
        int top = 0, right = 0, bottom = 0, left = 0;
        if(isTop)
            top = size;
        if(isRight)
            right = size;
        if(isBottom)
            bottom = size;
        if(isLeft)
            left = size;
        return BorderFactory.createMatteBorder(top, right, bottom, left, Color.decode(guiOrder.componentBorderColor));
    }
    public static Border getComponentBorder(boolean isTop, boolean isLeft, boolean isBottom, boolean isRight){
        return getComponentBorder(isTop, isRight, isBottom, isLeft, 2);
    }
    public static Color findHarmonicTextColor(String hexCode){
        int[] values = new int[3];
        int[] mainValues = new int[3];
        String checkedHexCode = hexCode.trim().toLowerCase();
        if(Color.decode(hexCode).equals(Color.white))
            return Color.black;
        else if(Color.decode(hexCode).equals(Color.black))
            return Color.white;
        mainValues = values;
        values[0] = GUISeeming.hexToDecimal(hexCode.substring(1, 3));
        values[1] = GUISeeming.hexToDecimal(hexCode.substring(3, 5));
        values[2] = GUISeeming.hexToDecimal(hexCode.substring(5, 7));
        //Aradaki uzaklığı hesaplayıp, ona göre işlem yapmak sisteme çok yük getirebilir.
        /*distanceRedGreen;
        distanceRedBlue;
        distanceGreenBlue;*/
        /*O yüzden fazlalığa göre ekleme yöntemi uygulayalım; şöyle ki; 
        gönderilen renkte değeri büyük olan katmanın yenisinde de değeri 
        biraz büyük olsun; yanî tam ters rengi olmasın; böylece arkaplan 
        rengine biraz daha yaklaşmış oluruz bi iznillâh; yine böylece yazı, 
        tuşun üzerinde göze batmaz bi iznillâh.*/
        //YÖNTEM - 1:
        /*for(int index = 0; index < 3; index++){
    //        System.err.println("values[" + index + "] = " + values[index]);
            values[index] = 255 - values[index];
            if(values[index] > 122 && values[index] < 160)
                values[index] = values[index] + 15;
            else if(values[index] > 169 && values[index] < 210)
                values[index] = values[index] + 25;
            else if(values[index] > 90 && values[index] < 122)
                values[index] = values[index] - 15 ;
            else if(values[index] > 50 && values[index] < 90)
                values[index] -= 25;
            else if(values[index] < 50)
                values[index] = values[index] - 40;
            else if(values[index] > 200)
                values[index] += 40;
            if(values[index] > 255)
                values[index] = 255;
            if(values[index] < 0)
                values[index] = 0;
    //        System.out.println("yeniValues[" + index + "] = " + values[index]);
        }*/
        //YÖNTEM - 2:
        /*
        for(int index = 0; index < 3; index++){
            if(values[index] < 50)
                values[index] = 255 - values[index];
            else if(values[index] > 200)
                values[index] = 255 - values[index];
            else if(values[index] > 50  && values[index] < 80)
                values[index] = 255 - values[index] - 30;
            else if(values[index] > 160 && values[index] < 200)
                values[index] = 255 - values[index] + 30;
            else{
                if(values[index] < 122)
                    values[index] -= (values[index] / 2);
                else
                    values[index] += (values[index] / 2);
            }
            if(values[index] < 0)
                values[index] = 0;
            else if(values[index] > 255)
                values[index] = 255;
        }
        */
        
        //YÖNTEM - 3:
        int totalValue = 0;
        for(int index2 = 0; index2 < 3; index2++){
            totalValue += values[index2];
        }
        if(totalValue > 470){
            for(int index = 0; index < 3; index++){
                if(values[index] != 0)
                    values[index] += (6 * (values[index] / 7));
            }
        }
        else{
            for(int index = 0; index < 3; index++){
                if(values[index] != 0)
                    values[index] -= (6 * (values[index] / 7));
            } 
        }
        int totalValue2 = 0;
        for(int index = 0; index < 3; index++){
            totalValue2 += values[index];
        }
        if(Math.abs(totalValue2 - totalValue) < 250){
            for(int index = 0; index < 3; index++){
                values[index] = 255 - values[index];
            }
        }
        for(int index = 0; index < 3; index++){
            if(values[index] > 255)
                values[index] = 255;
            else if(values[index] < 0)
                values[index] = 0;
        }
    /*    System.err.println("hexCode : " + hexCode);
        System.out.println("new color value : "  + values[0] + values[1] + values[2]);*/
        return new Color(values[0], values[1], values[2]);
    }
    @Override
    public int getID(){
        return 0;//return this.ID olmalı;
    }
    public static void setCurrentGuiSeeming(GUISeeming seeming, String code){
        guiOrder = seeming;
        /*if(CryptingService.getMd5(code).equals(IDARE.getMd5Code())){
            guiOrder = seeming;
            System.out.println("Görsell görünüm değiştirildi");
        }*/
    }
    //ARKAPLAN İŞLEM YÖNTEMLERİ:
    private static int hexToDecimal(String hexString){
        String hexNumber = hexString.trim().toUpperCase();//Hangisinin asci kodu düşükse ona çevir; misal küçük harflerin ascii kodu düşükse küçük harfler üzerinden işlem yap
        int value = 0, currentValue = 0;
        if(hexNumber.charAt(0) == '+' || hexNumber.charAt(0) == '-' || hexNumber.charAt(0) == '#')
            hexNumber = hexString.substring(1);
        for(int index = 0; index < hexNumber.length(); index++){
            switch(hexNumber.charAt(index)){
                case 'A' :{
                    currentValue = 10;
                    break;
                }
                case 'B' :{
                    currentValue = 11;
                    break;
                }
                case 'C' :{
                    currentValue = 12;
                    break;
                }
                case 'D' :{
                    currentValue = 13;
                    break;
                }
                case 'E' :{
                    currentValue = 14;
                    break;
                }
                case 'F' :{
                    currentValue = 15;
                    break;
                }
                case '9' :{
                    currentValue = 9;
                    break;
                }
                case '8' :{
                    currentValue = 8;
                    break;
                }
                case '7' :{
                    currentValue = 7;
                    break;
                }
                case '6' :{
                    currentValue = 6;
                    break;
                }
                case '5' :{
                    currentValue = 5;
                    break;
                }
                case '4' :{
                    currentValue = 4;
                    break;
                }
                case '3' :{
                    currentValue = 3;
                    break;
                }
                case '2' :{
                    currentValue = 2;
                    break;
                }
                case '1' :{
                    currentValue = 1;
                    break;
                }
                case '0' :{
                    currentValue = 0;
                    break;
                }
            }
            if(index == hexNumber.length() - 1)
                value += currentValue;
            else
                value += currentValue * (16 * (hexNumber.length() - index - 1));
        }
        return value;
    }

//ERİŞİM YÖNTEMLERİ:
    //ANA ERİŞİM YÖNTEMİ:
    public static GUISeeming getCurrentGUISeeming(){
        if(guiOrder == null){
            guiOrder = GUISeeming.GUIOrderProducer("Standard");
        }
        return guiOrder;
    }
    public String getGuiSeemingName(){
        return guiSeemingName;
    }
    public void setGuiSeemingName(String guiSeemingName){
        this.guiSeemingName = guiSeemingName;
    }
    public String getBackgroundColor(){
        return backgroundColor;
    }
    public void setBackgroundColor(String backgroundColor){
        this.backgroundColor = backgroundColor;
    }
    public String getButtonColor(){
        return buttonColor;
    }
    public void setButtonColor(String buttonColor){
        this.buttonColor = buttonColor;
    }
    public String getButtonTextColor(){
        return buttonTextColor;
    }
    public void setButtonTextColor(String buttonTextColor){
        this.buttonTextColor = buttonTextColor;
    }
    public String getButtonOnMouseColor(){
        return buttonOnMouseColor;
    }
    public void setButtonOnMouseColor(String buttonOnMouseColor){
        this.buttonOnMouseColor = buttonOnMouseColor;
    }
    public String getButtonTextOnMouseColor(){
        return buttonTextOnMouseColor;
    }
    public void setButtonTextOnMouseColor(String buttonTextOnMouseColor){
        this.buttonTextOnMouseColor = buttonTextOnMouseColor;
    }
    public String getTextColor(){
        return textColor;
    }
    public void setTextColor(String textColor){
        this.textColor = textColor;
    }
    public String getMenuColor(){
        return menuColor;
    }
    public void setMenuColor(String menuColor){
        this.menuColor = menuColor;
    }
    public String getNotingAreaColor(){
        return notingAreaColor;
    }
    public void setNotingAreaColor(String notingAreaColor){
        this.notingAreaColor = notingAreaColor;
    }
    public String getNotingAreaTextColor(){
        return notingAreaTextColor;
    }
    public void setNotingAreaTextColor(String notingAreaTextColor){
        this.notingAreaTextColor = notingAreaTextColor;
    }
    public String getNotingAreaCursorColor(){
        return notingAreaCursorColor;
    }
    public void setNotingAreaCursorColor(String notingAreaCursorColor){
        this.notingAreaCursorColor = notingAreaCursorColor;
    }
    public String getNotingAreaSelectedColor(){
        return notingAreaSelectedColor;
    }
    public void setNotingAreaSelectedColor(String notingAreaSelectedColor){
        this.notingAreaSelectedColor = notingAreaSelectedColor;
    }
    public String getNotingAreaSelectedTextColor(){
        return notingAreaSelectedTextColor;
    }
    public void setNotingAreaSelectedTextColor(String notingAreaSelectedTextColor){
        this.notingAreaSelectedTextColor = notingAreaSelectedTextColor;
    }
    public String getComponentBorderColor(){
        return componentBorderColor;
    }
    public void setComponentBorderColor(String componentBorderColor){
        this.componentBorderColor = componentBorderColor;
    }
    public String getTopMenuGroundBackgroundColor(){
        return topMenuGroundBackgroundColor;
    }
    public void setTopMenuGroundBackgroundColor(String topMenuGroundBackgroundColor){
        this.topMenuGroundBackgroundColor = topMenuGroundBackgroundColor;
    }
    public String getReleasebleMenuTextColor(){
        return releasebleMenuTextColor;
    }
    public void setReleasebleMenuTextColor(String releasebleMenuTextColor){
        this.releasebleMenuTextColor = releasebleMenuTextColor;
    }
    public String getReleasebleMenuItemColor(){
        return releasebleMenuItemColor;
    }
    public void setReleasebleMenuItemColor(String releasebleMenuItemColor){
        this.releasebleMenuItemColor = releasebleMenuItemColor;
    }
    public String getReleasebleMenuItemTextColor(){
        return releasebleMenuItemTextColor;
    }
    public void setReleasebleMenuItemTextColor(String releasebleMenuItemTextColor){
        this.releasebleMenuItemTextColor = releasebleMenuItemTextColor;
    }
    public String getReleasebleMenuItemOnMouseColor(){
        return releasebleMenuItemOnMouseColor;
    }
    public void setReleasebleMenuItemOnMouseColor(String releasebleMenuItemOnMouseColor){
        this.releasebleMenuItemOnMouseColor = releasebleMenuItemOnMouseColor;
    }
    public String getReleasebleMenuItemOnMouseTextColor(){
        return releasebleMenuItemOnMouseTextColor;
    }
    public void setReleasebleMenuItemOnMouseTextColor(String releasebleMenuItemOnMouseTextColor){
        this.releasebleMenuItemOnMouseTextColor = releasebleMenuItemOnMouseTextColor;
    }
    public String getEditToolsBackgroundColor(){
        return editToolsBackgroundColor;
    }
    public void setEditToolsBackgroundColor(String editToolsBackgroundColor){
        this.editToolsBackgroundColor = editToolsBackgroundColor;
    }
    /*public static OptionPaneUI getOptionPaneUI(){
        //.;.
    }*/
    public Font getFontUI(){
        if(fontUI == null){
            fontUI = new Font("Dialog", Font.ITALIC|Font.BOLD, 14);
        }
        return fontUI;
    }
    public void setFontUI(Font fontUI){
        this.fontUI = fontUI;
    }
}