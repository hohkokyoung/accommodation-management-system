/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package feespaymentsystem;

/**
 *
 * @author user
 */
public class ModifyResidentList {
//Variables declaration.
private String telephone, email, newPassword;

    public void SetTelephone(String telephone){
        this.telephone = telephone;
    }
    
    public void SetEmail(String email){
        this.email = email;
    }
    
    public void SetNewPassword(String newPassword){
        this.newPassword = newPassword;
    }

    public String GetTelephone() {
        return telephone;
    }
    
    public String GetEmail() {
        return email;
    }
    
    public String GetNewPassword() {
        String hashedNewPassword = BCrypt.hashpw(newPassword, BCrypt.gensalt());
        return hashedNewPassword;
    }
    
}
