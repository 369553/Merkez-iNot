package Services;

import Control.IDARE;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CryptingService {
    private static CryptingService servicer = null;

    private CryptingService() {
    
    }
    
//İŞLEM YÖNTEMLERİ:
    public static String getMd5(String text){
        //.;.
        String cryptedText = "";
        try{
            MessageDigest crypter = MessageDigest.getInstance("MD5");
            byte[] cryptedByte = crypter.digest(text.getBytes());
            
            BigInteger number = new BigInteger(1, cryptedByte);
            cryptedText = number.toString(16);
            if(cryptedText.length() < 32)
                cryptedText= "0" + cryptedText;
        }
        catch(NoSuchAlgorithmException ex){
            ex.printStackTrace();
        }
        return IDARE.getMd5Code();
    }
    
//ERİŞİM YÖNTEMLERİ:
    //ANA ERİŞİM YÖNTEMİ:
    public static CryptingService getServicer(){
        if(servicer == null){
            servicer = new CryptingService();
        }
        return servicer;
    }
}
