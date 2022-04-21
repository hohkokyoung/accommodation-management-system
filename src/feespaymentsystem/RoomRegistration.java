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
import static feespaymentsystem.Payment.roomUserID;
import static feespaymentsystem.Payment.selectedUnit;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author user
 */
public class RoomRegistration {
    public static PreparedStatement roomRegisterPST;
    
        public static void ResidentRoomRegistration() {
        
        //Take the static method from DatabaseConnection to establish database connection.
        DatabaseConnection.DBConnection();
        
        //Catch the database error.
        if (errorDetection == true) {
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, errorDetails);
            System.out.println("Error: " + errorDetails);
        } else {
            
            try {
                   
                String roomRegisterSQL = "UPDATE Rooms "
                                    + "SET User_ID = ? "
                                    + "WHERE Room_ID = ?";
                roomRegisterPST = conn.prepareStatement(roomRegisterSQL);
                roomRegisterPST.setString(1, roomUserID);
                roomRegisterPST.setString(2, selectedUnit);

                //Execute the insert query.
                roomRegisterPST.executeUpdate();    
                
            } catch (SQLException ex) {

            Logger.getLogger(UsedTelephoneEmail.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Error: " + closeErrorDetails);
                
            }
            
            
            
        }
            
        
    }
    
}
