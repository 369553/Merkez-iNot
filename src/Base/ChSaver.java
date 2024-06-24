package Base;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextArea;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.BadLocationException;

public class ChSaver implements KeyListener{
    JTextArea txt;
    private String content;
    private String selectedText = "";
    String distance = "";
    ArrayList<CheckPoint> liCheckpoints;
    int order = 0;
    boolean changed = false, isSelected = false;
    int oldLength = 0, length = 0;
    ArrayList<Integer> liAcceptingKeyCodesForCheckPoint;

    public ChSaver(JTextArea txt){
        this.txt = txt;
        txt.addKeyListener(this);
        content = txt.getText();
    }

//İŞLEM YÖNTEMLERİ:
    @Override
    public void keyTyped(KeyEvent e){
        //.;.
    }
    @Override
    public void keyPressed(KeyEvent e){
    }
    @Override
    public void keyReleased(KeyEvent e){
        length = txt.getText().length();
        if(length != oldLength)
            changed = true;
        else
            changed = false;
        oldLength = length;
        if(txt.getSelectedText() != null){
            int startPoint = txt.getCaret().getMark();
            int endPoint = txt.getCaret().getDot();
            if(startPoint > endPoint){
                int transferVariable = startPoint;
                startPoint = endPoint;
                endPoint = transferVariable;
            }
            try{
                selectedText = txt.getText(startPoint, endPoint - startPoint);
            }
            catch(BadLocationException ex){
            //.;.
            }
            if(!selectedText.isEmpty())
                isSelected = true;
             distance = startPoint + "-" + endPoint;
        }
            if(changed){
                int location = txt.getCaretPosition();
                int endPoint = location - 1;
                int startPoint = getOldEndPoint();
                if(isSelected){
                    //.;. İki adımı birleştir : silme ve yeni harf ekleme; ama sıkıntı şu : yeni harfe tıklandığında yeni harf yazılmadan evvel bu fonksiyona geliniyor; yanî bu fonksiyona iki kez geliniyor
                }
                if(detectedCondition(e.getKeyCode())){
                    addCheckPoint(new CheckPoint("add", startPoint + "-" + endPoint, true, content));
                }
            }
            else{/*Diğer durumlar: silme tuşu, ...*/
//                addCheckPoint(new CheckPoint("delete", distance, false, ""));
            }
        if(e.getKeyCode() == 32){
//            addCheckPoint(new CheckPoint("add", startPoint + "-" + endPoint, true, content));
        }
        else if(e.getKeyCode() == 8){
            //.;.
        }
        //.;.
    }
    public String nextCheckpoint(){
        if(!checkNextPoint())
            return null;
        order++;
        return applier(getLiCheckpoints().get(order));
    }
    public String lastCheckpoint(){
        if(!checkLastPoint())
            return null;
        order--;
        return applier(converter(getLiCheckpoints().get(order)));
    }
    public boolean checkNextPoint(){
        if(order == getLiCheckpoints().size() - 1)
            return false;
        return true;
    }
    public boolean checkLastPoint(){
        if(order > 0)
            return true;
        return false;
    }
    //ARKAPLAN İŞLEM YÖNTEMLERİ:
    private void addCheckPoint(CheckPoint ch){
        order++;
        getLiCheckpoints().set(order, ch);
        if(order < getLiCheckpoints().size() - 1){
            for(int index = order + 1; order < getLiCheckpoints().size(); index++){//Yeni adım eklendiğinde sonraki adımlar silinir; son adım, yeni eklenen olur bi iznillâh
                liCheckpoints.remove(index);
            }
        }
    }
    private CheckPoint converter(CheckPoint point){
        //.;.
        return null;
    }
    private String applier(CheckPoint point){
        StringBuilder strbuilder = new StringBuilder(content);
        String[] startToEnd = point.startToEnd.split("-");
        int startPoint = Integer.parseInt(startToEnd[0]);
        int endPoint = -1;
        if(startToEnd.length == 2)
            endPoint = Integer.parseInt(startToEnd[1]);
        if(point.funcName.equals("add")){
            add(strbuilder, point.text, startPoint, endPoint, point.isEffectLater);
        }
        else if(point.funcName.equals("delete")){
            delete(strbuilder, startPoint, endPoint, point.isEffectLater);
        }
        else if(point.funcName.equals("replace")){
            replace(strbuilder, point.text, startPoint, endPoint);
        }
        return strbuilder.toString();
    }
    private void add(StringBuilder targetText, String text, int startPosition, int endPosition, boolean isEffectLater){
        targetText.replace(startPosition, endPosition, text);
        if(!isEffectLater)
            targetText.delete(endPosition, targetText.length() - 1);
    }
    private void delete(StringBuilder targetText, int startPosition, int endPosition, boolean isEffectLater){
        if(endPosition == -1 || isEffectLater)
            endPosition = targetText.length();
        targetText.delete(startPosition, endPosition);
    }
    private void replace(StringBuilder targetText, String text, int startPosition, int endPosition){
        if(endPosition == -1)
            endPosition = startPosition + text.length();
        if(startPosition > endPosition)
            return;
        targetText.replace(startPosition, endPosition, text);
    }
    private int getOldEndPoint(){
        if(getLiCheckpoints().isEmpty())
            return 0;
        String[] startToEnd = getLiCheckpoints().get(order).startToEnd.split("-");
        int value;
        if(startToEnd.length == 2)
            value = Integer.parseInt(startToEnd[1]);
        else
            value = Integer.parseInt(startToEnd[0]);
        return value;
    }
    private boolean detectedCondition(int keyCode){
        for(int index : getLiAcceptingKeyCodesForCheckPoint()){
            if(keyCode == index)
                return true;
        }
        return false;
    }

//ERİŞİM YÖNTEMLERİ:
    private ArrayList<CheckPoint> getLiCheckpoints(){
        if(liCheckpoints == null){  
            liCheckpoints = new ArrayList<CheckPoint>();
        }
        return liCheckpoints;
    }
    private ArrayList<Integer> getLiAcceptingKeyCodesForCheckPoint(){
        if(liAcceptingKeyCodesForCheckPoint == null){  
            liAcceptingKeyCodesForCheckPoint = new ArrayList<Integer>();
            liAcceptingKeyCodesForCheckPoint.add(8);
            liAcceptingKeyCodesForCheckPoint.add(32);
            liAcceptingKeyCodesForCheckPoint.add(9);
            liAcceptingKeyCodesForCheckPoint.add(22);
            liAcceptingKeyCodesForCheckPoint.add(24);
        }
        return liAcceptingKeyCodesForCheckPoint;
    }

//ALT SINIF:
    private class CheckPoint{
        String funcName;
        String startToEnd;
        boolean isEffectLater;
        String text;

        public CheckPoint(){
            //.;.
        }
        public CheckPoint(String funcName, String startToEnd, boolean isEffectLater, String text){
            this.funcName = funcName;
            this.startToEnd = startToEnd;
            this.isEffectLater = isEffectLater;
            this.text = text;
        }

    //İŞLEM YÖNTEMLERİ:
        

    //ERİŞİM YÖNTEMLERİ:
        
    }
}