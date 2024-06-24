package View;

import javax.swing.Icon;

public class ColorInf{
    Icon a;
    String b;

    ColorInf(Icon a,String b){
        this.a=a;
        this.b=b;
    }

//İŞLEM YÖNTEMLERİ:
    public void setA(Icon a){
        this.a = a;
    }
    public void setB(String b){
        this.b = b;
    }

//ERİŞİM YÖNTEMLERİ:
    public Icon getA(){
        return a;
    }
    public String getB(){
        return b;
    }
}