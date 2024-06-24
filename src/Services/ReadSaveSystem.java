
package Services;

import Base.Note;
import Base.SystemSettings;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ReadSaveSystem {
    private static ReadSaveSystem SWSystem;
    //private static File managementFile, notesFile;
    
    private ReadSaveSystem(){
    }
//İŞLEM YÖNTEMLERİ:
    public boolean saveNote(Note note){
        File currentFile;
        /*Note'u olduğu gibi kaydet; sonuna category ismi, tarihi vs. yaz*/
        //Burada kaydetme işlemleri, dosya tipi işaretlemeleri, kayıt konumu, diske yazma izni olup olmadığı kontrol edilmeli, yapılmalı
        return true;
    }
    public boolean deleteFile(String path, String fileName){
        File file = new File(path + "\\" + fileName);
        if(!file.canWrite())
            return false;
        if(file.isDirectory())
            return false;
        System.err.println("path : " + file.getAbsolutePath());
        return file.delete();
    }
    
    
    
    public StringBuilder readMainFromFile() throws FileNotFoundException{
        String path = SystemSettings.getSettings().getPATHPosition();
        File managementFile = new File(path + "/centersys.mn");
        StringBuilder centersysContent = new StringBuilder();
        if(!managementFile.exists())
            throw new RuntimeException("Temel sistem dosyası bulunamadı");
        BufferedReader bufRead = null;
        try{
            bufRead = new BufferedReader(new FileReader(managementFile));
            String takedLine;
            while((takedLine=bufRead.readLine())!=null){
                centersysContent.append(takedLine);
            }
        }
        catch(Exception e_read){
            e_read.printStackTrace();
        }
        finally{
            if (bufRead!=null){
                try{
                    bufRead.close();
                }
                catch(IOException e_io){
                    e_io.printStackTrace();
                }
            }
        }
        
        return centersysContent;
    }
    
    public boolean saveMainToFile(String text){
        String path = SystemSettings.getSettings().getPATHPosition();
        File managementFile = new File(path + "/centersys.mn");
        try {
            managementFile.createNewFile() ;
        }
        catch( IOException e_io ){
            e_io.printStackTrace();
            System.err.println( "Temel sistem kayıt dosyası oluşturulamadı!" ) ;
        }
        try{
            BufferedWriter writer = new BufferedWriter( new FileWriter( managementFile.getAbsoluteFile() ) ) ;
            long space = managementFile.getTotalSpace();
            writer.write(text);
            writer.flush();
            writer.close();
            if(managementFile.getTotalSpace() > space)
                return true;
            else 
                return false;
        }
        catch(IOException e_IO){
            e_IO.printStackTrace();
            System.err.println("Veriler dosyaya yazılamadı");
        }
        return true;
    }
    
    /*public boolean writeCategory(Category category, boolean is_new) throws FileNotFoundException, UnsupportedEncodingException{
        String path = SystemSettings.getSettings().getPATHPosition();
        FileInputStream fis = null;
        InputStreamReader inputStream;
        inputStream = new InputStreamReader(fis, "UTF-8");
        FileReader fileRead = new FileReader(path + "/centersys.mn");
        BufferedReader bufRead = new BufferedReader(inputStream);
        bufRead.
        
        return true;
    }*/
    
    public boolean saveFile(String content, String path){
        String fileName = "HatırNot tarafından hâzırlanan metin dosyası.txt";
        return saveFile(content, path, fileName);
    }
    public boolean saveFile(String content, String path, String fileName){
        File saveLocation = new File(path);
        /*if(saveLocation == null)
            return false;*/
        if(!saveLocation.canWrite())
            return false;
        if(!saveLocation.isDirectory())
            return false;
        File produceFile = new File(saveLocation.getPath(), fileName);
    //    System.err.println("file dosya : " + produceFile.getAbsolutePath());
            long space = produceFile.getTotalSpace();
        try {
            FileWriter wrt = new FileWriter(produceFile);//DİSK SEÇİLDİĞİNDE HATÂ VERİYOR
    //        System.err.println("fileWriter üretildi");
            BufferedWriter bufwrt = new BufferedWriter(wrt);
    //        System.err.println("bufferedWriter üretildi");
    //        System.err.println("yazmadan önceki dosya boyutu : " + space);
            bufwrt.write(content);
            bufwrt.flush();
    //        System.err.println("Veri yazılmış olmalı");
            bufwrt.close();
    //        System.out.println("yeni dosya boyutu : " + produceFile.getTotalSpace());
            /*if(produceFile.getTotalSpace() > space){//Bu kontrolün yapılabilmesi için orada daha önce aynı isimde, aynı dosya boyutunda bir dosya olmamalı
    //            System.err.println("Dosya boyutu artmış; yanî veri yazılmış olmalı");
                return true;
            }
            else 
                return false;*/
        }
        catch (IOException ex) {
            ex.printStackTrace();
            System.err.println("Veriler dosyaya yazılamadı");
        }
        return true;
    }
    public String readFile(String path, String fileName){
        File file = new File(path + "\\" + fileName);
        System.err.println("ikinci satır");
        BufferedReader bufReader = null;
        StringBuilder strbuild = null;
        if(file == null)
            return null;
        try{
            FileReader fReader = new FileReader(file);
            System.err.println("try ikinci satır");
            bufReader = new BufferedReader(fReader);
            String takedLine;
            strbuild = new StringBuilder();
            
//            int i = 0;
//            while((i = bufReader.read()) != -1){
//                System.out.println(":" + i);
//            }

//            int i = 0;
//            while(bufReader.ready()){
//                i = bufReader.read();
//                if(i != -1){
//                    char c = (char) i;
//                    strbuild.append(c);
//                }
//            }

            while((takedLine=bufReader.readLine())!=null){
                strbuild.append(takedLine);
            }
        }
        catch(FileNotFoundException ex){
            return null;
        }
        catch(IOException ex){
            ex.printStackTrace();
        }
        finally{
            if(bufReader != null){
                try{
                    bufReader.close();
                }
                catch(IOException e_io){
                    e_io.printStackTrace();
                }
            }
        }
//        System.err.println("strbuild.toString():\n" + strbuild.toString());
        return strbuild.toString();
    }
    public String[] getFileList(String path){
        File file = new File(path);
        if(!file.isDirectory())
            return null;
        return file.list();
    }
    public boolean checkFilePositionForRW(String path){
        File file = new File(path);
        if(file == null)
            return false;
        if(!file.isDirectory())
            return false;
        if(file.canRead() && file.canWrite())
            return true;
        else
            return false;
    }

//ERİŞİM YÖNTEMLERİ:
    public static ReadSaveSystem usesaveReadSystem(){
        if (SWSystem == null){
            SWSystem = new ReadSaveSystem();
        }
        return SWSystem;
    }
}