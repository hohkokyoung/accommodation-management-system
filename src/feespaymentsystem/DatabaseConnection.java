/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package feespaymentsystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 *
 * @author user
 */
public class DatabaseConnection {
    
//Variables declaration.
    public static Connection conn;
    public static String url = "jdbc:sqlserver://localhost:1433;databaseName="
            + "FeesPaymentSystem;integratedSecurity=true";
    public static Boolean errorDetection = false, closeErrorDetection = false;
    public static String errorDetails, closeErrorDetails;
   
    public static void DBConnection() {
        
        //Establish connection.
        try {
           Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
           conn = DriverManager.getConnection(url);
        } catch (ClassNotFoundException | SQLException ex) {
            
            //Boolean variable to indicate the database connection issue.
            errorDetection = true;
            errorDetails = "" + ex;
        }
    }
    
    public static void CloseDBConnection(){
        
        try {
            conn.close();
                } catch (SQLException ex) {
                    closeErrorDetection = true;
                    closeErrorDetails = "" + ex;
            }
    }
    
}

