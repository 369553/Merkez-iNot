    package Base;

    import static Base.DBHelper.showErrorMessage;
    import Control.IDARE;
    import Services.CryptingService;
    import java.lang.reflect.Field;
    import java.sql.PreparedStatement;
    import java.sql.ResultSet;
    import java.sql.SQLException;
    import java.sql.Statement;
    import java.util.ArrayList;
    import java.util.HashMap;
    import java.sql.Connection;
    import java.util.Calendar;
    import java.util.Date;
    import java.util.Locale;
    import java.util.TimeZone;

    public class MySqlDB implements MainDB{
        private Statement STquery;
        private PreparedStatement preparedST;
        private Connection dbConnection;
        ArrayList<Category> categories;
        ArrayList<Note> notes;
        ArrayList<GUISeeming> themes;
        ArrayList<NoteTag> tags;
        HashMap<Integer, HashMap> pageDatas;
        private User user;
        private boolean backupingNow = false; 

        //----Sistem ortam değişkenleri:
        private ArrayList<String> specialFieldNamesForNote;
        //private ArrayList<String> specialFieldNamesForUser;

        public MySqlDB(Connection connection){
            this.dbConnection = connection;
            //.;.
            specialFieldNamesForNote = new ArrayList<String>();
            specialFieldNamesForNote.add("user");
            specialFieldNamesForNote.add("category");
            specialFieldNamesForNote.add("tags");
            //specialFieldNamesForUser = new ArrayList<String>();
            //specialFieldNamesForUser.add("guiSeeming");
        }

    //İŞLEM YÖNTEMLERİ:
        @Override
        public boolean installSystem(String code, DataBase database){
            if(!CryptingService.getMd5(code).equals(IDARE.getMd5Code()))
                return false;
            if(database == null)
                return false;
            backupingNow = true;
            this.readUserInfo();
            database.setUser(user, code, false, true);
            this.readCategories();
            if(categories == null)
                return false;
            database.setCategories(categories, code);
            this.readNoteTags();
            if(tags != null)
                database.setNoteTags(tags, code);
            this.readNotes();
            if(notes == null)
                return false;
            database.setNotes(notes, code);
            for(Note nt : notes){
                nt.getCategory().addANoteFromDB(this, code, nt.getID());
            }
            this.readNotePageData();
            if(pageDatas != null)
                database.setNotePageDatas(pageDatas);
            database.lastChecks(this, code);
            if(checkSystemForConsistency()){
    //            backupSystem(code, DB_PATH, false);//YEdek adresiyle sistem oluşturulduğunda bu işlem burada yapılamıyor; IDARE!de yapılıyor ama Elhamdülillâh
                backupingNow = false;
                return true;
            }
            backupingNow = false;
            return true;
        }
        @Override
        public boolean checkUserInfo(String code, String userName, String md5Password, User user){
            if(!CryptingService.getMd5(code).equals(IDARE.getMd5Code()))
                return false;
            if(!userName.equals(user.getName()))
                return false;
            /*if(!md5Password.equals(user.getPassword()))
                return false;*/
            return true;
        }
        @Override
        public boolean setupFirstConnection(String code, DataBase database){
            boolean isSuccessful = false;
            isSuccessful = this.prepareDBConnection();
            if(!isSuccessful)
                return false;
            String setup = "CREATE TABLE NOTES(id int NOT NULL UNIQUE PRIMARY KEY, name VARCHAR(50) NOT NULL, malumat VARCHAR(15750), lastChangeTime DATETIME, produceTime DATETIME, category int NOT NULL, user int NOT NULL, tags VARCHAR(500)) DEFAULT CHARSET = utf8mb4;";
            String setup2 = "CREATE TABLE CATEGORIES(id int NOT NULL UNIQUE PRIMARY KEY, name VARCHAR(50) NOT NULL, explanation VARCHAR(150), lastChangeTime DATETIME, produceTime DATETIME, categoryColor CHAR(7) CHARACTER SET latin1) DEFAULT CHARSET = utf8mb4;";
            String setup3 = "CREATE TABLE NOTETAGS(id int NOT NULL UNIQUE PRIMARY KEY, name VARCHAR(50)) DEFAULT CHARSET = utf8mb4;";
            String setup4 = "CREATE TABLE PAGEDATAS(noteID int UNIQUE PRIMARY KEY, caretPosition int, textSize smallint);";
            String setup5 = "CREATE TABLE SYSINFO(IDCounterForNote int NOT NULL, IDCounterForCategory int NOT NULL, IDCounterForNoteTag int NOT NULL, strDBType VARCHAR(10) CHARACTER SET latin1 NOT NULL) DEFAULT CHARSET = utf8mb4;";
            String setup6 = "CREATE TABLE USER(id int NOT NULL UNIQUE PRIMARY KEY, name VARCHAR(30) NOT NULL, passMd5 VARCHAR(200), guiSeeming VARCHAR(20) CHARACTER SET latin1, isLogging bit, isAutoSaving bit, path VARCHAR(1024)) DEFAULT CHARSET = utf8mb4;";
            Statement ex2 = null;
            try{
                ex2 = DBHelper.getConnection().createStatement();
                ex2.execute(setup);
                ex2.execute(setup2);
                ex2.execute(setup3);
                ex2.execute(setup4);
                ex2.execute(setup5);
                ex2.execute(setup6);
                isSuccessful = true;
            }
            catch(SQLException DBException){
                DBHelper.showErrorMessage(DBException);
            }
            if(isSuccessful)
                return true;
            return false;
        }
        public boolean prepareDBConnection(){
            Connection connectDB = DBHelper.getConnection();
            if(connectDB != null){
                int index = 1;
                connectDB = null;
                while(connectDB == null){
                    index++;
                    DBHelper.setDBUrlNumber(index, this);
                    if(setupDBByName("hatirnot" + String.valueOf(index))){
                        connectDB = DBHelper.getConnection();
                    try{
                        if(connectDB != null)
                        System.out.println("connectDB veritabanı : " + connectDB.getCatalog());
                    }
                    catch(SQLException DBException){
                        DBHelper.showErrorMessage(DBException);
                        System.out.println("Hatâ çıktı!");
                    }
                    }
                    if(index == 100)
                        return false;
                }
                return true;
            }
            return setupDBByName("hatirnot");
        }
        private boolean setupDBByName(String DBName){
            String setupDB = "CREATE DATABASE " + DBName;
            boolean isSuccessful = false;
            Statement ex = null;
            try{
                ex = DBHelper.getFirstConnection().createStatement();
                isSuccessful = ex.execute(setupDB);
                isSuccessful = true;
            }
            catch(SQLException DBException){
                DBHelper.showErrorMessage(DBException);
            }
            if(!isSuccessful)
                System.out.println("hatâ var!!");
            return isSuccessful;
        }
        @Override
        public boolean saveNote(Note note){
            Object[][] fieldValues = prepareDataNote(note, true);
            String[] fields = (String[]) fieldValues[0];
            return addRowToDB(note, "NOTES", fields, fieldValues[1]);
        }
        @Override
        public boolean saveNoteInfo(int noteID, HashMap<String, Integer> notePageData){
            if(DataBase.getDatabase().findNoteByID(noteID) == null)
                return false;
            String sqlOrder = "INSERT INTO PAGEDATAS (noteID, caretPosition, textSize) VALUES (?, ?, ?)";
            PreparedStatement preStatement = null;
            try{
                preStatement = DBHelper.getConnection().prepareStatement(sqlOrder);
                preStatement.setInt(1, noteID);
                preStatement.setInt(2, notePageData.get("textSize"));
                preStatement.setInt(3, notePageData.get("caretPosition"));
                preStatement.execute();
                return true;
            }
            catch(SQLException DBException){
                showErrorMessage(DBException);
            }
            return false;
        }
        @Override
        public boolean saveNoteWithNoteInfo(Note note, HashMap<String, Integer> notePageData){
            if(saveNote(note) && saveNoteInfo(note.getID(), notePageData))
                return true;
            return false;
        }
        @Override
        public boolean saveCategory(Category category){
            Object[][] parameters = prepareDataStandard(category, true);
            String[] fields = (String[]) parameters[0];
            return addRowToDB(category, "CATEGORIES", fields, parameters[1]);
        }
        @Override
        public boolean saveNoteTag(NoteTag tag){
            System.out.println("MySqlDB -> saveNoteTag");
            String sqlOrder = "INSERT INTO NOTETAGS (id, name) VALUES (?, ?)";
            try{
                PreparedStatement preStatement = DBHelper.getConnection().prepareStatement(sqlOrder);
                preStatement.setInt(1, tag.getID());
                preStatement.setString(2, tag.getName());
                preStatement.execute();
                return true;
            }
            catch(SQLException DBException){
                showErrorMessage(DBException);
            }
            return false;
        }
        @Override
        public boolean saveUserInfo(User user){
            return appOnUserInfo(user, true);
        }
        @Override
        public boolean saveSysInfo(SysInfo info){
            String sqlOrder = "INSERT INTO sysinfo (IDCounterForNote, IDCounterForCategory, IDCounterForNoteTag, strDBType) VALUES (?, ?, ?, ?)";
            try{
                PreparedStatement statement = DBHelper.getConnection().prepareStatement(sqlOrder);
                statement.setInt(1, info.getIDCounterForNote());
                statement.setInt(2, info.getIDCounterForCategory());
                statement.setInt(3, info.getIDCounterForNoteTag());
                statement.setString(4, info.getStrDBType());
                statement.execute();
                return true;
            }
            catch(SQLException DBException){
                DBHelper.showErrorMessage(DBException);
            }
            return false;
        }
        @Override
        public boolean deleteNote(Note note){
            return delRowToDB(note, "NOTES", "id");
        }
        @Override
        public boolean deleteNoteInfo(Note note){
            return delRowToDB(note, "PAGEDATAS", "noteID");
        }
        @Override
        public boolean deleteCategory(Category category){
            return delRowToDB(category, "CATEGORIES", "id");
        }
        @Override
        public boolean deleteNoteTag(NoteTag tag){
            return delRowToDB(tag, "NOTETAGS", "id");
        }
        @Override
        public boolean updateNote(Note note){
            if(backupingNow)
                return true;
            Object[][] fieldValues = prepareDataNote(note, false);
            String[] fields = (String[]) fieldValues[0];
            return updRowToDB(note, "notes", fields, fieldValues[1]);
        }
        @Override
        public boolean updateNoteInfo(int noteID, HashMap<String, Integer> notePageData){
            if(backupingNow)
                return true;
            if(DataBase.getDatabase().findNoteByID(noteID) == null)
                return false;
            String sqlOrder = "UPDATE PAGEDATAS SET caretPosition = ?, textSize = ? WHERE noteID = ?";
            PreparedStatement preStatement = null;
            try{
                System.out.println("Not bilgi güncelleme başı");
                preStatement = DBHelper.getConnection().prepareStatement(sqlOrder);
                preStatement.setInt(1, notePageData.get("caretPosition"));
                preStatement.setInt(2, notePageData.get("textSize"));
                preStatement.setInt(3, noteID);
                System.out.println("Not bilgi güncelleme sonu");
                preStatement.executeUpdate();
                return true;
            }
            catch(SQLException DBException){
                showErrorMessage(DBException);
            }
            return false;
        }
        @Override
        public boolean updateNoteWithNoteInfo(Note note, HashMap<String, Integer> notePageData){
            if(backupingNow)
                return true;
            if(updateNote(note) && updateNoteInfo(note.ID, notePageData))
                return true;
            return false;
        }
        @Override
        public boolean updateCategory(Category category){
            if(backupingNow)
                return true;
            Object[][] parameters = prepareDataStandard(category, false);
            String[] fields = (String[]) parameters[0];
            for(int sayac = 0; sayac < fields.length; sayac++){
//                System.out.println("[" + sayac + ". alan ismi] := " + fields[sayac].toString());
//                System.out.println("[Alan değeri ] := " + parameters[1][sayac].toString());
            }
            return updRowToDB(category, "categories", fields, parameters[1]);
        }
        @Override
        public boolean updateNoteTag(NoteTag tag){
            if(backupingNow)
                return true;
            return updRowToDB(tag, "NOTETAGS", new String[]{"name"}, new Object[]{tag.getName()});
        }

        @Override
        public boolean updateUserInfo(User user){
            if(backupingNow)
                return true;
            return appOnUserInfo(user, false);
        }

        @Override
        public boolean updateSysInfo(SysInfo info){
            /*Class infoClass = info.getClass();
            Field[] fields = infoClass.getDeclaredFields();
            String[] strFields = new String[fields.length + 3];
            Object[] values = new Object[fields.length + 3];
            int index = 0;
            for(Field f : fields){
                strFields[index] = f.getName();
                try{
                    values[index] = f.get(info);
                }
                catch(IllegalArgumentException e){
                    e.printStackTrace();
                }
                catch(IllegalAccessException e){
                    e.printStackTrace();
                }
            }*/
            boolean neverSavedBefore = false;
            String askIsHave = "SELECT * FROM sysinfo";
            ResultSet results = null;
            int idCat = -1;
            try{
                Statement statement = DBHelper.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
                results = statement.executeQuery(askIsHave);
                if(results.first())
                    idCat = results.getInt("IDCounterForCategory");
                else
                    neverSavedBefore = true;
            }
            catch(SQLException DBException){
                DBHelper.showErrorMessage(DBException);
            }
            if(neverSavedBefore)
                return saveSysInfo(info);
            String sqlOrder = "UPDATE SYSINFO SET IDCounterForNote = ?, IDCounterForCategory = ?, IDCounterForNoteTag = ?, strDBType = ?";
            try{
                PreparedStatement preStatement = DBHelper.getConnection().prepareStatement(sqlOrder);
                preStatement.setInt(1, info.getIDCounterForNote());
                preStatement.setInt(2, info.getIDCounterForCategory());
                preStatement.setInt(3, info.getIDCounterForNoteTag());
                preStatement.setString(4, info.getStrDBType());
                preStatement.executeUpdate();
                return true;
            }
            catch(SQLException DBException){
                showErrorMessage(DBException);
            }
            return false;
        }
        public void setSTquery(Statement STquery){
            this.STquery = STquery;
        }
        @Override
        public ArrayList<Category> readCategories(){
            String ask = new String("SELECT * FROM categories");
            ResultSet rs;
            if(categories == null)
                categories = new ArrayList<Category>();
            try{
                getSTQuery().clearBatch();
                rs = getSTQuery().executeQuery(ask);
                while(rs.next()){
                        Date produceTime = Date.from(rs.getTimestamp("produceTime", Calendar.getInstance(TimeZone.getTimeZone("GMT+3:00"))).toInstant());
                        Date lastChangeTime = Date.from(rs.getTimestamp("lastChangeTime", Calendar.getInstance(TimeZone.getTimeZone("GMT+3:00"))).toInstant());
                    Category category= new Category(rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("explanation"),
                            produceTime,
                            lastChangeTime,
                            rs.getString("categoryColor"));
    // ResultSet temp = getDBItemByIDandTable(resultCategories.getInt("image"), "image");
                    //image id alındıktan sonra ArrayList içinde o id ye sâhip olan simgenin geri döndürülmesi gerekir.

                    categories.add(category);
                }
            }
            catch(SQLException DBException){
                showErrorMessage(DBException);
            }
            return categories; 
        }
        @Override
        public ArrayList<Note> readNotes(){//Not sâhibi sistemdeki kullanıcı olarak atanıyor; yanî ileride çoklu kullanıcılı yapıya geçilirse burasının değiştirilmesi lazım
            String query = "SELECT * FROM notes";
            ResultSet rs;
            if(notes == null){
                notes = new ArrayList();
                try{
                    getSTQuery().clearBatch();
                    rs = getSTQuery().executeQuery(query);
                    while(rs.next()){
                        Date produceTime = Date.from(rs.getTimestamp("produceTime", Calendar.getInstance(TimeZone.getTimeZone("GMT+3:00"))).toInstant());
                        Date lastChangeTime = Date.from(rs.getTimestamp("lastChangeTime", Calendar.getInstance(TimeZone.getTimeZone("GMT+3:00"))).toInstant());
                        Note note = new Note(rs.getInt("id"),
                                DataBase.getDatabase().findUserByID(rs.getInt("user")),
                                rs.getString("name"),
                                rs.getString("malumat"),
                                DataBase.getDatabase().findCategoryByID(rs.getInt("category")),
                                produceTime,
                                lastChangeTime);
                       String[] tagIDs = rs.getString("tags").split(",");
                        if(tagIDs != null)
                            if(!tagIDs[0].isEmpty()){
                                for(int index = 0; index < tagIDs.length; index++){
                                    note.addTag(DataBase.getDatabase().findNoteTagByID(Integer.parseInt(tagIDs[index])));
                                }
                            }
                        notes.add(note);
                    }
                }
                catch(SQLException DBException){
                    showErrorMessage(DBException);
                }
            }
            return notes;
        }
        @Override
        public ArrayList<NoteTag> readNoteTags(){
            String query = "SELECT * FROM notetags";
            ResultSet resuts;
            if(tags == null)
                tags = new ArrayList<NoteTag>();
            try{
                getSTQuery().clearBatch();
                resuts = getSTQuery().executeQuery(query);
                while(resuts.next()){
                    NoteTag tag = new NoteTag(resuts.getString("name"), resuts.getInt("id"));
                    tags.add(tag);
                }
            }
            catch(SQLException DBException){
                showErrorMessage(DBException);
            }
            return tags;
        }
        @Override
        public SysInfo readSysInfo(String code){
            String sql = "SELECT * FROM sysinfo";
            ResultSet rs = null;
            SysInfo info = null;
            try{
                Statement statement =  DBHelper.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
                rs = statement.executeQuery(sql);
                rs.absolute(1);
                int IDCounterForNote = rs.getInt("IDCounterForNote");
                int IDCounterForCategory = rs.getInt("IDCounterForCategory");
                int IDCounterForNoteTag = rs.getInt("IDCounterForNoteTag");
                String strDBType = rs.getString("strDBType");
                return SysInfo.produceSysInfo(IDCounterForNote, IDCounterForCategory, IDCounterForNoteTag, strDBType, code);
            }
            catch(SQLException DBException){
                System.err.println("Hatâ burada, (MySqlDB -> readSysInfo)");
                showErrorMessage(DBException);
            }
                return null;
        }
        @Override
        public User readUserInfo(){
            String ask = new String("select * from user");
            ResultSet resultUserData;
            try{
                resultUserData = getSTQuery().executeQuery(ask);
                resultUserData.absolute(1);
                user = new User(resultUserData.getInt("id"),
                        resultUserData.getString("name"),
                        resultUserData.getString("passMd5"),//şifre aslî hâliyle saklanmamalı!
                        new SystemSettings(resultUserData.getString("guiSeeming"),
                                resultUserData.getString("path"),
                                resultUserData.getBoolean("isLogging"),//bit olarak sakla
                                resultUserData.getBoolean("isAutoSaving")
                        )
                );
                return user;
            }
            catch(SQLException DBException){
                showErrorMessage(DBException);
            }
            return null;
        }
        @Override
        public HashMap<Integer, HashMap> readNotePageData(){
            String sql = "SELECT * FROM pagedatas";
            ResultSet rs;
            if(pageDatas == null){
                pageDatas = new HashMap<Integer, HashMap>();
            }
            try{
                getSTQuery().clearBatch();
                rs = getSTQuery().executeQuery(sql);
                while(rs.next()){
                    HashMap<String, Integer> pData = new HashMap<String, Integer>();
                    int caretPos = rs.getInt("caretPosition");
                    int textSize = rs.getInt("textSize");
                    int noteID = rs.getInt("noteID");
                    if(textSize == 0)
                        textSize = 16;
                    pData.put("textSize", textSize);
                    pData.put("caretPosition", caretPos);
                    if(noteID != 0)
                        pageDatas.put(noteID, pData);
                }
                return pageDatas;
            }
            catch(SQLException DBException){
                showErrorMessage(DBException);
            }
            return null;
        }
        @Override
        public boolean backupSystem(String code, String path, boolean isCryot){
            //.;.
            return false;
        }
        //ARKAPLAN İŞLEM YÖNTEMLERİ:
        private boolean addRowToDB(IEntity entity, String tableName, String[] fields, Object[] values){
            int changedRowNumber = 0;
            StringBuilder sentence = new StringBuilder("INSERT INTO " + tableName + " ");
            sentence.append("(");
            for(int index = 0; index < fields.length; index++){
                sentence.append(fields[index]);
                if(index != fields.length - 1)
                    sentence.append(", ");
            }
            sentence.append(") ");
            sentence.append("VALUES ");
            sentence.append("( ");
            for(int index = 0; index < values.length; index++){
                sentence.append("?");
                if(index != fields.length - 1)
                    sentence.append(", ");
            }
            sentence.append(")");
            System.err.println("sentence not : " + sentence.toString());
            try{
                PreparedStatement preStatement = DBHelper.getConnection().prepareStatement(sentence.toString());
                for(int index = 1; index <= values.length; index++){
//                    System.out.println("Alan : " + fields[index - 1] + "\nSıra no : " + index + "\nYerleştirilen değer : " + values[index - 1].toString());
                    preStatement.setObject(index, values[index - 1]);
                }
                changedRowNumber = preStatement.executeUpdate();
            }
            catch(SQLException DBException){
                showErrorMessage(DBException);
            }
            if(changedRowNumber == 0)
                return false;
            return true;
        }
        private boolean delRowToDB(IEntity entity, String tableName, String primaryKeyName){
            String sentence = "DELETE FROM " + tableName + " WHERE " + primaryKeyName + " = ?";
            try{
                PreparedStatement preStatement = DBHelper.getConnection().prepareStatement(sentence);
                preStatement.setInt(1, entity.getID());
                Boolean ansewr = preStatement.execute();
                return true;
            }
            catch(SQLException DBException){
                showErrorMessage(DBException);
            }
            return false;
        }
        private boolean updRowToDB(Object entity, String tableName, String[] fields, Object[] values, String whereCondition, Object conditionAnswer){
            StringBuilder sentence = new StringBuilder("UPDATE " + tableName + " SET ");
            int changedRowNumber = 0;
            int indexOfQuestionMarks = 1;
            for(int index = 0; index < fields.length; index++){
                sentence.append(fields[index] + " = ?" );
//                sentence.append(values[index]);
                if(index != fields.length - 1)
                    sentence.append(", ");
            }
            sentence.append(" WHERE " + whereCondition + " = ?");
//                System.out.println("sentence verisiz hâli : " + sentence);
            PreparedStatement preStatement = null;
            try{
                preStatement = DBHelper.getConnection().prepareStatement(sentence.toString());
                for(int index = 1; index <= fields.length; index++){
                    preStatement.setObject(index, values[index - 1]);
                    indexOfQuestionMarks++;
                }
                if(conditionAnswer.getClass().getName().equals(String.class.getName()))
                    preStatement.setString(indexOfQuestionMarks, (String) conditionAnswer);
                else if(conditionAnswer.getClass().getName().equals(Integer.class.getName()))
                    preStatement.setInt(indexOfQuestionMarks, (int) conditionAnswer);
                System.out.println("sentence son hâli : " + preStatement.toString());
                changedRowNumber = preStatement.executeUpdate();
                return true;
            }
            catch(SQLException DBException){
                showErrorMessage(DBException);
            }
            return false;
        }
        private boolean updRowToDB(IEntity entity, String tableName, String[] fields, Object[] values){
            return updRowToDB(entity, tableName, fields, values, "id", entity.getID());
        }
        private boolean checkSystemForConsistency(){
            //Not etiket ID'lerinde veyâ diğer ID'lerde çakışma olup, olmadığına bakılmalı;
            //Çakışma varsa çözüm üretilmeli
            //.;.
            return true;
        }
        private Object manageSpecialFieldOfNote(Object dataInField){
            if(dataInField == null)
                return "";
            if(dataInField.getClass().getName().equals(Category.class.getName()))
                return ((Category) dataInField).getID();
            else if(dataInField.getClass().getName().equals(User.class.getName()))
                return ((User) dataInField).ID;
            else if(dataInField.getClass().getName().equals(ArrayList.class.getName())){
                if(((ArrayList) dataInField).isEmpty())
                    return "";
                if(((ArrayList) dataInField).get(0).getClass().getName().equals(NoteTag.class.getName())){
                    ArrayList<NoteTag> tags = (ArrayList<NoteTag>) dataInField;
                    StringBuilder resultValue = new StringBuilder();
                    for(NoteTag tag : tags){
                        resultValue.append(tag.ID + ", ");
                    }
                    resultValue.deleteCharAt(resultValue.length() - 1);
                    resultValue.deleteCharAt(resultValue.length() - 1);
                    return resultValue.toString();
                }
                /*ArrayList arLi = (ArrayList) dataInField;
                String genericTypeName = arLi.get(0).getClass().getName();
                for(Object value : arLi.toArray()){
                    Class.forName(genericTypeName);
                    //Burada kodlar daha esnek hâle getirilebilir:
                    //Listedeki elemanları liste sınıf tipinde elemanlar tipinde değişkenler olarak tanımlayıp, ID alanına erişilebilir.
                }*/
            }
            return null;
        }
        //SystemSettings özellikleri User özellikleri olarak kaydedildiğinden bu şekilde istisna idâresi uygun değil; o yüzden alttaki yöntem kaldırıldı (yorum yapıldı)
        /*private Object manageSpecialFieldOfUser(Object dataInField){
            if(dataInField.getClass().getName().equals(GUISeeming.class.getName()))
                return ((GUISeeming) dataInField).guiSeemingName;
            return null;
        }*/
       /* public int extractFontStyle(boolean isBold, boolean isItalic){
            if(isBold == true && isItalic == true){
                return 3;
            }
            else if(isBold == true)
                return 1;
            else if(isItalic == true)
                return 2;
            else
                return 0;
        }
        public SystemSettings solveSettings(String text){//Çağrılan dosyadan okumalar yaparak ayarları çıkarma
            return SystemSettings.getSettings();
        }*/
        //VERİ ÇEKME YÖNTEMLERİ:
        /*public ArrayList <GUISeeming> getDBThemes(){
            String ask = new String("select* from themes");
            themes = new ArrayList<GUISeeming>();
            ResultSet resultThemes;
            try{
                getSTQuery().clearBatch();
                resultThemes = getSTQuery().executeQuery(ask);
                while(resultThemes.next()){
                    themes.add(new GUISeeming(resultThemes.getString("themeName"),
                        resultThemes.getString("backgroundColor"),
                        resultThemes.getString("buttonColor"),
                        resultThemes.getString("buttonTextColor"),
                        resultThemes.getString("buttonOnMouseColor"),
                        resultThemes.getString("buttonTextOnMouseColor"),
                        resultThemes.getString("textColor"),
                        resultThemes.getString("menuColor"),
                        resultThemes.getString("notingAreaColor"),
                        resultThemes.getString("notingAreaTextColor"),
                        resultThemes.getString("notingAreaCursorColor"),
                        resultThemes.getString("notingAreaSelectedColor"),
                        resultThemes.getString("notingAreaSelectedTextColor"),
                        resultThemes.getString("componentBorderColor"),
                        resultThemes.getString("topMenuGroundBackgroundColor"),
                        resultThemes.getString("releasebleMenuTextColor"),
                        resultThemes.getString("releasebleMenuItemColor"),
                        resultThemes.getString("releasebleMenuItemTextColor"),
                        resultThemes.getString("releasebleMenuItemOnMouseColor"),
                        resultThemes.getString("releasebleMenuItemOnMouseTextColor"),
                        resultThemes.getString("editToolsBackgroundColor"),
                        fetchFont(resultThemes.getInt("fontUI"))));
                }
            }
            catch(SQLException DBException){
                showErrorMessage(DBException);
            }
            return themes;
        }
        public Font fetchFont(int id){
            String ask = new String("SELECT fontName, isItalic, isBold, textSize FROM FONTS WHERE ID=" + id);
            ResultSet fontResult;
            try{
                getSTQuery().clearBatch();
                fontResult = getSTQuery().executeQuery(ask);
                return new Font(fontResult.getString("fontName"), extractFontStyle(fontResult.getBoolean("isBold"), 
                        fontResult.getBoolean("isItalic")), fontResult.getInt("fontSize"));
            }
            catch(SQLException DBException){
                showErrorMessage(DBException);
            }
            return null;
        }*/
        private Object[][] prepareDataNote(Note note, boolean takeIDField){
            int sayac = 0;
            Class noteClass = note.getClass();
            Field[] fields = noteClass.getDeclaredFields();
            for(Field f : fields){
                if(f.getModifiers() != 4)
                    continue;
                if(!takeIDField)
                    if(f.getName().toLowerCase(Locale.ENGLISH).equals("id"))
                     continue;
                sayac++;
            }
            String[] strFields = new String[sayac];
            Object[] values = new Object[sayac];
            Object[][] complete = new Object[2][sayac];
            int index = 0;
            for(Field f : fields){
                if(f.getModifiers() != 4)
                    continue;
                if(!takeIDField)
                    if(f.getName().toLowerCase(Locale.ENGLISH).equals("id"))
                        continue;
                strFields[index] = f.getName();
                try{
                    if(getSpecialFieldNamesForNote().contains(f.getName()))
                        values[index] = manageSpecialFieldOfNote(f.get(note));
                    else
                        values[index] = f.get(note);
                } 
                catch(IllegalArgumentException e){
                    e.printStackTrace();
                }
                catch(IllegalAccessException e){
                    e.printStackTrace();
                }
                index++;
            }
            complete[0] = strFields;
            complete[1] = values;
            return complete;
        }
        private Object[][] prepareDataStandard(Object entity, boolean takeIDField){
            Class ctClass = entity.getClass();
            Field[] fields = ctClass.getDeclaredFields();
            int sayac = 0;
            for(Field f : fields){
                if(f.getModifiers() != 4)
                    continue;
                if(!takeIDField)
                    if(f.getName().toLowerCase(Locale.ENGLISH).equals("id"))
                         continue;
                sayac++;
            }
            String[] strFields = new String[sayac];
            Object[] values = new Object[sayac];
            Object[][] complete = new Object[2][sayac];
            int index = 0;
            for(Field f : fields){
                if(f.getModifiers() != 4)
                    continue;
                if(!takeIDField)
                    if(f.getName().toLowerCase(Locale.ENGLISH).equals("id"))
                        continue;
                strFields[index] = f.getName();
                try{
                    values[index] = f.get(entity);
                } 
                catch(IllegalArgumentException e){
                    e.printStackTrace();
                }
                catch(IllegalAccessException e){
                    e.printStackTrace();
                }
                index++;
            }
            complete[0] = strFields;
            complete[1] = values;
            return complete;
        }
        private boolean appOnUserInfo(User user, boolean isAddToDB){
            Class userClass = user.getClass();
            Field[] fields = userClass.getDeclaredFields();
            String[] strFields = new String[fields.length + 3];
            Object[] values = new Object[fields.length + 3];
            strFields[0] = "id";
            strFields[1] = "name";
            strFields[2] = "passMd5";
            strFields[3] = "guiSeeming";
            strFields[4] = "isLogging";
            strFields[5] = "isAutoSaving";
            strFields[6] = "path";
            values[0] = user.getID();
            values[1] = user.getName();
            values[2] = user.getPassword();
            values[3] = user.getPersonalSettings().getCurrentGuiSeeming().getGuiSeemingName();
            values[4] = user.getPersonalSettings().getIs_logging();
            values[5] = user.getPersonalSettings().getIs_autoSaving();
            values[6] = user.getPersonalSettings().getPATHPosition();
            if(!isAddToDB){
                Object[] tValues = new Object[strFields.length - 1];
                String[] tStr = new String[strFields.length - 1];
                for(int sayac = 1; sayac < 7; sayac++){
                    tStr[sayac - 1] = strFields[sayac];
                    tValues[sayac - 1] = values[sayac];
                }
                values = tValues;
                strFields = tStr;
            }
            if(isAddToDB)
                return addRowToDB(user, "USER", strFields, values);
            else
                return updRowToDB(user, "USER", strFields, values);
        }

    //ERİŞİM YÖNTEMLERİ:
        private Statement getSTQuery(){
            if(STquery == null){
                try{
                    STquery = DBHelper.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
                }
                catch(SQLException DBException){
                    DBHelper.showErrorMessage(DBException);
                }
            }
            return STquery;
        }
        private ArrayList<String> getSpecialFieldNamesForNote(){
            if(specialFieldNamesForNote == null){
                specialFieldNamesForNote = new ArrayList<String>();
            }
            return specialFieldNamesForNote;
        }
    }