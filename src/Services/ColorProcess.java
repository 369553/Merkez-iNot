
package Services;

import java.awt.Color;

public class ColorProcess {
    private static ColorProcess colorService = null;

    public ColorProcess() {
    }

//İŞLEM YÖNTEMLERİ:
    public String getAsHexCode(Color color){
        int redValue = color.getRed() ;
        int greenValue = color.getGreen();
        int blueValue = color.getBlue();
        return getAsHexCode(redValue, greenValue, blueValue);
    }
    public String getAsHexCode(int redValue, int greenValue, int blueValue){
        String hexRed = Integer.toHexString(redValue),          //ADIM 1 : Sayıları onaltılık tabana çevir
                hexGreen = Integer.toHexString(greenValue),
                hexBlue = Integer.toHexString(blueValue);
        String[] values = new String[]{hexRed, hexGreen, hexBlue};  //Döngüyle işlem yapabilmek için değerleri dizi hâline getir
        /*System.err.println("hexRed : " + hexRed);
        System.out.println("hexGreen : " + hexGreen);
        System.out.println("hexBlue : " + hexBlue);*/
        for(int index = 0; index < 3; index++){
            if(values[index].length() == 1){         //EĞER BASAMAK SAYISI TEK İSE ÖNÜNE SIFIR KOY
                String valueWithZero = "0" + values[index];
                values[index] = valueWithZero;
            }
        }
        String value = "#" + values[0] + values[1] + values[2];
        //value.toUpperCase()     //BU İŞLEME GEREK VARSA, BU İŞLEMİ YAP Bİ İZNİLLÂH
//        System.out.println("Girilen sayıların renk için birleştirilmiş onaltılık kodu : " + value);
        return value;
    }
    public boolean isHexCode(String text){
        String checkText = text.trim().toLowerCase();
        int currentValue;
        if(text.isEmpty())
            return false;
        if(text.length() != 7)
                return false;
        if(checkText.startsWith("#") || checkText.startsWith("0x") || checkText.startsWith("+") || checkText.startsWith("-")){
            for(int index = 1; index < text.length(); index++){
                switch(checkText.charAt(index)){
                    case 'a' :{
                        currentValue = 10;
                        break;
                    }
                    case 'b' :{
                        currentValue = 11;
                        break;
                    }
                    case 'c' :{
                        currentValue = 12;
                        break;
                    }
                    case 'd' :{
                        currentValue = 13;
                        break;
                    }
                    case 'e' :{
                        currentValue = 14;
                        break;
                    }
                    case 'f' :{
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
                    default :{
                        return false;
                    }
                }
            }
            return true;
        }
        return false;
    }

//ERİŞİM YÖNTEMLERİ:
    //ANA ERİŞİM YÖNTEMİ:
    public static ColorProcess getColorService(){
        if(colorService == null){
            colorService = new ColorProcess();
        }
        return colorService;
    }
}
