package Base;

public class chkPointSystem{
    String[] points;
    int locationOnPoints = 0;

    public chkPointSystem() {
        
    }

//İŞLEM YÖNTEMLERİ:
    public void addNewCheckPoint(String text){//Listenin sonuna bir eleman ekle
        if(points == null){//Kaydedilmiş adım yoksa
            points = new String[1];
            points[0] = text;//Yeni adımı kaydet
            locationOnPoints++;//İmleci son noktaya al
            //Tuşların aktifliğini güncelle
            return;
        }
        String[] value = new String[points.length + 1];//Kaydedilmiş adım varsa yeni liste üret; uzunluğu bir fazla olsun
        for(int index = 0; index < points.length; index++){//Eski listeyi yeni listeye kopyala
            value[index] = points[index];
        }
        value[value.length - 1] = text;//Yeni listenin son elemanını yeni gelen değer yap; yanî son adımı ekle
        points = value;//Listeyi tazele
        locationOnPoints++;//İmleci en sona al
    }
    public void removeCheckPoint(){//Listenin sonundan bir eleman siler bi iznillâh
        if(points == null)//Liste boşsa listeden eleman çıkarılamaz
            return;
        if(points.length == 1){//Listede sadece bir kayıt noktası varsa, kayıt noktası listesini sil
            points = null;
            locationOnPoints = 0;
        }
        String[] value = new String[points.length - 1];//Yeni liste oluştur; uzunluğu kayıt listesinden bir az olsun, bi iznillâh
        for(int index = 0; index < value.length; index++){
            value[index] = points[index];
        }
        locationOnPoints--;//İmleci yeni listenin sonuna getir
        points = value;//Listeyi tazele
    }
    public String getLastText(){
        if(points == null)//Kayıt noktaları listesi boş ise null değerini döndür
            return null;
        if(locationOnPoints == 0)
            return null;
        locationOnPoints--;//İmleci bir sola kaydır
        return points[locationOnPoints];//İmlecin öncesindeki değeri döndür
    }
    public String getNextText(){
        if(points == null)//Kayıtlı nokta yoksa null değerini döndür
            return null;
        if(locationOnPoints == points.length)//İleriye doğru kayıt noktası yoksa null değerini döndür
            return null;
        String value = points[locationOnPoints];
        locationOnPoints++;//İmleci bir sağa kaydır
        return value;//İmlecin bulunduğu değeri döndür
    }
    public void resetNextChPoints(){
        if(points == null)//Liste boşsa bir şey yapma
            return;
        if(locationOnPoints == points.length)//İmleç listenin sonundaysa bir şey yapma
            return;
        String[] value = new String[locationOnPoints];//Yeni liste oluştur, uzunluğu imlecin olduğu yere kadar olan liste uzunluğu olsun bi iznillâh
        for(int index = 0; index < locationOnPoints; index++){//İlgili yere kadar olan kayıt noktalarını yeni listeye kopyala
            value[index] = points[index];
        }
        points = value;//Listeyi tazele
        //İleri butonu donuk hâle getirilebilir.
    }

    //ARKAPLAN İŞLEM YÖNTEMLERİ:
    public void goToNextLocation(){//İmleci bir sola kaydır
        if(points == null)//Liste boşsa bir şey yapma
            return;
        if(locationOnPoints == (points.length))//İmleç listenin sonundaysa bir şey yapma
            return;
        locationOnPoints++;
    }
    public void goToBackLocation(){//İmleci bir sağa kaydır
        if(points == null)//Liste boşsa bir şey yapma
            return;
        if(locationOnPoints == 0)//İmleç listenin başındaysa bir şey yapma
            return;
        locationOnPoints--;
    }
}