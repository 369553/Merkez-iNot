package Control;

import Base.GUISeeming;
import View.ContactPanel;
import View.EntryPage;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import javax.swing.JFileChooser;

public class ActEntryPage implements ActionListener{
    EntryPage page;
    JFileChooser chooser;
    private String code;
    String path = null;

    private ActEntryPage(EntryPage page, String code){
        this.code = code;
        this.page = page;
        page.getBtnYesAnswer().addActionListener(this);
        page.getBtnNoAnswer().addActionListener(this);
        page.getBtnMySql().addActionListener(this);
        page.getBtnHatirDB().addActionListener(this);
        page.getBtnChooseFile().addActionListener(this);
        page.getBtnComplete().addActionListener(this);
        page.getBtnBackTo().addActionListener(this);
        page.getBtnRunWithoutDB().addActionListener(this);


        page.getBtnYesAnswer().addMouseListener(page.getMov());
        page.getBtnNoAnswer().addMouseListener(page.getMov());
        page.getBtnMySql().addMouseListener(page.getMov());
        page.getBtnHatirDB().addMouseListener(page.getMov());
        page.getBtnChooseFile().addMouseListener(page.getMov());
        page.getBtnComplete().addMouseListener(page.getMov());
        page.getBtnBackTo().addMouseListener(page.getMov());
        page.getBtnRunWithoutDB().addMouseListener(page.getMov());
    }

//İŞLEM YÖNTEMLERİ:
    @Override
    public void actionPerformed(ActionEvent e){
        if(e.getSource() == page.getBtnNoAnswer()){
            if(page.getStepNo() == 1){          //İLK EKRAN : SİSTEMİ İLK KEZ Mİ ÇALIŞTIRIYORSUNUZ? GELEN CEVÂB = HAYIR
                page.setIsRunFirst(false);
                page.runStep2();
            }
        }
        else if(e.getSource() == page.getBtnYesAnswer()){
            if(page.getStepNo() == 1){      //İLK EKRAN : SİSTEMİ İLK KEZ Mİ ÇALIŞTIRIYORSUNUZ? GELEN CEVÂB = EVET
                page.setIsRunFirst(true);
                page.runStep2();
            }
        }
        else if(e.getSource() == page.getBtnMySql()){
            if(page.getStepNo() == 2){      //İKİNCİ EKRAN : VERİTABANI SEÇİMİ, SEÇİLEN : MYSQL
                page.setStrDBType("mysql");
                page.runStep3();
            }
        }
        else if(e.getSource() == page.getBtnHatirDB()){
            if(page.getStepNo() == 2){      //İKİNCİ EKRAN : VERİTABANI SEÇİMİ, SEÇİLEN : HATIRDB
                page.setStrDBType("hatirdb");
                page.runStep3();
            }
        }
        else if(e.getSource() == page.getBtnRunWithoutDB()){
            if(page.getStepNo() == 2){      //İKİNCİ EKRAN : SEÇİLEN : VERİTABANI OLMADAN ÇALIŞTIR
                page.setStrDBType("");
                this.runWithoutDB();
                page = null;
            }
        }
        else if(e.getSource() == page.getBtnChooseFile()){      //DÖRDÜNCÜ ERKAN : HâtırDB dosyasının konumunu seçmek dosya seçiciyi aç
            if(page.getStepNo() == 3){
                if(page.getStrDBType().toLowerCase().equals("hatirdb")){
                    openFileChooser();
                }
            }
        }
        else if(e.getSource() == page.getBtnBackTo()){
            if(page.getStepNo() == 2){
                page.setStrDBType(null);
                page.runStep1();
                page.repaint();
            }
            else if(page.getStepNo() == 3){
                page.setStrDBType(null);
                page.getTxtName().setText("");
                page.getTxtPassword().setText("");
                page.runStep2();
                page.repaint();
            }
        }
        else if(e.getSource() == chooser){      //DOSYA SEÇİCİ : HâtırDB dosyasının konumu
            if (chooser.getSelectedFile() != null) {
                path = chooser.getSelectedFile().getAbsolutePath() ;
                chooser.setSelectedFile(null) ;
            }
        }
        else if(e.getSource() == page.getBtnComplete()){
            if(page.getStrDBType().equals("mysql")){
                String pass = new String(page.getTxtPassword().getPassword());
                String username = page.getTxtName().getText();
                if(username.isEmpty()){
                    ContactPanel.getContactPanel().showMessage("Lütfen MySql'de kayıtlı kullanıcı adınızı giriniz", "Warning");
                    return;
                }
                /*if(pass.isEmpty()){
                    ContactPanel.getContactPanel().showMessage("Şifre alanını boş bırakıyorsunuz", "Info");
                    return;
                }*/
                HashMap<String,String> info = new HashMap<String, String>();
                info.put("username", username);
                info.put("password", pass);
                boolean isSuccessful = false;
                if(page.getIsRunFirst())
                    isSuccessful = IDARE.startInstallationOfSystem(code, "mysql", info, false, false);
                else
                    isSuccessful = IDARE.startInstallationOfSystem(code, "mysql", info, true, false);
                if(!isSuccessful){
                    ContactPanel.getContactPanel().showMessage("Giriş yapılamadı; bilgilerinizi, seçimlerinizin gerektirdiklerini kontrol edin", "Warning");
                    return;
                }
            }
            else if(page.getStrDBType().equals("hatirdb")){
                if(path == null){
                    ContactPanel.getContactPanel().showMessage("Lütfen dosya dizini seçin", "Warning");
                    return;
                }
                HashMap<String,String> info = new HashMap<String, String>();
                info.put("path", path);
                String pass = new String(page.getTxtPassword().getPassword());
                if(pass.isEmpty()){
                    ContactPanel.getContactPanel().showMessage("Şifre alanını boş bırakmayınız", "Warning");
                    return;
                }
                info.put("password", pass);
                boolean isSuccessful = false;
                if(page.getIsRunFirst())
                    isSuccessful = IDARE.startInstallationOfSystem(code, "hatirdb", info, false, false);
                else
                    isSuccessful = IDARE.startInstallationOfSystem(code, "hatirdb", info, true, false);
                if(!isSuccessful){
                    ContactPanel.getContactPanel().showMessage("Giriş yapılamadı; bilgilerinizi, seçimlerinizin gerektirdiklerini kontrol edin", "Warning");
                    return;
                }
            }
        }
    }

//İŞLEM YÖNTEMLERİ:
    private void openFileChooser(){
        chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.addActionListener(this) ;
        chooser.setAcceptAllFileFilterUsed(true);
        GUISeeming.appGUI(chooser);
        chooser.showDialog(page.getPnlContent(), "Klasörü seç");//Burada dosya seçicinin üst sınıfını EntryPage sayfası mı, yoksa pnlContent görsel bileşeni mi yapmak lazım?
    }
    private void runWithoutDB(){
        IDARE.startInstallationOfSystem(code, "", null, false, true);
    }

//ERİŞİM YÖNTEMLERİ:
    //ANA ERİŞİM YÖNTEMİ:
    public static ActEntryPage ProduceActEntryPage(EntryPage page, String code){
        if(code == null)
            return null;
        if(code.isEmpty())
            return null;
        if(page == null)
            return null;
        if(Services.CryptingService.getMd5(code).equals(IDARE.getMd5Code())){
            return new ActEntryPage(page, code);
        }
        return null;
    }
}