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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author user
 */
public class DisplayReceipt {
    public static String receiptSQL;
    public static PreparedStatement receiptPST;
    public static ResultSet receiptResult;

    public static void LatestPayment() {
         
        //Take the static method from DatabaseConnection to establish database connection.
        DatabaseConnection.DBConnection();
        
        //Catch the database error.
        if (errorDetection == true) {
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, errorDetails);
            System.out.println("Error: " + errorDetails);
        } else {
            
            try {
            
                //SQL Statement to be executed. (Login Purposes)
                receiptSQL = "SELECT TOP(1) * "
                        + "FROM Payments "
                        + "ORDER BY Payment_ID DESC";               
                receiptPST = conn.prepareStatement(receiptSQL);
                receiptResult = receiptPST.executeQuery();
            
            //Catch SQL execution error.
            } catch (SQLException ex) {
                
                Logger.getLogger(DisplayReceipt.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("Error: " + closeErrorDetails);
                
            }
                    
            
        }
        
    }
    
}
