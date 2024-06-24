package Base;

import Services.CryptingService;

public class User implements IEntity{
    protected int ID;
    protected String name;
    protected String password;
    protected SystemSettings personalSettings;
    
    public User(String name, String password, SystemSettings userOptions){
        this.name = name;
        this.password = password;
        this.personalSettings = userOptions;
    }
    
    public User(int ID, String name, String password, SystemSettings userOptions){
        this(name, password, userOptions);
        setID(ID);
    }
    
    private User(String name, SystemSettings settings){
        this.name = name;
        this.personalSettings = settings;
    }
    
    public static User getUnknownUser(){
        return new User("Ziyâretçi", SystemSettings.getSettings());
    }

//İŞLEM YÖNTEMLERİ:
/*Kullanıcının son yaptığı işlemler ve hangi cihazlardan işlem yapıldığı gibi bilgiler kullanıcıya hizmet
olarak sunulabilir*/
    public boolean renameUser(String text){
        if(text.isEmpty())
            return false;
        if(name.equals(text))
            return false;
        setName(text);
        updateUserInfo();
        return true;
    }
    
    public boolean changePassword(String text){
        if(getPassword().equals(CryptingService.getServicer().getMd5(text)))
            return false;
        else if(text.length() < 8)
            return false;
//EK KONTROLLER EKLENMELİ:       else if 
        else{
            setPassword(CryptingService.getServicer().getMd5(text));
            updateUserInfo();
            return true;
        }
    }
    
    public void changeGUISeeming(GUISeeming guiOrder){
        if(getPersonalSettings().getCurrentGuiSeeming().equals(guiOrder))
            return;
        getPersonalSettings().setCurrentGuiSeeming(guiOrder);
        updateUserInfo();
    }
    
    public boolean changePath(String path){
        if(getPersonalSettings().getPATHPosition().equals(path))
            return false;
        getPersonalSettings().setPATHPosition(path);
        updateUserInfo();
        return true;
    }
    
    public boolean changeIsLogging(boolean value){
        if(getPersonalSettings().getIs_logging() == value)
            return false;
        getPersonalSettings().setIs_logging(value);
        updateUserInfo();
        return true;
    }
    
    public boolean changeIsAutoSave(boolean value){
        if(getPersonalSettings().getIs_autoSaving() == value)
            return false;
        getPersonalSettings().setIs_autoSaving(value);
        updateUserInfo();
        return true;
    }
    
    public boolean changeSystemSettings(SystemSettings settings){
        if(getPersonalSettings().equals(settings))
            return false;
        setSettings(settings);
        updateUserInfo();
        return true;
    }
    //ARKAPLAN YÖNTEMLERİ:
    private void updateUserInfo(){
        if(DataBase.getDatabase().getMainDB() != null){
            DataBase.getDatabase().getMainDB().updateUserInfo(this);
        }
    }

//ERİŞİM YÖNTEMLERİ:
    public String getName() {
        return name;
    }
    
    private void setName(String name) {
        this.name = name;
    }
    
    public String getPassword() {
        if (password == null){
            return "";
        }
        return password;
    }

    private void setPassword(String password) {
        this.password = password;
    }
//FIX:
    public SystemSettings getPersonalSettings(){
        if (personalSettings == null){
            personalSettings = SystemSettings.getSettings();
        }
        return personalSettings;
    }

    private void setSettings(SystemSettings settings) {
        this.personalSettings = settings;
    }

    public int getID() {
        return ID;
    }

    private void setID(int ID) {
        this.ID = ID;
    }
}