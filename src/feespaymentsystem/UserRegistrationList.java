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
public class UserRegistrationList {
    private String userID, firstName, lastName, telephone, email;
    
    public void SetUserID(String userID){
        this.userID = userID;
    }
    
    public void SetFirstName(String firstName){
        this.firstName = firstName;
    }
    
    public void SetLastName(String lastName){
        this.lastName = lastName;
    }
    
    public void SetTelephoneNumber(String telephone){
        this.telephone = telephone;
    }
    
    public void SetEmailAddress(String email){
        this.email = email;
    }
    
    public String GetUserID(){
        return userID;
    }
    
    public String GetFirstName(){
        return firstName;
    }
    
    public String GetLastName(){
        return lastName;
    }
    
    public String GetTelephoneNumber(){
        return telephone;
    }
    
    public String GetEmailAddress(){
        return email;
    }
    
}
