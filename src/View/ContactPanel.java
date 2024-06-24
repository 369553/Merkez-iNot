package View;

import Base.GUISeeming;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class ContactPanel extends JPanel{
    private static ContactPanel contactPanel;
    private JLabel lblMessage;
    FlowLayout layoutForThis;
    JScrollPane scrpane;
    String messageType;
    Font messageFont;
    String textColor = null;
    HashMap<String, String> messageTypeAndColor;
    private MessageDisposing thDispose;
    
    public ContactPanel(){
        setLayout(getLayoutForThis());
        this.add(getLblMessage());
        setMessageType("Standard");
        setVisible(true);
    }

//İŞLEM YÖNTEMLERİ:
    public void changeSize(int width, int height){
        getContactPanel().setPreferredSize(new Dimension(width, height));
    }
    public void showMessage(String message){
        appGUI();
        getLblMessage().setText(message);
        setVisible(true);
        thDispose = new MessageDisposing();
    }
    public void showMessage(String message, String messageType){
        setMessageType(messageType);
        showMessage(message);
    }
    public void clearPanel(){
        getLblMessage().setText("");
        setMessageType("Standard");
        appGUI();
        setVisible(true);
    }
    public void updateSeeming(){
        System.out.println("poegwkowepgkerpgkeroşgmkerokgerpkgergre");
        String[] strColors = GUISeeming.getColorsForContactPanel();
            getMessageTypeAndColor().put("Standard", strColors[0]);
            getMessageTypeAndColor().put("Warning", strColors[1]);
            getMessageTypeAndColor().put("Successful", strColors[2]);
            getMessageTypeAndColor().put("LittlePoint", strColors[3]);
            getMessageTypeAndColor().put("Info", strColors[4]);
            getMessageTypeAndColor().put("Advice", strColors[5]);
        if(GUISeeming.getCurrentGUISeeming().getGuiSeemingName().equals("Dark"))
            textColor = "#727080";
        else
            textColor = "#0e2613";
        clearPanel();
     }
    public void setLblMessage(JLabel lblMessage){
        this.lblMessage = lblMessage;
    }
    public void setLayoutForThis(FlowLayout layoutForThis){
        this.layoutForThis = layoutForThis;
    }
    public void setMessageType(String messageType){
        this.messageType = messageType;
    }
    public void setMessageTypeAndColor(HashMap<String, String> messageTypeAndColor){
        this.messageTypeAndColor = messageTypeAndColor;
    }
    public void setMessageFont(Font messageFont){
        this.messageFont = messageFont;
    }
    public void cutShowingMessage(){
        if(getThDispose() != null)
            getThDispose().interrupt();
        clearPanel();
    }
    //ARKAPLAN İŞLEM YÖNTEMLERİ:
    private void appGUI(){
        this.setBackground(Color.decode(getMessageColor()));
        getLblMessage().setForeground(Color.decode(getTextColor()));
    }

//ERİŞİM YÖNTEMLERİ:
    //ANA ERİŞİM YÖNTEMİ:
    public static ContactPanel getContactPanel(){
        if(contactPanel == null){
            contactPanel = new ContactPanel();
        }
        return contactPanel;
    }
    public String getMessageColor(){
        if(getMessageTypeAndColor().get(getMessageType()) != null)
           return getMessageTypeAndColor().get(getMessageType());
        return GUISeeming.getCurrentGUISeeming().getBackgroundColor();
    }
    public String getTextColor(){
        if(textColor == null){
            if(GUISeeming.getCurrentGUISeeming().getGuiSeemingName().equals("Dark"))
                textColor = "#727080";
            else
                textColor = "#0e2613";
        }
        return textColor;
    }
    public JLabel getLblMessage(){
        if(lblMessage == null){
            lblMessage = new JLabel();
            lblMessage.setFont(getMessageFont());
            lblMessage.setVisible(true);
        }
        return lblMessage;
    }
    public MessageDisposing getThDispose(){
        if(thDispose != null)
            if(thDispose.isAlive())
                return thDispose;
        return null;
    }
    public FlowLayout getLayoutForThis(){
        if(layoutForThis == null){
            layoutForThis = new FlowLayout(FlowLayout.CENTER, 2, 2);
        }
        return layoutForThis;
    }
    public String getMessageType(){
        if(messageType == null){
            messageType = "Info";
        }
        return messageType;
    }
    public HashMap<String, String> getMessageTypeAndColor(){
        if(messageTypeAndColor == null){
            messageTypeAndColor = new HashMap<>();
            String[] strColors = GUISeeming.getColorsForContactPanel();
            messageTypeAndColor.put("Standard", strColors[0]);
            messageTypeAndColor.put("Warning", strColors[1]);
            messageTypeAndColor.put("Successful", strColors[2]);
            messageTypeAndColor.put("LittlePoint", strColors[3]);
            messageTypeAndColor.put("Info", strColors[4]);
            messageTypeAndColor.put("Advice", strColors[5]);
        }
        return messageTypeAndColor;
    }
    public Font getMessageFont(){
        if(messageFont == null){
            messageFont = new Font("Times New Roman", Font.BOLD, 14);
        }
        return messageFont;
    }

//İÇ SINIF:
    private class MessageDisposing extends Thread{
        public MessageDisposing(){
            start();
        }

    //İÇ SINIF İŞLEM YÖNTEMLERİ:
        @Override
        public void run(){
            try{
                Thread.sleep(4444);
            }
            catch(InterruptedException e){
                System.out.println("İletişim paneliyle ilgili işlem kesintisi oldu");
            }
            ContactPanel.getContactPanel().clearPanel();
        }
    }
}