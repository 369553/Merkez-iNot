package View;

import Base.GUISeeming;
import Control.Movements;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.Font;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JPasswordField;

public class EntryPage extends JFrame{
    private static EntryPage entryPage;
    private boolean isRunFirst = false;
    private String strDBType = null;
    private short stepNo = 1;
    JPanel pnlContent;
    JLabel lblMessageText, lblName, lblPassword;
    JButton btnYesAnswer, btnNoAnswer, btnMySql, btnHatırDB, btnChooseFile, btnComplete, btnBackTo, btnRunWithoutDB;
    JTextField txtName;
    JPasswordField txtPassword;
    BorderLayout order;
    Movements mov;

    private EntryPage(){
        super("HâtırNOT");
        this.setLayout(getOrder());
        Dimension systemSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setBounds(systemSize.width / 7, systemSize.height / 7, 2 * (systemSize.width / 3), 2 * (systemSize.height / 3));
        this.setPreferredSize(new Dimension(systemSize.width / 2, systemSize.height / 2));
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.add(getPnlContent(), BorderLayout.CENTER);
        runStep1();
        GUISeeming.appGUI(this.getContentPane());
    }

//İŞLEM YÖNTEMLERİ:
    public void runStep1(){
        this.getPnlContent().removeAll();
        stepNo = 1;
        addContactPanel();
        Add.ADDCOMP(getPnlContent(), getLblMessageText(), 0, 0, 2, 1, Add.Insetser(0, 0, 5, 0), GridBagConstraints.CENTER, 1.0, 2.0);
        Add.ADDCOMP(getPnlContent(), getBtnYesAnswer(), 0, 1, 1, 1, Add.Insetser(5, 0, 0, 5), GridBagConstraints.HORIZONTAL, 1.0, 1.0);
        Add.ADDCOMP(getPnlContent(), getBtnNoAnswer(), 1, 1, 1, 1, Add.Insetser(5, 5, 0, 0), GridBagConstraints.HORIZONTAL, 1.0, 1.0);
        getLblMessageText().setText("Sistemi ilk kez mi çalıştırıyorsunuz?");
        addBtnBackTo();
   //     this.add(getPnlContent(), BorderLayout.CENTER);
        updateSeeming();
    }
    public void runStep2(){
        this.getPnlContent().removeAll();
        stepNo = 2;
        addBtnBackTo();
        addContactPanel();
        Add.ADDCOMP(getPnlContent(), getLblMessageText(), 0, 0, 2, 1, Add.Insetser(0, 0, 5, 0), GridBagConstraints.CENTER, 1.0, 2.0);
        Add.ADDCOMP(getPnlContent(), getBtnMySql(), 0, 1, 1, 1, Add.Insetser(5, 0, 0, 5), GridBagConstraints.HORIZONTAL, 1.0, 1.0);
        Add.ADDCOMP(getPnlContent(), getBtnHatirDB(), 1, 1, 1, 1, Add.Insetser(5, 5, 0, 0), GridBagConstraints.HORIZONTAL, 1.0, 1.0);
        Add.ADDCOMP(getPnlContent(), getBtnRunWithoutDB(), 0, 2, 2, 1, Add.Insetser(5, 0, 5, 0), GridBagConstraints.CENTER, 1.0, 1.0);
        if(isRunFirst)
            getLblMessageText().setText("Kayıtlarınızın saklanması için bir veritabanı seçin");
        else
            getLblMessageText().setText("Verilerinizi kaydetmek için hangi veritabanını kullanmıştınız?");
        addBtnBackTo();
    //    this.add(getPnlContent(), BorderLayout.CENTER);
        updateSeeming();
    }
    public void runStep3(){
        this.getPnlContent().removeAll();
        stepNo = 3;
        addBtnBackTo();
        addContactPanel();
        if(strDBType.toLowerCase().equals("mysql")){
            runStep3ForMySql();
        }
        else{
            runStep3ForHatirDB();
        }
        addBtnBackTo();
    //    this.add(getPnlContent(), BorderLayout.CENTER);
        updateSeeming();
        getTxtName().requestFocus();
    }
    /*public void runStep4(){
        addContactPanel();
        this.removeAll();
    }*/
    public void updateSeeming(){
        GUISeeming.appGUI(this.getPnlContent());
        this.getLblMessageText().setFont(this.getLblMessageText().getFont().deriveFont(Font.BOLD | Font.ITALIC, 17));
        this.setVisible(true);
    }
    public void addContactPanel(){
        this.add(ContactPanel.getContactPanel(), BorderLayout.SOUTH);
    }
    public void addBtnBackTo(){
        this.add(getBtnBackTo(), BorderLayout.NORTH);
    }
    //ARKAPLAN İŞLEM YÖNTEMLERİ:
    private void runStep3ForMySql(){
        Add.ADDCOMP(getPnlContent(), getLblMessageText(), 0, 0, 2, 1, Add.Insetser(0, 0, 5, 0), GridBagConstraints.CENTER, 1.0, 2.0);
        Add.ADDCOMP(getPnlContent(), getLblName(), 0, 1, 1, 1, Add.Insetser(5, 0, 5, 5), GridBagConstraints.CENTER, 1.0, 1.0);
        Add.ADDCOMP(getPnlContent(), getTxtName(), 1, 1, 1, 1, Add.Insetser(0, 5, 5, 0), GridBagConstraints.HORIZONTAL, 1.0, 1.0);
        Add.ADDCOMP(getPnlContent(), getLblPassword(), 0, 2, 1, 1, Add.Insetser(5, 0, 5, 5), GridBagConstraints.CENTER, 1.0, 1.0);
        Add.ADDCOMP(getPnlContent(), getTxtPassword(), 1, 2, 1, 1, Add.Insetser(5, 5, 0, 0), GridBagConstraints.HORIZONTAL, 1.0, 1.0);
        getLblMessageText().setText("MySQL veritabanı bağlantı bilgilerini girin");
        getLblName().setText("Kullanıcı adını giriniz: ");
        getLblPassword().setText("Şifreyi girin: ");
        Add.ADDCOMP(getPnlContent(), getBtnComplete(), 0, 3, 2, 1, Add.Insetser(5, 0, 0, 0), GridBagConstraints.BOTH, 1.0, 1.0);
    }
    private void runStep3ForHatirDB(){
        Add.ADDCOMP(getPnlContent(), getLblMessageText(), 0, 0, 2, 1, Add.Insetser(0, 0, 5, 0), GridBagConstraints.CENTER, 1.0, 2.0);
        Add.ADDCOMP(getPnlContent(), getLblName(), 0, 1, 1, 1, Add.Insetser(5, 0, 5, 5), GridBagConstraints.CENTER, 1.0, 1.0);
        Add.ADDCOMP(getPnlContent(), getBtnChooseFile(), 1, 1, 1, 1, Add.Insetser(0, 5, 5, 0), GridBagConstraints.HORIZONTAL, 1.0, 1.0);
        Add.ADDCOMP(getPnlContent(), getLblPassword(), 0, 2, 1, 1, Add.Insetser(5, 0, 5, 5), GridBagConstraints.CENTER, 1.0, 1.0);
        Add.ADDCOMP(getPnlContent(), getTxtPassword(), 1, 2, 1, 1, Add.Insetser(5, 5, 0, 0), GridBagConstraints.HORIZONTAL, 1.0, 1.0);
        getLblMessageText().setText("HâtırDB veritabanı bağlantı bilgilerini girin");
        getLblName().setText("Dosyayı seçiniz");
        getLblPassword().setText("Şifreyi girin: ");
        if(isRunFirst)
            ContactPanel.getContactPanel().showMessage("Sistemin yedekten yüklenmesini kontrol altında tutmak için bir şifre belirlemelisiniz", "Info");
        else
            ContactPanel.getContactPanel().showMessage("Daha önce belirlediğiniz şifreyi giriniz", "Info");
        Add.ADDCOMP(getPnlContent(), getBtnComplete(), 0, 3, 2, 1, Add.Insetser(5, 0, 0, 0), GridBagConstraints.BOTH, 1.0, 1.0);
    }

//ERİŞİM YÖNTEMLERİ:
    //ANA ERİŞİM YÖNTEMİ:
    public static EntryPage getEntryPage(){
        if(entryPage == null){
             entryPage = new EntryPage();
        }
        return entryPage;
    }
    public boolean getIsRunFirst() {
        return isRunFirst;
    }
    public void setIsRunFirst(boolean isRunFirst) {
        this.isRunFirst = isRunFirst;
    }
    public String getStrDBType() {
        return strDBType;
    }
    public void setStrDBType(String strDBType) {
        this.strDBType = strDBType;
    }
    public JPanel getPnlContent() {
        if(pnlContent == null){
            pnlContent = new JPanel();
            pnlContent.setLayout(new GridBagLayout());
            pnlContent.setPreferredSize(new Dimension(500, 200));
        }
        return pnlContent;
    }
    public JLabel getLblMessageText() {
        if(lblMessageText == null){
            lblMessageText = new JLabel("Sistemi ilk kez mi çalıştırıyorsunuz?");
        }
        return lblMessageText;
    }
    public void setLblMessageText(JLabel lblMessageText) {
        this.lblMessageText = lblMessageText;
    }
    public JLabel getLblName() {
        if(lblName == null){
            lblName = new JLabel("Kullanıcı adınız: ");
        }
        return lblName;
    }
    public JLabel getLblPassword() {
        if(lblPassword == null){
            lblPassword = new JLabel("Şifreniz: ");
        }
        return lblPassword;
    }
    public JButton getBtnYesAnswer() {
        if(btnYesAnswer == null){
            btnYesAnswer = new JButton("Evet");
            btnYesAnswer.setPreferredSize(new Dimension(150, 50));
        }
        return btnYesAnswer;
    }
    public JButton getBtnNoAnswer() {
        if(btnNoAnswer == null){
            btnNoAnswer = new JButton("Hayır");
            btnNoAnswer.setPreferredSize(new Dimension(150, 50));
        }
        return btnNoAnswer;
    }
    public JButton getBtnMySql() {
        if(btnMySql == null){
            btnMySql = new JButton("Cihazınızda kurulu MySQL veritabanını kullanın");
            btnMySql.setPreferredSize(new Dimension(150, 50));
            btnMySql.setEnabled(true);
        }
        return btnMySql;
    }
    public JButton getBtnHatirDB() {
        if(btnHatırDB == null){
            btnHatırDB = new JButton("HâtırNOT'a özel, yerel, şifreli dosya biçimi");
            btnHatırDB.setPreferredSize(new Dimension(150, 50));
        }
        return btnHatırDB;
    }
    public JButton getBtnRunWithoutDB(){
        if(btnRunWithoutDB == null){
            btnRunWithoutDB = new JButton("Sistemi veritabanı olmadan çalıştırın");
            btnRunWithoutDB.setPreferredSize(new Dimension(300, 50));
        }
        return btnRunWithoutDB;
    }
    public JButton getBtnChooseFile(){
        if(btnChooseFile == null){
            btnChooseFile = new JButton("Dosya dizini seçin");
            btnChooseFile.setPreferredSize(new Dimension(160, 50));
        }
        return btnChooseFile;
    }
    public JButton getBtnComplete(){
        if(btnComplete == null){
            btnComplete = new JButton("Giriş Yap");
            btnComplete.setPreferredSize(new Dimension(150, 50));
        }
        return btnComplete;
    }
    public JButton getBtnBackTo(){
        if(btnBackTo == null){
            btnBackTo = new JButton("ÖNCEKİ ADIMA GERİ DÖN");
            btnBackTo.setPreferredSize(new Dimension(150, 50));
        }
        return btnBackTo;
    }
    public JTextField getTxtName(){
        if(txtName == null){
            txtName = new JTextField();
            txtName.setPreferredSize(new Dimension(200, 40));
        }
        return txtName;
    }
    public JPasswordField getTxtPassword(){
        if(txtPassword == null){
            txtPassword = new JPasswordField();
            txtPassword.setPreferredSize(new Dimension(200, 40));
        }
        return txtPassword;
    }
    public BorderLayout getOrder(){
        if(order == null){
            order = new BorderLayout(5, 5);
        }
        return order;
    }
    public short getStepNo(){
        return stepNo;
    }
    public void setStepNo(short stepNo){
        this.stepNo = stepNo;
    }
    public Movements getMov(){
        if(mov == null){
            mov = new Movements();
        }
        return mov;
    }
}