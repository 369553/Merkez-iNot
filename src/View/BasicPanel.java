package View;

import Base.GUISeeming;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class BasicPanel {
    
    public static JPanel prepareBasicPanel(String componentTypeName, Object[] values){
        JPanel pnlValue = null;
        pnlValue.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        prepareBasicPanel(componentTypeName);
        if(values != null){
        switch(componentTypeName){
            case "JTextField" :{
                JTextField txtComponent = new JTextField((String) values[0]);
                pnlValue.add(txtComponent);
            }
            case "JComboBox" :{
                JComboBox cmboxComponent = new JComboBox(values);
                pnlValue.add(cmboxComponent);
            }
            case "JList" :{
                JList liComponent = new JList(values);
                JScrollPane scrpaneComponent = new JScrollPane(liComponent,
                    JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
                pnlValue.add(scrpaneComponent);
            }
            case "JTextArea" :{
                JTextArea txtareaComponent = new JTextArea((String) values[0]);
                pnlValue.add(txtareaComponent);
            }
            case "JButton" :{
                JButton btnComponent = new JButton((String) values[0]);
                pnlValue.add(btnComponent);
            }
        }
        GUISeeming.appGUI(pnlValue);
        }
        return pnlValue;
    }
    
    public static JPanel prepareBasicPanel(String componentTypeName){
        return prepareBasicPanel(componentTypeName, new Object[]{new String("")});
    }
    
    public static JPanel prepareBasicPanel(String componentTypeName, Object value){
        return prepareBasicPanel(componentTypeName, new Object[]{value});
    }
    
    
    
    /*public static JPanel prepareBasicPanel(String componentTypeName, Object[] values, ActionListener listener){
        JPanel pnlValue = null;
        
        return pnlValue;
    }*/
}
