/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package feespaymentsystem;

/**
 *
 * @author userz
 */
public class ResidentsList {
    //Variables declaration.
    private String residentID, residentFirstName, residentLastName, 
            residentTelephoneNumber, residentEmailAddress, residentRoomID, fees;

    //Encapsulations.
    public void SetResidentID(String residentID) {
        this.residentID = residentID;
    }
    
    public void SetResidentFirstName(String residentFirstName){
        this.residentFirstName = residentFirstName;
    }
    
    public void SetResidentLastName(String residentLastName){
        this.residentLastName = residentLastName;
    }
    
    public void SetResidentTelephoneNumber(String residentTelephoneNumber){
        this.residentTelephoneNumber = residentTelephoneNumber;
    }
    
    public void SetResidentEmailAddress(String residentEmailAddress){
        this.residentEmailAddress = residentEmailAddress;
    }
    
    public void SetResidentResidentRoomID(String residentRoomID){
        this.residentRoomID = residentRoomID;
    }
    
    public void SetOutstandingFees(String fees){
        this.fees = fees;
    }
    
    public String GetResidentID(){
        return residentID;
    }
    
    public String GetResidentFirstName(){
        return residentFirstName;
    }
    
    public String GetResidentLastName(){
        return residentLastName;
    }
    
    public String GetResidentTelephoneNumber(){
        return residentTelephoneNumber;
    }
    
    public String GetResidentEmailAddress(){
        return residentEmailAddress;
    }
    
    public String GetResidentRoomID(){
        return residentRoomID;
    }
    
    public String GetOustandingFees(){
        return "RM " + fees;
    }
    
}
