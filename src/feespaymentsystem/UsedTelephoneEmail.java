/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package feespaymentsystem;

import static feespaymentsystem.DatabaseConnection.closeErrorDetails;
import static feespaymentsystem.DatabaseConnection.conn;
import static feespaymentsystem.DatabaseConnection.errorDetails;
import static feespaymentsystem.DatabaseConnection.errorDetection;
import static feespaymentsystem.Login.userID;
import static feespaymentsystem.Login.userRole;
import static feespaymentsystem.StaffResident.residentID;
import static feespaymentsystem.Register.userRegistrationInitiation;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author user
 */
public class UsedTelephoneEmail {
    public static String usedInfoSQL, registerUsedInfoSQL;
    public static PreparedStatement usedInfoPST, lengthUsedInfoPST, registrationUsedInfoPST, lengthRegistrationUsedInfoPST;
    public static ResultSet usedInfoResult, lengthUsedInfoResult, registrationUsedInfoResult, lengthRegistrationUsedInfoResult;
    public static int lengthUsedInfo = 0;
    public static String[] lengthUsedInfoTelephone;
    public static String[] lengthUsedInfoEmail;
            
    public static void UsedResidentInfo(){
        
        //Take the static method from DatabaseConnection to establish database connection.
        DatabaseConnection.DBConnection();
        
        //Catch the database error.
        if (errorDetection == true) {
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, errorDetails);
            System.out.println("Error: " + errorDetails);
        } else {
            
            try {
                
                //SQL Statement to be executed. (Modify Purposes)
                usedInfoSQL = "SELECT User_TelephoneNumber, User_EmailAddress "
                            + "FROM Users "
                            + "WHERE User_ID != ?";    
                
                usedInfoPST = conn.prepareStatement(usedInfoSQL);
                
                lengthUsedInfoPST = conn.prepareStatement(usedInfoSQL);
                    
                registerUsedInfoSQL = "SELECT User_TelephoneNumber, User_EmailAddress "
                                   + "FROM Users";
                
                registrationUsedInfoPST = conn.prepareStatement(registerUsedInfoSQL);

                lengthRegistrationUsedInfoPST = conn.prepareStatement(registerUsedInfoSQL);
                
                if (userRegistrationInitiation == true){
                    
                    registrationUsedInfoResult = registrationUsedInfoPST.executeQuery();
                    
                    lengthRegistrationUsedInfoResult = lengthRegistrationUsedInfoPST.executeQuery();
                    
                    //Loop to count the size of the array.
                    while (lengthRegistrationUsedInfoResult.next()){
                        lengthUsedInfo++;
                    }
                    
                    //Array to store all the used info.
                    lengthUsedInfoTelephone = new String[lengthUsedInfo];   
                    lengthUsedInfoEmail = new String[lengthUsedInfo];
                    
                } else if (userRegistrationInitiation == false){
                            
                    if ("Resident".equals(userRole.trim())){

                        usedInfoPST.setString(1, userID);

                        lengthUsedInfoPST.setString(1, userID);

                    } else if ("Staff".equals(userRole.trim())){

                        usedInfoPST.setString(1, residentID);

                        lengthUsedInfoPST.setString(1, residentID);

                    }

                    usedInfoResult = usedInfoPST.executeQuery();

                    lengthUsedInfoResult = lengthUsedInfoPST.executeQuery();

                    //Loop to count the size of the array.
                    while (lengthUsedInfoResult.next()){
                        lengthUsedInfo++;
                    }
                    
                    //Array to store all the used info.
                    lengthUsedInfoTelephone = new String[lengthUsedInfo];   
                    lengthUsedInfoEmail = new String[lengthUsedInfo];
                    
                }
                

            
            } catch (SQLException ex) {
                 
                Logger.getLogger(UsedTelephoneEmail.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("Error: " + closeErrorDetails);
                
            }
            
            
            
        } 
    }
    
}
