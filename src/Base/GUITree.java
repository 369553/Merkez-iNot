package Base;

import java.util.ArrayList;
import java.util.HashMap;

public class GUITree <T> {
    ArrayList <T> baseComponents;
    HashMap <T, Integer> mapBaseCompNumber = null;
    ArrayList<Integer> usableNumbers = null;
    
    public GUITree(T component) {
        
    }
//İŞLEM YÖNTEMLERİ:
    public void addBaseComponent(T component){
        int value = getNewNumber();
        getMapBaseCompNumber().put(component, value);
        getBaseComponents().add(component);
    }
    
    public void deleteBaseComponent(T component){
        getBaseComponents().remove(component);
        int value = getMapBaseCompNumber().get(component);
        getMapBaseCompNumber().remove(component);
        if(value != (getBaseComponents().size() - 1))
            getUsableNumbers().add(value);
    }
    
    public void addNode(String nameOfBaseComponent, T component){
        
    }
    
    public void deleteNode(String nameOfBaseComponent, T component){
        
    }
    
//ERİŞİM YÖNTEMLERİ:
    private ArrayList<T> getBaseComponents(){
        if(baseComponents == null){
            baseComponents = new ArrayList<T>();
        }
        return baseComponents;
    }

    private HashMap<T, Integer> getMapBaseCompNumber() {
        if(mapBaseCompNumber == null){
            mapBaseCompNumber = new HashMap<T, Integer>();
        }
        return mapBaseCompNumber;
    }

    public ArrayList<Integer> getUsableNumbers() {
        if(usableNumbers == null){
            usableNumbers = new ArrayList<Integer>();
        }
        return usableNumbers;
    }
    
    
    
//ARKAPLAN İŞLEM YÖNTEMLERİ:
    private int getNewNumber(){
        int value;
        if(getUsableNumbers().size() == 0){
            value = getBaseComponents().size();
            return value;
        }
        else{
            value = getUsableNumbers().get(0);
            getUsableNumbers().remove(value);
            return value;
        }
    }
    
    
}
