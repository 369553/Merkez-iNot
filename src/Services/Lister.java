
package Services;

import java.util.ArrayList;
//import java.util.HashMap;

public class Lister {
    public static Lister lister;
//    private HashMap <Integer, Integer> orderOfAphabet;

    private Lister() {
        
    }

//İŞLEM YÖNTEMLERİ:
    public String[] listStringByAlphabeticalOrder(String[] texts){
        return texts;
        /*String[] values = new String[texts.length];
        if(texts == null)
            return null;
        for(int index = 0; index < texts.length; index++){
            
        }
        return values;*/
    }
    public String[] listStringByAlphabeticalOrder(ArrayList<String> texts){
        String[] value = new String[texts.size()];
        return listStringByAlphabeticalOrder(texts.toArray(value));
    }
    /*private String alphabeticComparison(String text1, String text2){
        int maxCycle = text1.length();
        if(text2.length() > maxCycle)
            maxCycle = text2.length();
        for(int index = 0; index < maxCycle; index++){
            
        }
    }*/

//ERİŞİM YÖNTEMLERİ:
    //ANA ERİŞİM YÖNTEMİ:
    public static Lister getLister() {
        if(lister == null){
            lister = new Lister();
        }
        return lister;
    }
    /*public HashMap<Integer, Integer> getOrderOfAphabet() {
        if(orderOfAphabet == null){
            orderOfAphabet = new HashMap<Integer, Integer>();
        }
        return orderOfAphabet;
    }*/
}
