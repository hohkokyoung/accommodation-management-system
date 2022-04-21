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
import static feespaymentsystem.Register.email;
import static feespaymentsystem.Register.finalNewID;
import static feespaymentsystem.Register.firstName;
import static feespaymentsystem.Register.lastName;
import static feespaymentsystem.Register.telephone;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author user
 */
public class UserRegistration {
    public static PreparedStatement registerPST;
    public static String randomPassword;
    
    public static void ResidentRegistration() {
        
        //Take the static method from DatabaseConnection to establish database connection.
        DatabaseConnection.DBConnection();
        
        //Catch the database error.
        if (errorDetection == true) {
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, errorDetails);
            System.out.println("Error: " + errorDetails);
        } else {
            
            try {
                   
                UserRegistrationList userRegistrationListForm = new UserRegistrationList();
                userRegistrationListForm.SetUserID(finalNewID);
                userRegistrationListForm.SetFirstName(firstName);
                userRegistrationListForm.SetLastName(lastName);
                userRegistrationListForm.SetTelephoneNumber(telephone);
                userRegistrationListForm.SetEmailAddress(email);

                Random random = new Random();
                randomPassword = String.format("%04d",random.nextInt(10000));
                
                String hashedRandomPassword = BCrypt.hashpw(randomPassword, BCrypt.gensalt());
                
                //SQL Statement to be executed. (Register Purposes)
                String registerSQL = "INSERT INTO Users "
                                    + "VALUES (?,?,?,?,?,?,?)";
                registerPST = conn.prepareStatement(registerSQL);
                registerPST.setString(1, userRegistrationListForm.GetUserID());
                registerPST.setString(2, userRegistrationListForm.GetFirstName());
                registerPST.setString(3, userRegistrationListForm.GetLastName());
                registerPST.setString(4, userRegistrationListForm.GetTelephoneNumber());
                registerPST.setString(5, userRegistrationListForm.GetEmailAddress());
                registerPST.setString(6, hashedRandomPassword);
                registerPST.setString(7, "Resident");   
                
                //Execute the insert query.
                registerPST.executeUpdate();    
                
            } catch (SQLException ex) {

            Logger.getLogger(UsedTelephoneEmail.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Error: " + closeErrorDetails);
                
            }
            
            
            
        }
            
        
    }
    
}
