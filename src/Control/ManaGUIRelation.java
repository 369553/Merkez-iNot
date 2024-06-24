package Control;
import javax.swing.JComponent;

public class ManaGUIRelation{
    Object thing;
    JComponent guiComponent;
    String dataType = "";
    
    public ManaGUIRelation(){
        
    }
    
    public ManaGUIRelation(Object thing, JComponent guiComponent) {
        this.thing = thing;
        this.guiComponent = guiComponent;
        dataType = extractDataType();
    }
    
//İŞLEM YÖNTEMLERİ:
    public String extractDataType(){
        switch(thing.getClass().getName()){
            case "Base.Note":
                return "Note";
            case "Base.Category":
                return "Category";
        }
        return "";
    }
    
//ERİŞİM YÖNTEMLERİ:

    public Object getThing() {
        return thing;
    }

    public void setThing(Object thing) {
        this.thing = thing;
    }

    public JComponent getGuiComponent() {
        return guiComponent;
    }

    public void setGuiComponent(JComponent guiComponent) {
        this.guiComponent = guiComponent;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }
    /*ESKİ:
    @Override
    public String toString(){
        String guiDataType = "";
        if(guiComponent.getClass().getName().equals("View.btnNote"))
            guiDataType = "btnNote";
        else if(guiComponent.getClass().getName().equals("View.btnFolder"))
            guiDataType = "btnFolder";
        else if (guiComponent.getClass().getName().equals("View.btnCategory"))
            guiDataType = "btnCategory";
        else
            guiDataType = guiComponent.getClass().getName();
        return new String("Veri tipi: " + dataType + "\n" + 
                "Veriyi simgeleyen görsel elemanın tipi: " + guiDataType + "\n" +
                "Veri bilgileri: " + thing.toString() + "\n" +
                "Veriyi simgeleyen görsel eleman bilgileri: " + guiComponent.toString());
    }*/
}