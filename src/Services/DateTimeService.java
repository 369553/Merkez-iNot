package Services;

import java.util.Date;

public class DateTimeService{
    private static DateTimeService dtService;

    public DateTimeService(){
    }

//İŞLEM YÖNTEMLERİ:
    public String showDateTimeAsString(Date date){
        System.err.println("Gelen vakit bilgisi : " + date.toString());
        String strDate = date.toString();
        if(!strDate.contains("EET")){
            String[] values = date.toString().split(" ");
            StringBuilder strbuilderDateTime = new StringBuilder();
            strbuilderDateTime.append(values[2] + " ");
            strbuilderDateTime.append(getMonthNameAsTR(values[1]) + " ");
            strbuilderDateTime.append(values[5] + " ");
            strbuilderDateTime.append(values[3]);
            return new String(strbuilderDateTime);
        }
        StringBuilder strbuilderDateTime = new StringBuilder();
        String[] values = date.toString().split(" ");
      //  date : Sat May 07 17:18:52 EET 2022
        strbuilderDateTime.append(values[2] + " ");
        strbuilderDateTime.append(getMonthNameAsTR(values[1]) + " ");
        strbuilderDateTime.append(values[5] + " ");
        strbuilderDateTime.append(values[3]);
        return new String(strbuilderDateTime);
    }
    public String getMonthNameAsTR(String monthAbridgement){
        switch(monthAbridgement){
            case "Dec" :{
                return "Aralık";
            }
            case "Nov" :{
                return "Kasım";
            }
            case "Oct" :{
                return "Ekim";
            }
            case "Sep" :{
                return "Eylül";
            }
            case "Aug" :{
                return "Ağustos";
            }
            case "Jul" :{
                return "Temmuz";
            }
            case "Jun" :{
                return "Haziran";
            }
            case "May" :{
                return "Mayıs";
            }
            case "Apr" :{
                return "Nisan";
            }
            case "Mar" :{
                return "Mart";
            }
            case "Feb" :{
                return "Şubat";
            }
            case "Jan" :{
                return "Ocak";
            }
            default :{
                return "Ocak";
            }
        }
    }

//ERİŞİM YÖNTEMLERİ:
    //ANA ERİŞİM YÖNTEMİ:
    public static DateTimeService getDtService() {
        if(dtService == null){
            dtService = new DateTimeService();
        }
        return dtService;
    }
}