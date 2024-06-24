package View;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JComboBox;

public class ProduceUserPanel extends JPanel{
    GridBagLayout layoutForThis;
    JLabel lblName, lblPassword, lblSymbol;
    JTextField txtName, txtPassword;
    JComboBox cmboxSymbol;
    
    public ProduceUserPanel(){
        Add.ADDCOMP(this, getLblName(), 0, 0, 1, 1, Add.getInsets(), GridBagConstraints.HORIZONTAL, 1.0, 1.0);
        Add.ADDCOMP(this, getLblPassword(), 1, 0, 1, 1, Add.getInsets(), GridBagConstraints.HORIZONTAL, 1.0, 1.0);
        Add.ADDCOMP(this, getLblSymbol(), 0, 2, 1, 1, Add.getInsets(), GridBagConstraints.HORIZONTAL, 1.0, 1.0);
        Add.ADDCOMP(this, getTxtName(), 1, 0, 1, 1, Add.getInsets(), GridBagConstraints.HORIZONTAL, 1.0, 1.0);
        Add.ADDCOMP(this, getTxtPassword(), 1, 1, 1, 1, Add.getInsets(), GridBagConstraints.HORIZONTAL, 1.0, 1.0);
        Add.ADDCOMP(this, getCmboxSymbol(), 1, 2, 1, 1, Add.getInsets(), GridBagConstraints.HORIZONTAL, 1.0, 1.0);
    }

    public GridBagLayout getLayoutForThis() {
        if(layoutForThis == null){
            layoutForThis = new GridBagLayout();
        }
        return layoutForThis;
    }

    public void setLayoutForThis(GridBagLayout layoutForThis) {
        this.layoutForThis = layoutForThis;
    }

    public JLabel getLblName() {
        if(lblName == null){
            lblName = new JLabel("İsim: ");
        }
        return lblName;
    }

    public void setLblName(JLabel lblName) {
        this.lblName = lblName;
    }

    public JLabel getLblPassword() {
        if(lblPassword == null){
            lblPassword = new JLabel("Şifre: ");
        }
        return lblPassword;
    }

    public void setLblPassword(JLabel lblPassword) {
        this.lblPassword = lblPassword;
    }

    public JLabel getLblSymbol() {
        if(lblSymbol == null){
            lblSymbol = new JLabel("Kullanıcı simgesi: ");
        }
        return lblSymbol;
    }

    public void setLblSymbol(JLabel lblSymbol) {
        this.lblSymbol = lblSymbol;
    }

    public JTextField getTxtName() {
        if(txtName == null){
            txtName = new JTextField();
        }
        return txtName;
    }

    public void setTxtName(JTextField txtName) {
        this.txtName = txtName;
    }

    public JTextField getTxtPassword() {
        if(txtPassword == null){
            txtPassword = new JTextField();
        }
        return txtPassword;
    }

    public void setTxtPassword(JTextField txtPassword) {
        this.txtPassword = txtPassword;
    }

    public JComboBox getCmboxSymbol() {
        if(cmboxSymbol == null){
            cmboxSymbol = new JComboBox();
        }
        return cmboxSymbol;
    }

    public void setCmboxSymbol(JComboBox cmboxSymbol) {
        this.cmboxSymbol = cmboxSymbol;
    }
    
}
